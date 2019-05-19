package com.coat.timerTask.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Pager;

import com.coat.timerTask.dao.SeattingPlanDao;
import dao.common.BaseDao;

public class SeattingPlanDaoImpl extends BaseDao implements SeattingPlanDao{

	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}
	
	
	public Pager querySeattindPlan(String[] fields, Pager page,Object... objects) throws Exception{
		String sql="FROM read_image_to_excel where status='Y' and  imgFile like ? and dataFile like ?"+
		" and  date_format(createDate,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d') " +
		" and  date_format(createDate,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" ;
		
		String limit=" order by createDate asc limit ?,? ";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}

}
