<%@page contentType="text/html;charset=GBK"%>
<%@page import="dao.AdminDAO"%>
<%
/*�����δ��¼ϵͳ*/
if (session.getAttribute("adminUsername")==null || session.getAttribute("adminUsername")=="")
	out.println("<script>alert('δ��¼ϵͳ��');top.location.href='signin.jsp';</script>");
else
{
	String adminUsername = session.getAttribute("adminUsername").toString();
	int isRoot = new AdminDAO().getIsRoot(adminUsername);
	if(0 == isRoot)
		out.print("<script>alert('�㲻�ǳ����û���');location.href='UserManage.jsp';</script>");
}
String adminUsername = request.getParameter("adminUsername");
if(new AdminDAO().DelAdminInfo(adminUsername) > 0) {
	out.println("<script>alert('�û���Ϣɾ���ɹ�!');location.href='UserManage.jsp';</script>");
} else {
	out.println("<script>alert('�û���Ϣɾ��ʧ�ܣ�');location.href='UserManage.jsp';</script>");
}
%>
