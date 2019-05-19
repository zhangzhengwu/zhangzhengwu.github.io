package servlet.staffservlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import servlet.DownStaffRequestServlet;
import util.Constant;
import util.DateHelper;
import util.DateUtils;
import util.FileUtil;
import util.Util;
import dao.LocationDao;
import dao.QueryStaffRequstDao;
import dao.exp.ExpCardQuota;
import dao.impl.LocationDaoImpl;
import dao.impl.QueryStaffRequstDaoImpl;

public class DownStaffRequestLocationServlet extends HttpServlet {
	Logger log=Logger.getLogger(DownStaffRequestServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer fname=new StringBuffer("NC_");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");  
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		//String nocode  = request.getParameter("nocode");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String ET=request.getParameter("ET");
		String layout=request.getParameter("layout");

		ExpCardQuota expcard = new ExpCardQuota();
		QueryStaffRequstDao rd=new QueryStaffRequstDaoImpl();
		fname.append(DateHelper.getMonthday());
		try {
			log.info("导出Staff  NameCard 开始...");
			String downFile="";
			downFile=Constant.StaffNameCardReport+DateUtils.getDateToday();
			if(FileUtil.directoryExists(downFile)){
				FileUtil.deleteAll(downFile);
				// new File(downFile).mkdirs();
			}else{
				new File(downFile).mkdirs();
			}
			LocationDao ld=new LocationDaoImpl();
			List<String> list =ld.queryLocationName();
			Result r=rd.queryRequstList(name, code, startDate, endDate, location, urgentCase, payer,ET,layout);
			for(int i=0;i<list.size();i++){
				//fname.append();
				OutputStream os = new FileOutputStream(downFile+"//"+fname+("_"+list.get(i)+"(Staff)")+".xls");
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
				HSSFWorkbook wb = new HSSFWorkbook();
				//定义输出类型
				//ResultSet res =rd.queryRequstListSet(name, code, startDate, endDate, location, urgentCase, payer,ET,layout);
			
				if(!Util.objIsNULL(r)&&r.getRowCount()>0){
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
					/*********************************设置Excel的列头*****************************************/
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
					//expcard.createFixationSheet(res, os,wb,sheet,list.get(i));
					expcard.createFixationSheetForResult(r, os, wb, sheet, list.get(i));
					
				}

			}

			out.print("导出成功!地址为:"+downFile.replaceAll("//", "/"));
			log.info("Staff NameCard 导出成功，地址为："+downFile.replaceAll("//", "/"));
			/* Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); 
			 StringSelection texts=new StringSelection(downFile.replaceAll("//", "/"));
			 clipboard.setContents(texts, null);*/
		}catch(FileNotFoundException f){
			out.print("error:另一个程序正在使用此文件，进程无法访问。");
			log.error("error:另一个程序正在使用此文件，进程无法访问。");
		} catch (Exception e) {
			log.error("导出Staff时出现"+e.toString());
		}
	}

}
