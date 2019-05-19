package servlet.staffservlet;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONArray;

import org.apache.axis.client.Service;
import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;

import servlet.CheckLoginServlet;
import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.HttpUtil;
import util.Util;

import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.dao.impl.LoginRecordDaoImpl;
import com.coat.loginrecord.entity.LoginRecord;
import com.test.webservice.client.GetUserAccessRightResponseGetUserAccessRightResult;
import com.test.webservice.client.UserAccessRightService;
import com.test.webservice.client.UserAccessRightServiceLocator;
import com.test.webservice.client.UserAccessRightServiceSoap_BindingStub;
import com.test.webservice.client.UserAccessRightServiceSoap_PortType;

import dao.AdminDAO;
import dao.DepartMentDao;
import dao.EclubDao;
import dao.impl.DepartMentDaoImpl;
import dao.impl.EclubDaoImpl;
import dao.impl.staffnamecard.StaffNameCardDaoImpl;
import dao.staffnamecard.StaffNameCardDao;
import entity.RequestStaffCompanyView;



public class GetStaffNameServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static Logger log=Logger.getLogger(GetStaffNameServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String msg=request.getParameter("msg");
		EclubDao ed=new EclubDaoImpl();
		List<String> usernames=null;
		String username="";
		String grade="";
		String result="";
		String userType="";
		String originalcode="";//真实的staffcode
		 try{
			 
			 HttpSession session=request.getSession();//
			 log.info("econvoy 登录COAT,IP: "+Util.getIpAddr(request)+" msg:"+msg);
			 usernames=findusers(msg);
			 log.info("远程身份解析系统返回结果"+usernames);
			 if(!Util.objIsNULL(usernames)){
				 if(usernames.size()>0){
					 username=usernames.get(0); 
					 originalcode=usernames.get(0);//记录原来的staffcode
					 session.setAttribute("originalcode", originalcode);
				 }else{
					 System.out.println("用户数据解析为空---"+msg+"["+usernames+"]"+"originalcode=="+originalcode);
					 log.error("用户数据解析为空---"+msg+"["+usernames+"]");
						result="error";
						return ;
				 }
			 }else{
				 System.out.println("用户数据获取为空---"+msg);
				 log.error("用户数据获取为空---"+msg);
					result="error";
					return ;
			 }
			 
			String s= new AdminDAO().checkStaffcode(originalcode, originalcode);
			if(Util.objIsNULL(s)){
				userType="Consultant";
			}else{
				 userType="Staff";
			}
			 
			/*if(usernames.get(1).equals("CD") || usernames.get(1).equals("Consultancy Division")){
				userType="Consultant";
			}else{
				 userType="Staff";
			}*/
			if (usernames.get(0).toUpperCase().substring(0, 3).equals("BOC")){
				// username="ML0476";
				 userType="Staff";
			 }	
			if (usernames.get(0).toUpperCase().equals("BOC105")){
				username="AC2215";
				userType="Consultant";
			}
			if (usernames.get(0).toUpperCase().equals("BOC075")){
				username="FC0058";
				userType="Staff";
			}
			 if (usernames.get(0).toUpperCase().equals("BOC106")){
				username="AN4754";
				userType="Staff";
			}
			grade=new AdminDAO().findGrade(username);
			if(!Util.objIsNULL(grade)){
				result="success";
			}else{
				System.out.println(username+"---grade为空---"+msg);
				log.error(username+"---grade为空---"+msg);
				result="error";
				return ;
			}
			System.out.println("user Role"+userType);
			session.setAttribute("convoy_userType", userType);
		//	session.setAttribute("EMAIL_ADDR", usernames.get(3));
			session.setAttribute("Econvoy",username+"-"+usernames.get(2).toString()+"-"+usernames.get(1)+"-"+"@convoy"+"-"+ed.sfClub(username)+"-"+grade+"-"+usernames.get(1));
			session.setAttribute("convoy_username", username);
			session.setAttribute("adminUsername", username);
			session.setAttribute("roleType",userType.equalsIgnoreCase("Consultant")?Constant.RoleType_Consultant:judgeStaffType(username));
			/*************************************获取公司 Start**********************************************/
			List<RequestStaffCompanyView> list=new ArrayList<RequestStaffCompanyView>();
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
			list=staffDao.getCompany();
			if(list.size()>0){
				session.setAttribute("getCompany", list);
			}
			session.setAttribute("namecardComanyList", JSONArray.fromObject(list).toString());
			/*************************************获取公司 End**********************************************/
			/*将用户登录信息保存至loginrecord表中*/
			LoginRecord loginRecord=new LoginRecord(username+"-"+originalcode,
					"econvoy",
					"login",
					Util.getIpAddr(request),
					DateUtils.getNowDateTime(),
					"Y");
			LoginRecordDao loginRecordDao=new LoginRecordDaoImpl();
			int rrr = loginRecordDao.saveLogin(loginRecord);
			if(rrr > 0){
				log.info("用户"+Util.getIpAddr(request)+"/"+username+"保存记录成功");
			} else {
				log.info("用户"+Util.getIpAddr(request)+"/"+username+"保存记录失败");
			}
			log.info("获取Econvoy staff code:"+username+" IP:"+Util.getIpAddr(request));
		 }catch(Exception e){
			 result="error";
			 e.printStackTrace();
			 log.error("获取Econvoy Staff code  时出现   ："+e);
			username=null;
		 }finally{
			 out.print(result); 
			 out.flush();
			 out.close();
		 }
	}
	
	/**
	 * 判断Staff 角色类型
	 * @author kingxu
	 * @date 2015-8-12
	 * @param staffcode
	 * @return
	 * @return String
	 */
	public static String judgeStaffType(String staffcode){
		String roleType="";
		try{
			if(Util.getProValue("public.namecar.role.hr").indexOf(staffcode.toUpperCase())>-1){
				roleType=Constant.RoleType_hr;
			}else{
				DepartMentDao dmd=new DepartMentDaoImpl();  
				if(dmd.vailSupervisor(staffcode)){
					roleType=Constant.RoleType_depthead;
				}else{
					roleType=Constant.RoleType_staff;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return roleType;
	}
	
	
/***
 * 获取用户信息
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
			log.info(uars.get_any());
			MessageElement[] me=uars.get_any();
			String ss=me[0].getElementsByTagName("username").item(0).getChildNodes().item(0).toString();
			String staffcode=me[0].getElementsByTagName("staffcode").item(0).getChildNodes().item(0).toString();
				users.add(0,staffcode);
				if(me[0].toString().indexOf("<DEPARTMENT_CODE>")>-1){
					users.add(1,me[0].getElementsByTagName("DEPARTMENT_CODE").item(0).getChildNodes().item(0).toString());
				}else{ 
					String dept=getDept(users.get(0));
					users.add(1,Util.objIsNULL(dept)?getStaffDept(users.get(0)):dept);
				}
				System.out.println(ss+"==="+users.get(1)+"  =====  "+DateUtils.getNowDateTime());
				log.error(ss+"==="+users.get(1)+"  =====  "+DateUtils.getNowDateTime());
				log.error(msg);
				if(me[0].toString().indexOf("<userid>")>-1)
					users.add(2,me[0].getElementsByTagName("userid").item(0).getChildNodes().item(0).toString());
				else {
					users.add(2,"");
				}
		}catch(Exception e){
			//e.printStackTrace();
			log.error("GetStaffNameServlet ===Method: findusers    出现"+e);
			System.out.println("GetStaffNameServlet ===Method: findusers    出现"+e.getMessage());
			users=null;
		}finally{
			
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
	
	public static void main(String[] args) {
		//System.out.println(DateUtils.compare_date("2015-02-28",DateUtils.getDateToday()));
		try {
			System.out.println(findusers("XGM-204615ce56554eb396796916c0477743"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getDept(String staffcode){
		String deptString="";
		Connection connection=null;
		try {
		connection=DBManager.getCon();
		String sql="select EmployeeId from cons_list where EmployeeId=?";
		PreparedStatement ps=connection.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
				deptString=rs.getString("EmployeeId");
		
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
	public static String getStaffDept(String staffcode){
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
	
}
