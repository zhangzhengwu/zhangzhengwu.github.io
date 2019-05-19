<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:choose>
<c:when test="${empty languageType}">
	<fmt:setLocale value="US"/>  
	<fmt:setBundle basename="config.ApplicationResource_en_US"/>  
</c:when>
<c:otherwise>
	<fmt:setLocale value="${languageType}"/>  
	<fmt:setBundle basename="config.ApplicationResource_${language}"/>  
</c:otherwise>
</c:choose>
<META content="IE=edge" http-equiv="X-UA-Compatible">

<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.css">
<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
	<link rel="stylesheet"href="<%=basePath%>/plug/css/font-awesome-ie7.css">
<![endif]--> 
<link rel="stylesheet" href="<%=basePath%>plug/css/layout.css">
<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
<script type="text/javascript" src="<%=basePath%>plug/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plug/js/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>plug/js/Common.js"></script>
<script type="text/javascript" src="<%=basePath%>plug/js/jquery-ajax.js"></script>

<script  type="text/javascript" src="<%=basePath%>css/date.js" ></script>
<script type="text/javascript" src="<%=basePath%>css/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript"  src="<%=basePath%>css/Util.js"></script>

