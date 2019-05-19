package entity;

public class Medical_staff {
	 private int id;
	 private String staffcode;
	 private String name;
	 private String company;//公司
	 private String dept;//部门
	 private String grade; //
	 private String plan;//报销类型
	 private String maxamount;//最大报销金额
	 private String type;//SP、GP
	 private String term; //报销次数
	 private String medical_Normal; //普科
	 private String medical_Special;//专科
	 private String medical_date; //报销日期
	 private String medical_fee;//报销费用
	 private String amount;//报销数
	 private String SameDay;//是否为同一天
	 
	 
 
	
	
	public int getId() {
		return id;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public String getName() {
		return name;
	}
	public String getCompany() {
		return company;
	}
	public String getDept() {
		return dept;
	}
	public String getGrade() {
		return grade;
	}
	public String getPlan() {
		return plan;
	}
	public String getMaxamount() {
		return maxamount;
	}
	public String getType() {
		return type;
	}
	public String getTerm() {
		return term;
	}
	public String getMedical_Normal() {
		return medical_Normal;
	}
	public String getMedical_Special() {
		return medical_Special;
	}
	public String getMedical_date() {
		return medical_date;
	}
	public String getMedical_fee() {
		return medical_fee;
	}
	public String getAmount() {
		return amount;
	}
	public String getSameDay() {
		return SameDay;
	}
 
	public void setId(int id) {
		this.id = id;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public void setMaxamount(String maxamount) {
		this.maxamount = maxamount;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public void setMedical_Normal(String medicalNormal) {
		medical_Normal = medicalNormal;
	}
	public void setMedical_Special(String medicalSpecial) {
		medical_Special = medicalSpecial;
	}
	public void setMedical_date(String medicalDate) {
		medical_date = medicalDate;
	}
	public void setMedical_fee(String medicalFee) {
		medical_fee = medicalFee;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setSameDay(String sameDay) {
		SameDay = sameDay;
	}
 
	 
}
