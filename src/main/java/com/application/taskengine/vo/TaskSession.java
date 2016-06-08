package com.application.taskengine.vo;

import com.application.common.vo.UserSessionVO;

/**
 * Created by cheng on 2015/10/17.
 */
public class TaskSession extends UserSessionVO {

	private int id ;
	private String phone ;
	private String showname ;
	@Override
	public Object getPrimaryKeyValue() {
		return id;
	}

	@Override
	public String getPhone() {
		return phone;
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

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
