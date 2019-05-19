package entity;

/**
 * SeatColorview entity. @author MyEclipse Persistence Tools
 */

public class SeatColorview implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3418494137576137660L;
	private Integer id;
	private String hssfcolor;
	private String hexcolor;

	// Constructors

	/** default constructor */
	public SeatColorview() {
	}

	/** full constructor */
	public SeatColorview(String hssfcolor, String hexcolor) {
		this.hssfcolor = hssfcolor;
		this.hexcolor = hexcolor;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHssfcolor() {
		return this.hssfcolor;
	}

	public void setHssfcolor(String hssfcolor) {
		this.hssfcolor = hssfcolor;
	}

	public String getHexcolor() {
		return this.hexcolor;
	}

	public void setHexcolor(String hexcolor) {
		this.hexcolor = hexcolor;
	}

}