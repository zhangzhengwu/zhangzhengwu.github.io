package dao;

import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;
import entity.CAttachment;
import entity.C_Access;
import entity.C_Payment;

public interface AccessCardDao {

	/**
	 * 保存AccessCard
	 * @param ca
	 * @return
	 */
	int saveAccessCard(C_Access ca,String filenameAndPath);
	/**
	 * 查询分页总条数
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @return
	 */
	int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status);
	
	/**
	 * 分页查询
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @return
	 */
	 List<C_Access> findAccessList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status, Page page) ;
	 
	 /**
	  * 查看明细
	  * @param refno
	  * @return
	  */
	 C_Access findAdminserviceByRef(String refno);
	 
	 /**
	  * 操作Access Completed
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @param payment
	  * @return
	  */
	 public int completed(String refno, String type,String staffcode,C_Payment payment); 
	 
	/**
	 * 操作Access Ready
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	 public int Ready(String refno, String type,String staffcode,String to,String localtion,String types,String remark);
	 
	 /**
	  * 操作Access HKADM
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @param exitAccess
	  * @param newAccess
	  * @return
	  */
	 int modify(String refno, String type,String staffcode,String exitAccess,String newAccess,String remark);
	 
	 /**
	  * 删除 AccessCard
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @return
	  */
	 public int Deleted(String refno, String type,String staffcode);
	 /**
	  * 取消订单
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @return
	  */
	 public int VOID(String refno, String type,String staffcode,String remark);
	 /**
	  * 根据条件导出AccessCard
	  * @param startDate
	  * @param endDate
	  * @param staffcode
	  * @param staffname
	  * @param refno
	  * @return
	  */
	 Result downAccessList(String startDate,String endDate, String staffcode, String staffname, String refno, String status);
	 /**
	  * 根据时间段导出AccessCard Reporting
	  * @param startDate
	  * @param endDate
	  * @return
	  */
	 Result downReportingForAccessCard(String startDate,String endDate);
	 
	 public String findAttachment(String refno);
}
