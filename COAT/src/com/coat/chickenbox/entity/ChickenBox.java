package com.coat.chickenbox.entity;

/**
 * ChickenBox entity. @author MyEclipse Persistence Tools
 */

public class ChickenBox implements java.io.Serializable {

	// Fields

	private Integer id;
	private String consultantId;
	private String alias;
	private String surName;
	private String givenName;
	private String chineseSurName;
	private String chineseName;
	private String gradeId;
	private String gradeName;
	private String recruiterId;
	private String recruiterGradeId;
	private String recruiterGradeName;
	private String territoryStartDate;
	private String internalPositionId;
	private String internalPositionName;
	private String externalPositionId;
	private String externalPositionName;
	private String directLine;
	private String email;
	private String hkid;
	private String location;
	private String mobile;
	private String adtreeHead;
	private String ddtreeHead;
	private String halfConsultant;

	// Constructors

	/** default constructor */
	public ChickenBox() {
	}

	/** full constructor */
	public ChickenBox(String consultantId, String alias, String surName,
			String givenName, String chineseSurName, String chineseName,
			String gradeId, String gradeName, String recruiterId,
			String recruiterGradeId, String recruiterGradeName,
			String territoryStartDate, String internalPositionId,
			String internalPositionName, String externalPositionId,
			String externalPositionName, String directLine, String email,
			String hkid, String location, String mobile, String adtreeHead,
			String ddtreeHead, String halfConsultant) {
		this.consultantId = consultantId;
		this.alias = alias;
		this.surName = surName;
		this.givenName = givenName;
		this.chineseSurName = chineseSurName;
		this.chineseName = chineseName;
		this.gradeId = gradeId;
		this.gradeName = gradeName;
		this.recruiterId = recruiterId;
		this.recruiterGradeId = recruiterGradeId;
		this.recruiterGradeName = recruiterGradeName;
		this.territoryStartDate = territoryStartDate;
		this.internalPositionId = internalPositionId;
		this.internalPositionName = internalPositionName;
		this.externalPositionId = externalPositionId;
		this.externalPositionName = externalPositionName;
		this.directLine = directLine;
		this.email = email;
		this.hkid = hkid;
		this.location = location;
		this.mobile = mobile;
		this.adtreeHead = adtreeHead;
		this.ddtreeHead = ddtreeHead;
		this.halfConsultant = halfConsultant;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConsultantId() {
		return this.consultantId;
	}

	public void setConsultantId(String consultantId) {
		this.consultantId = consultantId;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSurName() {
		return this.surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getChineseSurName() {
		return this.chineseSurName;
	}

	public void setChineseSurName(String chineseSurName) {
		this.chineseSurName = chineseSurName;
	}

	public String getChineseName() {
		return this.chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getRecruiterId() {
		return this.recruiterId;
	}

	public void setRecruiterId(String recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getRecruiterGradeId() {
		return this.recruiterGradeId;
	}

	public void setRecruiterGradeId(String recruiterGradeId) {
		this.recruiterGradeId = recruiterGradeId;
	}

	public String getRecruiterGradeName() {
		return this.recruiterGradeName;
	}

	public void setRecruiterGradeName(String recruiterGradeName) {
		this.recruiterGradeName = recruiterGradeName;
	}

	public String getTerritoryStartDate() {
		return this.territoryStartDate;
	}

	public void setTerritoryStartDate(String territoryStartDate) {
		this.territoryStartDate = territoryStartDate;
	}

	public String getInternalPositionId() {
		return this.internalPositionId;
	}

	public void setInternalPositionId(String internalPositionId) {
		this.internalPositionId = internalPositionId;
	}

	public String getInternalPositionName() {
		return this.internalPositionName;
	}

	public void setInternalPositionName(String internalPositionName) {
		this.internalPositionName = internalPositionName;
	}

	public String getExternalPositionId() {
		return this.externalPositionId;
	}

	public void setExternalPositionId(String externalPositionId) {
		this.externalPositionId = externalPositionId;
	}

	public String getExternalPositionName() {
		return this.externalPositionName;
	}

	public void setExternalPositionName(String externalPositionName) {
		this.externalPositionName = externalPositionName;
	}

	public String getDirectLine() {
		return this.directLine;
	}

	public void setDirectLine(String directLine) {
		this.directLine = directLine;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHkid() {
		return this.hkid;
	}

	public void setHkid(String hkid) {
		this.hkid = hkid;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAdtreeHead() {
		return this.adtreeHead;
	}

	public void setAdtreeHead(String adtreeHead) {
		this.adtreeHead = adtreeHead;
	}

	public String getDdtreeHead() {
		return this.ddtreeHead;
	}

	public void setDdtreeHead(String ddtreeHead) {
		this.ddtreeHead = ddtreeHead;
	}

	public String getHalfConsultant() {
		return this.halfConsultant;
	}

	public void setHalfConsultant(String halfConsultant) {
		this.halfConsultant = halfConsultant;
	}

}