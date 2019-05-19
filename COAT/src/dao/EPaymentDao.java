package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;

import entity.C_EPayment_List;
import entity.C_Epayment_Detail;
import entity.C_Epaymentt_Order;
import entity.C_Payment;

public interface EPaymentDao {

	/**
	 * 分页查询 订单信息
	 */
	public List<C_Epaymentt_Order> queryEpaymentt_Order(String staffcode,String ordercode, String startDate,String endDate,
			String staffname,String userType,String location,String status,int currentPage,int pageSize);
	
	/**
	 * 分页查询订单信息条数
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param staffname
	 * @param userType
	 * @param location
	 * @return
	 */
	public int getC_PaymentCount(String staffcode,String ordercode, String startDate, String endDate,String staffname,String userType,String location,String status);
	
	/**
	 * 分页查询产品信息
	 * @param productcode
	 * @param productname
	 * @param startDate
	 * @param endDate
	 * @param sfyx
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<C_EPayment_List> queryProduct(String productcode,String productname,String startDate,String endDate,String sfyx,int currentPage,int pageSize);
	
	/**
	 * 获取产品总条数
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param staffname
	 * @param userType
	 * @param location
	 * @param status
	 * @return
	 */
	public int getNum(String productcode,String productname,String startDate,String endDate,String sfyx);
	
	
	/**
	 * 查询订单明细
	 * @param refno
	 * @return
	 */
	public List<C_Epaymentt_Order> queryOrderDetial(String refno);
	
	/**
	 * 查询C_Payment
	 * @param refno
	 * @return
	 */
	public List<C_Payment> queryPayment(String refno);
	
	/**
	 * 查询C_Payment_Detail
	 * @param refno
	 * @return
	 */
	public List<C_Epayment_Detail>  queryDetail(String refno);
	
	public int saveEPayment(String filename, InputStream os);
	/**
	 * 完成订单
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @param payment
	 * @return
	 */
	public int completed(String type,String user,String refno,String status,String staffname,String staffcodes,C_Payment payment);
	
	/**
	 * 取消订单
	 * @param ordercode
	 * @param user
	 * @return
	 */
	public int VOID(String refno,String user);
	
	
	/**
	 * 产品加载
	 * @return
	 */
	public List<C_EPayment_List> queryRequestList();
	
	/**
	 * 保存订单信息
	 * @param epayment
	 * @return
	 */
	public int savePayment(C_Epaymentt_Order epayment,List<C_Epayment_Detail> detail);
	
	/**
	 * 根据条件导出epayment
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param staffname
	 * @param userType
	 * @param location
	 * @param status
	 * @return
	 */
	public ResultSet queryEpaymentt_Order(String staffcode, String ordercode,String startDate, String endDate, String staffname,String userType, String location, String status);
	
	public List<String[]> queryEpaymentt_Order2(String staffcode, String ordercode,String startDate, String endDate, String staffname,String userType, String location, String status);
	
	/**
	 * 批量保存顾问信息列表
	 * @param list
	 * @return
	 */
	public int saveEPaymentList(List<List<Object>> list);
	
	/**
	 * 批量保存订单申请
	 * @param list
	 * @return
	 */
	public String saveEpaymentRequest(List<List<Object>> list,String user);
	
}
