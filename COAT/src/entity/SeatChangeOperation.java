package entity;

/**
 * SeatChangeOperation entity. @author MyEclipse Persistence Tools
 */

public class SeatChangeOperation implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2155573864272189686L;
	private Integer id;
	private String refno;
	private String operationstatus;
	private String operationname;
	private String operationdate;

	// Constructors

	/** default constructor */
	public SeatChangeOperation() {
	}

	/** minimal constructor */
	public SeatChangeOperation(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SeatChangeOperation(Integer id, String refno,
			String operationstatus, String operationname, String operationdate) {
		this.id = id;
		this.refno = refno;
		this.operationstatus = operationstatus;
		this.operationname = operationname;
		this.operationdate = operationdate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRefno() {
		return this.refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getOperationstatus() {
		return this.operationstatus;
	}

	public void setOperationstatus(String operationstatus) {
		this.operationstatus = operationstatus;
	}

	public String getOperationname() {
		return this.operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	public String getOperationdate() {
		return this.operationdate;
	}

	public void setOperationdate(String operationdate) {
		this.operationdate = operationdate;
	}

}