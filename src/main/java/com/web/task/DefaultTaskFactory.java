package com.web.task;


import com.application.common.context.ApplicationServiceLocator;
import com.web.task.itf.ITaskRead;
import com.web.task.service.TaskReadImpl;
import com.web.task.util.ScheduleUtils;
import com.web.task.vo.ScheduleJobVo;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultTaskFactory extends AbstractTaskFactory {
	protected Logger logger = LoggerFactory.getLogger(DefaultTaskFactory.class);
	
	private static Scheduler scheduler = null ;

	public DefaultTaskFactory() {

	}
	public static Scheduler getScheduler(){
		try {
			if(scheduler==null){
				SchedulerFactory schedulerFactory = new StdSchedulerFactory();
				scheduler = schedulerFactory.getScheduler();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return scheduler;
	}

	private void initScheduler() {
		List<ScheduleJobVo> scheduleJobVos = ApplicationServiceLocator.getBean(ITaskRead.class).read();
		for(ScheduleJobVo scheduleJobVo: scheduleJobVos){
			ScheduleUtils.addTaskToScheduler(getScheduler(),scheduleJobVo);
		}
	}

	@Override
	public boolean startTaskCentery() throws SchedulerException {
		if(scheduler==null){
			scheduler = getScheduler();
		}
		scheduler.start();
		return false;
	}

	@Override
	public boolean stopTaskCentery() throws SchedulerException {
		if(scheduler==null){
			scheduler = getScheduler();
		}
		scheduler.shutdown();
		return false;
	}

	public boolean initTasks() throws SchedulerException{
		initScheduler();
		return true;
	}

	@Override
	public boolean initTask(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		if(scheduleJobVo==null){
			return false ;
		}
		ScheduleUtils.addTaskToScheduler(getScheduler(),scheduleJobVo);
		return true;
	}

	@Override
	public boolean updateJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		if(!scheduleJobVo.isJobstatus()){
		    getScheduler().deleteJob(scheduleJobVo.getJobKey());
		}else {
			ScheduleUtils.updateScheduleJob(getScheduler(), scheduleJobVo);
		}
		return false;
	}

	@Override
	public boolean runOnce(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		ScheduleUtils.runOnce(getScheduler(), scheduleJobVo.getJobCode(), scheduleJobVo.getJobGroupCode());
		return true;
	}

	@Override
	public boolean stop(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		getScheduler().deleteJob(scheduleJobVo.getJobKey());
		return false;
	}
	@Override
	public boolean stop(JobKey jobKey) throws SchedulerException {
	    getScheduler().deleteJob(jobKey);
		return true;
	}
	@Override
	public boolean start(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		ScheduleUtils.addTaskToScheduler(getScheduler(),scheduleJobVo);
		return false;
	}

	@Override
	public boolean pauseJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		ScheduleUtils.pauseJob(getScheduler(),scheduleJobVo.getJobKey());
		return true;
	}

	@Override
	public boolean resumeJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {
		ScheduleUtils.resumeJob(getScheduler(),scheduleJobVo.getJobKey());
		return true;
	}

	public boolean clean() {
		try {
			scheduler.shutdown();
			scheduler= null ;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}
}
