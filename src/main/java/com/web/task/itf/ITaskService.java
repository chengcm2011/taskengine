package com.web.task.itf;

import cheng.lib.exception.BusinessException;
import cheng.lib.itf.IService;
import com.web.task.model.TaskDeployModel;

public interface ITaskService extends IService {
	public boolean pauseJob(TaskDeployModel deploymodel) throws BusinessException;
	public boolean resumeJob(TaskDeployModel deploymodel) throws BusinessException;


	public boolean createScheduleJob(TaskDeployModel deploymodel) throws BusinessException;
	public boolean startScheduleJob(TaskDeployModel deploymodel) throws BusinessException;
	public boolean updateScheduleJob(TaskDeployModel deploymodel) throws BusinessException;
	public boolean deleteScheduleJob(TaskDeployModel deploymodel) throws BusinessException;
}
