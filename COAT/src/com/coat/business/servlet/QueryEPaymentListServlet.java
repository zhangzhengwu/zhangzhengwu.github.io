package com.coat.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coat.business.dao.EPaymentListDaoImpl;
import com.coat.business.entity.C_EPaymentList;

import entity.Excel;

import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;

public class QueryEPaymentListServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String method = request.getParameter("method");
		String procode = request.getParameter("procode");
		String proname = request.getParameter("proname");
		String sfyx = request.getParameter("sfyx");
		String result = "";
		
		if(method.equals("select")){
			int pagenow = Integer.parseInt(request.getParameter("pageNow"));
			Pager page = new Pager(pagenow, 15);
			EPaymentListDaoImpl eplImpl = new EPaymentListDaoImpl();  
			PrintWriter out = response.getWriter();
			try{
				page = eplImpl.findAll(procode, proname, sfyx, page);
				result = Util.getMsgJosnSuccessReturn(page);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}else if(method.equals("add")){
			double tempPrice = 0, tempQuantity = 0;
			String price = request.getParameter("price");
			price = Util.objIsNULL(price)?"0":price;
			tempPrice = Double.parseDouble(price);
			String quantity = request.getParameter("quantity");
			quantity = Util.objIsNULL(quantity)?"0":quantity;
			tempQuantity = Double.parseDouble(quantity);
			String unit = request.getParameter("unit");
			unit = Util.objIsNULL(unit)?"0":unit;
			String adddate = DateUtils.getNowDateTime();
			String addname = (String)request.getSession().getAttribute("adminUsername");
			String remark = request.getParameter("remark");
			int num = -1;
			
			PrintWriter out = response.getWriter();
			C_EPaymentList epl = new C_EPaymentList();
			epl.setProductcode(procode);
			epl.setProductname(proname);
			epl.setPrice(tempPrice);
			epl.setQuantity(tempQuantity);
			epl.setUnit(unit);
			epl.setAdddate(adddate);
			epl.setAddname(addname);
			epl.setSfyx(sfyx);
			epl.setRemark(remark);
			
			EPaymentListDaoImpl eplImpl = new EPaymentListDaoImpl();
			try{
				num = eplImpl.save(epl);
				result = Util.getMsgJonfornum(num);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
			
		}else if(method.equals("modify")){
			double tempPrice = 0, tempQuantity = 0;
			String price = request.getParameter("price");
			price = Util.objIsNULL(price)?"0":price;
			tempPrice = Double.parseDouble(price);
			String quantity = request.getParameter("quantity");
			quantity = Util.objIsNULL(quantity)?"0":quantity;
			tempQuantity = Double.parseDouble(quantity);
			String unit = request.getParameter("unit");
			unit = Util.objIsNULL(unit)?"0":unit;
			String remark = request.getParameter("remark");
			int id = Integer.parseInt(request.getParameter("proid"));
			int num = -1;
			
			PrintWriter out = response.getWriter();
			C_EPaymentList epl = new C_EPaymentList();
			epl.setId(id);
			epl.setProductcode(procode);
			epl.setProductname(proname);
			epl.setPrice(tempPrice);
			epl.setQuantity(tempQuantity);
			epl.setUnit(unit);
			epl.setSfyx(sfyx);
			epl.setRemark(remark);
			
			EPaymentListDaoImpl eplImpl = new EPaymentListDaoImpl();
			try{
				num = eplImpl.updateEPL(epl);
				result = Util.getMsgJonfornum(num);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
			
		}else if(method.equals("export")){
			EPaymentListDaoImpl eplImpl = new EPaymentListDaoImpl();
			List<Map<String,Object>>lists = null;
			Excel excel = new Excel();
			try{
				lists = eplImpl.findAll(procode, proname, sfyx);
				excel.setExcelContentList(lists);
				excel.setColumns(new String[]{"产品编号","产品名称","费用","数量","单价","添加人","添加时间","状态"});
				excel.setHeaderNames(new String[]{"productcode","productname","price","quantity","unit","addname","adddate","status"});
				excel.setSheetname("EPaymentList");
				excel.setFilename("EPaymentList"+System.currentTimeMillis()+".xls");
				String filename=ExcelTools.getExcelFileName(excel.getFilename());
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
		       //生成EXCEL
				ExcelTools.createExcel2(excel,response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(method.equals("delete")){
			int num = -1;
			int id = Integer.parseInt(request.getParameter("id"));
			PrintWriter out = response.getWriter();
			EPaymentListDaoImpl eplImpl = new EPaymentListDaoImpl();
			try{
				num = eplImpl.delete(id);
				result = Util.getMsgJonfornum(num);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
