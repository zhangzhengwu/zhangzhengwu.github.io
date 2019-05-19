<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/plug/common.inc" %>
	
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
 	<script type="text/javascript">
   		var downs=null;
 	    var pagenow =1;
		var totalpage=1;
		var total=0;
  /**********************************窗體加載************************************************/ 
 $(function(){
	 	
		$("#searchs").click(function(){
		       selects(1);	
		});

		//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				selects(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				selects(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				selects(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				selects(pagenow);
			});
	});
function selects(pagenows){
  //alert("ajax"+pagenows);
				$.ajax({
				type: "post",
				url:"QueryStaff_list",
				data: {'staffcode':$("#staffcodes").val(),'companyName':$("#name").val(),
					'deptid':$("#deptid").val(),'staffname':$("#staffName").val(),'pageNow':pagenows,'method':"select"},
				beforeSend: function(){
						parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(data){
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
					   // alert(pagenows);
						downs=dataRole[0];
						for(var i=0;i<dataRole[0].length;i++){
							html+="<tr id='select' title='"+i+"'><td align='center'>"+((i+1)+(dataRole[2]-1)*dataRole[4])+"</td><td align='center'>"
							+dataRole[0][i].staffcode +"</td><td align='center'>"+dataRole[0][i].company +"</td><td align='center'>"
							+dataRole[0][i].deptid +"</td><td style='text-align:left;'>"+dataRole[0][i].staffname +"</td><td align='center'>"
							+dataRole[0][i].englishname +"</td><td align='center'>"+dataRole[0][i].grade +"</td><td style='text-align:left;'>"+dataRole[0][i].email  
							+"</td><td align='center'>"+(dataRole[0][i].enrollmentDate==""?"":dataRole[0][i].enrollmentDate.substring(0,10)) 
							+"</td><td align='center'>"+dataRole[0][i].terminationDate +"</td>";		 
						}
			 				$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='19' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
					 $(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 	 page(pagenow,totalpage);
				 }
			 });return false;
				
		}
   
	/*****************************************************Down click**********************************/
	
function exportDate(){
		if(downs!=null){			
			var staffcode=$("#staffcodes").val();
			var companyName=$("#name").val();
			var deptid=$("#deptid").val();
			var staffname=$("#staffName").val();
		    var method="export";
	        location.href="QueryStaff_list?staffcode="+staffcode+"&companyName="+companyName+"&deptid="+deptid+"&staffname="+staffname+"&method="+method;
        }else{
	        alert("请先查询数据，再做导出相关操作！");
	        }	
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
 </script>
  
</head>
<body  >
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="staffcodes" id="staffcodes">
				</td>
				<td class="tagName">Company Name：</td>
				<td class="tagCont">
					<input type="text" name="names" id="name">
				</td>
			</tr>
			<tr>
				<td class="tagName">Deptment ID：</td>
				<td class="tagCont">
					<input type="text" name="staffcodes" id="deptid">
				</td>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input type="text" name="names" id="staffName">
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" style="padding-left: 30px;" colspan="2">
				<c:if test="${roleObj.search==1}">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test="${roleObj.export==1}">
					<a class="btn" id="down" name="downs" onclick="exportDate()">
						<i class="icon-download"></i>
						Export
					</a>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th class="width_5">Id</th>
				<th class="width_10">Staff Code</th>
				<th class="width_5">company</th>
				<th class="width_8">deptment ID</th>
				<th class="width_20">Staff Name</th>
				<th class="width_8">English Name</th>
				<th class="width_5">Grade</th>
				<th class="width_20">Email</th>
				<th class="width_10">Enrollment Date</th>
				<th class="width_10">Termination Date</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN style="color: red;" id="total">
						</SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="end" href="javascript:void(0);">尾页</a>
					</td>
				</tr>
			</table>
		</div>	
	</div>
</div>
</body>
</html>