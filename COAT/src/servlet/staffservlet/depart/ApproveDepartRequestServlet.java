package servlet.staffservlet.depart;

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

import dao.QueryStaffRequstDao;
import dao.impl.QueryStaffRequstDaoImpl;
/**
 * 已使用StaffNameCardWriteServlet.deptapproval 替换
 * @author kingxu
 * 2015-8-5 09:36:14
 */
@Deprecated
public class ApproveDepartRequestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ApproveDepartRequestServlet.class);
 
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
			adminUsername=request.getSession().getAttribute("convoy_username").toString();
			String StaffNo=request.getParameter("StaffNos");
			String urgentDate=request.getParameter("UrgentDate");
			String englishName=request.getParameter("EnglishNames");
			String remarkcons=request.getParameter("remarkcons");
			
			QueryStaffRequstDao smd=new QueryStaffRequstDaoImpl();
			int num=-1;
			num=smd.approveRequestDepartConvoy_remark(StaffNo, urgentDate, adminUsername,remarkcons);
			 if(num>0){////判断是否把原始数据保存成功
				 result="Approve Success";
				 
				 
				 HttpURLConnection htp=null;
					String body="Dear User,<br/><br/>"
					+"    The name card request initiated by "+englishName+"  "+StaffNo+" is in HRD approval status."
					+"<br/><br/>"
					+"Please visit and approve at:<br/>"
					+"<a href='http://www.econvoy.com/group/convoy/office-admin'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
					+"Thank You.";
					String parmString="to=yan.tsang@convoy.com.hk;HRD.info@convoy.com.hk&" +
					//"cc=King.Xu@convoy.com.hk&" +
					"subject=Staff name card request approval.&" +
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
							System.out.println(adminUsername+"-"+br.readLine()+"\r\n");
						}
			           htp.disconnect(); 
				 
				 
			 }else{
				 result="Approve Error!";
			 }
			log.info(adminUsername+"在SaveStaffRequestConvoyServlet中Depart 审核Request,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在SaveStaffRequestConvoyServlet中Depart 审核Request："+e);
			result="Approve Exception ："+e.toString();
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

}
