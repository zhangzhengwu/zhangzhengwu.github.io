package entity;

public class C_Epaymentt_Order {

	private String refno;
	private String staffcode;
	private String staffname;
	private String usertype;
	private String location;
	private String createdate;
	private String creater;
	private String status;
	private String sfyx;
	private String remark;
	
	public C_Epaymentt_Order(){
		
	}
	
	public C_Epaymentt_Order(String refno, String staffcode, String staffname,
			String usertype, String location, String createdate,
			String creater, String status, String sfyx, String remark) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.usertype = usertype;
		this.location = location;
		this.createdate = createdate;
		this.creater = creater;
		this.status = status;
		this.sfyx = sfyx;
		this.remark = remark;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	
	
}
