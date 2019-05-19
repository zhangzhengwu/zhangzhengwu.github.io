package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.SendMail;
import util.Util;
import dao.C_CompanyDao;
import dao.QueryMarkPermiumDao;
import entity.C_Companyasset;
import entity.C_CompanyassetDetail;
import entity.C_CompanyassetItem;
import entity.C_CompanyassetOperation;
import entity.C_EPayment_List;

/**
 * C_CompanyDaoImpl
 * @author Wilson
 * 2014-5-14 15:23:35
 * 
 */
public class C_CompanyDaoImpl implements C_CompanyDao {
	Logger logger = Logger.getLogger(C_CompanyDaoImpl.class);
	/**
	 * 保存C_SearDaoImpl表
	 */
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	DecimalFormat dFormat= new DecimalFormat("##.##");
	
	 /**
	  * 获取订单编号
	  * @return
	  */
	public String findref(){
		String num=null;
		try{
			con=DBManager.getCon();
			String sql="select count(*) from c_companyasset";
			ps=con.prepareStatement(sql);
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
				num="CA"+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public String complete(C_Companyasset cCompany ) {
		String refno="";
		try{
			//订单号生成
			 refno=findref();
			synchronized (this) {
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
			}
			cCompany.setRefno(refno);
			
			int num =  saveCompanyRecord(cCompany);
			if(num > 0){
				return refno;
			 }else{//保存消费记录表失败
				 logger.error("在保存Seat 订单详细时出现异常  ===REF.NO.："+refno);
				 return "Error";
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("save Seat Assignment出现异常  ===："+e);
		}finally{
			 
		}
		return refno;
	}
	public String completedetail(C_CompanyassetDetail cCompanyDetail ,String itemcode) {
		String refno="";
		try{
			 
			int num =  saveCompanyRecorddetail(cCompanyDetail,itemcode);
			if(num > 0){
				return "Your requested materials will be ready on the collection date. ADM will notify you by email.";
			}else{//保存消费记录表失败
				logger.error(itemcode+"在保存Seat 订单详细时出现异常  ===REF.NO.："+refno);
				return "Error";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("save Company Assignment出现异常  ===："+e);
		}finally{
			
		}
		return refno;
	}

	public int saveCompanyRecord(C_Companyasset cCompany) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_companyasset values(?,?,?,?,?,?,?,?,?,?,?,? )";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=(Connection) DBManager.getCon();
			pr=con.prepareStatement(sql);
			pr.setString(1, cCompany.getRefno());
			pr.setString(2, cCompany.getStaffcode() ); 
			pr.setString(3, cCompany.getStaffname()); 
			pr.setString(4, cCompany.getUserType());
			pr.setString(5, cCompany.getLocation());
			pr.setString(6, cCompany.getEventName());
			pr.setString(7, cCompany.getCollectionDate());
			pr.setString(8, cCompany.getReturnDate());
			pr.setString(9, cCompany.getCreator());
			pr.setString(10, cCompany.getCreateDate());
			pr.setString(11, cCompany.getStatus());
			pr.setString(12, cCompany.getSfyx());
			
			logger.info(cCompany.getRefno()+"在Company save成功！");
			num = pr.executeUpdate();
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存 Company异常："+e);
			return 0;
		}finally{
		}
		return num;
	}
	public int saveCompanyRecorddetail(C_CompanyassetDetail cCompany ,String itemcode) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_companyasset_detail(refno,itemcode,num) values(?,?,? )";
		logger.info("Company asset detail save_SQL:"+sql);
		try {
			con=(Connection) DBManager.getCon();
			con.setAutoCommit(false);
			pr=con.prepareStatement(sql);
			pr.setString(1, cCompany.getRefno());
			pr.setString(2, cCompany.getItemcode()); 
			pr.setInt(3, cCompany.getNum());
			num = pr.executeUpdate();
			
			String updsql="update c_companyasset_item set remainnum=remainnum-1 where itemcode='"+itemcode+"'";
			ps=con.prepareStatement(updsql);
			ps.execute();
			 
			con.commit();
			logger.info(cCompany.getRefno()+"在Company asset detail save成功！");
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存 Company asset detail异常："+e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}finally{
		}
		return num;
	}
	/**
	 * add方法COAT  操作记录
	 */
	public int saveCompanyOpreation(C_CompanyassetOperation seatOpreation) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_companyasset_operation (refno,operationType,operationName,operationDate) values(?,?,?,? )";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=(Connection) DBManager.getCon();
			con.setAutoCommit(false);
			pr=con.prepareStatement(sql);
			pr.setString(1, seatOpreation.getRefno());
			pr.setString(2, seatOpreation.getOperationType() ); 
			pr.setString(3, seatOpreation.getOperationName()); 
			pr.setString(4, seatOpreation.getOperationDate());
			 
			logger.info(seatOpreation.getRefno()+"在Company Operation save成功！");
			num = pr.executeUpdate();
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存Company Operation异常："+e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}finally{
		}
		return num;
	}
	/**
	 * down
	 */
	public List<C_Companyasset> downCompanyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status) {

		List<C_Companyasset> seatList=new ArrayList<C_Companyasset>();
		try{
			con=DBManager.getCon();
			String sql="select * from c_companyasset" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?" +
					" order by createDate desc ";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			rs=ps.executeQuery();
			while(rs.next()){
				seatList.add(new C_Companyasset(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"), 
						rs.getString("eventName"),
						rs.getString("collectionDate"),
						rs.getString("returnDate"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return seatList;
	
	}
	
	
	/**
	 * down
	 */
	public List<C_Companyasset> downCompanyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String collectionDate,String returnDate) {
		List<C_Companyasset> seatList=new ArrayList<C_Companyasset>();
		try{
			con=DBManager.getCon();
			String sql="select * from c_companyasset" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and collectionDate like ? and returnDate like ?"  +
					" order by createDate desc ";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+collectionDate+"%");
			ps.setString(8, "%"+returnDate+"%");
			rs=ps.executeQuery();
			while(rs.next()){
				seatList.add(new C_Companyasset(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"), 
						rs.getString("eventName"),
						rs.getString("collectionDate"),
						rs.getString("returnDate"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return seatList;
	
	}
	
	
	public List<C_Companyasset> findCompanyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status, Page page) {

		List<C_Companyasset> aList=new ArrayList<C_Companyasset>();
		try{
			con=DBManager.getCon();
			String sql="select refno,staffcode,staffname,userType,location,eventName,collectionDate,returnDate,creator,createDate,status,sfyx from c_companyasset" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?" +
					" order by createDate desc limit ?,?";
			ps=con.prepareStatement(sql);
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
				aList.add(new C_Companyasset(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"), 
						rs.getString("eventName"),
						rs.getString("collectionDate"),
						rs.getString("returnDate"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return aList;
	
	}

	
	public List<C_Companyasset> findCompanyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,String collectionDate,String returnDate,
			String status, Page page) {

		List<C_Companyasset> aList=new ArrayList<C_Companyasset>();
		try{
			con=DBManager.getCon();
			//System.out.println(startDate+"=-"
			//	+endDate+"-="+ staffcode+"=-"+staffname+"-="+refno+"=-"+ collectionDate+"-="+ returnDate+"=-"+status);
			String sql="select refno,staffcode,staffname,userType,location,eventName,collectionDate,returnDate,creator,createDate,status,sfyx from c_companyasset" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and collectionDate like ? and returnDate like ?" +
					" order by createDate desc limit ?,?";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+collectionDate+"%");
			ps.setString(8, "%"+returnDate+"%");
			ps.setInt(9, (page.getCurPage()-1)*page.getPageSize());
			ps.setInt(10, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				aList.add(new C_Companyasset(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"), 
						rs.getString("eventName"),
						rs.getString("collectionDate"),
						rs.getString("returnDate"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return aList;
	
	}
	
	public int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status) {
		int num=-1;
		try{
			con=DBManager.getCon();
			String sql="select count(*)as num from c_companyasset" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?";
			ps=con.prepareStatement(sql);
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
			DBManager.closeCon(con);
		}
		return num;
	}
	
	
	public int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status,String collectionDate,String returnDate) {
		int num=-1;
		try{
			con=DBManager.getCon();
			String sql="select count(*)as num from c_companyasset" +
					" where sfyx='Y' and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and collectionDate like ? and returnDate like ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+collectionDate+"%");
			ps.setString(8, "%"+returnDate+"%");
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt("num");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
 
	public int Ready(String refno, String type, String staffcode,String to) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_companyasset set status='"+type+"' where status='Submitted' and sfyx='Y' and refno='"+refno+"'";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException("操作Ready失败");
			}
			sql="insert c_companyasset_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			String content=" Dear Sir / Madam,<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested company asset at (location: 40/F Mailing Room).";
			
			String signature="Best Regards,<br/>";
			signature+="Administration<br/>";
			signature+="Operations Department";
			
			String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}
			
			con.commit();
			num=1;
		}catch (Exception e) {
			//e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int completed(String refno, String type, String staffcode,String to) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_companyasset set status='"+type+"' where status='Ready' and sfyx='Y' and refno='"+refno+"'";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException("操作Delivered失败");
			}
			sql="insert c_companyasset_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			String content=" Dear Sir / Madam,<br/>"+
			"&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested company asset at (location: 40/F Mailing Room).";
			
			String signature="Best Regards,<br/>";
			signature+="Administration<br/>";
			signature+="Operations Department";
			
			String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}
			
			con.commit();
			num=1;
		}catch (Exception e) {
			//e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public int returned(String refno, String type, String staffcode) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_companyasset set status='"+type+"' where status='Delivered' and sfyx='Y' and refno='"+refno+"';";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException("操作 Returned失败");
			}
			sql="insert c_companyasset_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException("操作变更新失败");
			}
			sql="update c_companyasset_detail a left join c_companyasset_item b on (a.itemcode=b.itemcode) "+ 
			"set b.remainnum=b.remainnum+1 where a.refno=? ;";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException("更新companyasset_detail失败");
			}
			con.commit();
			num=1;
		}catch (Exception e) {
			//e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public int deleted(String refno, String type, String staffcode) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_companyasset set status='"+type+"',sfyx='D' where sfyx='Y' and status='Submitted' and refno='"+refno+"';";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_companyasset_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			sql="update c_companyasset_detail a left join c_companyasset_item b on (a.itemcode=b.itemcode) "+ 
			"set b.remainnum=b.remainnum+1 where a.refno=? ;";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			con.commit();
			num=1;
		}catch (Exception e) {
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

	public C_Companyasset findCompanyByRef(String refno) {

		C_Companyasset cCompany=null;
		try{
			con=DBManager.getCon();
			String sql="select * from c_companyasset where refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				cCompany= new C_Companyasset(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"), 
						rs.getString("eventName"),
						rs.getString("collectionDate"),
						rs.getString("returnDate"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return cCompany;
	
	}
	public  List<C_CompanyassetItem> findCompanyBycode() {
		C_CompanyassetItem cCompany=null;
		 List<C_CompanyassetItem> cCompanylist= new ArrayList<C_CompanyassetItem>();;
		try{
			con=DBManager.getCon();
			String sql ="select itemId,itemcode,itemname,itemnum,remainnum,creator,createDate,sfyx from c_companyasset_item where sfyx='Y'";
			ps=con.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while (rs.next()){
				cCompany= new C_CompanyassetItem(
						rs.getInt("itemId"),
						rs.getString("itemcode"),
						rs.getString("itemname"),
						rs.getInt("itemnum"),
						rs.getInt("remainnum"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("sfyx"));
				cCompanylist.add(cCompany);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			DBManager.closeCon(con);
		}
		return cCompanylist;
	}
	public  List<C_CompanyassetItem> findCompanyBycode(String edate) {
		C_CompanyassetItem cCompany=null;
		List<C_CompanyassetItem> cCompanylist= new ArrayList<C_CompanyassetItem>();;
		try{
			con=DBManager.getCon();
			String sql =" select item.itemId,item.itemcode,item.itemname,item.itemnum,if(d.num>=item.itemnum,0,item.itemnum-(if(d.num is null,0,d.num))) remainnum,item.creator,item.createDate,item.sfyx from c_companyasset_item item "+ 
				" LEFT join ( select a.itemcode,count(*)as num FROM c_companyasset_detail a left JOIN c_companyasset b on(a.refno=b.refno)"+
				" where b.sfyx='Y' and  b.returnDate > ?  group by a.itemcode) d"+
				" on (item.itemcode= d.itemcode ) where item.sfyx='Y'";
			ps=con.prepareStatement(sql);
			ps.setString(1, edate);
			rs=ps.executeQuery();
			while (rs.next()){
				cCompany= new C_CompanyassetItem(
						rs.getInt("itemId"),
						rs.getString("itemcode"),
						rs.getString("itemname"),
						rs.getInt("itemnum"),
						rs.getInt("remainnum"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("sfyx"));
				cCompanylist.add(cCompany);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			DBManager.closeCon(con);
		}
		return cCompanylist;
	}
	public  List<C_CompanyassetDetail> findCompanydetailBycode(String refno) {
		C_CompanyassetDetail cCompanyd=null;
		List<C_CompanyassetDetail> cCompanylist= new ArrayList<C_CompanyassetDetail>();;
		try{
			con=DBManager.getCon();
			String sql ="select refno,cd.itemcode,itemname,num from c_companyasset_detail cd"+
						" LEFT JOIN c_companyasset_item ci on(cd.itemcode=ci.itemcode) where cd.refno=? ";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			while (rs.next()){
				cCompanyd= new C_CompanyassetDetail(
						rs.getString("refno"),
						rs.getString("itemcode"),
						rs.getString("itemname"),
						rs.getInt("num"));
				
				cCompanylist.add(cCompanyd);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			DBManager.closeCon(con);
		}
		return cCompanylist;
	}



	
	public Result downReportingForCompanyAsset(String startDate, String endDate) {
		
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select * from(select 'Month',createDate,refno,staffcode,staffname,userType,collectionDate,returnDate,eventname "+
					
						 " from c_companyasset cso where cso.sfyx='Y' and  DATE_FORMAT(cso.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and DATE_FORMAT(cso.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
			
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
		
			ResultSet rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出CompanyAsset 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;

}
public Result downReportingForCompanyAsset(String startDate, String endDate,String staffcode,String staffname, String refno,String status,String collectionDate,String returnDate) {
		
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select * from(select 'Month',createDate,refno,staffcode,staffname,userType,collectionDate,returnDate,eventname "+
					
						 " from c_companyasset cso where cso.sfyx='Y' and  DATE_FORMAT(cso.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and DATE_FORMAT(cso.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')" +
						 " and staffcode like ? and staffname like ? and refno like ? and status like ?" +
						 ")a";
			
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ResultSet rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出CompanyAsset 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;

}
 
	/**
	 * 获取CompanyAsset
	 * @return
	 */
	public Result findStore(String startDate, String endDate){
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select * from (select itemcode as procode,itemname as englishname,sum(itemnum ) as quantity from" +
					" c_companyasset_item cs group by itemcode)a";
			ps=con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("获取CompanyAsset异常=="+e.getMessage());
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
			String sql="select refno,itemcode,num from c_companyasset_detail where refno in("+
			"select refno from c_companyasset co"+
			" where sfyx='Y' and DATE_FORMAT(co.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" DATE_FORMAT(co.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')) order by refno,itemcode";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("获取CompanyAsset异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
		
		
	}
	/**
	 * 根据条件获取产品使用量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result chargeStore(String startDate, String endDate,String staffcode,String staffname, String refno,String status,String collectionDate,String returnDate){
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select refno,itemcode,num from c_companyasset_detail where refno in("+
			"select refno from c_companyasset co"+
			" where sfyx='Y' and DATE_FORMAT(co.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" DATE_FORMAT(co.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')" +
			" and staffcode like ? and staffname like ? and refno like ? and status like ?}" +
			" order by refno,itemcode";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ResultSet rs= null;
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("获取CompanyAsset异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
		
		
	}

	public int getAllRow(String Itemcode, String Itemname, String startDate,String endDate, String sfyx) {
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select * from c_companyasset_item where Itemcode like '%"+Itemcode+"%'");
			if(!Util.objIsNULL(Itemname)){
				sal.append(" and  Itemname like '%"+Itemname+"%'");
			}
			if(!Util.objIsNULL(sfyx)){
				sal.append(" and  sfyx like '%"+sfyx+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(createDate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(createDate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}
			//System.out.println("获取总行条数 ："+sal);
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询C_CompanyassetItem 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<C_CompanyassetItem> queryProduct(String Itemcode,String Itemname, String startDate, 
			String endDate, String sfyx,int currentPage, int pageSize) {
		
		List<C_CompanyassetItem> list=new ArrayList<C_CompanyassetItem>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_companyasset_item where Itemcode like '%"+Itemcode+"%'");
			if(!Util.objIsNULL(Itemname)){
				sal.append(" and  Itemname like '%"+Itemname+"%'");
			}
			if(!Util.objIsNULL(sfyx)){
				sal.append(" and  sfyx like '%"+sfyx+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(createDate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(createDate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			sal.append(" order by Itemcode desc  limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  C_CompanyassetItem  sql:===="+sal.toString());
			//System.out.println(sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_CompanyassetItem cl=new C_CompanyassetItem(
						rs.getInt("itemId"),
						rs.getString("itemcode"),
						rs.getString("itemname"),
						rs.getInt("itemnum"),
						rs.getInt("remainnum"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("sfyx"));
			
				list.add(cl);
				}
			//System.out.println("查询产品  ："+sal);
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询C_CompanyassetItem时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}

	public int saveCompanyAssetItem(List<List<Object>> list) {
		int num = 0;
		C_CompanyDao cDao=new C_CompanyDaoImpl();
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);//禁止提交事物
			for(int i=0;i<list.size();i++){
				List<Object> list2=list.get(i);

				String itemnums = Util.objIsNULL(list2.get(3).toString())?"0":list2.get(3).toString();
				String remainnums = Util.objIsNULL(list2.get(4).toString())?"0":list2.get(4).toString();
				
				String itemcode =(String)list2.get(1);
				String itemname = (String)list2.get(2);
				Integer itemnum = Integer.parseInt(itemnums);
				Integer remainnum= Integer.parseInt(remainnums);
				String creator  = (String)list2.get(5);
				String isactive  = (String)list2.get(7);
				
				String creators = Util.objIsNULL(creator)?"":creator;
				
				//code不为空 
				if (!Util.objIsNULL(itemcode) ) {
					num++;
					//更新标志为Y 则更新产品表中的数据
					if (isactive.trim().equals(Constant.YXBZ_Y)) {
						
						int acountnum = cDao.findAllByCode(itemcode); //判断数据是否存在
						
						C_CompanyDaoImpl dao=new  C_CompanyDaoImpl();
						String[] b=dao.findCode(itemcode);//根据itemcode查询数据库中的数据返回itemnum、remainnum
						
						String aa = Util.objIsNULL(b[0])?"0":b[0];	//数据库中itemnum的数量
						String bb = Util.objIsNULL(b[1])?"":b[1];	//数据库中remainnum的数量
						
						int numnum=Integer.parseInt(aa);
						int remain=Integer.parseInt(bb);
						
						//System.out.println("-------------->"+numnum+"---------->"+remain+"------>"+itemnum);
						//有记录存在 则更新
						if (acountnum > 0) {
							
							if(itemnum>=numnum){  //如果Excel中的itemnum数量大于数据库中的itemnum数量
								itemnum=numnum+(itemnum-numnum);
								remainnum=remain+(itemnum-numnum);
								
								String sql01="update c_companyasset_item set itemname='"+itemname+"',itemnum="+itemnum+",remainnum="+remainnum+",creator='"+creators+"',createDate='"+
								 DateUtils.getNowDateTime()+"',sfyx='"+isactive+"' where itemcode='"+itemcode+"' ";
								 ps=con.prepareStatement(sql01);
								 ps.executeUpdate();
								 logger.info("更新 c_companyasset_item！"+sql01);
							}else{
								int m=remain-(numnum-itemnum); //剩余量remainnum
								if(m>=0){
								remainnum=remain-(numnum-itemnum);
									String sql01="update c_companyasset_item set itemname='"+itemname+"',itemnum="+itemnum+",remainnum="+remainnum+",creator='"+creators+"',createDate='"+
									 DateUtils.getNowDateTime()+"',sfyx='"+isactive+"' where itemcode='"+itemcode+"' ";
									 ps=con.prepareStatement(sql01);
									 ps.executeUpdate();
									 logger.info("更新 c_companyasset_item！"+sql01);
								}else{
									logger.info(" c_companyasset_item procode 不允许添加！");
								}
							}
						
						}else {//无记录存在 则新增
							StringBuffer stringBuffer=new StringBuffer("insert into c_companyasset_item(itemcode," +"itemname,remainnum,creator,createDate,sfyx)"
									+" values('"+itemcode+"','"+itemname+"','"+itemnum+"','"+remainnum+"','"+creators+"','"+DateUtils.getNowDateTime()+"','"+
									isactive+"')");

							logger.info("save c_companyasset_item SQL:"+stringBuffer.toString());
							ps = con.prepareStatement(stringBuffer.toString());
							num = ps.executeUpdate();
							if(num < 0){
								logger.info("save c_companyasset_item信息表失败");
							}
						}
					}else if (isactive.trim().equals(Constant.YXBZ_D)) {
						//状态为D 则删除
						//int numa =aDao.delProduct(procode);
						String sql02 = "update c_companyasset_item set sfyx='N' where sfyx ='"+itemcode+"'";
						 ps=con.prepareStatement(sql02);
						 int numa=ps.executeUpdate();
						if (numa > 0) {
							logger.info("删除 c_companyasset_item！"+numa);
						}
					}
				} else {
					logger.info(" c_companyasset_item procode is null！");
				}
			}
			con.commit();//事物提交
		} catch (Exception e) {
			e.printStackTrace();
			try{
				con.rollback();//出异常时事物回滚
				num=0;
			}catch (Exception es) {
				es.printStackTrace();
			}
			logger.error("c_Mark_stock！" + e);
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	//判断itemcode是否存在
	public int findAllByCode(String itemcode) {
		int num=-1;
		Connection con=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select * from c_companyasset_item where itemcode ='"+itemcode+"'");
		
			logger.info("Query c_companyasset_item SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("c_companyasset_item 中根据条件查询数据个数时出现异常："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	//根据itemcode获取itemnum、remainnum
	public String[] findCode(String itemcode) {
		String []a=new String[2];
		Connection con=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select * from c_companyasset_item where itemcode ='"+itemcode+"'");
		
			logger.info("Query c_companyasset_item SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				a[0]=rs.getString("itemnum");
				a[1]=rs.getString("remainnum");
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("c_companyasset_item 中根据条件查询数据个数时出现异常："+e.toString());
			a[0]="";
			a[1]="";
		}finally{
			DBManager.closeCon(con);
		}
		return a;
	}
	
	
	/**
	 * ,String staffcode,String staffname, String refno,String status,String collectionDate,String returnDate
	 */
	

	
}
