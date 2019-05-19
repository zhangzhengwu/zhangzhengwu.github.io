package servlet.Consultant;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;

public class UpdateMedicalState extends HttpServlet {
	Logger log=Logger.getLogger(UpdateMedicalState.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String add_date=request.getParameter("add_date");
		String staffcode=request.getParameter("staffcode"); 
		String remark=request.getParameter("remark"); 
		MedicalDao md=new MedicalDaoImpl();
		 
		try{
			md.updateMedicalState_J(staffcode, add_date,remark);
			out.print("success");
		}catch(Exception e){
			out.print("error");
		}
		out.flush();
		out.close();
	}

}
