package cn.admin.entity.system;

public class SGroupRole_mix {
	// Virtual Fields

	private Integer menuroleid;// 菜单ID
	private String menuname;// 菜单名称
	private Integer menuid;
	private Integer groupid;


	

	private Integer parentId;// 父菜单ID 
	private Integer childshort;// 子菜单排序
	private Integer parentshort;// 父菜单排序
	private String createDate;
	private String add;
	private String upd;
	private String delete;
	private String search;
	private String export;
	private String audit;
	private String other;

	
	
	
	
	
	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Integer getMenuid() {
		return menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	
	
	public SGroupRole_mix(){
		
	}
	
	public SGroupRole_mix(String menuname,Integer menuid,Integer groupid, Integer parentId, Integer childshort,
			Integer parentshort, String createDate, String add, String upd,
			String delete, String search, String export, String audit,
			String other) {
		this.menuid=menuid;
		this.groupid=groupid;
		this.menuname = menuname;
		this.parentId = parentId;
		this.childshort = childshort;
		this.parentshort = parentshort;
		this.createDate = createDate;
		this.add = add;
		this.upd = upd;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
	}

	public SGroupRole_mix(Integer menuroleid,Integer menuid,Integer groupid,String menuname, Integer parentId, String createDate,
			String add, String upd, String delete, String search,
			String export, String audit, String other) {
		this.menuroleid = menuroleid;
		this.menuid=menuid;
		this.groupid=groupid;
		this.menuname = menuname;
		this.parentId = parentId;
		this.createDate = createDate;
		this.add = add;
		this.upd = upd;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
	}

	public Integer getMenuroleid() {
		return menuroleid;
	}

	public void setMenuroleid(Integer menuroleid) {
		this.menuroleid = menuroleid;
	}

	public SGroupRole_mix(Integer menuroleid, String menuname, Integer parentId,
			Integer childshort, Integer parentshort, String createDate,
			String add, String upd, String delete, String search,
			String export, String audit, String other) {
		this.menuroleid = menuroleid;
		this.menuname = menuname;
		this.parentId = parentId;
		this.childshort = childshort;
		this.parentshort = parentshort;
		this.createDate = createDate;
		this.add = add;
		this.upd = upd;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
	}

	/**
	 *over write
	 */
	public boolean equals(Object o) {
		SGroupRole_mix a = (SGroupRole_mix) o;
		return (a.menuid == menuid) ? true : false;
	}

	public int hashCode() {
		return menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getChildshort() {
		return childshort;
	}

	public void setChildshort(Integer childshort) {
		this.childshort = childshort;
	}

	public Integer getParentshort() {
		return parentshort;
	}

	public void setParentshort(Integer parentshort) {
		this.parentshort = parentshort;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getUpd() {
		return upd;
	}

	public void setUpd(String upd) {
		this.upd = upd;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
