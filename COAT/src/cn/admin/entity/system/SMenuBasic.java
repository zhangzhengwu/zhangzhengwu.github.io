package cn.admin.entity.system;

public class SMenuBasic {
	private int userid;
	private int groupid;
	private int menuid;
	private String meunname;
	private String menuAction;
	private String add;
	private String upd;
	private String delete;
	private String search;
	private String export;
	private String audit;
	private String other;
	private String sfyx;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public SMenuBasic() {

	}

	public SMenuBasic(int userid, int groupid, int menuid, String menuname,String menuAction,
			String add, String upd, String delete, String search,
			String export, String audit, String other, String sfyx) {
		this.upd = upd;
		this.add = add;
		this.audit = audit;
		this.menuAction=menuAction;
		this.delete = delete;
		this.export = export;
		this.groupid = groupid;
		this.menuid = menuid;
		this.meunname = menuname;
		this.other = other;
		this.search = search;
		this.sfyx = sfyx;
		this.userid = userid;
	}
	public String getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getMeunname() {
		return meunname;
	}

	public void setMeunname(String meunname) {
		this.meunname = meunname;
	}

	public int getMenuid() {
		return menuid;
	}

	public void setMenuid(int menuid) {
		this.menuid = menuid;
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

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
}
