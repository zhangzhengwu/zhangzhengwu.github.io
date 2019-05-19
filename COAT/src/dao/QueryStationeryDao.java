package dao;

import java.util.List;

import entity.C_stationeryOrder;
import entity.C_stationeryProduct;
import entity.C_stationeryRecord;

/**
 *  文具表
 * @author sky
 *
 */
public interface QueryStationeryDao {
	//=====================产品表==================//
	/**
	 * sky
	 * 查询所有产品
	 * @return
	 * */
	public List<C_stationeryProduct> queryStationery(String BlBZ) ;

	//=====================订单表==================//
	/**
	 * sky
	 * 对订单表的新增
	 * @return
	 * */
	public int saveOrderStationery(C_stationeryOrder order) ;
	
	//=====================记录表==================//
	/**
	 * sky
	 * 对记录表的新增
	 * @return
	 * */
	public int saveRecordStationery(List<C_stationeryRecord> list) ;
	
	/**
	 * @author sky
	 * 购买商品后，修改库存
	 */
	public int updateProQuantity(List<C_stationeryProduct> list) ;
	
	//=====================新增记录表、新增订单表、更新产品表数量==================//
	/**
	 * sky
	 * 新增记录表、新增订单表、更新产品表数量
	 * @return
	 * */
	public int saveThreeStationery(List<C_stationeryRecord> list,C_stationeryOrder order,List<C_stationeryProduct> list1) ;

	/**
	 * 	sky
	 *  通过产品code,产品名称去查询该产品的库存数量
	 * */
	public double getQuantityByProcode(String procode);

	/**
	 *  查询stationery 消费记录表的影响行数
	 */
	public int getRows(String startDate, String endDate,
			String ordercode, String productname,
			String clientname, String clientcode, String location);
	/**
	 *   
	 *  查询stationery 消费记录表
	 * @param startDate
	 * @param endDate
	 * @param ordercode
	 * @param productname
	 * @return
	 * 调用处   QueryStationeryOrderForHRServlet line in 
	 */
	public List<C_stationeryRecord> findStationeryRecord(String startDate,
			String endDate, String ordercode,
			String productname,String clientname,
			String clientcode,String location, int pageSize, int currentPage);
	/**
	 * 修改产品数量并更新库存
	 */
	public int updateStationeryStock(String id,String quantity,String allprice,String username);
	/**
	 * 根据id查询产品信息 
	 * @param id
	 * @param username
	 * @return
	 */

	public C_stationeryRecord findRecordById(String id);
	
	
	
	
	
	
	
	
	//=================================Wilson=================================//
	/**
	 * Wilson
	 * 验证是否存在code
	 * @param procode
	 * @return
	 */
	public int findAllByCode(String procode) ;
	/**
	 * Wilson
	 * 新增产品
	 * @param procode
	 * @param ename
	 * @param cname
	 * @param price
	 * @param unit
	 * @param num
	 * @param blbz
	 * @return
	 */
	public int saveProduct(String procode,String ename,String cname,Double price,String unit,Double num,String blbz);
	/**
	 * Wilson
	 * 修改产品
	 * @param procode
	 * @param ename
	 * @param cname
	 * @param price
	 * @param unit
	 * @param num
	 * @param blbz
	 * @return
	 */
	public int updProduct(String procode,String ename,String cname,Double price,String unit,Double num,String blbz);
	public int delProduct(String procode);
	
	/**
	 * 获取的订单id 以Syyyymmdd0001的方式
	 * */
	public String getOrderIdByRandom(String tableName);
}
