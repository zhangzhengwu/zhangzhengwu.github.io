package entity;

public class Medical {
	private String staffcode;
	private String name;
	private String AD_type;
	private String SP_type;
	private String medical_date;
	private String medical_Fee;
	private String entitled_Fee;
	private String Terms_year;
	private String medical_month;
	private String medical_Normal;
	private String medical_Special;
	private String staff_CodeDate;
	private String SameDaye;
	private String Half_Consultant;
	private String email;
	private String upd_Name;
	private String upd_Date;
	private String sfyx;
	private String resignDate;
	private String md;
	private String sfdj;//是否冻结
	
	
	public Medical() {
		
	}
	
	public Medical(String staffcode, String name, String aDType, String entitledFee,String medicalMonth) {
		super();
		this.staffcode = staffcode;
		this.name = name;
		AD_type = aDType;
		entitled_Fee = entitledFee;
		medical_month = medicalMonth;
	}
	
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAD_type() {
		return AD_type;
	}
	public void setAD_type(String aDType) {
		AD_type = aDType;
	}
	public String getSP_type() {
		return SP_type;
	}
	public void setSP_type(String sPType) {
		SP_type = sPType;
	}
	public String getMedical_date() {
		return medical_date;
	}
	public void setMedical_date(String medicalDate) {
		medical_date = medicalDate;
	}
	public String getMedical_Fee() {
		return medical_Fee;
	}
	public void setMedical_Fee(String medicalFee) {
		medical_Fee = medicalFee;
	}
	public String getEntitled_Fee() {
		return entitled_Fee;
	}
	public void setEntitled_Fee(String entitledFee) {
		entitled_Fee = entitledFee;
	}
	public String getTerms_year() {
		return Terms_year;
	}
	public void setTerms_year(String termsYear) {
		Terms_year = termsYear;
	}
	public String getMedical_month() {
		return medical_month;
	}
	public void setMedical_month(String medicalMonth) {
		medical_month = medicalMonth;
	}
	public String getMedical_Normal() {
		return medical_Normal;
	}
	public void setMedical_Normal(String medicalNormal) {
		medical_Normal = medicalNormal;
	}
	public String getMedical_Special() {
		return medical_Special;
	}
	public void setMedical_Special(String medicalSpecial) {
		medical_Special = medicalSpecial;
	}
	public String getStaff_CodeDate() {
		return staff_CodeDate;
	}
	public void setStaff_CodeDate(String staffCodeDate) {
		staff_CodeDate = staffCodeDate;
	}
	public String getSameDaye() {
		return SameDaye;
	}
	public void setSameDaye(String sameDaye) {
		SameDaye = sameDaye;
	}
	public String getHalf_Consultant() {
		return Half_Consultant;
	}
	public void setHalf_Consultant(String halfConsultant) {
		Half_Consultant = halfConsultant;
	}
	public String getUpd_Name() {
		return upd_Name;
	}
	public void setUpd_Name(String updName) {
		upd_Name = updName;
	}
	public String getUpd_Date() {
		return upd_Date;
	}
	public void setUpd_Date(String updDate) {
		upd_Date = updDate;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public String getResignDate() {
		return resignDate;
	}
	public void setResignDate(String resignDate) {
		this.resignDate = resignDate;
	}
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	public void setSfdj(String sfdj) {
		this.sfdj = sfdj;
	}
	public String getSfdj() {
		return sfdj;
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
	
}
