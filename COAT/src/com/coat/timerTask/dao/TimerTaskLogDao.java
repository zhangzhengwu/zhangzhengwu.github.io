package com.coat.timerTask.dao;

import java.util.List;
import java.util.Map;

public interface TimerTaskLogDao {
	/**
	 * 根据任务名称和执行时间获取已执行次数
	 * @author kingxu
	 * @date 2016-5-20
	 * @param taskName
	 * @param executeTime
	 * @param result
	 * @return
	 * @return int
	 * @throws Exception 
	 */
	int findTaskLogNum(String taskName,String executeTime,String result) throws Exception;
	/**
	 * 保存定时任务日志
	 * @author kingxu
	 * @date 2016-5-20
	 * @param taskName
	 * @param executeTime
	 * @param result
	 * @return
	 * @throws Exception
	 * @return int
	 */
	int saveTaskLog(String taskName,String executeTime,String result) throws Exception;
	
	
	List<Map<String,Object>> find_task_schedule(String nowHHmmTime) throws Exception;
}
