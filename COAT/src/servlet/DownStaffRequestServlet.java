package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DateHelper;
import util.DateUtils;
import dao.QueryStaffRequstDao;
import dao.exp.ExpCardQuota;
import dao.impl.QueryStaffRequstDaoImpl;
/**
 * DownStaffRequestServlet
 * @author Wilson
 *
 */
public class DownStaffRequestServlet extends HttpServlet {
Logger log=Logger.getLogger(DownStaffRequestServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer fname=new StringBuffer("Roymark_Staff_");
		response.setContentType("text/html;charset=utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
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
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
			//定义输出类型
			ResultSet res =rd.queryRequstListSet(name, code, startDate, endDate, location, urgentCase, payer,ET,layout);
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
				expcard.createFixationSheet(res, os,wb,sheet);
				res.close();
			}
		} catch (Exception e) {
			log.error("导出Staff时出现"+e.toString());
		}
	}
}