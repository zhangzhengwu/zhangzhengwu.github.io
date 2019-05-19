package com.coat.chickenbox.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.coat.pickuprecord.entity.PRecordList;
import com.coat.pickuprecord.entity.PRecordOrder;
import util.Pager;
import javax.servlet.jsp.jstl.sql.Result;

public interface ChickenBoxDao {

	/**
	 * 查询状态信息
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager queryChicken_BoxList(String[] fields, Pager page, Object... objects) throws Exception;
	
	/**
	 * 导出查询的数据
	 * @return
	 */
	public List<Map<String,Object>> downChickenBox(String staffcode,String fullname,String othername,String d_Recruite)throws SQLException;
	
	
	public int saveQueryRecord(String data,String staffcode,String fullname,String othersname,String d_Recruite,String username)throws SQLException ;
}
