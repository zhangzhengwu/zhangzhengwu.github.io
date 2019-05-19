package cn.admin.entity.system;

import java.util.List;

public class SMenuRole_mix implements Comparable<SMenuRole_mix> {
	// Virtual Fields

	private Integer usermenuid;// 用户菜单ID
	private String menuname;// 菜单名称
	private String menuAction;
	public String getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	private Integer menuid;// 菜单ID
	private Integer sortFlag;// 排序的标记1，是父层2是子层
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
	private List<SMenuRole_mix> sonList;
	

	public Integer getMenuid() {
		return menuid;
	}

	public List<SMenuRole_mix> getSonList() {
		return sonList;
	}

	public void setSonList(List<SMenuRole_mix> sonList) {
		this.sonList = sonList;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public SMenuRole_mix() {

	}

	public SMenuRole_mix(String menuname, Integer menuid, Integer parentId,
			Integer childshort, Integer parentshort, String createDate,
			String add, String upd, String delete, String search,
			String export, String audit, String other) {
		this.menuid = menuid;
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

	public SMenuRole_mix(Integer usermenuid, Integer menuid, String menuname,
			Integer parentId, Integer parentshort, Integer childshort,
			String createDate, String add, String upd, String delete,
			String search, String export, String audit, String other,
			List<SMenuRole_mix> sonLsit, Integer sortFlag) {
		this.usermenuid = usermenuid;
		this.menuid = menuid;
		this.menuname = menuname;
		this.parentshort = parentshort;
		this.childshort = childshort;
		this.parentId = parentId;
		this.createDate = createDate;
		this.add = add;
		this.upd = upd;
		this.sonList = sonLsit;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
		this.sortFlag = sortFlag;
	}

	public SMenuRole_mix(Integer usermenuid, Integer menuid, String menuname,String menuAction,
			Integer parentId,Integer childshort, String createDate, String add, String upd,
			String delete, String search, String export, String audit,
			String other, Integer sortFlag) {
		this.usermenuid = usermenuid;
		this.menuid = menuid;
		this.menuname = menuname;
		this.parentId = parentId;
		this.createDate = createDate;
		this.add = add;
		this.menuAction=menuAction;
		this.upd = upd;
		this.delete = delete;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
		this.childshort=childshort;
		this.sortFlag = sortFlag;
	}

	public SMenuRole_mix(Integer usermenuid, String menuname, Integer parentId,
			Integer childshort, Integer parentshort, String createDate,
			String add, String upd, String delete, String search,
			String export, String audit, String other,
			List<SMenuRole_mix> sonLsit) {
		this.usermenuid = usermenuid;
		this.menuname = menuname;
		this.sonList = sonLsit;
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

	public SMenuRole_mix(Integer usermenuid, String menuname, Integer parentId,
			Integer childshort, Integer parentshort, String createDate,
			String add, String upd, String delete, String search,
			String export, String audit, String other) {
		this.usermenuid = usermenuid;
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
		SMenuRole_mix a = (SMenuRole_mix) o;
		return (a.menuid == menuid) ? true : false;
	}

	public int hashCode() {
		return menuid;
	}

	public Integer getUsermenuid() {
		return usermenuid;
	}

	public void setUsermenuid(Integer usermenuid) {
		this.usermenuid = usermenuid;
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

	public int compareTo(SMenuRole_mix o) {
		// TODO Auto-generated method stub
		if (this.getSortFlag() == 1) {
			if (this.getParentshort() == null || o.getParentshort() == null) {
				return -1;
			}
			if (this.getParentshort() > o.getParentshort()) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (this.getChildshort() == null || o.getChildshort() == null) {
				return -1;
			}
			if (this.getChildshort() > o.getChildshort()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public Integer getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

}
