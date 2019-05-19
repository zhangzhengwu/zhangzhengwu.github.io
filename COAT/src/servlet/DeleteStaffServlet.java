package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;

import dao.AdminDAO;
import dao.StaffMotifyDao;
import dao.impl.StaffMotifyDaoImpl;
/**
 * 删DeleteStaffServlet信息
 * @author Wilson
 *
 */
public class DeleteStaffServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DeleteStaffServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String adminUsername = request.getSession().getAttribute("adminUsername").toString();
			int isRoot = new AdminDAO().getIsRoot(adminUsername);
			if(isRoot != 1){
				log.info("用户"+adminUsername+"在DeleteStaffServlet中进行了删除操作_无权限！");
				out.print("<script>alert('您不是管理員無法刪除數據!');location.href='queryStaffCard.jsp';</script>");
			}else{
				String staffcode = request.getParameter("StaffNo");
				String urgentdate = request.getParameter("Date");
				
				try{
					StaffMotifyDao delStaff = new StaffMotifyDaoImpl();
					int num = delStaff.updateStaffRequest(staffcode, urgentdate,adminUsername);
					log.info("用户"+adminUsername+"在DeleteStaffServlet中进行了删除操作");
					
					if(num > 0){
						out.print(Constant.Message.MESSAGE_DELETE_SUCCESS);
					}else {
						out.print(Constant.Message.MESSAGE_SJKYC);
					}
				}catch(Exception e){
					e.printStackTrace();
					log.error("用户 "+adminUsername+"在DeleteStaffServlet中进行了删除操作，失败！"+e);
				}finally{
					out.flush();
					out.close();
				}
			}
	}

}
