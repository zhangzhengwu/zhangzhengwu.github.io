package entity;

public class Missingreport {
	private int id;
	private String upddate;
	private String updname;
	private String type;
	private String remark;
	public Missingreport(){};
	public Missingreport(int id, String upddate, String updname, String type,
			String remark) {
		super();
		this.id = id;
		this.upddate = upddate;
		this.updname = updname;
		this.type = type;
		this.remark = remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getUpdname() {
		return updname;
	}
	public void setUpdname(String updname) {
		this.updname = updname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
