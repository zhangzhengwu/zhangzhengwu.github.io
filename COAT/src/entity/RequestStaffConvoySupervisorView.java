package entity;

/**
 * RequestStaffConvoySupervisorView entity. @author MyEclipse Persistence Tools
 */

public class RequestStaffConvoySupervisorView {

	// Fields

	private Integer employeeId;
	private String subCode;
	private String supervisorId;
	private String supervisorEmail;
	private String supercisor2Id;
	private String supervisor2Email;
	private String remark;

	// Constructors

	/** default constructor */
	public RequestStaffConvoySupervisorView() {
	}

	/** full constructor */
	public RequestStaffConvoySupervisorView(String subCode,
			String supervisorId, String supervisorEmail, String supercisor2Id,
			String supervisor2Email, String remark) {
		this.subCode = subCode;
		this.supervisorId = supervisorId;
		this.supervisorEmail = supervisorEmail;
		this.supercisor2Id = supercisor2Id;
		this.supervisor2Email = supervisor2Email;
		this.remark = remark;
	}

	// Property accessors

	public Integer getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getSubCode() {
		return this.subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSupervisorId() {
		return this.supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSupervisorEmail() {
		return this.supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	public String getSupercisor2Id() {
		return this.supercisor2Id;
	}

	public void setSupercisor2Id(String supercisor2Id) {
		this.supercisor2Id = supercisor2Id;
	}

	public String getSupervisor2Email() {
		return this.supervisor2Email;
	}

	public void setSupervisor2Email(String supervisor2Email) {
		this.supervisor2Email = supervisor2Email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}