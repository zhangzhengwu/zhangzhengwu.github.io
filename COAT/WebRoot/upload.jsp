<%@page contentType="text/html;charset=GBK" %>
<%
   String formName = request.getParameter("formName");
%>
<title>-ͼƬ�ϴ�</title>

<script language="JavaScript">
<!--
function checkform(){
if (form1.file1.value ==""){
		alert("��ѡ��ͼƬ��");
		form1.file1.focus();
		return false;}
form1.submit();
parent.document.<%=formName%>.zp.value = "�����ϴ�ͼƬ,��ȴ�....";
parent.document.<%=formName%>.submit.disabled = true;
}
//-->
</script>
<body topmargin="0" bgcolor="#F2F2F2">
<form name="form1" method="post" action="FileUploadServlet?formName=<%=formName %>" enctype="multipart/form-data" onsubmit="checkform()">
     <table  align=left width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="border-collapse: collapse" bordercolor="#C0C0C0">
        
		  <tr>
            
      <td CLASS="chinese" bgcolor="#EFEFEF" align=left>�ϴ�ͼƬ 
        <input type="file" name="file1" size=20>
               <input type="submit" name="Submit" value="�ϴ�" class="button">
      </td>
           </tr>
	   </table>
</form>
</body>