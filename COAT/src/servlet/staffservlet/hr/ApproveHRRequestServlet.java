package servlet.staffservlet.hr;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.QueryStaffRequstDao;
import dao.impl.QueryStaffRequstDaoImpl;

public class ApproveHRRequestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ApproveHRRequestServlet.class);
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		try{
			adminUsername=request.getSession().getAttribute("convoy_username").toString();
			String StaffNo=request.getParameter("StaffNos");
			String urgentDate=request.getParameter("UrgentDate");
			String remarkcons=request.getParameter("remarkcons");
			QueryStaffRequstDao smd=new QueryStaffRequstDaoImpl();
			int num=-1;
			num=smd.approveRequestHRConvoy(StaffNo,urgentDate, adminUsername,remarkcons);
			 if(num>0){////判断是否把原始数据保存成功
				 result="Approve Success";
			 }else{
				 result="Approve Error!";
			 }
			log.info(adminUsername+"在SaveStaffRequestConvoyServlet中HR 审核Request,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在SaveStaffRequestConvoyServlet中HR 审核Request时出现："+e);
			result="Approve Exception ："+e.toString();
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

}
