package entity;

public class Missingpayment {
	private int Id;
	private String receivedDate;
	private String principal;
	private String staffcode;
	private String staffcode2;
	private String policyno;
	private String clientname;
	private String missingsum;
	private String premiumDate;
	private String reason;
	private String datafrom;
	private String remark;
	private String adddate;
	private String addname;
	private String note;
	private String sfyx;
	public Missingpayment(){};
	public Missingpayment(int id, String receivedDate, String principal,
			String staffcode, String staffcode2, String policyno,
			String clientname, String missingsum, String premiumDate,
			String reason, String datafrom, String remark, String adddate,
			String addname, String note,String sfyx) {
		super();
		Id = id;
		this.receivedDate = receivedDate;
		this.principal = principal;
		this.staffcode = staffcode;
		this.staffcode2 = staffcode2;
		this.policyno = policyno;
		this.clientname = clientname;
		this.missingsum = missingsum;
		this.premiumDate = premiumDate;
		this.reason = reason;
		this.datafrom = datafrom;
		this.remark = remark;
		this.adddate = adddate;
		this.addname = addname;
		this.note = note;
		this. sfyx = sfyx;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getStaffcode2() {
		return staffcode2;
	}
	public void setStaffcode2(String staffcode2) {
		this.staffcode2 = staffcode2;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getMissingsum() {
		return missingsum;
	}
	public void setMissingsum(String missingsum) {
		this.missingsum = missingsum;
	}
	public String getPremiumDate() {
		return premiumDate;
	}
	public void setPremiumDate(String premiumDate) {
		this.premiumDate = premiumDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDatafrom() {
		return datafrom;
	}
	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
}
