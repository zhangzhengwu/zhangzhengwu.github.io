package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

import util.*;
import entity.Admin;  
/**
 * login
 * @author kingxu
 *
 */
/*关于管理员信息操作的业务处理类*/
public class AdminDAO {
	 Connection con = null;
	 PreparedStatement ps = null;
	 Logger log=Logger.getLogger(AdminDAO.class);
	
	
	/* 验证管理员身份登陆 */
	public  Admin checkLogin(String username,String password) {
		
	//	boolean flag = false;
		Admin	admins =new Admin();
		String sql = "select * from admin where adminUsername=? and adminPassword=?";
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
			admins.setAdminUsername(rs.getString("adminUsername"));
			admins.setAdminPassword(rs.getString("adminPassword"));
			admins.setIsRoot(Integer.parseInt(rs.getString("isRoot")));
			}
			rs.close();
		} catch (Exception e) {
			//flag = false;
			e.printStackTrace();
			log.error("用户登录时出现 ："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return admins;
	}
	public  Admin checkLogin_AES_ENCRYPT(String username,String password) {
		
		//	boolean flag = false;
		Admin	admins =new Admin();
		String sql = "select * from admin where adminUsername=? and adminPassword=AES_ENCRYPT(?,?)";
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, Util.convoy);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
				//	flag = true;
				admins.setAdminUsername(rs.getString("adminUsername"));
				admins.setAdminPassword(rs.getString("adminPassword"));
				admins.setIsRoot(Integer.parseInt(rs.getString("isRoot")));
			}
			rs.close();
		} catch (Exception e) {
			//flag = false;
			e.printStackTrace();
			log.error("用户登录时出现:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return admins;
	}
	/**
	 * 根据Staffcode查询grade
	 * @param staffcode
	 * @return
	 */
	public  String findGrade(String staffcode){
		String grade="";
		String sql="select staffcode,grade from(select EmployeeId as staffcode,grade from cons_list union"+ 
					" select staffcode,grade from staff_list)a where staffcode=? group by staffcode ";
		try{
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				grade=rs.getString("grade");
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Convoy  进行登录身份验证时出现：==AdminDAO   line in 91====="+e);
		}finally{
			DBManager.closeCon(con);
		}
		return grade;
	}
	
	/**
	 * convoy Consultant  虚拟登录
	 * @param username
	 * @param password
	 * @return
	 */
	public  String checkStaffcode(String username,String password) {
		
		//	boolean flag = false;
		String staffcode="";
		String sql = "select staffcode from staff_list where staffcode=? and staffcode=?";
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
				//	flag = true;
				staffcode=rs.getString("staffcode");
			}
			rs.close();
		} catch (Exception e) {
			//flag = false;
			e.printStackTrace();
			log.error("Convoy  Consultant 进行虚拟登录时出现："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return staffcode;
	}
	/*更改用户密码*/
	public  boolean ChangePassword(Admin admin) {
		try {
			//DBManager db = new DBManager();
			String sql = "update admin set adminPassword=AES_ENCRYPT(?,?) where adminUsername=? ";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1,admin.getAdminPassword());
			ps.setString(2,Util.convoy);
			ps.setString(3, admin.getAdminUsername());
			int result = ps.executeUpdate();
			 
			if( result > 0)
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return false;
	}
	
	/*得到某个用户的权限*/
	public  int getIsRoot(String adminUsername) { 
		String sql = "select isRoot from admin where adminUsername=?";
		ResultSet rs;
		int isRoot = 0;
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, adminUsername);
			rs = ps.executeQuery();
			if (rs.next()) {
				isRoot = rs.getInt("isRoot");
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
		return isRoot;
	}
	/*判断是否有相同的用户名*/
	public  int getIsUserName(String adminUsername) { 
		String sql = "select count(1) as num from admin where adminUsername=?";
		ResultSet rs;
		int haseCode = 0;
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1,adminUsername);
			rs = ps.executeQuery();
			if (rs.next()) {
				haseCode = rs.getInt("num");
			}else{
				haseCode=0;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断是否存在相同用户时出现："+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return haseCode;
	}
	
	/*得到所有的用户信息*/
	@SuppressWarnings("unchecked")
	public  ArrayList<Admin> QueryAllAdminInfo() {
		String sql = "select * from admin ";
				
		ArrayList adminList = new ArrayList();
		ResultSet rs;
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Admin admin = new Admin();
				admin.setAdminUsername(rs.getString("adminUsername"));
				admin.setAdminPassword(rs.getString("adminPassword"));
				admin.setIsRoot(rs.getInt("isRoot"));
				adminList.add(admin);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取所有用户信息时出现："+e.toString());
		} finally {
			DBManager.closeCon(con);
		} 
		
		return adminList;
	}
	
	/*根据记录编号删除某个用户信息*/
	public  int DelAdminInfo(String adminUsername) {
		int result = 0;
		String sql = "delete from admin where adminUsername=?";
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, adminUsername);
			result = ps.executeUpdate();
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("删除用户时出现："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return result;	
	}
	
	/*添加管理员信息*/
	public  boolean AddAdminInfo(Admin admin) {
		String sql = "insert into admin (adminUsername,adminPassword,isRoot) values (?,AES_ENCRYPT(?,?),?)";
		int effectCount = 0;
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, admin.getAdminUsername());
			ps.setString(2, admin.getAdminPassword());
			ps.setString(3, Util.convoy);
			ps.setInt(4, admin.getIsRoot());
			effectCount = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("添加用户时出现："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return effectCount>0?true:false;
	}
	
}
