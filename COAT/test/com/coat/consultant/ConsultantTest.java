package com.coat.consultant;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import net.sf.json.JSONObject;

import org.junit.Test;

import util.DBManager_sqlservler;


import com.coat.consultant.dao.ConsultantInfoDao;
import com.coat.consultant.dao.impl.ConsultantInfoDaoImpl;
import com.coat.consultant.entity.ConsultantInfo;
import com.coat.consultant.entity.ConsultantList;
public class ConsultantTest extends TestCase {

	@Test
	public void testSa(){
		Connection  con=null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		ConsultantInfoDao c=new ConsultantInfoDaoImpl();
		try{

			con=DBManager_sqlservler.getCon();
			String sqls="select top 1000  a.*,directLine,email,HKID,Location,halfConsultant,dateJoin,mobile,adtreehead,ddtreehead  from (select distinct ConsultantId,Alias,SurName,GivenName,ChineseSurName,ChineseName,GradeId,gradeName,recruiterId,recruiterGradeId,recruiterGradeName,territoryStartDate,InternalPositionid,InternalPositionName,ExternalPositionId,externalPositionname from vSZO_SOS_ConsultantInfo"+
						" where territoryEndDate is null and schemecode='HK' and RecruiterGradeId!='DUMMY')a left join vSZOADM2 b on (a.consultantId=b.employeeId )"+
						" where ( b.resignEffectiveDate is null and a.recruiterid=b.recuiterId)  ";
			PreparedStatement pss=con.prepareStatement(sqls);
			System.out.println(sqls);
			ResultSet rs=null;
			rs=pss.executeQuery();
			ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
			int columnCount = md.getColumnCount();   //获得列数 
			while (rs.next()) {
				Map<String,Object> rowData = new HashMap<String,Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);

			}
			rs.close();
/*
			ConsultantInfoDao c=new ConsultantInfoDaoImpl();

			ConsultantInfo cons=new ConsultantInfo();
			cons.setConsultantId("xxx-x-x--x-x-x");
			list.add(cons);
			cons=new ConsultantInfo();
			cons.setConsultantId("yy-y-y-y-y-");
			list.add(cons);*/
			int num=c.batchSaveConsultantInfo(list,"consultantInfo");
			System.out.println(num);


			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

	}

}
