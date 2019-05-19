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

		<title>Excel文件上传</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="plug/css/bootstrap.min.css">
		<link rel="stylesheet" href="plug/css/site.css">
		<link rel="stylesheet" href="plug/css/font-awesome.min.css">
		<link rel="stylesheet" href="plug/css/layout.css">
		
	</head>

	<body>
	<div class="cont-info">
		<form  action="" method="post">
			<div class="info-search">
				<table>
				<tr>
					<td width="25px">
						<input class="mybtn1" name="filePaths" id="filePaths" type="file"/>
					</td>
					<td>
						<button style="width:80px;" class="btn" id="fileLoad" name="fileLoad"  type="button" onclick="fileupload();">
							UpLoad
						</button>
					</td>
					
				</tr>
				</table>
			</div>
		</form>
		</div>
		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>css/ajaxfileupload.js"></script>
		<script type="text/javascript" src="plug/datePicker/WdatePicker.js"></script>
		
<script type="text/javascript">

/**********************************************文件上传start**************************************************************/
	function fileupload(){
             if($("#filePaths").val()==""){ 
                 alert("请选择有效文件"); 
                 return false;  
             }  
             $.ajaxFileUpload({  
            	  url:'UpLoadExcelServlet?methods=UpLoadFile', 
            	  secureuri:false,  
                  fileElementId:'filePaths',  
                  dataType: 'text/html',
                  success: function (data){
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
				url:"UpLoadExcelServlet",
				dateType:"html",
				data: {'uploadfile':fileName,"methods":"UpExcel"},
				success:function(date){
					if(date != "null"){
						var dataRole=eval(date);
						if(dataRole[1]==-1){
							alert("文件命名有误,应以[cwsi_user]开头!");
							return false;
						}
						//alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
						alert("導入數據成功,此次操作共耗时"+dataRole[2] +"分鐘"+dataRole[1]+"條數據");		
					}else{
						alert("非系统所需文件！");
					}
				 },error:function(){
					 alert("网络连接失败，请稍后重试...");
					 return false;
				 } 
				});
           } 
  
/**********************************************文件上传end**************************************************************/	

</script>
	</body>
</html>
