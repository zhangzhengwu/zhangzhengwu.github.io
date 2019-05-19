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

import dao.GetCNMDao;
import dao.MotifyDao;
import dao.PayerDao;
import dao.QueryChargeDao;
import dao.RequestRecordDao;
import dao.impl.GetCNMDaoImpl;
import dao.impl.MotifyDaoImpl;
import dao.impl.PayerDaoImpl;
import dao.impl.QueryChargeDaoImpl;
import dao.impl.RequestRecordDaoImpl;

import entity.Change_Record;


import servlet.staffservlet.StaffNameCardReaderServlet;
import util.Constant;
import util.DateUtils;
import util.Util;

public class NameCardWriteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(NameCardWriteServlet.class);
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
			}else if(("savenamecardrequest").equalsIgnoreCase(method)){//SZOADM 新增NameCard
				result=savenamecardrequest(request,response);
			}else if(("modifynamecardrequest").equalsIgnoreCase(method)){//SZOADM 新增NameCard
				result=modifynamecardrequest(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
			
		}catch (NullPointerException e) {
			log.error("Name Card==>"+method+"操作异常：空值=="+e);
			//	response.getWriter().print("Submit data anomalies, please refresh retry!");
			result=Util.joinException(e);
		}catch (Exception e) {
			log.error("Name Card==>"+method+"操作异常："+e);
			//response.getWriter().print("Exception:"+e.getMessage());
			result=Util.joinException(e);

		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	}
	/**
	 * SZOADM 修改名片
	 * @author kingxu
	 * @date 2015-9-28
	 * @param request
	 * @param response
	 * @return 
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String modifynamecardrequest(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";
		try{
			NameCardDao namecardDao=new NameCardDaoImpl();
			RequestNewBean rnb=new RequestNewBean();
			String reStaffNo=request.getParameter("reStaffNo");
			String reQuantity=request.getParameter("reQuantity");
			String rePayer=request.getParameter("rePayer");
			String reUrgent=request.getParameter("reUrgent");
			String Payer=request.getParameter("Payer");
			String StaffNo=request.getParameter("StaffNo");
			rnb.setStaff_code(request.getParameter("StaffNo"));
			rnb.setPayer(Payer);
			rnb.setName(request.getParameter("EnglishName"));
			rnb.setName_chinese(request.getParameter("ChineseName"));
			rnb.setTitle_english(request.getParameter("EnglishTitle_Department"));
			rnb.setTitle_chinese(request.getParameter("ChineseTitle_Department"));
			rnb.setExternal_english(request.getParameter("EnglishExternalTitle_Department"));
			rnb.setExternal_chinese(request.getParameter("ChineseExternalTitle_Department"));
			rnb.setAcademic_title_e(request.getParameter("AcademicTitle_e_Department"));
			rnb.setAcademic_title_c(request.getParameter("AcademicTitle_c_Department"));
			rnb.setProfess_title_e(request.getParameter("EnglishEducationTitle"));
			rnb.setProfess_title_c(request.getParameter("ChineseEducationTitle"));
			rnb.setTr_reg_no(request.getParameter("trg"));
			rnb.setCe_no(request.getParameter("CE"));
			rnb.setMpf_no(request.getParameter("MPF"));
			rnb.setRemark1(request.getParameter("HKCIB"));
			rnb.setE_mail(request.getParameter("Email"));
			rnb.setDirect_line(request.getParameter("DirectLine"));
			rnb.setFax(request.getParameter("FAX"));
			rnb.setBobile_number(request.getParameter("MobilePhone"));
			rnb.setUrgentDate(request.getParameter("UrgentDate"));
			rnb.setUpd_name(request.getSession().getAttribute("adminUsername").toString());
			rnb.setQuantity(request.getParameter("Quantity"));
			rnb.setUrgent(request.getParameter("urgent"));
			rnb.setLayout_type(request.getParameter("layout_type"));
			rnb.setUpd_date(DateUtils.getNowDateTime());
	/*		rnb.setCAM_only(request.getParameter("CAM"));
			rnb.setCCL_only(request.getParameter("CCL"));
			rnb.setCFG_only(request.getParameter("CFG"));
			rnb.setCFS_only(request.getParameter("CFS"));
			rnb.setCFSH_only(request.getParameter("CFSH"));
			rnb.setCIS_only(request.getParameter("CIS"));
			rnb.setCMS_only(request.getParameter("CMS"));
			rnb.setBlank_only(request.getParameter("Blank"));*/
			rnb.setCFS_only("");
			rnb.setCAM_only("");
			rnb.setCCL_only("");
			rnb.setCFSH_only("");
			rnb.setCMS_only("");
			rnb.setCFG_only("");
			rnb.setBlank_only("");
			rnb.setCIS_only("");
			rnb.setLocation(request.getParameter("location"));
			rnb.setAe_consultant(Util.objIsNULL(request.getParameter("aeConsultant"))?"N":request.getParameter("aeConsultant"));
			rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ET"))?"N":request.getParameter("ET"));
			rnb.setCard_type("N");
			StringBuffer stringBuffer=new StringBuffer("");
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
			rnb.setRefno(Integer.parseInt(request.getParameter("refno")));//获取申请编号
			NameCardPayerDao payerDao=new NameCardPayerDaoImpl();
			Map<String,Object> map=payerDao.nameCardUsage(Payer);
			payerDao=new NameCardPayerDaoImpl();
			double nowPayNum=payerDao.getPayerNumber(Payer,rnb.getUrgentDate());
			
			
			double sum=0;//用户名片限额
			double used=0;//用户已印名片数量
			if(!Util.objIsNULL(map)){
				sum=Constant.NAMECARD_NUM+Double.parseDouble(map.get("addnum")+"");//用户名片限额
				used=Double.parseDouble(map.get("used")+"")-Double.parseDouble(map.get("payernum")+"");
			}else{
				throw new RuntimeException("查询【"+Payer+"】名片办理情况 时出现异常[无法获取办理情况]");
			}
			namecardDao=new NameCardDaoImpl();//重新初始化
			result=namecardDao.updateNameCard(rnb, reStaffNo, rePayer, reUrgent, used-(Integer.parseInt(reQuantity)-nowPayNum), sum);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("在NameCardWriteServlet[modifynamecardrequest]中出现："+e.toString());
			throw new RuntimeException(e.getMessage());
		}finally{
			
		}
		return result;
		
	}
	/**
	 * SZOADM 添加名片记录
	 * @author kingxu
	 * @date 2015-9-22
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String savenamecardrequest(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";
		try{
				NameCardDao namecardDao=new NameCardDaoImpl();
				String StaffNo=request.getParameter("StaffNos");
				String Payer=request.getParameter("payers");
				String pay=request.getParameter("pays");
				StringBuffer stringBuffer=new StringBuffer("");
				RequestNewBean rnb=new RequestNewBean();
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
				rnb.setRemark1(request.getParameter("HKCIB_NOs"));//HKCIB
				rnb.setE_mail(request.getParameter("Emails"));
				rnb.setDirect_line(request.getParameter("DirectLines"));
				rnb.setFax(request.getParameter("FAXs"));
				rnb.setBobile_number(request.getParameter("Mobiles"));
				rnb.setQuantity(request.getParameter("nums"));
				rnb.setLocation(request.getParameter("locatins"));
				rnb.setLayout_type(request.getParameter("types").trim());
				rnb.setAe_consultant(Util.objIsNULL(request.getParameter("AEs"))?"N":request.getParameter("AEs"));
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
				rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));*/
				rnb.setCFS_only("");
				rnb.setCAM_only("");
				rnb.setCCL_only("");
				rnb.setCFSH_only("");
				rnb.setCMS_only("");
				rnb.setCFG_only("");
				rnb.setBlank_only("");
				rnb.setCIS_only("");
				rnb.setPayer(request.getParameter("payers"));
				rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
				rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
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
				rnb.setAddname(user);
				rnb.setAdddate(DateUtils.getNowDateTime());
				
				NameCardPayerDao payerDao=new NameCardPayerDaoImpl();
				Map<String,Object> map=payerDao.nameCardUsage(Payer);
				double sum=0;//用户名片限额
				double used=0;//用户已印名片数量
				if(!Util.objIsNULL(map)){
					sum=Constant.NAMECARD_NUM+Double.parseDouble(map.get("addnum")+"");//用户名片限额
					used=Double.parseDouble(map.get("used")+"")-Double.parseDouble(map.get("payernum")+"");
				}else{
					throw new RuntimeException("查询【"+Payer+"】名片办理情况 时出现异常[无法获取办理情况]");
				}
				result=namecardDao.saveNameCardRequest(rnb, user, used, sum);
		
		}catch (Exception e) {
			log.error("在NameCardWriteServlet[savenamecardrequest]中出现："+e.toString());
			throw new RuntimeException(e.getMessage());
		}
		return result;

	}
	


}
