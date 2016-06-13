package com.application.taskengine.util;

import com.application.taskengine.vo.ScheduleJobVo;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * Created by cheng on 2015/7/4.
 */
public class DynamicSchedulerFactory implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulerFactory.class);

    private static Scheduler scheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info(">>>>>>>>> init scheduler success >>>>>>>>>");
    }

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
            return null;
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
    public static boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
        return scheduler.checkExists(triggerKey);
    }

    /**
     *
     * @param jobCode
     * @param jobGroupCode
     * @return
     * @throws SchedulerException
     */
    public static boolean checkExists(String jobCode, String jobGroupCode) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroupCode);
        return scheduler.checkExists(triggerKey);
    }

    /**
     * addJob to scheduler
     *
     * @param scheduleJobVo
     * @return
     * @throws SchedulerException
     */
    public static boolean addJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {

        if (checkExists(scheduleJobVo.getTriggerKey())) {
            logger.info(">>>>>>>>> add fail, job already exist, scheduleJobVo:{} >>>>>>>>>", scheduleJobVo);
            return false;
        }

        Date date = scheduler.scheduleJob(scheduleJobVo.getJobDetail(), scheduleJobVo.getTrigger());

        logger.info(">>>>>>>>>>> add success, jobDetail:{}, cronTrigger:{}, date:{} >>>>>>>>>>>", scheduleJobVo.getJobDetail(), scheduleJobVo.getTrigger(), date);
        return true;
    }

    /**
     * reschedule task
     *
     * @param scheduleJobVo
     * @return
     * @throws SchedulerException
     */
    public static boolean rescheduleJob(ScheduleJobVo scheduleJobVo) throws SchedulerException {

        if (!checkExists(scheduleJobVo.getTriggerKey())) {
            logger.info(">>>>>>>>>>> rescheduleJob fail, job not exists, scheduleJobVo:{} >>>>>>>>>>>", scheduleJobVo);
            return false;
        }

        HashSet<Trigger> triggerSet = new HashSet<>();
        triggerSet.add(scheduleJobVo.getTrigger());

        scheduler.scheduleJob(scheduleJobVo.getJobDetail(), triggerSet, true);
        logger.info(">>>>>>>>>>> resume success, scheduleJobVo:{} >>>>>>>>>>>", scheduleJobVo);
        return true;
    }

    /**
     * removeJob task
     *
     * @param jobCode
     * @param jobGroupCode
     * @return
     * @throws SchedulerException
     */
    public static boolean removeJob(String jobCode, String jobGroupCode) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode, jobGroupCode);
        boolean result = false;
        if (checkExists(jobCode, jobGroupCode)) {
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
        if (checkExists(jobCode, jobGroup)) {
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
        if (checkExists(jobCode, jobGroupCode)) {
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
    public static boolean triggerJob(String jobName, String jobGroupCode) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroupCode);

        boolean result = false;
        if (checkExists(jobName, jobGroupCode)) {
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