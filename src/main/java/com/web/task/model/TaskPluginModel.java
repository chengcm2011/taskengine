package com.web.task.model;


import cheng.lib.lang.SuperModel;

public class TaskPluginModel extends SuperModel {
	private static final long serialVersionUID = 1L;
	private String pk_taskplugin;
	private String pluginname;
	private String pluginclass;
	private String plugindescription;
	private String bmodule;//模块
	private int dr;
	private String ts;

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	public String getPKFieldName() {
		return "pk_taskplugin";
	}

	public String getTableName() {
		return "task_taskplugin";
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPk_taskplugin() {
		return pk_taskplugin;
	}

	public void setPk_taskplugin(String pk_taskplugin) {
		this.pk_taskplugin = pk_taskplugin;
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