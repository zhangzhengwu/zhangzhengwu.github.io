package servlet.request;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.QueryStaffRequstDao;
import dao.impl.QueryStaffRequstDaoImpl;

public class VailEmailServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VailEmailServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		String method = request.getParameter("method");
		try{
			if(method.equals("Email"))
				findEmailBystaff(request,response);
			if(method.equals("RecruiterEmail"))
				findRecruiterEmailBystaff(request, response);
			else{
				response.getWriter().print("fail");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("StaffCode==>Email==>Exception", e);
		}finally{
			method=null;
		}
	}
	
	
	
	
	
	/**
	 * 查询Staff Recruiter
	 * @param request
	 * @param response
	 */
	void findRecruiterEmailBystaff(HttpServletRequest request, HttpServletResponse response){
		QueryStaffRequstDao qsr=new QueryStaffRequstDaoImpl();
		String staffcode=null;
		PrintWriter out=null;
		try{
			out=response.getWriter();
			staffcode=request.getParameter("staffcode");
			out.print(qsr.findRecruiterEmailByStaff(staffcode));
		}catch(Exception e){
			e.printStackTrace();
			log.error(staffcode+"==>Recruiter Email==>Exception:"+e);
		}finally{
			staffcode=null;
			qsr=null;
			out.flush();
			out.close();
		}
		
	}
	
	/**
	 * 根据staffcode、userType===>Email
	 * @param request
	 * @param response
	 */
	void findEmailBystaff(HttpServletRequest request, HttpServletResponse response){
		QueryStaffRequstDao qsr=new QueryStaffRequstDaoImpl();
		String staffcode=null;
		String userType=null;
		PrintWriter out=null;
		try{
			out=response.getWriter();
			staffcode=request.getParameter("staffcode");
			userType=request.getParameter("userType");
			out.print(qsr.findEmailByStaff(staffcode, userType));
		}catch(Exception e){
			e.printStackTrace();
			log.error(staffcode+"==>userType==>"+userType+"==>Email==>Exception:"+e);
		}finally{
			staffcode=userType=null;
			qsr=null;
			out.flush();
			out.close();
		}
		
	}
	
}
