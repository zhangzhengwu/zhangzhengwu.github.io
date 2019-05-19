<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
		java.util.Date date=new java.util.Date();
        String nowDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);  
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息新增</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


	<script type="text/javascript" src="<%=basePath%>plug/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plug/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="<%=basePath%>plug/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plug/js/Common.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>css/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="<%=basePath%>css/autocomplete/localdata_admin.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/autocomplete/jquery.autocomplete.css"/>
	<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome-ie7.css">
	<![endif]--> 
	<link rel="stylesheet" href="<%=basePath%>css/layout.css">
	<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
	<link rel="stylesheet" href="<%=basePath%>plug/autocomplete/jquery.autocomplete.css">
	<style type="text/css">
		.tagName{width: 15%!important;}
		.tagCont{width: 35%!important; position: relative;}
		.i-trash{cursor: pointer; position: absolute; top: 7px; margin-left: 3px;}
		.i-trash:hover{text-decoration: underline;}
		 	/* <--------临时添加 */
	.viable_span{
		border-radius: 4px;
		border: 1px solid rgb(223, 223, 223);
		height: 22px;
		vertical-align: middle;
		line-height: 20px;
		min-width: 50px;
		text-align: center;
		margin: 0px 5px 0px 0px;
		padding: 0px 5px;
		background-color: rgb(252, 252, 252);
		cursor: pointer;
		margin: 2px 5px;
		/*color: #428bca!important;
 		font-size: 0.9em!important;*/
	}
	.textDiv{float: left; width: 20%:}
	#to1, #to2{
		float: left;
		width: 80%;
	}
	strong{
		font-family: "microsoft yahei";
		/*color: red;*/
		font-size: 13px!important;
		/*font-weight: lighter!important;*/
	}
	</style>
		
  </head>
  
<body> 
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">收件人：</td>
				<td class="tagCont" colspan="3">
					<div class="textDiv">
						<input type="text" id="recipient" class="text"/>
					</div>
					<div id="to1"></div>
				</td>
			</tr>
			<%--<tr>
				<td width="176px;" height="30px;"></td>
			</tr>--%>
			<tr>
				<td class="tagName">组别：</td>
				<td class="tagCont" colspan="3">
					<div class="textDiv">
						<input type="text" id="group" class="text"/>
					</div>
					<div id="to2"></div>
				</td>
			</tr>
			<%--<tr>
				<td  width="176px;" height="30px;"></td>
			</tr>--%>
			<tr>
				<td class="tagName">标题：</td>
				<td class="tagCont">
					<input type="text" id="subject" class="text"/>
				</td>
				<td class="tagName">类型：</td>
				<td class="tagCont">
					     <select id="type">
                              <option value="" id="types">请选择</option>
                         </select>  
				</td>
			</tr>
			<tr>
				<%--<td class="tagName">roles：</td>
				<td class="tagCont">
					<input id="roles"  class="text" type="text"/>
				</td>--%>
				<td class="tagName">company：</td>
				<td class="tagCont">
					<input id="company"  class="text" type="text"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">startdate：</td>
				<td class="tagCont">
					<input type="text" name="startdate" id="startdate" onClick="Calendar('startdate');">
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#startdate').val('');"></i>
				</td>
				<td class="tagName">enddate：</td>
				<td class="tagCont">
					<input type="text" name="enddate" id="enddate" onClick="Calendar('enddate');">
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#enddate').val('');"></i>
				</td>			
			</tr>
			<tr>
				<td class="tagName">attr：</td>
				<td class="tagCont">
					<input id="attr"  class="text" type="text"/>
				</td>				
			</tr>
			<tr>
				<td class="tagName">content:</td>
				<td class="tagCont" colspan="3">
					<div id="content"></div>
					<script type="text/javascript">
				        var ue = getUeditorObj("content");
				    </script>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					<span id="errorMsg" style="color: red;"></span>
					<input id="nowDate"  class="text" type="hidden" value="<%=nowDate%>" />
				</td>
			</tr>
		</table>
	</div>
</div>


	<script type="text/javascript" src="<%=basePath%>css/date.js" ></script>
<%--<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
--%>
<script type="text/javascript" src="plug/autocomplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
var basepath = '<%=basePath%>';
var api = frameElement.api, W = api.opener;
var data = api.data;
var html="";
var dataRole=eval(data);
var user_array;
var group_arry;
var tohtml1="";
var tohtml2="";
$(function (){
	$.ajax({
		type:"post",
		url: "notice/NoticeServlet",
		data:{"method":"selectUser"},
		async:false,
		success:function(date){
			user_array=eval(date);
		},error:function(){
			alert(" Network connection is failed, please try later...");//网络连接失败
			return false;
		}
	});
	
	$.ajax({
		type:"post",
		url: "notice/NoticeServlet",
		data:{"method":"selectGroup"},
		async:false,
		success:function(date){
			group_arry=eval(date);
		},error:function(){
			alert(" Network connection is failed, please try later...");//网络连接失败
			return false;
		}
	});
	
	$("#recipient").autocomplete(user_array, {
		minChars: 1,					//最少输入字条
		max: 12,
		autoFill: false,				//是否选多个,用","分开
		mustMatch: false,				//是否全匹配, 如数据中没有此数据,将无法输入
		matchContains: true,			//是否全文搜索,否则只是前面作为标准
		scrollHeight: false,
		width:186,
		multiple:false,
		formatItem: function(row, i, max) {					//显示格式
			return "<span style='width:100px'>"+row.loginname+"</span>";
		},
		formatMatch: function(row, i, max) {				//以什么数据作为搜索关键词,可包括中文,
		   return row.loginname;
		},
		formatResult: function(row) {						//返回结果
			return row.loginname;
		}
	});
	
	$("#recipient").result(function(event, row, formatted) {
		$("#recipient").val("");
		if(tohtml1.indexOf("<span class='viable_span'>"+row.loginname+"</span>")>-1){
		
		}else{
			tohtml1+="<span class='viable_span'>"+row.loginname+"</span>";
		}
		
		$("#to1").empty().append(tohtml1);
     });
	
	$("#to1 span").live("dblclick",function(){ 
		//$("#to1 span").dblclick(function(){ 
		tohtml1=tohtml1.replace("<span class='viable_span'>"+$(this).html()+"</span>","");
		$(this).remove();	
		//alert('双击弹出');
	});
	
	$("#group").autocomplete(group_arry, {
		minChars: 1,					//最少输入字条
		max: 12,
		autoFill: false,				//是否选多个,用","分开
		mustMatch: false,				//是否全匹配, 如数据中没有此数据,将无法输入
		matchContains: true,			//是否全文搜索,否则只是前面作为标准
		scrollHeight: 220,
		width:300,
		multiple:false,
		formatItem: function(row, i, max) {					//显示格式
			return "<span style='width:50px'>"+row.groupcode+"</span><span style='width:200px'>"+row.groupname+"</span>";
		},
		formatMatch: function(row, i, max) {				//以什么数据作为搜索关键词,可包括中文,
		   return row.groupname;

		},
		formatResult: function(row) {						//返回结果
			return row.groupcode;
		}
	});
	var group_id="";
	$("#group").result(function(event, row, formatted) {
		$("#group").val("");
		group_id=row.groupid;
		if(tohtml2.indexOf("<span class='viable_span' name='"+row.groupid+"'>"+row.groupname+"</span>")>-1){
							
		}else{
			tohtml2+="<span class='viable_span' name='"+row.groupid+"'>"+row.groupname+"</span>";
		}
		$("#to2").empty().append(tohtml2);
     });
	
	$("#to2 span").live("dblclick",function(){ 
		tohtml2=tohtml2.replace("<span class='viable_span' name='"+group_id+"'>"+$(this).html()+"</span>","");
		$(this).remove();		
	});
	
});

for(var i=0;i<dataRole.length;i++){
    html+="<option value='"+dataRole[i].type+"'>"+dataRole[i].type+"</option>";
}
$("#types").after(html);  

api.button({
	name : '保存',
	callback : save
},{
	name : '取消'
});

	function save(){
		var recipient_value="";
		var roles_value="";
		$("#to1 span").each(function (){
			if(recipient_value==""){
				recipient_value+=$(this).text();
			}else{
				recipient_value+=","+$(this).text();
			}
		});
		$("#to2 span").each(function (){
			//alert($(this).attr("name"));
			if(roles_value==""){
				roles_value+=$(this).attr("name");
			}else{
				roles_value+=","+$(this).attr("name");
			}
		});
		if(recipient_value=="") {
			W.error("收件人不能为空!");
			$("#recipient").focus();
			return false;
		}
		if($("#subject").val()=="") {
			W.error("标题不能为空!");
			$("#subject").focus();
			return false;
		}
		if($("#type").val()==""||$("#type").val()=="请选择"){
			W.error("消息类型不能为空!");
			$("#type").focus();
			return false;
		}
		if($("#startdate").val()=="") {
			W.error("开始时间不能为空!");
			$("#startdate").focus();
			return false;
		}
		if($("#startdate").val() < $("#nowDate").val()){
			$("#startdate").focus();
			W.error("开始时间不能小于当前时间!");
			return false;
		}
		if($("#startdate").val()!="" && $("#enddate").val()!=""){
			if($("#startdate").val()>$("#enddate").val()){
				$("#startdate").focus();
			    W.error("开始时间不能大于终止时间!");
				return false;
			}
		}
	
		var _content = ue.getContent();
		if(_content == "" || _content == null){
			$('#errorMsg').text("内容不能为空!");
			return false;
		}
		//alert(recipient_value+"--->"+roles_value);
		if(confirm("是否保存？")){
		   $.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",			
			 data:{"recipient":recipient_value,"type":$("#type").val(),"roles":roles_value,
			   "startdate":$("#startdate").val(),"enddate":$("#enddate").val(),"attr":$("#attr").val(),"company":$("#company").val(),"content":_content,"subject":$("#subject").val(),"method":"addNotice"},
			 success:function(data){
				   var dataRole=eval(data);
				   if(dataRole==1){
					   W.success("保存成功！");
					   api.close();
				   }else{
					   W.error("保存失败！");
				   }
				}			
			});
		   return false;	 
 	 	}
	}
</script>
</body>
</html>
