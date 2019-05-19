package dao;

import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import entity.C_Payment;
import entity.C_Roomsetting;
import entity.C_RoomsettingOperation;
import entity.C_StationeryOperation;
/**
 * C_RoomSettingDao接口
 * @author Sky
 *
 */
public interface C_RoomSettingDao {
	/**
	 * 新增C_Roomsetting
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveRoomSetting(C_Roomsetting room);
	
	/**
	 * 分页查询信息
	 */
	public int getRoomcount(String eventName, String startDate,String endDate) ;
	
	/**
	 * 查询分页总条数
	 * @param eventName
	 * @param userType
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getRoomcount(String eventName,String userType,String staffcode,String staffname,String refno, String startDate,String endDate,String status); 
	/**
	 * 分页查询 信息
	 */
	public List<C_Roomsetting> queryRoomList(String eventName,String startDate, String endDate,int pageSize,int currentPage) ;
	/**
	 * 分页查询
	 * @param eventName
	 * @param userType
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<C_Roomsetting> queryRoomList(String eventName,String userType,String staffcode,String staffname,String refno,
			String startDate, String endDate,String status, int pageSize, int currentPage); 
	
	/**
	  * 获取流水号
	  * @return
	  */
	public String findref();
	/**
	 * 查询 订单明细信息
	 */
	public List<C_Roomsetting> getRoomSetting(String refno) ;
	
	public int getRoomSettingByName(String eventName) ;
	/**
	 * 根据refno删除数据
	 * @param refno
	 * @return
	 */
	public int delRoomSetting(String refno) ;
	
	public int changeStatus(String refno,String status) ;
	
	public int changeStationeryStatus(String ordercode,String status) ;
	/**
	 * 新增C_Roomsetting
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveRoomSettingOperation(C_RoomsettingOperation room);
	
	public int saveStationeryOperation(C_StationeryOperation stationery);
	/**
	 * 导出查询C_Roomsetting
	 * @param filename
	 * @param os
	 * @return
	 */
	public List<C_Roomsetting> queryRoomList(String eventName,String startDate, String endDate) ;
	
	/**
	 * 新增c_payment
	 * @param filename
	 * @param os
	 * @return
	 */
	public int savePayment(C_Payment payment);
//	public Map<String,Double> getStationeryProcodeQuantity(String ordercode);
//	
//	public int updateStationeryQuantity(String ordercode,String quantity) ;
	
	
	/**
	 * 到处reporting for RoomSetting
	 */
	public Result downReportingForRoomSetting(String startDate, String endDate) ;
	
	/**
	 * 查询产品列表以mediatype
	 * @return
	 */
	public Result getProductList();
	
	/**
	 * 查询Advertising订单列表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result getAdvertising(String startDate,String endDate);
	
	/**
	 * 查询epayment订单表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result getepayment(String startDate,String endDate);
}
