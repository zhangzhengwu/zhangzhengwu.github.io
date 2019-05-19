package com.coat.attendance.dao;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceDao {
	/**
	 * 保存辞职列表
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	int saveResign(List<List<Object>> list,String username) throws Exception;
	/**
	 * 保存借卡记录
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	int saveBorrow(List<List<Object>> list, String username) throws Exception; 
	/**
	 * 保存cclub成员列表
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public int savecclub(List<List<Object>> list, String username) throws Exception ;
	/**
	 * 保存假期列表
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public int saveEexceptionDate(List<List<Object>> list, String username) throws Exception;
	/**
	 * 保存请假记录
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public int saveleaveRecord(List<List<Object>> list, String username) throws Exception;
	/**
	 * 保存Emap记录
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public int saveEmap(List<List<Object>> list, String username) throws Exception;
	
	/**
	 * 保存TrainingList
	 * @author kingxu
	 * @date 2015-10-15
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public int saveTrainingList(List<List<Object>> list, String username) throws Exception;
	
	
	/**
	 * 计算Consultant 整月数据
	 * @author kingxu
	 * @date 2015-10-16
	 * @param filename
	 * @param username
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String cacularAttendance(String filename,String username) throws Exception;
	
	
	/**
	 * 上传Attendance数据
	 * @author kingxu
	 * @date 2015-10-16
	 * @param list
	 * @param username
	 * @return
	 * @throws SQLException
	 * @return String
	 */
	public String uploadAttendance(List<List<Object>> list, String username) throws SQLException;
}
