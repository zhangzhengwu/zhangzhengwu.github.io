package entity;

public class EtraineeList {
	public EtraineeList() {
	}
	private String mapdate;
	private String code;
	private String staffcode;
	private String ldatestart;
	private String ldateend;
	private String daynum="";
	private String D;
	private String lnum;
	private String lyear;
	private String leave_type="";
	private String updname;
	private String upddate;
	private String sfyx;
	public EtraineeList(
			String mapdate,
			String code,
			String staffcode,
			String ldatestart,
			String ldateend,
			String daynum,
			String D,
			String lnum,
			String lyear,
			String leave_type,
			String updname,
			String upddate,
			String sfyx) {
		super();
		this.mapdate = mapdate;
		this.code = code;
		this.staffcode = staffcode;
		this.ldatestart = ldatestart;
		this.ldateend = ldateend;
		this.daynum = daynum;
		this.D = D;
		this.lnum = lnum;
		this.lyear = lyear;
		this.leave_type = leave_type;
		this.updname = updname;
		this.upddate = upddate;
		this.sfyx = sfyx;
		
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getLdatestart() {
		return ldatestart;
	}
	public void setLdatestart(String ldatestart) {
		this.ldatestart = ldatestart;
	}
	public String getLdateend() {
		return ldateend;
	}
	public void setLdateend(String ldateend) {
		this.ldateend = ldateend;
	}
	public String getDaynum() {
		return daynum;
	}
	public void setDaynum(String daynum) {
		this.daynum = daynum;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	public String getLnum() {
		return lnum;
	}
	public void setLnum(String lnum) {
		this.lnum = lnum;
	}
	public String getLyear() {
		return lyear;
	}
	public void setLyear(String lyear) {
		this.lyear = lyear;
	}
	public String getLeave_type() {
		return leave_type;
	}
	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}
	public String getUpdname() {
		return updname;
	}
	public void setUpdname(String updname) {
		this.updname = updname;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public String getMapdate() {
		return mapdate;
	}
	public void setMapdate(String mapdate) {
		this.mapdate = mapdate;
	}
}
