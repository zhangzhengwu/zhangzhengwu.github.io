package entity;

public class Position_Staff_list {

	private String Id;
	private String position_ename;//职位英文名
	private String position_cname;//职位中文名
	private String add_date;//添加时间
	private String add_name;//添加人
	private String upd_name;//修改人
	private String upd_date;//修改时间
	private String sfyx;//是否有效
	
	
	
	
	
	
	
	
	
	
	
	
	public Position_Staff_list(String id, String positionEname,
			String positionCname, String addDate, String addName,
			String updName, String updDate, String sfyx) {
		super();
		Id = id;
		position_ename = positionEname;
		position_cname = positionCname;
		add_date = addDate;
		add_name = addName;
		upd_name = updName;
		upd_date = updDate;
		this.sfyx = sfyx;
	}
	public Position_Staff_list() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Position_Staff_list(String id, String positionEname,
			String positionCname, String addDate, String addName, String sfyx) {
		super();
		Id = id;
		position_ename = positionEname;
		position_cname = positionCname;
		add_date = addDate;
		add_name = addName;
		this.sfyx = sfyx;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPosition_ename() {
		return position_ename;
	}
	public void setPosition_ename(String position_ename) {
		this.position_ename = position_ename;
	}
	public String getPosition_cname() {
		return position_cname;
	}
	public void setPosition_cname(String position_cname) {
		this.position_cname = position_cname;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getAdd_name() {
		return add_name;
	}
	public void setAdd_name(String add_name) {
		this.add_name = add_name;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public String getUpd_name() {
		return upd_name;
	}
	public String getUpd_date() {
		return upd_date;
	}
	public void setUpd_name(String updName) {
		upd_name = updName;
	}
	public void setUpd_date(String updDate) {
		upd_date = updDate;
	}
}
