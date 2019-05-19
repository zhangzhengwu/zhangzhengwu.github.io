package cn.admin.entity.system;
/**
 * 
 * 菜单操作权限 @author kingxu
 *
 */
public class RoleManager {

	private Integer menuid;//菜单ID
	private String add;//操作权限
	private String del;//删除权限
	private String upd;//修改权限
	private String search;//查询权限
	private String export;//导出权限
	private String audit;//审核权限
	private String other;//其他权限
	
	
	
	
	
	
	
	/**
	 * 获取菜单操作权限函数
	 * @param menuid
	 * @param add
	 * @param del
	 * @param upd
	 * @param search
	 * @param export
	 * @param audit
	 * @param other
	 */
	public RoleManager(Integer menuid, String add, String del, String upd,
			String search, String export, String audit, String other) {
		super();
		this.menuid = menuid;
		this.add = add;
		this.del = del;
		this.upd = upd;
		this.search = search;
		this.export = export;
		this.audit = audit;
		this.other = other;
	}
	public Integer getMenuid() {
		return menuid;
	}
	public String getAdd() {
		return add;
	}
	public String getDel() {
		return del;
	}
	public String getUpd() {
		return upd;
	}
	public String getSearch() {
		return search;
	}
	public String getExport() {
		return export;
	}
	public String getAudit() {
		return audit;
	}
	public String getOther() {
		return other;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public void setUpd(String upd) {
		this.upd = upd;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public void setExport(String export) {
		this.export = export;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
	
	
	
	
	
	
}
