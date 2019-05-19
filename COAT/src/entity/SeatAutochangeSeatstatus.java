package entity;

/**
 * SeatAutochangeSeatstatus entity. @author MyEclipse Persistence Tools
 */

public class SeatAutochangeSeatstatus implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3721718789916990036L;
	private Integer id;
	private String refno;
	private String seatno;
	private Integer status;

	// Constructors

	/** default constructor */
	public SeatAutochangeSeatstatus() {
	}

	/** minimal constructor */
	public SeatAutochangeSeatstatus(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SeatAutochangeSeatstatus(Integer id, String refno, String seatno,
			Integer status) {
		this.id = id;
		this.refno = refno;
		this.seatno = seatno;
		this.status = status;
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

	public String getSeatno() {
		return this.seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}