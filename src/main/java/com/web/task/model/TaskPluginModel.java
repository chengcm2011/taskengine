package com.web.task.model;


import arch.util.lang.SuperModel;

public class TaskPluginModel extends SuperModel {
	private static final long serialVersionUID = 1L;
	private int id_taskplugin;
	private String pluginname;
	private String pluginclass;
	private String plugindescription;
	private String plugintype;//类型
	private String bmodule;//模块
	private int dr;
	private String ts;

	public String getPKFieldName() {
		return "id_taskplugin";
	}

	public String getTableName() {
		return "task_taskplugin";
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId_taskplugin() {
		return id_taskplugin;
	}

	public void setId_taskplugin(int id_taskplugin) {
		this.id_taskplugin = id_taskplugin;
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

	public String getPlugintype() {
		return plugintype;
	}

	public void setPlugintype(String plugintype) {
		this.plugintype = plugintype;
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