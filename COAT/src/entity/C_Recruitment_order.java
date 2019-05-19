package entity;

public class C_Recruitment_order {
 private String refno;
 private String staffcode;
 private String staffname;
 private String usertype;
 private String position;
 private String contactperson;
 private String contactemail;
 private String chargecode;
 private String chargename;
 private String date;
 private String createdate;
 private String creater;
 private String status;
 private String sfyx;
 private String remark;
 private String filterdate;
 
 private String mediacode;
 private String medianame;
 private String paymentAount;
 private String Handleder;
 private String mediatype;
 private String price;
 
 public C_Recruitment_order(){
	 
 }
 
public C_Recruitment_order(String refno, String staffcode, String staffname,
		String usertype, String position, String contactperson,
		String contactemail, String chargecode, String chargename, String date,
		String createdate, String creater, String status, String sfyx,
		String remark) {
	super();
	this.refno = refno;
	this.staffcode = staffcode;
	this.staffname = staffname;
	this.usertype = usertype;
	this.position = position;
	this.contactperson = contactperson;
	this.contactemail = contactemail;
	this.chargecode = chargecode;
	this.chargename = chargename;
	this.date = date;
	this.createdate = createdate;
	this.creater = creater;
	this.status = status;
	this.sfyx = sfyx;
	this.remark = remark;
}

public C_Recruitment_order(String refno, String staffcode, String staffname,
		String usertype, String position, String contactperson,
		String contactemail, String chargecode, String chargename, String date,
		String createdate, String creater, String status, String sfyx,
		String remark, String filterdate) {
	super();
	this.refno = refno;
	this.staffcode = staffcode;
	this.staffname = staffname;
	this.usertype = usertype;
	this.position = position;
	this.contactperson = contactperson;
	this.contactemail = contactemail;
	this.chargecode = chargecode;
	this.chargename = chargename;
	this.date = date;
	this.createdate = createdate;
	this.creater = creater;
	this.status = status;
	this.sfyx = sfyx;
	this.remark = remark;
	this.filterdate = filterdate;
}

public String getRefno() {
	return refno;
}
public void setRefno(String refno) {
	this.refno = refno;
}
public String getStaffcode() {
	return staffcode;
}
public void setStaffcode(String staffcode) {
	this.staffcode = staffcode;
}
public String getStaffname() {
	return staffname;
}
public void setStaffname(String staffname) {
	this.staffname = staffname;
}
public String getUsertype() {
	return usertype;
}
public void setUsertype(String usertype) {
	this.usertype = usertype;
}
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public String getContactperson() {
	return contactperson;
}
public void setContactperson(String contactperson) {
	this.contactperson = contactperson;
}
public String getContactemail() {
	return contactemail;
}
public void setContactemail(String contactemail) {
	this.contactemail = contactemail;
}
public String getChargecode() {
	return chargecode;
}
public void setChargecode(String chargecode) {
	this.chargecode = chargecode;
}
public String getChargename() {
	return chargename;
}
public void setChargename(String chargename) {
	this.chargename = chargename;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getCreatedate() {
	return createdate;
}
public void setCreatedate(String createdate) {
	this.createdate = createdate;
}
public String getCreater() {
	return creater;
}
public void setCreater(String creater) {
	this.creater = creater;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getSfyx() {
	return sfyx;
}
public void setSfyx(String sfyx) {
	this.sfyx = sfyx;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

public String getFilterdate() {
	return filterdate;
}

public void setFilterdate(String filterdate) {
	this.filterdate = filterdate;
}

public String getMediacode() {
	return mediacode;
}

public void setMediacode(String mediacode) {
	this.mediacode = mediacode;
}

public String getMedianame() {
	return medianame;
}

public void setMedianame(String medianame) {
	this.medianame = medianame;
}

public String getPaymentAount() {
	return paymentAount;
}

public void setPaymentAount(String paymentAount) {
	this.paymentAount = paymentAount;
}

public String getHandleder() {
	return Handleder;
}

public void setHandleder(String handleder) {
	Handleder = handleder;
}

public String getMediatype() {
	return mediatype;
}

public void setMediatype(String mediatype) {
	this.mediatype = mediatype;
}

public String getPrice() {
	return price;
}

public void setPrice(String price) {
	this.price = price;
}

public C_Recruitment_order(String refno, String staffcode, String staffname,
		String usertype, String position, String contactperson,
		String contactemail, String chargecode, String chargename, String date,
		String createdate, String creater, String status, String sfyx,
		String remark, String filterdate, String mediacode, String medianame,
		String paymentAount, String handleder, String mediatype, String price) {
	super();
	this.refno = refno;
	this.staffcode = staffcode;
	this.staffname = staffname;
	this.usertype = usertype;
	this.position = position;
	this.contactperson = contactperson;
	this.contactemail = contactemail;
	this.chargecode = chargecode;
	this.chargename = chargename;
	this.date = date;
	this.createdate = createdate;
	this.creater = creater;
	this.status = status;
	this.sfyx = sfyx;
	this.remark = remark;
	this.filterdate = filterdate;
	this.mediacode = mediacode;
	this.medianame = medianame;
	this.paymentAount = paymentAount;
	Handleder = handleder;
	this.mediatype = mediatype;
	this.price = price;
}


 
 
 
}
