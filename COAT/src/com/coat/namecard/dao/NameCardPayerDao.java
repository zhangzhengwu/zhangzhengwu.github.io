package com.coat.namecard.dao;

import java.sql.SQLException;
import java.util.Map;

public interface NameCardPayerDao {

	
	/**
	 * 获取名片办理情况
	 * @author kingxu
	 * @date 2015-9-24
	 * @param staffcode
	 * @return【used:已办理名片数量,addnum:额外新增数量,payernum:已支付数量】
	 * @return Map<String,Object>
	 * @throws SQLException 
	 */
	Map<String,Object> nameCardUsage(String staffcode) throws SQLException;
	
	/**
	 * 名片办理情况
	 * @author kingxu
	 * @date 2015-9-28
	 * @param staffcode
	 * @return employeeid:员工编号，eqnum:基础限额，used:已办理名片数量(包括自己支付),add:额外新增限额，selfpay:自己支付数量
	 * @throws SQLException
	 * @return Map<String,Object>
	 */
	public Map<String,Object> nameCardMarquee(String staffcode) throws SQLException;
	
	/**
	 * 根据staffcode，UrgentDate获取支付记录
	 * @author kingxu
	 * @date 2016-1-29
	 * @param staffcode
	 * @param urgentDate
	 * @return
	 * @throws SQLException
	 * @return double
	 */
	public double getPayerNumber(String staffcode,String urgentDate) throws SQLException;
	
}
