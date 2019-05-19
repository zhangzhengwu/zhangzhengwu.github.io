<%@page contentType="text/html;charset=GBK"%>
<%@page import="dao.AdminDAO"%>
<%
/*如果还未登录系统*/
if (session.getAttribute("adminUsername")==null || session.getAttribute("adminUsername")=="")
	out.println("<script>alert('未登录系统！');top.location.href='signin.jsp';</script>");
else
{
	String adminUsername = session.getAttribute("adminUsername").toString();
	int isRoot = new AdminDAO().getIsRoot(adminUsername);
	if(0 == isRoot)
		out.print("<script>alert('你不是超级用户！');location.href='UserManage.jsp';</script>");
}
String adminUsername = request.getParameter("adminUsername");
if(new AdminDAO().DelAdminInfo(adminUsername) > 0) {
	out.println("<script>alert('用户信息删除成功!');location.href='UserManage.jsp';</script>");
} else {
	out.println("<script>alert('用户信息删除失败！');location.href='UserManage.jsp';</script>");
}
%>
