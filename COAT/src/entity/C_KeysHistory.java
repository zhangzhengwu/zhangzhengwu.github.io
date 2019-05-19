package entity;

/**
 * CKeys entity. @author MyEclipse Persistence Tools
 */

public class C_KeysHistory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String staffcode;
	private String staffname;
	private String lockerno;
	private String deskDrawerno;
	private String pigenBoxno;
	private String remark;

	// Constructors

	/** default constructor */
	public C_KeysHistory() {
	}

	/** full constructor */
	public C_KeysHistory(int id,String staffcode, String staffname,
			String lockerno,String deskDrawerno, String pigenBoxno,String remark) {
		this.id = id;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.lockerno = lockerno;
		this.deskDrawerno = deskDrawerno;
		this.pigenBoxno = pigenBoxno;
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaffcode() {
		return staffcode;
	}

	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getLockerno() {
		return lockerno;
	}

	public void setLockerno(String lockerno) {
		this.lockerno = lockerno;
	}

	public String getPigenBoxno() {
		return pigenBoxno;
	}

	public void setPigenBoxno(String pigenBoxno) {
		this.pigenBoxno = pigenBoxno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeskDrawerno() {
		return deskDrawerno;
	}

	public void setDeskDrawerno(String deskDrawerno) {
		this.deskDrawerno = deskDrawerno;
	}
 }