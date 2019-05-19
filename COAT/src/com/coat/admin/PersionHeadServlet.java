package com.coat.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Util;

public class PersionHeadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String user=null;
	Logger log=Logger.getLogger(PersionHeadServlet.class);
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		try{
			user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(Util.objIsNULL(user)){
				throw new RuntimeException("User information timeout !");
			}
			if(method.equalsIgnoreCase("select")){
			//	select(request, response);
			}else{
				throw new RuntimeException("Unauthorized access!");
			}

		}catch (NullPointerException e) {
			log.error("AccessCard==>"+method+"操作异常：空值=="+e);
			Util.outExcetion(response, "Submit data anomalies, please refresh retry!");
		}catch (Exception e) {
			log.error("AccessCard==>"+method+"操作异常："+e);
			Util.outExcetion(response, Util.joinException(e));
		} finally{
			method=null; 
		} 
	}

}
