package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dao.QueryRequstDao;
import dao.exp.ExpRequestnew;
import dao.impl.QueryRequstDaoImpl;

import util.DateUtils;
import util.FileUtil;

public class SpecifiedExportServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer fname=new StringBuffer("NameCardInfo_Report");
		QueryRequstDao rd=new QueryRequstDaoImpl();
		try{
			 
			String downFile="\\\\hkgnas11\\sz-hk$\\ITD\\DefaultSignature\\"+DateUtils.getDateToday();
			if(FileUtil.directoryExists(downFile)){
				FileUtil.deleteAll(downFile);
				// new File(downFile).mkdirs();
			}else{
				new File(downFile).mkdirs();
			}
			//fname.append();
			OutputStream os = new FileOutputStream(downFile+"\\"+fname+".xls");
			response.reset();//清空输出流
			HSSFWorkbook wb = new HSSFWorkbook();
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			//response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
			ExpRequestnew expcard = new ExpRequestnew();
			ResultSet res=null;
			res =rd.autoReportRequstListSet();
			if(res!=null){
				HSSFSheet sheet = wb.createSheet();
				wb.setSheetName(0, "Query Request");
				//wb.setSheetName(1, "Query Requests");
				HSSFRow row=sheet.createRow(0);
				sheet.createFreezePane(0, 0);
				expcard.cteateTitleCell(wb,row,(short)0,"Query Request");
				expcard.cteateTitleCell(wb,row,(short)3,"Report Date:");
				expcard.cteateTitleCell(wb,row,(short)4,DateUtils.getNowDate());
				row=sheet.createRow(2);
				sheet.createFreezePane(0, 2);
				expcard.cteateTitleCell(wb,row,(short)0,"Year"+DateUtils.getYear());
				row = sheet.createRow((short)4);
				sheet.createFreezePane(0, 5);
				/****************************设置Excel的列头*************************************************/
				expcard.cteateTitleCell(wb,row,(short)0,"Location");
				expcard.cteateTitleCell(wb,row,(short)1,"Company Name");
				expcard.cteateTitleCell(wb, row, (short)2,"Layout_type");
				//expcard.cteateTitleCell(wb, row, (short)3,"Elite Team");
				expcard.cteateTitleCell(wb,row,(short)3,"Staff_Code");
				expcard.cteateTitleCell(wb,row,(short)4,"Name");
				expcard.cteateTitleCell(wb,row,(short)5,"Name_in_Chinese");
				expcard.cteateTitleCell(wb,row,(short)6,"Title with Department in English");
				expcard.cteateTitleCell(wb,row,(short)7,"Title with Department in Chinese");
				expcard.cteateTitleCell(wb,row,(short)8,"External Title with Department in English");
				expcard.cteateTitleCell(wb,row,(short)9,"External Title with Department in Chinese");
				/*				expcard.cteateTitleCell(wb,row,(short)11,"English Academic Title");
			expcard.cteateTitleCell(wb,row,(short)12,"Chinese Academic Title");*/
				expcard.cteateTitleCell(wb,row,(short)10,"English Academic & Professional Title");
				expcard.cteateTitleCell(wb,row,(short)11,"Chinese Academic & Professional Title");
				expcard.cteateTitleCell(wb,row,(short)12,"T.R. Reg. No.");
				expcard.cteateTitleCell(wb,row,(short)13,"CE No.");
				expcard.cteateTitleCell(wb,row,(short)14,"MPF No.");
				expcard.cteateTitleCell(wb,row,(short)15,"E-mail");
				expcard.cteateTitleCell(wb,row,(short)16,"Direct Line");
				expcard.cteateTitleCell(wb,row,(short)17,"Fax");
				expcard.cteateTitleCell(wb,row,(short)18,"Mobile Phone Number");
			 
				expcard.cteateTitleCell(wb,row,(short)19,"Submit Date");

				/**  sheet 2**/
				expcard.createFixationSheet(res, os,wb,sheet);
				//expcard.createFixationSheet(res, os, wb, sheet);
				res.close();
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		
	}

}
