package com.application.taskengine.model;


import com.cheng.lang.model.SuperModel;

/**
 */
public class TaskParamKeyModel extends SuperModel {
    private String pkTaskplugin;
    private String pkTaskparamkey;
    private String paramname;
    private String paramkey;

    private String ts;
    private int dr;

    @Override
    public String getTableName() {
        return "task_taskparamkey";
    }

    @Override
    public String getParentPKFieldName() {
        return "pkTaskplugin";
    }

    @Override
    public String getPKFieldName() {
        return "pkTaskparamkey";
    }

    public String getPkTaskplugin() {
        return pkTaskplugin;
    }

    public void setPkTaskplugin(String pkTaskplugin) {
        this.pkTaskplugin = pkTaskplugin;
    }

    public String getPkTaskparamkey() {
        return pkTaskparamkey;
    }

    public void setPkTaskparamkey(String pkTaskparamkey) {
        this.pkTaskparamkey = pkTaskparamkey;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public String getParamkey() {
        return paramkey;
    }

    public void setParamkey(String paramkey) {
        this.paramkey = paramkey;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }
}
