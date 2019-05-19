<%@page contentType="text/html;charset=utf-8"%>
<%@page import="dao.AdminDAO" %>
<%@page import="entity.Admin" %>

<%
/*如果还未登录系统*/

if (session.getAttribute("adminUsername")==null || session.getAttribute("adminUsername")=="")
	out.println("<script>alert('未登录系统！');top.location.href='signin.jsp';</script>");
else
{
	String adminUsername = session.getAttribute("adminUsername").toString();
	//int isRoot = AdminDAO.getIsRoot(adminUsername);
	//if(!adminUsername.equals("admin"))
		//out.print("<script>alert('你没有权限访问该页面!');location.href='desk.jsp';</script>");
}
%>
<html>
<head>
    <title>用户信息页面</title>
	<LINK href="style.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<style type="text/css">
</style>
	
	
<script language="javascript">
var downs = null;
var pagenow = 1;
var totalpage = 1;
var total = 0;
$(function(){
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
	$("#select").bind("click", function() {//尾页
			//search(1);	
		});
		selects(pagenow)
		
		
		$("select").live("change",function(){
			//alert(this.value+"---->"+$(this).parent().parent().children().eq(0).text());
			if(confirm("确认修改？")){
				$.ajax({
				type: "post",			
				url: "AdminServlet",
				data:{"adminUsername":$(this).parent().parent().children().eq(0).text(),"isRoot":this.value,"method":"edit"},
			    success:function(date){				
					//$("aaa0 > td").eq(0).html();
					//$("aaa0 > td").eq(1).children().val();		
					selects(pagenow);
				}					
			});	
			}	
			/**$("tr.aaa select").each(function(){			
				$(this).parent().empty().append($(this).find("option:selected").text());					
			});	*/
					
		});
	});

 

function selects(page){	
	page=pagenow;
	//$").hide();
	  	$.ajax({
        url:"AdminServlet",
        type:"POST",
        data:{"adminUsername":$("#adminUsername").val(),"pageNow":page,"method":"query"}, 
        beforeSend: function(){
        	parent.showLoad();
        },
        complete: function(){
        	parent.closeLoad();
        },
        success:function(date){
        	 	 var dataRole=eval(date);
		         var htmls="";
		         var html="";	         
		         $(".title").remove();
		         $("tr[class^='aaa']").remove();
		         if(dataRole[3]>0){
						total=dataRole[3];
						page =dataRole[2];
					    totalpage=dataRole[1];
					    down=dataRole[0];				       
			 for(var j=0;j<dataRole[0].length;j++){				
				 var shenfen="一般用户";
				 if(dataRole[0][j].isRoot == 1)
				    	   shenfen="管理员";
				       if(dataRole[0][j].isRoot == 4)
				    	   shenfen="Iris Team";
				       if(dataRole[0][j].isRoot == 5)
				    	   shenfen="Chloe Team";
				       if(dataRole[0][j].isRoot == 98)
				    	   shenfen="System UAT人员";
				       if(dataRole[0][j].isRoot == 99)
				    	   shenfen="COAT UAT人员";
				       if(dataRole[0][j].isRoot == 100)
				    	   shenfen="SZO UAT人员";
				       				      
						if(j%2==0){
		        		  htmls+="<tr class='aaa"+j+"'><td>"+dataRole[0][j].adminUsername+"</td><td id='"+dataRole[0][j].isRoot+"'>"+shenfen+"</td>"+"<td align='center'><a onclick='edit("+j+")'>"+"编辑"+"</a></td>"+"<td  align='center'><a onclick='del(\""+dataRole[0][j].adminUsername+"\")'>"+"删除"+"</a></td></tr>";  
		        	  }else{
		        		  htmls+="<tr class='aaa"+j+"' style='background-color:#ccc'><td>"+dataRole[0][j].adminUsername+"</td><td  id='"+dataRole[0][j].isRoot+"'>"+shenfen+"</td>"+"<td align='center'><a onclick='edit("+j+")'>"+"编辑"+"</a></td>"+"<td  align='center'><a onclick='del(\""+dataRole[0][j].adminUsername+"\")'>"+"删除"+"</a></td></tr>";
		        	  }		        		
				      }
			// alert($(".aaa td").first().html());
					  $(".page_and_btn").show();
					  $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
				}
					else{					
						html += "<tr class='aaa'><td colspan='10' style='color:blue;size=5px' align='center'><b>"
											+ "對不起，沒有您想要的數據!" + "</b></td></tr>"; 
						 $(".page_and_btn").hide();	
					}
						  $("#aaa").append(html); 
				          $("#aaa").append(htmls);
				          yemian(page,totalpage);
				      
						$("tr[class^='aaa']").height(24);
					}
  		 	});
		}
	
	/***********************************编辑*******************************************/
	function edit(i){
        
		//alert($(".aaa > td").eq(i*4).html());				 //得到的是adminUsername对象
		//alert($(".aaa > td").eq((i*4)+1).text());			 //得到的是isRooot对象 
		//var svalue=$(".aaa > td").eq((i*4)+1).attr("id");  //得到的是isRoot的id 
		var svalue=$(".aaa"+i+" >td").eq(1).attr("id");
		
		/**$(".aaa > td").eq((i*4)+1).replaceWith("<td><select ><option value='0'>一般用户</option><option value='1'>管理员</option>" +
		"<option value='4'>Iris Team</option><option value='5'>Chloe Team</option><option value='98'>System UAT人员</option>" +
		"<option value='99'>COAT UAT人员</option><option value='100'>SZO UAT人员</option></select></td>");
		**/
		
		$(".aaa"+i+" >td").eq(1).empty().append("<select ><option value='0'>一般用户</option><option value='1'>管理员</option>" +
		"<option value='4'>Iris Team</option><option value='5'>Chloe Team</option><option value='98'>System UAT人员</option>" +
		"<option value='99'>COAT UAT人员</option><option value='100'>SZO UAT人员</option></select>");				
		
		//alert($(".aaa select > option").text());
		//alert($(".aaa > td").eq((i*4)+1).children().text(svalue));
		//alert(svalue+"---"+i);		
		//$(".aaa > td").eq((i*4)+1).children().val(svalue);
		$(".aaa"+i+" > td").eq(1).children().val(svalue);

	
	}
	

	/****************************************保存用户信息********************************/			
								
		$(function(){
			$("#user").bind("click", function(){
		   	$("#insMessage").empty();
		});
			$("#password").bind("click",function(){
					$("#pwsMessage").empty();		   
			});
			$("#againPassword").bind("click",function(){
					$("#againMessage").empty();		   
			});	

		/****************************************添加按钮****************************/
		/*$("#add").click(function (){
			$("#pwsMessage").empty();
			$("#againMessage").empty()
			$("#insMessage").empty();
			$("#overs").show();
			$(".text").val("");
			
			$("select").die();

		});		*/
	});

	/***********************************删除******************/
	function del(n){	
		if(confirm("确定删除？")){  	
			  $.ajax({
				type: "post",			
				url: "AdminServlet",
				data:{"adminUsername":n,"method":"del"},
			    success:function(date){				
						//alert(date);													
						alert("删除成功！");
						selects();
						}					
			});				  
		 } 
	}

	function yemian(currt,total){
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
	function add()
	{
		mylhg(440,200,'UserManage_info.jsp',null,'新增');
	}

</script>
</head>

<body class="body">
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">用户名: </td>
				<td class="tagCont">
					<input id="adminUsername" type="text" name="name"  size="25" />
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					<a class="btn" onclick="selects()">
						<i class="icon-search"></i>
						查询
					</a>
					<a class="btn" onclick="add()">
						<i class="icon-plus"></i>
						添加
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="aaa">
			<thead id="title">
			<tr>
				<th class="width_35">用户名</th>
				<th class="width_35">身份</th>
				<th class="width_15">编辑</th>
				<th class="width_15">删除</th>
			</tr>
			</thead>
		</table>
		<div class="page_and_btn">
			<table>
		        <tr class="page_and_btn">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN style="color:red;" id="total">
						</SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="end" href="javascript:void(0);">尾页</a>
					</td>
				</tr>  
			</table>
		</div>
	</div>
</div>
<div id="login" style="top: 143px; left: 40%; width: 250px; height: 150px; display: none;">
   <p><img src="css/022.gif" width="30" height="30"></p>
   <p><span><span class="STYLE3">正在處理數據，請稍候...</span></span></p>
</div>
</body>
</html>
