package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.AddRequestDao;
import dao.impl.AddRequestDaoImpl;

public class VialDDServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VialDDServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
			response.setContentType("text/html;charset=utf-8;");
			PrintWriter out = response.getWriter();
			AddRequestDao ard=new AddRequestDaoImpl();
			String staffcode=request.getParameter("staffcode");
		try{
			if(ard.findDDorTreeHead(staffcode)){
				out.print("SUCCESS");
			}else{
				out.print("ERROR");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("zai VialDDServlet 中验证staff是否是DD时出现异常："+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}

}
