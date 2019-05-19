package servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import dao.AdminDAO;
import dao.EclubDao;
import dao.impl.EclubDaoImpl;

import servlet.request.AccessCardServlet;
import util.Constant;
import util.Util;

public class PermissionServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AccessCardServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=null;
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		String originalcode="";
		try{
			  originalcode=(String) request.getSession().getAttribute("originalcode");
			 if(method.equalsIgnoreCase("authorization")){
				 authorization(request,response);
			 }else {
					throw new Exception("Unauthorized access!");
				}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("PermissionServlet==>"+method+"====>操作人====>"+originalcode+"=====操作异常：空值=="+e);
			out=response.getWriter();
			out.print("Submit data anomalies, please refresh retry!");
		}finally{
			if(!Util.objIsNULL(out)){
				out.flush();
				out.close();
			}
		}
	
	}
	
	
	 void authorization(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 PrintWriter out=null;
		 try{
			 HttpSession session=request.getSession(true);//
			 String role=request.getParameter("role");
			 
			 String username=request.getParameter("usernames");
			 String userType="";
			 String grade="";
			 EclubDao ed=new EclubDaoImpl();
			 if("Consultant".equalsIgnoreCase(role)){
				username=Util.objIsNULL(username)?"FC0058":username;
				 userType="Conslultant";
				 session.setAttribute("roleType",Constant.RoleType_Consultant);
			 }else if("Staff".equalsIgnoreCase(role)){
				 username=Util.objIsNULL(username)?"EW0104":username;
				 userType="Staff";
				 session.setAttribute("roleType",Constant.RoleType_staff);
			 }else if("Dept Head".equalsIgnoreCase(role)){
				 username=Util.objIsNULL(username)?"DL0111":username;
				 userType="Staff";
				session.setAttribute("roleType",Constant.RoleType_depthead);
			 }else if("HR".equalsIgnoreCase(role)){
				 username=Util.objIsNULL(username)?"YT6970":username;
				 userType="Staff";
					session.setAttribute("roleType",Constant.RoleType_hr);
			 }else if("HKADM".equalsIgnoreCase(role)){
				 username=Util.objIsNULL(username)?"MC0321":username;
				 userType="Staff";
				 session.setAttribute("roleType",Constant.RoleType_HKADM);
			 }else{
				 throw new Exception("Unauthorized access!"); 
			 }
			 grade=new AdminDAO().findGrade(username);
			out=response.getWriter();
			 if(!Util.objIsNULL(username)){
			 	session.setAttribute("Econvoy",username+"-"+username+"-"+"CD"+"-"+"@convoy"+"-"+ed.sfClub(username)+"-"+grade+"-"+"CD");
				session.setAttribute("convoy_userType", userType);
				session.setAttribute("convoy_username", username);
				session.setAttribute("adminUsername", username);
				out.print("success");
			 }else{
				 out.print("error");
			 }
		 }catch (Exception e) {
			 throw new Exception(e.getMessage());
		}finally{
			if(!Util.objIsNULL(out)){
				out.flush();
				out.close();
			}
		}
	 }
	
	

}
