package com.coat.pickuprecord.entity;

/**
 * PRecordOrder entity. @author MyEclipse Persistence Tools
 */

public class PRecordOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refno;
	private Integer listId;
	private String staffcode;
	private String signcode;
	private String extension;
	private String password;
	private String creator;
	private String createdate;
	
	private String clientName;
	private String sender;
	private String documentType;
	private String documentId;
	private String scanDate;

	// Constructors
	public PRecordOrder(Integer listId,String staffcode, String signcode, String clientName,
			String sender,String documentId, String documentType, String scanDate) {
		this.listId = listId;
		this.staffcode = staffcode;
		this.signcode = signcode;
		this.clientName = clientName;
		this.sender = sender;
		this.documentId = documentId;
		this.documentType = documentType;
		this.scanDate = scanDate;
	}
	
	

	public String getDocumentId() {
		return documentId;
	}



	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}



	/** default constructor */
	public PRecordOrder() {
	}

	/** minimal constructor */
	public PRecordOrder(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public PRecordOrder(Integer id, String refno, Integer listId,
			String staffcode, String signcode, String extension,
			String password, String creator, String createdate) {
		this.id = id;
		this.refno = refno;
		this.listId = listId;
		this.staffcode = staffcode;
		this.signcode = signcode;
		this.extension = extension;
		this.password = password;
		this.creator = creator;
		this.createdate = createdate;
	}
	
	public PRecordOrder(String refno, Integer listId,
			String staffcode, String signcode, String extension,
			String password, String creator, String createdate) {
		this.refno = refno;
		this.listId = listId;
		this.staffcode = staffcode;
		this.signcode = signcode;
		this.extension = extension;
		this.password = password;
		this.creator = creator;
		this.createdate = createdate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRefno() {
		return this.refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public Integer getListId() {
		return this.listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public String getStaffcode() {
		return this.staffcode;
	}

	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}

	public String getSigncode() {
		return this.signcode;
	}

	public void setSigncode(String signcode) {
		this.signcode = signcode;
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