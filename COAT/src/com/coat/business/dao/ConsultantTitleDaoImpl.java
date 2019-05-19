package com.coat.business.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coat.additionquota.entity.QueryAdditional;
import com.coat.business.entity.PositionList;

import util.DBManager;
import util.Page;
import util.Pager;
import util.Util;


import entity.Position_list;
import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;


public class ConsultantTitleDaoImpl extends BaseDao implements ConsultantTitleDao  {
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	
	public Pager findAll(String ename, String cname, String sfyx, Pager page)throws SQLException{
		String sql = "from position_list where position_ename like ? and position_cname like ? and sfyx like ?";
		try{
			String limit = " limit ?,?";
			page = findPager(null,sql,limit,page,Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(sfyx));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select positionlist error");
		}finally{
			closeConnection();
		}
		return page;
	}
	public int delete(int id)throws SQLException{
		int r = -1;
		String sql = "update position_list set sfyx='N' where Id=? and sfyx='Y'";
		try{
			r = update(sql,new Object[]{id});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("delete position_list error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public int save(PositionList position) throws SQLException{
		int result = -1;
		try{
			String sql = "insert into position_list(position_ename,position_cname,add_date,add_name,sfyx) values(?,?,?,?,?)";
			result = saveEntity(sql,position.getPosition_ename(),position.getPosition_cname(),position.getAdd_date(),position.getAdd_name(),position.getSfyx());
			
		}catch(Exception e){
			logger.info("Add ConsultantTitle Error");
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return result;
	}
	
	public int modify(PositionList position) throws SQLException{
		int result = -1;
		try{
			String sql = "update position_list set position_ename=?, position_cname=?,sfyx=? where Id=?";
			result = update(sql,new Object[]{position.getPosition_ename(),position.getPosition_cname(),position.getSfyx(),position.getId()});
		}catch(Exception e){
			e.printStackTrace();
			logger.error("修改 consultTitle 异常！"+e);
		}finally{
			closeConnection();
		}
		return result;
	}
	public List<Map<String,Object>> findAll(String ename, String cname, String sfyx){
		List<Map<String,Object>> lists = null;
		try{
			String sql = "select *,if(sfyx='Y','有效','无效')as status from position_list where position_ename like ? and position_cname like ? and sfyx like ?";
			lists = listMap(sql,Util.modifyString(ename),Util.modifyString(ename),Util.modifyString(sfyx));
			
		}catch(Exception e){
			logger.info("find  Staff  Error");
			e.printStackTrace();	
		}
		return lists;
	}
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, PositionList.class);
	}
}
