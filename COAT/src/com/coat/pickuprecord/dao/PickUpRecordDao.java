package com.coat.pickuprecord.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.coat.pickuprecord.entity.PRecordList;
import com.coat.pickuprecord.entity.PRecordOrder;
import util.Pager;
import javax.servlet.jsp.jstl.sql.Result;

public interface PickUpRecordDao {

	/**
	 * 查询状态信息
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager queryPickUpList(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * 查询--导出
	 * @param staffcode
	 * @param clientName
	 * @param scandateS
	 * @param scandateE
	 * @return
	 */
	//public Result  queryPickUpListSet(String staffcode ,String clientName,String scandateS,String scandateE,String status)throws SQLException;
	
	public List<Map<String,Object>> queryPickUpListSet(String staffcode ,String clientName,String scandateS,String scandateE,String status)throws SQLException;
	
	/**
 	 * 批量上传PickUpList
 	 * @param list
 	 * @return
 	 */
 	public String uploadPickUpList(List<List<Object>> list,String user)throws Exception;
 	
 	public String uploadPickUp_HKCode(List<List<Object>> list,String user)throws Exception;
 	
 	/**
 	 * 根据code查询PickUpList信息
 	 * @param staffcard
 	 * @return
 	 */
 	public List<PRecordList> findList(String staffcard) throws Exception;
 	
 	/**
 	 * 根据code查询Order表信息
 	 * @param staffcard
 	 * @return
 	 */
 	public List<PRecordOrder> findByOrderCode(String staffcard)throws Exception;
 	
 	/**
 	 * 保存文件信息
 	 * @param refnos
 	 * @param staffcard
 	 * @param otherCode
 	 * @param exrension
 	 * @param password
 	 * @return
 	 */
 	public int saveOrder(String listId,String staffcard,String otherCode,String exrension,String password,
 			String adminUsername)throws SQLException;
 	
 	/**
 	 * 准备文件Ready
 	 * @param rnb
 	 * @param username
 	 * @param refno
 	 * @return
 	 * @throws SQLException
 	 */
 	String HKOAdm_Ready(PRecordList rnb,String username,String refno) throws SQLException;
 	/**
 	 * 批量Ready
 	 * @param username
 	 * @param refnos
 	 * @return
 	 * @throws SQLException
 	 */
 	String batchReady(String refnos,String staffCodes,String clientNames,String user) throws SQLException;
 	
 	/**
 	 * 接收文件Receive
 	 * @param listId
 	 * @return
 	 * @throws SQLException
 	 */
 	public int saveReceive(String listId,String adminUsername)throws SQLException;
 	/**
 	 * 删除p_Record_List
 	 * @param refno
 	 * @return
 	 * @throws SQLException
 	 */
 	public String delList(String user,String refno)throws SQLException;
 
 	public String rejectList(String user,String refno)throws SQLException;
 	
 	/**
 	 * 删除已有记录
 	 * @param staffcode
 	 * @return
 	 * @throws SQLException
 	 */
 	public String del_Order(String staffcode)throws SQLException;
 	/**
 	 * 新增单条List
 	 */
 	public int addList(String staffcode,String clientname,String location,String documentType,
 			String scandate,String sender,String remark,String adminUsername,String documentId)throws SQLException;
 	/**
 	 * 邮件定时提醒(逢周一、周四发送邮件提醒)
 	 * @return
 	 */
 	public int p_sendEmail();
 	
 	
 	/**
 	 * 保存文件信息
 	 */
 	public String saveListInterface(String documentId,String staffcode,String clientname,String location,String sender,
 			String documentType,String scanDate,String adminUsername)throws SQLException;
 	
}
