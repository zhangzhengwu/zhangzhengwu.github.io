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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.Constant;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.PIBAStudyNoteDao;
import dao.exp.ExpStaffPosition;
import dao.impl.PIBAStudyNoteDaoImpl;
import entity.CPibaBook;
import entity.CPibaOrder;
import entity.CPibaOrderDetial;
import entity.CPibaSigndetial;

public class PIBAStudyNoteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(PIBAStudyNoteServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	 
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
	
		try{
			 user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equals("select"))
				select(request, response);
			else if(method.equals("down"))
				down(request, response);
			else if(method.equals("detail"))
				Detail(request,response);
			else if(method.equals("complete"))
				complete(request,response);
			else if(method.equals("Ready"))
				Ready(request,response);
			else if(method.equals("VOID"))
				VOID(request,response);
			else if(method.equals("delete"))
				delete(request,response);
			else if(method.equals("deleteCon"))
				deleteCon(request,response);
			else if(method.equals("Submit"))
				Submits(request,response);
			else if(method.equals("queryBook"))
				queryBook(request,response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("PIBACard==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PIBACard==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 
	}
	
	void queryBook(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		 List<CPibaBook> list=null;
		try{
			out=response.getWriter();
			list=acd.queryBook();
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("AccessCard Detail Exception :"+e.getMessage());
		}finally{
			acd=null;
			list=null;
			out.flush();
			out.close();
		}
	}
	
	
 @SuppressWarnings("unchecked")
 void select(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
	 Page page=new Page();
	 List<CPibaOrder> list=new ArrayList<CPibaOrder>();
	try{
		out=response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		String refno=request.getParameter("refno");
		String status=request.getParameter("status");
		String start_date=request.getParameter("start_date");
		String end_date=request.getParameter("end_date");

		page.setAllRows(acd.getRow(staffcode, staffname, refno, status,start_date,end_date));
		page.setPageSize(15);//设置页面显示行数
		page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
		list=acd.findAccessList(staffcode, staffname, refno, status, page,start_date,end_date);
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
		log.error("AccessCard-Search Exception :"+e.getMessage());
		out.print("Search Exception :"+e.getMessage());
	}finally{
		acd=null;
		list=null;
		out.flush();
		out.close();
	}
  }
 
 @SuppressWarnings({ "static-access", "unchecked" })
 void down(HttpServletRequest request, HttpServletResponse response){
	 	PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream  os=null;
		try{
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			Result rss=acd.downPIBAList(startDate, endDate, staffcode, staffname, refno, status);
			os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=PIBA_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			HSSFSheet sheet = wb.createSheet("PIBA Order Detail");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"Location");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"Status");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"bookType");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"bookName");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"bookNum");
			expcard.cteateTWOTitleCell(wb,row,(short)8,"createDate");
			/*expcard.cteateTWOTitleCell(wb,row,(short)9,"payment");
			expcard.cteateTWOTitleCell(wb,row,(short)10,"Cash");
			expcard.cteateTWOTitleCell(wb,row,(short)11,"Octopus");
			expcard.cteateTWOTitleCell(wb,row,(short)12,"EPS");
			expcard.cteateTWOTitleCell(wb,row,(short)13,"Remarks");
			expcard.cteateTWOTitleCell(wb,row,(short)14,"Status");
			expcard.cteateTWOTitleCell(wb,row,(short)15,"Time");*/
			
			 sheet.setColumnWidth((short)0, 100*40); 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.setColumnWidth((short)2, 100*50); 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.autoSizeColumn((short)8); // 调整第四列宽度 
	         /*sheet.autoSizeColumn((short)9); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)10); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)11); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)12); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)13); // 调整第四列宽度 
	         sheet.setColumnWidth((short)14, 100*40); 
	         sheet.autoSizeColumn((short)15); // 调整第四列宽度 */			 
	        //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(i+1);
				        expcard.cteateTitleCell(wb,row6,(short)0,rows.get("refno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)1,rows.get("staffcode").toString());
				        expcard.cteateTitleCell(wb,row6,(short)2,rows.get("staffname").toString());
				        expcard.cteateTitleCell(wb,row6,(short)3,rows.get("location").toString());
				        expcard.cteateTitleCell(wb,row6,(short)4,Util.objIsNULL(rows.get("status"))?"":rows.get("status").toString());
				        expcard.cteateTitleCell(wb,row6,(short)5,Util.objIsNULL(rows.get("type"))?"":rows.get("type").toString());
				        expcard.cteateTitleCell(wb,row6,(short)6,Util.objIsNULL(rows.get("bookCName"))?"":rows.get("bookCName").toString());
				        expcard.cteateTitleCell(wb,row6,(short)7,Util.objIsNULL(rows.get("bookNum"))?"":rows.get("bookNum").toString()); 
				        expcard.cteateTitleCell(wb,row6,(short)8,Util.objIsNULL(rows.get("createDate"))?"":rows.get("createDate").toString());
				        
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Access-Export Exception :"+e.getMessage());
		}finally{
			acd=null;
			wb=null;
		
		}
	}

	@SuppressWarnings("unchecked")
	void Detail(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		List<CPibaOrderDetial> list=null;
		List<CPibaSigndetial>  list2=null;
		List lists=new ArrayList();
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			list=acd.findAdminserviceByRef(refno);
			list2=acd.findCPibaSigndetial(refno);
			
			lists.add(0, list);
			lists.add(1, list2);
			JSONArray jsons=JSONArray.fromObject(lists);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PIBA Detail Exception :"+e.getMessage());
		}finally{
			acd=null;
			list=null;
			out.flush();
			out.close();
		}
	}
 
 
	 void Submits(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		try{
			out=response.getWriter();
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String location=request.getParameter("location");
			//String bookIds=request.getParameter("bookIds");
			String type=request.getParameter("type");
			String userType=request.getParameter("userType");
			
			String bookTypes=request.getParameter("bookTypes");  //书本的类型
			String bookNames=request.getParameter("bookNames");	 //书本的名称
			String bookNumber=request.getParameter("bookNumber");//书本的数量
			String signcodes=request.getParameter("signcodes");	 //拿单人编号
			String signnames=request.getParameter("staffnames"); //拿单人姓名
			
			int num=acd.submitOrder(staffcode,staffname,location,type,user,userType,signcodes,signnames,bookTypes,bookNames,bookNumber);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PIBACard==>HKADM Exception :"+e.getMessage());
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
 
 void complete(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			int num=acd.complete(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PIBACard==>Ready Exception :"+e.getMessage());
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
}

 /**
  * 订单准备
  * TODO：订单准备之后需要发送邮件(待完成)
  * @param request
  * @param response
  */
 void Ready(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
	String result="";
	try{
		out=response.getWriter();
		String refno=request.getParameter("refno");
		String type=request.getParameter("type");
		String staffnames=request.getParameter("staffnames");
		String location=request.getParameter("location");
	
		int num=acd.Ready(refno, type, user,staffnames,location);
		result=Util.returnValue(num);
	
	}catch (Exception e) {
		log.error("PIBACard==>Ready Exception :"+e.getMessage());
		result=Util.joinException(e);
	}finally{
		out.print(result);
		out.flush();
		out.close();
	}
}

 /**
  * 取消订单
  * @param request
  * @param response
  */
 void VOID(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			int num=acd.VOID(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PIBACard==>VOID Exception :"+e.getMessage());
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
 
 
 
 
 void delete(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
	try{
		out=response.getWriter();
		String refno=request.getParameter("refno");
		String type=request.getParameter("type");
		int num=acd.Deleted(refno, type, user);
		if(num>0){
			out.print("Success!");
		}else{
			out.print("Error!");
		}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("PIBACard==>Delete Exception :"+e.getMessage());
		out.print("Exception :"+e.getMessage());
	}finally{
		out.flush();
		out.close();
	}
}
 
 void deleteCon(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PIBAStudyNoteDao acd=new PIBAStudyNoteDaoImpl();
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			int num=acd.DeletedCon(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PIBACard==>Delete Exception :"+e.getMessage());
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}

}
