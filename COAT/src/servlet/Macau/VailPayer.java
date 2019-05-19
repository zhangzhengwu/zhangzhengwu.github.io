package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import dao.PayerDao;
import dao.impl.PayerDaoImpl;

public class VailPayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VailPayer.class);
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		 String payer=request.getParameter("payer");
		 String user=request.getSession().getAttribute("adminUsername").toString();
		 PayerDao payerDao=new PayerDaoImpl();
		 String EmployeeName="";
		 try{
		 EmployeeName=payerDao.VailPayer(payer, user);
		 out.print(EmployeeName);
		 }catch(Exception e){
			 log.error("对cons_list查询名称时出现"+e.toString());
		 }finally{
		 out.flush();
		 out.close();
		 }
	}

}
