package dao;

import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;
import entity.CPibaBook;
import entity.CPibaOrder;
import entity.CPibaOrderDetial;
import entity.CPibaSigndetial;
import entity.C_Access;
import entity.C_Payment;

public interface PIBAStudyNoteDao {

	/**
	 * 保存AccessCard
	 * @param ca
	 * @return
	 */
	int saveAccessCard(C_Access ca);

	/**
	 * 提交订单
	 * @return
	 */
	int submitOrder(String staffcode,String staffname,String location,String type,String username,String userType,
			String signcodes,String signnames,String bookTypes,String bookNames,String bookNumber);
	
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
	int getRow(String staffcode,String staffname, String refno, String status,String start_date,String end_date);
	
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
	 List<CPibaOrder> findAccessList(String staffcode ,String staffname, String refno,String status, Page page,String start_date,String end_date) ;
	 
	 /**
	  * 查看明细
	  * @param refno
	  * @return
	  */
	 List<CPibaOrderDetial> findAdminserviceByRef(String refno);
	 
	 
	 List<CPibaSigndetial> findCPibaSigndetial(String refno);
	 
	 /**
	  * 查询所有的Book
	  * @return
	  */
	 List<CPibaBook> queryBook();
	 /**
	  * 操作Access Completed
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @param payment
	  * @return
	  */
	 public int complete(String refno, String type,String staffcode); 
	 
	/**
	 * 操作Access Ready
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	 public int Ready(String refno, String type,String staffcode,String staffnames,String location);
	 
	 /**
	  * 删除 AccessCard
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @return
	  */
	 public int Deleted(String refno, String type,String staffcode);

	 public int DeletedCon(String refno, String type,String staffcode);
	 /**
	  * 取消订单
	  * @param refno
	  * @param type
	  * @param staffcode
	  * @return
	  */
	 public int VOID(String refno, String type,String staffcode);
	 /**
	  * 根据条件导出AccessCard
	  * @param startDate
	  * @param endDate
	  * @param staffcode
	  * @param staffname
	  * @param refno
	  * @return
	  */
	 Result downPIBAList(String startDate,String endDate, String staffcode, String staffname, String refno, String status);
	 /**
	  * 根据时间段导出AccessCard Reporting
	  * @param startDate
	  * @param endDate
	  * @return
	  */
	 Result downReportingForAccessCard(String startDate,String endDate);
}
