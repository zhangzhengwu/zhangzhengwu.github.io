package entity;


public class RequestStaffCompanyView implements java.io.Serializable{





	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String company_ID;
	private String company_Type;
	private String englishName;
	private String chineseName;
	private String reamrk;
	
	
	
	
	public RequestStaffCompanyView() {
	}
	
	
 
	public RequestStaffCompanyView(String company_ID, String company_Type,
			String englishName, String chineseName, String reamrk) {
		super();
		this.company_ID = company_ID;
		this.company_Type = company_Type;
		this.englishName = englishName;
		this.chineseName = chineseName;
		this.reamrk = reamrk;
	}
	public String getCompany_ID() {
		return company_ID;
	}
	public void setCompany_ID(String companyID) {
		company_ID = companyID;
	}
	public String getCompany_Type() {
		return company_Type;
	}
	public void setCompany_Type(String companyType) {
		company_Type = companyType;
	}
	public String getReamrk() {
		return reamrk;
	}
	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}


	public String getEnglishName() {
		return englishName;
	}


	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}


	public String getChineseName() {
		return chineseName;
	}


	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	
	
	
}
