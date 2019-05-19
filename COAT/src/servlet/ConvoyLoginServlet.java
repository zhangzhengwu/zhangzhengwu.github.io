package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;
import dao.AdminDAO;

public class ConvoyLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(CheckLoginServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(); 
		/* 首先取得jsp页面传来的参数信息 */
		String username = request.getParameter("username");
		String password = request.getParameter("password"); 
		String errMessage="";
		 try{
		/* 验证输入信息的完整行和正确性 */
		if (username.equals(""))
			errMessage = "用户名不能为空!";
		if (password.equals(""))
			errMessage = "密码输入不能为空!";
		/*if (util.Tools.notEmpty(sessionCode) && !sessionCode.equalsIgnoreCase(code))
			errMessage += "验证码输入不正确!";*/
		if (password.indexOf("'") != -1){
			errMessage = "请不要进行sql注入攻击!";
		log.info(username+"-"+getIpAddr(request)+"对系统进行过SQL注入操作！");
		}
		
		String admin = null;
		admin=new AdminDAO().checkStaffcode(username,password);
		/*如果是管理员身份用户名和密码都验证成功则设置session的值然后重定向到管理首页*/
		if (!Util.objIsNULL(admin) ) {
			//session.setAttribute("adminUsername", admin.getAdminUsername());
		//	session.setAttribute("admin", admin);
			 session.setAttribute("convoy_username", admin);
			session.setAttribute("systemTime", DateUtils.getDateToday());
			log.info("用户"+getIpAddr(request)+"/"+username+"登陆成功");
			//response.sendRedirect("main.jsp");
			errMessage="success";
		} else {
			errMessage ="error";
			//request.setAttribute("errMessage",errMessage);
		//	RequestDispatcher wm = request.getRequestDispatcher("login.jsp");
			//wm.forward(request, response);
		}
		out.print(errMessage);
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		out.flush();
		out.close();
		 }
	}
	public String getIpAddr(HttpServletRequest request) {
	       String ip  = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	          ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	          ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	          ip = request.getRemoteAddr();
	       } 
	      
	       
	       return ip;
	}
}
