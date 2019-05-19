package com.coat.business.dao;

import java.sql.SQLException;

import com.coat.business.entity.C_PibaBook;

import util.Pager;

public interface PibaBookDao {
	public Pager findAll(String ename, String cname, String type, Pager page)throws SQLException;
	public int save(C_PibaBook pb)throws SQLException;
}
