<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>查詢頁面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">


<script type="text/javascript">
 	 /***********************************************************delete staff message************************/
   		var downs=null;
 	 var pagenow =1;
		var totalpage=1;
		var total=0;
    function shanchu(i){
		if(confirm("您确定要删除该记录？不可恢复！")){
	
				$.ajax({
				type:"post",
				url:"DeleteStaffServlet",
				beforeSend: function(){
						parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				data:{'StaffNo':downs[i].staff_code,'Date':downs[i].urgentDate},
				success:function(date){
					if(date == "删除成功！"){
						$("tr[title='"+i+"']").remove();
						alert("删除成功.");	
					}else{
						alert("删除失败.");	
					}
				}
			 });
				return false;
		 }
	 }
 /*****************************************************WindowForm Load *************************************/
 	var professionalCText="";
var professionalEText="";
var departmentCText="";
var departmentEText="";
 $(function(){
	 	
/**********************************窗體加載************************************************/
	/*position();
	professional();
	department();*/

 	//getlocation("location");
 	//getlocation("location_modify");
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	
 $("#body_view,#body_modify").width($("body").width());
 		
 		
 		

/****************************************************Search click***********************************/
		$("#searchs").click(function(){
		selects(1);	
		});
/**
 
				if($("#start_date").val()==""){
					alert("開始日期不能為空！");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("結束日期不能為空！");
					$("#end_date").focus();
					return false;
				}**/
				
				
	
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
		$("[type='checkbox'][name!='companys']").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
	});

   
	/*****************************************************Down click**********************************/
	
	$("#down").click(function(){
if(downs!=null){
	/**window.location.href="DownStaffRequestServlet?startDate="+
$("#start_date").val()+"&endDate="+$("#end_date").val()+"&name="+$("#name").val()+"&code="+
$("#staffcodes").val()+"&location="+$("#location").val()+"&urgentCase="+$("#urgentCase").val()+"&ET="+$("#ET_select").val();
	**/
	$.ajax({
				type: "post",
				url:"DownStaffRequestLocationServlet",
				async:false,
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
				'code':$("#staffcodes").val(),'name':$("#name").val(),'location':$("#location").val(),
				'urgentCase':$("#urgentCase").val(),'nocode':$("#nocode").val(),
				'ET':$("#ET_select").val(),'payer':$("#Namepayer").val(),"layout":$("#layout_select").val()},
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
	/******************************************************Down END***********************************/
$("#back").click(function(){
		  $("#body_view").show();$("#body_modify").hide();
});

	/*****************************************表单提交***************************/
$("#AddNameCard").click(function(){ 
	vailed();
});		
$("body").keydown(function(e){
	if(e.keyCode==13){
	selects(1);
	}
	});
/*****************************表单结束********************************/	
	/**
$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
				$("#urgentCase").val("Y");
				}else{
						$("#urgentCase").val("N");
				}
});
**/

});
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
 
 function selects(pagenow){

		if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("開始日期不能大於結束日期!");
				$("#start_date").focus();
				return false;
			}
		}
	//當ET_select選中時
/**if($("#ET_select").attr("checked")==true){
	$("#ET_select").val("Y");
}else{
	$("#ET_select").val("N");
}**/
		$.ajax({
		type: "post",
		url:"QueryStaffRequstServlet",
		data: {'name':$("#name").val(),'code':$("#staffcodes").val(),
			'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
			'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),
			'ET':$("#ET_select").val(),"layout":$("#layout_select").val(),
			'payer':$("#Namepayer").val(),
			'pageNow':pagenow},
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
					html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
					+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td align='center'>"
					+dataRole[0][i].title_english +"</td><td align='center'>"+dataRole[0][i].title_chinese +"</td><td align='center'>"
					//+dataRole[0][i].external_english +"</td><td align='center'>"+dataRole[0][i].external_chinese +"</td><td align='center'>"
					//+dataRole[0][i].profess_title_e +"</td><td align='center'>"+dataRole[0][i].profess_title_c +"</td><td align='center'>"
					+dataRole[0][i].tr_reg_no +"</td><td align='center'>"+dataRole[0][i].ce_no +"</td><td align='center'>"+dataRole[0][i].mpf_no 
					//+"</td><td align='center'>"+dataRole[0][i].e_mail 
					+"</td><td align='center'>"+dataRole[0][i].direct_line 
					+"</td><td align='center'>"+dataRole[0][i].fax +"</td><td align='center'>"+dataRole[0][i].bobile_number 
					+"</td><td align='center'>"+dataRole[0][i].quantity 
					+"</td><td>"+dataRole[0][i].upd_date+"</td><td align='center'>" +
					"<c:if test='${roleObj.upd==1}'><a href='javascript:void(0);' onclick='modify("+i+")'>"
					+"Modify"+"</a></c:if> &nbsp;<c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' onclick='shanchu("+i+")'>"
					+"Delete"+"</a></c:if></td></tr>";		 
				}
	 $(".page_and_btn").show();
			   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
			}
			else{
				html+="<tr id='select'><td colspan='19' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
			 $(".page_and_btn").hide();
			}
				 $("#jqajax:last").append(html);
			 	 $("tr[id='select']:even").css("background","#COCOCO");
              $("tr[id='select']:odd").css("background","#F0F0F0");
              	 page(pagenow,totalpage);
		 }
	 });
		
}
/****************************************************************************************************************/

/***********************************************************Search Click end************************/

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
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
  
  
  

	 

	function getlocation(select){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "<%=basePath%>common/CommonReaderServlet",
			data:{"method":"getlocation"},
			async:false,
			success:function(date){
			var location=eval(date);
			var html="";
			$("#"+select+"").empty();
			if(location.length>0){
				html+="<option value='' >Select Location...</option>";
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
			 
				}
			}else{
				html+="<option value=''>加载异常</option>";
			}
			$("#"+select+"").append(html);
			},error:function(){
				alert("网络连接失败,请返回登录页面重新登录!");
				return false;
			}
		});
	}
		
		
		var basepath = '<%=basePath%>';

/***********************************添加按钮***************************************************************/

function modify(deom)
{
	var url = basepath + "queryStaffCard_modify.jsp";
	var data = {
		downs : downs[deom]
	};
	
	editlhg(900,660,url,data);
}

 </script>
<style type="text/css">
<!--
#body_modify {
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 100;
	display: none;
}
-->
</style>

</head>
<body>
	<div class="cont-info">
		<div class="info-search">
			<table>
				<tr>
					<td class="tagName">Start Date：</td>
					<td class="tagCont"><input id="start_date" type="text"
						readonly="readonly" onClick="Calendar('start_date')" />&nbsp;&nbsp;
						<i class="icon-trash icon-large i-trash" id="clear1"
						align="middle" onclick="javascript:$('#start_date').val('');"></i>
					</td>
					<td class="tagName">End Date：</td>
					<td class="tagCont"><input id="end_date" type="text" name="it"
						readonly="readonly" onClick="Calendar('end_date')" />&nbsp;&nbsp;
						<i class="icon-trash icon-large i-trash" id="clear2"
						align="middle" onclick="javascript:$('#end_date').val('');"></i></td>
				</tr>
				<tr>
					<td class="tagName">Staff Code：</td>
					<td class="tagCont"><input type="text" name="staffcodes"
						id="staffcodes"></td>
					<td class="tagName">Full Name：</td>
					<td class="tagCont"><input type="text" name="names" id="name">
					</td>
				</tr>
				<tr>
					<td class="tagName">Payer：</td>
					<td class="tagCont"><input type="text" name="staffcode"
						id="Namepayer"></td>
					<td class="tagName">New Name Card：</td>
					<td class="tagCont"><select id="ET_select">
							<option value="">Select New Name Card</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
				</tr>
				<tr>
					<td class="tagName">Location：</td>
					<td class="tagCont">
					
					<%--<select name="select" id="location">
							<option value="">Select Location...</option>
							<option value="O">OIE</option>
							<option value="C">CP3</option>
							<option value="W">CWC</option>
					</select>
					
					--%>
					<input type="text" name="select" id="location" />
					</td>
					<td class="tagName">Urgent Case：</td>
					<td class="tagCont"><select id="urgentCase">
							<option value="">Select Urgent...</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
				</tr>
				<tr>
					<td class="tagName">Layout：</td>
					<td class="tagCont"><select name="layout_select"
						id="layout_select">
							<option value="">Select Layout...</option>
							<option value="S">Standard layout</option>
							<option value="P">Premium layout</option>
					</select></td>
					<td class="tagName"></td>
					<td class="tagCont"></td>
				</tr>
				<tr>
					<td class="tagCont" colspan="2"></td>
					<td class="tagCont" colspan="2">
						<c:if test="${roleObj.search==1}">
							<a class="btn" id="searchs" name="search"> <i class="icon-search"></i> Search </a>
						</c:if>
						<c:if test="${roleObj.export==1}">
							<a class="btn" id="down" name="downs"> <i class="icon-download"></i> Export </a>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		<div class="info-table">
			<table id="jqajax">
				<thead>
					<tr id="title">
						<th class="width_5">Staff Code</th>
						<th class="width_10">Name</th>
						<th class="width_5">Name(Chi)</th>
						<th class="width_15">Department(Eng)</th>
						<th class="width_10">Department(Chi)</th>
						<th class="width_5">T.R. Reg. No.</th>
						<th class="width_5">CE No.</th>
						<th class="width_5">MPF No.</th>
						<th class="width_5">Direct Line</th>
						<th class="width_5">Fax</th>
						<th class="width_8">Mobile Phone</th>
						<th class="width_5">Quantity</th>
						<th class="width_5">Sumbit Date</th>
						<th class="width_8">Operation</th>
					</tr>
				</thead>
			</table>
			<div align="center" class="page_and_btn" style="display: none;">
				<table class="main_table" width="100%" border="0" cellpadding="0"
					cellspacing="0" id="select_table">
					<tr class="main_head">
						<td colspan="6" align="center"><a id="first"
							href="javascript:void(0);">首页</a> <a id="pre"
							href="javascript:void(0);">上一页</a> 总共 <SPAN style="color: red;"
							id="total"> </SPAN>页 <a id="next" href="javascript:void(0);">下一页</a>
							<a id="end" href="javascript:void(0);">尾页</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>