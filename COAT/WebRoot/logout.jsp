<%@ page language="java" import="java.util.*,org.apache.log4j.Logger;" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<%

   	Logger log=Logger.getLogger("logou.jsp");
   	log.info("用户"+request.getRemoteAddr()+"/"+(session.getAttribute("adminUsername")==""?session.getAttribute("convoy_username"):session.getAttribute("adminUsername"))+"正常退出登录!");

   	   session.invalidate();
   out.print("<script>top.location.href='signin.jsp';</script>"); 
  
%>
