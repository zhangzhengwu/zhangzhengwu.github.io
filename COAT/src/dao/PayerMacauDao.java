package dao;

import java.sql.ResultSet;
import java.util.List;

import entity.Change_Macau;

public interface PayerMacauDao {
	/**
	 * 删除财务数据
	 * @param name
	 * @param StaffNo
	 * @param AddDate
	 * @return
	 */
	public int deleteChargeRecord(String name,String StaffNo, String AddDate);
	/**
	 * 导出财务表数据
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @return
	 */
	public ResultSet selectCharge(String startDate, String endDate,String staffcode);
	/**
	 * 查询财务表数据
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @return
	 */
	public List<Change_Macau> queryChargeList(String startDate,String endDate, String staffcode);
	
	/**
	 * 验证帮她办理名片的权限
	 * @param payer
	 * @return
	 */
	public String GetPosition(String payer);
	/**
	 * GetEnglishName
	 */
	public String GetEnglishName(String payer);
}
