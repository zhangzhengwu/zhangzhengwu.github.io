package com.coat.interceptor;
import java.io.*; 

import java.util.*; 

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*; 

import org.apache.commons.lang.StringEscapeUtils;

import com.coat.common.dao.CommonDao;
import com.coat.common.dao.OperationRecordDao;
import com.coat.common.dao.impl.CommonDaoImpl;
import com.coat.common.dao.impl.OperationRecordDaoImpl;
import com.coat.operationrecord.entity.OperationRecord;


import util.DateUtils;
import util.Util;



//监听登录的整个过程 

public class SessionListener implements Filter 
{

	public void destroy() {
		// TODO Auto-generated method stub

	}
	List<String> list=new ArrayList<String>();//排除无需记录的页面
	List<String> nologinList=new ArrayList<String>();//排除无需记录的页面
	List<String> servletlist=new ArrayList<String>();//排除无需记录的Servlet
	String nowdate="";
	List<Object[]> url_modular=null;
	OperationRecordDao or=null;

	public void doFilter(ServletRequest arg0, ServletResponse arg1,  
			FilterChain arg2) throws IOException, ServletException {  
		boolean flag=false;
		HttpServletResponse response = (HttpServletResponse) arg1;    
		HttpServletRequest request=(HttpServletRequest)arg0;  
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();    
		String username =  session.getAttribute("adminUsername")+"";//  
		String url=request.getRequestURI();
		if(url.indexOf(";")>-1){
			url=url.substring(0,url.indexOf(";"));
		}
		//System.out.println(request.getHeader("Referer")); 
		if(Util.objIsNULL(username) && Util.objIsNULL(session.getAttribute("convoy_username"))){  
			//判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转  
			if(!Util.objIsNULL(url) &&  Util.objIsNULL(request.getParameter("msg")) )  
			{
				if(filters(nologinList, url)){

				}else{
					
					System.out.println("登录信息丢失----------"+url+"("+request.getContextPath()+")  IP "+Util.getIpAddr(request)+"adminUsername: "+username+" convoy_username: "+session.getAttribute("convoy_username"));
					PrintWriter out = response.getWriter();
					String requestType = request.getHeader("X-Requested-With");
					if(Util.objIsNULL(requestType)){
						out.print("<script>alert('未登录系统!');top.location.href='"+request.getContextPath()+"/signin.jsp';</script>");
					}else{
						out.print("timeout");
					}
					out.flush();
					out.close();
					return ;
				}
			}  

		}else{//后期进行记录用户访问模块内容
			if(url.indexOf(".")>-1){
				if(url.endsWith(".jsp")&&!filters(list, url)){
					if(Util.getProValue("public.operation.logging.level").equals("1")){
						flag=true;
						//save( url, username, session, request);
					}
				}
			}else{
				String servletname=request.getServletPath().replace("/", "");
				if(url.endsWith("Servlet")&&!filters(servletlist, url)){
					if(Util.getProValue("public.operation.logging.level").equals("1")){
						flag=true;
						//save( url, username, session, request);
					}else if(Util.getProValue("public.operation.logging.level").equals("2")){
						if(!"select".equalsIgnoreCase(request.getParameter("method"))&&!servletname.startsWith("Query")&&!servletname.startsWith("Select")&&!servletname.startsWith("Vail")){
							flag=true;
							//save( url, username, session, request);
						}
					}else{
						flag=false;
					}
				}else{
					flag=false;
				}
			}
		}
		JSONPResponseWrapper jsonsprw = new JSONPResponseWrapper((HttpServletResponse) arg1);
		//已通过验证，用户访问继续  
		arg2.doFilter(arg0, jsonsprw);  
		ServletOutputStream out = null;
		try{
			byte[] b=jsonsprw.getResponseData();
			//arg2.doFilter(arg0, arg1);  
			if(flag==true){
				String returnValue="返回的是一个页面或者是文件下载";
				//if(!"0".equalsIgnoreCase(jsonsprw.getReturnType())){
				//if(!"0".equalsIgnoreCase(jsonsprw.getReturnType())){
					returnValue=new String(b,response.getCharacterEncoding());
				//}
					if(returnValue.indexOf("<html>")>-1 && returnValue.indexOf("</html>")>-1){
						returnValue=Util.getMsgJosnObject("page", "返回的是一个页面或者是文件下载",returnValue);
					}else if(returnValue.indexOf("{")<0 && returnValue.indexOf("[")<0){
						returnValue=Util.getMsgJosnObject("success","真实数据不是JSON字段.data才是真实的数据", returnValue);
					}
					
				//save( url, username, session, request,returnValue.replaceAll("\\\\r","").replaceAll("\\\\n",""),jsonsprw.getStatusCode()+"");
					save( url, username, session, request,returnValue,jsonsprw.getStatusCode()+"");
					
				//System.out.println(response.getContentType()+"--------"+jsonsprw.getReturnType()+"---"+returnValue);
			}
			response.setContentLength(b.length);  
			out=response.getOutputStream();
			out.write(b);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("写入输出流异常,有可能是用户取消用户下载等原因["+e.getMessage()+"]");
		}finally{
			if(!Util.objIsNULL(out)){
				try{
					out.flush();  
					out.close();
				}catch (Exception e) {
					System.out.println("关闭输出流异常["+e.getMessage()+"]");
				}
			}
		}


	}

	/**
	 * 过滤不需要记录的链接
	 * @author kingxu
	 * @date 2015-9-14
	 * @param list
	 * @param val
	 * @return
	 * @return boolean
	 */
	public static boolean filters(List<String> list,String val){
		boolean flag=false;
		for(int i=0;i<list.size();i++){
			if(val.toLowerCase().endsWith(list.get(i).toLowerCase())){
				flag=true;
				break;
			}
		}
		return flag;
	}
	public void init(FilterConfig arg0) throws ServletException {
		list.add("menu.jsp");
		list.add("desk.jsp");
		list.add("top.jsp");
		list.add("login.jsp");
		list.add("operationrecord_detail.jsp");
		list.add(".css");
		list.add(".png");
		list.add(".js");
		list.add(".jpg");
		list.add(".gif");
		list.add(".eot");
		list.add(".svg");
		list.add(".ttf");
		list.add(".woff");
		list.add(".otf");

		servletlist.add("QueryStaffPositionServlet");
		servletlist.add("QueryLocationServlet");
		servletlist.add("QueryProfessionalServlet");
		servletlist.add("QueryStaffProfessionalServlet");
		servletlist.add("QueryFileNameServlet");
		servletlist.add("QueryPositionServlet");
		servletlist.add("CheckLoginNameServlet");
		servletlist.add("common/CommonReaderServlet");
		servletlist.add("operationrecord/OperationRecordServlet");
		servletlist.add("loginrecord/LoginRecordServlet");
		servletlist.add("NoticeServlet");




		//nologinList.add("UpEmapServlet");

		
		nologinList.add("Login"); 
		nologinList.add("login.jsp");
		nologinList.add("index2.jsp");
		nologinList.add("signin.jsp");
		nologinList.add("UserLoginServlet");
		nologinList.add("top.jsp"); 
		nologinList.add("officeAdmin.jsp");
		nologinList.add("officeAdmin_main.jsp"); 
		nologinList.add("error.jsp"); 
		nologinList.add("QueryMedical_Consultant.jsp");
		nologinList.add("namecard/addNameCard.jsp"); 
		nologinList.add("namecard/queryNameCard.jsp"); 
		nologinList.add("namecard/addMedical_Consultant.jsp");
		nologinList.add("namecard/staff");
		nologinList.add("Request.jsp"); 
		nologinList.add("QueryMissingPaymentServlet");
		nologinList.add("CheckLoginNameServlet");
		nologinList.add("CheckLoginServlet");
		nologinList.add("getVerifyCodeServlet");
		nologinList.add("LoginoutServlet");
		nologinList.add("convoyadmin");
		nologinList.add("common/CommonReaderServlet");
		nologinList.add("pickuprecord/index.jsp");
		nologinList.add("PickUpRecordServlet");
		nologinList.addAll(Arrays.asList(Util.getProValue("system.nologin.list").split(",")));
	} 
	public void save(String url,String username,HttpSession session,HttpServletRequest request,String... result ){

		try{
			if(Util.objIsNULL(or)){
				or=new OperationRecordDaoImpl();
			}


			OperationRecord record=new OperationRecord();
			//System.out.println(request.getRequestURL());
			record.setUsername((Util.objIsNULL(username)?session.getAttribute("convoy_username"):username)+"");
			record.setIpaddress(Util.getIpAddr(request));
			record.setDate(DateUtils.getNowDateTime());
			record.setStatus("Y");
			record.setResult(result[1]);
			record.setModular("");
			record.setOperation("");
			record.setHttpaddress(request.getRequestURL().toString());
			String servletname=request.getServletPath().replace("/", "");
			record.setModular(getModular(url));
			if(url.endsWith(".jsp")){
				record.setOperation("visit");
				record.setModular("Page Visit");
			}else if("select".equalsIgnoreCase(request.getParameter("method"))||servletname.startsWith("Query")||servletname.startsWith("Select")||servletname.startsWith("StaffNoServlet")){
				record.setOperation("search");
			}else if(servletname.startsWith("Vail")||servletname.startsWith("Vial")){
				record.setOperation("Vail");
			}else if("down".equalsIgnoreCase(request.getParameter("method"))||servletname.startsWith("Down")){
				record.setOperation("Download");
			}else if(servletname.startsWith("Up")){
				record.setOperation("Upload");
			}

			record.setRemark1(Util.getRequestParameter(request));
			if(result.length>0)
				record.setRemark2(result[0]);
			//	 System.out.println("name=" + name+"---"+url+"-----------------"+request.getParameter("method"));
			or.saveRecord(record);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getModular(String url){
		String modular="";
		try{
			if(!nowdate.equalsIgnoreCase(DateUtils.getDateToday())){
				//System.out.println(nowdate+"---"+DateUtils.getDateToday());
				CommonDao commonDao=new CommonDaoImpl();
				url_modular=commonDao.findUrlMappingModular();
				nowdate=DateUtils.getDateToday();
			}
			if(!Util.objIsNULL(url_modular)){
				for(int i=0;i<url_modular.size();i++){
					if(url.toUpperCase().indexOf((url_modular.get(i)[0]+"").toUpperCase())>-1){
						modular=url_modular.get(i)[1]+"";
						i=url_modular.size();
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return modular;
	}

}

