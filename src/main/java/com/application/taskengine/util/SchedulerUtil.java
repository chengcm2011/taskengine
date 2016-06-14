package com.application.taskengine.util;

import cheng.lib.util.ClassUtil;
import com.application.taskengine.AbstractTaskImpl;
import com.application.taskengine.vo.ScheduleTaskVo;
import org.quartz.*;


public final class SchedulerUtil {

    /**
     *
     * @param scheduleTaskVo
     * @return
     */
	public static ScheduleTaskVo parse(ScheduleTaskVo scheduleTaskVo)  {
		setJobKey(scheduleTaskVo);
		setTriggerKey(scheduleTaskVo);
       CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleTaskVo.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(scheduleTaskVo.getTriggerKey()).withSchedule(cronScheduleBuilder).build();

		Class<? extends AbstractTaskImpl> jobClass_ = null;
		jobClass_ = ClassUtil.initClass1(scheduleTaskVo.getJobClass());
		JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(scheduleTaskVo.getJobKey()).build();
        scheduleTaskVo.setJobDetail(jobDetail);
		scheduleTaskVo.setTrigger(cronTrigger);
		return scheduleTaskVo;
    }


    /**
     *
     * @param scheduleTaskVo
     * @return
     */
    public static ScheduleTaskVo setTriggerKey(ScheduleTaskVo scheduleTaskVo)  {

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleTaskVo.getJobCode(), scheduleTaskVo.getJobGroupCode());
		scheduleTaskVo.setTriggerKey(triggerKey);
        return scheduleTaskVo;
    }


    /**
     *
     * @param scheduleTaskVo
     * @return
     */
    public static ScheduleTaskVo setJobKey(ScheduleTaskVo scheduleTaskVo)  {
    	JobKey jobKey = new JobKey(scheduleTaskVo.getJobCode(), scheduleTaskVo.getJobGroupCode());
		scheduleTaskVo.setJobKey(jobKey);
        return scheduleTaskVo;
    }

}