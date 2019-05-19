<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'test.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/autocomplete/jquery.autocomplete.css"/>
	
  <script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>

	<script type="text/javascript" src="css/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="css/autocomplete/localdata_admin.js"></script>

	
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
  
  <body><%--
   	  自动搜索功能：<input type="text" name="test" id="test"  /> 
   	 <p></p><p></p>
   	  
	--%><p>&nbsp;</p>
	<p>请输入信息</p>
	<p>
		<input type="text" name="input" id="input" />
	</p>
	<p>额外触发信息</p>
	<p>
		<input type="text" name="output" id="output" />
	</p>
  </body>
  
<script type="text/javascript">
var user_array;
$(function (){
   	/**
   	 * 设时延迟1s执行
   	 */
	/**var last;
	$("#input").keyup(function(event){
		last = event.timeStamp;	//利用event的timeStamp来标记时间，这样每次的keyup事件都会修改last的值，注意last必需为全局变量
		setTimeout(function(){
			if(last-event.timeStamp==0){//如果时间差为0（停止输入1s之内都没有其它的keyup事件发生）
				
	        }
		},1000);
	});**/

   	$.ajax({
		type:"post",
		url: "notice/NoticeServlet",
		data:{"method":"selectUser","inputText":$("#input").val()},
		async:false,
		success:function(date){
			user_array=eval(date);
		},error:function(){
			alert(" Network connection is failed, please try later...");//网络连接失败
			return false;
		}
	});
	
	$("#input").autocomplete(user_array, {
		minChars: 1,					//最少输入字条
		max: 12,
		autoFill: false,				//是否选多个,用","分开
		mustMatch: false,				//是否全匹配, 如数据中没有此数据,将无法输入
		matchContains: true,			//是否全文搜索,否则只是前面作为标准
		scrollHeight: 220,
		width:152,
		multiple:false,
		formatItem: function(row, i, max) {					//显示格式
			return "<span style='width:50px'>"+row.loginname+"</span>";
		},
		formatMatch: function(row, i, max) {				//以什么数据作为搜索关键词,可包括中文,
		   return row.loginname;

		},
		formatResult: function(row) {						//返回结果
			return row.loginname;
		}
	});
	
      //  $("#output").val(row.id) ;
	 //$("#input").result(function(event, row, formatted) {				//额外触发,可不要
		//更多操作
     //});
    	
}); 

 
  
  
</script>
  
</html>
