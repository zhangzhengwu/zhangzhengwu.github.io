package servlet.Medical;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;

public class VailMedicalServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VailMedicalServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String StaffNo=request.getParameter("StaffNo");
		String MedicalDate=request.getParameter("MedicalDate");
		String Special=request.getParameter("Special");
		int num=-1;
		try{
		MedicalDao md=new MedicalDaoImpl();
		num=md.VailMedical(StaffNo, MedicalDate, Special);
		out.print(num);
		}catch(Exception e){
			log.error(request.getSession().getAttribute("adminUsername").toString()+"在VailMedicalServlet中验证MedicalDate时出现："+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}

}
