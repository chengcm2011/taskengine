package com.web.task.vo;


import java.util.HashMap;
import java.util.Map;

public class TaskUnitVO {
	private int id_taskplugin ;
	private int id_taskdeploy ;
	private String taskname; //任务名称
	private String triggerstr ;
	private String priority;//#high,normal,low 任务名称优先级 线程相关
	private long millisecond;//执行周期 单位是毫秒。
	private long delay;//执行任务前的延迟时间， 单位是毫秒。
	private String pluginclass;// 任务插件
	private String startTime;//#yyyy-MM-dd HH mm ss 开始时间
	private String overTime;//#yyyy-MM-dd HH mm ss 结束时间
	private boolean runnable;// 任务是否可用
	private Map<String,Object> data = new HashMap<>();

	public int getId_taskdeploy() {
		return id_taskdeploy;
	}

	public void setId_taskdeploy(int id_taskdeploy) {
		this.id_taskdeploy = id_taskdeploy;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public long getMillisecond() {
		return millisecond;
	}

	public String getTriggerstr() {
		return triggerstr;
	}

	public void setTriggerstr(String triggerstr) {
		this.triggerstr = triggerstr;
	}

	public void setMillisecond(long millisecond) {
		this.millisecond = millisecond;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public String getPluginclass() {
		return pluginclass;
	}

	public void setPluginclass(String pluginclass) {
		this.pluginclass = pluginclass;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public boolean isRunnable() {
		return runnable;
	}

	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public int getId_taskplugin() {
		return id_taskplugin;
	}

	public void setId_taskplugin(int id_taskplugin) {
		this.id_taskplugin = id_taskplugin;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
