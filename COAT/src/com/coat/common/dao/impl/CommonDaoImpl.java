package com.coat.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.common.dao.CommonDao;

import dao.common.BaseDao;

public class CommonDaoImpl extends BaseDao implements CommonDao {

	
	
	
	
	

	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}

	
	
	
	
	
	
	
	
	public List<Object[]> findUrlMappingModular() throws SQLException {
		
		List<Object[]> list=null;
		try{
			String sql="select url,modular from modularmapping where status=1";
			list=findDate(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return list;
	}

}
