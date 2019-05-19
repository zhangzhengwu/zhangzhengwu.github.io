<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Return Mail Report</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
    
  </head>
  
<body >
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')"  value=""/>
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text"  onClick="return Calendar('end_date')" />
                	<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
				<c:if test='${roleObj.search==1}'>
					<button class="btn" name="search" disabled="disabled" onclick="javascript:alert('该功能暂未开放');">
						<i class="icon-search"></i>Search
					</button>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<button class="btn" id="export" onclick="down_access('data');">
						<i class="icon-download"></i>Export Data
					</button>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<button class="btn" id="export" onclick="down_access('report');">
						<i class="icon-download"></i>Export Report
					</button>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_">Num</th>
				<th class="width_">Staff Code</th>
				<th class="width_">Staff Code2</th>
				<th class="width_">ReceiveDate</th>
				<th class="width_">Company</th>
				<th class="width_">Product Name</th>
				<th class="width_">ClientHKID</th>
				<th class="width_">ClientName</th>
				<th class="width_">ClientOtherName</th>
				<th class="width_">CommencementDate</th>
				<th class="width_">Currency</th>
				<th class="width_">Amount</th>
				<th class="width_">Term</th>
				<th class="width_">ClientId</th>
				<th class="width_">Pfid</th>
				<th class="width_">PolicyNo</th>
				<th class="width_">Remark2</th>
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

   <script type="text/javascript" >
  
  
  
  
  
  
  
  
  var pagenow=1;
  var totalpage=0;
  var email=null;
  $(function(){
	  $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
	  $(".inputstyle,.field input").attr("disabled",true);
	  
	  
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
	
	
	
	$(".paymentAmount").click(function(){
		if($(".paymentAmount:checked").length>0){
			$(".paymentAmount").attr("checked",false);
			this.checked=true;
		}else{
			this.checked=true;
		}
		$("#amount").val(this.value);
		
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
    		 // url:"AccessCardServlet",
    		 url:"",
    		  type:"post",
    		  data:{"method":"select","startDate":$("#start_date").val(),
	    		  "endDate":$("#end_date").val(),"staffcode":$("#staffcode").val(),
	    		  "staffname":$("#staffname").val(),"refno":$("#refno").val(),
	    		  "status":$("#status").val(),"curretPage":pageNow},
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
    			  if(dataRole[3]>0){
    				  for(var i=0;i<dataRole[0].length;i++){
    				  	html+="<tr class='tr_search'><td>"+((pagenow-1)*15+(i+1))
    				  		+"</td><td>"+dataRole[0][i].refno
    				  		+"</td><td>"+dataRole[0][i].staffcode
    				  		+"</td><td>"+dataRole[0][i].staffname
    				  		+"</td><td>"+dataRole[0][i].creator
    				  		+"</td><td>"+dataRole[0][i].createDate
    				  		+"</td><td>"+dataRole[0][i].reason
    				  		+"</td><td>"+dataRole[0][i].status
    				  		+"</td><td><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].refno+"\");'>Detail</a>&nbsp;&nbsp;</td></tr>";
    				  		//<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a></td></tr>";
    				  }
    				  $(".page_and_btn").show();
    				  $("#export").attr("disabled",false);
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='9' align='center' ><b style='color:blue;size=5px'>"+"Sorry, there is no matching record."+"</b></td></tr>";
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
        
       function down_access(type){
    	   if($("#start_date").val()==""){
    		   alert("开始日期不能为空!");
    		   return false;
    	   }else if($("#end_date").val()==""){
    		   alert("结束日期不能为空!");
    		   return false;
    	   }else{
    		   location.href="<%=basePath%>"+"../ConvoyUtil/ServiceLogReportServlet?method="+type+"&receiveDate="+$("#start_date").val()
    		                +"&receiveDate2="+$("#end_date").val();
    	   }
       }
       /**
       function down_access(type){
    	   if($("#start_date").val()==""){
    		   alert("开始日期不能为空!");
    		   return false;
    	   }else if($("#end_date").val()==""){
    		   alert("结束日期不能为空!");
    		   return false;
    	   }else{
    		   
    		   location="/ConvoyUtil/ServiceLogReportServlet?method=report&receiveDate="+$("#start_date").val()
    		                +"&receiveDate2="+$("#end_date").val();
    	   }
       }**/

  </script>
  </body>
</html>
