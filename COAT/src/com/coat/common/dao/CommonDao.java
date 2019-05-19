package com.coat.common.dao;

import java.sql.SQLException;
import java.util.List;


public interface CommonDao {
	
	/**
	 * 获取url映射modular的记录
	 * @author kingxu
	 * @date 2015-12-22
	 * @return
	 * @return List<String[]>
	 * @throws SQLException 
	 */
	List<Object[]> findUrlMappingModular() throws SQLException;
}
