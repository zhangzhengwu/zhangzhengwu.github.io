package com.coat.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import servlet.staffservlet.QueryStaff_listServlet;
import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;

import com.coat.business.dao.ConsultantProTitleDaoImpl;

import entity.Excel;
import entity.Professional_title;

public class QueryConsultantProTitleServlet extends HttpServlet {
	Logger logger = Logger.getLogger(QueryStaff_listServlet.class);
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

	public static void main(String[] args) {
		Pager page=new Pager(1,15);
		System.out.println(JSONObject.fromObject(page).toString());
		
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
		
		String result="";//{status:'success',msg:'',data:''}
		
		
		if(method.equals("select")){
			response.setContentType("text/html");
			String ename = request.getParameter("pro_title_ename");
			String cname = request.getParameter("pro_title_cname");
			String sfyx = request.getParameter("pro_title_sfyx");
			int curPage = Integer.parseInt(request.getParameter("pageNow")); 
			int record = 0;

			PrintWriter out = response.getWriter();

			try{
				Pager page=new Pager(curPage,15);
				ConsultantProTitleDaoImpl cptImpl = new ConsultantProTitleDaoImpl();
				page=cptImpl.findAll(ename, cname, sfyx, page);
				
				System.out.println(JSONObject.fromObject(page).toString());
				//result=Util.getMsgJonfornum(num);
				
				//JSONArray jsons = JSONArray.fromObject(page);jsons.toString();
				
				result=Util.getMsgJosnSuccessReturn(page);//{state:'success','msg':'success',data:{pagenow:1,pagesize:15,total:10,list:}}
				 
						
			}catch (Exception e) {
				e.printStackTrace();
				result=Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
			
		}else if(method.equals("add")){
			response.setContentType("text/html");
			String date = DateUtils.getDateToday();
			String user = (String)request.getSession().getAttribute("adminUsername");
			String ename = request.getParameter("pro_title_ename");
			String cname = request.getParameter("pro_title_cname");
			String sfyx = request.getParameter("pro_title_sfyx");
			Professional_title pro = new Professional_title();
			PrintWriter out = response.getWriter();
			int num = -1;
			
			ConsultantProTitleDaoImpl cptImpl = new ConsultantProTitleDaoImpl();
			
			pro.setProf_title_ename(ename);
			pro.setProf_title_cname(cname);
			pro.setAdd_date(date);
			pro.setAdd_name(user);
			pro.setSfyx(sfyx);
			
			try{
				num = cptImpl.save(pro);
				result = Util.getMsgJonfornum(num);
			}catch(Exception e){
				e.printStackTrace();
				result=Util.joinException(e);
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}else if(method.equals("modify")){
			int num =-1;
			String id = request.getParameter("id");
			String ename = request.getParameter("pro_title_ename");
			String cname = request.getParameter("pro_title_cname");
			String sfyx = request.getParameter("pro_title_sfyx");
			ConsultantProTitleDaoImpl cptImpl = new ConsultantProTitleDaoImpl();
			Professional_title pro = new Professional_title();
			PrintWriter out = response.getWriter();
			
			pro.setProf_title_ename(ename);
			pro.setProf_title_cname(cname);
			pro.setSfyx(sfyx);
			pro.setId(id);
			
			try{
				num = cptImpl.updateCPT(pro);
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
			List<Map<String,Object>> lists = null;
			ConsultantProTitleDaoImpl cptImpl = new ConsultantProTitleDaoImpl();
			String ename = request.getParameter("pro_title_ename");
			String cname = request.getParameter("pro_title_cname");
			String sfyx = request.getParameter("pro_title_sfyx");
			Excel excel = new Excel();
			
			try{
				lists = cptImpl.findAll(ename, cname, sfyx);
				excel.setExcelContentList(lists);
				excel.setColumns(new String[]{"英文专业名称","中文专业名称","添加时间","添加人","状态"});
				excel.setHeaderNames(new String[]{"prof_title_ename","prof_title_cname","add_date","add_name","status"});
				excel.setSheetname("ConsultantProTitle");
				excel.setFilename("ConsultantProTitle"+System.currentTimeMillis()+".xls");
				String filename=ExcelTools.getExcelFileName(excel.getFilename());
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
		       //生成EXCEL
				ExcelTools.createExcel2(excel,response);
				
			}catch(Exception e){
				e.printStackTrace();
				logger.info("export cpt error");
			}
		}else if(method.equals("delete")){
			String id = request.getParameter("id");
			PrintWriter out = response.getWriter();
			int num = -1;
			ConsultantProTitleDaoImpl cptImpl = new ConsultantProTitleDaoImpl();
			try{
				num = cptImpl.delete(id);
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
