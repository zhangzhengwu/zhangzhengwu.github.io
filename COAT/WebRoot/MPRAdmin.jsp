<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<script src="css/date.js" language="JavaScript"></script>
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script src="css/Util.js" language="JavaScript"></script>
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
#head_div{
width:100%;
height:4%;
z-index:1;
top:0;
left:0;
position: absolute;

background-image:url("css/officeAdmin-menu-bar.gif");
}
#left_div{
width:13%;
height:96%;
position: absolute;
left:0; 
z-index:1;
text-align: justify;
border-bottom-width:1px;
border-bottom-color: #999;
border-bottom-style: solid;
 

}

#right_div{
width:87%;
height:96%;
position: absolute;
z-index:1;
border-left-width:1px;
border-left-color: #999;
border-left-style: solid;
text-align: justify;
border-bottom-width:1px;
border-bottom-color: #999;
border-bottom-style: solid;
  
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
font-family: 'Arial Narrow';
}

</style>

  <script type="text/javascript">

 
  $(function(){
	 
  	  $.ajax({
		 url:"GetMissPaymentServlet",
		 type:"post",
		 data:{"msg":requests("msg")},
		 async:false,
		 success:function(date){
			if(date==null || date=="null" || date==""){
				  location="error.jsp";
				  return false;
			}else if(date=="Staff"){
				location.href="namecard/MissingPayment_Staff.jsp";
			}else{
				location.href="namecard/MissingPayment_Consultant.jsp";
			}
		 },error:function(XMLHttpRequest, textStatus){
			 alert("----"+"---"+textStatus+"---"+XMLHttpRequest.status);
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
	  var paraObj = {}   
	  for (i=0; j=paraString[i]; i++){    
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
   
    
  </script>
  </head>
  <body>
	
  </body>
</html>
