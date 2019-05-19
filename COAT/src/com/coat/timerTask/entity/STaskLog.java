package com.coat.timerTask.entity;


/**
 * STaskLog entity. @author MyEclipse Persistence Tools
 */

public class STaskLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String taskName;
	private String executeTime;
	private String result;
	private Integer status;

	// Constructors

	/** default constructor */
	public STaskLog() {
	}

	/** full constructor */
	public STaskLog(String taskName, String executeTime, Integer status) {
		this.taskName = taskName;
		this.executeTime = executeTime;
		this.status = status;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getExecuteTime() {
		return this.executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}