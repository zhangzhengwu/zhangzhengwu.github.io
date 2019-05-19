package servlet.Macau;

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

import servlet.DownChargeServlet;
import util.Constant;
import util.DateUtils;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.PayerMacauDao;
import dao.exp.ExpChargeRecord;
import dao.impl.PayerMacauDaoImpl;

public class DownMacauRecordServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DownChargeServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}

	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 导出方法的servlet
		 */
			LOG.info(request.getSession().getAttribute("adminUsername").toString()+"对Name Card Charge Record进行了导出操作！");
			 response.setContentType("text/html;charset=utf-8");
			 String startDate = request.getParameter("startDate");
			 String endDate = request.getParameter("endDate");
			 String staffcode = request.getParameter("staffcode");
			 HSSFWorkbook wb = new HSSFWorkbook();
			 ExpChargeRecord expcard = new ExpChargeRecord();
			 PayerMacauDao mDao = new PayerMacauDaoImpl();
			 try {
				 	String fname =Constant.CHARMACAU_NAME;//Excel文件名
					OutputStream os = response.getOutputStream();//取出输出流
					response.reset();//清空输出流
					//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
					response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
					//定义输出类型
					ResultSet res =mDao.selectCharge(startDate, endDate, staffcode);
				if(res!=null){
					HSSFSheet sheet = wb.createSheet("new sheet");
					wb.setSheetName(0, DateUtils.getNowDate()); //日期做 sheet 页码名称
					HSSFRow row=sheet.createRow(0);
					sheet.createFreezePane(0, 1);
					expcard.cteateTitleCell(wb,row,(short)0,"Date");
					expcard.cteateTitleCell(wb,row,(short)1,"Staff Code");
					expcard.cteateTitleCell(wb,row,(short)2,"Name");
					expcard.cteateTitleCell(wb,row,(short)3,"Qty");
					expcard.cteateTitleCell(wb,row,(short)4,"Amount");
					expcard.cteateTitleCell(wb,row,(short)5,"Payer");
					expcard.cteateTitleCell(wb,row,(short)6,"Remarks");
					expcard.cteateTitleCell(wb,row,(short)7,"Informed to FAD");
					expcard.cteateTitleCell(wb,row,(short)8,"Charged Month");
					expcard.createFixationSheet(res, os,wb,sheet);
					res.close();
				}
			} catch (Exception e) {
				LOG.error("导出方法的servlet"+e);
			}
	}
	}
