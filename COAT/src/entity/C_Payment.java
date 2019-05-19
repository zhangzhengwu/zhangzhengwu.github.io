package entity;

/**
 * CPayment entity. @author MyEclipse Persistence Tools
 */

public class C_Payment implements java.io.Serializable {

	// Fields

	private Integer paymentId;
	private String refno;
	private String staffname;//客户名
	private String location;//楼层
	private String type;
	private String saleno; //交易编号
	private String paymentMethod;
	private Double paymentAount;
	private String paymentDate;
	private String handleder;
	private String creator;
	private String createDate;
	private String sfyx;

	// Constructors

	/** default constructor */
	public C_Payment() {
	}
	
	 
	/**
	 * for Stationery
	 * @param refno
	 * @param type
	 * @param paymentMethod
	 * @param paymentAount
	 * @param paymentDate
	 * @param handleder
	 * @param creator
	 * @param createDate
	 * @param sfyx
	 */
	public C_Payment(String refno, String type, String paymentMethod,
			Double paymentAount, String paymentDate, String handleder,
			String creator, String createDate, String sfyx,String staffname,String location,String saleno) {
		super();
		this.refno = refno;
		this.type = type;
		this.paymentMethod = paymentMethod;
		this.paymentAount = paymentAount;
		this.paymentDate = paymentDate;
		this.handleder = handleder;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
		this.staffname=staffname;
		this.location=location;
		this.saleno=saleno;
	}






	/**
	 * 保存 Payment
	 * @param type
	 * @param paymentMethod
	 * @param paymentAount
	 * @param paymentDate
	 * @param handleder
	 * @param sfyx
	 * @param staffname
	 * @param location
	 * @param saleno
	 */
	public C_Payment(String type,String paymentMethod, Double paymentAount,String paymentDate, String handleder, String sfyx,String staffname,String location,String saleno) {
		super();
		this.type = type;
		this.paymentMethod = paymentMethod;
		this.paymentAount = paymentAount;
		this.paymentDate = paymentDate;
		this.handleder = handleder;
		this.sfyx = sfyx;
		this.staffname=staffname;
		this.location=location;
		this.saleno=saleno;
	}
	
	public C_Payment(String paymentMethod, Double paymentAount,String paymentDate, String handleder, String sfyx,String staffname,String location,String saleno) {
		super();
		this.paymentMethod = paymentMethod;
		this.paymentAount = paymentAount;
		this.paymentDate = paymentDate;
		this.handleder = handleder;
		this.sfyx = sfyx;
		this.staffname=staffname;
		this.location=location;
		this.saleno=saleno;
	}
	
/*	public C_Payment(String type,String paymentMethod, Double paymentAount,
			String paymentDate, String handleder, String sfyx) {
		super();
		this.type = type;
		this.paymentMethod = paymentMethod;
		this.paymentAount = paymentAount;
		this.paymentDate = paymentDate;
		this.handleder = handleder;
		this.sfyx = sfyx;
	}*/



	/** full constructor */
	public C_Payment(String refno, String paymentMethod, Double paymentAount,
			String paymentDate, String handleder, String creator,
			String createDate, String sfyx) {
		this.refno = refno;
		this.paymentMethod = paymentMethod;
		this.paymentAount = paymentAount;
		this.paymentDate = paymentDate;
		this.handleder = handleder;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
	}

	// Property accessors

	public Integer getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getRefno() {
		return this.refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getPaymentAount() {
		return this.paymentAount;
	}

	public void setPaymentAount(Double paymentAount) {
		this.paymentAount = paymentAount;
	}

	public String getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getHandleder() {
		return this.handleder;
	}

	public void setHandleder(String handleder) {
		this.handleder = handleder;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}


	public String getStaffname() {
		return staffname;
	}


	public String getLocation() {
		return location;
	}



	public String getSaleno() {
		return saleno;
	}



	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}



	public void setLocation(String location) {
		this.location = location;
	}


	public void setSaleno(String saleno) {
		this.saleno = saleno;
	}

}