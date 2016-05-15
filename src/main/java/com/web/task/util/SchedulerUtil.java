package com.web.task.util;

import cheng.lib.log.ApplicationLogger;
import cheng.lib.util.ClassUtil;
import com.web.task.vo.ScheduleJobVo;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.*;

/**
 * base quartz scheduler util
 * @author xuxueli 2015-12-19 16:13:53
 */
public final class SchedulerUtil {

	// addJob 新增
    @SuppressWarnings("unchecked")
	public static ScheduleJobVo parse(ScheduleJobVo scheduleJobVo)  {
		setJobKey(scheduleJobVo);
		setTriggerKey(scheduleJobVo);
         //CronTrigger : TriggerKey + cronExpression	// withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobVo.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(scheduleJobVo.getTriggerKey()).withSchedule(cronScheduleBuilder).build();

        // JobDetail : jobClass
		Class<? extends Job> jobClass_ = null;
		jobClass_ = ClassUtil.initClass1(scheduleJobVo.getJobClass());
		JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(scheduleJobVo.getJobKey()).build();
        scheduleJobVo.setJobDetail(jobDetail);
		scheduleJobVo.setTrigger(cronTrigger);
		return scheduleJobVo ;
    }
    

    // unscheduleJob
    public static ScheduleJobVo setTriggerKey(ScheduleJobVo scheduleJobVo)  {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJobVo.getJobCode(), scheduleJobVo.getJobGroupCode());
		scheduleJobVo.setTriggerKey(triggerKey);
        return scheduleJobVo;
    }



    

    // run
    public static ScheduleJobVo setJobKey(ScheduleJobVo scheduleJobVo)  {
    	// TriggerKey : name + group
    	JobKey jobKey = new JobKey(scheduleJobVo.getJobCode(), scheduleJobVo.getJobGroupCode());
		scheduleJobVo.setJobKey(jobKey);
        return scheduleJobVo;
    }

}