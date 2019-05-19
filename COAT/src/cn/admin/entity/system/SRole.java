package cn.admin.entity.system;

/**
 * 
 * 用户组权限实体类 @author kingxu
 *
 */

public class SRole  {


	// Fields

	private Integer roleid;//用户组权限ID
	private String rolename;//用户组权限名称
	private Integer groupid;//组ID
	private Integer userid;//用户ＩＤ
	private String remark;//备注
	private String isoption;//是否为默认项
	private String createname;
	private String createDate;
	private String modifier;
	private String modifyDate;
	private String sfyx;//是否有效【Y:有效，N:失效，D:已删除】

	// Constructors

	/** default constructor */
	public SRole() {
	}

	/** minimal constructor */
	public SRole(Integer roleid) {
		this.roleid = roleid;
	}

	/** full constructor */
	public SRole(Integer roleid, String rolename, Integer groupid,
			Integer userid, String remark, String isoption, String createname,
			String createDate, String modifier, String modifyDate, String sfyx) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.groupid = groupid;
		this.userid = userid;
		this.remark = remark;
		this.isoption = isoption;
		this.createname = createname;
		this.createDate = createDate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
	}

	// Property accessors

	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsoption() {
		return this.isoption;
	}

	public void setIsoption(String isoption) {
		this.isoption = isoption;
	}

	public String getCreatename() {
		return this.createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

}