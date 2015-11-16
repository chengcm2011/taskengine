package com.web.task.itf;

import java.util.Date;
import java.util.TimerTask;

public interface IBussicBackTask extends  Runnable {
	
	public void setTimerOverTime(Date date);
	public void setTimerTask(TimerTask timerTask);
	
	public void run();
}