package entity;

/**
 * Mark premium 订单表
 *  Wilson 2013-5-21 10:36:52
 */
public class C_marOrder {
	private String ordercode; //订单流水号
	 private String ordertype;//订单用户类型
	private String clientname; //staffName
	private String clientcode; //staffcode
	private String department;//部门
	private Double priceall; //金额总价
	private String orderdate; //订单日期
	private String c_clubmember; //
	private String collectionlocation; //Loction
	private String paymentmethod; //支付方式
	private String chequeno; //支票号
	private String banker; //银行
	private String sfyx; //是否有效
	private String cash;//现金
	private String C_club;//是否C_Club成员
	private String cheque;//支票金额
	private String remark1; 
	private String remark2; 
	private String remark3; 
	private String remark4;
	private String status;//订单状态
	 
	 

	public C_marOrder(String ordercode, String ordertype, String clientname,
			String clientcode, String department, Double priceall,
			String orderdate, String cClubmember, String collectionlocation,
			String paymentmethod, String chequeno, String banker, String sfyx,
			String cash, String cClub, String cheque, String remark1,
			String remark2, String remark3, String remark4,String status) {
		super();
		this.ordercode = ordercode;
		this.ordertype = ordertype;
		this.clientname = clientname;
		this.clientcode = clientcode;
		this.department = department;
		this.priceall = priceall;
		this.orderdate = orderdate;
		c_clubmember = cClubmember;
		this.collectionlocation = collectionlocation;
		this.paymentmethod = paymentmethod;
		this.chequeno = chequeno;
		this.banker = banker;
		this.sfyx = sfyx;
		this.cash = cash;
		C_club = cClub;
		this.cheque = cheque;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
		this.setStatus(status);
	}


	public C_marOrder(){}
	
	
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
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
	public Double getPriceall() {
		return priceall;
	}
	public void setPriceall(Double priceall) {
		this.priceall = priceall;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getC_clubmember() {
		return c_clubmember;
	}
	public void setC_clubmember(String c_clubmember) {
		this.c_clubmember = c_clubmember;
	}
	public String getCollectionlocation() {
		return collectionlocation;
	}
	public void setCollectionlocation(String collectionlocation) {
		this.collectionlocation = collectionlocation;
	}
	public String getPaymentmethod() {
		return paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public String getBanker() {
		return banker;
	}
	public void setBanker(String banker) {
		this.banker = banker;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
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


	public String getCash() {
		return cash;
	}


	public void setCash(String cash) {
		this.cash = cash;
	}


	public String getC_club() {
		return C_club;
	}


	public void setC_club(String c_club) {
		C_club = c_club;
	}


	public String getCheque() {
		return cheque;
	}


	public void setCheque(String cheque) {
		this.cheque = cheque;
	}


	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}


	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		return status;
	}
	 
}