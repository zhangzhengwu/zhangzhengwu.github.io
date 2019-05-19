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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DBManager;
import util.DateUtils;
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

import dao.C_GetStationeryDao;
import dao.QueryStationeryDao;
import entity.C_Payment;
import entity.C_stationeryOrder;
import entity.C_stationeryRecord;

/**
 * GetStationery
 * add 2013年5月21日13:14:53
 * @author Wilson
 * 
 */
public class C_GetStationeryDaoImpl implements C_GetStationeryDao {
	PreparedStatement ps = null;
	Connection con = null;
	Logger logger = Logger.getLogger(C_GetStationeryDaoImpl.class);
	DecimalFormat dFormat = new DecimalFormat("##.##");
	/**
	 * 保存c_stationery_stock表
	 */
	public int saveStationery(String filename, InputStream os) {
		
		String sql = "";
		
		int num = 0;
		int beginRowIndex = 2;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		//此处不做删除
		//Util.deltables("promotion_c_report");
		QueryStationeryDao aDao  = new QueryStationeryDaoImpl();
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
				HSSFCell pricecell = row.getCell(3);
				HSSFCell unitcell = row.getCell(4);
				HSSFCell numcell = row.getCell(5);
				HSSFCell consonlycell = row.getCell(6);
				HSSFCell staffonlycell = row.getCell(7);
				HSSFCell isactivecell = row.getCell(8);
			 
				/** 给数据库里面的字段赋值* */
				String procode = Util.cellToString(codecell);
				String ename = Util.cellToString(enamecell);
				String cname = Util.cellToString(cnamecell);
				String price = Util.cellToString(pricecell);
				String unit = Util.cellToString(unitcell);
				String numc = Util.cellToString(numcell);
				String consonly = Util.cellToString(consonlycell);
				String staffonly = Util.cellToString(staffonlycell);
				String isactive = Util.cellToString(isactivecell);
				// System.out.println(cname);
				//code不为空 
				if (!Util.objIsNULL(procode) ) {
					sql = "insert c_stationery_stock(procode,englishname,chinesename,price," +
							"unit,quantity,adddate,staffonly,consonly) values('"+
							procode+ "','" + ename + "','" + 
							cname + "','" + price+ "','" + 
							unit + "','" + numc + "','"+
							DateUtils.getNowDateTime() + "','" + staffonly + "','"+ 
							consonly + "')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("保存 c_stationery_stock OK ！");
						//更新标志为Y 则更新产品表中的数据
						if (isactive.trim().equals(Constant.YXBZ_Y)) {
							String blbz = "";
							if (consonly.trim().equals(Constant.YXBZ_Y) && staffonly.trim().equals(Constant.YXBZ_Y)) {
								blbz = Constant.BLBZ_A; //
							}else if (consonly.trim().equals(Constant.YXBZ_N)) {
								blbz = Constant.BLBZ_S; //
							}else{
								blbz = Constant.BLBZ_C; //
							}
							
							int acountnum = aDao.findAllByCode(procode);
							//有记录存在 则更新
							if (acountnum > 0) {
								int updnum =aDao.updProduct(procode, ename, cname, new Double(price), unit, new Double(numc), blbz);
								logger.info("更新 c_stationery_product！"+updnum);
							
							}else {//无记录存在 则新增
								int addnum = aDao.saveProduct(procode, ename, cname, new Double(price), unit, new Double(numc), blbz);
								logger.info("新增 c_stationery_product！"+addnum);
							}
						}else if (isactive.trim().equals(Constant.YXBZ_D)) {
							//状态为D 则删除
							
							int numa =aDao.delProduct(procode);
							if (numa > 0) {
								logger.info("删除 c_stationery_product！"+numa);
							
							}
						}
						num++;
					} else {
						logger.info("保存 c_stationery_stock 为空！");
					}
				} else {
					logger.info("c_stationery_stock procode is null！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("c_stationery_stock！" + e);
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	public int getOrdercount(String staffcode,String ordercode, String startDate, String endDate) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from " +
					" c_stationery_order where  sfyx='Y' and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode ='"+ordercode+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
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
			logger.error("查询c_stationery_order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	
	}
	/**
	 * HR专用查询分页总条数
	 * @param staffcode
	 * @param ordercode
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param status
	 * @param orderType
	 * @return
	 */
	public int getOrdercount(String staffcode,String staffname,String ordercode, String startDate, String endDate,String location,String status,String orderType) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from " +
					" c_stationery_order where  sfyx='Y' and clientcode like '%"+clientcode+"%' and clientname like '%"+staffname+"%' ");
			if(!Util.objIsNULL(orderType)){
				sal.append(" and  staffOrCons ='"+orderType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  location ='"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status ='"+status+"'");
			}
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode ='"+ordercode+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
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
			logger.error("查询c_stationery_order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	
	}
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
			String startDate, String endDate,String location,String status,String orderType, int pageSize, int currentPage) {

		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
		try{
			StringBuffer sal=new StringBuffer("select ordercode,clientname,clientcode,priceall," +
					" orderdate,(select name from location where realName = o.location) as location,departhead,sfyx,staffOrCons,remark1,remark2,remark3,remark4,status  from c_stationery_order o where " +
					" sfyx='Y' and clientcode like '%"+clientcode+"%' and clientname like '%"+staffname+"%'");
			
			if(!Util.objIsNULL(orderType)){
				sal.append(" and  staffOrCons ='"+orderType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  location ='"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status ='"+status+"'");
			}
			
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode ='"+ordercode+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			sal.append(" order by orderdate desc limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询c_stationery_order   sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_stationeryOrder es =new C_stationeryOrder(
						rs.getString("ordercode"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("location"), 
						rs.getString("departhead") ,
						rs.getString("sfyx") , 
						rs.getString("staffOrCons"),
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"),
						rs.getString("status"));
				
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_stationery_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	
	
	}
	
	
	public List<C_stationeryOrder> queryOrderList(String staffcode,String ordercode,
			String startDate, String endDate, int pageSize, int currentPage) {

		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
		try{
			StringBuffer sal=new StringBuffer("select ordercode,clientname,clientcode,priceall," +
					" orderdate,(select name from location where realName = o.location) as location,departhead,sfyx,staffOrCons,remark1,remark2,remark3,remark4,status  from c_stationery_order o where " +
					" sfyx='Y' and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode ='"+ordercode+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			sal.append(" order by orderdate desc limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询c_stationery_order   sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_stationeryOrder es =new C_stationeryOrder(
						rs.getString("ordercode"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("location"), 
						rs.getString("departhead") ,
						rs.getString("sfyx") , 
						rs.getString("staffOrCons"),
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"),
						rs.getString("status"));
				
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_stationery_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	
	
	}
	public int getOrderRecordCount(String staffcode,String ordercode, String startDate,
			String endDate) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num FROM c_stationery_consume_record a " +
					" LEFT JOIN c_stationery_order b ON (a.ordercode = b.ordercode)" +
					" where b.clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  a.ordercode ='"+ordercode+"'");
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
			logger.error("查询c_stationery_order_record 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	
	}
	public List<C_stationeryRecord> queryOrderRecordList(String staffcode,String ordercode,
			String startDate, String endDate, int pageSize, int currentPage) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_stationeryRecord> list=new ArrayList<C_stationeryRecord>();
		try{
			StringBuffer sal=new StringBuffer("SELECT a.ordercode,a.procode,a.proname,b.clientname,b.clientcode,a.price,a.quantity," +
					" a.priceall,b.location,b.orderdate,a.procname FROM c_stationery_consume_record a " +
					" LEFT JOIN c_stationery_order b ON (a.ordercode = b.ordercode)" +
					" where b.clientcode like '%"+clientcode+"%' ");
			
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  a.ordercode ='"+ordercode+"'");
			}

			if(!Util.objIsNULL(startDate)){
				sal.append(" and b.orderdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and b.orderdate <='"+endDate+"'");
			}
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询c_stationery_consume_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_stationeryRecord es =new C_stationeryRecord( 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","",
						rs.getString("location"), 
						rs.getString("orderdate") ,
						rs.getString("clientname"),
						rs.getString("clientcode"),
						rs.getString("procname"));
				 
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_stationery_consume_record时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	
	
	
	}
 
	public int delStationeryOrder(String ordercode) {
		int r = -1;
		try {
			 con=DBManager.getCon();
			 String sql="update c_stationery_order set sfyx='"+Constant.YXBZ_N+"',remark1='"+DateUtils.getNowDateTime()+"' where ordercode='"+ordercode+"' ";
			 logger.info("upd c_stationery_order信息表SQL:"+sql);
			 
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			if(r<0){
				logger.info("upd c_stationery_order信息表失败");
			}
			 
				
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("upd c_stationery_order信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("upd c_stationery_order信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	
	public int delStationeryOrder(String ordercode,String user) {
		int r = -1;
		try {
			 con=DBManager.getCon();
			 con.setAutoCommit(false);//禁止自动提交
			 
			 String sql="update c_stationery_order set sfyx='"+Constant.YXBZ_D+"',status='Deleted',remark1='"+DateUtils.getNowDateTime()+"' where" +
			 		" sfyx='Y' and status='Submitted' and ordercode='"+ordercode+"' ";
			 logger.info("Delete==> c_stationery_order信息表SQL:"+sql);
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			 
			 
			 sql="insert c_stationery_operation(ordercode,operationType,operationName,operationDate)values('"+ordercode+"','"+Constant.C_Deleted+"','"+user+"','"+DateUtils.getNowDateTime()+"');";
			 logger.info("Delete==> c_stationery_Operation信息表SQL:"+sql);	
			 ps=con.prepareStatement(sql);
			 ps.execute();
				
				  sql="select procode,quantity from c_stationery_consume_record where ordercode='"+ordercode+"' ";
				  logger.info("Deleted==>Search c_stationery_consume_record信息表SQL:"+sql);
					ps=con.prepareStatement(sql);
					ResultSet rs= null;
					rs=ps.executeQuery();
					
					sql="update c_stationery_product set quantity=quantity+?,remark1='"+DateUtils.getNowDateTime()+"' where procode=? ";
					 logger.info("Review c_stationery_product信息表SQL:"+sql);
					 ps=con.prepareStatement(sql);
					while(rs.next()){   
						//System.out.println(rs.getString("procode")+"---"+rs.getString("quantity"));
						ps.setDouble(1,rs.getDouble("quantity"));
						ps.setString(2,rs.getString("procode"));
						ps.addBatch();
					}
					rs.close();
					ps.executeBatch();
					 con.commit();
			/*	if(map.size() != 0){
					 sql="update c_stationery_product set quantity=quantity+?,remark1='"+DateUtils.getNowDateTime()+"' where procode=? ";
					 logger.info("Review c_stationery_product信息表SQL:"+sql);
					 ps=con.prepareStatement(sql);
					Set set = map.entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry entry =  (Map.Entry)it.next();
						ps.setString(1,(String) entry.getKey());
						ps.setDouble(2,Double.parseDouble(entry.getValue().toString()));
						ps.addBatch();
					}
					ps.executeBatch();
				}
				 con.commit();*/
			if(r<1){
				logger.info("Deleted c_stationery_order信息表失败");
			}
			 
		} catch (SQLException e) {
			r=0;
			e.printStackTrace();
			logger.error("upd c_stationery_order信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			r=0;
			e.printStackTrace();
			logger.error("upd c_stationery_order信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	public int VOID(String ordercode,String user) {
		int r = -1;
		try {
			 con=DBManager.getCon();
			 con.setAutoCommit(false);//禁止自动提交
			 
			 String sql="update c_stationery_order set sfyx='D',status='VOID',remark1='"+DateUtils.getNowDateTime()+"' where" +
			 		" sfyx='Y' and status!='Completed' and ordercode='"+ordercode+"' ";
			 logger.info("VOID==> c_stationery_order信息表SQL:"+sql);
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			 
			 
			 sql="insert c_stationery_operation(ordercode,operationType,operationName,operationDate)values('"+ordercode+"','VOID','"+user+"','"+DateUtils.getNowDateTime()+"');";
			 logger.info("Delete==> c_stationery_Operation信息表SQL:"+sql);	
			 ps=con.prepareStatement(sql);
			 ps.execute();
				
				  sql="select procode,quantity from c_stationery_consume_record where ordercode='"+ordercode+"' ";
				  logger.info("Deleted==>Search c_stationery_consume_record信息表SQL:"+sql);
					ps=con.prepareStatement(sql);
					ResultSet rs= null;
					rs=ps.executeQuery();
					
					sql="update c_stationery_product set quantity=quantity+?,remark1='"+DateUtils.getNowDateTime()+"' where procode=? ";
					 logger.info("Review c_stationery_product信息表SQL:"+sql);
					 ps=con.prepareStatement(sql);
					while(rs.next()){   
						//System.out.println(rs.getString("procode")+"---"+rs.getString("quantity"));
						ps.setDouble(1,rs.getDouble("quantity"));
						ps.setString(2,rs.getString("procode"));
						ps.addBatch();
					}
					rs.close();
					ps.executeBatch();
					 con.commit();
			/*	if(map.size() != 0){
					 sql="update c_stationery_product set quantity=quantity+?,remark1='"+DateUtils.getNowDateTime()+"' where procode=? ";
					 logger.info("Review c_stationery_product信息表SQL:"+sql);
					 ps=con.prepareStatement(sql);
					Set set = map.entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry entry =  (Map.Entry)it.next();
						ps.setString(1,(String) entry.getKey());
						ps.setDouble(2,Double.parseDouble(entry.getValue().toString()));
						ps.addBatch();
					}
					ps.executeBatch();
				}
				 con.commit();*/
			if(r<1){
				logger.info("VOID c_stationery_order信息表失败");
			}
			 
		} catch (SQLException e) {
			r=0;
			e.printStackTrace();
			logger.error("Deleted c_stationery_order信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			r=0;
			e.printStackTrace();
			logger.error("Deleted c_stationery_order信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	
	
	
	
	
	public Map<String,Double> getStationeryProcodeQuantity(String ordercode) {
		Map<String,Double> map = new HashMap<String,Double>();
		try {
			 con=DBManager.getCon();
			 String sql="select procode,quantity from c_stationery_consume_record where ordercode='"+ordercode+"' ";
			 logger.info("upd c_stationery_product信息表SQL:"+sql);
				con=DBManager.getCon();
				ps=con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				while(rs.next()){   
					map.put(rs.getString("procode"), rs.getDouble("quantity"));
				}
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("upd c_stationery_product信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("upd c_stationery_product信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return map;
	}
	
	public int updateStationeryQuantity(String procode,String quantity) {
		int r = -1;
		try {
			 con=DBManager.getCon();
			 String sql="update c_stationery_product set quantity=quantity+'"+quantity+"',remark1='"+DateUtils.getNowDateTime()+"' where procode='"+procode+"' ";
			 logger.info("upd c_stationery_product信息表SQL:"+sql);
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			if(r<0){
				logger.info("upd c_stationery_product信息表失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("upd c_stationery_product信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("upd c_stationery_product信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	public List<String> getReportDetail(String staffcode,
			String ordercode, String startDate, String endDate) {

		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<String> list=new ArrayList<String>();
		try{
			StringBuffer sal=new StringBuffer("SELECT b.clientcode   FROM c_stationery_consume_record a " +
					" LEFT JOIN c_stationery_order b ON (a.ordercode = b.ordercode)" +
					" where b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			if(!Util.objIsNULL(startDate)){
				sal.append(" and b.orderdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and b.orderdate <='"+endDate+"'");
			}
			sal.append(" group by b.clientcode");
			
			//System.out.println(sal);
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
	public List<C_stationeryRecord> reportOrderRecord(String staffcode, String ordercode,
			String startDate, String endDate) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_stationeryRecord> list=new ArrayList<C_stationeryRecord>();
		try{
			
			StringBuffer sal=new StringBuffer("SELECT a.ordercode,a.procode,a.proname,b.clientname,b.clientcode,a.price,a.quantity," +
					" a.priceall,b.location,b.orderdate,a.procname FROM c_stationery_consume_record a " +
					" LEFT JOIN c_stationery_order b ON (a.ordercode = b.ordercode)" +
					" where b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d')>= DATE_FORMAT('"+startDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d')<= DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
			}
			
			
			//System.out.println(sal);
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				C_stationeryRecord es =new C_stationeryRecord( 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","",
						rs.getString("location"), 
						rs.getString("orderdate") ,
						rs.getString("clientname"),
						rs.getString("clientcode"),
						rs.getString("procname"));
				 
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
	public List<C_stationeryRecord> reportOrderRecordForPDF(String staffcode, String ordercode,
			String startDate, String endDate) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_stationeryRecord> list=new ArrayList<C_stationeryRecord>();
		try{
			
			StringBuffer sal=new StringBuffer("SELECT a.ordercode,a.procode,a.proname,b.clientname,b.clientcode,a.price,a.quantity," +
					" a.priceall,b.location,b.orderdate,a.procname FROM c_stationery_consume_record a " +
					" LEFT JOIN c_stationery_order b ON (a.ordercode = b.ordercode)" +
					" where b.clientcode like '%"+clientcode+"%' ");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and a.ordercode like '%"+ordercode+"%'");
			} 
			
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d %H:%i:%s')>= DATE_FORMAT('"+startDate+"','%Y-%m-%d %H:%i:%s')");
			}
			if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(b.orderdate,'%Y-%m-%d %H:%i:%s')<= DATE_FORMAT('"+endDate+"','%Y-%m-%d %H:%i:%s')");
			}
			
			
			//System.out.println(sal);
			logger.info("查询c_mar_record  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				C_stationeryRecord es =new C_stationeryRecord( 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall") , 
						"","","","",
						rs.getString("location"), 
						rs.getString("orderdate") ,
						rs.getString("clientname"),
						rs.getString("clientcode"),
						rs.getString("procname"));
				
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
	public int reprtPDF(String staffcode,String ordercode,  String startDate, String endDate ,String URL){

			String ordcodeStr = ordercode;
			Document document = new Document();// 建立一个Document对象
			document.setPageSize(PageSize.A4);// 设置页面大小
			String location="";
			try {
				/**
				 * 获取数据
				 */
	 			List<C_stationeryRecord> recordlist = reportOrderRecord(staffcode, ordcodeStr, startDate, endDate);
	 			C_stationeryOrder orderList = getOrder(ordcodeStr);
				if (Util.objIsNULL(orderList)) {
					return 0;
				}
				if (orderList.getLocation().trim().equals("Y")){
					location="@CONVOY";
				}else if(orderList.getLocation().trim().equals("C")){
					location="CP3";
				}else {
					location="CWC";
				}
				String urlString = "";
				String formType = "";
				String uprice = "";
				if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
					urlString = Constant.StationeryReport_URL_CONS;
					formType ="Stationery Requisition Form (Consultants Only)";
					uprice= "Unit Price(HK$)";
				}else {
					urlString = Constant.StationeryReport_URL_STAFF;
					formType ="Stationery Requisition Form (Staff Only)";
					uprice= "Price (HK$)";
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
					
					cell = new PdfPCell( new Paragraph(formType, headFont));
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
					String totalString="";
					String amt="";
					if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
						totalString=orderList.getPriceall()+"";
						amt = "Amt(HK$)";
					}else{
						totalString="0.00";
						amt = "Amt";
					}
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
					cell = new PdfPCell(new Paragraph(uprice, headFont)); 
					cell.setColspan(1);
					table.addCell(cell);
					//cell = new PdfPCell(new Paragraph("C-Club Price", headFont));
					//cell.setColspan(1);
					//table.addCell(cell);
					cell = new PdfPCell(new Paragraph("Quantity", headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(amt, headFont)); 
					cell.setColspan(1);
					table.addCell(cell);
				//动态加载数据
					for (int i = 0; i < recordlist.size(); i++) {
						C_stationeryRecord vo = (C_stationeryRecord)recordlist.get(i);
						cell = new PdfPCell(new Paragraph(vo.getProcode(),headFont));
						cell.setColspan(1);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(vo.getProname(),headFont));
						cell.setColspan(2);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(vo.getProcname(),headFont));
						cell.setColspan(2);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(vo.getPrice()+"",headFont));
						cell.setColspan(1);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(dFormat.format(vo.getQuantity())+"",headFont));
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
					cell = new PdfPCell( new Paragraph(totalString, headFont));
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
					
					cell = new PdfPCell( new Paragraph("Collection Location", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+location, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
					if (!orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
						cell = new PdfPCell( new Paragraph("Department", headFont));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
						cell.setBorder(0);
						cell.setColspan(2);// 设置合并单元格的列数
						table.addCell(cell);
						cell = new PdfPCell( new Paragraph(":"+orderList.getDeparthead(), headFont));
						cell.setBorder(0);
						cell.setColspan(6);// 设置合并单元格的列数
						table.addCell(cell);
					}
					if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
						cell = new PdfPCell( new Paragraph("Payment Method", headFont));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
						cell.setBorder(0);
						cell.setColspan(2);// 设置合并单元格的列数
						table.addCell(cell);
						cell = new PdfPCell( new Paragraph(":", headFont));
						cell.setBorder(0);
						cell.setColspan(6);// 设置合并单元格的列数
						table.addCell(cell);
						
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
						String Chequeno = Util.objIsNULL(orderList.getCheque_no())?"________________":orderList.getCheque_no();
						
						cell = new PdfPCell( new Paragraph(":$"+Chequenum+"     Banker:"+banker+"     Cheque No.:"+Chequeno, headFont));
						cell.setBorder(0);
						cell.setColspan(6);// 设置合并单元格的列数
						table.addCell(cell);
						
					}
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

					/*
					cell = new PdfPCell( new Paragraph("Amount Received by", headFont));
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
					
					if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
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
				logger.error("导出PDF——c_stationery_Order时出现 ："+DateUtils.getNowDateTime()+e.toString());
			}
			document.close();
			return 1;

		
	}

	public List<C_stationeryRecord> getRecordForUpd(String ordercode) {

		List<C_stationeryRecord> list=new ArrayList<C_stationeryRecord>();
		try{
			StringBuffer sal=new StringBuffer("SELECT * from c_stationery_consume_record where quantity>0 and ordercode = '"+ordercode+"' ");
		 
			logger.info("查询c_stationery_consume_record sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){   
				C_stationeryRecord es =new C_stationeryRecord( 
						rs.getInt("id"), 
						rs.getString("ordercode"), 
						rs.getString("procode"),
						rs.getString("proname"),
						rs.getString("procname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"), 
						rs.getDouble("priceall"), 
						rs.getString("remark1") , 
						rs.getString("remark2") , 
						rs.getString("remark3") , 
						rs.getString("remark4")  );
				 
				list.add(es);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_stationery_consume_record 时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	
	}
	public C_stationeryOrder getOrder(String ordercode) {
		// 
		C_stationeryOrder staOrder = new C_stationeryOrder();
		try{
			String sql="select * from c_stationery_order where ordercode=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, ordercode);
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				staOrder =new C_stationeryOrder(
						rs.getString("ordercode"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("location"), 
						rs.getString("departhead") ,
						rs.getString("sfyx"),
						rs.getString("staffOrCons") , 
						rs.getString("cash"),
						rs.getString("C_club"),
						rs.getString("cheque") , 
						rs.getString("cheque_no") , 
						rs.getString("banker") , 
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4")
						);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return staOrder;
	}
	public List<C_stationeryOrder> queryOrderListForPDF(String staffcode,
			String ordercode, String startDate, String endDate, int pageSize,
			int currentPage) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
		try{
			StringBuffer sal=new StringBuffer("select ordercode,clientname,clientcode,priceall," +
					" orderdate,(select name from location where realName = o.location) as location,departhead,sfyx,staffOrCons,remark1,remark2,remark3,remark4  from c_stationery_order o where " +
					" sfyx='Y' and clientcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  ordercode ='"+ordercode+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d %H:%i:%s') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d %H:%i:%s')");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and DATE_FORMAT(orderdate,'%Y-%m-%d %H:%i:%s') <=DATE_FORMAT('"+endDate+"','%Y-%m-%d %H:%i:%s')");
			}
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询c_stationery_order   sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_stationeryOrder es =new C_stationeryOrder(
						rs.getString("ordercode"), 
						rs.getString("clientname"), 
						rs.getString("clientcode") ,
						rs.getDouble("priceall"), 
						rs.getString("orderdate") , 
						rs.getString("location"), 
						rs.getString("departhead") ,
						rs.getString("sfyx") , 
						rs.getString("staffOrCons"),
						rs.getString("remark1"),
						rs.getString("remark2"),
						rs.getString("remark3"),
						rs.getString("remark4"));
				 
				list.add(es);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_stationery_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public int reprtPDFOnTime(String staffcode, String ordercode,
			String startDate, String endDate, String URL) {


		String ordcodeStr = ordercode;
		Document document = new Document();// 建立一个Document对象
		document.setPageSize(PageSize.A4);// 设置页面大小
		String location="";
		try {
			/**
			 * 获取数据
			 */
 			List<C_stationeryRecord> recordlist = reportOrderRecordForPDF(staffcode, ordcodeStr, startDate, endDate);
 			C_stationeryOrder orderList = getOrder(ordcodeStr);
			if (Util.objIsNULL(orderList)) {
				return 0;
			}
			/**
			 * 2014-7-7 12:30:42 wilson upd 
			 * if (orderList.getLocation().trim().equals("Y")){
				location="@CONVOY";
			}else if(orderList.getLocation().trim().equals("C")){
				location="CP3";
			}else {
				location="CWC";
			}*/
			location = orderList.getLocation().trim();
			
			String urlString = "";
			String formType = "";
			String uprice = "";
			if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
				urlString = Constant.StationeryReport_URL_CONS+DateUtils.afterDate(DateUtils.getDateToday())+"/";
				formType ="Stationery Requisition Form (Consultants Only)";
				uprice= "Unit Price(HK$)";
			}else {
				urlString = Constant.StationeryReport_URL_STAFF+DateUtils.afterDate(DateUtils.getDateToday())+"/";
				formType ="Stationery Requisition Form (Staff Only)";
				uprice= "Price (HK$)";
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
				
				cell = new PdfPCell( new Paragraph(formType, headFont));
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
				String totalString="";
				String amt="";
				if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
					totalString=orderList.getPriceall()+"";
					amt = "Amt(HK$)";
				}else{
					totalString="0.00";
					amt = "Amt";
				}
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
				cell = new PdfPCell(new Paragraph(uprice, headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
				//cell = new PdfPCell(new Paragraph("C-Club Price", headFont));
				//cell.setColspan(1);
				//table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Quantity", headFont));
				cell.setColspan(1);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(amt, headFont)); 
				cell.setColspan(1);
				table.addCell(cell);
			//动态加载数据
				for (int i = 0; i < recordlist.size(); i++) {
					C_stationeryRecord vo = (C_stationeryRecord)recordlist.get(i);
					cell = new PdfPCell(new Paragraph(vo.getProcode(),headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getProname(),headFont));
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getProcname(),headFont));
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(vo.getPrice()+"",headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(dFormat.format(vo.getQuantity())+"",headFont));
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
				cell = new PdfPCell( new Paragraph(totalString, headFont));
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
				
				cell = new PdfPCell( new Paragraph("Collection Location", headFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
				cell.setBorder(0);
				cell.setColspan(2);// 设置合并单元格的列数
				table.addCell(cell);
				cell = new PdfPCell( new Paragraph(":"+location, headFont));
				cell.setBorder(0);
				cell.setColspan(6);// 设置合并单元格的列数
				table.addCell(cell);
				if (!orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("Department", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+orderList.getDeparthead(), headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
				if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("Payment Method", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":", headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
					
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
					String Chequeno = Util.objIsNULL(orderList.getCheque_no())?"________________":orderList.getCheque_no();
					
					cell = new PdfPCell( new Paragraph(":$"+Chequenum+"     Banker:"+banker+"     Cheque No.:"+Chequeno, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
					
				}
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
				
				if (orderList.getStaffOrCons().trim().equals(Constant.ORDER_TYPE_Cons)) {
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
			logger.error("导出PDF——c_stationery_Order时出现 ："+DateUtils.getNowDateTime()+e.toString());
		}
		document.close();
		return 1;

	

	}
	public List<C_Payment> getRecordPayment(String ordercode,String type) {
		List<C_Payment> list=new ArrayList<C_Payment>();
		try{
			StringBuffer sal=new StringBuffer("SELECT * from c_payment p where type='"+type+"' and refno = '"+ordercode+"' " +
					"and exists(select * from c_stationery_order where p.refno = ordercode and status='Completed')");
		 
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
