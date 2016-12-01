package com.application.taskengine.model;


import com.cheng.lang.model.SuperModel;

/**
 */
public class TaskParamValueModel extends SuperModel {

	private String pkTaskparamvalue;

	private String pkTaskdeploy;
	private String pkTaskparamkey;
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
		return "pkTaskparamkey";
	}

	@Override
	public String getPKFieldName() {
		return "pkTaskparamvalue";
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

	public String getPkTaskparamvalue() {
		return pkTaskparamvalue;
	}

	public void setPkTaskparamvalue(String pkTaskparamvalue) {
		this.pkTaskparamvalue = pkTaskparamvalue;
	}

	public String getPkTaskdeploy() {
		return pkTaskdeploy;
	}

	public void setPkTaskdeploy(String pkTaskdeploy) {
		this.pkTaskdeploy = pkTaskdeploy;
	}

	public String getPkTaskparamkey() {
		return pkTaskparamkey;
	}

	public void setPkTaskparamkey(String pkTaskparamkey) {
		this.pkTaskparamkey = pkTaskparamkey;
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
