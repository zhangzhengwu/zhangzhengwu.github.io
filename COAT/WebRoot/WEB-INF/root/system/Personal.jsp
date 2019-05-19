<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Personal Head Image</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="<%=basePath%>plug/css/layout.css">
<script type="text/javascript" src="<%=basePath%>css/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=basePath%>css/ajaxfileupload.js"></script>

<script type="text/javascript">
var basepath = "<%=basePath%>";


function uploadHead(){
	if(confirm("确定已有头像文件?")){
	 $.ajaxFileUpload({  
	        url:'UploadHeadimgServlet', 
	        secureuri:false,  
	        fileElementId:'uploadifys',  
	        dataType: 'text/html',
	        success: function (data) {
		   		if(data != 'error'){
		   			var result=$.parseJSON(data);
		   			if(result.state=="success"){
		   				alert("头像上传成功！");
		   				$("#head_img").attr("src", result.msg);
		   			}else{
		   				alert(result.msg);
		   			}
		   		}else{
		   			alert("请选择有效文件");
		   			return false;
		   		}
	        },error: function (data, status, e){  
	            alert("fail");  
	        }  
	    });  
	}
}
$(function(){

	$("#head_img").error(function(){
		this.src="<%=basePath%>/plug/img/1.jpg";
	});
});

</script>
</head>
<body>
	<div style="margin: 15% 30%; position: absolute; width: 400px;">
		<div id="imgList" style="margin-bottom: 20px;">
			<img id="head_img" style="width: 250px"
				src="${loginUser.headimage==null?'#':loginUser.headimage}"
				alt="求选择图片上传">
		</div>
		<p>
			<input type="file" name="uploadifys" id="uploadifys" /> <a
				href="javascript:void(0);" onclick="uploadHead()">头像上传</a>

		</p>

	</div>
</body>
</html>
