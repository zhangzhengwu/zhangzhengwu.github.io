package servlet.staffservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
import dao.QueryStaffRequstDao;
import dao.impl.AddStaffRequestDaoImpl;
import dao.impl.QueryStaffRequstDaoImpl;
import entity.RequestStaffConvoyBean;

public class AddStaffRequestConvoyServlet extends HttpServlet {
	 
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
		String email="";
		RequestStaffConvoyBean rnb=new RequestStaffConvoyBean();
		  try{
			  adminUsername = request.getSession().getAttribute("convoy_username").toString();
		if(Util.objIsNULL(adminUsername)){
				out.print("<script>alert('Identity information is missing, please login again!');top.location.href='login.jsp';</script>");
		}
		String StaffNo=request.getParameter("StaffNos");
		//staff no this String Payer=request.getParameter("payers");
		//String urgent=request.getParameter("urgent");
		//staff no this String urgentDate=request.getParameter("urgentDate");
 		//staff no this _String pay=request.getParameter("pays");
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
		 
		
		//staff no this _rnb.setPayer(request.getParameter("payers"));
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
					num=addStaffRequestDao.saveStaffRequestConvoy(rnb);
					
					
					
					
					QueryStaffRequstDao qsrDao=new QueryStaffRequstDaoImpl();
					email=qsrDao.findDeptByStaff(StaffNo);
					if(!Util.objIsNULL(email)){
						HttpURLConnection htp=null;
						String body="Dear Department Head,<br/><br/>"
						+"    The name card request initiated by "+rnb.getName()+"  "+StaffNo+"  is in Department Head approval status."
						+"<br/><br/>"
						+"Please visit and approve at:<br/>"
						+"<a href='http://www.econvoy.com/group/convoy/office-admin'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
						+"Thank You.";
						String parmString="to="+email+"&" +
						//"cc=King.Xu@convoy.com.hk&" +
						"subject=COAT: "+rnb.getName()+" - Name Card Request&" +
						"webapp=COAT&" +
						"body="+body;
						
						htp=(HttpURLConnection)new URL(request.getRequestURL().substring(0,request.getRequestURL().indexOf(request.getRequestURI()))+"/ExchangeMail/SendMailServlet").openConnection();
						htp.setDoOutput(true);
						htp.setRequestMethod("POST");
						htp.setUseCaches(false);
						htp.setInstanceFollowRedirects(true);
						htp.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
						OutputStream tarparm=htp.getOutputStream();
					
					
						//System.out.println("Client----第"+(i+1)+"次调用Servlet");
						tarparm.write((parmString).getBytes());//传递参数
						tarparm.flush();
						tarparm.close();
						
					      InputStreamReader isr=new InputStreamReader(htp.getInputStream());
				            BufferedReader br=new BufferedReader(isr);
				            
				           if(br.ready()) {
								System.out.println("Client-----获取返回结果：==="+br.readLine());
								System.out.println(StaffNo+"-"+br.readLine()+"\r\n");
							}
				           htp.disconnect(); 
		  			}
					
					
					
					
					
					
					
					
			}catch (Exception e){
				e.printStackTrace();
				log.error("保存staff NameCard Convoy时出现："+e.toString());
			}
			finally{
			   /*if(num>0){
				   response.sendRedirect("StaffSuccess.jsp");
				}else if(Integer.parseInt(rnb.getQuantity())>0 && !(rnb.getUrgent().toUpperCase().equals("Y"))){
					out.print("<script type='text/javascript'>alert('添加成功_Staff！');location.href='addStaffCard.jsp';</script>");
				
				}
				else{
					int isRoot = AdminDAO.getIsRoot(adminUsername);
					if(isRoot==1){
						response.sendRedirect("queryStaffCard.jsp");
					}
				} */
				//out.print("<script type='text/javascript'>alert('  申请已被接受,成功受理"+(rnb.getUrgent().equals("Y")?"3个工作日后":"14天后")+"可领取NameCard.\\n是否成功都将会有Email通知!');location.href='namecard/staff/queryStaffCard.jsp';</script>");
				if(!Util.objIsNULL(email)){
					out.print("1");//暂时改为1和-1区分操作是否成功 2015年7月31日14:11:55
					//out.print("<script type='text/javascript'>alert('We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!');location.href='namecard/staff/queryStaffCard.jsp';</script>");
				}else{
					out.print("-1");
					//out.print("<script type='text/javascript'>alert('Email Send Error!');location.href='namecard/staff/queryStaffCard.jsp';</script>");
				}
				out.flush();
				out.close();
			}
 
	}

}
