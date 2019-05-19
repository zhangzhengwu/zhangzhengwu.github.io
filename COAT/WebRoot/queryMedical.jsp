<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head> 
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
 	<script type="text/javascript">
		
		/******************************************************************************************/
 	 /***********************************************************delete staff message************************/
  	  var downs=null;
	  var pagenow =1;
	  var totalpage=1;
	  var total=0;
    function shanchu(i){
		if(confirm("您确定要删除该记录？不可恢复！")){
	
				$.ajax({
				type:"post",
				url:"DeleteMedicalServlet",
				data:{'StaffNo':downs[i].staffcode,'Date':downs[i].upd_Date},
				success:function(date){
					if(date == "01"){
						alert("删除成功！");
						select(1);
					}else{
						alert("数据库异常，请与管理员联系！");
					}
				}
			 });
		 }
	 }
    /*****************************************************************************************************/

     
/***************************************************************************************************/
 /*****************************************************WindowForm Load *************************************/
 $(function(){
    $("body").css("overflow","");
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	
	$("#quit").click(function(){
		$("body").css("overflow","");
	});
/****************************************************Search click***********************************/
	$("#searchs").click(function(){
		select(1);
	});
	/***********************************************************Search Click end************************/
	/**********************************MedicalFee blur**************************************************/
	
	/**************************************************************************************************/
	  /*********************************************special框发生改变时**************************************/
  
  /****************************************************************************************************/
  
  /*****************************************************************************************************/
	 /**************************************************************************************************/
  /****************************************************************************************************/
	/*********************************************motify click******************************************/
	
	
			//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				select(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select(pagenow);
			});

	
	/***************************************************************************************************/
	/***********************************************select function************************************/
	
	/**************************************************************************************************/
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
   
/*********************************改变时间格式*********************************************/
	 
/**********************************************获取财务结算月份**************************************************/
	  function getMon(){
	  var now =new Date();
	  var day=now.getDate();
		  if(day<26){
		  return now.getMonth()+1;
	  }else{
		  return (now.getMonth()+2)>12?1:(now.getMonth()+2);
	  }

 }
	  function getYea(){
	  var now =new Date();
	  var day=now.getYear();
	  return day;

 }
	/*****************************************************Down click**********************************/
	
	$("#down").click(function(){ 
		if(downs!=null){
			window.location.href="DownMedicalServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
		}
	
	});
	/******************************************************Down END***********************************/

});
    function select(pagenow){
    		if(typeof(pagenow) == "undefined"){
    			pagenow = 1;
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
			url:"QueryMedicalServlet",
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
					var caozuo="";
					downs=dataRole[0];
					total=dataRole[3];
					pagenow =dataRole[2];
			   		totalpage=dataRole[1];
					for(var i=0;i<dataRole[0].length;i++){
						if(dataRole[0][i].sfdj=="N"){
							if("${adminUsername}"=="admin"||"${roleObj.audit}"=="1"){
								caozuo="<c:if test='${roleObj.upd==1}'><a href='javascript:void(0);' onclick='modify("+i+")'>"+"修改"+"</a></c:if>";
							}else{
								caozuo="已冻结";
							}
						}else{
							caozuo="<c:if test='${roleObj.upd==1}'><a href='javascript:void(0);' onclick='modify("+i+")'>"+"修改"+"</a></c:if> /" +
							"<c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' onclick='shanchu("+i+")'>"
								+"刪除"+"</a></c:if>";
						}
						html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].AD_type +"</td><td align='center'>"+dataRole[0][i].SP_type+"</td><td align='center'>"
							+dataRole[0][i].medical_date +"</td><td align='center'>"+dataRole[0][i].medical_Fee +"</td><td align='center'>"
							+dataRole[0][i].terms_year +"</td><td align='center'>"
							+(((dataRole[0][i].upd_Date.substring(5,7)=="12" && parseFloat(dataRole[0][i].upd_Date.substring(8,10))>25)?(parseFloat(dataRole[0][i].upd_Date.substring(0,4))+1):(dataRole[0][i].upd_Date.substring(0,4)))+"-"+dataRole[0][i].medical_month )+"</td><td align='center'>"+dataRole[0][i].entitled_Fee +"</td><td align='center'>"+dataRole[0][i].upd_Date+"</td><td align='center'>"+caozuo+"</td></tr>";		 
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
			 },error:function(){
				 alert("Network connection is failed, please try later...");
				 return false;
			 }
		 });
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
  </head>
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input id="start_date" type="text"    onClick="Calendar('start_date')" />&nbsp;&nbsp;
                	<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"    onClick="Calendar('end_date')" />&nbsp;&nbsp;
                 	<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="staffcode" id="staffcodes">
				</td>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input type="text" name="names" id="name">
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" colspan="2">
				<c:if test="${roleObj.search==1}">
					<a class="btn" id="searchs" name="search">
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
				<th class="width_">Special?</th>
				<th class="width_">Medical Date</th>
				<th class="width_">Medical Fee</th>
				<th class="width_">Terms</th>
				<th class="width_">Payer Month</th>
				<th class="width_">Autopay</th>
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
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
var basepath = "<%=basePath%>";
function modify(index)
{
	var data = downs[index];
	var url = basepath + "queryMedical_modify.jsp";
	editlhg(800,360,url,data);
}
</script>  		
</body>
</html>