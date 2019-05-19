package entity;

/**
 * CPibaBook entity. @author MyEclipse Persistence Tools
 */

public class CPibaBook implements java.io.Serializable {

	// Fields

	private Integer bookNo;
	private String type;
	private String bookCname;
	private String bookEname;
	private String language;
	private Integer num;
	private String creator;
	private String createDate;

	// Constructors

	/** default constructor */
	public CPibaBook() {
	}

	/** minimal constructor */
	public CPibaBook(Integer num) {
		this.num = num;
	}

	/** full constructor */
	public CPibaBook(String type, String bookCname, String bookEname,
			String language, Integer num, String creator, String createDate) {
		this.type = type;
		this.bookCname = bookCname;
		this.bookEname = bookEname;
		this.language = language;
		this.num = num;
		this.creator = creator;
		this.createDate = createDate;
	}

	
	public CPibaBook(Integer bookNo, String type, String bookCname,
			String bookEname, String language, Integer num, String creator,
			String createDate) {
		super();
		this.bookNo = bookNo;
		this.type = type;
		this.bookCname = bookCname;
		this.bookEname = bookEname;
		this.language = language;
		this.num = num;
		this.creator = creator;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getBookNo() {
		return this.bookNo;
	}

	public void setBookNo(Integer bookNo) {
		this.bookNo = bookNo;
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

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
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