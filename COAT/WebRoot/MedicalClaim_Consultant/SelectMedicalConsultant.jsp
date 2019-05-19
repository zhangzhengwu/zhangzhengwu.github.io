<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
	<%
		if (session.getAttribute("adminUsername")==null || session.getAttribute("adminUsername")=="")
			out.println("<script>alert('未登录系统！');top.location.href='signin.jsp';</script>");
	%>
  <head>
    <base href="<%=basePath%>">
    
    <title>QueryMedicalConsultant</title>
 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<meta http-equiv="description" content="This is my page">  
	
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">

    <style type="text/css">
<!--
#Medical {
	position:absolute;
	width:100%;
	z-index:-1;
	left: 3px;
	top: 3px;
}
.txt{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 18px;

    }
	.txts{
    color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: '仿宋';
	font-size: 16px;

    }
.STYLE1 {
	font-size: 36px;
	font-weight: bold;
	color: #0000FF;
}
#content{
	position:absolute;
	width:100%;
	z-index:-1;
	left:3px;
	
}
-->
    </style>
  </head>
  <script type="text/javascript" >
  var downs=null;
  $(function(){
	 
     $("#content").css("top",$("#Medical").height());
     $("#start_date").val(CurentTime());
     $("#end_date").val(CurentTime());
/*********************************改变时间格式*********************************************/
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
/*****************************************************Down click**********************************/
	
	$("#down").click(function(){ 
		if(downs!=null){
			window.location.href="<%=basePath%>DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/******************************************************Down END***********************************/
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
	selects();
	});
/******************************************************************************************************************/
 /***********************************************select function************************************/
	function selects(){
				if($("#start_date").val()==""){
					alert("開始日期不能為空！");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("結束日期不能為空！");
					$("#end_date").focus();
					return false;
				}
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("開始日期不能大於結束日期!");
						$("#start_date").focus();
						return false;
					}
				}
		 
				$.ajax({
				type: "post",
				url:"SelectMedicalConsultantServlet",
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'code':$("#staffcodes").val(),'name':$("#name").val()},
				success:function(data){
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole.length>0){
					downs=dataRole;

						for(var i=0;i<dataRole.length;i++){
												var state="";
												var colors="";
												if(dataRole[i].shzt=="Y"){
													state="已審核";
													colors="<tr id='select' title='"+i+"' style='background:#D5D5D5;'> ";
												}else if(dataRole[i].shzt=="N"){
													state="待審核";
															colors="<tr id='select' title='"+i+"' style='background:gray;'> ";
												}else{
													state="已拒絕";
															colors="<tr id='select' title='"+i+"' style='background:red;'> ";
												}
							html+=colors+"<td align='center'>"+dataRole[i].staffcode+"</td><td align='center'>"
							+dataRole[i].name +"</td><td align='center'>"+dataRole[i].SP_type+
							"</td><td align='center'>"+dataRole[i].medical_date+"</td><td align='center'>"+dataRole[i].medical_Fee+
							"</td><td align='center'>"+dataRole[i].entitled_Fee+"</td><td align='center'>"
							+dataRole[i].medical_month +"</td><td align='center'>"+state+"</td><td>"+dataRole[i].remark+"</td></tr>";		 
						}
			
					}
					else{
						html+="<tr id='select'><td colspan='15' style='color:blue;size=5px' align='center'><b>"+"對不起，沒有您想要的數據!"+"</b></td></tr>";
					}
						 $("#jqajax:last").append(html);
					 	// $("tr[id='select']:odd").css("background","#COCOCO");
		               //  $("tr[id='select']:even").css("background","#D5D5D5");
				 }
			 });
	}
	/**************************************************************************************************/
  });
  
  </script>
  <body>
  <br>
  <div id="Medical">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-ss">
    
            <tr>
              <td height="21" colspan="4" align="left"><div align="center" class="STYLE1">QueryMedicalConsultant<br>
              </div></td>
            </tr>
            <tr>
              <td  height="48" align="left">Start Date：
              <input name="text" type="text" id="start_date" onClick="Calendar('start_date')" readonly="readonly" />              </td>
              <td   height="48" align="left"> End Date：
              <input id="end_date" type="text" name="it" readonly="readonly" onClick="Calendar('end_date')" /></td>
              <td   align="left" style="color:red"><!-- <div align="right">點擊日期無反應請按<a href="javascript:parent.location.reload();">F5</a></div> --></td>
              <td  >&nbsp;</td>
            </tr>
            <tr style="display:none;">
              <td height="23" align="left">Staff Code ：
              <input type="text" name="staffcode" id="staffcodes"></td>
              <td height="23" align="left">Full&nbsp;Name ：
              <input type="text" name="names" id="name"></td>
              <td align="left" style="color:red"></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              
              <td colspan="4" align="left" style="color:red"> </td>
            </tr>
           
            <tr>
              <td colspan="3" align="right"><input id="searchs" name="search" type="submit" value="Search"/>              </td>
              <td  ><input onclick="javascript:location.href='addMedical_Consultant.jsp';" name="downs" type="button" value="Add">              </td>
            </tr>
    </table>
    
  </div>
  <div id="content">
  <table width="100%"  border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      <tr id="title">
        <td width="8%"  height="23" align="center"><b>Staff Code</b></td>
        <td   align="center"><b>Name</b></td>
        <td    align="center"><b>SP</b></td>
        <td   align="center"><strong>Date</strong></td>
        <td  align="center"><strong>Consultaing Fee </strong></td>
        <td    align="center"><strong>Claimed Amount </strong></td>
   
        <td   align="center"><strong>Claimed Month </strong></td>
     
        <td  align="center"><strong>State</strong></td>
        <td width="35%"  align="center"><strong>Remark</strong></td>
      </tr>
    </table>
  </div>
  </body>
</html>
