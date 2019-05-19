<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<link rel="stylesheet" href="plug/css/bootstrap.min.css">
<link rel="stylesheet"href="plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
<link rel="stylesheet" href="css/layout.css">
<link rel="stylesheet" href="plug/css/site.css">
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script language="javascript">

$(function(){
	$("#nowPassword").bind("click", function(){
	   	$("#nowMessage").hide();
	});
});
/**
function xiugai(){
	$.ajax({
       url:"SystemUserServlet",
       type:"POST",
       data:{"nowPassword":$("#nowPassword").val(),"method":"checkPassword"},  
       success:function(date){
    	   //alert(date);
    	   if(date==1){
    		   $("#nowMessage").hide();
    		   chenggong();
    	   }else{   		   
	   		   $("#nowMessage").show();
	   		   $("#nowPassword").focus();
	   		   $("#nowPassword").val("");
    	   }
		}
 	});

}**/
function xiugai(){	
	//密码最小长度改为8位
	if($("#newPassword").val().length<8){
		//alert("密码长度不能小于8个字符!");
		$("#pwd_err").show();
		$("#newPassword").focus();
		return false;
	}	
	  if($("#againPassword").val()!=$("#newPassword").val()){
	    //alert('两次密码输入不一致!');
	    $("#pwdack_tip").show(); 
	    $("#againPassword").focus();
	    return false;
 	 }
	 var score = checkPassword($("#newPassword"));
	 
	 //根据得分判断密码复杂度
	 if(score < 2 ){
		 $('#newMessage').show();
		 $("#newPassword").focus();
		 return false;
	 }else{
		 $('#newMessage').hide();
	 }
	 
	 
	 
  	if(confirm("是否修改密码？")){  	  
  		$.ajax({
        url:"SystemUserServlet",
        type:"POST",
        data:{"newPassword":$("#newPassword").val(),"method":"updatePassword","nowPassword":$("#nowPassword").val()}, 
        success:function(date){
        	//window.location.href = window.location.href;
        	var result=$.parseJSON(date);
        	 alert(result.msg);
        	 if("success"==result.state){
        	 	location.reload();
        	 }
        	 
        	 
		},error:function(){
				alert("网络连接失败,请联系管理员或稍后重试...");
				return false;
			}		
	 	});
  	}
}
</script>
<style type="text/css">
.info-title .title-info table .tagName{width: 20%!important;}
.info-title .title-info table .tagCont{width: 30%!important;}
</style>
</head>
<body>
<div class="cont-info">
	<div class="info-title">
		<div class="title-head">
			<h1><i class="icon-edit"></i>系统安全密码设置</h1>
		</div>
		<div class="title-info">
			<table style="margin-top: 30px;">
				<tr>
					<td class="tagName">当前密码</td>
					<td class="tagCont">
						<div style="float:left;width: 200px; margin-bottom: 10px;">
							<input  id="nowPassword"  type=password name=nowPassword size=20  />
						</div>
						<div class="ywz_zhuce_yongyu1"> 
							<span id="nowMessage" style="color: #f00; display: none;">当前密码有误</span> 
						</div>
					</td>
				</tr>
				<tr>
					<td class="tagName">重新设置密码</td>
					<td class="tagCont">
						<div style="float:left;width: 200px; margin-bottom: 10px;">
							<input  id="newPassword"  type=password name=newPassword onkeyup="getLevel(this)" size=20 />	
							<div class="ywz_zhuce_huixian" id='pwdLevel_1'> </div>
				            <div class="ywz_zhuce_huixian" id='pwdLevel_2'> </div>
				            <div class="ywz_zhuce_huixian" id='pwdLevel_3'> </div>
				            <div class="ywz_zhuce_hongxianwenzi"> 弱</div>
				            <div class="ywz_zhuce_hongxianwenzi"> 中</div>
				            <div class="ywz_zhuce_hongxianwenzi"> 强</div>
				            <div style="clear: both;"></div>
						</div>
						<div class="ywz_zhuce_yongyu1"> 
							<span id="pwd_tip" style="color: #898989"><font style="color: #F00">*</font> 8-16位，由字母（区分大小写）、数字、符号组成</span> 
							<span id="pwd_err" style="color: #f00; display:none;">8-16位，由字母（区分大小写）、数字、符号组成</span>
							<span id="newMessage" style="color: #f00; display: none;">请增强密码的复杂度</span> 
						</div>
			            
					</td>
				</tr>
				<tr>
					<td class="tagName">再次确认新密码</td>
					<td class="tagCont">
						<div style="float:left;width: 200px; margin-bottom: 10px;">
							<input  id="againPassword"  type=password name=newPassAgain size=20  />
						</div>
						<div class="ywz_zhuce_yongyu1"> 
							<span id="pwdack_tip" style="color: #f00; display: none;">两次输入的密码不一致</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="2">
						<a class="btn" onclick="xiugai();">
							修改密码
						</a>
						<input type=hidden name="action" value="modify"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript" src="plug/js/Util.js"></script>
<script type="text/javascript">
$("#againPassword").bind("blur",function(){
	var pwd = $("#newPassword").val();
	var pwd_ack = $("#againPassword").val();
	
	if(pwd_ack != pwd){
		$("#pwdack_tip").show();
	}else{
		$("#pwdack_tip").hide();
	}
});
</script>
</body>
</html>