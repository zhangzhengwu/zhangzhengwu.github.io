package entity;

public class Professional_title_Staff {
	private String Id;
	private String prof_title_ename;
	private String prof_title_cname;
	private String add_date;
	private String add_name;
	private String upd_name;
	private String upd_date;
	private String sfyx;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Professional_title_Staff() {
		super();
	}
	public Professional_title_Staff(String profTitleEname,
			String profTitleCname, String addDate, String addName,
			String updName, String updDate, String sfyx) {
		super();
		prof_title_ename = profTitleEname;
		prof_title_cname = profTitleCname;
		add_date = addDate;
		add_name = addName;
		upd_name = updName;
		upd_date = updDate;
		this.sfyx = sfyx;
	}
	public Professional_title_Staff(String id, String profTitleEname,
			String profTitleCname, String addDate, String addName,
			String updName, String updDate, String sfyx) {
		super();
		Id = id;
		prof_title_ename = profTitleEname;
		prof_title_cname = profTitleCname;
		add_date = addDate;
		add_name = addName;
		upd_name = updName;
		upd_date = updDate;
		this.sfyx = sfyx;
	}
	public Professional_title_Staff(String id, String profTitleEname,
			String profTitleCname, String addDate, String addName, String sfyx) {
		super();
		Id = id;
		prof_title_ename = profTitleEname;
		prof_title_cname = profTitleCname;
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
	public String getProf_title_ename() {
		return prof_title_ename;
	}
	public void setProf_title_ename(String prof_title_ename) {
		this.prof_title_ename = prof_title_ename;
	}
	public String getProf_title_cname() {
		return prof_title_cname;
	}
	public void setProf_title_cname(String prof_title_cname) {
		this.prof_title_cname = prof_title_cname;
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
