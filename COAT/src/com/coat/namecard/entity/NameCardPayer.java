package com.coat.namecard.entity;
/**
 * namecard 支付表
 * @author kingxu
 *
 */
public class NameCardPayer {
private String staffCode;//员工号
private String name;//员工名字
private String number;//名片数量
private String amount;//支付金额
private String payer;//支付人
private String Remarks;//付款备注
private String infomed_to_FAD;//FAD通知日期
private String charged_Month;//付款日期
private String addDate;//名片印刷申请日期
private String up_name;//up_name
private String up_date;//up_date
private String sfyx;//是否有效














public NameCardPayer() {
}
public String getStaffCode() {
	return staffCode;
}
public void setStaffCode(String staffCode) {
	this.staffCode = staffCode;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getPayer() {
	return payer;
}
public void setPayer(String payer) {
	this.payer = payer;
}
public String getRemarks() {
	return Remarks;
}
public void setRemarks(String remarks) {
	Remarks = remarks;
}
public String getInfomed_to_FAD() {
	return infomed_to_FAD;
}
public void setInfomed_to_FAD(String infomedToFAD) {
	infomed_to_FAD = infomedToFAD;
}
public String getCharged_Month() {
	return charged_Month;
}
public void setCharged_Month(String chargedMonth) {
	charged_Month = chargedMonth;
}
public String getAddDate() {
	return addDate;
}
public void setAddDate(String addDate) {
	this.addDate = addDate;
}
public String getUp_name() {
	return up_name;
}
public void setUp_name(String upName) {
	up_name = upName;
}
public String getUp_date() {
	return up_date;
}
public void setUp_date(String upDate) {
	up_date = upDate;
}
public String getSfyx() {
	return sfyx;
}
public void setSfyx(String sfyx) {
	this.sfyx = sfyx;
}

}
