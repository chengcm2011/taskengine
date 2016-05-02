package com.web.task.model;


import cheng.lib.lang.SuperModel;

/**
 * Created by cheng on 2015/8/28.
 */
public class TaskParamValueModel extends SuperModel {

	private String pk_taskparamvalue ;

	private String pk_taskdeploy ;
	private String pk_taskparamkey ;
	private String paramkey ;
	private String paramname ;
	private String paramvalue ;

	private String ts ;
	private int dr ;

	@Override
	public String getTableName() {
		return "task_tasparamvalue";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_taskparamkey";
	}

	@Override
	public String getPKFieldName() {
		return "pk_taskparamvalue";
	}

	public String getParamkey() {
		return paramkey;
	}

	public void setParamkey(String paramkey) {
		this.paramkey = paramkey;
	}

	public String getParamname() {
		return paramname;
	}

	public void setParamname(String paramname) {
		this.paramname = paramname;
	}

	public String getPk_taskparamvalue() {
		return pk_taskparamvalue;
	}

	public void setPk_taskparamvalue(String pk_taskparamvalue) {
		this.pk_taskparamvalue = pk_taskparamvalue;
	}

	public String getPk_taskdeploy() {
		return pk_taskdeploy;
	}

	public void setPk_taskdeploy(String pk_taskdeploy) {
		this.pk_taskdeploy = pk_taskdeploy;
	}

	public String getPk_taskparamkey() {
		return pk_taskparamkey;
	}

	public void setPk_taskparamkey(String pk_taskparamkey) {
		this.pk_taskparamkey = pk_taskparamkey;
	}

	public String getParamvalue() {
		return paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
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
