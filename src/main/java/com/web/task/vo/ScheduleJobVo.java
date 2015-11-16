package com.web.task.vo;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * Created by liyd on 12/19/14.
 */
public class ScheduleJobVo  {
    private String jobName ;
    private String jobCode ;
    private String jobGroupCode ;
    private JobKey jobKey ;
    private TriggerKey triggerKey ;
    private JobDetail jobDetail ;
    private Trigger trigger ;
    private boolean jobstatus ;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public String getJobCode() {
        return jobCode;
    }

    public boolean isJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(boolean jobstatus) {
        this.jobstatus = jobstatus;
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
}
