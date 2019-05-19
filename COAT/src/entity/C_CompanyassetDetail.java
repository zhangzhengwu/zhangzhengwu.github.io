package entity;

/**
 * CCompanyassetDetail entity. @author MyEclipse Persistence Tools
 */

public class C_CompanyassetDetail implements java.io.Serializable {

	// Fields

 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer detailId;
	private String refno;
	private String itemcode;
	private String itemname;// 2014-6-25 16:53:12 King 新增  Macy要求增加itemname
	private Integer num;

	// Constructors

	/** default constructor */
	public C_CompanyassetDetail() {
	}

	/** full constructor */
	public C_CompanyassetDetail(String refno, String itemcode,String itemname, Integer num) {
		this.refno = refno;
		this.itemcode = itemcode;
		this.itemname = itemname;
		this.num = num;
	}
	
	/** full constructor */
	public C_CompanyassetDetail(String refno, String itemcode,Integer num) {
		this.refno = refno;
		this.itemcode = itemcode;
		this.num = num;
	}

	// Property accessors

	public Integer getDetailId() {
		return this.detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getRefno() {
		return this.refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getItemcode() {
		return this.itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemname() {
		return itemname;
	}

}