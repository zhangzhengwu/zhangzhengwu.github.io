package com.coat.common.dao.impl;



import java.sql.Connection;
import java.sql.PreparedStatement;

import util.DBManager;

import com.coat.common.dao.OperationRecordDao;
import com.coat.operationrecord.entity.OperationRecord;

public class OperationRecordDaoImpl implements OperationRecordDao {

	public int saveRecord(OperationRecord record) {
		Connection con=null;
		PreparedStatement ps=null;
		int num=-1;
		try{
			con=DBManager.getCon();
			String sql = "insert OperationRecord(username,ipaddress,modular,operation,date,status,remark1,remark2,httpaddress,result)" +
					" values (?,?,?,?,?,'Y',?,?,?,?) ";
			   ps = con.prepareStatement(sql);  
			   ps.setString(1, record.getUsername());
			   ps.setString(2, record.getIpaddress());
			   ps.setString(3, record.getModular());
			   ps.setString(4, record.getOperation());
			   ps.setString(5, record.getDate());
			   ps.setString(6, record.getRemark1());
			   ps.setString(7, record.getRemark2());
			   ps.setString(8, record.getHttpaddress());
			   ps.setString(9, record.getResult());
	            num= ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}


}
