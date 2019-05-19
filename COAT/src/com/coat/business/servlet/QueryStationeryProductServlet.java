package com.coat.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;

import com.coat.business.dao.StationeryProductDaoImpl;

import entity.C_stationeryProduct;
import entity.Excel;

public class QueryStationeryProductServlet extends HttpServlet {

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

		String ename = request.getParameter("englishname");
		String cname = request.getParameter("chinesename");
		String procode = request.getParameter("procode");
		String blbz = request.getParameter("blbz");
		String method = request.getParameter("method");
		String result = null;
		
		if(method.equals("select")){
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			StationeryProductDaoImpl spImpl = new StationeryProductDaoImpl();
			int pagenow = Integer.parseInt(request.getParameter("pageNow"));
			Pager page = new Pager(pagenow, 15);
			try{
				page = spImpl.findAll(procode, ename, cname, blbz, page);
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
			response.setContentType("text/html");
			String price = request.getParameter("price");
			price = Util.objIsNULL(price)?"0":price;
			tempPrice = Double.parseDouble(price);
			String unit = request.getParameter("unit");
			String quantity = request.getParameter("quantity");
			quantity = Util.objIsNULL(quantity)?"0":quantity;
			tempQuantity = Double.parseDouble(quantity);
			
			String remark = request.getParameter("remark");
			String updates = DateUtils.getNowDateTime();
			
			PrintWriter out = response.getWriter();
			int num = -1, count = 0;
			StationeryProductDaoImpl spImpl = new StationeryProductDaoImpl();
			C_stationeryProduct sproduct = new C_stationeryProduct();
			sproduct.setEnglishname(ename);
			sproduct.setChinesename(cname);
			sproduct.setProcode(procode);
			sproduct.setPrice(tempPrice);
			sproduct.setUnit(unit);
			sproduct.setUpdates(updates);
			sproduct.setQuantity(tempQuantity);
			sproduct.setBLBZ(blbz);
			sproduct.setRemark1(remark);
			
			try{
				count = spImpl.isExist(procode);
				if(count==1){
					result = Util.getMsgJosnObject("error", "产品编号已存在");
				}else{
					num = spImpl.save(sproduct);
					result = Util.getMsgJonfornum(num);
				}
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
			response.setContentType("text/html");
			String price = request.getParameter("price");
			price = Util.objIsNULL(price)?"0":price;
			tempPrice = Double.parseDouble(price);
			
			String unit = request.getParameter("unit");
			String quantity = request.getParameter("quantity");
			quantity = Util.objIsNULL(quantity)?"0":quantity;
			tempQuantity = Double.parseDouble(quantity);
			String remark = request.getParameter("remark");
			
			PrintWriter out = response.getWriter();
			int num = -1;
			StationeryProductDaoImpl spImpl = new StationeryProductDaoImpl();
			C_stationeryProduct sproduct = new C_stationeryProduct();
			sproduct.setEnglishname(ename);
			sproduct.setChinesename(cname);
			sproduct.setProcode(procode);
			sproduct.setPrice(tempPrice);
			sproduct.setUnit(unit);
			sproduct.setQuantity(tempQuantity);
			sproduct.setBLBZ(blbz);
			sproduct.setRemark1(remark);
			
			try{
				num = spImpl.updateSP(sproduct);
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
			StationeryProductDaoImpl spImpl = new StationeryProductDaoImpl();
			List<Map<String,Object>>lists = null;
			Excel excel = new Excel();
			try{
				lists = spImpl.findAll(procode, ename, cname, blbz);
				excel.setExcelContentList(lists);
				excel.setColumns(new String[]{"产品编号","产品英文名称","产品中文名称","单价","单位","数量","适用类型","更新时间","备注"});
				excel.setHeaderNames(new String[]{"procode","englishname","chinesename","price","unit","quantity","status","updates","remark1"});
				excel.setSheetname("StationeryProduct");
				excel.setFilename("StationeryProduct"+System.currentTimeMillis()+".xls");
				String filename=ExcelTools.getExcelFileName(excel.getFilename());
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
		       //生成EXCEL
				ExcelTools.createExcel2(excel,response);
			}catch(Exception e){
				e.printStackTrace();
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
