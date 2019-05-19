package servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.dao.impl.LoginRecordDaoImpl;
import com.coat.loginrecord.entity.LoginRecord;

import util.Constant;
import util.DateUtils;
import util.Util;

import dao.AdminDAO;

import entity.Admin;

public class CheckLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(CheckLoginServlet.class);
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession(); 
		/* 首先取得jsp页面传来的参数信息 */
		String username = request.getParameter("username");
		String password = request.getParameter("password"); 
		/*	String code = request.getParameter("code");
		String sessionCode = (String) session.getAttribute("code");*/

		String errMessage = "";
		try{


			/* 验证输入信息的完整行和正确性 */
			if (username.equals(""))
				errMessage += "用户名不能为空!";
			if (password.equals(""))
				errMessage += "密码输入不能为空!";
			/*if (util.Tools.notEmpty(sessionCode) && !sessionCode.equalsIgnoreCase(code))
				errMessage += "验证码输入不正确!";*/
			if (password.indexOf("'") != -1){
				errMessage += "请不要进行sql注入攻击!";
				log.error(username+"对系统进行过SQL注入操作！");
			}
			/* 如果验证没有通过转到登陆页并提示错误信息 */
			if (!errMessage.equals("")) {
				request.setAttribute("errMessage", errMessage);

				RequestDispatcher wm = request.getRequestDispatcher("signin.jsp");
				wm.forward(request, response);
				return;
			}
			/* 如果初步验证通过就需要进一步验证 */ 
			Admin admin = new Admin();
			admin=new AdminDAO().checkLogin_AES_ENCRYPT(username,password);
			/*如果是管理员身份用户名和密码都验证成功则设置session的值然后重定向到管理首页*/

			if (!Util.objIsNULL(admin.getAdminUsername()) && !Util.objIsNULL(admin.getAdminPassword())) {
				session.setAttribute("adminUsername", admin.getAdminUsername());
				session.setAttribute("convoy_username", admin.getAdminUsername());
				session.setAttribute("admin", admin);
				session.setAttribute("systemTime", DateUtils.getDateToday());
				session.setAttribute("flag", "true");
				session.setAttribute("roleType",Constant.RoleType_staff);
				log.info("用户"+getIpAddr(request)+"/"+username+"登陆成功");
				
				/*将用户登录信息保存至loginrecord表中*/
				LoginRecord loginRecord=new LoginRecord(admin.getAdminUsername(),
						"Admin",
						"login",
						Util.getIpAddr(request),
						DateUtils.getNowDateTime(),
						"Y");
				LoginRecordDao loginRecordDao=new LoginRecordDaoImpl();
				int rrr = loginRecordDao.saveLogin(loginRecord);
				if(rrr > 0){
					log.info("用户"+Util.getIpAddr(request)+"/"+username+"保存记录成功");
				} else {
					log.info("用户"+Util.getIpAddr(request)+"/"+username+"保存记录失败");
				}
				response.sendRedirect("main.jsp");
			} else {
				errMessage += "管理员帐号或密码错误!";
				request.setAttribute("errMessage",errMessage);
				RequestDispatcher wm = request.getRequestDispatcher("login.jsp");
				wm.forward(request, response);
			}

		}catch (Exception e) {
			errMessage = e.getMessage();
			request.setAttribute("errMessage",errMessage);
			RequestDispatcher wm = request.getRequestDispatcher("login.jsp");
			wm.forward(request, response);
		}finally{

		}
	}

	/*	public String getIpAddr(HttpServletRequest request) {
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
	 */

	/**
	 * 优化获取IP的方法    
	 */
	public static String getIpAddr(HttpServletRequest request) {   
		String ip = request.getHeader("X-Forwarded-For");   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("Proxy-Client-IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("WL-Proxy-Client-IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("HTTP_CLIENT_IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getRemoteAddr();   
		}   
		return ip;   
	} 


}
