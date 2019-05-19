<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Motify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
 
	<link rel="stylesheet" type="text/css" href="css/style.css" />
 

    <style type="text/css">
<!--
#Layer1 {
	position:absolute;
	width:456px;
	height:176px;
	z-index:1;
	left: 512px;
	top: 123px;
}
.STYLE1 {
	font-size: 16px;
	font-family: "仿宋";
	font-weight: bold;
}
-->
    </style>
	
	 
</head>
<script type="text/javascript">
$(function(){
$("#sub").click(function(){
	if($("#number").val()%100 !=0){
		alert("Additional必須是100的整數倍！");
		$("#number").focus();
		return false;
	}
	else{
		$("#myform").submit();
	}
});
 
$("#quit").click(function(){
window.location.href="queryAdditional.jsp";
});
});
</script>

 
  
  <body >
  <form id="myform" action="SaveAdditionalServlet" method="post" ">
  <div id="Layer1">
    <table width="457" height="185" border="1" class="table-ss">
      <tr>
        <td  align="right"><div align="center"><span class="STYLE1">信息修改</span></div></td>
      </tr>
      <tr>
        <td  align="right"> <div align="left">顾问编号: 
          <input type="text" readonly="readonly" name="StaffNo" value="${sessionScope.additional.staffNo}">
        </div></td>
      </tr>
      <tr>
        <td  align="right"> <div align="left">顾问姓名: 
          <input type="text" name="Name" readonly="readonly" value="${sessionScope.additional.name}">
        </div></td>
      </tr>
      <tr>
        <td  align="right"> <div align="left">添加数量: 
          <input id="number" type="text" name="num" value="${sessionScope.additional.num}">
        </div></td>
      </tr>
      <tr>
        <td  align="right"> <div align="left">&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;&nbsp;因: 
          <input name="Remark" type="text" value="${sessionScope.additional.remark}" size="35">
        </div></td>
      </tr>
      <tr>
        <td  align="right"><div align="left">是否有效:
            <select name="select"> 
			<c:if test="${sessionScope.additional.sfyx=='Y'}">
              <option value="Y" selected="selected">有效</option>
              <option value="N" >无效</option>
              </c:if>
              <c:if test="${sessionScope.additional.sfyx=='N'}">
              <option value="Y" >有效</option>
              <option value="N" selected="selected">无效</option>
              </c:if>
          </select>
        </div></td>
      </tr>
      <tr>
        <td height="25">
          <div align="right">
            <input type="button" name="button" id="sub" value="保存">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <input type="button" name="button" id="quit" value="返回">
        </div></td>
      </tr>
    </table>
  </div>
  </form>
  </body>
</html>
