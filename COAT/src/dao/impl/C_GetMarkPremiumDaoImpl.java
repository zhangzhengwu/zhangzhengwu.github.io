package dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.SendMail;
import util.Util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.C_GetMarkPremiumDao;
import dao.QueryMarkPermiumDao;
import entity.C_EPayment_List;
import entity.C_Payment;
import entity.C_marOrder;
import entity.C_marProduct;
import entity.C_marRecord;

/**
 * Promotion
 * 
 * @author Wilson
 * 
 */
public class C_GetMarkPremiumDaoImpl implements C_GetMarkPremiumDao {

	Logger logger = Logger.getLogger(C_GetMarkPremiumDaoImpl.class);
	/**
	 * 保存c_MarkPermium_stock表
	 */
	Connection con = null;
	PreparedStatement ps = null;
	String sql = "";
	DecimalFormat dFormat= new DecimalFormat("##.##");
	public int saveMarkPremium(String filename, InputStream os) {
		
		int num = 0;
		int beginRowIndex = 2;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		//此处不做删除
		//Util.deltables("promotion_c_report");
		QueryMarkPermiumDao aDao  = new QueryMarkPremiumDaoImpl();
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据* */
				HSSFCell codecell = row.getCell(0);
				HSSFCell enamecell = row.getCell(1);
				HSSFCell cnamecell = row.getCell(2);
				HSSFCell unitcell = row.getCell(3);
				HSSFCell unitpricecell = row.getCell(4);
				HSSFCell cclubpricecell = row.getCell(5);
				HSSFCell specialpricecell = row.getCell(6);
				HSSFCell numcell = row.getCell(7);
				HSSFCell isactivecell = row.getCell(8);
			 
				/** 给数据库里面的字段赋值* */
				String procode = Util.cellToString(codecell);
				String ename = Util.cellToString(enamecell);
				String cname = Util.cellToString(cnamecell);
				String unit = Util.cellToString(unitcell);
				String unitprice  = Util.cellToString(unitpricecell);
				String cclubprice  = Util.cellToString(cclubpricecell);
				String specialprice  = Util.cellToString(specialpricecell);
				String numc  = Util.cellToString(numcell);
				String isactive = Util.cellToString(isactivecell);
				 
				String unitpricenum = Util.objIsNULL(unitprice)?"0":unitprice;
				String cclubpricenum = Util.objIsNULL(cclubprice)?"":cclubprice;
				String specialpricenum = Util.objIsNULL(specialprice)?"":specialprice;
				String numcnum = Util.objIsNULL(numc)?"0":numc;
				//code不为空 
				if (!Util.objIsNULL(procode) ) {
					String club = Util.objIsNULL(cclubpricenum)?"0":cclubpricenum;
					sql = "insert c_mar_stock(procode,englishname,chinesename,unit,unitprice,c_clubprice,specialprice" +
							",quantity,adddate) values('"+
							procode+"','"+ename+"','" + 
							cname+"','"+unit+"',"+
							unitpricenum+",'"+club+"','"+
							specialpricenum+"'," +numcnum+ ",'"+ 
							DateUtils.getNowDateTime()+"')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						num++;
						logger.info("保存 c_mar_stock OK ！");
						//办理标志
						String blbz = Constant.BLBZ_A;
						//更新标志为Y 则更新产品表中的数据
						if (isactive.trim().equals(Constant.YXBZ_Y)) {
							
							int acountnum = aDao.findAllByCode(procode);
							
							//如果C-CLUB价格为空 则跟回原价
							if (Util.objIsNULL(cclubpricenum)) {
								cclubpricenum = unitpricenum;
							}
							//有记录存在 则更新
							if (acountnum > 0) {
								int updnum =aDao.updProduct(procode, ename, cname, new Double(unitpricenum),new Double(cclubpricenum),specialpricenum, unit, new Double(numcnum), blbz);
								logger.info("更新 c_Mar_product！"+updnum);
							
							}else {//无记录存在 则新增
								int addnum = aDao.saveProduct(procode, ename, cname, new Double(unitpricenum),new Double(cclubpricenum),specialpricenum, unit, new Double(numcnum), blbz);
								logger.info("新增 c_Mar_product！"+addnum);
							}
						}else if (isactive.trim().equals(Constant.YXBZ_D)) {
							//状态为D 则删除
							
							int numa =aDao.delProduct(procode);
							if (numa > 0) {
								logger.info("删除 c_Mar_product！"+numa);
							
							}
						}
						
					} else {
						logger.info("保存 c_Mar_stock 为空！");
					}
					
				} else {
					logger.info("c_Mar_stock procode is null！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("c_Mark_stock！" + e);
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 	   Auto-generated method stub
	 */
	public List<C_marProduct> findMarProduct() {
		List<C_marProduct> list=new ArrayList<C_marProduct>();
		try{
			String sql="select * from c_mar_product where BLBZ=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, Constant.BLBZ_A);
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(new C_marProduct(rs.getString("procode"),
						rs.getString("englishname"),
						rs.getString("chinesename"), 
						rs.getString("unitprice"), 
						rs.getString("c_clubprice"), 
						rs.getString("specialprice"), 
						rs.getString("unit"),
						rs.getString("quantity"), 
						rs.getString("blbz"),
						rs.getString("updates"), 
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"), 
						rs.getString("remark4")));
		
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 * Auto-generated method stub
	 */
	public List<C_marProduct> findMarProduct(String BLBZ) {
		List<C_marProduct> list=new ArrayList<C_marProduct>();
		try{
			String sql="select * from c_mar_product where BLBZ='A' or BLBZ=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, BLBZ);
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(new C_marProduct(rs.getString("procode"),
						rs.getString("englishname"),
						rs.getString("chinesename"), 
						rs.getString("unitprice"), 
						rs.getString("c_clubprice"), 
						rs.getString("specialprice"), 
						rs.getString("unit"),
						rs.getString("quantity"), 
						rs.getString("blbz"),
						rs.getString("updates"), 
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"), 
						rs.getString("remark4")));
				
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	/**
	 * 删除订单编号并更新产品库存
	 */
	public int delMarkOrder(String ordercode) {
		int r = -1;
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try {
			 con=DBManager.getCon();
			 con.setAutoCommit(false);//禁止自动提交
			 String sql="update c_mar_order set sfyx='"+Constant.YXBZ_N+"',status='"+Constant.C_Deleted+"',remark1='"+DateUtils.getNowDateTime()+"' where ordercode='"+ordercode+"' ";
			 logger.info("upd c_mar_order信息表SQL:"+sql);
			 
			 ps=con.prepareStatement(sql);
			r=ps.executeUpdate();
			
		sql="select * from c_mar_record where ordercode='"+ordercode+"'";
		
		ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			list.add(new C_marRecord(ordercode,
					rs.getString("procode"),
					rs.getString("proname"), 
					rs.getString("calculation"),
					rs.getDouble("price"), 
					rs.getDouble("quantity"),
					rs.getDouble("priceall"), 
					rs.getString("remark1"),
					rs.getString("remark2"),
					rs.getString("remark3"),
					rs.getString("remark4")));
		}
			sql="update c_mar_product set quantity=quantity+? where procode=?";
			ps=con.prepareStatement(sql);
			for(int i=0;i<list.size();i++){
				C_marRecord cm=list.get(i);
				ps.setDouble(1,cm.getQuantity());
				ps.setString(2, cm.getProcode());
				ps.addBatch();
			}
			ps.executeBatch();
			rs.close();
			con.commit();
			
			if(r<0){
				logger.info("upd c_mar_order信息表失败");
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			try {
				logger.error("upd c_mar_order信息表数据回滚！"+e);
				con.rollback();
				
			} catch (SQLException e1) {
				logger.error("upd c_mar_order信息表数据回滚异常！"+e);
				e1.printStackTrace();
			}
			logger.error("upd c_mar_order信息表异常！"+e);
		} finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	
	
	/**
	 * 改版后VOID订单编号并更新产品库存
	 */
	public int VOID(String ordercode,String user) {
		int r = -1;
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try {
			 con=DBManager.getCon();
			 con.setAutoCommit(false);//禁止自动提交
			 String sql="update c_mar_order set sfyx='N',status='VOID',remark1='"+DateUtils.getNowDateTime()+"' where ordercode='"+ordercode+"' ";
			 logger.info("VOID==>upd c_mar_order信息表SQL:"+sql);
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			
			
			 sql="insert c_mar_operation(refno,operationType,operationName,operationDate)values('"+ordercode+"','VOID','"+user+"','"+DateUtils.getNowDateTime()+"');";
			 logger.info("VOID==>upd c_mar_operation信息表SQL:"+sql);	
			 ps=con.prepareStatement(sql);
			ps.execute();
			
		sql="select * from c_mar_record where ordercode='"+ordercode+"'";
		logger.info("VOID==>upd c_mar_record信息表SQL:"+sql);
		ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			list.add(new C_marRecord(ordercode,
					rs.getString("procode"),
					rs.getString("proname"), 
					rs.getString("calculation"),
					rs.getDouble("price"), 
					rs.getDouble("quantity"),
					rs.getDouble("priceall"), 
					rs.getString("remark1"),
					rs.getString("remark2"),
					rs.getString("remark3"),
					rs.getString("remark4")));
		}
			sql="update c_mar_product set quantity=quantity+? where procode=?";
			logger.info("VOID==>upd c_mar_product信息表SQL:"+sql);
			ps=con.prepareStatement(sql);
			for(int i=0;i<list.size();i++){
				C_marRecord cm=list.get(i);
				ps.setDouble(1,cm.getQuantity());
				ps.setString(2, cm.getProcode());
				ps.addBatch();
			}
			ps.executeBatch();
			rs.close();
			con.commit();
			
			if(r<0){
				logger.info("VOID c_mar_order信息表失败");
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			r=0;
			try {
				logger.error("VOID c_mar_order信息表数据回滚！"+e);
				con.rollback();
				
			} catch (SQLException e1) {
				logger.error("VOID c_mar_order信息表数据回滚异常！"+e);
				e1.printStackTrace();
			}
			logger.error("VOID c_mar_order信息表异常！"+e);
		} finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	
	
	
	/**
	 * 改版后删除订单编号并更新产品库存
	 */
	public int delMarkOrder(String ordercode,String user) {
		int r = -1;
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try {
			 con=DBManager.getCon();
			 con.setAutoCommit(false);//禁止自动提交
			 String sql="update c_mar_order set sfyx='"+Constant.YXBZ_N+"',status='"+Constant.C_Deleted+"',remark1='"+DateUtils.getNowDateTime()+"' where sfyx='Y' and status='Submitted' and ordercode='"+ordercode+"' ";
			 logger.info("Delete==>upd c_mar_order信息表SQL:"+sql);
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			
			
			 sql="insert c_mar_operation(refno,operationType,operationName,operationDate)values('"+ordercode+"','"+Constant.C_Deleted+"','"+user+"','"+DateUtils.getNowDateTime()+"');";
			 logger.info("Delete==>upd c_mar_operation信息表SQL:"+sql);	
			 ps=con.prepareStatement(sql);
			ps.execute();
			
		sql="select * from c_mar_record where ordercode='"+ordercode+"'";
		logger.info("Delete==>upd c_mar_record信息表SQL:"+sql);
		ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			list.add(new C_marRecord(ordercode,
					rs.getString("procode"),
					rs.getString("proname"), 
					rs.getString("calculation"),
					rs.getDouble("price"), 
					rs.getDouble("quantity"),
					rs.getDouble("priceall"), 
					rs.getString("remark1"),
					rs.getString("remark2"),
					rs.getString("remark3"),
					rs.getString("remark4")));
		}
			sql="update c_mar_product set quantity=quantity+? where procode=?";
			logger.info("Delete==>upd c_mar_product信息表SQL:"+sql);
			ps=con.prepareStatement(sql);
			for(int i=0;i<list.size();i++){
				C_marRecord cm=list.get(i);
				ps.setDouble(1,cm.getQuantity());
				ps.setString(2, cm.getProcode());
				ps.addBatch();
			}
			ps.executeBatch();
			rs.close();
			con.commit();
			
			if(r<0){
				logger.info("Delete c_mar_order信息表失败");
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			r=0;
			try {
				logger.error("Delete c_mar_order信息表数据回滚！"+e);
				con.rollback();
				
			} catch (SQLException e1) {
				logger.error("Delete c_mar_order信息表数据回滚异常！"+e);
				e1.printStackTrace();
			}
			logger.error("Delete c_mar_order信息表异常！"+e);
		} finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	
	
	public int getOrderRecordCount(String staffcode,String ordercode,  
			String startDate, String endDate) {
		int num=-1;
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num FROM c_mar_record a " +
					" LEFT JOIN c_mar_order b ON (a.ordercode = b.ordercode)" +
					" where a.sfyx='Y' and b.sfyx='Y' and  b.clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			}

			if(!Util.objIsNULL(startDate)){
				sal.append(" and b.orderdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and b.orderdate <='"+endDate+"'");
			}
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_record 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	}
	public int getOrdercount(String staffcode,String ordercode, String startDate, String endDate) {
		int num=-1;
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from c_mar_order" +
					" where  sfyx='Y' and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode like '%"+ordercode+"%'");
			}

			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	}
	/**
	 * 改版后查询总数方法
	 */
	public int getOrdercount(String staffcode,String ordercode, String startDate, String endDate,String staffname,String userType,String location,String status) {
		int num=-1;
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from c_mar_order" +
					" where  sfyx='Y' and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode like '%"+ordercode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  clientname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  orderType ='"+userType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  collectionlocation = '"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status = '"+status+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	}
	/**
	 * 改版后 查询方法
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
			String endDate,String staffname,String userType,String location,String status, int pageSize, int currentPage) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_marOrder> list=new ArrayList<C_marOrder>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_mar_order where sfyx='Y'" +
					" and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode like '%"+ordercode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  clientname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  orderType ='"+userType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  collectionlocation = '"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status = '"+status+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			sal.append(" order by orderdate desc  limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  c_mar_order  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				  
				C_marOrder es =new C_marOrder(
						rs.getString("ordercode"), 
						rs.getString("orderType"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getString("department") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"), 
						rs.getString("paymentmethod") ,
						rs.getString("chequeno") ,
						rs.getString("banker") ,
						rs.getString("sfyx") , 
						rs.getString("cash") , 
						rs.getString("C_club") , 
						rs.getString("cheque") , 
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"),
						rs.getString("status"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	
	public List<C_marOrder> queryOrderList(String staffcode,String ordercode, String startDate,
			String endDate, int pageSize, int currentPage) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_marOrder> list=new ArrayList<C_marOrder>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_mar_order where sfyx='Y'" +
					" and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode like '%"+ordercode+"%'");
			}

			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			sal.append(" order by orderdate desc  limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  c_mar_order  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				  
				C_marOrder es =new C_marOrder(
						rs.getString("ordercode"), 
						rs.getString("orderType"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getString("department") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"), 
						rs.getString("paymentmethod") ,
						rs.getString("chequeno") ,
						rs.getString("banker") ,
						rs.getString("sfyx") , 
						rs.getString("cash") , 
						rs.getString("C_club") , 
						rs.getString("cheque") , 
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"),
						rs.getString("status"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	public List<C_marOrder> queryOrderListForPDF(String staffcode,String ordercode, String startDate,
			String endDate, int pageSize, int currentPage) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_marOrder> list=new ArrayList<C_marOrder>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_mar_order where sfyx='Y'" +
					" and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode like '%"+ordercode+"%'");
			}
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d %H:%i:%s')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d %H:%i:%s') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(orderdate,'%Y-%m-%d %H:%i:%s')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d %H:%i:%s') ");
			}
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  c_mar_order  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				
				C_marOrder es =new C_marOrder(
						rs.getString("ordercode"), 
						rs.getString("orderType"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getString("department") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"), 
						rs.getString("paymentmethod") ,
						rs.getString("chequeno") ,
						rs.getString("banker") ,
						rs.getString("sfyx") , 
						rs.getString("cash") , 
						rs.getString("C_club") , 
						rs.getString("cheque") , 
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"),
						rs.getString("status"));
				
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public List<C_marRecord> queryOrderRecordList(String staffcode,String ordercode,
			 String startDate, String endDate, int pageSize,
			int currentPage) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try{
			StringBuffer sal=new StringBuffer("SELECT a.ordercode,a.procode,a.proname,b.clientname,b.clientcode,a.price,a.quantity," +
					" a.priceall,b.c_clubmember,b.collectionlocation,b.paymentmethod,b.orderdate FROM c_mar_record a " +
					" LEFT JOIN c_mar_order b ON (a.ordercode = b.ordercode)" +
					" where a.sfyx='Y' and b.sfyx='Y' and  b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and b.orderdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and b.orderdate <='"+endDate+"'");
			}
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				   
				C_marRecord es =new C_marRecord( 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","",
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"), 
						rs.getString("paymentmethod") ,
						rs.getString("orderdate"),
						rs.getString("clientname"),
						rs.getString("clientcode") );
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_record时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public List<String> getReportDetail(String staffcode,
			String ordercode, String startDate, String endDate) {

		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<String> list=new ArrayList<String>();
		try{
			StringBuffer sal=new StringBuffer("SELECT b.clientcode   FROM c_mar_record a " +
					" LEFT JOIN c_mar_order b ON (a.ordercode = b.ordercode)" +
					" where a.sfyx='Y' and b.sfyx='Y' and  b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			if(!Util.objIsNULL(startDate)){
				sal.append(" and b.orderdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and b.orderdate <='"+endDate+"'");
			}
			sal.append(" group by b.clientcode");
			
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				 
				list.add(rs.getString("clientcode"));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_record时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	
	}
	public List<C_marRecord> reportOrderRecord(String staffcode, String ordercode,
			String startDate, String endDate) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try{
			StringBuffer sal=new StringBuffer("SELECT a.ordercode,a.procode,a.proname,b.clientname,b.clientcode,a.price,a.quantity," +
					" a.priceall,b.c_clubmember,b.collectionlocation,b.paymentmethod,b.orderdate FROM c_mar_record a " +
					" LEFT JOIN c_mar_order b ON (a.ordercode = b.ordercode)" +
					" where a.sfyx='Y' and b.sfyx='Y' and b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				   
				C_marRecord es =new C_marRecord( 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","",
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"), 
						rs.getString("paymentmethod") ,
						rs.getString("orderdate"),
						rs.getString("clientname"),
						rs.getString("clientcode") );
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_record时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public List<C_marRecord> reportOrderRecordForPDF(String staffcode, String ordercode,
			String startDate, String endDate) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try{
			StringBuffer sal=new StringBuffer("SELECT a.ordercode,a.procode,a.proname,b.clientname,b.clientcode,a.price,a.quantity," +
					" a.priceall,b.c_clubmember,b.collectionlocation,b.paymentmethod,b.orderdate FROM c_mar_record a " +
					" LEFT JOIN c_mar_order b ON (a.ordercode = b.ordercode)" +
					" where a.sfyx='Y' and b.sfyx='Y' and b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d %H:%i:%s')>= DATE_FORMAT('"+startDate+"','%Y-%m-%d %H:%i:%s')");
			}
			if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d %H:%i:%s')<= DATE_FORMAT('"+endDate+"','%Y-%m-%d %H:%i:%s')");
			}
			
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				   
				C_marRecord es =new C_marRecord( 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","",
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"), 
						rs.getString("paymentmethod") ,
						rs.getString("orderdate"),
						rs.getString("clientname"),
						rs.getString("clientcode") );
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_record时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public int reprtPDF(String staffcode,String ordercode,  String startDate, String endDate,String URL){
	String ordcodeStr = ordercode;
		Document document = new Document();// 建立一个Document对象
		document.setPageSize(PageSize.A4);// 设置页面大小
		String urlString = "";
		String location = "";
		String title = "";
		try {
			/**
			 * 获取数据
			 */
 			List<C_marRecord> recordlist = reportOrderRecord(staffcode, ordcodeStr, startDate, endDate);
			C_marOrder orderList = getOrder(ordcodeStr);
			if (Util.objIsNULL(orderList)) {
				return 0;
			}
			if (orderList.getCollectionlocation().trim().equals("Y")){
				location="@CONVOY";
			}else if(orderList.getCollectionlocation().trim().equals("C")){
				location="CP3";
			}else {
				location="CWC";
			}
			
			if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
				urlString = Constant.MarkReport_URL_CONS;
				title ="Marketing Premium Order Form (Consultant)";
			}else {
				urlString = Constant.MarkReport_URL_STAFF;
				title ="Marketing Premium Order Form (Staff)";
			}

			File f = new File(urlString);
			if(!f.exists()){
				f.mkdirs();
			}
			
			PdfWriter.getInstance(document, new FileOutputStream(urlString+ staffcode+"_"+ordcodeStr+".pdf"));// 建立一个PdfWriter对象
			  // 建立一个PdfWriter对象
			document.open();
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", false);// 设置中文字体 BaseFont.NOT_EMBEDDED
			Font headFont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
			//Font headFont1 = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
			//Font headFont2 = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
			
			float[] widths = { 100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f  };// 设置表格的列宽
			
			PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
				table.setSpacingBefore(130f);// 设置表格上面空白宽度
				table.setTotalWidth(535);// 设置表格的宽度
				table.setLockedWidth(true);// 设置表格的宽度固定
				// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
			Image img = Image.getInstance(URL+"\\images\\report.png");
			
			PdfPCell cell = new PdfPCell(img,true);// 建立一个单元格
				cell.setBorder(0);//设置单元格无边框
				cell.setFixedHeight(40);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);// 增加单元格
				
			 //空行
				cell = new PdfPCell( new Paragraph("  ", headFont));
				cell.setBorder(0);//设置单元格无边框
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);// 增加单元格
				
				
			//设置头部部分
				cell = new PdfPCell( new Paragraph("Title : ", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph(title, headFont));
				cell.setBorder(0);
				cell.setColspan(3);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("Order Reference : ", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(orderList.getOrdercode(), headFont));
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
			//第二行 头部部分 
				cell = new PdfPCell( new Paragraph("Order by :", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph(orderList.getClientcode()+"("+orderList.getClientname()+")", headFont));
				cell.setBorder(0);
				cell.setColspan(3);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("Order Date:", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(orderList.getOrderdate().substring(0,10), headFont));
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
			
			//换两行
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
			
			//订单表格 列头
				cell = new PdfPCell(new Paragraph("Code", headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Description", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Chinese Product Name", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Unit Price(HK$)", headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
				//cell = new PdfPCell(new Paragraph("C-Club Price", headFont));
				//cell.setColspan(1);
				//table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Quantity", headFont));
				cell.setColspan(1);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Amt(HK$)", headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
			//动态加载数据
				for (int i = 0; i < recordlist.size(); i++) {
					C_marRecord vo = (C_marRecord)recordlist.get(i);
					cell = new PdfPCell(new Paragraph(vo.getProcode(),headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getProname(),headFont));
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("",headFont));
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getPrice()+"",headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(dFormat.format(vo.getQuantity()),headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getPriceall()+"",headFont));
					cell.setColspan(1);
					table.addCell(cell);
				}
					
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("Total:", headFont));
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(orderList.getPriceall()+"", headFont));
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				
				
			//换两行
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("—————————————————————————————————————————————————————", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
			
			//设置尾部部分
			//只有顾问有C-CLUB
			if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
				cell = new PdfPCell( new Paragraph("C-Club Member", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":"+orderList.getC_clubmember(), headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
			}
				
				
			cell = new PdfPCell( new Paragraph("Collection Location", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":"+location, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
			if (!orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("Department", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+orderList.getDepartment(), headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
				cell = new PdfPCell( new Paragraph("Payment Method", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":", headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				
				if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("C-club", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					String clubnum = Util.objIsNULL(orderList.getC_club())?"________________":orderList.getC_club();
					cell = new PdfPCell( new Paragraph(":$"+clubnum, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
				cell = new PdfPCell( new Paragraph("Cash", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				String cashnum = Util.objIsNULL(orderList.getCash())?"________________":orderList.getCash();
				cell = new PdfPCell( new Paragraph(":$"+cashnum, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph("Cheque", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				String Chequenum = Util.objIsNULL(orderList.getCheque())?"________________":orderList.getCheque();
				String banker = Util.objIsNULL(orderList.getBanker())?"________________":orderList.getBanker();
				String Chequeno = Util.objIsNULL(orderList.getChequeno())?"________________":orderList.getChequeno();
				
				cell = new PdfPCell( new Paragraph(":$"+Chequenum+"     Banker:"+banker+"     Cheque No.:"+Chequeno, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
					
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph("Received by ", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":_________________", headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
					cell = new PdfPCell( new Paragraph(orderList.getClientcode()+"("+orderList.getClientname()+")", headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
	
				/*cell = new PdfPCell( new Paragraph("Amount Received by", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":_________________", headFont));
				cell.setBorder(0);
				cell.setColspan(5);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(7);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph("Date", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":_________________", headFont));
				cell.setBorder(0);
				cell.setColspan(5);// 设置合并单元格的列数
				table.addCell(cell);
				*/
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				
				//订单表格 列头
				Font headFont2 = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小
				headFont2.setColor(BaseColor.WHITE);
				cell = new PdfPCell(new Paragraph("For ADM Use", headFont2)); 
				cell.setBackgroundColor(BaseColor.BLACK);
				
				cell.setColspan(8);
				table.addCell(cell);
				
				if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell(new Paragraph("Amount Received by ", headFont)); 
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("", headFont)); 
					cell.setColspan(6);
					table.addCell(cell);
				}
				cell = new PdfPCell(new Paragraph("Handled by ", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("", headFont)); 
				cell.setColspan(6);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Date", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("", headFont)); 
				cell.setColspan(6);
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
			document.add(table);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出PDF——c_mar_Order时出现 ："+DateUtils.getNowDateTime()+e.toString());
		}
		document.close();
		return 1;

	}
	public List<C_marRecord> getRecordForUpd(String ordercode) {
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try{
			StringBuffer sal=new StringBuffer("SELECT * from c_mar_record where sfyx='Y' and ordercode = '"+ordercode+"' ");
		// System.out.println(sql.toString());
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				C_marRecord es =new C_marRecord(
						rs.getInt("id"),
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getString("calculation"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","" );
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_record时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 * 保存Marketing Premium Order
	 */
	public int saveMarOrder(C_marOrder order) {
		int num=-1;
		try{
			con=DBManager.getCon();
		//	con.setAutoCommit(false);//禁止自动提交事务
			sql="insert into c_mar_order values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			ps=con.prepareStatement(sql);
			ps.setString(1,order.getOrdercode());
			ps.setString(2, order.getOrdertype());
			ps.setString(3,order.getClientname());
			ps.setString(4,order.getClientcode());
			ps.setDouble(5,order.getPriceall());
			ps.setString(6,order.getOrderdate());
			ps.setString(7,order.getC_clubmember());
			ps.setString(8,order.getCollectionlocation());
			ps.setString(9,order.getPaymentmethod());
			ps.setString(10,order.getChequeno());
			ps.setString(11,order.getBanker());
			ps.setString(12,order.getSfyx());
			ps.setString(13,order.getRemark1());
			ps.setString(14,order.getRemark2());
			ps.setString(15,order.getRemark3());
			ps.setString(16,order.getRemark4());
			logger.error("保存Marketing Premium Order  sql====:"+sql);
			num=ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			num=0;
			logger.error("保存Marketing Premium Order 时出现："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 批量保存Marketing Premium Record
	 * @param list
	 * @return
	 * @throws SQLException 
	 */
	public int saveMarRecord(List<C_marRecord> list,C_marOrder order)  {
		int num=-1;
		StringBuffer stringBuffer=new StringBuffer();
	try{
		con=DBManager.getCon();
		
		con.setAutoCommit(false);//禁止自动提交事务
		
		//for(int i=0;i<list.size();i++){
		//	C_marRecord cm=list.get(i);
			 stringBuffer.append("insert into c_mar_record (ordercode,procode,proname,calculation,price,quantity,priceall,sfyx,remark1,remark2,remark3,remark4) values(?,?,?,?,?,?,?,?,?,?,?,?);");
			 //stringBuffer.append("update c_mar_product set quantity=quantity-? where procode=? ;");
			//stringBuffer.append("insert into c_mar_record (ordercode,procode,proname,price,quantity,priceall,remark1,remark2,remark3,remark4) values('"+cm.getOrdercode()+"','"+cm.getProcode()+"','"+cm.getProname()+"','"+cm.getPrice()+"','"+cm.getQuantity()+"','"+cm.getPriceall()+"','','','','');");
			//stringBuffer.append("update c_mar_product set quantity=quantity-"+cm.getQuantity()+" where procode='"+cm.getProcode()+"' ;");
	//	}
		
		ps=con.prepareStatement(stringBuffer.toString());
		for(int i=0;i<list.size();i++){
			C_marRecord cm=list.get(i);
			ps.setString((1), cm.getOrdercode());
			ps.setString((2), cm.getProcode());
			ps.setString((3), cm.getProname());
			ps.setString(4, cm.getCalculation());
			ps.setDouble((5), cm.getPrice());
			ps.setDouble((6), cm.getQuantity());
			ps.setDouble((7), cm.getPriceall());
			ps.setString((8), "Y");
			ps.setString((9), "");
			ps.setString((10), "");
			ps.setString((11), "");
			ps.setString((12), "");
			//ps.setDouble((11), cm.getQuantity());
			//ps.setString((12), cm.getProcode());
			//加入批处理
			ps.addBatch();
		}
	
		ps.executeBatch();
		
		//con.commit();
		stringBuffer=new StringBuffer("update c_mar_product set quantity=quantity-? where procode=? ;");
		ps=con.prepareStatement(stringBuffer.toString());
		for(int i=0;i<list.size();i++){
			C_marRecord cm=list.get(i);
			/*ps.setString((1), cm.getOrdercode());
			ps.setString((2), cm.getProcode());
			ps.setString((3), cm.getProname());
			ps.setDouble((4), cm.getPrice());
			ps.setDouble((5), cm.getQuantity());
			ps.setDouble((6), cm.getPriceall());
			ps.setString((7), "");
			ps.setString((8), "");
			ps.setString((9), "");
			ps.setString((10), "");*/
			ps.setDouble((1), cm.getQuantity());
			ps.setString((2), cm.getProcode());
			//加入批处理
			ps.addBatch();
		}
	ps.executeBatch();
	
	sql="insert into c_mar_order values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	ps=con.prepareStatement(sql);
	ps.setString(1,order.getOrdercode());
	ps.setString(2, order.getOrdertype());
	ps.setString(3,order.getClientname());
	ps.setString(4,order.getClientcode());
	ps.setString(5, order.getDepartment());
	ps.setDouble(6,order.getPriceall());
	ps.setString(7,order.getOrderdate());
	ps.setString(8,order.getC_clubmember());
	ps.setString(9,order.getCollectionlocation());
	ps.setString(10,order.getPaymentmethod());
	ps.setString(11,order.getChequeno());
	ps.setString(12,order.getBanker());
	ps.setString(13,order.getSfyx());
	ps.setString(14,order.getCash());
	ps.setString(15,order.getC_club());
	ps.setString(16,order.getCheque());
	ps.setString(17,order.getRemark1());
	ps.setString(18,order.getRemark2());
	ps.setString(19,order.getRemark3());
	ps.setString(20,order.getRemark4());
	ps.setString(21,order.getStatus());
	logger.info("保存Marketing Premium Order  sql====:"+sql);
	num=ps.executeUpdate();
	
	
	
		
		con.commit();
		num=list.size();
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				logger.error("数据回滚  原因：==="+e);
				con.rollback();
			} catch (SQLException e1) {
				logger.error("数据回滚时出现：==="+e1);
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

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
	public List<C_marRecord> findMarketingPremium(String startDate,
			String endDate, String ordercode, String ordertype,
			String productname,String clientname,String clientcode,String location,int currentPage,int pageSize) {
		List<C_marRecord> list=new ArrayList<C_marRecord>();
		try{
			StringBuffer stringBuffer=new StringBuffer("select id, r.ordercode,ordertype,r.procode,r.proname,r.calculation,r.price,r.quantity,r.priceall,clientname,clientcode,collectionlocation as location,orderdate  from c_mar_record r left join c_mar_order o on(o.ordercode=r.ordercode) where o.sfyx='Y' and r.sfyx='Y' ");
			if(!Util.objIsNULL(startDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(ordercode)){
				stringBuffer.append(" and r.ordercode='"+ordercode+"'");
			}
			
			if(!Util.objIsNULL(ordertype)){
				stringBuffer.append(" and ordertype='"+ordertype+"'");
			}
			if(!Util.objIsNULL(clientname)){
				stringBuffer.append(" and clientname like '%"+clientname+"%'");
			}
			if(!Util.objIsNULL(clientcode)){
				stringBuffer.append(" and clientcode like '%"+clientcode+"%'");
			}
			if(!Util.objIsNULL(location)){
				stringBuffer.append(" and collectionlocation like '%"+location+"%'");
			}
			if(!Util.objIsNULL(productname)){
				stringBuffer.append(" and procode like '%"+productname+"%'");
			}
			
			stringBuffer.append(" order by orderdate desc");
			stringBuffer.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			
		 con=DBManager.getCon();
		 logger.info("C_GetMarkPremiumDaoImpl line in  794 查询 Marketing Premium");
		 ps=con.prepareStatement(stringBuffer.toString());
		 ResultSet rs=ps.executeQuery();
		 while(rs.next()){
			 list.add(new C_marRecord(
					 Integer.parseInt(rs.getString("id")),
					 rs.getString("ordercode"),
					 rs.getString("procode"),
					 rs.getString("proname"),
					 rs.getString("calculation"),
					 Double.parseDouble(rs.getString("price")),
					 Double.parseDouble(rs.getString("quantity")),
					 Double.parseDouble(rs.getString("priceall")),
					 rs.getString("location"),
					 rs.getString("orderdate"),
					 rs.getString("clientname"),
					 rs.getString("clientcode"),
					 rs.getString("ordertype")));
		 }
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			 logger.info("C_GetMarkPremiumDaoImpl line in  816 查询 Marketing Premium时出现   ："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 *查询Marketing Premium 消费记录表的影响行数
	 */
	public int getRows(String startDate, String endDate,
			String ordercode, String ordertype, String productname,
			String clientname, String clientcode, String location) {
		int num=-1;
		try{
			StringBuffer stringBuffer=new StringBuffer("select count(*) as num  from c_mar_record r left join c_mar_order o on(o.ordercode=r.ordercode) where o.sfyx='Y' and r.sfyx='Y' ");
			if(!Util.objIsNULL(startDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				stringBuffer.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(ordercode)){
				stringBuffer.append(" and r.ordercode='"+ordercode+"'");
			}
			
			if(!Util.objIsNULL(ordertype)){
				stringBuffer.append(" and ordertype='"+ordertype+"'");
			}
			if(!Util.objIsNULL(clientname)){
				stringBuffer.append(" and clientname like '%"+clientname+"%'");
			}
			if(!Util.objIsNULL(clientcode)){
				stringBuffer.append(" and clientcode like '%"+clientcode+"%'");
			}
			if(!Util.objIsNULL(location)){
				stringBuffer.append(" and collectionlocation like '%"+location+"%'");
			}
			if(!Util.objIsNULL(productname)){
				stringBuffer.append(" and procode like '%"+productname+"%'");
			}
		 con=DBManager.getCon();
		 logger.info("C_GetMarkPremiumDaoImpl line in  855 查询 Marketing Premium");
		 ps=con.prepareStatement(stringBuffer.toString());
		 ResultSet rs=ps.executeQuery();
		 while(rs.next()){
		 num=rs.getInt("num");
		 }
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			 logger.info("C_GetMarkPremiumDaoImpl line in  865 查询 Marketing Premium时出现   ："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

public C_marOrder getOrder(String ordercode) {
		C_marOrder cmarOrder = new C_marOrder();
		try{
			String sql="select * from c_mar_order where ordercode=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, ordercode);
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				cmarOrder=new C_marOrder(rs.getString("ordercode"),
						rs.getString("ordertype"),
						rs.getString("clientname"), 
						rs.getString("clientcode"), 
						rs.getString("department"),
						rs.getDouble("priceall"), 
						rs.getString("orderdate"),
						rs.getString("c_clubmember"), 
						rs.getString("collectionlocation"),
						rs.getString("paymentmethod"), 
						rs.getString("chequeno"),
						rs.getString("banker"),
						rs.getString("sfyx"),
						rs.getString("cash"),
						rs.getString("C_club"),
						rs.getString("cheque"),
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"), 
						rs.getString("remark4"),
						rs.getString("status"));
				
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return cmarOrder;
	}
/**
 *修改产品数量并更新库存
 */
public int updateMarketingPremiuimStock(String id,String quantity,String allprice,String username) {
	C_marRecord cm=findRecordById(id);
	String upd_date=DateUtils.getNowDateTime();
	int num=-1;
	try{
		con=DBManager.getCon();
		con.setAutoCommit(false);
		if(Double.parseDouble(quantity)>0){
		sql="insert into c_mar_record (ordercode,procode,proname,calculation,price,quantity,priceall,sfyx,remark1,remark2,remark3,remark4) values(?,?,?,?,?,?,?,?,?,?,?,?);";
			ps=con.prepareStatement(sql);
			ps.setString((1), cm.getOrdercode());
			ps.setString((2), cm.getProcode());
			ps.setString((3), cm.getProname());
			ps.setString(4, cm.getCalculation());
			ps.setDouble((5), cm.getPrice());
			ps.setDouble((6), Double.parseDouble(quantity));
			ps.setDouble((7), Double.parseDouble(allprice));
			ps.setString((8), "Y");
			ps.setString((9), "");
			ps.setString((10), "");
			ps.setString((11), username);
			ps.setString((12), upd_date);
			ps.addBatch();
			ps.executeBatch();
		}
			sql="update c_mar_record set sfyx='N',remark3=?,remark4=? where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, upd_date);
			ps.setInt(3, Integer.parseInt(id));
			ps.addBatch();
			ps.executeBatch();
			
			sql="update c_mar_product set quantity=quantity+? where procode=?";
			ps=con.prepareStatement(sql);
			ps.setDouble(1, cm.getQuantity()-Double.parseDouble(quantity));
			ps.setString(2,cm.getProcode());
			ps.addBatch();
			ps.executeBatch();
			
			sql="update c_mar_order set priceall=priceall-? where ordercode=?";
			ps=con.prepareStatement(sql);
			ps.setDouble(1, cm.getPriceall()-Double.parseDouble(allprice));
			ps.setString(2,cm.getOrdercode());
			ps.addBatch();
			ps.executeBatch();
			
			
			
			con.commit();
			num=1;
	}catch(Exception e){
		num=0;
		e.printStackTrace();
		logger.error("在C_GetMarkPremiumDaoImpl中 line in   1198 保存更新数据时出现  ："+sql+" ===="+e);
		try{
			con.rollback();
		}catch(Exception e1){
			e.printStackTrace();
			logger.error("在C_GetMarkPremiumDaoImpl中 line in   1203 数据回滚时出现    :=="+sql+"=="+e1);
		}
	}finally{
		DBManager.closeCon(con);
	}
	return num;
}

/**
 * 根据id查询产品信息 
 * @param id
 * @param username
 * @return
 */

public C_marRecord findRecordById(String id){
	C_marRecord cm=new C_marRecord();
	try{
		con=DBManager.getCon();
		sql="select * from C_mar_record where id=?";
		ps=con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(id));
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			cm.setOrdercode(rs.getString("ordercode"));
			cm.setProcode(rs.getString("procode"));
			cm.setProname(rs.getString("proname"));
			cm.setCalculation(rs.getString("calculation"));
			cm.setPrice(rs.getDouble("price"));
			cm.setPriceall(rs.getDouble("priceall"));
			cm.setQuantity(rs.getDouble("quantity"));
		}rs.close();
	}catch(Exception e){
		e.printStackTrace();
		logger.error("在C_GetMarkPremiumDaoImpl 中根据Id获取C_MarRecord时  line in 1179 出现 ：==="+e);
	}finally{
	DBManager.closeCon(con);	
	}
	return cm;
}
public int reprtPDFOnTime(String staffcode, String ordercode, String startDate,
		String endDate, String URL) {

	String ordcodeStr = ordercode;
		Document document = new Document();// 建立一个Document对象
		document.setPageSize(PageSize.A4);// 设置页面大小
		String urlString = "";
		String location = "";
		String title = "";
		try {
			/**
			 * 获取数据
			 */
 			List<C_marRecord> recordlist = reportOrderRecordForPDF(staffcode, ordcodeStr, startDate, endDate);
			C_marOrder orderList = getOrder(ordcodeStr);
			if (Util.objIsNULL(orderList)) {
				return 0;
			}
			/**
			 * 2014-7-7 12:30:42 wilson upd 
			 * if (orderList.getCollectionlocation().trim().equals("Y")){
				location="@CONVOY";
			}else if(orderList.getCollectionlocation().trim().equals("C")){
				location="CP3";
			}else {
				location="CWC";
			}*/
			location = orderList.getCollectionlocation().trim();
			
			if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
				urlString = Constant.MarkReport_URL_CONS+DateUtils.afterDate(DateUtils.getDateToday())+"/";
				title ="Marketing Premium Order Form (Consultant)";
			}else {
				urlString = Constant.MarkReport_URL_STAFF+DateUtils.afterDate(DateUtils.getDateToday())+"/";
				title ="Marketing Premium Order Form (Staff)";
			}

			File f = new File(urlString);
			if(!f.exists()){
				f.mkdirs();
			}
			
			PdfWriter.getInstance(document, new FileOutputStream(urlString+ staffcode+"_"+ordcodeStr+".pdf"));// 建立一个PdfWriter对象
			  // 建立一个PdfWriter对象
			document.open();
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", false);// 设置中文字体 BaseFont.NOT_EMBEDDED
			Font headFont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
			//Font headFont1 = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
			//Font headFont2 = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
			
			float[] widths = { 100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f  };// 设置表格的列宽
			
			PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
				table.setSpacingBefore(130f);// 设置表格上面空白宽度
				table.setTotalWidth(535);// 设置表格的宽度
				table.setLockedWidth(true);// 设置表格的宽度固定
				// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
			Image img = Image.getInstance(URL+"\\images\\report.png");
			
			PdfPCell cell = new PdfPCell(img,true);// 建立一个单元格
				cell.setBorder(0);//设置单元格无边框
				cell.setFixedHeight(40);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);// 增加单元格
				
			 //空行
				cell = new PdfPCell( new Paragraph("  ", headFont));
				cell.setBorder(0);//设置单元格无边框
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);// 增加单元格
				
				
			//设置头部部分
				cell = new PdfPCell( new Paragraph("Title : ", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph(title, headFont));
				cell.setBorder(0);
				cell.setColspan(3);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("Order Reference : ", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(orderList.getOrdercode(), headFont));
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
			//第二行 头部部分 
				cell = new PdfPCell( new Paragraph("Order by :", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph(orderList.getClientcode()+"("+orderList.getClientname()+")", headFont));
				cell.setBorder(0);
				cell.setColspan(3);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("Order Date:", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(orderList.getOrderdate().substring(0,10), headFont));
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
			
			//换两行
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
			
			//订单表格 列头
				cell = new PdfPCell(new Paragraph("Code", headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Description", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Chinese Product Name", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Unit Price(HK$)", headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
				//cell = new PdfPCell(new Paragraph("C-Club Price", headFont));
				//cell.setColspan(1);
				//table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Quantity", headFont));
				cell.setColspan(1);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Amt(HK$)", headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
			//动态加载数据
				for (int i = 0; i < recordlist.size(); i++) {
					C_marRecord vo = (C_marRecord)recordlist.get(i);
					cell = new PdfPCell(new Paragraph(vo.getProcode(),headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getProname(),headFont));
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("",headFont));
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getPrice()+"",headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(dFormat.format(vo.getQuantity()),headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getPriceall()+"",headFont));
					cell.setColspan(1);
					table.addCell(cell);
				}
					
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("Total:", headFont));
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(orderList.getPriceall()+"", headFont));
				cell.setColspan(1);// 设置合并单元格的列数
				table.addCell(cell);
				
				
			//换两行
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph("—————————————————————————————————————————————————————", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
			
			//设置尾部部分
			//只有顾问有C-CLUB
			if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
				cell = new PdfPCell( new Paragraph("C-Club Member", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":"+orderList.getC_clubmember(), headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
			}
				
				
			cell = new PdfPCell( new Paragraph("Collection Location", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":"+location, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
			if (!orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("Department", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+orderList.getDepartment(), headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
				cell = new PdfPCell( new Paragraph("Payment Method", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":", headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				
				if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("C-club", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					String clubnum = Util.objIsNULL(orderList.getC_club())?"________________":orderList.getC_club();
					cell = new PdfPCell( new Paragraph(":$"+clubnum, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
				cell = new PdfPCell( new Paragraph("Cash", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				String cashnum = Util.objIsNULL(orderList.getCash())?"________________":orderList.getCash();
				cell = new PdfPCell( new Paragraph(":$"+cashnum, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph("Cheque", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				String Chequenum = Util.objIsNULL(orderList.getCheque())?"________________":orderList.getCheque();
				String banker = Util.objIsNULL(orderList.getBanker())?"________________":orderList.getBanker();
				String Chequeno = Util.objIsNULL(orderList.getChequeno())?"________________":orderList.getChequeno();
				
				cell = new PdfPCell( new Paragraph(":$"+Chequenum+"     Banker:"+banker+"     Cheque No.:"+Chequeno, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
					
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				
				cell = new PdfPCell( new Paragraph("Received by ", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":_________________", headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
					cell = new PdfPCell( new Paragraph(orderList.getClientcode()+"("+orderList.getClientname()+")", headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
	
				cell = new PdfPCell( new Paragraph(" ", headFont));
				cell.setBorder(0);
				cell.setColspan(8);// 设置合并单元格的列数
				table.addCell(cell);
				
				//订单表格 列头
				Font headFont2 = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小
				headFont2.setColor(BaseColor.WHITE);
				cell = new PdfPCell(new Paragraph("For ADM Use", headFont2)); 
				cell.setBackgroundColor(BaseColor.BLACK);
				
				cell.setColspan(8);
				table.addCell(cell);
				
				if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell(new Paragraph("Amount Received by ", headFont)); 
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("", headFont)); 
					cell.setColspan(6);
					table.addCell(cell);
				}
				cell = new PdfPCell(new Paragraph("Handled by ", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("", headFont)); 
				cell.setColspan(6);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Date", headFont)); 
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("", headFont)); 
				cell.setColspan(6);
				table.addCell(cell);
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
			document.add(table);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出PDF——c_mar_Order时出现 ："+DateUtils.getNowDateTime()+e.toString());
		}
		document.close();
		return 1;

	
}
/**
 * 根据订单Code Ready
 * @param OrderCode
 * @param status
 * @return
 */
public int ready(String OrderCode,String status,String staffcode,String to ,String location) {
	int num=-1;
	try{
		con=DBManager.getCon();
		con.setAutoCommit(false);
		sql="update c_mar_order set status=? where ordercode=? and status='Submitted' and sfyx='Y' ";
		ps=con.prepareStatement(sql);
		ps.setString(1, status);
		ps.setString(2, OrderCode);
		num=ps.executeUpdate();
		if(num<1){
			throw new RuntimeException();
		}
		sql="insert c_mar_operation(refno,operationType,operationName,operationDate)values('"+OrderCode+"','Ready','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=con.prepareStatement(sql);
		num=ps.executeUpdate();
		if(num<1){
			throw new RuntimeException();
		}
		String signature="";
		String content=" Dear Sir / Madam,<br/>";
		if("148 Electric Road".equalsIgnoreCase(location) || "Peninsula".equalsIgnoreCase(location)){
			content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your requested Mkt Premium by internal transfer today.";	
		}else{
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested Mkt Premium at (location: "+("CP3".equalsIgnoreCase(location)?"17/F":"40/F")+" Mailing Room)";
		}
		signature+="<br/>Best Regards,<br/>";
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
		e.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
			logger.error("Ready 数据回滚异常==="+e.getMessage());
		}
		logger.error("Ready 异常==="+e.getMessage());
	}finally{
		DBManager.closeCon(con);
	}
	return num;
}

/**
 * 操作AccessCard Completed
 */
public int completed(String refno, String type,String staffcode,String savetype,String paymethod,String payamount,
		String payDate,String handle,String staffname,String location,String saleno) {
	int num=0;
	try{
		con=DBManager.getCon();
		con.setAutoCommit(false);
		sql="update c_mar_order set status=? where ordercode=? and  status='Ready' and sfyx='Y'";
		 ps=con.prepareStatement(sql);
		 ps.setString(1, type);
		 ps.setString(2, refno);
		 ps.execute();
		
		sql="insert c_mar_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=con.prepareStatement(sql);
		ps.execute();
		
		String menthod[]=paymethod.split("~~");
		String amount[]=payamount.split("~~");
		String date[]=payDate.split("~~");
		String handles[]=handle.split("~~");
		String salenos[]=saleno.split("~~");
		for (int i = 0; i < menthod.length; i++) {
			sql="insert c_payment(refno,type,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx,staffname,location,saleno)values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			ps.setString(2, savetype);
			ps.setString(3, menthod[i]);
			ps.setDouble(4, Double.parseDouble(amount[i]));
			ps.setString(5, date[i]);
			ps.setString(6, handles[i]);
			ps.setString(7, staffcode);
			ps.setString(8, DateUtils.getNowDateTime());
			ps.setString(9, "Y");
			ps.setString(10, staffname);
			ps.setString(11, location);
			ps.setString(12, salenos[i]);
			ps.execute();
		}
		
		con.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(con);
	}
	return num;
}




/**
 * 导出Reporting Premium
 * @param startDate
 * @param endDate
 * @return
 */
public Result downReportingForMarketingPremium(String startDate, String endDate) {
	Result rss=null;
	try{
		con=DBManager.getCon();
		String sql="select * from(SELECT 'Month',ca.orderdate,ca.ordercode,clientcode,clientname,ca.orderType,"+
					" if(cp.paymentMethod='Cash',cp.paymentAount,'0.00') as Cash,"+
					" if(cp.paymentMethod='C-Club',cp.paymentAount,'0.00') as Club,"+
					" if(cp.paymentMethod='Octopus',cp.paymentAount,'0.00') as Octopus,"+
					" if(cp.paymentMethod='EPS',cp.paymentAount,'0.00') as EPS"+
					" from c_mar_order ca"+
					" left join c_payment cp on(ca.ordercode=cp.refno) "+
					" where ca.sfyx='Y' and ca.priceAll>0 and DATE_FORMAT(ca.orderdate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(ca.orderdate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";

		ps=con.prepareStatement(sql);
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
		DBManager.closeCon(con);
	}
	return rss;
}

/**
 * 获取Marketing Premium
 * @return
 */
public Result findStore(String startDate, String endDate){
	Result rss=null;
	try{
		con=DBManager.getCon();
		String sql="select * from (select procode,englishname,sum(quantity) as quantity from" +
				" c_mar_product cs group by procode)a";
		ps=con.prepareStatement(sql);
		//ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
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
		String sql="select ordercode,procode,quantity from c_mar_record where sfyx='Y' and ordercode in("+
		"select ordercode from c_mar_order co"+
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
		logger.error("获取Marketing Premium 异常=="+e.getMessage());
		e.printStackTrace();
	}finally{
		DBManager.closeCon(con);
	}
	return rss;
	
	
}
	//获取总行数
	public int getAllRow(String productcode, String productname, String startDate,String endDate, String BLBZ) {
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select * from c_mar_product where procode like '%"+productcode+"%'");
			if(!Util.objIsNULL(productname)){
				sal.append(" and  englishname like '%"+productname+"%'");
			}
			if(!Util.objIsNULL(BLBZ)){
				sal.append(" and  BLBZ like '%"+BLBZ+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(updates,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(updates,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
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
			logger.error("查询c_mar_product 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	//根据条件查询产品信息
	public List<C_marProduct> queryProduct(String productcode, String productname,String startDate, String endDate,
			String BLBZ, int currentPage,int pageSize) {
		List<C_marProduct> list=new ArrayList<C_marProduct>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_mar_product where procode like '%"+productcode+"%'");
			if(!Util.objIsNULL(productname)){
				sal.append(" and  englishname like '%"+productname+"%'");
			}
			if(!Util.objIsNULL(BLBZ)){
				sal.append(" and  BLBZ like '%"+BLBZ+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(updates,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(updates,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			sal.append(" order by procode desc  limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  C_marProduct  sql:===="+sal.toString());
			//System.out.println(sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_marProduct cl=new C_marProduct(
						rs.getString("procode"),
						rs.getString("englishname"),
						rs.getString("chinesename"),
						rs.getString("unitprice"),
						rs.getString("c_clubprice"),
						rs.getString("specialprice"),
						rs.getString("unit"),
						rs.getString("quantity"),
						rs.getString("blbz"),
						rs.getString("updates"),
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"));
			
				list.add(cl);
				}
			//System.out.println("查询产品  ："+sal);
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询C_marProduct时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public int saveMarkPremiumList(List<List<Object>> list) {
		int num = 0;
		QueryMarkPermiumDao aDao  = new QueryMarkPremiumDaoImpl();
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);//禁止提交事物
			for(int i=2;i<list.size();i++){
				List<Object> list2=list.get(i);

				String procode =(String)list2.get(0);
				String ename = (String)list2.get(1);
				String cname = (String)list2.get(2);
				String unit = (String)list2.get(3);
				String unitprice  = (String)list2.get(4);
				String cclubprice  = (String)list2.get(5);
				String specialprice  = (String)list2.get(6);
				String numc  = (String)list2.get(7);
				String isactive = (String)list2.get(8);
				 
				String unitpricenum = Util.objIsNULL(unitprice)?"0":unitprice;
				String cclubpricenum = Util.objIsNULL(cclubprice)?"":cclubprice;
				String specialpricenum = Util.objIsNULL(specialprice)?"":specialprice;
				String numcnum = Util.objIsNULL(numc)?"0":numc;
				//code不为空 
				if (!Util.objIsNULL(procode) ) {
					String club = Util.objIsNULL(cclubpricenum)?"0":cclubpricenum;
					sql = "insert c_mar_stock(procode,englishname,chinesename,unit,unitprice,c_clubprice,specialprice" +",quantity,adddate)" +
							" values('"+procode+"','"+ename+"','" + cname+"','"+unit+"',"+
							unitpricenum+",'"+club+"','"+specialpricenum+"'," +numcnum+ ",'"+ DateUtils.getNowDateTime()+"')";
					ps = con.prepareStatement(sql);
					
					//System.out.println("库存表--->"+sql);
					
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						num++;
						logger.info("保存 c_mar_stock OK ！");
						//办理标志
						String blbz = Constant.BLBZ_A;
						//更新标志为Y 则更新产品表中的数据
						if (isactive.trim().equals(Constant.YXBZ_Y)) {
							
							int acountnum = aDao.findAllByCode(procode); //判断数据是否存在
							
							//如果C-CLUB价格为空 则跟回原价
							if (Util.objIsNULL(cclubpricenum)) {
								cclubpricenum = unitpricenum;
							}
							//有记录存在 则更新
							if (acountnum > 0) {
								//int updnum =aDao.updProduct(procode, ename, cname, new Double(unitpricenum),new Double(cclubpricenum),specialpricenum, unit, new Double(numcnum), blbz);
								 String sql01="update c_mar_product set englishname='"+ename+"',chinesename='"+cname+"',unitprice="+
								 unitprice+",c_clubprice="+cclubpricenum+",specialprice='"+specialpricenum+"',unit='"+
								 unit+"',quantity= quantity+"+numcnum+",BLBZ='"+blbz+"',updates='"+DateUtils.getNowDateTime()+"' where procode='"+
								 procode+"' ";
								 ps=con.prepareStatement(sql01);
								 ps.executeUpdate();
								logger.info("更新 c_Mar_product！"+sql01);
							
							}else {//无记录存在 则新增
								//int addnum = aDao.saveProduct(procode, ename, cname, new Double(unitpricenum),new Double(cclubpricenum),specialpricenum, unit, new Double(numcnum), blbz);
								StringBuffer stringBuffer=new StringBuffer("insert into c_mar_product(procode," +
										"englishname,chinesename,unitprice,c_clubprice,specialprice,unit,quantity,BLBZ,updates)" +
										" values('"+procode+"','"+ename+"','"+cname+"','"+unitprice+"','"+cclubpricenum+"','"+specialpricenum+"','"+
										unit+"','"+numcnum+"','"+blbz+"','"+DateUtils.getNowDateTime()+"')");

								logger.info("save MarkPremium SQL:"+stringBuffer.toString());
								ps = con.prepareStatement(stringBuffer.toString());
								num = ps.executeUpdate();
								if(num < 0){
									logger.info("save MarkPremium信息表失败");
								}
							}
						}else if (isactive.trim().equals(Constant.YXBZ_D)) {
							//状态为D 则删除
							//int numa =aDao.delProduct(procode);
							String sql02 = "update c_mar_product set BLBZ='N' where procode ='"+procode+"'";
							 ps=con.prepareStatement(sql02);
							 int numa=ps.executeUpdate();
							if (numa > 0) {
								logger.info("删除 c_Mar_product！"+numa);
							
							}
						}
						
					} else {
						logger.info("保存 c_Mar_stock 为空！");
					}
					
				} else {
					logger.info("c_Mar_stock procode is null！");
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
	
	public List<C_Payment> getRecordPayment(String ordercode,String type) {
		List<C_Payment> list=new ArrayList<C_Payment>();
		try{
			StringBuffer sal=new StringBuffer("SELECT * from c_payment p where type='"+type+"' and refno = '"+ordercode+"' " +
					"and exists(select * from c_mar_order where p.refno = ordercode and status='Completed')");
		 
			logger.info("查询c_payment sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				C_Payment es =new C_Payment( 
						rs.getString("refno"), 
						rs.getString("type"), 
						rs.getString("paymentMethod"),
						rs.getDouble("paymentAount"),
						rs.getString("paymentDate"),
						rs.getString("Handleder"),
						rs.getString("creator"), 
						rs.getString("createDate"), 
						rs.getString("sfyx"),
						rs.getString("staffname"),
						rs.getString("location"),rs.getString("saleno"));
				 
				list.add(es);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_payment 时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	
}
