package dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import entity.C_Payment;
import entity.C_stationeryOrder;
import entity.C_stationeryRecord;
/**
 * C_GetStationeryDao接口
 * @author Wilson
 *
 */
public interface C_GetStationeryDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveStationery(String filename, InputStream os);//上传文件接口
	
	/**
	 * 分页查询 订单信息
	 */
	public int getOrdercount(String staffcode, String startDate,String ordercode, String endDate) ;
	
	/**
	 * HR专用查询
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param status
	 * @param orderType
	 * @return
	 */
	public int getOrdercount(String staffcode,String staffname,String ordercode, String startDate, String endDate,String location,String status,String orderType);
	/**
	 * 分页查询 订单信息
	 */
	public List<C_stationeryOrder> queryOrderList(String staffcode, String ordercode,String startDate, String endDate,int pageSize,int currentPage) ;
	
	
	/**
	 * HR专用分页查询
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param status
	 * @param orderType
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<C_stationeryOrder> queryOrderList(String staffcode,String staffname,String ordercode,
			String startDate, String endDate,String location,String status,String orderType, int pageSize, int currentPage);
	
	/**
	 * 分页查询 订单信息
	 */
	public List<C_stationeryOrder> queryOrderListForPDF(String staffcode, String ordercode,String startDate, String endDate,int pageSize,int currentPage) ;
	/**
	 * 分页查询 订单明细信息
	 */
	public int getOrderRecordCount(String staffcode,String ordercode, String startDate, String endDate) ;
	/**
	 * 分页查询 订单信息
	 */
	public List<C_stationeryRecord> queryOrderRecordList(String staffcode, String ordercode,String startDate, String endDate,int pageSize,int currentPage) ;
	/**
	 * 导出 订单明细信息 先分组查询数据
	 */
	public List<String> getReportDetail(String staffcode,String ordercode,  String startDate, String endDate) ;
	/**
	 * 导出 订单明细信息
	 */
	public List<C_stationeryRecord> reportOrderRecord(String staffcode,String ordercode,  String startDate, String endDate) ;
	/**
	 * 导出 订单明细信息
	 */
	public int reprtPDF(String staffcode,String ordercode,  String startDate, String endDate,String urlString) ;
	/**
	 * 导出 订单明细信息
	 */
	public int reprtPDFOnTime(String staffcode,String ordercode,  String startDate, String endDate,String urlString) ;
	/**
	 * 查询 订单明细信息 用作修改处
	 */
	public List<C_stationeryRecord> getRecordForUpd(String ordercode) ;
	
	public List<C_Payment> getRecordPayment(String ordercode,String type) ;
	/**
	 * 查询 订单明细信息 用作修改处
	 */
	public C_stationeryOrder getOrder(String ordercode) ;
	
	/**
	 *改版前删除订单
	 * @param ordercode 订单号
	 * @return
	 */
	public int delStationeryOrder(String ordercode) ;
	
	/**
	 *改版后删除订单
	 * @param ordercode 订单号
	 * @param user 操作用户
	 * @return
	 */
	public int delStationeryOrder(String ordercode,String user) ;
	/**
	 * 改版后VOID操作
	 * @param ordercode
	 * @param user
	 * @return
	 */
	public int VOID(String ordercode,String user);
	
	
	

	public Map<String,Double> getStationeryProcodeQuantity(String ordercode);
	
	public int updateStationeryQuantity(String ordercode,String quantity) ;
}
