package com.coat.loginrecord.dao;

import java.sql.SQLException;

import com.coat.loginrecord.entity.LoginRecord;

import util.Pager;

/**
 * 
 * 登录/退出记录 接口
 * 2015-09-06
 * @author orlandozhang
 *
 */
public interface LoginRecordDao {

	/**
	 * 登录/退出记录 分页查询
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
	Pager findPager(String[] fields, Pager page, Object... objects) throws Exception;
		
	/**
	 * 保存登录用户记录
	 * 
	 * @param lr
	 * @throws SQLException 
	 */
	public int saveLogin(LoginRecord lr) throws SQLException;
	
	/**
	 * 保存退出用户记录
	 * 
	 * @param lr
	 * @throws SQLException 
	 */
	public int saveLogout(LoginRecord lr) throws SQLException;
	
	
	/**
	 * 通过IP查询最近登录记录
	 * 
	 * @param ipaddress
	 * @return
	 * @throws Exception 
	 */
	public  String  select(String ipaddress) throws Exception;
	/**
	 * 根据用户名统计本年度登录次数
	 * @author kingxu
	 * @date 2015-12-21
	 * @param username
	 * @return
	 * @throws Exception
	 * @return Double[]
	 */
	public Double[] loginUsageByusername(String username) throws Exception;
}
