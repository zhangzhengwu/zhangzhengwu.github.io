package servlet.staffservlet.hr;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import servlet.staffservlet.AddStaffRequestConvoyServlet;
import util.Constant;
import util.DateUtils;
import util.Util;
import dao.AddStaffRequestDao;
import dao.impl.AddStaffRequestDaoImpl;
import entity.RequestStaffConvoyBean;

public class AddHRRequestConvoyServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AddStaffRequestConvoyServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String adminUsername="";
		int num = 0; 
		RequestStaffConvoyBean rnb=new RequestStaffConvoyBean();
		  try{
			  adminUsername = request.getSession().getAttribute("convoy_username").toString();
		if(Util.objIsNULL(adminUsername)){
				out.print("<script>alert('Identity information is missing, please login again!');top.location.href='login.jsp';</script>");
		}
		String StaffNo=request.getParameter("StaffNos");
		StringBuffer stringBuffer=new StringBuffer("");

		rnb.setStaff_code(StaffNo);
		rnb.setName(request.getParameter("EnglishNames"));
		rnb.setName_chinese(request.getParameter("ChineseNames"));
		rnb.setTitle_english(request.getParameter("EnglishPositions")+"; "+request.getParameter("EnglishStaffDepartmentTexts"));
		rnb.setTitle_chinese(request.getParameter("ChinesePositions")+"; "+request.getParameter("ChineseStaffDepartmentTexts"));
		rnb.setAcademic_title_e(request.getParameter("EnglishAcademicTitles"));
		rnb.setAcademic_title_c(request.getParameter("ChineseAcademicTitles"));
		rnb.setProfess_title_e(request.getParameter("EnglishProfessionalTitles"));
		rnb.setProfess_title_c(request.getParameter("ChineseProfessionalTitles"));
		rnb.setExternal_english(request.getParameter("englishExternal"));
		rnb.setExternal_chinese(request.getParameter("chineseExternal"));
		rnb.setUrgentDate(DateUtils.getNowDateTime());
		rnb.setTr_reg_no(request.getParameter("TR_RegNos"));
		rnb.setCe_no(request.getParameter("CENOs"));
		rnb.setMpf_no(request.getParameter("MPFA_NOs"));
		rnb.setE_mail(request.getParameter("Emails"));
		rnb.setDirect_line(request.getParameter("DirectLines"));
		rnb.setFax(request.getParameter("FAXs"));
		rnb.setBobile_number(request.getParameter("Mobiles"));
		rnb.setQuantity(request.getParameter("nums"));
		rnb.setLocation(request.getParameter("locatins"));
		rnb.setLayout_type(request.getParameter("types").trim());
		rnb.setCard_type(Constant.NAMECARD_TYPE);
		rnb.setUpd_name(adminUsername);
		rnb.setUpd_date(DateUtils.getNowDateTime()); 
		rnb.setCFS_only(Util.objIsNULL(request.getParameter("CFS"))?"N":request.getParameter("CFS"));
		rnb.setCAM_only(Util.objIsNULL(request.getParameter("CAM"))?"N":request.getParameter("CAM"));
		rnb.setCIS_only(Util.objIsNULL(request.getParameter("CIS"))?"N":request.getParameter("CIS"));
		rnb.setCCL_only(Util.objIsNULL(request.getParameter("CCL"))?"N":request.getParameter("CCL"));
		rnb.setCFSH_only(Util.objIsNULL(request.getParameter("CFSH"))?"N":request.getParameter("CFSH"));
		rnb.setCMS_only(Util.objIsNULL(request.getParameter("CMS"))?"N":request.getParameter("CMS"));
		rnb.setCFG_only(Util.objIsNULL(request.getParameter("CFG"))?"N":request.getParameter("CFG"));
		rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
		rnb.setCCIA_only(Util.objIsNULL(request.getParameter("CCIA"))?"N":request.getParameter("CCIA"));
		rnb.setCCFSH_only(Util.objIsNULL(request.getParameter("CCFSH"))?"N":request.getParameter("CCFSH"));
		rnb.setCWMC_only(Util.objIsNULL(request.getParameter("CWMC"))?"N":request.getParameter("CWMC"));
		 
		
		rnb.setPayer(adminUsername);
		rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
		rnb.setRemarkcons(request.getParameter("remarks"));
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout");
			}
			 
			if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			 stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
			}
			rnb.setMarks(stringBuffer.toString());
		/*******************等待处理*****************************/
					AddStaffRequestDao addStaffRequestDao=new AddStaffRequestDaoImpl();
					/**
					 * 保存新表数据
					 */
					num=addStaffRequestDao.saveHRRequestConvoy(rnb);
					if(num>0)
						out.print("<script type='text/javascript'>alert('Application has been accepted!');location.href='namecard/staff/queryStaffCard.jsp';</script>");
					else
						out.print("Application submission failed,Cause:Data anomalies!");	
					
			}catch (Exception e){
				e.printStackTrace();
				log.error("保存staff NameCard Convoy时出现："+e.toString());
				out.print("<script type='text/javascript'>alert('Application submission failed,Cause:"+e.toString()+"');</script>");
			}
			finally{
				out.flush();
				out.close();
			}
 
	}

}
