package entity;
/**
 * Convoy实体类
 * @author King.xu
 *
 */
public class Econvoy {
private String staffcode;//员工编号
private String staffname;//员工姓名
private String personType;//员工类型
private String location;//办公地点
private String isC_Club;//是否是C_Club成员






public Econvoy(){
	
}










public Econvoy(String staffcode, String staffname, String personType,
		String location, String isCClub) {
	super();
	this.staffcode = staffcode;
	this.staffname = staffname;
	this.personType = personType;
	this.location = location;
	isC_Club = isCClub;
}
public String getStaffcode() {
	return staffcode;
}
public String getStaffname() {
	return staffname;
}
public String getPersonType() {
	return personType;
}
public String getLocation() {
	return location;
}
public String getIsC_Club() {
	return isC_Club;
}
public void setStaffcode(String staffcode) {
	this.staffcode = staffcode;
}
public void setStaffname(String staffname) {
	this.staffname = staffname;
}
public void setPersonType(String personType) {
	this.personType = personType;
}
public void setLocation(String location) {
	this.location = location;
}
public void setIsC_Club(String isCClub) {
	isC_Club = isCClub;
}
 
	
	
}
