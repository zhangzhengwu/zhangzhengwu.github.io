package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.PayerMacauDao;
import entity.Change_Macau;

public class PayerMacauDaoImpl implements PayerMacauDao {
	Connection con = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(PayerMacauDaoImpl.class);
	/**
	 * GetEnglishName
	 */
	public String GetEnglishName(String payer) {
		String EmployeeName = "";
		try {
			con = DBManager.getCon();
			String sql = "select EmployeeName from cons_macau where EmployeeId='"
					+ payer + "'";
			logger.info("获取cons_list英文名称SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				EmployeeName = rs.getString(1);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取cons_list英文名称异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("获取cons_list英文名称异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return EmployeeName;
	}
	
	/**
	 * GetPosition
	 */
	public String GetPosition(String payer) {
		String position = "";
		try {
			con = DBManager.getCon();
			String sql = "select Position from cons_macau where EmployeeId='"
				+ payer + "'";
			logger.info("获取职位英文名称SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				position = rs.getString(1);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取Position英文名称异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("获取Position英文名称异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return position;
	}
	/**
	 * queryChargeList
	 */
	public List<Change_Macau> queryChargeList(String startDate,
			String endDate, String staffcode) {
		List<Change_Macau> list = new ArrayList<Change_Macau>();
		StringBuffer sql= new StringBuffer("select StaffCode,Name,Number,Amount,Payer,Remarks,Infomed_to_FAD,charged_Month,AddDate from change_macau ");
		if(!Util.objIsNULL(startDate) && !Util.objIsNULL(endDate)){
			sql.append(" WHERE sfyx='Y' and DATE_FORMAT(up_date,'%Y-%m-%d') >= '"+startDate+"' and DATE_FORMAT(up_date,'%Y-%m-%d') <= '"+endDate+"'");
		}
		if (!Util.objIsNULL(staffcode)) {
			sql.append(" AND StaffCode like '%"+staffcode+"%'");
		}
		sql.append(" order by AddDate asc ");
		try {
			con = DBManager.getCon();
			logger.info("查询财务信息表 PayerMacauDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Change_Macau cqBean = new Change_Macau();
				cqBean.setStaffCode(rs.getString(1));
				cqBean.setName(rs.getString(2));
				cqBean.setNumber(rs.getString(3));
				cqBean.setAmount(rs.getString(4));
				cqBean.setPayer(rs.getString(5));
				cqBean.setRemarks(rs.getString(6));
				cqBean.setInfomed_to_FAD(rs.getString(7));
				cqBean.setCharged_Month(rs.getString(8));
				cqBean.setAddDate(rs.getString(9));
				list.add(cqBean);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询财务信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * 导出财务信息表
	 */
	public ResultSet selectCharge(String startDate, String endDate,
			String staffcode) {
		ResultSet rs=null;
		try{
			StringBuffer sql= new StringBuffer("select AddDate,StaffCode,Name,Number,Amount,Payer,Remarks from change_macau ");
			if(!Util.objIsNULL(startDate) && !Util.objIsNULL(endDate)){
				sql.append(" WHERE sfyx='Y' and DATE_FORMAT(adddate,'%Y-%m-%d') >= '"+startDate+"' and DATE_FORMAT(adddate,'%Y-%m-%d') <= '"+endDate+"'");
			}
			if (!Util.objIsNULL(staffcode)) {
				sql.append(" AND StaffCode like '%"+staffcode+"%'");
			}
			con = DBManager.getCon();
			logger.info("导出财务信息表PayerMacauDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出财务信息表异常！"+e);
		}finally
		{
			//关闭连接
			//DBManager.closeCon(con);
		}
		return rs;
	}
	 
	/**
	 * 刪除財務表數據
	 */
	public int deleteChargeRecord(String name,String StaffNo, String AddDate) {
		int d=-1;
		String sql="";
		try{
			sql="update  change_macau set up_date='"+DateUtils.getNowDateTime()+"' , up_name='"+name+"', sfyx='N' where StaffCode='"+StaffNo+"' and AddDate='"+AddDate+"'";
			con=DBManager.getCon();
			
			logger.info("刪除財務表數據PayermacauDaoImpl SQL:"+sql.toString());
			ps=con.prepareStatement(sql);
			d=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("刪除財務表數據异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return d;
	}


}
