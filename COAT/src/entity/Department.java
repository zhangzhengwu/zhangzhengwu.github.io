package entity;

public class Department {
 private String Id;
 private String dpt;//部门简称
 private String department;//部门全称
 private String depart_head;//部门第一负责人
 private String depart_head_bak;//部门第二负责人
 private String add_name;//添加人
 private String add_date;//添加时间
 private String upd_name;//修改人
 private String upd_date;//修改时间
 private String sfyx;//是否有效
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
/**
 * 保存Depart
 * @param dpt
 * @param department
 * @param departHead
 * @param departHeadBak
 * @param addName
 * @param addDate
 * @param sfyx
 */
public Department(String dpt, String department, String departHead,
		String departHeadBak, String addName, String addDate, String sfyx) {
	super();
	this.dpt = dpt;
	this.department = department;
	depart_head = departHead;
	depart_head_bak = departHeadBak;
	add_name = addName;
	add_date = addDate;
	this.sfyx = sfyx;
}
/**
 * 修改Depart
 * @param dpt
 * @param department
 * @param departHead
 * @param departHeadBak
 * @param addName
 * @param addDate
 * @param updName
 * @param updDate
 * @param sfyx
 */
public Department(String dpt, String department, String departHead,
		String departHeadBak, String addName, String addDate, String updName,
		String updDate, String sfyx) {
	super();
	this.dpt = dpt;
	this.department = department;
	depart_head = departHead;
	depart_head_bak = departHeadBak;
	add_name = addName;
	add_date = addDate;
	upd_name = updName;
	upd_date = updDate;
	this.sfyx = sfyx;
}
public Department() {
	super();
	// TODO Auto-generated constructor stub
}
/**
 * 查询Department
 * @param id
 * @param dpt
 * @param department
 * @param departHead
 * @param departHeadBak
 * @param addName
 * @param addDate
 * @param updName
 * @param updDate
 * @param sfyx
 */
public Department(String id, String dpt, String department, String departHead,
		String departHeadBak, String addName, String addDate, String updName,
		String updDate, String sfyx) {
	super();
	Id = id;
	this.dpt = dpt;
	this.department = department;
	depart_head = departHead;
	depart_head_bak = departHeadBak;
	add_name = addName;
	add_date = addDate;
	upd_name = updName;
	upd_date = updDate;
	this.sfyx = sfyx;
}
public String getId() {
	return Id;
}
public String getDpt() {
	return dpt;
}
public String getDepartment() {
	return department;
}
public String getDepart_head() {
	return depart_head;
}
public String getDepart_head_bak() {
	return depart_head_bak;
}
public String getAdd_name() {
	return add_name;
}
public String getAdd_date() {
	return add_date;
}
public String getUpd_name() {
	return upd_name;
}
public String getUpd_date() {
	return upd_date;
}
public String getSfyx() {
	return sfyx;
}
public void setId(String id) {
	Id = id;
}
public void setDpt(String dpt) {
	this.dpt = dpt;
}
public void setDepartment(String department) {
	this.department = department;
}
public void setDepart_head(String departHead) {
	depart_head = departHead;
}
public void setDepart_head_bak(String departHeadBak) {
	depart_head_bak = departHeadBak;
}
public void setAdd_name(String addName) {
	add_name = addName;
}
public void setAdd_date(String addDate) {
	add_date = addDate;
}
public void setUpd_name(String updName) {
	upd_name = updName;
}
public void setUpd_date(String updDate) {
	upd_date = updDate;
}
public void setSfyx(String sfyx) {
	this.sfyx = sfyx;
}
	
	
 
 
	
}
