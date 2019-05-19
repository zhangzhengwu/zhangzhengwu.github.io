package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.QueryStationeryDao;
import entity.C_stationeryOrder;
import entity.C_stationeryProduct;
import entity.C_stationeryRecord;
/**
 *  
 * @author sky
 *
 */
public class QueryStationeryDaoImpl implements QueryStationeryDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryStationeryDaoImpl.class);
	/**
	 *  查询文具表
	 * @author sky
	 *
	 */
	public List<C_stationeryProduct> queryStationery(String BlBZ) {
		
		List<C_stationeryProduct> list = new ArrayList<C_stationeryProduct>();
		try {
			connection = DBManager.getCon();
			String sqlString = null;
			if(BlBZ == null){
				sqlString ="SELECT procode,englishname,chinesename,price,unit,quantity FROM c_stationery_product";
			}else{
				sqlString ="SELECT procode,englishname,chinesename,price,unit,quantity FROM c_stationery_product where BlBZ = '"+BlBZ+"' or BlBZ = 'A'";
			}
			logger.info(" 查询stationery_list SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				C_stationeryProduct peoBean = new C_stationeryProduct();
				peoBean.setProcode(rs.getString("procode"));
				peoBean.setEnglishname(rs.getString("englishname"));
				peoBean.setChinesename(rs.getString("chinesename"));
				peoBean.setPrice(rs.getDouble("price"));
				peoBean.setUnit(rs.getString("unit"));
//				peoBean.setUpdates(rs.getString("updates"));
				peoBean.setQuantity(rs.getDouble("quantity"));
				list.add(peoBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 stationery_list异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 position_list异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		
		return list;
	
	}

	/**
	 * 获取的订单id 以Syyyymmdd0001的方式
	 * */
	public String getOrderIdByRandom(String tableName){
		int number = 0;
		int temp = 0;
		String sql = "select substring(a.ordercode,10,length(a.ordercode)) as number from "+tableName+" a";
		try {
			connection = DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			 while(rs.next()){
				 number = rs.getInt("number");
				 if(number > temp){
					 temp = number;
				 }
			 }
			 rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		String oid = Integer.valueOf(temp+1).toString();
		if(oid.length() == 1) oid = "000"+oid;
     	if(oid.length() == 2) oid = "00"+oid;
     	if(oid.length() == 3) oid = "0"+oid;
		return oid;
	}
	
	/**
	 * Wilson
	 * CODE 验证
	 */
	public int findAllByCode(String procode) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select * from c_stationery_product where procode ='"+procode+"'");
		
			logger.info("Query Stationery SQL:"+stringBuffer.toString());
			ps = connection.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Stationery 中根据条件查询数据个数时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * Wilson
	 * 产品信息，全部保存 重复 CODE 已做验证
	 */
	public int saveProduct(String procode, String ename, String cname,
			Double price, String unit, Double quantity, String blbz) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("insert into c_stationery_product(procode," +
					"englishname,chinesename,price,unit,quantity,BLBZ,updates)" +
					" values('"+procode+"','"+ename+"','"+cname+"','"+price+"','"+unit+"','"+quantity+"','"+blbz+"','"+DateUtils.getNowDateTime()+"')");
		
			logger.info("saveProduct SQL:"+stringBuffer.toString());
			ps = connection.prepareStatement(stringBuffer.toString());
			num = ps.executeUpdate();
			if(num < 0){
				logger.info("saveProduct信息表失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveProduct 数据个数时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * Wilson
	 * 累计库存
	 */
	public int updProduct(String procode, String ename, String cname,
			Double price, String unit, Double num, String blbz) {
		int r = -1;
		try {
			 
			 connection=DBManager.getCon();
			 String sql="update c_stationery_product set englishname='"+ename+"',chinesename='"+cname+"',price="+price+",unit='"+unit+"',quantity=quantity+"+num+",BLBZ='"+blbz+"' where procode='"+procode+"' ";
			 logger.info("updProduct信息表SQL:"+sql);
			 
			ps=connection.prepareStatement(sql);
			r=ps.executeUpdate();
			if(r<0){
				logger.info("updProduct信息表失败");
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("updProduct信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("updProduct信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return r;
			
	}

	
	
	
	
	
	
	
	/**
	 * @author sky
	 * 购买商品后，修改库存
	 */
	public int updateProQuantity(List<C_stationeryProduct> list) {
		int r = -1;
		PreparedStatement psta = null;
		try {
			C_stationeryProduct product = null;
			connection=DBManager.getCon();
			for (int i = 0; i < list.size(); i++) {
				product = (C_stationeryProduct)list.get(i);
				String sql="update c_stationery_product set quantity=(quantity-?) where procode=? and englishname=? and chinesename=? ";
				psta = connection.prepareStatement(sql);
				psta.setDouble(1, product.getQuantity());
				psta.setString(2, product.getProcode());
				psta.setString(3, product.getEnglishname());
				psta.setString(4, product.getChinesename());
				logger.info("updateProQuantity信息表SQL:"+sql);
				r = psta.executeUpdate();
				if(r<0){
					logger.info("updateProQuantity信息表失败");
				}
			}
		}catch(Exception e){
			r=0;
			e.printStackTrace();
			try {
				logger.error("updateProQuantity信息表异常！"+e);
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("数据回滚时出现：==="+e1);
				e1.printStackTrace();
			}
		}
		finally{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return r;
			
	}
	
	/**
	 * 	sky
	 *  通过产品code,产品名称去查询该产品的库存数量
	 * */
	public double getQuantityByProcode(String procode){
		double num = 0;
		try {
			connection=DBManager.getCon();
			String sql="select quantity from c_stationery_product where procode='"+procode+"'";
			logger.info("selectQuantity信息表SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num = rs.getDouble("quantity");
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("selectQuantity信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("selectQuantity信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return num;
	}

	/**
	 * sky
	 * 新增记录表、新增订单表、更新产品表数量
	 * @return
	 * */
	public int saveThreeStationery(List<C_stationeryRecord> list,C_stationeryOrder order,List<C_stationeryProduct> list1){
		int num=-1;
		String s = "";
		PreparedStatement psta = null;
		try {
			connection=DBManager.getCon();
			connection.setAutoCommit(false);//禁止自动提交事务
			
			
			//新增订单表
			s = "insert into c_stationery_order(ordercode,clientname,clientcode,priceall,orderdate,location,departhead,sfyx,staffOrCons,cash,C_club,cheque,cheque_no,banker,status) ";
			s += " values(?,?,?,?,sysdate(),?,?,?,?,?,?,?,?,?,?)";
			psta = connection.prepareStatement(s);
			psta.setString(1, order.getOrdercode());
			psta.setString(2, order.getClientname());
			psta.setString(3, order.getClientcode());
			psta.setDouble(4, order.getPriceall());
			psta.setString(5, order.getLocation());
			psta.setString(6, order.getDeparthead());
			psta.setString(7, order.getSfyx());
			psta.setString(8, order.getStaffOrCons());
			psta.setString(9, order.getCash());
			psta.setString(10, order.getC_club());
			psta.setString(11, order.getCheque());
			psta.setString(12, order.getCheque_no());
			psta.setString(13, order.getBanker());
			psta.setString(14, order.getStatus());
			logger.info("saveOrder SQL:"+s);
			num = psta.executeUpdate();
			
			
			
			//新增记录表
			for (int i = 0; i < list.size(); i++) {
				C_stationeryRecord  record = (C_stationeryRecord)list.get(i);
				s = "insert into c_stationery_consume_record(ordercode,procode,proname,price,quantity,priceall,procname) ";
				s += " values(?,?,?,?,?,?,?)";
				psta = connection.prepareStatement(s);
				psta.setString(1, record.getOrdercode());
				psta.setString(2, record.getProcode());
				psta.setString(3, record.getProname());
				psta.setDouble(4, record.getPrice());
				psta.setDouble(5, record.getQuantity());
				psta.setDouble(6, record.getPriceall());
				psta.setString(7, record.getProcname());
				logger.info("saveRecord SQL:"+s);
				num = psta.executeUpdate();
				
			}
			
			
			//购买商品后，修改库存
			C_stationeryProduct product = null;
			for (int i = 0; i < list1.size(); i++) {
				product = (C_stationeryProduct)list1.get(i);
				String sql="update c_stationery_product set quantity=(quantity-?) where procode=? and englishname=? and chinesename=? ";
				psta = connection.prepareStatement(sql);
				psta.setDouble(1, product.getQuantity());
				psta.setString(2, product.getProcode());
				psta.setString(3, product.getEnglishname());
				psta.setString(4, product.getChinesename());
				logger.info("updateProQuantity信息表SQL:"+sql);
				num = psta.executeUpdate();
			}
			
			connection.commit();   //提交
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveOrder 数据个数时出现：==="+e);
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("数据回滚时出现：==="+e1);
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(connection);
		}
		
		return num;
	}
	/**
	 * sky
	 * 对订单表的新增
	 * @return
	 * */
	public int saveOrderStationery(C_stationeryOrder order) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			String s = "insert into c_stationery_order(ordercode,clientname,clientcode,priceall,orderdate,location,departhead,sfyx,staffOrCons,cash,C_club,cheque,cheque_no,banker) ";
			s += " values(?,?,?,?,sysdate(),?,?,?,?,?,?,?,?,?)";
			logger.info("saveOrder SQL:"+s);
			PreparedStatement psta = connection.prepareStatement(s);
			psta.setString(1, order.getOrdercode());
			psta.setString(2, order.getClientname());
			psta.setString(3, order.getClientcode());
			psta.setDouble(4, order.getPriceall());
			psta.setString(5, order.getLocation());
			psta.setString(6, order.getDeparthead());
			psta.setString(7, order.getSfyx());
			psta.setString(8, order.getStaffOrCons());
			psta.setString(9, order.getCash());
			psta.setString(10, order.getC_club());
			psta.setString(11, order.getCheque());
			psta.setString(12, order.getCheque_no());
			psta.setString(13, order.getBanker());
//			ps = connection.prepareStatement(s);
			num = psta.executeUpdate();
			if(num < 0){
				logger.info("saveOrder信息表失败");
			}
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveOrder 数据个数时出现：==="+e);
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("数据回滚时出现：==="+e1);
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}


	/**
	 * sky
	 * 对记录表的新增
	 * @return
	 * */
	public int saveRecordStationery(List<C_stationeryRecord> list) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			for (int i = 0; i < list.size(); i++) {
				C_stationeryRecord  record = (C_stationeryRecord)list.get(i);
				String s = "insert into c_stationery_consume_record(ordercode,procode,proname,price,quantity,priceall,procname) ";
				s += " values(?,?,?,?,?,?,?)";
				PreparedStatement psta = connection.prepareStatement(s);
				psta.setString(1, record.getOrdercode());
				psta.setString(2, record.getProcode());
				psta.setString(3, record.getProname());
				psta.setDouble(4, record.getPrice());
				psta.setDouble(5, record.getQuantity());
				psta.setDouble(6, record.getPriceall());
				psta.setString(7, record.getProcname());
				
				logger.info("saveRecord SQL:"+s);
//				String[] params = {record.getOrdercode(),record.getProcode(),record.getProname(),
//						record.getPrice()+"",record.getQuantity()+"",record.getPriceall()+"",
//						record.getProcname()};
//				ps = connection.prepareStatement(s,params);
				num = psta.executeUpdate();
				if(num < 0){
					logger.info("saveRecord信息表失败");
				}
			}
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveRecord 数据个数时出现：==="+e);
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("数据回滚时出现：==="+e1);
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	
	/**
	 *  查询stationery 消费记录表的影响行数
	 */
	public int getRows(String startDate, String endDate,
			String ordercode, String productname,
			String clientname, String clientcode, String location) {
		int num=-1;
		try{
			StringBuffer stringBuffer=new StringBuffer("select count(*) as num  from c_stationery_consume_record r left join c_stationery_order o on(o.ordercode=r.ordercode) where o.sfyx='Y' ");
			if(!Util.objIsNULL(startDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate.trim()+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate.trim()+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(ordercode)){
				stringBuffer.append(" and r.ordercode='"+ordercode.trim()+"'");
			}
			if(!Util.objIsNULL(productname)){
				stringBuffer.append(" and r.proname='"+productname.trim()+"'");
			}
			if(!Util.objIsNULL(clientname)){
				stringBuffer.append(" and clientname like '%"+clientname.trim()+"%'");
			}
			if(!Util.objIsNULL(clientcode)){
				stringBuffer.append(" and clientcode like '%"+clientcode.trim()+"%'");
			}
			if(!Util.objIsNULL(location)){
				stringBuffer.append(" and location like '%"+location.trim()+"%'");
			}
			connection=DBManager.getCon();
		    logger.info("QueryStationeryDaoImpl 查询stationery");
		    ps=connection.prepareStatement(stringBuffer.toString());
		    ResultSet rs=ps.executeQuery();
		     while(rs.next()){
		    	 num=rs.getInt("num");
			 }
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			 logger.info("QueryStationeryDaoImpl line in  查询 Stationery时出现   ："+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	
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
			String clientcode,String location, int pageSize, int currentPage) {
		List<C_stationeryRecord> list = new ArrayList<C_stationeryRecord>();
		try{
			StringBuffer stringBuffer=new StringBuffer("select id,r.ordercode,sum(r.priceall) as priceall,clientname,clientcode,location,orderdate,o.status  from c_stationery_consume_record r left join c_stationery_order o on(o.ordercode=r.ordercode) where sfyx='Y' and r.quantity<>0 ");
			if(!Util.objIsNULL(startDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate.trim()+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate.trim()+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(ordercode)){
				stringBuffer.append(" and r.ordercode='"+ordercode.trim()+"'");
			}
			if(!Util.objIsNULL(productname)){
				stringBuffer.append(" and r.proname='"+productname.trim()+"'");
			}
			if(!Util.objIsNULL(clientname)){
				stringBuffer.append(" and clientname like '%"+clientname.trim()+"%'");
			}
			if(!Util.objIsNULL(clientcode)){
				stringBuffer.append(" and clientcode like '%"+clientcode.trim()+"%'");
			}
			if(!Util.objIsNULL(location)){
				stringBuffer.append(" and location like '%"+location.trim()+"%'");
			}
			
			stringBuffer.append(" group by ordercode order by orderdate desc");
			stringBuffer.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			connection=DBManager.getCon();
		    logger.info("QueryStationeryDaoImpl 查询stationery");
			 ps=connection.prepareStatement(stringBuffer.toString());
			 ResultSet rs=ps.executeQuery();
			 while(rs.next()){
				 C_stationeryRecord record = new C_stationeryRecord();
				 record.setId(rs.getInt("id"));
				 record.setOrdercode(rs.getString("ordercode"));
				 record.setPriceall(Double.parseDouble(rs.getString("priceall")));
				 record.setClientname(rs.getString("clientname"));
				 record.setClientcode(rs.getString("clientcode"));
				 record.setLocation(rs.getString("location"));
				 record.setOrderdate(rs.getString("orderdate"));
				 record.setStatus(rs.getString("status"));
				 
				 list.add(record);
			 }
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			 logger.info("QueryStationeryDaoImpl line in  查询 Stationery时出现   ："+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	
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
	//TODO:
	/*public List<C_stationeryRecord> findStationeryRecord(String startDate,
			String endDate, String ordercode,
			String productname,String clientname,
			String clientcode,String location, int pageSize, int currentPage) {
		List<C_stationeryRecord> list = new ArrayList<C_stationeryRecord>();
		try{
			StringBuffer stringBuffer=new StringBuffer("select id,r.ordercode,r.procode,r.proname,r.price,r.quantity,r.priceall,clientname,clientcode,location,orderdate  from c_stationery_consume_record r left join c_stationery_order o on(o.ordercode=r.ordercode) where sfyx='Y' and r.quantity<>0 ");
			if(!Util.objIsNULL(startDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate.trim()+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate.trim()+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(ordercode)){
				stringBuffer.append(" and r.ordercode='"+ordercode.trim()+"'");
			}
			if(!Util.objIsNULL(productname)){
				stringBuffer.append(" and r.proname='"+productname.trim()+"'");
			}
			if(!Util.objIsNULL(clientname)){
				stringBuffer.append(" and clientname like '%"+clientname.trim()+"%'");
			}
			if(!Util.objIsNULL(clientcode)){
				stringBuffer.append(" and clientcode like '%"+clientcode.trim()+"%'");
			}
			if(!Util.objIsNULL(location)){
				stringBuffer.append(" and location like '%"+location.trim()+"%'");
			}
			
			stringBuffer.append(" order by orderdate desc");
			stringBuffer.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			connection=DBManager.getCon();
		    logger.info("QueryStationeryDaoImpl 查询stationery");
			 ps=connection.prepareStatement(stringBuffer.toString());
			 ResultSet rs=ps.executeQuery();
			 while(rs.next()){
				 C_stationeryRecord record = new C_stationeryRecord();
				 record.setId(rs.getInt("id"));
				 record.setOrdercode(rs.getString("ordercode"));
				 record.setProcode(rs.getString("procode"));
				 record.setProname(rs.getString("proname"));
				 record.setPrice(Double.parseDouble(rs.getString("price")));
				 record.setQuantity(Double.parseDouble(rs.getString("quantity")));
				 record.setPriceall(Double.parseDouble(rs.getString("priceall")));
				 record.setClientname(rs.getString("clientname"));
				 record.setClientcode(rs.getString("clientcode"));
				 record.setLocation(rs.getString("location"));
				 record.setOrderdate(rs.getString("orderdate"));
				 
				 list.add(record);
			 }
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			 logger.info("QueryStationeryDaoImpl line in  查询 Stationery时出现   ："+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}*/
	/**
	 * 修改产品数量并更新库存
	 */
	public int updateStationeryStock(String id,String quantity,String allprice,String username) {
		C_stationeryRecord cm=findRecordById(id);
		int num=-1;
		String sql = "";
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			sql="update c_stationery_order set priceall=priceall-"+(cm.getPrice()*(cm.getQuantity()-Double.parseDouble(quantity)))+" where" +
					" sfyx='Y' and status!='Completed' and ordercode='"+cm.getOrdercode()+"'";
			ps=connection.prepareStatement(sql);
			num=ps.executeUpdate();
			
			if(num<=0){
				throw new RuntimeException();
			}
			/*
			sql="update c_mar_record set remark3=?,remark4=? where id=?";
			ps=connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, DateUtils.getNowDateTime());
			ps.setInt(3, Integer.parseInt(id));
			//ps.addBatch();
			num=ps.executeUpdate();
			if(num<=0){
				throw new RuntimeException();
			}*/
			sql="update c_stationery_consume_record set quantity="+Double.parseDouble(quantity)+",priceall=price*"+Double.parseDouble(quantity)+" where procode='"+cm.getProcode()+"' and ordercode='"+cm.getOrdercode()+"'";
			ps=connection.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<=0){
				throw new RuntimeException();
			}
			sql="update c_stationery_product set quantity=quantity+"+(cm.getQuantity()-Double.parseDouble(quantity))+" where procode='"+cm.getProcode()+"'";
			ps=connection.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<=0){
				throw new RuntimeException();
			}
			connection.commit();
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			logger.error("在QueryStationeryDaoImpl中保存更新数据时出现  ："+sql+" ===="+e);
			try{
				connection.rollback();
				//throw new RuntimeException(e);
			}catch(Exception e1){
				e.printStackTrace();
				logger.error("在QueryStationeryDaoImpl中数据回滚时出现    :=="+sql+"=="+e1);
			}
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * 根据id查询产品信息 
	 * @param id
	 * @param username
	 * @return
	 */

	public C_stationeryRecord findRecordById(String id){
		C_stationeryRecord cm=new C_stationeryRecord();
		try{
			connection=DBManager.getCon();
			String sql="select ordercode,procode,proname,price,quantity,priceall from c_stationery_consume_record where id=?";
			ps=connection.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(id));
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				cm.setOrdercode(rs.getString("ordercode"));
				cm.setProcode(rs.getString("procode"));
				cm.setProname(rs.getString("proname"));
				cm.setPrice(rs.getDouble("price"));
				cm.setQuantity(rs.getDouble("quantity"));
				cm.setPriceall(rs.getDouble("priceall"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在QueryStationeryDaoImpl 中根据Id查询时出现 ：==="+e);
		}finally{
		DBManager.closeCon(connection);	
		}
		return cm;
	}
	public int delProduct(String procode) {
		Connection con = null;
		PreparedStatement ps = null;
		int num =0;
		String sql = "update c_stationery_product set BLBZ='N' where procode ='"+procode+"'";
		//String sql = "delete from c_mar_product where procode ='"+procode+"'";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			num = ps.executeUpdate();

			System.out.println("=====del========c_stationery_product============" + num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}
	
}
