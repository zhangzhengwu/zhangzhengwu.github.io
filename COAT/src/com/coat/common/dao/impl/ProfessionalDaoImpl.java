package com.coat.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.common.dao.ProfessionalDao;

import dao.common.BaseDao;

public class ProfessionalDaoImpl extends BaseDao implements ProfessionalDao {

	
	public List<Map<String,Object>> getProfessional() throws Exception{
		List<Map<String, Object>> list=null;
		try{
			String sql="SELECT prof_title_ename FROM professional_title WHERE SFYX='Y'";
			list=findListMap(sql);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			closeConnection();
		}
		return list;
	}
	public List<Map<String,Object>> getStaffProfessional() throws Exception{
		List<Map<String, Object>> list=null;
		try{
			String sql="SELECT prof_title_ename FROM professional_title_staff WHERE SFYX='Y'";
			list=findListMap(sql);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			closeConnection();
		}
		return list;
	}
	
	
	
	
	
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



}
