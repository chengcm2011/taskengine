package com.application.taskengine.model;


import com.cheng.lang.model.SuperModel;

public class TaskPluginModel extends SuperModel {
    private static final long serialVersionUID = 1L;
    private String pkTaskplugin;
    private String pluginname;
    private String pluginclass;
    private String plugindescription;
    private String bmodule;//模块
    private int dr;
    private String ts;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    public String getPKFieldName() {
        return "pkTaskplugin";
    }

    public String getTableName() {
        return "task_taskplugin";
    }

    public String getPkTaskplugin() {
        return pkTaskplugin;
    }

    public void setPkTaskplugin(String pkTaskplugin) {
        this.pkTaskplugin = pkTaskplugin;
    }

    public String getPluginname() {
        return pluginname;
    }

    public void setPluginname(String pluginname) {
        this.pluginname = pluginname;
    }

    public String getPluginclass() {
        return pluginclass;
    }

    public void setPluginclass(String pluginclass) {
        this.pluginclass = pluginclass;
    }

    public String getPlugindescription() {
        return plugindescription;
    }

    public void setPlugindescription(String plugindescription) {
        this.plugindescription = plugindescription;
    }

    public String getBmodule() {
        return bmodule;
    }

    public void setBmodule(String bmodule) {
        this.bmodule = bmodule;
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
}