package com.coat.additionquota.entity;
/**
 * 查询NameCard Quota 
 * @author kingxu
 *
 */
public class CardquotaBean {
	private String initials;
	private String name;
	private String name_china;
	private String entitled_Quota;
	private String additional;
	private String total_Quota;
	private String quota_Used;
	private String self_Paid;
	private String balance;
	private String addDate;
	
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName_china() {
		return name_china;
	}
	public void setName_china(String name_china) {
		this.name_china = name_china;
	}
	public String getEntitled_Quota() {
		return entitled_Quota;
	}
	public void setEntitled_Quota(String entitled_Quota) {
		this.entitled_Quota = entitled_Quota;
	}
	public String getAdditional() {
		return additional;
	}
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	public String getTotal_Quota() {
		return total_Quota;
	}
	public void setTotal_Quota(String total_Quota) {
		this.total_Quota = total_Quota;
	}
	public String getQuota_Used() {
		return quota_Used;
	}
	public void setQuota_Used(String quota_Used) {
		this.quota_Used = quota_Used;
	}
	public String getSelf_Paid() {
		return self_Paid;
	}
	public void setSelf_Paid(String self_Paid) {
		this.self_Paid = self_Paid;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	/**
	 * @param addDate the addDate to set
	 */
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	/**
	 * @return the addDate
	 */
	public String getAddDate() {
		return addDate;
	}

}
