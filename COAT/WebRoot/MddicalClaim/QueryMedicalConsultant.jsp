<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryMedicalConsultant</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
    <style type="text/css">
<!--
#Medical {
	position:absolute;
	width:100%;
	z-index:0;
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
 
.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}

img{
height:12px;
width:12px;
}
-->
    </style>
  </head>
  <script type="text/javascript" >
  var downs=null;
    var pagenow =1;
  var totalpage=1;
  var total=0;
  var Consultantemail=null;
    /**
 * 快捷键
 * */
function monitor(e) {
	e = e ? e : event;// 兼容FF 
	if (e.shiftKey && e.altKey && e.keyCode == 76) {//组合快捷键   shift+alt+L
		$("#sendMail").show();
	} 

}
  $(function(){
	  document.onkeyup = monitor;
  $("#start_date").val(CurentTime);
     $("#end_date").val(CurentTime);
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
			//window.location.href="../DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		//	window.location.href="../TestDownServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
			$.ajax({
				type: "post",
				url:"TestDownServlet",
				async:false,
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'code':$("#staffcodes").val(),'name':$("#name").val()},
				success:function(date){
			clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
					alert(date);
				},error:function(){
					alert("导出失败!");
				}
				});
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	
	$("#down_all").click(function(){ 
		if(downs!=null){
		window.location.href="<%=basePath%>DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		//	window.location.href="../TestDownServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/******************************************************Down END***********************************/
	
	$("#sendMail").click(function(){
		if(confirm("确定立即发送邮件?")){
			$.ajax({
				url:"SendMailServlet",
				type:"post",
				data:{"method":"medical"},
				success:function(date){
					if(date=="未授权的访问"){
						alert(date);
					}else{
						alert("发送成功!!!");
					}
				},error:function(){
					alert("网络连接失败!!!");
				}
			});
		}
	});
	
	
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
	
	selects(1);
	});
/******************************************************************************************************************/
	
			//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				selects(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				selects(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				selects(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				selects(pagenow);
			});
/***********************************************select function************************************/
	function selects(pagenow){
			/**	if($("#start_date").val()==""){
					alert("開始日期不能為空！");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("結束日期不能為空！");
					$("#end_date").focus();
					return false;
				}
			**/	 
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("開始日期不能大於結束日期!");
						$("#start_date").focus();
						return false;
					}
				}
		 
				$.ajax({
				type: "post",
				url:"QueryMedicalConsultantServlet",
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'code':$("#staffcodes").val(),'name':$("#name").val(),'pageNow':pagenow},
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(data){
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole[3]>0){
						total=dataRole[3];
					pagenow =dataRole[2];
				    totalpage=dataRole[1];
					downs=dataRole[0];
						for(var i=0;i<dataRole[0].length;i++){
							html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].AD_type +"</td><td align='center'>"+dataRole[0][i].SP_type+"</td><td align='center'>"
							+dataRole[0][i].medical_date+"</td><td align='center'>"+dataRole[0][i].medical_Fee+"</td><td align='center'>"+dataRole[0][i].entitled_Fee+"</td><td align='center'>"+dataRole[0][i].terms_year+"</td><td align='center'>"
							+(((dataRole[0][i].upd_Date.substring(5,7)=="12" && parseFloat(dataRole[0][i].upd_Date.substring(8,10))>25)?(parseFloat(dataRole[0][i].upd_Date.substring(0,4))+1):(dataRole[0][i].upd_Date.substring(0,4)))+"-"+dataRole[0][i].medical_month ) +"</td><td align='center'>"+dataRole[0][i].medical_Normal +"</td><td align='center'>"+dataRole[0][i].medical_Special+"</td><td align='center'>"+dataRole[0][i].upd_Date+"</td><td align='center'><c:if test='${roleObj.audit==1}'><a href='javascript:void(0);' onclick='reject("+i+");'>reject</a></c:if></td></tr>";		 
						}
						$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='18' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
					 $(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                    page(pagenow,totalpage);
				 }
			 });
	}
	/**************************************************************************************************/
  });
  function reject(i){
	if($("#subject").val()==""){
		alert("请选择邮件主题!");
		return false;
	}else{
 
	
	$.ajax({
		url:"QueryEmailByConsultant",
		type:"post",
		async:false,
		data:{"staffcode":downs[i].staffcode},
		success:function(date){
		var em=eval(date);
			if(em!="" && em!=null){
				Consultantemail=em;
			}else{
				alert("该Consultant没有Email，请及时更新ConsultantList");
				return false;
			}
		},error:function(){
			alert("网络连接失败，请稍后重试...");
			return false;
		}
	});
	
	var recontext="";
	recontext+="Staff Code: "+downs[i].staffcode+"%0d";
	recontext+="Consulting date: "+downs[i].medical_date+"%0d";
	recontext+="Consulting Fee:"+downs[i].medical_Fee+"%0d";
	recontext+="Reject Reason:%0d";
	recontext+="1. Receipts must be dropped into the drop-in box within 3 months from the consulting date.%0d";
	recontext+="2. Without the Registration No. on the receipt which is provided by the Chinese Doctor.%0d";
	recontext+="3. More than 1 medical claim at the same day unless the doctor refer to see the specialist.%0d";
	recontext+="4. Exceed year quota for medical claim. (15 times for normal Visit and 10 times for special Visit)%0d";
	recontext+="5. No medical claim quota for consultant Trainee.%0d";
	recontext+="6. The consulting date is during your training period.%0d";
	recontext+="7. Your plan of medical claim is not included Dental.%0d";
	recontext+="8. X-Ray, Lab-Test, physiotherapy treatment and sonic sound claims only can be considered as General visit items.%0d";
	recontext+="9. To claim as specialist items, referral letter and receipt must be logged in with specialist stated on the receipt.%0d";
	recontext+="%0d";
	/* recontext+="If you have inquiries please email to SZOAdm@convoy.com.hk%0d"; */
 	window.open("mailto:"+$.trim(Consultantemail)+"?subject="+$("#subject").val()+"&body="+recontext); 
 	}
  }
    function page(currt,total){
			if(currt<=1){
					$("#first").hide();
					$("#pre").hide();
				}else{
					$("#first").show();
					$("#pre").show();
				}
				if(currt>=total){
					$("#end").hide();
					$("#next").hide();
				}else{
					$("#end").show();
					$("#next").show();
				}
	}
  </script>
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class='tagName'>Start Date：</td>
				<td class='tagCont'>
					<input name="text" type="text" id="start_date" onClick="Calendar('start_date')" readonly="readonly" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class='tagName'>End Date：</td>
				<td class='tagCont'>
					<input id="end_date" type="text" name="it" readonly="readonly" onClick="Calendar('end_date')" />
            		<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class='tagName'>Staff Code：</td>
				<td class='tagCont'>
					<input type="text" name="staffcode" id="staffcodes">
				</td>
				<td class='tagName'>Staff Name：</td>
				<td class='tagCont'>
					<input type="text" name="names" id="name">
				</td>
			</tr>	
			<tr>
				<td class='tagName'>Subject：</td>
				<td class='tagCont'>
					<select name="subject" id="subject">
		                <option value="">请选择</option>
		                <option value="You medical claim was rejected">You medical claim was rejected</option>
		                <option value=" Medical claims notice"> Medical claims notice</option>
		            </select>
				</td>
				<td class='tagName'></td>
				<td class='tagCont'>
					
				</td>
			</tr>
			<tr>
				<td class='tagCont' colspan="2"></td>
				<td class='tagCont' colspan="2">
				<c:if test="${roleObj.search==1}">
					<a class="btn" id="searchs">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test="${roleObj.export==1}">
					<a class="btn" id="down" name="downs">
						<i class="icon-download"></i>
						Export
					</a>
				</c:if>
				<c:if test="${roleObj.export==1}">
					<a class="btn" id="down_all" name="downs_all">
						<i class="icon-download"></i>
						Export All
					</a>
				</c:if>
		            <c:if test="${adminUsername=='admin'||adminUsername=='jackieli'||adminUsername=='janewen'||adminUsername=='susiezhu'}">
		            	<a class="btn" id="sendMail" style="display: none;">
							<i class="icon-mail"></i>
							Send Mail
						</a>
	            	</c:if> 
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th class="width_">Staff Code</th>
				<th class="width_">Name</th>
				<th class="width_">AD_Code</th>
				<th class="width_">SP</th>
				<th class="width_">Medical Date</th>
				<th class="width_">Consultaing Fee</th>
				<th class="width_">Claimed Amount</th>
				<th class="width_">Terms</th>
				<th class="width_">Claimed Month</th>
				<th class="width_">Normal</th>
				<th class="width_">Special</th>
				<th class="width_">Submit Date</th>
				<th class="width_">Operation</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN style="color: red;" id="total">
						</SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="end" href="javascript:void(0);">尾页</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>  
</body>
</html>
