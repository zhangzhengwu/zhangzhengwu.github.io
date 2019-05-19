package entity;

public class Location {
	private int locationId;;
	private String name;
	private String realName;
	private String locationInfo;
	private String remark;
	

	public int getLocationId() {
			return locationId;
		}
	
	
		public void setLocationId(int locationId) {
			this.locationId = locationId;
		}
	
	
	public Location() {
	}


	public Location(String name, String realName, String locationInfo, String remark) {
		super();
		this.name = name;
		this.realName = realName;
		this.locationInfo = locationInfo;
		this.remark = remark;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * @return the locationInfo
	 */
	public String getLocationInfo() {
		return locationInfo;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
/**
 * @param realName the realName to set
 */
public void setRealName(String realName) {
	this.realName = realName;
}
/**
 * @param locationInfo the locationInfo to set
 */
public void setLocationInfo(String locationInfo) {
	this.locationInfo = locationInfo;
}
/**
 * @param remark the remark to set
 */
public void setRemark(String remark) {
	this.remark = remark;
}
}
