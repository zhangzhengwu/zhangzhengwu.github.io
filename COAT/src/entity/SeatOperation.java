package entity;

/**
 * SeatOperation entity. @author MyEclipse Persistence Tools
 */

public class SeatOperation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1690897252507679894L;
	private Integer id;
	private String seatno;
	private String staffcode;
	private String staffname;
	private String extensionno;
	private String floor;
	private String location;
	private String pigenboxno;
	private String deskDrawerno;
	private String lockerno;
	private String ifhidden;
	private String ifad;
	private String operationname;
	private String operationdate;
	private String reason;

	// Constructors

	/** default constructor */
	public SeatOperation() {
	}

	/** full constructor */
	public SeatOperation(String seatno, String staffcode, String staffname,
			String extensionno, String floor, String location,
			String pigenboxno, String deskDrawerno, String lockerno,
			String ifhidden, String ifad, String operationname,
			String operationdate, String reason) {
		this.seatno = seatno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.extensionno = extensionno;
		this.floor = floor;
		this.location = location;
		this.pigenboxno = pigenboxno;
		this.deskDrawerno = deskDrawerno;
		this.lockerno = lockerno;
		this.ifhidden = ifhidden;
		this.ifad = ifad;
		this.operationname = operationname;
		this.operationdate = operationdate;
		this.reason = reason;
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

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPigenboxno() {
		return this.pigenboxno;
	}

	public void setPigenboxno(String pigenboxno) {
		this.pigenboxno = pigenboxno;
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

	public String getIfhidden() {
		return this.ifhidden;
	}

	public void setIfhidden(String ifhidden) {
		this.ifhidden = ifhidden;
	}

	public String getIfad() {
		return this.ifad;
	}

	public void setIfad(String ifad) {
		this.ifad = ifad;
	}

	public String getOperationname() {
		return this.operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	public String getOperationdate() {
		return this.operationdate;
	}

	public void setOperationdate(String operationdate) {
		this.operationdate = operationdate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}