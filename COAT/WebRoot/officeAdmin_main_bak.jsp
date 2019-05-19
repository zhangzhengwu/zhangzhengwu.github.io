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
html{
overflow:visible;
}
*{
font-size:14px;
font-family: 'Arial';
}
body{
	top: 0px;
	left:0px;
	width:100%;
	height:100%;
	overflow: hidden;
	background-color: #004A81;

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
	display:none;
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
	height:25 px;
	z-index:1;
	top:0px;
	left:0px;
	position: absolute;
	font-size: 16px;
	background-image:url("css/officeAdmin-menu-bar.gif");

}
#left_div{
	position: absolute;
	width: 210px;
	height:96%;
	left: 0px;
	top: 25px; 	
	z-index:1;
	border-bottom-width:1px;
	border-bottom-color: #999;
	border-bottom-style: solid;
	font-family: 'Arial';
}
 
#right_div{
	position: absolute;
	left:210px;
	top: 25px;
	width:87%;
	height:99%;
	z-index:1;
	border-left-width:1px;
	border-left-color: #999;
	border-left-style: solid;
	text-align: justify;
	border-bottom-width:1px;
	border-bottom-color: #999;
	border-bottom-style: solid;
	font-size: 12 px;
	background-image:url("css/officeAdmin_bg.gif");
	overflow:hidden; 
}
td a{
	text-decoration: none;
	color:#FFFFF2;
}
td a:hover{
	color:#6495ed;
}
td a:ACTIVE {
	color:#6495ed;
}
td strong{
}
 tr td{
	color: #FFFF3B;

}
#left_div table{
	width:100%;
}



</style>

  <script type="text/javascript">
  
  var staffcode="${convoy_username}";
   var econvoy="${Econvoy}".split("-");
  function resize(){
   $("#left_div,#right_div").css("top",$("#head_div").height());
   $("#right_div").css("left",$("#left_div").width());
   $("#head_div,#login_black").width($("body").width());
   $("#right_div,#page_frame").width($("#head_div").width()- $("#left_div").width());
   $("#left_div,#right_div").height($(window).height()-$("#head_div").height()-1);
  
  }
  $(function(){
	   window.onresize=resize;
	  if("${convoy_userType}"=="Staff"){
		  $(".Consultant").remove();
		  	//控制只有某些部门的staff可以访问Opt Out菜单
			 if(econvoy[2]!="ADM" && econvoy[2]!="LCD" && econvoy[2]!="CSD" && econvoy[2]!="BPD" && econvoy[2]!="SSC"&& econvoy[2]!="CED"){
					  $("#optList").remove();
				  }
				//控制只有某些部门的staff可以访问ReturnMail菜单
			  if(econvoy[2]!="ADM" && econvoy[2]!="BPD" && econvoy[2]!="CSD" && econvoy[2]!="SSC" && econvoy[2]!="CED"){
				  	$("#returnmailList").remove();
			  }
				//控制只有某些Staff可以进行Return Mail 的录入.
			if( staffcode!='CK7336' && staffcode!="FK0076" && staffcode!="AY6459" && staffcode!="MA0321" && staffcode!="CK0101" && staffcode!="RC0106" && staffcode!="ML0476" && staffcode!="JT2421" && staffcode!="YC5151" && staffcode!="LK3480"){
				$("#return_mail_staff").remove();
			}
				//HR Namecard 维护模块
			if(staffcode=="PL5988" ||staffcode=="KN6840" ||staffcode=="YT6970"){
				$("#staff_namecard").remove();
			}else{
				$("#hr_namecard").remove();
			}
			/**
			 * 验证部门主管
			 * */
		$.ajax({
			url:"VailDepartHeadServlet",
			type:"post",
			data:null,
			success:function(date){
			if(date=="true"){	 
				$("#depart_span").show();
			}else{
				$("#depart_span").remove();
			}
			},error:function(){
				alert(" Network connection is failed, please try later...");//网络连接失败
		 		document.location="error.jsp";
				return false;
			}
		});	
			
			
	  }else{
		   $(".Staff").remove();
	  }
	 if(econvoy[5]=="PA"){
		 $("#cons").remove();
		 $("#staff").show();
	 }
	
  /***page init******/
  
	$("#head_div,#login_black").css("width",$("body").width());
	$("#right_div,#page_frame").width($("#head_div").width()- $("#left_div").width());
	$("#left_div,#right_div").css("top",$("#head_div").height());
	$("#left_div,#right_div").height($(window).height()-$("#head_div").height());
   $("#loginUser").empty().append("Login User : "+"${convoy_username}");
	  checkname();
	  
 
	  
	  
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
		  }         
	  var returnValue = paraObj[paras.toLowerCase()];       
		  if(typeof(returnValue)=="undefined"){      
			  return "";    
		  }else{       
			  return returnValue;      
			  }   
		  }
   function checkname(){
	  $.ajax({
		 url:"CheckLoginNameServlet",
		 type:"post",
		 data:{"staffcode":"${convoy_username}"},
		 success:function(date){
			 if(date=="error"){
				 alert("timeout");
				  document.location="error.jsp";
			 }
		 },error:function(){
		 		alert(" Network connection is failed, please try later...");//网络连接失败
		 		document.location="error.jsp";
				return false;
		 }
	  });
	  window.setTimeout("checkname();", 6000);
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
     
     function menu_click(link_menu){
    	 if(staffcode==null){
    		 return false;
    	 }else{
    		 if( $('#page_frame').attr('src').toUpperCase().indexOf("/ADD")>0){
    			 if(confirm("Confirm to exit?")){
    				  $('#page_frame').attr('src',link_menu);
    			 }else{
    				 return false;
    			 }
    		 }else{
    		 $('#page_frame').attr('src',link_menu);
    		 }
    	 }
     }
     
     function openlinkWin(){
    	 var link="http://10.30.1.63:8888/ConvoyUtil/Return_Mail/ReturnMailScanning.jsp?user=${convoy_username}";
    			var win=window.open(link,"openlinkWin","toolbar=no,location=no,directories=no,menubar=no, scrollbars=no,resizable=yes,status=no,top=0,left=0"); 
		//var win=window.open(link,"openlinkWin","fullscreen,location=yes,resizable=yes,scrollbars=yes");  
     }
     
     
  </script>
  </head>
  <body>
	 <div id="show_all">
	
		   <div id="head_div" >
			   <table width="100%" >
				   <tr>
					   <td width="33%" align="left"><a style="color: white;font-size: 16px;" href="javascript:void(0);" onClick="menu_click('officeAdmin_index.jsp');"><strong>HOME</strong></a></td>
					   <td width="33%" align="center"> <strong style="color:yellow;font-size: 16px;" >Convoy Office Admin Tools(COAT)</strong></td>
					   <td  width="34%" align="right" ><strong><span id="loginUser" style="color:white;font-size: 16px;"></span></strong></td>
				   </tr>
			   </table>
		  </div>
		   <div id="left_div"   >
				<table  class="Consultant" width="200" id="cons" >
					 <tr onClick="javascript:$('#medical_cons_table').fadeToggle('fast','linear');">
						 <td colspan="2"><strong >Medical Claim</strong></td>
					 </tr>
					 <tr id="medical_cons_table"  ><td></td>
						 <td>
						&nbsp;&nbsp;<%--<a  href="javascript:void(0);" >New Medical Claim</a><br>
						&nbsp;--%><a href="javascript:void(0);" onClick="menu_click('MedicalClaim_Consultant/QueryMedical_Consultant.jsp');"> Medical Claim Query</a>
						 </td>
					 </tr>
				</table>
				<table  class="Staff" id="staff">
					 <tr onClick="javascript:$('#medical_staff_table').fadeToggle('fast','linear');">
						 <td colspan="2" ><strong >Staff Medical Claim</strong></td>
					 </tr>
					 <tr id="medical_staff_table"  ><td></td>
						 <td >
						&nbsp;&nbsp;<%--<a  href="javascript:void(0);" >New Medical Claim</a><br>
						&nbsp;--%><a href="javascript:void(0);" onClick="menu_click('MedicalClaim_Consultant/QueryMedical_Staff.jsp');"> Medical Claim Query</a>
						 </td>
					 </tr>
				</table>
				
				
				<table  class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#namecard_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong  >Name Card</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="namecard_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('namecard/addNameCard.jsp');" > Name Card Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/queryNameCard.jsp');"> Name Card Query</a>
						 </td>
					 </tr>
				</table>
				<table  class="Staff" id="staff_namecard"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#staff_namecard_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong  >Staff Name Card</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="staff_namecard_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('namecard/staff/addStaffCard.jsp');" > Name Card Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/staff/queryStaffCard.jsp');"> Name Card Query</a>
						<span id="depart_span" style="display: none;"><br/>&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/departhead/approveStaffCard.jsp');"> Name Card Approve</a></span>
						 </td>
					 </tr>
				</table>
			<table  class="Staff" id="hr_namecard"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#staff_hrnamecard_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong  >HR Name Card</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="staff_hrnamecard_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('namecard/hr/addStaffCard.jsp');" > Name Card Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/hr/queryStaffCard.jsp');"> Name Card Query</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/hr/approveStaffCard.jsp');"> Name Card Approve</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/hr/departHead.jsp');"> Department Head</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/hr/staff_title_list.jsp');"> Staff Position Title</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('namecard/hr/department.jsp');"> Staff Department</a><br>
						 </td>
					 </tr>
				</table>	
				
				
				
				
				
				
				<table  class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#Stationery_cons_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Stationery Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Stationery_cons_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('COAT_Order/addStationery.jsp');" > Stationery Form Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('COAT_Order/QueryStationeryOrder.jsp');"> Stationery Form Query</a>
						 </td>
					 </tr>
				</table>
				<table  class="Staff"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#Stationery_Staff_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Staff Stationery Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Stationery_Staff_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('COAT_Order/addStationery_staff.jsp');" > Stationery Form Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('COAT_Order/QueryStationeryStaffOrder.jsp');"> Stationery Form Query</a>
						 </td>
					 </tr>
				</table>
				
				<table width="100%" class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#marketing_premium_cons_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Marketing Premium Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="marketing_premium_cons_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('COAT_Order/addMarketingPremium.jsp');" > Marketing Premium Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('COAT_Order/QueryMarkPremiumOrder.jsp');"> Marketing Premium Form Query</a>
						 </td>
					 </tr>
				</table>
				
				
				
				<table width="100%" class="Staff"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#marketing_premium_staff_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Staff Marketing Premium Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="marketing_premium_staff_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a  href="javascript:void(0);" onClick="menu_click('COAT_Order/addMarketingPremium_staff.jsp');" > Marketing Premium Request</a><br>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('COAT_Order/QueryMarkPremiumOrder.jsp');"> Marketing Premium Form Query</a>
						 </td>
					 </tr>
				</table>
				
					<table width="100%" class="Staff" id="returnmailList"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#Return_Mail_staff_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Return Mail</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Return_Mail_staff_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						 <span id="return_mail_staff" >&nbsp;&nbsp;<a href="javascript:openlinkWin();" > Return Mail Scanning</a><br/></span>
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('Return_Mail/ScanningRecord_Staff.jsp');"> Return Mail Query</a>
						 </td>
					 </tr>
					 
				</table>
				
				
					
					<table width="100%" class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#Return_Mail_cons_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Return Mail</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Return_Mail_cons_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('Return_Mail/ScanningRecord.jsp');"> Return Mail Query</a>
						 </td>
					 </tr>
					 
				</table>
				
				
								
					<table width="100%" class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#Optout_cons_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Client Opt Out List</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Optout_cons_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('Optout/OptoutList.jsp');"> Existing Client</a>
						 </td>
					 </tr>
				</table>
				
					<table width="100%" class="Staff" id="optList"> <!--  添加主菜单需另外添加table-->
					 <tr onClick="javascript:$('#Optout_staff_table').fadeToggle('fast','linear');">
					 	<td colspan="2"><strong>Client Opt Out List</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Optout_staff_table"  ><td></td>
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						&nbsp;&nbsp;<a href="javascript:void(0);" onClick="menu_click('Optout/OptoutList_Staff.jsp');">Existing Client</a>
						 </td>
					 </tr>
				</table>
				
				
				
				
				
				
				
				
				
				
				
				
				
		   </div>
		   <div id="right_div"  >
				 <iframe id="page_frame" src="officeAdmin_index.jsp" frameborder="0" style="background-image:'css/officeAdmin_bg.gif';" width="100%" height="100%">
				 </iframe>
		   </div>
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		  <div id="login_black" align="center" >
		    <div id="login" >
				  <table width="100%" height="100%">
					  <tr height="28" style="background-image:url(css/officeAdmin-menu-bar.gif); " ><td colspan="3" align="center"><strong style="color:yellow;" >User Login</strong></td></tr>
					  <tr height="40"><td  align="center"  width="80"><strong>Login User:</strong></td><td align="center" colspan="2"><input type="text" id="userlogin" /></td></tr>
					  <tr height="40"><td  align="center"><strong>Password:</strong></td><td align="center" colspan="2"><input type="text" id="password" /></td></tr>
					  <tr height="30"><td colspan="3"><span style="color: red;" id="messages"></span> </td></tr>
					  <tr height="30%"><td width="60"></td><td align="center"><input type="button" value="login" onClick="loginUser();"/>&nbsp;&nbsp;<input type="button" value="Cancel"/></td></tr>
				  </table>
			  </div>
		  </div>
	 </div>
 



  </body>
</html>
