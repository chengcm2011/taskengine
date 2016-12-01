package com.application.taskengine.service;

import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskParamValueModel;
import com.application.taskengine.model.TaskPluginModel;
import com.application.taskengine.util.DynamicSchedulerFactory;
import com.application.taskengine.util.SchedulerUtil;
import com.application.taskengine.vo.ScheduleTaskVo;
import com.cheng.jdbc.SQLParameter;
import com.cheng.jdbc.itf.IDataBaseService;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.ApplicationLogger;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
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
        if (StringUtils.isBlank(taskDeployModel.getPkTaskdeploy())) {
            dataBaseService.insert(taskDeployModel);
            try {
                DynamicSchedulerFactory.addTask(createScheduleJobVo(taskDeployModel));
            }catch (SchedulerException e){
                ApplicationLogger.error(e);
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
            DynamicSchedulerFactory.removeTask(taskdeploy.getPkTaskdeploy(), taskdeploy.getPkTaskplugin());
        }catch (SchedulerException s){
            ApplicationLogger.error(s);
            throw new BusinessException("删除失败");
        }
        return true ;
    }
    public void disableTask(TaskDeployModel taskdeploy) throws BusinessException{
        taskdeploy.setRunnable("N");
        taskdeploy.setTs(TimeToolkit.getCurrentTs());
        dataBaseService.update(taskdeploy,new String[]{"runnable","ts"});
        try {
            DynamicSchedulerFactory.removeTask(taskdeploy.getPkTaskdeploy(), taskdeploy.getPkTaskplugin());
        }catch (SchedulerException s){
            ApplicationLogger.error(s);
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
            ApplicationLogger.error(e);
            throw new BusinessException("任务添加失败");
        }
    }

    public boolean updateTask(TaskDeployModel taskDeployModel) throws BusinessException{
        TaskDeployModel dbtaskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, taskDeployModel.getPkTaskdeploy());
        if ("N".equals(dbtaskDeployModel.getRunnable())) {
            if ("Y".equals(taskDeployModel.getRunnable())) {
                addTask(taskDeployModel);
            } else {

            }
            dataBaseService.update(taskDeployModel);
            return true;
        } else {
            if ("Y".equals(taskDeployModel.getRunnable())) {
                dataBaseService.update(taskDeployModel);
            }
        }

        if("N".equals(taskDeployModel.getRunnable())){
            disableTask(taskDeployModel);
            return false ;
        }
        try {
            DynamicSchedulerFactory.rescheduleTask(createScheduleJobVo(taskDeployModel));
        }catch (SchedulerException e){
            ApplicationLogger.error(e);
            throw new BusinessException("任务更新失败");
        }
        return true ;
    }
    public boolean runOnceTask(TaskDeployModel taskDeployModel){
        try {
            DynamicSchedulerFactory.runOnceTask(taskDeployModel.getPkTaskdeploy(), taskDeployModel.getPkTaskplugin());
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("runOnceTask task is error ", e);
            return false ;
        }
    }


    public boolean pause(TaskDeployModel taskDeployModel) {
        try {
            DynamicSchedulerFactory.pauseJob(taskDeployModel.getPkTaskdeploy(), taskDeployModel.getPkTaskplugin());    // jobStatus do not store
            return true;
        } catch (SchedulerException e) {
            ApplicationLogger.error("pause task is error ",e);
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
        TaskPluginModel t = dataBaseService.queryByPK(TaskPluginModel.class, taskDeployModel.getPkTaskplugin(), new String[]{"pluginclass"});
        scheduleTaskVo.setJobClass(t.getPluginclass());
        try {
            SchedulerUtil.parse(scheduleTaskVo);
            scheduleTaskVo.getJobDetail().getJobDataMap().putAll(getParams(taskDeployModel));
        } catch (Exception e) {
            ApplicationLogger.error(e);
           throw new  BusinessException(e);
        }
        return scheduleTaskVo;
    }

    private Map<String,Object> getParams (TaskDeployModel taskDeployModel) throws Exception{
        Map<String,Object> taskunitmap = new HashMap<>();
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(taskDeployModel.getPrimaryKey());
        List<TaskParamValueModel> taskParamValueModels = dataBaseService.queryByClause(TaskParamValueModel.class, " dr=0 and pkTaskdeploy=?", sqlParameter);
        for (int j=0;j<taskParamValueModels.size();j++){
            TaskParamValueModel t = taskParamValueModels.get(j);
            taskunitmap.put(t.getParamkey(),t.getParamvalue());
        }
        taskunitmap.put(taskDeployModel.getPKFieldName(), taskDeployModel.getPrimaryKey());
        return taskunitmap ;
    }
}
