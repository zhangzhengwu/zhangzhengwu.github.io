package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import dao.LocationDao;
import util.DBManager;
import util.Page;
import entity.Location;

public class LocationDaoImpl implements LocationDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(LocationDaoImpl.class);
	/**
	 * 查询所有的Location
	 */
	public List<Location> queryMacauLocation() {
		List<Location> locationList=new ArrayList<Location>();
		try{
			con=DBManager.getCon();
			sql="select * from locationmacau";
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=ps.executeQuery();
			while(rs.next()){
				Location location=new Location();
				location.setLocationId(Integer.parseInt(rs.getString(1)));
				location.setName(rs.getString("name"));
				location.setRealName(rs.getString("realName"));
				location.setLocationInfo(rs.getString("locationInfo"));
				location.setRemark(rs.getString("remark"));
				locationList.add(location);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在LocationDaoImpl.queryMacauLocation()中出现:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return locationList;
	}
	
	/**
	 * 查询所有的Location
	 */
	public List<Location> queryLocation() {
		List<Location> locationList=new ArrayList<Location>();
		try{
			con=DBManager.getCon();
			sql="select * from Location";
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=ps.executeQuery();
			while(rs.next()){
				Location location=new Location();
				location.setLocationId(Integer.parseInt(rs.getString("locationId")));
				location.setName(rs.getString("name"));
				location.setRealName(rs.getString("realName"));
				location.setLocationInfo(rs.getString("locationInfo"));
				location.setRemark(rs.getString("remark"));
				locationList.add(location);
			}
			ps.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在LocationDaoImpl.queryLocation()中出现:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return locationList;
	}
	
	/**
	 * 查询所有的LocationName
	 */
	public List<String> queryLocationName() {
		List<String> locationList=new ArrayList<String>();
		try{
			con=DBManager.getCon();
			sql="select name from Location";
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=ps.executeQuery();
			while(rs.next()){
				locationList.add(rs.getString("name"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在LocationDaoImpl.queryLocationName()中出现:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return locationList;
	}
	
	//添加功能
	public boolean AddLocation(Location location) {
		String sql = "insert into location (name,locationInfo,remark) values (?,?,?)";
		int effectCount = 0;
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, location.getName());
			ps.setString(2, location.getLocationInfo());
			ps.setString(3, location.getRemark());
			effectCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return effectCount>0?true:false;
	}
	
	//根据LocationId删除
	public int DelLocationInfo(String locationId) {
		int result=0;
		String sql = "delete from location where locationId='"+locationId+"'";
		
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			result = ps.executeUpdate();
		 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("DelLocationInfo---->>"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return result;
	}
	
	//获取行数
	public int getRows(String name) {
		int num=-1;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select count(*) as count from location");
			stringBuffer.append(" where name like '%"+name+"%' ");
			stringBuffer.append(" group by locationId");
			//String sql="select count(*) as count from location";
			ps=con.prepareStatement(stringBuffer.toString());
			logger.info("QueryCardQuota Exp SQL:"+ps.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();
			}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("QuerDao："+e.toString());
				num=0;
			}finally{
				DBManager.closeCon(con);
			}
			return num;
	}
	
	//根据名字查询
	public List<Location> queryRequestList(String name, Page page) {
		List<Location> list = new ArrayList<Location>();
		try {
			con = DBManager.getCon();	
			
			StringBuffer stringBuffer=new StringBuffer("select *from location");
			stringBuffer.append(" where name like '%"+name+"%' ");
			stringBuffer.append(" group by locationId");
			stringBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
		
			//String sql="select *from location where name like ? limit ?,?";        
	        //ps.setString(1, "%"+name.trim()+"%");
	        /* ps.setLong(2, (page.getCurPage()-1)*page.getPageSize());
	        ps.setLong(3, page.getPageSize());*/
			ps=con.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			 while(rs.next()){  
				  	Location rsBean = new Location();
				  	rsBean.setLocationId(Integer.parseInt(rs.getString(1)));
					rsBean.setName(rs.getString(2));
					rsBean.setRealName(rs.getString(3));
					rsBean.setLocationInfo(rs.getString(4));
					rsBean.setRemark(rs.getString(5));
					list.add(rsBean);
			  	}	
			  rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}finally{ 
			DBManager.closeCon(con);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return list;
	}
	
	//判断名字是否相同
	public boolean selectName(String name) {
		String sql="select*from location where name= ?";
		
		boolean a=false;
		
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			
			if(rs.next()){
				a=false;
			}else{
				a=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
			return a;
	}
}
