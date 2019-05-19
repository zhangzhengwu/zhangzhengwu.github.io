package dao;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;
import entity.RequestStaffBean;
import entity.RequestStaffConvoyBean;
/**
 * 查询RequstStaff列表 DAO类
 * @author wilson
 *
 */
public interface QueryStaffRequstDao {
	/**
	 *  
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<RequestStaffBean> queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page);
	/**
	 *  
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<RequestStaffConvoyBean> queryRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page);
	/**
	 * HR HR查询为其他Staff办理记录
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @param ET
	 * @param layout_select
	 * @param payer
	 * @param page
	 * @return
	 */
	public List<RequestStaffConvoyBean> queryHRRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page);
	
	/**
	 * 为Dept 查询为其他Staff办理记录
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @param ET
	 * @param layout_select
	 * @param payer
	 * @param page
	 * @return
	 */
	public List<RequestStaffConvoyBean> queryDeptRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page);
	/**
	 *  获取总行数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer);
	/**
	 *  获取总行数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer);
	/**
	 * 为Dept Head 查询staff条数
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @param ET
	 * @param layout_select
	 * @param payer
	 * @return
	 */
	public int getDeptConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer);
	
	/**
	 * HR查询为其他Staff办理总条数
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @param ET
	 * @param layout_select
	 * @param payer
	 * @return
	 */
	public int getHRConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer);
/**
 * 导出RequstStaff列表DAO类
 * @param startDate
 * @param endDate
 * @return
 */
	public ResultSet queryRequstListSet(String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String ET,String layout_select);
	/**
	 * 导出Request Staff 列表
	 * @author kingxu
	 * @date 2016-4-20
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @param payer
	 * @param ET
	 * @param layout_select
	 * @return
	 * @return Result
	 */
	public Result queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String ET,String layout_select);
 
	/**
	 * 根据staffcode，urgentDate，upd_date 置为N
	 * @return
	 */
	public int updateRequestStaff(String staffcode,String urgentDate,String username);
	 
	/**
	 * Approve Request Staff Convoy 数据
	 * @return
	 */
	public int approveRequestStaffConvoy(String staffcode,String urgentDate,String username);
	/**
	 * HR 审核
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @return
	 */
	public int approveRequestHRConvoy(String staffcode,String urgentDate,String username);
	/**
	 * HR 审核  加 Remark
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @param remarkCons
	 * @return
	 */
	public int approveRequestHRConvoy(String staffcode,String urgentDate,String username,String remarkCons);
	
	
	/**
	 * Depart 审核
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @return
	 */
	public int approveRequestDepartConvoy(String staffcode,String urgentDate,String username);
	
	
	/**
	 * 根据staffcode以及userType获取对应的Email
	 * @param StaffCode
	 * @param userType
	 * @return
	 */
	public String findEmailByStaff(String StaffCode,String userType);
	/**
	 *根据Staffcode获取对应Recruiter 的Email Address
	 * @param StaffCode
	 * @return
	 */
	public String findRecruiterEmailByStaff(String StaffCode); 
	
	
	
	
	
	/**
	 * 获取departhead Email
	 * @param StaffCode
	 * @return
	 */
	public String findDeptByStaff(String StaffCode); 
	/**
	 * 获取上级邮箱
	 * @param StaffCode
	 * @return
	 */
	public String[] findSupervisorEmail(String StaffCode); 
	
	/**
	 * Approve DepartmentHead   
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @param remarkcons
	 * @return
	 */
	public int approveRequestDepartConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons); 
	/**
	 * Approve Department Head 
	 * @author kingxu
	 * @date 2015-8-7
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @param remarkcons
	 * @param englisname 
	 * @return
	 * @return int
	 */
	public String approveRequestDepartConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons,String englisname); 
	
 
	
	
	
	
}
