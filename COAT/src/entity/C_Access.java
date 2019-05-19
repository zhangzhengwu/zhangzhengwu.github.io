package entity;

/**
 * CAccess entity. @author MyEclipse Persistence Tools
 */

public class C_Access implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String refno;//流水号
	private String staffcode;//用户编号
	private String staffname;//用户名称
	private String userType;//用户类型
	private String location;//location
	private String staffCard;//是否选择员工卡
	private String historyno;//历史卡号
	private String newno;//新卡号
	private String photoSticker;//员工膜贴
	private String reason;//原因
	private String creator;//创建人
	private String createDate;//创建时间
	private String status;//状态
	private String sfyx;//是否有效
	private String remark;
	private String remark1;
	
	// Constructors

	/** default constructor */
	public C_Access() {
	}


	public String getRemark1() {
		return remark1;
	}


	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}


	/**
	 * 分页查询
	 * @param refno
	 * @param staffcode
	 * @param staffname
	 * @param reason
	 * @param creator
	 * @param createDate
	 * @param status
	 */
	public C_Access(String refno, String staffcode, String staffname,
			String reason, String creator, String createDate, String status) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.reason = reason;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
	}
	
	public C_Access(String refno, String staffcode, String staffname,
			String reason, String creator, String createDate, String status,String remark) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.reason = reason;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.remark = remark;
	}

	/**
	 * 保存AccessCard
	 * @param refno
	 * @param staffcode
	 * @param staffname
	 * @param userType
	 * @param location
	 * @param staffCard
	 * @param photoSticker
	 * @param reason
	 * @param creator
	 * @param createDate
	 * @param status
	 * @param sfyx
	 */
	public C_Access(String refno, String staffcode, String staffname,
			String userType, String location, String staffCard,
			String photoSticker, String reason, String creator,
			String createDate, String status, String sfyx) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.location = location;
		this.staffCard = staffCard;
		this.photoSticker = photoSticker;
		this.reason = reason;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
	}


	public C_Access(String refno, String staffcode, String staffname,
			String userType, String location, String staffCard,
			String historyno, String newno, String photoSticker, String reason,
			String creator, String createDate, String status, String sfyx,String remark) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.location = location;
		this.staffCard = staffCard;
		this.historyno = historyno;
		this.newno = newno;
		this.photoSticker = photoSticker;
		this.reason = reason;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
		this.remark = remark;
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

	public String getStaffCard() {
		return this.staffCard;
	}

	public void setStaffCard(String staffCard) {
		this.staffCard = staffCard;
	}

	public String getHistoryno() {
		return this.historyno;
	}

	public void setHistoryno(String historyno) {
		this.historyno = historyno;
	}

	public String getNewno() {
		return this.newno;
	}

	public void setNewno(String newno) {
		this.newno = newno;
	}

	public String getPhotoSticker() {
		return this.photoSticker;
	}

	public void setPhotoSticker(String photoSticker) {
		this.photoSticker = photoSticker;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}