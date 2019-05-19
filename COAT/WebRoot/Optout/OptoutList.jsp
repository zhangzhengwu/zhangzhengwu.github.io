<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ScanningRecord</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="css/jquery-pager-1.0.js"></script>
	<link rel="stylesheet" type="text/css" href="css/pager.css">
		<script src="css/date.js" language="JavaScript"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">

<script type="text/javascript">
var pageindex=1;
     var pagenow =1;
  var totalpage=1;
  var total=0;

$(function(){

	$("#search").click(function(){
		pagenow = 1;
		select_pager(1);
	});
	$("#report").click(function(){
		location.href="http://szosvr11:8888/ConvoyUtil/ExpOptoutListServlet?staffcode="+$("#staffcode").val()+"&clientId="+$("#clientId").val();
	});
	
			var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			$("#staffcode").val(econvoy[0]);
		
	          
              		//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select_pager(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select_pager(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				select_pager(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select_pager(pagenow);
			});
});

function select_pager(pagenow){
		$.ajax({
			url:"http://szosvr11:8888/ConvoyUtil/OptoutListServlet",
			type:"post",
			data:{"staffcode":$("#staffcode").val(),"clientId":$("#clientId").val(),"pagenow":pagenow},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(date){
				var html="";
				$("#stable tr.field").remove();
				if(date=="error"){
					
				}else if(date==null){
					
				}else{
					var dataRole=eval(date);
					$(".page_and_btn").hide();
					if(dataRole[2]>0){
						pagenow =dataRole[1];
				    	totalpage=dataRole[3];
						for(var i=0;i<dataRole[0].length;i++){
							html+="<tr class='field'>" +
							"<td>"+((pagenow-1)*15+i+1)+"</td>" +
							"<td>"+dataRole[0][i].optdate+"</td>" +
							"<td>"+dataRole[0][i].clientId+"</td>" +
							"<td>"+dataRole[0][i].clientName+"</td>" +
							"<td>"+dataRole[0][i].requestType+"</td>" +
							
							//"<td>"+dataRole[0][i].staffname+"</td>" +
							//"<td>"+dataRole[0][i].chickenboxno+"</td>" +
							//	"<td>"+dataRole[0][i].location+"</td>" +
							//"<td>"+dataRole[0][i].scanningName+"</td>" +
							//"<td>"+dataRole[0][i].scanningDate+"</td>" +
							
							"</tr>";
						}
						$(".page_and_btn").show();
					}else{
						html+="<tr class='field'><td colspan='7' style='color:blue;size=14px' align='center'><b>"+" Sorry, there is no matching record."+"</b></td></tr>";
					}
				}
				//InitPager(dataRole[2],dataRole[1]);
				page(dataRole[1],dataRole[3]);
				  $("#total").empty().append(dataRole[1]+"/"+dataRole[3]);
				$("#stable").append(html);
				$("#stable tr.field:odd").css("backgroundColor","#F0F0F0");
				$("#stable tr.field:even").css("backgroundColor","#COCOCO");
			},error:function(){
				alert("Network Connection Error!");
			}
		});
}

   function InitPager(RecordCount, PageIndex) {
            $("#test").setPager({ RecordCount: RecordCount, PageIndex: PageIndex, buttonClick: PageClick });
            //$("#result").html("您点击的是第" + PageIndex + "页");
            pageindex=PageIndex;
        };
        PageClick = function(RecordCount, PageIndex) {
            //InitPager(RecordCount, PageIndex);
       	select_pager(PageIndex);
        };
        
        
        
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
				<td class="tagName">ClientID:</td>
				<td class="tagCont">
					<input type="text" style="width:150px;" id="clientId"/>
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<input id="staffcode"  type="hidden"/>
					<a class="btn" id="search">
						<i class="icon-search"></i>
						Search
					</a>
					<a class="btn" id="report">
						Report
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="stable">
			<thead>
			<tr>
				<th class="width_">Serial</th>
				<th class="width_">Opt out date</th>
				<th class="width_">Client ID</th>
				<th class="width_">Client Name</th>
				<th class="width_">Opt Out Channel</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">First Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a> Total
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
