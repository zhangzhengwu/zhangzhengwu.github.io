package entity;

/**
 * SeatSpeciallist entity. @author MyEclipse Persistence Tools
 */

public class SeatSpeciallist implements java.io.Serializable {

	// Fields

	private Integer id;
	private String seatno;
	private String staffcode;
	private String staffname;
	private String extensionno;
	private String location;
	private String floor;
	private String deskDrawerno;
	private String lockerno;
	private String pigenBoxno;
	private String status;
	private String updater;
	private String updateDate;
	private String remark;

	// Constructors

	/** default constructor */
	public SeatSpeciallist() {
	}

	/** full constructor */
	public SeatSpeciallist(String seatno, String staffcode, String staffname,
			String extensionno, String location, String floor,
			String deskDrawerno, String lockerno, String pigenBoxno,
			String status, String updater, String updateDate, String remark) {
		this.seatno = seatno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.extensionno = extensionno;
		this.location = location;
		this.floor = floor;
		this.deskDrawerno = deskDrawerno;
		this.lockerno = lockerno;
		this.pigenBoxno = pigenBoxno;
		this.status = status;
		this.updater = updater;
		this.updateDate = updateDate;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeatno() {
		return this.seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
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

	public String getExtensionno() {
		return this.extensionno;
	}

	public void setExtensionno(String extensionno) {
		this.extensionno = extensionno;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDeskDrawerno() {
		return this.deskDrawerno;
	}

	public void setDeskDrawerno(String deskDrawerno) {
		this.deskDrawerno = deskDrawerno;
	}

	public String getLockerno() {
		return this.lockerno;
	}

	public void setLockerno(String lockerno) {
		this.lockerno = lockerno;
	}

	public String getPigenBoxno() {
		return this.pigenBoxno;
	}

	public void setPigenBoxno(String pigenBoxno) {
		this.pigenBoxno = pigenBoxno;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdater() {
		return this.updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}