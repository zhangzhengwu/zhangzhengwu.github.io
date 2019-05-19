package entity;

public class Staff_listBean {

	 private int Id;
	 private String staffcode;
	 private String company;
	 private String deptid;
	 private String staffname;
	 private String englishname; 
	 private String grade;
	 private String Etype;
	 private String email;
	 private String patient;
	 private String pplan;
	 private String dental;
	 private String dplan;
	 private String remarks;
	 private String enrollmentDate;//生效时间，过试用期时间
	 private String terminationDate;//失效时间，离职时间
	 private String location;
	 
	 
	 
	 
	public Staff_listBean() {
		 
	}
 
	public Staff_listBean(String staffcode, String company, String deptid,
			String staffname, String englishname, String grade, String etype,
			String email, String patient, String pplan, String dental,
			String dplan, String remarks, String enrollmentDate,
			String terminationDate,String location) {
		super();
		this.staffcode = staffcode;
		this.company = company;
		this.deptid = deptid;
		this.staffname = staffname;
		this.englishname = englishname;
		this.grade = grade;
		Etype = etype;
		this.email = email;
		this.patient = patient;
		this.pplan = pplan;
		this.dental = dental;
		this.dplan = dplan;
		this.remarks = remarks;
		this.enrollmentDate = enrollmentDate;
		this.terminationDate = terminationDate;
		this.location = location;
	}

	public Staff_listBean(int id, String staffcode, String company,
			String deptid, String staffname, String englishname, String grade,
			String etype, String email, String patient, String pplan,
			String dental, String dplan, String remarks, String enrollmentDate,
			String terminationDate) {
		super();
		Id = id;
		this.staffcode = staffcode;
		this.company = company;
		this.deptid = deptid;
		this.staffname = staffname;
		this.englishname = englishname;
		this.grade = grade;
		Etype = etype;
		this.email = email;
		this.patient = patient;
		this.pplan = pplan;
		this.dental = dental;
		this.dplan = dplan;
		this.remarks = remarks;
		this.enrollmentDate = enrollmentDate;
		this.terminationDate = terminationDate;
	}

	public int getId() {
		return Id;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public String getCompany() {
		return company;
	}
	public String getDeptid() {
		return deptid;
	}
	public String getStaffname() {
		return staffname;
	}
	public String getEnglishname() {
		return englishname;
	}
	public String getGrade() {
		return grade;
	}
	public String getEtype() {
		return Etype;
	}
	public String getEmail() {
		return email;
	}
	public String getPatient() {
		return patient;
	}
	public String getPplan() {
		return pplan;
	}
	public String getDental() {
		return dental;
	}
	public String getDplan() {
		return dplan;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setId(int id) {
		Id = id;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setEtype(String etype) {
		Etype = etype;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public void setPplan(String pplan) {
		this.pplan = pplan;
	}
	public String getEnrollmentDate() {
		return enrollmentDate;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setEnrollmentDate(String enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	public void setDental(String dental) {
		this.dental = dental;
	}
	public void setDplan(String dplan) {
		this.dplan = dplan;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
}
