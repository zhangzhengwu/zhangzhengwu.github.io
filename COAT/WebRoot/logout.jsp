<%@ page language="java" import="java.util.*,org.apache.log4j.Logger;" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<%

   	Logger log=Logger.getLogger("logou.jsp");
   	log.info("�û�"+request.getRemoteAddr()+"/"+(session.getAttribute("adminUsername")==""?session.getAttribute("convoy_username"):session.getAttribute("adminUsername"))+"�����˳���¼!");

   	   session.invalidate();
   out.print("<script>top.location.href='signin.jsp';</script>"); 
  
%>
