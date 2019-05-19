package entity;

public class ELeave {
	private String staffcode;
	private String leaveDate;
	private String  nature;
	private String leaveMonth;
	private String codetype;
 //不會取名
	
	private String remark;
	
	
	
	
	
	public String getStaffcode() {
		return staffcode;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public String getNature() {
		return nature;
	}

	public String getLeaveMonth() {
		return leaveMonth;
	}

	public String getCodetype() {
		return codetype;
	}

	public String getRemark() {
		return remark;
	}

	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public void setLeaveMonth(String leaveMonth) {
		this.leaveMonth = leaveMonth;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
