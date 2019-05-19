package entity;

/**
 * CPibaSigndetial entity. @author MyEclipse Persistence Tools
 */

public class CPibaSigndetial implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refno;
	private String staffcode;
	private String signcode;
	private String signname;
	private String creator;
	private String createdate;
	private String remark;

	// Constructors

	/** default constructor */
	public CPibaSigndetial() {
	}

	/** minimal constructor */
	public CPibaSigndetial(String refno, String staffcode, String signname) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.signname = signname;
	}

	/** full constructor */
	public CPibaSigndetial(String refno, String staffcode, String signcode,
			String signname, String creator, String createdate, String remark) {
		this.refno = refno;
		this.staffcode = staffcode;
		this.signcode = signcode;
		this.signname = signname;
		this.creator = creator;
		this.createdate = createdate;
		this.remark = remark;
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

	public String getSigncode() {
		return this.signcode;
	}

	public void setSigncode(String signcode) {
		this.signcode = signcode;
	}

	public String getSignname() {
		return this.signname;
	}

	public void setSignname(String signname) {
		this.signname = signname;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}