package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import util.Util;

public class CheckLoginNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(CheckLoginNameServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String Staffcode=request.getParameter("staffcode");
		try{
			// 设置该响应不在缓存中读取
			response.addHeader("Expires", "0");
			response.addHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.addHeader("Pragma", "no-cache");
			HttpSession session=request.getSession(false);
			int num=Integer.parseInt(session.getAttribute("UseracceTime")+"");
		//	System.out.println(num);
			//System.out.println(session.getAttribute("convoy_username"));
			if(num>=300 || Util.objIsNULL(session.getAttribute("convoy_username"))||Util.objIsNULL(session.getAttribute("UseracceTime"))){
				//System.out.println("-------");
				log.info("用户扫描：====未操作系统时间=="+num+"=====convoy_username="+session.getAttribute("convoy_username")+"=========useracceTime======="+session.getAttribute("UseracceTime"));
				session.invalidate();
				out.print("error");
			}else if(num>=295&& num<300){//警告页面
				out.print("warn");
			}else{
				out.print("success");
			}
		}catch(Exception e){
			log.error("用户："+Staffcode+"===在循环获取用户信息时出现："+e);
		}finally{
			out.flush();
			out.close();
		}
	}

}
