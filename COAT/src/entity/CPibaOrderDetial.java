package entity;

/**
 * CPibaOrderDetial entity. @author MyEclipse Persistence Tools
 */

public class CPibaOrderDetial implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refno;
	private String staffcode;
	private String type;
	private String bookCname;
	private String bookEname;
	private String bookNum;
	private String language;
	private String remark;
	private String creator;
	private String createDate;

	// Constructors

	/** default constructor */
	public CPibaOrderDetial() {
	}

	/** minimal constructor */
	public CPibaOrderDetial(String refno, String type, String bookNum,
			String language, String remark) {
		this.refno = refno;
		this.type = type;
		this.bookNum = bookNum;
		this.language = language;
		this.remark = remark;
	}

	/** full constructor */
	public CPibaOrderDetial(String refno, String staffcode, String type,
			String bookCname, String bookEname, String bookNum,
			String language, String remark, String creator, String createDate) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.type = type;
		this.bookCname = bookCname;
		this.bookEname = bookEname;
		this.bookNum = bookNum;
		this.language = language;
		this.remark = remark;
		this.creator = creator;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBookCname() {
		return this.bookCname;
	}

	public void setBookCname(String bookCname) {
		this.bookCname = bookCname;
	}

	public String getBookEname() {
		return this.bookEname;
	}

	public void setBookEname(String bookEname) {
		this.bookEname = bookEname;
	}

	public String getBookNum() {
		return this.bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

}