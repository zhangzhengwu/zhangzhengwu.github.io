package com.coat.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.business.entity.C_EPaymentList;

import util.Pager;

public interface EPaymentListDao {
	public Pager findAll(String procode, String proname, String sfyx, Pager page)throws SQLException;
	public int save(C_EPaymentList epl)throws SQLException;
	public int updateEPL(C_EPaymentList epl)throws SQLException;
	public int delete(int id)throws SQLException;
	public List<Map<String,Object>>findAll(String procode, String proname, String sfyx)throws SQLException;
}
