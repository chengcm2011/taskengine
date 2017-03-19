package com.application.taskengine.itf;

import com.application.taskengine.model.TaskDeployModel;
import com.cheng.lang.IService;
import com.cheng.lang.PageVO;
import com.cheng.lang.exception.BusinessException;

/**
 *
 */
public interface ITaskService extends IService {

    public void initTask() throws BusinessException;

    public boolean addTask(TaskDeployModel taskDeployModel) throws BusinessException;

    public boolean removeTask(TaskDeployModel pkTaskdeploy) throws BusinessException;

    public boolean updateTask(TaskDeployModel taskDeployModel) throws BusinessException;

    public boolean runOnceTask(TaskDeployModel taskDeployModel);


    public boolean resume(TaskDeployModel taskDeployModel);

    public boolean pause(TaskDeployModel taskDeployModel);

    public void disableTask(TaskDeployModel taskDeployModel);

    public void enableTask(TaskDeployModel taskDeployModel);

    public PageVO getTaskLog(String pkTaskdeploy, PageVO pageVO);
}
