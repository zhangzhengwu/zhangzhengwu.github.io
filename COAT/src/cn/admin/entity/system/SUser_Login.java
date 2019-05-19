package cn.admin.entity.system;

import java.util.List;

public class SUser_Login {
	// virtual Fileds
	private Integer groupid;// 用户的组别ID,当前组
	private String userStu;// 用户记录是否有效【Y:有效，N:失效，D:已删除】
	private String groupStu;// 组别记录否有效【Y:有效，N:失效，D:已删除】
	private String roleStu;// 规则记录否有效【Y:有效，N:失效，D:已删除】
	private String groupname;
	private String isoption;
	private List<SGroup> groupList;

	public String getIsoption() {
		return isoption;
	}

	public void setIsoption(String isoption) {
		this.isoption = isoption;
	}

	public List<SGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<SGroup> groupList) {
		this.groupList = groupList;
	}

	private String groupfullname;
	private String groupCode;
	private Integer userid;
	private String loginname;// 登录名
	private String loginpass;// 登录密码
	private String usercode;// 用户编号
	private String truename;// 真实名称
	private String englishname;// 英文名称
	private String chinesename;// 中文名称
	private String idcard;// 身份证号码
	private String sex;// 性别
	private String birthday;// 出生日期
	private String headimage;// 头像
	private String registration;// 户籍
	private String address;// 地址
	private String dept;// 部门
	private String postion;// 职位
	private String createname;// 创建人
	private String createdate;// 创建时间
	private String modifier;// 修改人
	private String modifyDate;// 修改时间

	// private String sfyx;// 是否有效【Y:有效，N:失效，D:已删除】

	public SUser_Login() {

	}

	public SUser_Login(Integer userid, String loginname, String loginpass,
			Integer groupid, String groupname, String userStu, String groupStu,
			String roleStu, String isoption, String img) {
		this.userid = userid;
		this.loginname = loginname;
		this.headimage = img;
		this.loginpass = loginpass;
		this.groupid = groupid;
		this.groupname = groupname;
		this.userStu = userStu;
		this.groupStu = groupStu;
		this.roleStu = roleStu;
		this.isoption = isoption;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getUserStu() {
		return userStu;
	}

	public void setUserStu(String userStu) {
		this.userStu = userStu;
	}

	public String getGroupStu() {
		return groupStu;
	}

	public void setGroupStu(String groupStu) {
		this.groupStu = groupStu;
	}

	public String getRoleStu() {
		return roleStu;
	}

	public void setRoleStu(String roleStu) {
		this.roleStu = roleStu;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupfullname() {
		return groupfullname;
	}

	public void setGroupfullname(String groupfullname) {
		this.groupfullname = groupfullname;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpass() {
		return loginpass;
	}

	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPostion() {
		return postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
}
