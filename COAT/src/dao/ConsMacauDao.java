package dao;

import java.util.List;
import util.Page;
import entity.ConsListMacau;

public interface ConsMacauDao {

	/**
	 * 查询ConsListMacau
	 * @param employeeId
	 * @param c_Name
	 * @param HKID
	 * @param recruiterId
	 * @param page
	 * @return
	 */
	public List<ConsListMacau> queryReqeustList(String employeeId,String c_Name,String HKID,String recruiterId,Page page);
	

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
	 */
	public int saveMacauList(List<List<Object>> list);
}
