package dao;

import javax.servlet.jsp.jstl.sql.Result;

import entity.C_Payment;

public interface C_StationeryDao {
	
	int ready(String order, String status,String user,String to,String location);
	
	int completed(String order, String status,String user ,String type,String paymethod,
			String payamount,String payDate,String handle,String staffname,String location,String saleno);
	
	/**
	 * 根据时间范围导出Reporting
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Result downReportingForStationery(String startDate,String endDate);
	/**
	 * 获取Stationery 库存
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result findStore(String startDate, String endDate);
	
	/**
	 * 获取Stationery库存使用量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result chargeStore(String startDate, String endDate);
}
