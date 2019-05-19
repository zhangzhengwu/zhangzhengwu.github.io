<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'uploadfile.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=basePath%>/css/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=basePath%>/css/ajaxfileupload.js"></script>
		<style>
.prog-border {
	height: 15px;
	width: 205px;
	background: #fff;
	border: 1px solid #000;
	margin: 0;
	padding: 2;

}

.prog-bar {
	height: 13px;
	margin: 1px 0;
	padding: 0px;
	background: #178399;
	font-size: 10pt;
}

body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
}
</style>
</head>

<body>
	<div
		style="margin-left:20%; margin-top:10%; width:500px; height:300px; border:solid #000000">
			<BR> 请选择上传的文件： <BR> <input id="importFile" name="importFile" type="file">
			<input id="submitButton" type="submit" value="上传" onclick="fileupload()"/>
		<!-- 这里显示进度条 -->
		<div id="status"></div>
	</div>
	<script type="text/javascript">
	var flag=false;
  var basePath="<%=basePath%>";
  function fileupload(){
	  flag=false;
	  setTimeout(fileprogress(), 0);
	  $.ajaxFileUpload({  
	      url:basePath+'file/FileUploadServlet', 
	      data:{"method":"upload"},
	      secureuri:false,  
	      acceptType:"img",
	      fileElementId:'importFile',  
	      dataType: 'text/html',
	      success: function (date) {
	    	  var result=$.parseJSON(date);
	    	  if(result.status=="success"){
	    		  alert(result.status);
	    	  }else{
	    	 	 alert(result.msg);
	    	  }
	    	
	    	//alert(result.status+'--'+result.filename);
	    	
	      
	      },error: function (data, status, e){  
	   	   fileName=null;
	          alert(data+"--"+status+"--"+e);  
				return false;
	      },complete:function(){
	    	flag=true;  
	      }
	  });  
  }
	// byteProcessed;//已传输大小
	// filesize;//文件大小
	// percent;//完成百分比
	// uploadRate;//上传速度
	// usetime;//已耗时
	// totaltime;//预计总耗时
	// status;//上传状态
	
	 
				
				
			
  					
  function fileprogress(){
	  if(!flag){
		  $.ajax({
			 url:basePath+'file/FileUploadServlet',
			 type:"post",
			 data:{"method":"status"},
			 success:function(date){
				if(date!=""){
					 var result=$.parseJSON(date);
					 var html="<b>文件上传状态:</b><br/>";
	  					html+="<div class='prog-border'><div class='prog-bar' style=\"width: "
							+ result.percent + "%;\"></div></div>";
	  					html+="<b>已上传:</b>"+result.byteProcessed+"<br/>";
	  					html+="<b>总大小:</b>"+result.filesize+"<br/>";
	  					html+="<b>上传速率:</b>"+result.uploadRate+" KB/s<br/>";
	  					html+="<b>已耗时:</b>"+result.usetime+"<br/>";
	  					html+="<b>预计耗时:</b>"+result.totaltime+"<br/>";
	  					html+="<b>状态:</b>"+result.status+"<br/>";
	  					if(result.status=="Y"){
	  						flag=true;
	  					}
					  $("#status").empty().append(html);
				}
			 },error: function (data, status, e){  
			          alert("網絡連接失敗，請稍後重試2...");  
			   }  
		  });
		  setTimeout(fileprogress, 1000);
	  }else{
		  
	  }
	  
  }
  
  
  </script>


</body>

</html>
