package servlet.Medical.Staff;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import net.sf.json.JSONArray;

import dao.StaffMedicalDao;
import dao.impl.StaffMedicalDaoImpl;

public class VailSameDayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VailSameDayServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String medicalDate=request.getParameter("medicalDate");
		StaffMedicalDao smd=new StaffMedicalDaoImpl();
	try{
		JSONArray jsons=JSONArray.fromObject(smd.getType(staffcode, medicalDate));
		out.print(jsons.toString());
	}catch(Exception e){
		e.printStackTrace();
		log.error("查询MedicalDate当天报销的所有type："+e.toString());
	}finally{
		out.flush();
		out.close();
		}
	}

}
