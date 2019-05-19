package entity;

/**
 * Mark Premium 
 * Wilson 2013-5-21 10:36:52
 */
public class C_marProduct {
	private String procode;
	private String englishname;
	private String chinesename;
	private String unitprice;
	private String c_clubprice;
	private String specialprice;
	private String unit;
	private String quantity;
	private String BLBZ;
	private String updates;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	public C_marProduct(){}
	
	public C_marProduct(String procode, String englishname, String chinesename,
			String unitprice, String c_clubprice, String specialprice,
			String unit, String quantity, String blbz, String updates,
			String remark1, String remark2, String remark3, String remark4) {
		super();
		this.procode = procode;
		this.englishname = englishname;
		this.chinesename = chinesename;
		this.unitprice = unitprice;
		this.c_clubprice = c_clubprice;
		this.specialprice = specialprice;
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
	public String getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}
	public String getC_clubprice() {
		return c_clubprice;
	}
	public void setC_clubprice(String c_clubprice) {
		this.c_clubprice = c_clubprice;
	}
	public String getSpecialprice() {
		return specialprice;
	}
	public void setSpecialprice(String specialprice) {
		this.specialprice = specialprice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BLBZ == null) ? 0 : BLBZ.hashCode());
		result = prime * result
				+ ((c_clubprice == null) ? 0 : c_clubprice.hashCode());
		result = prime * result
				+ ((chinesename == null) ? 0 : chinesename.hashCode());
		result = prime * result
				+ ((englishname == null) ? 0 : englishname.hashCode());
		result = prime * result + ((procode == null) ? 0 : procode.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((remark1 == null) ? 0 : remark1.hashCode());
		result = prime * result + ((remark2 == null) ? 0 : remark2.hashCode());
		result = prime * result + ((remark3 == null) ? 0 : remark3.hashCode());
		result = prime * result + ((remark4 == null) ? 0 : remark4.hashCode());
		result = prime * result
				+ ((specialprice == null) ? 0 : specialprice.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result
				+ ((unitprice == null) ? 0 : unitprice.hashCode());
		result = prime * result + ((updates == null) ? 0 : updates.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final C_marProduct other = (C_marProduct) obj;
		if (BLBZ == null) {
			if (other.BLBZ != null)
				return false;
		} else if (!BLBZ.equals(other.BLBZ))
			return false;
		if (c_clubprice == null) {
			if (other.c_clubprice != null)
				return false;
		} else if (!c_clubprice.equals(other.c_clubprice))
			return false;
		if (chinesename == null) {
			if (other.chinesename != null)
				return false;
		} else if (!chinesename.equals(other.chinesename))
			return false;
		if (englishname == null) {
			if (other.englishname != null)
				return false;
		} else if (!englishname.equals(other.englishname))
			return false;
		if (procode == null) {
			if (other.procode != null)
				return false;
		} else if (!procode.equals(other.procode))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (remark1 == null) {
			if (other.remark1 != null)
				return false;
		} else if (!remark1.equals(other.remark1))
			return false;
		if (remark2 == null) {
			if (other.remark2 != null)
				return false;
		} else if (!remark2.equals(other.remark2))
			return false;
		if (remark3 == null) {
			if (other.remark3 != null)
				return false;
		} else if (!remark3.equals(other.remark3))
			return false;
		if (remark4 == null) {
			if (other.remark4 != null)
				return false;
		} else if (!remark4.equals(other.remark4))
			return false;
		if (specialprice == null) {
			if (other.specialprice != null)
				return false;
		} else if (!specialprice.equals(other.specialprice))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (unitprice == null) {
			if (other.unitprice != null)
				return false;
		} else if (!unitprice.equals(other.unitprice))
			return false;
		if (updates == null) {
			if (other.updates != null)
				return false;
		} else if (!updates.equals(other.updates))
			return false;
		return true;
	}

}