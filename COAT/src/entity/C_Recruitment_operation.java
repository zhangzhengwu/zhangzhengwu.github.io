package entity;

public class C_Recruitment_operation {
 private Integer operationId;
 private String  refno;
 private String  operationType;
 private String  operationName;
 private String  operationDate;
 
 
 
public C_Recruitment_operation(Integer operationId, String refno,
		String operationType, String operationName, String operationDate) {
	super();
	this.operationId = operationId;
	this.refno = refno;
	this.operationType = operationType;
	this.operationName = operationName;
	this.operationDate = operationDate;
}


public C_Recruitment_operation() {
	super();
	// TODO Auto-generated constructor stub
}


public Integer getOperationId() {
	return operationId;
}
public void setOperationId(Integer operationId) {
	this.operationId = operationId;
}
public String getRefno() {
	return refno;
}
public void setRefno(String refno) {
	this.refno = refno;
}
public String getOperationType() {
	return operationType;
}
public void setOperationType(String operationType) {
	this.operationType = operationType;
}
public String getOperationName() {
	return operationName;
}
public void setOperationName(String operationName) {
	this.operationName = operationName;
}
public String getOperationDate() {
	return operationDate;
}
public void setOperationDate(String operationDate) {
	this.operationDate = operationDate;
}
 
 
 
}
