package com.application.taskengine.service;

import cheng.lib.exception.BusinessException;
import cheng.lib.log.ApplicationLogger;
import cheng.lib.util.TimeToolkit;
import com.application.common.context.ApplicationServiceLocator;
import com.application.module.jdbc.SQLParameter;
import com.application.module.jdbc.itf.IDataBaseService;
import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskParamValueModel;
import com.application.taskengine.model.TaskPluginModel;
import com.application.taskengine.util.DynamicSchedulerFactory;
import com.application.taskengine.util.SchedulerUtil;
import com.application.taskengine.vo.ScheduleTaskVo;
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
                DynamicSchedulerFactory.addTask(createScheduleJobVo(task));
            }catch (Exception e){
                ApplicationLogger.error(e);
            }
        }
    }

    public boolean addTask(TaskDeployModel taskDeployModel) throws BusinessException {
        if(StringUtils.isBlank(taskDeployModel.getPk_taskdeploy())){
            dataBaseService.insert(taskDeployModel);
            try {
                DynamicSchedulerFactory.addTask(createScheduleJobVo(taskDeployModel));
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
            DynamicSchedulerFactory.removeTask(taskdeploy.getPk_taskdeploy(), taskdeploy.getPk_taskplugin());
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
            DynamicSchedulerFactory.removeTask(taskdeploy.getPk_taskdeploy(), taskdeploy.getPk_taskplugin());
        }catch (SchedulerException s){
            throw new BusinessException("禁用失败");
        }
    }

    @Override
    public void enableTask(TaskDeployModel taskDeployModel) {
        taskDeployModel.setRunnable("Y");
        taskDeployModel.setTs(TimeToolkit.getCurrentTs());
        dataBaseService.update(taskDeployModel,new String[]{"runnable","ts"});
        try {
            DynamicSchedulerFactory.addTask(createScheduleJobVo(taskDeployModel));
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
            DynamicSchedulerFactory.rescheduleTask(createScheduleJobVo(taskDeployModel));
        }catch (SchedulerException e){
            throw new BusinessException("任务更新失败");
        }
        return true ;
    }
    public boolean runOnceTask(TaskDeployModel taskDeployModel){
        try {
            DynamicSchedulerFactory.runOnceTask(taskDeployModel.getPk_taskdeploy(), taskDeployModel.getPk_taskplugin());
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


    private ScheduleTaskVo createScheduleJobVo(TaskDeployModel taskDeployModel) {
        ScheduleTaskVo scheduleTaskVo = new ScheduleTaskVo();
        scheduleTaskVo.setJobCode(taskDeployModel.getPk_taskdeploy());
        scheduleTaskVo.setJobGroupCode(taskDeployModel.getPk_taskplugin());
        scheduleTaskVo.setCronExpression(taskDeployModel.getTriggerstr());
        TaskPluginModel t = dataBaseService.queryByPK(TaskPluginModel.class,taskDeployModel.getPk_taskplugin(),new String[]{"pluginclass"});
        scheduleTaskVo.setJobClass(t.getPluginclass());
        try {
            SchedulerUtil.parse(scheduleTaskVo);
            scheduleTaskVo.getJobDetail().getJobDataMap().putAll(getParams(taskDeployModel));
        } catch (Exception e) {
           throw new  BusinessException(e);
        }
        return scheduleTaskVo;
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
