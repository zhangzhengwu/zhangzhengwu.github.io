package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.SendMail;
import util.Util;
import dao.AccessCardDao;
import entity.CAttachment;
import entity.C_Access;
import entity.C_Adminservice;
import entity.C_Payment;

public class AccessCardDaoImpl implements AccessCardDao {
	Logger logger = Logger.getLogger(AccessCardDaoImpl.class);
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public int saveAccessCard(C_Access ca,String filenameAndPath) {
		int num=-1;
		try{
			synchronized (this) {
				String refno=findref();
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
				else
					ca.setRefno(refno);
			}
			
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="";
			if(ca.getPhotoSticker().equalsIgnoreCase("Y")){
				String name=filenameAndPath.substring(filenameAndPath.lastIndexOf("\\")+1,filenameAndPath.length());   
				//String path=filenameAndPath.substring(0,filenameAndPath.lastIndexOf("\\")+1);   
				
				sql="insert c_attachment(refno,staffcode,staffname,attachmentName,attachmentPath,creator,createDate,sfyx,item)" +
				"values('"+ca.getRefno()+"','"+ca.getStaffcode()+"','"+ca.getStaffname()+"','"+name+"', " +
						"'"+filenameAndPath+"','"+ca.getCreator()+"','"+DateUtils.getNowDateTime()+"','Y','AccessCard'); ";
				ps=conn.prepareStatement(sql);
				num=ps.executeUpdate();
				if(num<1){
					throw new RuntimeException();
				}
			}
			
			sql="insert c_access(refno,staffcode,staffname,userType,location,staffCard,photoSticker,reason,creator,createDate,status,sfyx)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,ca.getRefno());
			ps.setString(2,ca.getStaffcode());
			ps.setString(3, ca.getStaffname());
			ps.setString(4,ca.getUserType());
			ps.setString(5,ca.getLocation());
			ps.setString(6, ca.getStaffCard());
			ps.setString(7, ca.getPhotoSticker());
			ps.setString(8, ca.getReason());
			ps.setString(9, ca.getCreator());
			ps.setString(10, ca.getCreateDate());
			ps.setString(11,ca.getStatus());
			ps.setString(12, ca.getSfyx());
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			conn.commit();
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
		
		
	}
	
	
	 /**
	  * 获取订单编号
	  * @return
	  */
public String findref(){
	String num=null;
	try{
		conn=DBManager.getCon();
		String sql="select count(*) from c_access";
		ps=conn.prepareStatement(sql);
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
			num=Constant.ACCESSCARD+DateUtils.Ordercode()+num;
		}rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}
	
/**
 * 查询总条数
 */
public int getRow(String startDate, String endDate, String staffcode,
		String staffname, String refno, String status) {
	int num=-1;
	try{
		conn=DBManager.getCon();
		String sql="select count(*)as num from c_access" +
				" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
				" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
				" staffcode like ? and staffname like ? and refno like ? and status like ?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
		ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
		ps.setString(3, "%"+staffcode+"%");
		ps.setString(4, "%"+staffname+"%");
		ps.setString(5, "%"+refno+"%");
		ps.setString(6, "%"+status+"%");
		rs=ps.executeQuery();
		if(rs.next()){
			num=rs.getInt("num");
		}
		rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

/**
 * 分页查询
 */
public List<C_Access> findAccessList(String startDate,
		String endDate, String staffcode, String staffname, String refno,
		String status, Page page) {
	List<C_Access> c_AccessList=new ArrayList<C_Access>();
	try{
		conn=DBManager.getCon();
		String sql="select refno,staffcode,staffname,creator,createDate,status,reason,remark from c_access" +
				" where sfyx='Y' and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
				" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
				" staffcode like ? and staffname like ? and refno like ? and status like ?" +
				" order by createDate desc limit ?,?";
		ps=conn.prepareStatement(sql);
		
	    //System.out.println(sql);
		
		ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
		ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
		ps.setString(3, "%"+staffcode+"%");
		ps.setString(4, "%"+staffname+"%");
		ps.setString(5, "%"+refno+"%");
		ps.setString(6, "%"+status+"%");
		ps.setInt(7, (page.getCurPage()-1)*page.getPageSize());
		ps.setInt(8, page.getPageSize());
		rs=ps.executeQuery();
		while(rs.next()){
			c_AccessList.add(new C_Access(
					rs.getString("refno"),
					rs.getString("staffcode"),
					rs.getString("staffname"),
					rs.getString("reason"),
					rs.getString("creator"),
					rs.getString("createDate"),
					rs.getString("status"),
					rs.getString("remark")));
		}
		rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return c_AccessList;
}
/**
 * 查看详细
 * @param refno
 * @return
 */
public C_Access findAdminserviceByRef(String refno) {
	C_Access c_Access=null;
	try{
		conn=DBManager.getCon();
		String sql="select * from c_access where refno=?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, refno);
		rs=ps.executeQuery();
		if(rs.next()){
			c_Access=new C_Access(
					rs.getString("refno"),
					rs.getString("staffcode"),
					rs.getString("staffname"),
					rs.getString("userType"),
					rs.getString("location"),
					rs.getString("staffCard"),
					rs.getString("historyno"),
					rs.getString("newno"),
					rs.getString("photoSticker"),
					rs.getString("reason"),
					rs.getString("creator"),
					rs.getString("createDate"),
					rs.getString("status"),
					rs.getString("sfyx"),
					rs.getString("remark"));
		}
		rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return c_Access;
}

/**
 * 操作AccessCard Ready
 */
public int Ready(String refno, String type,String staffcode,String to,String localtion,String types,String remark) {
	int num=0;
	try{
		conn=DBManager.getCon();
		conn.setAutoCommit(false);
		String sql="update c_access set status='"+type+"',remark='"+remark+"' where status='HKADM' and sfyx!='D' and refno='"+refno+"'";
		ps=conn.prepareStatement(sql);
		num=ps.executeUpdate();
		if(num<1){
			throw new RuntimeException();
		}
		sql="insert c_access_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=conn.prepareStatement(sql);
		num=ps.executeUpdate();
		if(num<1){
			throw new RuntimeException();
		}
		String content="Dear Sir / Madam,<br/>";
		if(localtion=="148 Electric Road" || localtion=="Peninsula"){
			if(types=="staffcard")
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your re-issued access card by internal transfer today. Please send $50 handling charges OR return your damaged card to ADM in @CONVOY by internal transfer. ";
			else if(types=="photosticker")
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your photo sticker by internal transfer today. ";
			else
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your re-issued access card and photo sticker by internal transfer today. Please send $50 handling charges OR return your damaged card to ADM in @CONVOY by internal transfer. ";
		}else{
			if(types=="staffcard"){
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your re-issued access card with $50 handling charges at (location: "+(localtion=="CP3"?"17/F":"40/F")+" Mailing Room). OR exchange your card with your damaged card. ";
			}else{
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your photo sticker at "+(localtion=="CP3"?"17/F":"40/F")+" "+localtion+" Mailing Room";
			}
		}
		
		String signature="Best Regards,<br/>";
		signature+="Administration<br/>";
		signature+="Operations Department";
		
		String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
		JSONObject json=new JSONObject(result);
		if(json.get("state")=="error"){
			throw new RuntimeException((String)json.get("msg"));
		}
		
		conn.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

/**
 * 删除 AccessCard
 * @param refno
 * @param type
 * @param staffcode
 * @return
 */
public int Deleted(String refno, String type,String staffcode) {
	int num=0;
	try{
		conn=DBManager.getCon();
		conn.setAutoCommit(false);
		String sql="update c_access set status='"+type+"',sfyx='D' where status='Submitted' and sfyx!='D' and refno='"+refno+"'";
		ps=conn.prepareStatement(sql);
		num=ps.executeUpdate();
		if(num<1){
			throw new RuntimeException();
		}
		sql="insert c_access_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=conn.prepareStatement(sql);
		num=ps.executeUpdate();
		if(num<1){
			throw new RuntimeException();
		}
		
		conn.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

public int VOID(String refno, String type,String staffcode,String remark) {
	int num=0;
	try{
		conn=DBManager.getCon();
		conn.setAutoCommit(false);
		String sql="update c_access set status='"+type+"',remark='"+remark+"',status='VOID' where status!='Completed' and sfyx='Y' and refno='"+refno+"'";
		ps=conn.prepareStatement(sql);
		ps.execute();
		
		sql="insert c_access_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=conn.prepareStatement(sql);
		ps.execute();
		
		conn.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

/**
 * 操作AccessCard Completed
 */
public int completed(String refno, String type,String staffcode,C_Payment payment) {
	int num=0;
	try{
		conn=DBManager.getCon();
		conn.setAutoCommit(false);
		String sql="update c_access set status='"+type+"' where status='Ready' and sfyx!='D' and refno='"+refno+"'";
		 ps=conn.prepareStatement(sql);
		 ps.execute();
		
		 sql="insert c_access_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=conn.prepareStatement(sql);
		ps.execute();
		
		sql="insert c_payment(refno,type,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx,staffname,location,saleno)values(?,?,?,?,?,?,?,?,?,?,?,?)";
		ps=conn.prepareStatement(sql);
		ps.setString(1,refno);
		ps.setString(2, payment.getType());
		ps.setString(3, payment.getPaymentMethod());
		ps.setDouble(4, payment.getPaymentAount());
		ps.setString(5, payment.getPaymentDate());
		ps.setString(6, payment.getHandleder());
		ps.setString(7, staffcode);
		ps.setString(8, DateUtils.getNowDateTime());
		ps.setString(9, payment.getSfyx());
		ps.setString(10, payment.getStaffname());
		ps.setString(11, payment.getLocation());
		ps.setString(12, payment.getSaleno());
		ps.execute();
		conn.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

/**
 * 操作Access  HKADM
 */

public int modify(String refno, String type,String staffcode,String exitAccess,String newAccess,String remark) {
	int num=0;
	try{
		conn=DBManager.getCon();
		conn.setAutoCommit(false);
		String sql="update c_access set status='"+type+"',historyno='"+exitAccess+"',newno='"+newAccess+"',remark='"+remark+"' where refno='"+refno+"'";
		 ps=conn.prepareStatement(sql);
		 ps.execute();
		
		 sql="insert c_access_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=conn.prepareStatement(sql);
		ps.execute();
		
		 conn.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

/**
 * 导出AccessCard
 */
public Result downAccessList(String startDate, String endDate,
		String staffcode, String staffname, String refno, String status) {
		Result rss=null;
		try{
			conn=DBManager.getCon();
			String sql="SELECT ca.refno,staffcode,ca.staffname,staffcard,historyno,newno,photosticker,cp.paymentDate,cp.handleder,cp.paymentMethod,reason,Status,ca.createDate,cp.paymentAount,ca.remark"+ 
			" from c_access ca"+
			" left join c_payment cp on(ca.refno=cp.refno)" +
			" where  ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" staffcode like ? and ca.staffname like ? and ca.refno like ? and status like ?";
			
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出AccessCard 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return rss;
}


public Result downReportingForAccessCard(String startDate, String endDate) {
	Result rss=null;
	try{
		conn=DBManager.getCon();
		String sql="select * from(SELECT 'Month',ca.createDate,ca.refno,staffcode,ca.staffname,ca.userType,"+
					" if(cp.paymentMethod='Cash',cp.paymentAount,'0.00') as Cash,"+
					" if(cp.paymentMethod='Octopus',cp.paymentAount,'0.00') as Octopus,"+
					" if(cp.paymentMethod='EPS',cp.paymentAount,'0.00') as EPS,"+
					" if(staffcard='Y',1,'')as staffcard,"+
					" if(photosticker='Y',1,'') as photosticker,reason "+
					" from c_access ca"+
					" left join c_payment cp on(ca.refno=cp.refno) "+
					" where  ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
		
		ps=conn.prepareStatement(sql);
		ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
		ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);

		rs=ps.executeQuery();
		rss=ResultSupport.toResult(rs);
		 rs.close();
	}catch (Exception e) {
		logger.error("导出AccessCard 异常=="+e.getMessage());
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return rss;
}

	public String  findAttachment(String refno) {
		String  path=null;
		try{
			if(Util.objIsNULL(refno)){
				refno="";
			}
			conn=DBManager.getCon();
			String sql="select * from c_attachment where item='AccessCard' and refno='"+refno+"' order by createDate desc";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				path = rs.getString("attachmentPath");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return path;
	}


 












}
