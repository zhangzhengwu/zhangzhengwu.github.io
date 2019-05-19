package servlet.EmapServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import dao.EConsultantReportDao;
import dao.impl.EConsultantReportDaoImpl;

public class DelConsultantReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DelConsultantReport.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
		String staffcode=request.getParameter("staffcode");
		String mapdate=request.getParameter("mapdate");
		EConsultantReportDao ec=new EConsultantReportDaoImpl();
		int num=-1;
		num=ec.deleteConsultantReport(staffcode, mapdate);
		if(num>0){
			out.print("success");
		}else{
			out.print("error");
		}
		}catch(Exception e){
			e.printStackTrace();
			log.error("删除 Consultant Emap Report时出现："+e);
		}finally{
			out.flush();
			out.close();
		}
	}

}
