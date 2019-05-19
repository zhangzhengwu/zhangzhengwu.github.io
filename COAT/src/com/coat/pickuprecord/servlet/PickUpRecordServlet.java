package com.coat.pickuprecord.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.coat.pickuprecord.dao.PickUpRecordDao;
import com.coat.pickuprecord.dao.impl.PickUpRecordDaoImpl;
import com.coat.pickuprecord.entity.PRecordList;
import com.coat.pickuprecord.entity.PRecordOrder;
import dao.exp.ExpStaffPosition;
import util.DateUtils;
import util.HttpUtil;
import util.ObjectExcelView;
import util.PageData;
import util.Pager;
import util.Tools;
import util.Util;


public class PickUpRecordServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(PickUpRecordServlet.class);
	private static final String APIPATH = Util.getProValue("PickUp.get.api.path");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}
	
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		PrintWriter out = response.getWriter();
		String result="";
		try{
			user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equalsIgnoreCase("select")){
				result=queryPickUpList(request, response);
			}else if(method.equalsIgnoreCase("select_self")){
				result=select_self(request,response);
			}else if(method.equalsIgnoreCase("findList")){
				result=findList(request,response);
			}else if(method.equalsIgnoreCase("saveOrder")){
				result=saveOrder(request,response);
			}else if(method.equalsIgnoreCase("save_Ready")){
				result=HKOAdm_Ready(request,response);
			}else if(method.equalsIgnoreCase("delList")){
				result=delList(request,response);
			}else if(method.equalsIgnoreCase("saveReceive")){
				result=saveReceive(request,response);
			}else if(method.equalsIgnoreCase("findByOrderCode")){
				result=findByOrderCode(request,response);
			}else if(method.equalsIgnoreCase("del_Order")){
				result=del_Order(request,response);
			}else if(method.equalsIgnoreCase("addList")){
				result=addList(request,response);
			}else if(method.equalsIgnoreCase("batchReady")){
				result=batchReady(request,response);
			}else if(method.equalsIgnoreCase("down")){
				down(request,response);
			}else if(method.equalsIgnoreCase("down_self")){
				down_Self(request,response);
			}else if(method.equalsIgnoreCase("reject_back")){
				result=reject_back(request,response);
			}else if(method.equalsIgnoreCase("saveListInterface")){
				result=saveListInterface(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
		}catch (NullPointerException e) {
			log.error("Pick Up==>"+method+"操作异常：空值=="+e);
			result=Util.joinException(e);
		}catch (Exception e) {
			log.error("Pick Up==>"+method+"操作异常："+e);
			result=Util.joinException(e);
		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	
	}

	String queryPickUpList(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode");
		String clientName =request.getParameter("clientname");
		String scandateS=request.getParameter("startDate");
		String scandateE=request.getParameter("endDate");
		String status=request.getParameter("status");
		
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		String result="";
		try{
			Pager page=Util.pageInfo(request);
			page=dao.queryPickUpList(null, page, Util.modifyString(staffcode),
					Util.modifyString(clientName),Util.modifystartdate(scandateS),Util.modifyenddate(scandateE)
					,Util.modifyString(status));
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result;
	}
	
	String select_self(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = user;
		String clientName =request.getParameter("clientname");
		String scandateS=request.getParameter("startDate");
		String scandateE=request.getParameter("endDate");
		String status=request.getParameter("status");
		
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		String result="";
		
		try{
			Pager page=Util.pageInfo(request);
			page=dao.queryPickUpList(null, page, Util.modifyString(staffcode),
					Util.modifyString(clientName),Util.modifystartdate(scandateS),Util.modifyenddate(scandateE)
					,Util.modifyString(status));
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result;
	}
	
	String findList(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html;charset=utf-8");
		List<PRecordList> list=new ArrayList<PRecordList>();
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		String result="";
		
		
		try{
			String staffcard = request.getParameter("staffcard");
			list=dao.findList(staffcard);
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result;
	}
	
	String findByOrderCode(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html;charset=utf-8");
		List<PRecordOrder> list=new ArrayList<PRecordOrder>();
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		String result="";
		try{
			String staffcard = request.getParameter("staffcard");
			list=dao.findByOrderCode(staffcard);
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result;
	}
	
	String saveOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String result="";
		String adminUsername="";
		String listId = request.getParameter("refnos");
		String staffcard=request.getParameter("staffcard");
		String otherCode=request.getParameter("otherCode");
		String exrension=request.getParameter("exrension");
		String password=request.getParameter("password");
		
		try{
			adminUsername = request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			int num=dao.saveOrder(listId,staffcard,otherCode,exrension,password,adminUsername);
			JSONArray jsons=JSONArray.fromObject(num);
			result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result; 
	}
	
	String HKOAdm_Ready(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8;");
		String result="";
		String adminUsername = "";
		
		try{
			String refno=request.getParameter("refno");
			adminUsername=request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			PRecordList rnb=new PRecordList();
			rnb.setStaffcode(request.getParameter("m_staffcode"));
			rnb.setClientName(request.getParameter("m_clientname"));
			rnb.setLocation(request.getParameter("m_location"));
			rnb.setSender(request.getParameter("m_sender"));
			rnb.setDocumentType(request.getParameter("m_documentType"));
			rnb.setDocumentId(request.getParameter("m_documentId"));
			rnb.setScanDate(request.getParameter("m_scandate"));
			rnb.setStatus("Ready");
			rnb.setResult(request.getParameter("m_remark"));
			
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			result=dao.HKOAdm_Ready(rnb,adminUsername,refno);
				 
			log.info(adminUsername+"在PickUpRecordServlet中HKOAdm_Ready时,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在PickUpRecordServlet中HKOAdm_Ready 审核时出现："+e.getMessage());
			result=Util.jointException(e);
		}
		return result;
	}
	
	String delList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result="";
		String adminUsername="";
		try {
			adminUsername=request.getSession().getAttribute("adminUsername")+"";	
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			String refno =request.getParameter("refno");
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			result=dao.delList(adminUsername,refno);
			
			log.info(adminUsername+"在PickUpRecordServlet中删除时,结果为："+result);
		} catch (Exception e) {
			log.error(adminUsername+"在PickUpRecordServlet中删除时出现："+e.getMessage());
			result=Util.jointException(e);
		}
		return result;
	}
	
	String saveReceive(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String result = "";
		String adminUsername="";
		String listId = request.getParameter("refnos");
		try{
			adminUsername = request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			String checkID = request.getParameter("checkID");
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			int num=dao.saveReceive(listId,adminUsername);
			if(num > 0){
				if("admin_HK".equals(checkID)){
					Map<String, String> map = new HashMap<String, String>();
					map.put("ConvoyDocumentID", request.getParameter("ConvoyDocumentID"));
					map.put("UpdateBy", request.getParameter("UpdateBy"));
					
					result = HttpUtil.post(APIPATH, map);
				}else{
					result = Util.getMsgJosnObject_success();
				}
			}else{
				result = Util.getMsgJosnObject_error();
			}
			log.info("recevied status : " + result);
			/*JSONArray jsons=JSONArray.fromObject(num);
			result=jsons.toString();*/
		}catch (Exception e) {
			log.error("save receive : " + e.getMessage());
			throw new RuntimeException(e);
		} 
		return result; 
	}
	
	String del_Order(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result="";
		String adminUsername="";
		try {
			adminUsername=request.getSession().getAttribute("adminUsername")+"";	
			
			String staffcode =request.getParameter("staffcode");
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			result=dao.del_Order(staffcode);
			
			log.info(adminUsername+"在PickUpRecordServlet中删除时,结果为："+result);
		} catch (Exception e) {
			log.error(adminUsername+"在PickUpRecordServlet中删除时出现："+e.getMessage());
			result=Util.jointException(e);
		}
		return result;
	}
	
	String addList(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String result="";
		String staffcode =request.getParameter("staffcode");
		String clientname =request.getParameter("clientname");
		String location =request.getParameter("location");
		String documentType =request.getParameter("documentType");
		String documentId =request.getParameter("documentId");
		String scandate =request.getParameter("scandate");
		String sender =request.getParameter("sender");
		String remark =request.getParameter("result");
		String adminUsername = "";
		int num=-1;
		try{
			adminUsername=request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			num=dao.addList(staffcode,clientname,location,documentType,scandate,sender,remark,adminUsername,documentId);
			if(num>0){
				result=Util.getMsgJosnObject("success","success");
			}else{
				result=Util.getMsgJosnObject("error","error");
			}
		}catch (Exception e) {
			e.printStackTrace();
			result=Util.jointException(e);
			log.error(adminUsername+"PickUpRecordServlet-->新增List 时出现 ："+e);
		}
		return result;
	}
	
	String batchReady(HttpServletRequest request,HttpServletResponse response)throws IOException{
		response.setContentType("text/html;charset=utf-8;");
		String result="";
		String adminUsername = "";
		try{
			String refnos=request.getParameter("refnos");
			String staffCodes=request.getParameter("staffCodes");
			String clientNames=request.getParameter("clientNames");
			adminUsername=request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			result=dao.batchReady(refnos,staffCodes,clientNames,adminUsername);
			log.info(adminUsername+"在PickUpRecordServlet中HKOAdm_Ready时,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在PickUpRecordServlet中HKOAdm_Ready 审核时出现："+e.getMessage());
			result=Util.jointException(e);
		}
		return result;
	}
	/**
	 * 导出P_Record_List
	 * @param request
	 * @param response
	 * @return TODO
	 * @throws IOException
	 */
/*	@SuppressWarnings("static-access")
	public void down(HttpServletRequest request,HttpServletResponse response)throws IOException{
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream  os=null;
		try{
			String staffcode = request.getParameter("staffcode");
			String clientName =request.getParameter("clientname");
			String scandateS=request.getParameter("startDate");
			String scandateE=request.getParameter("endDate");
			String status=request.getParameter("status");
			Result rss=dao.queryPickUpListSet(staffcode, clientName, scandateS, scandateE,status);
			response.reset();//清空输出流
			os = response.getOutputStream();//取出输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=Pick Up Document_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			//list=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			HSSFSheet sheet = wb.createSheet("Pick Up");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
			
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Staffcode");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Client Name");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Location");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"Sender");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"Document Type");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"Scan Date");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"Status");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"Received By");
			expcard.cteateTWOTitleCell(wb,row,(short)8,"Receive Date");
			expcard.cteateTWOTitleCell(wb,row,(short)9,"Remark");
			
			 sheet.setColumnWidth((short)0, 100*40); 
			 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.setColumnWidth((short)2, 100*50); 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.autoSizeColumn((short)8); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
	        
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(i+1);
				        p.put("var1",rows.get("staffcode").toString());
				        expcard.cteateTitleCell(wb,row6,(short)1,rows.get("clientName").toString());
				        expcard.cteateTitleCell(wb,row6,(short)2,rows.get("location").toString());
				        expcard.cteateTitleCell(wb,row6,(short)3,rows.get("sender").toString());
				        expcard.cteateTitleCell(wb,row6,(short)4,rows.get("documentType").toString());
				        expcard.cteateTitleCell(wb,row6,(short)5,rows.get("scanDate").toString());
				        expcard.cteateTitleCell(wb,row6,(short)6,rows.get("status").toString());
				        expcard.cteateTitleCell(wb,row6,(short)7,rows.get("code").toString()); 
				        expcard.cteateTitleCell(wb,row6,(short)8,rows.get("upd_date").toString());
			        	expcard.cteateTitleCell(wb,row6,(short)9,rows.get("result").toString());
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Pick Up-Export Exception :"+e.getMessage());
		}finally{
			dao=null;
			wb=null;
		}
	}*/
	public void down(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String staffcode = request.getParameter("staffcode");
		String clientName =request.getParameter("clientname");
		String scandateS=request.getParameter("startDate");
		String scandateE=request.getParameter("endDate");
		String status=request.getParameter("status");
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		try{
			List<Map<String,Object>> list=dao.queryPickUpListSet(staffcode, clientName, scandateS, scandateE,status);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0,"Staffcode");
			map.put(1,"Client Name");
			map.put(2,"Location");
			map.put(3,"Sender");
			map.put(4,"Document ID");
			map.put(5,"Document Type");
			map.put(6,"Scan Date");
			map.put(7,"Status");
			map.put(8,"Received By");
			map.put(9,"Receive Date");
			map.put(10,"Remark");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					Map<String,Object> rows = list.get(i);
			        p.put("var1",rows.get("staffcode")+"");
			        p.put("var2",rows.get("clientName")+"");
			        p.put("var3",rows.get("location")+"");
			        p.put("var4",rows.get("sender")+"");
			        p.put("var5",rows.get("documentId")+"");
			        p.put("var6",rows.get("documentType")+"");
			        p.put("var7",rows.get("scanDate")+"");
			        p.put("var8",rows.get("status")+"");
			        p.put("var9",rows.get("code")+""); 
			        p.put("var10",rows.get("upd_date")+"");
		        	p.put("var11",rows.get("result")+"");
					data.add(p);
				}
			}
			model.put("varList", data);
			String filename ="Pick Up Document_"+DateUtils.Ordercode()+".xls";
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,filename,"Pick Up");
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Pick Up-Export Exception :"+e.getMessage());
		}finally{
			dao=null;
		}
	}
	
	/*@SuppressWarnings("static-access")
	public void down_Self(HttpServletRequest request,HttpServletResponse response)throws IOException{
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream  os=null;
		try{
			String staffcode = user;
			String clientName =request.getParameter("clientname");
			String scandateS=request.getParameter("startDate");
			String scandateE=request.getParameter("endDate");
			String status=request.getParameter("status");
			Result rss=dao.queryPickUpListSet(staffcode, clientName, scandateS, scandateE,status);
			response.reset();//清空输出流
			os = response.getOutputStream();//取出输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=Pick Up Document_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			//list=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			HSSFSheet sheet = wb.createSheet("Pick Up");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
			
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Staffcode");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Client Name");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Location");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"Sender");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"Document Type");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"Scan Date");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"Status");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"Received By");
			expcard.cteateTWOTitleCell(wb,row,(short)8,"Receive Date");
			expcard.cteateTWOTitleCell(wb,row,(short)9,"Remark");
			
			 sheet.setColumnWidth((short)0, 100*40); 
			 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.setColumnWidth((short)2, 100*50); 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.autoSizeColumn((short)8); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
	        
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.cp.put("var1"teTitleCell(wb,row6,(short)0,rows.get("staffcode").toString());
				        expcard.cteateTitleCell(wb,row6,(short)1,rows.get("clientName").toString());
				        expcard.cteateTitleCell(wb,row6,(short)2,rows.get("location").toString());
				        expcard.cteateTitleCell(wb,row6,(short)3,rows.get("sender").toString());
				        expcard.cteateTitleCell(wb,row6,(short)4,rows.get("documentType").toString());
				        expcard.cteateTitleCell(wb,row6,(short)5,rows.get("scanDate").toString());
				        expcard.cteateTitleCell(wb,row6,(short)6,rows.get("status").toString());
				        expcard.cteateTitleCell(wb,row6,(short)7,rows.get("code").toString()); 
				        expcard.cteateTitleCell(wb,row6,(short)8,rows.get("upd_date").toString());
			        	expcard.cteateTitleCell(wb,row6,(short)9,rows.get("result").toString());
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Pick Up-Export Exception :"+e.getMessage());
		}finally{
			dao=null;
			wb=null;
		}
	}*/
	public void down_Self(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String staffcode = user;
		String clientName =request.getParameter("clientname");
		String scandateS=request.getParameter("startDate");
		String scandateE=request.getParameter("endDate");
		String status=request.getParameter("status");
		PickUpRecordDao dao=new PickUpRecordDaoImpl();
		try{
			List<Map<String,Object>> list=dao.queryPickUpListSet(staffcode, clientName, scandateS, scandateE,status);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0,"Staffcode");
			map.put(1,"Client Name");
			map.put(2,"Location");
			map.put(3,"Sender");
			map.put(4,"Document ID");
			map.put(5,"Document Type");
			map.put(6,"Scan Date");
			map.put(7,"Status");
			map.put(8,"Received By");
			map.put(9,"Receive Date");
			map.put(10,"Remark");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					Map<String,Object> rows = list.get(i);
			        p.put("var1",rows.get("staffcode")+"");
			        p.put("var2",rows.get("clientName")+"");
			        p.put("var3",rows.get("location")+"");
			        p.put("var4",rows.get("sender")+"");
			        p.put("var5",rows.get("documentId")+"");
			        p.put("var6",rows.get("documentType")+"");
			        p.put("var7",rows.get("scanDate")+"");
			        p.put("var8",rows.get("status")+"");
			        p.put("var9",rows.get("code")+""); 
			        p.put("var10",rows.get("upd_date")+"");
		        	p.put("var11",rows.get("result")+"");
					data.add(p);
				}
			}
			model.put("varList", data);
			String filename ="Pick Up Document_"+DateUtils.Ordercode()+".xls";
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,filename,"Pick Up");
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Pick Up-Export Exception :"+e.getMessage());
		}
	}
	
	
	String reject_back(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result="";
		String adminUsername="";
		try {
			adminUsername=request.getSession().getAttribute("adminUsername")+"";	
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			String refno =request.getParameter("refno");
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			result=dao.rejectList(adminUsername,refno);
			
			log.info(adminUsername+"在PickUpRecordServlet中删除时,结果为："+result);
		} catch (Exception e) {
			log.error(adminUsername+"在PickUpRecordServlet中删除时出现："+e.getMessage());
			result=Util.jointException(e);
		}
		return result;
	}
	
	String saveListInterface(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String result="";
		String adminUsername="admin_HK";
		String documentId=request.getParameter("documentId");
		String staffcode=request.getParameter("staffcode");
		String clientname=request.getParameter("clientname");
		String location=request.getParameter("location");
		String sender=request.getParameter("sender");
		String documentType=request.getParameter("documentType");
		String scanDate=request.getParameter("scanDate");
		try{
			
			PickUpRecordDao dao=new PickUpRecordDaoImpl();
			result=dao.saveListInterface(documentId,staffcode,clientname,location,sender,documentType,scanDate,adminUsername);
			
			/*if(num>0){
				result=Util.getMsgJosnObject("success","success");
			}else{
				result=Util.getMsgJosnObject("error","error");
			}*/
			
			//JSONArray jsons=JSONArray.fromObject(result);
			//result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result; 
	}
	
	
}
