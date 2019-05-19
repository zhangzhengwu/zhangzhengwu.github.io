package entity;

public class RequestStaffBean {

	private String staff_code;
	private String name;
	private String name_chinese;
	private String title_english;
	private String title_chinese;
	private String external_english;
	private String external_chinese;
	private String academic_title_e;
	private String academic_title_c;
	private String profess_title_e;
	private String profess_title_c;
	private String tr_reg_no;
	private String ce_no;
	private String mpf_no;
	private String e_mail;
	private String direct_line;
	private String fax;
	private String bobile_number;
	private String quantity;
	private String upd_date;
	private String upd_name;
	private String card_type;
	private String layout_type;
	private String location;
	private String ae_consultant;
	private String eliteTeam;
	private String CAM_only;
	private String CFS_only;
	private String CIS_only;
	private String CCL_only;
	private String CFSH_only;
	private String CMS_only;
	private String CFG_only;
	private String Blank_only;
	private String CCIA_only;
	private String CCFSH_only;
	private String CWMC_only;
	private String marks;
	private String urgent;
	private String payer;
	private String urgentDate;
	private String Company;
	
	public RequestStaffBean() {
	}
	
	public RequestStaffBean(String staffCode, String name, String nameChinese,
			String titleEnglish, String titleChinese, String externalEnglish,
			String externalChinese, String academicTitleE,
			String academicTitleC, String professTitleE, String professTitleC,
			String trRegNo, String ceNo, String mpfNo, String eMail,
			String directLine, String fax, String bobileNumber,
			String quantity, String updDate, String updName, String cardType,
			String layoutType, String location, String aeConsultant,
			String eliteTeam, String cAMOnly, String cFSOnly, String cISOnly,
			String cCLOnly, String cFSHOnly, String cMSOnly, String cFGOnly,
			String blankOnly, String cCIAOnly, String cCFSHOnly,
			String cWMCOnly, String marks, String urgent, String payer,
			String urgentDate) {
		staff_code = staffCode;
		this.name = name;
		name_chinese = nameChinese;
		title_english = titleEnglish;
		title_chinese = titleChinese;
		external_english = externalEnglish;
		external_chinese = externalChinese;
		academic_title_e = academicTitleE;
		academic_title_c = academicTitleC;
		profess_title_e = professTitleE;
		profess_title_c = professTitleC;
		tr_reg_no = trRegNo;
		ce_no = ceNo;
		mpf_no = mpfNo;
		e_mail = eMail;
		direct_line = directLine;
		this.fax = fax;
		bobile_number = bobileNumber;
		this.quantity = quantity;
		upd_date = updDate;
		upd_name = updName;
		card_type = cardType;
		layout_type = layoutType;
		this.location = location;
		ae_consultant = aeConsultant;
		this.eliteTeam = eliteTeam;
		CAM_only = cAMOnly;
		CFS_only = cFSOnly;
		CIS_only = cISOnly;
		CCL_only = cCLOnly;
		CFSH_only = cFSHOnly;
		CMS_only = cMSOnly;
		CFG_only = cFGOnly;
		Blank_only = blankOnly;
		CCIA_only = cCIAOnly;
		CCFSH_only = cCFSHOnly;
		CWMC_only = cWMCOnly;
		this.marks = marks;
		this.urgent = urgent;
		this.payer = payer;
		this.urgentDate = urgentDate;
	}
	
	public RequestStaffBean(String staffCode, String name, String nameChinese,
			String titleEnglish, String titleChinese, String externalEnglish,
			String externalChinese, String academicTitleE,
			String academicTitleC, String professTitleE, String professTitleC,
			String trRegNo, String ceNo, String mpfNo, String eMail,
			String directLine, String fax, String bobileNumber,
			String quantity, String updDate, String updName, String cardType,
			String layoutType, String location, String aeConsultant,
			String eliteTeam, String cAMOnly, String cFSOnly, String cISOnly,
			String cCLOnly, String cFSHOnly, String cMSOnly, String cFGOnly,
			String blankOnly, String cCIAOnly, String cCFSHOnly,
			String cWMCOnly, String marks, String urgent, String payer,
			String urgentDate,String Company) {
		staff_code = staffCode;
		this.name = name;
		name_chinese = nameChinese;
		title_english = titleEnglish;
		title_chinese = titleChinese;
		external_english = externalEnglish;
		external_chinese = externalChinese;
		academic_title_e = academicTitleE;
		academic_title_c = academicTitleC;
		profess_title_e = professTitleE;
		profess_title_c = professTitleC;
		tr_reg_no = trRegNo;
		ce_no = ceNo;
		mpf_no = mpfNo;
		e_mail = eMail;
		direct_line = directLine;
		this.fax = fax;
		bobile_number = bobileNumber;
		this.quantity = quantity;
		upd_date = updDate;
		upd_name = updName;
		card_type = cardType;
		layout_type = layoutType;
		this.location = location;
		ae_consultant = aeConsultant;
		this.eliteTeam = eliteTeam;
		CAM_only = cAMOnly;
		CFS_only = cFSOnly;
		CIS_only = cISOnly;
		CCL_only = cCLOnly;
		CFSH_only = cFSHOnly;
		CMS_only = cMSOnly;
		CFG_only = cFGOnly;
		Blank_only = blankOnly;
		CCIA_only = cCIAOnly;
		CCFSH_only = cCFSHOnly;
		CWMC_only = cWMCOnly;
		this.marks = marks;
		this.urgent = urgent;
		this.payer = payer;
		this.urgentDate = urgentDate;
		this.Company = Company;
	}
	
	
	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getStaff_code() {
		return staff_code;
	}
	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName_chinese() {
		return name_chinese;
	}
	public void setName_chinese(String name_chinese) {
		this.name_chinese = name_chinese;
	}
	public String getTitle_english() {
		return title_english;
	}
	public void setTitle_english(String title_english) {
		this.title_english = title_english;
	}
	public String getTitle_chinese() {
		return title_chinese;
	}
	public void setTitle_chinese(String title_chinese) {
		this.title_chinese = title_chinese;
	}
	public String getExternal_english() {
		return external_english;
	}
	public void setExternal_english(String external_english) {
		this.external_english = external_english;
	}
	public String getExternal_chinese() {
		return external_chinese;
	}
	public void setExternal_chinese(String external_chinese) {
		this.external_chinese = external_chinese;
	}
	 
	public String getAcademic_title_e() {
		return academic_title_e;
	}
	public void setAcademic_title_e(String academic_title_e) {
		this.academic_title_e = academic_title_e;
	}
	public String getAcademic_title_c() {
		return academic_title_c;
	}
	public void setAcademic_title_c(String academic_title_c) {
		this.academic_title_c = academic_title_c;
	}
	public String getProfess_title_e() {
		return profess_title_e;
	}
	public void setProfess_title_e(String profess_title_e) {
		this.profess_title_e = profess_title_e;
	}
	public String getProfess_title_c() {
		return profess_title_c;
	}
	public void setProfess_title_c(String profess_title_c) {
		this.profess_title_c = profess_title_c;
	}
	public String getTr_reg_no() {
		return tr_reg_no;
	}
	public void setTr_reg_no(String tr_reg_no) {
		this.tr_reg_no = tr_reg_no;
	}
	public String getCe_no() {
		return ce_no;
	}
	public void setCe_no(String ce_no) {
		this.ce_no = ce_no;
	}
	public String getMpf_no() {
		return mpf_no;
	}
	public void setMpf_no(String mpf_no) {
		this.mpf_no = mpf_no;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getDirect_line() {
		return direct_line;
	}
	public void setDirect_line(String direct_line) {
		this.direct_line = direct_line;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getBobile_number() {
		return bobile_number;
	}
	public void setBobile_number(String bobile_number) {
		this.bobile_number = bobile_number;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUpd_date() {
		return upd_date;
	}
	public void setUpd_date(String upd_date) {
		this.upd_date = upd_date;
	}
	public String getUpd_name() {
		return upd_name;
	}
	public void setUpd_name(String upd_name) {
		this.upd_name = upd_name;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getLayout_type() {
		return layout_type;
	}
	public void setLayout_type(String layout_type) {
		this.layout_type = layout_type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAe_consultant() {
		return ae_consultant;
	}
	public void setAe_consultant(String ae_consultant) {
		this.ae_consultant = ae_consultant;
	}
	public String getCAM_only() {
		return CAM_only;
	}
	public void setCAM_only(String cam_only) {
		CAM_only = cam_only;
	}
	public String getCFS_only() {
		return CFS_only;
	}
	public void setCFS_only(String cfs_only) {
		CFS_only = cfs_only;
	}
	public String getCIS_only() {
		return CIS_only;
	}
	public void setCIS_only(String cis_only) {
		CIS_only = cis_only;
	}
	public String getCCL_only() {
		return CCL_only;
	}
	public void setCCL_only(String ccl_only) {
		CCL_only = ccl_only;
	}
	public String getCFSH_only() {
		return CFSH_only;
	}
	public void setCFSH_only(String cfsh_only) {
		CFSH_only = cfsh_only;
	}
	public String getCMS_only() {
		return CMS_only;
	}
	public void setCMS_only(String cms_only) {
		CMS_only = cms_only;
	}
	public String getCFG_only() {
		return CFG_only;
	}
	public void setCFG_only(String cfg_only) {
		CFG_only = cfg_only;
	}
	public String getBlank_only() {
		return Blank_only;
	}
	public void setBlank_only(String blank_only) {
		Blank_only = blank_only;
	}
	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}
	public String getUrgent() {
		return urgent;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayer() {
		return payer;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getMarks() {
		return marks;
	}
	public void setUrgentDate(String urgentDate) {
		this.urgentDate = urgentDate;
	}
	public String getUrgentDate() {
		return urgentDate;
	}
	/**
	 * @param cCIA_only the cCIA_only to set
	 */
	public void setCCIA_only(String cCIA_only) {
		CCIA_only = cCIA_only;
	}
	/**
	 * @return the cCIA_only
	 */
	public String getCCIA_only() {
		return CCIA_only;
	}
	/**
	 * @param cCFSH_only the cCFSH_only to set
	 */
	public void setCCFSH_only(String cCFSH_only) {
		CCFSH_only = cCFSH_only;
	}
	/**
	 * @return the cCFSH_only
	 */
	public String getCCFSH_only() {
		return CCFSH_only;
	}
	/**
	 * @param eliteTeam the eliteTeam to set
	 */
	public void setEliteTeam(String eliteTeam) {
		this.eliteTeam = eliteTeam;
	}
	/**
	 * @return the eliteTeam
	 */
	public String getEliteTeam() {
		return eliteTeam;
	}
	/**
	 * @param cWMC_only the cWMC_only to set
	 */
	public void setCWMC_only(String cWMC_only) {
		CWMC_only = cWMC_only;
	}
	/**
	 * @return the cWMC_only
	 */
	public String getCWMC_only() {
		return CWMC_only;
	}
}
