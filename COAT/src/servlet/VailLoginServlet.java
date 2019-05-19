package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import entity.Admin;

public class VailLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try{
			String LoginName=request.getParameter("LoginName");
			String Password=request.getParameter("password");
			Admin admin =new AdminDAO().checkLogin_AES_ENCRYPT(LoginName,Password);
			 
			HttpSession session=request.getSession();
			if(admin.getAdminUsername().equals(LoginName)){
				session.setAttribute("flag", "true");
				out.print("success");
			}else{
				session.setAttribute("flag", "false");
				out.print("error");
			}
	
		}catch(Exception e){
			
		}finally{
			out.flush();
			out.close();
		}
	}

}
