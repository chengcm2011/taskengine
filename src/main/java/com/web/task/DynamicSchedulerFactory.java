package com.web.task;

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
public final class DynamicSchedulerFactory implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulerFactory.class);
    
    // Scheduler
    private static Scheduler scheduler;

	@Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(scheduler, "quartz scheduler is null");
        logger.info(">>>>>>>>> init quartz scheduler success.[{}]", scheduler);
    }
	
	// getJobKeys
	public static List<Map<String, Object>> getJobList(){
		List<Map<String, Object>> jobList = new ArrayList<Map<String,Object>>();
		
		try {
			if (scheduler.getJobGroupNames()==null || scheduler.getJobGroupNames().size()==0) {
				return null;
			}
			String groupName = scheduler.getJobGroupNames().get(0);
			Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
			if (jobKeys!=null && jobKeys.size()>0) {
				for (JobKey jobKey : jobKeys) {
			        TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
			        Trigger trigger = scheduler.getTrigger(triggerKey);
			        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			        TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			        Map<String, Object> jobMap = new HashMap<>();
					String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
					jobMap.put("NextFireTime",trigger.getNextFireTime());
					jobMap.put("jobclass",jobDetail.getJobClass());
					jobMap.put("jobcode",triggerKey.getName());
					jobMap.put("jobname",triggerKey.getName());
					jobMap.put("jobgroupcode",triggerKey.getGroup());
			        jobMap.put("TriggerStateName", triggerState.name());
					jobMap.put("cronExpression",cronExpression);
			        jobList.add(jobMap);
				}
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
		return jobList;
	}
	
	// fill job info
	public static void fillJobInfo(ScheduleJobVo scheduleJobVo) {
		// TriggerKey : name + group
        TriggerKey triggerKey = scheduleJobVo.getTriggerKey();
        JobKey jobKey = scheduleJobVo.getJobKey();
        try {
			Trigger trigger = scheduler.getTrigger(triggerKey);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			
			// parse params
			if (trigger!=null && trigger instanceof CronTriggerImpl) {
				String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
				scheduleJobVo.setCronExpression(cronExpression);
			}
			if (jobDetail!=null) {
				String jobClass = jobDetail.getJobClass().getName();
				scheduleJobVo.setJobClass(jobClass);
			}
			if (triggerState!=null) {
				scheduleJobVo.setTriggerState(triggerState);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	// check if exists
	public static boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
		return scheduler.checkExists(triggerKey);
	}
	public static boolean checkExists(String jobName, String jobGroup) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return scheduler.checkExists(triggerKey);
	}
	// addJob 新增
    @SuppressWarnings("unchecked")
	public static boolean addJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {
    	// TriggerKey : name + group
		TriggerKey triggerKey = scheduleJobVo.getTriggerKey();
		JobKey jobKey = scheduleJobVo.getJobKey();
        // TriggerKey valid if_exists
        if (checkExists(scheduleJobVo.getTriggerKey())) {
            logger.info(">>>>>>>>> addJob fail, job already exist, scheduleJobVo:{}", scheduleJobVo);
            return false;
        }
        
        // CronTrigger : TriggerKey + cronExpression	// withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getJobCron()).withMisfireHandlingInstructionDoNothing();
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
//
//        // JobDetail : jobClass
//		Class<? extends Job> jobClass_ = null;
//		try {
//			jobClass_ = (Class<? extends Job>)Class.forName(jobInfo.getJobClass());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		JobDetail jobDetail = JobBuilder.newJob(jobClass_).withIdentity(jobKey).build();
//        if (jobInfo.getJobData()!=null) {
//        	JobDataMap jobDataMap = jobDetail.getJobDataMap();
//        	jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(), Map.class));	// JobExecutionContext context.getMergedJobDataMap().get("mailGuid");
//		}
        
        // schedule : jobDetail + cronTrigger
        Date date = scheduler.scheduleJob(scheduleJobVo.getJobDetail(), scheduleJobVo.getTrigger());

        logger.info(">>>>>>>>>>> addJob success, jobDetail:{}, cronTrigger:{}, date:{}", scheduleJobVo.getJobDetail(), scheduleJobVo.getTrigger(), date);
        return true;
    }
    
    // reschedule
    @SuppressWarnings("unchecked")
	public static boolean rescheduleJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {
    	
    	// TriggerKey valid if_exists
        if (!checkExists(scheduleJobVo.getTriggerKey())) {
        	logger.info(">>>>>>>>>>> rescheduleJob fail, job not exists, scheduleJobVo:{}", scheduleJobVo);
            return false;
        }
        
        // TriggerKey : name + group
//        TriggerKey triggerKey = scheduleJobVo.getTriggerKey();
//        JobKey jobKey = scheduleJobVo.getJobKey();
//
//        // CronTrigger : TriggerKey + cronExpression
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getJobCron()).withMisfireHandlingInstructionDoNothing();
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
//
//        //scheduler.rescheduleJob(triggerKey, cronTrigger);
//
//        // JobDetail-JobDataMap fresh
//        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
//    	JobDataMap jobDataMap = jobDetail.getJobDataMap();
//    	jobDataMap.clear();
//    	jobDataMap.putAll(JacksonUtil.readValue(jobInfo.getJobData(), Map.class));
//
    	// Trigger fresh
    	HashSet<Trigger> triggerSet = new HashSet<Trigger>();
    	triggerSet.add(scheduleJobVo.getTrigger());
        
        scheduler.scheduleJob(scheduleJobVo.getJobDetail(), triggerSet, true);
        logger.info(">>>>>>>>>>> resumeJob success, scheduleJobVo:{}", scheduleJobVo);
        return true;
    }
    
    // unscheduleJob
    public static boolean removeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            result = scheduler.unscheduleJob(triggerKey);
            logger.info(">>>>>>>>>>> removeJob, triggerKey:{}, result [{}]", triggerKey, result);
        }
        return true;
    }


	// Pause
    public static boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> pauseJob success, triggerKey:{}", triggerKey);
        } else {
        	logger.info(">>>>>>>>>>> pauseJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }
    
    // resume
    public static boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> resumeJob success, triggerKey:{}", triggerKey);
        } else {
        	logger.info(">>>>>>>>>>> resumeJob fail, triggerKey:{}", triggerKey);
        }
        return result;
    }
    
    // run
    public static boolean triggerJob(String jobName, String jobGroup) throws SchedulerException {
    	// TriggerKey : name + group
    	JobKey jobKey = new JobKey(jobName, jobGroup);
        
        boolean result = false;
        if (checkExists(jobName, jobGroup)) {
            scheduler.triggerJob(jobKey);
            result = true;
            logger.info(">>>>>>>>>>> runJob success, jobKey:{}", jobKey);
        } else {
        	logger.info(">>>>>>>>>>> runJob fail, jobKey:{}", jobKey);
        }
        return result;
    }

	public static void setScheduler(Scheduler scheduler) {
		DynamicSchedulerFactory.scheduler = scheduler;
	}
}