package com.coat.chickenbox.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Pager;
import util.Util;

import com.coat.chickenbox.dao.ChickenBoxDao;

import dao.common.BaseDao;

public class ChickenBoxDaoImpl extends BaseDao implements ChickenBoxDao{
	ResultSet rs=null;	
	Connection conn=null;
	PreparedStatement ps=null;

	Logger logger=Logger.getLogger(this.getClass());
	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;	
	}
	
	public Pager queryChicken_BoxList(String[] fields, Pager page,Object... objects) throws Exception{
//		String sql="FROM (select DISTINCT a.ConsultantId as staffcode,CASE a.alias WHEN 'null' THEN CONCAT(a.surName)  ELSE CONCAT(a.alias,' ',a.surName)  END as  othername," +
	//2017-3-17 17:06:07，king， 优化，当consultantInfo里面的alias为空时，other name 取c_adminservice_allextension中的staffname，避免othername显示不正确的问题再次发生
		String sql="FROM (select DISTINCT a.ConsultantId as staffcode,CASE  WHEN a.alias='' or a.alias is null THEN CONCAT(c.staffname)  ELSE CONCAT(a.alias,' ',a.surName)  END as  othername," +
				" CASE a.alias WHEN 'null' THEN CONCAT(a.surName,' ',a.givenName)  ELSE CONCAT(a.alias,',',a.surName,' ',a.givenName)  END as  fullname1," +
				" CONCAT(a.surName,' ',a.givenName) as fullname2,b.location,b.pigenBoxno as boxNo,b.extensionno,a.RecruiterId from" +
				" consultantInfo a LEFT JOIN seat_list b on a.ConsultantId=b.staffcode  LEFT JOIN c_adminservice_allextension c on" +
				" a.ConsultantId=c.staffcode ) xx" +
				" where xx.staffcode like ? and xx.fullname1 like ? and xx.othername like ? and xx.RecruiterId like ? ";
		String limit=" order by xx.staffcode asc limit ?,? ";
		//System.out.println(sql);
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}

	public List<Map<String, Object>> downChickenBox(String staffcode,String fullname, String othername, String dRecruite)throws SQLException {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			//2017-3-17 17:06:07，king， 优化，当consultantInfo里面的alias为空时，other name 取c_adminservice_allextension中的staffname，避免othername显示不正确的问题再次发生
		/*	String sql="select DISTINCT a.ConsultantId as staffcode,CASE a.alias WHEN 'null' THEN CONCAT(a.surName)  ELSE CONCAT(a.alias,' ',a.surName)  END as  othername," +
			" CASE a.alias WHEN 'null' THEN CONCAT(a.surName,' ',a.givenName)  ELSE CONCAT(a.alias,'，',a.surName,' ',a.givenName)  END as  fullname1," +
			" CONCAT(a.surName,' ',a.givenName) as fullname2,a.Location,b.chickenboxno as boxNo,c.extension,a.RecruiterId from" +
			" consultantInfo a LEFT JOIN return_mail.r_cons_info b on a.ConsultantId=b.staffcode  LEFT JOIN c_adminservice_allextension c on" +
			" a.ConsultantId=c.staffcode where 1=1 ";
			StringBuffer sqlString=new StringBuffer(sql);
			if(!Util.objIsNULL(staffcode)){
				sqlString.append("and  a.ConsultantId like '%"+staffcode+"%' ");
			}
			if(!Util.objIsNULL(fullname)){
				sqlString.append(" and CONCAT(a.alias,'，',a.surName,' ',a.givenName)  like '%"+fullname+"%' ");
			}
			if(!Util.objIsNULL(othername)){
				sqlString.append(" and CONCAT(a.alias,' ',a.surName) like '%"+othername+"%' ");
			}
			if(!Util.objIsNULL(dRecruite)){
				sqlString.append(" and a.RecruiterId  like '%"+dRecruite+"%' ");
			}
			sqlString.append(" order by a.ConsultantId ");*/
			
			//2017-3-17 17:06:07，king， 优化，当consultantInfo里面的alias为空时，other name 取c_adminservice_allextension中的staffname，避免othername显示不正确的问题再次发生
			String sql="select DISTINCT * FROM (select DISTINCT a.ConsultantId as staffcode,CASE  WHEN a.alias='' or a.alias is null THEN CONCAT(c.staffname)  ELSE CONCAT(a.alias,' ',a.surName)  END as  othername," +
					" CASE a.alias WHEN 'null' THEN CONCAT(a.surName,' ',a.givenName)  ELSE CONCAT(a.alias,',',a.surName,' ',a.givenName)  END as  fullname1," +
					" CONCAT(a.surName,' ',a.givenName) as fullname2,b.location,b.pigenBoxno as boxNo,b.extensionno,a.RecruiterId from" +
					" consultantInfo a LEFT JOIN seat_list b on a.ConsultantId=b.staffcode  LEFT JOIN c_adminservice_allextension c on" +
					" a.ConsultantId=c.staffcode ) xx" +
					" where xx.staffcode like ? and xx.fullname1 like ? and xx.othername like ? and xx.RecruiterId like ? ";
			String limit=" order by xx.staffcode asc ";
			list=findListMap(sql+limit,staffcode,fullname,othername,dRecruite);
			System.out.println("-----"+list.size());
		}catch (Exception e) {
			logger.error("导出ChickenBox 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return list;

	}

	public int saveQueryRecord(String data,String staffcode,String fullname,String othersname,String d_Recruite,String username) throws SQLException {
		String nowDate=DateUtils.getNowDateTime();
		try {
			JSONArray jsons=JSONArray.fromObject(data);
			List<net.sf.json.JSONObject> list=(List<net.sf.json.JSONObject>) jsons.get(0);
			String sqls1[] = new String [list.size()];
			String sqls2[] = new String [list.size()];
			super.openTransaction();//开启事物
			for (int i = 0; i < list.size(); i++) {
				//保存查询记录
				sqls1[i]="insert into chicken_box_query_record(staffcode,othername,fullname1,fullname2,location" +
				",boxNo,extension,recruiterId,findTime,createName,sfyx)values(" +
				"'"+list.get(i).get("staffcode")+"'," +
				"'"+list.get(i).get("othername")+"'," +
				"'"+list.get(i).get("fullname1")+"'," +
				"'"+list.get(i).get("fullname2")+"'," +
				"'"+list.get(i).get("Location")+"'," +
				"'"+list.get(i).get("boxNo")+"'," +
				"'"+list.get(i).get("extensionno")+"'," +
				"'"+list.get(i).get("RecruiterId")+"'," +
				"'"+nowDate+"','"+username+"'," +
				"'Y')" ;
				
				//保存查询条件
				sqls2[i]="insert into chicken_box_queryCondition_record(staffcode,f_code,f_funllname,f_othername,f_recruiteId," +
					"findTime,createName,sfyx)values(" +
					"'"+list.get(i).get("staffcode")+"'," +
					"'"+staffcode+"'," +
					"'"+fullname+"'," +
					"'"+othersname+"'," +
					"'"+d_Recruite+"'," +
					"'"+nowDate+"','"+username+"'," +
					"'Y')";
				
			}
			int num=calculateNum(super.batchUpdate(sqls1));//批量插入查询记录
			if(num<list.size()){
				throw new  Exception("批量插入查询记录异常!");
			}
			int num2=calculateNum(super.batchUpdate(sqls2));		  //批量插入查询条件记录
			if(num2<list.size()){
				throw new Exception("批量插入查询条件记录异常!");
			}
			super.sumbitTransaction();//提交事物
		} catch (Exception e) {
			super.rollbackTransaction();//事物回滚
			e.printStackTrace();
		}finally{
			super.closeConnection();//关闭连接
		}
		return 0;
	}
	
	
}
