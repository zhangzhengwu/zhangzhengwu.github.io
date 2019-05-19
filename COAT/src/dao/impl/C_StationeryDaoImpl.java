package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.DBManager;
import util.DateUtils;
import util.SendMail;
import util.Util;
import dao.C_StationeryDao;
import entity.C_Payment;

public class C_StationeryDaoImpl implements C_StationeryDao {

	Logger logger = Logger.getLogger(C_StationeryDaoImpl.class);
	
	Connection con = null;
	PreparedStatement ps = null;
	String sql = "";
	public int completed(String order, String status, String user,String type,String paymethod,
			String payamount,String payDate,String handle,String staffname,String location,String saleno) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			sql="update c_stationery_order set status=? where ordercode=? and status='Ready' and sfyx='Y' ";
			 ps=con.prepareStatement(sql);
			 ps.setString(1, status);
			 ps.setString(2, order);
			 num=ps.executeUpdate();
			 if(num<1){
				 throw new RuntimeException(order+"操作失败,只能操作状态为Ready的记录.");
			 }
			
			 sql="insert c_stationery_operation(ordercode,operationType,operationName,operationDate)values('"+order+"','"+status+"','"+user+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				 throw new RuntimeException("写入记录失败.");
			 }
			
			String menthod[]=paymethod.split("~~");
			String amount[]=payamount.split("~~");
			String date[]=payDate.split("~~");
			String handles[]=handle.split("~~");
			String salenos[]=saleno.split("~~");
			for (int i = 0; i < menthod.length; i++) {
				sql="insert c_payment(refno,type,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx,staffname,location,saleno)values(?,?,?,?,?,?,?,?,?,?,?,?)";
				ps=con.prepareStatement(sql);
				ps.setString(1,order);
				ps.setString(2, type);
				ps.setString(3, menthod[i]);
				ps.setDouble(4, Double.parseDouble(amount[i]));
				ps.setString(5, date[i]);
				ps.setString(6, handles[i]);
				ps.setString(7, user);
				ps.setString(8, DateUtils.getNowDateTime());
				ps.setString(9, "Y");
				ps.setString(10, staffname);
				ps.setString(11, location);
				ps.setString(12, salenos[i]);
				num=ps.executeUpdate();
				if(num<1){
					throw new RuntimeException("写入Payment失败");
				}
			}
			con.commit();
			num=1;
		}catch (Exception e) {
			num=0;
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	

	public int ready(String order, String status, String user,String to,String location) {
		int num=-1;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			sql="update c_stationery_order set status=? where ordercode=? and sfyx='Y' and status='Submitted' ";
			ps=con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2, order);
			num=ps.executeUpdate();
			if(num<=0){
				throw new RuntimeException(order+"操作失败,只能操作的状态为Submitted.");
			}
			sql="insert c_stationery_operation(ordercode,operationType,operationName,operationDate)values('"+order+"','Ready','"+user+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<=0){
				throw new RuntimeException("写入记录失败");
			}
			System.out.println(location);
			String content=" Dear Sir / Madam,<br/>";
			if(location.equalsIgnoreCase("148 Electric Road") || location.equalsIgnoreCase("Peninsula")){
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your requested Stationery by internal transfer today.";	
			}else{
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested Stationery at (location: "+(location.equalsIgnoreCase("CP3")?"17/F":"40/F")+" Mailing Room)";
			}
			String signature="Best Regards,<br/>";
			signature+="Administration<br/>";
			signature+="Operations Department";
			
			String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
			JSONObject json=new JSONObject(result);
			if("error".equalsIgnoreCase(json.get("state")+"")){
				throw new RuntimeException((String)json.get("msg"));
			}
			
			con.commit();
			num=1;
			
		}catch (Exception e) {
			num=0;
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.error("Stationery Ready 数据回滚异常==="+e.getMessage());
			}
			logger.error("Stationery Ready 异常==="+e.getMessage());
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public Result downReportingForStationery(String startDate, String endDate) {
		
			Result rss=null;
			try{
				con=DBManager.getCon();
				String sql="select * from(select 'Month',orderdate,ordercode,clientcode,clientname,staffOrCons,"+
							" if(ct.paymentMethod='Cash',ct.paymentAount,'0.00') as Cash,"+
							" if(ct.paymentMethod='C-Club',ct.paymentAount,'0.00') as 'C-Club',"+
							" if(ct.paymentMethod='Octopus',ct.paymentAount,'0.00')as Octopus,"+
							" if(ct.paymentMethod='EPS',ct.paymentAount,'0.00') as EPS "+
							 " from c_stationery_order cso left join c_payment ct on(cso.ordercode=ct.refno) where cso.sfyx='Y' and DATE_FORMAT(cso.orderdate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and DATE_FORMAT(cso.orderdate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
				ps=con.prepareStatement(sql);
				ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
				ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			
				ResultSet rs=ps.executeQuery();
				rss=ResultSupport.toResult(rs);
				 rs.close();
			}catch (Exception e) {
				logger.error("导出AccessCard 异常=="+e.getMessage());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(con);
			}
			return rss;

	}

	 
	

	/**
	 * 获取Stationery
	 * @return
	 */
	public Result findStore(String startDate, String endDate){
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select * from (select procode,englishname,sum(quantity) as quantity from" +
					" c_stationery_product cs group by procode)a";
			ps=con.prepareStatement(sql);
			///ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			//ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("获取Marketing Premium 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
		
		
	}
	
	/**
	 * 获取产品使用量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result chargeStore(String startDate, String endDate){
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select ordercode,procode,quantity from c_stationery_consume_record where ordercode in("+
			"select ordercode from c_stationery_order co"+
			" where co.sfyx='Y' and co.priceAll>0 and DATE_FORMAT(co.orderdate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" DATE_FORMAT(co.orderdate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')) order by ordercode";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("Reporting==>获取Stationery 使用量 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
		
		
	}
	
	
	
	
}
