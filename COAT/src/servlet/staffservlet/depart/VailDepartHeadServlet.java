package servlet.staffservlet.depart;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import dao.DepartMentDao;
import dao.impl.DepartMentDaoImpl;

public class VailDepartHeadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger=Logger.getLogger(VailDepartHeadServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		DepartMentDao dmd=new DepartMentDaoImpl();  
		String Username=null;
		try{
			 Username= request.getSession().getAttribute("convoy_username").toString();
			 boolean flag=dmd.vailHead(Username);
			 logger.info("验证"+Username+"是否为Department Head,结果"+flag);
			 out.print(flag+"");
		}catch (Exception e) {
			logger.error("验证"+Username+"是否为Department Head时出现异常--"+e.toString());
		}
		out.flush();
		out.close();
	}

}
