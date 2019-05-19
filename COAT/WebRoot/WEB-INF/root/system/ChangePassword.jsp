<%@page contentType="text/html;charset=UTF-8"%>

<html>
<head>
<link rel="stylesheet" href="plug/css/bootstrap.css">
<link rel="stylesheet" href="plug/css/site.css">
<link rel="stylesheet" href="plug/css/font-awesome.min.css">
<link rel="stylesheet" href="plug/css/layout.css">
<link rel="stylesheet" href="css/systemmsg/main.css">
<!--[if IE 7]>
<link rel="stylesheet" href="plug/css/font-awesome-ie7.min.css">
<![endif]-->
<style type="text/css">
body{
	
}

</style>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script language="javascript">

function xiugai(){
	  $("nowMessage").empty();
      $("#newMessage").empty();
      $("#againMessage").empty();
   	if($("#newPassword").val().length<6){
		//alert("���볤�Ȳ���С��6���ַ�!");
		$("#newMessage").empty().append("���볤�Ȳ���С��6���ַ�!").css('font-size','12px'); 
		return false;
	}	
	  if($("#againPassword").val()!=$("#newPassword").val()){
	    //alert('�����������벻һ��!');
	    $("#againMessage").empty().append("�����������벻һ��!").css('font-size','12px'); 
	    $("#againPassword").focus();
	    return false;
 	 }
	$.ajax({
       url:"SystemUserServlet",
       type:"POST",
       data:{"nowPassword":$("#nowPassword").val(),"method":"verify"},  
       success:function(date){
    	  // alert(date);
    	   if(date=="success"){
    		   chenggong();
    	   }else if(date=="error"){   		   
	   		    $("#nowMessage").empty().append("����������!").css('font-size','12px');
	   		    $("#nowPassword").val("");
    	   }else if(date=="null"){
    		     // session Ϊ��   ��ת��¼ҳ��
    		     location.href="signin.jsp";
    	   }else {
    		   alert(date);
    	   }
		}
 	});
}
function chenggong(){	
  	if(confirm("�Ƿ��޸����룿")){  	  
  		$.ajax({
        url:"SystemUserServlet",
        type:"POST",
        data:{"newPassword":$("#newPassword").val(),"method":"change"}, 
        success:function(date){
        	if(date=="success"){
        		alert("�޸ĳɹ�!");
        		window.location.href = window.location.href;
        	}else if (date=="null"){
        		 location.href="signin.jsp";
        	}else if(date=="error"){
        		alert("�޸�ʧ��!");
        	}else{
        		alert(date);
        	}
		},error:function(){
				alert("��������ʧ��,����ϵ����Ա���Ժ�����...");
				return false;
			}		
	 	});
  	}
}

</script>
</head>
<body>
<div class="cont-info">
	<div class="info-title">
		<div class="title-head">
			<h1>
				<i class="icon-edit"></i>
				ϵͳ��ȫ��������
			</h1>
		</div>
		<div class="title-info">
			<table style="margin-top:30px;">
				<tr>
					<td class="tagName">��ǰ���� </td>
					<td class="tagCont">
						<input  id="nowPassword"  type=password name=nowPassword size=20 />
						<span id="nowMessage"></span>
					</td>
				</tr>
				<tr>
					<td class="tagName">������������</td>
					<td class="tagCont">
						<input  id="newPassword"  type=password name=newPassword size=20 />
			   			<span id="newMessage"></span>
					</td>
				</tr>
				<tr>
					<td class="tagName">�ٴ�ȷ��������</td>
					<td class="tagCont">
						<input  id="againPassword"  type=password name=newPassAgain size=20 />
                		<span id="againMessage"></span>
					</td>
				</tr>
				<tr>
					<td class="tagName" colspan="2" style="text-align:center;">
						<a class="btn" onclick="xiugai();">
							�޸�����
						</a>
          				<input type=hidden name="action" value="modify"/>
					</td>
				</tr>
				<tr style="height:20px;">
					
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>