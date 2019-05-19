package servlet.Consultant;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;
import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;
import entity.Medical_Consultant;

public class AddConsultantMedicalServlet extends HttpServlet {
	Logger log=Logger.getLogger(AddConsultantMedicalServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MedicalDao md=new MedicalDaoImpl();
		Medical_Consultant mc=new Medical_Consultant();
		String special=	Util.objIsNULL(request.getParameter("special"))?"":request.getParameter("special"); 
		mc.setStaffcode(request.getParameter("StaffNo")); 
		mc.setName(request.getParameter("EnglishName"));
		mc.setAD_type(request.getParameter("AD"));
		mc.setSP_type(special);
		mc.setMedical_date(request.getParameter("MedicalDate"));
		mc.setMedical_Fee(request.getParameter("MedicalFee"));
		mc.setEntitled_Fee(request.getParameter("Amount"));
		mc.setTerms_year("");
		mc.setMedical_month(request.getParameter("MonenyMonth"));
		mc.setShzt("N");
		mc.setAdd_date(DateUtils.getNowDateTime());
		mc.setAdd_name(request.getParameter("addName"));
		mc.setRemark("");
		mc.setFile(request.getParameter("photo"));
		mc.setMedical_Normal("");
/*		if(special.equals("S")){
			mc.setMedical_Normal(request.getParameter("MedicalNormal"));
			mc.setMedical_Special(Integer.parseInt(request.getParameter("MedicalSpecial"))+1+"");
		}else{
			mc.setMedical_Normal(Integer.parseInt(request.getParameter("MedicalNormal"))+1+"");
			mc.setMedical_Special(request.getParameter("MedicalSpecial"));
		}*/
		mc.setMedical_Special("");
		mc.setStaff_CodeDate(request.getParameter("StaffNoDate"));
		mc.setSameDaye(request.getParameter("sameDay"));
		mc.setHalf_Consultant(request.getParameter("HalfConsultant"));
		mc.setUpd_Name("shenheren");
		mc.setUpd_Date(DateUtils.getNowDateTime());
		mc.setSfyx("Y");
		int num=-1;
		try{
		num=md.addMedical_Consultant(mc);
		out.print(num);
		}catch(Exception e){
			log.error(request.getSession().getAttribute("adminUsername").toString()+"在AddConsultantMedicalServlet中添加Medical_Consultant时出现："+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}
}
