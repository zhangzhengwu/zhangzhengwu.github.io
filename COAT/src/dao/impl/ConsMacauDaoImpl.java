package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.AdditionalDao;
import dao.ConsMacauDao;
import entity.ConsList;
import entity.ConsListMacau;
import entity.QueryAdditional;

public class ConsMacauDaoImpl implements ConsMacauDao{

	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(ConsMacauDaoImpl.class);


	//查询conslistMacau
	public List<ConsListMacau> queryReqeustList(String employeeId,String c_Name, String HKID, String recruiterId, Page page) {
		
		List<ConsListMacau> list=new ArrayList<ConsListMacau>();
		try {
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select *from cons_macau");
			stringBuffer.append(" where employeeId like '%"+employeeId+"%' "+" and c_Name like '%"+c_Name+"%'"+" and HKID like '%"+HKID+"%'"+" and recruiterId like '%"+recruiterId+"%'");
			stringBuffer.append(" group by employeeId ");
			stringBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			ps=connection.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			
			while(rs.next()){
				ConsListMacau rsBean=new ConsListMacau();
				rsBean.setEmployeeId(rs.getString(1));
				rsBean.setAlias(rs.getString(2));
				rsBean.setC_Name(rs.getString(3));			
				rsBean.setJoinDate(rs.getString(4));
				rsBean.setDirectLine(rs.getString(5));
				rsBean.setEmail(rs.getString(6));
				rsBean.setEmployeeName(rs.getString(7));
				rsBean.setExternalPosition(rs.getString(8));
				rsBean.setGrade(rs.getString(9));
				rsBean.setHKID(rs.getString(10));
				rsBean.setGroupDateJoin(rs.getString(11));
				rsBean.setLocation(rs.getString(12));
				rsBean.setMobile(rs.getString(13));
				rsBean.setPosition(rs.getString(14));
				rsBean.setC_PositionName(rs.getString(15));
				rsBean.setE_PositionName(rs.getString(16));
				rsBean.setRecruiterId(rs.getString(17));
				rsBean.setRecruiterName(rs.getString(18));
				rsBean.setTelephoneNo(rs.getString(19));
				list.add(rsBean);
			}
			rs.close();		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY CONSMACAU ERROR!"+e);
		} finally{ 
			DBManager.closeCon(connection);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return list;
	}
	
	
	//获取总行数
	public int getRows(String employeeId, String c_Name, String HKID,String recruiterId) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select count(*) as count from cons_macau");
			stringBuffer.append(" where employeeId like '%"+employeeId+"%' "+" and c_Name like '%"+c_Name+"%'"+" and HKID like '%"+HKID+"%'"+" and recruiterId like '%"+recruiterId+"%'");
			stringBuffer.append(" group by employeeId ");

			ps=connection.prepareStatement(stringBuffer.toString());
			logger.info("ConsMacauDaoImpl SQL:"+ps.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("在ConsMacauDaoImpl 中根据条件查询数据个数时出现："+e.toString());
				num=0;
			}finally{
				DBManager.closeCon(connection);
			}
			return num;
	}

	//上传Excel
	public int saveMacauList(List<List<Object>> list) {
		//int num=-1;
		int numt=0;
		int numf=0;
		Connection conn=null;
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			
			String sql="insert into cons_macau(EmployeeId,EmployeeName,C_Name,Alias,joinDate,DirectLine,Email,ExternalPosition,Grade,HKID,GroupDateJoin,Location,Mobile,Position,C_PositionName,E_PositionName,RecruiterId,RecruiterName,TelephoneNo)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps2=connection.prepareStatement(sql);
			for(int i=3;i<list.size();i++){
				List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(0)+"")){
				
				if(findbyStaffCode(list2.get(0)+"")){      
					ps2.setString(1, list2.get(0)+"");
					ps2.setString(2, list2.get(1)+"");
					ps2.setString(3, list2.get(2)+"");
					ps2.setString(4, list2.get(3)+"");
					ps2.setString(5, list2.get(4)+"");
					ps2.setString(6, list2.get(5)+"");
					ps2.setString(7, list2.get(6)+"");
					ps2.setString(8, list2.get(7)+"");
					ps2.setString(9, list2.get(8)+"");
					ps2.setString(10, list2.get(9)+"");
					ps2.setString(11, list2.get(10)+"");
					ps2.setString(12, list2.get(12)+"");
					ps2.setString(13, list2.get(13)+"");
					ps2.setString(14, list2.get(14)+"");
					ps2.setString(15, list2.get(15)+"");
					ps2.setString(16, list2.get(16)+"");
					ps2.setString(17, list2.get(17)+"");
					ps2.setString(18, "");
					ps2.setString(19, "");
			
					/*for(int j=0;j<19;j++){
						ps2.setString((j+1), list2.get(j)+"");
					}*/
					ps2.addBatch();
					numt+=1;			//插入成功条数
					
				}else{
					logger.info(list.get(0)+"--->上傳新增顧問時已存在，未保存.");
					//System.out.println(list2.get(0)+"已经存在");
					numf+=1;			//已存在条数
					
				}
				
				if(!Util.objIsNULL(list2.get(3))&& list2.get(3).toString().substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
					if(Util.findnq_addition(list2.get(0)+"")==0){	//该staffcode没在additional中
						AdditionalDao additionDao=new AdditionalDaoImpl();
						QueryAdditional qa=new QueryAdditional();
						qa.setStaffNo(list2.get(0)+"");
						qa.setName(list2.get(6)+"");
						qa.setAdditional(100+"");
						qa.setNum(100+"");
						qa.setRemark("New Comer");
						qa.setAdd_name("Upload");
						qa.setSfyx("Y");
						additionDao.add(qa);
					}
				}
				if(list2.get(8).toString().equals(Constant.PA_TYPE)||list2.get(8).toString().equals(Constant.SA_TYPE)||list2.get(8).toString().equals(Constant.CA_TYPE)){
					if(list2.get(8).toString().equals(Constant.PA_TYPE)){
					 		sql="insert nq_additional values('"+list2.get(0).toString()+"','"+list2.get(6).toString()+"','-400','PA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
					}else if(list2.get(8).toString().equals(Constant.SA_TYPE)){
							sql="insert nq_additional values('"+list2.get(0).toString()+"','"+list2.get(6).toString()+"','-300','SA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
					}else if(list2.get(8).toString().equals(Constant.CA_TYPE)){
						sql="insert nq_additional values('"+list2.get(0).toString()+"','"+list2.get(6).toString()+"','-300','CA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
					}
					pss=conn.prepareStatement(sql);
					pss.executeUpdate(); 
				}
			}	
			}
			ps2.executeBatch();
			connection.commit();
			//num=list.size();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
			DBManager.closeCon(conn);
		}
		return numt;
		
	}
	
	//根据staffcode判断数据是否重复
	public boolean findbyStaffCode(String staffcode){
		boolean flag=false;
		Connection connections=null;
		try{
			connections=DBManager.getCon();
			String sql="select EmployeeId from cons_macau where EmployeeId=?";
			PreparedStatement ps=connections.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs=ps.executeQuery();
			//flag=rs.next();
		
			if(rs.next()){
				flag=false;
			}else{
				flag=true;
			}
			
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("根據staffcode查詢Consultant是否存在時出現異常-->"+e.getMessage());
		}finally{
			DBManager.closeCon(connections);
		}
		
		return flag;
	}
	
}
