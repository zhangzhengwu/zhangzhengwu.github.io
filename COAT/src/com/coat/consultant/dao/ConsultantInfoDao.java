package com.coat.consultant.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coat.consultant.entity.ConsultantInfo;

public interface ConsultantInfoDao {
	/**
	 * 保存ConsultantInfo
	 * @author kingxu
	 * @date 2016-5-20
	 * @param cons
	 * @return
	 * @return int
	 * @throws SQLException 
	 */
	int saveConsultantInfo(ConsultantInfo cons) throws SQLException;
	/**
	 * 批量保存ConsultantInfo
	 * @author kingxu
	 * @date 2016-5-20
	 * @param cons
	 * @return
	 * @throws SQLException
	 * @return int
	 */
	public int batchSaveConsultantInfo(List<Map<String,Object>> cons,String tableName) throws SQLException;
	/**
	 * 从香港远程数据库同步数据到本地
	 * @author kingxu
	 * @date 2016-5-20
	 * @throws SQLException
	 * @return void
	 */
	public String synchConsultantInfo() throws SQLException;

}
