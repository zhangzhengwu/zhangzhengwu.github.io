package entity;

public class MissingSendMail {

	private Integer id;
	private String staffcode;
	private String email;
	private String updata;
	private String stuts;
	private String remark;
	
	
	
	
	public MissingSendMail(Integer id, String staffcode, String email,
			String updata, String stuts, String remark) {
		super();
		this.id = id;
		this.staffcode = staffcode;
		this.email = email;
		this.updata = updata;
		this.stuts = stuts;
		this.remark = remark;
	}
	
	
	public MissingSendMail() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUpdata() {
		return updata;
	}
	public void setUpdata(String updata) {
		this.updata = updata;
	}
	public String getStuts() {
		return stuts;
	}
	public void setStuts(String stuts) {
		this.stuts = stuts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
