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
			var url = basepath + "page/companyasset/companyasset_info.jsp";
			addlhg(900,500,url);
		});
		/* Add end */
  	
  	});

  	
  	function select(pagenow){
  		$.ajax({
  			url:basepath+"CompanyAssetItemServlet",
  			type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
  			data:{"method":"select",
  				  "itemcode":$("#itemcode").val(),
  				  "itemname":$("#itemname").val(),
  				  "sfyx":$("#sfyx").val(),
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
		  					 +"</td><td>"+dataRole[0][i].itemcode
		  					 +"</td><td>"+dataRole[0][i].itemname
		  					 +"</td><td>"+dataRole[0][i].itemnum
		  					 +"</td><td>"+(dataRole[0][i].itemnum - (dataRole[0][i].remainnum))
		  					 +"</td><td>"+dataRole[0][i].remainnum
		  					 +"</td><td>"+(dataRole[0][i].sfyx == "Y"? "有效":"无效")
		  					 +"</td><td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='update(\""+dataRole[0][i].itemId+"\");'>Update</a></c:if>&nbsp;&nbsp;";
		  					 html+="<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='del(\""+dataRole[0][i].itemId+"\");'>Delete</a></c:if>&nbsp;</td></tr>";
					}
	  				$(".page_and_btn").show();
	  				$("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
	  			}else{
	  				html+="<tr class='tr_search'><td colspan='9' align='center'>Sorry, there is no matching record.</td></tr>";
    				$(".page_and_btn").hide();
	  			}
	  			$("#CompanyAssetLists").append(html);
	  			page(pagenow,totalpage);
  			},error:function(){
  			 	error("Network connection is failed, please try later...");
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

 	function update(i) {
		$.dialog( {
			title : '修改',
			id : 'companyasset_update',
			width : 1000,
			height : 500,
			cover : true,
			drag : false,
			lock : true,
		
			focus : false,
			max : false,
			min : false,
			resize : false,
			content : "url:" + basepath
					+ "CompanyAssetItemServlet?method=openUpdateCompanyAsset&itemId=" + i + ""
		});
		load();
	}

	function del(i){
	    if(confirm('are you sure ? ')){  
			$.ajax({
				type:"post",
				url: basepath+"CompanyAssetItemServlet",
	                beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
				data:{"method":"delete","itemId":i},
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

  	</script>
  </head>
	  
  <body>
<div class="cont-info">
  <div class="info-search">
	<table>
		<tr>
			<td class="tagName">编号:</td>
			<td class="tagCont">
				<input type="text" name="itemcode" id="itemcode"/>
			</td>
			<td class="tagName">名称:</td>
			<td class="tagCont">
				<input type="text" name="itemname" id="itemname"/>
			</td>
		</tr>
	
		<tr>
			<td class="tagName">是否有效:</td>
			<td class="tagCont">
				<select id ="sfyx" name ="sfyx" >
	              	<option value="">Please Select Location</option>
	              	<option value="Y">有效</option>
	              	<option value="D">无效</option>
             	</select> 
            </td>	
			<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="search" >
							<i class="icon-search"></i>
							Search
						</a>
					</c:if>				
					<c:if test="${roleObj.add==1}">
	  					<a class="btn" id="add">
	  						<i class="icon-plus"></i>
	  						Add
	  					</a>
  					</c:if>
			</td>
		</tr>
     </table>
  </div>
  <div class="info-table">
  	<table id="CompanyAssetLists">
  		<thead>
		<tr>
			<th class="width_5">序号</th>
			<th class="width_10">编号</th>
			<th class="width_15">名称</th>
			<th class="width_5">库存数量</th>
			<th class="width_5">已借出数量</th>
			<th class="width_5">剩余数量</th>
			<th class="width_5">状态</th>
			<th class="width_10">操作</th>
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
