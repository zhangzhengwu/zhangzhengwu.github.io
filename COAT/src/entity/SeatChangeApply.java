package entity;

/**
 * SeatChangeApply entity. @author MyEclipse Persistence Tools
 */

public class SeatChangeApply implements java.io.Serializable {

	// Fields

	private Integer id;
	private String refno;
	private String staffcodea;
	private String seatnoa;
	private String pigeonboxa;
	private String staffcodeb;
	private String seatnob;
	private String pigeonboxb;
	private String seatnoc;
	private String staffcodec;
	private String pigeonboxc;
	private String seatnod;
	private String staffcoded;
	private String pigeonboxd;
	private String extensiond;
	private String extensionc;
	private String extensionb;
	private String extensiona;
	private Integer checkflag;
	private Integer extensionflag;
	private String createdate;
	private String status;
	private String remark;
	private String remarkall;
	private String remark3;
	private String remark1;
	private String remark2;
	private String sfyx;

	// Constructors

	/** default constructor */
	public SeatChangeApply() {
	}

	/** full constructor */
	public SeatChangeApply(String refno, String staffcodea, String seatnoa,
			String pigeonboxa, String staffcodeb, String seatnob,
			String pigeonboxb, String seatnoc, String staffcodec,
			String pigeonboxc, String seatnod, String staffcoded,
			String pigeonboxd, String extensiond, String extensionc,
			String extensionb, String extensiona, Integer checkflag,
			Integer extensionflag, String createdate, String status,
			String remark, String remarkall, String remark3, String remark1,
			String remark2, String sfyx) {
		this.refno = refno;
		this.staffcodea = staffcodea;
		this.seatnoa = seatnoa;
		this.pigeonboxa = pigeonboxa;
		this.staffcodeb = staffcodeb;
		this.seatnob = seatnob;
		this.pigeonboxb = pigeonboxb;
		this.seatnoc = seatnoc;
		this.staffcodec = staffcodec;
		this.pigeonboxc = pigeonboxc;
		this.seatnod = seatnod;
		this.staffcoded = staffcoded;
		this.pigeonboxd = pigeonboxd;
		this.extensiond = extensiond;
		this.extensionc = extensionc;
		this.extensionb = extensionb;
		this.extensiona = extensiona;
		this.checkflag = checkflag;
		this.extensionflag = extensionflag;
		this.createdate = createdate;
		this.status = status;
		this.remark = remark;
		this.remarkall = remarkall;
		this.remark3 = remark3;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.sfyx = sfyx;
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

	public String getStaffcodea() {
		return this.staffcodea;
	}

	public void setStaffcodea(String staffcodea) {
		this.staffcodea = staffcodea;
	}

	public String getSeatnoa() {
		return this.seatnoa;
	}

	public void setSeatnoa(String seatnoa) {
		this.seatnoa = seatnoa;
	}

	public String getPigeonboxa() {
		return this.pigeonboxa;
	}

	public void setPigeonboxa(String pigeonboxa) {
		this.pigeonboxa = pigeonboxa;
	}

	public String getStaffcodeb() {
		return this.staffcodeb;
	}

	public void setStaffcodeb(String staffcodeb) {
		this.staffcodeb = staffcodeb;
	}

	public String getSeatnob() {
		return this.seatnob;
	}

	public void setSeatnob(String seatnob) {
		this.seatnob = seatnob;
	}

	public String getPigeonboxb() {
		return this.pigeonboxb;
	}

	public void setPigeonboxb(String pigeonboxb) {
		this.pigeonboxb = pigeonboxb;
	}

	public String getSeatnoc() {
		return this.seatnoc;
	}

	public void setSeatnoc(String seatnoc) {
		this.seatnoc = seatnoc;
	}

	public String getStaffcodec() {
		return this.staffcodec;
	}

	public void setStaffcodec(String staffcodec) {
		this.staffcodec = staffcodec;
	}

	public String getPigeonboxc() {
		return this.pigeonboxc;
	}

	public void setPigeonboxc(String pigeonboxc) {
		this.pigeonboxc = pigeonboxc;
	}

	public String getSeatnod() {
		return this.seatnod;
	}

	public void setSeatnod(String seatnod) {
		this.seatnod = seatnod;
	}

	public String getStaffcoded() {
		return this.staffcoded;
	}

	public void setStaffcoded(String staffcoded) {
		this.staffcoded = staffcoded;
	}

	public String getPigeonboxd() {
		return this.pigeonboxd;
	}

	public void setPigeonboxd(String pigeonboxd) {
		this.pigeonboxd = pigeonboxd;
	}

	public String getExtensiond() {
		return this.extensiond;
	}

	public void setExtensiond(String extensiond) {
		this.extensiond = extensiond;
	}

	public String getExtensionc() {
		return this.extensionc;
	}

	public void setExtensionc(String extensionc) {
		this.extensionc = extensionc;
	}

	public String getExtensionb() {
		return this.extensionb;
	}

	public void setExtensionb(String extensionb) {
		this.extensionb = extensionb;
	}

	public String getExtensiona() {
		return this.extensiona;
	}

	public void setExtensiona(String extensiona) {
		this.extensiona = extensiona;
	}

	public Integer getCheckflag() {
		return this.checkflag;
	}

	public void setCheckflag(Integer checkflag) {
		this.checkflag = checkflag;
	}

	public Integer getExtensionflag() {
		return this.extensionflag;
	}

	public void setExtensionflag(Integer extensionflag) {
		this.extensionflag = extensionflag;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemarkall() {
		return this.remarkall;
	}

	public void setRemarkall(String remarkall) {
		this.remarkall = remarkall;
	}

	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

}