package com.web.task;

import arch.util.lang.TimeToolkit;
import arch.util.toolkit.DBRunner;
import arch.util.toolkit.db.Transaction;
import com.application.common.context.ApplicationServiceLocator;
import com.application.common.exception.BusinessException;
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
		int id_taskdeploy = tt.getJobDetail().getJobDataMap().getInt("id_taskdeploy");
		TaskLogModel taskLogModel = new TaskLogModel();
		taskLogModel.setId_taskdeploy(id_taskdeploy);
		taskLogModel.setVdef1(TimeToolkit.getCurrentTs());

		taskLogModel.setRunserver(SystemInfoUtil.getInstance().getOs_name()+":"+SystemInfoUtil.getInstance().getOs_mac()+":"+SystemInfoUtil.getInstance().getOs_ip());
		logger.info("task run begin time ："+ taskLogModel.getVdef1());
		long b = System.currentTimeMillis();
		DBRunner dbRunner = ApplicationServiceLocator.getBDRunner();
		try{

			Object object = dbRunner.transaction(new Transaction<Object>() {
				@Override
				public Object apply(DBRunner db) throws Exception {
					return doexecute(db, tt.getJobDetail().getJobDataMap());
				}

			});
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
				dbRunner.insertModel(taskLogModel);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new JobExecutionException(e);
			}
		}
	}
	public abstract String doexecute(DBRunner dbRunner,JobDataMap jobDataMap) throws BusinessException ;
	
}
