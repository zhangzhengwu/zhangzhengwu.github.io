 package servlet;

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
import com.coat.additionquota.dao.AdditionalQuotaDao;
import com.coat.additionquota.dao.impl.AdditionalQuotaDaoImpl;
import util.DateHelper;
import util.DateUtils;
import util.ExcelTools;
import util.ObjectExcelView;
import util.PageData;
import util.Tools;
import util.Util;
import dao.QueryCardConvoyDao;
import dao.exp.ExpCardConvoy;
import dao.impl.QueryConvoyCardDaoImpl;
import entity.Excel;
/**
 * 
 * @author king.xu
 *
 */
public class DownConvoyRequestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

Logger log=Logger.getLogger(DownConvoyRequestServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
/*	
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			log.info(request.getSession().getAttribute("adminUsername").toString()+"对Roymark_Consultant进行导出操作");
			StringBuffer fname=new StringBuffer("Roymark_Consultant_");
			response.setContentType("text/html;charset=utf-8");
			HSSFWorkbook wb = new HSSFWorkbook();
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String location  = request.getParameter("location");
			String urgentCase  = request.getParameter("urgentCase");
			String name  = request.getParameter("name");
			String code  = request.getParameter("code");
			String shzt  = request.getParameter("shzt");
			//导出类
			ExpCardConvoy expcard = new ExpCardConvoy();
			QueryCardConvoyDao rd = new QueryConvoyCardDaoImpl();
 
			fname.append(DateHelper.getMonthday());
		try {
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
			//定义输出类型
			ResultSet res = rd.queryRequst(name, code, startDate, endDate, location, urgentCase, shzt);
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
				expcard.cteateTitleCell(wb,row,(short)2,"Layout_type");
				expcard.cteateTitleCell(wb,row,(short)3,"Staff_Code");
				expcard.cteateTitleCell(wb,row,(short)4,"Name");
				expcard.cteateTitleCell(wb,row,(short)5,"Name_in_Chinese");
				expcard.cteateTitleCell(wb,row,(short)6,"Title with Department in English");
				expcard.cteateTitleCell(wb,row,(short)7,"Title with Department in Chinese");
				expcard.cteateTitleCell(wb,row,(short)8,"External Title with Department in English");
				expcard.cteateTitleCell(wb,row,(short)9,"External Title with Department in Chinese");
				expcard.cteateTitleCell(wb,row,(short)10,"English Academic Title");
				expcard.cteateTitleCell(wb,row,(short)11,"Chinese Academic Title");
				expcard.cteateTitleCell(wb,row,(short)12,"English Professional Title");
				expcard.cteateTitleCell(wb,row,(short)13,"Chinese Professional Title");
				expcard.cteateTitleCell(wb,row,(short)14,"T.R. Reg. No.");
				expcard.cteateTitleCell(wb,row,(short)15,"CE No.");
				expcard.cteateTitleCell(wb,row,(short)16,"MPF No.");
				expcard.cteateTitleCell(wb,row,(short)17,"E-mail");
				expcard.cteateTitleCell(wb,row,(short)18,"Direct Line");
				expcard.cteateTitleCell(wb,row,(short)19,"Fax");
				expcard.cteateTitleCell(wb,row,(short)20,"Mobile Phone Number");
				expcard.cteateTitleCell(wb,row,(short)21,"Quantity");
				expcard.cteateTitleCell(wb,row,(short)22,"SHZT");
				expcard.createFixationSheet(res, os,wb,sheet);
				res.close();
			}
		} catch (Exception e) {
			log.error("导出方法的servlet"+e);
		}
	}*/
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String shzt  = request.getParameter("shzt");
		QueryCardConvoyDao rd = new QueryConvoyCardDaoImpl();
	    try{
	    	List<Map<String,Object>> list=rd.queryRequst(name, code, startDate, endDate, location, urgentCase, shzt);
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
			map.put(0, "Location");
			map.put(1, "Check_Type");
			map.put(2, "Layout_type");
			map.put(3, "Staff_Code");
			map.put(4, "Name");
			map.put(5, "Name_in_Chinese");
			map.put(6, "Title with Department in English");
			map.put(7, "Title with Department in Chinese");
			map.put(8, "External Title with Department in English");
			map.put(9, "External Title with Department in Chinese");
			map.put(10, "English Academic Title");
			map.put(11, "Chinese Academic Title");
			map.put(12, "English Professional Title");
			map.put(13, "Chinese Professional Title");
			map.put(14, "T.R. Reg. No.");
			map.put(15, "CE No.");
			map.put(16, "MPF No.");
			map.put(17, "CIB Reg. No.");
			map.put(18, "E-mail");
			map.put(19, "Direct Line");
			map.put(20, "Fax");
			map.put(21, "Mobile Phone Number");
			map.put(22, "Quantity");
			map.put(23, "SHZT");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					p.put("var1", list.get(i).get("location")); 
					p.put("var2", list.get(i).get("type")); 
					p.put("var3", list.get(i).get("layout_type")); 
					p.put("var4", list.get(i).get("staff_code")); 
					p.put("var5", list.get(i).get("name")); 
					p.put("var6", list.get(i).get("name_chinese")); 
					p.put("var7", list.get(i).get("title_english")); 
					p.put("var8", list.get(i).get("title_chinese")); 
					p.put("var9", list.get(i).get("external_english")); 
					p.put("var10",list.get(i).get( "external_chinese")); 
					p.put("var11", list.get(i).get("academic_title_e")); 
					p.put("var12", list.get(i).get("academic_title_c")); 
					p.put("var13", list.get(i).get("profess_title_e")); 
					p.put("var14", list.get(i).get("profess_title_c")); 
					p.put("var15", list.get(i).get("tr_reg_no")); 
					p.put("var16", list.get(i).get("ce_no")); 
					p.put("var17", list.get(i).get("mpf_no")); 
					p.put("var18", list.get(i).get("remark1")); 
					p.put("var19", list.get(i).get("e_mail")); 
					p.put("var20", list.get(i).get("direct_line")); 
					p.put("var21", list.get(i).get("fax")); 
					p.put("var22", list.get(i).get("bobile_number")); 
					p.put("var23", list.get(i).get("quantity")); 
					p.put("var24", list.get(i).get("shzt")); 
					data.add(p);
				}
			}
			model.put("varList", data);
			Date date=new Date();
			String filename ="Roymark_Consultant_"+Tools.date2Str(date, "yyyyMMddHHmmss")+".xls";
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,filename,"Query Request");
		}catch (Exception e) {
	       log.error("NameCard Quota Query数据导出失败"+e.toString());
		   e.printStackTrace();
	    }
	}
	
}
