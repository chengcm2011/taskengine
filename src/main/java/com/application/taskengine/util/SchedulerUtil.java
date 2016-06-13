package com.application.taskengine.util;

import cheng.lib.util.ClassUtil;
import com.application.taskengine.AbstractTaskImpl;
import com.application.taskengine.vo.ScheduleJobVo;
import org.quartz.*;


public final class SchedulerUtil {

    /**
     *
     * @param scheduleJobVo
     * @return
     */
	public static ScheduleJobVo parse(ScheduleJobVo scheduleJobVo)  {
		setJobKey(scheduleJobVo);
		setTriggerKey(scheduleJobVo);
       CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobVo.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(scheduleJobVo.getTriggerKey()).withSchedule(cronScheduleBuilder).build();

		Class<? extends AbstractTaskImpl> jobClass_ = null;
		jobClass_ = ClassUtil.initClass1(scheduleJobVo.getJobClass());
		JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(scheduleJobVo.getJobKey()).build();
        scheduleJobVo.setJobDetail(jobDetail);
		scheduleJobVo.setTrigger(cronTrigger);
		return scheduleJobVo ;
    }


    /**
     *
     * @param scheduleJobVo
     * @return
     */
    public static ScheduleJobVo setTriggerKey(ScheduleJobVo scheduleJobVo)  {

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJobVo.getJobCode(), scheduleJobVo.getJobGroupCode());
		scheduleJobVo.setTriggerKey(triggerKey);
        return scheduleJobVo;
    }


    /**
     *
     * @param scheduleJobVo
     * @return
     */
    public static ScheduleJobVo setJobKey(ScheduleJobVo scheduleJobVo)  {
    	JobKey jobKey = new JobKey(scheduleJobVo.getJobCode(), scheduleJobVo.getJobGroupCode());
		scheduleJobVo.setJobKey(jobKey);
        return scheduleJobVo;
    }

}