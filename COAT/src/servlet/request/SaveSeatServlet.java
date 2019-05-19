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
import dao.C_SeatDao;
import dao.exp.ExpStaffPosition;
import dao.impl.C_SeatDaoImpl;
import entity.C_Seatassignment;

public class SaveSeatServlet extends HttpServlet {

	/**
	 * Wilson 2014-5-14 15:02:34
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SaveSeatServlet.class);
	String user=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mothed = request.getParameter("method");
		if (mothed.equals("down")) {
			doPost(request, response);
		}else {
			PrintWriter out = response.getWriter();
			out.print("You have no legal power!");
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
			}else if (mothed.toLowerCase().trim().equals("ready")) {
				Ready(request,response);
			}else if (mothed.toLowerCase().trim().equals("select")) {
				select(request,response);
			}else if (mothed.toLowerCase().trim().equals("delete")) {
				del(request,response);
			}else if (mothed.toLowerCase().trim().equals("detail")) {
				Detail(request,response);
			}else if (mothed.toLowerCase().trim().equals("down")) {
				down(request,response);
			}else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("Seat Assignment==>"+mothed+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Seat Assignment==>"+mothed+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		}finally{
			mothed=null;
		}
		
	}
	@SuppressWarnings({ "static-access", "unchecked" })
	private void down(HttpServletRequest request, HttpServletResponse response) {
		C_SeatDao aDao=new C_SeatDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		try{
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			Result rss=aDao.downSeatServiceList( startDate, endDate, staffcode, staffname, refno, status);
			OutputStream  os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=Request for Seat Assignment_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			//list=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			HSSFSheet sheet = wb.createSheet("Keys");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
				
				
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Ref.#");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Code");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Name");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"ext.no");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"floor");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"seat#");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"locker#");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"desk drawer#");
			expcard.cteateTWOTitleCell(wb,row,(short)8,"pigeon box#");
			expcard.cteateTWOTitleCell(wb,row,(short)9,"remarks"); 
			
			 sheet.setColumnWidth((short)0, 100*40); 
			 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
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
			            Map<String,String> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(i+1);
				        expcard.cteateTitleCell(wb,row6,(short)0,rows.get("refno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)1,rows.get("staffcode").toString());
				        expcard.cteateTitleCell(wb,row6,(short)2,rows.get("staffname").toString());
				        expcard.cteateTitleCell(wb,row6,(short)3,Util.objIsNULL(rows.get("extensionno"))?"":rows.get("extensionno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)4,Util.objIsNULL(rows.get("floor"))?"":rows.get("floor").toString());
				        expcard.cteateTitleCell(wb,row6,(short)5,Util.objIsNULL(rows.get("seatno"))?"":rows.get("seatno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)6,Util.objIsNULL(rows.get("lockerno"))?"":rows.get("lockerno").toString()); 
				        expcard.cteateTitleCell(wb,row6,(short)7,Util.objIsNULL(rows.get("deskDrawerno"))?"":rows.get("deskDrawerno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)8,Util.objIsNULL(rows.get("pigenBoxno"))?"":rows.get("pigenBoxno").toString()); 
				        expcard.cteateTitleCell(wb,row6,(short)9,rows.get("remark").toString());
				         
			         
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
			log.info(user+"==>Seat Assignment==>Export=="+DateUtils.getNowDateTime());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Seat Assignment==>Export操作异常："+e);
		}finally{
			aDao=null;
			wb=null;
			
		}
	}

	private void Detail(HttpServletRequest request, HttpServletResponse response){
		C_SeatDao seatDao=new C_SeatDaoImpl();
		PrintWriter out=null;
		C_Seatassignment cSeatassignment=null;
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			cSeatassignment=seatDao.findSearByRef(refno);
			JSONArray jsons=JSONArray.fromObject(cSeatassignment);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Seat Assignment==>Detail操作异常："+e);
		}finally{
			cSeatassignment=null;
			seatDao=null;
			out.flush();
			out.close();
		}
	}
	private void del(HttpServletRequest request, HttpServletResponse response) {
		C_SeatDao seatDao=new C_SeatDaoImpl();
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type= Constant.C_deleted;
			int num=seatDao.delSeat(refno, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
			log.info(user+"===>Seat Assignment==refno==="+refno+"---==type==="+ type+"===>Deleted操作影响行数"+num);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Seat Assignment==>Deleted操作异常："+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	private void select(HttpServletRequest request, HttpServletResponse response) {
		C_SeatDao seatDao=new C_SeatDaoImpl();
		PrintWriter out=null;
		 Page page=new Page();
		 List<C_Seatassignment> list=new ArrayList<C_Seatassignment>();
		try{
			out=response.getWriter();
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			
			page.setAllRows(seatDao.getRow(startDate, endDate, staffcode, staffname, refno, status));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			list=seatDao.findSeatServiceList(startDate, endDate, staffcode, staffname, refno, status, page);
			List list1=new ArrayList();
			list1.add(0,list);//数据存放
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总条数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Seat Assignment==>Search操作异常："+e);
			out.print("Search Exception :"+e.getMessage());
		}finally{
			list=null;
			out.flush();
			out.close();
		}
		
	}

	private void complete(HttpServletRequest request, HttpServletResponse response) {
		C_SeatDao seatDao=new C_SeatDaoImpl();
		PrintWriter out=null;
		String result="";
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			String to=request.getParameter("to");
			String location=request.getParameter("location");
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=seatDao.Completed(refno, type, user,to,location);
			result=Util.returnValue(num);
		}catch (Exception e) {
			result=Util.joinException(e);
			log.error("Seat Assignment==>Completed操作异常："+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		 
	}
	private void Ready(HttpServletRequest request, HttpServletResponse response) {
		C_SeatDao seatDao=new C_SeatDaoImpl();
		PrintWriter out=null;
		String result="";
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			String to=request.getParameter("to");
			String location=request.getParameter("location");
			
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=seatDao.Ready(refno, type, user,to,location);
			result=Util.returnValue(num);
		}catch (Exception e) {
			result=Util.joinException(e);
			log.error("Seat Assignment==>Completed操作异常："+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		
	}
 
	private void add(HttpServletRequest request, HttpServletResponse response) {
		C_SeatDao seatDao=new C_SeatDaoImpl();
		PrintWriter out= null;
		String result="";
		String staffcode=request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		String location=request.getParameter("location");
		String extensionno=request.getParameter("extensionno");
		String floor=request.getParameter("floor");
		String seatno =request.getParameter("seatno");
		String lockerno=request.getParameter("lockerno");
		String deskDrawerno=request.getParameter("deskDrawerno");
		String pigenBoxno=request.getParameter("pigenBoxno");
		String remark=request.getParameter("remark");
		
		try{
			out = response.getWriter();
			user=(String) request.getSession().getAttribute("adminUsername");
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			 result = seatDao.saveSeat(new C_Seatassignment("",staffcode,staffname,location,extensionno,floor,
				seatno,lockerno,deskDrawerno,pigenBoxno,remark,user,
				DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y));
		}catch (Exception e) {
			result=Util.jointException(e);
			log.error("Seat Assignment==>Save操作异常："+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}
	

}
