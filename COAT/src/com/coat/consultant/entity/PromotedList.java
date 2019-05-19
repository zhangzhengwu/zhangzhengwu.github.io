package com.coat.consultant.entity;

/**
 * PromotedList entity. @author MyEclipse Persistence Tools
 */

public class PromotedList implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String staffcode;
	private String staffname;
	private String grade;
	private String resign;
	private String promotedate;
	private String reason;

	// Constructors

	/** default constructor */
	public PromotedList() {
	}

	/** full constructor */
	public PromotedList(String staffcode, String staffname, String grade,
			String resign, String promotedate, String reason) {
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.grade = grade;
		this.resign = resign;
		this.promotedate = promotedate;
		this.reason = reason;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStaffcode() {
		return this.staffcode;
	}

	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getResign() {
		return this.resign;
	}

	public void setResign(String resign) {
		this.resign = resign;
	}

	public String getPromotedate() {
		return this.promotedate;
	}

	public void setPromotedate(String promotedate) {
		this.promotedate = promotedate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}