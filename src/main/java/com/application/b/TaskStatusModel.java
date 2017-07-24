package com.application.b;

import com.cheng.lang.model.SuperModel;
import com.cheng.lang.model.UFBoolean;

/**
 * 任务状态
 *
 * @author chengys4
 *         2017-07-24 10:08
 **/
public class TaskStatusModel extends SuperModel {
    private String pkTaskStatus;
    private long tid;
    private UFBoolean close;
    private int dr = 0;
    private String ts;

    public String getPkTaskStatus() {
        return pkTaskStatus;
    }

    public void setPkTaskStatus(String pkTaskStatus) {
        this.pkTaskStatus = pkTaskStatus;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public UFBoolean getClose() {
        return close;
    }

    public void setClose(UFBoolean close) {
        this.close = close;
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

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public String getPKFieldName() {
        return "pkTaskStatus";
    }

    @Override
    public String getTableName() {
        return "t_task_status";
    }
}
