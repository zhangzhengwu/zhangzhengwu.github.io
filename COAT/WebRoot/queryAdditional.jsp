<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache" >
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<script src="css/date.js" language="JavaScript"></script>
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
		<script type="text/javascript" src="./css/ajaxfileupload.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
<script src="css/Util.js" language="JavaScript"></script>
    <style type="text/css">
<!--

#Addtional {
	position:absolute;
	width:100%; 
 
	z-index:-1;
	top:100px;
 
}
#overs {
	position:absolute;
	width:100%;
	height:100%;
	z-index:50;
	display:none;
	background-color:#999999;
	opacity:0.9;
	
}
#Layer2 {
	position:absolute;
	width:388px;
	height:242px;
	z-index:5;
	left: 286px;
	top: 241px;
	
}
 
.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
-->
    </style>

	<script type="text/javascript" >
		var pagenow =1;
		var totalpage=1;
		var total=0;
		var downs = null;

	$(function(){
		$("body").css("overflow","visible");
		/******************************************************获取QueryAdditional**********************************************************/

 		select(1);
 /*****************************************************獲取QueryAdditional結束**********************/
 	/*******************************批量增加******************************************/
	$("#addAll").click(function(){
			if($("#shangchuan").is(":hidden")){
				$("#shangchuan").show(500);
			}
		else{
		$("#shangchuan").hide(500);
		}
	});
 /***********************************************************************************/
 	

	/****************************************************查询按钮单击事件***************************/
	$("#additional").click(function(){
		select(1);
	});
	$("body").keydown(function(e){
	if(e.keyCode==13){
	select(1);
	}
	});
	
	/******************************************************查询按钮单击事件结束**********************/
	
 /***********************************************select()方法***************************************/
    $('#search').bind('click',function(){
    	select(1);
    });
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

 	
/*************************取消********************************/
$("#exit").click(function(){
$("#overs").hide();
});
/*************************************************************/

/**************************************************************************************************/







/*************************************************************/
 
 /********************************** 验证上传文件是否为空******************************************/
function fileupload(){
	
    if($("#ups").val()==""){ 
        alert("请选择有效文件"); 
        return false;  
    }  

    $.ajaxFileUpload({  
         url:'UpLoadFileServlet', 
         secureuri:false,  
         fileElementId:'ups',  
         dataType: 'text/html',
         success: function (data) {
            if(data!="error"){
                fileName=data;
                //alert("上传成功，上传路径为："+fileName);
                uploadConsultant(fileName);
            }
	 		    else{
  	 		    alert("上传失败");
  	 		}
         },error: function (data, status, e){  
             alert("fail");  
         }  
     });  
  }
  
function uploadConsultant(fileName){
	if(fileName==""){
		alert("請選擇需要上傳的文件！");
		return false;
		}else{
		$("#inputs").attr("disabled","disabled");
	 $.ajax({
		type: "post",
		url:"<%=basePath%>/UpAllServlet",
		dateType:"html",
		data: {'up':fileName},
		success:function(date){
		$("#login").hide(); 
			if(date!="null"){
				var dataRole=eval(date);
				//$("#num").empty();	
				//var html="<font>總共"+dataRole[1]+"條記錄</font>";
				//$("#num").append(html);
				
				alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
				
				$("#start_date").val(CurentTime());
				$("#end_date").val(CurentTime());
				$("#additional,#upAll").click();
			 
			}else{
				alert("非系统所需文件！");
			}
			$("#inputs").removeAttr("disabled");
			}
		 });
	 }
}
$("#inputs").click(function(){ 
	fileupload();
});
	$("#inputss").click(function(){  
		if($("#ups").val()==""){
		alert("請選擇需要上傳的文件！");
		return false;
		}else{
		$("#inputs").attr("disabled","disabled");
	 $.ajax({
		type: "post",
		url:"UpAllServlet",
		dateType:"html",
		data: {'up':getPath($("#ups"))},
		success:function(date){
		$("#login").hide(); 
			if(date!="null"){
				var dataRole=eval(date);
				//$("#num").empty();	
				//var html="<font>總共"+dataRole[1]+"條記錄</font>";
				//$("#num").append(html);
				
				alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
				
				$("#start_date").val(CurentTime());
				$("#end_date").val(CurentTime());
				$("#additional,#upAll").click();
			 
			}else{
				alert("非系统所需文件！");
			}
			$("#inputs").removeAttr("disabled");
			}
		 });
	 }
	}); 
	
	/***********************************************************************************/

	
	/**************************************************************************************/
	/*****************************************************获取当前时间************************************************/
		function CurentTime(){ 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
       if(month < 10){
       clock += "0";
        }
        clock += month + "-";
  if(day < 10){
         clock += "0";
         }
        clock += day + "";
        return clock; 
    } 
/************************************************************************************************************************/		
	});
	
	function getPath(obj)  { 
		if(obj){ 
			if (window.navigator.userAgent.indexOf("MSIE")>=1){
				obj.select(); 
				return document.selection.createRange().text;    
			} else if(window.navigator.userAgent.indexOf("Firefox")>=1){    
				if(obj.files){
					return obj.files.item(0).getAsDataURL();
		 		}       
				return obj.value; 
			}     
		return obj.value;   
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
	function select(pagenow){
		if(typeof(pagenow) == "undefined"){
			pagenow = 1;
		}
if($("#satrt_date").val()!="" &&  $("#end_date").val()!=""){
		if($("#start_date").val() > $("#end_date").val()){
			alert("開始日期不能大於結束日期！");
			return  false;
		}
	}
 
$.ajax({
	type: "post",
	url: "<%=basePath%>/QueryAdditionalServlet",
	data: {'StaffNo':$("#StaffNo").val(),'start_date':$("#start_date").val(),'end_date':$("#end_date").val(),'Valid':$("#Valid").val(),'pageNow':pagenow},
	beforeSend: function(){
		parent.showLoad();
	},
	complete: function(){
		parent.closeLoad();
	},
	success:function(date){
	 var html="";
	 		  var dataRole=eval(date);
	 		  $("tr[id='select']").remove();
 			if(dataRole[3]>0){
					total=dataRole[3];
					pagenow =dataRole[2];
				    totalpage=dataRole[1];
				    downs = dataRole[0];
				   for(var i=0;i<dataRole[0].length;i++){
					   	
				   var sfyx="";
				   if(dataRole[0][i].sfyx=='Y'){
				   sfyx="有效";
				   html+="<tr id='select'>";
				   }else{
				   sfyx="无效";
   				   html+="<tr class='ss' bordercolor='#FF0000' id='select'>";
				   }
				  html+="<td align='center'>"+dataRole[0][i].staffNo+"</td><td >"+
				  dataRole[0][i].name+"</td><td align='center'>"+
				  dataRole[0][i].num+"</td><td align='center'>"+
				  dataRole[0][i].remark+"</td><td align='center'>"+
				  dataRole[0][i].add_name+"</td><td align='center'>"+
				  dataRole[0][i].add_date+"</td><td align='center'>"+
				  dataRole[0][i].upd_name+"</td><td align='center'>"+       
				  dataRole[0][i].upd_date+"</td><td align='center'><font size='+0'>"+sfyx+"</font></td><td align='center'><a onclick='modify("+i+")'>"+"修改"+"</a></td></tr>";
				   }
				   	 $(".page_and_btn").show();
			   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
			   	    //var tempdata = dataRole[0][i].staffNo,dataRole[0][i].remark;
		   }
		   else{
		   html+="<tr id='select'><td colspan='10' align='center'><font size=+1>"+"對不起，暫時沒有找到您想要的數據！"+"</font></td></tr>";
		    $(".page_and_btn").hide();		
		   }
	   	  		 $("#test:last").append(html); 
   	    		$("tr[id='select']:even").css("background","#COCOCO");
                $("tr[id='select']:odd").css("background","#F0F0F0");
                $(".ss").css("backgroundColor","red");
                	 page(pagenow,totalpage);
					}
		});
									
}
 /*************************************************************************************************/
	</script>
</head>
  <body>  
  <div class="cont-info">
  	<div class="info-search">
  		<table>
  			
  			<tr>
  				<td class="tagName">開始日期：</td>
  				<td class="tagCont">
  					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" readonly="readonly" /> 
  					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
  				</td>
  				<td class="tagName">結束日期：</td>
  				<td class="tagCont">
  					<input name="text" type="text" id="end_date" onClick="return Calendar('end_date')"   readonly="readonly" /> 
  					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i> 
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">顾问编号：</td>
  				<td class="tagCont">
  					<input type="text" name="Initials" id="StaffNo">
  				</td>
  				<td class="tagName">是否有效：</td>
  				<td class="tagCont">
  					<select name="select" id="Valid">
					   <option value="">請選擇</option>
					   <option value="Y">有效</option>
					   <option value="N">无效</option>
				    </select>
  				</td>
  			</tr>
  			<tr>
  				<td class="tagCont" colspan="2">
	  				<div id="shangchuan" style="display: none;" >
					    <input type="file" name="up" id="ups" />
					    <input name="submit" type="submit" id="inputs" value="導 入"/>
				    </div>
  				</td>
  				<td class="tagCont" colspan="2">
  					<a class="btn" id="additional">
  						<i class="icon-search"></i>
  						搜索
  					</a>
  					<a class="btn" id="add">
  						<i class="icon-plus"></i>
  						新增
  					</a> 
  					<a class="btn" id="addAll">
  						<i class="icon-plus"></i>
  						新增多个
  					</a>
  				</td>
  			</tr>
  		</table>
  	</div>
  	<div class="info-table">
  		<table id="test">
  			<thead>
  			<tr>
  				<th class="width_">Staff Code</th>
  				<th class="width_">Name</th>
  				<th class="width_">Additional</th>
  				<th class="width_">Remark</th>
  				<th class="width_">Add_Name</th>
  				<th class="width_">Add_Date</th>
  				<th class="width_">Update_Name</th>
  				<th class="width_">Update_Date</th>
  				<th class="width_">sfyx</th>
  				<th class="width_">Oprate</th>
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

    <div id="overs" >
    <div id="Layer2" style="background-color:black;">
      
    </div>
    
</div>
</body>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
var basepath = '<%=basePath%>';

/***************************************************Select**********************************************/
function selects(pagenow){
	if($("#satrt_date").val()!="" &&  $("#end_date").val()!=""){
		if($("#start_date").val() > $("#end_date").val()){
			alert("開始日期不能大於結束日期！");
			return  false;
		}
	}
 
	$.ajax({
		type: "post",
		url: "additionquota/AdditionalQuotaServlet",
		data: {"method":"select",'StaffNo':$("#StaffNo").val(),'start_date':$("#start_date").val(),'end_date':$("#end_date").val(),'Valid':$("#Valid").val(),'pageNow':pagenow},
		success:function(date){
		  var html="";
 		  var dataRole=eval(date);
 		  $("tr[id='select']").remove();
			if(dataRole[3]>0){
				tempObject=dataRole[0];
				total=dataRole[3];
				pagenow =dataRole[2];
			    totalpage=dataRole[1];
			   for(var i=0;i<dataRole[0].length;i++){
				   	
			   var sfyx="";
			   if(dataRole[0][i].sfyx=='Y'){
			   sfyx="有效";
			   html+="<tr id='select'>";
			   }else{
			   sfyx="无效";
  				   html+="<tr class='ss' bordercolor='#FF0000' id='select'>";
			   }
			  html+="<td align='center'>"+dataRole[0][i].staffNo+"</td><td >"+
			  dataRole[0][i].name+"</td><td align='center'>"+
			  dataRole[0][i].num+"</td><td align='center'>"+
			  dataRole[0][i].remark+"</td><td align='center'>"+
			  dataRole[0][i].add_name+"</td><td align='center'>"+
			  dataRole[0][i].add_date+"</td><td align='center'>"+
			  dataRole[0][i].upd_name+"</td><td align='center'>"+       
			  dataRole[0][i].upd_date+"</td><td align='center'><font size='+0'>"+sfyx+"</font></td><td align='center'><a onclick="+"modify("+i+")"+">"+"修改"+"</a></td></tr>";
			   }
			   	 $(".page_and_btn").show();
		   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
		   	    //var tempdata = dataRole[0][i].staffNo,dataRole[0][i].remark;
	   }
	   else{
	   html+="<tr id='select'><td colspan='10' align='center'><font color='blue';size=+1>"+"對不起，暫時沒有找到您想要的數據！"+"</font></td></tr>";
	    $(".page_and_btn").hide();		
	   }
   	  		 $("#test:last").append(html); 
  	    		$("tr[id='select']:even").css("background","#COCOCO");
               $("tr[id='select']:odd").css("background","#F0F0F0");
               $(".ss").css("backgroundColor","red");
               	 page(pagenow,totalpage);
				}
	 });
									
}
 /*************************************************************************************************/
 


/***********************************添加按钮***************************************************************/
$("#add").click(function (){ 
	var url = basepath + "queryAdditional_info.jsp";
	addlhg(440,300,url);
});
function modify(index)
{
	var data = downs[index];
	var url = basepath + "queryAdditional_modify.jsp";
	editlhg(440,300,url,data);
}
</script>
</html>
