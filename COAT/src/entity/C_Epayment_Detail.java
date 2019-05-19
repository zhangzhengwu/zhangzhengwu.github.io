package entity;

public class C_Epayment_Detail {

	private int detailid;
	private String refno;
	private String productcode;
	private String productname;
	private double price;
	private double quantity;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public C_Epayment_Detail(String refno, String productcode,
			String productname) {
		super();
		this.refno = refno;
		this.productcode = productcode;
		this.productname = productname;
	}
	public C_Epayment_Detail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getDetailid() {
		return detailid;
	}
	public void setDetailid(int detailid) {
		this.detailid = detailid;
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
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
	
}
