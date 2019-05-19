package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.C_RoomSettingDao;
import entity.C_Payment;
import entity.C_Roomsetting;
import entity.C_RoomsettingOperation;
import entity.C_StationeryOperation;

/**
 * C_RoomSettingDaoImpl
 * add 2014年5月14日16:52:53
 * @author Sky
 * 
 */
public class C_RoomSettingDaoImpl implements C_RoomSettingDao {
	PreparedStatement ps = null;
	Connection connection = null;
	Logger logger = Logger.getLogger(C_RoomSettingDaoImpl.class);
	DecimalFormat dFormat = new DecimalFormat("##.##");
	/**
	 * RoomSetting 流水号前缀
	 */
	public static final String RoomSettingId="RS";
	/**
	 * 保存c_stationery_stock表
	 */
	public int saveRoomSetting(C_Roomsetting room) {
		int num=-1;
		String s = "";
		PreparedStatement psta = null;
		try {
			connection=DBManager.getCon();
			connection.setAutoCommit(false);//禁止自动提交事务
			
			//新增订单表
			s = "insert into c_roomsetting(refno,staffcode,staffname,userType,eventname,eventDate,startTime,endTime,convoy,CP3,remark,creator,createDate,status,sfyx) ";
			s += " values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?)";
			psta = connection.prepareStatement(s);
			psta.setString(1, room.getRefno());
			psta.setString(2, room.getStaffcode());
			psta.setString(3, room.getStaffname());
			psta.setString(4, room.getUserType());
			psta.setString(5, room.getEventname());
			psta.setString(6, room.getEventDate());
			psta.setString(7, room.getStartTime());
			psta.setString(8, room.getEndTime());
			psta.setString(9, room.getConvoy());
			psta.setString(10, room.getCp3());
			psta.setString(11, room.getRemark());
			psta.setString(12, room.getCreator());
			psta.setString(13, room.getStatus());
			psta.setString(14, room.getSfyx());
			logger.info("saveRoomSetting SQL:"+s);
			num = psta.executeUpdate();
			connection.commit();   //提交
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveRoomSetting 数据个数时出现：==="+e);
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
	public int getRoomcount(String eventName, String startDate,String endDate) {
		eventName = Util.objIsNULL(eventName)?"":eventName;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from " +
					" c_roomsetting where  sfyx='Y' ");
			if(!Util.objIsNULL(eventName)){
				sal.append(" and  eventname like '%"+eventName+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}

		return num;
	}
	/**
	 * 优化后查询分页条数方法
	 */
	public int getRoomcount(String eventDate,String userType,String staffcode,String staffname,String refno, String startDate,String endDate,String status) {
		eventDate = Util.objIsNULL(eventDate)?"":eventDate;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from " +
					" c_roomsetting where  sfyx='Y' ");
			if(!Util.objIsNULL(eventDate)){
				sal.append(" and  eventDate like '%"+eventDate+"%'");
			}
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and  staffcode like '%"+staffcode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  userType like '%"+userType+"%'");
			}
			if(!Util.objIsNULL(refno)){
				sal.append(" and  refno like '%"+refno+"%'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status like '%"+status+"%'");
			}
			if(!Util.objIsNULL(eventDate)){
				sal.append(" and  eventDate like '%"+eventDate+"%'");
			}
			
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(createDate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(createDate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			//System.out.println(sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}

		return num;
	}
	
	
	/**
	 * 优化后分页查询
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
	public List<C_Roomsetting> queryRoomList(String eventDate,String userType,String staffcode,String staffname,String refno,
			String startDate, String endDate, String status,int pageSize, int currentPage) {
		eventDate = Util.objIsNULL(eventDate)?"":eventDate;
		List<C_Roomsetting> list=new ArrayList<C_Roomsetting>();
		try{
			StringBuffer sal=new StringBuffer("select refno,staffcode,staffname,userType,eventname,eventDate,startTime,endTime,convoy,CP3,remark,creator,createDate,status,sfyx " +
					" from c_roomsetting where sfyx='Y' ");
			if(!Util.objIsNULL(eventDate)){
				sal.append(" and  eventDate like '%"+eventDate+"%'");
			}
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and  staffcode like '%"+staffcode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  userType like '%"+userType+"%'");
			}
			if(!Util.objIsNULL(refno)){
				sal.append(" and  refno like '%"+refno+"%'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status like '%"+status+"%'");
			}
			if(!Util.objIsNULL(eventDate)){
				sal.append(" and  eventDate like '%"+eventDate+"%'");
			}
			
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(createDate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(createDate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			sal.append("order by createDate desc limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询c_roomsetting   sql:===="+sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_Roomsetting es =new C_Roomsetting(
						rs.getString("refno"), 
						rs.getString("staffcode"), 
						rs.getString("staffname"), 
						rs.getString("userType") ,
						rs.getString("eventname"), 
						rs.getString("eventDate") , 
						rs.getString("startTime"), 
						rs.getString("endTime") ,
						rs.getString("convoy") , 
						rs.getString("CP3"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	
	
	
	
	
	
	
	public List<C_Roomsetting> queryRoomList(String eventName,
			String startDate, String endDate, int pageSize, int currentPage) {
		eventName = Util.objIsNULL(eventName)?"":eventName;
		List<C_Roomsetting> list=new ArrayList<C_Roomsetting>();
		try{
			StringBuffer sal=new StringBuffer("select refno,staffcode,staffname,userType,eventname,eventDate,startTime,endTime,convoy,CP3,remark,creator,createDate,status,sfyx " +
					" from c_roomsetting where sfyx='Y' ");
			if(!Util.objIsNULL(eventName)){
				sal.append(" and  eventname like '%"+eventName+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			sal.append("order by createDate desc limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询c_roomsetting   sql:===="+sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_Roomsetting es =new C_Roomsetting(
						rs.getString("refno"), 
						rs.getString("staffcode"), 
						rs.getString("staffname"), 
						rs.getString("userType") ,
						rs.getString("eventname"), 
						rs.getString("eventDate") , 
						rs.getString("startTime"), 
						rs.getString("endTime") ,
						rs.getString("convoy") , 
						rs.getString("CP3"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	public int delRoomSetting(String refno) {
		//eventName = Util.objIsNULL(eventName)?"":eventName;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("update c_roomsetting set sfyx='N',status='Deleted' where refno = '"+refno+"'");
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}

		return num;
	}
	/**
	  * 获取流水号
	  * @return
	  */
	public String findref(){
		String num=null;
		ResultSet rs= null;
		try{
			connection=DBManager.getCon();
			String sql="select count(*) from c_roomsetting";
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getString(1);
				if(rs.getInt(1)<9){
					num="000"+(rs.getInt(1)+1);
				}else if(rs.getInt(1)<99){
					num="00"+(rs.getInt(1)+1);
				}else if(rs.getInt(1)<999){
					num="0"+(rs.getInt(1)+1);
				}else{
					num=""+(rs.getInt(1)+1);
				}
				num=RoomSettingId+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	public List<C_Roomsetting> getRoomSetting(String refno) {
		refno = Util.objIsNULL(refno)?"":refno;
		List<C_Roomsetting> list=new ArrayList<C_Roomsetting>();
		try{
			StringBuffer sal=new StringBuffer("select refno,staffcode,staffname,userType,eventname,eventDate,startTime,endTime,convoy,CP3,remark,creator,createDate,status,sfyx " +
					" from c_roomsetting where sfyx='Y' ");
			if(!Util.objIsNULL(refno)){
				sal.append(" and refno = '"+refno+"'");
			}
			logger.info("查询c_roomsetting   sql:===="+sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_Roomsetting es =new C_Roomsetting(
						rs.getString("refno"), 
						rs.getString("staffcode"), 
						rs.getString("staffname"), 
						rs.getString("userType") ,
						rs.getString("eventname"), 
						rs.getString("eventDate") , 
						rs.getString("startTime"), 
						rs.getString("endTime") ,
						rs.getString("convoy") , 
						rs.getString("CP3"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	public int changeStatus(String refno,String status) {
		refno = Util.objIsNULL(refno)?"":refno;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("update c_roomsetting set status='"+status+"' where refno = '"+refno+"'");
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}

		return num;
	}
	public int saveRoomSettingOperation(C_RoomsettingOperation oper) {
		int num=-1;
		String s = "";
		PreparedStatement psta = null;
		try {
			connection=DBManager.getCon();
			connection.setAutoCommit(false);//禁止自动提交事务
			
			//新增订单表
			s = "insert into c_roomsetting_operation(refno,operationType,operationName,operationDate) ";
			s += " values(?,?,?,sysdate())";
			psta = connection.prepareStatement(s);
			psta.setString(1, oper.getRefno());
			psta.setString(2, oper.getOperationType());
			psta.setString(3, oper.getOperationName());
			logger.info("saveRoomSettingOperation SQL:"+s);
			num = psta.executeUpdate();
			connection.commit();   //提交
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveRoomSettingOperation 数据个数时出现：==="+e);
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
	public int getRoomSettingByName(String eventName) {
		eventName = Util.objIsNULL(eventName)?"":eventName;
		int num = 0;
		try{
			StringBuffer sal=new StringBuffer("select refno,eventname,eventDate " +
					" from c_roomsetting where 1=1 ");
			if(!Util.objIsNULL(eventName)){
				sal.append(" and eventname = '"+eventName+"'");
			}
			logger.info("查询c_roomsetting   sql:===="+sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				num = 1;
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	public int changeStationeryStatus(String ordercode, String status) {
		ordercode = Util.objIsNULL(ordercode)?"":ordercode;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("update c_stationery_order set status='"+status+"' where ordercode = '"+ordercode+"'");
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_stationery_order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}

		return num;
	}	
	public int saveStationeryOperation(C_StationeryOperation oper) {
		int num=-1;
		String s = "";
		PreparedStatement psta = null;
		try {
			connection=DBManager.getCon();
			connection.setAutoCommit(false);//禁止自动提交事务
			
			//新增订单表
			s = "insert into c_stationery_operation(ordercode,operationType,operationName,operationDate) ";
			s += " values(?,?,?,sysdate())";
			psta = connection.prepareStatement(s);
			psta.setString(1, oper.getOrdercode());
			psta.setString(2, oper.getOperationType());
			psta.setString(3, oper.getOperationName());
			logger.info("saveStationeryOperation SQL:"+s);
			num = psta.executeUpdate();
			connection.commit();   //提交
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveStationeryOperation 数据个数时出现：==="+e);
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
	
	public List<C_Roomsetting> queryRoomList(String eventDate,String userType,String staffcode,String staffname,String refno,String status,
			String startDate, String endDate) {
		eventDate = Util.objIsNULL(eventDate)?"":eventDate;
		List<C_Roomsetting> list=new ArrayList<C_Roomsetting>();
		try{
			StringBuffer sal=new StringBuffer("select refno,staffcode,staffname,userType,eventname,eventDate,startTime,endTime,convoy,CP3,remark,creator,createDate,status,sfyx " +
					" from c_roomsetting where sfyx='Y' ");
			if(!Util.objIsNULL(eventDate)){
				sal.append(" and  eventDate like '%"+eventDate+"%'");
			}
			
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and  staffcode like '%"+staffcode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  userType like '%"+userType+"%'");
			}
			if(!Util.objIsNULL(refno)){
				sal.append(" and  refno like '%"+refno+"%'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status like '%"+status+"%'");
			}
			
			
			
			
			
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			logger.info("查询c_roomsetting   sql:===="+sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_Roomsetting es =new C_Roomsetting(
						rs.getString("refno"), 
						rs.getString("staffcode"), 
						rs.getString("staffname"), 
						rs.getString("userType") ,
						rs.getString("eventname"), 
						rs.getString("eventDate") , 
						rs.getString("startTime"), 
						rs.getString("endTime") ,
						rs.getString("convoy") , 
						rs.getString("CP3"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	
	
	
	public List<C_Roomsetting> queryRoomList(String eventName,
			String startDate, String endDate) {
		eventName = Util.objIsNULL(eventName)?"":eventName;
		List<C_Roomsetting> list=new ArrayList<C_Roomsetting>();
		try{
			StringBuffer sal=new StringBuffer("select refno,staffcode,staffname,userType,eventname,eventDate,startTime,endTime,convoy,CP3,remark,creator,createDate,status,sfyx " +
					" from c_roomsetting where  sfyx='Y'  ");
			if(!Util.objIsNULL(eventName)){
				sal.append(" and  eventname like '%"+eventName+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(eventDate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			logger.info("查询c_roomsetting   sql:===="+sal.toString());
			connection=DBManager.getCon();
			ps = connection.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_Roomsetting es =new C_Roomsetting(
						rs.getString("refno"), 
						rs.getString("staffcode"), 
						rs.getString("staffname"), 
						rs.getString("userType") ,
						rs.getString("eventname"), 
						rs.getString("eventDate") , 
						rs.getString("startTime"), 
						rs.getString("endTime") ,
						rs.getString("convoy") , 
						rs.getString("CP3"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_roomsetting时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	public int savePayment(C_Payment payment) {
		int num=-1;
		String s = "";
		PreparedStatement psta = null;
		try {
			connection=DBManager.getCon();
			connection.setAutoCommit(false);//禁止自动提交事务
			
			//新增订单表
			s = "insert into c_payment(refno,type,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx) ";
			s += " values(?,?,?,?,?,?,?,sysdate(),?)";
			psta = connection.prepareStatement(s);
			psta.setString(1, payment.getRefno());
			psta.setString(2, payment.getType());
			psta.setString(3, payment.getPaymentMethod());
			psta.setDouble(4, payment.getPaymentAount());
			psta.setString(5, payment.getPaymentDate());
			psta.setString(6, payment.getHandleder());
			psta.setString(7, payment.getCreator());
			psta.setString(8, payment.getSfyx());
			logger.info("saveC_Payment SQL:"+s);
			num = psta.executeUpdate();
			connection.commit();   //提交
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("saveC_Payment 数据个数时出现：==="+e);
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
	
	public Result downReportingForRoomSetting(String startDate, String endDate) {
		Result rss=null;
		try{
			connection=DBManager.getCon();
			String sql="select * from(SELECT 'Month',ca.createDate,ca.refno,staffcode,staffname,ca.userType,"+
						" if(convoy!='',1,'')as convoy,"+
						" if(CP3!='',1,'') as CP3" +
						" from c_roomsetting ca"+
						" where  ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
						" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
			
			ps=connection.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出ADMService 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return rss;
	}
	
	
	public Result getProductList(){
		Result rss=null;
		try{
			//Map<String, String> proMap=new HashMap<String, String>();
			connection=DBManager.getCon();
			String sql="select mediacode,mediatype,medianame from c_recruitment_list order by mediatype desc,adddate";
			
			ps=connection.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("获取Epayment产品列表 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return rss;
		
	}
	
	public Result getAdvertising(String startDate,String endDate){
		Result rss=null;
		try{
			//Map<String, String> proMap=new HashMap<String, String>();
			connection=DBManager.getCon();
			String sql="select * from (select co.createdate,co.refno,staffcode,staffname,usertype,cd.mediacode from c_recruitment_order co "+
						"left join c_recruitment_detail cd on co.refno=cd.refno where co.sfyx='Y' and  DATE_FORMAT(co.createdate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
						" DATE_FORMAT(co.createdate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')  ) a ";
			
			ps=connection.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("获取Advertising订单列表 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return rss;
		
	}	
	public Result getepayment(String startDate,String endDate){
		Result rss=null;
		try{
			//Map<String, String> proMap=new HashMap<String, String>();
			connection=DBManager.getCon();
			String sql="select * from(select ca.createdate,ca.refno,ca.staffcode,ca.staffname,usertype," +
					" if(cp.paymentMethod='Cash',cp.paymentAount,'0.00') as Cash,"+
					" if(cp.paymentMethod='C-Club',cp.paymentAount,'0.00') as Club,"+
					" if(cp.paymentMethod='Octopus',cp.paymentAount,'0.00') as Octopus,"+
					" if(cp.paymentMethod='EPS',cp.paymentAount,'0.00') as EPS"+
					" from c_epayment_order ca"+
					" left join c_payment cp on(ca.refno=cp.refno) "+
					" where ca.sfyx='Y' and DATE_FORMAT(ca.createdate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and"+
					" DATE_FORMAT(ca.createdate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
			
			ps=connection.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			rs.close();
		}catch (Exception e) {
			logger.error("获取epayment订单列表 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return rss;
		
	}	
	
	
	
	
}
