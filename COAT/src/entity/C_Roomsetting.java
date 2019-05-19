package entity;

/**
 * CRoomsetting entity. @author MyEclipse Persistence Tools
 */

public class C_Roomsetting implements java.io.Serializable {

	// Fields

	private String refno;
	private String staffcode;
	private String staffname;
	private String userType;
	private String eventname;
	private String eventDate;
	private String startTime;
	private String endTime;
	private String convoy;
	private String cp3;
	private String remark;
	private String creator;
	private String createDate;
	private String status;
	private String sfyx;

	// Constructors

	/** default constructor */
	public C_Roomsetting() {
	}

	/** full constructor */
	public C_Roomsetting(String staffcode, String staffname, String userType,
			String eventname, String eventDate, String startTime,
			String endTime, String convoy, String cp3, String remark,
			String creator, String createDate, String status, String sfyx) {
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.eventname = eventname;
		this.eventDate = eventDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.convoy = convoy;
		this.cp3 = cp3;
		this.remark = remark;
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

	public String getEventname() {
		return this.eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getConvoy() {
		return this.convoy;
	}

	public void setConvoy(String convoy) {
		this.convoy = convoy;
	}

	public String getCp3() {
		return this.cp3;
	}

	public void setCp3(String cp3) {
		this.cp3 = cp3;
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

	public C_Roomsetting(String refno, String staffcode, String staffname,
			String userType, String eventname, String eventDate,
			String startTime, String endTime, String convoy, String cp3,
			String remark, String creator, String createDate, String status,
			String sfyx) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.eventname = eventname;
		this.eventDate = eventDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.convoy = convoy;
		this.cp3 = cp3;
		this.remark = remark;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
	}
	
}