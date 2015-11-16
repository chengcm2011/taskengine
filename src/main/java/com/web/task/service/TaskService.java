package com.web.task.service;

import arch.util.toolkit.DBRunner;
import com.application.common.context.ApplicationServiceLocator;
import com.application.common.exception.BusinessException;
import com.web.task.DefaultTaskFactory;
import com.web.task.TaskJobCacheManager;
import com.web.task.itf.ITaskService;
import com.web.task.model.TaskDeployModel;
import com.web.task.model.TaskParamKeyModel;
import com.web.task.model.TaskParamValueModel;
import com.web.task.util.ScheduleUtils;
import com.web.task.vo.ScheduleJobVo;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TaskService implements ITaskService {

	@Resource
	TaskReadImpl taskRead ;
	@Resource
	DBRunner dbRunner ;

	/**
	 * 保存任务
	 * @param deploymodel
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public boolean createScheduleJob(TaskDeployModel deploymodel) throws BusinessException {

		try {
			if(StringUtils.isBlank(deploymodel.getRunnable())){
				deploymodel.setRunnable("N");
			}
			//得到所有的参数
			List<TaskParamKeyModel> data = dbRunner.queryBeans2(TaskParamKeyModel.class, " dr=0 and id_taskplugin=?", deploymodel.getId_taskplugin());
			if(data==null || data.size()<=0){
				throw  new BusinessException("保存失败，没有设置参数");
			}
			dbRunner.insertModel(deploymodel);
			for (TaskParamKeyModel item:data){
				TaskParamValueModel taskParamValueModel = new TaskParamValueModel();
				taskParamValueModel.setId_taskdeploy(deploymodel.getId_taskdeploy());
				taskParamValueModel.setId_taskparamkey(item.getId_taskparamkey());
				taskParamValueModel.setParamkey(item.getParamkey());
				taskParamValueModel.setParamname(item.getParamname());
				dbRunner.insertModel(taskParamValueModel);
			}
			//查询具体的任务插件
			ScheduleJobVo scheduleJobVo = taskRead.read(deploymodel.getId_taskdeploy()+"",deploymodel.getId_taskplugin()+"");

			TaskJobCacheManager.getInstance().set(deploymodel.getId_taskdeploy()+deploymodel.getId_taskplugin()+"",scheduleJobVo);

			DefaultTaskFactory.getFactory().initTask(scheduleJobVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;
	}

	@Override
	public boolean startScheduleJob(TaskDeployModel deploymodel) throws BusinessException {
		try {
			ScheduleJobVo scheduleJobVo = taskRead.read(deploymodel.getId_taskdeploy()+"",deploymodel.getId_taskplugin()+"");

			TaskJobCacheManager.getInstance().set(deploymodel.getId_taskdeploy()+deploymodel.getId_taskplugin()+"",scheduleJobVo);

			DefaultTaskFactory.getFactory().initTask(scheduleJobVo);
		}catch (Exception e){
			throw new BusinessException(e);
		}
		return true;
	}

	/**
	 * 更新任务
	 * @param deploymodel
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public boolean updateScheduleJob(TaskDeployModel deploymodel) throws BusinessException {
		try {
			ApplicationServiceLocator.getBDRunner().update(deploymodel);
			ScheduleJobVo scheduleJobVo = taskRead.read(deploymodel.getId_taskdeploy()+"",deploymodel.getId_taskplugin()+"");
			String key = deploymodel.getId_taskdeploy() + deploymodel.getId_taskplugin() + "";
			TaskJobCacheManager.getInstance().set(key,scheduleJobVo);
			DefaultTaskFactory.getFactory().updateJob(scheduleJobVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;
	}

	@Override
	public boolean deleteScheduleJob(TaskDeployModel deploymodel) throws BusinessException {
		//把任务从任务中心删除
		try {
			DefaultTaskFactory.getFactory().stop(ScheduleUtils.getJobKey(deploymodel.getId_taskdeploy() + "", deploymodel.getId_taskplugin() + ""));
		}catch (Exception e){
			throw new BusinessException(e);
		}
		return false;
	}



	@Override
	public boolean pauseJob(TaskDeployModel deploymodel) throws BusinessException {
		try {
			ScheduleJobVo scheduleJobVo = taskRead.read(deploymodel.getId_taskdeploy()+"",deploymodel.getId_taskplugin()+"");
			DefaultTaskFactory.getFactory().pauseJob(scheduleJobVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;
	}

	@Override
	public boolean resumeJob(TaskDeployModel deploymodel) throws BusinessException {
		try {
			ScheduleJobVo scheduleJobVo = taskRead.read(deploymodel.getId_taskdeploy()+"",deploymodel.getId_taskplugin()+"");
			DefaultTaskFactory.getFactory().resumeJob(scheduleJobVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;
	}


}
