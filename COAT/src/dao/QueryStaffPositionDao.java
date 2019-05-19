package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;

import entity.Position_Staff_list;
/**
 *  Position list staff excel表
 * @author kingxu
 *
 */
public interface QueryStaffPositionDao {
	/**
	 * 查询 Position list staff excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public List<Position_Staff_list> queryPosition();
	/**
	 * 判断是否已经存在该Position_ename
	 * @param positionEname
	 * @return
	 */
	public boolean findPositionName(String positionEname);
	
	public Position_Staff_list queryStaffPositionbyName(String position_ename);
	/**
	 * 根据Position_ename 查询需要修改的Position 
	 * @param position_ename
	 * @return
	 */
	public Position_Staff_list queryStaffPositionId(String position_Id);
	
	/**
	 * 保存Position_staff_list
	 * @param ps
	 * @return
	 */
	public int savePosition(Position_Staff_list pss);
	/**
	 * 修改Position信息
	 * @param pss
	 * @return
	 */
	public int ModifyPosition(Position_Staff_list pss);
	
	
	
	public Position_Staff_list queryStaffPosition(String position_ename);
	/**
	 * 根据条件查询条数
	 * @param startDate
	 * @param endDate
	 * @param position_ename
	 * @param sfyx
	 * @return
	 */
	public int getRow(String startDate,String endDate,String position_ename,String sfyx);
	/**
	 * 分页查询Position
	 * @param startDate
	 * @param endDate
	 * @param position_ename
	 * @param sfyx
	 * @param page
	 * @return
	 */
	public List<Position_Staff_list>findPosition(String startDate,String endDate,String position_ename,String sfyx,Page page);
	/**
	 * 导出StaffPosition
	 * @param startDate
	 * @param endDate
	 * @param position_ename
	 * @param sfyx
	 * @return
	 */
	public ResultSet DownPosition(String startDate,String endDate,String position_ename,String sfyx);
	
	
}
