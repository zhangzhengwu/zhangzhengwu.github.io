<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML>
<html>
<head>
 <base href="<%=basePath%>"> 
    
<title>My JSP 'index.jsp' starting page</title>
   <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="css/ajaxfileupload.js"></script>
	 <link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
	<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome-ie7.css">
	<![endif]--> 
	<link rel="stylesheet" href="<%=basePath%>css/layout.css">
	<script type="text/javascript">
	var realName=null;
	$(function(){
	
		//getproduct();
 
		$("#uploads").click(function(){<%-- 显示上传层 --%>
		$("#shangchuan").show(200);
		return false;
		});
		//Wilson test
		$("#reporttest").click(function(){
		 
			//window.location.href="../ReportMarkPremiumServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&ordercode="+$("#ordercodes").val()+"&staffcode="+$("#staffcodes").val();
			$.ajax({
				type: "post",
				url:"ReportTestServlet",
				async:false,
				data: null,
				success:function(date){
					//clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
					alert(date);
				},error:function(){
					alert("导出失败!");
				}
			});
		});

	
	
	
	 
	});  
function getPath(obj)  { 
	if(obj){ 
			if (window.navigator.userAgent.indexOf("MSIE")>=1){
				obj.select(); $("#products").focus();
				return document.selection.createRange().text;    
			} else if(window.navigator.userAgent.indexOf("Firefox")>=1){    
				if(obj.files){
					return obj.files.item(0).getAsDataURL();
		 		}       
				return obj.value; 
			}     
		return obj.value;   
		} 
	}  
	//上传
	  var rightFileType= new Array("xls");  //定义要上传文件的类型
	
	
	
           function fileupload(){
               if($("#up").val()==""){ 
                  alert("请选择有效文件"); 
                   return false;  
               }  
            $.ajaxFileUpload({  
                       url:'UpLoadFileServlet', 
                       secureuri:false,  
                       fileElementId:'up',  
                       dataType: 'text/html',
                       complete: function(){
            				$('#filecont').empty();
                       },
                       success: function (data) {
                         if(data!="error"){
                          var fileName=data;
                          //alert("上传成功，上传路径为："+fileName);
                          uploadAll(fileName);
                         }else{
                        	 fileName=null;
            	 		    alert("上传失败");
            	 		    return false;
            	 		 }
                       },error: function (data, status, e){  
                    	   fileName=null;
                           alert("網絡連接失敗，請稍後重試2...");  
                 			return false;
                       }  
                   });  
           }  
           
     
    function uploadAll(fileNames){
		if(fileNames==null){
			alert("請選擇需要上傳的文件！");
			return;
		}
		$(".titles").remove();
		$("#login").show(); 
		$("#inputs").attr("disabled","disabled");
		 $.ajax({
			type: "post",
			url:"UpAllServlet",
			dateType:"html",
			//data: {'up':getPath($("#up"))},
			data: {'up':fileNames},
			beforeSend:function(){
				parent.showLoad();
			},
			complete:function(){
				parent.closeLoad();
			},
			success:function(date){
			$("#login").hide(); 
				  if(date=="error"){
					alert("数据上传异常!");
					return false;
				}else if(date=="notfound"){
					alert("上传文件未找到!");
					return false;
				}else if(date!="null" && date!="" && date!=null){
				$("#inputs").attr("disabled",false);
					var dataRole=eval(date);
					$("#num").empty();	
					var html="<font>總共"+dataRole[1]+"條記錄</font>";
					$("#num").append(html);
					alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
				
					//getproduct();
				}else{
					alert("非系统所需文件！");
				}
			},error: function (data, status, e){  
                  alert("網絡連接失敗，請稍後重試1...");  
                  return false;
            }  
		 });
		
		
	}
           
	</script>
<style type="text/css">
.uploadlist{
	position: relative;
	width: 98%;
	height: 400px;
	margin: 10px auto 30px auto;;
	border-radius: 5px;
	border: 1px solid #ddd;
}
.uploadlist .remark{
	position: absolute;
	left: 0px;
	top: 0px;
	display: block;
	padding: 3px 6px;
	border-bottom-right-radius: 5px;
	border-right: 1px solid #ddd;
	border-bottom: 1px solid #ddd;
	font-size: 12px;
	color: #333;
	background: #f1f1f1;
}
.uploadlist table{margin: 35px auto!important;}
.uploadlist .tagName{width: 20%; text-align: left!important; padding-left: 10px;}
.uploadlist .tagCont{width: 60%;}
.uploadlist .tagBtn{width: 20%; text-align: right; padding-right: 15px;}

.fileup{
	position: relative;
	display: table;
	height: 34px;
	width: 100%;
}
.fileup .filecont{
	/*display: table-cell;*/
	width: 100%;
	*width: auto;
	height: 34px;
	*height: 14px;
	z-index: 50;
	padding: 10px 12px;
	/*padding: 10px 0px 10px 0px;*/
	font-size: 14px;
	text-align: left;
	vertical-align: middle;
	/* line-height: 1.42857143; */
	color: #555;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
	box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
	-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
	-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
}
.fileup .filecont:hover{
	/*border: #2e6da4;*/
}
.fileup .filebtn{
	display:block;
	z-index: 50;
	cursor: pointer;
	color: #fff!important;;
}
.fileup .filefunction{
	position: absolute;
	right: 0px;
	top: 0px;
	width: 120px;
	height: 100%;
	*height: auto;
	display: inline-block;
	padding: 8px 12px;
	*padding: 8px 0px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	cursor: pointer;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	background: #2C85D1;
	border-top-left-radius: 0px;
	border-bottom-left-radius: 0px;
}
.fileup input[type='file']{
	position: absolute;
	right: 0px;
	top: 0px;
	cursor: pointer;
	height: 34px;
	width: 120px;
	opacity: 0;
	-webkit-opacity: 0;  
    /* Netscape and Older than Firefox 0.9 */  
    -moz-opacity: 0;  
    /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/  
    -khtml-opacity: 0;  
    /* IE9 + etc...modern browsers */  
    opacity: 0;  
    /* IE 4-9 */  
    filter:alpha(opacity=0);  
    /*This works in IE 8 & 9 too*/  
    -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";  
    /*IE4-IE9*/  
    filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0)!important;  
	z-index: 100;
}
.filecont:hover{
	border: 1px solid #428bca;
}
.filefunction:hover{
	background: #337ab7;
}
.filefunction:hover .filecont{
	border: 1px solid red;
}
.fileBtn{
	position: absolute;
	right: 120px;
	top: 0px;
	border: 1px solid #ccc;
	background: #fff;
	color: #333;
	display: none;
	height: 34px;
	*height: 36px;
	font-size: 12px;
	padding: 0px 30px;
}
.fileBtn:hover{
	background: #eee;
}
</style>	 
</head>
  
<body >
<div class="cont-info">
	<div class="info-title">
		<div class="title-head">
			<h1>
				<i class="icon-folder-open"></i>
				Admin 数据处理
			</h1>
		</div>
		<div class="title-info">
			<div class="uploadlist">
				<span class="remark">
					Excel文件
				</span>
				<table>
					<tr>
						<td class="tagName" colspan="3">
							點擊上傳或<a id="reporttest" href="#">导出Excel</a>文件
						</td>
					</tr>
					<tr>
						<td class="tagName" colspan="3" id="setFileUp">
							<script type="text/javascript" src="plug/js/fileUp.js"></script>
							<script type="text/javascript">
								$('#setFileUp').fileUp({
									fileId : 'up',
									fileName : 'up',
									upbtnId : 'inputs',
									width : '100%',
									upload : function(){
										fileupload();
									}
								});
							</script>
						</td>
					</tr>
					<tr>
						<td class="tagName"></td>
						<td class="tagCont">
							
						</td>
						<td class="tagBtn"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<table id="products" class="table-ss" width="100%" height="100%" border="0" align="center" style="display: none;"  >
  <tr >
      <td height="49" width="100%" colspan="8" align="center"><span class="STYLE3"><strong>Admin 数据处理 </strong></span>    </td>
   
  </tr>
    <tr height="70" >
      <td height="35" width="100%" colspan="8" align="right"> <span id="num"><a id="uploads" href="#">點擊上傳Excel文件</a> /// <a id="reporttest" href="#">导出Excel文件</a>  </span></td>
    </tr>
    <tr >
      <td colspan="8" align="right" height="35"><div id="shangchuan" style="display:none;">
          <input type="file"  id="up" name="up" />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input name="submit" type="submit" id="inputs" value="導 入"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div></td>
    </tr>
	<tr height="" >
	<td width="30%"></td>
	<td><img src="images/4yc5.png"> </td>
	<td width="30%">&nbsp;</td>
	</tr>
	
	
	<tr>
	
	<td>
	<div id="upload_view" class="STYLE1" style="display: none;" >
   &nbsp; <input type="file" name="filePaths" id="filePaths"/>
    <input type="button" name="fileLoad" id="fileLoad" value="Upload" onclick="fileupload()"> 
    </div>
	</td>
	</tr>
	
</table>
</body>
</html>
