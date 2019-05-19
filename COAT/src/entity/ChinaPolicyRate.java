package entity;

public class ChinaPolicyRate {

	private String Id;
	private String company;
	private String policyNo;
	private String product;
	private String client;
	private String payment;
	private String term;
	private String effective;
	private String premium;
	private String rate;
	private String rate2;
	private String Trainee;
	private String FYB;
	private String commission;
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
	private String licenseNo;
	private String idCardNo;
	private String bankAccount;
	
	public ChinaPolicyRate(){}
	
	public ChinaPolicyRate(String id, String company, String policyNo,
			String product, String client, String payment, String term,
			String effective, String premium, String rate, String rate2,
			String Trainee,String FYB,String commission,
			String staffname, String salesOffice, String handledTitle,
			String coach, String insured, String acceptedDate,
			String receiptDate, String policyType, String remark,
			String proxyTwo, String acceptedMonth, String payrollMonth,
			String commissionDate, String irType, String policyYear,
			String licenseNo, String idCardNo, String bankAccount) {
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
		this.rate = rate;
		this.rate2 = rate2;
		this.Trainee = Trainee;
		this.FYB = FYB;
		this.commission = commission;
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
		this.licenseNo = licenseNo;
		this.idCardNo = idCardNo;
		this.bankAccount = bankAccount;
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
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRate2() {
		return rate2;
	}
	public void setRate2(String rate2) {
		this.rate2 = rate2;
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
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}
	
}
