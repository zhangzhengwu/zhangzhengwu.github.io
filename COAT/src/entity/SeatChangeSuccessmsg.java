package entity;

public class SeatChangeSuccessmsg {
	private int id;
	private String staffcode;
	private String staffname;
	private String seatnobefore;
	private String locationbefore;
	private String floorbefore;
	private String seatnoafter;
	private String locationafter;
	private String floorafter;
	private String exchangetime;
	private int status;
	private String remark;
	public SeatChangeSuccessmsg() {
		super();
	}
	public SeatChangeSuccessmsg(int id, String staffcode, String staffname, String seatnobefore, String locationbefore,
			String floorbefore, String seatnoafter, String locationafter, String floorafter, String exchangetime,
			int status, String remark) {
		super();
		this.id = id;
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.seatnobefore = seatnobefore;
		this.locationbefore = locationbefore;
		this.floorbefore = floorbefore;
		this.seatnoafter = seatnoafter;
		this.locationafter = locationafter;
		this.floorafter = floorafter;
		this.exchangetime = exchangetime;
		this.status = status;
		this.remark = remark;
	}

	public SeatChangeSuccessmsg(String staffcode, String staffname, String seatnobefore, String locationbefore,
			String floorbefore, String seatnoafter, String locationafter, String floorafter, String exchangetime) {
		super();
		this.staffcode = staffcode;
		this.staffname = staffname;
		this.seatnobefore = seatnobefore;
		this.locationbefore = locationbefore;
		this.floorbefore = floorbefore;
		this.seatnoafter = seatnoafter;
		this.locationafter = locationafter;
		this.floorafter = floorafter;
		this.exchangetime = exchangetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getSeatnobefore() {
		return seatnobefore;
	}
	public void setSeatnobefore(String seatnobefore) {
		this.seatnobefore = seatnobefore;
	}
	public String getLocationbefore() {
		return locationbefore;
	}
	public void setLocationbefore(String locationbefore) {
		this.locationbefore = locationbefore;
	}
	public String getFloorbefore() {
		return floorbefore;
	}
	public void setFloorbefore(String floorbefore) {
		this.floorbefore = floorbefore;
	}
	public String getSeatnoafter() {
		return seatnoafter;
	}
	public void setSeatnoafter(String seatnoafter) {
		this.seatnoafter = seatnoafter;
	}
	public String getLocationafter() {
		return locationafter;
	}
	public void setLocationafter(String locationafter) {
		this.locationafter = locationafter;
	}
	public String getFloorafter() {
		return floorafter;
	}
	public void setFloorafter(String floorafter) {
		this.floorafter = floorafter;
	}
	public String getExchangetime() {
		return exchangetime;
	}
	public void setExchangetime(String exchangetime) {
		this.exchangetime = exchangetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
