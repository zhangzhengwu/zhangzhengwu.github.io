package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Page;
import entity.Consultant_Master;
import entity.RequestConvoyBean;
/**
 * 查询request convoy cons 所需的数据
 * @author Wilson
 * 2012年9月27日10:11:38
 */
public interface QueryCardConvoyDao {
	/**
	 * 根据StaffNo查询New Card Convoy
	 */
	public List<Consultant_Master> getConsList(String StaffNo);
	/**
	 * 保存newMacau
	 * @param rnb
	 * @return
	 */
	public int  saveNewRequest(RequestConvoyBean rnb);
	/**
	 * 插入历史数据
	 * @param rnb
	 * @return
	 */
	public int saveMasterHistory(RequestConvoyBean rnb);
	/**
	 * 查询RequestConvoyBean
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public List<RequestConvoyBean> queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase,String shzt,Page page);
	/**
	 * 導出RequestConvoyBean數據
	 */
	public List<Map<String,Object>> queryRequst(String name,String code,String startDate,String endDate,String location,String urgentCase,String shzt)throws SQLException;
	
	/**
	 * 插入RequestConvoyBean
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int  saveNewConvoy(RequestConvoyBean rnb,String reStaffNo);
	
	
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateAdditionals(RequestConvoyBean rnb);
	/***
	 * 更新requestNew 并更新Quantity 管理员权限
	 * @param rnb
	 * @return
	 */
		public int updateNumber(RequestConvoyBean rnb,String reStaffNo);
	/**
	 * 插入NewMacau对象
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int  saveConvoyNew(RequestConvoyBean rnb,String reStaffNo);
	/**
	 * 更新req_record
	 * @param rnb
	 * @param Payer
	 * @param rePayer
	 * @return
	 */
	public int updateRequestRecord(RequestConvoyBean rnb,String Payer, String rePayer);
	/**
	 * 更新历史表
	 * 
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int updateMasterNumber(RequestConvoyBean rnb, String reStaffNo ); 
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
 	 * 获取行数
 	 * @param name
 	 * @param code
 	 * @param startDate
 	 * @param endDate
 	 * @param location
 	 * @param urgentCase
 	 * @param shzt
 	 * @return
 	 * 调用处QueryConvoyCardServlet line in 52
 	 */
	public int  getRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String shzt);

}
