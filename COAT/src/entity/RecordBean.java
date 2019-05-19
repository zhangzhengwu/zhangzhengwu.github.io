package entity;
/**
 * RecordBean
 * @author kingxu
 *
 */
public class RecordBean {
	private String request_date;
	private String code;
	private String name;
	private String departmen;
	private String quantity;
	private String updname;
	
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartmen() {
		return departmen;
	}
	public void setDepartmen(String departmen) {
		this.departmen = departmen;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUpdname() {
		return updname;
	}
	public void setUpdname(String updname) {
		this.updname = updname;
	}
}
