package com.coat.namecard.entity;

/**
 * RequestStaffConvoyDetial entity. @author MyEclipse Persistence Tools
 */

public class NameCardConvoyDetial implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private Integer staffrefno;
	private String staffcode;
	private String staffnameE;
	private String staffnameC;
	private String state;
	private String creator;
	private String createdate;
	private String remark;

	// Constructors

	/** default constructor */
	public NameCardConvoyDetial() {
	}

	/** minimal constructor */
	public NameCardConvoyDetial(String username, Integer staffrefno,
			String staffcode) {
		this.username = username;
		this.staffrefno = staffrefno;
		this.staffcode = staffcode;
	}

	/** full constructor */
	public NameCardConvoyDetial(String username, Integer staffrefno,
			String staffcode, String staffnameE, String staffnameC,
			String state, String creator, String createdate, String remark) {
		this.username = username;
		this.staffrefno = staffrefno;
		this.staffcode = staffcode;
		this.staffnameE = staffnameE;
		this.staffnameC = staffnameC;
		this.state = state;
		this.creator = creator;
		this.createdate = createdate;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getStaffrefno() {
		return this.staffrefno;
	}

	public void setStaffrefno(Integer staffrefno) {
		this.staffrefno = staffrefno;
	}

	public String getStaffcode() {
		return this.staffcode;
	}

	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}

	public String getStaffnameE() {
		return this.staffnameE;
	}

	public void setStaffnameE(String staffnameE) {
		this.staffnameE = staffnameE;
	}

	public String getStaffnameC() {
		return this.staffnameC;
	}

	public void setStaffnameC(String staffnameC) {
		this.staffnameC = staffnameC;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}