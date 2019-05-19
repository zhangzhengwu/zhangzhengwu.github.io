package com.coat.namecard.entity;
/**
 * 名片印刷历史表
 * @author kingxu
 *
 */
public class Consultant_Master {
	private String StaffNo;//员工号
	private String Name;//拼音名
	private String englishName;//英文名
	private String C_Name ;//中文名
	private String E_DepartmentTitle;//英文部门名称 
	private String C_DepartmentTitle; //中文部门名称
	private String E_ExternalTitle;//外部职位英文头衔
	private String C_ExternalTitle;//外部职位中文头衔
	private String E_EducationTitle;//英文教育头衔
	private String C_EducationTitle;//中文教育头衔
	private String TR_RegNo;
	private String CENo;
	private String MPFNo;
	private String Email;//Email邮箱
	private String DirectLine;
	private String Fax;//传真
	private String MobilePhone;//联系电话
	private String num;//名片数量
	private String lastPosition_E;//上次使用英文职位记录
	private String lastPosition_C;//上次使用中文职位记录
	private String sfcf;//一周内是否重复
	private String grade;//职位简写
	private String recruiterId;//上级code


public String getStaffNo() {
	return StaffNo;
}
public void setStaffNo(String staffNo) {
	StaffNo = staffNo;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public String getC_Name() {
	return C_Name;
}
public void setC_Name(String cName) {
	C_Name = cName;
}
public String getE_DepartmentTitle() {
	return E_DepartmentTitle;
}
public void setE_DepartmentTitle(String eDepartmentTitle) {
	E_DepartmentTitle = eDepartmentTitle;
}
public String getC_DepartmentTitle() {
	return C_DepartmentTitle;
}
public void setC_DepartmentTitle(String cDepartmentTitle) {
	C_DepartmentTitle = cDepartmentTitle;
}
public String getE_ExternalTitle() {
	return E_ExternalTitle;
}
public void setE_ExternalTitle(String eExternalTitle) {
	E_ExternalTitle = eExternalTitle;
}
public String getC_ExternalTitle() {
	return C_ExternalTitle;
}
public void setC_ExternalTitle(String cExternalTitle) {
	C_ExternalTitle = cExternalTitle;
}
public String getE_EducationTitle() {
	return E_EducationTitle;
}
public void setE_EducationTitle(String eEducationTitle) {
	E_EducationTitle = eEducationTitle;
}
public String getC_EducationTitle() {
	return C_EducationTitle;
}
public void setC_EducationTitle(String cEducationTitle) {
	C_EducationTitle = cEducationTitle;
}
public String getTR_RegNo() {
	return TR_RegNo;
}
public void setTR_RegNo(String tRRegNo) {
	TR_RegNo = tRRegNo;
}
public String getCENo() {
	return CENo;
}
public void setCENo(String cENo) {
	CENo = cENo;
}
public String getMPFNo() {
	return MPFNo;
}
public void setMPFNo(String mPFNo) {
	MPFNo = mPFNo;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getDirectLine() {
	return DirectLine;
}
public void setDirectLine(String directLine) {
	DirectLine = directLine;
}
public String getFax() {
	return Fax;
}
public String getGrade() {
	return grade;
}
public String getRecruiterId() {
	return recruiterId;
}
public void setGrade(String grade) {
	this.grade = grade;
}
public void setRecruiterId(String recruiterId) {
	this.recruiterId = recruiterId;
}
public void setFax(String fax) {
	Fax = fax;
}
public String getMobilePhone() {
	return MobilePhone;
}
public void setMobilePhone(String mobilePhone) {
	MobilePhone = mobilePhone;
}
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
}
/**
 * @param lastPosition_E the lastPosition_E to set
 */
public void setLastPosition_E(String lastPosition_E) {
	this.lastPosition_E = lastPosition_E;
}
/**
 * @return the lastPosition_E
 */
public String getLastPosition_E() {
	return lastPosition_E;
}
/**
 * @param lastPosition_C the lastPosition_C to set
 */
public void setLastPosition_C(String lastPosition_C) {
	this.lastPosition_C = lastPosition_C;
}
/**
 * @return the lastPosition_C
 */
public String getLastPosition_C() {
	return lastPosition_C;
}
public void setSfcf(String sfcf) {
	this.sfcf = sfcf;
}
public String getSfcf() {
	return sfcf;
}
 
/**
 * @param englishName the englishName to set
 */
public void setEnglishName(String englishName) {
	this.englishName = englishName;
}
/**
 * @return the englishName
 */
public String getEnglishName() {
	return englishName;
}


}
