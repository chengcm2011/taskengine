package com.application.taskengine.vo;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * Created by liyd on 12/19/14.
 */
public class ScheduleTaskVo {
    private String jobCode ;
    private String jobGroupCode ;

    private JobKey jobKey ;
    private TriggerKey triggerKey ;

    private String cronExpression ;
    private String jobClass ;

    private JobDetail jobDetail ;
    private Trigger trigger ;

    private Trigger.TriggerState triggerState;

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobGroupCode() {
        return jobGroupCode;
    }

    public void setJobGroupCode(String jobGroupCode) {
        this.jobGroupCode = jobGroupCode;
    }

    public JobKey getJobKey() {
        return jobKey;
    }

    public void setJobKey(JobKey jobKey) {
        this.jobKey = jobKey;
    }

    public TriggerKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(TriggerKey triggerKey) {
        this.triggerKey = triggerKey;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Trigger.TriggerState getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(Trigger.TriggerState triggerState) {
        this.triggerState = triggerState;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    @Override
    public String toString() {
        return "ScheduleTaskVo{" +
                "jobCode='" + jobCode + '\'' +
                ", jobGroupCode='" + jobGroupCode + '\'' +
                ", jobKey=" + jobKey +
                ", triggerKey=" + triggerKey +
                ", cronExpression='" + cronExpression + '\'' +
                ", jobClass='" + jobClass + '\'' +
                ", jobDetail=" + jobDetail +
                ", trigger=" + trigger +
                ", triggerState=" + triggerState +
                '}';
    }
}
