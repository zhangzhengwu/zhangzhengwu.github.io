<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryMarkPremiumOrder</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/Util.js?ver=<%=new Date() %>"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<script type="text/javascript" src="./css/ajaxfileupload.js"></script>
	 <link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
   
 
  </head>
  <script type="text/javascript" >
  var downs=null;
  var pagenow=1;
  var totalPage=1;
  var before=null;
  function resize(){
	  $("#overs,#divId,#divIdSelected,.span_menu,#Medical").width($("body").width()).css("left",0); 
  }
  
  $(function(){
	  window.onresize=resize;
	  $("#topTable").attr("disabled",true);
  	  var econvoy="${Econvoy}".split("-");
  	
     //注册单击事件
 	 var da = new Date();
     //注册单击事件
	 $("#start_date").val(getMonthBeforeDay(da));	
	 $("#end_date").val(getBeforeMonthLastDay(da));	
	
	/***************************************************************************************************/
	/*
	*获取前一个月的第一天
	* date : Date类型.
	*/
	function getMonthBeforeDay(date){
		
		 var yesterday_milliseconds=date.getTime();  //-1000*60*60*24   
		 var yesterday = new Date();     
	     yesterday.setTime(yesterday_milliseconds);     
	   
		 var strYear = yesterday.getFullYear();  
		 var strDay = yesterday.getDate();  
		 var strMonth = yesterday.getMonth()+1;
		 if(strMonth<10)  
		 {  
		  strMonth="0"+strMonth;  
		 }  
		 if(strDay<10)  
		 {  
		  strDay="0"+strDay;  
		 }  
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
	/*
	*获取前一个月的最后一天
	*date : Date类型.  
	*/
	
	function getBeforeMonthLastDay(date){
		
		 var yesterday_milliseconds=date.getTime();     //-1000*60*60*24
		 var yesterday = new Date();     
	     yesterday.setTime(yesterday_milliseconds);     
	   
		 var strYear = yesterday.getFullYear();  
		 var strDay = yesterday.getDate();  
		 var strMonth = yesterday.getMonth()+1;
		 if(strMonth<10)  
		 {  
		  strMonth="0"+strMonth;  
		 }  
		 if(strDay<10)  
		 {  
		  strDay="0"+strDay;  
		 }  
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
 	/******************************************selects Click*****************************************************/
	
 	$("#searchs").click(function(){
		selects_last(1);
	});
 				//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				selects_last(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				selects_last(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				selects_last(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalPage;
				selects_last(pagenow);
			});

  });
  /****************************************QueryEpaymentOrder**********************************************************/
  function selects_last(pageNow){
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
			$.ajax({
			type:"post",
			url:"EPaymentServlet",
			data: {'method':'queryProduct','start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),
					'productcode':$("#productcode").val(),'sfyx':$("#ordertype").val(),"productname":$("#productname").val(),'curretPage':pageNow},
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
					downs=dataRole;
					$(".page_and_btn").show();
	 			   	totalPage=dataRole[1];
	 			 
					for(var i=0;i<dataRole[0].length;i++){
					
						html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].productcode+"</td><td  align='left'>"+
						dataRole[0][i].productname+"</td><td>"+dataRole[0][i].price+"</td><td align='left'>"+dataRole[0][i].quantity+"</td>"
						+"<td align='center'>"+dataRole[0][i].unit+"</td><td align='center'>"+dataRole[0][i].adddate+"</td>" +
						"</td><td align='center'>"+dataRole[0][i].addname+"</td>" +"<td align='center'>"+dataRole[0][i].sfyx+"</tr>";
					}
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='8' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	 page(dataRole[2],dataRole[1]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 },error:function(){
					alert("Network connection is failed, please try later...");
			   				return false;
				}
		 });
	}
  
  function page(currt,total){
	 
		if(pagenow<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(pagenow>=totalPage){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
	}
  
 function fileupload(){
             if($("#filePaths").val()==""){ 
                 alert("请选择有效文件"); 
                 return false;  
             }  
       
             $.ajaxFileUpload({  
                  url:'UpLoadFileServlet', 
                  secureuri:false,  
                  fileElementId:'filePaths',  
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
				return;
			}
			 $.ajax({
				type: "post",
				url:"UpExcelServlet",
				dateType:"html",
				data: {'uploadfile':fileName},
				success:function(date){
					if(date != "null"){
						//alert(date);
						var dataRole=eval(date);
						//alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
						alert("導入數據成功,此次操作共耗时"+dataRole[2] +"分鐘"+dataRole[1]+"條數據");		
						$("#shangchuan").attr("disabled",true);
					}else{
						alert("非系统所需文件！");
					}
				 },error:function(){
					 alert("网络连接失败，请稍后重试...");
					 return false;
				 } 
				});
           } 
  
</script>
<body>
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"  onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Product Code：</td>
				<td class="tagCont">
					<input type="text" name="productcode" id="productcode">
				</td>
				<td class="tagName">Sfyx：</td>
				<td class="tagCont">
					<select name="ordertype" id="ordertype">
		                <option value="">Please Select Status</option>
			            <option value="Y">Y</option>
			            <option value="N">N</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Product Name：</td>
				<td class="tagCont">
					<input type="text" name="productname" id="productname">
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2" style="text-align: center;">
					<div id="shangchuan">
		        	  <input type="file" name="filePaths" id="filePaths"/>
   					  <input type="button" name="fileLoad" id="fileLoad" value="Upload" onclick="fileupload()"> 
	        		</div>
				</td>
				<td class="tagCont" colspan="2">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_10">Product Code</th>
				<th class="width_15">Product Name</th>
				<th class="width_10">Price</th>
				<th class="width_10">Quantity</th>
				<th class="width_10">Unit</th>
				<th class="width_15">AddDate</th>
				<th class="width_10">AddName</th>
				<th class="width_10">Sfyx</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a><SPAN style="color: red;" id="curretPage">
						</SPAN> /
						<SPAN style="color: red;" id="totalPage">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>
