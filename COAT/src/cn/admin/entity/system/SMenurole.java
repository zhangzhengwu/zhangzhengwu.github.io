package cn.admin.entity.system;

/**
 * 
 * 菜单权限实体类 @author kingxu
 *
 */

public class SMenurole  {

	
 
	// Fields

	private Integer menuroleid;//菜单权限ＩＤ
	private Integer roleid;//用户组权限ID
	private Integer menuid;//菜单ＩＤ
	private String add;//新增操作权限
	private String upd;//修改操作权限
	private String delete;//删除操作权限
	private String search;//查询操作权限
	private String export;//导出操作权限
	private String audit;//审核操作权限
	private String other;//其他操作权限
	private String createname;//创建人
	private String createDate;//创建时间
	private String sfyx;//是否有效【Y:有效，N:失效，D:已删除】

	// Constructors

	/** default constructor */
	public SMenurole() {
	}

	public SMenurole(Integer roleid, Integer menuid, String add, String upd,
			String delete, String search, String export, String audit,
			String other, String createname, String createDate, String sfyx) {
		super();
		this.roleid = roleid;
		this.menuid = menuid;
		this.add = add;
		this.upd = upd;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
		this.createname = createname;
		this.createDate = createDate;
		this.sfyx = sfyx;
	}

	/** minimal constructor */
	public SMenurole(Integer menuroleid) {
		this.menuroleid = menuroleid;
	}

	/** full constructor */
	public SMenurole(Integer menuroleid, Integer roleid, Integer menuid,
			String add, String upd, String delete, String search,
			String export, String audit, String other,String createname,String createDate, String sfyx) {
		this.menuroleid = menuroleid;
		this.roleid = roleid;
		this.menuid = menuid;
		this.add = add;
		this.upd = upd;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
		this.createname = createname;
		this.createDate = createDate;
		
		this.sfyx = sfyx;
	}

	// Property accessors

	public Integer getMenuroleid() {
		return this.menuroleid;
	}

	public void setMenuroleid(Integer menuroleid) {
		this.menuroleid = menuroleid;
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public String getAdd() {
		return this.add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getUpd() {
		return this.upd;
	}

	public void setUpd(String upd) {
		this.upd = upd;
	}

	public String getDelete() {
		return this.delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getExport() {
		return this.export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getAudit() {
		return this.audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getCreatename() {
		return createname;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}