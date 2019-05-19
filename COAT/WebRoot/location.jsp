<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>    
    <title>This is location page!</title> 
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<style type="text/css">
#Layer2 {
	position:absolute;
	width:388px;
	height:242px;
	z-index:5;
	left: 750px;
	top: 220px;
	
}	
#login {
	position:absolute;
	width:165px;
	height:71px;
	z-index:100;
	left: 525px;
	top: 129px;
	border-color:#99CCCC;
display:none;
	}

#overs {
	display:none;
}

.STYLE1 {font-size: 10px}
.STYLE3 {font-size: 16px;
font-family:"仿宋"}
    



</style>
  </head>
   <script type="text/javascript">
  	var method=null;
  	var down=null;
	var pagenow =1;
	var totalpage=1;
	var total=0;
	
	
	
   function select(){     
	if($("#name_search").val()==null){
		//alert("==1111111111==");
      /******************全查*****************************/	
        //$("#overs").hide();
      	load();
     	$.ajax({
        url:"EntryLocationServlet",
        type:"POST",
        data:{"name":name,"method":query},
        beforeSend: function(){
        	parent.showLoad();
        },
        complete: function(){
        	parent.closeLoad();
        },
        success:function(date){
        //alert(date);	        	   
          var dataRole=eval(date);   //把数据转换成JSON
          //alert(dataRole[0].locationInfo);
          var htmls="";
          $(".aaa").remove();	//先把数据清空再查询
          //遍历          
          for(var i=0;i<dataRole.length;i++){
          	htmls+="<tr class='aaa'><td>"+dataRole[i].name+"</td><td>"+dataRole[i].locationInfo+"</td><td>"+dataRole[i].remark+"</td><td  align='center'><input type='button' value='删除'  onclick='del("+dataRole[i].locationId+")'>"+"</></td></tr>";
          }
          $("#aaa").after(htmls);
          $(".txt").val("");
     	  }
  		 });   
		}else{
			search2();	
		} 
} 
    
  
  $(function(){
  //注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				search2(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				search2(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				search2(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				search2(pagenow);
			});
	$("#select").bind("click", function() {//尾页
			//search(1);	
		});
	});

    
  function search2(){ 
    	/******************根据条件查询*****************************/	
     	$.ajax({
        url:"EntryLocationServlet",
        type:"POST",
        data:{"name":$("#name_search").val(),"pageNow":pagenow,"method":"query"},
        beforeSend: function(){
        	parent.showLoad();
        },
        complete: function(){
        	parent.closeLoad();
        },
        success:function(date){       	
		  //alert(date);	        	   
          var dataRole=eval(date);   
          var htmls="";
          $(".aaa").remove();	
          //$("#down").removeAttr("disabled");
         
         if(dataRole[3]>0){
				total=dataRole[3];
				pagenow =dataRole[2];
			    totalpage=dataRole[1];
			    down=dataRole[0];
				    
          for(var j=0;j<dataRole[0].length;j++){
        	  if(j%2==0){
        		  htmls+="<tr class='aaa'><td>"+dataRole[0][j].name+"</td><td>"+dataRole[0][j].locationInfo+"</td><td>"+dataRole[0][j].remark+"</td><td  align='center'><a onclick='del("+dataRole[0][j].locationId+")'>"+"删除"+"</a></td></tr>"; 
        	  }else{
        		  htmls+="<tr class='aaa' style='background-color:#ccc'><td >"+dataRole[0][j].name+"</td><td>"+dataRole[0][j].locationInfo+"</td><td >"+dataRole[0][j].remark+"</td><td  align='center'><a onclick='del("+dataRole[0][j].locationId+")'>"+"删除"+"</a></td></tr>";
        	  }
          	
          }   
 
		  $(".page_and_btn").show();
		  $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
			}
			else{
				//alert(" 对不起，没有你想要的数据！"); 
				htmls+="<tr class='aaa'><td colspan='10' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
				 $(".page_and_btn").hide();	
			}
		          $("#jqajax").append(htmls);
		          page(pagenow,totalpage);
     	  },error:function(){
     		  alert("Error");
     	  }
  		 });
     }
			
	/******************保存用户信息*****************************/			
	/**function save(){
		if($("#name").val()!=""){			
		$.ajax({
			type: "post",			
			url: "EntryLocationServlet",
			data:{'name':$("#name").val(),'locationInfo':$("#locationInfo").val(),'remark':$("#remark").val(),"method":"save"},
			success:function(date){
				   if(date!=null){
					   alert(date);
					   select();
				   }
				}			
			});		
		
		}else{ 
			$("#insMessage").empty().append("Name不能为空！").css('font-size','12px');
		}									
	}**/
	
	
	$(function(){
		/***********************************添加按钮******************/
		$("#add").click(function (){
			addlhg(440,200,'location_info.jsp','');
		});
	});
	
	

	/***********************************删除******************/
	function del(n){
	if(confirm("确定删除？")){  	
		  $.ajax({
			type: "post",			
			url: "EntryLocationServlet",
			data:{"locationId":n,"method":"del"},
		    success:function(date){				
					//alert(date);	
					select();
					$.dialog.alert("删除成功！");
					
			}					
		});				  
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
  
<body style='overflow:auto;'>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Location Name:</td>
				<td class="tagCont">
					<input id="name_search" type="text" name="name"  size="25" />
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					<a class="btn" onclick="select()">
						<i class="icon-search"></i>
						Search
					</a>
					<a class="btn" id="add">
						<i class="icon-plus"></i>
						Add
					</a>
				</td>
			</tr>
		</table>		
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="aaa">
			<tr>
				<th class="width_40">Name</th>
				<th class="width_20">LocationInfo</th>
				<th class="width_30">Remark</th>
				<th class="width_10">Delete</th>
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
<script type="text/javascript"></script>
</body>
</html>