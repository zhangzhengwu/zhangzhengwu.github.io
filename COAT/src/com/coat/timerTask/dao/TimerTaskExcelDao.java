package com.coat.timerTask.dao;

import java.sql.SQLException;
import java.util.List;

public interface TimerTaskExcelDao {
	/**
	 * 本地Execl表数据存入数据库
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public int saveExcelList(List<List<Object>> list) throws SQLException;
	
}
