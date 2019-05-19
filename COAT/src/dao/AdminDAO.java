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
/*���ڹ���Ա��Ϣ������ҵ������*/
public class AdminDAO {
	 Connection con = null;
	 PreparedStatement ps = null;
	 Logger log=Logger.getLogger(AdminDAO.class);
	
	
	/* ��֤����Ա��ݵ�½ */
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
			log.error("�û���¼ʱ���� ��"+e);
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
			log.error("�û���¼ʱ����:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return admins;
	}
	/**
	 * ����Staffcode��ѯgrade
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
			log.error("Convoy  ���е�¼�����֤ʱ���֣�==AdminDAO   line in 91====="+e);
		}finally{
			DBManager.closeCon(con);
		}
		return grade;
	}
	
	/**
	 * convoy Consultant  �����¼
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
			log.error("Convoy  Consultant ���������¼ʱ���֣�"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return staffcode;
	}
	/*�����û�����*/
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
	
	/*�õ�ĳ���û���Ȩ��*/
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
	/*�ж��Ƿ�����ͬ���û���*/
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
			log.error("�ж��Ƿ������ͬ�û�ʱ���֣�"+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return haseCode;
	}
	
	/*�õ����е��û���Ϣ*/
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
			log.error("��ȡ�����û���Ϣʱ���֣�"+e.toString());
		} finally {
			DBManager.closeCon(con);
		} 
		
		return adminList;
	}
	
	/*���ݼ�¼���ɾ��ĳ���û���Ϣ*/
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
			log.error("ɾ���û�ʱ���֣�"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return result;	
	}
	
	/*��ӹ���Ա��Ϣ*/
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
			log.error("����û�ʱ���֣�"+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return effectCount>0?true:false;
	}
	
}
