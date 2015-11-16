package com.web.task.itf;

import com.web.task.vo.ScheduleJobVo;
import com.web.task.vo.TaskUnitVO;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;


public interface ITaskCentry {

	public  boolean startTaskCentery() throws SchedulerException;
	public  boolean stopTaskCentery() throws SchedulerException;
	public  boolean initTasks() throws SchedulerException;
	public  boolean  clean();


	public  boolean initTask(ScheduleJobVo scheduleJobVo) throws SchedulerException;
	public  boolean updateJob(ScheduleJobVo scheduleJobVo) throws SchedulerException;
	public  boolean runOnce(ScheduleJobVo scheduleJobVo) throws SchedulerException;
	public  boolean stop(ScheduleJobVo scheduleJobVo) throws SchedulerException;
	public  boolean stop(JobKey jobKey) throws SchedulerException;
	public  boolean start(ScheduleJobVo scheduleJobVo) throws SchedulerException;
	public  boolean pauseJob(ScheduleJobVo scheduleJobVo) throws SchedulerException ;
	public  boolean resumeJob(ScheduleJobVo scheduleJobVo)  throws SchedulerException;
}
