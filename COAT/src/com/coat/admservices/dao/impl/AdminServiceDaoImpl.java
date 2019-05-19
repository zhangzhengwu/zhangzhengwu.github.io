package com.coat.admservices.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import util.ReadExcel;
import util.SendMail;
import util.Util;

import com.coat.admservices.dao.AdminServiceDao;
import com.coat.admservices.entity.CAdminserviceAllextension;
import com.coat.admservices.entity.C_Adminservice;

public class AdminServiceDaoImpl implements AdminServiceDao{
	Logger logger = Logger.getLogger(AdminServiceDaoImpl.class);
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;

	public AdminServiceDaoImpl() {
	}

	/**
	 * 保存AdminService
	 */
	 public int add(C_Adminservice adminservice,String password,String phoneType) {
		int num=-1;
		String email="";
		try{
			synchronized (this) {
				String refno=findref();
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
				else
					adminservice.setRefno(refno);
			}
			
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="insert c_adminservice(refno,staffcode,staffname,userType,location,fluorTube,floor,seat,phoneRepair,phoneNumber,PhoneRpass,phoneNumber2,copierRepair,floor2,copier,officeMaintenance,floor3,description,remark,creator,createDate,status,sfyx,seatNumber,phonePas,forgetExtenmsion,phoneType)" +
					" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			ps=conn.prepareStatement(sql);
			ps.setString(1, adminservice.getRefno());
			ps.setString(2, adminservice.getStaffcode());
			ps.setString(3, adminservice.getStaffname());
			ps.setString(4, adminservice.getUserType());
			ps.setString(5, adminservice.getLocation());
			ps.setString(6, adminservice.getFluorTube());
			ps.setString(7, adminservice.getFloor());
			ps.setString(8, adminservice.getSeat());
			ps.setString(9, adminservice.getPhoneRepair());
			ps.setString(10, adminservice.getPhoneNumber());
			ps.setString(11, adminservice.getPhoneRpass());
			ps.setString(12, adminservice.getPhoneNumber2());
			ps.setString(13, adminservice.getCopierRepair());
			ps.setString(14, adminservice.getFloor2());
			ps.setString(15, adminservice.getCopier());
			ps.setString(16, adminservice.getOfficeMaintenance());
			ps.setString(17, adminservice.getFloor3());
			ps.setString(18, adminservice.getDescription());
			ps.setString(19, adminservice.getRemark());
			ps.setString(20, adminservice.getCreator());
			ps.setString(21, adminservice.getCreateDate());
			if(!Util.objIsNULL(adminservice.getForgetExtenmsion())){
				ps.setString(22, Constant.C_Completed);		//---->Submitted完之后直接Completed
			}else{
				ps.setString(22, Constant.C_Submitted);		
			}
			ps.setString(23, adminservice.getSfyx());
			
			ps.setString(24, adminservice.getSeatNumber());
			ps.setString(25, adminservice.getPhonePas());
			ps.setString(26, adminservice.getForgetExtenmsion());
			ps.setString(27, adminservice.getPhoneType());
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			
			sql="select Email from cons_list where EmployeeId='"+adminservice.getStaffcode()+"'";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				email=rs.getString("Email");
			}
			rs.close();
			if(!Util.objIsNULL(adminservice.getForgetExtenmsion())){
				/**
				 * Submitted之后发送一封邮件到申请人邮箱
				 */
				String emailBody="Dear "+adminservice.getStaffname()+",<br/>" +
					" &nbsp;&nbsp;&nbsp;&nbsp;Please be informed that the phone login password of "+adminservice.getForgetExtenmsion()+" is "+password+" ," +
					" And your phone type is 4019 .<br/> " ;
				emailBody+="&nbsp;&nbsp;&nbsp;&nbsp;在此通知閣下啟動電話內線"+adminservice.getForgetExtenmsion()+"的密碼為"+password+"。而現設定的電話型號為4019。<br/>";//-->电话型号默认为4019
				emailBody+="&nbsp;&nbsp;&nbsp;&nbsp;If you phone type is incorrect, please inform us before login. <br/>" +
						"&nbsp;&nbsp;&nbsp;&nbsp;如發現檯頭電話型號與設定不符，請於啟動內線前通知我們。<br/>" +
						"Phone type is as follow 電話型號如下:<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<img  src='phone_4010.jpg'/><img  src='phone_4019.jpg'/><br/>";//--------------<<图片
				
				emailBody+="The login procedure 啟動程序 <br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"1.Pick up the handset 提起聽筒 <br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"2.Dial “your extension number” & “your password” 輸入”內線” 和 ”密碼”  <br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"3.Press “OK” (if your phone type is 4019)  再按“OK” (如電話型號是4019) <br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"4.Put down the handset 放回聽筒<br/>&nbsp;&nbsp;&nbsp;&nbsp;";
				
				emailBody+="Besides, the “User Guide for login or logout the Alcatel Phone” can be found at eConvoy.<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"你亦可到eConvoy 瀏覽使用守則。(Path : eConvoy > Administration Department > Manual & User Guide > Phone Set Password<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"Should you have any enquiries, please contact ADM hotline at ext 3667.<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
						"如有任何疑問，歡迎致電行政部熱線3667查詢。";
				
				//System.out.println("申请人编号-->"+adminservice.getStaffcode()+"   申请人邮箱-->"+email);
				
				String result=SendMail.send("ADM Services Request", email,Util.getProValue("public.admservice.phone.4010.img")+";"+Util.getProValue("public.admservice.phone.4019.img"), emailBody, null, "email.ftl", "Administration Department行政部");
				
				JSONObject json=new JSONObject(result);
				if(!json.get("state").equals("success")){
					throw new RuntimeException((String)json.get("msg"));
				}
			}
			
			conn.commit();
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				conn.rollback();//异常回滚
				throw new RuntimeException(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}
	 
	 /**
	  * 定时上传香港ip phone列表--0点
	  */
	 public String timerTaskUploadAllExtension(){
		String result="";
	 	Util.printLogger(logger,"开始执行指定任务00点-->扫描AdminService-->AllExtension状态 并处理状态");    
		try{	
			int num=uploadAllExtension();
			if(num>=0){
				result="success";
				Util.printLogger(logger,"执行指定任务00点-->扫描扫描AdminService-->AllExtension状态 并处理状态成功");    
			}else{
				Util.printLogger(logger,"执行指定任务00点-->扫描扫描AdminService-->AllExtension状态 并处理状态失败"); 
				throw new Exception();
			}
		}catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger,"执行指定任务00点-->扫描扫描AdminService-->AllExtension状态 并处理状态 失败"+e.getMessage());
		}
		return result;
	 }
	 
	 public int uploadAllExtension() throws Exception{
		  int num=-1;
		  String sql="";
		  try {
			  conn=DBManager.getCon();
			 /* Properties p = new Properties();  
  			  String paths = Thread.currentThread().getContextClassLoader().getResource("configure.properties").getPath();
  			  p.load(new FileInputStream(paths));*/
  			  //获取文件路径
  			 // String filePath=p.getProperty("public.admservice.upload.filepath.allExtension");
			  String filePath=Util.getProValue("public.admservice.upload.filepath.allExtension");
  			  //System.out.println("文件路径-->"+filePath+"");
  			  File file=new File(filePath);
  			  List<List<Object>> list=ReadExcel.readExcel(file, "All Extension", 20, 0);
			  Util.deltables("c_adminservice_allextension");
			  List<String> staffList=new ArrayList<String>();
			  for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
				if(!Util.objIsNULL(list2.get(4)+"")){
					 String extension=list2.get(0).toString();
					 String pwd=list2.get(1).toString();
					 if(extension.indexOf(".") > 0){
					     //正则表达
						 extension = extension.replaceAll("0+?$", "");//去掉后面无用的零
						 extension = extension.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
					 }
					 if(pwd.indexOf(".")>0){
						 //正则表达
						 pwd = pwd.replaceAll("0+?$", "");//去掉后面无用的零
						 pwd = pwd.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
					 }
					
					if(!staffList.contains(list2.get(4).toString().toUpperCase())){//判断数据库是否存在该code
						staffList.add(list2.get(4).toString().toUpperCase());
						sql="insert into c_adminservice_allextension(staffcode,staffname,extension,password,finalFreeNo,department,reamrk,resignedOn,admHandled,ITDHandled," +
								"number,ipPhone,phoneType,IDDFunction,recruiter,recording,creator,createDate,sfyx)" +
							"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ps=conn.prepareStatement(sql);
						ps.setString(1, Util.objIsNULL(list2.get(4))?"":list2.get(4).toString().trim());
						ps.setString(2, list2.get(5).toString());
						ps.setString(3, extension);
						ps.setString(4, pwd);
						ps.setString(5, "");					 //-->list2.get(2).toString()
						ps.setString(6, list2.get(3).toString());
						ps.setString(7, list2.get(6).toString());
						ps.setString(8, "");					 //-->list2.get(7).toString()
						ps.setString(9, list2.get(8).toString());
						ps.setString(10,list2.get(9).toString());
						ps.setString(11, "1");//-->number默认为1
						ps.setString(12, list2.get(11).toString());
						ps.setString(13, list2.get(12).toString());
						ps.setString(14, list2.get(13).toString());
						ps.setString(15, list2.get(14).toString());
						ps.setString(16, list2.get(15).toString());
						ps.setString(17, "System");						//创建人默认为system
						ps.setString(18, DateUtils.getNowDateTime());   //创建时间为系统当前上传时间
						ps.setString(19, "Y");
						ps.executeUpdate();
						num++;
					}
			   }
			}
			list=null;
		} catch (Exception e) {
			num=-1;
			throw new Exception(e);
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	 }
	 /**
	  * 判断c_adminservice_allextension的staffcode是否存在
	  */
	 public String findAllExtensionCode(String staffcode){
		String num="";
		Connection connection=null;
		try {
			connection=DBManager.getCon();
			String sql="select staffcode from c_adminservice_allextension where staffcode='"+staffcode+"'";
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getString("staffcode");
			}
			rs.close();
		} catch (Exception e) {
			num="";
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
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
		String sql="select count(*) from c_adminservice";
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
			num=Constant.ADMINSERVICE+DateUtils.Ordercode()+num;
		}rs.close();
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
	public List<C_Adminservice> findAdminServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String location,String requestType, Page page) {
		List<C_Adminservice> adminserviceList=new ArrayList<C_Adminservice>();
		try{
			conn=DBManager.getCon();
			String sql="select refno,staffcode,staffname,location,creator,createDate,status,remark from c_adminservice" +
					" where sfyx='Y' and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					"  DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?  and location like ? "+(Util.objIsNULL(requestType)?"":" and "+requestType+"='Y' ") +
					" order by createDate desc limit ?,?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+location+"%");
			ps.setInt(8, (page.getCurPage()-1)*page.getPageSize());
			ps.setInt(9, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				adminserviceList.add(new C_Adminservice(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return adminserviceList;
	}
	
	/**
	 * Adminservice 导出
	 */
	public List<C_Adminservice> downAdminServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String location,String requestType) {
		List<C_Adminservice> adminserviceList=new ArrayList<C_Adminservice>();
		try{
			conn=DBManager.getCon();
			String sql="select * from c_adminservice" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and location like ? "+(Util.objIsNULL(requestType)?"":" and "+requestType+"='Y' ")+
					" order by createDate desc ";
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+location+"%");
			rs=ps.executeQuery();
			while(rs.next()){
				adminserviceList.add(new C_Adminservice(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("location"),
						rs.getString("fluorTube"),
						rs.getString("floor"),
						rs.getString("seat"),
						rs.getString("phoneRepair"),
						rs.getString("phoneNumber"),
						rs.getString("phoneRpass"),
						rs.getString("phoneNumber2"),
						rs.getString("copierRepair"),
						rs.getString("floor2"),
						rs.getString("copier"),
						rs.getString("officeMaintenance"),
						rs.getString("floor3"),
						rs.getString("description"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"),
						rs.getString("seatNumber"),
						rs.getString("phonePas"),
						rs.getString("forgetExtenmsion"),
						rs.getString("phoneType")
						));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return adminserviceList;
	}
	
	/**
	 * 查询总条数
	 */
	public int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status,String location,String requestType) {
		int num=-1;
		try{
			conn=DBManager.getCon();
			String sql="select count(*)as num from c_adminservice" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ? and location like ? "+(Util.objIsNULL(requestType)?"":" and "+requestType+"='Y' ") ;
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setString(7, "%"+location+"%");
			 
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
	public C_Adminservice findAdminserviceByRef(String refno) {
		C_Adminservice adminservice=null;
		try{
			conn=DBManager.getCon();
			String sql="select * from c_adminservice where refno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				adminservice=new C_Adminservice(rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("fluorTube"),
						rs.getString("floor"),
						rs.getString("seat"),
						rs.getString("phoneRepair"),
						rs.getString("phoneNumber"),
						rs.getString("phoneRpass"),
						rs.getString("phoneNumber2"),
						rs.getString("copierRepair"),
						rs.getString("floor2"),
						rs.getString("copier"),
						rs.getString("officeMaintenance"),
						rs.getString("floor3"),
						rs.getString("description"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"),
						rs.getString("location"),//-->
						rs.getString("seatNumber"),
						rs.getString("phonePas"),
						rs.getString("forgetExtenmsion"),
						rs.getString("phoneType")
						);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return adminservice;
	}
	/**
	 * 操作AdminService
	 */
	public int modify(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+type+"' where sfyx='Y' and status='Submitted' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
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
	
	public int VOID(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+type+"',sfyx='D' where status!='Completed' and sfyx='Y' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
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
	 * 删除AdminService
	 */
	public int Deleted(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+type+"',sfyx='D' where status='Submitted' and sfyx='Y' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
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
	
	
	
	
	public Result downReportingForADMService(String startDate, String endDate) {
		Result rss=null;
		try{
			conn=DBManager.getCon();
			String sql="select * from(SELECT 'Month',ca.createDate,ca.refno,staffcode,staffname,ca.userType,"+
						" if(fluorTube='Y',1,'')as fluorTube,"+
						" if(PhoneRepair='Y',1,'') as PhoneRepair," +
						" if(Phonerpass='Y',1,'') as Phonerpass," +
						" if(copierRepair='Y',1,'') as copierRepair," +
						" if(officeMaintenance='Y',1,'')as officeMaintenance,if(description is null,'',description) as description "+
						" from c_adminservice ca"+
						" where ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
						" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
			
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);

			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			rs.close();
		}catch (Exception e) {
			logger.error("导出ADMService 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return rss;
	}
	
	public CAdminserviceAllextension findAllExtension(String staffcode){
		CAdminserviceAllextension extension=new CAdminserviceAllextension();
		try {
			conn=DBManager.getCon();
			String sql="select * from c_adminservice_allextension where staffcode='"+staffcode+"'";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			if(rs.next()){
				extension=new CAdminserviceAllextension(
						rs.getString("extension"),
						rs.getString("password"),
						rs.getString("phoneType")
				);
			}
			rs.close();
		} catch (Exception e) {
			logger.error("查询allExtension 异常=="+e.getMessage());
			e.printStackTrace();
		}
		return extension;
	}

	public String completed(String refno, String user) {
		String result="";
		try{
			C_Adminservice c= findAdminserviceByRef(refno);
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_adminservice set status='"+Constant.C_Completed+"' where sfyx='Y' and status='Submitted' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			sql="insert c_adminservice_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+Constant.C_Completed+"','"+user+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			if(Util.objIsNULL(c)){
				throw new RuntimeException("Application for information loss");
			}
			if(!Util.objIsNULL(c.getPhoneNumber2())){
				String EmailContent="Dear "+c.getStaffname()+"<br/><br/>"+
									"Please be informed that the voice mail box of "+c.getPhoneNumber2()+" had reset. The temporary password is "+c.getPhoneNumber2()+", please set up the voice mail immediately.<br/><br/>"+
									"Should you have any enquiries, please contact ADM hotline at ext3667.<br/>";
				String sign="Administration Department";
				
				sql="select Email from cons_list where EmployeeId='"+c.getStaffcode()+"'"+
					"union select email from staff_list where staffcode='"+c.getStaffcode()+"'";
				String to="";
				ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next()){
					to=rs.getString("Email");
				}
				rs.close();
				result=SendMail.send("ADM Services Request--"+c.getStaffcode(), to, EmailContent, "email.ftl", sign);
				JSONObject json=new JSONObject(result);
				if(!"success".equalsIgnoreCase(json.get("state")+"")){
					throw new RuntimeException("ADM Service Completed SendMail Exception:"+json.getString("msg"));
				}
			}else{
				result=Util.getMsgJosnObject_success();
			}
			
			 conn.commit();
		}catch (Exception e) {
			result=Util.joinException(e);
			try {
				if(!Util.objIsNULL(conn)){
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return result;
	}
	
	   
}
