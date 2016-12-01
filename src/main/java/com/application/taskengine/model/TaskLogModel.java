package com.application.taskengine.model;


import com.cheng.lang.model.SuperModel;

public class TaskLogModel extends SuperModel {

	private static final long serialVersionUID = 1L;
	private String pkTasklog;
	private String pkTaskdeploy;
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
		return "pkTaskdeploy";
	}

	@Override
	public String getPKFieldName() {
		return "pkTasklog";
	}

	@Override
	public String getTableName() {
		return "task_tasklog";
	}

	public String getPkTasklog() {
		return pkTasklog;
	}

	public void setPkTasklog(String pkTasklog) {
		this.pkTasklog = pkTasklog;
	}

	public String getPkTaskdeploy() {
		return pkTaskdeploy;
	}

	public void setPkTaskdeploy(String pkTaskdeploy) {
		this.pkTaskdeploy = pkTaskdeploy;
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
