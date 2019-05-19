package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AddRequestDao;
import dao.MacauDao;
import dao.impl.AddRequestDaoImpl;
import dao.impl.MacauDaoImpl;

public class VailMacauDDServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
			response.setContentType("text/html;charset=utf-8;");
			PrintWriter out = response.getWriter();
			MacauDao ard=new MacauDaoImpl();
			String staffcode=request.getParameter("staffcode");
		try{
			if(ard.findDDorTreeHead(staffcode)){
				out.print("SUCCESS");
			}else{
				out.print("ERROR");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}

}
