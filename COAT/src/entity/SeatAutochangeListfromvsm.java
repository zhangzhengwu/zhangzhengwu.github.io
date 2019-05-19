package entity;

/**
 * SeatAutochangeListfromvsm entity. @author MyEclipse Persistence Tools
 */

public class SeatAutochangeListfromvsm implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8362696694053883957L;
	private Integer id;
	private String staffcode;
	private String staffname;
	private String leadercode;
	private String leadername;
	private Integer changetype;
	private Integer changeflag;
	private String createdate;
	private String sfyx;
	private String remark;

	// Constructors

	/** default constructor */
	public SeatAutochangeListfromvsm() {
	}

	/** minimal constructor */
	public SeatAutochangeListfromvsm(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SeatAutochangeListfromvsm(Integer id, String staffcode,
			String staffname, String leadercode, String leadername,
			Integer changetype, Integer changeflag, String createdate,
			String sfyx, String remark) {
		this.id = id;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.leadercode = leadercode;
		this.leadername = leadername;
		this.changetype = changetype;
		this.changeflag = changeflag;
		this.createdate = createdate;
		this.sfyx = sfyx;
		this.remark = remark;
	}

	// Property accessors

	public SeatAutochangeListfromvsm(String staffcode, String staffname,
			String leadercode, String leadername, Integer changetype,
			Integer changeflag, String createdate, String sfyx, String remark) {
		super();
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.leadercode = leadercode;
		this.leadername = leadername;
		this.changetype = changetype;
		this.changeflag = changeflag;
		this.createdate = createdate;
		this.sfyx = sfyx;
		this.remark = remark;
	}

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

	public String getLeadercode() {
		return this.leadercode;
	}

	public void setLeadercode(String leadercode) {
		this.leadercode = leadercode;
	}

	public String getLeadername() {
		return this.leadername;
	}

	public void setLeadername(String leadername) {
		this.leadername = leadername;
	}

	public Integer getChangetype() {
		return this.changetype;
	}

	public void setChangetype(Integer changetype) {
		this.changetype = changetype;
	}

	public Integer getChangeflag() {
		return this.changeflag;
	}

	public void setChangeflag(Integer changeflag) {
		this.changeflag = changeflag;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}