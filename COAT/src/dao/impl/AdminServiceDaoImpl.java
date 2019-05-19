package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;

import dao.AdminServiceDao;
import entity.C_Adminservice;

public class AdminServiceDaoImpl implements AdminServiceDao{
	Logger logger = Logger.getLogger(AdminServiceDaoImpl.class);
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	/**
	 * 保存AdminService
	 */
	 public int add(C_Adminservice adminservice) {
		int num=-1;
		try{
			synchronized (this) {
				String refno=findref();
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
				else
					adminservice.setRefno(refno);
			}
			
			conn=DBManager.getCon();
			String sql="insert c_adminservice values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			ps=conn.prepareStatement(sql);
			ps.setString(1, adminservice.getRefno());
			ps.setString(2, adminservice.getStaffcode());
			ps.setString(3, adminservice.getStaffname());
			ps.setString(4, adminservice.getUserType());
			ps.setString(5, adminservice.getLocation());
			ps.setString(6, adminservice.getFluorTube());
			ps.setString(7, adminservice.getFloor());
			ps.setString(8, adminservice.getSeat());
			ps.setString(9, adminservice.getPhoneRepair());
			ps.setString(10, adminservice.getPhoneNumber());
			ps.setString(11, adminservice.getPhoneRpass());
			ps.setString(12, adminservice.getPhoneNumber2());
			ps.setString(13, adminservice.getCopierRepair());
			ps.setString(14, adminservice.getFloor2());
			ps.setString(15, adminservice.getCopier());
			ps.setString(16, adminservice.getOfficeMaintenance());
			ps.setString(17, adminservice.getFloor3());
			ps.setString(18, adminservice.getDescription());
			ps.setString(19, adminservice.getRemark());
			ps.setString(20, adminservice.getCreator());
			ps.setString(21, adminservice.getCreateDate());
			ps.setString(22, adminservice.getStatus());
			ps.setString(23, adminservice.getSfyx());
			num=ps.executeUpdate();
		
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}
	 /**
	  * 获取订单编号
	  * @return
	  */
public String findref(){
	String num=null;
	try{
		conn=DBManager.getCon();
		String sql="select count(*) from c_adminservice";
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next()){
			num=rs.getString(1);
			if(rs.getInt(1)<9){
				num="000"+(rs.getInt(1)+1);
			}else if(rs.getInt(1)<99){
				num="00"+(rs.getInt(1)+1);
			}else if(rs.getInt(1)<999){
				num="0"+(rs.getInt(1)+1);
			}else{
				num=""+(rs.getInt(1)+1);
			}
			num=Constant.ADMINSERVICE+DateUtils.Ordercode()+num;
		}rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}
	/**
	 * 分页查询
	 */
	public List<C_Adminservice> findAdminServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String location,String requestType, Page page) {
		List<C_Adminservice> adminserviceList=new ArrayList<C_Adminservice>();
		try{
			conn=DBManager.getCon();
			String sql="select refno,staffcode,staffname,location,creator,createDate,status,remark from c_adminservice" +
					" where sfyx='Y' and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					"  DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?  and location like ? "+(Util.objIsNULL(requestType)?"":" and "+requestType+"='Y' ") +
					" order by createDate desc limit ?,?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+location+"%");
			ps.setInt(8, (page.getCurPage()-1)*page.getPageSize());
			ps.setInt(9, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				adminserviceList.add(new C_Adminservice(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return adminserviceList;
	}
	
	/**
	 * Adminservice 导出
	 */
	public List<C_Adminservice> downAdminServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String location,String requestType) {
		List<C_Adminservice> adminserviceList=new ArrayList<C_Adminservice>();
		try{
			conn=DBManager.getCon();
			String sql="select * from c_adminservice" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and location like ? "+(Util.objIsNULL(requestType)?"":" and "+requestType+"='Y' ")+
					" order by createDate desc ";
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+location+"%");
			rs=ps.executeQuery();
			while(rs.next()){
				adminserviceList.add(new C_Adminservice(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("fluorTube"),
						rs.getString("floor"),
						rs.getString("seat"),
						rs.getString("phoneRepair"),
						rs.getString("phoneNumber"),
						rs.getString("phoneRpass"),
						rs.getString("phoneNumber2"),
						rs.getString("copierRepair"),
						rs.getString("floor2"),
						rs.getString("copier"),
						rs.getString("officeMaintenance"),
						rs.getString("floor3"),
						rs.getString("description"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return adminserviceList;
	}
	
	/**
	 * 查询总条数
	 */
	public int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status,String location,String requestType) {
		int num=-1;
		try{
			conn=DBManager.getCon();
			String sql="select count(*)as num from c_adminservice" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and location like ? "+(Util.objIsNULL(requestType)?"":" and "+requestType+"='Y' ") ;
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+location+"%");
			 
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt("num");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}
	public C_Adminservice findAdminserviceByRef(String refno) {
		C_Adminservice adminservice=null;
		try{
			conn=DBManager.getCon();
			String sql="select * from c_adminservice where refno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				adminservice=new C_Adminservice(rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("fluorTube"),
						rs.getString("floor"),
						rs.getString("seat"),
						rs.getString("phoneRepair"),
						rs.getString("phoneNumber"),
						rs.getString("phoneRpass"),
						rs.getString("phoneNumber2"),
						rs.getString("copierRepair"),
						rs.getString("floor2"),
						rs.getString("copier"),
						rs.getString("officeMaintenance"),
						rs.getString("floor3"),
						rs.getString("description"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
				
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return adminservice;
	}
	/**
	 * 操作AdminService
	 */
	public int modify(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+type+"' where sfyx='Y' and status='Submitted' and refno='"+refno+"'";
			 ps=conn.prepareStatement(sql);
			 ps.execute();
			
			 sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			 conn.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}
	
	public int VOID(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+type+"',sfyx='D' where status!='Completed' and sfyx='Y' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			conn.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}
	
	/**
	 * 删除AdminService
	 */
	public int Deleted(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+type+"',sfyx='D' where status='Submitted' and sfyx='Y' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			conn.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	} 
	
	
	
	
	public Result downReportingForADMService(String startDate, String endDate) {
		Result rss=null;
		try{
			conn=DBManager.getCon();
			String sql="select * from(SELECT 'Month',ca.createDate,ca.refno,staffcode,staffname,ca.userType,"+
						" if(fluorTube='Y',1,'')as fluorTube,"+
						" if(PhoneRepair='Y',1,'') as PhoneRepair," +
						" if(Phonerpass='Y',1,'') as Phonerpass," +
						" if(copierRepair='Y',1,'') as copierRepair," +
						" if(officeMaintenance='Y',1,'')as officeMaintenance,if(description is null,'',description) as description "+
						" from c_adminservice ca"+
						" where ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
						" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
			
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);

			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出ADMService 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return rss;
	}
	 
}
