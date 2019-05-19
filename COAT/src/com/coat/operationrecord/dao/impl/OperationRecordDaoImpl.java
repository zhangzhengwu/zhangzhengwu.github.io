package com.coat.operationrecord.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Pager;
import util.Util;

import com.coat.operationrecord.dao.OperationRecordDao;
import com.coat.operationrecord.entity.OperationRecord;

import dao.common.BaseDao;

/**
 * 用户访问地址记录 实现类
 * 2015-09-07
 * @author orlandozhang
 *
 */
public class OperationRecordDaoImpl extends BaseDao implements OperationRecordDao {
	Logger logger = Logger.getLogger(OperationRecordDaoImpl.class);
	public Pager findPager(String[] fields, Pager page, Object... objects)
			throws Exception {
		String sql=" FROM operationrecord " +
				" where   date_format(date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and username like ? " +
				" and modular like ? " +
				" and operation like ?" +
				" and status like ? ";

		String limit="order by date desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	public int saveOperationRecord(OperationRecord or) throws SQLException{
		String sql = "insert OperationRecord(username,ipaddress,modular,operation,date,status,remark1,httpaddress,result)" +
				" values (?,?,?,?,?,'Y',?,?,?) ";
		int r = -1;
		try {
           // r = saveEntity(sql,or.getUsername(),or.getIpaddress(),or.getModular(),or.getOperation(),or.getDate(),or.getRemark1(),or.getHttpaddress(),or.getResult());
			if(getConnction().isClosed()){
        		openConnection();
        	}
			r=updates(sql,or.getUsername(),or.getIpaddress(),or.getModular(),or.getOperation(),or.getDate(),or.getRemark1(),or.getHttpaddress(),or.getResult());
			if(r<0){
				 logger.info("save OperationRecord error==["+sql+"]");
			} else {
			     logger.info("save OperationRecord success==["+sql+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save OperationRecord exception==["+sql+"]："+e);
		}finally{
			closeConnection();
		}
		return r; 
	}
	
	public List<Object[]> findUsageByusername30day(String username) throws Exception{
		List<Object[]> list=null;
		try{
			String sql="select if(modular ='' or modular is null,'Unknown',modular),count(*) from operationrecord where username =? and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(date) group by modular";
			list=findDate(sql, username);
		}catch (Exception e) {
			throw e;
		}finally{
			closeConnection();
		}
		
		return list;
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, OperationRecord.class);
	}
	
	public Connection getConnection() throws SQLException{
		return getConnction();	
	}
	
	public void openConnections() {
		try {
			openConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void insert(String sql) throws SQLException, ClassNotFoundException{
		try{
		update2(sql);
		}finally{
			closeConnection();
		}
	}
	public Double[] queryLogReport_Year(String start_Y,String end_Y) throws Exception {
		Double[] num=new Double[12];
		
		if(Util.objIsNULL(start_Y)){
			start_Y="1990";
		}
		if(Util.objIsNULL(end_Y)){
			end_Y="2099";
		}
		try{
			String sql="select month(date) as months,count(*) as num from operationrecord" +
					" where year(date)>=? and  year(date)<=?   group by month(date) order by month(date)";
			List<Object[]> list=findDate(sql,start_Y,end_Y);
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
	public Double[] queryLogReport_Month(String start_M, String end_M)throws Exception {
		Double[] num=new Double[31];
		
		if(Util.objIsNULL(start_M)){
			start_M="1999-01";
		}
		if(Util.objIsNULL(end_M)){
			end_M="2099-01";
		}
		try{
			String sql="select day(date) as days,count(*) as num from operationrecord" +
					" where DATE_FORMAT(date,'%Y-%m')>=? and DATE_FORMAT(date,'%Y-%m') <=?  group by day(date) order by day(date)";
			List<Object[]> list=findDate(sql,start_M,end_M);
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
	public  List<Map<String,Object>> queryLogReport_Day(String startDate,String endDate) throws Exception {
		
		if(Util.objIsNULL(startDate)){
			startDate="1999-01-01";
		}
		if(Util.objIsNULL(endDate)){
			endDate="2099-01-01";
		}
		List<Object[]> listStr=new ArrayList<Object[]>();
		List<Map<String,Object>> mapList=null;
		try{
			String sql="select a.username,a.date,b.userid ,c.groupid,d.groupname,HOUR(date) as dates ,count(*) as num" +
					" from operationrecord a LEFT JOIN s_user b on a.username=b.loginname LEFT JOIN s_role c on b.userid=c.userid" +
					" LEFT JOIN s_group d on c.groupid=d.groupid  where" +
					" DATE_FORMAT(date,'%Y-%m-%d')>='"+startDate+"' and DATE_FORMAT(date,'%Y-%m-%d')<='"+endDate+"' group by HOUR(date),groupname order by  groupname, HOUR(date)";
			//System.out.println(sql);
		    listStr=findDate(sql);
		    String groupname="";
		    mapList= new ArrayList<Map<String, Object>>();
		    Map<String,Object> map=null;
		    int[]hour=null;
		    for (int i = 0; i < listStr.size(); i++) {
		    	if(Util.objIsNULL(groupname)){//对象为空
		    		groupname=listStr.get(i)[4]+"";
		    		hour=new int[24];
		    		map=new HashMap<String, Object>();
		    		map.put("name", groupname);
		    		
		    	}else if(!groupname.equals(listStr.get(i)[4])){//第二个不同的角色名
		    		map.put("data", hour);
		    		mapList.add(map);
		    		hour=new int[24];
		    		map=new HashMap<String, Object>();
		    		groupname=listStr.get(i)[4]+"";
		    		map.put("name", groupname);
		    		
		    	}
		    	hour[Integer.parseInt(listStr.get(i)[5]+"")]=Integer.parseInt(listStr.get(i)[6]+"");
		    	if(i==listStr.size()-1){
		    		map.put("data", hour);
		    		mapList.add(map);
		    	}
		    }
				/*if((listStr.get(i)[3]+"").equals("1")){//炒鸡管理员
					str[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else if((listStr.get(i)[3]+"").equals("2")){//管理员
					str2[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else if((listStr.get(i)[3]+"").equals("3")){//香港行政
					str3[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else if((listStr.get(i)[3]+"").equals("11")){//IT系统管理员
					str4[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else if((listStr.get(i)[3]+"").equals("15")){//SSC Admin Team
					str5[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else if((listStr.get(i)[3]+"").equals("16")){//CS Processing Team
					str6[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else if((listStr.get(i)[3]+"").equals("17")){//香港IT
					str7[Integer.parseInt(""+listStr.get(i)[5])-1]=listStr.get(i)[6]+"";
				}else{
					
				}
			}
    		list.add(0,str);
			list.add(1,str2);
			list.add(2,str3);
			list.add(3,str4);
			list.add(4,str5);
			list.add(5,str6);
			list.add(6,str7);*/
		}catch (Exception e) {
			throw e;
		}finally{
			closeConnection();
		}
		return mapList;
	}
	

}
