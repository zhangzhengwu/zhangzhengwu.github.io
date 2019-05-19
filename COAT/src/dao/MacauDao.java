package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import util.DBManager;
import util.Page;
import entity.Consultant_Master;
import entity.NewMacau;

public interface MacauDao {
	/**
	 * 根据StaffNo查询NewMacau
	 */
	public List<Consultant_Master> getConsList(String StaffNo);
	/**
	 * 保存newMacau
	 * @param rnb
	 * @return
	 */
	public int  saveNewRequest(NewMacau rnb);
/**
 * 插入历史数据
 * @param rnb
 * @return
 */
	public int saveMasterHistory(NewMacau rnb);
	/**
	 * 查询MacauRequest
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public List<NewMacau> queryRequstList(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,Page page);
	/**
	 * 查询MacauRequest Size
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public int getRows(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase);
	/**
	 * 導出Macau數據
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public ResultSet queryRequst(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase);
	
	public List<String[]> queryRequst2(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase);
	
	
	/**
 * 插入NewMacau
 * @param rnb
 * @param reStaffNo
 * @return
 */
	public int  saveNewMacau(NewMacau rnb,String reStaffNo);
	
	
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateAdditionals(NewMacau rnb);
/***
 * 更新requestNew 并更新Quantity 管理员权限
 * @param rnb
 * @return
 */
	public int updateNumber(NewMacau rnb,String reStaffNo);
/**
 * 插入NewMacau对象
 * @param rnb
 * @param reStaffNo
 * @return
 */
	public int  saveMacauNew(NewMacau rnb,String reStaffNo);
	/**
	 * 更新req_record
	 * @param rnb
	 * @param Payer
	 * @param rePayer
	 * @return
	 */
	public int updateRequestRecord(NewMacau rnb,String Payer, String rePayer);
	/**
	 * 更新历史表
	 * 
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int updateMasterNumber(NewMacau rnb, String reStaffNo ); 
	/**
	 * 删除财务表数据
	 * @param name
	 * @param StaffNo
	 * @param AddDate
	 * @return
	 */
	public int deleteChargeRecord(String name,String StaffNo, String AddDate);
	
	
	/**
	 * 保存 consultant list excel表
	 * @param filename
	 * @param os
	 * @return
	 */
 	public int saveCIB(String filename, InputStream os,String username);
 	
 	/**
 	 * 名片使用情况
 	 * @param code
 	 * @return
 	 */
 	public String queryConsNum(String code);
 	

	/**
	 * 是否是DD、DDTree
	 * @param staffcode
	 * @return
	 */
	public boolean findDDorTreeHead(String staffcode);
 	
}
