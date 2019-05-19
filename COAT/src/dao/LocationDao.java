package dao;

import java.util.List;

import util.Page;

import entity.Location;

public interface LocationDao {
	/**
	 * 查询所有Location
	 * @return
	 */
	public List<Location> queryLocation();
	/**
	 * 查询所有Macau Location
	 * @return
	 */
	public List<Location> queryMacauLocation();
	/**
	 * 按名字查询
	 * @return
	 */
	public List<String> queryLocationName();	
	
	/**
	 * 根据名字查询
	 * @param name
	 * @param page
	 * @return
	 */
	public List<Location> queryRequestList(String name,Page page);
	/**
	 * 获取所有行数
	 * @param name
	 * @return
	 */
	public int  getRows(String name);
	/**
	 * 删除
	 * @param locationId
	 * @return
	 */
	public int DelLocationInfo(String locationId);
	/**
	 * 添加功能
	 * @param location
	 * @return
	 */
	public boolean AddLocation(Location location);
	/**
	 * 防止名字重复添加
	 * @param name
	 * @return
	 */
	public boolean selectName(String name);
}
