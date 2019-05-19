package entity;

public class ChinaPolicy {
	private String Id;
	private String company;
	private String policyNo;
	private String product;
	private String client;
	private String payment;
	private String term;
	private String effective;
	private String premium;
	private String fby_value;
	private String b_value;
	private String Trainee;
	private String FYB;
	private String staffname;
	private String salesOffice;
	private String handledTitle;
	private String coach;
	private String insured;
	private String acceptedDate;
	private String receiptDate;
	private String policyType;
	private String Remark;
	private String proxyTwo;
	private String acceptedMonth;
	private String PayrollMonth;
	private String commissionDate;
	private String irType;
	private String policyYear;
	
	public ChinaPolicy(){}
	
	public ChinaPolicy(String id, String company, String policyNo,
			String product, String client, String payment, String term,
			String effective, String premium, String fby_value, String b_value,
			String trainee, String fyb, String staffname, String salesOffice,
			String handledTitle, String coach, String insured,
			String acceptedDate, String receiptDate, String policyType,
			String remark, String proxyTwo, String acceptedMonth,
			String payrollMonth, String commissionDate, String irType,
			String policyYear) {
		super();
		Id = id;
		this.company = company;
		this.policyNo = policyNo;
		this.product = product;
		this.client = client;
		this.payment = payment;
		this.term = term;
		this.effective = effective;
		this.premium = premium;
		this.fby_value = fby_value;
		this.b_value = b_value;
		Trainee = trainee;
		FYB = fyb;
		this.staffname = staffname;
		this.salesOffice = salesOffice;
		this.handledTitle = handledTitle;
		this.coach = coach;
		this.insured = insured;
		this.acceptedDate = acceptedDate;
		this.receiptDate = receiptDate;
		this.policyType = policyType;
		Remark = remark;
		this.proxyTwo = proxyTwo;
		this.acceptedMonth = acceptedMonth;
		PayrollMonth = payrollMonth;
		this.commissionDate = commissionDate;
		this.irType = irType;
		this.policyYear = policyYear;
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
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
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
	public String getEffective() {
		return effective;
	}
	public void setEffective(String effective) {
		this.effective = effective;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getFby_value() {
		return fby_value;
	}
	public void setFby_value(String fby_value) {
		this.fby_value = fby_value;
	}
	public String getB_value() {
		return b_value;
	}
	public void setB_value(String b_value) {
		this.b_value = b_value;
	}
	public String getTrainee() {
		return Trainee;
	}
	public void setTrainee(String trainee) {
		Trainee = trainee;
	}
	public String getFYB() {
		return FYB;
	}
	public void setFYB(String fyb) {
		FYB = fyb;
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
	public String getHandledTitle() {
		return handledTitle;
	}
	public void setHandledTitle(String handledTitle) {
		this.handledTitle = handledTitle;
	}
	public String getCoach() {
		return coach;
	}
	public void setCoach(String coach) {
		this.coach = coach;
	}
	public String getInsured() {
		return insured;
	}
	public void setInsured(String insured) {
		this.insured = insured;
	}
	public String getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(String acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getProxyTwo() {
		return proxyTwo;
	}
	public void setProxyTwo(String proxyTwo) {
		this.proxyTwo = proxyTwo;
	}
	public String getAcceptedMonth() {
		return acceptedMonth;
	}
	public void setAcceptedMonth(String acceptedMonth) {
		this.acceptedMonth = acceptedMonth;
	}
	public String getPayrollMonth() {
		return PayrollMonth;
	}
	public void setPayrollMonth(String payrollMonth) {
		PayrollMonth = payrollMonth;
	}
	public String getCommissionDate() {
		return commissionDate;
	}
	public void setCommissionDate(String commissionDate) {
		this.commissionDate = commissionDate;
	}
	public String getIrType() {
		return irType;
	}
	public void setIrType(String irType) {
		this.irType = irType;
	}
	public String getPolicyYear() {
		return policyYear;
	}
	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}
	
}
