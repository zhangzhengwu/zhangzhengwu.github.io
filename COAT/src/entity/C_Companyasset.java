package entity;

/**
 * CCompanyasset entity. @author MyEclipse Persistence Tools
 */

public class C_Companyasset implements java.io.Serializable {

	// Fields

	private String refno;
	private String staffcode;
	private String staffname;
	private String userType;
	private String location;
	private String eventName;
	private String collectionDate;
	private String returnDate;
	private String creator;
	private String createDate;
	private String status;
	private String sfyx;

	// Constructors

	/** default constructor */
	public C_Companyasset() {
	}

	/** full constructor */
	public C_Companyasset(String refno,String staffcode, String staffname, String userType,
			String location, String eventName, String collectionDate,
			String returnDate, String creator, String createDate,
			String status, String sfyx) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.location = location;
		this.eventName = eventName;
		this.collectionDate = collectionDate;
		this.returnDate = returnDate;
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

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCollectionDate() {
		return this.collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
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