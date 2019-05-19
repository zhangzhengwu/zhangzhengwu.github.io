package com.coat.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coat.business.entity.C_PibaBook;

import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;

import util.Pager;
import util.Util;

public class PibaBookDaoImpl extends BaseDao implements PibaBookDao {
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	public Pager findAll(String ename, String cname, String type, Pager page)throws SQLException{
		String sql = " from c_piba_book where bookEName like ? and bookCName like ? and type like ?";
		try{
			String limit = " limit ?,?";
			page = findPager(null,sql,limit,page,Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(type));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select pibabook error");
		}finally{
			closeConnection();
		}
		return page;
	}
	public int save(C_PibaBook pb)throws SQLException{
		int r = -1;
		String sql = "insert into c_piba_book(bookCName,bookEName,type,language,num,creator,createDate)values(?,?,?,?,?,?,?)";
		try{
			r = saveEntity(sql,pb.getBookCName(),pb.getBookEName(),pb.getType(),pb.getLanguage(),pb.getNum(),pb.getCreator(),pb.getCraeteDate());
		}catch(Exception e){
			e.printStackTrace();
			logger.info("insert pibabook error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public int updatePB(C_PibaBook pb)throws SQLException{
		int r = -1;
		String sql = "update c_piba_book set bookCName=?,bookEName=?,type=?,language=?,num=? where bookNo=?";
		try{
			r = update(sql,new Object[]{pb.getBookCName(),pb.getBookEName(),pb.getType(),pb.getLanguage(),pb.getNum(),pb.getBookNo()});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("update pibabook error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public List<Map<String,Object>>findAll(String ename,String cname,String type)throws SQLException{
		List<Map<String,Object>> lists = null;
		String sql = "select * from c_piba_book where bookEName like ? and bookCName like ? and type like ?";
		try{
			lists = listMap(sql,Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(type));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select pibabook error");
		}
		return lists;
	}
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, C_PibaBook.class);
	}
}
