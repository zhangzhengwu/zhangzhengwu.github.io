package com.coat.namecard.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.coat.namecard.dao.NameCardDao;
import com.coat.namecard.dao.NameCardPayerDao;
import com.coat.namecard.dao.impl.NameCardDaoImpl;
import com.coat.namecard.dao.impl.NameCardPayerDaoImpl;
import com.coat.namecard.entity.NameCardConvoy;
import com.coat.namecard.entity.RequestNewBean;

import util.Constant;
import util.DateUtils;
import util.Util;

public class NameCardConvoyWriteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(NameCardConvoyWriteServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<span style='position:fixed;top:50%;left:40%;font-size:24px;'>You don't have the permission!</span>");
	}
	String user=null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method")+"";
		PrintWriter out = response.getWriter();
		String result="";
		try{
			user=Util.objIsNULL(request.getSession().getAttribute("adminUsername"))?(request.getSession().getAttribute("convoy_username")+""):(request.getSession().getAttribute("adminUsername"))+"";//request.getSession().getAttribute("convoy_username").toString();
			if(Util.objIsNULL(user)){
				new RuntimeException("Identity information is missing");
			}
			if(("saveconvoyrequest").equalsIgnoreCase(method)){
				result=saveconvoyrequest(request, response);
			}else if(("approveRequest").equalsIgnoreCase(method)){
				result=approveRequest(request, response);
			}else{
				throw new Exception("Unauthorized access!");
			}

		}catch (NullPointerException e) {
			log.error("Name Card==>"+method+"操作异常：空值=="+e);
			result=Util.joinException(e);			
		}catch (Exception e) {
			log.error("Name Card==>"+method+"操作异常："+e);
			result=Util.joinException(e);
		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	}
	
	/**
	 * SZOADM Approve NameCard Request
	 * @author kingxu
	 * @date 2015-10-13
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String approveRequest (HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";
		try{
				NameCardDao namecardDao=new NameCardDaoImpl();
				StringBuffer stringBuffer=new StringBuffer("");
				RequestNewBean rnb=new RequestNewBean();
				String StaffNo=request.getParameter("StaffNos");
				String Payer=request.getParameter("payers");
				String urgentDate=request.getParameter("UrgentDate");
				//String sfs=Util.objIsNULL(request.getParameter("sfs"))?"N":request.getParameter("sfs");
				rnb.setStaff_code(request.getParameter("StaffNos"));
				rnb.setName(request.getParameter("EnglishNames"));
				rnb.setName_chinese(request.getParameter("ChineseNames"));
				rnb.setTitle_english(request.getParameter("EnglishPositions"));
				rnb.setTitle_chinese(request.getParameter("ChinesePositions"));
				rnb.setExternal_english(request.getParameter("EnglishExternalTitles"));
				rnb.setExternal_chinese(request.getParameter("ChineseExternalTitles"));
				rnb.setAcademic_title_e(request.getParameter("EnglishAcademicTitles"));
				rnb.setAcademic_title_c(request.getParameter("ChineseAcademicTitles"));
				rnb.setProfess_title_e(request.getParameter("EnglishProfessionalTitles"));
				rnb.setProfess_title_c(request.getParameter("ChineseProfessionalTitles"));
				rnb.setUrgentDate(Util.objIsNULL(urgentDate)?DateUtils.getNowDateTime():urgentDate);
				rnb.setTr_reg_no(request.getParameter("TR_RegNos"));
				rnb.setCe_no(request.getParameter("CENOs"));
				rnb.setMpf_no(request.getParameter("MPFA_NOs"));
				rnb.setRemark1(request.getParameter("HKCIB_NOs"));
				rnb.setE_mail(request.getParameter("Emails"));
				rnb.setDirect_line(request.getParameter("DirectLines"));
				rnb.setFax(request.getParameter("FAXs"));
				rnb.setBobile_number(request.getParameter("Mobiles"));
				rnb.setQuantity(request.getParameter("nums"));
				rnb.setLocation(request.getParameter("locations"));
				rnb.setLayout_type(request.getParameter("types").trim());
			    rnb.setAe_consultant(Util.objIsNULL(request.getParameter("AEs"))?"N":request.getParameter("AEs"));
			    rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
				rnb.setCard_type("N");
				rnb.setUpd_name(request.getSession().getAttribute("adminUsername").toString());
				rnb.setUpd_date(Util.dateToStrWithoutHm(new Date())); 

				/*rnb.setCFS_only(Util.objIsNULL(request.getParameter("CFS"))?"N":request.getParameter("CFS"));
				rnb.setCAM_only(Util.objIsNULL(request.getParameter("CAM"))?"N":request.getParameter("CAM"));
				rnb.setCIS_only(Util.objIsNULL(request.getParameter("CIS"))?"N":request.getParameter("CIS"));
				rnb.setCCL_only(Util.objIsNULL(request.getParameter("CCL"))?"N":request.getParameter("CCL"));
				rnb.setCFSH_only(Util.objIsNULL(request.getParameter("CFSH"))?"N":request.getParameter("CFSH"));
				rnb.setCMS_only(Util.objIsNULL(request.getParameter("CMS"))?"N":request.getParameter("CMS"));
				rnb.setCFG_only(Util.objIsNULL(request.getParameter("CFG"))?"N":request.getParameter("CFG"));
				rnb.setCIB_only(Util.objIsNULL(request.getParameter("CIB"))?"N":request.getParameter("CIB"));
				rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
				*/
				rnb.setCFS_only("");
				rnb.setCAM_only("");
				rnb.setCIS_only("");
				rnb.setCCL_only("");
				rnb.setCFSH_only("");
				rnb.setCMS_only("");
				rnb.setCFG_only("");
				rnb.setBlank_only("");
				rnb.setPayer(request.getParameter("payers"));
				rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
				//boolean isElite=rrd.vailElite_request_new(rnb.getStaff_code(),rnb.getUrgentDate());
				 String EmployeeName="";
				 EmployeeName=namecardDao.getEnglishNameByCode(Payer);
					if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
						stringBuffer.append("Premium Layout ");
					}
					if(rnb.getEliteTeam().toUpperCase().trim().equals("Y")){//顾问为Elite Team 成员
						if(stringBuffer.length()>0)
							stringBuffer.append("+ ");
						stringBuffer.append("Elite Team  ");

					}
					
				rnb.setAe_consultant(Util.objIsNULL(request.getParameter("AEs"))?"N":request.getParameter("AEs"));
				
				/*System.out.println(StaffNo+"----》"+Payer);
				Payer="";*/
				
				if(!StaffNo.toLowerCase().trim().equals(Payer.toLowerCase().trim())){//名片名字与支付人名字 不相同
					stringBuffer.append(" Pay by "+EmployeeName+"("+Payer+")");
				}
				if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
					if(stringBuffer.length()>0)
						stringBuffer.append("+ ");
					stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
				}
				rnb.setMarks(stringBuffer.toString());
				rnb.setCompany(request.getParameter("company"));//存入公司
				rnb.setAddname(user);
				rnb.setAdddate(DateUtils.getNowDateTime());
				rnb.setRemark(request.getParameter("remarks"));
				
				NameCardPayerDao payerDao=new NameCardPayerDaoImpl();
				Map<String,Object> map=payerDao.nameCardUsage(Payer);
				double sum=0;//用户名片限额
				double used=0;//用户已印名片数量
				if(!Util.objIsNULL(map)){
					sum=Constant.NAMECARD_NUM+Double.valueOf(map.get("addnum").toString());//用户名片限额
					used=(Double)map.get("used")-(Double)map.get("payernum");
				}else{
					throw new RuntimeException("查询【"+Payer+"】名片办理情况 时出现异常[无法获取办理情况]");
				}
				namecardDao=new NameCardDaoImpl();
				result=namecardDao.approveNameCardRequest(rnb, user, used, sum);
		
		}catch (Exception e) {
			e.printStackTrace();
			log.error("在NameCardWriteServlet[approveRequest]中出现："+e.toString());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return result;

	
	}
	
	
	
	
	
	
	/**
	 * Consultant NameCard 提交申请
	 * @author kingxu
	 * @date 2015-9-22
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String saveconvoyrequest(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";

		try{
			String StaffNo=request.getParameter("StaffNos");
			String Payer=request.getParameter("payers");
			String pay=request.getParameter("pays");
			StringBuffer stringBuffer=new StringBuffer("");
			//NO CODE 情况下处理方式
			//String sfs=Util.objIsNULL(request.getParameter("sfs"))?"N":request.getParameter("sfs");
			NameCardConvoy rnb=new NameCardConvoy();
			rnb.setStaff_code(request.getParameter("StaffNos"));
			rnb.setName(request.getParameter("EnglishNames"));
			rnb.setName_chinese(request.getParameter("ChineseNames"));
			rnb.setTitle_english(request.getParameter("EnglishPositions"));
			rnb.setTitle_chinese(request.getParameter("ChinesePositions"));
			rnb.setExternal_english(request.getParameter("EnglishExternalTitles"));
			rnb.setExternal_chinese(request.getParameter("ChineseExternalTitles"));
			rnb.setAcademic_title_e("");
			rnb.setAcademic_title_c("");
			rnb.setProfess_title_e(request.getParameter("EnglishProfessionalTitles"));
			rnb.setProfess_title_c(request.getParameter("ChineseProfessionalTitles"));
			rnb.setUrgentDate(DateUtils.getNowDateTime());
			rnb.setTr_reg_no(request.getParameter("TR_RegNos"));
			rnb.setCe_no(request.getParameter("CENOs"));
			rnb.setMpf_no(request.getParameter("MPFA_NOs"));
			rnb.setRemark1(request.getParameter("HKCIB_NOs"));//orlando  HKCIB
			rnb.setE_mail(request.getParameter("Emails"));
			rnb.setDirect_line(request.getParameter("DirectLines"));
			rnb.setFax(request.getParameter("FAXs"));
			rnb.setBobile_number(request.getParameter("Mobiles"));
			rnb.setQuantity(request.getParameter("nums"));
			rnb.setLocation(request.getParameter("locations"));
			rnb.setLayout_type(request.getParameter("types").trim());
			rnb.setAe_consultant(Util.objIsNULL(request.getParameter("AEs"))?"N":request.getParameter("AEs"));
			rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
			rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
			rnb.setCard_type("N");
			rnb.setUpd_name("");  //request.getSession().getAttribute("adminUsername").toString()
			rnb.setUpd_date("");	//Util.dateToStrWithoutHm(new Date())
			rnb.setAdd_name(request.getParameter("payers"));
			rnb.setAdd_date(Util.dateToStrWithoutHm(new Date()));
			rnb.setShzt("S");   //S 待审核  Y 已批准  N 已拒绝
			rnb.setRemark("");//Constant.convoy_card_remark
			rnb.setRemarkcons(request.getParameter("remarkcons"));
			rnb.setPayer(request.getParameter("payers"));

			/*rnb.setCFS_only(Util.objIsNULL(request.getParameter("CFS"))?"N":request.getParameter("CFS"));
			rnb.setCAM_only(Util.objIsNULL(request.getParameter("CAM"))?"N":request.getParameter("CAM"));
			rnb.setCIS_only(Util.objIsNULL(request.getParameter("CIS"))?"N":request.getParameter("CIS"));
			rnb.setCCL_only(Util.objIsNULL(request.getParameter("CCL"))?"N":request.getParameter("CCL"));
			rnb.setCFSH_only(Util.objIsNULL(request.getParameter("CFSH"))?"N":request.getParameter("CFSH"));
			rnb.setCMS_only(Util.objIsNULL(request.getParameter("CMS"))?"N":request.getParameter("CMS"));
			rnb.setCFG_only(Util.objIsNULL(request.getParameter("CFG"))?"N":request.getParameter("CFG"));
			rnb.setCIB_only(Util.objIsNULL(request.getParameter("CIB"))?"N":request.getParameter("CIB"));
			rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
			*/
			rnb.setCFS_only("");
			rnb.setCAM_only("");
			rnb.setCIS_only("");
			rnb.setCCL_only("");
			rnb.setCFSH_only("");
			rnb.setCMS_only("");
			rnb.setCFG_only("");
			rnb.setCIB_only("");
			rnb.setBlank_only("");
			
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout ");
			}
			if(rnb.getEliteTeam().toUpperCase().trim().equals("Y")){//顾问为Elite Team 成员
				if(stringBuffer.length()>0)
					stringBuffer.append("+ ");
				stringBuffer.append("Elite Team  ");
			}
			if(!StaffNo.toLowerCase().trim().equals(Payer.toLowerCase().trim())){//名片名字与支付人名字 不相同
				if(stringBuffer.length()>0)
					stringBuffer.append("+ ");
				stringBuffer.append(" Pay by "+pay+"("+Payer+")");
			}
			if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
				if(stringBuffer.length()>0)
					stringBuffer.append("+ ");
				stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
			}
			rnb.setMarks(stringBuffer.toString());
			rnb.setCompany(request.getParameter("company"));//存入公司
			/*******************等待处理*****************************/
			NameCardDao namecardDao=new NameCardDaoImpl();
			result=namecardDao.saveNewRequest(rnb, user);


		}catch (Exception e){
			log.error("在AddConvoyCardServlet中出现："+e.toString());
			new RuntimeException(e.getMessage());
		}

		return result;
	}
	
	
	
	
	
	
	
	
	
}
