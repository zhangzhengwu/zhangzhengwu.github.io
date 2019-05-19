package entity;

/**
 * CCompanyassetOperation entity. @author MyEclipse Persistence Tools
 */

public class C_CompanyassetOperation implements java.io.Serializable {

	// Fields

	private Integer operationId;
	private String refno;
	private String operationType;
	private String operationName;
	private String operationDate;

	// Constructors

	/** default constructor */
	public C_CompanyassetOperation() {
	}

	/** full constructor */
	public C_CompanyassetOperation(String refno, String operationType,
			String operationName, String operationDate) {
		this.refno = refno;
		this.operationType = operationType;
		this.operationName = operationName;
		this.operationDate = operationDate;
	}

	// Property accessors

	public Integer getOperationId() {
		return this.operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public String getRefno() {
		return this.refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationName() {
		return this.operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationDate() {
		return this.operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

}