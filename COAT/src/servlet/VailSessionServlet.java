package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

import antlr.Utils;

public class VailSessionServlet extends HttpServlet {

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
			// 设置该响应不在缓存中读取
			response.addHeader("Expires", "0");
			response.addHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.addHeader("Pragma", "no-cache");
			HttpSession session=request.getSession();
			if(Util.objIsNULL(session.getAttribute("flag")))
				out.print("null");
			else if(session.getAttribute("flag").equals("true"))
				out.print("success");
			else
				out.print("error");
	
		}catch(Exception e){
			e.printStackTrace();
			out.print("error");
		}finally{
			out.flush();
			out.close();
		}
	}

}
