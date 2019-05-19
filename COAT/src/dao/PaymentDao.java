package dao;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;
import entity.C_Payment;

public interface PaymentDao {
	/**
	 * 根据refno 查询C_Payment信息
	 * @param refno
	 * @return
	 */
	C_Payment findBYRef(String refno);
	/**
	 * 根据条件查询条数 for payment
	 * @param startDate
	 * @param endDate
	 * @param requestType
	 * @return
	 */
	int getRow(String startDate, String endDate, String requestType);
	/**
	 * 分页查询Payment信息
	 * @param startDate
	 * @param endDate
	 * @param requestType
	 * @param page
	 * @return
	 */
	public List<C_Payment> findAccessList(String startDate,
			String endDate, String requestType, Page page);
	/**
	 * 导出Payment
	 * @param startDate
	 * @param endDate
	 * @param requestType
	 * @return
	 */
	public Result downPaymentList(String startDate, String endDate,String requestType);
	
	/**
	 * 根据时间遍历epayment的产品信息
	 * @param startDate
	 * @param endDate
	 * @param requestType
	 * @return
	 */
	Map<String, String> findEpaymentList(String startDate,String endDate,String requestType);
	
}
