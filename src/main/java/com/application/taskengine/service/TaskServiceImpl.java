package com.application.taskengine.service;

import com.application.taskengine.LogMap;
import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskLogModel;
import com.application.taskengine.model.TaskParamValueModel;
import com.application.taskengine.model.TaskPluginModel;
import com.application.taskengine.util.DynamicSchedulerFactory;
import com.application.taskengine.util.SchedulerUtil;
import com.application.taskengine.vo.ScheduleTaskVo;
import com.cheng.jdbc.SQLParameter;
import com.cheng.jdbc.itf.IBaseDAO;
import com.cheng.lang.PageVO;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.ApplicationLogger;
import com.cheng.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl implements ITaskService {

    @Resource
    IBaseDAO baseDAO;

    @Override
    public void initTask() throws BusinessException {
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam("Y");
        List<TaskDeployModel> tasks = baseDAO.queryByClause(TaskDeployModel.class, " runnable=? and dr= 0", sqlParameter);
        for (TaskDeployModel task : tasks) {
            try {
                DynamicSchedulerFactory.addTask(createScheduleJobVo(task));
            } catch (Exception e) {
                ApplicationLogger.error(e);
            }
        }
    }

    public boolean addTask(TaskDeployModel taskDeployModel) throws BusinessException {
        if (StringUtils.isBlank(taskDeployModel.getPkTaskdeploy())) {
            baseDAO.insert(taskDeployModel);
            try {
                DynamicSchedulerFactory.addTask(createScheduleJobVo(taskDeployModel));
            } catch (SchedulerException e) {
                ApplicationLogger.error(e);
                throw new BusinessException("任务添加失败");
            }
            return true;
        } else {
            return updateTask(taskDeployModel);
        }
    }


    public boolean removeTask(TaskDeployModel taskdeploy) throws BusinessException {
        baseDAO.deleteByPK(TaskDeployModel.class, taskdeploy.getPrimaryKey());
        try {
            DynamicSchedulerFactory.removeTask(taskdeploy.getPkTaskdeploy(), taskdeploy.getPkTaskplugin());
        } catch (SchedulerException s) {
            ApplicationLogger.error(s);
            throw new BusinessException("删除失败");
        }
        return true;
    }

    public void disableTask(TaskDeployModel taskdeploy) throws BusinessException {
        taskdeploy.setRunnable("N");
        taskdeploy.setTs(TimeToolkit.getCurrentTs());
        baseDAO.update(taskdeploy, new String[]{"runnable", "ts"});
        try {
            DynamicSchedulerFactory.removeTask(taskdeploy.getPkTaskdeploy(), taskdeploy.getPkTaskplugin());
        } catch (SchedulerException s) {
            ApplicationLogger.error(s);
            throw new BusinessException("禁用失败");
        }
    }

    @Override
    public void enableTask(TaskDeployModel taskDeployModel) {
        taskDeployModel.setRunnable("Y");
        taskDeployModel.setTs(TimeToolkit.getCurrentTs());
        baseDAO.update(taskDeployModel, new String[]{"runnable", "ts"});
        try {
            DynamicSchedulerFactory.addTask(createScheduleJobVo(taskDeployModel));
        } catch (SchedulerException e) {
            ApplicationLogger.error(e);
            throw new BusinessException("任务添加失败");
        }
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
            mdata.put("taskname", taskDeployModel.getTaskname());
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

    public boolean updateTask(TaskDeployModel taskDeployModel) throws BusinessException {
        TaskDeployModel dbtaskDeployModel = baseDAO.queryByPK(TaskDeployModel.class, taskDeployModel.getPkTaskdeploy());
        if ("N".equals(dbtaskDeployModel.getRunnable())) {
            if ("Y".equals(taskDeployModel.getRunnable())) {
                addTask(taskDeployModel);
            } else {

            }
            baseDAO.update(taskDeployModel);
            return true;
        } else {
            if ("Y".equals(taskDeployModel.getRunnable())) {
                baseDAO.update(taskDeployModel);
            }
        }

        if ("N".equals(taskDeployModel.getRunnable())) {
            disableTask(taskDeployModel);
            return false;
        }
        try {
            DynamicSchedulerFactory.rescheduleTask(createScheduleJobVo(taskDeployModel));
        } catch (SchedulerException e) {
            ApplicationLogger.error(e);
            throw new BusinessException("任务更新失败");
        }
        return true;
    }

    public boolean runOnceTask(TaskDeployModel taskDeployModel) {
        try {
            DynamicSchedulerFactory.runOnceTask(taskDeployModel.getPkTaskdeploy(), taskDeployModel.getPkTaskplugin());
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("runOnceTask task is error ", e);
            return false;
        }
    }


    public boolean pause(TaskDeployModel taskDeployModel) {
        try {
            DynamicSchedulerFactory.pauseJob(taskDeployModel.getPkTaskdeploy(), taskDeployModel.getPkTaskplugin());    // jobStatus do not store
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("pause task is error ", e);
            return false;
        }
    }

    public boolean resume(TaskDeployModel taskDeployModel) {
        try {
            DynamicSchedulerFactory.resumeJob(taskDeployModel.getPkTaskdeploy(), taskDeployModel.getPkTaskplugin());
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("resume task is error ", e);
            return false;
        }
    }


    private ScheduleTaskVo createScheduleJobVo(TaskDeployModel taskDeployModel) {
        ScheduleTaskVo scheduleTaskVo = new ScheduleTaskVo();
        scheduleTaskVo.setJobCode(taskDeployModel.getPkTaskdeploy());
        scheduleTaskVo.setJobGroupCode(taskDeployModel.getPkTaskplugin());
        scheduleTaskVo.setCronExpression(taskDeployModel.getTriggerstr());
        TaskPluginModel t = baseDAO.queryByPK(TaskPluginModel.class, taskDeployModel.getPkTaskplugin(), new String[]{"pluginclass"});
        scheduleTaskVo.setJobClass(t.getPluginclass());
        try {
            SchedulerUtil.parse(scheduleTaskVo);
            scheduleTaskVo.getJobDetail().getJobDataMap().putAll(getParams(taskDeployModel));
        } catch (Exception e) {
            ApplicationLogger.error(e);
            throw new BusinessException(e);
        }
        return scheduleTaskVo;
    }

    private Map<String, Object> getParams(TaskDeployModel taskDeployModel) throws Exception {
        Map<String, Object> taskunitmap = new HashMap<>();
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(taskDeployModel.getPrimaryKey());
        List<TaskParamValueModel> taskParamValueModels = baseDAO.queryByClause(TaskParamValueModel.class, " dr=0 and pkTaskdeploy=?", sqlParameter);
        for (int j = 0; j < taskParamValueModels.size(); j++) {
            TaskParamValueModel t = taskParamValueModels.get(j);
            taskunitmap.put(t.getParamkey(), t.getParamvalue());
        }
        taskunitmap.put(taskDeployModel.getPKFieldName(), taskDeployModel.getPrimaryKey());
        return taskunitmap;
    }
}
