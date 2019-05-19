package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import cn.admin.entity.system.SUser_Login;


public class getMenuCompetence extends HttpServlet {

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
		Map<String,Object> user = null;
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String menuname=request.getParameter("menuname");
		String menuid=request.getParameter("menuid");
		try {
			HttpSession session = request.getSession();
			if (Util.objIsNULL(session.getAttribute("loginUser"))) {
				response.getWriter().println("<script type=\"text/javascript\">top.location.href='index.jsp'</script>");
				return;
			} else {
				user = (Map<String, Object>) session.getAttribute("loginUser");
			}
			if (Util.objIsNULL(menuid)) {
				out.print("请在系统内部访问！！！");
				return;
			}
			Integer userid = Integer.parseInt(user.get("userid")+"");
			SUsermenuDao uroleDao = new SUsermenuDaoImpl();
			//SMenuBasic rs = uroleDao.findUserMenuBymenuId(userid, Integer.parseInt(menuid), 1);
			SMenuBasic rs =uroleDao.findMenubyusermenu(userid, Integer.parseInt(menuid));
			String menuAction=rs.getMenuAction();
			
			if (Util.objIsNULL(menuAction)) {
				out.print("未授权的访问！！！");
			} else {
				if(!menuname.equalsIgnoreCase(menuAction)){
					out.print("请勿非法篡改系统数据！！！");
				} else {
					
					request.setAttribute("eMsg", "");
					request.setAttribute("menuid", menuid);
					request.setAttribute("roleObj", rs);
					request.getRequestDispatcher(menuAction).forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}




}
