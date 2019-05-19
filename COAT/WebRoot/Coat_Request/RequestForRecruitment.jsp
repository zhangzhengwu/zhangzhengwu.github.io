<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>招聘广告申请</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script type="text/javascript" src="./css/Util.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
    
    <script type="text/javascript">
      var param;
      var userType=null;
    $(document).ready(function(){
         
      //Recruiter Staff Code 失去焦点
       $("#code").blur(function(){
    	    if($("#code").val()!=""){
    	    	find();
    	    }else{
    	    	 $("#code").focus();
    	    }
            
       });
        //Staff Code 失去焦点 获取Consultant name
        $("#staffCode").blur(function(){
           findName();
        }); 
        
        
         var econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			//$("#code").val(econvoy[0]).blur();
          	//$("#name").val(econvoy[1]);
          	//$("#position").val(econvoy[5]);
          	//userType=econvoy[2];
          	userType="Consultant";
       // userType="haha";
        
    $("[type='checkbox']").live("click",function (){
          if(this.checked){
             param= $("[type='checkbox']:checked").val();
            $("[type='checkbox']").not("[value='']").not($(this)).attr("disabled",true); 
          }else{
          	$("[type='checkbox']").not("[value='']").not(this).attr("disabled",false);
          }
       
        }); 
          
    });
    
    //查找consultant Name
    function findName(){
       if( $("#staffCode").val()!=""){
        $.ajax({
         url:'RecruitmentServlet?method='+"findname",
         type:'post',
         data:{"staffcode":$("#staffCode").val()},
         success:function(date){
         if(date=="error"){
              error("Staff code does not exist");
              $("#staffCode").val("");
             
           }else{
               $("#cname").val(date);
           }
             
           }
        });
      }
   
    }
    
    //查找所有產品
    function find(){
     $("#name").val("");
     $("#position").val("");
     $(".product").remove();
      $.ajax({
       url:'RecruitmentServlet',
       type:'post',
       data:{"code1":$("#code").val(),"method":"find"},
       success:function(date){
           //判断dd的值 
           if(date==""){
        	   //表示没有该code
        	   error("The Code do not exist");
              $("#code").val("");
        	  $("#code").focus();
        	  return;
           }else if(dd=="error"){
        	   //查询数据错误
        	   alert("Data is in error, please contact the system administrator");
        	   return;
           }else{
        	   var dd=eval(date);
        	   //一切正常
        	//1.给文本框赋值  
        	   $("#name").val(dd[0].employeeName);
        	   $("#position").val(dd[0].e_PositionName);
        	 //2.列出产品  
        	    var ht=""; 
        	   if(dd[1].length>0){
        		  for(var i=0;i<dd[1].length;i++){
                   if(dd[1][i].remark!='Y'){
                     ht+="<tr class='product'><td>"+dd[1][i].mediatype+"</td><td>"+dd[1][i].medianame+"</td><td>"+dd[1][i].price+"</td><td><input type='checkbox' name='check' value='"+dd[1][i].mediacode+"' /></td></tr>";
                  }else{
                     // ht+="<tr class='product'><td>"+dd[1][i].mediatype+"</td><td>"+dd[1][i].medianame+"</td><td>"+dd[1][i].price+"</td><td><input type='checkbox' disabled='disabled'  value='' name='check'/></td></tr>";
                       alert("Recruiter only allow to queue  up in ONE media");
                       location.href="<%=basePath%>/Coat_Request/RequestForRecruitment.jsp";  
                       return;
                  }
               }      
        	  }
        	    $("#buttomTable").append(ht);
           }
         }
      });
    }
    //submit  request
    function save(){
          $(".redspan").text("*");   
       //验证用户信息是否填写正确
         if($("#code").val().length!=0){
               if($("#code").val().length !=6){
                   error(" Length not with 6 bytes");
                   $("#code").focus();
                  return; 
               }    
         }else{
               error("Recruiter Staff Code is null");
               $("#code").focus();
                  return;
         }
        if($("#name").val().length==0){
              error("Recruiter Name is null");
              $("#name").focus();
              return;
        }
        if($("#email").val().length==0){
             error("Email is null"); 
             $("#email").focus();
             return;
        }else{
            reg=/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/gi;
            if(!reg.test($("#email").val())){
               error("Illegal mailbox");   
               $("#email").focus();
               return;   
            }else{
              var num=$("#email").val().indexOf("@convoy.com.hk"); 
               if(num==-1){
                     error("The mailbox is not in the correct format"); 
                     $("#email").focus();
                return;
                 } 
            }
           
        }
        if($("#staffCode").val().length==0){
           error("Staff Code is null");
           $("#staffCode").focus();
           return;
        }else{
             if($("#staffCode").val().length!=6){
                error("Length not with 6 bytes");
                $("#staffCode").focus();
                return;
             }
        } 
        if(param!=null){
         //验证完成，提交订单
            if(confirm('Are you Sure Submit?')){
            
            $.ajax({
            url:'RecruitmentServlet?method='+"save",
            type:'post',
            async:false,
            data:{"userType":userType,"code":$("#code").val(),"name":$("#name").val(),"position":$("#position").val(),"persion":$("#persion").val(),"email":$("#email").val(),"staffCode":$("#staffCode").val(),"cname":$("#cname").val(),"param":param},
            success:function(date){
            if(date=="success"){
	             successAlert("Success",function(){
	            	 location.href=location.href;
	             });
              }else{
                 errorAlert("The application failed");
              }
           }
        });
            
      }
        }else{
           error("Not choose Available Media ");
          }
       
    }
    
    
    
    
    </script>
  </head>
<body>
<div class="e-container">
	<div class="info-form">
		<h4>Recruitment Advertising Request</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Recruiter Staff Code</td>
				<td class="tagCont">
					<input id="code" type="text" size="50" class="inputstyle"  maxlength="6"/>
		    		<span class="redspan" id="codespan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Recruiter Name</td>
				<td class="tagCont">
					<input id="name" type="text" size="50" class="inputstyle" maxlength="50"/>
		    		<span class="redspan" id="namespan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Position</td>
				<td class="tagCont">
					<input id="position" type="text" size="50" class="inputstyle" maxlength="50"/>
	            	<span class="redspan">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divId">
		<h4>Available Media List</h4>
		<table id="buttomTable">
			<tr>
				<td class="width_25">Media Type</td>
				<td class="width_25">Media Name</td>
				<td class="width_25">Unit Price</td>
				<td class="width_25">Queue</td>
			</tr>
		</table>
	</div>
	<div class="info-form">
		<h4>Information shows on Advertisement</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Contact Person</td>
				<td class="tagCont">
					<input id="persion" type="text" size="50" class="inputstyle" maxlength="50"/>
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Contact Email</td>
				<td class="tagCont">
					<input id="email" type="text" size="50" class="inputstyle" maxlength="50"/>
		    		<span class="redspan" id="emailspan">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Cost charge to</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCont">
					<input id="staffCode" type="text" size="50" class="inputstyle" maxlength="6"/>
		    		<span class="redspan" id="staffCodespan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant Name</td>
				<td class="tagCont">
					<input id="cname" type="text" size="50" class="inputstyle" maxlength="50"/>
		    		<span class="redspan">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" id="save" onclick="save()">Submit</button>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>
</body>
</html>
