package com.coat.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coat.business.entity.C_Attachment;

import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;

import util.Pager;
import util.Util;

public class AttachmentDaoImpl extends BaseDao implements AttachmentDao {
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	public Pager findAll(String item,String refno,String sfyx,Pager page)throws SQLException{
		String sql = "from c_attachment where item like ? and refno like ? and sfyx like ?";
		try{
			String limit = " limit ?,?";
			page = findPager(null,sql,limit,page,Util.modifyString(item),Util.modifyString(refno),Util.modifyString(sfyx));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select attachment error");
		}finally{
			closeConnection();
		}
		return page;
	}
	public List<Map<String,Object>>findAll(String item, String refno, String sfyx)throws SQLException{
		List<Map<String,Object>>lists = null;
		String sql = "select *,if(sfyx='Y','有效','无效')as status from c_attachment where item like ? and refno like ? and sfyx like ?";
		try{
			lists = listMap(sql,Util.modifyString(item),Util.modifyString(refno),Util.modifyString(sfyx));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select attachment error");
		}finally{
			closeConnection();
		}
		return lists;
	}
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, C_Attachment.class);
	}
}
