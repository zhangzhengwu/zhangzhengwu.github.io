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
import dao.AdditionalDao;
import entity.QueryAdditional;
/**
 * 查询Additional 实现类
 * @author King.XU
 *
 */
public class AdditionalDaoImpl implements AdditionalDao {
	Connection con=null;
	PreparedStatement ps=null;
	StringBuffer sqlBuffer=new StringBuffer("");
	List<QueryAdditional> list=new ArrayList<QueryAdditional>();
	Logger logger = Logger.getLogger(AdditionalDaoImpl.class);
	/**
	 * 查询Additional
	 */
	public List<QueryAdditional> getQueryAdditional() {
		String sql="";
		try {
			con=DBManager.getCon();
			sql="select * from nq_additional where sfyx='Y' order by add_date desc";
			ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			logger.info("查询Additional实现类_AdditionalDaoImpl:"+sql);
			while(rs.next()){
				QueryAdditional qa=new QueryAdditional();
				qa.setStaffNo(rs.getString(1));
				qa.setName(rs.getString(2));
				qa.setNum(rs.getString(3));
				qa.setRemark(rs.getString(4));
				qa.setAdd_name(rs.getString(5));
				qa.setAdd_date(rs.getString(6));
				qa.setUpd_date(rs.getString(8));
				qa.setUpd_name(rs.getString(7));
				qa.setSfyx(rs.getString(9));
				list.add(qa);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Additional实现类异常："+e);
		} catch (ClassNotFoundException eNotFound) {
			eNotFound.printStackTrace();
			logger.error("查询Additional实现类异常："+eNotFound);
		}
		finally{
			DBManager.closeCon(con);
		}
	 return list;
	}
	/**
	 * getQueryAdditional
	 */
	public List<QueryAdditional> getQueryAdditional(String StaffNo,
			 String Valid,String start_date,String end_date,Page page) {
		try {
			con=DBManager.getCon();
				sqlBuffer=new StringBuffer("select * from nq_additional where initials like '%"+StaffNo+"%' ");		
			if(!Valid.equals("")){
				sqlBuffer.append(" and sfyx = '"+Valid+"' ");
			}
			if(!Util.objIsNULL(start_date)){
				sqlBuffer.append(" and date_format(add_date,'%Y-%m-%d') >='"+start_date+"' ");
			}
			if(!Util.objIsNULL(end_date)){
				sqlBuffer.append(" and date_format(add_date,'%Y-%m-%d')  <='"+end_date+"' ");
			}
			 sqlBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info(sqlBuffer.toString());
			ps=con.prepareStatement(sqlBuffer.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				QueryAdditional qa=new QueryAdditional();
				qa.setStaffNo(rs.getString("initials"));
				qa.setName(rs.getString("name"));
				qa.setNum(rs.getString("additional"));
				qa.setRemark(rs.getString("remarks"));
				qa.setAdd_name(rs.getString("add_user"));
				qa.setAdd_date(rs.getString("add_date"));
				qa.setUpd_date(rs.getString("upd_date"));
				qa.setUpd_name(rs.getString("upd_user"));
				qa.setSfyx(rs.getString("sfyx"));
				list.add(qa);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Additional实现类异常："+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询Additional实现类异常："+e);
		}
		finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 * add方法
	 */
	public void add(QueryAdditional qa) {
		
		String	sql="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) values('"+qa.getStaffNo()+"','"+qa.getName()+"','"+qa.getNum()+"','"+qa.getRemark()+"','"+qa.getAdd_name()+"',now(),'',NULL,'"+qa.getSfyx()+"')";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			 int r=-1;
			 r=ps.executeUpdate();
		 
			 if(r<0){
				 logger.info("插入Additional失敗！");
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("保存Additional实现类异常："+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("保存Additional实现类异常："+e);
		}finally{
			DBManager.closeCon(con);
		}
	}

	public int getRows(String StaffNo, String Valid, String startDate,
			String endDate) {
		int num=-1;
		try {
			con=DBManager.getCon();
				sqlBuffer=new StringBuffer("select count(*) as Num from nq_additional where initials like '%"+StaffNo+"%' ");		
			if(!Valid.equals("")){
				sqlBuffer.append(" and sfyx = '"+Valid+"' ");
			}
			if(!Util.objIsNULL(startDate)){
				sqlBuffer.append(" and date_format(add_date,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sqlBuffer.append(" and date_format(add_date,'%Y-%m-%d') <='"+endDate+"' ");
			}
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}
			ps.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

}
