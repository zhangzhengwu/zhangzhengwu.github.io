<%@page contentType="text/html;charset=GBK"%>
<%@page import="dao.AdminDAO"%>
<%@page import="entity.Admin" %>
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
String adminPassword = request.getParameter("adminPassword");
int isRoot = Integer.parseInt(request.getParameter("isRoot"));
Admin admin = new Admin();
admin.setAdminPassword(adminPassword);
admin.setAdminUsername(adminUsername);
admin.setIsRoot(isRoot);
int haseCode = new AdminDAO().getIsUserName(adminUsername);
if(haseCode>0){
	out.print("<script>alert('����ͬ���û����ˣ�');location.href='UserManage.jsp';</script>");	
}else{
	if(new AdminDAO().AddAdminInfo(admin)) {
		out.print("<script>alert('�û���Ϣ��ӳɹ���');location.href='UserManage.jsp';</script>");
	} else {
		out.print("<script>alert('�û���Ϣ���ʧ�ܣ�');location.href='UserManage.jsp';</script>");
	}
}

%>