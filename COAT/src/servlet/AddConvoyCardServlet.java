package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;
import dao.ConsconvoyNamecardDao;
import dao.QueryStaffRequstDao;
import dao.impl.ConsconvoyNamecardDaoImpl;
import dao.impl.QueryStaffRequstDaoImpl;
import entity.NameCardConvoy;
/**
 * 新增到临时表中
 * @author Wilson
 *
 */
public class AddConvoyCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AddConvoyCardServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			//String adminUsername = request.getSession().getAttribute("adminUsername").toString();
			String StaffNo=request.getParameter("StaffNos");
			String Payer=request.getParameter("payers");
			//String urgentDate=request.getParameter("urgentDate");
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
		    rnb.setAe_consultant(Util.objIsNULL(request.getParameter("AEs"))?"N":request.getParameter("AEs"));
		    rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
		    
		    rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		    rnb.setCard_type("N");
			
			rnb.setUpd_name("");  //request.getSession().getAttribute("adminUsername").toString()
			rnb.setUpd_date("");	//Util.dateToStrWithoutHm(new Date())
			//TODO: 新增人 新增时间
			rnb.setAdd_name(request.getParameter("payers"));
			rnb.setAdd_date(Util.dateToStrWithoutHm(new Date()));
			rnb.setShzt("S");   //S 待审核  Y 已批准  N 已拒绝
			rnb.setRemark("");//Constant.convoy_card_remark
			rnb.setRemarkcons(request.getParameter("remarkcons"));
			rnb.setPayer(request.getParameter("payers"));
			
			rnb.setCFS_only(Util.objIsNULL(request.getParameter("CFS"))?"N":request.getParameter("CFS"));
			rnb.setCAM_only(Util.objIsNULL(request.getParameter("CAM"))?"N":request.getParameter("CAM"));
			rnb.setCIS_only(Util.objIsNULL(request.getParameter("CIS"))?"N":request.getParameter("CIS"));
			rnb.setCCL_only(Util.objIsNULL(request.getParameter("CCL"))?"N":request.getParameter("CCL"));
			rnb.setCFSH_only(Util.objIsNULL(request.getParameter("CFSH"))?"N":request.getParameter("CFSH"));
			rnb.setCMS_only(Util.objIsNULL(request.getParameter("CMS"))?"N":request.getParameter("CMS"));
			rnb.setCFG_only(Util.objIsNULL(request.getParameter("CFG"))?"N":request.getParameter("CFG"));
			rnb.setCIB_only(Util.objIsNULL(request.getParameter("CIB"))?"N":request.getParameter("CIB"));
			rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
			
		
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
 
		/*******************等待处理*****************************/

	 
				ConsconvoyNamecardDao addcons = new ConsconvoyNamecardDaoImpl();
				int num = addcons.saveNewRequest(rnb);//保存new Name Card convoy（新增数据）

				if (num>0) {
					System.out.println("----保存成功");
					//System.out.println("We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!");
					//System.out.println("--------保存new Name Card convoy oko!!!----------");
					//out.print("<script type='text/javascript'>alert('We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!');location.href='namecard/queryNameCard.jsp';</script>");
					out.print("<script>alert('We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!');location.href='namecard/queryNameCard.jsp';</script>");
				}else{
					System.out.println("保存失败");
					out.print("<script>alert('提交失败!');location.href='namecard/addNameCard.jsp';</script>");
				}
				 
				
		}catch (Exception e){
			e.printStackTrace();
		log.error("在AddConvoyCardServlet中出现："+e.toString());
		}finally{
		//	out.print("<script type='text/javascript'>alert('We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!');location.href='namecard/queryNameCard.jsp';</script>");
			out.flush();
			out.close();
		}
 
	}

}
