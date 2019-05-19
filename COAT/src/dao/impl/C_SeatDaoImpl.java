package dao.impl;

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
import util.Page;
import util.SendMail;
import util.Util;
import dao.C_SeatDao;
import dao.QueryStaffRequstDao;
import entity.C_Seatassignment;
import entity.C_SeatassignmentOperation;

/**
 * C_SearDaoImpl
 * @author Wilson
 * 2014-5-14 15:23:35
 * 
 */
public class C_SeatDaoImpl implements C_SeatDao {
	Logger logger = Logger.getLogger(C_SeatDaoImpl.class);
	/**
	 * 保存C_SearDaoImpl表
	 */
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	DecimalFormat dFormat= new DecimalFormat("##.##");
	/**
	 * 保存上传信息
	 */
	public String saveSeat(String filename, InputStream os,String basePath) {
		int num=0;
		String uploadmsg="upload Over.";
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		//Util.deltables("");/*           等待填写                      */
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell StaffNocell = row.getCell(0);
				HSSFCell Staffnamecell = row.getCell(1);
				HSSFCell seatnovell=row.getCell(2);
				HSSFCell extnovell=row.getCell(3);
				HSSFCell locationcell=row.getCell(4);
				HSSFCell pigenBoxnovell=row.getCell(5);//Chicken Box No.
				HSSFCell deskDrawernovell=row.getCell(6);
				HSSFCell lockernovell=row.getCell(7);
				HSSFCell Emailadd=row.getCell(8);
				HSSFCell Recruiter=row.getCell(9);
				HSSFCell Recruiteremail=row.getCell(10);
				HSSFCell remarkCell=row.getCell(11);
				/**给数据库里面的字段赋值**/
				String staffcode = Util.cellToString(StaffNocell);
				String staffname =Util.cellToString(Staffnamecell);
				String seatno =Util.cellToString(seatnovell);
				String extno =Util.cellToString(extnovell);
				String locationcel =Util.cellToString(locationcell);
				String floor ="";
				String location ="";
				if (!Util.objIsNULL(locationcel.trim())) {
					floor = locationcel.trim().substring(0, locationcel.indexOf("F")+1).replace("/", "");
					location = locationcel.trim().substring(locationcel.indexOf("F")+2,locationcel.length());
					if (location.indexOf("Cityplaza")==0) {
						location="CP3";
					}
				}
				num++;
				String pigenBoxno=Util.cellToString(pigenBoxnovell);
				String deskDrawerno=Util.cellToString(deskDrawernovell);
				String lockerno=Util.cellToString(lockernovell);
				String remark=Util.cellToString(remarkCell);
				String date = DateUtils.getNowDateTime();
				//判断是否重复
				if (hasSeat(staffcode) !="" ) {
					uploadmsg +="On "+num+" Line,Staff Code : "+staffcode+" Effe! \r\n";
					logger.info(staffcode+"上传重复！");
					continue;
				}
				/**
				 * 貌似没能全部验证 
				 * 貌似没能全部验证 
				 */
				if(!Util.objIsNULL(location) || !Util.objIsNULL(extno) ||
						!Util.objIsNULL(floor) ||!Util.objIsNULL(seatno) ||
						!Util.objIsNULL(lockerno) || !Util.objIsNULL(deskDrawerno) || !Util.objIsNULL(pigenBoxno) ||
						!Util.objIsNULL(staffcode) || staffcode.length()==6){
					//订单号生成
					String refno="";
					synchronized (this) {
						refno=findref();
						if(Util.objIsNULL(refno))
							throw new Exception("流水号产生异常");
					}
					con = DBManager.getCon();
					con.setAutoCommit(false);
					sql = "insert c_seatassignment (refno,staffcode,staffname," +
							"location,extensionno,floor,seatno,lockerno,deskDrawerno," +
							"pigenBoxno,remark,creator,creatDate,status,sfyx" +
							") values('"+refno+"','"+staffcode+"','"+staffname+"','"+
							location+"','"+extno+"','"+floor+"','"+seatno+"','"+
							lockerno+"','"+deskDrawerno+"','"+pigenBoxno+"','"+remark+"','system','"+date+"','"+Constant.C_Submitted+"','Y')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						try{
							String staffEmail=Util.cellToString(Emailadd);
							String recruiterEmail=Util.cellToString(Recruiteremail);
							String body="Dear Sir/Madam,<br/><br/>"
							+"         Please Be advised that the following seat arrangement will be effective from next Monday.<br/><br/>"
							+"<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;font-color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
							+"<tr><td>"+staffcode+"</td><td>"+staffname+"</td><td>"+seatno+"</td><td>"+pigenBoxno+"</td><td>"+extno+"</td><td>"+floor+" "+location+"</td></tr></table><br/><br/>"
							+"ADM will notify you by email again for key collection";
							//+"<br/><br/>Best Regards,<br/>"
							//+"Administration<br/>"
							//+"Operations Department";
							String result=SendMail.send("COAT Request Notice", staffEmail+";"+recruiterEmail, "adminfo@convoy.com.hk", null, null, body, "COAT",  "email.ftl", "Best Regards,<br/>Administration<br/>Operations Department");
							JSONObject json=new JSONObject(result);
							if(!"success".equalsIgnoreCase(json.get("state")+"")){
								throw new Exception(json.getString("msg"));
							}
							
							
							
							
							
							
							
							
							/**
							String parmString="to="+staffEmail+";"+recruiterEmail+"&" +
							"cc=adminfo@convoy.com.hk&" +
							//"cc=King.Xu@convoy.com.hk&" +
							"subject=COAT Request Notice&" +
							"webapp=COAT&" +
							"body="+body;
							
							//"http://192.168.224.200/ExchangeMail/SendMailServlet"
							System.out.println(basePath);
							htp=(HttpURLConnection)new URL(basePath+"/ExchangeMail/SendMailServlet").openConnection();
							htp.setDoOutput(true);
							htp.setRequestMethod("POST");
							htp.setUseCaches(false);
							htp.setInstanceFollowRedirects(true);
							htp.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
							OutputStream tarparm=htp.getOutputStream();
						
						
							System.out.println("Client----第"+(i+1)+"次调用Servlet");
							tarparm.write((parmString).getBytes());//传递参数
							tarparm.flush();
							tarparm.close();
							
						      InputStreamReader isr=new InputStreamReader(htp.getInputStream());
					            BufferedReader br=new BufferedReader(isr);
					            
					           if(br.ready()) {
									System.out.println("Client-----获取返回结果：==="+br.readLine());
									System.out.println(staffcode+"-"+br.readLine()+"\r\n");
								}
					           htp.disconnect(); 
							**/
							
						con.commit();
						logger.info("插入Seat表成功！");
					}catch (Exception e) {
						e.printStackTrace();
						con.rollback();
						logger.info("插入Seat表On "+num+" Line,Staff Code : "+staffcode+"==>异常"+e.getMessage());
						uploadmsg +="On "+num+" Line,Staff Code : "+staffcode+" -->Email Error! \r\n";
					}
						//未保存上传记录表
						//未保存上传记录表
						//未保存上传记录表
					}else {
						uploadmsg +="On "+num+" Column，Staff Code ："+staffcode+"Upload Error！ </BR>";
						logger.info(staffcode+"上传保存失敗！");
					}
				}else {
					uploadmsg +="On "+num+" Column，Staff Code ："+staffcode+" Upload Error！</BR>";
					logger.info(staffcode+"上传保存失敗！");
				}
			}
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入keys表异常！"+e);
		} finally {
			
			
			//关闭连接
			DBManager.closeCon(con);
		}
		System.out.println(uploadmsg);
		return uploadmsg;
	}
	/**
	 * 该staffcode在指定日期是否请假
	 */
	public String hasSeat(String staffcode) {
		String nums="";
		 try{
			 con = DBManager.getCon();
			 sql = "select staffcode from c_seatassignment where sfyx='Y' and staffcode='"+staffcode+"' ";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("staffcode");
				}else{
					nums="";
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询c_seatassignment表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
	}
	 /**
	  * 获取订单编号
	  * @return
	  */
	public String findref(){
		String num=null;
		try{
			con=DBManager.getCon();
			String sql="select count(*) from c_seatassignment";
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
				num="SA"+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public int complete(C_Seatassignment cSeatassignment ) {
		int num =0;
		try{
			//订单号生成
			String refno=findref();
			synchronized (this) {
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
			}
			cSeatassignment.setRefno(refno);
			
			num =  saveSeatRecord(cSeatassignment);
			if(num > 0){
				return num;
			 }else{//保存消费记录表失败
				 logger.error("在保存Seat 订单详细时出现异常  ===REF.NO.："+refno);
				 return num;
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("save Seat Assignment出现异常  ===："+e);
		}finally{
			 
		}
		return num;
	}
	
	public String saveSeat(C_Seatassignment seatassignment){
		String result="";
		try{
			//订单号生成
			String refno=findref();
			synchronized (this) {
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
			}
			con=DBManager.getCon();
			con.setAutoCommit(false);
			PreparedStatement pr=null;
			seatassignment.setRefno(refno);
			String	sql="insert c_seatassignment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
			pr=con.prepareStatement(sql);
			pr.setString(1, seatassignment.getRefno());
			pr.setString(2, seatassignment.getStaffcode() ); 
			pr.setString(3, seatassignment.getStaffname()); 
			pr.setString(4, seatassignment.getLocation());
			pr.setString(5, seatassignment.getExtensionno());
			pr.setString(6, seatassignment.getFloor());
			pr.setString(7, seatassignment.getSeatno());
			pr.setString(8, seatassignment.getLockerno());
			pr.setString(9, seatassignment.getDeskDrawerno());
			pr.setString(10, seatassignment.getPigenBoxno());
			pr.setString(11, seatassignment.getRemark());
			pr.setString(12, seatassignment.getCreator());
			pr.setString(13, seatassignment.getCreatDate());
			pr.setString(14, seatassignment.getStatus());
			pr.setString(15, seatassignment.getSfyx());
			logger.info(seatassignment.getRefno()+"在Seat Operation save成功！");
		int	num = pr.executeUpdate();
		if(num>0){
			QueryStaffRequstDao qsr=new QueryStaffRequstDaoImpl();
			String staffemail=qsr.findEmailByStaff(seatassignment.getStaffcode(),"");
			if(Util.objIsNULL(staffemail)){
				throw new Exception("Staff email address does not exist");
			}
			String recruiterEmail=qsr.findRecruiterEmailByStaff(seatassignment.getStaffcode());
			if(Util.objIsNULL(recruiterEmail)){
				throw new Exception("Recruiter email address does not exist");
			}
			String body="Dear Sir/Madam,<br/><br/>"
			+"         Please Be advised that the following seat arrangement will be effective from next Monday.<br/><br/>"
			+"<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
			+"        <table id='xx' cellpadding=0 cellspacing=0 >"
			+"<tr id='first' style='background-color:black;font-color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
			+"<tr><td>"+seatassignment.getStaffcode()+"</td><td>"+seatassignment.getStaffname()+"</td><td>"+seatassignment.getSeatno()+"</td><td>"+seatassignment.getPigenBoxno()+"</td><td>"+seatassignment.getExtensionno()+"</td><td>"+ seatassignment.getFloor()+" "+seatassignment.getLocation()+"</td></tr></table><br/><br/>"
			+"ADM will notify you by email again for key collection";
			//+"<br/><br/>Best Regards,<br/>"
			//+"Administration<br/>"
			//+"Operations Department";
			 result=SendMail.send("COAT Request Notice", staffemail+";"+recruiterEmail, "adminfo@convoy.com.hk", null, null, body, "COAT",  "email.ftl", "Best Regards,<br/>Administration<br/>Operations Department");
			JSONObject json=new JSONObject(result);
			if(!"success".equalsIgnoreCase(json.get("state")+"")){
				throw new Exception(json.getString("msg"));
			}
		}
			
			
			
			
			con.commit();
			logger.info(seatassignment.getRefno()+"在Seat Operation save成功！");
		}catch (Exception e) {
			result=Util.jointException(e);
			logger.info(seatassignment.getRefno()+"在Seat Operation save异常！"+e.getMessage());
		}finally{
			DBManager.closeCon(con);
		}
		return result;
	}
	
	
	

	public int saveSeatRecord(C_Seatassignment seatassignment) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_seatassignment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=(Connection) DBManager.getCon();
			pr=con.prepareStatement(sql);
			pr.setString(1, seatassignment.getRefno());
			pr.setString(2, seatassignment.getStaffcode() ); 
			pr.setString(3, seatassignment.getStaffname()); 
			pr.setString(4, seatassignment.getLocation());
			pr.setString(5, seatassignment.getExtensionno());
			pr.setString(6, seatassignment.getFloor());
			pr.setString(7, seatassignment.getSeatno());
			pr.setString(8, seatassignment.getLockerno());
			pr.setString(9, seatassignment.getDeskDrawerno());
			pr.setString(10, seatassignment.getPigenBoxno());
			pr.setString(11, seatassignment.getRemark());
			pr.setString(12, seatassignment.getCreator());
			pr.setString(13, seatassignment.getCreatDate());
			pr.setString(14, seatassignment.getStatus());
			pr.setString(15, seatassignment.getSfyx());
			
			logger.info(seatassignment.getRefno()+"在Seat Operation save成功！");
			num = pr.executeUpdate();
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存Seat Operation异常："+e);
			return 0;
		}finally{
		}
		return num;
	}
	/**
	 * add方法COAT Seat 操作记录
	 */
	public int saveSeatOpreation(C_SeatassignmentOperation seatOpreation) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_seatassignment_operation (refno,operationType,operationName,operationDate) values(?,?,?,? )";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=(Connection) DBManager.getCon();
			pr=con.prepareStatement(sql);
			pr.setString(1, seatOpreation.getRefno());
			pr.setString(2, seatOpreation.getOperationType() ); 
			pr.setString(3, seatOpreation.getOperationName()); 
			pr.setString(4, seatOpreation.getOperationDate());
			
			logger.info(seatOpreation.getRefno()+"在Seat Operation save成功！");
			num = pr.executeUpdate();
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存Seat Operation异常："+e);
			return 0;
		}finally{
		}
		return num;
	}

	public Result downSeatServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status) {
		Result rss=null;
		List<C_Seatassignment> seatList=new ArrayList<C_Seatassignment>();
		try{
			con=DBManager.getCon();
			String sql="SELECT ca.refno,staffcode,ca.staffname,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,remark "+ 
				" from c_seatassignment ca"+
				" left join c_payment cp on(ca.refno=cp.refno)" +
				" where ca.sfyx='Y' and  DATE_FORMAT(ca.creatDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
				" DATE_FORMAT(ca.creatDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
				" staffcode like ? and ca.staffname like ? and ca.refno like ? and status like ?";
			
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			rs=ps.executeQuery();
			 
			rss = ResultSupport.toResult(rs);
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
	
	}

	public List<C_Seatassignment> findSeatServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status, Page page) {

		List<C_Seatassignment> seatList=new ArrayList<C_Seatassignment>();
		try{
			con=DBManager.getCon();
			String sql="select refno,staffcode,staffname,location,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,remark,creator,creatDate,status,sfyx from c_seatassignment" +
					" where  sfyx='Y' and  DATE_FORMAT(creatDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(creatDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?" +
					" order by creatDate desc limit ?,?";
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
				seatList.add(new C_Seatassignment(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("extensionno"),
						rs.getString("floor"),
						rs.getString("seatno"),
						rs.getString("lockerno"),
						rs.getString("deskDrawerno"),
						rs.getString("pigenBoxno"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("creatDate"),
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

	public int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status) {
		int num=-1;
		try{
			con=DBManager.getCon();
			String sql="select count(*)as num from c_seatassignment" +
					" where sfyx='Y' and DATE_FORMAT(creatDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(creatDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
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

	public int Ready(String refno, String type, String staffcode,String to,String location) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_seatassignment set status='"+type+"'"+(type.equals(Constant.C_Delivered)?",sfyx='D'":"")+" where  status='Submitted' and refno='"+refno+"' and sfyx!='D' ";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_seatassignment_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			String content=" Dear Sir / Madam,<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect the relevant keys of your new seat at : "+location+"  Admin. Counter.";
			content+="<br/>";
			String signature="Regards,<br/>";
			signature+="Administration Department<br/>";
			
			String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
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
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int Completed(String refno, String type, String staffcode,String to,String location) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_seatassignment set status='"+type+"'"+(type.equals(Constant.C_Delivered)?",sfyx='D'":"")+" where status='Ready' and  refno='"+refno+"' and sfyx!='D' ";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_seatassignment_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			String content=" Dear Sir / Madam,<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect the relevant keys of your new seat at : "+location+"  Admin. Counter.";
			content+="<br/>";
			String signature="Regards,<br/>";
			signature+="Administration Department<br/>";
			
			String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
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
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public C_Seatassignment findSearByRef(String refno) {

		C_Seatassignment seatassignment=null;
		try{
			con=DBManager.getCon();
			String sql="select * from c_seatassignment where refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				seatassignment=new C_Seatassignment(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("extensionno"),
						rs.getString("floor"),
						rs.getString("seatno"),
						rs.getString("lockerno"),
						rs.getString("deskDrawerno"),
						rs.getString("pigenBoxno"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("creatDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return seatassignment;
	
	}
	public int delSeat(String refno, String staffcode) {

		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update c_seatassignment set status='"+Constant.C_Delivered+"',sfyx='D' where refno='"+refno+"' and sfyx!='D' and status='"+Constant.C_Submitted+"'" ;
			 ps=con.prepareStatement(sql);
			 System.out.println(sql);
			num=ps.executeUpdate();
			if(num>0){
				sql="insert c_seatassignment_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+Constant.C_Delivered+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
				ps=con.prepareStatement(sql);
				num=ps.executeUpdate();
			}
			if(num>0){
			 con.commit();
			}else{
				throw new RuntimeException("删除失败!");
			}
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
 
}
