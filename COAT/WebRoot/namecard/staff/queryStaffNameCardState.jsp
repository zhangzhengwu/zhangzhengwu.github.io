<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Access Card Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	
  </head>
  <script type="text/javascript" >
  var pagenow=1;
  var totalpage=0;
  var basepath = '<%=basePath%>';
  $(function(){
	//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				search_access(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				search_access(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				search_access(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				search_access(pagenow);
			});
  });
  
  function search_access(pageNow){
	  if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
	   $.ajax({
    		  url:"StaffNameCardReaderServlet",
    		  type:"post",
    		  data:{"method":"selectState","staffcode":$("#staffcode").val(),"staffrefno":$("#staffrefno").val(),"pagenow":pageNow},
	    	  beforeSend: function(){
	    	  	  parent.showLoad();
	    	  },
	    	  complete: function(){
	    		  parent.closeLoad();
	    	  },
    		  success:function(date){
    			  var dataRole=eval(date);
    			  var html="";
    			  pagenow=dataRole[2];
    			  totalpage=dataRole[1];
    			  $(".tr_search").remove();
    			  var shzt="";
    			  if(dataRole[3]>0){
    				  for(var i=0;i<dataRole[0].length;i++){
    					if(dataRole[0][i].state=="S")
							shzt="Pending";
						else if(dataRole[0][i].state=="E")//等待Department Head 审核
							shzt="Dept Approved";
						else if(dataRole[0][i].state=="R")//等待HR 审核
							shzt="HR Approved";
						else if(dataRole[0][i].state=="Y")
							shzt="Approved";
						else if(dataRole[0][i].state=="N")
							shzt="Rejected"
    				  	html+="<tr class='tr_search'><td>"+((pagenow-1)*15+(i+1))
    				  		+"</td><td>"+dataRole[0][i].username
    				  		+"</td><td>"+dataRole[0][i].staffrefno
    				  		+"</td><td>"+dataRole[0][i].staffcode
    				  		+"</td><td>"+dataRole[0][i].staffnameE
    				  		+"</td><td>"+dataRole[0][i].staffnameC
    				  		+"</td><td>"+shzt //-->申请状态
    				  		+"</td><td>"+dataRole[0][i].createdate
    				  		+"</td><td>"+dataRole[0][i].remark
    				  		html+="</td></tr>";
    				  }
    				  $(".page_and_btn").show();
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='9' align='center' >"+"Sorry, there is no matching record."+"</td></tr>";
    				  $(".page_and_btn").hide();
    				  $("#export").attr("disabled",true);
    			  }
    			  $("#jqajax").append(html);
    			  page(pagenow,totalpage);
    		  },error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
    	  });
  }
  
    /**
       * 分页活动方法
       * @param {Object} currt
       * @param {Object} total
       */
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
				$("#curretPage").empty().append(currt);
				$("#totalPage").empty().append(total);
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
				complete: function(){
				 	$.dialog.list['menu_upload'].close();
				},
				success:function(date){
					if(date != "null"){
						var dataRole=eval(date);
						alert(dataRole[4]+" 共耗时:"+dataRole[3] +"分鐘");	
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
 	
 	$('#upload_btn').live('click',function(){
 		var data = 'uploadConsultant';
 		var url = basepath + "publicUpload.jsp";
		uploadlhg(440,300,url,data);
 	});
  </script>
<body>
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">StaffRefno：</td>
				<td class="tagCont">
					<input type="text" name="staffrefno" id="staffrefno">
				</td>
				<td class="tagName">StaffCode：</td>
				<td class="tagCont">
					<input type="text" name="staffcode" id="staffcode">
				</td>
			</tr>
			<tr>
				<td class="tagCont" style="padding-left: 10%;" colspan="2">
				</td>
				<td class="tagCont"  colspan="2">
					<c:if test="${roleObj.upd==1}">
						<a class="btn" id="upload_btn"><i class="icon-upload-alt"></i>Upload File</a>
					</c:if>
					<c:if test="${roleObj.search==1}">
						<a class="btn" name="search" onclick="search_access(1)"><i class="icon-search"></i>Search</a>
					</c:if>
				</td>
				
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_10">Num</th>
				<th class="width_10">User Name</th>
				<th class="width_10">Staff Refno</th>
				<th class="width_10">Staff Code</th>
				<th class="width_10">Staff NameE</th>
				<th class="width_10">Staff NameC</th>
				<th class="width_10">Status</th>
				<th class="width_10">Create Date</th>
				<th class="width_15">remark</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a><SPAN style="color: red;" id="curretPage">
						0</SPAN> /
						<SPAN style="color: red;" id="totalPage">0
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>
