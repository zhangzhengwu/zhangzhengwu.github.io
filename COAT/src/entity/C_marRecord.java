package entity;

/**
 * Mark Premium 订单明细表 Wilson 2013-5-21 10:36:52
 */
public class C_marRecord {
	private int id;
	private String ordercode;
	private String procode;
	private String proname;
	private String calculation;
	private Double price;
	private Double quantity;
	private Double priceall;

	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	/**
	 * 附加 Order
	 */
	private String location;
	private String orderdate;
	private String clientname;
	private String clientcode;
	private String c_clubmember; 
	private String paymentmethod; 
	private String ordertype;
	
	
	public C_marRecord(){}
	
	 
	public C_marRecord(String ordercode, String procode, String proname,
			String calculation, Double price, Double quantity, Double priceall,
			String remark1, String remark2, String remark3, String remark4) {
		super();
		this.ordercode = ordercode;
		this.procode = procode;
		this.proname = proname;
		this.calculation = calculation;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	}
	public C_marRecord(int id,String ordercode, String procode, String proname,
			String calculation, Double price, Double quantity, Double priceall,
			String remark1, String remark2, String remark3, String remark4) {
		super();
		this.id=id;
		this.ordercode = ordercode;
		this.procode = procode;
		this.proname = proname;
		this.calculation = calculation;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	}
 
/**
 *  Marketing Premium HR 专用函数
 * @param ordercode
 * @param procode
 * @param proname
 * @param calculation
 * @param price
 * @param quantity
 * @param priceall
 * @param location
 * @param orderdate
 * @param clientname
 * @param clientcode
 * @param ordertype
 */
	public C_marRecord(String ordercode, String procode, String proname,
			String calculation, Double price, Double quantity, Double priceall,
			String location, String orderdate, String clientname,
			String clientcode, String ordertype) {
		super();
		this.ordercode = ordercode;
		this.procode = procode;
		this.proname = proname;
		this.calculation = calculation;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.location = location;
		this.orderdate = orderdate;
		this.clientname = clientname;
		this.clientcode = clientcode;
		this.ordertype = ordertype;
	}


	public C_marRecord(int id, String ordercode, String procode, String proname,
		String calculation, Double price, Double quantity, Double priceall,
		String location, String orderdate, String clientname,
		String clientcode, String ordertype) {
	super();
	this.id = id;
	this.ordercode = ordercode;
	this.procode = procode;
	this.proname = proname;
	this.calculation = calculation;
	this.price = price;
	this.quantity = quantity;
	this.priceall = priceall;
	this.location = location;
	this.orderdate = orderdate;
	this.clientname = clientname;
	this.clientcode = clientcode;
	this.ordertype = ordertype;
}


	public C_marRecord(String ordercode, String procode, String proname,
			Double price, Double quantity, Double priceall, String remark1,
			String remark2, String remark3, String remark4, String c_clubmember,
			String location, String paymentmethod,
			String orderdate, String clientname, String clientcode) {
		this.ordercode = ordercode;
		this.procode = procode;
		this.proname = proname;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
		this.location = location;
		this.orderdate = orderdate;
		this.clientname = clientname;
		this.clientcode = clientcode;
		this.c_clubmember = c_clubmember;
		this.paymentmethod = paymentmethod;
	}
	public String getC_clubmember() {
		return c_clubmember;
	}

	public void setC_clubmember(String c_clubmember) {
		this.c_clubmember = c_clubmember;
	}

	public String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}
	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPriceall() {
		return priceall;
	}

	public void setPriceall(Double priceall) {
		this.priceall = priceall;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}

	/**
	 * @param calculation the calculation to set
	 */
	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

	/**
	 * @return the calculation
	 */
	public String getCalculation() {
		return calculation;
	}


	/**
	 * @param ordertype the ordertype to set
	 */
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}


	/**
	 * @return the ordertype
	 */
	public String getOrdertype() {
		return ordertype;
	}


 
}