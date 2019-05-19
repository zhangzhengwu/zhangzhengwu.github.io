package entity;

/**
 * CRoomsettingOperation entity. @author MyEclipse Persistence Tools
 */

public class C_StationeryOperation implements java.io.Serializable {

	// Fields

	private Integer operationId;
	private String ordercode;
	private String operationType;
	private String operationName;
	private String operationDate;

	// Constructors

	/** default constructor */
	public C_StationeryOperation() {
	}

	/** full constructor */
	public C_StationeryOperation(String ordercode, String operationType,
			String operationName, String operationDate) {
		this.ordercode = ordercode;
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

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

}