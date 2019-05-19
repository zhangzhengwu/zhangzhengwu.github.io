package servlet.Consultant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;
import entity.Medical_Consultant;

public class SelectMedicalConsultantServlet extends HttpServlet {
	Logger log=Logger.getLogger(SelectMedicalConsultantServlet.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String start_date=request.getParameter("startDate");
		String end_date=request.getParameter("endDate");
		String Staff_Code=request.getParameter("code");
		String FullName=request.getParameter("name");
		 MedicalDao md= new MedicalDaoImpl();
		 List<Medical_Consultant> list = new ArrayList<Medical_Consultant>();
		 try{
			 list=md.queryMedical_Consultant(start_date, end_date, Staff_Code, FullName);
			 JSONArray jsons=JSONArray.fromObject(list);
			 out.print(jsons.toString());
		 }catch(Exception e){
			 log.error("TestUser"+"在SelectMedicalConsultantServelet中查询MedicalConsultant时出现："+e.toString());
		 }finally{
			 out.flush();
			 out.close();
		 }
		 
	}

}
