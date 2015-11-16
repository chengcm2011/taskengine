package com.web.task.util;

import arch.util.lang.ClassUtil;
import com.web.task.exception.ScheduleException;
import com.web.task.vo.ScheduleJobVo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by cheng on 2015/10/17.
 */
public class ScheduleUtils {
	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);

	/**
	 * 获取触发器key
	 *
	 * @param jobCode
	 * @param jobGroupCode
	 * @return
	 */
	public static TriggerKey getTriggerKey(String jobCode, String jobGroupCode) {

		return TriggerKey.triggerKey(jobCode, jobGroupCode);
	}
	/**
	 * 获取jobKey
	 *
	 * @param jobCode the job name
	 * @param jobGroupCode the job group
	 * @return the job key
	 */
	public static JobKey getJobKey(String jobCode, String jobGroupCode) {
		return JobKey.jobKey(jobCode, jobGroupCode);
	}

	/**
	 * 获取表达式触发器
	 *
	 * @param scheduler the scheduler
	 * @param jobName the job name
	 * @param jobGroup the job group
	 * @return cron trigger
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) {

		try {
			TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);
			return (CronTrigger) scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			LOG.error("获取定时任务CronTrigger出现异常", e);
			throw new ScheduleException("获取定时任务CronTrigger出现异常");
		}
	}
	public static void addTaskToScheduler(Scheduler scheduler, ScheduleJobVo scheduleJobVo) {
		try {
			scheduler.scheduleJob(scheduleJobVo.getJobDetail(), scheduleJobVo.getTrigger());
		} catch (SchedulerException e) {
			LOG.error("创建定时任务失败", e);
			throw new ScheduleException("创建定时任务失败");
		}
	}
	/**
	 * 创建定时任务
	 *
	 * @param jobName the jobName
	 * @param jobCode the job name
	 * @param jobGroupCode the job group
	 * @param cronExpression the cron expression
	 * @param jobClass the is jobClass
	 * @param param the param
	 */
	public static ScheduleJobVo createScheduleJob(String jobName, String jobCode, String jobGroupCode,
										 String cronExpression,String jobClass,String runable, Map<String,Object> param) {
		//同步或异步

		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(ClassUtil.initClass1(jobClass)).withIdentity(jobCode, jobGroupCode).build();

		//放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().putAll(param);
		//表达式调度构建器
		//按新的cronExpression表达式构建一个新的trigger
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobCode, jobGroupCode).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
		ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
		scheduleJobVo.setJobName(jobName);
		scheduleJobVo.setJobKey(getJobKey(jobCode, jobGroupCode));
		scheduleJobVo.setTriggerKey(getTriggerKey(jobCode, jobGroupCode));
		scheduleJobVo.setJobCode(jobCode);
		scheduleJobVo.setJobGroupCode(jobGroupCode);
		scheduleJobVo.setJobDetail(jobDetail);
		scheduleJobVo.setTrigger(cronTrigger);
		if("Y".equals(runable)){
			scheduleJobVo.setJobstatus(true);
		}else {
			scheduleJobVo.setJobstatus(false);
		}
		return scheduleJobVo ;
	}

	/**
	 * 运行一次任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("运行一次定时任务失败", e);
			throw new ScheduleException("运行一次定时任务失败");
		}
	}

	/**
	 * 暂停任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) {

		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		pauseJob(scheduler,jobKey);
	}
	public static void pauseJob(Scheduler scheduler,JobKey jobKey ) {
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("暂停定时任务失败", e);
			throw new ScheduleException("暂停定时任务失败");
		}
	}
	/**
	 * 恢复任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) {

		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		resumeJob(scheduler,jobKey);
	}
	public static void resumeJob(Scheduler scheduler, JobKey jobKey) {
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("暂停定时任务失败", e);
			throw new ScheduleException("暂停定时任务失败");
		}
	}

	/**
	 * 更新定时任务
	 *
	 * @param scheduler the scheduler
	 * @param scheduleJobVo the scheduleJobVo
	 */
	public static void updateScheduleJob(Scheduler scheduler, ScheduleJobVo scheduleJobVo) {
		try {

			TriggerKey triggerKey = scheduleJobVo.getTriggerKey();
			CronTrigger trigger = (CronTrigger) scheduleJobVo.getTrigger();
			//按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			LOG.error("更新定时任务失败", e);
			throw new ScheduleException("更新定时任务失败");
		}
	}

	/**
	 * 删除定时任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) {
		try {
			scheduler.deleteJob(getJobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			LOG.error("删除定时任务失败", e);
			throw new ScheduleException("删除定时任务失败");
		}
	}

}
