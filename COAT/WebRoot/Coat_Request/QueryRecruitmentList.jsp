<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryRecruitment</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/Util.js?ver=<%=new Date() %>"></script>
	<script src="./css/date.js" type="text/javascript"></script>
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
  var d=null;
  var curPage=1;
  var totalPage=1;
  var temp;
  
  $(function(){
  	selectMediaName();
    
  	  
     //注册单击事件
 	var da = new Date();
     //注册单击事件
	$("#start_date").val(getMonthBeforeDay(da));	
	$("#end_date").val(getMonthBeforeDay(da));	

	$("#pre").bind("click", function() {//上一页
		curPage=curPage - 1;
		selects();
	});
	
	$("#next").bind("click", function() {//下一页
		curPage=curPage + 1;
		selects();
	});
	$("#first").bind("click", function() {//首页
		curPage=1;
		selects();
	});
	$("#end").bind("click", function() {//尾页
		curPage=totalPage;
		selects();
	});


	
	$("#searchs").click(function(){
		selects();
	});

  });
  
  function selectMediaName(){
  
  		$.ajax({
			type  : "post",
			url : "QueryRecruitmentHRServlet",
			data : {"method":"queryMediaName"},
			success : function(date){
						var d=eval(date);
						var html;
						if(d.length>0){
							for(var i=0;i<d.length;i++){
								html+="<option value='"+d[i].medianame+"'>"+d[i].medianame+"</option>";
							}
							$("#medianame:last").append(html);
						}
			
			
			} 		
  		
  		});
  
  }
  
  
  
  
  
  	function selects(){
                //时间判断
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
				var beginTime = $("#start_date").val();
				var endTime = $("#end_date").val();
				if(beginTime>endTime){
					alert("Start Date can’t be later than End Date.");
					$("#start_date").focus();
					return false;
				}
			}
			$.ajax({
			type: "post",	
			url:"QueryRecruitmentHRServlet",
			data: {"date1":$("#start_date").val(),"date2":$("#end_date").val(),"medianame":$("#medianame").val(),'pageIndex':curPage,"method":"findRecruitmentList"},
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
				if(dataRole[0].length>0){
					downs=dataRole;
					curPage=dataRole[2];
	 			   	totalPage=dataRole[1];
	 			   
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select' title='"+i+"' style='line-height:20px;'><td align='center'>"+
						dataRole[0][i].staffcode+"</td><td align='center'>"+
						dataRole[0][i].staffname+"</td><td align='center'>"+
						dataRole[0][i].medianame+"</td><td align='center'>"+
					    dataRole[0][i].createdate+"</td><td align='center'>"+
						dataRole[0][i].status+"</td></tr>";
					}
						$(".page_and_btn").show();
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	   
				     page(dataRole[2],dataRole[1]);	
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 }
		 });
	}
  function page(currt,total){
	 
		if(curPage<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(curPage>=totalPage){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
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
				<td class="tagName">Media Name：</td>
				<td class="tagCont">
					<select id="medianame" name="medianame">
		             	<option value="">Please Select </option>
	            	</select>
				</td>
				<td class="tagCont" style="text-align: center;">
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
				<th class="width_">StaffCode</th>
				<th class="width_">StaffName</th>
				<th class="width_">medianame</th>
				<th class="width_">CreateDate</th>
				<th class="width_">Status</th>
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
