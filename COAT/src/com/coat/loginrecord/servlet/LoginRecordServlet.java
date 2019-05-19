package com.coat.loginrecord.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import util.Help;
import util.Pager;
import util.Util;

import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.dao.impl.LoginRecordDaoImpl;
import com.coat.loginrecord.entity.LoginRecord;

/**
 * 登录/退出记录 selvet
 * 2015-09-06
 * @author orlandozhang
 *
 */
public class LoginRecordServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(LoginRecordServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			if(method.equalsIgnoreCase("select")){
				select(request,response);
			}else if(method.equalsIgnoreCase("usage")){
				result=loginUsage(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
			
		} catch (NullPointerException e) {
			log.error("AccessCard==>"+method+"操作异常：空值=="+e);
			result=Util.getMsgJosnObject("exception","Submit data anomalies, please refresh retry!");
		} catch (Exception e) {
			log.error("AccessCard==>"+method+"操作异常："+e);
			result=Util.joinException(e);
			
		} finally {
			if(!Util.objIsNULL(result)){
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		}
	}


    void select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate  = request.getParameter("endDate");
		String username  = request.getParameter("username");
		String platform  = request.getParameter("platform");
		String ipaddress  = request.getParameter("ipaddress");
		String status  = request.getParameter("status");
		PrintWriter out = response.getWriter();
		LoginRecordDao loginRecordDao=new LoginRecordDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=loginRecordDao.findPager(null, page,Util.objIsNULL(startDate)?"1900-01-01":startDate,Util.objIsNULL(endDate)?"2099-12-31":endDate,Util.objIsNULL(username)?"%%":username,Util.objIsNULL(platform)?"%%":platform,Util.objIsNULL(ipaddress)?"%%":ipaddress,Util.objIsNULL(status)?"%%":status);
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		}
    }
    
    String loginUsage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result="";
    	try{
    		String username=""+(request.getSession().getAttribute("adminUsername"));
    		LoginRecordDao loginRecordDao=new LoginRecordDaoImpl();
    		Double[] num=loginRecordDao.loginUsageByusername(username);
    		result=JSONArray.fromObject(num).toString();
    		result=Util.getMsgJosnObject("success",result);
    		
    	}catch (Exception e) {
    		throw e;
    	}
    	return result; 
    }
    
    
    
}
