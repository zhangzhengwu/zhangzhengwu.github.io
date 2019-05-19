package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.AdditionalDao;
import dao.ConsListDao;
import entity.ConsList;
import entity.QueryAdditional;

public class ConsListDaoImpl implements ConsListDao{

	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(ConsListDaoImpl.class);
	
	
	/**
	 * 根据条件查询
	 */
	public List<ConsList> queryReqeustList(String employeeId, String c_Name,
			String HKID, String recruiterId, Page page) {
		List<ConsList> list=new ArrayList<ConsList>();
		
		try {
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select *from cons_list");
			stringBuffer.append(" where employeeId like '%"+employeeId+"%' "+" and c_Name like '%"+c_Name+"%'"+" and HKID like '%"+HKID+"%'"+" and recruiterId like '%"+recruiterId+"%'");
			stringBuffer.append(" group by employeeId ");
			stringBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			ps=connection.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			
			while(rs.next()){
				ConsList rsBean=new ConsList();
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
				rsBean.setADTreeHead(rs.getString(20));
				rsBean.setDDTreeHead(rs.getString(21));
				list.add(rsBean);
			}
			rs.close();		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY CONS_LIST ERROR!"+e);
		} finally{ 
				DBManager.closeCon(connection);
		}
		return list;
	}

	/**
	 * 获取行数
	 * @param name
	 * @return
	 */
	public int getRows(String employeeId, String c_Name,String HKID, String recruiterId) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select count(*) as count from cons_list");
			stringBuffer.append(" where employeeId like '%"+employeeId+"%' "+" and c_Name like '%"+c_Name+"%'"+" and HKID like '%"+HKID+"%'"+" and recruiterId like '%"+recruiterId+"%'");
			stringBuffer.append(" group by employeeId ");

			ps=connection.prepareStatement(stringBuffer.toString());
			logger.info("ConsList Exp SQL:"+ps.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("在QuerDao 中根据条件查询数据个数时出现："+e.toString());
				num=0;
			}finally{
				DBManager.closeCon(connection);
			}
			return num;
	}
	

	/**
	 * 查找集合
	 */
	public List<ConsList> downConsList(String employeeId, String c_Name,String HKID, String recruiterId) {
		List<ConsList> list=new ArrayList<ConsList>();
		
		StringBuffer stringBuffer=new StringBuffer("select EmployeeId,Alias,C_Name,HKID,RecruiterId," +
				"joinDate,DirectLine,Email,EmployeeName,ExternalPosition,GroupDateJoin,Location,C_PositionName,E_PositionName,RecruiterName,TelephoneNo,ADTreeHead,DDTreeHead from cons_list");
		stringBuffer.append(" where employeeId like '%"+employeeId+"%' "+"and c_Name like '%"+c_Name+"%'"+"and HKID like '%"+HKID+"%'"+"and recruiterId like '%"+recruiterId+"%'");
		stringBuffer.append(" group by employeeId");
				
		try{
			connection = DBManager.getCon();
			logger.info("ConsList Exp SQL:"+stringBuffer);
		//	System.out.println(stringBuffer.toString());
			ps = connection.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
		
			if(rs!=null){
				while(rs.next()){
					list.add(new ConsList(
							rs.getString("employeeId"),
							rs.getString("alias"),
						    rs.getString("c_Name"),
						    rs.getString("joinDate"),
				    		rs.getString("directLine"),
				    		rs.getString("email"),
				    		rs.getString("employeeName"),
				    		rs.getString("externalPosition"),
				    		rs.getString("HKID"),
				    		rs.getString("groupDateJoin"),
				    		rs.getString("location"),
				    		rs.getString("c_PositionName"),
				    		rs.getString("e_PositionName"),
				    		rs.getString("recruiterId"),
				    		rs.getString("recruiterName"),
				    		rs.getString("telephoneNo"),
				    		rs.getString("ADTreeHead"),
		    				rs.getString("DDTreeHead")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询ConsList 异常:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("查询ConsList 异常:"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return list;
	}

	public int saveConsultantList(List<List<Object>> list) throws SQLException {
		int num=-1;
		Connection conn=null;
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			Map<String,String> newComer=new HashMap<String,String>();
			String sql="insert into cons_list(EmployeeId,Alias,C_Name,joinDate,DirectLine,Email,EmployeeName,ExternalPosition,Grade,HKID,GroupDateJoin,Location,Mobile,Position,C_PositionName,E_PositionName,RecruiterId,RecruiterName,TelephoneNo,ADTreeHead,DDTreeHead)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps2=connection.prepareStatement(sql);
			for(int i=0;i<list.size();i++){
				List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(0)+"")){
				
				
				if(findbyStaffCode(list2.get(0)+"")){//未排除上传文件中重复的staffcode
					for(int j=0;j<21;j++){
						ps2.setString((j+1), list2.get(j)+"");
					}
					ps2.addBatch();
				}else{
					logger.info(list.get(0)+"--->上傳新增顧問時已存在，未保存.");
				}
				
				if(!Util.objIsNULL(list2.get(3))&& list2.get(3).toString().substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
					if(Util.findnq_addition(list2.get(0)+"")==0){//该staffcode没在additional中
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
				if(Constant.PA_TYPE.equalsIgnoreCase(list2.get(8)+"")||Constant.SA_TYPE.equalsIgnoreCase(list2.get(8)+"")||Constant.CA_TYPE.equalsIgnoreCase(list2.get(8)+"")){
					String sqls="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx)" +
							"values('"+list2.get(0)+"','"+list2.get(6)+"',-300,'"+list2.get(8).toString().toUpperCase()+"','Uploader-person','"+DateUtils.getDateToday()+"','','','Y');";
					pss=connection.prepareStatement(sqls);
					pss.executeUpdate(); 
				}else{
					if(!Util.objIsNULL(list2.get(3)) && list2.get(3).toString().substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
						newComer.put(list2.get(0)+"", list2.get(6)+"");
					}
				}
				
				
				
				
			/*	if(list2.get(8).toString().equals(Constant.PA_TYPE)||list2.get(8).toString().equals(Constant.SA_TYPE)||list2.get(8).toString().equals(Constant.CA_TYPE)){
					if(list2.get(8).toString().equals(Constant.PA_TYPE)){
					 		sql="insert nq_additional values('"+list2.get(0).toString()+"','"+list2.get(6).toString()+"','-400','PA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
					}else if(list2.get(8).toString().equals(Constant.SA_TYPE)){
							sql="insert nq_additional values('"+list2.get(0).toString()+"','"+list2.get(6).toString()+"','-300','SA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
					}else if(list2.get(8).toString().equals(Constant.CA_TYPE)){
						sql="insert nq_additional values('"+list2.get(0).toString()+"','"+list2.get(6).toString()+"','-300','CA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
					}
					pss=conn.prepareStatement(sql);
					pss.executeUpdate(); 
				}*/
				
				//获取已存在的staffcode
				
				
				//List<String> newStaff2= findStringDate("select initials from nq_additional where sfyx='Y' and remarks='New Comer' and additional=100   and initials in("+Arrays.asList(newComer.keySet()).toString().replace("[[", "'").replace("]]", "'").replaceAll(",", "','").replaceAll(" ", "")+")");
				List<String> newStaff2=new ArrayList<String>();
				sql="select initials from nq_additional where sfyx='Y' and remarks='New Comer' and additional=100   and initials in("+Arrays.asList(newComer.keySet()).toString().replace("[[", "'").replace("]]", "'").replaceAll(",", "','").replaceAll(" ", "")+")";
				pss=connection.prepareStatement(sql);
				ResultSet r=pss.executeQuery();
				while(r.next()){
					newStaff2.add(r.getString("initials"));
				}
				r.close();
			 
				List<Object[]> params=new ArrayList<Object[]>();
					Set<String> keylist=newComer.keySet();
					for(String key:keylist){
						if(!newStaff2.contains(key)){
							params.add(new Object[]{key,newComer.get(key)});
							sql="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) " +
									"values(?,?,100,'New Comer','Uploader-person',now(),'','','Y')";
							pss=connection.prepareStatement(sql);
							pss.execute();
						}
					}
			 
				
				
				
				
				
				
			}	
				
			}
			
			ps2.executeBatch();
			connection.commit();
			num=list.size();
		}catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}finally{
			DBManager.closeCon(connection);
			DBManager.closeCon(conn);
		}
		return num;
	}
	
	public boolean findbyStaffCode(String staffcode){
		boolean flag=false;
		Connection connections=null;
		try{
			connections=DBManager.getCon();
			String sql="select count(*) from cons_list where EmployeeId=?";
			PreparedStatement ps=connections.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs=ps.executeQuery();
			flag=rs.next();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("根據staffcode查詢Consultant是否存在時出現異常-->"+e.getMessage());
		}finally{
			DBManager.closeCon(connections);
		}
		
		return flag;
	}

	public String findMailByCode(String staffcode) {
		Connection con=null;
		String email="";
		try{
			con=DBManager.getCon();
			String sql="select email from namecardpro.cons_list where EmployeeId=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs=ps.executeQuery();
			if(rs.next()){
				email=rs.getString("email");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return email;
	}
	
	public String findNameByCode(String staffcode) {
		Connection con=null;
		String name="";
		try{
			con=DBManager.getCon();
			String sql="select EmployeeName from namecardpro.cons_list where EmployeeId=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs=ps.executeQuery();
			if(rs.next()){
				name=rs.getString("EmployeeName");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return name;
	}
	

	public String findMail_nameByCode(String staffcode) {
		Connection con=null;
		String val="";
		try{
			con=DBManager.getCon();
			String sql="select email,employeeName from namecardpro.cons_list where EmployeeId=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs=ps.executeQuery();
			if(rs.next()){
				val=rs.getString("email")+";~;"+rs.getString("employeeName");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return val;
	}
}
