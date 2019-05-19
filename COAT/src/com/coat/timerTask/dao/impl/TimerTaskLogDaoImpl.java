package com.coat.timerTask.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Constant;
import util.FileUtil;
import util.Util;

import com.coat.timerTask.dao.TimerTaskLogDao;
import com.coat.timerTask.entity.STaskLog;

import dao.common.BaseDao;

public class TimerTaskLogDaoImpl extends BaseDao implements TimerTaskLogDao {

	Logger logger = Logger.getLogger(TimerTaskLogDaoImpl.class);
 

	public TimerTaskLogDaoImpl() {
	}


	/**
	 * 系统定时备份并清理temp文件--22点
	 * @return
	 */
	public String timeTaskCleanBackUp(){
		String result="";
		
		
		try{
			Util.printLogger(logger,"开始执行指定任务-转移备份临时文件");    
			FileUtil.copyFolderContext(Constant.applicationPath+"\\upload\\temp\\", Util.getProValue("upload.temp.backup.filepath"));
			Util.printLogger(logger,"开始执行指定任务-转移备份临时文件成功!");    
			Util.printLogger(logger, "-------------------------");    
			Util.printLogger(logger, "开始执行指定任务-删除临时文件");    
			FileUtil.deleteAll(Constant.applicationPath+"\\upload\\temp\\");
			Util.printLogger(logger, "指定任务-->删除临时文件成功!");
			result="success";
		}catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger, "指定任务-->删除临时文件失败："+e.getMessage());
		}
		return result;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, STaskLog.class);
	}

	public int findTaskLogNum(String taskName, String executeTime,String result) throws Exception {
		try{
			return super.findCount("select count(*) from s_task_log where taskName=? and result=? and status=1 and DATE_FORMAT(executeTime,'%Y-%m-%d')=DATE_FORMAT(?,'%Y-%m-%d')", taskName,result,executeTime);
		}finally{
			super.closeConnection();
		}
	}

	public int saveTaskLog(String taskName, String executeTime,String result) throws Exception {
		try{
			return super.saveEntity("insert into s_task_log(taskName,executeTime,result)values(?,?,?);", taskName,executeTime,result);
		}finally{
			super.closeConnection();
		}
	}

	public List<Map<String,Object>> find_task_schedule(String nowHHmmTime) throws Exception {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>(); 
		try {
			String sql="select * from s_task_schedule where status=1 and  executeTime like ?";
			list=super.listMap(sql, new Object[]{"%"+nowHHmmTime+"%"});
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return list;
	}
	
	
	public String test(){
		String result="success";
		
		
		return result;
	}
	





}
