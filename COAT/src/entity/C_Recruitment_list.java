package entity;
public class C_Recruitment_list {
    private Integer id;
    private String mediacode;
    private String mediatype;
    private String medianame;
    private String price;
    private int quantity;
    private String unit;
    private String adddate;
    private String addname;
    private String remark;
    private String sfyx;
 
public C_Recruitment_list(){}
 

public C_Recruitment_list(Integer id, String mediacode, String mediatype,
			String medianame, String price, int quantity, String unit,
			String adddate, String addname, String remark, String sfyx) {
		super();
		this.id = id;
		this.mediacode = mediacode;
		this.mediatype = mediatype;
		this.medianame = medianame;
		this.price = price;
		this.quantity = quantity;
		this.unit = unit;
		this.adddate = adddate;
		this.addname = addname;
		this.remark = remark;
		this.sfyx = sfyx;
	}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getMediacode() {
	return mediacode;
}
public void setMediacode(String mediacode) {
	this.mediacode = mediacode;
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
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public String getUnit() {
	return unit;
}
public void setUnit(String unit) {
	this.unit = unit;
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
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getSfyx() {
	return sfyx;
}
public void setSfyx(String sfyx) {
	this.sfyx = sfyx;
}
 
 
 
 
 


 
 
}
