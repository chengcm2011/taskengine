package com.application.taskengine.util;

import com.application.taskengine.vo.ScheduleTaskVo;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by cheng on 2015/7/4.
 */
public class DynamicSchedulerFactory {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulerFactory.class);

    private static Scheduler scheduler;

    public static List<Map<String, Object>> getJobList() {
        List<Map<String, Object>> jobList = new ArrayList<>();

        try {
            if (scheduler.getJobGroupNames() == null || scheduler.getJobGroupNames().size() == 0) {
                return null;
            }
            List<String> groupCodes = scheduler.getJobGroupNames();
            if (groupCodes == null || groupCodes.isEmpty()) {
                return jobList;
            }
            for (String groupName : groupCodes) {
                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                if (jobKeys == null || jobKeys.isEmpty()) {
                    continue;
                }
                for (JobKey jobKey : jobKeys) {
                    TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
                    Trigger trigger = scheduler.getTrigger(triggerKey);
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    TriggerState triggerState = scheduler.getTriggerState(triggerKey);
                    Map<String, Object> jobMap = new HashMap<>();
                    String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
                    jobMap.put("NextFireTime", trigger.getNextFireTime());
                    jobMap.put("jobclass", jobDetail.getJobClass());
                    jobMap.put("jobcode", triggerKey.getName());
                    jobMap.put("jobname", triggerKey.getName());
                    jobMap.put("jobgroupcode", triggerKey.getGroup());
                    jobMap.put("TriggerStateName", triggerState.name());
                    jobMap.put("cronExpression", cronExpression);
                    jobList.add(jobMap);
                }
            }
        } catch (SchedulerException e) {
            logger.error("task get error", e);
        }
        return jobList;
    }


    /**
     * check if triggerKey exists
     *
     * @param triggerKey
     * @return
     * @throws SchedulerException
     */
    public static boolean checkTaskExists(TriggerKey triggerKey) throws SchedulerException {
        return scheduler.checkExists(triggerKey);
    }

    /**
     *
     * @param jobCode
     * @param jobGroupCode
     * @return
     * @throws SchedulerException
     */
    public static boolean checkTaskExists(String jobCode, String jobGroupCode) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroupCode);
        return scheduler.checkExists(triggerKey);
    }

    /**
     * addTask to scheduler
     *
     * @param scheduleTaskVo
     * @return
     * @throws SchedulerException
     */
    public static boolean addTask(ScheduleTaskVo scheduleTaskVo) throws SchedulerException {

        if (checkTaskExists(scheduleTaskVo.getTriggerKey())) {
            logger.info(">>>>>>>>> add fail, task already exist, scheduleTaskVo:{} >>>>>>>>>", scheduleTaskVo);
            return false;
        }

        Date date = scheduler.scheduleJob(scheduleTaskVo.getJobDetail(), scheduleTaskVo.getTrigger());

        logger.info(">>>>>>>>>>> add success, jobDetail:{}, cronTrigger:{}, date:{} >>>>>>>>>>>", scheduleTaskVo.getJobDetail(), scheduleTaskVo.getTrigger(), date);
        return true;
    }

    /**
     * reschedule task
     *
     * @param scheduleTaskVo
     * @return
     * @throws SchedulerException
     */
    public static boolean rescheduleTask(ScheduleTaskVo scheduleTaskVo) throws SchedulerException {

        if (!checkTaskExists(scheduleTaskVo.getTriggerKey())) {
            logger.info(">>>>>>>>>>> rescheduleTask fail, task not exists, scheduleTaskVo:{} >>>>>>>>>>>", scheduleTaskVo);
            return false;
        }

        HashSet<Trigger> triggerSet = new HashSet<>();
        triggerSet.add(scheduleTaskVo.getTrigger());

        scheduler.scheduleJob(scheduleTaskVo.getJobDetail(), triggerSet, true);
        logger.info(">>>>>>>>>>> resume success, scheduleTaskVo:{} >>>>>>>>>>>", scheduleTaskVo);
        return true;
    }

    /**
     * removeTask task
     *
     * @param jobCode
     * @param jobGroupCode
     * @return
     * @throws SchedulerException
     */
    public static boolean removeTask(String jobCode, String jobGroupCode) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroupCode);
        boolean result = false;
        if (checkTaskExists(jobCode, jobGroupCode)) {
            result = scheduler.unscheduleJob(triggerKey);
            logger.info(">>>>>>>>>>> remove, triggerKey:{}, result [{}] >>>>>>>>>>>", triggerKey, result);
        }
        return true;
    }


    /**
     * Pause
     *
     * @param jobCode
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean pauseJob(String jobCode, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroup);

        boolean result = false;
        if (checkTaskExists(jobCode, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> pause success, triggerKey:{} >>>>>>>>>>>", triggerKey);
        } else {
            logger.info(">>>>>>>>>>> pause fail, triggerKey:{} >>>>>>>>>>>", triggerKey);
        }
        return result;
    }

    /**
     * resume task
     *
     * @param jobCode
     * @param jobGroupCode
     * @return
     * @throws SchedulerException
     */
    public static boolean resumeJob(String jobCode, String jobGroupCode) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroupCode);

        boolean result = false;
        if (checkTaskExists(jobCode, jobGroupCode)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
            logger.info(">>>>>>>>>>> resume success, triggerKey:{} >>>>>>>>>>>", triggerKey);
        } else {
            logger.info(">>>>>>>>>>> resume fail, triggerKey:{} >>>>>>>>>>>", triggerKey);
        }
        return result;
    }

    /**
     * run
     *
     * @param jobName
     * @param jobGroupCode
     * @return
     * @throws SchedulerException
     */
    public static boolean runOnceTask(String jobName, String jobGroupCode) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroupCode);

        boolean result = false;
        if (checkTaskExists(jobName, jobGroupCode)) {
            scheduler.triggerJob(jobKey);
            result = true;
            logger.info(">>>>>>>>>>> runJob success, jobKey:{} >>>>>>>>>>>", jobKey);
        } else {
            logger.info(">>>>>>>>>>> runJob fail, jobKey:{} >>>>>>>>>>>", jobKey);
        }
        return result;
    }

    public void setScheduler(Scheduler scheduler) {
        DynamicSchedulerFactory.scheduler = scheduler;
    }
}