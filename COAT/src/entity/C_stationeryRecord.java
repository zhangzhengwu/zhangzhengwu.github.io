package entity;

/**
 * 文具 成交明细表
 *  Wilson 2013-5-21 10:36:52
 */
public class C_stationeryRecord {
	private int id;
	private String ordercode;
	private String procode;
	private String proname;
	private String procname;
	private Double price;
	private Double quantity;
	private Double priceall;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String status;

	/**
	 * 附加字段
	 */
	private String location;
	private String orderdate;
	private String clientname;
	private String clientcode;
	
	public C_stationeryRecord(){}
	
	public C_stationeryRecord(int id, String ordercode, String procode,
			String proname,String procname, Double price, Double quantity, Double priceall,
			String remark1, String remark2, String remark3, String remark4) {
		super();
		this.id = id;
		this.ordercode = ordercode;
		this.procode = procode;
		this.proname = proname;
		this.procname = procname;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	}
	
	public C_stationeryRecord(String ordercode,String procode,
			String proname, Double price, Double quantity,
			Double priceall, String remark1, String remark2, String remark3,
			String remark4, String location, String orderdate,
			String clientname,String clientcode,String procname) {

		this.ordercode = ordercode;
		this.proname = proname;
		this.procode = procode;
		this.clientname = clientname;
		this.clientcode = clientcode;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
		this.location = location;
		this.orderdate = orderdate;
		this.procname = procname;

	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
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

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}
	
	public String getProcname() {
		return procname;
	}

	public void setProcname(String procname) {
		this.procname = procname;
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

	public C_stationeryRecord(int id, String ordercode, String procode,
			String proname, String procname, Double price, Double quantity,
			Double priceall, String remark1, String remark2, String remark3,
			String remark4, String status, String location, String orderdate,
			String clientname, String clientcode) {
		super();
		this.id = id;
		this.ordercode = ordercode;
		this.procode = procode;
		this.proname = proname;
		this.procname = procname;
		this.price = price;
		this.quantity = quantity;
		this.priceall = priceall;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
		this.status = status;
		this.location = location;
		this.orderdate = orderdate;
		this.clientname = clientname;
		this.clientcode = clientcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}