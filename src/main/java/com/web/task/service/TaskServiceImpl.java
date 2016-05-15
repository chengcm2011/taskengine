package com.web.task.service;

import cheng.lib.exception.BusinessException;
import cheng.lib.log.ApplicationLogger;
import cheng.lib.util.TimeToolkit;
import com.application.common.context.ApplicationServiceLocator;
import com.application.module.jdbc.SQLParameter;
import com.application.module.jdbc.itf.IDataBaseService;
import com.web.task.itf.ITaskService;
import com.web.task.model.TaskDeployModel;
import com.web.task.model.TaskParamValueModel;
import com.web.task.model.TaskPluginModel;
import com.web.task.DynamicSchedulerFactory;
import com.web.task.util.SchedulerUtil;
import com.web.task.vo.ScheduleJobVo;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 16/5/14.
 */
@Component
@Transactional
public class TaskServiceImpl implements ITaskService {

    @Resource
    IDataBaseService dataBaseService ;

    @Override
    public void initTask() throws BusinessException {
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam("Y");
        List<TaskDeployModel> tasks = dataBaseService.queryByClause(TaskDeployModel.class," runnable=? and dr= 0",sqlParameter);
        for (TaskDeployModel task:tasks){
            try {
                DynamicSchedulerFactory.addJob(createScheduleJobVo(task));
            }catch (Exception e){
                ApplicationLogger.error(e);
            }
        }
    }

    public boolean addTask(TaskDeployModel taskDeployModel) throws BusinessException {
        if(StringUtils.isBlank(taskDeployModel.getPk_taskdeploy())){
            dataBaseService.insert(taskDeployModel);
            try {
                DynamicSchedulerFactory.addJob(createScheduleJobVo(taskDeployModel));
            }catch (SchedulerException e){
                throw new BusinessException("任务添加失败");
            }
            return true ;
        }else {
            return updateTask(taskDeployModel);
        }
    }


    public boolean removeTask(TaskDeployModel taskdeploy) throws BusinessException{
        dataBaseService.deleteByPK(TaskDeployModel.class, taskdeploy.getPrimaryKey());
        try {
            DynamicSchedulerFactory.removeJob(taskdeploy.getPk_taskdeploy(), taskdeploy.getPk_taskplugin());
        }catch (SchedulerException s){
            throw new BusinessException("删除失败");
        }
        return true ;
    }
    public void disableTask(TaskDeployModel taskdeploy) throws BusinessException{
        taskdeploy.setRunnable("N");
        taskdeploy.setTs(TimeToolkit.getCurrentTs());
        dataBaseService.update(taskdeploy,new String[]{"runnable","ts"});
        try {
            DynamicSchedulerFactory.removeJob(taskdeploy.getPk_taskdeploy(), taskdeploy.getPk_taskplugin());
        }catch (SchedulerException s){
            throw new BusinessException("禁用失败");
        }
    }

    @Override
    public void enableTask(TaskDeployModel taskDeployModel) {
        taskDeployModel.setRunnable("N");
        taskDeployModel.setTs(TimeToolkit.getCurrentTs());
        dataBaseService.update(taskDeployModel,new String[]{"runnable","ts"});
        try {
            DynamicSchedulerFactory.addJob(createScheduleJobVo(taskDeployModel));
        }catch (SchedulerException e){
            throw new BusinessException("任务添加失败");
        }
    }

    public boolean updateTask(TaskDeployModel taskDeployModel) throws BusinessException{
        dataBaseService.update(taskDeployModel);
        if("N".equals(taskDeployModel.getRunnable())){
            disableTask(taskDeployModel);
            return false ;
        }
        try {
            DynamicSchedulerFactory.rescheduleJob(createScheduleJobVo(taskDeployModel));
        }catch (SchedulerException e){
            throw new BusinessException("任务更新失败");
        }
        return true ;
    }
    public boolean runOnceTask(TaskDeployModel taskDeployModel){
        try {
            DynamicSchedulerFactory.triggerJob(taskDeployModel.getPk_taskdeploy(), taskDeployModel.getPk_taskplugin());
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("runOnceTask task is error ", e);
            return false ;
        }
    }


    public boolean pause(TaskDeployModel taskDeployModel) {
        try {
            DynamicSchedulerFactory.pauseJob(taskDeployModel.getPk_taskdeploy(), taskDeployModel.getPk_taskplugin());	// jobStatus do not store
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("pause task is error ",e);
            return false;
        }
    }

    public boolean resume(TaskDeployModel taskDeployModel) {
        try {
            DynamicSchedulerFactory.resumeJob(taskDeployModel.getPk_taskdeploy(), taskDeployModel.getPk_taskplugin());
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("resume task is error ", e);
            return false;
        }
    }


    private ScheduleJobVo createScheduleJobVo(TaskDeployModel taskDeployModel) {
        ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
        scheduleJobVo.setJobCode(taskDeployModel.getPk_taskdeploy());
        scheduleJobVo.setJobGroupCode(taskDeployModel.getPk_taskplugin());
        scheduleJobVo.setCronExpression(taskDeployModel.getTriggerstr());
        TaskPluginModel t = dataBaseService.queryByPK(TaskPluginModel.class,taskDeployModel.getPk_taskplugin(),new String[]{"pluginclass"});
        scheduleJobVo.setJobClass(t.getPluginclass());
        try {
            SchedulerUtil.parse(scheduleJobVo);
            scheduleJobVo.getJobDetail().getJobDataMap().putAll(getParams(taskDeployModel));
        } catch (Exception e) {
           throw new  BusinessException(e);
        }
        return scheduleJobVo ;
    }

    private Map<String,Object> getParams (TaskDeployModel taskDeployModel) throws Exception{
        Map<String,Object> taskunitmap = new HashMap<>();
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(taskDeployModel.getPrimaryKey());
        List<TaskParamValueModel> taskParamValueModels = ApplicationServiceLocator.getBean(IDataBaseService.class).queryByClause(TaskParamValueModel.class, " dr=0 and pk_taskdeploy=?", sqlParameter);
        for (int j=0;j<taskParamValueModels.size();j++){
            TaskParamValueModel t = taskParamValueModels.get(j);
            taskunitmap.put(t.getParamkey(),t.getParamvalue());
        }
        taskunitmap.put(taskDeployModel.getPKFieldName(), taskDeployModel.getPrimaryKey());
        return taskunitmap ;
    }
}
