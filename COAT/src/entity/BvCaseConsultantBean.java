package entity;
 
/**
 * BvCaseConsultantBean
 * @author wilson
 *
 */
public class BvCaseConsultantBean {


	private String id;	//id
	private String bitid;	//bitid	
	private String submitdate;	//Submit_Date	
	private String btttype;		//BIT_Type	
	private String consultant_ID ;    //Consultant_ID	
	private double bv; 			//BV
	private double Case_No;		//Case_No
	private String Policy_No;	//Policy_No
	private String Client_Name;	//Client_Name
	private String Comment_Date;
	private String Term;
	private String Premium;
	private String BV_Factor;
	private String Discount_Factor;
	private String CCY;
	private String Consultant_ID1;
	private String Consultant_ID2;
	private String cBV;
	private String Assumed_BV;
	private String Case_Count;
	private String Assumed_Case_Count;
	private String Addition_BV;
	
	public String getComment_Date() {
		return Comment_Date;
	}
	public void setComment_Date(String comment_Date) {
		Comment_Date = comment_Date;
	}
	public String getTerm() {
		return Term;
	}
	public void setTerm(String term) {
		Term = term;
	}
	public String getPremium() {
		return Premium;
	}
	public void setPremium(String premium) {
		Premium = premium;
	}
	public String getBV_Factor() {
		return BV_Factor;
	}
	public void setBV_Factor(String factor) {
		BV_Factor = factor;
	}
	public String getDiscount_Factor() {
		return Discount_Factor;
	}
	public void setDiscount_Factor(String discount_Factor) {
		Discount_Factor = discount_Factor;
	}
	public String getCCY() {
		return CCY;
	}
	public void setCCY(String ccy) {
		CCY = ccy;
	}
	public String getConsultant_ID1() {
		return Consultant_ID1;
	}
	public void setConsultant_ID1(String consultant_ID1) {
		Consultant_ID1 = consultant_ID1;
	}
	public String getConsultant_ID2() {
		return Consultant_ID2;
	}
	public void setConsultant_ID2(String consultant_ID2) {
		Consultant_ID2 = consultant_ID2;
	}
	public String getCBV() {
		return cBV;
	}
	public void setCBV(String cbv) {
		cBV = cbv;
	}
	public String getAssumed_BV() {
		return Assumed_BV;
	}
	public void setAssumed_BV(String assumed_BV) {
		Assumed_BV = assumed_BV;
	}
	public String getCase_Count() {
		return Case_Count;
	}
	public void setCase_Count(String case_Count) {
		Case_Count = case_Count;
	}
	public String getAssumed_Case_Count() {
		return Assumed_Case_Count;
	}
	public void setAssumed_Case_Count(String assumed_Case_Count) {
		Assumed_Case_Count = assumed_Case_Count;
	}
	public String getAddition_BV() {
		return Addition_BV;
	}
	public void setAddition_BV(String addition_BV) {
		Addition_BV = addition_BV;
	}
	public String getBtttype() {
		return btttype;
	}
	public void setBtttype(String btttype) {
		this.btttype = btttype;
	}
	public String getConsultant_ID() {
		return consultant_ID;
	}
	public void setConsultant_ID(String consultant_ID) {
		this.consultant_ID = consultant_ID;
	}
	 
	public String getPolicy_No() {
		return Policy_No;
	}
	public void setPolicy_No(String policy_No) {
		Policy_No = policy_No;
	}
	public String getClient_Name() {
		return Client_Name;
	}
	public void setClient_Name(String client_Name) {
		Client_Name = client_Name;
	}
	public String getBitid() {
		return bitid;
	}
	public void setBitid(String bitid) {
		this.bitid = bitid;
	}
	public String getSubmitdate() {
		return submitdate;
	}
	public void setSubmitdate(String submitdate) {
		this.submitdate = submitdate;
	}
	public double getBv() {
		return bv;
	}
	public void setBv(double bv) {
		this.bv = bv;
	}
	public double getCase_No() {
		return Case_No;
	}
	public void setCase_No(double case_No) {
		Case_No = case_No;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
