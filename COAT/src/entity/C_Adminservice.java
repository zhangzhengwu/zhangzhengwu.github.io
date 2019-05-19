package entity;

/**
 * CAdminservice entity. @author MyEclipse Persistence Tools
 */

public class C_Adminservice implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String refno;//流水号
	private String staffcode;//顾问编号
	private String staffname;//顾问名称
	private String userType;//用户类型
	private String location;//location
	private String fluorTube;//是否选择更换灯管
	private String floor;//楼层
	private String seat;//座位号
	private String phoneRepair;//是否维修电话
	private String phoneNumber;//电话号码
	private String phoneRpass;//是否重置电话号码密码
	private String phoneNumber2;//电话号码
	private String copierRepair;//是否维修打印机
	private String floor2;//楼层
	private String copier;//打印机号码
	private String officeMaintenance;//办公室维护
	private String floor3;//楼层
	private String description;//m描述
	private String remark;//备注
	private String creator;//创建人
	private String createDate;//创建时间
	private String status;//表单状态
	private String sfyx;//是否有效

	// Constructors

	/** default constructor */
	public C_Adminservice() {
	}

	
	
	/**
	 * 分页查询构造方法
	 * @param refno
	 * @param staffcode
	 * @param staffname
	 * @param remark
	 * @param creator
	 * @param createDate
	 * @param status
	 */
	public C_Adminservice(String refno, String staffcode, String staffname,String location,
			String remark, String creator, String createDate, String status) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.location=location;
		this.remark = remark;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
	}


	
	
	

	


/**
 * 保存
 * @param refno
 * @param staffcode
 * @param staffname
 * @param userType
 * @param fluorTube
 * @param floor
 * @param seat
 * @param phoneRepair
 * @param phoneNumber
 * @param phoneRpass
 * @param phoneNumber2
 * @param copierRepair
 * @param floor2
 * @param copier
 * @param officeMaintenance
 * @param floor3
 * @param description
 * @param remark
 * @param creator
 * @param createDate
 * @param status
 * @param sfyx
 * @param location
 */
	public C_Adminservice(String refno, String staffcode, String staffname,
			String userType, String fluorTube, String floor, String seat,
			String phoneRepair, String phoneNumber, String phoneRpass,
			String phoneNumber2, String copierRepair, String floor2,
			String copier, String officeMaintenance, String floor3,
			String description, String remark, String creator,
			String createDate, String status, String sfyx,String location) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.userType = userType;
		this.fluorTube = fluorTube;
		this.floor = floor;
		this.seat = seat;
		this.phoneRepair = phoneRepair;
		this.phoneNumber = phoneNumber;
		this.phoneRpass = phoneRpass;
		this.phoneNumber2 = phoneNumber2;
		this.copierRepair = copierRepair;
		this.floor2 = floor2;
		this.copier = copier;
		this.officeMaintenance = officeMaintenance;
		this.floor3 = floor3;
		this.description = description;
		this.remark = remark;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
		this.location=location;
	}



	/**
	 * 内容详细
	 * @param refno
	 * @param staffcode
	 * @param staffname
	 * @param fluorTube
	 * @param floor
	 * @param seat
	 * @param phoneRepair
	 * @param phoneNumber
	 * @param phoneRpass
	 * @param phoneNumber2
	 * @param copierRepair
	 * @param floor2
	 * @param copier
	 * @param officeMaintenance
	 * @param floor3
	 * @param description
	 * @param remark
	 * @param creator
	 * @param createDate
	 * @param status
	 * @param sfyx
	 */
	public C_Adminservice(String refno, String staffcode, String staffname,String location,
			String fluorTube, String floor, String seat, String phoneRepair,
			String phoneNumber, String phoneRpass, String phoneNumber2,
			String copierRepair, String floor2, String copier,
			String officeMaintenance, String floor3, String description,
			String remark, String creator, String createDate, String status,
			String sfyx) {
		super();
		this.refno = refno;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.location=location;
		this.fluorTube = fluorTube;
		this.floor = floor;
		this.seat = seat;
		this.phoneRepair = phoneRepair;
		this.phoneNumber = phoneNumber;
		this.phoneRpass = phoneRpass;
		this.phoneNumber2 = phoneNumber2;
		this.copierRepair = copierRepair;
		this.floor2 = floor2;
		this.copier = copier;
		this.officeMaintenance = officeMaintenance;
		this.floor3 = floor3;
		this.description = description;
		this.remark = remark;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
	}



	/** full constructor */
	public C_Adminservice(String staffcode, String staffname, String fluorTube,
			String floor, String seat, String phoneRepair, String phoneNumber,
			String phoneRpass, String phoneNumber2, String copierRepair,
			String floor2, String copier, String officeMaintenance,
			String floor3, String description, String remark, String creator,
			String createDate, String status, String sfyx) {
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.fluorTube = fluorTube;
		this.floor = floor;
		this.seat = seat;
		this.phoneRepair = phoneRepair;
		this.phoneNumber = phoneNumber;
		this.phoneRpass = phoneRpass;
		this.phoneNumber2 = phoneNumber2;
		this.copierRepair = copierRepair;
		this.floor2 = floor2;
		this.copier = copier;
		this.officeMaintenance = officeMaintenance;
		this.floor3 = floor3;
		this.description = description;
		this.remark = remark;
		this.creator = creator;
		this.createDate = createDate;
		this.status = status;
		this.sfyx = sfyx;
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

	public String getFluorTube() {
		return this.fluorTube;
	}

	public void setFluorTube(String fluorTube) {
		this.fluorTube = fluorTube;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getSeat() {
		return this.seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getPhoneRepair() {
		return this.phoneRepair;
	}

	public void setPhoneRepair(String phoneRepair) {
		this.phoneRepair = phoneRepair;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneRpass() {
		return this.phoneRpass;
	}

	public void setPhoneRpass(String phoneRpass) {
		this.phoneRpass = phoneRpass;
	}

	public String getPhoneNumber2() {
		return this.phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getCopierRepair() {
		return this.copierRepair;
	}

	public void setCopierRepair(String copierRepair) {
		this.copierRepair = copierRepair;
	}

	public String getFloor2() {
		return this.floor2;
	}

	public void setFloor2(String floor2) {
		this.floor2 = floor2;
	}

	public String getCopier() {
		return this.copier;
	}

	public void setCopier(String copier) {
		this.copier = copier;
	}

	public String getOfficeMaintenance() {
		return this.officeMaintenance;
	}

	public void setOfficeMaintenance(String officeMaintenance) {
		this.officeMaintenance = officeMaintenance;
	}

	public String getFloor3() {
		return this.floor3;
	}

	public void setFloor3(String floor3) {
		this.floor3 = floor3;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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



	public void setUserType(String userType) {
		this.userType = userType;
	}



	public String getUserType() {
		return userType;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getLocation() {
		return location;
	}

}