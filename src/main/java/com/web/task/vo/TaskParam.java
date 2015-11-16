package com.web.task.vo;

public class TaskParam {

	private String param ;
	public TaskParam(String param) {
		this.param = param ;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	public String toString() {
		return "TaskParam [param=" + param + "]";
	}

}
