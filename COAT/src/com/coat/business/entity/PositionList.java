package com.coat.business.entity;

public class PositionList {
	private int Id;
	private String position_ename;
	private String position_cname;
	private String add_date;
	private String add_name;
	private String sfyx;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getPosition_ename() {
		return position_ename;
	}
	public void setPosition_ename(String position_ename) {
		this.position_ename = position_ename;
	}
	public String getPosition_cname() {
		return position_cname;
	}
	public void setPosition_cname(String position_cname) {
		this.position_cname = position_cname;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getAdd_name() {
		return add_name;
	}
	public void setAdd_name(String add_name) {
		this.add_name = add_name;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
}
