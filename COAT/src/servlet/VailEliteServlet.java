package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.RequestRecordDao;
import dao.impl.RequestRecordDaoImpl;

public class VailEliteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VailEliteServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String table=request.getParameter("table");
		RequestRecordDao rrd=new RequestRecordDaoImpl();
	try{
		if(table.equals("request_new")){
			out.print(rrd.vailElite_request_new(staffcode));
		}else if(table.equals("request_staff")){
			out.print(rrd.vailElite_request_staff(staffcode));
		}else if(table.equals("request_macau")){
			out.print(rrd.vailElite_request_macau(staffcode));
		}
		 
	}catch(Exception e){
		e.printStackTrace();
		log.error("在VailEliteServlet 中查询Elite Team 成员办理名片的记录时出现："+e.toString());
	}finally{
		out.flush();
		out.close();
	}
	}

}
