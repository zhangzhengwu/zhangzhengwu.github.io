package dao;

import java.io.InputStream;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import entity.C_EPayment_List;
import entity.C_Payment;
import entity.C_marOrder;
import entity.C_marProduct;
import entity.C_marRecord;
 
public interface C_GetMarkPremiumDao {
	
	/**
	 * 批量保存产品列表
	 * @param list
	 * @return
	 */
	public int saveMarkPremiumList(List<List<Object>> list);
	
	public List<C_Payment> getRecordPayment(String ordercode,String type) ;
	/**
	 * 获取Marketing Premium的产品总行数
	 * @param productcode
	 * @param productname
	 * @param startDate
	 * @param endDate
	 * @param sfyx
	 * @return
	 */
	public int getAllRow(String productcode,String productname,String startDate,String endDate,String BLBZ);
	
	/**
	 * 根据条件分页查询产品信息
	 * @param productcode
	 * @param productname
	 * @param startDate
	 * @param endDate
	 * @param sfyx
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<C_marProduct> queryProduct(String productcode,String productname,String startDate,String endDate,String BLBZ,int currentPage,int pageSize);
	
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveMarkPremium(String filename, InputStream os);//上传文件接口
	/**
	 * 查询Marketing Premium的所有产品
	 * @return
	 */
	public List<C_marProduct> findMarProduct();
	/**
	 * 查询Marketing Premium的指定产品
	 * @return
	 */
	public List<C_marProduct> findMarProduct(String BLBZ);
	/**
	 * 批量保存Marketing Premium Record
	 * @param list
	 * @return
	 * 调用处 SaveMarketingPrimiumRecordServlet line in 
	 */
	public int saveMarRecord(List<C_marRecord> list,C_marOrder order);
	/**
	 * 保存Marketing Premium Order订单
	 * @param corder
	 * @return
	 * 调用处 SaveMarketingPrimiumRecordServlet line in
	 */
	public int  saveMarOrder(C_marOrder corder);
	/**
	 * 查询Marketing Premium 消费记录表
	 * @param startDate
	 * @param endDate
	 * @param ordercode
	 * @param ordertype
	 * @param productname
	 * @return
	 * 调用处   QueryMarketingPremiumHRServlet line in 
	 */
	public List<C_marRecord> findMarketingPremium(String startDate,String endDate,String ordercode,String ordertype,String productname,String clientname,String clientcode,String location,int currentPage,int pageSize);
	
	/**
	 * 修改产品数量并更新库存
	 * @param id
	 * @param quantity
	 * @param allprice
	 * @param username
	 * @return
	 */
	public int updateMarketingPremiuimStock(String id,String quantity,String allprice,String username);
	
	
	/**
	 * 查询Marketing Premium 消费记录表的影响行数
	 * @param startDate
	 * @param endDate
	 * @param ordercode
	 * @param ordertype
	 * @param productname
	 * @return
	 * 调用处   QueryMarketingPremiumHRServlet line in 
	 */
	public int getRows(String startDate,String endDate,String ordercode,String ordertype,String productname,String clientname,String clientcode,String location);
	
	
	
	/**
	 * 分页查询 订单信息
	 */
	public int getOrdercount(String staffcode, String ordercode, String startDate, String endDate) ;
	
	/**
	 * 改版后分页查询订单信息条数
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param staffname
	 * @param userType
	 * @param location
	 * @return
	 */
	public int getOrdercount(String staffcode,String ordercode, String startDate, String endDate,String staffname,String userType,String location,String status);
	/**
	 * 改版后分页查询订单信息
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param staffname
	 * @param userType
	 * @param location
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<C_marOrder> queryOrderList(String staffcode,String ordercode, String startDate,
			String endDate,String staffname,String userType,String location,String status, int pageSize, int currentPage);
	
	
	
	/**
	 * 分页查询 订单信息
	 */
	public List<C_marOrder> queryOrderList(String staffcode,String ordercode, String startDate, String endDate,int pageSize,int currentPage) ;
	/**
	 * 分页查询 订单信息 FOR PDF
	 */
	public List<C_marOrder> queryOrderListForPDF(String staffcode,String ordercode, String startDate, String endDate,int pageSize,int currentPage) ;
	/**
	 * 分页查询 订单明细信息
	 */
	public int getOrderRecordCount(String staffcode,String ordercode,  String startDate, String endDate) ;
	/**
	 * 分页查询 订单明细信息
	 */
	public List<C_marRecord> queryOrderRecordList(String staffcode, String ordercode, String startDate, String endDate,int pageSize,int currentPage) ;
	/**
	 * 导出 订单明细信息 先分组查询数据
	 */
	public List<String> getReportDetail(String staffcode,String ordercode,  String startDate, String endDate) ;
	/**
	 * 导出 订单明细信息
	 */
	public List<C_marRecord> reportOrderRecord(String staffcode,String ordercode,  String startDate, String endDate) ;
	/**
	 * 导出 订单明细信息
	 */
	public int reprtPDF(String staffcode,String ordercode,  String startDate, String endDate,String URL) ;
	
	/* 
	 *  导出 订单明细信息 定时任务
	 */
	public int reprtPDFOnTime(String staffcode,String ordercode,  String startDate, String endDate,String URL) ;
	/**
	 * 查询 订单明细信息 用作修改处
	 */
	public List<C_marRecord> getRecordForUpd(String ordercode) ;
	/**
	 * 查询 订单 
	 */
	public C_marOrder getOrder(String ordercode) ;
	/**
	 * 删除申请（改版前）
	 * @param ordercode
	 * @return
	 */
	public int delMarkOrder(String ordercode) ;
	/**
	 * 删除申请（改版后）
	 * @param ordercode
	 * @param user
	 * @return
	 */
	public int delMarkOrder(String ordercode,String user) ;
	/**
	 * 改版后VOID操作
	 * @param ordercode
	 * @param user
	 * @return
	 */
	 int VOID(String ordercode,String user);
	
	/**
	 * 根据订单Code Ready
	 * @param OrderCode
	 * @param status
	 * @return
	 */
	public int ready(String OrderCode,String status,String staffcode,String to,String location);
	
	
	/**
	 * 完成订单，新增支付信息
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @param payment
	 * @return
	 */
	public int completed(String refno, String type,String staffcode,String savetype,String paymethod,String payamount,
			String payDate,String handle,String staffname,String location,String saleno);
	/**
	 * 导出Report for Marketing Premium
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result downReportingForMarketingPremium(String startDate, String endDate) ;
	/**
	 * 获取Marketing Premium库存
	 */
	public Result findStore(String startDate, String endDate);
	/**
	 * 获取库存使用量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result chargeStore(String startDate, String endDate);
	
	
}
