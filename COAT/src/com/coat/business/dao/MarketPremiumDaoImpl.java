package com.coat.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;
import entity.C_marProduct;
import util.Pager;
import util.Util;

public class MarketPremiumDaoImpl extends BaseDao implements MarketPremiumDao {
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	public Pager findAll(String procode, String ename, String cname, String blbz, Pager page)throws SQLException {
		String sql = " from c_mar_product where procode like ? and englishname like ? and chinesename like ? and BLBZ like ?";
		String limit = " limit ?,?";
		try{
			page = findPager(null,sql,limit,page,Util.modifyString(procode),Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(blbz));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select marketpremium error");
		}finally{
			closeConnection();
		}
		return page;
	}
	public int isExist(String procode)throws SQLException{
		int r=-1;
		
		try{
			String sql = "select count(*) from c_mar_product where procode='"+procode+"'";
			r = findCount(sql);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select marproduct by procode error");
		}
		return r;
	};
	public int save(C_marProduct mp)throws SQLException{
		int r = -1;
		String sql = "insert into c_mar_product(procode,englishname,chinesename,unitprice,c_clubprice,specialprice,unit,quantity,BLBZ,updates,remark1) values(?,?,?,?,?,?,?,?,?,?,?)";
		try{
			r = saveEntityNoKeys(sql,mp.getProcode(),mp.getEnglishname(),mp.getChinesename(),mp.getUnitprice(),mp.getC_clubprice(),mp.getSpecialprice(),mp.getUnit(),mp.getQuantity(),mp.getBLBZ(),mp.getUpdates(),mp.getRemark1());
		}catch(Exception e){
			e.printStackTrace();
			logger.info("insert marketpremium error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public int updateMP(C_marProduct mp)throws SQLException{
		int r = -1;
		String sql = "update c_mar_product set englishname=?,chinesename=?,unitprice=?,c_clubprice=?,specialprice=?,unit=?,quantity=?,BLBZ=?,remark1=? where procode=?";
		try{
			r = update(sql,new Object[]{mp.getEnglishname(),mp.getChinesename(),mp.getUnitprice(),mp.getC_clubprice(),mp.getSpecialprice(),mp.getUnit(),mp.getQuantity(),mp.getBLBZ(),mp.getRemark1(),mp.getProcode()});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("update marketpremium error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public int delete(String procode)throws SQLException{
		int r=-1;
		String sql = "update c_mar_product set BLBZ='N' where procode=? and BLBZ='A'";
		try{
			r = update(sql,new Object[]{procode});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("delete marproduct error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public List<Map<String,Object>>findAll(String procode,String ename,String cname,String blbz)throws SQLException{
		List<Map<String,Object>> lists = null;
		String sql = "select *, case BLBZ when 'A' then '所有人' when 'S' then '内勤' else '顾问' end as status from c_mar_product where procode like ? and englishname like ? and chinesename like ? and BLBZ like ?";
		try{
			lists = listMap(sql,new Object[]{Util.modifyString(procode),Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(blbz)});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select marketpremium error");
		}finally{
			closeConnection();
		}
		return lists;
	}
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, C_marProduct.class);
	}
}
