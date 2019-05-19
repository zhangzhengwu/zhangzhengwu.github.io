package entity;

public class Medical_record_staff {
	 private int id;
	 private String staffcode;//staffcode
	 private String name;//员工姓名
	 private String company;//公司
	 private String dept;//部门
	 private String grade; 
	 private String plan;
	 private String packages;
	 private String email;
	 
	 private String maxamount;//最大报销金额
	 private String type;//SP/GP/Dental/Regular
	 private String term; //报销次数
	 private String medical_Normal; //普科
	 private String medical_Special;//专科
	 private String medical_Regular;//口腔科
	 private String medical_Dental;//牙科科
	 
	 private String medical_date; //报销日期
	 private String medical_fee;//报销费用
	 private String medical_month;//结算月份
	 private String amount;//报销数
	 private String return_oraginal;//是否返回报销凭证原件
	 private String SameDay;
	 private String upd_Name;
	 private String upd_Date; 
	 private String add_Name;
	 private String add_Date; 
	 private String sfyx;
	 
	 
	public Medical_record_staff() {
	}
	
	
 
	
	 

	 

 


















	public Medical_record_staff(String staffcode, String name, String company,
			String dept, String grade, String plan, String packages,
			String email, String maxamount, String type, String term,
			String medicalNormal, String medicalSpecial, String medicalRegular,
			String medicalDental, String medicalDate, String medicalFee,
			String medicalMonth, String amount, String returnOraginal,
			String sameDay, String addName,
			String addDate, String updName, String updDate, String sfyx) {
		super();
		this.staffcode = staffcode;
		this.name = name;
		this.company = company;
		this.dept = dept;
		this.grade = grade;
		this.plan = plan;
		this.packages = packages;
		this.email = email;
		this.maxamount = maxamount;
		this.type = type;
		this.term = term;
		medical_Normal = medicalNormal;
		medical_Special = medicalSpecial;
		medical_Regular = medicalRegular;
		medical_Dental = medicalDental;
		medical_date = medicalDate;
		medical_fee = medicalFee;
		medical_month = medicalMonth;
		this.amount = amount;
		return_oraginal = returnOraginal;
		SameDay = sameDay;
		upd_Name = updName;
		upd_Date = updDate;
		add_Name = addName;
		add_Date = addDate;
		this.sfyx = sfyx;
	}



























	public Medical_record_staff(int id, String staffcode, String name,
			String company, String dept, String grade, String plan,
			String packages, String email, String maxamount, String type,
			String term, String medicalNormal, String medicalSpecial,
			String medicalRegular, String medicalDental, String medicalDate,
			String medicalFee, String medicalMonth, String amount,
			String returnOraginal, String sameDay,  String addName, String addDate, String updName, String updDate, String sfyx) {
		super();
		this.id = id;
		this.staffcode = staffcode;
		this.name = name;
		this.company = company;
		this.dept = dept;
		this.grade = grade;
		this.plan = plan;
		this.packages = packages;
		this.email = email;
		this.maxamount = maxamount;
		this.type = type;
		this.term = term;
		medical_Normal = medicalNormal;
		medical_Special = medicalSpecial;
		medical_Regular = medicalRegular;
		medical_Dental = medicalDental;
		medical_date = medicalDate;
		medical_fee = medicalFee;
		medical_month = medicalMonth;
		this.amount = amount;
		return_oraginal = returnOraginal;
		SameDay = sameDay;
		upd_Name = updName;
		upd_Date = updDate;
		add_Name = addName;
		add_Date = addDate;
		this.sfyx = sfyx;
	}



























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
	public String getUpd_Name() {
		return upd_Name;
	}
	public String getMedical_Regular() {
		return medical_Regular;
	}




	public String getMedical_Dental() {
		return medical_Dental;
	}




	public void setMedical_Regular(String medicalRegular) {
		medical_Regular = medicalRegular;
	}




	public void setMedical_Dental(String medicalDental) {
		medical_Dental = medicalDental;
	}




	public String getUpd_Date() {
		return upd_Date;
	}
	public String getSfyx() {
		return sfyx;
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
	public void setUpd_Name(String updName) {
		upd_Name = updName;
	}
	public void setUpd_Date(String updDate) {
		upd_Date = updDate;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}


	/**
	 * @param packages the packages to set
	 */
	public void setPackages(String packages) {
		this.packages = packages;
	}


	/**
	 * @return the packages
	 */
	public String getPackages() {
		return packages;
	}


	/**
	 * @param return_oraginal the return_oraginal to set
	 */
	public void setReturn_oraginal(String return_oraginal) {
		this.return_oraginal = return_oraginal;
	}


	/**
	 * @return the return_oraginal
	 */
	public String getReturn_oraginal() {
		return return_oraginal;
	}






	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}






	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}






	/**
	 * @param medical_month the medical_month to set
	 */
	public void setMedical_month(String medical_month) {
		this.medical_month = medical_month;
	}






	/**
	 * @return the medical_month
	 */
	public String getMedical_month() {
		return medical_month;
	}










	/**
	 * @param add_Name the add_Name to set
	 */
	public void setAdd_Name(String add_Name) {
		this.add_Name = add_Name;
	}










	/**
	 * @return the add_Name
	 */
	public String getAdd_Name() {
		return add_Name;
	}










	/**
	 * @param add_Date the add_Date to set
	 */
	public void setAdd_Date(String add_Date) {
		this.add_Date = add_Date;
	}










	/**
	 * @return the add_Date
	 */
	public String getAdd_Date() {
		return add_Date;
	}
	 
	 
}
