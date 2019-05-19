package com.coat.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ExcelTools;
import util.Pager;
import util.Util;

import com.coat.business.dao.AttachmentDaoImpl;

import entity.Excel;

public class QueryAttachmentServlet extends HttpServlet {

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
		String item = request.getParameter("item");
		String refno = request.getParameter("refno");
		String staffcode = request.getParameter("staffcode");
		String sfyx = request.getParameter("sfyx");
		String result = "";
		
		if(method.equals("select")){
			PrintWriter out = response.getWriter();
			AttachmentDaoImpl aIpml = new AttachmentDaoImpl(); 
			int pagenow = Integer.parseInt(request.getParameter("pageNow"));
			
			try{
				Pager page = new Pager(pagenow, 15);
				page = aIpml.findAll(item, refno, sfyx, page);
				result = Util.getMsgJosnSuccessReturn(page);
			}catch(Exception e){
				e.printStackTrace();
				result = Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}else if(method.equals("export")){
			AttachmentDaoImpl aIpml = new AttachmentDaoImpl(); 
			try{
				List<Map<String,Object>>lists = null;
				lists = aIpml.findAll(item, refno, sfyx);
				Excel excel = new Excel();
				excel.setExcelContentList(lists);
				excel.setColumns(new String[]{"附件编号","员工编号","员工姓名","附件名称","附件路径","添加人","添加时间","状态","项目位置"});
				excel.setHeaderNames(new String[]{"refno","staffcode","staffname","attachmentName","attachmentPath","creator","createDate","status","item"});
				excel.setSheetname("Attachment");
				excel.setFilename("Attachment"+System.currentTimeMillis()+".xls");
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
