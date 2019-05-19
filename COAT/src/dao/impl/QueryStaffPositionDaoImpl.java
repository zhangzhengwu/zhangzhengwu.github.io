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
import dao.QueryStaffPositionDao;
import entity.Position_Staff_list;
/**
 * QueryStaffPositionDao 实现类
 * @author Wilson.SHEN
 *
 */
public class QueryStaffPositionDaoImpl implements QueryStaffPositionDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryStaffPositionDaoImpl.class);
	//queryPosition
	public List<Position_Staff_list> queryPosition() {

		List<Position_Staff_list> list = new ArrayList<Position_Staff_list>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE SFYX='Y' order by position_ename ";
			logger.info("queryPosition SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Position_Staff_list peoBean = new Position_Staff_list();
				peoBean.setId(rs.getString(1));
				peoBean.setPosition_ename(rs.getString(2));
				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
				list.add(peoBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPosition异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPosition异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	
	public boolean findPositionName(String positionEname){
		boolean flag=false;
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT position_ename FROM position_list_staff WHERE SFYX='Y' and position_ename=?";
			logger.info("queryPosition by " +positionEname+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ps.setString(1, positionEname);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				flag=true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("findPositionName " +positionEname+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("findPositionName " +positionEname+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return flag;
	}
	
	
	public Position_Staff_list queryStaffPosition(String position_ename) {
		Position_Staff_list peoBean = new Position_Staff_list();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE SFYX='Y' and id="+position_ename;
			logger.info("queryPosition by " +position_ename+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setPosition_ename(rs.getString(2));
				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}
	public Position_Staff_list queryStaffPositionbyName(String position_ename) {
		Position_Staff_list peoBean = new Position_Staff_list();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE SFYX='Y' and position_ename='"+position_ename+"'";
			logger.info("queryPosition by " +position_ename+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setPosition_ename(rs.getString(2));
				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}

	public List<Position_Staff_list> findPosition(String startDate,
			String endDate, String positionEname, String sfyx, Page page) {
		List<Position_Staff_list> list=new ArrayList<Position_Staff_list>();
		
		try{
		connection=DBManager.getCon();
		String sql="select id,position_ename,position_cname,add_date,add_name,upd_name,upd_date,sfyx from position_list_staff WHERE sfyx like ? and position_ename like ? ";
		if(!Util.objIsNULL(startDate)){
			sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
		}
		if(!Util.objIsNULL(endDate)){
			sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
		}
			sql+=" order by add_date desc limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+"";
			ps=connection.prepareStatement(sql);
			ps.setString(1, "%"+sfyx+"%");
			ps.setString(2, "%"+positionEname+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate))
					ps.setString(4, endDate);
			}else{
				if(!Util.objIsNULL(endDate))
					ps.setString(3, endDate);
			}
			ResultSet rs=ps.executeQuery();
				while (rs.next()) {
					list.add(new Position_Staff_list(
							rs.getString("id"),
							rs.getString("position_Ename"),
							rs.getString("position_Cname"),
							rs.getString("add_Date"),
							rs.getString("add_Name"),
							rs.getString("upd_Name"),
							rs.getString("upd_date"),
							rs.getString("sfyx")));
				}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询 Postion 异常！=="+e);
		}finally{
			DBManager.closeCon(connection);
		}
			
			
		return list;
	}

	public int getRow(String startDate, String endDate, String positionEname,
			String sfyx) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			String sql="select count(*) from position_list_staff WHERE sfyx like ? and position_ename like ? ";
			if(!Util.objIsNULL(startDate)){
				sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
			}
			if(!Util.objIsNULL(endDate)){
				sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
			}
				sql+=" order by add_date desc";
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+positionEname+"%");
				if(!Util.objIsNULL(startDate)){
					ps.setString(3, startDate);
					if(!Util.objIsNULL(endDate))
						ps.setString(4, endDate);
				}else{
					if(!Util.objIsNULL(endDate))
						ps.setString(3, endDate);
				}
				ResultSet rs=ps.executeQuery();
				if(rs.next()){
					num=Integer.parseInt(rs.getString(1));
				}
				rs.close();
		}catch(Exception e){
			logger.error("查询 Postion总条数 异常！=="+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}

	public Position_Staff_list queryStaffPositionId(String positionId) {
		Position_Staff_list peoBean = new Position_Staff_list();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE id='"+positionId+"'";
			logger.info("queryPosition by positionId=" +positionId+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setPosition_ename(rs.getString(2));
				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPositionby positionId=" +positionId+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPositionby positionId=" +positionId+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}

	public int savePosition(Position_Staff_list pss) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="insert into position_list_staff(position_ename,position_cname,add_date,add_name,upd_name,upd_date,sfyx)values(?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setString(1, pss.getPosition_ename());
			ps.setString(2, pss.getPosition_cname());
			ps.setString(3, pss.getAdd_date());
			ps.setString(4, pss.getAdd_name());
			ps.setString(5, pss.getUpd_name());
			ps.setString(6, pss.getUpd_date());
			ps.setString(7, pss.getSfyx());
			num=ps.executeUpdate();
			logger.info("保存Staff Position  by positionId=" +pss.getId()+" Data:"+Util.reflectTest(pss));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("保存Staff Position 异常!==="+e);
		}
		return num;
	}

	public int ModifyPosition(Position_Staff_list pss) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="update position_list_staff set position_ename=?,position_cname=?,upd_name=?,upd_date=?,sfyx=? where id=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, pss.getPosition_ename());
			ps.setString(2, pss.getPosition_cname());
			ps.setString(3, pss.getUpd_name());
			ps.setString(4, pss.getUpd_date());
			ps.setString(5, pss.getSfyx());
			ps.setInt(6, Integer.parseInt(pss.getId()));
			num=ps.executeUpdate();
			logger.info("修改Staff Postion by positionId=" +pss.getId()+" SQL:"+Util.reflectTest(pss));
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("修改Staff Position 异常!==="+e);
		}
		return num;
	}

	public ResultSet DownPosition(String startDate, String endDate,
			String positionEname, String sfyx) {
		ResultSet rs=null;
		try{
			connection=DBManager.getCon();
			String sql="select id,position_ename,position_cname,add_name,add_date,upd_name,upd_date,sfyx from position_list_staff WHERE sfyx like ? and position_ename like ? ";
			if(!Util.objIsNULL(startDate)){
				sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
			}
			if(!Util.objIsNULL(endDate)){
				sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
			}
				sql+=" order by add_date desc";
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+positionEname+"%");
				if(!Util.objIsNULL(startDate)){
					ps.setString(3, startDate);
					if(!Util.objIsNULL(endDate))
						ps.setString(4, endDate);
				}else{
					if(!Util.objIsNULL(endDate))
						ps.setString(3, endDate);
				}
				rs=ps.executeQuery();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("导出Postion 异常！=="+e);
			}
		return rs;
				
	}
}
