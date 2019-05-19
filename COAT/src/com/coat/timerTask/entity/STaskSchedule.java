package com.coat.timerTask.entity;

/**
 * STaskSchedule entity. @author MyEclipse Persistence Tools
 */

public class STaskSchedule implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4816409218298008715L;
	private Integer id;
	private String taskName;
	private String executeTime;
	private String executeScript;
	private String explain;
	private Integer status;
	private String creator;
	private String createDate;
	private String remark;

	// Constructors

	/** default constructor */
	public STaskSchedule() {
	}

	/** full constructor */
	public STaskSchedule(String taskName, String executeTime,
			String executeScript, String explain, Integer status,
			String creator, String createDate, String remark) {
		this.taskName = taskName;
		this.executeTime = executeTime;
		this.executeScript = executeScript;
		this.explain = explain;
		this.status = status;
		this.creator = creator;
		this.createDate = createDate;
		this.remark = remark;
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

	public String getExecuteScript() {
		return this.executeScript;
	}

	public void setExecuteScript(String executeScript) {
		this.executeScript = executeScript;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}