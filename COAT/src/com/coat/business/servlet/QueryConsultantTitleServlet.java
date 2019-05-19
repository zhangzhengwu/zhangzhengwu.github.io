package com.coat.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.coat.business.dao.ConsultantTitleDaoImpl;
import com.coat.business.entity.PositionList;

import servlet.staffservlet.QueryStaff_listServlet;
import util.DBManager;
import util.DateUtils;
import util.ExcelTools;
import util.Page;
import util.Pager;
import util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import entity.Excel;
import entity.Position_list;

public class QueryConsultantTitleServlet extends HttpServlet {
	
	Logger logger = Logger.getLogger(QueryStaff_listServlet.class);
	String user = null;
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
		String method = request.getParameter("method").trim();
		String result = "";
		
		if(method.equals("select")){
			String ename = request.getParameter("position_ename").trim();
			String cname = request.getParameter("position_cname").trim();
			String sfyx = request.getParameter("position_sfyx").trim();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			int pagenow = Integer.parseInt(request.getParameter("pageNow"));
			ConsultantTitleDaoImpl ctImpl = new ConsultantTitleDaoImpl();
			
			try{
				Pager page = new Pager(pagenow,15);
				page = ctImpl.findAll(ename, cname, sfyx, page);
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
			String ename = request.getParameter("position_ename").trim();
			String cname = request.getParameter("position_cname").trim();
			String sfyx = request.getParameter("position_sfyx").trim();
			PrintWriter out = response.getWriter();
			user = (String)request.getSession().getAttribute("adminUsername");
			int num = -1;
			
			PositionList position = new PositionList();
			position.setPosition_ename(ename);
			position.setPosition_cname(cname);
			position.setAdd_date(DateUtils.getDateToday());
			position.setAdd_name(user);
			position.setSfyx(sfyx);
			ConsultantTitleDaoImpl ctImpl = new ConsultantTitleDaoImpl();
			
			try{
				num = ctImpl.save(position);
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
			String ename = request.getParameter("position_ename").trim();
			String cname = request.getParameter("position_cname").trim();
			String sfyx = request.getParameter("position_sfyx").trim();
			PrintWriter out = response.getWriter();
			int id = Integer.parseInt(request.getParameter("id"));
			int num = -1;
			
			PositionList position = new PositionList();
			position.setId(id);
			position.setPosition_ename(ename);
			position.setPosition_cname(cname);
			position.setSfyx(sfyx);
			
			ConsultantTitleDaoImpl ctImpl = new ConsultantTitleDaoImpl();
			
			try{
				num = ctImpl.modify(position);
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
			String ename = request.getParameter("position_ename").trim();
			String cname = request.getParameter("position_cname").trim();
			String sfyx = request.getParameter("position_sfyx").trim();
			ConsultantTitleDaoImpl ctImpl =  new ConsultantTitleDaoImpl();
			Excel excel = new Excel();
			
			try{
				List<Map<String,Object>> positions = ctImpl.findAll(ename, cname, sfyx);
				
				excel.setExcelContentList(positions);
				 //设置Excel列头
			    excel.setColumns(new String[]{"英文职位名称","中文职位名称","添加时间","添加人","状态"});
			    //属性字段名称
			    excel.setHeaderNames(new String[]{"position_ename","position_cname","add_date","add_name","status"});
			   //sheet名称
			    excel.setSheetname("ConsultantTitle");
			    //文件名称
				excel.setFilename("ConsultantTitle"+System.currentTimeMillis()+".xls");
				String filename=ExcelTools.getExcelFileName(excel.getFilename());
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
		       //生成EXCEL
				ExcelTools.createExcel2(excel,response);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("ConsultantTitle导出异常:"+e.toString());
			}
		}else if(method.equals("delete")){
			
			PrintWriter out = response.getWriter();
			int id = Integer.parseInt(request.getParameter("id"));
			int num = -1;
			ConsultantTitleDaoImpl ctImpl = new ConsultantTitleDaoImpl();
			try{
				num = ctImpl.delete(id);
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
