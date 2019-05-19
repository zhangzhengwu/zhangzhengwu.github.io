package com.coat.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.C_stationeryProduct;

import util.Pager;

public interface StationeryProductDao {
	public Pager findAll(String procode, String ename, String cname, String blbz, Pager page)throws SQLException;
	public int save(C_stationeryProduct sproduct)throws SQLException;
	public int updateSP(C_stationeryProduct sp)throws SQLException;
	public int isExist(String procode)throws SQLException;
	public List<Map<String,Object>>findAll(String procode, String ename, String cname, String blbz)throws SQLException;
}
