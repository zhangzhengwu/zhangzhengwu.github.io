package com.coat.additionquota.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;

import com.coat.additionquota.dao.AdditionalQuotaDao;
import com.coat.additionquota.dao.impl.AdditionalQuotaDaoImpl;
import com.coat.additionquota.entity.QueryAdditional;

import dao.exp.ExpCardQuota;
import entity.Excel;

/**
 * 新增 卡片 张数 信息
 * @author Hugo
 *
 */
public class AdditionalQuotaServlet extends HttpServlet {
	Logger log=Logger.getLogger(AdditionalQuotaServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			doPost(request, response);
	}
	String user="";
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		//System.out.println("method-->"+method);
		try {
			if(method.equals("select")){
				select(request,response);
			}else if(method.equals("add")){
				add(request,response);
			}else if(method.equals("modify")){
				modify(request,response);
			}else if(method.equals("queryQuotaChecking")){
				queryQuotaChecking(request,response);
			}else if(method.equals("down")){
				downCardQuota(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
		} catch (Exception e) {
			log.error("AdditionalQuotaServlet Service==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}finally{
			method=null; 
			
		} 
	}
	
	public void select(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		String StaffNo = request.getParameter("StaffNo");
		String start_date=request.getParameter("start_date");
		String end_date=request.getParameter("end_date");
		String Valid = request.getParameter("Valid");
		AdditionalQuotaDao ad = new AdditionalQuotaDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=ad.findPager(null, page,Util.modifystartdate(start_date),Util.modifyenddate(end_date),Util.modifyString(StaffNo),Util.modifyString(Valid));
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
				
			JSONArray jsons = JSONArray.fromObject(list);
			out.println(jsons.toString());
		} catch (Exception e) {
			log.error("在AdditionalQuotaServlet中对nq_additional进行查询时出现"+e.toString());
		} finally {
			out.flush();
			out.close();
		}
	}

	public void queryQuotaChecking(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String staffcode =request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		AdditionalQuotaDao qdao=new AdditionalQuotaDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=qdao.findCardQuotaList(null, page,Util.modifyString(staffcode),Util.modifyString(staffname));
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
			
			//System.out.println(page.getPagenow()+"---"+page.getTotalpage());
			JSONArray jsons = JSONArray.fromObject(list);
			out.println(jsons.toString());
		}catch (Exception e) {
			log.error("在AdditionalQuotaServlet中进行查询时出现"+e.toString());
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		} 
	
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
	     try{
			 QueryAdditional qa=new QueryAdditional();
			 qa.setStaffNo(request.getParameter("ins"));
			 qa.setName(request.getParameter("n"));
			 qa.setRemark(request.getParameter("Re"));
		     qa.setAdd_name(request.getSession().getAttribute("adminUsername").toString());
		     qa.setNum("100");
		     qa.setSfyx("Y");
		     AdditionalQuotaDao ad = new AdditionalQuotaDaoImpl();
		     ad.saveLogin(qa);
		     out.print("1");
	     }catch(Exception e){
	    	 log.equals("AdditionalQuotaServlet 添加Additional時出現："+e);
	     }finally{
	    	 out.flush();
		     out.close();
	     }
	}
	
	public void modify(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String StaffNo = request.getParameter("StaffNo");
		String Additional = request.getParameter("num");
		String Remarks = request.getParameter("Remark");
		String reremark = request.getParameter("reremark");
		String sfyx = request.getParameter("select");
		AdditionalQuotaDao md = new AdditionalQuotaDaoImpl();
		try{
			int r = md.updateAdditional(StaffNo, Additional, Remarks, sfyx, reremark);
			if (r < 0) {
					out.print("-1");
			} else {
					out.print("1");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("在AdditionalQuotaServlet中对nq_additional进行修改时出现"+e.toString()	);
		}finally{
			out.flush();
			out.close();
		}
	}

	/*@SuppressWarnings("static-access")
	public void down(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		log.info(request.getSession().getAttribute("adminUsername").toString()+"对NameCard Quota Query 进行了导出操作！");
		response.setContentType("text/html;charset=utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
	 	ExpCardQuota expcard = new ExpCardQuota();
		//QueryCardQuotaDao qcDao = new QueryCardQuotaDaoImpl();
		AdditionalQuotaDao qcDao=new AdditionalQuotaDaoImpl();
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
		//ExcelTools
		
	}catch (Exception e) {
		log.error("导出方法的servlet"+e);
	}
 }
	*/
	public void downCardQuota(HttpServletRequest request ,HttpServletResponse response)throws ServletException, IOException{
		//Excel 数据导出
	  	//拿到查询条件
		String staffcode =request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		AdditionalQuotaDao qcDao=new AdditionalQuotaDaoImpl();
	    Excel excel=new Excel();
	    try{
		    //查询数据
		    List<Map<String,Object>>list=qcDao.queryCardQuota(staffcode,staffname);
			//把数据交给Excel
		    excel.setExcelContentList(list);	
		    //设置Excel列头
		    excel.setColumns(new String[]{"Initials","Name","Name_in_Chinese","Entitled Quota","Additional","Total_Quota","Quota_Used","Self_Paid","Balance","Add_Date"});
		    //属性字段名称
		    excel.setHeaderNames(new String[]{"employeeId","EmployeeName","C_Name","eqnum","addnum","total_quota","used","selfpay","balance","adddate"});
		   //sheet名称
		    excel.setSheetname("NameCard Quota Query");
		    //文件名称
			excel.setFilename("NameCard Quota Query"+System.currentTimeMillis());
			String filename=ExcelTools.getExcelFileName(excel.getFilename());
			response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
	       //生成EXCEL
			ExcelTools.createExcel2(excel,response);
	    }catch (Exception e) {
	       log.error("NameCard Quota Query数据导出失败"+e.toString());
		   e.printStackTrace();
	    }
		
	
	}
	
}