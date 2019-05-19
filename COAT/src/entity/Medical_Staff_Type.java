package entity;

public class Medical_Staff_Type {
	private int Id;
	private String type;
	private String money;
	private String Number;
	private String per;
	private String remark1;
	private String remark2;
	private String remark3;
	
	
	
	
	
	
	public Medical_Staff_Type() {
	}
	public Medical_Staff_Type(int id, String type, String money, String number,
			String per, String remark1, String remark2, String remark3) {
		super();
		Id = id;
		this.type = type;
		this.money = money;
		Number = number;
		this.per = per;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		//this.maxAmount = maxAmount;
	}
	public int getId() {
		return Id;
	}
	public String getType() {
		return type;
	}
	public String getMoney() {
		return money;
	}
	public String getNumber() {
		return Number;
	}
	public String getPer() {
		return per;
	}
	public String getRemark1() {
		return remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public String getRemark3() {
		return remark3;
	}
 
	public void setId(int id) {
		Id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
 
	
	
	
}
