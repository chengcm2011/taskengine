package com.application.taskengine.vo;


import com.cheng.common.UserSessionVO;

/**
 *
 */
public class TaskSession extends UserSessionVO {

	private int id ;
	private String showname ;
	@Override
	public Object getPrimaryKeyValue() {
		return id;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
