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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Tools;
import util.Util;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.QueryCardConvoyDao;
import dao.QueryChargeDao;
import dao.exp.ExpChargeRecord;
import dao.impl.QueryChargeDaoImpl;
import dao.impl.QueryConvoyCardDaoImpl;
import entity.Change_Record;

/**
 * 导出财务信息数据
 * @author kingxu
 *
 */
public class DownChargeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DownChargeServlet.class);
	/**
	 * 导出方法的servlet
	 */
	/*@SuppressWarnings("static-access")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.info(request.getSession().getAttribute("adminUsername").toString()+"对Name Card Charge Record进行了导出操作！");
		 response.setContentType("text/html;charset=utf-8");
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("staffcode");
		 HSSFWorkbook wb = new HSSFWorkbook();
		 ExpChargeRecord expcard = new ExpChargeRecord();
		 QueryChargeDao qcDao = new QueryChargeDaoImpl();
		 try {
			 	String fname =Constant.CHARGEFILE_NAME;//Excel文件名
				OutputStream os = response.getOutputStream();//取出输出流
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
				//定义输出类型
				ResultSet res =qcDao.selectCharge(startDate, endDate, staffcode);
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
	}*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("staffcode");
		 QueryChargeDao qcDao = new QueryChargeDaoImpl();
	    try{
	    	List<Change_Record> list=qcDao.selectCharge(startDate, endDate, staffcode);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0, "Date");
			map.put(1, "Staff Code");
			map.put(2, "Name");
			map.put(3, "Qty");
			map.put(4, "Amount");
			map.put(5, "Payer");
			map.put(6, "Remarks");
			map.put(7, "Informed to FAD");
			map.put(8, "Charged Month");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					p.put("var1", list.get(i).getAddDate()); 
					p.put("var2", list.get(i).getStaffCode()); 
					p.put("var3", list.get(i).getName()); 
					p.put("var4", list.get(i).getNumber()); 
					p.put("var5", list.get(i).getAmount()); 
					p.put("var6", list.get(i).getPayer()); 
					p.put("var7", list.get(i).getRemarks()); 
					p.put("var8", list.get(i).getInfomed_to_FAD()); 
					p.put("var9", list.get(i).getCharged_Month()); 
					data.add(p);
				}
			}
			model.put("varList", data);
			//Date date=new Date();
			String fname =Constant.CHARGEFILE_NAME;//Excel文件名
			//String filename ="Roymark_Consultant_"+Tools.date2Str(date, "yyyyMMddHHmmss")+".xls";
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,fname, DateUtils.getNowDate());
		}catch (Exception e) {
			LOG.error(Constant.CHARGEFILE_NAME+"数据导出失败"+e.toString());
		    e.printStackTrace();
	    }
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}
}