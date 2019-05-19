<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'publicUpload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="css/ajaxfileupload.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
	<style type="text/css">
	body{background: #fff!important;}
	.info-upload-title{height: 30px; width: 100%; border-bottom: 1px solid #ddd; position: relative;}
	.info-upload-title .btn{padding: 0px 10px!important; font-size: 0.8em; margin-top: 3px; }
	#up, #up_btn{width: 80px; height: 22px;}
	#up{position: absolute; z-index: 1;  -webkit-opacity: 0;  /* Netscape and Older than Firefox 0.9 */  -moz-opacity: 0;  /* Safari 1.x (pre WebKit!) 老式khtml内核的Safari浏览器*/  -khtml-opacity: 0;  /* IE9 + etc...modern browsers */  opacity: 0;  /* IE 4-9 */  filter:alpha(opacity=0);  /*This works in IE 8 & 9 too*/  -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";  /*IE4-IE9*/  filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0)!important;}
	.info-upload-cont{}
	
	.reg-button{height: 40px; padding: 10px 14px; border-bottom: 1px solid #ddd; position: relative;}
	.reg-upload{padding: 4px 10px;}
	.upload-item-btn{position: absolute; right: 14px; top: 2px; }
	.upload-item-btn a{display: block; text-align:center; cursor: pointer; text-decoration: none; }
	.upload-item-btn a:hover{color: #555;}
	
	.upload-item{position: relative;background-color: #F5F5F5;-webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;font: 11px Verdana, Geneva, sans-serif;margin-top: 5px;padding: 5px 10px;}
	.process-line{background-color: #E5E5E5;margin-top: 10px;margin-bottom: 2px;width: 100%;}
	.process-line .line{background-color: #0099FF;height: 3px;width: 1px;}
    .fileName, .state{font-size: 0.8px;}
    .state{margin-left: 20px;}
   
	
	</style>
  </head>
  
<body>
<div class="info-cont">
	<div class="info-upload-title">
		<input type="file" id="up" name="up"/>
		<input type="button" class="btn" id="up_btn" value="选择文件"/>
		<input type="button" class="btn" id="doupload" value="开始上传"/>
	</div>
	<div class="info-upload-cont" id="reg-upload">
		暂无内容
	</div>
</div>
<script type="text/javascript">

$('#up').live('change',function(){
	var _fileName = getFileName($(this).val());
	var _size = 0, _state = '', obj = null, html = '';
	
	if(_fileName == '' || _fileName == 'undefined'){
		return false;
	}
	obj = {
		'filename' : _fileName,
		'size' : _size + 'mb',
		'state' : _state
	};
	html = getULItem(obj);
	
	$('#reg-upload').empty().append(html);
});
//获取上传文件名
function getFileName(path)
{
	var _path = '', _splitPath = '', _fileName = '';
	
	_path = path;
	_splitPath = _path.split("\\");
	_fileName = _splitPath[_splitPath.length - 1];
	
	return _fileName;
}
/*
 * 制作一个upload Div块 
 */
function getULItem(obj)
{
	var _obj = null, html = '';
	var _id = 0, len = $('.upload-item').length, lastId = '', lastIndex = 0;
	
	//设置item的下标值用于删除
	if(len > 0){
		lastId = $('.upload-item')[len-1].id;
		lastIndex = parseInt(lastId.substring(12,lastId.length));
	}	
	id = len > 0 ? ++lastIndex :  1;

	_obj = $('<div class="upload-item" id="upload-list-'+id+'"></div>');
	html = '<div class="upload-item-btn">'+
		   '<a onclick="javascript: removeItem('+id+')">x</a>'+
		   '</div>'+
		   '<span class="fileName">'+obj.filename+'('+obj.size+')</span>'+
		   '<span class="state" id="state-'+id+'" style="color: red;">'+obj.state+'</span>'+
		   '<div class="process-line">' +
		   '<div class="line" id="line-'+id+'"></div>' +
		   '</div>';
	$(_obj).html(html);
	
	return $(_obj);
}
//用于setTimeout自动移除
function clearItem()
{
	if($('#upload-list-1').length == 0){
		return false;
	}
	$('#upload-list-1').slideUp(500,function(){
		$(this).remove();
	});
}
//移除某个item
function removeItem(id)
{
	var _obj = $('#upload-list-' + id);
	
	$('#up').val('');
	$(_obj).remove();
}
/* 对附件进行校验，通过则返回URL */
 var api = frameElement.api, _data = api.data, W = api.opener;
function fileupload(){
   if($("#filePaths").val()==""){
	   api.close();
       W.alert("请选择有效文件"); 
       return false;  
   }  
  
   $.ajaxFileUpload({  
        url:'UpLoadFileServlet', 
        secureuri:false,  
        fileElementId:'up',  
        dataType: 'text/html',
        complete: function(){
	   		//api.close();
        },
        success: function (data) {
	   		if(data != 'error'){
	   			$('#line-1').animate({
					'width' : 100+'%'
				},500,function(){
					//字符创调用函数
					W[_data](data);
				});
	   		}else{
	   			W.alert("请选择有效文件");
	   			
	   			$('#upload-list-1').slideUp(500,function(){
					$(this).remove();
				});
	   			return false;
	   		}
        	//alert(data);
        },error: function (data, status, e){  
            alert("fail");  
        }  
    });  
}
function sayHello(){
	alert('hello');
}
$('#doupload').bind('click',function(){
	fileupload();
});
</script>
</body>
</html>
