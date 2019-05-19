package entity;

public class Recruitment_list {

	private String refno;
	private String createDate;
	private String staffcode;
	private String staffname;
	private String mediatype;
	private String medianame;
	private String date;
	private String contactperson;
	private String contactemail;
	private String price;
	private String chargecode;
	private String handleber;
	private String status;
	
	
	
	
	
	public Recruitment_list(String refno, String createDate, String staffcode,
			String staffname, String mediatype, String medianame, String date,
			String contactperson, String contactemail, String price,
			String chargecode, String handleber,String status) {
		super();
		this.refno = refno;
		this.createDate = createDate;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.mediatype = mediatype;
		this.medianame = medianame;
		this.date = date;
		this.contactperson = contactperson;
		this.contactemail = contactemail;
		this.price = price;
		this.chargecode = chargecode;
		this.handleber = handleber;
		this.status=status;
	}
	public Recruitment_list() {
		super();
		
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getMediatype() {
		return mediatype;
	}
	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}
	public String getMedianame() {
		return medianame;
	}
	public void setMedianame(String medianame) {
		this.medianame = medianame;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public String getContactemail() {
		return contactemail;
	}
	public void setContactemail(String contactemail) {
		this.contactemail = contactemail;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getChargecode() {
		return chargecode;
	}
	public void setChargecode(String chargecode) {
		this.chargecode = chargecode;
	}
	public String getHandleber() {
		return handleber;
	}
	public void setHandleber(String handleber) {
		this.handleber = handleber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
