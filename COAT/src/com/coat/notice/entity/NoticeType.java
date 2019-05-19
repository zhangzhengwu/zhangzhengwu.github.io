package com.coat.notice.entity;

public class NoticeType {
	private String type;
	private String name;
	
	public NoticeType(String type) {
		super();
		this.type = type;
	}
	public NoticeType(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public NoticeType() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
