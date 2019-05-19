package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;

import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.dao.impl.LoginRecordDaoImpl;
import com.coat.loginrecord.entity.LoginRecord;

public class LoginoutServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger("LoginoutServlet");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			LoginRecordDao l =new LoginRecordDaoImpl();
			LoginRecord lr=null;
			HttpSession session=request.getSession();
			String user="";//user name
			String ip=Util.getIpAddr(request);//ip address
			String remark="";
			if(!Util.objIsNULL(session)){
				user=(String) (Util.objIsNULL(session.getAttribute("adminUsername"))?session.getAttribute("convoy_username"):session.getAttribute("adminUsername"));
				session.invalidate();
			}else{
				user=ip;//当session为空是 将IP赋值user
				LoginRecordDao loginDao =new LoginRecordDaoImpl();
				String name = loginDao.select(user);
				if(name != null){
					remark = user;
					user = name;
				}
			}
			lr=new LoginRecord(user,"Admin","logout",ip,DateUtils.getNowDateTime(),"Y",remark);
			int rrr = l.saveLogout(lr);
			if(rrr > 0){
				log.info("用户"+ip+"/"+user+"保存记录成功");
			} else {
				log.info("用户"+ip+"/"+user+"保存记录失败");
			}
			log.info("用户"+ip+"/"+user+"正常退出登录!");
			out.print("<script>top.location.href='login.jsp';</script>");
			
		}catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}



}
