package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.AddStaffRequestDao;
import dao.impl.AddStaffRequestDaoImpl;
@Deprecated
public class RejectRequestStaffConvoyServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ApproveStaffRequestServlet.class);
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
		String staffcode=request.getParameter("staffcode");
		String urgentDate=request.getParameter("urgentDate");
		try{
			adminUsername=request.getSession().getAttribute("convoy_username").toString();
			AddStaffRequestDao asrd=new AddStaffRequestDaoImpl();
			int num=asrd.rejectStaffRequestConvoy(staffcode, urgentDate,adminUsername);
			if(num>0){
				result="Reject Success!";
			}else{
				result="Reject Error!";
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(adminUsername+"  -Reject Request Staff Convoy 时出现 ："+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

}
