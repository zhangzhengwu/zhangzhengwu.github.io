<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'consultantprotitle.jsp' starting page</title>
    
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
   				<td class="tagName">英文专业名称：</td>
   				<td class="tagCont">
   					<input type="text" name="pro_title_ename" id="pro_title_ename" />
   				</td>
   				<td class="tagName">中文专业名称：</td>
   				<td class="tagCont">
   					<input type="text" name="pro_title_cname" id="pro_title_cname" />
   				</td>
   			</tr>
   			 
   			<tr>
   				<td class="tagName">状态：</td>
   				<td class="tagCont">
   					<select id="pro_title_sfyx">
   						<option value="">全部</option>
   						<option value="Y">有效</option>
   						<option value="N">无效</option>
   					</select>
   				</td>
   				<td class="tagCont" style="" colspan="2">
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
  				<th class="width_10">序号</th>
  				<th class="width_10">英文专业名称</th>
  				<th class="width_10">中文专业名称</th>
  				<th class="width_10">添加时间</th>
  				<th class="width_10">添加人</th>
  				<th class="width_10">状态</th>
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
				addlhg(400, 280, basepath+"page/consultantprotitle/consultantprotitle_info.jsp" );
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
   				'pro_title_ename': $('#pro_title_ename').val(),
   				'pro_title_cname': $('#pro_title_cname').val(),
   				'pro_title_sfyx': $('#pro_title_sfyx').val(),
   				'method': 'select',
   				'pageNow': pageNow
   			};
   			$.myAjax("QueryConsultantProTitleServlet", data, function(result){
   				var html = ""; 
   				if(parseInt(result.data.total) > 0){
   					var lists = result.data.list || null;
   						datalists = lists;
   						total=result.data.total;
						pagenow =result.data.pagenow;
					    totalpage=result.data.totalpage;
 
   					for(var i=0; i<lists.length; i++){
	   					html += '<tr>'+
									'<td>'+(i+1)+'</td>'+
									'<td>'+lists[i].prof_title_ename+'</td>'+
									'<td>'+lists[i].prof_title_cname+'</td>'+
									'<td>'+lists[i].add_date+'</td>'+
									'<td>'+lists[i].add_name+'</td>'+
									'<td>'+transCN(lists[i].sfyx)+'</td>'+
									'<td>'+
										'<a href="javascript:void(0);" class="opts" onclick="modify('+i+')">修改</a>';
						if(lists[i].sfyx=='Y'){
							html +=		'<a href="javascript:void(0);" class="opts" onclick="del('+lists[i].Id+')">删除</a>';
						}
						html +=		'</td>'+
								'</tr>';
	   				}
   					$('.page_and_btn').show();
   					$('#total').empty().append(pagenow+"/"+totalpage);
   					page(pagenow, totalpage);
   				}else{
   					html+="<tr id='select'><td colspan='7' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
			 		$(".page_and_btn").hide();
   				}
   				$('#lists').append(html);
   			});
   			
   			return false;
   		}
   		
   		function modify(i){
   			var data = {list: 	datalists[i], id: datalists[i].Id };
   			editlhg(400, 280, basepath+"page/consultantprotitle/consultantprotitle_modify.jsp", data);
   		}
   		function transCN(str){
   			if(str){
   				str = str=="Y"?"有效":"失效";
   				return str;
   			}else{
   				return "";
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
		function exportDate(){
			if(datalists != null){
				var ename = $('#pro_title_ename').val(),
					cname = $('#pro_title_cname').val(),
					sfyx = $('#pro_title_sfyx').val();
				location.href= "QueryConsultantProTitleServlet?&pro_title_ename="+ename+"&pro_title_cname="+cname+"&pro_title_sfyx="+sfyx+"&method=export";
			}else{
				alert("请先查询数据再进行导出操作");
			}
		}
		function del(id){
			$.dialog.confirm("是否删除该条数据？",function(){
				var data = {'id':id,method:'delete'};
				myAjaxs("QueryConsultantProTitleServlet",data);
			});
		}
    </script>
</body>
</html>
