package dao;

import java.sql.SQLException;
import java.util.List;

import entity.ConsList;
import util.Page;

public interface ConsListDao {

	/**
	 * 根据条件查询Cons_List
	 * @param employeeId
	 * @param c_Name
	 * @param HKID
	 * @param RecruiterId
	 * @return
	 */
	public List<ConsList> queryReqeustList(String employeeId,String c_Name,String HKID,String recruiterId,Page page);

	/**
	 * 导出Cons_List
	 * @param employeeId
	 * @param c_Name
	 * @param HKID
	 * @param recruiterId
	 * @return
	 */
	public List<ConsList>  downConsList(String employeeId,String c_Name,String HKID,String recruiterId);
	
	
	
	/**
	 * 获取行数
	 * @param employeeId
	 * @param c_Name
	 * @param HKID
	 * @param recruiterId
	 * @return
	 */
	public int getRows(String employeeId, String c_Name,String HKID, String recruiterId);
	
	/**
	 * 批量保存顾问信息列表
	 * @param list
	 * @return
	 * @throws SQLException 
	 */
	public int saveConsultantList(List<List<Object>> list) throws SQLException;
	/**
	 * 根据staffcode查询邮箱
	 * @param staffcode
	 * @return
	 */
	String findMailByCode(String staffcode);
	/**
	 * 根据staffcode查询名字
	 * @param staffcode
	 * @return
	 */
	String findNameByCode(String staffcode);
 
	
}
