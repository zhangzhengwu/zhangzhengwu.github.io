package servlet.MissPayment;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.axis.client.Service;
import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;

import sun.util.logging.resources.logging;
import util.DBManager;
import util.DateUtils;
import util.Util;

import com.test.webservice.client.GetUserAccessRightResponseGetUserAccessRightResult;
import com.test.webservice.client.UserAccessRightService;
import com.test.webservice.client.UserAccessRightServiceLocator;
import com.test.webservice.client.UserAccessRightServiceSoap_BindingStub;
import com.test.webservice.client.UserAccessRightServiceSoap_PortType;

import dao.AdminDAO;
import dao.EclubDao;
import dao.impl.EclubDaoImpl;
import entity.Econvoy;



public class GetMissPaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(GetMissPaymentServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(" You no Access!");
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String msg=request.getParameter("msg");
		List<String> usernames=null;
		String username="";
		String result="";
		 try{
			 HttpSession session=request.getSession(true);//
			 usernames=findusers(msg);
			if (!Util.objIsNULL(usernames)) {
			
			 username=usernames.get(0);
		

			if(usernames.get(1).equals("CD") || usernames.get(1).equals("Consultancy Division")){
				result="Consultant";//Consultant
			}else{
				result="Staff";//Staff
			}
			if (usernames.get(0).toUpperCase().substring(0, 3).equals("BOC")){
				 username="BK5140";
				 result="Staff";
						
			 }
			if (usernames.get(0).toUpperCase().equals("BOC106")){
				username="AC5800";
				result="Consultant";
			}
			/*
			 *2015-2-10 15:39:26   
			 * if(usernames.get(0).toUpperCase().equals("XCRXX6001") ||usernames.get(0).toUpperCase().equals("XCRXX6002") ){
				result="Consultant";
			}*/
			if(usernames.get(0).length()==9){
				result="Consultant";
				//username=usernames.get(0).substring(3);
			}

			session.setAttribute("convoy_username", username);
			log.info("MPR Econvoy staff code:"+username);
			session.setAttribute("uType", result);//储存用户类型
			}else{
				result="error";
			}
		 }catch(Exception e){
			 result="error";
			 e.printStackTrace();
			 log.error(username+" MPR Econvoy ERROR ："+e);
			username=null;
		 }finally{
			 out.print(result); 
			 out.flush();
			 out.close();
		 }
		 log.info(username+"MPR Econvoy result:"+result);
	}
	
/***
 * 
 * @param msg
 * @return
 * @throws MalformedURLException
 * @throws RemoteException
 **/
	public static List<String> findusers(String msg) throws MalformedURLException, RemoteException {
		UserAccessRightService urs=new UserAccessRightServiceLocator();
			List<String> users=new ArrayList<String>();
			GetUserAccessRightResponseGetUserAccessRightResult uars=new GetUserAccessRightResponseGetUserAccessRightResult();
			UserAccessRightServiceSoap_PortType usp=new UserAccessRightServiceSoap_BindingStub(new URL(urs.getUserAccessRightServiceSoapAddress()), new Service());
		try{
			uars=usp.getUserAccessRight(msg, "eip", "convoy");
		//	Object[] objs=uars.get_any();
			MessageElement[] me=uars.get_any();
			//System.out.println((me[0].getChildNodes().item(2).getChildNodes().item(0))); //获取到具体的值
			//for(Object c:objs){
			//String ss=me[0].getChildNodes().item(2).getChildNodes().item(0).toString();
		 	String ss=me[0].getElementsByTagName("username").item(0).getChildNodes().item(0).toString();
		//	 ss=ss.substring(ss.indexOf("<username>")+10, ss.indexOf("</username>"));
				users.add(0,ss.substring(ss.lastIndexOf(" ")+1));
				//users.add(1,me[0].getElementsByTagName("DEPARTMENT_CODE").item(0).getChildNodes().item(0).toString());
				if(me[0].toString().indexOf("<DEPARTMENT_CODE>")>-1){
					users.add(1,me[0].getElementsByTagName("DEPARTMENT_CODE").item(0).getChildNodes().item(0).toString());
				}else{ 
					users.add(1,getDept(users.get(0)));
					//users.add(1,"SSC");
				}
				System.out.print(ss);
				System.out.println("  =====  "+DateUtils.getNowDateTime());
				users.add(2,ss.substring(0,ss.length()-6));
				//users.add(2,ss.substring(ss.lastIndexOf(" ")+1));
				//}
		}catch(Exception e){
			e.printStackTrace();
			Logger.getLogger(" GetStaffNameServlet").error("GetStaffNameServlet ===Method: findusers    出现"+e);
			users =null ;
		}
		return users;
	}
	/***
	 * 2013年5月23日10:17:59
	 * @param msg
	 * @return
	 * @throws MalformedURLException
	 * @throws RemoteException
	 
	public static List<String> findDepartmentusers(String msg) throws MalformedURLException, RemoteException {
		List<String> users=new ArrayList<String>();
		UserAccessRightService urs=new UserAccessRightServiceLocator();
			GetUserAccessRightResponseGetUserAccessRightResult uars=new GetUserAccessRightResponseGetUserAccessRightResult();
			UserAccessRightServiceSoap usp=new UserAccessRightServiceSoapStub(new URL(urs.getUserAccessRightServiceSoapAddress()), new Service());
		uars=usp.getUserAccessRight(msg, "eip", "convoy");
		//	Object[] objs=uars.get_any();
		MessageElement[] me=uars.get_any();
		//System.out.println((me[0].getChildNodes().item(2).getChildNodes().item(0))); //获取到具体的值
		//for(Object c:objs){
		//String ss=me[0].getChildNodes().item(2).getChildNodes().item(0).toString();
		String ss=me[0].getElementsByTagName("staffcode").item(0).getChildNodes().item(0).toString();
		//	 ss=ss.substring(ss.indexOf("<username>")+10, ss.indexOf("</username>"));
		users.add(0,ss);
		users.add(1,me[0].getElementsByTagName("DEPARTMENT_CODE").item(0).getChildNodes().item(0).toString());
		//}
		return users;
	}
	*/
	
	public static String getDept(String staffcode){
		String deptString="";
		Connection connection=null;
		try {
		connection=DBManager.getCon();
		String sql="select deptid from staff_list where staffcode=?";
		PreparedStatement ps=connection.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
				deptString=rs.getString("deptid");
		
		rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return deptString;
	}
	public static void main(String[] args) {
		String a="XCRXX6001";
		if(a.length()==9){
			System.out.println(a.substring(3)+"--");
		}
	}
}
