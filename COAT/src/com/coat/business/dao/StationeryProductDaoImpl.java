package com.coat.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;
import entity.C_stationeryProduct;
import entity.Professional_title;
import util.Pager;
import util.Util;

public class StationeryProductDaoImpl extends BaseDao implements StationeryProductDao {
	
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	public Pager findAll(String procode, String ename, String cname, String blbz, Pager page)throws SQLException{
		 
		String sql = " from c_stationery_product where procode like ? and englishname like ? and chinesename like ? and BLBZ like ?";
		
		try{
			String limit = " limit ?,?";
			page = findPager(null, sql, limit, page, Util.modifyString(procode), Util.modifyString(ename), Util.modifyString(cname), Util.modifyString(blbz));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select stationery error");
		}finally{
			closeConnection();
		}
		
		return page;
	}
	public int isExist(String procode)throws SQLException{
		int r = -1;
		try{
			String sql = "select count(*) from c_stationery_product where procode='"+procode+"'";
			r = findCount(sql);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select stationeryproduct by procode error");
		}
		return r;
	}
	public int save(C_stationeryProduct sp)throws SQLException{
		int r = -1;
		String sql = "insert into c_stationery_product(procode,englishname,chinesename,price,unit,quantity,BLBZ,updates,remark1)values(?,?,?,?,?,?,?,?,?)";
		try{
			r = saveEntityNoKeys(sql,sp.getProcode(),sp.getEnglishname(),sp.getChinesename(),sp.getPrice(),sp.getUnit(),sp.getQuantity(),sp.getBLBZ(),sp.getUpdates(),sp.getRemark1());
		}catch(Exception e){
			e.printStackTrace();
			logger.info("add stationery product error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public int updateSP(C_stationeryProduct sp)throws SQLException{
		int r = -1;
		String sql ="update c_stationery_product set englishname=?,chinesename=?,price=?,unit=?,quantity=?,BLBZ=?,remark1=? where procode=?";
		try{
			r = update(sql,new Object[]{sp.getEnglishname(),sp.getChinesename(),sp.getPrice(),sp.getUnit(),sp.getQuantity(),sp.getBLBZ(),sp.getRemark1(),sp.getProcode()});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("update stationery product error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public List<Map<String,Object>>findAll(String procode, String ename, String cname, String blbz)throws SQLException{
		List<Map<String,Object>> lists = null;
		String sql = "select *, case BLBZ when 'A' then '所有人' when 'S' then '内勤' else '顾问' end as status from c_stationery_product where procode like ? and englishname like ? and chinesename like ? and BLBZ like ?";
		try{
			lists = listMap(sql,Util.modifyString(procode),Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(blbz));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select stationery product error");
		}
		return lists;
	}
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, C_stationeryProduct.class);
	}
}
