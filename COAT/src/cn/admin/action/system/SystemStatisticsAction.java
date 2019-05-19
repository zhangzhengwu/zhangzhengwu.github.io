package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;
import cn.admin.dao.impl.system.SUsermenuDaoImpl;
import cn.admin.dao.system.SUsermenuDao;
import cn.admin.entity.system.SMenuBasic;

public class SystemStatisticsAction extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		String user = null;
		String result="";
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("adminUsername") == null){
				return;
			} else if (!method.equals("login")) {
				user = ""+session.getAttribute("adminUsername");
			}
			if (method.equals("usage")) {
				usage(request, response);
			
			}else {
				throw new Exception("Unauthorized access!");
			}
		} catch (NullPointerException e) {
			result=Util.joinException(e);
		} catch (Exception e) {
			result=Util.joinException(e);
		} finally {
			
		}
	}

	
	
	String usage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		return null;
	}
	
	
}
