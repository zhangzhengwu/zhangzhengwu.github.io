package com.coat.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Pager;
import util.Util;
import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;
import entity.Professional_title;

public class ConsultantProTitleDaoImpl extends BaseDao implements ConsultantProTitleDao {
	/*Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;*/
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	
	public Pager findAll(String ename, String cname, String sfyx, Pager page) throws SQLException{
		List<Professional_title> professions = new ArrayList<Professional_title>();
		String sql = " from professional_title where prof_title_ename like ? and prof_title_cname like ? and sfyx like ?  ";
	
		try{
			String limit="  limit ?,?";
			page=findPager(null, sql, limit, page,	Util.modifyString(ename),Util.modifyString(cname),Util.modifyString(sfyx));
		}catch(Exception e){
			e.printStackTrace();
			logger.info("find consultProTitle error");
		}finally{
			closeConnection();
		}
		return page;
	}
	public int save(Professional_title pro)throws SQLException{
		int r = -1;
		try{
			String sql = "insert into professional_title(prof_title_ename, prof_title_cname, add_date, add_name, sfyx) values(?,?,?,?,?)";
			r = saveEntity(sql, pro.getProf_title_ename(), pro.getProf_title_cname(), pro.getAdd_date(), pro.getAdd_name() , pro.getSfyx());
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("save consultantprotitle error");
		}finally{
			closeConnection();
		}
		return r;
	}
	//CPT: Consultant Professional Title
	public int updateCPT(Professional_title pro)throws SQLException{
		int r = -1;
		try{
			String sql = "update professional_title set prof_title_ename=?,prof_title_cname=?,sfyx=? where Id=?";
			r=update(sql,new Object[]{pro.getProf_title_ename(),pro.getProf_title_cname(),pro.getSfyx(),pro.getId()}); 
		}catch(Exception e){
			e.printStackTrace();
			logger.info("update CPT error");
		}finally{
			closeConnection();
		}
		return r;
		
	}
	public int delete(String id)throws SQLException{
		int r=-1;
		try{
			String sql = "update professional_title set sfyx='N' where Id=? and sfyx='Y'";
			r = update(sql,new Object[]{id});
		}catch(Exception e){
			e.printStackTrace();
			logger.info("delete professionaltitle error");
		}finally{
			closeConnection();
		}
		return r;
	}
	public List<Map<String,Object>>findAll(String _ename, String _cname, String _sfyx)throws SQLException{
		String ename = Util.objIsNULL(_ename)?"":_ename;
		String cname = Util.objIsNULL(_cname)?"":_cname;
		String sfyx = Util.objIsNULL(_sfyx)?"":_sfyx;
		List<Map<String,Object>> lists = null;
		
		try{
			String sql = "select *, if(sfyx='Y','有效','无效')as status from professional_title where prof_title_ename like '%"+ename+"%' and prof_title_cname like '%"+cname+"%' and sfyx like '%"+sfyx+"%'";
			lists = listMap(sql);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("select ctp map errpr");
		}finally{
			closeConnection();
		}
		return lists;
	}
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, Professional_title.class);
	}
}
