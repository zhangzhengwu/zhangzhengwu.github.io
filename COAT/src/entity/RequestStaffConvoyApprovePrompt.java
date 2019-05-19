package entity;

public class RequestStaffConvoyApprovePrompt {

	private String refno;
	private String emailBody;
	private String emailTitle;
	private String emailAddress;
	private String sendDate;
	private String state;
	private String reamrk;
	
	private String UrgentDate;
	private String shzt;
	private String staff_code;
	private String name;
	private String maildate;
	
	public RequestStaffConvoyApprovePrompt(String refno, String emailBody,
			String emailTitle, String emailAddress, String sendDate,
			String state, String reamrk) {
		super();
		this.refno = refno;
		this.emailBody = emailBody;
		this.emailTitle = emailTitle;
		this.emailAddress = emailAddress;
		this.sendDate = sendDate;
		this.state = state;
		this.reamrk = reamrk;
	}
	
	
	public RequestStaffConvoyApprovePrompt(String refno, String emailBody,
			String emailTitle, String emailAddress, String sendDate,
			String state, String reamrk, String urgentDate, String shzt,String staff_code,String name,String maildate) {
		super();
		this.refno = refno;
		this.emailBody = emailBody;
		this.emailTitle = emailTitle;
		this.emailAddress = emailAddress;
		this.sendDate = sendDate;
		this.state = state;
		this.reamrk = reamrk;
		UrgentDate = urgentDate;
		this.shzt = shzt;
		this.staff_code = staff_code;
		this.name = name;
		this.maildate = maildate;
	}



	public String getMaildate() {
		return maildate;
	}
	public void setMaildate(String maildate) {
		this.maildate = maildate;
	}
	public String getStaff_code() {
		return staff_code;
	}
	public void setStaff_code(String staffCode) {
		staff_code = staffCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrgentDate() {
		return UrgentDate;
	}
	public void setUrgentDate(String urgentDate) {
		UrgentDate = urgentDate;
	}
	public String getShzt() {
		return shzt;
	}
	public void setShzt(String shzt) {
		this.shzt = shzt;
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getEmailTitle() {
		return emailTitle;
	}
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReamrk() {
		return reamrk;
	}
	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}
	
	
}
