package com.coat.notice.entity;

public class Notice {
	private int id;
	private String type;//消息类型 All，Group，Personal
	private String subject;//消息题目
	private String content;//消息内容
	private String attr;//附件
	private String recipient;//消息接收人
	private String roles;
	private String company;//接收消息的公司
	private String startdate; //消息提示开始时间
	private String enddate; //消息提示结束时间
	private String creator; //消息创建人
	private String createdate;//消息创建时间
	private String status;
	private String remark;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	
	
	private String type1;
	private String noticeid;
	private String username;
	private String ifread;
	
	
	public Notice() {
		super();
	}
	public Notice(int id, String type, String subject, String content,
			String attr, String recipient, String roles, String company, String startdate,
			String enddate, String creator, String createdate, String status,
			String remark, String remark1, String remark2, String remark3,
			String remark4) {
		super();
		this.id = id;
		this.type = type;
		this.subject = subject;
		this.content = content;
		this.attr = attr;
		this.recipient = recipient;
		this.roles = roles;
		this.company = company;
		this.startdate = startdate;
		this.enddate = enddate;
		this.creator = creator;
		this.createdate = createdate;
		this.status = status;
		this.remark = remark;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	}
	
	public Notice(int id,String subject,String company,String createdate,String creator,String type) {
		super();
		this.id = id;
		this.subject = subject;
		this.company = company;
		this.createdate = createdate;
		this.creator = creator;
		this.type = type;
	}
	
	
	public Notice(int id,String type, String subject, String content, String attr,
			String recipient, String roles, String company, String startdate, String enddate,
			String creator, String createdate, String status,String ifread) {
		super();
		this.id = id;
		this.type = type;
		this.subject = subject;
		this.content = content;
		this.attr = attr;
		this.recipient = recipient;
		this.roles = roles;
		this.company = company;
		this.startdate = startdate;
		this.enddate = enddate;
		this.creator = creator;
		this.createdate = createdate;
		this.status = status;
		this.ifread = ifread;
	}
	
	
	public Notice(int id, String type, String subject, String content,
			String attr, String recipient, String roles, String company,
			String startdate, String enddate, String creator,
			String createdate, String status, String type1, String noticeid,
			String username, String ifread) {
		super();
		this.id = id;
		this.type = type;
		this.subject = subject;
		this.content = content;
		this.attr = attr;
		this.recipient = recipient;
		this.roles = roles;
		this.company = company;
		this.startdate = startdate;
		this.enddate = enddate;
		this.creator = creator;
		this.createdate = createdate;
		this.status = status;
		this.type1 = type1;
		this.noticeid = noticeid;
		this.username = username;
		this.ifread = ifread;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIfread() {
		return ifread;
	}
	public void setIfread(String ifread) {
		this.ifread = ifread;
	}
	
}
