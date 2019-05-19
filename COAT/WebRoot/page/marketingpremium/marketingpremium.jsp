<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'marketingpremium.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body>
  <div class="cont-info">
  	<div class="info-search">
  		<table>
   			<tr>
   				<td class="tagName">产品编号：</td>
   				<td class="tagCont">
   					<input type="text" name="procode" id="procode" />
   				</td>
   				<td class="tagName">可订购对象：</td>
   				<td class="tagCont">
   					<select id="blbz">
   						<option value=""> 请选择</option>
						<option value="A">Consultant && Staff</option>
						<option value="C">Consultant</option>
						<option value="S">Staff</option>
						<option value="N">Hidden</option>
					</select>
   				</td>
   			</tr>
   			<tr>
   				<td class="tagName">产品英文名称：</td>
   				<td class="tagCont">
   					<input type="text" name="englishname" id="englishname" />
   				</td>
   				<td class="tagName">产品中文名称：</td>
   				<td class="tagCont">
   					<input type="text" name="chinesename" id="chinesename" />
   				</td>
   			</tr>
   			<tr>
   				<td class="" style="text-align: center;" colspan="4">
   					<c:if test="${roleObj.search==1}">
						<a class="btn" id="searchs" name="search">
							<i class="icon-search"></i>
							搜索
						</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="down" name="downs" onclick="exportDate()">
							<i class="icon-download"></i>
							导出
						</a>
					</c:if>
					<a class="btn" id="add" name="add">
						<i class="icon-plus"></i>
						新增
					</a>
   				</td>
   			</tr>
   		</table>
  	</div>
  	<div class="info-table">
  		<table>
  			<thead>
  			<tr>
  				<th class="width_10">产品编号</th>
  				<th class="width_10">产品英文名称</th>
  				<th class="width_10">产品中文名称</th>
  				<th class="width_10">单价</th>
  				<th class="width_10">俱乐部价格</th>
  				<th class="width_10">特殊价格</th>
  				<th class="width_10">单位</th>
  				<th class="width_10">数量</th>
  				<th class="width_10">可订购对象</th>
  				<th class="width_10">操作</th>
  			</tr>
  			</thead>
  			<tbody id="lists">
    			
   			</tbody>
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
	<script type="text/javascript">
    	var pagenow =1;
		var totalpage=1;
		var total=0;
		var datalists = null;
		var basepath = "<%=basePath%>";
		
    	$(function(){
    		$('#searchs').click(function(){
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
			$('#add').click(function(){
				addlhg(760, 320, basepath+"page/marketingpremium/marketingpremium_info.jsp" );
			});
    	});
    	function page(_pagenow,_total)
    	{
    		if(_pagenow && _total){
    			if(_pagenow <= l){
	    			$('#first').hide();
	    			$('#pre').hide();
	    		}else{
	    			$('#first').show();
	    			$('#pre').show();
	    		}
	    		if(_pagenow >= _total){
	    			$('#next').hide();
	    			$('#end').hide();
	    		}else{
	    			$('#next').show();
	    			$('#end').show();
	    		}
    		}
    	}
    	function select(pageNow){
   			$('#lists').empty();
   			var data = {
   				'procode': $('#procode').val(),
   				'englishname': $('#englishname').val(),
   				'chinesename': $('#chinesename').val(),
   				'blbz': $('#blbz').val(),
   				'method': 'select',
   				'pageNow': pageNow
   			};
   			$.myAjax("QueryMarketPremiumServlet", data, function(result){
   				var html = ""; 
   				//result = eval("("+result+")"),
   				
   					

   				if(parseInt(result.data.total) > 0){
   					var lists = result.data.list || null;
   						datalists = lists;
   						total=result.data.total;
						pagenow =result.data.pagenow;
					    totalpage=result.data.totalpage;
 
   					for(var i=0; i<lists.length; i++){
	   					html += '<tr>'+
									'<td>'+lists[i].procode+'</td>'+
									'<td>'+lists[i].englishname+'</td>'+
									'<td>'+lists[i].chinesename+'</td>'+
									'<td>'+lists[i].unitprice+'</td>'+
									'<td>'+lists[i].c_clubprice+'</td>'+
									'<td>'+lists[i].specialprice+'</td>'+
									'<td>'+lists[i].unit+'</td>'+
									'<td>'+lists[i].quantity+'</td>'+
									'<td>'+transCN(lists[i].BLBZ)+'</td>'+
									'<td>'+
										'<a href="javascript:void(0);" class="opts" onclick="modify('+i+')">修改</a>';
						if(lists[i].BLBZ=='A'){
							html +=		'<a href="javascript:void(0);" class="opts" onclick="del('+i+')">删除</a>';
						}
						html +=		'</td>'+
								'</tr>';
	   				}
   					$('.page_and_btn').show();
   					$('#total').empty().append(pagenow+"/"+totalpage);
   					page(pagenow, totalpage);
   				}else{
   					html+="<tr id='select'><td colspan='10' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
			 		$(".page_and_btn").hide();
   				}
   				$('#lists').append(html);
   			});
   			
   			return false;
   		}
   		
   		function modify(i){
   			var data = datalists[i];
   			console.log("datalist ="+data);
   			editlhg(760, 320, basepath+"page/marketingpremium/marketingpremium_modify.jsp", data);
   		}
   		function transCN(str){
   			switch(str){
   				case "A":
   					str = "Consultant && Staff";
   					break;
   				case "N":
   					str = "Hidden";
   					break;
   				case "C":
   					str="Consultant";
   					break;
   				case "S":
   					str="Staff"
   					break;
   			}
 			return str;
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
		function exportDate(){
			if(datalists != null){
				var ename = $('#englishname').val(),
					cname = $('#chinesename').val(),
					blbz = $('#blbz').val(),
					procode = $('#procode').val();
				location.href= "QueryMarketPremiumServlet?procode="+procode+"&englishname="+ename+"&chinesename="+cname+"&blbz="+blbz+"&method=export";
			}else{
				alert("请先查询数据再进行导出操作");
			}
		}
		function del(index){
			$.dialog.confirm("是否删除该条数据？",function(){
				var data = {'id':datalists[index].procode,method:'delete'};
				myAjaxs("QueryMarketPremiumServlet",data);
			});
		}
    </script>
</body>
