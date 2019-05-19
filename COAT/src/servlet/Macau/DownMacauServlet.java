package servlet.Macau;

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

import servlet.DownRequestServlet;
import util.DateHelper;
import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Tools;
import util.Util;
import dao.MacauDao;
import dao.QueryCardConvoyDao;
import dao.exp.ExpMacau;
import dao.impl.MacauDaoImpl;
import dao.impl.QueryConvoyCardDaoImpl;

public class DownMacauServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DownRequestServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/*@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 	log.info(request.getSession().getAttribute("adminUsername").toString()+"对CIB_Consultant进行导出操作");
		StringBuffer fname=new StringBuffer("CIB_Consultant_");
		response.setContentType("text/html;charset=utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String ET_select  = request.getParameter("ET");
		String layout_select  = request.getParameter("layout_select");
		ExpMacau expcard = new ExpMacau();
		MacauDao rd=new MacauDaoImpl();
		fname.append(DateHelper.getMonthday());
	try {
		OutputStream os = response.getOutputStream();//取出输出流
		response.reset();//清空输出流
		//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
		//定义输出类型
		ResultSet res =rd.queryRequst(ET_select,layout_select,name, code, startDate, endDate, location, urgentCase);
		if(res!=null){
			HSSFSheet sheet = wb.createSheet("new sheet");
			wb.setSheetName(0, "Query Request");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 0);
			expcard.cteateTitleCell(wb,row,(short)0,"Query Request");
			expcard.cteateTitleCell(wb,row,(short)3,"UpDate:");
			expcard.cteateTitleCell(wb,row,(short)4,DateUtils.getNowDate());
			row=sheet.createRow(2);
			sheet.createFreezePane(0, 2);
			expcard.cteateTitleCell(wb,row,(short)0,"Year"+DateUtils.getYear());
			row = sheet.createRow((short)4);
			sheet.createFreezePane(0, 5);
		 *//****************************设置Excel的列头*************************************************//*
			expcard.cteateTitleCell(wb,row,(short)0,"Location");
			expcard.cteateTitleCell(wb,row,(short)1,"Check_Type");
			expcard.cteateTitleCell(wb, row, (short)2,"Layout_type");
			expcard.cteateTitleCell(wb, row, (short)3,"Elite Team");
			expcard.cteateTitleCell(wb,row,(short)4,"Staff_Code");
			expcard.cteateTitleCell(wb,row,(short)5,"Name");
			expcard.cteateTitleCell(wb,row,(short)6,"Name_in_Chinese");
			expcard.cteateTitleCell(wb,row,(short)7,"Title with Department in English");
			expcard.cteateTitleCell(wb,row,(short)8,"Title with Department in Chinese");
			expcard.cteateTitleCell(wb,row,(short)9,"External Title with Department in English");
			expcard.cteateTitleCell(wb,row,(short)10,"External Title with Department in Chinese");
			expcard.cteateTitleCell(wb,row,(short)11,"English Academic Title");
			expcard.cteateTitleCell(wb,row,(short)12,"Chinese Academic Title");
			expcard.cteateTitleCell(wb,row,(short)13,"English Professional Title");
			expcard.cteateTitleCell(wb,row,(short)14,"Chinese Professional Title");
			expcard.cteateTitleCell(wb,row,(short)15,"T.R. Reg. No.");
			expcard.cteateTitleCell(wb,row,(short)16,"CE No.");
			expcard.cteateTitleCell(wb,row,(short)17,"MPF No.");
			expcard.cteateTitleCell(wb,row,(short)18,"E-mail");
			expcard.cteateTitleCell(wb,row,(short)19,"Direct Line");
			expcard.cteateTitleCell(wb,row,(short)20,"Fax");
			expcard.cteateTitleCell(wb,row,(short)21,"Mobile Phone Number");
			expcard.cteateTitleCell(wb,row,(short)22,"Quantity");
			expcard.createFixationSheet(res, os,wb,sheet);
			res.close();
		}
	} catch (Exception e) {
		log.error("导出方法的servlet"+e);
	}
}*/
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		log.info(request.getSession().getAttribute("adminUsername").toString()+"对CIB_Consultant进行导出操作");
		StringBuffer fname=new StringBuffer("CIB_Consultant_");
		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String ET_select  = request.getParameter("ET");
		String layout_select  = request.getParameter("layout_select");
		MacauDao rd=new MacauDaoImpl();
		fname.append(DateHelper.getMonthday());
	try {
		//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		//定义输出类型
    	List<String[]> list=rd.queryRequst2(ET_select,layout_select,name, code, startDate, endDate, location, urgentCase);
    	Map<String, Object> model=new HashMap<String, Object>();
		List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
		Map<Integer,Object> map=new HashMap<Integer, Object>();
		title.add(map);
		map=new HashMap<Integer, Object>();
		map.put(0, "Query Request");
		map.put(3, "Download Time:");
		map.put(4, DateUtils.getNowDate());
		title.add(map);
		map=new HashMap<Integer, Object>();
		map.put(0, "Year"+DateUtils.getYear());
		title.add(map);
		map=new HashMap<Integer, Object>();
		title.add(map);
		map=new HashMap<Integer, Object>();
		map.put(0,"Location");
		map.put(1,"Check_Type");
		map.put(2,"Layout_type");
		map.put(3,"Elite Team");
		map.put(4,"Staff_Code");
		map.put(5,"Name");
		map.put(6,"Name_in_Chinese");
		map.put(7,"Title with Department in English");
		map.put(8,"Title with Department in Chinese");
		map.put(9,"External Title with Department in English");
		map.put(10,"External Title with Department in Chinese");
		map.put(11,"English Academic Title");
		map.put(12,"Chinese Academic Title");
		map.put(13,"English Professional Title");
		map.put(14,"Chinese Professional Title");
		map.put(15,"T.R. Reg. No.");
		map.put(16,"CE No.");
		map.put(17,"MPF No.");
		map.put(18,"E-mail");
		map.put(19,"Direct Line");
		map.put(20,"Fax");
		map.put(21,"Mobile Phone Number");
		map.put(22,"Quantity");
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
				p.put("var10",list.get(i)[9]);  
				p.put("var11",list.get(i)[10]);  
				p.put("var12",list.get(i)[11]);  
				p.put("var13",list.get(i)[12]);  
				p.put("var14",list.get(i)[13]); 
				p.put("var15",list.get(i)[14]); 
				p.put("var16",list.get(i)[15]); 
				p.put("var17",list.get(i)[16]); 
				p.put("var18",list.get(i)[17]); 
				p.put("var19",list.get(i)[18]); 
				p.put("var20",list.get(i)[19]); 
				p.put("var21",list.get(i)[20]); 
				p.put("var22",list.get(i)[21]); 
				p.put("var23",list.get(i)[22]); 
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
		view.buildExcelDocument2(model, wb,response,fname.toString(),"Query Request");
	
	} catch (Exception e) {
		log.error("导出方法的servlet"+e);
	}
}

}
