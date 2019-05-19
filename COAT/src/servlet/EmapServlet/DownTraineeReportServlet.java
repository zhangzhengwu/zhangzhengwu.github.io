package servlet.EmapServlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Tools;
import util.Util;


import dao.EtraineeDao;
import dao.exp.ExpTraineeRecord;
import dao.impl.EtraineeDaoImpl;

public class DownTraineeReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DownTraineeReportServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 
		response.setContentType("text/html;charset=utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
		String startDate=request.getParameter("start_Date");
		String endDate=request.getParameter("end_Date");
		String staffcode=request.getParameter("staffcode");
		String username="";
		if(request.getSession().getAttribute("adminUsername")==null){
			username="Auxiliary tools";
		}else{
		 username=request.getSession().getAttribute("adminUsername").toString();
		}
		
		EtraineeDao  ec = new EtraineeDaoImpl();

		/******************************************分页代码***********************************************/
		//String pageIndex = request.getParameter("curretPage");
		int currentPage = 1;//当前页
		int pageSize = 30;//页面大小
		int y = DateUtils.getYear(Util.objIsNULL(startDate)?endDate:startDate);
		int m = DateUtils.getMonth(Util.objIsNULL(startDate)?endDate:startDate);
		String year = y+"";
		String month = m+"";
		int total=0;//得到总记录数
		int totalPage=0;//总页数
		/*if(Util.objIsNULL(pageIndex)){//页面当前页为空时
			pageIndex="1";//强制赋值第一页
		}
		currentPage=Integer.parseInt(pageIndex);//设置当前页
*/		total=ec.getCount(staffcode, startDate, endDate);//得到总页数
		totalPage=total/pageSize;//总页数=总记录数/页面大小
		if(total%pageSize>0){//如果总记录数%页面大小==0时
			totalPage+=1;
		}
		if(currentPage>=totalPage){
			currentPage=totalPage;
		}if(currentPage<=1){
			currentPage=1;
		}

		/************************************************************************************************/
		try {
			String fname =Constant.TRAINEE_report+"_"+year+"_"+month+""; //Excel文件名  TRAINEE + 年  + 月
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
			//定义输出类型
			ResultSet res =ec.queryrsbystaff(staffcode, startDate, endDate, pageSize, currentPage);
			ExpTraineeRecord expcons=new ExpTraineeRecord();
			if(res!=null){
				HSSFSheet sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "DATA");
				HSSFRow row=sheet.createRow(0);
				 /****************************设置Excel的列头*************************************************/
				 expcons.cteateTitleCell(wb, row, (short)0, "Num");
				 expcons.cteateTitleCell(wb, row, (short)1, "Code");
				 expcons.cteateTitleCell(wb, row, (short)2, "Code 2");
				 expcons.cteateTitleCell(wb, row, (short)3, "Date");  // Leave Type
				 expcons.cteateTitleCell(wb, row, (short)4, "Date 2");
				 expcons.cteateTitleCell(wb, row, (short)5, "");
				 expcons.cteateTitleCell(wb, row, (short)6, "");
				 expcons.cteateTitleCell(wb, row, (short)7, "");
				 expcons.cteateTitleCell(wb, row, (short)8, "");
				 expcons.cteateTitleCell(wb, row, (short)9, "Leave Type");
				 expcons.cteateTitleCell(wb, row, (short)10, "Resign Date");
				 expcons.cteateTitleCell(wb, row, (short)11, "Join Title");
				 expcons.createFixationSheet(res, os, wb, sheet);
				 res.close();
			}
			log.info(username+"导出Trainee 成功！");
		} catch (Exception e) {
			log.error(username+"导出Trainee —— servlet异常！"+e);
		}finally{
			
		}
	}
	
	/*public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String startDate=request.getParameter("start_Date");
		String endDate=request.getParameter("end_Date");
		String staffcode=request.getParameter("staffcode");
		String username="";
		if(request.getSession().getAttribute("adminUsername")==null){
			username="Auxiliary tools";
		}else{
		 username=request.getSession().getAttribute("adminUsername").toString();
		}
		EtraineeDao  ec = new EtraineeDaoImpl();
		*//******************************************分页代码***********************************************//*
		int currentPage = 1;//当前页
		int pageSize = 30;//页面大小
		int y = DateUtils.getYear(Util.objIsNULL(startDate)?endDate:startDate);
		int m = DateUtils.getMonth(Util.objIsNULL(startDate)?endDate:startDate);
		String year = y+"";
		String month = m+"";
		*//************************************************************************************************//*
		try {
			String fname =Constant.TRAINEE_report+"_"+year+"_"+month+""; //Excel文件名  TRAINEE + 年  + 月
			//定义输出类型
			List<String []> list =ec.queryrsbystaff(staffcode, startDate, endDate, pageSize, currentPage);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			*//****************************设置Excel的列头*************************************************//*
			map.put( 0, "Num");
			map.put( 1, "Code");
			map.put( 2, "Code 2");
			map.put( 3, "Date");  // Leave Type
			map.put( 4, "Date 2");
			map.put( 5, "");
			map.put( 6, "");
			map.put( 7, "");
			map.put( 8, "");
			map.put( 9, "Leave Type");
			map.put( 10, "Resign Date");
			map.put( 11, "Join Title");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					p.put("var1", list.get(i)[0]); 
					p.put("var2", list.get(i)[0]); 
					p.put("var3", list.get(i)[0]); 
					p.put("var4", list.get(i)[0]); 
					p.put("var5", list.get(i)[0]); 
					p.put("var6", list.get(i)[0]); 
					p.put("var7", list.get(i)[0]); 
					p.put("var8", list.get(i)[0]); 
					p.put("var9", list.get(i)[0]); 
					p.put("var10",list.get(i)[0]); 
					p.put("var11", list.get(i)[0]); 
					p.put("var12", list.get(i)[0]); 
					data.add(p);
				}
			}
			model.put("varList", data);
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,fname,"DATA");
			log.info(username+"导出Trainee 成功！");
		} catch (Exception e) {
			log.error(username+"导出Trainee —— servlet异常！"+e);
		}finally{
			
		}
	}*/

}
