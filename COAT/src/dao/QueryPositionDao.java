package dao;

import java.util.List;

import entity.Position_list;
/**
 *  Position list excel表
 * @author kingxu
 *
 */
public interface QueryPositionDao {
	/**
	 * 查询 Position list excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public List<Position_list> queryPosition();
	
	/**
	 * 查询position list name
	 * @return
	 */
	public List<String > queryPositionEName();
	
	
	public Position_list queryPosition(String position_ename);
}
