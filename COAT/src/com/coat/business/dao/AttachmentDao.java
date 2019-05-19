package com.coat.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Pager;

public interface AttachmentDao {
	public Pager findAll(String item,String refno,String sfyx,Pager page)throws SQLException;
	public List<Map<String,Object>>findAll(String item, String refno, String sfyx)throws SQLException;
}
