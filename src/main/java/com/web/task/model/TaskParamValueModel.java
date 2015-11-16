package com.web.task.model;

import arch.util.lang.SuperModel;

/**
 * Created by cheng on 2015/8/28.
 */
public class TaskParamValueModel extends SuperModel {

	private int id_tasparamvalue ;

	private int id_taskdeploy ;
	private int id_taskparamkey ;
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
	public String getPKFieldName() {
		return "id_tasparamvalue";
	}

	public int getId_tasparamvalue() {
		return id_tasparamvalue;
	}

	public void setId_tasparamvalue(int id_tasparamvalue) {
		this.id_tasparamvalue = id_tasparamvalue;
	}

	public int getId_taskdeploy() {
		return id_taskdeploy;
	}

	public void setId_taskdeploy(int id_taskdeploy) {
		this.id_taskdeploy = id_taskdeploy;
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

	public int getId_taskparamkey() {
		return id_taskparamkey;
	}

	public void setId_taskparamkey(int id_taskparamkey) {
		this.id_taskparamkey = id_taskparamkey;
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
