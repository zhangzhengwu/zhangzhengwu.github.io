<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Success.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
     <style type="text/css">
<!--
#Layer1 {
	position:absolute;
	width:486px;
	height:91px;
		
	z-index:1;
	left: 258px;
	top: 98px;
}
.STYLE2 {
	font-size: 24px;
	font-weight: bold;
	color: #005aa7;
	font-family: "仿宋";
}
.table-si {
	border: 1px solid #999999;
	border-collapse:collapse;
	background-color: #FFFFFF;

}
-->
     </style>

  <script type="text/javascript">
  /**页面加载时触发事件**/
  $(function(){
  var num=5;
  $("#num").val(num).html(num);
  if($.browser.msie){
  setInterval(changes,1000);//每隔一秒执行changeNum()方法一次
  setInterval("window.location.href='addNameCard.jsp'",5000);//4秒后执行跳转（为了和数字相应）
  }
	  function changes(){
	num=num-1;
			 $("#num").val(num).html(num);
	
	  }
  });
  
  </script>
  <body ><center>
   <div id="Layer1" align="center">
    <table border="1" cellpadding="0" cellspacing="0"   bgcolor="#F3F3FA" class="table-ziz" style="top: 1px; height: 116px; left: 1px; width: 474px;">
      <tr>
        <td height="32" colspan="2"><div align="center"><span class="STYLE2">插入數據成功！</span></div></td>
      </tr>
      <tr>
        <td height="29" colspan="2" align="center" class="STYLE2"> <span id="num" style='color:red;'></span>秒後返回添加頁面</td>
      </tr>
      <tr>
        <td width="237" height="26" class="STYLE2"><a href="addNameCard.jsp" >繼續插入數據</a></td>
        <td width="241" class="STYLE2"><a href="queryNameCard.jsp">查詢數據</a></td>
      </tr>
    </table>
  </div></center>
  </body>
</html>
