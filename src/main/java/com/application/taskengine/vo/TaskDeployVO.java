package com.application.taskengine.vo;


import com.cheng.lang.vo.SuperVO;

/**
 * 任务部署信息  一个任务插件可以部署多次 。会产生多少部署信息
 * @author cheng
 *
 */
public class TaskDeployVO extends SuperVO {

	private static final long serialVersionUID = 1L;
	private String pk_taskdeploy ;
	private String pk_taskplugin ;//任务插件主键
	private String taskname ;//任务名称
	private String taskdescription ;//任务描述
	private String priority;//#high,normal,low 优先级 线程相关
	private String period;//2  周期 数字
	private String period_unit;//YEAR 周期单位
	private String delay;//执行任务前的延迟时间，单位是毫秒。
	private String startTime;//#yyyy-MM-dd HH mm ss 开始时间
	private String overTime;//#yyyy-MM-dd HH mm ss 开始时间
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	private boolean runnable;// 任务是否可用
	private int dr ;
	private String ts ;
	
	public String getParentPKFieldName() {
		
		return "pk_taskplugin";
	}
	
	public String getPKFieldName() {
		return "pk_taskdeploy";
	}
	
	public String getTableName() {
		return "pub_taskdeploy";
	}
	public String getPk_taskdeploy() {
		return pk_taskdeploy;
	}
	public void setPk_taskdeploy(String pk_taskdeploy) {
		this.pk_taskdeploy = pk_taskdeploy;
	}
	public String getPk_taskplugin() {
		return pk_taskplugin;
	}
	public void setPk_taskplugin(String pk_taskplugin) {
		this.pk_taskplugin = pk_taskplugin;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPeriod_unit() {
		return period_unit;
	}
	public void setPeriod_unit(String period_unit) {
		this.period_unit = period_unit;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public boolean getRunnable() {
		return runnable;
	}
	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getTaskdescription() {
		return taskdescription;
	}
	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}
}
