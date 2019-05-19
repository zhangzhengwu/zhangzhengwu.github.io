package com.coat.consultant.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBManager;
import util.DBManager_sqlservler;
import util.Util;

import com.coat.consultant.dao.ConsultantInfoDao;
import com.coat.consultant.entity.ConsultantInfo;

import dao.common.BaseDao;

public class ConsultantInfoDaoImpl extends BaseDao implements
ConsultantInfoDao {

	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, ConsultantInfo.class);
	}

	public int saveConsultantInfo(ConsultantInfo cons) throws SQLException {
		int num=0;
		try{
			num=super.saveObject(cons);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return num;
	}

	public int batchSaveConsultantInfo(List<Map<String,Object>> cons,String tableName) throws SQLException{
		int num=0;
		try{
			super.openTransaction();
			num=saveMapList(cons, tableName);
			super.sumbitTransaction();
		}catch (Exception e) {
			super.rollbackTransaction();
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return num;

	}

	/**
	 * 从香港同步远程数据
	 * @author kingxu
	 * @date 2016-5-20
	 * @return
	 * @return int
	 * @throws SQLException 
	 */
	public String synchConsultantInfo(){
		String result="";
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Connection  con=null;
		ResultSet rs=null;
		String staffcode="";
		try{
				con=DBManager_sqlservler.getCon();
				String sqls="select a.*,case when directLine like '[+]%' then '' else directLine end as directLine,email,halfConsultant,mobile,adtreehead,ddtreehead  from (select distinct ConsultantId,case when Alias is null then '' else Alias end as Alias,SurName,GivenName,ChineseSurName,ChineseName,GradeId,gradeName,recruiterId,recruiterGradeId,recruiterGradeName,territoryStartDate,InternalPositionid,InternalPositionName,ExternalPositionId,externalPositionname,GroupDateJoin as dateJoin,Location,idcard as HKID  from vSZO_SOS_ConsultantInfo"+
						" where territoryEndDate is null and schemecode='HK'  and EmployeeCompanyCode='CFS' and RecruiterGradeId !='DUMMY')a left join vSZOADM2 b on (a.consultantId=b.employeeId )"+
						" where ( b.resignEffectiveDate is null and a.recruiterid=b.recuiterId) order by ConsultantId  ";
				PreparedStatement pss=con.prepareStatement(sqls);
				rs=pss.executeQuery();
				ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
				int columnCount = md.getColumnCount();   //获得列数 
				while (rs.next()) {
					if(!staffcode.equalsIgnoreCase(rs.getString("ConsultantId"))){
						Map<String,Object> rowData = new HashMap<String,Object>();
						for (int i = 1; i <= columnCount; i++) {
							rowData.put(md.getColumnName(i), Util.objIsNULL(rs.getObject(i))?"":rs.getObject(i));
						}
						list.add(rowData);
						staffcode=rs.getString("ConsultantId");
					}

				}
				
				super.updates("delete from consultantInfo");
				int num= batchSaveConsultantInfo(list,"consultantInfo");
				if(num>0){
					result="success";
				}else{
					throw new Exception();
				}
			}catch (Exception e) {
				result = e.getMessage();
				e.printStackTrace();
			}finally{
				try {
					if(!Util.objIsNULL(rs)&&rs.isClosed()==false){
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DBManager.closeCon(con);
				try {
					super.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;

		}

	}
