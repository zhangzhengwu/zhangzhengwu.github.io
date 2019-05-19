package dao;

import java.sql.SQLException;
import java.util.List;

import util.Pager;
import entity.MedicalOutPutList;

public interface MedicalOptOutDao {

	/**
	 * 分页查询
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
	Pager findMedicalOptOutList(String[] fields, Pager page, Object... objects) throws Exception;
	
	/**
	 * 根据传入对象对数据库进行单一插入操作
	 * @param medicalOutPutList
	 * @return 插入的条数
	 * @throws SQLException 
	 */
	int save(MedicalOutPutList medicalOutPutList) throws SQLException;
	
	/**
	 * @return
	 * @throws SQLException 
	 */
	int delete(String staffcode) throws SQLException;
	
	int ifExist(String staffcode);

	/**
	 * 根据条件导出excel
	 */
	public List<MedicalOutPutList> exportDate(String startdate,String enddate,String staffcode);

	/**
	 * 批量上传SeatList
	 * @param list
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String uploadMedicalOutPutList(List<List<Object>> list,String user) throws Exception;	
	
}
