package com.coat.ivr.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Util;

import com.coat.ivr.dao.IVROptOutListDao;
import com.coat.ivr.dao.impl.IVROptOutListDaoImpl;

public class IvrServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3201790821757182949L;
	Logger log = Logger.getLogger(IvrServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request,response);
	}
	String user = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			user=(String) request.getSession().getAttribute("adminUsername");
			if (method.equals("synphone")){
				result = synphone(request, response);
			}else {
				throw new Exception("Unauthorized access!");
			}
		} catch (NullPointerException e) {
			result=Util.joinException(e);
			log.error("IvrServlet==>" + method + "操作异常：空值==" + e);
		} catch (Exception e) {
			log.error("IvrServlet==>" + method + "操作异常：" + e);
			result=Util.joinException(e);
		} finally {
			if(!Util.objIsNULL(result)){
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		}
	}

	
	public String synphone(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		IVROptOutListDao ivrDao = null;
		String result="";
		try {
			ivrDao = new IVROptOutListDaoImpl();
			result=ivrDao.timeTaskGetDailyOptoutList();
		} catch (Exception e) {
			result=Util.joinException(e);
			e.printStackTrace();
		} 
		return result;
	}

}