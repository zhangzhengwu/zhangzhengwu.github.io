<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<script src="css/date.js" language="JavaScript"></script>
	<script src="css/Util.js" language="JavaScript"></script>
    <link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 


<style type="text/css">
html{
overflow:visible;
}
*{
font-size:12px;
font-family: 'Arial';
color: #333;
}
a{
	text-decoration: none!important;
}
strong{
	font-weight: lighter;
}
body{
	top: 0px;
	left:0px;
	width:100%;
	height:100%;
	overflow: hidden;
	/**background-color: #004A81;*/
	min-width:1024px;
	background: #F9F9F9;
	border: 1px solid #eee;

}
 
 #topic{
	 position:absolute;
	 width:300px;
	 height:200px;
	 z-index:1000;
	 display:none;
 	background-color:#F5F5F5;
 	border-radius:4px;
 	
 }
 
#head_div{
	width:100%;
	height:26px;
	z-index:1;
	top:0px;
	left:0px;
	position: absolute;
	
	border: 1px solid #ccc;
	border-bottom: 0px;
	
	font-size: 16px;
	/*background-image:url("css/officeAdmin-menu-bar.gif");*/
	background: #0e4d8b;
	
background-repeat: round;
}
#head_div table{width:100%;}
#left_div{
	position: absolute;
	width: 210px;
	height:96%;
	left: 0px;
	top: 26px; 	
	z-index:10;
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	font-family: 'Arial';
	background: #f9f9f9;
}
 
#right_div{
	position: absolute;
	left:210px;
	top: 26px;
	width:87%;
	height:99%;
	z-index:1;
	border: 1px solid #ccc;
	border-top: 0px;
	text-align: justify;
	font-size: 12 px;
	border-right: 1px solid #ccc;
	/*background-image:url("css/officeAdmin_bg.gif");*/
	overflow:hidden; 
}
td a{
	text-decoration: none;
	color:#333;
}

td a:hover{
color: #333;
text-decoration: none;
	/**color:#6495ed;**/
}
td a:ACTIVE {
text-decoration: none;
}
td strong{
}
#show_all tr td{
	color: #333;
	position: relative;
	line-height: 26px;
	padding-left: 15px;
}
#left_div table{
	width:100%;

	border-collapse:inherit;
}
#left_div table tr:first-child {
	
	cursor: pointer;
	padding: 5px 10px;
	font-size: 14px;
	color: #333;
	background: #f4f4f4;
	box-shadow: inset -3px 0px 8px -4px rgba(0, 0, 0, 0.22);
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
	
}
#left_div table tr:first-child td{
	border-top: 1px solid #fff;
	border-bottom: 1px solid #dbdbdb;	
}
#left_div table tr:last-child{
	cursor: pointer;
	
	display:none;
	/*
	border-top: 1px solid #fff;
	border-bottom: 1px solid #dbdbdb;
	*/
	
		
}
#left_div table tr:last-child td{
	padding-left: 0px;
}
#left_div table tr:last-child div{
line-height:28px;
	width:100%;
	padding-left: 20px;
}
#left_div table tr:last-child div:hover{
	background-color:#ddd;
}
div.current{
background-color:#428bca;
}

#tipDiv{
	position: absolute;
	top:0px;
	left:0px;
	float: right;
	z-index: 999;
	overflow-x: hidden;
	overflow-y: hidden;
	width: 100%;
	height: 100%;
	display: none;
	border: 0px solid #D1D1D1;
	/*background-color: rgba(255, 255, 255);*/
	background: #fff;
	/* older safari/Chrome browsers */  
    -webkit-opacity: 0.6;  
    /* Netscape and Older than Firefox 0.9 */  
    -moz-opacity: 0.6;  
    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/  
    -khtml-opacity: 0.6;  
    /* IE9 + etc...modern browsers */  
    opacity: .6;  
    /* IE 4-9 */  
    filter:alpha(opacity=60);  
    /*This works in IE 8 & 9 too*/  
    -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=60)";  
    /*IE4-IE9*/  
    filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=60)!important;  
} /* 遮罩层 */
/* 名片板块 */
.namecard{
	width: 610px;
	height: 362px;
	padding: 15px;
	display: none;
	position: absolute;
	border-radius: 5px;
	background: #fff;
	-webkit-text-size-adjust:none;
	/*background: url('images/bg.png');*/
}
.closeBtn{
	position: absolute; 
	right: -4px;
	top: -5px;
	width: 22px;
	height: 22px;
	text-align: center;
	
}
.closeBtn a:hover{
	background: #0066B6;
	text-decoration: none;
}
.closeBtn a{
	display: block;
	width: 100%;
	height: 100%;
	background: #2290E7;
	cursor: pointer;right: -4px;
top: -5px;
width: 22px;
height: 22px;
	border-radius: 5px;
	color: #fff;
}
.functionLine{
	position: absolute;
	z-index: 700;
	bottom: 14px;
	left: 18px;
	
	width: 576px;
	height: 0px;
	display: none;
	
	text-align: right;
}
.functionLine .bg{
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 566;
	background: rgb(151, 151, 151);
	-webkit-opacity: 0.6;  
    /* Netscape and Older than Firefox 0.9 */  
    -moz-opacity: 0.6;  
    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/  
    -khtml-opacity: 0.6;  
    /* IE9 + etc...modern browsers */  
    opacity: .6;  
    /* IE 4-9 */  
    filter:alpha(opacity=60);  
    /*This works in IE 8 & 9 too*/  
    -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=60)";  
    /*IE4-IE9*/  
    filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=60)!important;
}
.functionLine .tool{
	position: absolute; 
	width: 100%;
	height: 100%;
	Z-INDEX: 666;
}
.functionLine .remark{
	float: left;
	display: block;
	height: 20px;
	margin-left: 35px;
}
#mybtn{
	width: 40px;
	height: 20px;
	background: #2290E7;
	border: 0px;
	color: #fff;
	cursor: pointer;
	margin: 2px 10px 0px 0px;
}
#mybtn:hover{
	background: #1965A2;
}
.version_bgimg{
	position: absolute;
	z-index: 500;
	width: 580px;
	height: 333px;
	/*background: url('images/bg.png') #fff;*/
}
.version_bgimg img{
	width: 100%;
	height: 100%;
}
#version_ch{
	width: 580px; height:333px;
	z-index: 600;
	position: absolute;
	/*background: url('images/bg.png') #fff;*/
}
#version_en{
	position: absolute;
	z-index: 600;
	width: 580px; height:333px;
	/*background: url('images/bg.png') #fff;*/
	display: none;
}
.version_container{
	height: 305px;
	overflow: hidden;
	width: 100%;
}
/**内容style*/
.namecard *{color: #555; font-family: 'microsoft yahei'; text-decoration: none;}
.namecard p{line-height: 16px!important; margin: 0px; font-size: 11px; /*font-weight: bold;*/}
.nameinfo{float: left; min-height: 129px; width: 100%; position: relative;}
.infos{/*position: absolute; top: 50px; left: 35px;*/ margin: 50px 0px 0px 35px;}
.infos h4{font-size: 16px; color: #0e4d8b; margin: 0px; font-weight: bold;}
.infos p{font-size: 11px;}
.infos .remark{font-size: 11px; }
.infos .remark span{font-size: 11px;}
.infos .remark em{font-size: 11px;} 
.otherinfo{float: left; /*height: 150px;*/ margin-top: 16px; width: 100%; position: relative;}
.urlinfo{position: absolute; bottom: 14px; /*padding-left: 330px;*/ width: 100%;}
.urlinfo .remark{float: left; width: 50%; color: red; padding-left: 35px; display: none;}
.urlinfo .url{float:right; width: 50%; padding-left: 40px;}
.companyinfo{float: left; width: 310px; height: 100%; position: relative;}
.companyinfo p{ margin-left: 35px; width: 290px; word-wrap: break-word;}
.contactinfo{float: left; width: 270px; height: 100%; position: relative;}
.contactinfo p{ margin-left: 20px; width: 245px; line-height: 16px!important;}
.contactinfo span{float: left; font-size: 11px; height: 16px;}
/*两者加起来220px */
.contactinfo .tagname{width: 30px;}
.contactinfo .tagcont{width: 200px; word-break: break-all;}
/*en版标头稍宽*/
#version_en .tagname{width: 40px;}
#version_en .tagcont{width: 205px;}
#tipLayer{
	position: absolute;
	top:0px;
	left:0px;
	float: right;
	z-index: 998;
	overflow-x: hidden;
	overflow-y: hidden;
	width: 100%;
	height: 100%;
	display: none;
	border: 0px solid #D1D1D1;
	/*background-color: rgba(255, 255, 255);*/
	background: #5A5A5A;
	/* older safari/Chrome browsers */  
    -webkit-opacity: 0.6;  
    /* Netscape and Older than Firefox 0.9 */  
    -moz-opacity: 0.6;  
    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/  
    -khtml-opacity: 0.6;  
    /* IE9 + etc...modern browsers */  
    opacity: .6;  
    /* IE 4-9 */  
    filter:alpha(opacity=60);  
    /*This works in IE 8 & 9 too*/  
    -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=60)";  
    /*IE4-IE9*/  
    filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=60)!important;  
} 
</style>

	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
  <script type="text/javascript">
 	function _ajax(msg,url,data,callback){
			
				if((!msg)||confirm(msg)){
					$.ajax({
					type:"post",
					url: url,
					dataType:'json',
					data:data,
					beforeSend:function(){
					showLoad();
					},
					success:function(result){
						if(callback){
							callback(result);
						}else{
							alert(result.state);
						}
					},error:function(){
						alert("Network connection is failed, please try later...");
					},complete:function(){
						closeLoad();
					}
					});
				
				}  
		}
  var depthead=false;

  var staffcode="${convoy_username}";
   var econvoy="${Econvoy}".split("-");
   
  function resize(){
	  //需考虑间距情况
	   $("#left_div,#right_div").css("top",$("#head_div").height()+1);
	   $("#right_div").css("left",$("#left_div").width()+1);
	   $("#head_div").width($("body").width());
	   $("#right_div,#page_frame").width($("#head_div").width()- $("#left_div").width());
	   $("#left_div,#right_div").height($(window).height()-$("#head_div").height()+1);
	   
	   if($('#namecard').length > 0){
		   var namecard = $('#namecard');
		   var ncWid = $(namecard).width(), ncHeight = $(namecard).height();
			var winWid = $(document).width(), winHeight = $(document).height(), _top = 0;
			_top = (winHeight - ncHeight)/2 > 100 ? (winHeight - ncHeight)/2-50 : (winHeight - ncHeight)/2;
			
			$(namecard).css({
				'z-index': 9999,
				'left': (winWid-ncWid)/2 +'px',
				'top': _top
			}).fadeIn('fast').show();
	   }
  }
  
  

  
  
  
  $(function(){
	  //初始化需要重设高宽度,需考虑间距情况
	  $("#left_div,#right_div").css("top",$("#head_div").height()+1);
	   $("#right_div").css("left",$("#left_div").width()+1);
	   
	   $("#head_div").width($("body").width());
	   $("#right_div,#page_frame").width($("#head_div").width()- $("#left_div").width());
	   $("#left_div,#right_div").height($(window).height()-$("#head_div").height()+1);
	  $("#loginUser").empty().append(econvoy[1]);
	  //$("#left_div table tr:first-child").addClass("#left_div table tr:first-child");
	  $("#left_div table tr:last-child").hide();//初始化关闭子菜单为兼容IE7
	  $("#left_div table tr:first-child").click(function(){
		  if($(this).siblings().is(":hidden")){
			 $("#left_div table tr:last-child").hide();
			 //$("#left_div table tr:last-child").not($(this).siblings()).hide();
		  }
		  $(this).siblings().slideToggle('slow');
	  });
	   window.onresize=resize;
	  if("${convoy_userType}"=="Staff"){
		  $(".Consultant").remove();
		  	//控制只有某些部门的staff可以访问Opt Out菜单
			 if(econvoy[2]!="ADM"  && econvoy[2]!="LCD" && econvoy[2]!="CSD" && econvoy[2]!="BPD" && econvoy[2]!="SSC"&& econvoy[2]!="CED"&& staffcode!='CC0199'){
					  $("#optList").remove();
				  }
				//控制只有某些部门的staff可以访问ReturnMail菜单
			  if(econvoy[2]!="ADM" && econvoy[2]!="MGT" && econvoy[2]!="BPD" && econvoy[2]!="CSD" && econvoy[2]!="SSC" && econvoy[2]!="CED"){
				  	$("#returnmailList").remove();
			  }
				//控制只有某些Staff可以进行Return Mail 的录入.
			if( staffcode!='JL0219' && staffcode!='CW0114' && staffcode!='LC0103' && staffcode!='KC5417' && staffcode!='CK7336' && staffcode!="FK0076" && staffcode!="AY6459" && staffcode!="MA0321" && staffcode!="LH5672" && staffcode!="CK0101" && staffcode!="RC0106" && staffcode!="ML0476" && staffcode!="JT2421" && staffcode!="YC5151" && staffcode!="LK3480"){
				$(".return_mail_staff").remove();
			}
				//HR Namecard 维护模块
			if("${roleType}"=="HR"){
				$("#staff_namecard").remove();//移除普通staff模块
			//	 if("hrapproval"==requests("handle")){//判断是否需要进入审核页面
				//	$("#hr_namecard tr:first-child,#hr_approval_span").click();
				 //}
			}else{
				$("#hr_namecard").remove();
			}
			
			
			/**
			*新增dept head 自动进入审核页面
			*/
			if("${roleType}"=="DEPT"){
				$("#depart_span,#depart_request,#depart_hr_span").show();
				 if("deptapproval"==requests("handle")){//判断是否需要进入审核页面
					depthead=true;
					$("#staff_namecard  tr:first-child,#depart_span").click();
				}
			}else{
				$("#depart_span,#depart_request,#depart_hr_span").remove();
			}
			 
	  }else{
		   $(".Staff").remove();
	  }
	 if(econvoy[5]=="PA"){
		 $("#cons").remove();
		// $("#key_request").show();
		 $("#staff").show();
	 }else{
		//$("#key_request").hide(); 
	 }
	 
	 if(econvoy[5]=="AD" || econvoy[5]=="DD" || econvoy[5]=="PA"){
		 $("#AD_DD_PA").show();
	 }else{
		  $("#AD_DD_PA").hide();
	 }
	 
	 
	
  /***page init******/
 
	checkname();

	  
 
	  
	  
  });
 
    
     
     
  </script>
  <style type="text/css">
  .userinfo{
  	height: 60px;
  	/*width: 210px;*/
  	padding: 0px 35px 5px 35px;
  }
  /*#position_div{
  	position: absolute;
  	top: 26px;
  	left: 210px;
  	margin:0px 10px;
  	height: 26px;
  	width: 100%;
  	border: 1px solid #ddd;
  }*/
  .userinfo span{
  	float: left;
  }
  .userIcon{
  	display: block;
  	width: 40px;
	height: 40px;
	border: 2px solid #ddd;
	text-align: center;
	margin: 0px auto;
	border-radius: 50px;
	color: #ddd;
  }
  .icon-user{
  	display: block;
  	margin: 5px;
  	color: #ddd;
  }
  .icon-caret-right{
  	display: inline-block;
  	width: 12px;
  }
  .userName{
  	display: block;
  	height: 45px;
  	width: 84px;
  	font-size: 12px;
  	padding-top: 3px;
  	margin: 0px 5px;
  	font-style: italic;
  }
  #loginUser{
  	font-size: 14px;
  }
  .positionInfo{
  	width: 30%;
  	float: left;
  	height: 100%;
  	text-align: left;
  }
  .positionInfo strong{
  	color: #ddd;
  }
  .positionInfo strong:hover{
  	color: #fff;
  }
  .positionInfo .icon-home{
  	color: inherit;
  	font-size: 14px;
  	margin-right: 4px;
  }
  .positionInfo .homeBtn{
  	color: #ddd;
  }
  .positionInfo .homeBtn:hover{
  	color: #fff;
  }
  .functionInfo{
  	float: right;
  	width: 69%;
  	height: 100%;
  	text-align: right;
  	padding-right: 20px;
  	color: #fff;
  }
  .functionInfo ul{margin: 0px; padding: 0px;}
  .functionInfo ul li{
  	float: right;
  	list-style-type: none;
  }
  .functionInfo ul li i{
  	color: #ddd;
  	cursor: pointer;
  }
  .functionInfo ul li a{
  	color: #ddd;
  	cursor: pointer;
  }
  .functionInfo ul li a:hover i{
  	color: #fff;
  }
  .functionInfo ul li span{
  	color: #fff;
  	padding: 0px 15px;
  }
  .functionInfo select{
  	border: 0px;
  	color: #ddd;
  	width: 90px;
  	background: initial;
  }
  .functionInfo select:hover{
  	color: #fff;
  }
  table .icon-list-alt{
  	display: inline-block;
  	width: 22px;
  }
  .logout:hover{
  	text-decoration: underline;
  }
  </style>
  </head>
  <body>
	 <div id="show_all">
	 
	
		   <div id="head_div" >
			   <table >
				   <tr>
					   <td align="left" style="width: 209px; background: #f9f9f9; border-right: 1px solid #ccc; padding: 0px;">
					    </td>
					   
					   <td  width="" align="right">
					   		<div class="positionInfo">
					   			<a style="color: #ddd;font-size: 16px;" class="homeBtn" href="javascript:void(0);" onClick="menu_click(this,'officeAdmin_index.jsp');"><strong><i class="icon-home" style="color: #ddd;"></i>HOME</strong></a>
					    &nbsp;<i class="icon-angle-right" style="color: #ffffff!important;"  >
					    	</i>
					    	&nbsp; 
					    		<em id="currentPath" style="font-style: normal; color: #ddd
					    		;">Index</em>  
					   		</div>
					   		<div class="functionInfo">
					   			<!--  <strong><span  style="color:white;font-size: 16px;line-height: 30px;vertical-align: middle;"></span></strong>
						   	 	<a class="btn" onclick="openWindow(location.href)" style="padding: 2px 5px;" id="fullscreen">
						   	 		<i class="icon-fullscreen"></i>
						   	 	</a>-->
						   	 	<ul>
						   	 		
						   	 		<li id="fullLi">
						   	 			<a class="" id="fullscreen">
						   	 				<i class="icon-fullscreen"  ></i>
						   	 			</a>
						   	 		</li>
						   	 		<li id="logoutLi">
						   	 			<a class="logout" id="logout">注销</a>
						   	 		</li>
						   	 		<li>

						   	 			<c:if test="${originalcode=='BOC106' || originalcode=='BOC075'  || originalcode=='KC0102' }">
						   	 			<input id="usernames" type="text" style="height: 20px;vertical-align: middle;border-radius: 2px;line-height: 20px;"/>
											<select style="color:#000;border-radius: 2px;" onchange="changerole(this)">
												<option value="Consultant" <c:if test="${roleType=='Consultant'}"> selected=selected</c:if>>Consultant </option>
												<option value="Staff" <c:if test="${roleType=='STAFF'}"> selected=selected</c:if>>Staff </option>
												<option value="Dept Head" <c:if test="${roleType=='DEPT'}"> selected=selected</c:if>>Supervisor </option>
												<option value="HKADM" <c:if test="${roleType=='HKADM'}"> selected=selected</c:if>>HKADM </option>
												<option value="HR" <c:if test="${roleType=='HR'}"> selected=selected</c:if>>HR </option>
											</select>
											<span>|</span>
										</c:if>
										<c:if test="${originalcode=='MA0321' &&1==2}">
										<input id="usernames" type="text" style="height: 20px;vertical-align: middle;border-radius: 2px;line-height: 20px;"/>
											<select style="color:#000;border-radius: 2px;">
												<option <c:if test="${convoy_userType=='Consultant'}"> selected=selected</c:if>>Consultant </option>
												<option <c:if test="${roleType=='Staff'}"> selected=selected</c:if>>Staff </option>
												<option <c:if test="${roleType=='HKADM'}"> selected=selected</c:if>>HKADM </option>
											</select>
											<span>|</span>
										</c:if>
						   	 		</li>
						   	 	</ul>
					   		</div>
					   </td>
				   </tr>
			   </table>
		  </div>
		   <div id="left_div"   >
		   		<div class="userinfo">
		   			<span class="userIcon">
		   				<i class="icon icon-user icon-2x"></i>
		   			</span>
		   			<span class="userName">Welcome<br /><em id="loginUser">Zhangsan</em></span>
		   		</div>
				<table  class="Consultant" width="200" id="cons"  cellpadding="0" cellspacing="1">
					 <tr >
						 <td><i class="icon-list-alt"></i><strong >Medical Claim</strong></td>
					 </tr>
					 <tr id="medical_cons_table"  >
						 <td >
						
						<div onClick="menu_click(this,'MedicalClaim_Consultant/QueryMedical_Consultant.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Medical Claim Query</a></div>
						 </td>
					 </tr>
				</table>
				<table  class="Staff" id="staff" cellpadding="0" cellspacing="1">
					 <tr >
						 <td ><i class="icon-list-alt"></i><strong >Medical Claim</strong></td>
					 </tr>
					 <tr id="medical_staff_table"  >
						 <td >
						<div  onClick="menu_click(this,'MedicalClaim_Consultant/QueryMedical_Staff.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Medical Claim Query</a></div>
						 </td>
					 </tr>
				</table>
				
				
				<table  class="Consultant"  cellpadding="0" cellspacing="1"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong  >Name Card</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="namecard_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div onClick="menu_click(this,'namecard/addNameCard.jsp');"><i class="icon-caret-right"></i><a  href="javascript:void(0);"  >Name Card Request</a></div>
						<div onClick="menu_click(this,'namecard/queryNameCard.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Name Card Query</a></div>
						 </td>
					 </tr>
				</table>
				<table  class="Staff" id="staff_namecard"> <!--  添加主菜单需另外添加table--> 
					 <tr>
					 	<td><i class="icon-list-alt"></i><strong  >Name Card</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="staff_namecard_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						
						<div id="staff_span" onClick="menu_click(this,'namecard/staff/addstaffnamecard.jsp');" ><i class="icon-caret-right"></i><a  href="javascript:void(0);" >Name Card Request</a></div>
						<div onClick="menu_click(this,'namecard/staff/queryStaffCard.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Name Card Query</a></div>
						<div id="depart_span" style="display: none;" onClick="menu_click(this,'namecard/departhead/approveStaffCard.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Name Card Approve</a></div>
						<div onClick="menu_click(this,'manual/tips for Staff  name card request on COAT.docx');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Namecard Tips for Staff</a></div>
						
						 </td>
					 </tr>
				</table>
			<table  class="Staff" id="hr_namecard"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong  >HR Name Card</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="staff_hrnamecard_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						 <div onClick="menu_click(this,'namecard/staff/addstaffnamecard.jsp');"><i class="icon-caret-right"></i><a  href="javascript:void(0);"  >Name Card Request</a></div>
						<div onClick="menu_click(this,'namecard/staff/queryStaffCard.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Name Card Query</a></div>
						<%--<div id="hr_approval_span" onClick="menu_click(this,'namecard/hr/approveStaffCard.jsp');"><a href="javascript:void(0);" > Name Card Approve</a></div>
						--%><div id="depart_hr_span"  onClick="menu_click(this,'namecard/departhead/approveStaffCard.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Dept Head Name Card Approve</a></div>
						<div onClick="menu_click(this,'namecard/hr/departHead.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Department Head</a></div>
						<div onClick="menu_click(this,'namecard/hr/staff_title_list.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);"> Staff Position Title</a></div>
						<div onClick="menu_click(this,'namecard/hr/department.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);"> Staff Department</a></div>
						 </td>
					 </tr>
				</table>	
				
			
				
				
				
				
				
				
				<table  class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Stationery Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Stationery_cons_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div onClick="menu_click(this,'COAT_Order/addStationery.jsp');" ><i class="icon-caret-right"></i><a  href="javascript:void(0);">Stationery Form Request</a></div>
						<div onClick="menu_click(this,'COAT_Order/QueryStationeryOrder.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Stationery Form Query</a></div>
						 </td>
					 </tr>
				</table>
				<table  class="Staff"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Stationery Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Stationery_Staff_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div onClick="menu_click(this,'COAT_Order/addStationery_staff.jsp');" ><i class="icon-caret-right"></i><a  href="javascript:void(0);">Stationery Form Request</a></div>
						<div onClick="menu_click(this,'COAT_Order/QueryStationeryStaffOrder.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Stationery Form Query</a></div>
						 </td>
					 </tr>
				</table>
				
				<table width="100%" class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Marketing Premium Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="marketing_premium_cons_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div onClick="menu_click(this,'COAT_Order/addMarketingPremium.jsp');"><i class="icon-caret-right"></i><a  href="javascript:void(0);" >Marketing Premium Request</a></div>
						<div onClick="menu_click(this,'COAT_Order/QueryMarkPremiumOrder.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Marketing Premium Form Query</a></div>
						 </td>
					 </tr>
				</table>
				
				
				
				<table width="100%" class="Staff"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Marketing Premium Form</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="marketing_premium_staff_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div onClick="menu_click(this,'COAT_Order/addMarketingPremium_staff.jsp');"><i class="icon-caret-right"></i><a  href="javascript:void(0);" >Marketing Premium Request</a></div>
						<div onClick="menu_click(this,'COAT_Order/QueryMarkPremiumOrder.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Marketing Premium Form Query</a></div>
						 </td>
					 </tr>
				</table>
				
					<table width="100%" class="Staff" id="returnmailList"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Return Mail</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Return_Mail_staff_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<%--<div class="return_mail_staff" ><a href="javascript:openlinkWin();" > Return Mail Scanning</a><br/></div>
						--%><div class="return_mail_staff" ><i class="icon-caret-right"></i><a href="javascript:openlinknewWin();" >Return Mail Scanning</a><br/></div>
						<div onClick="menu_click(this,'Return_Mail/ScanningRecord_Staff.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Return Mail Query</a></div>
						 </td>
					 </tr>
					 
				</table>
				
				
					
					<table width="100%" class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Return Mail</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Return_Mail_cons_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div  onClick="menu_click(this,'Return_Mail/ScanningRecord.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Return Mail Query</a></div>
						 </td>
					 </tr>
					 
				</table>
				
				
								
					<table width="100%" class="Consultant"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Client Opt Out List</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Optout_cons_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div onClick="menu_click(this,'Optout/OptoutList.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Existing Client</a></div>
						 </td>
					 </tr>
				</table>
				
					<table width="100%" class="Staff" id="optList"> <!--  添加主菜单需另外添加table-->
					 <tr >
					 	<td><i class="icon-list-alt"></i><strong>Client Opt Out List</strong></td><!-- 主菜单名 -->
					 </tr>
					 <tr id="Optout_staff_table"  >
						 <td><!-- 添加子菜单只需添加<a>标签 -->
						<div  onClick="menu_click(this,'Optout/OptoutList_Staff.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Existing Client</a></div>
						 </td>
					 </tr>
				</table>
				
				
				
				
					<table  class="Staff" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Access Card</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="staff_access_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
							<div onClick="menu_click(this,'Coat_Request/AccessCardRequest.jsp');"><i class="icon-caret-right"></i><a  href="javascript:void(0);"  >Access Card Request</a></div>
							<div onClick="menu_click(this,'Coat_Request/QueryAccessCardRequest.jsp');"><i class="icon-caret-right"></i><a  href="javascript:void(0);" >Access Card Query </a><br></div>
							
							 </td>
						 </tr>
				</table>	
				
					<table  class="Staff" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><strong  ><i class="icon-list-alt"></i>ADM Service</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="staff_admservice_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->

							<div onClick="menu_click(this,'Coat_Request/ADMservicesRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">ADM Service Request</a></div>
							<div onClick="menu_click(this,'Coat_Request/QueryADMservicesRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >ADM Service Query </a></div><%--
							
							<a href="javascript:void(0);" onClick="menu_click(this,'Coat_Request/KeyRequest.jsp');">Keys Request</a><br>
							<a href="javascript:void(0);" onClick="menu_click(this,'Coat_Request/QueryKeyRequest.jsp');">Query Keys</a><br>
							
							
							--%>							
							 </td>
						 </tr>
				</table>	
					<table  class="Staff" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Company Asset</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="staff_companyAsset_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->

							<div  onClick="menu_click(this,'Coat_Request/RequestForCompanyAsset.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Company Asset Request</a></div>
							<div onClick="menu_click(this,'Coat_Request/QueryRequestForCompanyAsset.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);" >Company Asset Query </a></div>
							
						
							 </td>
						 </tr>
				</table>	
					<table  class="Staff" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Room Setting</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="staff_roomsetting_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/RequestForRoomSetting.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Room Setting Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryRequestForRoomSetting.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Room Setting Query </a></div>
							
							
							
							
						
							 </td>
						 </tr>
				</table>	
					<table  class="Staff" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >SeatAssignment</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="staff_seat_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/QuerySeatAssignment.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Key Distribution</a></div>
							 </td>
						 </tr>
				</table>	
				
				
				
				
				
				
				<table  class="Consultant" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Access Card</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_access_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/AccessCardRequest.jsp');" ><i class="icon-caret-right"></i><a  href="javascript:void(0);">Access Card Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryAccessCardRequest.jsp');" ><i class="icon-caret-right"></i><a  href="javascript:void(0);">Access Card Query </a></div>
							
										 </td>
						 </tr>
				</table>	
				
				
				
				
				
				
				<table  class="Consultant" > <!--  添加主菜单需另外添加table-->
						 <tr>
						 	<td><i class="icon-list-alt"></i><strong  >ADM Service</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_admservice_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
							
								<div onClick="menu_click(this,'Coat_Request/ADMservicesRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">ADM Service Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryADMservicesRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">ADM Service Query </a></div>
							 </td>
						 </tr>
				</table>	
				
				
				
				
				
				
				<table  class="Consultant"  > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Keys</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_key_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/KeyRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Keys Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryKeyRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Keys Query </a></div>
							 </td>
						 </tr>
				</table><%--	
				
				
					<table  class="Staff"  id="key_request"> <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><strong  >Keys</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/KeyRequest.jsp');"><a href="javascript:void(0);">Keys Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryKeyRequest.jsp');"><a href="javascript:void(0);">Keys Query </a></div>
							 </td>
						 </tr>
				</table>	
				
				
				
				
				
				--%><table  class="Consultant" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Company Asset</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_companyasset_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
							
								<div onClick="menu_click(this,'Coat_Request/RequestForCompanyAsset.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Company Asset Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryRequestForCompanyAsset.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Company Asset Query </a></div>
							 </td>
						 </tr>
				</table>	
				
				
				
				
				
				
				<table  class="Consultant" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Room Setting</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_roomsetting_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
							 
								<div onClick="menu_click(this,'Coat_Request/RequestForRoomSetting.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Room Setting Request</a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryRequestForRoomSetting.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Room Setting Query </a></div>
							</td>
						 </tr>
				</table>	
				
				
				
				
				

				
				<table  class="Consultant" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >PIBA Study Note</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/RequestPIBAStudyNote.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">PIBA Study Note Request</a></div>
							 	<div onClick="menu_click(this,'Coat_Request/QueryPIBAStudyNoteConsultant.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">PIBA Study Note Query</a></div>
							 </td>
						 </tr>
				</table>
				
				
				<table  class="Consultant"  id="AD_DD_PA"> <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Recruitment Advertising</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/RequestForRecruitment.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Recruitment Advertising Request </a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryRecruitment.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Recruitment Advertising Query </a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryRecruitmentList.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Recruitment Advertising List </a></div>
							 </td>
						 </tr>
				</table>	
				
				
					<table  class="Staff" > <!--  添加主菜单需另外添加table-->
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >ePayment</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'Coat_Request/ePaymentRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">ePayment Request </a></div>
								<div onClick="menu_click(this,'Coat_Request/QueryEPaymentRequest.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">ePayment Query </a></div>
							 </td>
						 </tr>
				</table>	
<!-- 				<table  class="Staff" >
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Seat Exchange</strong></td>主菜单名
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td>添加子菜单只需添加<a>标签
								<div onClick="menu_click(this,'seat/SeatChangeApply_build.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatChangeApply_User</a></div>
								<div onClick="menu_click(this,'seat/SeatChangeApply_query.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatChangeApply_Query</a></div>
								<div onClick="menu_click(this,'seat/SeatChangeApply_leader.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatChangeApply_Leader</a></div>
								<div onClick="menu_click(this,'seat/SeatMap.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Seat Zone Plan</a></div>
							 </td>
						 </tr>
				</table>	
				<table  class="Staff" >
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Seat Auto Exchange</strong></td>主菜单名
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td>添加子菜单只需添加<a>标签
								<div onClick="menu_click(this,'seat/SeatAutoChangeApply_query.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatAutoChangeApply</a></div>
								<div onClick="menu_click(this,'seat/SeatAutoChangeApply_ADMquery.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatAutoChangeManagement</a></div>
							 </td>
						 </tr>
				</table>	 -->
				<table  class="Consultant" >
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >Seat Exchange</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'seat/SeatChangeApply_build.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatChangeApply_User</a></div>
								<div onClick="menu_click(this,'seat/SeatChangeApply_query.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatChangeApply_Query</a></div>
								<div onClick="menu_click(this,'seat/SeatChangeApply_leader.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">SeatChangeApply_Leader</a></div>
								<!-- <div onClick="menu_click(this,'seat/SeatMap.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Seat Zone Plan</a></div> -->
							 </td>
						 </tr>
				</table>	
				<table  class="Consultant" >
						 <tr >
						 	<td><i class="icon-list-alt"></i><strong  >SeatAssignment</strong></td><!-- 主菜单名 -->
						 </tr>
						 <tr id="cons_seat_table"  >
							 <td><!-- 添加子菜单只需添加<a>标签 -->
								<div onClick="menu_click(this,'seat/SeatAutoChangeApply_query.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Recruiter's Corner</a></div>
								<div onClick="menu_click(this,'seat/SeatAutoChangeApply_ADMquery.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Seat Assignment</a></div>
								<div onClick="menu_click(this,'Coat_Request/QuerySeatAssignment.jsp');"><i class="icon-caret-right"></i><a href="javascript:void(0);">Key Distribution </a></div>
							 </td>
						 </tr>
				</table>	
				
				
				
				
				
		   </div>
		   <!--  <div id="position_div">
		   		<span>Home&nbsp;></span>
		   		<em>Index</em>
		   </div>-->
		   <div id="right_div"  >
				 <iframe id="page_frame" src="officeAdmin_index.jsp" frameborder="0" width="100%" scrolling="yes" height="100%">
				 </iframe>
		   </div>
		   <div style="clear:both"></div>
		  <div id="login_black" align="center" style="display: none;" >
		    <div id="login" >
				  <table width="100%" height="100%">
					  <tr height="28" style="background-image:url(css/officeAdmin-menu-bar.gif); " ><td colspan="3" align="center"><strong style="color:yellow;" >User Login</strong></td></tr>
					  <tr height="40"><td  align="center"  width="80"><strong>Login User:</strong></td><td align="center" colspan="2"><input type="text" id="userlogin" /></td></tr>
					  <tr height="40"><td  align="center"><strong>Password:</strong></td><td align="center" colspan="2"><input type="text" id="password" /></td></tr>
					  <tr height="30"><td colspan="3"><span style="color: red;" id="messages"></span> </td></tr>
					  <tr height="30%"><td width="60"></td><td align="center"><input type="button" value="login" onClick="loginUser();"/><input type="button" value="Cancel"/></td></tr>
				  </table>
			  </div>
		  </div>
	 </div>
 
 <script type="text/javascript">
 if(top==this){
	 $("#fullscreen").attr("disabled","disabled");
 }
 $(function(){
	 document.onkeydown = function() {
        var oEvent = window.event;
        if((window.event.keyCode == 27) && $('#namecard').length > 0){
        	closeLayer();
        }
        if (window.event.altKey)             //屏蔽Alt+F4   
	    {   
	        window.event.keyCode = 0;
	        return false;   
	    }   
    }
	
	function loadBtn()
	{
		if(top.location == self.location){
			$('#logoutLi').show();
			$('#fullLi').hide();
		}else{
			$('#logoutLi').hide();
			$('#fullLi').show();
		}
	}
	loadBtn();
 });

 function menu_click(obj,link_menu){
	    
 	 if(staffcode==null){
 		 return false;
 	 }else{
 		 if( $('#page_frame').attr('src').toUpperCase().indexOf("/ADD")>0){
 			 if(confirm("Confirm to exit?")){
 				  $('#page_frame').attr('src',link_menu);
 				 $("#currentPath").empty().append($(obj).text()=="HOME "?"Index":$(obj).text());
 			 }else{
 				 return false;
 			 }
 		 }else{
 			 $('#page_frame').attr('src',link_menu);
 			 $("#currentPath").empty().append($(obj).text()=="HOME"?"Index":$(obj).text());
 		 }
 	 }
}
  
  
  
  

 function openlinkWin(){
	 var link="http://"+window.location.host+"/ConvoyUtil/Return_Mail/ReturnMailScanning.jsp?user=${convoy_username}";
			var win=window.open(link,"openlinkWin","toolbar=no,location=no,directories=no,menubar=no, scrollbars=no,resizable=yes,status=no,top=0,left=0"); 
 }
 function openlinknewWin(){
	 var link="http://"+window.location.host+"/ConvoyUtil/Return_Mail_New/ReturnMailScanning.jsp?user=${convoy_username}";
			var win=window.open(link,"openlinkWins","toolbar=no,location=no,directories=no,menubar=no, scrollbars=no,resizable=yes,status=no,top=0,left=0"); 
 }
 
 function changerole(obj){
	 
	 if(obj.value!=""){
		 $.ajax({
			url:"admin/PermissionServlet",
			type:"post",
			data:{"method":"authorization","role":obj.value,"usernames":$("#usernames").val()},
			success:function(date){
				if(date=="error"){
					alert(date);
				}else if(date=="success"){
					//alert(date);
					location.reload();
				}else{
					alert("exception:"+date);
				}
			},error:function(){
				alert(" Network connection is failed, please try later...");//网络连接失败
				return false;
			}
		 });
	 }
 }
 
 function openWindow(owurl)
 {
	 //var tmp= window.open("about:blank","fullscreen=1"); ie下全屏相当于F11
	 var tmp=window.open(owurl,"fullscreen");
	 tmp.moveTo(0,0); 
	 tmp.resizeTo(screen.width+20,screen.height); 
	 
	 tmp.focus(); 
	 tmp.location=owurl; 
 }
 
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
			 if(date!="success"){
				 //alert("timeout");
				  //document.location="error.jsp";
				  return false;
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
			    $("#loginUser").empty().append("${convoy_username}");
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

 
 function showLoad(tipInfo) {
	if (tipInfo == undefined || tipInfo == '') {
		tipInfo = 'Loading...';
	}
	if($('#tipDiv').length > 0){
		$('#tipDiv').fadeIn();
		return false;
	}
	var eTip = document.createElement('div');
	eTip.setAttribute('id', 'tipDiv');	
	eTip.innerHTML = '<div style="position:absolute;left: 50%; top: 30%; text-align: center; margin-left: -60px;padding:5px 15px;width:120px;"><img src=\'./css/022.gif\' /><span style=\'color:black; margin: 8px auto; font-size:1.1em;font-weight:bolder;\'>' + tipInfo + '</span></div>';
	try {
		document.body.appendChild(eTip);
	} catch (e) {
	}
	$('#tipDiv').fadeIn();
}

	function closeLoad() {
		$('#tipDiv').fadeOut();
	}
	function CurentTime()
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
         if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
           clock += "0";
        clock += day + "";
        return clock; 
    }
//预览功能下阴影收缩
var turn = function(target,time,opts){
	target.hover(function(){
		$('.functionLine').animate(opts[0],time,function(){
			$(this).show();
		});
	},function(){
		$('.functionLine').animate(opts[1],time,function(){
			$(this).hide();
		});
	});
}
//名片板块-预览功能
function showNameCard(html){
	if(html == null || html == 'undefined'){
		alert('加载数据错误');
		return false;
	}
	var namecard = $(html);
	$(document.body).append($(namecard));
	
	var ncWid = $(namecard).width(), ncHeight = $(namecard).height();
	var winWid = $(document).width(), winHeight = $(document).height(), _top = 0;
	var layer = $('<div id="tipLayer"></div>')
	$(document.body).append($(layer));
	_top = (winHeight - ncHeight)/2 > 100 ? (winHeight - ncHeight)/2-50 : (winHeight - ncHeight)/2;
	
	$(layer).fadeIn('fast',function(){
		$(namecard).css({
			'z-index': 9999,
			'left': (winWid-ncWid)/2 +'px',
			'top': _top
		}).fadeIn('fast').show();
	});
	//名片下方的透明层效果
	var horizontalOpts = [{'height':24},{'height':'0px'}];
	turn($('.namecard'),100,horizontalOpts);
}
$('#mybtn').live('click',function(){
	var opts = [{'width':0},{'width':'580px'}], _this = $(this),_remark = $('#ver_remark');
	var time=300;
	if($('#version_en').is(':visible')){
		$('.version_en').animate(opts[0],time,function(){
			$(this).hide().prev().show();
			$(this).prev().animate(opts[1],time);
		});
		_this.val('EN');
		_remark.text("僅供資料預覽");
		
	}else{
		
		$('.version_ch').stop().animate(opts[0],time,function(){
			$(this).hide().next().show();
			$(this).next().animate(opts[1],time);
		});
		_this.val('CH');
		_remark.text('Preview Information Only');
	}
});
$('#close').live('click',function(){
	$('#namecard').fadeOut().hide(function(){
		closeLayer();
	});
});
function closeLayer(){
	$('#namecard').fadeOut('fast').hide(function(){
		$('#tipLayer').remove();
		$(this).remove();
	});
	//$('#namecard').fadeOut('fast').hide();
	
}
$('#fullscreen').bind('click',function(){
	var _this = $(this), _disabled = _this.attr('disabled');
	if(_disabled == 'disabled' || _disabled){
		return false;
	}else{
		openWindow(location.href);
	}
});
$('#logout').bind('click',function(){
	location.href = 'login.jsp';
});
 </script>
  </body>
</html>
