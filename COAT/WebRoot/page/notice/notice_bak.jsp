<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
 	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<link rel="stylesheet" href="<%=basePath%>/css/layout.css">
	<link rel="stylesheet" href="<%=basePath%>/plug/css/site.css">
	<script src="<%=basePath%>/css/date.js" language="JavaScript"></script>
	<script src="<%=basePath%>/css/Util.js" language="JavaScript"></script>
	<script type="text/javascript" src="<%=basePath%>plug/lhgdialog/lhgdialog.min.js"></script>
		
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.info {
	height: 26px;
	line-height: 36px;
}
hr{
color: #EEF2FD;
margin: 7px 0px!important;
}
.info th {
	text-align: right;
	width: 95px;
	color: #4f4f4f;

	font-size: 12px;

}

.info td {
	text-align: left;
}

#apDiv1 {
	position:absolute;
	width:435px;
	height:281px;
	z-index:1;
	left: 563px;
	top: 26px;
	background-color:#E6EDF2;
	text-align: center;
	font-weight: bold;
}
#apDiv2 {
	position:absolute;
	width:435px;
	height:281px;
	z-index:1;
	left: 58px;
	top: 350px;
	background-color:#E6EDF2;
	text-align: center;
	font-weight: bold;
}
#apDiv3 {
	position:absolute;
	width:435px;
	height:281px;
	z-index:1;
	left: 58px;
	top: 26px;
	background-color:#E6EDF2;
	text-align: center;
	font-weight: bold;
}
#apDiv4 {
	position:absolute;
	width:435px;
	height:281px;
	z-index:1;
	left: 563px;
	top: 350px;

	background-color:#E6EDF2;
	text-align: center;
	font-weight: bold;

}
 div{
 border-radius:4px 4px 4px 4px;
 border: 1px solid #ccc;
 }
 .tagName{ text-align: left; padding-left: 15px;}
 .tagCont{ text-align: right; padding-right: 15px;}
 .tagCont a{text-decoration: none;}
 .tagCont a:hover{text-decoration: underline;}
</style>


<script type="text/javascript">
var basepath = '<%=basePath%>';
var notice=null;
function app(type){
	var html="";
 
	if(notice.length>0){
		var f=0;
		for(var i=0;i<notice.length;i++){
			
			if(notice[i].type==type && f<5){
				var types=notice[i].type+notice[i].id
				if(f==4){				
					html+="<tr title='-' class='main_info'><td></td><td><a href='javascript:void(0);' onclick='detail2(\""+notice[i].type+"\")'>更多...</a></td></tr>";
					
				}else{
					html+="<tr title='-' class='main_info'><td title="+notice[i].title+"><a href='javascript:void(0);' onclick='detail1("+notice[i].id+")'>"+notice[i].subject+"</a><hr/></td><td>"+notice[i].enddate+"<hr/></td></tr>  ";
				}
				f++;
				
			}
		}
	}else{//没有数据
		html+="<tr title='-' class='main_info'><td colspan='2'><s:text name='notAll'></s:text><td></tr>";
	}
	if(html==""){
		html+="<tr title='-' class='main_info'><td colspan='2'><s:text name='notAll'></s:text><td></tr>";
	}
	return html;
}

function notice1(){
		 $.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"select"},
			 success:function(data){
				if(data==null || data == "[]"){
//					alert("没有您的消息！");
//					$('#bodyIframe', top.document).attr('src', basepath+"page/notice/notice.jsp");
//					window.parent.location="page/notice/notice.jsp";
				}else{
						notice=eval(data);
			 	 $("[title='-']").remove();
				 $("#personal:last").append(app("Personal")); 
				 $("#group:last").append(app("Group")); 
				 $("#all:last").append(app("All")); 
			
				 }
			 },error:function(){
			   alert("<s:text name='connectionError'/>");
			 }
			 
		 });
	 }



function detail1(noticeId){
	$.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"selectSingle","noticeId":noticeId },	
			 success:function(data){
			 	var url1 = basepath + "page/notice/notice_detail1.jsp";
	            infolhg(1200,600,url1,data);
			 }
	});		
}

function detail2(noticetype){
	$('#bodyIframe', top.document).attr('src', basepath+"/page/notice/notice_detail2.jsp?noticetype="+noticetype);
/**	$.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"selectListByType","noticetype":noticetype },	
			 success:function(data){
			 	if(data==null || data == "[]"){
					alert("没有更多消息！");
				}else{
					var url2 = basepath + "page/notice/notice_detail2.jsp";
	            	infolhg(1600,800,url2,data);
					
			 	}

			 }
	});		**/
}





</script>
</head>
<body onload = "notice1()">
<div id="apDiv1">
  <table width="100%"  id="group" border="0" cellpadding="0" cellspacing="0" >
	   <tr class="main_info">
		   	<td colspan="2">
		   		<h3><strong>群组消息</strong></h3>
		   	</td>
	   </tr>
       <tr>
       <td colspan="2"><hr></td>
       </tr>
	  <tr class="main_info">
		  <td width="70%">公告主题</td>
		  <td>有效时间</td>
	  </tr>
	  <tr>
	  	<td colspan="2"><hr></td>
	  </tr>
  </table>
<br>
</div>

<div id="apDiv2">
  <table id="personal" width="100%" border="0" cellpadding="0" cellspacing="0">
     <tr class="main_info">
	     <td colspan="2">
	     	<h3><strong>个人消息</strong></h3>
	     </td>
     </tr>
	 <tr>
	  	 <td colspan="2"><hr></td>
	 </tr>
    <tr class="main_info">
	    <td width="70%">公告主题</td>
	    <td>有效时间</td>
    </tr>
     <tr>
     	<td colspan="2"><hr></td>
     </tr>
  </table>
</div>
<div id="apDiv3">
  <table id="all" width="100%" border="0" cellpadding="0" cellspacing="0"  >
     <tr class="main_info"><td colspan="2"><h3><strong>公共消息</strong></h3></td></tr>
       <tr><td colspan="2"><hr/></td></tr>
        <tr class="main_info"><td  width="70%">公告主题</td><td>有效时间</td></tr>
   <tr><td colspan="2"><hr></td></tr>
  </table>
</div>

<div id="apDiv4" ></div>

<script type="text/javascript">
$('.documents').each(function(){
	$(this).bind('click',function(){
		var _urlType = $(this).attr('alt'),baseUrl = '../attachment/document/' , url = '';
	
		switch(_urlType){
			case 'ch':
				url = baseUrl + 'ICS_User_Guide_CN_V2.doc';
				break;
			case 'hk':
				url = baseUrl + 'ICS_User_Guide_HK_V2.doc';
				break;
		}
		window.open(url);
	});
});
</script>
</body>
</html>