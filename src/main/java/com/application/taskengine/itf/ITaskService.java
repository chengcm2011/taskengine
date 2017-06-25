package com.application.taskengine.itf;

import com.application.taskengine.model.TaskDeployModel;
import com.cheng.lang.IService;
import com.cheng.lang.PageVO;
import com.cheng.lang.exception.BusinessException;

/**
 *
 */
public interface ITaskService extends IService {

    boolean saveTask(TaskDeployModel taskDeployModel) throws BusinessException;

    boolean removeTask(TaskDeployModel pkTaskdeploy) throws BusinessException;

    boolean runOnceTask(TaskDeployModel taskDeployModel);

    boolean resume(TaskDeployModel taskDeployModel);

    boolean pause(TaskDeployModel taskDeployModel);

    void disableTask(TaskDeployModel taskDeployModel);

    void enableTask(TaskDeployModel taskDeployModel);

    PageVO getTaskLog(String pkTaskdeploy, PageVO pageVO);


}
