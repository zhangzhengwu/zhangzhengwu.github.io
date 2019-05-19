package entity;

/**
 * 本实体文具 产品表 
 * Wilson 2013-5-21 10:36:52
 */
public class C_stationeryProduct {
	private String procode; 
	private String englishname; 
	private String chinesename; 
	private Double price; 
	private String unit; 
	private Double quantity; 
	private String BLBZ; 
	private String updates; 
	private String remark1; 
	private String remark2; 
	private String remark3; 
	private String remark4;
	public C_stationeryProduct() {
		super();
	} 
	public C_stationeryProduct(String procode, String englishname,
			String chinesename, Double price, String unit, Double quantity,
			String blbz, String updates, String remark1, String remark2,
			String remark3, String remark4) {
		super();
		this.procode = procode;
		this.englishname = englishname;
		this.chinesename = chinesename;
		this.price = price;
		this.unit = unit;
		this.quantity = quantity;
		BLBZ = blbz;
		this.updates = updates;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
		this.remark4 = remark4;
	} 
	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getBLBZ() {
		return BLBZ;
	}

	public void setBLBZ(String blbz) {
		BLBZ = blbz;
	}

	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	
	 
}