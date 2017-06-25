package com.application.taskengine.service;

import com.application.console.service.JobAPIService;
import com.application.console.service.impl.JobAPIServiceImpl;
import com.application.taskengine.LogMap;
import com.application.taskengine.common.IElasticJobInit;
import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskLogModel;
import com.cheng.jdbc.itf.IBaseDAO;
import com.cheng.lang.PageVO;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.ApplicationLogger;
import com.cheng.util.BeanUtil;
import com.cheng.web.ApplicationServiceLocator;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl implements ITaskService {

    private JobAPIService jobAPIService = new JobAPIServiceImpl();

    @Resource
    IBaseDAO baseDAO;

    public boolean saveTask(TaskDeployModel taskDeployModel) throws BusinessException {
        if (StringUtils.isBlank(taskDeployModel.getPkTaskdeploy())) {
            baseDAO.insert(taskDeployModel);
            return true;
        } else {
            baseDAO.update(taskDeployModel);
            return true;
        }
    }


    public boolean removeTask(TaskDeployModel taskdeploy) throws BusinessException {
        baseDAO.deleteByPK(TaskDeployModel.class, taskdeploy.getPrimaryKey());
        try {
            jobAPIService.getJobOperatorAPI().shutdown(Optional.of(taskdeploy.getPkTaskdeploy()), Optional.<String>absent());
            jobAPIService.getJobSettingsAPI().removeJobSettings(taskdeploy.getPkTaskdeploy());
        } catch (Exception s) {
            ApplicationLogger.error(s);
            throw new BusinessException("删除失败");
        }
        return true;
    }

    public void disableTask(TaskDeployModel taskdeploy) throws BusinessException {
        taskdeploy.setRunnable("N");
        taskdeploy.setTs(TimeToolkit.getCurrentTs());
        baseDAO.update(taskdeploy, new String[]{"runnable", "ts"});
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(taskdeploy.getPkTaskdeploy()), Optional.<String>absent());
        jobAPIService.getJobSettingsAPI().removeJobSettings(taskdeploy.getPkTaskdeploy());
    }

    public void enableTask(TaskDeployModel taskDeployModel) {
        taskDeployModel.setRunnable("Y");
        taskDeployModel.setTs(TimeToolkit.getCurrentTs());
        baseDAO.update(taskDeployModel, new String[]{"runnable", "ts"});
        //从数据库中初始化任务配置
        ApplicationServiceLocator.getService(IElasticJobInit.class).JobInit(taskDeployModel.getPkTaskdeploy());
        jobAPIService.getJobOperatorAPI().enable(Optional.of(taskDeployModel.getPkTaskdeploy()), Optional.<String>absent());
    }

    @Override
    public PageVO getTaskLog(String pkTaskdeploy, PageVO pageVO) {

        if (!LogMap.ispersistence) {
            pageVO = init(pkTaskdeploy);
        } else {
            pageVO.setCondition(" dr=0 and pkTaskdeploy='" + pkTaskdeploy + "'");
            pageVO = baseDAO.queryByPage(TaskLogModel.class, pageVO);
        }
        List<TaskLogModel> list = (List<TaskLogModel>) pageVO.getData();
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TaskLogModel taskLogModel = list.get(i);
            Map<String, Object> mdata = BeanUtil.getValueMap(taskLogModel);
            TaskDeployModel taskDeployModel = baseDAO.queryByPK(TaskDeployModel.class, taskLogModel.getPkTaskdeploy());
            mdata.put("taskname", taskDeployModel.getTaskName());
            data.add(mdata);
        }
        pageVO.setData(data);

        return pageVO;
    }


    private PageVO init(String key) {
        PageVO pageVO = new PageVO(1, 20);
        List<TaskLogModel> data = LogMap.getLog(key);
        pageVO.setData(data);
        return pageVO;
    }

    public boolean runOnceTask(TaskDeployModel taskDeployModel) {
        try {
            jobAPIService.getJobOperatorAPI().trigger(Optional.of(taskDeployModel.getPkTaskdeploy()), Optional.<String>absent());
            return true;
        } catch (Exception e) {
            ApplicationLogger.error("runOnceTask task is error ", e);
            return false;
        }
    }


    /**
     * 暂停
     *
     * @param taskDeployModel
     * @return
     */
    public boolean pause(TaskDeployModel taskDeployModel) {
        try {
            jobAPIService.getJobOperatorAPI().disable(Optional.of(taskDeployModel.getPkTaskdeploy()), Optional.<String>absent());
            return true;
        } catch (Exception e) {
            ApplicationLogger.error("pause task is error ", e);
            return false;
        }
    }

    /**
     * 重新运行
     *
     * @param taskDeployModel
     * @return
     */
    public boolean resume(TaskDeployModel taskDeployModel) {
        try {
            jobAPIService.getJobOperatorAPI().enable(Optional.of(taskDeployModel.getPkTaskdeploy()), Optional.<String>absent());
            return true;
        } catch (Exception e) {
            ApplicationLogger.error("resume task is error ", e);
            return false;
        }
    }
}
