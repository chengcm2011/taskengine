package com.application.taskengine.model;


import com.cheng.lang.model.SuperModel;

public class TaskDeployModel extends SuperModel {

    public static final String PK_TASK_DEPLOY = "pkTaskdeploy";
    private String pkTaskdeploy;
    private String pkTaskplugin;
    private String taskType;
    private String taskName;
    private String taskDescription;

    private String cronExpression;

    private String runnable;// 任务是否可用
    private int dr;
    private String ts;

    private String vdef1;
    private String vdef2;
    private String vdef3;
    private String vdef4;
    private String vdef5;


    public String getTableName() {
        return "task_taskdeploy";
    }

    @Override
    public String getParentPKFieldName() {
        return "pkTaskplugin";
    }

    @Override
    public String getPKFieldName() {
        return "pkTaskdeploy";
    }

    public String getPkTaskdeploy() {
        return pkTaskdeploy;
    }

    public void setPkTaskdeploy(String pkTaskdeploy) {
        this.pkTaskdeploy = pkTaskdeploy;
    }

    public String getPkTaskplugin() {
        return pkTaskplugin;
    }

    public void setPkTaskplugin(String pkTaskplugin) {
        this.pkTaskplugin = pkTaskplugin;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getRunnable() {
        return runnable;
    }

    public void setRunnable(String runnable) {
        this.runnable = runnable;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getVdef1() {
        return vdef1;
    }

    public void setVdef1(String vdef1) {
        this.vdef1 = vdef1;
    }

    public String getVdef2() {
        return vdef2;
    }

    public void setVdef2(String vdef2) {
        this.vdef2 = vdef2;
    }

    public String getVdef3() {
        return vdef3;
    }

    public void setVdef3(String vdef3) {
        this.vdef3 = vdef3;
    }

    public String getVdef4() {
        return vdef4;
    }

    public void setVdef4(String vdef4) {
        this.vdef4 = vdef4;
    }

    public String getVdef5() {
        return vdef5;
    }

    public void setVdef5(String vdef5) {
        this.vdef5 = vdef5;
    }

    public enum JobType {

        fix_simple, elastic_simple, elastic_flow
    }
}