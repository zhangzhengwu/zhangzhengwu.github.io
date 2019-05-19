package cn.admin.entity.system;
import java.util.List;

/**
 * 
 * 用户实体类 @author kingxu
 * 
 */
public class SUser {

	//virtual Fileds
	private List<SGroup> groupList;
	
	// Fields

	private Integer userid;
	private String loginname;// 登录名
	private String loginpass;// 登录密码
	private String usercode;// 用户编号
	private String truename;// 真实名称
	private String englishname;// 英文名称
	private String chinesename;// 中文名称
	private String email;//郵箱
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
	private String sfyx;// 是否有效【Y:有效，N:失效，D:已删除】

	// Constructors

	/** default constructor */
	public SUser() {
	}

	/** minimal constructor */
	public SUser(Integer userid) {
		this.userid = userid;
	}

	public SUser(Integer userid, String loginname, String loginpass) {
		this.userid = userid;
		this.loginname = loginname;
		this.loginpass = loginpass;
	}
	
	
	public SUser(Integer userid, String loginname, String loginpass,
			String usercode, String truename, String englishname,
			String chinesename, String idcard, String sex, String birthday,
			String headimage, String registration, String address, String dept,
			String postion, String createname, String createdate,
			String modifier, String modifyDate, String sfyx) {
		this.userid = userid;
		this.loginname = loginname;
		this.loginpass = loginpass;
		this.usercode = usercode;
		this.truename = truename;
		this.englishname = englishname;
		this.chinesename = chinesename;
		this.idcard = idcard;
		this.sex = sex;
		this.birthday = birthday;
		this.headimage = headimage;
		this.registration = registration;
		this.address = address;
		this.dept = dept;
		this.postion = postion;
		this.createname = createname;
		this.createdate = createdate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
	}

	/** full constructor */
	public SUser(Integer userid, String loginname, String loginpass,
			String usercode, String truename, String englishname,
			String chinesename, String idcard, String sex, String birthday,
			String headimage, String registration, String address, String dept,
			String postion, String createname, String createdate,
			String modifier, String modifyDate, String sfyx,String email) {
		this.userid = userid;
		this.loginname = loginname;
		this.loginpass = loginpass;
		this.usercode = usercode;
		this.truename = truename;
		this.englishname = englishname;
		this.chinesename = chinesename;
		this.idcard = idcard;
		this.sex = sex;
		this.birthday = birthday;
		this.headimage = headimage;
		this.registration = registration;
		this.address = address;
		this.dept = dept;
		this.postion = postion;
		this.createname = createname;
		this.createdate = createdate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
		this.email=email;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpass() {
		return this.loginpass;
	}

	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getTruename() {
		return this.truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getEnglishname() {
		return this.englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getChinesename() {
		return this.chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadimage() {
		return this.headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public String getRegistration() {
		return this.registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPostion() {
		return this.postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}

	public String getCreatename() {
		return this.createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}