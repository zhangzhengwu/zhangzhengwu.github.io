package com.coat.timerTask.dao.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.ReadExcel;
import util.Util;

import com.coat.timerTask.dao.TimerTaskExcelDao;

import dao.common.BaseDao;


public class TimerTaskExcelDaoImpl extends BaseDao implements TimerTaskExcelDao {

	
	Logger logger=Logger.getLogger(TimerTaskExcelDaoImpl.class);
	
	public TimerTaskExcelDaoImpl() {
	}

	/**
	 * 本地Execl表数据存入数据库
	 */
	public String saveExcelIntoDataBase(){
			List<List<Object>> list = null;
			Util.printLogger(logger, "开始执行指定任务-saveExcelIntoDataBase");
			String  uploadfile = Util.getProValue("TimerTask.pickup.hkcodemappingstaffcode.upload.file");
			File file = new File(uploadfile);
			String result="";
			try {
				list = ReadExcel.readExcel(file, 0, 20, 1);
				saveExcelList(list);
				result="success";
				Util.printLogger(logger, "指定任务-saveExcelIntoDataBase-->成功!");
			} catch (Exception e) {
				result=e.getMessage();
				e.printStackTrace();
				Util.printLogger(logger, "指定任务-saveExcelIntoDataBase-->失败!"+e.getMessage());
			}
			return result;
	}
	
	/**
	 * 
	 */
	public int saveExcelList(List<List<Object>> list) throws SQLException {
		int num = -1;
		try {
			super.openTransaction();
			//将所有旧数据置为N
			super.update2("update p_record_hkcode set sfyx='N' where HK_staffcode != substring(staffcode,LENGTH(staffcode)-5,LENGTH(staffcode))");
			String sql = "insert into p_record_hkcode(staffcode,HK_staffcode,staffname,creator,createdate,sfyx) values (?,?,?,?,?,?)";
			String nowTime=DateUtils.getNowDateTime();
			List<String> staffList=new ArrayList<String>();
			List<Object[]> paramlist=new ArrayList<Object[]>(); 
			for(int i=0;i<list.size();i++){
				List<Object> list2=list.get(i);
				staffList.add(list2.get(0)+"");
				paramlist.add(new String[]{""+list2.get(0),""+list2.get(3),""+list2.get(2),"SYSTEM",nowTime,"Y"});
			}
			//将数据库中staffcode和Excel表相同的置为N
			super.update2("update p_record_hkcode set sfyx='N' where staffcode in("+ListConvertToSQLParam(staffList)+")");//'xx','xx','xxx'
			//执行表数据批量插入
			num=calculateNum(batchInsert(sql,paramlist));
			super.sumbitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			super.rollbackTransaction();
		} finally{
			super.closeConnection();
		}
		return num;
	}


	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}

}
