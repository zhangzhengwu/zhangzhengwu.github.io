package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MotifyDao;
import dao.impl.MotifyDaoImpl;

public class RepeatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MotifyDao md = new MotifyDaoImpl();
		String staffcode=request.getParameter("StaffNo");
		String remark=request.getParameter("Remark");
		try{
		int num=md.selectRepeat(staffcode, remark);
		out.print(num);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	
}
