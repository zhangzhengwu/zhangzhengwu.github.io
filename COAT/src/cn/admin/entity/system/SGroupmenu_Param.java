package cn.admin.entity.system;

public class SGroupmenu_Param implements Cloneable {
	private int menuroleid;
	private String add;
	private int menuid;
	private int groupid;
	private String del;
	private String sel;
	private String upd;
	private String audit;
	private String exp;
	private String other;

	public SGroupmenu_Param() {

	}
	/*
	 * 比较前台传入过来的内容和数据库原来的记录是否一致。一致就不做进步的操作
	 * */
	public boolean equals(Object o) {
		SGroupmenu_Param a = (SGroupmenu_Param) o;
		if (a.menuroleid == menuroleid && a.add.equals(add)
				&& a.del.equals(del) && a.sel.equals(sel) && a.upd.equals(upd)
				&& a.audit.equals(audit) && a.exp.equals(exp)
				&& a.other.equals(other)) {
			return true;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return menuroleid;
	}

	public SGroupmenu_Param clone() {
		SGroupmenu_Param cloneObj = null;
		try {
			cloneObj = (SGroupmenu_Param) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloneObj;
	}

	public SGroupmenu_Param(int menuroleid,int groupid,int menuid ,String add, String upd, String del,
			String sel, String exp, String audit, String other) {
		this.add = add;
		this.groupid=groupid;
		this.menuid=menuid;
		this.menuroleid = menuroleid;
		this.del = del;
		this.sel = sel;
		this.upd = upd;
		this.audit = audit;
		this.exp = exp;
		this.other = other;
	}
	public int getMenuroleid() {
		return menuroleid;
	}
	public void setMenuroleid(int menuroleid) {
		this.menuroleid = menuroleid;
	}
	public int getMenuid() {
		return menuid;
	}
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getUpd() {
		return upd;
	}

	public void setUpd(String upd) {
		this.upd = upd;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}
