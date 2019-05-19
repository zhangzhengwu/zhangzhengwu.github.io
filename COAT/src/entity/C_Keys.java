package entity;

/**
 * CKeys entity. @author MyEclipse Persistence Tools
 */

public class C_Keys implements java.io.Serializable {

	// Fields

	private String refno;
	private String staffcode;
	private String staffname;
	private String userType;
	private String location;
	private String locker;
	private String lockerno;
	private String deskDrawer;
	private String deskDrawerno;
	private String pigenBox;
	private String pigenBoxno;
	private String mobileDrawer;
	private String mobileno;
	private String remarks;
	private String creator;
	private String createDate;
	private String status;
	private String sfyx;

	// Constructors

	/** default constructor */
	public C_Keys() {
	}

	/** full constructor */
	public C_Keys(String refno,String staffcode, String staffname, String userType,
			String location, String locker, String lockerno, String deskDrawer,
			String deskDrawerno, String pigenBox, String pigenBoxno,
			String mobileDrawer, String mobileno, String remarks,
			String creator, String createDate, String status, String sfyx) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.location = location;
		this.locker = locker;
		this.lockerno = lockerno;
		this.deskDrawer = deskDrawer;
		this.deskDrawerno = deskDrawerno;
		this.pigenBox = pigenBox;
		this.pigenBoxno = pigenBoxno;
		this.mobileDrawer = mobileDrawer;
		this.mobileno = mobileno;
		this.remarks = remarks;
		this.creator = creator;
		this.createDate = createDate;
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

	public String getLocker() {
		return this.locker;
	}

	public void setLocker(String locker) {
		this.locker = locker;
	}

	public String getLockerno() {
		return this.lockerno;
	}

	public void setLockerno(String lockerno) {
		this.lockerno = lockerno;
	}

	public String getDeskDrawer() {
		return this.deskDrawer;
	}

	public void setDeskDrawer(String deskDrawer) {
		this.deskDrawer = deskDrawer;
	}

	public String getDeskDrawerno() {
		return this.deskDrawerno;
	}

	public void setDeskDrawerno(String deskDrawerno) {
		this.deskDrawerno = deskDrawerno;
	}

	public String getPigenBox() {
		return this.pigenBox;
	}

	public void setPigenBox(String pigenBox) {
		this.pigenBox = pigenBox;
	}

	public String getPigenBoxno() {
		return this.pigenBoxno;
	}

	public void setPigenBoxno(String pigenBoxno) {
		this.pigenBoxno = pigenBoxno;
	}

	public String getMobileDrawer() {
		return this.mobileDrawer;
	}

	public void setMobileDrawer(String mobileDrawer) {
		this.mobileDrawer = mobileDrawer;
	}

	public String getMobileno() {
		return this.mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}