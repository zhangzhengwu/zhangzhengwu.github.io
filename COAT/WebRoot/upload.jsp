<%@page contentType="text/html;charset=GBK" %>
<%
   String formName = request.getParameter("formName");
%>
<title>-图片上传</title>

<script language="JavaScript">
<!--
function checkform(){
if (form1.file1.value ==""){
		alert("请选择图片！");
		form1.file1.focus();
		return false;}
form1.submit();
parent.document.<%=formName%>.zp.value = "正在上传图片,请等待....";
parent.document.<%=formName%>.submit.disabled = true;
}
//-->
</script>
<body topmargin="0" bgcolor="#F2F2F2">
<form name="form1" method="post" action="FileUploadServlet?formName=<%=formName %>" enctype="multipart/form-data" onsubmit="checkform()">
     <table  align=left width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="border-collapse: collapse" bordercolor="#C0C0C0">
        
		  <tr>
            
      <td CLASS="chinese" bgcolor="#EFEFEF" align=left>上传图片 
        <input type="file" name="file1" size=20>
               <input type="submit" name="Submit" value="上传" class="button">
      </td>
           </tr>
	   </table>
</form>
</body>