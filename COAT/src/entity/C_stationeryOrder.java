package entity;

/**
 * 文具 成交表
 * Wilson 2013-5-21 10:36:52
 */
public class C_stationeryOrder {
	private String ordercode;
	private String clientname;
	private String clientcode;
	private Double priceall;
	private String orderdate;
	private String location;
	private String departhead;
	private String sfyx;
	private String staffOrCons;
	private String cash;
	private String C_club;
	private String cheque;
	private String cheque_no;
	private String banker;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String status;
	public C_stationeryOrder() {
	}
	public C_stationeryOrder(String ordercode,
			  String clientname,
			  String clientcode,
			  Double priceall,
			  String orderdate,
			  String location,
			  String departhead,
			  String sfyx,
			  String staffOrCons,
			  String remark1,
			  String remark2,
			  String remark3,
			  String remark4) {
				
				this.ordercode = ordercode;
				this. clientname=clientname;
				this. clientcode=clientcode;
				this. priceall=priceall;
				this. orderdate=orderdate;
				this. location=location;
				this. departhead=departhead;
				this. sfyx=sfyx;
				this. staffOrCons=staffOrCons;
				this. remark1=remark1;
				this. remark2=remark2;
				this. remark3=remark3;
				this. remark4=remark4;
				 
			}
	public C_stationeryOrder(String ordercode,
			  String clientname,
			  String clientcode,
			  Double priceall,
			  String orderdate,
			  String location,
			  String departhead,
			  String sfyx,
			  String staffOrCons,
			  String remark1,
			  String remark2,
			  String remark3,
			  String remark4,String status) {
				
				this.ordercode = ordercode;
				this. clientname=clientname;
				this. clientcode=clientcode;
				this. priceall=priceall;
				this. orderdate=orderdate;
				this. location=location;
				this. departhead=departhead;
				this. sfyx=sfyx;
				this. staffOrCons=staffOrCons;
				this. remark1=remark1;
				this. remark2=remark2;
				this. remark3=remark3;
				this. remark4=remark4;
				this. status = status;
				 
			}
	public C_stationeryOrder(String ordercode,
	  String clientname,
	  String clientcode,
	  Double priceall,
	  String orderdate,
	  String location,
	  String departhead,
	  String sfyx,
	  String staffOrCons,
	  String cash,
	  String C_club,
	  String cheque,
	  String cheque_no,
	  String banker,
	  String remark1,
	  String remark2,
	  String remark3,
	  String remark4) {
		
		this.ordercode = ordercode;
		this. clientname=clientname;
		this. clientcode=clientcode;
		this. priceall=priceall;
		this. orderdate=orderdate;
		this. location=location;
		this. departhead=departhead;
		this. sfyx=sfyx;
		this. staffOrCons=staffOrCons;
		this. cash=cash;
		this. C_club=C_club;
		this. cheque=cheque;
		this. cheque_no=cheque_no;
		this. banker=banker;
		this. remark1=remark1;
		this. remark2=remark2;
		this. remark3=remark3;
		this. remark4=remark4;
		 
	}
	
	public String getCheque_no() {
		return cheque_no;
	}
	public void setCheque_no(String chequeNo) {
		cheque_no = chequeNo;
	}
	public String getBanker() {
		return banker;
	}
	public void setBanker(String banker) {
		this.banker = banker;
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
	public void setC_club(String cClub) {
		C_club = cClub;
	}
	public String getCheque() {
		return cheque;
	}
	public void setCheque(String cheque) {
		this.cheque = cheque;
	}
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDeparthead() {
		return departhead;
	}
	public void setDeparthead(String departhead) {
		this.departhead = departhead;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public String getStaffOrCons() {
		return staffOrCons;
	}
	public void setStaffOrCons(String staffOrCons) {
		this.staffOrCons = staffOrCons;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}