package com.coat.timerTask.dao;

import java.util.List;

import util.Page;

import com.coat.timerTask.entity.STaskLog;
import com.coat.timerTask.entity.STaskSchedule;


public interface TimeTaskDao {
	
	//获取查询数据总行数
	public int getRows(String taskName, String status, String executeTime);
	
	//根据条件查询所有定时任务列表
	public List<STaskSchedule> queryTimeTaskSchedule(String taskName,String status,String executeTime,Page page);
	
	//保存STaskSchedule
	public int save(STaskSchedule s);
	
	//修改STaskSchedule
	public int update(STaskSchedule s);
	
	//根据条件获取定时查询记录的总行数
	public int getRows(String taskName);
	
	//根据条件查询所有定时任务历史记录列表
	public List<STaskLog> queryTimeTaskLog(String taskName,Page page);
	
	//根据taskName查询是否已有相同名称的定时任务
	public int getSameTaskName(String taskName);
}
