package com.coat.pickuprecord.entity;

/**
 * PRecordList entity. @author MyEclipse Persistence Tools
 */

public class PRecordList implements java.io.Serializable {

	// Fields

	private Integer refno;
	private String staffcode;
	private String clientName;
	private String location;
	private String documentId;
	private String documentType;
	private String scanDate;
	private String status;
	private String result;
	private String creator;
	private String createdate;
	private String sfyx;
	private String sender;
	
	private String upd_name;
	private String upd_date;
	private String extension;
	// Constructors

	/** default constructor */
	public PRecordList() {
	}

	/** full constructor */
	public PRecordList(String staffcode, String clientName, String location,String sender,
			String documentType, String scanDate, String status, String result,
			String creator, String createdate,String sfyx) {
		this.staffcode = staffcode;
		this.clientName = clientName;
		this.location = location;
		this.sender = sender;
		this.documentType = documentType;
		this.scanDate = scanDate;
		this.status = status;
		this.result = result;
		this.creator = creator;
		this.createdate = createdate;
		this.sfyx = sfyx;
	}
	public PRecordList(String staffcode, String clientName, String location,String sender,String documentId,
			String documentType, String scanDate, String status, String result,
			String creator, String createdate,String sfyx) {
		this.staffcode = staffcode;
		this.clientName = clientName;
		this.location = location;
		this.sender = sender;
		this.documentId = documentId;
		this.documentType = documentType;
		this.scanDate = scanDate;
		this.status = status;
		this.result = result;
		this.creator = creator;
		this.createdate = createdate;
		this.sfyx = sfyx;
	}
	
	/** full constructor */
	public PRecordList(int refno,String staffcode, String clientName, String location,String sender,String documentId,
			String documentType, String scanDate, String status, String result,
			String creator, String createdate,String sfyx,String extension) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.clientName = clientName;
		this.location = location;
		this.sender = sender;
		this.documentId = documentId;
		this.documentType = documentType;
		this.scanDate = scanDate;
		this.status = status;
		this.result = result;
		this.creator = creator;
		this.createdate = createdate;
		this.sfyx = sfyx;
		this.extension = extension;
	}

	// Property accessors

	public Integer getRefno() {
		return this.refno;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUpd_name() {
		return upd_name;
	}

	public void setUpd_name(String updName) {
		upd_name = updName;
	}

	public String getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(String updDate) {
		upd_date = updDate;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
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

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getScanDate() {
		return this.scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
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