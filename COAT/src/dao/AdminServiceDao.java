package dao;

import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;

import entity.C_Adminservice;

public interface AdminServiceDao {
	/**
	 * 保存AdminService
	 * @param adminservice
	 * @return
	 */
	public int add(C_Adminservice adminservice);
 /**
  * 根据查询AdminService总条数
  * @param startDate
  * @param endDate
  * @param staffcode
  * @param staffname
  * @param refno
  * @param status
  * @param location
  * @param request
  * @return
  */
	public int getRow(String startDate,String endDate,String staffcode,String staffname,String refno,String status,String location,String requestType);
	
	 /**
	  * 取消订单
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @return
	  */
	 public int VOID(String refno, String type,String staffcode);
	/**
	 * 根据条件分页查询AdminService
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @return
	 */
	public List<C_Adminservice> findAdminServiceList(String startDate,String endDate,String staffcode,String staffname,String refno,String status,String location,String requestType,Page page);
	/**
	 * 根据Ref.NO查询Adminservice
	 * @param refno
	 * @return
	 */
	public C_Adminservice findAdminserviceByRef(String refno);
	/**
	 * 根据Ref.NO操作
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int modify(String refno,String type,String staffcode);
	/**
	 * 根据Ref.NO操作
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int Deleted(String refno,String type,String staffcode);
	/**
	 * 根据条件导出
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @param location
	 * @param requestType
	 * @return
	 */
	public List<C_Adminservice> downAdminServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String location,String requestType);
	
	
	/**
	 * 到处Reporting for ADMService
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result downReportingForADMService(String startDate, String endDate);
	
}
