<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
		<base href="<%=basePath%>">
		<title>查詢頁面</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		
		<script type="text/javascript">
/*****************************************************WindowForm Load *************************************/
var downs = null;
var pagenow = 1;
var totalpage = 1;
var total = 0;
var basepath = '<%=basePath%>';

  $(function(){
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
}); 
	/****************************************************查询******************************************/		
	function selects(pagenow) {
		$.ajax({
			url:"ConsMacauServlet",
			type:"POST",
			data:{"employeeId":$("#employeeId").val(),"c_Name":$("#c_Name").val(),"HKID":$("#HKID").val(),"recruiterId":$("#recruiterId").val(),"pageNow":pagenow,"method":"query"},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(date){
				 var dataRole=eval(date);   
		         var htmls="";
		         var html="";
		         $(".title").remove();
		         if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
			          for(var j=0;j<dataRole[0].length;j++){
							  htmls+="<tr class='title' ><td align='center'>"+dataRole[0][j].employeeId+"</td><td>"+dataRole[0][j].alias+"</td><td>"
							  +dataRole[0][j].employeeName+"</td>"+"<td >"+dataRole[0][j].c_Name
							  +"<td align='center'>"+dataRole[0][j].HKID+"</td>"+"<td align='center'>"
			        		  +dataRole[0][j].joinDate+"</td>"+"<td align='center'>"+dataRole[0][j].directLine+"</td>"+"<td align='left'>"+dataRole[0][j].email+"</td>"
			        		  +"<td>"+dataRole[0][j].externalPosition+"</td>"+"</td>"+"<td align='left'>"+dataRole[0][j].location+"</td>"+
			        		  "<td>"+dataRole[0][j].e_PositionName+"</td>"+"<td align='center'>"+dataRole[0][j].recruiterId+
			        		  "</td>"+"<td>"+dataRole[0][j].recruiterName+"</td>"
			        		  +"</td>"+"</tr>";
			         	 }   
						  $(".page_and_btn").show();
						  $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
			}else{				
				htmls += "<tr class='title'><td colspan='18' align='center'>"
									+ "對不起，沒有您想要的數據!" + "</td></tr>";
				 $(".page_and_btn").hide();							 
			}
				  $("#jqajax:last").append(htmls); 
				  $("tr.title:even").css("background","#COCOCO");
		          $("tr.title:odd").css("background","#F0F0F0");
		          page(pagenow,totalpage);
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

	/*********************************文件上传*********************************************************/


 	function uploadConsultant(fileName){
           	if(fileName==""){
				alert("請選擇需要上傳的文件！");
				return;
			}
			 $.ajax({
				type: "post",
				url:"UpExcelServlet",
				dateType:"html",
				complete: function(){
				  $.dialog.list['menu_upload'].close();
				},
				data: {'uploadfile':fileName},
				success:function(date){
					if(date != "null"){
						//alert(date);
						var dataRole=eval(date);
						alert("導入數據成功!本次操作插入"+dataRole[1] +"數據    "+dataRole[3]+"條數據已存在    "+"共耗時"+dataRole[2]+"分鐘");		
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
		$('#upload_btn').live('click',function(){
	 		var data = 'uploadConsultant';
	 		var url = basepath + "publicUpload.jsp";
			uploadlhg(440,300,url,data);
	 	});

</script>
		<style type="text/css">
#login {
	position:absolute;
	width:200px;
	height:71px;
	z-index:100;
	left: 525px;
	top: 129px;
	border-color:#99CCCC;
display:none;
	}
	
</style>
	</head>
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">EmployeeId：</td>
				<td class="tagCont">
					<input id="employeeId" type="text"/>
				</td>
				<td class="tagName">C_Name：</td>
				<td class="tagCont">
					<input id="c_Name" type="text"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">RecruiterId：</td>
				<td class="tagCont">
					<input type="text" name="codebtn" id="recruiterId" />
				</td>
				<td class="tagName">HKID：</td>
				<td class="tagCont">
					<input type="text" name="codebtn" id="HKID" />
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="4" style="text-align: center;">
					<c:if test="${roleObj.upd==1}">
						<a class="btn" id="upload_btn">
							<i class="icon-upload-alt"></i>
							Upload File
						</a>
					</c:if>
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="aaa" name="search" onclick="selects(1)">
							<i class="icon-search"></i>
							Search
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
				<th class="width_">EmployeeId</th>
				<th class="width_">Alias</th>
				<th class="width_">EmployeeName</th>
				<th class="width_">C_Name</th>
				<th class="width_">HKID</th>
				<th class="width_">joinDate</th>
				<th class="width_">DirectLine</th>
				<th class="width_">Email</th>
				<th class="width_">ExternalPosition</th>
				<th class="width_">Location</th>
				<th class="width_">E_PositionName</th>
				<th class="width_">RecruiterId</th>
				<th class="width_">RecruiterName</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0" cellspacing="0" id="select_table">
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
		

	<center> 
	    <div id="login" style="top: 143px; left: 40%; ">
		    <p><img src="css/022.gif" width="32" height="32"></p>
		    <p><span style="font-family:'隶书';"><span class="STYLE3">正在處理數據，請稍候...</span></span></p>
	  	</div>
	</center>
</body>
</html>
