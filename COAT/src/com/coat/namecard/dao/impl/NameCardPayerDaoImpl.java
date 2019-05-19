package com.coat.namecard.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.Util;

import com.coat.namecard.dao.NameCardPayerDao;

import dao.common.BaseDao;

import entity.NameCardConvoy;

public class NameCardPayerDaoImpl extends BaseDao implements NameCardPayerDao {
	Logger logger=Logger.getLogger(this.getClass());
	
	
	
	
	/**
	 * 名片使用情况
	 * @author kingxu
	 * @date 2015-9-24
	 * @return
	 * @return Map<String,Object>
	 * @throws SQLException 
	 */
	public Map<String,Object> nameCardUsage(String staffcode) throws SQLException{
		Map<String,Object> result=null;
		try{
			String sql=" select * from (select if(used is null,0,used)as used,if(addnum is null,0,addnum) as addnum,if(payernum is null,0,payernum)as payernum from ("+
						" select code, sum(quantity) as used from req_record where code='"+staffcode+"'  and YEAR(request_date) = YEAR(NOW())  "+
						" )a  left  join ("+
						"	select initials,sum(additional) as addnum from nq_additional where sfyx='Y' and  initials='"+staffcode+"' and year(add_date)=year(now())"+  
						" )b on(1=1) "+
						" left join ("+
						"	select payer, sum(Number)as payernum from change_record where sfyx='Y' and YEAR(up_date) = YEAR(NOW()) and payer='"+staffcode+"'"+
						" )c on (1=1))x";
			logger.info("查询【"+staffcode+"】名片办理情况 时 sql ===="+sql);
			result=findMap(sql);
			if(Util.objIsNULL(result)){
				throw new RuntimeException("无法获取办理情况");
			}
		}catch (Exception e) {
			logger.error("查询【"+staffcode+"】名片办理情况 时出现 ===="+e.getMessage());
			throw new RuntimeException("查询【"+staffcode+"】名片办理情况 时出现异常["+e.getMessage()+"]");
		}finally{
			closeConnection();
		}
		return result;
	}
	
	
	public double getPayerNumber(String staffcode,String urgentDate) throws SQLException{
		double num=0;
		try{
			String sql=" select number from change_record where sfyx='Y' and payer=? and addDate=? ";
			logger.info("查询【"+staffcode+"】名片办理情况 时 sql ===="+sql);
			List<Object> list=findDate2(sql, staffcode,urgentDate);
			if(list.size()>0){
				num=Double.parseDouble(list.get(0)+"");
			}
		}catch (Exception e) {
			logger.error("查询【"+staffcode+"】名片办理情况 时出现 ===="+e.getMessage());
			throw new RuntimeException("查询【"+staffcode+"】名片办理情况 时出现异常["+e.getMessage()+"]");
		}finally{
			closeConnection();
		}
		return num;
	}
	

	
	/**
	 * 名片办理情况
	 * @author kingxu
	 * @date 2015-9-28
	 * @param staffcode
	 * @return employeeid:员工编号，eqnum:基础限额，used:已办理名片数量(包括自己支付),add:额外新增限额，selfpay:自己支付数量
	 * @throws SQLException
	 * @return Map<String,Object>
	 */
	public Map<String,Object> nameCardMarquee(String staffcode) throws SQLException{
		Map<String,Object> result=null;
		try{
			String sql=" select * from (select employeeId,"+Constant.NAMECARD_NUM+" as eqnum, if(used is null,0,used)as used,if(addnum is null,0,addnum) as addnum,if(payernum is null,0,payernum)as selfpay from " +
					" (select employeeId from cons_list  where employeeId='"+staffcode+"') t 	LEFT JOIN("+
						" select code, sum(quantity) as used from req_record where code='"+staffcode+"'  and YEAR(request_date) = YEAR(NOW())  "+
						" )a on(1=1) left  join ("+
						"	select initials,sum(additional) as addnum from nq_additional where sfyx='Y' and  initials='"+staffcode+"' and year(add_date)=year(now())"+  
						" )b on(1=1) "+
						" left join ("+
						"	select payer, sum(Number)as payernum from change_record where sfyx='Y' and YEAR(up_date) = YEAR(NOW()) and payer='"+staffcode+"'"+
						" )c on (1=1))x";
			logger.info("查询【"+staffcode+"】名片办理情况 时 sql ===="+sql);
			//employeeid:员工编号，eqnum:基础限额，used:已办理名片数量(包括自己支付),add:额外新增限额，selfpay:自己支付数量
			result=findMap(sql);
			if(Util.objIsNULL(result)){
				throw new RuntimeException("无法获取办理情况");
			}
		}catch (Exception e) {
			logger.error("查询【"+staffcode+"】名片办理情况 时出现 ===="+e.getMessage());
			throw new RuntimeException("查询【"+staffcode+"】名片办理情况 时出现异常["+e.getMessage()+"]");
		}finally{
			closeConnection();
		}
		return result;
	}

	


	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, NameCardConvoy.class);
	}

}
