package com.coat.timerTask.dao.impl;

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

import com.coat.timerTask.dao.TimeTaskDao;
import com.coat.timerTask.entity.STaskLog;
import com.coat.timerTask.entity.STaskSchedule;

import dao.common.BaseDao;

public class TimeTaskDaoImpl extends BaseDao implements TimeTaskDao {

	Connection con=null;
	PreparedStatement ps=null;
	StringBuffer sqlBuffer=new StringBuffer("");
	Logger logger = Logger.getLogger(TimeTaskDaoImpl.class);
	
	public int getRows(String taskName, String status, String executeTime){
		int num = -1;
		try {
			con = DBManager.getCon();
			sqlBuffer = new StringBuffer("select count(*) as Num from s_task_schedule where 1=1 ");		
			if(!Util.objIsNULL(status)){
				sqlBuffer.append(" and status like '%"+status+"%'  ");
			}
			if(!Util.objIsNULL(taskName)){
				sqlBuffer.append(" and taskName like '%"+taskName+"%' ");
			}
			if(!Util.objIsNULL(executeTime)){
				sqlBuffer.append(" and POSITION('"+executeTime+"' IN executeTime)>0 ");//POSITION('08:00' IN executeTime)>0
			}
			sqlBuffer.append(" order by executeTime");
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				num = Integer.parseInt(rs.getString("Num"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			num = 0;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			num = 0;
			e.printStackTrace();
		} finally {
		}
		
		return num;
	}

	public List<STaskSchedule> queryTimeTaskSchedule(String taskName,String status, String executeTime, Page page) {
		List<STaskSchedule> list= new ArrayList<STaskSchedule>();
		try {
			con = DBManager.getCon();
			sqlBuffer = new StringBuffer("select * from s_task_schedule where 1=1 ");
			if(!Util.objIsNULL(status)){
				sqlBuffer.append(" and status like '%"+status+"%'  ");
			}
			if(!Util.objIsNULL(taskName)){
				sqlBuffer.append(" and taskName like '%"+taskName+"%' ");
			}
			if(!Util.objIsNULL(executeTime)){
				sqlBuffer.append(" and POSITION('"+executeTime+"' IN executeTime)>0 ");//POSITION('08:00' IN executeTime)>0
			}
			sqlBuffer.append(" order by executeTime");
			sqlBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				STaskSchedule sts = new STaskSchedule();
				sts.setId(Integer.parseInt(rs.getString("id")));
				sts.setTaskName(rs.getString("taskName"));
				sts.setExecuteTime(rs.getString("executeTime"));
				sts.setExecuteScript(rs.getString("executeScript"));
				sts.setExplain(rs.getString("explain"));
				sts.setStatus(Integer.parseInt(rs.getString("status")));
				sts.setCreator(rs.getString("creator"));
				sts.setCreateDate(rs.getString("createDate"));
				sts.setRemark(rs.getString("remark"));
				list.add(sts);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询STaskSchedule实现类异常："+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询STaskSchedule实现类异常："+e);
		}
		return list;
	}

	public int save(STaskSchedule s) {
		int num = -1;
		String sql = "insert into s_task_schedule(taskName,executeTime,executeScript,`explain`,creator,createDate,remark) values (?,?,?,?,?,?,?)";
		try {
			num = saveEntity(sql,s.getTaskName(),s.getExecuteTime(),s.getExecuteScript(),s.getExplain(),s.getCreator(),s.getCreateDate(),s.getRemark());
			if(num < 0){
				 logger.info("save s_task_schedule error==["+sql+"]");
			}else{
				 logger.info("save s_task_schedule success==["+sql+"]");
			}
		} catch (Exception e) {
			num = -1;
			logger.info("save s_task_schedule exception==["+sql+"]["+e.getMessage()+"]");
			e.printStackTrace();
		}
		return num;
	}
	
	public int update(STaskSchedule s) {
		int num = -1;
		String sql = "update s_task_schedule set taskName=?,executeTime=?,executeScript=?,`explain`=?,status=?,remark=? where id=?";
		try {
			num = update2(sql,s.getTaskName(),s.getExecuteTime(),s.getExecuteScript(),s.getExplain(),s.getStatus(),s.getRemark(),s.getId());
			if(num < 0){
				logger.info("update s_task_schedule error==["+sql+"]");
			}else{
				logger.info("update s_task_schedule success==["+sql+"]");
			}
		} catch (Exception e) {
			num = -1;
			logger.info("update s_task_schedule exception==["+sql+"]["+e.getMessage()+"]");
			e.printStackTrace();
		}
		return num;
	}

	
	public int getRows(String taskName){
		int num = -1;
		try {
			con = DBManager.getCon();
			sqlBuffer = new StringBuffer("select count(*) as Num from s_task_log where 1=1 ");		

			if(!Util.objIsNULL(taskName)){
				sqlBuffer.append(" and taskName like '%"+taskName+"%' ");
			}

//			sqlBuffer.append(" order by id");
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				num = Integer.parseInt(rs.getString("Num"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			num = 0;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			num = 0;
			e.printStackTrace();
		} finally {
		}
		
		return num;
	}
	
	public int getSameTaskName(String taskName){
		int num = -1;
		try {
			con = DBManager.getCon();
			sqlBuffer = new StringBuffer("select * from s_task_schedule where status=1 ");		
			
			if(!Util.objIsNULL(taskName)){
				sqlBuffer.append(" and taskName like '%"+taskName+"%' ");
			}
			
//			sqlBuffer.append(" order by id");
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				num = 1;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			num = 0;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			num = 0;
			e.printStackTrace();
		} finally {
		}
		
		return num;
	}

	
	
	public List<STaskLog> queryTimeTaskLog(String taskName, Page page) {
		List<STaskLog> list= new ArrayList<STaskLog>();
		try {
			con = DBManager.getCon();
			sqlBuffer = new StringBuffer("select * from s_task_log where 1=1 ");

			if(!Util.objIsNULL(taskName)){
				sqlBuffer.append(" and taskName like '%"+taskName+"%' ");
			}

			sqlBuffer.append(" order by executeTime desc");
			sqlBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				STaskLog stl = new STaskLog();
				stl.setTaskName(rs.getString("taskName"));
				stl.setExecuteTime(rs.getString("executeTime"));
				stl.setResult(rs.getString("result"));
				stl.setStatus(Integer.parseInt(rs.getString("status")));
				list.add(stl);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询STaskLog实现类异常："+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询STaskLog实现类异常："+e);
		}
		return list;
	}
	
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}
	
	
	
	
}
