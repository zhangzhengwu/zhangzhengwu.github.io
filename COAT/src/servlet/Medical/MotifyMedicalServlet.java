package servlet.Medical;

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
import entity.Medical;

public class MotifyMedicalServlet extends HttpServlet {
Logger log=Logger.getLogger(MotifyMedicalServlet.class);
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
		Medical mc=new Medical();
		String upd_name=request.getSession().getAttribute("adminUsername").toString();
		String special=	Util.objIsNULL(request.getParameter("special"))?"":request.getParameter("special");
		String updDate=request.getParameter("upd_Date");//修改之前的UpdateDate
		special=special.equals("on")?"S":special;
		String S=Util.objIsNULL(request.getParameter("S"))?"":request.getParameter("S");
		mc.setStaffcode(request.getParameter("StaffNo")); 
		mc.setName(request.getParameter("EnglishName"));
		mc.setAD_type(request.getParameter("AD"));
		mc.setSP_type(special);
		mc.setMedical_date(request.getParameter("MedicalDate"));
		mc.setMedical_Fee(request.getParameter("MedicalFee"));
		mc.setEntitled_Fee(request.getParameter("Amount"));
		mc.setTerms_year(request.getParameter("used"));
		mc.setMedical_month(request.getParameter("MonenyMonth"));
		
		mc.setStaff_CodeDate(request.getParameter("StaffNoDate"));
		mc.setSameDaye(request.getParameter("SameDaye"));
		mc.setHalf_Consultant(request.getParameter("HalfConsultant"));
		mc.setUpd_Name(upd_name);
		 mc.setUpd_Date(DateUtils.getNowDateTime()); 
		//mc.setUpd_Date(updDate);
		mc.setSfyx("Y");
		if(!S.equals(special)){
 
			if(S.equals("S")){/**原本為专科**/
				mc.setMedical_Normal(Integer.parseInt(request.getParameter("medical_Normal"))+1+"");
				mc.setMedical_Special(Integer.parseInt(request.getParameter("medical_Special"))-1+"");
				md.upNormal(mc.getStaffcode(),mc.getUpd_Date(),mc.getTerms_year(), upd_name);
			}else{			  /**原本为普科**/
				mc.setMedical_Normal(Integer.parseInt(request.getParameter("medical_Normal"))-1+"");
				mc.setMedical_Special(Integer.parseInt(request.getParameter("medical_Special"))+1+"");
				md.upSpecial(mc.getStaffcode(), mc.getUpd_Date(),mc.getTerms_year(), upd_name);
			}
		}else{
			mc.setMedical_Normal(request.getParameter("medical_Normal"));
			mc.setMedical_Special(request.getParameter("medical_Special"));
		}
		/******************************************************************************************/
	
	 mc.setUpd_Date(updDate);//把更新之后的date依然设置为原来的updateDate
		try{
			md.update(mc.getStaffcode(), updDate, upd_name);
			int num=-1;
			num=md.addMedical(mc);
			if(num>0){
				out.print("1");
			}else{
				out.print("-1");
			}
		}catch(Exception e){
			log.error(upd_name+"在MotifyMedicalServelt中執行motifyMedical方法時出現"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}
}