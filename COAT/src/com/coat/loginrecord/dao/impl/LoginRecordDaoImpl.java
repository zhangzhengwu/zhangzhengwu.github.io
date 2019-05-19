package com.coat.loginrecord.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Pager;

import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.entity.LoginRecord;

import dao.common.BaseDao;

/**
 * 登录/退出记录 实现类
 * 2015-09-06
 * @author orlandozhang
 *
 */
public class LoginRecordDaoImpl extends BaseDao implements LoginRecordDao {

	Logger logger = Logger.getLogger(LoginRecordDaoImpl.class);
	public Pager findPager(String[] fields, Pager page, Object... objects)
			throws Exception {
		String sql=" FROM loginrecord " +
				" where   date_format(date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and username like ? " +
				" and platform like ?" +
				" and ipaddress like ?" +
				" and status like ?";

		String limit="order by date desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, LoginRecord.class);
	}

	public int saveLogin(LoginRecord lr) throws SQLException {
		int r = -1;
		String sql = "insert loginrecord(username,platform,operation,ipaddress,date,status,remark1,remark2,remark3,remark4) values ('"+lr.getUsername()+"','"+lr.getPlatform()+"','"+lr.getOperation()+"','"+lr.getIpaddress()+"','"+lr.getDate()+"','"+lr.getStatus()+"','','','','') ";
		try {
            //r = saveEntity(sql);
            r=update2(sql);
			if(r<0){
				 logger.info("save loginrecord error==["+sql+"]");
			} else {
			     logger.info("save loginrecord success==["+sql+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save loginrecord exception==["+sql+"]："+e);
			r = -1;
		}finally{
			closeConnection();
		}
		return r;
	}

	
	public int saveLogout(LoginRecord lr) throws SQLException {
		int r = -1;
		String sql = "insert loginrecord(username,platform,operation,ipaddress,date,status,remark1,remark2,remark3,remark4) values ('"+lr.getUsername()+"','"+lr.getPlatform()+"','"+lr.getOperation()+"','"+lr.getIpaddress()+"','"+lr.getDate()+"','"+lr.getStatus()+"','','','','') ";
		try {
            r = saveEntity(sql);
			if(r<0){
				 logger.info("save loginrecord error==["+sql+"]");
			} else {
			     logger.info("save loginrecord success==["+sql+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save loginrecord exception==["+sql+"]："+e);
			r = -1;
		}finally{
			closeConnection();
		}
		
		return r;
		
	}

	public String select(String ipaddress) throws Exception {
		String username = "";
		Map<String,Object> map=new HashMap<String, Object>(); 
		String sql = "select username from loginrecord where status = 'Y' and ipaddress='"+ipaddress+"' and DATE_FORMAT(NOW(),'%Y-%m-%d')=DATE_FORMAT(date,'%Y-%m-%d') order by date desc limit 0,1";
		try {
			map = findMap(sql);
			username = (String) map.get("username");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("select loginrecord exception==["+sql+"]："+e);
		}finally{
			super.closeConnection();
		}
		return username;
	}
	
	public Double[] loginUsageByusername(String username) throws Exception{
		Double[] num=new Double[12];
		try{
			String sql="select month(date)as months,count(*) as num from loginrecord where username=? and operation='login' and year(now())=year(date) group by month(date) order by month(date)";
			List<Object[]> list=findDate(sql, username);
			for(int i=0;i<list.size();i++){
				Object[] object=list.get(i);
				num[Integer.parseInt(""+object[0])-1]=Double.parseDouble(""+object[1]);
			}
		}catch (Exception e) {
			throw e;
		}finally{
			closeConnection();
		}
		
		return num;
	}
}
