<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Department_position_list</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="css/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script language="javascript" type="text/javascript" src="css/date.js"></script>
	<script language="javascript" type="text/javascript" src="css/Util.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">

<script type="text/javascript">
  var pagenow =1;
  var totalpage=1;
  var total=0;
  var down=null;
  
  
  
  /**
 * 只读文本框禁用退格键
 * 
 * */
function BackSpace(e){
	  	 e = e ? e : event;// 兼容FF 
	if(e.keyCode==8){//屏蔽退格键
	     var type=window.event.srcElement.type;//获取触发事件的对象类型
		 var reflag=window.event.srcElement.readOnly;//获取触发事件的对象是否只读
	  	 var disflag=window.event.srcElement.disabled;//获取触发事件的对象是否可用
		  if(type=="text"||type=="textarea"){//触发该事件的对象是文本框或者文本域
			   if(reflag||disflag){//只读或者不可用
			    window.event.returnValue=false;//阻止浏览器默认动作的执行
			   }
		  }
		  else{ 
		   window.event.returnValue=false;//阻止浏览器默认动作的执行
		  }
	 }else if(e.keyCode==27){//ESC
		 div_close();
	 }
}
  
  
  
$(function(){
	selects(pagenow);
	document.onkeydown=BackSpace;
	 
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





function selects(pageNow){
		$.ajax({
		url:"DepartmentServlet",
		type:"post",
		async:false,
		data:{"method":"select","startDate":$("#start_date").val(),"endDate":$("#end_date").val(),"department":$("#department").val(),"sfyx":$("#sfyx").val(),"curretPage":pageNow},
		beforeSend: function(){
			parent.showLoad();
		},
		complete: function(){
			parent.closeLoad();
		},
		success:function(date){
			if(date=="error" || date=="null"){
				alert("Query data anomalies");
				return false;
			}else{
			 	$(".tr_result").remove();
				var dataRole=eval(date);
				var html="";
				if(dataRole[3]>0){
					down=dataRole[0];
					total=dataRole[3];
					pagenow =dataRole[2];
			   		totalpage=dataRole[1];
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr class='tr_result' "+(dataRole[0][i].sfyx=="N"?"style='background:#FF4500;'":"")+"><td>" +((pagenow-1)*15+i+1)
							+"</td><td>" +dataRole[0][i].prof_title_ename
							+"</td><td>" +dataRole[0][i].prof_title_cname
							+"</td><td>" +dataRole[0][i].add_name
							+"</td><td>" +dataRole[0][i].add_date
							+"</td><td>" +dataRole[0][i].upd_name
							+"</td><td>" +dataRole[0][i].upd_date
							+"</td><td>" +(dataRole[0][i].sfyx=="Y"?"Effective":"Invalid")
							+"</td><td><a href='javascript:void(0);' onclick='modify("+i+");'>Modify</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='Del("+i+")'>Delete</a></td></tr>";
					}
					$(".page_and_btn").show();
				}else{
					$(".page_and_btn").hide();
					html+="<tr class='tr_result'><td colspan='7' align='center'>Sorry, there is no matching record.</td></tr>";
				}
				$("#searchTable").append(html);
			 
                 page(pagenow,totalpage);
			}
		},error:function(){
			alert("Network connection is failed, please try later...");
			return false;
		}
	});
}
/***function modify(id){
div_open();
$("#modify_dpt").val(down[id].prof_title_ename);
$("#modify_department").val(down[id].prof_title_cname);

$("#modify_addName").val(down[id].add_name);
$("#modify_addDate").val(down[id].add_date);
$("#modify_sfyx").val(down[id].sfyx);
$("#modify_save").attr("name",down[id].id);
}*/

function Del(id){
	if(down!=null){
		
		if(confirm("OK to Delete the information?")){
					$.ajax({
				url:"DepartmentServlet",
				type:"post",
				data:{"method":"del","department":down[id].prof_title_ename,"deptId":down[id].id},
				success:function(date){
					alert(date);
					div_close();
					selects(pagenow);
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
		}
	}else{
		alert("Data is illegal tampering detection system, refused to operate!");
	}
}
function saveModify(deptId){
	var dpt = $("#modify_dpt").val();
	if(dpt == null || dpt == ""){
		alert("please input Dept(Simple)!");
		$("#modify_dpt").focus();
		return false;
	}
	var department = $("#modify_department").val();
	if(department == null || department == ""){
		alert("please input Department!");
		$("#modify_department").focus();
		return false;
	}

	if(confirm("OK to Save the information?")){
		$.ajax({
			url:"DepartmentServlet",
			type:"post",
			data:{"method":"modify","dpt":dpt,"department":department,"sfyx":$("#modify_sfyx").val(),"deptId":deptId,"modify_add_date":$("#modify_addDate").val()},
			success:function(date){
				alert(date);
				div_close();
				selects(pagenow);
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
	}
}

function save(){
	var dpt = $("#add_dpt").val();
	if(dpt == null || dpt == ""){
		alert("please input Department(Eng)!");
		$("#add_dpt").focus();
		return false;
	}
	var department = $("#add_department").val();
	if(department == null || department == ""){
		alert("please input Department(Chi)!");
		$("#add_department").focus();
		return false;
	}
 
	if(confirm("OK to Save the information?")){
				$.ajax({
			url:"DepartmentServlet",
			type:"post",
			data:{"method":"save","dpt":dpt,"department":department,"sfyx":$("#add_sfyx").val()},
			success:function(date){
				alert(date);
				div_close();
				selects(pagenow);
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
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
				$("#total").empty().append(currt+"/"+total);
	}
   
   function div_open(){
		$("#shelter").show();
   }
   function div_close(){
		$("#shelter,#add_shelter").hide();
   }
   
   function downs(){ 
		if(downs!=null){
			window.location.href="<%=basePath%>"+"DepartmentServlet?method=down&startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&department="+$("#department").val()+"&sfyx="+$("#sfyx").val();
		}
		else{
			alert("Please query data first, and then do export related operation!");
		}
	
	}
</script>
  </head>
  
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date:</td>
				<td class="tagCont">
					<input id="start_date" type="text"  readonly="readonly" onClick="return Calendar('start_date')"/>
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date:</td>
				<td class="tagCont">
					<input id="end_date" type="text"  readonly="readonly" onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Department(Eng):</td>
				<td class="tagCont">
					<input id="department" type="text" />
				</td>
				<td class="tagName">Effective?:</td>
				<td class="tagCont">
					<select id="sfyx">
						<option value="">please choose option</option>
						<option value="Y">Effective</option>
						<option value="N">Invalid</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn"  onclick="selects(1);">
						<i class="icon-search"></i>
						Search
					</a>
				  	<a class="btn" onclick="add();">
				  		<i class="icon-plus"></i>
				  		New
				  	</a>
				  	<a class="btn" onclick="downs();">
				  		<i class="icon-download"></i>
				  		Export
				  	</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="searchTable">
			<thead id="tr_title">
			<tr>
				<th class="width_">Number</th>
				<th class="width_">Department(Eng)</th>
				<th class="width_">Department(Chi)</th>
				<th class="width_">Submit Name</th>
				<th class="width_">Submit Date</th>
				<th class="width_">Modify Name</th>
				<th class="width_">Modify Date</th>
				<th class="width_">Status</th>
				<th class="width_">Handle</th>
			</tr>
			</thead>
		</table>
		<div align="right" class="page_and_btn"  >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="8" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a> Total
						<SPAN style="color: red;" id="total">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
   
   <div id="shelter">
   
</div>
   
    <div id="add_shelter">
   
   </div>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
var basepath = '<%=basePath%>';

function add()
{
	var url = basepath + "/namecard/hr/department_info.jsp";
	addlhg(420,300,url)
}
function modify(id){
	var data = down[id];
	var url = basepath + "/namecard/hr/department_modify.jsp";
	editlhg(420,300,url,data);
}
</script>   
</body>
</html>