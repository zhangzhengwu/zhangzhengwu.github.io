package com.coat.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.common.dao.PositionDao;

import dao.common.BaseDao;

public class PositionDaoImpl extends BaseDao implements PositionDao {

	public List<Map<String,Object>> getposition() throws Exception{
		List<Map<String, Object>> list=null;
		try{
			String sql="SELECT position_ename FROM position_list WHERE SFYX='Y'";
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
