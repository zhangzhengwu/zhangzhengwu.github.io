package servlet.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import util.Constant;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.C_KeyDao;
import dao.exp.ExpStaffPosition;
import dao.impl.C_KeyDaoImpl;
import entity.C_Keys;
import entity.C_KeysHistory;
import entity.C_Payment;

public class SaveKeyServlet extends HttpServlet {

	/**
	 * Wilson 2014-5-14 15:02:34
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SaveKeyServlet.class);
	String user=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String mothed = request.getParameter("method");
		user=(String) request.getSession().getAttribute("adminUsername");
		if (mothed.equals("down")) {
			doPost(request, response);
		}else {
			PrintWriter out = response.getWriter();
			out.print("You have no legal power!");
			log.info(user+"===>非法访问KeyServlet");
			out.flush();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		String mothed = request.getParameter("method");
		try{
			user=(String) request.getSession().getAttribute("adminUsername");
			if (mothed.toLowerCase().trim().equals("add")) {
				add(request,response);
			}else if (mothed.toLowerCase().trim().equals("complete")) {
				complete(request,response);
			}else if (mothed.toUpperCase().trim().equals("VOID")) {
				VOID(request,response);
			}else if (mothed.toLowerCase().trim().equals("paymend")) {
				paymend(request,response);
			}else if (mothed.toLowerCase().trim().equals("select")) {
				select(request,response);
			}else if (mothed.toLowerCase().trim().equals("delete")) {
				del(request,response);
			}else if (mothed.toLowerCase().trim().equals("detail")) {
				Detail(request,response);
			}else if (mothed.toLowerCase().trim().equals("down")) {
				down(request,response);
			}else if (mothed.toLowerCase().trim().equals("findcons")) {
				findcons(request,response);
			}else {
				throw new Exception("Unauthorized access!");
			}
			
			
		}catch (NullPointerException e) {
			log.error("KeyRequest==>"+mothed+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("KeyRequest==>"+mothed+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			mothed=null; 
		} 
		
	}
	private void findcons(HttpServletRequest request,
			HttpServletResponse response) {
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		C_KeysHistory cKeys=null;
		try{
			out=response.getWriter();
			String refno=request.getParameter("staffcode");
			cKeys=aDao.findKeyBycode(refno);
			JSONArray jsons=JSONArray.fromObject(cKeys);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("KeyRequest==FindCons操作异常："+e);
			out.print("null");
		}finally{
			out.flush();
			out.close();
		}
		
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	private void down(HttpServletRequest request, HttpServletResponse response) {

		C_KeyDao aDao=new C_KeyDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		try{
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			Result rss=aDao.downKeysList( startDate, endDate, staffcode, staffname, refno, status);
			OutputStream  os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=Request for Keys_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			//list=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			HSSFSheet sheet = wb.createSheet("Keys");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
				
				
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Ref#");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Code");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Name");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"Locker#");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"Desk Drawer#");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"Pigen Box#");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"receipt Date");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"Signature");
			expcard.cteateTWOTitleCell(wb,row,(short)8,"payment");
			expcard.cteateTWOTitleCell(wb,row,(short)9,"Cash");
			expcard.cteateTWOTitleCell(wb,row,(short)10,"Octopus");
			expcard.cteateTWOTitleCell(wb,row,(short)11,"EPS");
			expcard.cteateTWOTitleCell(wb,row,(short)12,"Remarks");
			
			 sheet.setColumnWidth((short)0, 100*40); 
			 sheet.setColumnWidth((short)1, 100*20); 			 // 调整第二列宽度 
			 sheet.setColumnWidth((short)2, 100*60); 			 // 调整第三列宽度 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.autoSizeColumn((short)8); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)10); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)11); // 调整第四列宽度 
	         sheet.setColumnWidth((short)12, 100*200); 
	         HSSFCellStyle style1 = wb.createCellStyle();
			    HSSFFont font = wb.createFont();
			    font.setFontHeightInPoints((short)10);
			    font.setFontName("宋体");
			    
			    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			    style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			    style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			    style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			    style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			    style1.setFont(font);
	      
	         
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			            
			            
			        	HSSFRow row6=sheet.createRow(i+1);
				        expcard.cteateTitleCell(wb,row6.createCell(0),rows.get("refno")+"",style1);
				        expcard.cteateTitleCell(wb,row6.createCell(1),rows.get("staffcode")+"",style1);
				        expcard.cteateTitleCell(wb,row6.createCell(2),rows.get("staffname")+"",style1);
				        expcard.cteateTitleCell(wb,row6.createCell(3),Util.objIsNULL(rows.get("lockerno"))?"":rows.get("lockerno")+"",style1);
				        expcard.cteateTitleCell(wb,row6.createCell(4),Util.objIsNULL(rows.get("deskDrawerno"))?"":rows.get("deskDrawerno")+"",style1);
				        expcard.cteateTitleCell(wb,row6.createCell(5),Util.objIsNULL(rows.get("pigenBoxno"))?"":rows.get("pigenBoxno")+"",style1);
				        expcard.cteateTitleCell(wb,row6.createCell(6),Util.objIsNULL(rows.get("paymentDate"))?"":rows.get("paymentDate")+"",style1); 
				        expcard.cteateTitleCell(wb,row6.createCell(7),Util.objIsNULL(rows.get("handleder"))?"":rows.get("handleder")+"",style1);
				       /* 
				        expcard.cteateTitleCell(wb,row6,(short)9,"");
				        expcard.cteateTitleCell(wb,row6,(short)10,"");*/
				        if(Util.objIsNULL(rows.get("paymentAount"))){
				        	expcard.cteateTitleCell(wb,row6.createCell(8),"",style1);
				        }else{
				        	expcard.cteateNumCell(wb,row6,(short)8,""+rows.get("paymentAount"));
				        }
				        String paymethedString = Util.objIsNULL(rows.get("paymentMethod"))?"":rows.get("paymentMethod")+"";
				        if(paymethedString.equals("Cash")){
				        	expcard.cteateTitleCell(wb,row6.createCell(9),"Y",style1);//Cash
				        }else{  
				        	expcard.cteateTitleCell(wb,row6.createCell(9),"",style1);
				        }
				        
				        if(paymethedString.equals("Octopus")){
				        	expcard.cteateTitleCell(wb,row6.createCell(10),"Y",style1);//Cash
				        }else{  
				        	expcard.cteateTitleCell(wb,row6.createCell(10),"",style1);//Octopus
				        }
				        if(paymethedString.equals("EPS")){
				        	expcard.cteateTitleCell(wb,row6.createCell(11),"Y",style1);//Cash
				        }else{  
				        	expcard.cteateTitleCell(wb,row6.createCell(11),"",style1);//Octopus
				        }
				        expcard.cteateTitleCell(wb,row6.createCell(12),rows.get("remarks")+"",style1);
				    
				        	 
			         
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("KeyRequest==>Export操作异常："+e);
		}finally{
			aDao=null;
			wb=null;
			
		}
	
		
	}

	private void Detail(HttpServletRequest request, HttpServletResponse response){
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		C_Keys cKeys=null;
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			cKeys=aDao.findKeyByRef(refno);
			JSONArray jsons=JSONArray.fromObject(cKeys);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("KeyRequest==>Detail操作异常："+e);
			out.print("Error!");
		}finally{
			out.flush();
			out.close();
		}
	}
	private void del(HttpServletRequest request, HttpServletResponse response) {
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type= Constant.C_deleted;
			int num=aDao.detele(refno, type, user,"D");
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("KeyRequest==>Delete操作异常："+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	private void select(HttpServletRequest request, HttpServletResponse response) {
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		 Page page=new Page();
		 List<C_Keys> list=new ArrayList<C_Keys>();
		try{
			out=response.getWriter();
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			
			page.setAllRows(aDao.getRow(startDate, endDate, staffcode, staffname, refno, status));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			list=aDao.findKeyList(startDate, endDate, staffcode, staffname, refno, status, page);
			List list1=new ArrayList();
			list1.add(0,list);//数据存放
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总条数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("KeyRequest==>Search操作异常："+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			list=null;
			out.flush();
			out.close();
		}
		
	}
	/**
	 * 完成
	 * @param request
	 * @param response
	 */
		void paymend(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			C_KeyDao aDao=new C_KeyDaoImpl();
			try{
				String order=request.getParameter("refno");
				String status=request.getParameter("status");
				String  paymethod=request.getParameter("paymethod");//支付方式
				String payamount=request.getParameter("payamount");//支付金额
				String payDate=request.getParameter("payDate");//支付时间
				String handle=request.getParameter("handle");//收银员
				String savetype=request.getParameter("savetype");
				String saleno=request.getParameter("saleno");
				String staffname=request.getParameter("staffname");
				String location=request.getParameter("location");
				if (!order.equals("") || !status.equals("") ) {
					int num=aDao.completed(order, status, user, new C_Payment(savetype,
							paymethod,
							Double.parseDouble(payamount),
							payDate,
							handle,
							"Y",staffname,location,saleno));
							if(num>0){
								out.print("success!");
							}else{
								out.print("error!");
							}
				}else {
					out.print("error!");
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("KeyRequest==>Pament操作异常："+e);
				out.print("Exception ："+e.toString());
			}finally{
				out.flush();
				out.close();
			}
		}
	 void complete(HttpServletRequest request, HttpServletResponse response) {
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		String result="";
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			String payment=request.getParameter("payment");
			String location=request.getParameter("location");
			String to=request.getParameter("to");
			
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=aDao.Ready(refno, type, user,"Y",payment,location,to);
			result=Util.returnValue(num);
			
			/*if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}*/
		}catch (Exception e) {
			//e.printStackTrace();
			result=Util.joinException(e);
			log.error("KeyRequest==>Ready操作异常："+e);
			//out.print("Exception :"+e.getMessage());
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		 
	}
	
	 void VOID(HttpServletRequest request, HttpServletResponse response) {
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		try{
			
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=aDao.VOID(refno, type, user,"D");
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("KeyRequest==>VOID操作异常："+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		 
	}
	
	
 
	 void add(HttpServletRequest request, HttpServletResponse response) {
		C_KeyDao aDao=new C_KeyDaoImpl();
		PrintWriter out=null;
		try{
			out=response.getWriter();
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String userType=request.getParameter("userType");
			String location=request.getParameter("location");
			String locker=request.getParameter("locker");
			String lockerno=request.getParameter("lockerno");
			String deskDrawer =request.getParameter("deskDrawer");
			String deskDrawerno=request.getParameter("deskDrawerno");
			String pigenBox=request.getParameter("pigenBox");
			String pigenBoxno=request.getParameter("pigenBoxno");
			String mobileDrawer=request.getParameter("mobileDrawer");
			String mobileno=request.getParameter("mobileno");
			String remarks=request.getParameter("remarks");
			
			String ret = aDao.complete(new C_Keys("",staffcode,staffname,userType,location,locker,lockerno,
					deskDrawer,deskDrawerno,pigenBox,pigenBoxno,mobileDrawer,mobileno,remarks,staffcode,
					DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y));
			out.print(ret);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("KeyRequest==>Save操作异常："+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	

}
