<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 清除缓存防止页面后退安全隐患,使用时直接包含即可 --> 
   <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<title>NameCard信息管理_用户登录</title>
 
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image:url(images/login_wel.jpg);
	overflow:hidden;
}
.STYLE1 {
	color: #000000;
	font-size: 12px;
}

.txt{
   color:#005aa7;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:21px;
	font-family: '仿宋';
	font-size: 16px;
}
-->
</style>

</head>
<script type="text/javascript">


    function changeVcode() {
        document.getElementById('valiCode').src = "code.jsp";
    }  
   document.onkeydown = function(e){
        if(!e) e = window.event;//火狐中是 window.event
        if((e.keyCode || e.which) == 13){
   			checkSubmit();
        }
    } 
   
   
   function checkSubmit(){
	   if(document.getElementById('Username').value==""){
		   	alert("用户名不能为空!");document.getElementById('Username').focus();
	   }else if(document.getElementById('Password').value==""){
			 alert("密码输入不能为空!");document.getElementById('Password').focus();
	   }else if (document.getElementById('Password').value.indexOf("'") != -1){
			alert("非法字符，请重新输入!");
			document.getElementById('Password').value="";
			document.getElementById('Password').focus();
		}else{
		   document.getElementById('loginForm').submit();
	   }
		   			
   }
</script>
<body onload="javascript:document.getElementById('Username').focus();" >

<% if(request.getAttribute("errMessage")!= null) {%>
	    <script>
	         alert('<%=request.getAttribute("errMessage")%>');
	         location.href="signin.jsp";
	    </script> 
	<% } %>
<form method="post" action="CheckLoginServlet" id="loginForm">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="962" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="200" colspan="3" align="center" ><p
			style="color: #004A7C; font-size: 38px; font-weight: bold;"> 
			 欢迎登录康宏深圳运营管理中心</p></td>
        
      </tr>
       
      <tr>
        <td width="299"></td>
        <td width="329"><table width="99%" height="167" border="0" cellpadding="0" cellspacing="0">
          <tr>
             
            <td width="300"  ><table width="100%" border="0" cellspacing="0" cellpadding="0">
            
              <tr>
                <td width="20%" height="25"><div align="right"><span class="STYLE2">用户名:</span></div></td>
                <td width="56%" height="25"><div align="center">
                  <input type="text" name="username"  id="Username" maxlength="20"  class="txt" />
                </div></td>
                <td width="27%" height="25">&nbsp;</td>
              </tr>
              <tr>
                <td width="16%" height="38"><div align="right"><span class="STYLE2">密码:</span></div></td>
                   <td width="56%" height="25"><div align="center">
                  <input type="password" name="password"    id="Password" maxlength="50"  class="txt"/>
                </div></td>
                <td height="38"><div align="left">
                    
                 </div></td>
              </tr><!-- 
                <tr>
                <td height="38"><div align="right"><span class="STYLE2">验证码:</span></div></td>
                   <td width="56%" height="38"> <div align="center">
                  <input type="text" name="code" onKeyDown="submitForm();"   id="Code"   class="txt"/>
                </div></td>
                <td height="38"><div align="left">
                     <img id = "valiCode" alt="点击更换" title="点击更换" src="code.jsp" style="cursor:hand;"  onClick="changeVcode()" />
                 </div></td>
              </tr>
               -->
              <tr>
                <td height="45" colspan=3><div align="center">
                   <img src='images/dl.gif'  onClick="checkSubmit();"/></div>                   </td>
              </tr>
            </table>          </tr>
        </table></td>
        <td width="334" height="53"></td>
      </tr>
      <tr>
        <td  > </td>
        <td  >&nbsp;</td>
        <td height="213"  >&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>