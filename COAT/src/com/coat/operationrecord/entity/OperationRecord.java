package com.coat.operationrecord.entity;

public class OperationRecord {

	private int id;
	private String username;
	private String ipaddress;
	private String httpaddress;
	private String modular;
	private String operation;
	private String date;
	private String result;
	private String status;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	public OperationRecord() {

	}

	public OperationRecord(int id, String username, String ipaddress,
			String httpaddress, String modular, String operation, String date,
			String result, String status, String remark1, String remark2,
			String remark3, String remark4) {
		super();
		this.id = id;
		this.username = username;
		this.ipaddress = ipaddress;
		this.httpaddress = httpaddress;
		this.modular = modular;
		this.operation = operation;
		this.date = date;
		this.result = result;
		this.status = status;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getModular() {
		return modular;
	}
	public void setModular(String modular) {
		this.modular = modular;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public String getHttpaddress() {
		return httpaddress;
	}

	public void setHttpaddress(String httpaddress) {
		this.httpaddress = httpaddress;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
