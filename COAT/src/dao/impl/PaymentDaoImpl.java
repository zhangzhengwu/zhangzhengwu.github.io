package dao.impl;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Page;
import util.Util;

import dao.PaymentDao;
import entity.C_Payment;

public class PaymentDaoImpl implements PaymentDao{
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(PaymentDaoImpl.class);
	/**
	 * 根据流水号查询Payment信息
	 */
	public C_Payment findBYRef(String refno) {
		C_Payment cp=null;
		try{
			con=DBManager.getCon();
			sql="select * from c_payment where refno=? and sfyx='Y'";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				cp=new C_Payment(
						rs.getString("refno"),
						rs.getString("type"),
						rs.getString("paymentMethod"),
						
						rs.getDouble("paymentAount"),
						rs.getString("paymentDate"),
						rs.getString("handleder"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("sfyx"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("saleno"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return cp;
	}
	
	
	/**
	 * 查询总条数
	 */
	public int getRow(String startDate, String endDate, String requestType) {
		int num=-1;
		try{
			con=DBManager.getCon();
			String sql="select count(*)as num from c_payment" +
					" where DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" sfyx='Y' and type like ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+requestType+"%");
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
	
	
	
	public List<C_Payment> findAccessList(String startDate,
			String endDate, String requestType, Page page) {
		List<C_Payment> c_PaymentList=new ArrayList<C_Payment>();
		try{
			con=DBManager.getCon();
			String sql="select * from c_payment" +
					" where DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					"  sfyx='Y' and type like ?" +
					" order by createDate desc limit ?,?";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+requestType+"%");
			ps.setInt(4, (page.getCurPage()-1)*page.getPageSize());
			ps.setInt(5, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				c_PaymentList.add(new C_Payment(
						rs.getString("refno"),
						rs.getString("type"),
						rs.getString("paymentMethod"),
						
						rs.getDouble("paymentAount"),
						rs.getString("paymentDate"),
						rs.getString("handleder"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("sfyx"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("saleno")));
				
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return c_PaymentList;
		
	}
	
	/**
	 * 导出Payment
	 */
	public Result downPaymentList(String startDate, String endDate,String requestType) {
			Result rss=null;
			try{
				con=DBManager.getCon();
			//	String sql="select * from c_payment " +
				String sql="select cp.refno,staffcode,cp.staffname,location,type,paymentMethod,saleno,paymentAount,Handleder,paymentDate from c_payment cp"
							+" LEFT JOIN("
							+" select ordercode as refno,clientcode as staffcode from c_mar_order"
							+" union"
							+" select ordercode as refno,clientcode as staffcode from c_stationery_order"
							+" union" 
							+" select refno,staffcode from c_access"
							+" union"
							+" select refno,staffcode from c_keys"
							+" union"
							+" select refno,staffcode from c_recruitment_order"
							+" union"
							+" select refno,staffcode from c_epayment_order"
							
							
							+" )b on(cp.refno=b.refno) "
							+" where cp.sfyx='Y' and cp.refno is not null and cp.paymentAount>0 and DATE_FORMAT(cp.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" 
							+" DATE_FORMAT(cp.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" 
							+"  cp.type like ?";
				//System.out.println(sql);
				ps=con.prepareStatement(sql);
				ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
				ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
				ps.setString(3, "%"+requestType+"%");
				rs=ps.executeQuery();
				rss=ResultSupport.toResult(rs);
				 rs.close();
			}catch (Exception e) {
				logger.error("导出Payment 异常=="+e.getMessage());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(con);
			}
			return rss;
	}
	
	
	
	public Map<String, String> findEpaymentList(String startDate,String endDate,String requestType){
		//List<C_Epayment_Detail> list=new ArrayList<C_Epayment_Detail>();
		Map<String, String> EpaymentMap=new HashMap<String,String>();
		try{
			con=DBManager.getCon();
			sql="select cp.refno,cd.productcode,cd.productname from c_payment cp left join C_epayment_detail cd on(cp.refno=cd.refno) "
				+" where cp.type='epayment' and DATE_FORMAT(cp.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" 
				+" DATE_FORMAT(cp.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" 
				+" cp.sfyx='Y' and cp.type like ?";
			ps=con.prepareStatement(sql);
			//System.out.println(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+requestType+"%");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				//EpaymentMap.put(rs.getString("refno"), rs.getString("productcode")+";~;"+rs.getString("productname"));
				EpaymentMap.put(rs.getString("refno"), rs.getString("productname"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return EpaymentMap;
	}
	
	
}
