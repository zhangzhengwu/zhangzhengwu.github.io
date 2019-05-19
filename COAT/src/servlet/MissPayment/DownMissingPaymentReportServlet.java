package servlet.MissPayment;

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

import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Tools;
import util.Util;
import dao.MissingPaymentDao;
import dao.exp.ExpMissingPayment;
import dao.impl.MissingPaymentDaoImpl;
import entity.Missingreport;

public class DownMissingPaymentReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DownMissingPaymentReportServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	/*@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
		String startdate=request.getParameter("startdate");
		String enddate=request.getParameter("enddate");
		String staffcode=request.getParameter("staffcode");
		String principal=request.getParameter("principal");
		String clientname=request.getParameter("clientname");
		String policyno=request.getParameter("policyno");
		String ctype=request.getParameter("ctype");
		String lastday=request.getParameter("lastday");
		
		MissingPaymentDao mpr=new MissingPaymentDaoImpl();
		String username="";
		if(Util.objIsNULL(request.getSession().getAttribute("uType"))){
			
		}else{
			if(request.getSession().getAttribute("uType").toString().equals("Staff")){
				if(Util.objIsNULL(request.getSession().getAttribute("adminUsername"))){
					username=request.getSession().getAttribute("convoy_username").toString();
				}else{
					username="";
				}
			}else{
				if(Util.objIsNULL(staffcode)){
					if(Util.objIsNULL(request.getSession().getAttribute("convoy_username"))){
						staffcode="未知用户";
					}else{
						staffcode=request.getSession().getAttribute("convoy_username").toString();
					}
					
				}
			}
		}
		*//************************************************************************************************//*
		try {
			String fname ="Missing Payment Report Template format_"+DateUtils.getDateToday()+""; //Excel文件名  TRAINEE + 年  + 月
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
			//定义输出类型
			ResultSet res =mpr.selectMissForReport(staffcode, principal, startdate, enddate, clientname, policyno, ctype, lastday);
			
			ExpMissingPayment expcons=new ExpMissingPayment();
			if(res!=null){
				HSSFSheet sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "Report");
				HSSFRow row=sheet.createRow(0);
				row.setHeight((short) 400);
				 *//****************************设置Excel的列头*************************************************//*
				 expcons.cteateTitleCell(wb, row, (short)0, "Principal");
				 expcons.cteateTitleCell(wb, row, (short)1, "Policy Owner Name");
				 expcons.cteateTitleCell(wb, row, (short)2, "Policy Number");
				 expcons.cteateTitleCell(wb, row, (short)3, "Modal Premium ");  // Leave Type
				 expcons.cteateTitleCell(wb, row, (short)4, "Premium Due Date");
				 expcons.cteateTitleCell(wb, row, (short)5, "Principal Remark");
				 expcons.cteateTitleCell(wb, row, (short)6, "Last Update Date");
				 expcons.cteateTitleCell(wb, row, (short)7, "Next Collection Date");
				 expcons.createFixationSheet(res, os, wb, sheet);
				 res.close();
			}
			log.info(username+"导出Missing Payment 成功！");
		} catch (Exception e) {
			log.error(username+"导出Missing Payment —— servlet异常！"+e);
		}finally{
			Missingreport mp = new Missingreport ();
			mp.setUpddate(DateUtils.getNowDateTime());
			mp.setUpdname(staffcode);
			mp.setType("E");
			mpr.saveMissingReport(mp);
		}
	}*/
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate=request.getParameter("startdate");
		String enddate=request.getParameter("enddate");
		String staffcode=request.getParameter("staffcode");
		String principal=request.getParameter("principal");
		String clientname=request.getParameter("clientname");
		String policyno=request.getParameter("policyno");
		String ctype=request.getParameter("ctype");
		String lastday=request.getParameter("lastday");
		MissingPaymentDao mpr=new MissingPaymentDaoImpl();
		String username="";
		if(Util.objIsNULL(request.getSession().getAttribute("uType"))){
			
		}else{
			if(request.getSession().getAttribute("uType").toString().equals("Staff")){
				if(Util.objIsNULL(request.getSession().getAttribute("adminUsername"))){
					username=request.getSession().getAttribute("convoy_username").toString();
				}else{
					username="";
				}
			}else{
				if(Util.objIsNULL(staffcode)){
					if(Util.objIsNULL(request.getSession().getAttribute("convoy_username"))){
						staffcode="未知用户";
					}else{
						staffcode=request.getSession().getAttribute("convoy_username").toString();
					}
					
				}
			}
		}
		/************************************************************************************************/
		String fname ="Missing Payment Report Template format_"+DateUtils.getDateToday()+""; //Excel文件名  TRAINEE + 年  + 月
		try {
			//定义输出类型
			List<String[]> list =mpr.selectMissForReport(staffcode, principal, startdate, enddate, clientname, policyno, ctype, lastday);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0, "Staff Code");
			map.put(1, "Principal");
			map.put(2, "Policy Owner Name");
			map.put(3, "Policy Number");
			map.put(4, "Modal Premium ");  // Leave Type
			map.put(5, "Premium Due Date");
			map.put(6, "Principal Remark");
			map.put(7, "Last Update Date");
			map.put(8, "Next Collection Date");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					String str=Util.objIsNULL(list.get(i)[9]) ? list.get(i)[8]:(list.get(i)[8]+"/"+list.get(i)[9]);
					p.put("var1", str);
//					if(!Util.objIsNULL(list.get(i)[9])){
//						p.put("var1", list.get(i)[8]+"/"+list.get(i)[9]);
//					}else{
//						p.put("var1", list.get(i)[8]);
//					}
					p.put("var2", list.get(i)[0]); 
					p.put("var3", list.get(i)[1]); 
					p.put("var4", list.get(i)[2]); 
					p.put("var5", list.get(i)[3]); 
					p.put("var6", list.get(i)[4]); 
					p.put("var7", list.get(i)[5]); 
					p.put("var8", list.get(i)[6]); 
					p.put("var9", list.get(i)[7]); 
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
			view.buildExcelDocument2(model, wb,response,fname,"Report");
			log.info(username+"导出Missing Payment 成功！");
		} catch (Exception e) {
			log.error(username+"导出Missing Payment —— servlet异常！"+e);
		}finally{
			Missingreport mp = new Missingreport ();
			mp.setUpddate(DateUtils.getNowDateTime());
			mp.setUpdname(staffcode);
			mp.setType("E");
			mpr.saveMissingReport(mp);
		}
	}

}
