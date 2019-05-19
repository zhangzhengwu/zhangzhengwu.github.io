package com.coat.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.common.dao.LocationDao;

import dao.common.BaseDao;

public class LocationDaoImpl extends BaseDao implements LocationDao {
	
	
	public List<Map<String, Object>> getlocation() throws Exception {
		 List<Map<String, Object>> list=null;
		try{
			String sql="select realName,name from location";
			list=findListMap(sql);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			closeConnection();
		}
		return list;
	}
	
	public List<Map<String, Object>> getmacaulocation() throws Exception {
		 List<Map<String, Object>> list=null;
		try{
			String sql="select realName,name from locationmacau";
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
