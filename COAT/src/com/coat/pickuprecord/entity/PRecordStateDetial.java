package com.coat.pickuprecord.entity;

/**
 * PRecordStateDetial entity. @author MyEclipse Persistence Tools
 */

public class PRecordStateDetial implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer refno;
	private String staffcode;
	private String cilentName;
	private String location;
	private String scanDate;
	private String state;
	private String creator;
	private String createdate;

	// Constructors

	/** default constructor */
	public PRecordStateDetial() {
	}

	/** minimal constructor */
	public PRecordStateDetial(Integer refno) {
		this.refno = refno;
	}

	/** full constructor */
	public PRecordStateDetial(Integer refno, String staffcode,
			String cilentName, String location, String scanDate, String state,
			String creator, String createdate) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.cilentName = cilentName;
		this.location = location;
		this.scanDate = scanDate;
		this.state = state;
		this.creator = creator;
		this.createdate = createdate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRefno() {
		return this.refno;
	}

	public void setRefno(Integer refno) {
		this.refno = refno;
	}

	public String getStaffcode() {
		return this.staffcode;
	}

	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}

	public String getCilentName() {
		return this.cilentName;
	}

	public void setCilentName(String cilentName) {
		this.cilentName = cilentName;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getScanDate() {
		return this.scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
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

}