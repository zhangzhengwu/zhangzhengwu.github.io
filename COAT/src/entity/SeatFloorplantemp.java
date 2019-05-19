package entity;

/**
 * SeatFloorplantemp entity. @author MyEclipse Persistence Tools
 */

public class SeatFloorplantemp implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer teamcode;
	private String floor;
	private String ddtreeHead;
	private String joinDate;
	private String hssfcolor;
	private Integer hexcolor;
	private String rbgColor;

	// Constructors

	/** default constructor */
	public SeatFloorplantemp() {
	}

	/** full constructor */
	public SeatFloorplantemp(Integer teamcode, String floor, String ddtreeHead,
			String joinDate, String hssfcolor, Integer hexcolor, String rbgColor) {
		this.teamcode = teamcode;
		this.floor = floor;
		this.ddtreeHead = ddtreeHead;
		this.joinDate = joinDate;
		this.hssfcolor = hssfcolor;
		this.hexcolor = hexcolor;
		this.rbgColor = rbgColor;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeamcode() {
		return this.teamcode;
	}

	public void setTeamcode(Integer teamcode) {
		this.teamcode = teamcode;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDdtreeHead() {
		return this.ddtreeHead;
	}

	public void setDdtreeHead(String ddtreeHead) {
		this.ddtreeHead = ddtreeHead;
	}

	public String getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getHssfcolor() {
		return this.hssfcolor;
	}

	public void setHssfcolor(String hssfcolor) {
		this.hssfcolor = hssfcolor;
	}

	public Integer getHexcolor() {
		return this.hexcolor;
	}

	public void setHexcolor(Integer hexcolor) {
		this.hexcolor = hexcolor;
	}

	public String getRbgColor() {
		return this.rbgColor;
	}

	public void setRbgColor(String rbgColor) {
		this.rbgColor = rbgColor;
	}

}