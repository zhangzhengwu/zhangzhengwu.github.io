package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DateUtils;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.QueryCardQuotaDao;
import dao.exp.ExpCardQuota;
import dao.impl.QueryCardQuotaDaoImpl;
/**
 * 导出CardQuota信息数据
 * @author kingxu
 *
 */
public class DownCardQuotaServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DownCardQuotaServlet.class);
	/**
	 * 导出方法的servlet
	 */
	@SuppressWarnings("static-access")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			LOG.info(request.getSession().getAttribute("adminUsername").toString()+"对NameCard Quota Query 进行了导出操作！");
			response.setContentType("text/html;charset=utf-8");
		
			HSSFWorkbook wb = new HSSFWorkbook();
		 	ExpCardQuota expcard = new ExpCardQuota();
			QueryCardQuotaDao qcDao = new QueryCardQuotaDaoImpl();
		try {
			String staffcode =request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String fname ="NameCard Quota Query";//Excel文件名
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
			//定义输出类型
			ResultSet res =qcDao.selectCardQuota(staffcode,staffname);
			if(res!=null){
				HSSFSheet sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "NameCard Quota Query");
				HSSFRow row=sheet.createRow(1);
				sheet.createFreezePane(0, 1);
				expcard.cteateTitleCell(wb,row,(short)0,"NameCard QuotaQuery");
				expcard.cteateTitleCell(wb,row,(short)7,"Upto:");
				expcard.cteateTitleCell(wb,row,(short)8,DateUtils.getNowDate());
				row=sheet.createRow(2);
				sheet.createFreezePane(0, 2);
				expcard.cteateTitleCell(wb,row,(short)0,"Year"+DateUtils.getYear());
				row = sheet.createRow(4);
				sheet.createFreezePane(0, 5);
				expcard.cteateTitleCell(wb,row,(short)0,"Initials");
				expcard.cteateTitleCell(wb,row,(short)1,"Name");
				expcard.cteateTitleCell(wb,row,(short)2,"Name_in_Chinese");
				expcard.cteateTitleCell(wb,row,(short)3,"Entitled Quota");
				expcard.cteateTitleCell(wb,row,(short)4,"Additional");
				expcard.cteateTitleCell(wb,row,(short)5,"Total_Quota");
				expcard.cteateTitleCell(wb,row,(short)6,"Quota_Used");
				expcard.cteateTitleCell(wb,row,(short)7,"Self_Paid");
				expcard.cteateTitleCell(wb,row,(short)8,"Balance");
				expcard.createFixationSheet(res, os,wb,sheet);
				res.close();
			}
		} catch (Exception e) {
			LOG.error("导出方法的servlet"+e);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
