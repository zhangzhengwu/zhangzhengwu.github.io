package entity;

public class ConsList {

	private String employeeId;
	private String alias;
	private String c_Name;
	private String joinDate;
	private String directLine;
	private String email;
	private String employeeName;
	private String externalPosition;
	private String grade;
	private String HKID;
	private String groupDateJoin;
	private String location;
	private String mobile;
	private String position;
	private String c_PositionName;
	private String e_PositionName;  
	private String recruiterId;
	private String recruiterName;
	private String telephoneNo;
	private String ADTreeHead;
	private String DDTreeHead;
	
	public ConsList() {
		
	}
	public ConsList(String employeeId, String alias, String cName,
			String joinDate, String directLine, String email,
			String employeeName, String externalPosition,
			String hKID, String groupDateJoin, String location,
			String cPositionName, String ePositionName,
			String recruiterId, String recruiterName, String telephoneNo,
			String aDTreeHead, String dDTreeHead) {
		super();
		this.employeeId = employeeId;
		this.alias = alias;
		c_Name = cName;
		this.joinDate = joinDate;
		this.directLine = directLine;
		this.email = email;
		this.employeeName = employeeName;
		this.externalPosition = externalPosition;
		HKID = hKID;
		this.groupDateJoin = groupDateJoin;
		this.location = location;
		c_PositionName = cPositionName;
		e_PositionName = ePositionName;
		this.recruiterId = recruiterId;
		this.recruiterName = recruiterName;
		this.telephoneNo = telephoneNo;
		ADTreeHead = aDTreeHead;
		DDTreeHead = dDTreeHead;
	}
	
	
	public ConsList(String employeeId, String employeeName) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getC_Name() {
		return c_Name;
	}
	public void setC_Name(String cName) {
		c_Name = cName;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getDirectLine() {
		return directLine;
	}
	public void setDirectLine(String directLine) {
		this.directLine = directLine;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getExternalPosition() {
		return externalPosition;
	}
	public void setExternalPosition(String externalPosition) {
		this.externalPosition = externalPosition;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getHKID() {
		return HKID;
	}
	public void setHKID(String HKID) {
		this.HKID = HKID;
	}
	public String getGroupDateJoin() {
		return groupDateJoin;
	}
	public void setGroupDateJoin(String groupDateJoin) {
		this.groupDateJoin = groupDateJoin;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getC_PositionName() {
		return c_PositionName;
	}
	public void setC_PositionName(String cPositionName) {
		c_PositionName = cPositionName;
	}
	public String getE_PositionName() {
		return e_PositionName;
	}
	public void setE_PositionName(String ePositionName) {
		e_PositionName = ePositionName;
	}
	public String getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(String recruiterId) {
		this.recruiterId = recruiterId;
	}
	public String getRecruiterName() {
		return recruiterName;
	}
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getADTreeHead() {
		return ADTreeHead;
	}
	public void setADTreeHead(String aDTreeHead) {
		ADTreeHead = aDTreeHead;
	}
	public String getDDTreeHead() {
		return DDTreeHead;
	}
	public void setDDTreeHead(String dDTreeHead) {
		DDTreeHead = dDTreeHead;
	}
	
}
