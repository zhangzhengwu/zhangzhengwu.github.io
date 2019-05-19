package com.coat.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coat.business.dao.PibaBookDaoImpl;
import com.coat.business.entity.C_PibaBook;

import entity.Excel;

import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;

public class QueryPibaBookServlet extends HttpServlet {

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

		String ename = request.getParameter("bookEName");
		String cname = request.getParameter("bookCName");
		String type = request.getParameter("type");
		String method = request.getParameter("method");
		String result = null;
		
		if(method.equals("select")){
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			int pagenow = Integer.parseInt(request.getParameter("pageNow"));
			Pager page = new Pager(pagenow, 15);
			PibaBookDaoImpl pbImpl = new PibaBookDaoImpl();
			
			try{
				page = pbImpl.findAll(ename, cname, type, page);
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
			response.setContentType("text/html");
			String num = request.getParameter("num");
			num = Util.objIsNULL(num)?"0":num;
			int quantity = 0;
			quantity = Integer.parseInt(num);
			String language = request.getParameter("language");
			String creator = (String)request.getSession().getAttribute("adminUsername");
			String createDate = DateUtils.getDateToday();
			PrintWriter out = response.getWriter();
			PibaBookDaoImpl pbImpl = new PibaBookDaoImpl();
			C_PibaBook pb = new C_PibaBook();
			pb.setBookCName(cname);
			pb.setBookEName(ename);
			pb.setType(type);
			pb.setNum(quantity);
			pb.setLanguage(language);
			pb.setCreator(creator);
			pb.setCraeteDate(createDate);
			
			int r = -1;
			try{
				r = pbImpl.save(pb);
				result = Util.getMsgJonfornum(r);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}else if(method.equals("modify")){
			response.setContentType("text/html");
			int bookNo = Integer.parseInt(request.getParameter("bookNo"));
			String num = request.getParameter("num");
			num = Util.objIsNULL(num)?"0":num;
			int quantity = 0;
			quantity = Integer.parseInt(num);
			String language = request.getParameter("language");
			PrintWriter out = response.getWriter();
			PibaBookDaoImpl pbImpl = new PibaBookDaoImpl();
			C_PibaBook pb = new C_PibaBook();
			int r = -1;
			pb.setBookNo(bookNo);
			pb.setBookCName(cname);
			pb.setBookEName(ename);
			pb.setType(type);
			pb.setNum(quantity);
			pb.setLanguage(language);
			
			try{
				r = pbImpl.updatePB(pb);
				result = Util.getMsgJonfornum(r);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}else if(method.equals("export")){
			PibaBookDaoImpl pbImpl = new PibaBookDaoImpl();
			List<Map<String,Object>> lists = null;
			try{
				lists = pbImpl.findAll(ename, cname, type);
				Excel excel = new Excel();
				excel.setExcelContentList(lists);
				excel.setColumns(new String[]{"保险中文名称","保险英文名称","类型","语言","数量"});
				excel.setHeaderNames(new String[]{"bookCName","bookEName","type","language","num"});
				excel.setSheetname("PibaBook");
				excel.setFilename("PibaBook"+System.currentTimeMillis()+".xls");
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
