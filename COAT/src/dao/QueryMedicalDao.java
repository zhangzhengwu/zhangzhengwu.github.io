package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;
import entity.Medical;
/**
 * 查询QueryMedical列表 DAO类
 * @author Wilson
 *
 */
public interface QueryMedicalDao {
	/**
	 * 查询Medical列表DAO类
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public List<Medical> queryRequstList(String name,String code,String startDate,String endDate,Page page);
	/**
	 * 导出Medical列表DAO类
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//public ResultSet queryRequstListSet(String name,String code,String startDate,String endDate);
	public List<Medical> queryRequstListSet(String name,String code,String startDate,String endDate);
	/**
	 * 导出Medical列表给consultant DAO类
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ResultSet upLoadForConsList(String name,String code,String startDate,String endDate);
	
	
	public List<Medical> upLoadForConsList2(String name,String code,String startDate,String endDate);
	/**
	 * 查询staffcode
	 * @param name
	 * @param staffcode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> selectConsList(String name,String staffcode,String startDate,String endDate);

/**
 *  Medical getRow
 * @param name
 * @param code
 * @param startDate
 * @param endDate
 * @return
 * 调用处 QueryMedicalServlet line in 
 */
	public int getRows(String name, String code,String startDate, String endDate);
}
