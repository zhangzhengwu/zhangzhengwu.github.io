package com.coat.admservices.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.coat.admservices.dao.AdminServiceDao;
import com.coat.admservices.dao.impl.AdminServiceDaoImpl;
import com.coat.admservices.entity.CAdminserviceAllextension;
import com.coat.admservices.entity.C_Adminservice;
import dao.exp.ExpStaffPosition;
import util.Constant;
import util.DateUtils;
import util.Page;
import util.Util;

public class AdminServicesServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AdminServicesServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}
	
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		try{
			 user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equals("select"))
				select(request, response);
			else if(method.equals("down"))
				down(request, response);
			else if(method.equals("add"))
				add(request, response);
			else if(method.equals("VOID"))
				VOID(request,response);
			else if(method.equals("detail"))
				Detail(request,response);
			else if(method.equals("complete"))
				complete(request,response);
			else if(method.equals("delete"))
				delete(request,response);
			else if(method.equals("queryAllExtension"))
				queryAllExtension(request,response);
			else {
				throw new Exception("Unauthorized access!");
			}
		}catch (NullPointerException e) {
			log.error("ADM Service==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ADM Service==>"+method+" 操作异常："+e);
			response.getWriter().print("Exception:"+e.toString());
		} finally{
			method=null; 
			
		} 
	}

	private void complete(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		String result="";
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			//String type=request.getParameter("type");
			result=adminServiceDao.completed(refno, user);
			/*int num=adminServiceDao.modify(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}*/
		}catch (Exception e) {
			result=Util.joinException(e);
			log.error("ADM Service==>Completed 操作异常：空值=="+e.toString());
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}
	

	 void VOID(HttpServletRequest request, HttpServletResponse response){
			PrintWriter out=null;
			AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
			try{
				out=response.getWriter();
				String refno=request.getParameter("refno");
				String type=request.getParameter("type");
				int num=adminServiceDao.VOID(refno, type, user);
				if(num>0){
					out.print("Success!");
				}else{
					out.print("Error!");
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error("AccessCard==>VOID Exception :"+e.getMessage());
				out.print("Exception :"+e.getMessage());
			}finally{
				out.flush();
				out.close();
			}
		}
	 
	
	private void delete(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		try{
			out=response.getWriter();
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			int num=adminServiceDao.Deleted(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ADM Service==>Deleted 操作异常：空值=="+e.toString());
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
	private void Detail(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		 C_Adminservice adminservice=null;
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			adminservice=adminServiceDao.findAdminserviceByRef(refno);
			JSONArray jsons=JSONArray.fromObject(adminservice);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ADM Service==>Detail 操作异常：空值=="+e.toString());
		}finally{
			adminservice=null;
			adminServiceDao=null;
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private void select(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		 Page page=new Page();
		 List<C_Adminservice> list=new ArrayList<C_Adminservice>();
		try{
			out=response.getWriter();
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			String location=request.getParameter("location");
			String requestType=request.getParameter("requestType");
			page.setAllRows(adminServiceDao.getRow(startDate, endDate, staffcode, staffname, refno, status,location,requestType));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			list=adminServiceDao.findAdminServiceList(startDate, endDate, staffcode, staffname, refno, status,location,requestType, page);
			List list1=new ArrayList();
			list1.add(0,list);//数据存放
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总条数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			jsons=null;
			list1=null;
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("ADM Service==>Search 操作异常：空值=="+e.toString());
			out.print("Search Exception :"+e.getMessage());
		}finally{
			adminServiceDao=null;
			list=null;
			out.flush();
			out.close();
		}
	}
	/**
	 * 导出
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	private void down(HttpServletRequest request, HttpServletResponse response) {
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		List<C_Adminservice> list=null;
		try{
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			String location=request.getParameter("location");
			String requestType=request.getParameter("requestType");
			
			HSSFWorkbook wb = new HSSFWorkbook();
			OutputStream  os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=ADMINSERVICE_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			list=adminServiceDao.downAdminServiceList(startDate, endDate, staffcode, staffname, refno, status,location,requestType);
			
			HSSFSheet sheet = wb.createSheet("Change of Fluorescent Tube");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
			//sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"Floor");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"seat");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"Remarks");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"Status");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"Time");
			
			
			HSSFSheet sheet2 = wb.createSheet("Desk Phone Repair");
			
			HSSFRow row2=sheet2.createRow(0);
			sheet.createFreezePane(0, 1);
			//sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row2,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row2,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row2,(short)2,"Staff Name");
			
			expcard.cteateTWOTitleCell(wb,row2,(short)3,"Seat Number");//----<<
			expcard.cteateTWOTitleCell(wb,row2,(short)4,"Ext.No");
			expcard.cteateTWOTitleCell(wb,row2,(short)5,"Password");   //--<<
			expcard.cteateTWOTitleCell(wb,row2,(short)6,"Phone Type"); //--<<
			
			expcard.cteateTWOTitleCell(wb,row2,(short)7,"Remarks");
			expcard.cteateTWOTitleCell(wb,row2,(short)8,"Status");
			expcard.cteateTWOTitleCell(wb,row2,(short)9,"Time");
			
			
			HSSFSheet sheet3 = wb.createSheet("Phone Voicemail Reset");
			HSSFRow row3=sheet3.createRow(0);
			sheet.createFreezePane(0, 1);
			//sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row3,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row3,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row3,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row3,(short)3,"Ext.No");
			expcard.cteateTWOTitleCell(wb,row3,(short)4,"Remarks");
			expcard.cteateTWOTitleCell(wb,row3,(short)5,"Status");
			expcard.cteateTWOTitleCell(wb,row3,(short)6,"Time");
			//---------------------------------------------------------------------------
			HSSFSheet sheet7 = wb.createSheet("Forget Extension's Password");
			HSSFRow row7=sheet7.createRow(0);
			sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row7,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row7,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row7,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row7,(short)3,"Ext.No");
			expcard.cteateTWOTitleCell(wb,row7,(short)4,"Remarks");
			expcard.cteateTWOTitleCell(wb,row7,(short)5,"Status");
			expcard.cteateTWOTitleCell(wb,row7,(short)6,"Time");
			//---------------------------------------------------------------------------
			
			HSSFSheet sheet4 = wb.createSheet("Copier Repair");
			HSSFRow row4=sheet4.createRow(0);
			sheet.createFreezePane(0, 1);
			//sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row4,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row4,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row4,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row4,(short)3,"Floor");
			expcard.cteateTWOTitleCell(wb,row4,(short)4,"Copier");
			expcard.cteateTWOTitleCell(wb,row4,(short)5,"Remarks");
			expcard.cteateTWOTitleCell(wb,row4,(short)6,"Status");
			expcard.cteateTWOTitleCell(wb,row4,(short)7,"Time");
			
			
			
			HSSFSheet sheet5 = wb.createSheet("Other");
			HSSFRow row5=sheet5.createRow(0);
			sheet.createFreezePane(0, 1);
			//sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row5,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row5,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row5,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row5,(short)3,"Floor");
			expcard.cteateTWOTitleCell(wb,row5,(short)4,"Descriptions");
			expcard.cteateTWOTitleCell(wb,row5,(short)5,"Remarks");
			expcard.cteateTWOTitleCell(wb,row5,(short)6,"Status");
			expcard.cteateTWOTitleCell(wb,row5,(short)7,"Time");
			
			
			
			 sheet.setColumnWidth((short)0, 100*40); 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度 
	         
	         sheet2.setColumnWidth((short)0, 100*40); 
	         sheet2.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet2.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet2.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet2.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet2.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet2.autoSizeColumn((short)6); // 调整第四列宽度 
	         sheet2.autoSizeColumn((short)7); // 调整第四列宽度 
	         sheet2.autoSizeColumn((short)8); // 调整第四列宽度 
	         sheet2.autoSizeColumn((short)9); // 调整第四列宽度 
	         
	         sheet3.setColumnWidth((short)0, 100*40); 
	         sheet3.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet3.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet3.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet3.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet3.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet3.autoSizeColumn((short)6); // 调整第四列宽度 
	         
	         sheet4.setColumnWidth((short)0, 100*40); 
	         sheet4.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet4.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet4.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet4.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet4.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet4.autoSizeColumn((short)6); // 调整第四列宽度 
	         sheet4.autoSizeColumn((short)7); // 调整第四列宽度 
	         
	         sheet5.setColumnWidth((short)0, 100*40); 
	         sheet5.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet5.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet5.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet5.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet5.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet5.autoSizeColumn((short)6); // 调整第四列宽度 
	         sheet5.autoSizeColumn((short)7); // 调整第四列宽度 
	         //expcard.createFixationSheet(rs, os,wb,sheet);
	         
	         sheet7.setColumnWidth((short)0, 100*40); 
	         sheet7.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet7.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet7.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet7.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet7.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet7.autoSizeColumn((short)6); // 调整第四列宽度 
	         sheet7.autoSizeColumn((short)7); // 调整第四列宽度 
			int a=0;
			int b=0;
			int c=0;
			int d=0;
			int e=0;
			int f=0;
			for(int i=0;i<list.size();i++){
				C_Adminservice adminservice=list.get(i);
				if(adminservice.getFluorTube().equals("Y")){
					a+=1;
					HSSFRow row6=sheet.createRow(a);
					expcard.cteateTitleCell(wb,row6,(short)0,adminservice.getRefno());
					expcard.cteateTitleCell(wb,row6,(short)1,adminservice.getStaffcode());
					expcard.cteateTitleCell(wb,row6,(short)2,adminservice.getStaffname());
					expcard.cteateTitleCell(wb,row6,(short)3,adminservice.getFloor());
					expcard.cteateTitleCell(wb,row6,(short)4,adminservice.getSeat());
					expcard.cteateTitleCell(wb,row6,(short)5,adminservice.getRemark());
					expcard.cteateTitleCell(wb,row6,(short)6,adminservice.getStatus());
					expcard.cteateTitleCell(wb,row6,(short)7,DateUtils.getHourAndSecond(adminservice.getCreateDate()));
				}
				if(adminservice.getPhoneRepair().equals("Y")){
					b+=1;
					HSSFRow row6=sheet2.createRow(b);
					expcard.cteateTitleCell(wb,row6,(short)0,adminservice.getRefno());
					expcard.cteateTitleCell(wb,row6,(short)1,adminservice.getStaffcode());
					expcard.cteateTitleCell(wb,row6,(short)2,adminservice.getStaffname());
					expcard.cteateTitleCell(wb,row6,(short)3,adminservice.getSeatNumber());	//--<<
					expcard.cteateTitleCell(wb,row6,(short)4,adminservice.getPhoneNumber());
					expcard.cteateTitleCell(wb,row6,(short)5,adminservice.getPhonePas());	//--<<
					expcard.cteateTitleCell(wb,row6,(short)6,adminservice.getPhoneType());	//--<<
					expcard.cteateTitleCell(wb,row6,(short)7,adminservice.getRemark());
					expcard.cteateTitleCell(wb,row6,(short)8,adminservice.getStatus());
					expcard.cteateTitleCell(wb,row6,(short)9,DateUtils.getHourAndSecond(adminservice.getCreateDate()));
				}
				if(adminservice.getPhoneRpass().equals("Y")){
					c+=1;
					HSSFRow row6=sheet3.createRow(c);
					expcard.cteateTitleCell(wb,row6,(short)0,adminservice.getRefno());
					expcard.cteateTitleCell(wb,row6,(short)1,adminservice.getStaffcode());
					expcard.cteateTitleCell(wb,row6,(short)2,adminservice.getStaffname());
					expcard.cteateTitleCell(wb,row6,(short)3,adminservice.getPhoneNumber2());
					expcard.cteateTitleCell(wb,row6,(short)4,adminservice.getRemark());
					expcard.cteateTitleCell(wb,row6,(short)5,adminservice.getStatus());
					expcard.cteateTitleCell(wb,row6,(short)6,DateUtils.getHourAndSecond(adminservice.getCreateDate()));
				}
				//----------------------------------------------------------------------------------------
				if(!adminservice.getForgetExtenmsion().equals("")){
					d+=1;
					HSSFRow row6=sheet7.createRow(d);
					expcard.cteateTitleCell(wb,row6,(short)0,adminservice.getRefno());
					expcard.cteateTitleCell(wb,row6,(short)1,adminservice.getStaffcode());
					expcard.cteateTitleCell(wb,row6,(short)2,adminservice.getStaffname());
					expcard.cteateTitleCell(wb,row6,(short)3,adminservice.getForgetExtenmsion());
					expcard.cteateTitleCell(wb,row6,(short)4,adminservice.getRemark());
					expcard.cteateTitleCell(wb,row6,(short)5,adminservice.getStatus());
					expcard.cteateTitleCell(wb,row6,(short)6,DateUtils.getHourAndSecond(adminservice.getCreateDate()));
				}
				//----------------------------------------------------------------------------------------
				if(adminservice.getCopierRepair().equals("Y")){
					e+=1;
					HSSFRow row6=sheet4.createRow(e);
					expcard.cteateTitleCell(wb,row6,(short)0,adminservice.getRefno());
					expcard.cteateTitleCell(wb,row6,(short)1,adminservice.getStaffcode());
					expcard.cteateTitleCell(wb,row6,(short)2,adminservice.getStaffname());
					expcard.cteateTitleCell(wb,row6,(short)3,adminservice.getFloor2());
					expcard.cteateTitleCell(wb,row6,(short)4,adminservice.getCopier());
					expcard.cteateTitleCell(wb,row6,(short)5,adminservice.getRemark());
					expcard.cteateTitleCell(wb,row6,(short)6,adminservice.getStatus());
					expcard.cteateTitleCell(wb,row6,(short)7,DateUtils.getHourAndSecond(adminservice.getCreateDate()));
				}
				if(adminservice.getOfficeMaintenance().equals("Y")){
					f+=1;
					HSSFRow row6=sheet5.createRow(f);
					expcard.cteateTitleCell(wb,row6,(short)0,adminservice.getRefno());
					expcard.cteateTitleCell(wb,row6,(short)1,adminservice.getStaffcode());
					expcard.cteateTitleCell(wb,row6,(short)2,adminservice.getStaffname());
					expcard.cteateTitleCell(wb,row6,(short)3,adminservice.getFloor3());
					expcard.cteateTitleCell(wb,row6,(short)4,adminservice.getDescription());
					expcard.cteateTitleCell(wb,row6,(short)5,adminservice.getRemark());
					expcard.cteateTitleCell(wb,row6,(short)6,adminservice.getStatus());
					expcard.cteateTitleCell(wb,row6,(short)7,DateUtils.getHourAndSecond(adminservice.getCreateDate()));
				}
				
			}
			wb.write(os);
			os.flush();
			os.close();
			
			
			log.error(user+"==>AdminService==>Export=="+DateUtils.getNowDateTime());
			}catch (Exception e) {
				log.error("AdminService==>Export 导出异常=="+e);
				e.printStackTrace();
			}finally{
				adminServiceDao=null;
				list=null;
			}
		
	}
	
	
	/**
	 * 新增
	 * @param request
	 * @param response
	 */
	private void add(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		try{
			out=response.getWriter();
			//Client client=new Client();
			//BeanUtils.populate(client, request.getParameterMap());
			String password=request.getParameter("password");
			String phoneType=request.getParameter("phoneType");
			
			int num=adminServiceDao.add(new C_Adminservice("",
					request.getParameter("staffcode"),
					request.getParameter("staffname"),
					request.getParameter("userType"),
					request.getParameter("fluortube").equals("Y")?"Y":"N",
					request.getParameter("floor"),
					request.getParameter("seat"),
					request.getParameter("phoneRepair").equals("Y")?"Y":"N",
					request.getParameter("phone1"),
					request.getParameter("phoneRpass").equals("Y")?"Y":"N",
					request.getParameter("phone2"),
					request.getParameter("copierRepair").equals("Y")?"Y":"N",
					request.getParameter("floor2"),
					request.getParameter("copier"),
					request.getParameter("officeMain").equals("Y")?"Y":"N",
					request.getParameter("floor3"),
					request.getParameter("descript"),
					request.getParameter("remark"),user,
					DateUtils.getNowDateTime(),
					Constant.C_Submitted,
					"Y",
					request.getParameter("location"),
					request.getParameter("seatNumber"),
					request.getParameter("passwords"),
					request.getParameter("forgetExtension"),
					request.getParameter("xinghao")
			),password,phoneType);
			if(num>0){
				out.print("Your request will be handled in 2 working days.");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("AdminService==>Save Exception=="+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			adminServiceDao=null;
			out.flush();
			out.close();
		}
			
	}
	
	public void queryAllExtension(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out=null;
		AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
		CAdminserviceAllextension admin=new CAdminserviceAllextension();
		try{
			out=response.getWriter();
			String staffcode=request.getParameter("staffcode");
			admin=adminServiceDao.findAllExtension(staffcode);
			JSONArray jsons=JSONArray.fromObject(admin);
			out.print(jsons.toString());
			jsons=null;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			adminServiceDao=null;
			out.flush();
			out.close();
		}
		
	}
	

}
