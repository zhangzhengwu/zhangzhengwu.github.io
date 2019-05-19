package entity;

/**
 * MedicalOutPutList entity. @author MyEclipse Persistence Tools
 */

public class MedicalOutPutList implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239123351664060872L;
	private Integer id;
	private String staffcode;
	private String staffname;
	private String createdate;
	private String creater;
	private String remark;
	private String sfyx;

	// Constructors

	/** default constructor */
	public MedicalOutPutList() {
	}

	/** full constructor */
	public MedicalOutPutList(String staffcode, String staffname,
			String createdate, String creater, String remark, String sfyx) {
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.createdate = createdate;
		this.creater = creater;
		this.remark = remark;
		this.sfyx = sfyx;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

}