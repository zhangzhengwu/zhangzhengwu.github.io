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

import com.coat.business.dao.MarketPremiumDaoImpl;

import entity.C_marProduct;
import entity.Excel;

public class QueryMarketPremiumServlet extends HttpServlet {

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

		String method = request.getParameter("method");
		
		String result = null;
		
		if(method.equals("select")){
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String procode = request.getParameter("procode");
			String ename = request.getParameter("englishname");
			String cname = request.getParameter("chinesename");
			String blbz = request.getParameter("blbz");
			int pagenow = Integer.parseInt(request.getParameter("pageNow"));
			MarketPremiumDaoImpl mpImpl = new MarketPremiumDaoImpl();
			Pager page = new Pager(pagenow, 15);
			try{
				page = mpImpl.findAll(procode, ename, cname, blbz, page);
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
			String unitprice = request.getParameter("unitprice");
			unitprice = Util.objIsNULL(unitprice)?"0":unitprice;
			String clubprice = request.getParameter("clubprice");
			clubprice = Util.objIsNULL(clubprice)?"0":clubprice;
			String specialprice = request.getParameter("specialprice");
			specialprice = Util.objIsNULL(specialprice)?"0":specialprice;
			String unit = request.getParameter("unit");
			String quantity = request.getParameter("quantity");
			quantity = Util.objIsNULL(quantity)?"0":quantity;
			String remark = request.getParameter("remark");
			String updates = DateUtils.getNowDateTime();
			String procode = request.getParameter("procode");
			String ename = request.getParameter("englishname");
			String cname = request.getParameter("chinesename");
			String blbz = request.getParameter("blbz");
			int num = -1, count = 0;
			
			PrintWriter out = response.getWriter();
			C_marProduct mp = new C_marProduct();
			mp.setProcode(procode);
			mp.setEnglishname(ename);
			mp.setChinesename(cname);
			mp.setBLBZ(blbz);
			mp.setUnitprice(unitprice);
			mp.setC_clubprice(clubprice);
			mp.setSpecialprice(specialprice);
			mp.setUnit(unit);
			mp.setQuantity(quantity);
			mp.setUpdates(updates);
			mp.setRemark1(remark);
			
			MarketPremiumDaoImpl mpImpl = new MarketPremiumDaoImpl();
			try{
				//查看该产品编号是否存在
				count = mpImpl.isExist(procode);
				if(count==1){
					result = Util.getMsgJosnObject("error", "产品编号已存在");
				}else{
					num = mpImpl.save(mp);
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
			String unitprice = request.getParameter("unitprice");
			unitprice = Util.objIsNULL(unitprice)?"0":unitprice;
			String clubprice = request.getParameter("clubprice");
			clubprice = Util.objIsNULL(clubprice)?"0":clubprice;
			String specialprice = request.getParameter("specialprice");
			specialprice = Util.objIsNULL(specialprice)?"0":specialprice;
			String unit = request.getParameter("unit");
			String quantity = request.getParameter("quantity");
			quantity = Util.objIsNULL(quantity)?"0":quantity;
			String remark = request.getParameter("remark");
			String procode = request.getParameter("procode");
			String ename = request.getParameter("englishname");
			String cname = request.getParameter("chinesename");
			String blbz = request.getParameter("blbz");
			int num = -1;
			
			PrintWriter out = response.getWriter();
			C_marProduct mp = new C_marProduct();
			mp.setProcode(procode);
			mp.setEnglishname(ename);
			mp.setChinesename(cname);
			mp.setBLBZ(blbz);
			mp.setUnitprice(unitprice);
			mp.setC_clubprice(clubprice);
			mp.setSpecialprice(specialprice);
			mp.setUnit(unit);
			mp.setQuantity(quantity);
			mp.setRemark1(remark);
			
			MarketPremiumDaoImpl mpImpl = new MarketPremiumDaoImpl();
			
			try{
				num = mpImpl.updateMP(mp);
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
			MarketPremiumDaoImpl mpImpl = new MarketPremiumDaoImpl();
			String procode = request.getParameter("procode");
			String ename = request.getParameter("englishname");
			String cname = request.getParameter("chinesename");
			String blbz = request.getParameter("blbz");
			try{
				List<Map<String,Object>>lists = null;
				Excel excel = new Excel();
				lists = mpImpl.findAll(procode, ename, cname, blbz);
				excel.setExcelContentList(lists);
				excel.setColumns(new String[]{"产品编号","产品英文名称","产品中文名称","单价","俱乐部价格","特殊价格","单位","数量","适用类型"});
				excel.setHeaderNames(new String[]{"procode","englishname","chinesename","unitprice","c_clubprice","specialprice","unit","quantity","status"});
				excel.setSheetname("MarketingPremium");
				excel.setFilename("MarketingPremium"+System.currentTimeMillis()+".xls");
				String filename=ExcelTools.getExcelFileName(excel.getFilename());
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
		       //生成EXCEL
				ExcelTools.createExcel2(excel,response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(method.equals("delete")){
			PrintWriter out = response.getWriter();
			String procode = request.getParameter("id");
			int num = -1;
			MarketPremiumDaoImpl mpImpl = new MarketPremiumDaoImpl();
			try{
				num = mpImpl.delete(procode);
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
