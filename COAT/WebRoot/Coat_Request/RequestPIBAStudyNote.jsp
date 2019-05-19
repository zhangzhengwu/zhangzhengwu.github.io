<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'RequestPIBAStudyNote.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
  </head>
  
  <body >
  	<div class="e-container">
  		<div class="info-form">
			<h4>IIQAS Study Notes Request</h4>
			<table class="form-table" id="topTable">
				<tr>
					<td class="tagName">Consultant/Staff Code</td>
					<td class="tagCont">
						<input id="staffCodeid" type="text" class="inputstyle" name="" />
    					<span class="redspan">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Consultant/Staff Name</td>
					<td class="tagCont">
						<input type="text"  id="nameid" class="inputstyle"/>
    					<span class="redspan">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Location</td>
					<td class="tagCont">
						<select name="locationid" id="locationid">
				            <option value="">please select location</option>
				            <option value="@Convoy">@Convoy</option>
				            <option value="CP3">CP3</option>
			            </select>
	            		<span class="redspan">*</span>
					</td>
				</tr>
			</table>
  		</div>
  		<div class="info-form" id="divId">
  			<h4>Request Options</h4>
  			<table class="form-table" id="form_options">
  				<tr>
  					<td class="width_15"><strong>Type</strong></td>
  					<td class="width_40"><strong>Book Name</strong></td>
  					<td class="width_15"><strong>Quantity</strong></td>
  					<td class="width_15"><strong>Chinese Version</strong></td>
  					<td class="width_15"><strong>English Version</strong></td>
  				</tr>
  			</table>
  			<div id="form_context" class="e-sub-container detailDiv" style="margin-top: 30px;display: none;">
  			<h4>Details of User</h4>
		    	<table class="form-table" id="detailTable">
		    		<tr>
		    			<%--<td class="width_15">
		    				<strong>Num</strong>
		    			</td>
		    			--%><td class="width_40">
		    				<strong>Name of User(Full Name)</strong><strong style="color: red;">*</strong>
		    			</td>
		    			<td class="width_45">
		    				<strong>Staff Code(if any)</strong>
		    			</td>
		    		</tr>
		    	</table>
  			</div>
	    	<br/>
		    <div  style="text-align: center;">
			    <input type="button" class="btn" value="Submit" onclick="getButtomTableValue();"/>
				<input type="button" class="btn" value="Reset" onclick="cancelOrder();"/>
			    
		    </div>
  		</div>
  	</div>
  </body>
  <script>
  var bookTypes="";  //书本的类型
  var bookNames="";  //书本的名称
  var bookNumber=""; //书本的数量
  var cORe="";		 //中文or英文?
  
  $(function (){
	   $.ajax({
    		 url:"PIBAStudyNoteServlet",
    		 type:"post",
    		 data:{"method":"queryBook"},
    		  beforeSend: function(){
    			  parent.showLoad();
     	  },
     	  complete: function(){
     		  parent.closeLoad();
     	  },
    		 success:function(date){
    			 //alert(date);
    			 var dataRole=eval(date);
    			 var html="";
    			 if(dataRole.length>0){
    				  for(var i=0;i<dataRole.length;i++){
    					  html="<tr class='tr_detail'><td  style='display:none;'>"+dataRole[i].bookNo+"</td><td rowspan='2' id='fff"+i+"'>"+dataRole[i].type+"</td>" +
    					  "<td>"+dataRole[i].bookCname+"</td>" +
    					  "<td rowspan='2'><img class='imgbut ope' src='images/jian.png' onclick='deleteQuantity(\"id"+i+"\");' >" +
    					  "<input type='text' bookno='no"+dataRole[i].bookNo+"' class='ope' summ='sss'  id='id"+i+"' name='name"+i+"' style='width: 30px;border-left-width: 0px;border-right-width: 0px;border-top-width: 0px;' value='1' onkeyup='checkNumber(this);' onafterpaste='checkNumber(this);'  maxlength='1'  >" +
    					  "<img class='imgbut ope' src='images/jia.png' onclick='addQuantity(\"id"+i+"\");' ></td>" +
    					  "<td rowspan='2'><input type='checkbox' ccc='c' textid='id"+i+"' paper='"+dataRole[i].bookNo+"' name='cb' value='"+dataRole[i].bookCname+"' /></td>" +
    					  "<td rowspan='2'>" +
    					  "<input type='checkbox'  textid='id"+i+"' ccc='e' paper2='"+dataRole[i].bookNo+"' name='cb' value='"+dataRole[i].bookEname+"' />" +
    					  "</td></tr>"
	    				  html+="<tr><td>"+dataRole[i].bookEname+"</td></tr>"
   			  			 
	    				  $("#form_options").append(html);
    				  }
    		     }
    		 },error:function(){
    			errorAlert("Network connection is failed, please try later...");
  			   	 return false;
    		 }
    	 });
	   
	    //$("#ssss"+dataRole[i].bookNo+"")
	   
	    var count=0;
	    var num1=0;
	    var num2=0;
		$("input[name='cb']").live("click",function(){
			
				var paper = $(this).attr('paper');
				if($(this).attr("ccc")=='e'){
					paper = $(this).attr('paper2');
				}
			    var textId = $(this).attr('textid');
			    var val = $(this).attr('value');
			    makeDetailTable(paper, textId, val, $(this));
			    if(this.checked){
				    if($(this).attr("ccc")=="c"){
				    	$("input[ccc='e'][paper2='"+$(this).attr("paper")+"']").attr("checked",false);
				    }else{
				    	$("input[ccc='c'][paper='"+$(this).attr("paper2")+"']").attr("checked",false);
				    }
			    }
			    
		});
		
		$("#staffCodeid").val("${convoy_username}");
		$("#nameid").val("${adminUsername}");
		
  });
  

  
  function cancelOrder(){
	  setTimeout(function(){location.reload();},1000);
  }
  
  function deleteQuantity(id){
	  var item = $('#'+id);
	  var curVal = $(item).val();
	  if(curVal > 1){
		  $(item).val(curVal-1);
		  if($('input[textid='+id+']:checked').length > 0){
			  var _this = $('input[textid='+id+']:checked');
			  var _paper = _this.attr('paper');
			  if($('input[textid='+id+']:checked').attr("ccc")=='e'){
				  _paper = _this.attr('paper2');
			  }
			  var _textId = _this.attr('textid');
			  var _val = _this.attr('value');
			  makeDetailTable(_paper, _textId, _val, _this);
			  //delDetailData(_paper,curVal-1);
		  }
	  } 
  }
	/**
	 * 只能输入数字
	 * @param {Object} obj
	 */
	function checkNumber(obj){
		obj.value=obj.value.replace(/\D/g,'');
		if(parseFloat(obj.value)>5){
			obj.value=5;
		}
	};
	
  function addQuantity(id){
	  var item = $('#'+id);
	  var curVal = $(item).val();
	  //每项不能超过五本书
	  if(curVal>=5){
		  return false;
	  }
	  $(item).val(parseFloat(curVal)+1);
	  //alert("paper: "+_paper + "\ntextid: "+_textId+"\nval: "+_val);
	  
	  if($('input[textid='+id+']:checked').length > 0){
		  var _this = $('input[textid='+id+']:checked');
		  var _paper = _this.attr('paper');
		  if($('input[textid='+id+']:checked').attr("ccc")=='e'){
			  _paper = _this.attr('paper2');
		  }
		  var _textId = _this.attr('textid');
		  var _val = _this.attr('value');
		  makeDetailTable(_paper, _textId, _val, _this);
	  }
  }
  
  function delDetailData(paper,rowindex){
	  $('tr.'+paper+':last').remove();
	  $('tr.'+paper+' .rowindex').attr('rowspan',rowindex);
  }
  
  var sumMax=0;
  var max = new Array();
  function makeDetailTable(_paper, _textId, _val, _obj){
	  //alert(_paper+"--"+ _textId+"--"+  _val+"--"+  _obj)
	  var detail = $('#detailTable');
	  var _this = null;
	  var quantity = parseFloat($('#'+_textId).val());
	  var flag = false;
	  var ii=_textId.substring(2,5);
	  var bookType=$("#fff"+ii+"").text();
	  var now_num=-1;
	  if(_obj != null){//由checkbox触发
		  _this = _obj;
	  }
	  
		  ids="";
		  $('tr.'+_paper).each(function(){
			  $(this).remove(0);
		  });
		  var mm=0;
		  var bookNum="";
		  var mmMax = new Array();
		  $(".tr_detail").find("input[summ='sss']").each(function(){
			  bookNum=$(this).parent().parent().children().html();
			  if($("input[paper='"+bookNum+"']").attr("checked")){
					//alert(this.value);
					mmMax[bookNum]=this.value;
			  }
			  if($("input[paper2='"+bookNum+"']").attr("checked")){
					//alert(this.value);
					mmMax[bookNum]=this.value;
			  }
	  	  });
		  
		
		  	now_num=$("input[bookno='no"+_paper+"']").val();
		  
		  		
		  /**
		   * 对集合按升序进行排序,比较出最大值
		   * @param {Object} a
		   * @param {Object} b
		   * @return {TypeName} 
		   */
		  mmMax.sort(function compare(a, b) { 
			  return b-a; 
		  });
    	//  alert("最大值：" + mmMax[0]);
    	  var temp=$("#detailTable input[name='staffname']").length;
    	  if(mmMax[0]==undefined){
    		  mmMax[0]=-1;
    	  }
    	 
    	  if(temp<=mmMax[0]){//结果tr少于最大值
    	 		var html = "";
    		    for(var j=0; j<(parseFloat(mmMax[0])-temp); j++){
    		    	html+="<tr class='"+(temp+j)+"'>";
    		    	html+= '<td><input type="text" name="staffname" / ><span class="redspan">*</span></td>'
			      		+"<td><input type='text' name='code' paper='"+_paper+"' /></td>";
			        html+='</tr>';
    		    }
    	 	  
    	  	$("#detailTable:last").append(html);
    	  }else{
    		  $("#detailTable tr").each(function(n){
    			 if(n!=0 && n>mmMax[0]){
    				$(this).remove();
    			 } 
    		  });
    	  }
		  
		  if($('#detailTable tr').length == 1){
			  $('div.detailDiv').hide();
		  }else{
    	    $('div.detailDiv').show();
		  }
    	  /**
	     // $("#detailTable tr").next().remove();
		  html = '<tr  class="'+_paper+'"><td colspan="3" style="height:14px;"></td></tr>';
		  for(var j=0; j<mmMax[0]; j++){
			  html+= '<tr  class="'+_paper+'">';
			  if(!flag){
				  html+= '<td class="rowIndex" rowspan="'+mmMax[0]+'">'+ bookType +'</td>';
				  flag = true;
			  }
			  html+= '<td><input type="text" name="staffname" / ><span class="redspan">*</span></td>'
			        +"<td><input type='text' name='code' paper='"+_paper+"' /></td>"
			        +'</tr>';
		  }
		  if(mmMax[0]==undefined ||now_num==mmMax[0] || now_num==-1){
			$("#detailTable tr").next().remove();
		  	$(detail).append(html);
		  }
		  **/
		
  }
  
    function getButtomTableValue(){
        var rig=true;
		var codeSum="";    	
		
		if($("#staffCodeid").val()==""){
    		 error("'Staff Code' 不能为空!");
    		 $("#staffCodeid").focus();
    		 return false;
    		 //rig=false;
    	}
    	if($("#nameid").val()==""){
    		 error("'Staff Name' 不能为空!");
    		 $("#nameid").focus();
    		 return false;
    		 //rig=false;
    	}
    	if($("#locationid").val()==""){
    		 error("'Location' 不能为空!");
    		 $("#locationid").focus();
    		 return false;
    		 //rig=false;
    	}
    	var ids="";
    	var staffnames="";
    	
    	$("#form_context").find("input[name='staffname']").each(function(){
			  if(this.value==""){
				  error("'Name Of User' 不能为空");
				  this.focus();
				  rig=false;
				  return false;
			  }else{
				  staffnames+=this.value+"~,~";
			  }
			  
			  //alert($(this).parent().next().children().val())  //对应的拿单人编号signcode
			  var signcode=$(this).parent().next().children().val();
			  if(signcode==""){
			  	codeSum+=$(this).parent().next().children().val()+" ~,~";
			  }else{
				  if(signcode.length!=6){
					  error("'Staff Code' 格式错误!")
					  $(this).parent().next().children().focus();
					  rig=false;
				  	  return false;
				  }
				codeSum+=$(this).parent().next().children().val()+"~,~";
			  }
  		});
   	
   	   $(".tr_detail").find("input[name='cb']").each(function(){
   		   //alert(this.value);
   		   if(this.checked){
   			   		bookTypes+=$(this).parent().parent().children().next().html()+"~,~";
				    if($(this).attr("ccc")=="c"){
				    	//alert($(this).parent().parent().children().next().next().html()+"--中文");
				    	bookNames+=$(this).parent().parent().children().next().next().html()+"~,~";
				    }else{
				    	bookNames+=$(this).parent().parent().next().children().html()+"~,~";
				    	//alert($(this).parent().parent().next().children().html()+"--英文");
				    }
				    //alert($(this).parent().parent().children().next().next().next().children().next().val()+"--数量");
				    bookNumber+=$(this).parent().parent().children().next().next().next().children().next().val()+"~,~";
		   }
		  
  	   });
   	   
    	if($('#detailTable tr').length == 1){
    		error("Didn't choose any order!");
				rig=false;
				return false;
    	}
    	if(rig){
		    if(confirm("Are you sure to submit?")){
	    	   	  $.ajax({
		    		 url:"PIBAStudyNoteServlet",
		    		 type:"post",
		    		 data:{"method":"Submit","type":"Submitted","staffcode":$("#staffCodeid").val(),"staffname":$("#nameid").val(),
	    	   		  "location":$("#locationid").val(),"userType":"${convoy_userType}",
	    	   		   "bookTypes":bookTypes,
	    	   		   "bookNames":bookNames,
	    	   		   "bookNumber":bookNumber,
	    	   		   "staffnames":staffnames,
	    	   		   "signcodes":codeSum},
		    		 success:function(date){
		    			successAlert(date);
		    			cancelOrder();
		    		 },error:function(){
		    			errorAlert("Network connection is failed, please try later...");
		  			   	 return false;
		    		 }
		    	 });
	        	}
    	}
    	
  }
 
  </script>
</html>
