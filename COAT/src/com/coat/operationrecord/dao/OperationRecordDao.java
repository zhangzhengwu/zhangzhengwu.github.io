package com.coat.operationrecord.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.operationrecord.entity.OperationRecord;

import util.Pager;

/**
 * 
 * 用户访问地址记录 接口
 * 2015-09-07
 * @author orlandozhang
 *
 */
public interface OperationRecordDao {

	/**
	 * 用户访问地址记录 分页查询
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
		Pager findPager(String[] fields, Pager page, Object... objects) throws Exception;
		/**
		 * 记录用户访问地址
		 * @author kingxu
		 * @date 2015-9-10
		 * @param or
		 * @return
		 * @return int
		 * @throws SQLException 
		 */
		int saveOperationRecord(OperationRecord or) throws SQLException;
		/**
		 * 根据用户名查询最近30天的访问记录
		 * @author kingxu
		 * @date 2015-12-21
		 * @param username
		 * @return
		 * @throws SQLException
		 * @return List<Object[]>
		 * @throws Exception 
		 */
		List<Object[]> findUsageByusername30day(String username) throws SQLException, Exception;
		
		
		
		Connection getConnection() throws SQLException;
		void openConnections() throws SQLException;
		void insert(String string) throws SQLException, ClassNotFoundException;
		
		
		Double[] queryLogReport_Year(String months,String b)throws Exception;
		
		Double[] queryLogReport_Month(String isYesr,String isMonth)throws Exception;
		
		 List<Map<String,Object>> queryLogReport_Day(String startDate,String endDate)throws Exception;
}
