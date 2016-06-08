package com.application.taskengine.model;


import cheng.lib.lang.SuperModel;

public class TaskDeployModel extends SuperModel {

	private String pk_taskdeploy;
	private String pk_taskplugin;
	private String taskname;
	private String taskdescription;

	private String triggerstr ;

	private String runnable;// 任务是否可用
	private int dr ;
	private String ts ;

	private String vdef1 ;
	private String vdef2 ;
	private String vdef3 ;
	private String vdef4 ;
	private String vdef5 ;



	public String getTableName() {
		return "task_taskdeploy";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_taskplugin";
	}

	@Override
	public String getPKFieldName() {
		return "pk_taskdeploy";
	}


	public String getVdef1() {
		return vdef1;
	}

	public String getTriggerstr() {
		return triggerstr;
	}

	public void setTriggerstr(String triggerstr) {
		this.triggerstr = triggerstr;
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

	public String getPk_taskdeploy() {
		return pk_taskdeploy;
	}

	public void setPk_taskdeploy(String pk_taskdeploy) {
		this.pk_taskdeploy = pk_taskdeploy;
	}

	public String getPk_taskplugin() {
		return pk_taskplugin;
	}

	public void setPk_taskplugin(String pk_taskplugin) {
		this.pk_taskplugin = pk_taskplugin;
	}


	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskdescription() {
		return taskdescription;
	}

	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
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
	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}
}