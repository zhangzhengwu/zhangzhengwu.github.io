package com.coat.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.C_marProduct;

import util.Pager;

public interface  MarketPremiumDao {
	public Pager findAll(String procode, String ename,String cname,String blbz,Pager page)throws SQLException;
	public int save(C_marProduct mp)throws SQLException;
	public int updateMP(C_marProduct sp)throws SQLException;
	public int delete(String procode)throws SQLException;
	public List<Map<String,Object>>findAll(String procode, String ename, String cname,String blbz)throws SQLException;
}
