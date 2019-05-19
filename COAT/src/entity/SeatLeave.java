package entity;

/**
 * SeatLeave entity. @author MyEclipse Persistence Tools
 */

public class SeatLeave implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String seatno;
	private String staffcode;
	private String staffname;
	private String extensionno;
	private String floor;
	private String location;
	private String pigenboxno;
	private String creater;
	private String createdate;

	// Constructors

	/** default constructor */
	public SeatLeave() {
	}

	/** full constructor */
	public SeatLeave(String seatno, String staffcode, String staffname,
			String extensionno, String floor, String location,
			String pigenboxno, String creater, String createdate) {
		this.seatno = seatno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.extensionno = extensionno;
		this.floor = floor;
		this.location = location;
		this.pigenboxno = pigenboxno;
		this.creater = creater;
		this.createdate = createdate;
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

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}