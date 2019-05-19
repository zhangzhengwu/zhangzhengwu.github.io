<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
	<style type="text/css">

	tr.closed{display:none;}
	.detailContainer{ margin: 10px auto; }
	.detailContainer .stepBox{float: left;}
	.detailContainer .step{float:left; margin: 0px 5px;  text-align: center;}
	.detailContainer .step span{font-size: 0.75em; display: block; height: 36px;}
	
	.detailContainer div.arrow{float: left;width: 30px; padding: 4px 5px;}
	.detailContainer a:hover{text-decoration: none;}
	.detailContainer a.arrow{color: #ddd;}
	.detailContainer a.done, .detailContainer a.notdo{display: block; width: 100%; padding: 6px 8px;}
	.detailContainer a.done{background: rgb(99, 197, 162); color: #fff;}
	.detailContainer a.notdo{background: #ddd; color: #fff;}
	
	</style>
 	<script type="text/javascript">
 	 /***********************************************************delete staff message************************/
   		var downs=null;
 		var pagenow =1;
		var totalpage=1;
		var total=0;
		var tempChartAry = new Array();
    function shanchu(i){
		if(confirm("您确定要删除该记录？不可恢复！")){
	
				$.ajax({
				type:"post",
				url:"DeleteStaffServlet",
				data:{'StaffNo':downs[i].staff_code,'Date':downs[i].urgentDate},
				success:function(date){
					alert(date);
					$("tr[title='"+i+"']").remove();
				}
			 });
		 }
	 }
 /*****************************************************WindowForm Load *************************************/
 $(function(){
	 	
/**********************************窗體加載************************************************/
//	position();
	professional();
	department();
	var professionalCText="";
	var professionalEText="";
	var departmentCText="";
	var departmentEText="";
 	//getlocation("location");
 	//getlocation("location_modify");
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	
 	$("#EnglishStaffDepartment").hide();
	$("#EnglishProfessionalTitle").hide();
/****************************************************Search click***********************************/
		$("#searchs").click(function(){
		selects(1);	
		});
	
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
	$("[type='checkbox']").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
	});
	
	
		/****************************************************************************************************************/

	/***********************************************************Search Click end************************/
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

	/****************************************pater验证结束***********************/
$("#EnglishPosition").change(function(){
 
	$.ajax({
		type:"post",
		url:"SelectStaffPositionServlet",
		data:{'EnglishName':$("#EnglishPosition").val().replace('\r\n','').replace('\r\n','').replace('\r\n','')},
		success:function (date){
			if(date!="" && date!="null" && date !=null){
				$("#ChinesePosition").val(date);
			}else{
				$("#ChinesePosition").val("s");
			}
		}
		
	});
		
});
	$("#EnglishProfessionalTitle").change(function(){
	$.ajax({
		type:"post",
		url:"SelectProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishProfessionalTitle").val()},
		success :function(date){
			 
			if(date!="[]" || date!="null" || date){
				var csd= $("#ChineseProfessionalTitle").val();
				var esd=$("#EnglishProfessionalTitleText").val();
				professionalCText = csd==""?date:(csd+(csd.substring(csd.length-1)==";"?"":";")+date);
				professionalEText = esd==""?$("#EnglishProfessionalTitle").val():(esd+(esd.substring(esd.length-1)==";"?"":";")+$("#EnglishProfessionalTitle").val());
				
				$("#ChineseProfessionalTitle").val(professionalCText);
				$("#EnglishProfessionalTitleText").val(professionalEText);
		 	}
		}
	});
});
	
	/*****************************************************/
$("#EnglishStaffDepartment").change(function(){
	$.ajax({
		type:"post",
		url:"SelectStaffProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishStaffDepartment").val()},
		success :function(date){
			if(date!="[]" || date!="null" || date){
				var csd= $("#ChineseStaffDepartmentText").val();
				var esd=$("#EnglishStaffDepartmentText").val();
				departmentCText = csd==""?date:(csd+(csd.substring(csd.length-1)==";"?"":";")+date);
				departmentEText = esd==""?$("#EnglishStaffDepartment").val():(esd+(esd.substring(esd.length-1)==";"?"":";")+$("#EnglishStaffDepartment").val());
				
				$("#ChineseStaffDepartmentText").val(departmentCText);
				$("#EnglishStaffDepartmentText").val(departmentEText);

		 	}
			
		}
		
	});
	
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


$("#fujian_list").change(function(){
//	 if(confirm("确定下载查看?")){
			   $("#downs").attr("href","upload/"+$("#fujian_list").val()).empty().append(($("#fujian_list").val().length)>50?($("#fujian_list").val().substring(0,30)+"..."+$("#fujian_list").val().substring($("#fujian_list").val().length-16)):$("#fujian_list").val());
		
			  //window.open("../upload/"+$("#fujian_list").val());
			  //	$("downs").attr("",("../upload/"+$("#fujian_list").val());
		// }
	});


});
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
  
  
  
function vailed(){
	var nums=$("#num").val();                                                      
	if($("#StaffNo_modify").val()==""){																 
		alert("Staff Code不能為空！");	
		$("#StaffNo").focus();
		return false;																			 
	}		
		if($("#ET").attr("checked")==true){
	$("#ET").val("Y");
	}else{
	$("#ET").val("N");
	}
	if($("#num").val()==""){															 
		alert("Quantity不能為空！");	
			$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		alert("Quantity 必須是100的整数倍!");	
		$("#num").focus();
		return false;															 
	}else{
			if($("#num").val()%100 !=0){
			alert("quantity必须是100的整数倍！");
			$("#num").focus();
			return false;
		}	
	}		
/**************************判断事件是否合法*****************/
var datetime=(new Date()).getFullYear()+"-"+((new Date()).getMonth()+1)+"-"+(new Date()).getDate()
/**********************************************************/
/*****************判断复选框是否被选中**********************/
	 
		if($("#urgent").attr("checked")==true){//當urgentCase選中時
				$("#urgent").val("Y");
				if(parseInt($("#num").val())<100 && parseInt($("#num").val())>0){
					alert("在Urgent情况下，Quantity不能低于100!");
					$("#num").focus();
					return false;
				}
		}
		if($("#location_modify").val()==""){
			alert("请选择location!");
			$("#location_modify").focus();
			return false;
		}
		//貌似trim()方法不支持
		 
		if(parseInt($("#num").val())<=0){
			alert("Quantity不能小于0!"); 
			$("#num").focus();
				return false;
		}
		if($("#EnglishPosition").val()=="positionName" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			alert("請選擇Title_Department in English ！");
			$("#EnglishPosition").focus();
			return false;
		}else{
			var positiontext = $("#EnglishPosition").find("option:selected").text();
			$("#EnglishPositions").val(positiontext);
		}
		if(confirm("確定通过申请?")){
			$("#AddNameCard").attr("disabled","disabled");
			modify_staff();		
		}
}
	 
function position(){
	$.ajax({
		type: "post",
		url: "QueryStaffPositionServlet",
		data: null,
		success: function(date){
		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
				html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
			}
			$("#EnglishPosition:last").append(html);
			}
		}
	});
}
 
function department(){
	$.ajax({
		type: "post",
		//url: "QueryStaffProfessionalServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getstaffprofessional"},
		success: function(date){
		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishStaffDepartment:last").append(html);
			}
		}
		
	});
	
}
function professional(){
	$.ajax({
		type: "post",
		//url: "QueryProfessionalServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getprofessional"},
		success: function(date){
		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishProfessionalTitle:last").append(html);
			}
		}
		
	});
	
}
		function getlocation(select){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			async:false,
			success:function(date){
			var location=eval(date);
			var html="";
			$("#"+select+"").empty();
			if(location.length>0){
			 
				html+="<option value='' >Please Select Location</option>";
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
		
		function modify_staff(){
			var company_Type="";
			$("input[class='check_company']").each(function(){
				if($(this).attr("checked")){
					if(company_Type==""){
						company_Type+=this.name;
					}else{
						company_Type+="+"+this.name;
					}
				}
				
			});
			$("#company_Type").val(company_Type);
			$.ajax({
				//url:"ApproveStaffRequestServlet",
				url:"StaffNameCardWriteServlet",
				type:"post",
				data:$("#myform").serialize(),
				success:function(date){
				  var result=$.parseJSON(date);
					if(result.state=="success"){
						reback();
						 $("#AddNameCard").attr("disabled","");
						 $("#searchs").click();
					}
					alert(result.msg);
				},error:function(){
					alert("网络连接失败，请稍后重试...");
					return false;
				}
				
			});
		}
		
 
function fujianlist(){
		$.ajax({
				url:"QueryFileNameServlet",
				type:"post",
				data:{"staffcode":$("#StaffNo_modify").val()},
				success:function(date){
					var dataRole=eval(date);
					var html="";
					$("#fujian_list option").remove();
					if(dataRole!="" && dataRole!=null){
						html+="<option>请选择</option>";
						for(var i=0;i<dataRole.length;i++){
								html+="<option  class='oladf'>"+dataRole[i]+"</option>";
						}
					}else{
						html+="<option  class='oladf'>没有任何上传记录</option>"
					}
					$("#fujian_list").append(html);
				},error:function(){
					alert("网络连接失败，请稍后重试...");
					return false;
				}
				
			});
}
  function translateEn(data){
	  var result = '';
	  if(typeof(data) == 'undefined' || data == '')
	  {
		  result = '';
	  }else{
		  if(data=="S")
		  	result="Pending";
	 	  else if(data=="E")//等待Department Head 审核
			result="Dept Approved";
		  else if(data=="R")//等待HR 审核
			result="HR Approved";
		  else if(data=="Y")
			result="Approved";
		  else if(data=="N")
			result="Rejected";
		  else if(data=="D")
			result="Delivery";
		  else if(data=="G")
			result="Receive";
	  }
	  return result;
  }
  function newChart(state){
	  if(typeof(state) == 'undefined' || state == null){
		  state = '';
	  }
	  switch(typeof(state))
	  {
	  	case 'object':
	  		var _obj = state;
	  		this.id = _obj.id;
			this.creator = _obj.creator;
			this.createdate = _obj.createdate;
			this.state = _obj.state;
			this.done = 'Y';
			this.edit = 'N';
	  		break;
	  	default: 
	  		this.id = 0;
			this.creator = '';
			this.craetedate = '';
			this.state = state;
			this.done = 'N';
			this.edit = 'N';
	  		break;
	  }
  }
  function makeChartAry(){
	  var chartAry = ['S','E','Y','N','D','G'];
	  //var chartAry = ['S','E','R','Y','N','D','G']; //听说暂时HR不参与审核了
	  //var chartAry = ['S','E','R','Y','N'];  //D&G根据Reject状态决定是否追加
	  var tempAry = new Array();
	  
	  for(var i=0; i<chartAry.length; i++)
	  {
		  var _obj = new newChart(chartAry[i]);
		  tempAry.push(_obj);
	  }
	  return tempAry;
  }
	function initProcess(ary){
		       console.log(ary);
		var tempAry = new Array(), newAry = new Array();
		var chartAry = makeChartAry();
		var yFlag = false, nFlag = false, index = -1, yIndex = -1, nIndex = -1, dIndex = -1, gIndex = -1, lastIndex = -1;
		
		for(var i=0; i<ary.length; i++)
		{
			for(var j=0; j<chartAry.length; j++){
				//将后台返回的流程进行标记
				if(ary[i].state == chartAry[j].state)
				{
					chartAry[j] = new newChart(ary[i]);
				}
			}
			//判断后台返回的流程中是否有Approved或者Reject, 进行标记
			if(ary[i].state == 'Y'){
				yFlag = true;
			}else if(ary[i].state == 'N'){
				nFlag = true;
			}
		}
		for(var i=0; i<chartAry.length; i++){
			if(chartAry[i].state == 'Y'){
				yIndex = i;
			}else if(chartAry[i].state == 'N'){
				nIndex = i;
			}else if(chartAry[i].state == 'D'){
				dIndex = i;
			}else if(chartAry[i].state == 'G'){
				gIndex = i;
			}
		}
		//对Approved和Rejected共存进行处理
		if(yFlag == false && nFlag == false){
			chartAry.splice(nIndex,1);
		}else if(yFlag == true){
			chartAry.splice(nIndex,1);
		}else if(nFlag == true){ 
			//Reject存在的话, 那么Delivery和Receiver不需要显示
			//注意这里的删除顺序：从后到前
			chartAry.splice(gIndex,1);
			chartAry.splice(dIndex,i);
			chartAry.splice(yIndex,1);
		}
		//将最后一个流程前未处理的流程进行删除
		for(var i=chartAry.length-1; i>=0; i--){
			if(chartAry[i].done == 'Y'){
				if(lastIndex == -1){
					lastIndex = i;	
				}
			}else{
				if(i < lastIndex){
					chartAry.splice(i,1);
					i++;
				}
			}
		}
		//只为标记处最后一个已操作流程的下标
		for(var i=chartAry.length-1; i>=0; i--){
			if(chartAry[i].done == 'Y'){
				lastIndex = i;
				break;
			}
		}
		//设置可编辑的单个流程
		if(lastIndex < chartAry.length-1){
			chartAry[lastIndex+1].edit = 'Y';
		}
		tempAry = chartAry;
		
		return tempAry;
	}
	
	function queryProcess2(code,index,second){
		if(second){
			window.setTimeout(function(){queryProcess(code,index)},second);
		}
	}
	
	/* 查看操作流程
	 * Jimmy
	 * 2015年9月10日10:37:53
	 *  */
	 function queryProcess(code,index){
		var nextChild = $('#jqajax tr[id=select]:eq('+index+')').next();
					//判断流程是否已存在
		if($(nextChild).attr('target') == "details")
		{
			$(nextChild).remove();
		}else{
			$.ajax({
			type: "post",
			url:"StaffNameCardReaderServlet",
			data:{method: 'detail', "staffrefno":code},
			success:function(data){
				if(typeof(data) == 'undefined' || data == null){
					error("暂无记录");
					return false;
				}
				var flagStr = new Array();
				var datalist = eval(data);
				if(datalist.length > 0){
					//var tempStr = makeChartAry();
					$("tr[target='details']").remove();
					var tempStr = initProcess(datalist); //自定义对象数组
					tempChartAry = tempStr;
					var html = '<tr target="details"><td colspan="11" style="padding: 20px 20px 10px 5%;"><div class="detailContainer">';
		
					for(var i=0; i<tempStr.length; i++){
						if(tempStr[i].done == "Y")
						{
							html += '<div class="stepBox"><div class="step"><a class="done">'+translateEn(tempStr[i].state)+'('+tempStr[i].creator+')</a><span>'+tempStr[i].createdate+'</span></div>';
						}else{
							html += '<div class="stepBox"><div class="step"><a class="notdo" onclick="addState('+code+','+index+',\''+tempStr[i].state+'\',\''+tempStr[i].edit+'\')" >'+translateEn(tempStr[i].state)+'</a><span></span></div>';
						} 
						
						if(i == tempStr.length-1){
							html += '<div style="clear: both;"></div></div>';
						}else{
							html += '<div class="arrow"><a class="arrow"><i class="icon-arrow-right icon-1x"></i></a></div><div style="clear: both;"></div></div>';
						}
					}
					html += '<div style="clear: both;"></div></div></td></tr>';
					
					$('#jqajax tr[id=select]:eq('+index+')').after(html);
				}else{
					error("暂无数据");
					return false;
				}
				
			 }
		 });
		}
	 }
	
	function addState(code,index,state,flag){
		if(state!="D" && state!="G"){
			return false;
		}
		var data =new Array();
		
		if(flag != 'Y'){
			error("请完成前面的操作再继续...");
			return false;
		}
		data[0]=downs[index].refno;
		data[1]=state;
		data[2]=index;
		var url = basepath + "namecard/staff/approveStaffCard_info.jsp";
		addlhg(450,260,url,data);
	}
 </script>
  <style type="text/css">
<!--
 
#body_modify{
	position:absolute;
	width: 100%;
	height:100%;
	z-index: 100;
	display: none;
	background: #fff;
	font-family: 'Arial Narrow';
} 
.txt{
    color:#005aa7;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: 'Arial Narrow';
	font-size: 16px;
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
height: 12px;
width: 12px;

}

#select{
height:25px;
}
-->
  </style>
  
  </head>
  <body>
  <div class="cont-info" id="lists">
  	<div class="info-search">
  		<table>
  			<tr>
  				<td class="tagName">Start Date：</td>
  				<td class="tagCont">
  					<input id="start_date" type="text" readonly="readonly" onClick="Calendar('start_date')" />
  					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
  				</td>
  				<td class="tagName">End Date：</td>
  				<td class="tagCont">
  					<input id="end_date" type="text" name="it" readonly="readonly" onClick="Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">Staff Code：</td>
  				<td class="tagCont">
  					<input type="text" name="staffcode" id="staffcodes">
  				</td>
  				<td class="tagName">Full Name：</td>
  				<td class="tagCont">
  					<input type="text" name="names" id="name">
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">Layout：</td>
  				<td class="tagCont">
  					<select name="layout_select" id="layout_select">
	                	<option value="">Select Layout....</option>
		                <option value="S">Standard layout</option>
		                <option value="P">premium layout</option>
		            </select>
  				</td>
  				<td class="tagName">New Name Card?：</td>
  				<td class="tagCont">
  					<select name="ET_select" id="ET_select">
				      <option value="">Please Select New Name Card</option>
				      <option value="Y">Yes</option>
				      <option value="">No</option>
				    </select>
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">Location：</td>
  				<td class="tagCont">
  					<%--<select name="select" id="location"  >
		                <option value="">Please Select Location</option>
		                <option value="O">OIE</option>
		                <option value="C">CP3</option>
		                <option value="W">CWC</option>
		            </select>
  				--%>
  					<input type="text" name="select" id="location"/>
  				</td>
  				<td class="tagName">Urgent Case：</td>
  				<td class="tagCont">
  					<select name="urgentCase" id="urgentCase">
		                <option value="">Please Select Urgent</option>
		                <option value="Y">Yes</option>
		                <option value="N">No</option>
		            </select>
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">Status:</td>
  				<td class="tagCont">
  					<select id="isverify">
            			<option value="">Please Select Status</option>
            			<option value="S">Pending</option><!-- 待审核 -->
            			<option value="E">Dept Approved</option><!-- dept head 已审核 -->
            			<option value="R">HR Approved</option><!-- HR 已审核 -->
            			<option value="Y">Approved</option><!-- SZADM 已审核，已录入正式表 -->
            			<option value="N">Rejected</option><!-- 任何一个环节被rejected都是这个状态 -->
            		</select>
  				</td>
  				<td class="tagCont" colspan='2'>
	  				<c:if test="${roleObj.search==1}">
	  					<a class="btn" id="searchs" name="search"><i class="icon-search"></i>Search</a>
	  				</c:if>
	  				<c:if test="${roleObj.export==1}">
  						<a class="btn" id="down" name="downs"><i class="icon-download"></i>Export</a>
  					</c:if>
  				</td>
  			</tr>
  		</table>
  	</div>
  	<div class="info-table">
  		<table id="jqajax">
  			<thead>
  			<tr>
  				<th class="width_5">Refno</th>
  				<th class="width_5">Staff Code</th>
  				<th class="width_8">Name</th>
  				<th class="width_8">Name in Chinese</th>
  				<th class="width_20">Title with Department(Eng)</th>
  				<th class="width_10">Title with Department(Chi)</th><%--
  				<th class="width_8">Professional Title(Eng)</th>
  				<th class="width_8">Professional Title(Chi)</th>
  				--%><th class="width_5">Quantity</th>
  				<th class="width_8">Submit Date</th>
  				<th class="width_8">Status</th>
  				<th class="width_5">Operation</th>
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
  <div class="cont-info" id="details" style="display: none;">
  	<div class="info-title">
  		<div class="title-info">
  			<form action="" id="myform" method="post" onSubmit="return modify_staff">
  			<table>
  				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo_modify"  readonly="readonly"  name="StaffNos"    type="text" size="35" class="txt" />
					</td>
					<td class="tagName">quantity:</td>
					<td class="tagCont">
						<select id="num" name="nums"   >
				          <option value="100">100</option>
				          <option value="200">200</option>
				          <option value="300">300</option>
				          <option value="400">400</option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Location:</td>
					<td class="tagCont">
						<%--<select name="locatins" id="location_modify">
			      		</select>
					--%>
						<input type="text" name="locatins" id="location_modify"/>
					</td>
					<td class="tagName">Layout: </td>
					<td class="tagCont">
						<select name="types" id="type_modify">
				        	<option value="S">Standard Layout</option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">New Name Card:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input name="ET"  type="checkbox" id="ET" title="1" value="N"/>Yes
						</label>
					</td>
					<td class="tagName">UrgentCase:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input name="urgent" type="checkbox" id="urgent" title="1" value="N">Yes
						</label>
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" type="text" size="35" class="txt" id="EnglishName_modify" />
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName_modify" name="ChineseNames" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Position (Eng): </td>
					<td class="tagCont">
						<%--<select name="EnglishPosition" id="EnglishPosition" >
					        <option value="positionName">Please Select PositionName</option>
					    </select>
					--%>
						<input type="text" name="EnglishPositions" id="EnglishPosition"/>
					
					</td>
					<td class="tagName">Position (Chi): </td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" type="text" size="35"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Department (Eng):</td>
					<td class="tagCont">
						<input id="EnglishStaffDepartmentText" name="EnglishStaffDepartmentTexts" onClick="javascript:$('#EnglishStaffDepartment').show();" type="text" size="35"  class="txt"/>
						<br />
						<select id="EnglishStaffDepartment" name="EnglishStaffDepartments">
				          <option value="department">Please Select Department</option>
				        </select>
					</td>
					<td class="tagName">Department (Chi):</td>
					<td class="tagCont">
						<input id="ChineseStaffDepartmentText" name="ChineseStaffDepartmentTexts" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic  Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishAcademicTitle" name="EnglishAcademicTitles" type="text" size="35"  class="txt"/>
					</td>
					<td class="tagName">Academic Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseAcademicTitle" name="ChineseAcademicTitles" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Professional Title (Eng):</td>
					<td class="tagCont">
						<select id="EnglishProfessionalTitle" name="EnglishProfessional">
					        <option value="professional">Please Select ProfessionalTitle</option>
					    </select><br />
						<input id="EnglishProfessionalTitleText" onClick="javascript:$('#EnglishProfessionalTitle').show();" name="EnglishProfessionalTitles" type="text" size="35"  class="txt"/>
					</td>
					<td class="tagName">Professional Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitles" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">External Title (Eng):</td>
					<td class="tagCont">
						<input type="text" class="txt" size="35" name="englishExternal" id="englishExternal"/>
					</td>
					<td class="tagName">External Title (Chi):</td>
					<td class="tagCont">
						<input type="text" class="txt" name="chineseExternal" id="chineseExternal" size="35" />
					</td>
				</tr>
				<tr>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNos" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENOs" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NOs" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobiles" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAXs" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLines" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont">
						<input id="Email" name="Emails" type="text" onKeyUp="this.value=this.value.toLowerCase();" size="35" class="txt" />
					</td>
					<td class="tagName">Attachment:</td>
					<td class="tagCont">
						<select id="fujian_list"></select>
					</td>
				</tr>
				<tr>
					<td class="tagName">
						<input id="pay" type="hidden" name="pays"/> 
						<a id="downs"  target="_Blank" ></a>
						<input id="EnglishPositions" type="hidden" name="EnglishPositions"/>
						Company:
					</td>
					<td class="tagCont" colspan="3">
						<ul>
						  <c:forEach items="${getCompany}" var="co" >
							<li>
						      	<label class="inline checkbox">
									<input class="check_company" type="checkbox" title="${co.englishName}" name="${co.company_Type}" />${co.company_Type}
								</label>
							</li>
					      </c:forEach>
						
							<%--<li>
								<label class="inline checkbox">
									<input id="CC" type="checkbox" name="CFS" value="Y"   />CFS
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="C" type="checkbox" name="CAM" value="N" />CAM
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="CCC" type="checkbox" name="CIS" value="N" />CIS
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="C2" type="checkbox" name="CCL" value="N" />CCL
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="C3" type="checkbox" name="CFSH" value="N" />CFSH
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="C4" type="checkbox" name="CMS" value="N" />CMS
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="C22" type="checkbox" name="CFG" value="N" />CFG
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input id="C23" type="checkbox" name="Blank" value="N" />Blank
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input  id="C24" type="checkbox" name="CCIA" value="N" />CCIA
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input  id="C25" type="checkbox" name="CCFSH" value="N" />CCFSH
								</label>
							</li>
							<li>
								<label class="inline checkbox">
									<input  id="C26" type="checkbox" name="CWMC" value="N" />CWMC
								</label>
							</li>--%>
						</ul>
						<div style="clear:both;"></div>
					</td>
				</tr>
				<tr>
					<td class="tagName">RemarkCons:</td>
					<td class="tagCont" colspan="3">
						<textarea name="remarkcons" id="remarkcons" cols="78" rows="5" style="width: 80%;"></textarea>
					</td>
				</tr>
				<tr style="display: ;">
					<td class="tagBtn" colspan="4">
					<c:if test="${roleObj.audit==1}">
						<input id="AddNameCard" type="button" class="btn" name="Submit" value="Approve"/>
					</c:if>
					<c:if test="${roleObj.audit==1}">
						<input id="Reject_NameCard" type="button" class="btn" name="Submit" value="Reject" onClick="reject_staff();" />
					</c:if>
						<input id="back" type="button" class="btn" value="Back" onclick="reback();"/>
						<input id="urgentDate" class="btn" name="UrgentDate" type="hidden"/>
						<input id="upd_dates" class="btn" name="upd_date" type="hidden"/>
						<input id="refno" class="btn" name="refno" type="hidden"/>
						<input  class="btn" name="method" value="szoadmapproval" type="hidden"/>
						<input  id="company_Type" name="company_Type" type="hidden"/>
					</td>
				</tr>
  			</table>
  			</form>
  		</div>
  	</div>
  </div>	
</body>
  <script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
  <script>
  var basepath = "<%=basePath%>";
  function modify(num){
	  var data = downs[num];
	  $('#lists').hide();
	  $('#details').show();
	  var title_e_number=data.title_english.indexOf(";");
		var title_c_number=data.title_chinese.indexOf(";");
	  	$("#body_view").hide();
	  	$("#body_modify").show();
	  	$("#refno").val(data.refno);
		$("#StaffNo_modify").val(data.staff_code);
		$("#num").val(data.quantity);
		$("#type_modify").val(data.layout_type);
		$("#EnglishName_modify").val(data.name);
		$("#ChineseName_modify").val(data.name_chinese);
		$("#EnglishPosition").val($.trim(data.title_english.substring(0,title_e_number)));
		$("#ChinesePosition").val($.trim(data.title_chinese.substring(0,title_c_number)));
		$("#EnglishStaffDepartmentText").val($.trim(data.title_english.substring(title_e_number+1))); 
		$("#ChineseStaffDepartmentText").val($.trim(data.title_chinese.substring(title_c_number+1)));
		$("#EnglishProfessionalTitleText").val(data.profess_title_e);
		$("#ChineseProfessionalTitle").val(data.profess_title_c);
		$("#TR_RegNo").val(data.tr_reg_no);
		$("#CENO").val(data.ce_no);
		$("#MPFA_NO").val(data.mpf_no);
		$("#Mobile").val(data.bobile_number);
		$("#FAX").val(data.fax);
		$("#DirectLine").val(data.direct_line);
		$("#Email").val(data.e_mail);
		$("#upd_dates").val(data.upd_date);
		$("#urgentDate").val(data.UrgentDate);
		$("#location_modify").val(data.location);
		$("#remarkcons").val(data.remarkcons);
		$("#EnglishAcademicTitle").val(data.academic_title_e);
		$("#ChineseAcademicTitle").val(data.academic_title_c);
		$("#englishExternal").val(data.external_english);
		$("#chineseExternal").val(data.external_chinese);
	if(data.urgent=="Y"){
		$("#urgent").attr("checked","checked").val("Y");
	}else{
		$("#urgent").val("N").removeAttr("checked");
	}
	 if(data.eliteTeam=="Y"){
		$("#ET").attr("checked","checked").val("Y");
	}else{
		$("#ET").val("N").removeAttr("checked");
	}
	
	$("input[type='checkbox'][class='check_company']").removeAttr("checked");
	var companys=data.Company.split("+");
	for(var i=0;i<companys.length;i++){
		$("input[type='checkbox'][class='check_company'][name='"+companys[i]+"']").attr("checked",true);
	}
	 
	/**if(data.CFS_only=="Y"){
		$("#CC").attr("checked","checked").val("Y");
	}else{
		$("#CC").val("N").removeAttr("checked");
		}
	if(data.CAM_only=="Y"){
		$("#C").attr("checked","checked").val("Y");
	}else{
		$("#C").val("N").removeAttr("checked");
	}
	if(data.CIS_only=="Y"){
		$("#CCC").attr("checked","checked").val("Y");
	}else{
		$("#CCC").val("N").removeAttr("checked");
	}
	if(data.CCL_only=="Y"){
		$("#C2").attr("checked","checked").val("Y");
	}else{
		$("#C2").val("N").removeAttr("checked");
	}
	if(data.CFSH_only=="Y"){
		$("#C3").attr("checked","checked").val("Y");
	}else{
		$("#C3").val("N").removeAttr("checked");
	}
	if(data.CMS_only=="Y"){
		$("#C4").attr("checked","checked").val("Y");
	}else{
		$("#C4").val("N").removeAttr("checked");
	}
	if(data.CFG_only=="Y"){
		$("#C22").attr("checked","checked").val("Y");
	}else{
		$("#C22").val("N").removeAttr("checked");
	}
	if(data.Blank_only=="Y"){
		$("#C23").attr("checked","checked").val("Y");
	}else{
		$("#C23").val("N").removeAttr("checked");
	}
	if(data.CCIA_only=="Y"){
		$("#C24").attr("checked","checked").val("Y");
	}else{
		$("#C24").val("N").removeAttr("checked");
	}
	if(data.CCFSH_only=="Y"){
		$("#C25").attr("checked","checked").val("Y");
	}else{
		$("#C25").val("N").removeAttr("checked");
	}
	if(data.CWMC_only=="Y"){
		$("#C26").attr("checked","checked").val("Y");
	}else{
		$("#C26").val("N").removeAttr("checked");
	}*/
	
	
	if(data.shzt=="R"||data.shzt=="E"){
		$("#AddNameCard,#Reject_NameCard").show();
	}else if(data.shzt=="S"){
			$("#AddNameCard").hide();
			$("#Reject_NameCard").show();
	}else{
		$("#AddNameCard,#Reject_NameCard").hide();
	}
	 fujianlist();
	  
	  
	  
	  
	  
	  /**$.dialog( {
		title : '详情',
		id : 'menu_new',
		data : data,
		width : 1000,
		height : 800,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath + "namecard/staff/approveStaffCard_details.jsp"
	}); */ 
  }
  function reback(){
		$('#details').hide();
		$('#lists').show();
}
	function fujianlist(){
		$.ajax({
				url:"QueryFileNameServlet",
				type:"post",
				data:{"staffcode":$("#StaffNo_modify").val()},
				success:function(date){
					var dataRole=eval(date);
					var html="";
					$("#fujian_list option").remove();
					if(dataRole!="" && dataRole!=null){
						html+="<option>请选择</option>";
						for(var i=0;i<dataRole.length;i++){
								html+="<option  class='oladf'>"+dataRole[i]+"</option>";
						}
					}else{
						html+="<option  class='oladf'>没有任何上传记录</option>"
					}
					$("#fujian_list").append(html);
				},error:function(){
					alert("网络连接失败，请稍后重试...");
					return false;
				}
				
			});
	}
	function reject_staff(){
			
		
		if(confirm("確定拒绝该申请?")){
		$.ajax({
			//url:"RejectRequestStaffConvoyServlet",
			url:"StaffNameCardWriteServlet",
			type:"post",
			data:{"method":"reject","role":"SZADM","staffcode":$("#StaffNo_modify").val(),"urgentDate":$("#urgentDate").val(),"refno":$("#refno").val(),"remark":$("#remarkcons").val(),
			"name_chinese":$("#ChineseName_modify").val(),"name_english":$("#EnglishName_modify").val()},
			success:function(date){
				var result=$.parseJSON(date);
				if(result.state=="success"){
					reback();
				  $("#AddNameCard").attr("disabled","");
				  $("#searchs").click();
				  
				}
				alert(result.msg);
			},error:function(){
				alert("网络连接失败，请稍后重试...");
				return false;
			}
			
		});
		}
	}
	function selects(pagenow){
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("開始日期不能大於結束日期!");
						$("#start_date").focus();
						return false;
					}
				}
				$.ajax({
				type: "post",
				//url:"QueryStaffRequestConvoyServlet",
				url:"StaffNameCardReaderServlet",
				data: {'name':$("#name").val(),'code':$("#staffcodes").val(),
					'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
					'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),
					'ET':$("#ET_select").val(),"layout":$("#layout_select").val(),'payer':$("#staffcodes").val(),
					'pagenow':pagenow,"isverify":$("#isverify").val(),"method":"select"},
				beforeSend: function(){
						$('tr[target=details]').hide();
						parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(data){
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select'],tr[id='selectstate']").remove();
					downs=null;
					if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
						downs=dataRole[0];
						var shzt="";
						for(var i=0;i<dataRole[0].length;i++){
						if(dataRole[0][i].shzt=="S")
							shzt="Pending";
						else if(dataRole[0][i].shzt=="E")//等待Department Head 审核
							shzt="Dept Approved";
						else if(dataRole[0][i].shzt=="R")//等待HR 审核
							shzt="HR Approved";
						else if(dataRole[0][i].shzt=="Y")
							shzt="Approved";
						else if(dataRole[0][i].shzt=="N")
							shzt="Rejected"
							html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].refno+"</td><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td align='left' title='"+dataRole[0][i].title_english+"'>"
							+standardChar2(";",dataRole[0][i].title_english) +"</td><td align='left' title='"+dataRole[0][i].title_chinese+"'>"+standardChar2(";",dataRole[0][i].title_chinese) +"</td>"

							//+"<td align='center'>"+dataRole[0][i].profess_title_e +"</td><td align='center'>"+dataRole[0][i].profess_title_c +"</td>" 

							+"<td align='center'>"+dataRole[0][i].quantity +"</td><td align='center'>"+dataRole[0][i].UrgentDate.substring(0,10)+"</td>" +
							"<td align='left'>"+shzt+"</td><td align='center'><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='modify("+i+")'>"
							+"详细"+"</a></c:if>&nbsp;<c:if test='${roleObj.search==1}'><a onclick=\"queryProcess('"+dataRole[0][i].refno+"',"+i+")\">流程</a></c:if></td></tr>";		 
						}
							$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='10' size='5px' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
						$(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 	 page(pagenow,totalpage);
				 }
			 });
					
		}
  </script>
</html>