package entity;

/**
 * CRecruitmentAuxiliary entity. @author MyEclipse Persistence Tools
 */

public class CRecruitmentAuxiliary implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2894468238504538380L;
	private Integer id;
	private String refno;
	private String mediacode;
	private String status;
	private String createdate;
	private String schedate;

	// Constructors

	/** default constructor */
	public CRecruitmentAuxiliary() {
	}

	/** minimal constructor */
	public CRecruitmentAuxiliary(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public CRecruitmentAuxiliary(Integer id, String refno, String mediacode,
			String status, String createdate, String schedate) {
		this.id = id;
		this.refno = refno;
		this.mediacode = mediacode;
		this.status = status;
		this.createdate = createdate;
		this.schedate = schedate;
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

	public String getMediacode() {
		return this.mediacode;
	}

	public void setMediacode(String mediacode) {
		this.mediacode = mediacode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getSchedate() {
		return this.schedate;
	}

	public void setSchedate(String schedate) {
		this.schedate = schedate;
	}

}