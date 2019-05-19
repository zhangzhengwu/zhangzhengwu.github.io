package servlet.Medical.Staff;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import dao.StaffMedicalDao;
import dao.impl.StaffMedicalDaoImpl;
import entity.Medical_record_staff_bak;

public class SaveRejectBakServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SaveStaffMedicalServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}

	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String UserName=request.getSession().getAttribute("adminUsername").toString();
		String staffcode=request.getParameter("staffcode");
		String type=request.getParameter("type").substring(request.getParameter("type").lastIndexOf('-')+1);
		String MedicalDate=request.getParameter("MedicalDate");
		String MedicalFee=request.getParameter("MedicalFee");
		String staffname=request.getParameter("staffname");
		String company=request.getParameter("Company");
		String dept=request.getParameter("Department");
		String grade=request.getParameter("grade");
		String entitle=request.getParameter("entitle");//实际报销金额
		String return_oraginal=request.getParameter("return_oraginal");
		String pplan=request.getParameter("pplan");
		String packages=request.getParameter("package");
		//String maxamount=request.getParameter("max_amount");//最高报销次数
		String amount=request.getParameter("max_amount_money");//最高报销金额
		String medicalNormal=request.getParameter("Normal_Number");
		String medicalSpecial=request.getParameter("Special_Number");
		String Oral_Number=request.getParameter("Oral_Number");
		String Dental_Number=request.getParameter("Dental_Number");
		String email=request.getParameter("email");
		String SameDay=request.getParameter("SameDay");
		String Subject=request.getParameter("subject");
		String Remark=request.getParameter("remark");
		StaffMedicalDao smd=new StaffMedicalDaoImpl();
	
		
	 //保存
		/***********************判断选择的Medical Type   根据类型增加数量**************/
		if(type.equals("SP")){//专科
			medicalSpecial=(Integer.parseInt(medicalSpecial)+1)+"";
		}else if(type.equals("GP")){//普科
			medicalNormal=(Integer.parseInt(medicalNormal)+1)+"";
		}else if(type.equals("Regular")){//口腔科
			Oral_Number=(Integer.parseInt(Oral_Number)+1)+"";
		}else if(type.equals("Dental")){//牙科
			Dental_Number=(Double.parseDouble(Dental_Number)+Double.parseDouble(entitle))+"";
		}
		/**********************************/  
		String term=(Integer.parseInt(medicalNormal)+Integer.parseInt(medicalSpecial)+Integer.parseInt(Oral_Number))+"";//报销总数
		Medical_record_staff_bak mrs=new Medical_record_staff_bak(staffcode,staffname, company, dept, grade, pplan, packages,email, amount,type, term, 
				medicalNormal, medicalSpecial,Oral_Number,Dental_Number,MedicalDate, MedicalFee,DateUtils.strChToUs(DateUtils.getNowDateTime()), entitle, return_oraginal,SameDay, UserName, DateUtils.getNowDateTime(), "Y",Subject,Remark); 
			int num =smd.saveRejectBak(mrs);
			if(num>0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("在SaveRejectBakServlet中保存staffMedical_bak时出现："+e.toString());
			out.print("null");
		}finally{
			out.flush();
			out.close();
		}
	}

}
