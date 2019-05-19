package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import util.DBManager;
import util.Page;
import util.Util;
import dao.AdminDao02;
import entity.Admin;



public class AdminDao02Impl implements AdminDao02{


	static Connection connection = null;
	static PreparedStatement ps = null;
	ResultSet rs=null;
	static Logger logger = Logger.getLogger(AdminDao02Impl.class);
	
	/**
	 * 添加
	 */
	public boolean AddAdmin(Admin admin) {
		String sql = "insert into admin (adminUsername,adminPassword,isRoot) values (?,AES_ENCRYPT(?,?),?)";
		int effectCount = 0;
		try {
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setString(1, admin.getAdminUsername());
			ps.setString(2, admin.getAdminPassword());
			ps.setString(3, Util.convoy);
			ps.setInt(4, admin.getIsRoot());
			effectCount = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加用户时出现："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return effectCount>0?true:false;
	}

	/**
	 * 删除
	 */
	public int DelAdminInfo(String adminUsername) {
		int result = 0;
		String sql = "delete from admin where adminUsername=?";
		try {
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setString(1, adminUsername);
			result = ps.executeUpdate();
		 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户时出现："+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return result;	
	}

	/**
	 * 获取行数
	 */
	public int getRows(String adminUsername) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select count(*) as count from admin");
			stringBuffer.append(" where adminUsername like '%"+adminUsername+"%' ");
			stringBuffer.append(" group by adminUsername");

			ps=connection.prepareStatement(stringBuffer.toString());
			logger.info("Admin Exp SQL:"+ps.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("在QuerAdmin 中根据条件查询数据个数时出现："+e.toString());
				num=0;
			}finally{
				DBManager.closeCon(connection);
			}
			return num;
	}
	
	
	/**
	 * 判断是否有相同的用户名
	 * 
	 */
	public int getIsUserName(String adminUsername) { 
		String sql = "select count(1) as num from admin where adminUsername=?";
		ResultSet rs;
		int haseCode = 0;
		try {
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
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
			logger.error("判断是否存在相同用户时出现："+e.toString());
		} finally {
			DBManager.closeCon(connection);
		}
		return haseCode;
	}
	

	/**
	 * 查询
	 */
	public List<Admin> queryReqeustList(String adminUsername, Page page) {
		
		List<Admin> list=new ArrayList<Admin>();		
		try {
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select *from admin");
			stringBuffer.append(" where adminUsername like '%"+adminUsername+"%' ");
			stringBuffer.append(" group by adminUsername");
			stringBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			ps=connection.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			
			while(rs.next()){
				Admin rsBean=new Admin();
				rsBean.setAdminUsername(rs.getString(1));
				rsBean.setAdminPassword(rs.getString(2));
				rsBean.setIsRoot(Integer.parseInt(rs.getString(3)));			
				list.add(rsBean);
			}
			rs.close();		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY ADMIN ERROR!"+e);
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

	/**
	 * 得到某个用的权限
	 */
	public int getIsRoot(String adminUsername) {
		String sql = "select isRoot from admin where adminUsername=?";
		ResultSet rs;
		int isRoot = 0;
		try {
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
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
			DBManager.closeCon(connection);
		}
		return isRoot;
	}

	/**
	 * 修改用户密码
	 */
	public boolean ChangePassword(Admin admin) {
		try {
			//DBManager db = new DBManager();
			String sql = "update admin set adminPassword=AES_ENCRYPT(?,?) where adminUsername=? ";
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
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
			DBManager.closeCon(connection);
		}
		return false;
	}

	//验证当前密码
	public boolean checkLogin_AES_ENCRYPT(String adminUsername,String adminPassword) {
		boolean flag = false;
		String sql = "select * from admin where adminUsername=? and adminPassword=AES_ENCRYPT(?,?)";
		try {
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setString(1, adminUsername);
			ps.setString(2, adminPassword);
			ps.setString(3, Util.convoy);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error("用户登录时出现:"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return flag;
	}
	/**
	 * 修改密码
	 */
	public boolean changePassword(Admin admin) {
		try {
			String sql = "update admin set adminPassword=AES_ENCRYPT(?,?) where adminUsername=? ";
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setString(1,admin.getAdminPassword());
			ps.setString(2,Util.convoy);
			ps.setString(3, admin.getAdminUsername());
			int result = ps.executeUpdate();			 
			if( result > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return false;
	}

	/**
	 * 编辑
	 */
	public String edit(Admin admin) {
		try {
			String sql = "update admin set isRoot=? where adminUsername=? ";
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setInt(1,admin.getIsRoot());
			ps.setString(2,admin.getAdminUsername());
			ps.executeUpdate();			 
			//if( result > 0)
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return null;
		
	}
}
