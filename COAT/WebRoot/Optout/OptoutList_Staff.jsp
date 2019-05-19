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
			<link rel="stylesheet" type="text/css" href="css/style.css">
		 
<style type="text/css">
*{font-family: Arial Narrow;font-size: 14px;}
body{vertical-align: middle;background-image: url("css/officeAdmin_bg.gif");width:100%;margin:0px;padding:0px;}
 .daw {
    width: 100%;
    height: auto;
    left:0px;
  /**  margin: 0px auto;
    margin-top: -5px;
    margin-left:-8px;**/
    overflow: hidden;
   
}
.daw-z {
    width: 100%;
    height: auto;

      /** margin: 0px auto;
 background: url(UI/images/news-title-bg.gif) repeat-y;
    padding-top: 0px;**/
    overflow: hidden;
}
.editTable {
    margin-top: 0px;
    margin-bottom: 5px;

    font-size: 14px;
    border: #83accf 1px solid;
   
}
.editTable span.bigtitle{
    font-weight: bold;
    height: 25px;
}
.editTable td.title{
	height:25px;
   vertical-align: middle;
}

.editTable tr.title{
	height:25px;
   vertical-align: middle;
}
.editTable tr.title td{
text-align:center;
font-weight:bold;
font-size:14px;

}
.editTable tr{
line-height: 15px;
}

.editTable td.title{
text-align:right;
}
#stable tr{
line-height: 20px;
}
#stable{

}
#stable tr.field{
text-align:center;
}
#stable .title td{
text-align:center;
border:1px solid #000;
}
img{
height: 12px;
width: 12px;
cursor: pointer;
}
  .page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}

</style>


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
	//alert("The test page, only for testing!");
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
     <div class="daw">
          	 
           <div class="daw-z">
   
   
    <table width="100%" id="searchTable" align="center" class="editTable" border="0" cellspacing="0" cellpadding="0" style="border-bottom: 0px;">
	<tr style="vertical-align: top;" height="10"><td colspan="4" align="center"> <strong>Opt Out List Query</strong></td></tr>
	<tr><td class="title">ClientID:</td>
	<td class="field"><input type="text" style="width:150px;" id="clientId"/>
	<%--<img src="css/003_15.png" name="clear1" id="clear1"   align="middle"     onClick="javascript:$('#startDate').val('');">
	--%></td>
	<td class="title">Staff Code:</td>
	<td class="field">
	<input id="staffcode"  type="text"/>
	<%--
	<img src="css/003_15.png" name="clear1" id="clear1"   align="middle"     onClick="javascript:$('#endDate').val('');">
	--%></td></tr>
	
	

	
	<tr>
	<td  class="field"></td><td  class="field"></td><td  class="field"></td><td  class="field"><input type="button" id="search" value="Search"/>&nbsp;&nbsp;<input type="button" id="report" value="Report"/></td>
	</tr>
	
	</table>        
    <table width="70%"  id="stable" class="table-ss" border="1"  cellspacing="0" cellpadding="0" style="margin-top: -5px;border:1px solid #000;margin-left:0px;">
        
        <tr  class="title" style="background-color:silver;"><%--
            <td align="center" class="title" colspan="2">
                &nbsp;<span class="bigtitle">Return Mail ScanningRecord</span></td>
        --%>
         <td >Serial</td>
        <td >Opt out date</td>
        <td >Client ID</td>
        <td >Client Name</td>
        <td>Opt Out Channel</td>
      
      
        </tr><%--
     
        
       
 
       
      
        <tr>
            <td colspan="2">
                <div align="center" style="height:25px;">
                 
                </div>
            </td>
        </tr>
  --%></table>


   
 
       </div>
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
<%--<div id="test" class="pageinfo" ></div>
 
   --%></div> 
  </body>
</html>
