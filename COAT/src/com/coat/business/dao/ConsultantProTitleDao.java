package com.coat.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Page;
import util.Pager;

import entity.Professional_title;

public interface ConsultantProTitleDao {
	Pager findAll(String ename, String cname, String sfyx, Pager page) throws SQLException;
	public int save(Professional_title pro)throws SQLException;
	public int updateCPT(Professional_title pro)throws SQLException;
	public int delete(String id)throws SQLException;
	public List<Map<String,Object>>findAll(String ename, String cname, String sfyx)throws SQLException;
}
