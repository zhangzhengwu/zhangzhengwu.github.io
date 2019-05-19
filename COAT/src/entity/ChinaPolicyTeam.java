package entity;

public class ChinaPolicyTeam {

	private String Id;
	private String company;
	private String policyNo;
	private String product;
	private String clientUnit;
	private String payment;
	private String term;
	private String premium;
	private String rate;
	private String commission;
	private String comiFeeRate;
	private String comiFee;
	private String business;
	private String staffname;
	private String salesOffice;
	
	public ChinaPolicyTeam(){}
	
	public ChinaPolicyTeam(String id, String company, String policyNo,
			String product, String clientUnit, String payment, String term,
			String premium, String rate, String commission, String comiFeeRate,
			String comiFee, String business, String staffname,
			String salesOffice) {
		super();
		Id = id;
		this.company = company;
		this.policyNo = policyNo;
		this.product = product;
		this.clientUnit = clientUnit;
		this.payment = payment;
		this.term = term;
		this.premium = premium;
		this.rate = rate;
		this.commission = commission;
		this.comiFeeRate = comiFeeRate;
		this.comiFee = comiFee;
		this.business = business;
		this.staffname = staffname;
		this.salesOffice = salesOffice;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getClientUnit() {
		return clientUnit;
	}
	public void setClientUnit(String clientUnit) {
		this.clientUnit = clientUnit;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getComiFeeRate() {
		return comiFeeRate;
	}
	public void setComiFeeRate(String comiFeeRate) {
		this.comiFeeRate = comiFeeRate;
	}
	public String getComiFee() {
		return comiFee;
	}
	public void setComiFee(String comiFee) {
		this.comiFee = comiFee;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getSalesOffice() {
		return salesOffice;
	}
	public void setSalesOffice(String salesOffice) {
		this.salesOffice = salesOffice;
	}
	
}
