package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.AdditionalDao;
import dao.EPaymentDao;
import entity.C_EPayment_List;
import entity.C_Epayment_Detail;
import entity.C_Epaymentt_Order;
import entity.C_Payment;
import entity.QueryAdditional;

public class EPaymentDaoImpl implements EPaymentDao{

	Logger logger = Logger.getLogger(C_GetMarkPremiumDaoImpl.class);
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
public int saveEPayment(String filename, InputStream os) {
		
		int num = 0;
		int beginRowIndex = 2;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		//此处不做删除
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行
				con = DBManager.getCon();
				/** 获取Excel里面的指定单元格数据* */
				HSSFCell codecell = row.getCell(0);
				HSSFCell cnamecell = row.getCell(1);
				HSSFCell isactivecell = row.getCell(2);
			 
				/** 给数据库里面的字段赋值* */
				String procode = Util.cellToString(codecell);
				String cname = Util.cellToString(cnamecell);
				String isactive = Util.cellToString(isactivecell);
				//double unitpricenum = Util.objIsNULL(unitprice)?0d:Double.valueOf(unitprice);
				//code不为空 
				if (!Util.objIsNULL(procode) ) {
					String sql =// "insert c_epayment_stock(mediacode,mediatype,medianame,price,quantity,unit,adddate,addname,remark,sfyx" +
									"insert c_epayment_stock(productcode,productname,price,quantity,unit,adddate,addname,remark,sfyx " +
							" ) values('"+procode+"','"+cname+"','0','0','','"+DateUtils.getNowDateTime()+"','','','Y')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						num++;
						logger.info("保存 c_epayment_stock OK ！");
						//更新标志为Y 则更新产品表中的数据
						 if (isactive.trim().equals(Constant.YXBZ_Y)) {
							
							 int acountnum = findAllByCode(procode);
							
							//有记录存在 则更新
								if (acountnum  > 0) { 
									int updnum =updProduct(procode,"", cname, 0d, 0D, "", "", "", "Y");
									logger.info("更新 c_epayment_list！"+updnum);
								
								}else {//无记录存在 则新增
									int addnum = saveProduct(procode,"", cname, 0d, 0D, "", "", "", "Y");
									logger.info("新增 c_epayment_list！"+addnum);
								}
							}else if (isactive.trim().equals(Constant.YXBZ_D)) {
								//状态为D 则删除
								
								int numa = delProduct(procode);
								if (numa > 0) {
									logger.info("删除 c_epayment_list！"+numa);
							}
						}
						
					} else {
						logger.info("保存 c_epayment_list 为空！");
					}
					
				} else {
					logger.info("c_epayment_list procode is null！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("c_epayment_list！" + e);
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}

	//批量上传epayment列表
	public int saveEPaymentList(List<List<Object>> list) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止提交事物
			
			for(int i=2;i<list.size();i++){
				List<Object> list2=list.get(i);
				
				String procode =(String) list2.get(0);
				String cname = (String) list2.get(1);
				String isactive = (String) list2.get(4);
				
			if(!Util.objIsNULL(list2.get(0)+"")){
				String sql ="insert c_epayment_stock(productcode,productname,price,quantity,unit,adddate,addname,remark,sfyx " +" ) " +
						"values('"+procode+"','"+cname+"','0','0','','"+DateUtils.getNowDateTime()+"','','','Y')";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
				num+=1;
				 if (isactive.trim().equals(Constant.YXBZ_Y)) {
					 int acountnum = findAllByCode(procode);
					 	//有记录存在 则更新
						if (acountnum  > 0) { 
							//int updnum =updProduct(procode,"", cname, 0d, 0D, "", "", "", "Y");
							String sql01="update c_epayment_list set productcode='"+procode+"',productname='"+cname+"'," +"price="+0d+",quantity='"+0D+"',unit=' ', adddate='"+DateUtils.getNowDateTime()+"' , addname=' ' , remark=' ' ,sfyx='Y' where productcode='"+procode+"' ";
							ps=con.prepareStatement(sql01);
							ps.executeUpdate();
							logger.info("更新 c_epayment_list！"+sql01);
						}else {//无记录存在 则新增
							//int addnum = saveProduct(procode,"", cname, 0d, 0D, "", "", "", "Y");
							String sql02 = "insert c_epayment_list(productcode,productname,price,quantity,unit,adddate,addname,remark,sfyx" +" ) values('"+
								procode+"','"+cname+"','0','0','','"+ //price,quantity,unit
								DateUtils.getNowDateTime()+"','','','Y')";
							ps = con.prepareStatement(sql02);
							int rs = ps.executeUpdate();
							if(rs<0){
								logger.info("upd c_epayment_list Product信息表失败");
							}
							logger.info("新增 c_epayment_list！"+sql02);
						}
						
						}else if (isactive.trim().equals(Constant.YXBZ_D)){
							//状态为D则删除
							//int numa = delProduct(procode);
							String sql03 = "update c_epayment_list set sfyx='D' where productcode ='"+procode+"'";
							ps = con.prepareStatement(sql03);
							ps.executeUpdate();
							logger.info("删除 c_epayment_list！"+sql03);
						}else {
							logger.info("保存 c_epayment_list 为空！");
						}
			}	
			}
			con.commit();//事物提交
			//num=list.size();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				con.rollback();//出异常时事物回滚
				num=0;
			}catch (Exception es) {
				es.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);//关闭连接
		}
		return num;
	}

	public int updProduct(String mediacode, String mediatype, String productname,Double  price, Double quantity,String unit,  String updname, String remark,   String sfyx) {
		int r = -1;
		Connection con=null;
		try {
			
			con=DBManager.getCon();
			String sql="update c_epayment_list set productcode='"+mediacode+"',productname='"+productname+"'," +
					"price="+price+",quantity='"+quantity+"',unit='"+
			unit+"', adddate='"+DateUtils.getNowDateTime()+"' , addname='"+updname+"' , remark='"+remark+"' ,sfyx='"+sfyx+"' where productcode='"+
			mediacode+"' ";
			logger.info("upd c_epayment_list Product信息表SQL:"+sql);
			
			ps=con.prepareStatement(sql);
			r=ps.executeUpdate();
			if(r<0){
				logger.info("upd c_epayment_list Product信息表失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("upd c_epayment_list Product信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("upd c_epayment_list Product信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
		
	}
/**
 * Wilson
 * 修改产品，
 * 累计数量
 */
public int saveProduct(String procode, String type, String cname,
		Double  unitpricenum, Double quantity,String unit,  String updname, String remark,   String sfyx) {
	Connection con=null;
	int r = -1;
	try {
		 
		 con=DBManager.getCon();
			String sql = "insert c_epayment_list(productcode,productname,price,quantity,unit,adddate,addname,remark,sfyx" +
				" ) values('"+
				procode+"','"+cname+"','0','0','','"+ //price,quantity,unit
				DateUtils.getNowDateTime()+"','','','Y')";
		ps = con.prepareStatement(sql);
		r = ps.executeUpdate();
		if(r<0){
			logger.info("upd c_epayment_list Product信息表失败");
		}
		 
			
	} catch (SQLException e) {
		e.printStackTrace();
		logger.error("upd c_epayment_list Product信息表异常！"+e);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		logger.error("upd c_epayment_list Product信息表异常！"+e);
	}
	finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return r;
		
}
	public int findAllByCode(String procode) {
		int num=-1;
		Connection con=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select * from c_epayment_list where productcode ='"+procode+"'");
		
			logger.info("Query c_epayment_list SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("c_epayment_list 中根据条件查询数据个数时出现异常："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public int delProduct(String procode) {
		Connection con = null;
		PreparedStatement ps = null;
		int num =0;
		String sql = "update c_epayment_list set sfyx='D' where productcode ='"+procode+"'";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			num = ps.executeUpdate();
			//System.out.println("=====del========c_epayment_list============" + num);
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

	/**
	 * 分页查询  订单信息
	 */
	public List<C_Epaymentt_Order> queryEpaymentt_Order(String staffcode, String ordercode,String startDate, String endDate, String staffname,String userType, 
			String location, String status, int currentPage,int pageSize) {
		
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		List<C_Epaymentt_Order> list=new ArrayList<C_Epaymentt_Order>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_epayment_order where sfyx='Y'" +
					" and staffcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  refno like '%"+ordercode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  usertype ='"+userType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  location = '"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status = '"+status+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(createdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(createdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			sal.append(" order by createdate desc  limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  C_Payment  sql:===="+sal.toString());
			//System.out.println(sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_Epaymentt_Order es =new C_Epaymentt_Order(
						rs.getString("refno"), 
						rs.getString("staffcode"), 
						rs.getString("staffname"), 
						rs.getString("usertype"), 
						rs.getString("location") , 
						rs.getString("createdate"), 
						rs.getString("creater"), 
						rs.getString("status") ,
						rs.getString("sfyx") ,
						rs.getString("remark"));
				 
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

	/**
	 * 获取订单信息总数
	 */
	public int getC_PaymentCount(String staffcode, String ordercode,String startDate, String endDate, String staffname,
			String userType, String location, String status) {
		
		int num=-1;
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from c_epayment_order" +
					" where  sfyx='Y' and staffcode like '%"+clientcode+"%'");
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  refno like '%"+ordercode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  userType ='"+userType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  location = '"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  status = '"+status+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(createdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(createdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
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
			logger.error("查询C_Epaymentt_Order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * 查询明细   Order表
	 */
	public List<C_Epaymentt_Order> queryOrderDetial(String refno) {
		ResultSet rs= null;
		List<C_Epaymentt_Order> list = new ArrayList<C_Epaymentt_Order>();
		try {
			con = DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select *from c_epayment_order");
			stringBuffer.append(" where refno= '"+refno+"'");
		
			ps=con.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			  while(rs.next()){
				  C_Epaymentt_Order rsBean = new C_Epaymentt_Order();
				  	rsBean.setRefno(rs.getString(1));
					rsBean.setStaffcode(rs.getString(2));
					rsBean.setStaffname(rs.getString(3));
					rsBean.setUsertype(rs.getString(4));
					rsBean.setLocation(rs.getString(5));
					rsBean.setCreatedate(rs.getString(6));
					rsBean.setCreater(rs.getString(7));
					rsBean.setStatus(rs.getString(8));
					rsBean.setSfyx(rs.getString(9));
					rsBean.setRemark(rs.getString(10));
					list.add(rsBean);
			  	}	  
			  rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY C_Epaymentt_Order ERROR!"+e);
		}finally{ 
			DBManager.closeCon(con);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}

	/**
	 * 查询ePayment
	 */
	public List<C_Payment> queryPayment(String refno) {
		ResultSet rs= null;
		List<C_Payment> list = new ArrayList<C_Payment>();
		try {
			con = DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select *from C_Payment");
			stringBuffer.append(" where refno= '"+refno+"'");
		
			ps=con.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			  while(rs.next()){
				  C_Payment rsBean = new C_Payment();
				  	rsBean.setPaymentId(Integer.parseInt(rs.getString(1)));
				  	rsBean.setRefno(rs.getString(2));
					rsBean.setStaffname(rs.getString(3));
					rsBean.setLocation(rs.getString(4));
					rsBean.setType(rs.getString(5));
					rsBean.setSaleno(rs.getString(6));
					rsBean.setPaymentMethod(rs.getString(7));
					rsBean.setPaymentAount(Double.parseDouble(rs.getString(8)));
					rsBean.setPaymentDate(rs.getString(9));
					rsBean.setHandleder(rs.getString(10));
					rsBean.setCreator(rs.getString(11));
					rsBean.setCreateDate(rs.getString(12));
					rsBean.setSfyx(rs.getString(13));
					list.add(rsBean);
			  	}	  
			  rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY Payment ERROR!"+e);
		}finally{ 
			DBManager.closeCon(con);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 查询Detail
	 */
	public List<C_Epayment_Detail> queryDetail(String refno) {
		ResultSet rs= null;
		List<C_Epayment_Detail> list = new ArrayList<C_Epayment_Detail>();
		try {
			con = DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select *from C_Epayment_Detail");
			stringBuffer.append(" where refno= '"+refno+"'");
		
			ps=con.prepareStatement(stringBuffer.toString());
			rs=ps.executeQuery();
			  while(rs.next()){
				  C_Epayment_Detail rsBean = new C_Epayment_Detail();
				  	rsBean.setDetailid(Integer.parseInt(rs.getString(1)));
				  	rsBean.setRefno(rs.getString(2));
					rsBean.setProductcode(rs.getString(3));
					rsBean.setProductname(rs.getString(4));
					rsBean.setPrice(Double.parseDouble(rs.getString(5)));
					rsBean.setQuantity(Double.parseDouble(rs.getString(6)));
					list.add(rsBean);
			  	}	  
			  rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY Epayment_Detail ERROR!"+e);
		}finally{ 
			DBManager.closeCon(con);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 完成订单
	 */
	public int completed(String type,String user,String refno, String status, String staffname,String staffcodes, C_Payment payment) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);    //禁止提交事物
			String sql="update c_epayment_order set status=? , staffcode=?,staffname=? ,location=? where refno=?";
			 ps=con.prepareStatement(sql);
				ps.setString(1, status);
				ps.setString(2, staffcodes);
				ps.setString(3, staffname);
				ps.setString(4, payment.getLocation());
				ps.setString(5, refno);
			 ps.execute();
			
			sql="insert c_epayment_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+status+"','"+user+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			ps.execute();
			
			sql="insert c_payment(refno,staffname,location,type,saleno,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx)values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			ps.setString(1,refno);
			ps.setString(2, payment.getStaffname()); 
			ps.setString(3, payment.getLocation());
			ps.setString(4, type);//epayment
			ps.setString(5, payment.getSaleno());
			ps.setString(6, payment.getPaymentMethod());
			ps.setDouble(7, payment.getPaymentAount());
			ps.setString(8, payment.getPaymentDate());
			ps.setString(9, payment.getHandleder());
			ps.setString(10, payment.getCreator());
			ps.setString(11, DateUtils.getNowDateTime());
			ps.setString(12, payment.getSfyx());
		
			ps.execute();
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

	/**
	 * 取消订单
	 */
	public int VOID(String refno, String user) {
		int r = -1;
		try {
			 con=DBManager.getCon();
			 con.setAutoCommit(false);//禁止自动提交
			 String sql="update c_epayment_order set sfyx='N',status='VOID',remark ='"+DateUtils.getNowDateTime()+"' where refno='"+refno+"' ";
			 logger.info("VOID==>upd c_epayment_order信息表SQL:"+sql);
			 ps=con.prepareStatement(sql);
			 r=ps.executeUpdate();
			
			 sql="insert c_epayment_operation(refno,operationType,operationName,operationDate)values('"+refno+"','VOID','"+user+"','"+DateUtils.getNowDateTime()+"');";
			 logger.info("VOID==>upd c_epayment_operation信息表SQL:"+sql);	
			 ps=con.prepareStatement(sql);
			 ps.execute();

			 ps.executeBatch();
			 con.commit();
			
			if(r<0){
				logger.info("VOID c_epayment_order信息表失败");
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			r=0;
			try {
				logger.error("VOID c_epayment_order信息表数据回滚！"+e);
				con.rollback();
				
			} catch (SQLException e1) {
				logger.error("VOID c_epayment_order信息表数据回滚异常！"+e);
				e1.printStackTrace();
			}
			logger.error("VOID c_epayment_order信息表异常！"+e);
		} finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}

	public List<C_EPayment_List> queryRequestList() {
		ResultSet rs=null;
		List<C_EPayment_List> list = new ArrayList<C_EPayment_List>();
		try {
			con = DBManager.getCon();
			String sql="select *from c_epayment_list";
	        ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			  while(rs.next()){
				  C_EPayment_List rsBean = new C_EPayment_List();
				  	rsBean.setId(Integer.parseInt(rs.getString(1)));
					rsBean.setProductcode(rs.getString(2));
					rsBean.setProductname(rs.getString(3));
					rsBean.setPrice(Double.parseDouble(rs.getString(4)));
					rsBean.setQuantity(Double.parseDouble(rs.getString(5)));;
					rsBean.setUnit(rs.getString(6));
					rsBean.setAdddate(rs.getString(7));
					rsBean.setAddname(rs.getString(8));
					rsBean.setRemark(rs.getString(9));
					rsBean.setSfyx(rs.getString(10));
					list.add(rsBean);
			  	}	  
			  rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY C_EPayment_List ERROR!"+e);
		}finally{ 
			DBManager.closeCon(con);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int savePayment(C_Epaymentt_Order epayment,
			List<C_Epayment_Detail> detail) {
		int num=-1;
		try{
			synchronized (this) {
				String refno=findref();
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
				else
					epayment.setRefno(refno);
			}
			
			con=DBManager.getCon();
			con.setAutoCommit(false);	//禁止自动提交事务
	
			C_Epayment_Detail product = null;
			for(int i=0;i<detail.size();i++){
				product = (C_Epayment_Detail)detail.get(i);
				String sql="insert into c_epayment_detail(refno,productcode,productname,price,quantity) values (?,?,?,?,?)";
				ps=con.prepareStatement(sql);
				ps.setString(1,epayment.getRefno());
				ps.setString(2,product.getProductcode());
				ps.setString(3,product.getProductname());
				ps.setDouble(4,product.getPrice());
				ps.setDouble(5,product.getQuantity());
				num=ps.executeUpdate();
			}
			
			String sql="insert c_epayment_order(refno,staffcode,staffname,userType,location,createdate,creater,status,sfyx,remark) values (?,?,?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			ps.setString(1,epayment.getRefno());
			ps.setString(2,epayment.getStaffcode());
			ps.setString(3, epayment.getStaffname());
			ps.setString(4,epayment.getUsertype());
			ps.setString(5,epayment.getLocation());
			ps.setString(6, epayment.getCreatedate());
			ps.setString(7, epayment.getCreater());
			ps.setString(8, epayment.getStatus());
			ps.setString(9, epayment.getSfyx());
			ps.setString(10, epayment.getRemark());
			num=ps.executeUpdate();
			
			
			
			
			con.commit();
			num=detail.size();
		
			
		}catch(Exception e){
			e.printStackTrace();
			num=0;
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
	  * 获取订单编号
	  * @return
	  */
	public String findref(){
		String num=null;
		ResultSet rs=null;
		try{
			con=DBManager.getCon();
			String sql="select count(*) from c_epayment_order";
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
				num=Constant.EPAYMENT+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	/**
	 * 通过历史记录条数自动生成订单号
	 * @param nums
	 * @return
	 */
	public String findref(int nums){
		String num="";
		try{
				if(nums<9){
					num="000"+(nums+1);
				}else if(nums<99){
					num="00"+(nums+1);
				}else if(nums<999){
					num="0"+(nums+1);
				}else{
					num=""+(nums+1);
				}
				num=Constant.EPAYMENT+DateUtils.Ordercode()+num;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return num;
	}
	
	
	
	public ResultSet queryEpaymentt_Order(String staffcode, String ordercode,String startDate, String endDate, String staffname,String userType, 
			String location, String status) {
		
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		ResultSet rs= null;
		try{
			StringBuffer sal=new StringBuffer("select a.refno,a.staffcode,a.staffname,a.location,b.productcode,b.productname,c.paymentMethod,c.paymentAount,c.paymentDate,c.Handleder,a.status  from c_epayment_order a "); 
			sal.append(" left join c_epayment_detail b on(a.refno=b.refno) ");
			sal.append(" left join c_payment c on(a.refno=c.refno) ");
			sal.append(" where a.sfyx='Y'  and a.staffcode like '%"+clientcode+"%'"); 
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  a.refno like '%"+ordercode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  a.staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  a.usertype ='"+userType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  a.location = '"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  a.status = '"+status+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(a.createdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(a.createdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			logger.info("导出E-Payment  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出E-Payment时出现 ："+e.toString());
		}finally{
		}
		return rs;
	}
	
	public List<String []> queryEpaymentt_Order2(String staffcode, String ordercode,String startDate, String endDate, String staffname,String userType, String location, String status) {
		String clientcode = Util.objIsNULL(staffcode)?"":staffcode;
		ResultSet rs= null;
		List<String []>list=new ArrayList<String[]>();
		try{
			StringBuffer sal=new StringBuffer("select a.refno,a.staffcode,a.staffname,a.location,b.productcode,b.productname,c.paymentMethod,c.paymentAount,c.paymentDate,c.Handleder,a.status  from c_epayment_order a "); 
			sal.append(" left join c_epayment_detail b on(a.refno=b.refno) ");
			sal.append(" left join c_payment c on(a.refno=c.refno) ");
			sal.append(" where a.sfyx='Y'  and a.staffcode like '%"+clientcode+"%'"); 
			if(!Util.objIsNULL(ordercode)){
				sal.append(" and  a.refno like '%"+ordercode+"%'");
			}
			if(!Util.objIsNULL(staffname)){
				sal.append(" and  a.staffname like '%"+staffname+"%'");
			}
			if(!Util.objIsNULL(userType)){
				sal.append(" and  a.usertype ='"+userType+"'");
			}
			if(!Util.objIsNULL(location)){
				sal.append(" and  a.location = '"+location+"'");
			}
			if(!Util.objIsNULL(status)){
				sal.append(" and  a.status = '"+status+"'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(a.createdate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(a.createdate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			logger.info("导出E-Payment  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					list.add(new String []{
							rs.getString("refno"),
							rs.getString("staffcode"),
							rs.getString("staffname"),
							rs.getString("location"),
							rs.getString("productcode"),
							rs.getString("productname"),
							rs.getString("paymentMethod"),
							rs.getString("paymentAount"),
							rs.getString("paymentDate"),
							rs.getString("Handleder"),
							rs.getString("status")
					});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出E-Payment时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * 分页查询产品信息
	 */
	public List<C_EPayment_List> queryProduct(String productcode,String productname, String startDate, String endDate, String sfyx,
			int currentPage, int pageSize) {

		List<C_EPayment_List> list=new ArrayList<C_EPayment_List>();
		try{
			StringBuffer sal=new StringBuffer("select * from c_epayment_list where productcode like '%"+productcode+"%'");
			if(!Util.objIsNULL(productname)){
				sal.append(" and  productname like '%"+productname+"%'");
			}
			if(!Util.objIsNULL(sfyx)){
				sal.append(" and  sfyx like '%"+sfyx+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(adddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(adddate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			sal.append(" order by productcode desc  limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  C_EPayment_List  sql:===="+sal.toString());
			//System.out.println(sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				C_EPayment_List cl=new C_EPayment_List(
						rs.getInt("id"),
						rs.getString("productcode"),
						rs.getString("productname"),
						rs.getDouble("price"),
						rs.getDouble("quantity"),
						rs.getString("unit"),
						rs.getString("adddate"),
						rs.getString("addname"),
						rs.getString("remark"),
						rs.getString("sfyx"));
			
				list.add(cl);
				}
			//System.out.println("查询产品  ："+sal);
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询c_mar_order时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	/**
	 * 获取产品总条数
	 */
	public int getNum(String productcode, String productname, String startDate,String endDate, String sfyx) {
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select * from c_epayment_list where productcode like '%"+productcode+"%'");
			if(!Util.objIsNULL(productname)){
				sal.append(" and  productname like '%"+productname+"%'");
			}
			if(!Util.objIsNULL(sfyx)){
				sal.append(" and  sfyx like '%"+sfyx+"%'");
			}
			if(!Util.objIsNULL(startDate)){
				sal.append(" and  DATE_FORMAT(adddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and  DATE_FORMAT(adddate,'%Y-%m-%d')  <=DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
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
			logger.error("查询C_Epaymentt_Order 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	
	public String saveEpaymentRequest(List<List<Object>> list,String user) {
		int num=0;
		int line=0;
		//Connection conn=null;
		String refno="";
		String productname="";
		String productcode="";
		String price="";
		String location="";
		String xx="";
		Connection connection=null;
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			PreparedStatement ps2=null;
			//conn=DBManager.getCon();
			String sql="";
			for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
				//System.out.println(list2.size()+"-----------------------list.size");
			if(!Util.objIsNULL(list2.get(0)+"")){
				if(list2.size()<=6){ //-->Excel模板一
					line=i;
					synchronized (this) {
						String sql2="select count(*) from c_epayment_order";
						ps2=connection.prepareStatement(sql2);
						rs=ps2.executeQuery();
						if(rs.next()){
							//System.out.println(rs.getString(1));
							refno=findref(Integer.parseInt(rs.getString(1)));
						}
						rs.close();
						if(Util.objIsNULL(refno)){
							throw new Exception("流水号产生异常");
						}
					}
					sql="insert into c_epayment_order(refno,staffcode,staffname,usertype,location,createdate,creater,status,sfyx,remark)" +
							"values('"+refno+"','"+list2.get(0)+"','"+list2.get(1)+"','Staff','"+list2.get(2)+"','"+DateUtils.getNowDateTime()+"','"+user+"','Submitted','Y','')";
					ps2=connection.prepareStatement(sql);
					//System.out.println(sql.toString()+"----");
					
					num+=ps2.executeUpdate();
					if(num<(i+0)){
						throw new RuntimeException();
					}
					String sql3="select productname from c_epayment_list where productcode='"+list2.get(3)+"' ";
					ps2=connection.prepareStatement(sql3);
					rs=ps2.executeQuery();
					if(rs.next()){
						productname=rs.getString(1);
					}
					rs.close();
					//System.out.println("产品名称--->"+productname);
					
					sql="insert into c_epayment_detail(refno,productcode,productname,price,quantity) " +
							"values('"+refno+"','"+list2.get(3)+""+"','"+productname+""+"','"+list2.get(4)+""+"','1')";
					ps2=connection.prepareStatement(sql);
					num+=ps2.executeUpdate();
					if(num<(i+1)){
						throw new RuntimeException();
					}
					
				}else{ 	//-->Excel模板二
					line=i;
					synchronized (this) {
						String sql2="select count(*) from c_epayment_order";
						ps2=connection.prepareStatement(sql2);
						rs=ps2.executeQuery();
						if(rs.next()){
							//System.out.println(rs.getString(1));
							refno=findref(Integer.parseInt(rs.getString(1)));
						}
						rs.close();
						if(Util.objIsNULL(refno)){
							throw new Exception("流水号产生异常");
						}
					}
					
					if(!Util.objIsNULL(list2.get(2))){
						location= list.get(0).get(2).toString();
					}else if(!Util.objIsNULL(list2.get(3))){
						location= list.get(0).get(3).toString();
					}else if(!Util.objIsNULL(list2.get(4))){
						location= list.get(0).get(4).toString();
					}else if(!Util.objIsNULL(list2.get(5))){
						location= list.get(0).get(5).toString();
					}else if(!Util.objIsNULL(list2.get(6))){
						location= list.get(0).get(6).toString();
					}
					//System.out.println("location------->"+location);
					sql="insert into c_epayment_order(refno,staffcode,staffname,usertype,location,createdate,creater,status,sfyx,remark)" +
							"values('"+refno+"','"+list2.get(0)+"','"+list2.get(1)+"','Staff','"+location+"','"+DateUtils.getNowDateTime()+"','"+user+"','Submitted','Y','')";
					ps2=connection.prepareStatement(sql);
					//System.out.println(sql.toString()+"----");
					
					num+=ps2.executeUpdate();
					if(num<(i+0)){
						throw new RuntimeException();
					}
					if(!Util.objIsNULL(list2.get(7))){
						productcode=list.get(0).get(7).toString();
						price=list2.get(7)+"";
					}else{
						productcode=list.get(0).get(8).toString();
						price=list2.get(8)+"";
					}
					//System.out.println("productcode--------->"+productcode);
					
					String sql3="select productname from c_epayment_list where productcode='"+productcode+"' ";
					ps2=connection.prepareStatement(sql3);
					rs=ps2.executeQuery();
					if(rs.next()){
						productname=rs.getString(1);
					}
					rs.close();
					//System.out.println("产品名称--->"+productname);
					
					sql="insert into c_epayment_detail(refno,productcode,productname,price,quantity) " +
							"values('"+refno+"','"+productcode+""+"','"+productname+""+"','"+price+""+"','1')";
					ps2=connection.prepareStatement(sql);
					num+=ps2.executeUpdate();
					if(num<(i+1)){
						throw new RuntimeException();
					}
				}
			}	
			
			
			}
			connection.commit();
			xx=Util.getMsgJosnObject("success", "成功条数:"+num/2+"");
		}catch (Exception e) {
			//String xx=Util.jointException(e);
			xx=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(connection);
		}
		return xx;
		
	}
}
