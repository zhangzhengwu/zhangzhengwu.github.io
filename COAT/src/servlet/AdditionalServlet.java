package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.AdditionalDao;
import dao.impl.AdditionalDaoImpl;
import entity.QueryAdditional;

public class AdditionalServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Logger log=Logger.getLogger(AdditionalServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	     AdditionalDao ad=new AdditionalDaoImpl();
	     ad.add(qa);
	     	out.print("1");
	     }catch(Exception e){
	    	 log.equals("AdditionalServlet.java 添加Additional時出現："+e);
	     }finally{
	    	 out.flush();
		     out.close();
	     }
	}
}
