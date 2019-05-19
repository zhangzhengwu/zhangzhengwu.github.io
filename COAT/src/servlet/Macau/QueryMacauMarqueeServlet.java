package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import servlet.QueryConsMarqueeServlet;
import dao.MacauDao;
import dao.impl.MacauDaoImpl;

public class QueryMacauMarqueeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(QueryMacauMarqueeServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		 String code=request.getParameter("code");
		MacauDao cDao=new MacauDaoImpl();
		 String message="";
		 try{
			 message=cDao.queryConsNum(code);
			 out.print(message);
		 }catch(Exception e){
			 log.error("对cons_macau查询名称时出现"+e.toString());
		 }finally{
			 out.flush();
			 out.close();
		 }
	}
}
