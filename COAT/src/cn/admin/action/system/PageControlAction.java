package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;


public class PageControlAction extends HttpServlet {




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String loginUser= (String) request.getSession().getAttribute("adminUsername");
			if(Util.objIsNULL(loginUser)){//登陆信息为空时
				request.setAttribute("error", "User Info timeout.");
				out.print("<script>top.location.href='index.jsp'</script>");
			}else{
				String method=request.getParameter("m");
				/*String menuid = request.getParameter("menuid");
				SUser_Login user = (SUser_Login) request.getSession().getAttribute("loginUser");
				Integer userid = user.getUserid();
				SUsermenuDao uroleDao = new SUsermenuDaoImpl();
				SMenuBasic rs = uroleDao.findUserMenuBymenuId(userid, Integer
						.parseInt(menuid), user.getGroupid());
				request.getSession().setAttribute("roleObj", rs);
				request.setAttribute("menuid",menuid);*/
				request.getRequestDispatcher(method).forward(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}finally{
			out.flush();
			out.close();
		}
	
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Unauthorized Access!");
		out.flush();
		out.close();
	}


}
