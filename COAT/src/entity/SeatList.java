package entity;

/**
 * SeatList entity. @author MyEclipse Persistence Tools
 */

public class SeatList implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5789346703888866353L;
	private Integer id;
	private String staffcode;
	private String staffname;
	private String location;
	private String extensionno;
	private String floor;
	private String seatno;
	private String lockerno;
	private String deskDrawerno;
	private String pigenBoxno;
	private String status;
	private String updater;
	private String updateDate;
	private String ifhidden;
	private String remark;	
	private String remark1;

	// Constructors

	/** default constructor */
	public SeatList() {
	}

	public SeatList(String seatno) {
		super();
		this.seatno = seatno;
	}

	/** full constructor */
	public SeatList(String staffcode, String staffname, String location,
			String extensionno, String floor, String seatno, String lockerno,
			String deskDrawerno, String pigenBoxno, String status,
			String updater, String updateDate, String remark) {
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.location = location;
		this.extensionno = extensionno;
		this.floor = floor;
		this.seatno = seatno;
		this.lockerno = lockerno;
		this.deskDrawerno = deskDrawerno;
		this.pigenBoxno = pigenBoxno;
		this.status = status;
		this.updater = updater;
		this.updateDate = updateDate;
		this.remark = remark;
	}


	public SeatList(String staffcode, String staffname,
			String location, String extensionno, String floor, String seatno,
			String lockerno, String deskDrawerno, String pigenBoxno,
			String status, String updater, String updateDate, String remark,
			String remark1) {
		super();
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.location = location;
		this.extensionno = extensionno;
		this.floor = floor;
		this.seatno = seatno;
		this.lockerno = lockerno;
		this.deskDrawerno = deskDrawerno;
		this.pigenBoxno = pigenBoxno;
		this.status = status;
		this.updater = updater;
		this.updateDate = updateDate;
		this.remark = remark;
		this.remark1 = remark1;
	}
	
	
	public SeatList(String staffcode, String staffname, String location,
			String extensionno, String floor, String seatno, String lockerno,
			String deskDrawerno, String pigenBoxno, String status,
			String updater, String updateDate, String ifhidden, String remark,
			String remark1) {
		super();
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.location = location;
		this.extensionno = extensionno;
		this.floor = floor;
		this.seatno = seatno;
		this.lockerno = lockerno;
		this.deskDrawerno = deskDrawerno;
		this.pigenBoxno = pigenBoxno;
		this.status = status;
		this.updater = updater;
		this.updateDate = updateDate;
		this.ifhidden = ifhidden;
		this.remark = remark;
		this.remark1 = remark1;
	}

	public SeatList(String staffcode, String seatno,
			String pigenBoxno) {
		super();
		this.staffcode = staffcode;
		this.seatno = seatno;
		this.pigenBoxno = pigenBoxno;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIfhidden() {
		return ifhidden;
	}

	public void setIfhidden(String ifhidden) {
		this.ifhidden = ifhidden;
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

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

}