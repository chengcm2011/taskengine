package com.web.task.exception;

/**
 * Created by cheng on 2015/10/17.
 */
public class ScheduleException extends RuntimeException {
	public ScheduleException(String errormsg) {
		super(errormsg);
	}
	public ScheduleException(Exception exception) {
		super(exception);
	}
}
