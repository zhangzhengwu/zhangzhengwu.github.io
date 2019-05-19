package dao.staffnamecard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Page;
import util.Pager;
import entity.C_Access;
import entity.RequestStaffBean;
import entity.RequestStaffCompanyView;
import entity.RequestStaffConvoyBean;
import entity.RequestStaffConvoyDetial;
/**
 * 最新StaffNameCard接口  
 * 2015-06-03 11:00:09
 * @author kingxu
 *
 */
public interface StaffNameCardDao {
/**
 * SZADM Approve页面 分页查询条数 
 * @param name
 * @param code
 * @param startDate
 * @param endDate
 * @param location
 * @param urgentCase
 * @param ET
 * @param layout_select
 * @param payer
 * @param isverify
 * @return
 */
	public int getConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,String isverify);
	/**
	 * 最新StaffNameCard分页查询
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
	 * @param isverify
	 * @return
	 */
	public List<RequestStaffConvoyBean> queryRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page,String isverify);
/**
 * 最新StaffNameCard分页查询
 * @param fields
 * @param page
 * @param ET
 * @param objects
 * @return
 * @throws Exception 
 */
	Pager findPager(String[] fields, Pager page,String ET, Object... objects) throws Exception;
	
	
	Pager queryStateDetail(String[] fields, Pager page, Object... objects) throws Exception;
		
	
	/**
	 * 查询hr approval 数据
	 * @author kingxu
	 * @date 2015-8-10
	 * @param fields
	 * @param page
	 * @param ET
	 * @param objects
	 * @return
	 * @return Pager
	 * @throws Exception 
	 */
	Pager findHRApprovalPager(String[] fields, Pager page,String ET, Object... objects) throws Exception;
/**
 * 查询dept head approval 数据
 * @author kingxu
 * @date 2015-8-4
 * @param fields
 * @param page
 * @param ET
 * @param objects
 * @return
 * @throws Exception
 * @return Pager
 */
	Pager findDeptApprovalPager(String[] fields, Pager page,String ET, Object... objects) throws Exception;
	
	/**
	 * Reject 在econvoy上 Staff本人提交的申请
	 * @param staffcode
	 * @param urgentDate
	 * @param adminUsername
	 * @param remark- reject reason
	 * @return
	 * @throws SQLException
	 */
	int rejectStaffRequestConvoy(String staffcode, String urgentDate,String adminUsername,String refno,String remark,String nameE,String nameC) throws SQLException;
	/**
	 * Dept Reject 在econvoy上 Staff本人提交的申请
	 * @param staffcode
	 * @param urgentDate
	 * @param adminUsername
	 * @param refno
	 * @return
	 * @throws SQLException
	 */
	int rejectStaffRequestConvoyDept(String staffcode, String urgentDate,String adminUsername,String refno,String remark,String nameE,String nameC) throws SQLException;
	/**
	 * HR Reject 在econvoy上 Staff本人提交的申请
	 * @param staffcode
	 * @param urgentDate
	 * @param adminUsername
	 * @param refno
	 * @return
	 * @throws SQLException
	 */
	int rejectStaffRequestConvoyHR(String staffcode, String urgentDate,String adminUsername,String refno,String remark,String nameE,String nameC) throws SQLException;
	/**
	 * dept Approval
	 * @author kingxu
	 * @date 2015-8-10
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @param remarkcons
	 * @param englishName
	 * @return
	 * @return String
	 * @throws SQLException 
	 */
	String approveRequestDepartConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons,String englishName,String refno,String chineseNames) throws SQLException; 
	/**
	 * HRD Approval
	 * @author kingxu
	 * @date 2015-8-10
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @param remarkcons
	 * @param englishName
	 * @return
	 * @return String
	 * @throws SQLException 
	 */
	String approveRequestHRConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons,String englishName,String refno,String chineseName) throws SQLException; 
	/**
	 * staff 删除名片申请
	 * @author kingxu
	 * @date 2015-8-11
	 * @param refno
	 * @param adminUsername
	 * @param urgentDate
	 * @return
	 * @throws SQLException
	 * @return int
	 */
	public int delstaffnamecard(String refno,String adminUsername,String urgentDate) throws SQLException;
	/**
	 * 删除dept head 名片申请
	 * @author kingxu
	 * @date 2015-8-11
	 * @param refno
	 * @param adminUsername
	 * @param urgentDate
	 * @return
	 * @throws SQLException
	 * @return int
	 */
	 int deldeptnamecard(String refno,String adminUsername,String urgentDate) throws SQLException;
	 /**
	  * 删除hr 名片申请
	  * @author kingxu
	  * @date 2015-8-11
	  * @param refno
	  * @param adminUsername
	  * @param urgentDate
	  * @return
	  * @throws SQLException
	  * @return int
	  */
	 int delhrnamecard(String refno,String adminUsername,String urgentDate) throws SQLException;
	 /**
	  * 保存staffnamecard申请(兼容staff/dept head/hr)
	  * @author kingxu
	  * @date 2015-8-12
	  * @param rnb
	  * @param roleType
	  * @return
	  * @return String
	 * @throws SQLException 
	  */
	 String saveStaffRequestConvoy(RequestStaffConvoyBean rnb,String roleType,String adminUsername) throws SQLException; 
	 
	 /**
	  * SZOADM Approval
	  * @author kingxu
	  * @date 2015-8-12
	  * @param rnb
	  * @param username
	  * @param refno
	  * @return
	  * @return String
	 * @throws SQLException 
	  */
	 String SZOADMApproval(RequestStaffConvoyBean rnb,String username,String refno) throws SQLException;
	 
	 /**
	  * 远程获取list
	  * @param staffNo
	  * @return
	 * @throws SQLException 
	  */
	 public List<Map<String,Object>> queryStaffRequestConvoy(String staffNo) throws SQLException;
	 @Deprecated
	 public String queryStaffRequestConvoy1(String staffNo) throws SQLException;
	 
	 /**
	  * 远程获取GRADE
	  * @param staffNo
	  * @return
	  * @throws SQLException
	  */
	 public String queryStaffRequestGrade(String staffNo) throws SQLException;
	 
	 /**
	  * 验证positioncode是否匹配
	  * @param positioncode
	  * @return
	 * @throws SQLException 
	  */
//     public  String  getPositionCode(String positioncode) throws SQLException;

	 public String getLocation(String staffcode) throws SQLException;
	 
     /**
      * 验证departmentcode是否匹配
      * @param departmentcode
      * @return
      * @throws SQLException 
      */
//     public  String  getDepartmentCode(String departmentcode) throws SQLException;
     
     public List<RequestStaffConvoyDetial> findNameCardDetail(String staffrefno) throws SQLException;
     
    /**
 	 * 批量保存订单申请
 	 * @param list
 	 * @return
 	 */
 	public String uploadNameCardDetial(List<List<Object>> list,String user)throws Exception;
 	/**
 	 * approve的提示邮件，在用户未执行的情况下以一个星期一次的频率给用户发送提示邮件
 	 * @return
 	 * @throws Exception 
 	 */
 	public int approvePrompt() throws Exception;
 	/**
 	 * 新增单个状态Delivery/Receive
 	 * @param refno
 	 * @param state
 	 * @param approveDate
 	 * @return
 	 */
 	public int addState(String refno,String state,String approveDate,String adminUsername)throws SQLException;
 	
 	/**
	 * 查询request_staff上一次的申请记录
	 * @return
	 * @throws SQLException
	 */
	List<RequestStaffBean> getOldRecord(String staffcode)throws SQLException;
	
	/**
	 * 获取公司 
	 * @return
	 */
	List<RequestStaffCompanyView> getCompany();
	/**
	 * 获取顾问上级信息
	 * @throws Exception 
	 */
	int getSupervisor() throws Exception;
	/**
	 * 获取远程数据库公司信息
	 * @throws Exception 
	 */
	int getForm_Company() throws Exception;
	
	
	String findCompany(String staffNO);
	
	String getCompany_EnglishName(String Type);
}
