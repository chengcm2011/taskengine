package com.web.task;

import cheng.lib.exception.BusinessException;
import cheng.lib.util.TimeToolkit;
import com.application.common.context.ApplicationServiceLocator;
import com.application.module.jdbc.itf.IDataBaseService;
import com.web.task.model.TaskLogModel;
import com.web.task.util.SystemInfoUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public abstract class AbstractTaskImpl implements Job {

	protected Logger logger = LoggerFactory.getLogger(AbstractTaskImpl.class);
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		final JobExecutionContext tt = arg0 ;
		String pk_taskdeploy = tt.getJobDetail().getJobDataMap().getString("pk_taskdeploy");
		TaskLogModel taskLogModel = new TaskLogModel();
		taskLogModel.setPk_taskdeploy(pk_taskdeploy);
		taskLogModel.setVdef1(TimeToolkit.getCurrentTs());

		taskLogModel.setRunserver(SystemInfoUtil.getInstance().getOs_name()+":"+SystemInfoUtil.getInstance().getOs_mac()+":"+SystemInfoUtil.getInstance().getOs_ip());
		logger.info("task run begin time ："+ taskLogModel.getVdef1());
		long b = System.currentTimeMillis();
		try{

			Object object = doexecute(tt.getJobDetail().getJobDataMap());

			taskLogModel.setIssuccess(true);
			taskLogModel.setReturnstr(object.toString());
			logger.info("task run result ："+taskLogModel.getReturnstr());
		}catch(Exception e){
			taskLogModel.setIssuccess(false);
			throw new JobExecutionException(e);
		}
		finally{
			taskLogModel.setRuntime(System.currentTimeMillis()-b+"ms");
			taskLogModel.setVdef2(TimeToolkit.getCurrentTs());
			logger.info("task run end time ："+taskLogModel.getVdef2());
			try {
				ApplicationServiceLocator.getService(IDataBaseService.class).insert(taskLogModel);
			} catch (Exception e) {
				e.printStackTrace();
				throw new JobExecutionException(e);
			}
		}
	}
	public abstract String doexecute(JobDataMap jobDataMap) throws BusinessException;
	
}
