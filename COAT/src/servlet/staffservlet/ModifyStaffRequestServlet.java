package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;
import util.DateUtils;
import util.Util;
import dao.AddStaffRequestDao;
import dao.QueryStaffRequstDao;
import dao.impl.AddStaffRequestDaoImpl;
import dao.impl.QueryStaffRequstDaoImpl;
import entity.RequestStaffBean;

public class ModifyStaffRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ModifyStaffRequestServlet.class);
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
			adminUsername=request.getSession().getAttribute("adminUsername").toString();
		String StaffNo=request.getParameter("StaffNos");
		String company_Type=request.getParameter("company_Type");
		StringBuffer stringBuffer=new StringBuffer("");
		RequestStaffBean rnb=new RequestStaffBean();
		rnb.setStaff_code(StaffNo);
		rnb.setPayer(StaffNo);
		rnb.setName(request.getParameter("EnglishNames"));
		rnb.setName_chinese(request.getParameter("ChineseNames"));
		rnb.setTitle_english(request.getParameter("EnglishPositions")+"; "+request.getParameter("EnglishStaffDepartmentTexts"));
		rnb.setTitle_chinese(request.getParameter("ChinesePositions")+"; "+request.getParameter("ChineseStaffDepartmentTexts"));
	
		rnb.setExternal_english(request.getParameter("englishExternal"));
		rnb.setExternal_chinese(request.getParameter("chineseExternal"));
		rnb.setAcademic_title_e(request.getParameter("EnglishAcademicTitles"));
		rnb.setAcademic_title_c(request.getParameter("ChineseAcademicTitles"));
		rnb.setProfess_title_e(request.getParameter("EnglishProfessionalTitles"));
		rnb.setProfess_title_c(request.getParameter("ChineseProfessionalTitles"));
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
		rnb.setUpd_name(request.getSession().getAttribute("adminUsername").toString());
		rnb.setUpd_date(request.getParameter("upd_date")); 
		rnb.setUrgentDate(request.getParameter("UrgentDate"));
		
		rnb.setCompany(company_Type);
		/*rnb.setCFS_only(Util.objIsNULL(request.getParameter("CFS"))?"N":request.getParameter("CFS"));
		rnb.setCAM_only(Util.objIsNULL(request.getParameter("CAM"))?"N":request.getParameter("CAM"));
		rnb.setCIS_only(Util.objIsNULL(request.getParameter("CIS"))?"N":request.getParameter("CIS"));
		rnb.setCCL_only(Util.objIsNULL(request.getParameter("CCL"))?"N":request.getParameter("CCL"));
		rnb.setCFSH_only(Util.objIsNULL(request.getParameter("CFSH"))?"N":request.getParameter("CFSH"));
		rnb.setCMS_only(Util.objIsNULL(request.getParameter("CMS"))?"N":request.getParameter("CMS"));
		rnb.setCFG_only(Util.objIsNULL(request.getParameter("CFG"))?"N":request.getParameter("CFG"));
		rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
		rnb.setCCIA_only(Util.objIsNULL(request.getParameter("CCIA"))?"N":request.getParameter("CCIA"));
		rnb.setCCFSH_only(Util.objIsNULL(request.getParameter("CCFSH"))?"N":request.getParameter("CCFSH"));
		rnb.setCWMC_only(Util.objIsNULL(request.getParameter("CWMC"))?"N":request.getParameter("CWMC"));*/
		//staff no this _rnb.setPayer(request.getParameter("payers"));
		
		rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ET"))?"N":request.getParameter("ET"));
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout");
			}
			if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			 stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
			}
			rnb.setMarks(stringBuffer.toString());
		
			 QueryStaffRequstDao smd=new QueryStaffRequstDaoImpl();
			 int num=-1;
			 num=smd.updateRequestStaff(StaffNo,rnb.getUrgentDate(), adminUsername);
			 if(num>0){//判断是否把原始数据保存成功
				 AddStaffRequestDao ard=new AddStaffRequestDaoImpl();
				 int nums=ard.saveStaffRequest(rnb);
				 if(nums>0){//是否新增成功
					 rnb.setUpd_date(DateUtils.getNowDateTime());
					int numH= ard.saveStaffMasterHistory(rnb);
					 if(numH>0){//是否新增历史记录
						 result="修改成功!";
					 }else{
						 result="修改成功，但历史数据保存出错!";
					 }
				 }else{
					 result="修改失败!";
				 }
			 }else{
				 result="修改失败，保存原始数据失败!";
			 }
			log.info(adminUsername+"在ModifyStaffRequestServlet中修改staff数据时,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在ModifyStaffRequestServlet中修改staff数据时出现："+e);
			result="修改失败："+e.toString();
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

}
