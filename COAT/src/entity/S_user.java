package entity;

public class S_user {

	private int userid;
	private String loginname;
	private String loginpass;
	private String usercode;
	private String truename;
	private String englishname;
	private String chinesename;
	private String email;
	private String idcard;
	private String sex;
	private String birthday;
	private String headimage;
	private String registration;
	private String address;
	private String dept;
	private String postion;
	private String createname;
	private String createdate;
	private String modifier;
	private String modifyDate;
	private String sfyx;
	public S_user() {
		super();
	}
	
	public S_user(int userid, String loginname, String loginpass,
			String usercode, String truename, String englishname,
			String chinesename, String email, String idcard, String sex,
			String birthday, String headimage, String registration,
			String address, String dept, String postion, String createname,
			String createdate, String modifier, String modifyDate, String sfyx) {
		super();
		this.userid = userid;
		this.loginname = loginname;
		this.loginpass = loginpass;
		this.usercode = usercode;
		this.truename = truename;
		this.englishname = englishname;
		this.chinesename = chinesename;
		this.email = email;
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
	
	public S_user(String loginname) {
		super();
		this.loginname = loginname;
		
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	
	
	
	
}
