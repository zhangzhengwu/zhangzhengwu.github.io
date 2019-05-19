package cn.admin.entity.system;

public class SUsermenu_Param implements Cloneable {
	private int usermenuid;
	private String add;
	private int menuid;


	private String del;
	private String sel;
	private String upd;
	private String audit;
	private String exp;
	private String other;

	public SUsermenu_Param() {

	}
	
	public int getMenuid() {
		return menuid;
	}
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}

	private int userid;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/*
	 * 比较前台传入过来的内容和数据库原来的记录是否一致。一致就不做进步的操作
	 * */
	public boolean equals(Object o) {
		SUsermenu_Param a = (SUsermenu_Param) o;
		if (a.usermenuid == usermenuid && a.add.equals(add)
				&& a.del.equals(del) && a.sel.equals(sel) && a.upd.equals(upd)
				&& a.audit.equals(audit) && a.exp.equals(exp)
				&& a.other.equals(other)) {
			return true;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return usermenuid;
	}

	public SUsermenu_Param clone() {
		SUsermenu_Param cloneObj = null;
		try {
			cloneObj = (SUsermenu_Param) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloneObj;
	}

	public SUsermenu_Param(int usermenuid,int userid,int menuid ,String add, String upd, String del,
			String sel, String exp, String audit, String other) {
		this.add = add;
		this.userid=userid;
		this.menuid=menuid;
		this.usermenuid = usermenuid;
		this.del = del;
		this.sel = sel;
		this.upd = upd;
		this.audit = audit;
		this.exp = exp;
		this.other = other;
	}

	public int getUsermenuid() {
		return usermenuid;
	}

	public void setUsermenuid(int usermenuid) {
		this.usermenuid = usermenuid;
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
