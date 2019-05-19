package entity;

/**
 * CAttachment entity. @author MyEclipse Persistence Tools
 */

public class CAttachment implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refno;
	private String staffcode;
	private String staffname;
	private String attachmentName;
	private String attachmentPath;
	private String creator;
	private String createDate;
	private String sfyx;
	private String item;

	// Constructors

	/** default constructor */
	public CAttachment() {
	}

	public CAttachment(String attachmentPath ) {
		this.attachmentPath=attachmentPath;
	}
	/** minimal constructor */
	public CAttachment(String refno, String staffcode) {
		this.refno = refno;
		this.staffcode = staffcode;
	}

	/** full constructor */
	public CAttachment(String refno, String staffcode, String staffname,
			String attachmentName, String attachmentPath, String creator,
			String createDate, String sfyx, String item) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.attachmentName = attachmentName;
		this.attachmentPath = attachmentPath;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
		this.item = item;
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

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getAttachmentName() {
		return this.attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentPath() {
		return this.attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
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

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}