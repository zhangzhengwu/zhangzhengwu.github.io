package entity;

public class Principal {
	private int Id;
	private String principal;
	private String adddate;
	private String addname;
	private String remark;
	public Principal(){};
	public Principal(int id, String principal, String adddate, String addname,
			String remark) {
		super();
		Id = id;
		this.principal = principal;
		this.adddate = adddate;
		this.addname = addname;
		this.remark = remark;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
