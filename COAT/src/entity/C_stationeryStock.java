package entity;

/**
 * 文具 库存表
 * Wilson 2013-5-21 10:36:52
 */
public class C_stationeryStock {
	private int id;
	private String procode;
	private String englishname;
	private String chinesename;
	private Double price;
	private String unit;
	private Double quantity;
	private String adddate;
	private String staffonly;
	private String consonly;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	
	
	public C_stationeryStock(int id, String procode, String englishname,
			String chinesename, Double price, String unit, Double quantity,
			String adddate, String staffonly, String consonly, String remark1,
			String remark2, String remark3, String remark4) {
		super();
		this.id = id;
		this.procode = procode;
		this.englishname = englishname;
		this.chinesename = chinesename;
		this.price = price;
		this.unit = unit;
		this.quantity = quantity;
		this.adddate = adddate;
		this.staffonly = staffonly;
		this.consonly = consonly;
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
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
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
	public String getStaffonly() {
		return staffonly;
	}
	public void setStaffonly(String staffonly) {
		this.staffonly = staffonly;
	}
	public String getConsonly() {
		return consonly;
	}
	public void setConsonly(String consonly) {
		this.consonly = consonly;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}