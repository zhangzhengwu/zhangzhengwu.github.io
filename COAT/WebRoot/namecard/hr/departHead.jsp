<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>DepartMent Information</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="css/jquery-1.4.2.min.js"></script>
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
		url:"StaffDepartHeadServlet",
		type:"post",
		async:false,
		data:{"method":"select","startDate":$("#start_date").val(),"endDate":$("#end_date").val(),"dpt":$("#dpt").val(),"sfyx":$("#sfyx").val(),"curretPage":pageNow},
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
							+"</td><td>" +dataRole[0][i].dpt
							+"</td><td  style='text-align:left;'>" +dataRole[0][i].department
							+"</td><td>" +dataRole[0][i].depart_head
							+"</td><td>" +dataRole[0][i].depart_head_bak
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
					html+="<tr class='tr_result'><td colspan='11' align='center'>Sorry, there is no matching record.</td></tr>";
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
function Del(id){
	if(down!=null){
		if(confirm("OK to Delete the information?")){
					$.ajax({
				url:"StaffDepartHeadServlet",
				type:"post",
				data:{"method":"del","addDate":down[id].add_date,"dptId":down[id].id},
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
			window.location.href="<%=basePath%>"+"StaffDepartHeadServlet?method=down&startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&dpt="+$("#dpt").val()+"&sfyx="+$("#sfyx").val();
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
				<td class="tagName">Department Abbreviation:</td>
				<td class="tagCont">
					<input id="dpt" type="text" />
				</td>
				<td class="tagName">Is valid:</td>
				<td class="tagCont">
					<select id="sfyx"><option value="">please choose option</option><option>Y</option><option>N</option></select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" onclick="selects(1);">
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
				<th class="width_5">Number</th>
				<th class="width_15">Department Abbreviation</th>
				<th class="width_20">Department</th>
				<th class="width_10">Department Head</th>
				<th class="width_10">Department Head2</th>
				<th class="width_5">Submit Name</th>
				<th class="width_10">Submit Date</th>
				<th class="width_5">Modify Name</th>
				<th class="width_10">Modify Date</th>
				<th class="width_5">Status</th>
				<th class="width_5">Handle</th>
			</tr>
			</thead>
		</table>
		<div align="right" class="page_and_btn"  >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
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
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug/js/Common.js"></script>
<script type="text/javascript">
var basepath = '<%=basePath%>';
function add()
{
	var url = basepath + "namecard/hr/departHead_info.jsp";
	addlhg(480,340,url);
}
function modify(id)
{
	var data = down[id];
	var url = basepath + "namecard/hr/departHead_modify.jsp";
	editlhg(480,340,url,data);
}

</script> 
    <div id="add_shelter">
   
   </div>
</body>
</html>
