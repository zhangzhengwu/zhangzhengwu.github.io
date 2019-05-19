package entity;

public class Staff_Master {
private String staffcode;//staffcode
private String packages;//报销类型
private String medicalDate;//报销时间
private String total_amount;//累计报销额
private String total_Normal;//累计普科报销次数
private String total_Special;//累计专科报销次数
private String total_Regular;//累计专科报销次数




public Staff_Master() {
}

 
public Staff_Master(String staffcode, String packages, String medicalDate,
		String totalAmount, String totalNormal, String totalSpecial,
		String totalRegular) {
	super();
	this.staffcode = staffcode;
	this.packages = packages;
	this.medicalDate = medicalDate;
	total_amount = totalAmount;
	total_Normal = totalNormal;
	total_Special = totalSpecial;
	total_Regular = totalRegular;
}


public String getStaffcode() {
	return staffcode;
}
public String getPackages() {
	return packages;
}
public String getMedicalDate() {
	return medicalDate;
}
public String getTotal_amount() {
	return total_amount;
}
public String getTotal_Normal() {
	return total_Normal;
}
public String getTotal_Special() {
	return total_Special;
}
public void setStaffcode(String staffcode) {
	this.staffcode = staffcode;
}
public void setPackages(String packages) {
	this.packages = packages;
}
public void setMedicalDate(String medicalDate) {
	this.medicalDate = medicalDate;
}
public void setTotal_amount(String totalAmount) {
	total_amount = totalAmount;
}
public void setTotal_Normal(String totalNormal) {
	total_Normal = totalNormal;
}
public void setTotal_Special(String totalSpecial) {
	total_Special = totalSpecial;
}

/**
 * @param total_Regular the total_Regular to set
 */
public void setTotal_Regular(String total_Regular) {
	this.total_Regular = total_Regular;
}

/**
 * @return the total_Regular
 */
public String getTotal_Regular() {
	return total_Regular;
}

	
}
