package entity;

public class C_EPayment_List {

	private int id;
	private String productcode;
	private String productname;
	private double price;
	private double quantity;
	private String unit;
	private String adddate;
	private String addname;
	private String remark;
	private String sfyx;
	
	public C_EPayment_List(){
		
	}
	
	
	public C_EPayment_List(int id, String productcode, String productname,
			double price, double quantity, String unit, String adddate,
			String addname, String remark, String sfyx) {
		super();
		this.id = id;
		this.productcode = productcode;
		this.productname = productname;
		this.price = price;
		this.quantity = quantity;
		this.unit = unit;
		this.adddate = adddate;
		this.addname = addname;
		this.remark = remark;
		this.sfyx = sfyx;
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	
}
