package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.ConsconvoyNamecardDao;
import dao.impl.ConsconvoyNamecardDaoImpl;
/**
 * 财务查询页面
 * @author kingxu
 *
 */
public class QueryConsMarqueeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(QueryConsMarqueeServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		 String code=request.getParameter("code");
		 boolean DD=Boolean.parseBoolean(request.getParameter("DD"));
		 ConsconvoyNamecardDao cDao=new ConsconvoyNamecardDaoImpl();
		 String message="";
		 try{
			 message=cDao.queryConsNum(code,DD);
			 out.print(message);
		 }catch(Exception e){
			 log.error("对cons_list查询名称时出现异常"+e.toString());
		 }finally{
			 out.flush();
			 out.close();
		 }
	}

}
