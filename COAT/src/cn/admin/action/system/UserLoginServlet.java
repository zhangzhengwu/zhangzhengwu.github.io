package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.coat.consultant.dao.ConsultantListDao;
import com.coat.consultant.dao.impl.ConsultantListDaoImpl;
import com.coat.consultant.entity.ConsultantList;
import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.dao.impl.LoginRecordDaoImpl;
import com.coat.loginrecord.entity.LoginRecord;

import util.Constant;
import util.DateUtils;
import util.Util;
import cn.admin.dao.impl.system.SUserDaoImpl;
import cn.admin.dao.system.SUserDao;
import cn.admin.util.ADAuth;
import dao.AdminDAO;
import dao.DepartMentDao;
import dao.EclubDao;
import dao.impl.DepartMentDaoImpl;
import dao.impl.EclubDaoImpl;
import dao.impl.staffnamecard.StaffNameCardDaoImpl;
import dao.staffnamecard.StaffNameCardDao;
import entity.RequestStaffCompanyView;

public class UserLoginServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(UserLoginServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			HttpSession session = request.getSession();
			/* 首先取得jsp页面传来的参数信息 用户名，密码，验证码 */
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String errMessage = "";
			/* 验证输入信息的完整行和正确性 */
			if (Util.objIsNULL(username)){
				errMessage += "用户名不能为空!";
			}else if (Util.objIsNULL(password)){
				errMessage += "密码输入不能为空!";
			}else if (password.indexOf("'") != -1) {
				errMessage += "请不要进行sql注入攻击!";
				log.error(username + "对系统进行过SQL注入操作！");
			}
			Map<String,Object> map=null;
			/* 如果验证没有通过转到登陆页并提示错误信息 */
			if (Util.objIsNULL(errMessage)) {
				SUserDao sUserDao = new SUserDaoImpl();
				map=sUserDao.checklogin(username, password);
				if(!Util.objIsNULL(map)){
					session.setAttribute("loginUser", map);
					session.setAttribute("adminUsername",map.get("loginname"));
					session.setAttribute("convoy_username", map.get("loginname"));

					/*************************************获取公司 Start**********************************************/
					List<RequestStaffCompanyView> list=new ArrayList<RequestStaffCompanyView>();
					StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
					list=staffDao.getCompany();
					if(list.size()>0){
						session.setAttribute("getCompany", list);
					}
					/*************************************获取公司 End**********************************************/
					
					
					LoginRecord loginRecord=new LoginRecord(map.get("loginname")+"",
							"Admin",
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
				}else{
					Map<String,String> person=ADAuth.vailADAuth(username, password);
					if(Util.objIsNULL(person)||person.size()<1){
						errMessage+="用户名或密码错误!!!";
					}else{

						/*************************************获取公司 Start**********************************************/
						List<RequestStaffCompanyView> list=new ArrayList<RequestStaffCompanyView>();
						StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
						list=staffDao.getCompany();
						if(list.size()>0){
							session.setAttribute("getCompany", list);
						}
						session.setAttribute("namecardComanyList", JSONArray.fromObject(list).toString());
						/*************************************获取公司 End**********************************************/
						
						
						ConsultantListDao clDao=new ConsultantListDaoImpl();
						ConsultantList cl=clDao.findConsultantByCode(person.get("staffCode")+"");
						EclubDao ed=new EclubDaoImpl();
						String userType="";
						String staffcode=person.get("staffCode")+"";
						String grade="";
						String originalcode=person.get("staffCode");
						if(!Util.objIsNULL(cl)){//顾问
							userType="Consultant";
							grade=cl.getGrade();
							staffcode=cl.getEmployeeId();
						}else{//Staff
							userType="Staff";
							grade=new AdminDAO().findGrade(staffcode);
					
						}
						if (staffcode.toUpperCase().substring(0, 3).equals("BOC")){
							// username="ML0476";
							 userType="Staff";
						 }	
						if (staffcode.toUpperCase().equals("BOC105")){
							staffcode="AC2215";
							userType="Consultant";
						}
						if (staffcode.toUpperCase().equals("BOC075")){
							staffcode="FC0058";
							userType="Staff";
						}
						 if (staffcode.toUpperCase().equals("BOC106")){
							 staffcode="DL0111";
							userType="Staff";
						}
						
						
						 session.setAttribute("originalcode", originalcode);
						System.out.println("user Role"+userType);
						session.setAttribute("convoy_userType", userType);
						//	session.setAttribute("EMAIL_ADDR", usernames.get(3));
							session.setAttribute("Econvoy",staffcode+"-"+person.get("username")+"-unkown depart-"+"@convoy"+"-"+ed.sfClub(username)+"-"+grade+"-unkown depart");
							session.setAttribute("convoy_username", staffcode);
							session.setAttribute("adminUsername", staffcode);
							session.setAttribute("roleType",userType.equalsIgnoreCase("Consultant")?Constant.RoleType_Consultant:judgeStaffType(username));

						/*将用户登录信息保存至loginrecord表中*/
						LoginRecord loginRecord=new LoginRecord(staffcode+"-"+person.get("staffCode"),
								"econvoy-Admin",
								"login",
								Util.getIpAddr(request),
								DateUtils.getNowDateTime(),
								"Y");
						LoginRecordDao loginRecordDao=new LoginRecordDaoImpl();
						int rrr = loginRecordDao.saveLogin(loginRecord);
						if(rrr > 0){
							log.info("用户"+Util.getIpAddr(request)+"/"+staffcode+"保存记录成功");
						} else {
							log.info("用户"+Util.getIpAddr(request)+"/"+staffcode+"保存记录失败");
						}
						log.info("获取Econvoy staff code:"+staffcode);
						response.sendRedirect("officeAdmin_main.jsp");
						//request.getRequestDispatcher("officeAdmin_main.jsp").forward(request, response);
						return;
						
					}
					
					
					
					
					
				}
				
				
			}else{
				System.out.println(username+"---"+errMessage);
			}
 
			
			if(Util.objIsNULL(errMessage)){
				request.getRequestDispatcher("WEB-INF/root/system/main.jsp").forward(request, response);
				
			}else{
				request.setAttribute("errMessage", errMessage);
				request.getRequestDispatcher("signin.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("errMessage", e.getMessage());
			try {
				request.getRequestDispatcher("signin.jsp").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.error("User Logining Exception :" + e.getMessage());
		} finally {
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
	
}
