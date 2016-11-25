package com.application.taskengine.model;


import com.cheng.lang.model.SuperModel;

/**
 */
public class TaskParamKeyModel extends SuperModel {
	private String pk_taskplugin ;
	private String pk_taskparamkey ;
	private String paramname ;
	private String paramkey ;

	private String ts ;
	private int dr ;

	@Override
	public String getTableName() {
		return "task_taskparamkey";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_taskplugin";
	}

	@Override
	public String getPKFieldName() {
		return "pk_taskparamkey";
	}

	public String getPk_taskplugin() {
		return pk_taskplugin;
	}

	public void setPk_taskplugin(String pk_taskplugin) {
		this.pk_taskplugin = pk_taskplugin;
	}

	public String getPk_taskparamkey() {
		return pk_taskparamkey;
	}

	public void setPk_taskparamkey(String pk_taskparamkey) {
		this.pk_taskparamkey = pk_taskparamkey;
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
