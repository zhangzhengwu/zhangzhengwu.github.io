package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.MotifyDao;
import dao.impl.MotifyDaoImpl;
import entity.QueryAdditional;
/**
 * 中转页面
 * @author king.xu
 *
 */
public class MotifyServlet extends HttpServlet {
Logger log=Logger.getLogger(MotifyServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 PrintWriter out=response.getWriter();
		 response.setContentType("text/html;charset=utf-8");
		 String StaffNo=request.getParameter("StaffNo");
		 String Remark=request.getParameter("Remark");
		 MotifyDao md=new MotifyDaoImpl();
		 QueryAdditional qa=new QueryAdditional(); 
		 try{
		 qa= md.getAdditional(StaffNo, Remark);
		 request.getSession().setAttribute("additional",qa);
		 //request.getRequestDispatcher("queryAdditional_modify.jsp").forward(request, response)
		 response.sendRedirect("queryAdditional_modify.jsp");
		 }catch(Exception e){
			 log.error("對nq_additional进行查询时出现"+e.toString()); 
		 }finally{
	     out.flush();
		 out.close();
		 }
	}

}
