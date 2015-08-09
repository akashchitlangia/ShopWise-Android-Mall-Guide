package com.utopian.tools;

public class DbBean {
	private String taskName;
	private int status;
	private int id;
	
	public DbBean()
	{
		this.taskName=null;
		this.status=0;
	}
	public DbBean(String taskName, int status) {
		super();
		this.taskName = taskName;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
