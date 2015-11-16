package com.web.task.model;

import arch.util.lang.SuperModel;

/**
 * Created by cheng on 2015/8/28.
 */
public class TaskParamKeyModel extends SuperModel {
	private int id_taskplugin ;
	private int id_taskparamkey ;
	private String paramname ;
	private String paramkey ;

	private String ts ;
	private int dr ;

	@Override
	public String getTableName() {
		return "task_taskparamkey";
	}

	@Override
	public String getPKFieldName() {
		return "id_taskparamkey";
	}

	public int getId_taskplugin() {
		return id_taskplugin;
	}

	public void setId_taskplugin(int id_taskplugin) {
		this.id_taskplugin = id_taskplugin;
	}

	public int getId_taskparamkey() {
		return id_taskparamkey;
	}

	public void setId_taskparamkey(int id_taskparamkey) {
		this.id_taskparamkey = id_taskparamkey;
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
