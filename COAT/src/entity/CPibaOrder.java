package entity;

/**
 * CPibaOrder entity. @author MyEclipse Persistence Tools
 */

public class CPibaOrder implements java.io.Serializable {

	// Fields

	private String refno;
	private String staffcode;
	private String staffname;
	private String userType;
	private String location;
	private String creator;
	private String createDate;
	private String status;
	private String sfyx;
	private String remark;
	private String remark2;
	private String remark3;
	private String remark4;

	// Constructors

	/** default constructor */
	public CPibaOrder() {
	}

	/** minimal constructor */
	public CPibaOrder(String refno, String staffcode) {
		this.refno = refno;
		this.staffcode = staffcode;
	}

	/** full constructor */
	public CPibaOrder(String refno, String staffcode, String staffname,
			String userType, String location, String creator,
			String createDate, String status, String sfyx, String remark,
			String remark2, String remark3, String remark4) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.location = location;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
		this.remark = remark;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	}

	// Property accessors

	public String getRefno() {
		return this.refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
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

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return this.remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

}