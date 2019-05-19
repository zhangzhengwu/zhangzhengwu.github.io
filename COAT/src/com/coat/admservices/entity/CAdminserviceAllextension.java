package com.coat.admservices.entity;

/**
 * CAdminserviceAllextension entity. @author MyEclipse Persistence Tools
 */

public class CAdminserviceAllextension implements java.io.Serializable {

	// Fields

	private Integer id;
	private String staffcode;
	private String staffname;
	private String extension;
	private String password;
	private String finalFreeNo;
	private String department;
	private String reamrk;
	private String resignedOn;
	private String admHandled;
	private String itdhandled;
	private String number;
	private String ipPhone;
	private String phoneType;
	private String iddfunction;
	private String recruiter;
	private String recording;
	private String creator;
	private String createDate;
	private String sfyx;

	// Constructors

	/** default constructor */
	public CAdminserviceAllextension() {
	}

	/** full constructor */
	public CAdminserviceAllextension(String staffcode, String staffname,
			String extension, String password, String finalFreeNo,
			String department, String reamrk, String resignedOn,
			String admHandled, String itdhandled, String number,
			String ipPhone, String phoneType, String iddfunction,
			String recruiter, String recording, String creator,
			String createDate, String sfyx) {
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.extension = extension;
		this.password = password;
		this.finalFreeNo = finalFreeNo;
		this.department = department;
		this.reamrk = reamrk;
		this.resignedOn = resignedOn;
		this.admHandled = admHandled;
		this.itdhandled = itdhandled;
		this.number = number;
		this.ipPhone = ipPhone;
		this.phoneType = phoneType;
		this.iddfunction = iddfunction;
		this.recruiter = recruiter;
		this.recording = recording;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
	}

	/** full constructor */
	public CAdminserviceAllextension(String extension, String password,String phoneType) {
		this.extension = extension;
		this.password = password;
		this.phoneType = phoneType;
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

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFinalFreeNo() {
		return this.finalFreeNo;
	}

	public void setFinalFreeNo(String finalFreeNo) {
		this.finalFreeNo = finalFreeNo;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getReamrk() {
		return this.reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}

	public String getResignedOn() {
		return this.resignedOn;
	}

	public void setResignedOn(String resignedOn) {
		this.resignedOn = resignedOn;
	}

	public String getAdmHandled() {
		return this.admHandled;
	}

	public void setAdmHandled(String admHandled) {
		this.admHandled = admHandled;
	}

	public String getItdhandled() {
		return this.itdhandled;
	}

	public void setItdhandled(String itdhandled) {
		this.itdhandled = itdhandled;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIpPhone() {
		return this.ipPhone;
	}

	public void setIpPhone(String ipPhone) {
		this.ipPhone = ipPhone;
	}

	public String getPhoneType() {
		return this.phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getIddfunction() {
		return this.iddfunction;
	}

	public void setIddfunction(String iddfunction) {
		this.iddfunction = iddfunction;
	}

	public String getRecruiter() {
		return this.recruiter;
	}

	public void setRecruiter(String recruiter) {
		this.recruiter = recruiter;
	}

	public String getRecording() {
		return this.recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
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

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

}