package servlet.EmapServlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Util;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.EpartConsultantReportDao;
import dao.exp.ExpConsultantReport;
import dao.impl.EpartConsultantReportDaoImpl;

public class DownpartConsultantReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Log LOG = LogFactory.getLog(DownpartConsultantReportServlet.class);
		response.setContentType("text/html;charset=utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
		String startDate=request.getParameter("start_Date");
		String endDate=request.getParameter("end_Date");
		String staffcode=request.getParameter("staffcode");
		EpartConsultantReportDao ec=new EpartConsultantReportDaoImpl();
		/******************************************分页代码***********************************************/
		String pageIndex = request.getParameter("curretPage");
		int currentPage = 1;//当前页
		int pageSize = 30;//页面大小
		int total=0;//得到总记录数
		int totalPage=0;//总页数
		int y = DateUtils.getYear(Util.objIsNULL(startDate)?endDate:startDate);
		int m = DateUtils.getMonth(Util.objIsNULL(startDate)?endDate:startDate);
		String year = y+"";
		String month = m+"";
		if(Util.objIsNULL(pageIndex)){//页面当前页为空时
			pageIndex="1";//强制赋值第一页
		}
		currentPage=Integer.parseInt(pageIndex);//设置当前页
		total=ec.getCount(staffcode, startDate, endDate);//得到总页数
		totalPage=total/pageSize;//总页数=总记录数/页面大小
		if(total%pageSize>0){//如果总记录数%页面大小==0时
			totalPage+=1;
		}
		if(currentPage>totalPage){
			currentPage=totalPage;
		}if(currentPage<1){
			currentPage=1;
		}

		/************************************************************************************************/
		try {
			
			String fname =""+Constant.CONS_MAP_REPORT+"_"+year+"_"+month+""; //Excel文件名  map + 年  + 月
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
			//定义输出类型
			ResultSet res =ec.queryrsbystaff(staffcode, startDate, endDate, pageSize, currentPage);
			ExpConsultantReport expcons=new ExpConsultantReport();
			HSSFRow row =null;
			HSSFSheet sheet = null;
			if(res!=null){
				sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "Penalty");
				row=sheet.createRow(0);
				expcons.cteateTitleCell(wb, row, (short)0,"Monthly Late Penalty Records");
				row=sheet.createRow(1);
				sheet.createFreezePane(0,1);
				expcons.cteateTitleCell(wb, row, (short)0,"Year");
				expcons.cteateTitleCell(wb, row, (short)1,year);
				expcons.cteateTitleCell(wb, row, (short)2,"Month");
				expcons.cteateTitleCell(wb, row, (short)3,month);
				 row=sheet.createRow(2);
				 sheet.createFreezePane(0,3);
				 /****************************设置Excel的列头*************************************************/
				 expcons.cteateTitleCell(wb, row, (short)0, "Recruiter");
				 expcons.cteateTitleCell(wb, row, (short)1, "Code");
				 expcons.cteateTitleCell(wb, row, (short)2, "Position");
				 expcons.cteateTitleCell(wb, row, (short)3, "Name");
				 expcons.cteateTitleCell(wb, row, (short)4, "Code");
				 expcons.cteateTitleCell(wb, row, (short)5, "num");
				 expcons.cteateDateTitleCell(wb, row, (short)6, year+"-"+month+"-1");
				 expcons.cteateDateTitleCell(wb, row, (short)7, year+"-"+month+"-2");
				 expcons.cteateDateTitleCell(wb, row, (short)8, year+"-"+month+"-3");
				 expcons.cteateDateTitleCell(wb, row, (short)9, year+"-"+month+"-4");
				 expcons.cteateDateTitleCell(wb, row, (short)10, year+"-"+month+"-5");
				 expcons.cteateDateTitleCell(wb, row, (short)11, year+"-"+month+"-6");
				 expcons.cteateDateTitleCell(wb, row, (short)12, year+"-"+month+"-7");
				 expcons.cteateDateTitleCell(wb, row, (short)13, year+"-"+month+"-8");
				 expcons.cteateDateTitleCell(wb, row, (short)14, year+"-"+month+"-9");
				 expcons.cteateDateTitleCell(wb, row, (short)15, year+"-"+month+"-10");
				 expcons.cteateDateTitleCell(wb, row, (short)16, year+"-"+month+"-11");
				 expcons.cteateDateTitleCell(wb, row, (short)17, year+"-"+month+"-12");
				 expcons.cteateDateTitleCell(wb, row, (short)18, year+"-"+month+"-13");
				 expcons.cteateDateTitleCell(wb, row, (short)19, year+"-"+month+"-14");
				 expcons.cteateDateTitleCell(wb, row, (short)20, year+"-"+month+"-15");
				 expcons.cteateDateTitleCell(wb, row, (short)21, year+"-"+month+"-16");
				 expcons.cteateDateTitleCell(wb, row, (short)22, year+"-"+month+"-17");
				 expcons.cteateDateTitleCell(wb, row, (short)23, year+"-"+month+"-18");
				 expcons.cteateDateTitleCell(wb, row, (short)24, year+"-"+month+"-19");
				 expcons.cteateDateTitleCell(wb, row, (short)25, year+"-"+month+"-20");
				 expcons.cteateDateTitleCell(wb, row, (short)26, year+"-"+month+"-21");
				 expcons.cteateDateTitleCell(wb, row, (short)27, year+"-"+month+"-22");
				 expcons.cteateDateTitleCell(wb, row, (short)28, year+"-"+month+"-23");
				 expcons.cteateDateTitleCell(wb, row, (short)29, year+"-"+month+"-24");
				 expcons.cteateDateTitleCell(wb, row, (short)30, year+"-"+month+"-25");
				 expcons.cteateDateTitleCell(wb, row, (short)31, year+"-"+month+"-26");
				 expcons.cteateDateTitleCell(wb, row, (short)32, year+"-"+month+"-27");
				   expcons.cteateDateTitleCell(wb, row, (short)33, year+"-"+month+"-28");
				 expcons.cteateDateTitleCell(wb, row, (short)34, year+"-"+month+"-29");
				 expcons.cteateDateTitleCell(wb, row, (short)35, year+"-"+month+"-30");
				 expcons.cteateDateTitleCell(wb, row , (short)36, year+"-"+month+"-31");
				 if (DateUtils.getDays(y,m) <= 31) {
					 expcons.cteateDateTitleCell(wb, row, (short)33, year+"-"+month+"-28");
					 expcons.cteateDateTitleCell(wb, row, (short)34, year+"-"+month+"-29");
					 expcons.cteateDateTitleCell(wb, row, (short)35, year+"-"+month+"-30");
					 expcons.cteateDateTitleCell(wb, row , (short)36, year+"-"+month+"-31");
				 }
				 if (DateUtils.getDays(y,m) <= 30){
					 expcons.cteateDateTitleCell(wb, row, (short)33, year+"-"+month+"-28");
					 expcons.cteateDateTitleCell(wb, row, (short)34, year+"-"+month+"-29");
					 expcons.cteateDateTitleCell(wb, row, (short)35, year+"-"+month+"-30");
					 expcons.cteateTitleCell(wb, row , (short)36, "-");
			 	 }
				 if (DateUtils.getDays(y,m) <= 29) {
					 expcons.cteateDateTitleCell(wb, row, (short)33, year+"-"+month+"-28");
					 expcons.cteateDateTitleCell(wb, row, (short)34, year+"-"+month+"-29");
					 expcons.cteateTitleCell(wb, row, (short)35, "-");
					 expcons.cteateTitleCell(wb, row, (short)36, "-");
			 	 } 
				 if (DateUtils.getDays(y,m) <= 28) {
					 expcons.cteateDateTitleCell(wb, row, (short)33, year+"-"+month+"-28");
					 expcons.cteateTitleCell(wb, row, (short)34, "-");
					 expcons.cteateTitleCell(wb, row, (short)35, "-");
					 expcons.cteateTitleCell(wb, row, (short)36, "-");
				 } 
				
				 expcons.cteateTitleCell(wb, row, (short)37, "Total");
				 expcons.cteateTitleCell(wb, row, (short)38, "C-Club");
				 expcons.cteateTitleCell(wb, row, (short)39, "Penalty");
				 expcons.cteateTitleCell(wb, row, (short)40, "");
				 expcons.cteateTitleCell(wb, row, (short)41, "No.of MAP");
				 expcons.cteateTitleCell(wb, row, (short)42, "");
				 expcons.cteateTitleCell(wb, row, (short)43, "Working Day");
				 expcons.cteateTitleCell(wb, row, (short)44, "Annual Leave");
				 expcons.cteateTitleCell(wb, row, (short)45, "Sick Leave");
				 expcons.cteateTitleCell(wb, row, (short)46, "Other Leave");
				 expcons.cteateTitleCell(wb, row, (short)47, "Card Borrow");
				 expcons.cteateTitleCell(wb, row, (short)48, "MAP");
				 expcons.cteateTitleCell(wb, row, (short)49, "Lateness");
				 expcons.cteateTitleCell(wb, row, (short)50, "No Show");
				 expcons.cteateTitleCell(wb, row, (short)51, "On Time");
				 expcons.cteateTitleCell(wb, row, (short)52, "% Lateness");
				 expcons.cteateTitleCell(wb, row, (short)53, "% No Show");
				 expcons.cteateTitleCell(wb, row, (short)54, "%　On Time");
				 expcons.createFixationSheet(res, os, wb, sheet);
				 res.close();
			} 
		}catch (Exception e) {
			LOG.error("导出方法的servlet"+e);
		}finally{
 
		}
	}
	
	/*public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Log LOG = LogFactory.getLog(DownpartConsultantReportServlet.class);
		response.setContentType("text/html;charset=utf-8");
		String startDate=request.getParameter("start_Date");
		String endDate=request.getParameter("end_Date");
		String staffcode=request.getParameter("staffcode");
		EpartConsultantReportDao ec=new EpartConsultantReportDaoImpl();
		*//******************************************分页代码***********************************************//*
		String pageIndex = request.getParameter("curretPage");
		int currentPage = 1;//当前页
		int pageSize = 30;//页面大小
		int total=0;//得到总记录数
		int totalPage=0;//总页数
		int y = DateUtils.getYear(Util.objIsNULL(startDate)?endDate:startDate);
		int m = DateUtils.getMonth(Util.objIsNULL(startDate)?endDate:startDate);
		String year = y+"";
		String month = m+"";
		if(Util.objIsNULL(pageIndex)){//页面当前页为空时
			pageIndex="1";//强制赋值第一页
		}
		currentPage=Integer.parseInt(pageIndex);//设置当前页
		total=ec.getCount(staffcode, startDate, endDate);//得到总页数
		totalPage=total/pageSize;//总页数=总记录数/页面大小
		if(total%pageSize>0){//如果总记录数%页面大小==0时
			totalPage+=1;
		}
		if(currentPage>totalPage){
			currentPage=totalPage;
		}if(currentPage<1){
			currentPage=1;
		}
		*//************************************************************************************************//*
		try {
			String fname =""+Constant.CONS_MAP_REPORT+"_"+year+"_"+month+""; //Excel文件名  map + 年  + 月
			//定义输出类型
			List<String[]> list =ec.queryrsbystaff(staffcode, startDate, endDate, pageSize, currentPage);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0, "Monthly Late Penalty Records");
			title.add(map);
			map=new HashMap<Integer, Object>();
			map.put(0, "Year");
			map.put(1, year);
			map.put(2, "Month");
			map.put(3, month);
			title.add(map);
			map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			 *//****************************设置Excel的列头*************************************************//*
			 map.put(0, "Recruiter");
			 map.put(1, "Code");
			 map.put(2, "Position");
			 map.put(3, "Name");
			 map.put(4, "Code");
			 map.put(5, "num");
			 map.put(6, year+"-"+month+"-1");
			 map.put(7, year+"-"+month+"-2");
			 map.put(8, year+"-"+month+"-3");
			 map.put(9, year+"-"+month+"-4");
			 map.put(10, year+"-"+month+"-5");
			 map.put(11, year+"-"+month+"-6");
			 map.put(12, year+"-"+month+"-7");
			 map.put(13, year+"-"+month+"-8");
			 map.put(14, year+"-"+month+"-9");
			 map.put(15, year+"-"+month+"-10");
			 map.put(16, year+"-"+month+"-11");
			 map.put(17, year+"-"+month+"-12");
			 map.put(18, year+"-"+month+"-13");
			 map.put(19, year+"-"+month+"-14");
			 map.put(20, year+"-"+month+"-15");
			 map.put(21, year+"-"+month+"-16");
			 map.put(22, year+"-"+month+"-17");
			 map.put(23, year+"-"+month+"-18");
			 map.put(24, year+"-"+month+"-19");
			 map.put(25, year+"-"+month+"-20");
			 map.put(26, year+"-"+month+"-21");
			 map.put(27, year+"-"+month+"-22");
			 map.put(28, year+"-"+month+"-23");
			 map.put(29, year+"-"+month+"-24");
			 map.put(30, year+"-"+month+"-25");
			 map.put(31, year+"-"+month+"-26");
			 map.put(32, year+"-"+month+"-27");
			 if (DateUtils.getDays(y,m) <= 31) {
				 map.put(33, year+"-"+month+"-28");
				 map.put(34, year+"-"+month+"-29");
				 map.put(35, year+"-"+month+"-30");
				 map.put(36, year+"-"+month+"-31");
			 }
			 if (DateUtils.getDays(y,m) <= 30){
				 map.put(33, year+"-"+month+"-28");
				 map.put(34, year+"-"+month+"-29");
				 map.put(35, year+"-"+month+"-30");
				 map.put(36, "-");
		 	 }
			 if (DateUtils.getDays(y,m) <= 29) {
				 map.put(33, year+"-"+month+"-28");
				 map.put(34, year+"-"+month+"-29");
				 map.put(35, "-");
				 map.put(36, "-");
		 	 } 
			 if (DateUtils.getDays(y,m) <= 28) {
				 map.put(33, year+"-"+month+"-28");
				 map.put(34, "-");
				 map.put(35, "-");
				 map.put(36, "-");
			 } 
			 map.put(37, "Total");
			 map.put(38, "C-Club");
			 map.put(39, "Penalty");
			 map.put(40, "");
			 map.put(41, "No.of MAP");
			 map.put(42, "");
			 map.put(43, "Working Day");
			 map.put(44, "Annual Leave");
			 map.put(45, "Sick Leave");
			 map.put(46, "Other Leave");
			 map.put(47, "Card Borrow");
			 map.put(48, "MAP");
			 map.put(49, "Lateness");
			 map.put(50, "No Show");
			 map.put(51, "On Time");
			 map.put(52, "% Lateness");
			 map.put(53, "% No Show");
			 map.put(54, "%　On Time");
			 title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					p.put("var1", list.get(i)[0]); 
					p.put("var2", list.get(i)[1]); 
					p.put("var3", list.get(i)[2]); 
					p.put("var4", list.get(i)[3]); 
					p.put("var5", list.get(i)[4]); 
					p.put("var6", list.get(i)[5]); 
					p.put("var7", list.get(i)[6]); 
					p.put("var8", list.get(i)[7]); 
					p.put("var9", list.get(i)[8]); 
					p.put("var10", list.get(i)[9]); 
					p.put("var11", list.get(i)[10]); 
					p.put("var12", list.get(i)[11]); 
					p.put("var13", list.get(i)[12]); 
					p.put("var14", list.get(i)[13]); 
					p.put("var15", list.get(i)[14]); 
					p.put("var16", list.get(i)[15]); 
					p.put("var17", list.get(i)[16]); 
					p.put("var18", list.get(i)[17]); 
					p.put("var19", list.get(i)[18]); 
					p.put("var20", list.get(i)[19]); 
					p.put("var21", list.get(i)[20]); 
					p.put("var22", list.get(i)[21]); 
					p.put("var23", list.get(i)[22]); 
					p.put("var24", list.get(i)[23]); 
					p.put("var25", list.get(i)[24]); 
					p.put("var26", list.get(i)[25]); 
					p.put("var27", list.get(i)[26]); 
					p.put("var28", list.get(i)[27]); 
					p.put("var29", list.get(i)[28]); 
					p.put("var30", list.get(i)[29]); 
					p.put("var31", list.get(i)[30]); 
					p.put("var32", list.get(i)[31]); 
					p.put("var33", list.get(i)[32]); 
					p.put("var34", list.get(i)[33]); 
					p.put("var35", list.get(i)[34]); 
					p.put("var36", list.get(i)[35]); 
					p.put("var37", list.get(i)[36]); 
					p.put("var38", list.get(i)[37]); 
					p.put("var39", list.get(i)[38]); 
					p.put("var40", list.get(i)[39]); 
					p.put("var41", list.get(i)[40]); 
					p.put("var42", list.get(i)[41]); 
					p.put("var43", list.get(i)[42]); 
					p.put("var44", list.get(i)[43]); 
					p.put("var45", list.get(i)[44]); 
					p.put("var46", list.get(i)[45]); 
					p.put("var47", list.get(i)[46]); 
					p.put("var48", list.get(i)[47]); 
					p.put("var49", list.get(i)[48]); 
					p.put("var50", list.get(i)[49]); 
					p.put("var51", list.get(i)[50]); 
					p.put("var52", list.get(i)[51]); 
					p.put("var53", list.get(i)[52]); 
					p.put("var54", list.get(i)[53]); 
					p.put("var55", list.get(i)[54]); 
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
			view.buildExcelDocument2(model, wb,response,fname,"Penalty");
		}catch (Exception e) {
			LOG.error("导出方法的servlet"+e);
		}finally{
 
		}
	}*/
	

}
