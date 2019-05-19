package entity;
/**
 * 
 * RequestRecord
 *
 */
public class RequestRecord {
private String request_date;//提交請求日期
private String code;//用戶ID
private String name;//用戶名稱
private String departmen;//所屬部門
private String quantity;//名片數量
private String upd_name;//更新用戶
private String layout_Type;//类型

public String getLayout_Type() {
	return layout_Type;
}
public void setLayout_Type(String layoutType) {
	layout_Type = layoutType;
}
public String getRequest_date() {
	return request_date;
}
public void setRequest_date(String requestDate) {
	request_date = requestDate;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDepartmen() {
	return departmen;
}
public void setDepartmen(String departmen) {
	this.departmen = departmen;
}
public String getQuantity() {
	return quantity;
}
public void setQuantity(String quantity) {
	this.quantity = quantity;
}
public String getUpd_name() {
	return upd_name;
}
public void setUpd_name(String updName) {
	upd_name = updName;
}

}
