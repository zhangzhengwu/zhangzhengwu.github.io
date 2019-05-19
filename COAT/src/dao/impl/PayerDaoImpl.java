package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import dao.PayerDao;
import entity.Change_Macau;
import entity.Change_Record;

/**
 * PayerDao 实现类
 * @author King.XU
 * 
 */
public class PayerDaoImpl implements PayerDao {
	Connection con = null;
	PreparedStatement ps = null;
	String sql = "";
	Logger logger = Logger.getLogger(PayerDaoImpl.class);
	/**
	 * GetEnglishName
	 */
	public String GetEnglishName(String payer) {
		String EmployeeName = "";
		try {
			con = DBManager.getCon();
			sql = "select EmployeeName from cons_list where EmployeeId='"
					+ payer + "'";
			logger.info("获取cons_list英文名称SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				EmployeeName = rs.getString(1);
			}
			rs.close();
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
			sql = "select Position from cons_list where EmployeeId='"
				+ payer + "'";
			logger.info("获取职位英文名称SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				position = rs.getString(1);
			}
			rs.close();
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
	public String VailPayer(String payer,String user) {
		String EmployeeName = "";
		try {
			con = DBManager.getCon();
			sql = "select EmployeeName from cons_macau where EmployeeId='"
				+ payer + "'";
			logger.info("获取cons_Macau英文名称SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				EmployeeName = rs.getString(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(user+"在PayerDaoImpl.VailPayer中获取cons_Macau英文名称异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(user+"在PayerDaoImpl.VailPayer中获取cons_Macau英文名称异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return EmployeeName;
	}

	//获取额外的名片数量
	public int getadd(String StaffNo) {
		int num = 0;
		try {
			con = DBManager.getCon();
			sql = "select FORMAT(sum(additional),0) as num from nq_additional where sfyx='Y' and  initials='"
					+ StaffNo + "' and year(add_date)=year(now()) ";
			logger.info("获取额外的名片数量SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				num = Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		} catch (Exception e) {
			num = 0;
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	//获取当前今年使用了多少名片数量
	public int getused(String StaffNo) {
		int num = 0;
		try {
			con = DBManager.getCon();
			sql = "select FORMAT(sum(quantity),0) as used from req_record where code='"
					+ StaffNo
					+ "'  and YEAR(request_date) = YEAR(NOW())";
			logger.info("获取当前用户今年使用了多少名片数量SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				num = Integer.parseInt(rs.getString("used"));
			}
			rs.close();
		} catch (Exception e) {
			num = 0;
		
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * //获取当前今年使用了多少名片数量
	 * 专属DD/DD Tree Head
	 */
	
	public int getDDused(String StaffNo) {
		int num = 0;
		try {
			con = DBManager.getCon();
			sql = "select FORMAT(sum(quantity),0) as used from req_record where code='"
				+ StaffNo
				+ "' and YEAR(request_date) = YEAR(NOW())";
			logger.info("获取当前用户今年使用了多少名片数量SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				num = Integer.parseInt(rs.getString("used"));
			}
			rs.close();
		} catch (Exception e) {
			num = 0;
			
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	
	//保存财务信息表
	public void saveChange(Change_Record cr) {
		try {
			con = DBManager.getCon();
			sql = "insert change_record values('"+cr.getStaffCode()+"','"
					+ cr.getName() + "','" + cr.getNumber() + "','"
					+ cr.getAmount() + "','" + cr.getPayer() + "','"
					+ cr.getRemarks() + "','','','"+cr.getAddDate()+"','"+cr.getUp_date()+"','"+cr.getUp_name()+"','Y')";
			logger.info("保存财务信息表SQL- staffcode:"+cr.getStaffCode()+"----"+sql);
			ps = con.prepareStatement(sql);
			int r = ps.executeUpdate();
			if (r <= 0) {
				logger.info("保存财务信息表失败!- staffcode:"+cr.getStaffCode());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("保存财务信息表异常!- staffcode:"+cr.getStaffCode()+"----"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("保存财务信息表异常!- staffcode:"+cr.getStaffCode()+"----"+e);
		}

	}
	//删除财务表信息
	public void deletePayer(String name ,String StaffNo, String Date) {
		try {
			con=DBManager.getCon();
			sql="update change_record set sfyx='D',up_date='"+DateUtils.getNowDateTime()+"', up_name ='"+name+"' where StaffCode='"+StaffNo+"' and AddDate='"+Date+"'";
			logger.info("删除财务信息表SQL:"+sql);
			ps=con.prepareStatement(sql);
			int r=ps.executeUpdate();
			if(r<0){
				logger.info("删除财务信息表失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("删除财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("删除财务信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
	
	}
	//保存财务信息表
	public void saveMacauChange(Change_Macau cr) {
		try {
			con = DBManager.getCon();
			sql = "insert change_Macau values('" + cr.getStaffCode() + "','"
			+ cr.getName() + "','" + cr.getNumber() + "','"
			+ cr.getAmount() + "','" + cr.getPayer() + "','"
			+ cr.getRemarks() + "','','','"+cr.getAddDate()+"','"+cr.getUp_date()+"','"+cr.getUp_name()+"','Y')";
			logger.info("保存Macau财务信息表SQL:"+sql);
			ps = con.prepareStatement(sql);
			int r = ps.executeUpdate();
			if (r <= 0) {
				logger.info("保存Macau财务信息表失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("保存Macau财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("保存Macau财务信息表异常！"+e);
		}
		
	}
	//删除财务表信息
	public void deleteMacauPayer(String name ,String StaffNo, String Date) {
		try {
			con=DBManager.getCon();
			sql="update change_Macau set sfyx='D',up_date='"+DateUtils.getNowDateTime()+"', up_name ='"+name+"' where StaffCode='"+StaffNo+"' and AddDate='"+Date+"'";
			logger.info("删除Macau财务信息表SQL:"+sql);
			ps=con.prepareStatement(sql);
			int r=ps.executeUpdate();
			if(r<0){
				logger.info("删除Macau财务信息表失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("删除Macau财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("删除Macau财务信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		
	}
	public int payerNumber(String payer){
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="select Tzero(sum(Number)) as num from change_record where sfyx='Y' and YEAR(up_date) = YEAR(NOW()) and payer=? ";
			logger.info("查询财务支付数量 sql ===="+sql+"==="+payer);
			ps=con.prepareStatement(sql);
			ps.setString(1, payer);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询财务支付数量 时出现 ===="+e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

}
