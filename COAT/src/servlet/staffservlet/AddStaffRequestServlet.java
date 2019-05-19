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
import dao.AdminDAO;
import dao.impl.AddStaffRequestDaoImpl;
import entity.RequestStaffBean;

public class AddStaffRequestServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AddStaffRequestServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String adminUsername = request.getSession().getAttribute("adminUsername").toString();
		
		String StaffNo=request.getParameter("StaffNos");
		//staff no this String Payer=request.getParameter("payers");
		//String urgent=request.getParameter("urgent");
		//staff no this String urgentDate=request.getParameter("urgentDate");
 		//staff no this _String pay=request.getParameter("pays");
		StringBuffer stringBuffer=new StringBuffer("");

		String company_val=request.getParameter("company_val");
		
		RequestStaffBean rnb=new RequestStaffBean();
		rnb.setStaff_code(StaffNo);
		rnb.setName(request.getParameter("EnglishNames"));
		rnb.setName_chinese(request.getParameter("ChineseNames"));
		rnb.setTitle_english(request.getParameter("EnglishPositions")+";"+request.getParameter("EnglishStaffDepartmentTexts"));
		rnb.setTitle_chinese(request.getParameter("ChinesePositions")+";"+request.getParameter("ChineseStaffDepartmentTexts"));
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
		rnb.setUpd_date(DateUtils.getNowDateTime()); 
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
		 
		rnb.setCompany(company_val);
		
		
		//staff no this _rnb.setPayer(request.getParameter("payers"));
		rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout");
			}
			 
			if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			 stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
			}
			rnb.setMarks(stringBuffer.toString());
		/*******************等待处理*****************************/
		int num = 0; 
		  try{
				if(Integer.parseInt(rnb.getQuantity())<0){
					int isRoot = new AdminDAO().getIsRoot(adminUsername);
					if(0 == isRoot){
						out.print("<script>alert('您不是管理員無法刪除數據!');location.href='addNameCard.jsp';</script>");
					}
				}else{
					AddStaffRequestDao addStaffRequestDao=new AddStaffRequestDaoImpl();
					/**
					 * 保存新表数据
					 */
					num=addStaffRequestDao.saveStaffRequest(rnb);
					
					if (num>0) {
					/**
					 * 保存历史数据
					 */
					addStaffRequestDao.saveStaffMasterHistory(rnb);
					 
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				log.error("保存staff NameCard时出现："+e.toString());
			}
			finally{
			   if(num>0){
				   //response.sendRedirect("StaffSuccess.jsp");
				   out.print("<script type='text/javascript'>alert('添加成功');location.href='page/staffnamecard/addStaffCard.jsp';</script>");
				}else if(Integer.parseInt(rnb.getQuantity())>0 && !(rnb.getUrgent().toUpperCase().equals("Y"))){
					out.print("<script type='text/javascript'>alert('添加成功_Staff！');location.href='page/staffnamecard/addStaffCard.jsp';</script>");
				
				}else{
					int isRoot = new AdminDAO().getIsRoot(adminUsername);
					if(isRoot==1){
						response.sendRedirect("page/staffnamecard/queryStaffCard.jsp");
					}
				} 
				out.flush();
				out.close();
			}
 
	}

}
