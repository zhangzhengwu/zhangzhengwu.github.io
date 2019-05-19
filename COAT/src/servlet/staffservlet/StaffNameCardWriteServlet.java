package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import sun.misc.Perf.GetPerfAction;
import util.Constant;
import util.DateUtils;
import util.SendMail;
import util.Util;
import dao.AddStaffRequestDao;
import dao.QueryStaffRequstDao;
import dao.impl.AddStaffRequestDaoImpl;
import dao.impl.QueryStaffRequstDaoImpl;
import dao.impl.staffnamecard.StaffNameCardDaoImpl;
import dao.staffnamecard.StaffNameCardDao;
import entity.RequestStaffConvoyBean;

public class StaffNameCardWriteServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(StaffNameCardReaderServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<span style='position:fixed;top:50%;left:40%;font-size:24px;'>You don't have the permission!</span>");
	}
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method")+"";
		//System.out.println("method-->"+method);
		try{
			user=request.getSession().getAttribute("adminUsername")+"";//request.getSession().getAttribute("convoy_username").toString();
			if(method.equalsIgnoreCase("reject")){
				reject(request, response);
			}else if(method.equalsIgnoreCase("deptapproval")){
				deptapproval(request, response);
			}else if(method.equalsIgnoreCase("hrapproval")){//2015-11-11 09:40:11取消HR审核流程
				//hrapproval(request, response);
				throw new Exception("Unauthorized access!");
			}else if(method.equalsIgnoreCase("requestconvoystaff")){
				requestconvoystaff(request, response);
			}else if(method.equalsIgnoreCase("delstaffnamecard")){
				delstaffnamecard(request, response);
			}else if(method.equalsIgnoreCase("saverequestconvoy")){//保存staff/dept head/hr 提交的申请
				saverequestconvoy(request, response);
			}else if(method.equalsIgnoreCase("saverequestconvoy1")){//保存staff/dept head/hr 提交的申请
				saverequestconvoy1(request, response);
			}else if(method.equalsIgnoreCase("szoadmapproval")){//保存staff/dept head/hr 提交的申请
				szoadmapproval(request, response);
			}else if(method.equalsIgnoreCase("addState")){
				addState(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}

		}catch (NullPointerException e) {
			log.error("Staff Name Card==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Staff Name Card==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 
	}
	
	
	void saverequestconvoy1(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String adminUsername="";
		String roleType="";
		//String roleType=Constant.RoleType_staff;
		try{
			roleType=request.getSession().getAttribute("roleType")+"";
			adminUsername = request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
//			adminUsername = request.getParameter("pay");
//			roleType=request.getSession().getAttribute("roleType").toString();
			//System.out.println("----------->"+roleType);
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			if(Util.objIsNULL(roleType)){
				throw new RuntimeException("Role information acquisition exception");
			}
			RequestStaffConvoyBean rnb=new RequestStaffConvoyBean();
					String StaffNo=request.getParameter("StaffNo");
					String company_Type=request.getParameter("company_Type");
					StringBuffer stringBuffer=new StringBuffer("");
					rnb.setStaff_code(StaffNo);
					rnb.setPayer(request.getParameter("pay"));
					rnb.setName(request.getParameter("EnglishName"));
					rnb.setName_chinese(request.getParameter("ChineseName"));
					rnb.setTitle_english(request.getParameter("EnglishPosition")+"; "+request.getParameter("EnglishStaffDepartmentText"));
					rnb.setTitle_chinese(request.getParameter("ChinesePosition")+"; "+request.getParameter("ChineseStaffDepartmentText"));
					rnb.setAcademic_title_e(request.getParameter("EnglishAcademicTitle"));
					rnb.setAcademic_title_c(request.getParameter("ChineseAcademicTitle"));
					rnb.setProfess_title_e(request.getParameter("EnglishProfessionalTitle"));
					rnb.setProfess_title_c(request.getParameter("ChineseProfessionalTitle"));
					rnb.setExternal_english(request.getParameter("englishExternal"));
					rnb.setExternal_chinese(request.getParameter("chineseExternal"));
					rnb.setUrgentDate(DateUtils.getNowDateTime());
					rnb.setTr_reg_no(request.getParameter("TR_RegNo"));
					rnb.setCe_no(request.getParameter("CENO"));
					rnb.setMpf_no(request.getParameter("MPFA_NO"));
					rnb.setE_mail(request.getParameter("Email"));
					rnb.setDirect_line(request.getParameter("DirectLine"));
					rnb.setFax(request.getParameter("FAX"));
					rnb.setBobile_number(request.getParameter("Mobile"));
					rnb.setQuantity(request.getParameter("num"));
					rnb.setLocation(request.getParameter("locatin"));
					rnb.setLayout_type(request.getParameter("type").trim());
					rnb.setCard_type("N");
					rnb.setUpd_name(adminUsername);
					rnb.setUpd_date(DateUtils.getNowDateTime()); 
					
					rnb.setCompany(company_Type);
					rnb.setCFS_only("");
					rnb.setCAM_only("");
					rnb.setCIS_only("");
					rnb.setCCL_only("");
					rnb.setCFSH_only("");
					rnb.setCMS_only("");
					rnb.setCFG_only("");
					rnb.setBlank_only("");
					rnb.setCCIA_only("");
					rnb.setCCFSH_only("");
					rnb.setCWMC_only("");
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
					
					
					rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
					rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
					rnb.setRemarkcons((request.getParameter("remark")+"").replace("Please state your special request in this box", ""));
					if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
						stringBuffer.append("Premium Layout");
					}
					if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
						stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
					}
					rnb.setMarks(stringBuffer.toString());
					/* if(Constant.RoleType_depthead.equalsIgnoreCase(roleType)){//dept head 提交申请
						rnb.setShzt("E");
					}else */if(Constant.RoleType_hr.equalsIgnoreCase(roleType)){//hr 提交申请
						rnb.setShzt("R");
					}else{
						rnb.setShzt("S");
					}
					StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
//					String department_code=staffDao.queryStaffRequestConvoy1(StaffNo);
					String grade=staffDao.queryStaffRequestGrade(StaffNo);;
					String noapproves=Util.getProValue("namecard.staff.noapprove");
					List<String> noapprove=Arrays.asList(noapproves.split(","));
//					if("MGT".equalsIgnoreCase(department_code)){
					if(noapprove.contains(grade)){
						rnb.setShzt("E");
						roleType=Constant.RoleType_depthead;
					}else{
						roleType=Constant.RoleType_staff;
					}
					result=staffDao.saveStaffRequestConvoy(rnb, roleType,adminUsername);
		}catch (Exception e){
			result=Util.getMsgJosnObject("exception", e.getMessage());
			log.error("保存staff NameCard Convoy时出现："+e.toString());
		}
		finally{
			out.print(result);
			out.flush();
			out.close();
		}
		
	}
	
	/**
	 * SZO ADM 审核
	 * @author kingxu
	 * @date 2015-8-12
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	void szoadmapproval(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		try{
			adminUsername=request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
		String StaffNo=request.getParameter("StaffNos");
		String company_type=request.getParameter("company_Type");
		String refno=request.getParameter("refno");
		StringBuffer stringBuffer=new StringBuffer("");
		RequestStaffConvoyBean rnb=new RequestStaffConvoyBean();
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
		rnb.setUpd_name(adminUsername);
		rnb.setUpd_date(request.getParameter("upd_date")); 
		rnb.setUrgentDate(request.getParameter("UrgentDate"));
		
		rnb.setCompany(company_type);
		rnb.setCFS_only("");
		rnb.setCAM_only("");
		rnb.setCIS_only("");
		rnb.setCCL_only("");
		rnb.setCFSH_only("");
		rnb.setCMS_only("");
		rnb.setCFG_only("");
		rnb.setBlank_only("");
		rnb.setCCIA_only("");
		rnb.setCCFSH_only("");
		rnb.setCWMC_only("");
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
		rnb.setCWMC_only(Util.objIsNULL(request.getParameter("CWMC"))?"N":request.getParameter("CWMC"));
		*/
		
		
		//staff no this _rnb.setPayer(request.getParameter("payers"));
		rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ET"))?"N":request.getParameter("ET"));
		if(!Util.objIsNULL(request.getParameter("remarkcons"))){
			rnb.setRemarkcons(request.getParameter("remarkcons"));
		}
		
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout");
			}
			if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			 stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
			}
			rnb.setMarks(stringBuffer.toString());
			rnb.setShzt("Y");//SZO ADM Approval
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
			 result=staffDao.SZOADMApproval(rnb, adminUsername, refno);
			
		/*	 QueryStaffRequstDao smd=new QueryStaffRequstDaoImpl();
			  int num=-1;
			  num=smd.approveRequestStaffConvoy(rnb.getStaff_code(), rnb.getUrgentDate(), adminUsername);
			 if(num>0){////判断是否把原始数据保存成功
				 AddStaffRequestDao ard=new AddStaffRequestDaoImpl();
				 int nums=ard.saveStaffRequest(rnb);
				 if(nums>0){//是否新增成功
					 rnb.setUpd_date(DateUtils.getNowDateTime());
					int numH= ard.saveStaffConvoyMasterHistory(rnb);
					 if(numH>0){//是否新增历史记录
						 result="审核成功!";
					 }else{
						 result="审核成功，但历史数据保存出错!";
					 }
				 }else{
					 result="保存失败!";
				 }
			 }else{
				 result="审核失败!";
			 }*/
			 
			log.info(adminUsername+"在SaveStaffRequestConvoyServlet中ADM 审核时,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在SaveStaffRequestConvoyServlet中ADM 审核时出现："+e.getMessage());
			result=Util.jointException(e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}
	
	
	
	/**
	 * 保存staffnamecard申请(兼容staff/dept head/hr)
	 * @author kingxu
	 * @date 2015-8-12
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	void saverequestconvoy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result="";
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String adminUsername="";
		String roleType="";
		try{
			adminUsername = request.getSession().getAttribute("convoy_username")+"";
			roleType=request.getSession().getAttribute("roleType")+"";
			//System.out.println("----------->"+roleType);
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			if(Util.objIsNULL(roleType)){
				throw new RuntimeException("Role information acquisition exception");
			}
			RequestStaffConvoyBean rnb=new RequestStaffConvoyBean();
					String StaffNo=request.getParameter("StaffNos");
					StringBuffer stringBuffer=new StringBuffer("");
					rnb.setStaff_code(StaffNo);
					rnb.setPayer(request.getParameter("pays"));
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
					 if(Constant.RoleType_depthead.equalsIgnoreCase(roleType)){//dept head 提交申请
						rnb.setShzt("E");
					}else if(Constant.RoleType_hr.equalsIgnoreCase(roleType)){//hr 提交申请
						rnb.setShzt("R");
					}else{
						rnb.setShzt("S");
					}
					StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
					result=staffDao.saveStaffRequestConvoy(rnb, roleType,adminUsername);
		}catch (Exception e){
			e.printStackTrace();
			result=Util.getMsgJosnObject("exception", e.getMessage());
			log.error("保存staff NameCard Convoy时出现："+e.toString());
		}
		finally{
			out.print(result);
			out.flush();
			out.close();
		}


	}
	
	
	
	
	void hrapproval(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("-------------------------------");
		
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		try{
			
			adminUsername = request.getSession().getAttribute("convoy_username")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			String StaffNo=request.getParameter("StaffNos");
			String urgentDate=request.getParameter("UrgentDate");
			String remarkcons=request.getParameter("remarkcons");
			String englishName=request.getParameter("EnglishNames");
			String chineseName=request.getParameter("ChineseNames");
			String refno=request.getParameter("refno");

			if(Util.objIsNULL(adminUsername)){
				result=Util.getMsgJosnObject("exception","Identity information is missing");
			}else{
				StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
				result=staffDao.approveRequestHRConvoy_remark(StaffNo, urgentDate, adminUsername,remarkcons,englishName,refno,chineseName);
			}
			log.info(adminUsername+"在HRApproval环节 审核Request,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在HRApproval环节 审核Request,异常："+e);
			result=Util.jointException(e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}

	}
	
	/**
	 * Staff 删除namecard申请
	 * @author kingxu
	 * @date 2015-8-5
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	void delstaffnamecard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		try{
			String refno=request.getParameter("refno");
			String urgentDate=request.getParameter("urgentDate");
			String type=request.getParameter("type");
			adminUsername=request.getSession().getAttribute("convoy_username")+"";
			if(Util.objIsNULL(adminUsername)){
				result=Util.getMsgJosnObject("exception","Identity information is missing");
			}else{
				if(Util.objIsNULL(refno)){
					result=Util.getMsgJosnObject("exception","Application number does not exist");
				}else{
					StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
					int num=-1;
					if(Constant.RoleType_staff.equalsIgnoreCase(type)){
						num=staffDao.delstaffnamecard(refno, adminUsername, urgentDate);
					}else if(Constant.RoleType_depthead.equalsIgnoreCase(type)){
						num=staffDao.deldeptnamecard(refno, adminUsername, urgentDate);
					}else if(Constant.RoleType_hr.equalsIgnoreCase(type)){
						num=staffDao.delhrnamecard(refno, adminUsername, urgentDate);
					}else{
						throw new RuntimeException("Unauthorized request");
					}
					if(num>1){
						result=Util.getMsgJosnObject("success", "success");
					}else{
						result=Util.getMsgJosnObject("error", "error");
					}
				}
			}
		}catch (Exception e) {
			result=Util.jointException(e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}
	/**
	 * StaffNameCard dept head Approval
	 * @author kingxu
	 * @date 2015-8-5
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	void deptapproval(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		try{
			adminUsername = request.getSession().getAttribute("convoy_username")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			String StaffNo=request.getParameter("StaffNos");
			String urgentDate=request.getParameter("UrgentDate");
			String englishName=request.getParameter("EnglishNames");
			String chineseNames=request.getParameter("ChineseNames");
			String remarkcons=request.getParameter("remarkcons");
			String refno=request.getParameter("refno");

			if(Util.objIsNULL(adminUsername)){
				result=Util.getMsgJosnObject("exception","Identity information is missing");
			}else{
				StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
				result=staffDao.approveRequestDepartConvoy_remark(StaffNo, urgentDate, adminUsername,remarkcons,englishName,refno,chineseNames);
			}
			log.info(adminUsername+"在DepartApproval环节 审核Request,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在DepartApproval环节 审核Request,异常："+e);
			result="Approve Exception ："+e.toString();
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}

	}


	/**
	 * staff econvoy submit
	 * @author kingxu
	 * @date 2015-8-4
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	void requestconvoystaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result="";
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String adminUsername="";
		int num = 0; 
		String email="";
		RequestStaffConvoyBean rnb=new RequestStaffConvoyBean();
		try{
			adminUsername = request.getSession().getAttribute("convoy_username")+"";
			if(Util.objIsNULL(adminUsername)){
				result=Util.getMsgJosnObject("exception","Identity information is missing");
			}else{
				String StaffNo=request.getParameter("StaffNos");
				QueryStaffRequstDao qsrDao=new QueryStaffRequstDaoImpl();
				email=qsrDao.findDeptByStaff(StaffNo);//获取部门主管邮箱
				if(!Util.objIsNULL(email)){//仅在部门主管邮箱不为空的情况下进行下一步操作
					StringBuffer stringBuffer=new StringBuffer("");
					rnb.setStaff_code(StaffNo);
					rnb.setPayer(request.getParameter("pays"));
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

					String body="Dear Department Head,<br/><br/>"
							+"    The name card request initiated by "+rnb.getName()+"  "+StaffNo+"  is in Department Head approval status."
							+"<br/><br/>"
							+"Please visit and approve at:<br/>"
							+"<a href='http://www.econvoy.com/group/convoy/office-admin?handle=deptapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
							+"Thank You.";
					result=SendMail.send("COAT: "+rnb.getName()+" - Name Card Request", email, null, null, null, body, "COAT", "email.ftl", "");
					JSONObject json=new JSONObject(result);
					if("success".equalsIgnoreCase(json.get("state")+"")){

						/*******************等待处理*****************************/
						AddStaffRequestDao addStaffRequestDao=new AddStaffRequestDaoImpl();
						/**
						 * 保存新表数据
						 */
						num=addStaffRequestDao.saveStaffRequestConvoy(rnb);
						if(num>0){
							result=Util.getMsgJosnObject("success", "success");
						}else{
							result=Util.getMsgJosnObject("error", "error");

						}
					}
				}else{
					result=Util.getMsgJosnObject("error", "Access department supervisor information exception");
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			result=Util.getMsgJosnObject("exception", e.getMessage());
			log.error("保存staff NameCard Convoy时出现："+e.toString());
		}
		finally{
			/*		if(!Util.objIsNULL(email)){
				out.print("1");//暂时改为1和-1区分操作是否成功 2015年7月31日14:11:55
				//out.print("<script type='text/javascript'>alert('We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!');location.href='namecard/staff/queryStaffCard.jsp';</script>");
			}else{
				out.print("-1");
				//out.print("<script type='text/javascript'>alert('Email Send Error!');location.href='namecard/staff/queryStaffCard.jsp';</script>");
			}*/
			out.print(result);
			out.flush();
			out.close();
		}


	}


	void reject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		String staffcode=request.getParameter("staffcode");
		String urgentDate=request.getParameter("urgentDate");
		String refno=request.getParameter("refno");
		String role=request.getParameter("role");
		String remark=request.getParameter("remark");
		String nameE=request.getParameter("name_english");
		String nameC=request.getParameter("name_chinese");
		try{
			adminUsername=request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
			int num=-1;
			if(("SZADM").equalsIgnoreCase(role)){
				num=staffDao.rejectStaffRequestConvoy(staffcode, urgentDate, adminUsername,refno,remark,nameE,nameC);
			}else if(("HR").equalsIgnoreCase(role)){
				num=staffDao.rejectStaffRequestConvoyHR(staffcode, urgentDate, adminUsername, refno,remark,nameE,nameC);
			}else{
				num=staffDao.rejectStaffRequestConvoyDept(staffcode, urgentDate, adminUsername, refno,remark,nameE,nameC);
			}
			if(num>1){
				result=Util.getMsgJosnObject("success", "Reject Success!");
			}else{
				result=Util.getMsgJosnObject("error", "Reject Error!");
			}
		}catch(Exception e){
			result=Util.jointException(e);
			log.error(adminUsername+"  -Reject Request Staff Convoy 时出现 ："+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

	public void addState(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		PrintWriter out = response.getWriter();
		String result="";
		String refno =request.getParameter("refno");
		String state=request.getParameter("state");
		String approveDate=request.getParameter("approveDate");
		String adminUsername = "";
		int num=-1;
		try{
			adminUsername=request.getSession().getAttribute("adminUsername")+"";
			if(Util.objIsNULL(adminUsername)){
				throw new RuntimeException("Identity information is missing");
			}
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
			num=staffDao.addState(refno,state,approveDate,adminUsername);
			if(num>0){
				result=Util.getMsgJosnObject("success","success");
			}else if(num==-2){
				result=Util.getMsgJosnObject("error","Data already exists!");
			}else{
				result=Util.getMsgJosnObject("success","error");
			}
			//result=Util.returnValue(num);
		}catch (Exception e) {
			e.printStackTrace();
			result=Util.jointException(e);
			log.error(adminUsername+"  -Add State Staff Convoy 时出现 ："+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}
}
