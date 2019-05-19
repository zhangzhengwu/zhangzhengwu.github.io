package com.coat.namecard.dao;

import java.sql.SQLException;

public interface NameCardRecordDao {
	/**
	 * 查询staffcode是否已经办理过eliteteam并排除某条记录
	 * @author kingxu
	 * @date 2015-9-29
	 * @param staffcode
	 * @param refno
	 * @return
	 * @return boolean
	 * @throws SQLException 
	 */
	boolean vailrequestElite(String staffcode,int refno) throws SQLException;
}
