package com.web.task.model;


import arch.util.lang.SuperModel;

public class TaskDeployModel extends SuperModel {

	private int id_taskdeploy;
	private int id_taskplugin;
	private int tasktype ;
	private String taskname;
	private String taskdescription;

	private String triggerstr ;

	private String stardatetime ;
	private String overdatetime ;
	private String runnable;// 任务是否可用
	private int dr ;
	private String ts ;

	private String vdef1 ;
	private String vdef2 ;
	private String vdef3 ;
	private String vdef4 ;
	private String vdef5 ;

	public enum TaskType {
		Simple(1,"简单重复"),
		Cron(2,"Cron表达式");

		public final int code;public final String typename;
		TaskType(int code, String typename) {this.code = code;this.typename = typename;}
		public static TaskType getTaskType(int code) {
			for (TaskType item : TaskType.values()){
				if (item.code == code) {return item;}}return null;
		}
		public static String getTaskTypeName(int code) {
			for (TaskType item : TaskType.values()) {
				if (item.code == code) {return item.typename;}}return "";
		}
	}
	public enum PeriodUnit {
		秒(0,1000L),
		分(1,1000L * 60),
		时(2,1000L * 60 * 60),
		天(3,1000L * 60 * 60 * 24),
		周(4,1000L * 60 * 60 * 24 * 7),
		月(5,1000L * 60 * 60 * 24 * 30),
		年(6,1000L * 60 * 60 * 24 * 365);

		public final int code;public final long millisecond;
		PeriodUnit(int code, long millisecond) {this.code = code;this.millisecond = millisecond;}
		public static PeriodUnit getPeriodUnit(int code) {
			for (PeriodUnit item : PeriodUnit.values()){
				if (item.code == code) {return item;}}return null;
		}
		public static long getPeriodUnitValue(int code) {
			for (PeriodUnit item : PeriodUnit.values()) {
				if (item.code == code) {return item.millisecond;}}return 1000;
		}
	}
	public String getTableName() {
		return "task_taskdeploy";
	}
	@Override
	public String getPKFieldName() {
		return "id_taskdeploy";
	}

	public int getId_taskdeploy() {
		return id_taskdeploy;
	}

	public void setId_taskdeploy(int id_taskdeploy) {
		this.id_taskdeploy = id_taskdeploy;
	}

	public String getVdef1() {
		return vdef1;
	}

	public int getTasktype() {
		return tasktype;
	}

	public void setTasktype(int tasktype) {
		this.tasktype = tasktype;
	}

	public String getTriggerstr() {
		return triggerstr;
	}

	public void setTriggerstr(String triggerstr) {
		this.triggerstr = triggerstr;
	}

	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}

	public String getVdef2() {
		return vdef2;
	}

	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}

	public String getVdef3() {
		return vdef3;
	}

	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}

	public String getVdef4() {
		return vdef4;
	}

	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}

	public String getVdef5() {
		return vdef5;
	}

	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}

	public int getId_taskplugin() {
		return id_taskplugin;
	}

	public void setId_taskplugin(int id_taskplugin) {
		this.id_taskplugin = id_taskplugin;
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
	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}
}