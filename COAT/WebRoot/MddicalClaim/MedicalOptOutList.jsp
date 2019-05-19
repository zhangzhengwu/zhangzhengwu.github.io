<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<script type="text/javascript">
	var basepath = "<%=basePath%>";
 	 var pagenow =1;
	 var totalpage=1;
	 var total=0;
  	 var num1;
  	 var num2;
  	 var num3;
  	 var num4;
  	 var bv1;
  	 var bv2;
  	 
  	$(function(){
   	
  			select(1);
  	
	  		$("#search").click(function(){
			select(1);	
			});
  	
  	
  			$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select(pagenow);
			});
			$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select(pagenow);
			});
			$("#first").bind("click", function() {//首页
				pagenow = 1;
				select(pagenow);
			});
			$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select(pagenow);
			});
  	

			/* Add start */
			$("#add").click(function (){ 
				var url = basepath + "MddicalClaim/MedicalOptOutList_info.jsp";
				addlhg(900,500,url);
			});
			/* Add end */
  	
  	});


  	
  	function select(pagenow){
  		$.ajax({
  			url:basepath+"MedicalServlet",
  			type:"post",
            beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
  			data:{"method":"select",
  				  "startDate":$("#start_date").val(),
  				  "endDate":$("#end_date").val(),
  				  "staffcode":$("#staffcode").val(),
  				  'pagenow':pagenow},
  			success:function(date){
	  			var dataRole = eval(date);
	  			var html="";
	  			$(".tr_search").remove();
	  			if(dataRole[3]>0){
		  			total=dataRole[3];
		  			pagenow = dataRole[2];
		  			totalpage = dataRole[1];
	  				for ( var i = 0; i < dataRole[0].length; i++) {
		  				html+="<tr class='tr_search'><td>"+(((pagenow-1)*15)+(i+1))
		  					 +"</td><td>"+dataRole[0][i].staffcode
		  					 +"</td><td>"+dataRole[0][i].staffname
		  					 +"</td><td>"+dataRole[0][i].createdate
		  					 +"</td><td>"+dataRole[0][i].remark
		  					 +"</td><td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='del(\""+dataRole[0][i].staffcode+"\");'>Delete</a></c:if>&nbsp;</td></tr>";
					}
	  				$(".page_and_btn").show();
	  				$("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
	  			}else{
	  				html+="<tr class='tr_search'><td colspan='9' align='center'>Sorry, there is no matching record.</td></tr>";
    				$(".page_and_btn").hide();
	  			}
	  			$("#seatLists").append(html);
	  			page(pagenow,totalpage);
  			},error:function(){
  			 	alert("网络连接异常!");
  				return false;
  			}
  			
  		});
  	}
  	
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

	}  	
  
  
      function  exportdata(){
      var startDate=$("#start_date").val();
      var endDate=$("#end_date").val();
      var staffcode=$("#staffcode").val();
      var exportExecl="exportExecl";
      location.href="<%=basePath%>"+"MedicalServlet?method="+exportExecl+"&staffcode="+staffcode+"&startDate="+startDate+"&endDate="+endDate;
    }	
 

	  function del(i){
	  if(confirm('确认删除当前记录? ')){  
			$.ajax({
				type:"post",
				url: basepath+"MedicalServlet",
                beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				data:{"method":"del","staffcode":i},
				dataType:'json',
				success: function(result){
    				if("success" == result.state){
    					success(result.msg);
    					select(1);
    				}else{
    					error(result.msg);
    				}
	    		 },error:function(result){
	    			 error("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
			});
			return false;
			}
	  	      
	  	      
	  }

  	
		$('#upload_btn').live('click',function(){
	 		var data = 'uploadMedicalOutPutList';
	 		var url = basepath + "publicUpload.jsp";
			uploadlhg(440,300,url,data);
	 	});
	 	
		$('#model_btn').live('click',function(){
			location.href=basepath + "TemplateResours/MedicalOutPutList_xxx.xls";;
	 	});	 	
	 	
	 	function uploadMedicalOutPutList(fileName){
           	if(fileName==""){
				alert("請選擇需要上傳的文件！");
				return;
			}
			 $.ajax({
				type: "post",
				url:"UpExcelServlet",
				dateType:"html",
				complete: function(){
				  $.dialog.list['menu_upload'].close();
				},
				data: {'uploadfile':fileName},
				success:function(date){
					if(date != "null"){
						//alert(date);
						var dataRole=eval(date);
						alert("導入數據成功!本次操作新增"+dataRole[1] +"条數據");		
						$("#shangchuan").attr("disabled",true);
						$("#search").click();
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
  </head>
	  
  <body>
<div class="cont-info">
  <div class="info-search">
	<table>
		<tr>
			<td class="tagName">Opt Out Date From：</td>
			<td class="tagCont">
				<input id="start_date" type="text"    onClick="Calendar('start_date')" />&nbsp;&nbsp;
               	<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
			</td>
			<td class="tagName">Opt Out Date To：</td>
			<td class="tagCont">
				<input id="end_date" type="text" name="it"    onClick="Calendar('end_date')" />&nbsp;&nbsp;
                	<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
			</td>
		</tr>	
		<tr>
			<td class="tagName">Staff Code:</td>
			<td class="tagCont">
				<input type="text" name="staffcode" id="staffcode"/>
			</td>
			<td class="tagCont" colspan="2">
				<c:if test="${roleObj.search==1}">
				<a class="btn" id="search" >
					<i class="icon-search"></i>
					Search
				</a>
				</c:if>
				<c:if test="${roleObj.export==1}">
					<a class="btn" id="exp" onclick="exportdata()">
						<i class="icon-download"></i>
						Export
					</a>
				</c:if>					
				<c:if test="${roleObj.add==1}">
 					<a class="btn" id="add">
 						<i class="icon-plus"></i>
 						Add
 					</a>
 					</c:if>
				<c:if test="${roleObj.upd==1}">
					<a class="btn" id="model_btn">
						<i class="icon-download"></i>
						Template 
					</a>
				</c:if>
				<c:if test="${roleObj.upd==1}">
					<a class="btn" id="upload_btn">
						<i class="icon-upload-alt"></i>
						Import
					</a>
				</c:if>		
			</td>					
		</tr>
     </table>
  </div>
  <div class="info-table">
  	<table id="seatLists">
  		<thead>
		<tr>
			<th class="width_5">Num</th>
			<th class="width_10">Staff Code</th>
			<th class="width_10">Staff Name</th>
			<th class="width_10">Create Date</th>
			<th class="width_55">Remark</th>
			<th class="width_10">Handle</th>
		</tr>
		</thead>  	
  	</table>
 		<div align="center" class="page_and_btn">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a>Total
						<SPAN style="color: red;" id="total">
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
