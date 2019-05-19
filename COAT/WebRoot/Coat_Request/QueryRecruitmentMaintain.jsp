<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryRecruitment</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/Util.js?ver=<%=new Date() %>"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<script type="text/javascript" src="./css/ajaxfileupload.js"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
    <style type="text/css">
<!--

 .images{
width:16px;
 height:16px; 
 margin-top:2px;
}
.inputNumber{
width: 24px;
border-left-width: 0px;
border-right-width: 0px;
border-top-width: 0px;
border-bottom-width: 0px;

}
.inputNumber_color{
width: 24px;

 border:#009AE2 solid 1px;


}

#Medical {
position:absolute;
	width:100%;
	height:99%;
	z-index:0;
	left: 0px;
	top: 0px;
}
#overs {

	position:absolute;
		top: 0px;
	width:100%;
	height:100%;
	z-index:50;
	display:none;
	font-size: 14px;
	font-family: 'Arial Narrow';
	background-color: #F5FFFA;
}
.span_menu{
	background-image: url("images/bg.gif");
	z-index:5;
	width: 100%;
}
.head_bottom{
	top:expression(document.getElementById('divIdSelected').scrollTop-2);
 	position:relative ;
 	background-color: silver;
	height: 25px;
}

#divIdSelected{
	 
	/****滚动块颜色*/
	SCROLLBAR-FACE-COLOR: #F0FFFF;
 	/****滚动块边框颜色*/
 	SCROLLBAR-SHADOW-COLOR: #fff; 
 	/****滚动条箭头颜色*/
 	SCROLLBAR-ARROW-COLOR: #EAECEC;
    /****滚动条背景颜色*/
    SCROLLBAR-TRACK-COLOR: #fff;
}

body{
	font-family: 'Arial Narrow';
	width: 100%;
	background-image: url("css/officeAdmin_bg.gif");
	overflow-x:hidden;
}
tr td{
	font-size: 12px;
	font-family: 'Arial Narrow';
}
.width_left{
	left:0px;
	width:100%;
}
.txt{
    color:#0000FF;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 	background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-size: 12px;
	font-family: 'Arial Narrow';

}
.select{text-align: center;
   height: 12px;
}

.txts{
    color:#005aa7;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-size: 14px;
	font-family: 'Arial Narrow';

}
.inputstyle{
border-width:0px;
background-color: #F5FFFA;
}
img{
	width: 12px;
	height: 12px;
}
.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
.STYLE1 {
		font-size: 14px;
	font-family: 'Arial Narrow';
	font-weight: bold;
	color: #00305A;
}
-->
    </style>
  <link href="./css/style.css" rel="stylesheet" type="text/css">
  </head>
  <script type="text/javascript" >
  var curPage=1;
  var totalPage=1;
  	
  
  $(function(){
	$("#pre").bind("click", function() {//上一页
		curPage=curPage - 1;
		selects();
	});
	
	$("#next").bind("click", function() {//下一页
		curPage=curPage + 1;
		selects();
	});
	$("#first").bind("click", function() {//首页
		curPage=1;
		selects();
	});
	$("#end").bind("click", function() {//尾页
		curPage=totalPage;
		selects();
	});
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
		selects();
	});
    $("#upload").click(function(){
    	 fileupload();
    });
	
  });
  
  
  	function selects(){
  		   if($("#date").val() !="" && $("#date1").val() !=null){
  			   
  			   var start=$("#date").val();
  			   var end=$("#date1").val();
  			   if(start>end){
  				   alert("Start Date can’t be later than End Date");
  				   return ;
  			   }
  		   }      
  		
			$.ajax({
			type: "post",	
			url:"RecruitmentServlet",
			data: {"pageIndex":curPage,"method":"medialist","code":$("#code").val(),"name":$("#name").val(),"type":$("#type").val(),"date":$("#date").val(),"date1":$("#date1").val()},
			success:function(data){
			
			    var totals=0;
				var dataRole=eval(data);
				var html="";
				//移除记录
				$("tr[id='select']").remove();
				if(dataRole[0].length>0){
					curPage=dataRole[2];
	 			   	totalPage=dataRole[1];
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select' title='"+i+"' style='line-height:20px;'><td align='center'>"+
						dataRole[0][i].mediacode+"</td><td align='center'>"+
						dataRole[0][i].mediatype+"</td><td align='center'>"+
						dataRole[0][i].medianame+"</td><td align='center'>"+
						dataRole[0][i].price+"</td><td align='center'>"+
					    dataRole[0][i].quantity+"</td><td align='center'>"+
					    dataRole[0][i].unit+"</td><td align='center'>"+
					    dataRole[0][i].adddate+"</td><td align='center'>"+
					    dataRole[0][i].addname+"</td><td align='center'>"+
						dataRole[0][i].remark+"</td><td align='center'>";
							html+="</td></tr>";
					}
						$(".page_and_btn").show();
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' style='color:blue;size=5px' align='center'><b>"+"Sorry, there is no matching record."+"</b></td></tr>";
				   }	   
				     page(dataRole[2],dataRole[1]);	
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
		      }
		 });
	}
  function page(currt,total){
	 
		if(curPage<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(curPage>=totalPage){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
	}
 function fileupload(){
    	
             if($("#filePaths").val()==""){ 
                 alert("请选择有效文件"); 
                 return false;  
             }  
             $.ajaxFileUpload({  
                  url:'UpLoadFileServlet', 
                  secureuri:false,  
                  fileElementId:'filePaths',  
                  dataType: 'text/html',
                  success: function (data) {
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
				url:"UpExcelServlet",
				dateType:"html",
				data: {'uploadfile':fileName},
				success:function(date){
					if(date != "null"){
						//alert(date);
						var dataRole=eval(date);
						//alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
						alert("導入數據成功,此次操作共耗时"+dataRole[2] +"分鐘"+dataRole[1]+"條數據");		
						$("#shangchuan").attr("disabled",true);
					}else{
						alert("非系统所需文件！");
					}
				 },error:function(){
					 alert("网络连接失败，请稍后重试...");
					 return false;
				 } 
				});
           
           }  
  
  </script>
  <body style='overflow:auto;'><br>
  <div id="Medical">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="table-ss" background="css/officeAdmin_bg.gif">
            <tr>
              <td height="21" colspan="4" align="left"><div align="center" class="STYLE1"> Recruitment Maintain<br>
              </div></td>
            </tr>
              <tr>
              <td height="23" align="right">Start&nbsp;&nbsp;&nbsp; Date: </td>
              <td height="23" align="left"><input name="text" type="text" id="date" onClick="return Calendar('date')" />&nbsp;&nbsp;<img src="css/003_15.png" name="clear1" id="clear1"  align="middle"   onClick="javascript:$('#date').val('');"></td>
              <td height="23" align="right">End&nbsp;&nbsp;&nbsp; Date: </td>
              <td height="23" align="left"><input name="text" type="text" id="date1" onClick="return Calendar('date1')" />&nbsp;&nbsp;<img src="css/003_15.png" name="clear1" id="clear1"  align="middle"   onClick="javascript:$('#date1').val('');"></td>
              </tr>
            <tr>
              <td height="23" align="right">MediaCode&nbsp;：              </td>
              <td height="23" align="left"><input type="text" name="clientcode" id="code"></td>
              <td height="23" align="right">Media&nbsp;&nbsp;Name ：              </td>
              <td height="23" align="left"><input type="text" name="clientname" id="name"></td>
            </tr>
             <tr>
              <td height="23" align="right">MediaType ：  </td>
              <td height="23" align="left"><input type="text" name="ordercode" id="type"></td>
              <td colspan="2"><label style="background-color:green;" >有一些隔绝在人与人之间的东西，可以轻易地就在彼此间划开深深的沟壑，下过雨再变成河，就再也没有办法渡过。</label></td>    
                    
             </tr>
             
             
            <tr>
            <td></td>
              <td colspan="2" align="right"><input id="searchs" name="search" type="submit" value="Search" align="right"/> &nbsp;&nbsp;&nbsp;&nbsp;
              <td  height="23" align="left"><input type="file" name="filePaths" id="filePaths"/>&nbsp;&nbsp;&nbsp;  <input id="upload" name="upload" type="submit" value="upload" />             
            </tr>
            
                 
    </table>
    
      <table width="100%" border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      
      <tr id="title"  bgcolor='silver' style="line-height: 25px;">
        <td  align="center"><b><Strong>MediaCode</strong></b></td> 
        <td  align="center"><strong>Mediatype</strong><br></td> 
        <td  align="center"><strong>Medianame</strong><br></td>
        <td  align="center"><strong>Price</strong><br></td>
        <td  align="center"><strong>Quantity</strong><br></td>
        <td  align="center"><strong>Unit</strong><br></td>
        <td  align="center"><strong>AddDate</strong><br></td>
        <td  align="center"><strong>AddName</strong><br></td>
        <td  align="center"><strong>Remark</strong><br></td>
      </tr>
    </table>
    
    <div align="center" class="page_and_btn">

			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a><SPAN style="color: red;" id="curretPage">
						</SPAN> /
						<SPAN style="color: red;" id="totalPage">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
  </div>
  </body>
</html>
