package com.coat.namecard.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.coat.namecard.dao.NameCardRecordDao;
import com.coat.namecard.entity.NameCardConvoy;

import dao.common.BaseDao;

public class NameCardRecordDaoImpl extends BaseDao implements NameCardRecordDao {

	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, NameCardConvoy.class);
	}
	
	
	public boolean vailrequestElite(String staffcode,int refno) throws SQLException{
		boolean flag=false;
		try{
		String sql="select count(*) from request_new where card_type='N' and quantity>0 and staff_code='"+staffcode+"' and eliteTeam='Y' and urgent='N' and refno!="+refno+" and date_format(UrgentDate,'%Y')=date_format(now(),'%Y') ";
		int num=findCount(sql);
		if(num>0){
			flag=true;
		}
		}catch (Exception e) {
			throw new RuntimeException("查询【"+staffcode+"】Elite办理情况 时出现异常["+e.getMessage()+"]");
		}finally{
			closeConnection();
		}
		return flag;	
	}

}
