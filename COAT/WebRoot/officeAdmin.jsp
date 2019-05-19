<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <%
  String Referer=request.getHeader("Referer");
  %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>officeAdmin</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<style type="text/css">
body{
overflow: hidden;
background-image:url("css/officeAdmin_bg.gif");
top: 0;
width:100%;
 
}
#login_black{
position: absolute;
width: 100%;
height: 100%;
z-index: 100;
background-color:#999999;
opacity:0.9;
filter: alpha(opacity=90);
top: 0px;
left: 0px;
display: none;
}
#login{
position: absolute;
width: 340px;
height: 180px;
z-index: 100;
border-width:1px;
border-color: #999;
border-style: solid;
background-color:silver;
}

a{
text-decoration: none;
color:#1e90ff;
}
a:hover{
color:#6495ed;
}
a:ACTIVE {
	color:red;
}
body{
font-family: 'Arial';
}

</style>

  <script type="text/javascript">
  var referer="<%=Referer%>";
  var paraString ="";
	if(referer.indexOf("?")>-1){
		paraString ="&"+referer.substring(referer.indexOf("?")+1,referer.length).split("&");  
	}
  $(function(){
  /*******************/
  	  $.ajax({
		 url:"GetStaffNameServlet",
		 type:"post",
		 data:{"msg":requests("msg")},
		 async:false,
		 success:function(date){
			if(date==null || date=="null" ||date=="error" ||date==""){
				  location.href="error.jsp";
				  return false;
			}else{
				//location.href="officeAdmin_main.jsp?ver="+(new Date().getTime())+paraString;
				location.href="officeAdmin_main.jsp";
			}
		 },error:function(XMLHttpRequest, textStatus){
			// alert("----"+"---"+textStatus+"---"+XMLHttpRequest.status);
			alert("Connection Error");
		 }
	  });
  
 
  });
  /***
   * 获取当前页面参数
   * @param {Object} paras
   * @return {TypeName} 
   */
  function requests(paras)    {  
	  var url = location.href;    
	  var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");   
	  var paraObj = {}; 
	  for (var i=0; j=paraString[i]; i++){    
		  paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);     
		  }         var returnValue = paraObj[paras.toLowerCase()];       
		  if(typeof(returnValue)=="undefined"){      
			  return "";    
			  }else{       
				  return returnValue;      
				  }   
		  }
  
   function checkname(){
	  if("${convoy_username}"=="" || "${convoy_username}"==null ){
		  document.location="error.jsp";
	   }  
   }
   
     function loginUser(){
	  $.ajax({
		 url:"ConvoyLoginServlet",
		 type:"post",
		 data:{"username":$("#userlogin").val(),"password":$("#password").val()},
		 success:function(result){
			 if(result!="error"){
				  $("#loginUser").empty().append("Login User : "+"${convoy_username}");
				  $("#login_black").hide();
			  }else{
				  	$("#userlogin,#password").val("");
					$("#messages").empty().append("loginUser or password error!");
					$("#userlogin").focus();
			  }
		 },error:function(){
			$("#userlogin,#password").val("");
			$("#messages").empty().append("loginUser or password error!");
			$("#userlogin").focus();
			return false;	
		 }
	  });
  }
  </script>
  </head>
  <body><div style="position:absolute;top: 200px;left:550px;">
	 <table>
		 <tr>
			 <td></td>
			 <td align="center"><p><img src="css/022.gif" width="32" height="32"></p></td>
			 <td></td>
		 </tr>
		 <tr>
			 <td></td>
			 <td>    <p><span style=" font-family:Arial Narrow;font-size: 18px;"><b>Page loading...</b></span></p></td>
			 <td></td>
		 </tr>
		 
	 </table>
    		

 
	 </div>
  </body>
</html>
