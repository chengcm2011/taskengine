package com.application.taskengine.model;


import cheng.lib.lang.SuperModel;

public class TaskLogModel extends SuperModel {

	private static final long serialVersionUID = 1L;
	private String pk_tasklog ;
	private String pk_taskdeploy ;
	private String returnstr ;
	private boolean issuccess ;
	private String runtime ;
	private String runserver ;
	private String vdef1;
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	private String ts;
	private int dr;

	@Override
	public String getParentPKFieldName() {
		return "pk_taskdeploy";
	}

	@Override
	public String getPKFieldName() {
		return "pk_tasklog";
	}

	@Override
	public String getTableName() {
		return "task_tasklog";
	}

	public String getPk_tasklog() {
		return pk_tasklog;
	}

	public void setPk_tasklog(String pk_tasklog) {
		this.pk_tasklog = pk_tasklog;
	}

	public String getPk_taskdeploy() {
		return pk_taskdeploy;
	}

	public void setPk_taskdeploy(String pk_taskdeploy) {
		this.pk_taskdeploy = pk_taskdeploy;
	}

	public boolean issuccess() {
		return issuccess;
	}

	public String getReturnstr() {
		return returnstr;
	}

	public void setReturnstr(String returnstr) {
		this.returnstr = returnstr;
	}

	public boolean isIssuccess() {
		return issuccess;
	}

	public void setIssuccess(boolean issuccess) {
		this.issuccess = issuccess;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getRunserver() {
		return runserver;
	}

	public void setRunserver(String runserver) {
		this.runserver = runserver;
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
