<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'cacularAttendance.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">



 <style type="text/css">
 .status{width:50px;}
 .status img{display: none;}
 .result{text-align:left;}
 
 
 .fileup{
	position: relative;
	display: table;
	height: 26px;
	width: 100%;
}
.fileup .filecont{
	/*display: table-cell;*/
	width: 100%;
	*width: auto;
	height: 26px;
	*height: 14px;
	z-index: 50;
	padding: 6px 12px;
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
	padding: 5px 12px;
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
	height: 26px;
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
.fileup>.fileBtn{
	position: absolute;
	right: 120px;
	top: 0px;
	border: 1px solid #ccc;
	background: #fff;
	color: #333!important;
	display: none;
	height: 26px;
	*height: 28px;
	font-size: 12px;
	padding: 0px 30px;
}
.fileBtn:hover{
	background: #eee;
}
.btn-group{
	position: relative;
    display: inline-block;
    font-size: 0;
    vertical-align: middle;
    white-space: nowrap;
}
.btn-group .btn{
	padding: 4px 25px;	
}

.tagName{
	width: 35%!important;
}
 </style>
</head> 
<body>

	<div class="cont-info">
		<div class="info-title">
			<div class="title-info">
				<table>
						<tr>
							<td style="text-align: center;" colspan="4">
								<p class="alert alert-info">
									上传文件基本路径:\\bocnas11\dept\SZO\CSD\SZO Programm\SZO Admin\Attendence\test\
								</p>
							</td>
						</tr>
						<tr>	
							<td class="tagName">Resign consultant list:</td>
							<td class="tagCont" ><input type="text" id="resign"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
						<tr>	
							<td class="tagName">Consultant_Card_Borrow:</td>
							<td class="tagCont" ><input type="text" id="borrow"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
							<tr>	
							<td class="tagName">C-Club Member:</td>
							<td class="tagCont" ><input type="text" id="cclub"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
						<tr>	
							<td class="tagName">Exceptional Date:</td>
							<td class="tagCont" ><input type="text" id="exceptionDate"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
						<tr>	
							<td class="tagName">Consultant_Leave Records:</td>
							<td class="tagCont" ><input type="text" id="leaveRecord"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
					
						<tr>	
							<td class="tagName">Emap record:</td>
							<td class="tagCont" ><input type="text" id="emap"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
						<tr>	
							<td class="tagName">Training record:</td>
							<td class="tagCont" ><input type="text" id="trainingList"  readonly="readonly"></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
				
						<tr>
							<td></td>
							<td style='text-align:right; padding-right: 16px;'>
								<button type="submit" name="Submit2" class="btn"  onclick="uploadBaseData()" >上 传</button>
							</td>
							<td></td>
							<td ></td>
						</tr>
						
						<tr>
							<td colspan="4">
							<hr style="width:100%;"/>
							</td>
						</tr>
						<c:if test="${roleObj.upd==1}">
						<tr>
							<td class="tagName"></td>
							<td class="tagCont" colspan="3">
								
								<div class="btn-group" style="margin-bottom: 20px;">	 
									<a class="btn" id="b4">处理Trainee数据</a>
									<a class="btn" id="b5">计算部分数据</a>
									<a class="btn" onclick="cacularAttendance()">处理Consultant整月数据</a>
								</div>
							</td>
						</tr>
						</c:if>	
						<tr>	
							<td class="tagName">Traninee record:</td>
							<td class="tagCont" >		<input type="text" id="trainee" readonly="readonly"/></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
						<tr id="cons">	
							<td class="tagName">Consultant List:</td>
							<td class="tagCont" >		<input type="text" id="conslist" readonly="readonly"/></td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"><label></label></td>
						</tr>
						<tr id="attendance">	
							<td class="tagName">Attendance（主表）:</td>
							<td class="tagCont" id="setFileUp">
								<script type="text/javascript" src="plug/js/fileUp.js"></script>
								<script type="text/javascript">
									$('#setFileUp').fileUp({
										fileName : 'filePaths',
										fileId : 'filePaths',
										upbtnId : 'inputs',
										upload : function(){
											uploadFile();
										}
									});
								</script>
							</td>
							<td class="status"><img src="<%=basePath%>images/arrow_top.gif"/></td>
							<td class="result"></td>
						</tr>
						<tr style="height: 40px;"></tr>
				</table> 
	
			</div>

		</div>
<div id="status"></div>

	</div>





 <script type="text/javascript" src="<%=basePath%>plug/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>css/ajaxfileupload.js"></script>

<script type="text/javascript">
var basePath="<%=basePath%>";
var folder="\\\\bocnas11\\dept\\SZO\\CSD\\SZO Programm\\SZO Admin\\Attendence\\test\\";//文件存储盘符
var complete=false;
var n=0;
$(function() {
	$("#resign").val("terminate&resign consultant list.xls");
	$("#leaveRecord").val("Leave Records.xls");
	$("#borrow").val("Card Borrrow Records.xls");
	$("#emap").val("Emap.xls");
	$("#exceptionDate").val("Exceptional Date.xls");
	$("#cclub").val("C-Club Member.xls");
	$("#trainee").val("trainee list.xls");
	$("#trainingList").val("Training List.xls");
	$("#conslist").val("consultant List.xls");
	$(".tagCont input[type='text']").width(500);
	
	
	
	
	
	
	
	
	
	
	
	
	/************************************************b4单击事件***********************************************/
	$("#b4").click(function(){
			upExcel(folder+$("#trainee").val(),"#s10","#i10");
	});

	/************************************************b5单击事件***********************************************/
	$("#b5").click(function(){
		var cons="O:\\SZO\\CSD\\SZO Programm\\SZO Admin\\Attendence\\test\\consultant other.xls";
		upExcel(cons,"#s9","#i9");
	});
});











/**
 * 计算Consultant 整月数据
 */
function cacularAttendance(){
	if(confirm("确定开始计算Consultant 整月打卡数据?")){
		$.ajax({
			url:basePath+"attendance/AttendanceWriteServlet",
			type:"post",
			beforeSend: function(){
				parent.showLoad();
				$("#cons").find("img").attr("src",basePath+"images/arrow_top.gif").show();
				$("#cons").find("label").empty();
			},
			complete: function(){
				parent.closeLoad();
			},
			data:{"method":"cacularAttendance","conslist":$("#conslist").val()},
			success:function(date){
				var result=$.parseJSON(date);
				alert(result.msg);
				var imgs="";
				if(result.state=="success"){
					imgs="images/yes.png";
				}else{
					imgs="images/no.png";
				}
				$("#cons").find("img").attr("src",basePath+imgs);
				$("#cons").find("label").empty().append(result.msg);
			},error:function(){
				alert("Connection Error!");
			}
		});
	}
}



/**
 * 上传计算所需基础数据
 */
function uploadBaseData(){
	if(confirm("确定开始上传基础数据?")){
		$("div.title-info tr:lt(9)").find("img").attr("src",basePath+"images/arrow_top.gif").show();
		$("div.title-info tr:lt(9)").find("label").empty();
		$.ajax({
			url:basePath+"attendance/AttendanceWriteServlet",
			type:"post",
			beforeSend: function(){
				parent.showLoad();
				complete=false;
				window.setTimeout("getStatus()", 1000);
			},
			complete: function(){
				parent.closeLoad();
				complete=true;
			},
			data:{"method":"uploadbaseData",
				"resign":$("#resign").val(),
				"borrow":$("#borrow").val(),
				"cclub":$("#cclub").val(),
				"exceptionDate":$("#exceptionDate").val(),
				"leaveRecord":$("#leaveRecord").val(),
				"emap":$("#emap").val(),
				"trainingList":$("#trainingList").val()
				//"trainee":"trainee list.xls"
				},
			success:function(date){
				var result=$.parseJSON(date);
				alert(result.msg);
			},error:function(){
				alert("Connection Error");
				return false;
			}
		});
	}
		
		

}

/**
 * 上传Attendance表
 */
function uploadFile(){
	if($("#filePaths").val()==""){
		alert("请选择Attendance文件");
		return false;
	}
	$("#attendance").find("img").attr("src",basePath+"images/arrow_top.gif").show();
	$("#attendance").find("label").empty();
	parent.showLoad();
    $.ajaxFileUpload({  
        url:'UpLoadFileServlet', 
        secureuri:false,  
        fileElementId:'filePaths',  
        dataType: 'text/html',
        complete: function(){
    		$('#filecont').empty();
        },
        success: function (data) {
           if(data!="error"){
               fileName=data;
               uploadAttendance(fileName);
           }else{
 	 		    alert(data);
 	 		  parent.closeLoad();
 	 		}
        },error: function (data, status, e){  
            alert("Connection Error");  
            parent.closeLoad();
        }  
    });  
}

function uploadAttendance(filename){
	$.ajax({
		url:basePath+"attendance/AttendanceWriteServlet",
		type:"post",
		data:{"method":"uploadAttendance","attendance":filename},
		complete: function(){
			parent.closeLoad();
		},
		success:function(date){
			var result=$.parseJSON(date);
			alert(result.msg);
			$("#attendance").find("label").empty().append(result.msg);
			if(result.state=="success"){
				imgs="images/yes.png";
			}else{
				imgs="images/no.png";
			}
			$("#attendance").find("img").attr("src",basePath+imgs);
			$("#attendance").find("label").empty().append(result.msg);
		},error:function(){
			alert("Connection Error");
			return false;
		}
		
	});
}


var cache="";
function getStatus(){
	if(complete){
		//alert(complete+"--"+n);
		cache="";
		return false;
	}else{
		n++;
		$.ajax({
			url:basePath+"attendance/AttendanceWriteServlet",
			type:"post",
			data:{"method":"getuploadStatus"},
			success:function(date){
					var result=eval(date);
					for(var i=0;i<result.length;i++){
						//$("#status").append(result[i]);
						
						for(var item in result[i]){
							if(cache.indexOf(item)<0){
								cache+=item+" ";
								var imgs="";
								if(parseFloat(result[i][item])>0){//正常
									imgs="images/yes.png";
									$("#"+item+"").parent().next().next().find("label").empty().append("成功上传条数 :"+result[i][item]);
								}else if(parseFloat(result[i][item])==-1){//文件未找到
									imgs="images/no.png";
									$("#"+item+"").parent().next().next().find("label").empty().append("文件未找到！");
								}else{
									imgs="images/no.png";
									$("#"+item+"").parent().next().next().find("label").empty().append("文件上传错误,请检查上传文件！");
								}
								$("#"+item).parent().next().find("img").attr("src",basePath+imgs);
							//	$("#status").append(item+":"+result[i][item]+"<br/>");
							}
							
						}
					}
				
			},error:function(){
				alert("error");
			}
		});
	setTimeout("getStatus()", 1000);
	}
}





/*******************************************************上传通用方法******************************************************************/
function upExcel(up,s1,i1){
		 $.ajax({
			type: "post",
			url:basePath+"UpEmapServlet",
			dateType:"html",
			data: {'up':up},
			beforeSend:function() {
				parent.showLoad();
			},	
			complete: function(){
				parent.closeLoad();
			},
			success:function(date){ 
			
				alert(date);
				},error:function(){
					cl();
					$(s1).attr('src','image/cross.png');

				}
			 });
		 }
 
	
	
</script>
</body>
</html>
