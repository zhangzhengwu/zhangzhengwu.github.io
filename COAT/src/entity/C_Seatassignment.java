package entity;

/**
 * CSeatassignment entity. @author MyEclipse Persistence Tools
 */

public class C_Seatassignment implements java.io.Serializable {

	// Fields

	private String refno;
	private String staffcode;
	private String staffname;
	private String location;
	private String extensionno;
	private String floor;
	private String seatno;
	private String lockerno;
	private String deskDrawerno;
	private String pigenBoxno;
	private String remark;
	private String creator;
	private String creatDate;
	private String status;
	private String sfyx;

	// Constructors

	/** default constructor */
	public C_Seatassignment() {
	}

	/** full constructor */
	public C_Seatassignment(String refno,String staffcode, String staffname, String location,
			String extensionno, String floor, String seatno, String lockerno,
			String deskDrawerno, String pigenBoxno, String remark,
			String creator, String creatDate, String status, String sfyx) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.location = location;
		this.extensionno = extensionno;
		this.floor = floor;
		this.seatno = seatno;
		this.lockerno = lockerno;
		this.deskDrawerno = deskDrawerno;
		this.pigenBoxno = pigenBoxno;
		this.remark = remark;
		this.creator = creator;
		this.creatDate = creatDate;
		this.status = status;
		this.sfyx = sfyx;
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getExtensionno() {
		return this.extensionno;
	}

	public void setExtensionno(String extensionno) {
		this.extensionno = extensionno;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getSeatno() {
		return this.seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}

	public String getLockerno() {
		return this.lockerno;
	}

	public void setLockerno(String lockerno) {
		this.lockerno = lockerno;
	}

	public String getDeskDrawerno() {
		return this.deskDrawerno;
	}

	public void setDeskDrawerno(String deskDrawerno) {
		this.deskDrawerno = deskDrawerno;
	}

	public String getPigenBoxno() {
		return this.pigenBoxno;
	}

	public void setPigenBoxno(String pigenBoxno) {
		this.pigenBoxno = pigenBoxno;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
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

}