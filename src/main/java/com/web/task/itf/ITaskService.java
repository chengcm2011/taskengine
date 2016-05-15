package com.web.task.itf;

import cheng.lib.exception.BusinessException;
import cheng.lib.itf.IService;
import com.web.task.model.TaskDeployModel;

/**
 * Created by cheng on 16/5/14.
 */
public interface ITaskService extends IService{

    public void initTask() throws BusinessException;

    public boolean addTask(TaskDeployModel taskDeployModel) throws BusinessException;

    public boolean removeTask(TaskDeployModel pk_taskdeploy) throws BusinessException;

    public boolean updateTask(TaskDeployModel taskDeployModel) throws BusinessException;

    public boolean runOnceTask(TaskDeployModel taskDeployModel);



    public boolean resume(TaskDeployModel taskDeployModel);

    public boolean pause(TaskDeployModel taskDeployModel);

    public void disableTask(TaskDeployModel taskDeployModel);

    public void enableTask(TaskDeployModel taskDeployModel);
}
