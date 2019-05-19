package com.coat.pickuprecord.entity;

/**
 * PRecordHkcode entity. @author MyEclipse Persistence Tools
 */

public class PRecordHkcode implements java.io.Serializable {

	// Fields

	private Integer id;
	private String staffcode;
	private String hkStaffcode;
	private String staffname;
	private String creator;
	private String createdate;
	private String sfyx;

	// Constructors

	/** default constructor */
	public PRecordHkcode() {
	}

	/** full constructor */
	public PRecordHkcode(String staffcode, String hkStaffcode,
			String staffname, String creator, String createdate, String sfyx) {
		this.staffcode = staffcode;
		this.hkStaffcode = hkStaffcode;
		this.staffname = staffname;
		this.creator = creator;
		this.createdate = createdate;
		this.sfyx = sfyx;
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

	public String getHkStaffcode() {
		return this.hkStaffcode;
	}

	public void setHkStaffcode(String hkStaffcode) {
		this.hkStaffcode = hkStaffcode;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
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

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

}