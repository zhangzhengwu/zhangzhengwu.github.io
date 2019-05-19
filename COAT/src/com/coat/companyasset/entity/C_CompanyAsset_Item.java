package com.coat.companyasset.entity;

public class C_CompanyAsset_Item {
	
	private Integer itemId;
	private String itemcode;
	private String itemname;
	private Integer itemnum;
	private Integer remainnum;
	private String creator;
	private String createDate;
	private String sfyx;
	public C_CompanyAsset_Item() {
		super();
	}
	public C_CompanyAsset_Item(Integer itemId, String itemcode, String itemname, Integer itemnum, Integer remainnum,
			String creator, String createDate, String sfyx) {
		super();
		this.itemId = itemId;
		this.itemcode = itemcode;
		this.itemname = itemname;
		this.itemnum = itemnum;
		this.remainnum = remainnum;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
	}

	public C_CompanyAsset_Item(String itemcode, String itemname, Integer itemnum, Integer remainnum, String creator,
			String createDate, String sfyx) {
		super();
		this.itemcode = itemcode;
		this.itemname = itemname;
		this.itemnum = itemnum;
		this.remainnum = remainnum;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Integer getItemnum() {
		return itemnum;
	}
	public void setItemnum(Integer itemnum) {
		this.itemnum = itemnum;
	}
	public Integer getRemainnum() {
		return remainnum;
	}
	public void setRemainnum(Integer remainnum) {
		this.remainnum = remainnum;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	

}
