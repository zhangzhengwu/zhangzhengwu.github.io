package com.coat.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.business.entity.PositionList;

import util.Page;
import util.Pager;
import entity.Position_list;

public interface ConsultantTitleDao {
	//返回条数
	public Pager findAll(String ename,String cname,String sfyx,Pager page)throws SQLException;
	
	public int save(PositionList position) throws SQLException;
	
	public int modify(PositionList position) throws SQLException;
	
	public List<Map<String,Object>> findAll(String position_ename, String position_cname, String sfyx);
	
	public int delete(int id)throws SQLException;
}
