package entity;

public class C_Recruitment_detail {

 private Integer detailid;
 private String refno;
 private String mediacode;
 private String medianame;
 private double price;
 private double quantity;
 
 
 public C_Recruitment_detail(){
	 
 }
 
public C_Recruitment_detail(Integer detailid, String refno, String mediacode,
		String medianame, double price, double quantity) {
	super();
	this.detailid = detailid;
	this.refno = refno;
	this.mediacode = mediacode;
	this.medianame = medianame;
	this.price = price;
	this.quantity = quantity;
}
public Integer getDetailid() {
	return detailid;
}
public void setDetailid(Integer detailid) {
	this.detailid = detailid;
}
public String getRefno() {
	return refno;
}
public void setRefno(String refno) {
	this.refno = refno;
}
public String getMediacode() {
	return mediacode;
}
public void setMediacode(String mediacode) {
	this.mediacode = mediacode;
}
public String getMedianame() {
	return medianame;
}
public void setMedianame(String medianame) {
	this.medianame = medianame;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public double getQuantity() {
	return quantity;
}
public void setQuantity(double quantity) {
	this.quantity = quantity;
}
 
 
 
}
