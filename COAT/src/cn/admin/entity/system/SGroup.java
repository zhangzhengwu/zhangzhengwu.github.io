package cn.admin.entity.system;

/**
 * 用户组实体类 @author King
 */

public class SGroup {

	// Fields

	private Integer groupid;// 组ID
	private String groupcode;// 组编号
	private String groupname;// 组名称
	private String groupfullname;// 组全名
	private String remark;// 备注
	private String createname;
	private String createDate;
	private String modifier;
	private String modifyDate;
	private String sfyx;// 是否有效【Y:有效，N:失效，D:已删除】

	// virtual Fields
	private String isoption;
	private String groupStu;
	private String roleStu;

	// Constructors
	/** full constructor */
	public SGroup(Integer groupid, String groupcode, String groupname) {
		this.groupid = groupid;
		this.groupcode = groupcode;
		this.groupname = groupname;
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

	public String getIsoption() {
		return isoption;
	}

	public void setIsoption(String isoption) {
		this.isoption = isoption;
	}

	/** default constructor */
	public SGroup() {
	}

	/** minimal constructor */
	public SGroup(Integer groupid) {
		this.groupid = groupid;
	}

	public SGroup(Integer groupid, String groupname) {
		this.groupid = groupid;
		this.groupname = groupname;
	}

	/** full constructor */
	public SGroup(Integer groupid, String groupcode, String groupname,
			String groupfullname, String remark, String createname,
			String createDate, String modifier, String modifyDate, String sfyx) {
		this.groupid = groupid;
		this.groupcode = groupcode;
		this.groupname = groupname;
		this.groupfullname = groupfullname;
		this.remark = remark;
		this.createname = createname;
		this.createDate = createDate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
	}
	public SGroup(Integer groupid, String groupcode, String groupname,
			String groupfullname, String remark, String createname,
			String createDate, String modifier, String modifyDate, String sfyx,String isoption) {
		this.groupid = groupid;
		this.groupcode = groupcode;
		this.groupname = groupname;
		this.groupfullname = groupfullname;
		this.remark = remark;
		this.createname = createname;
		this.createDate = createDate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
		this.isoption=isoption;
	}

	// Property accessors

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupcode() {
		return this.groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupfullname() {
		return this.groupfullname;
	}

	public void setGroupfullname(String groupfullname) {
		this.groupfullname = groupfullname;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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