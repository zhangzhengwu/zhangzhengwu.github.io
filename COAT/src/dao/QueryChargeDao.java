package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;
import entity.Change_Record;

/**
 * charge 财务信息查询
 * @author kingxu
 *
 */
public interface QueryChargeDao {

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @return
	 */
	public List<Change_Record> queryChargeList(String startDate,String endDate,String staffcode,Page page);
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @return
	 */
	public int getRows(String startDate,String endDate,String staffcode);
	/**
	 * ResultSet
	 * @return
	 */
	public List<Change_Record> selectCharge(String startDate,String endDate,String staffcode);
	/**
	 * deleteChargeRecord
	 * @param name
	 * @param StaffCode
	 * @param AddDate
	 * @return
	 */
	public int deleteChargeRecord(String name,String StaffCode,String AddDate);
}
