package entity;

/**
 * CCompanyassetItem entity. @author MyEclipse Persistence Tools
 */

public class C_CompanyassetItem implements java.io.Serializable {

	// Fields

	private int itemId;
	private String itemcode;
	private String itemname;
	private int itemnum;
	private int remainnum;
	private String creator;
	private String createDate;
	private String sfyx;

	// Constructors

	/** default constructor */
	public C_CompanyassetItem() {
	}

	/** full constructor */
	public C_CompanyassetItem(int itemId,String itemcode, String itemname, int itemnum,
			int remainnum, String creator, String createDate, String sfyx) {
		this.itemId = itemId;
		this.itemcode = itemcode;
		this.itemname = itemname;
		this.itemnum = itemnum;
		this.remainnum = remainnum;
		this.creator = creator;
		this.createDate = createDate;
		this.sfyx = sfyx;
	}

	// Property accessors

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemcode() {
		return this.itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public int getItemnum() {
		return this.itemnum;
	}

	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}

	public int getRemainnum() {
		return this.remainnum;
	}

	public void setRemainnum(int remainnum) {
		this.remainnum = remainnum;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

}