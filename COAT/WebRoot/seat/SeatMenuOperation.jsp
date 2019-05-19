<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<script type="text/javascript">
	 var basepath = '<%=basePath%>';
 	 var pagenow =1;
	 var totalpage=1;
	 var total=0;
	 var down = null;
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
  	
  	
  	});
  	
  	function select(pagenow){
  		$.ajax({
  			url:basepath+"SeatServlet",
  			type:"post",
            beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
  			data:{"method":"selectCleanOperation",
  			      "startdate":$("#startdate").val(),
  				  "enddate":$("#enddate").val(),
  				  "staffcode":$("#staffcode").val(),
  				  "seatno":$("#seatno").val(),
  				  'pagenow':pagenow},
  			success:function(date){
	  			var dataRole = eval(date);
	  			var html="";
	  			$(".tr_search").remove();
	  			down = null;
	  			if(dataRole[3]>0){
	  				down = dataRole;
		  			total=dataRole[3];
		  			pagenow = dataRole[2];
		  			totalpage = dataRole[1];
	  				for ( var i = 0; i < dataRole[0].length; i++) {
		  				html+="<tr class='tr_search'><td>"+(((pagenow-1)*15)+(i+1))
		  					 +"</td><td>"+dataRole[0][i].seatno
		  					 +"</td><td>"+dataRole[0][i].staffcode
		  					 +"</td><td>"+dataRole[0][i].staffname
		  					 +"</td><td>"+dataRole[0][i].location
		  					 +"</td><td>"+dataRole[0][i].extensionno
		  					 +"</td><td>"+dataRole[0][i].floor
		  					 +"</td><td>"+dataRole[0][i].lockerno
		  					 +"</td><td>"+dataRole[0][i].deskDrawerno
		  					 +"</td><td>"+dataRole[0][i].pigenboxno
		  					 +"</td><td>"+(dataRole[0][i].ifhidden=="Y"?"YES":"NO")
		  					 +"</td><td>"+dataRole[0][i].ifad
		  					 +"</td><td>"+dataRole[0][i].operationname
		  					 +"</td><td>"+dataRole[0][i].operationdate
		  					 +"</td><td>"+dataRole[0][i].reason
		  					 +"</td></tr>";
					}
					
	  				$(".page_and_btn").show();
	  				$("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
	  			}else{
	  				html+="<tr class='tr_search'><td colspan='9' align='center'>Sorry, there is no matching record.</td></tr>";
    				$(".page_and_btn").hide();
	  			}
	  			$("#seatchangeapplylists").append(html);
	  			page(pagenow,totalpage);
  			},error:function(){
  			 	alert("please select at least one Request option!");
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
  	
  	</script>
  </head>
	  
  <body>
<div class="cont-info">
  <div class="info-search">
	<table>
		<tr>
			<td class="tagName">Start Date:</td>
			<td class="tagCont">
				<input type="text" id="startdate" name="startdate" onClick="return Calendar('startdate')"/>
				<i class="icon-trash icon-large i-trash"  align="middle" onclick="javascript:$('#startdate').val('');"></i>
			</td>
			<td class="tagName">End Date:</td>
			<td class="tagCont">
				<input type="text" id="enddate" name="enddate" onClick="return Calendar('enddate')"/>
				<i class="icon-trash icon-large i-trash"  align="middle" onclick="javascript:$('#enddate').val('');"></i>
			</td>
		</tr>
		<tr>
			<td class="tagName">Staff Code:</td>
			<td class="tagCont">
				<input type="text" name="staffcode" id="staffcode"/>
			</td>
			<td class="tagName">Seat No:</td>
			<td class="tagCont">
				<input type="text" name="seatno" id="seatno"/>
			</td>					
		</tr>
		<tr>
			<td class="tagName"></td>
			<td class="tagCont"></td>
			<td class="tagCont" colspan="2">
					<a class="btn" id="search">
						<i class="icon-search"></i>
						Search
					</a>
			</td>
		</tr>
     </table>
  </div>
  <div class="info-table">
  	<table id="seatchangeapplylists">
  		<thead>
		<tr>
			<th class="width_5">num</th>
			<th class="width_5">Seat No</th>
			<th class="width_5">Staff Code</th>
			<th class="width_5">Name</th>
			<th class="width_5">Location</th>
			<th class="width_5">Ext.#</th>
			<th class="width_5">Floor</th>
			<th class="width_5">Locker No</th>
			<th class="width_5">Desk Drawer No</th>
			<th class="width_5">Pigen Box No</th>
			<th class="width_5">IF Hidden</th>
			<th class="width_5">AD or DD</th>
			<th class="width_5">Operation Name</th>
			<th class="width_5">Operation Date</th>
			<th class="width_5">Reason</th>
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
