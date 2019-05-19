package entity;

/**
 * SeatAutochangeApply entity. @author MyEclipse Persistence Tools
 */

public class SeatAutochangeApply implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 558060821642373681L;
	private Integer id;
	private String refno;
	private String staffcode;
	private String staffname;
	private String seatnobefore;
	private String seatno;
	private String leadercode;
	private String leadername;
	private String createdate;
	private String updatedate;
	private Integer flag;
	private String status;
	private String sfyx;
	private String remarkA;
	private String remarkB;
	private String remarkC;

	// Constructors

	/** default constructor */
	public SeatAutochangeApply() {
	}

	/** minimal constructor */
	public SeatAutochangeApply(Integer id) {
		this.id = id;
	}

	public SeatAutochangeApply(String refno, String staffcode,
			String staffname, String seatnobefore, String seatno,
			String leadercode, String leadername, String createdate,
			String updatedate, Integer flag, String status, String sfyx,
			String remarkA, String remarkB, String remarkC) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.seatnobefore = seatnobefore;
		this.seatno = seatno;
		this.leadercode = leadercode;
		this.leadername = leadername;
		this.createdate = createdate;
		this.updatedate = updatedate;
		this.flag = flag;
		this.status = status;
		this.sfyx = sfyx;
		this.remarkA = remarkA;
		this.remarkB = remarkB;
		this.remarkC = remarkC;
	}

	/** full constructor */
	public SeatAutochangeApply(Integer id, String refno, String staffcode,
			String staffname, String seatnobefore, String seatno,
			String leadercode, String leadername, String createdate,
			String updatedate, Integer flag, String status, String sfyx,
			String remarkA, String remarkB, String remarkC) {
		this.id = id;
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.seatnobefore = seatnobefore;
		this.seatno = seatno;
		this.leadercode = leadercode;
		this.leadername = leadername;
		this.createdate = createdate;
		this.updatedate = updatedate;
		this.flag = flag;
		this.status = status;
		this.sfyx = sfyx;
		this.remarkA = remarkA;
		this.remarkB = remarkB;
		this.remarkC = remarkC;
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

	public String getSeatnobefore() {
		return this.seatnobefore;
	}

	public void setSeatnobefore(String seatnobefore) {
		this.seatnobefore = seatnobefore;
	}

	public String getSeatno() {
		return this.seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
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

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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

	public String getRemarkA() {
		return this.remarkA;
	}

	public void setRemarkA(String remarkA) {
		this.remarkA = remarkA;
	}

	public String getRemarkB() {
		return this.remarkB;
	}

	public void setRemarkB(String remarkB) {
		this.remarkB = remarkB;
	}

	public String getRemarkC() {
		return this.remarkC;
	}

	public void setRemarkC(String remarkC) {
		this.remarkC = remarkC;
	}

}