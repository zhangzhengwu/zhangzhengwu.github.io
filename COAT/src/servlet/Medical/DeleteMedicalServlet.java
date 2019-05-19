package servlet.Medical;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;
import dao.AdminDAO;
import dao.ConsMedicalDao;
import dao.impl.ConsMedicalDaoImpl;
/**
 * 删DeleteMedicalServlet信息
 * @author Wilson
 *
 */
public class DeleteMedicalServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DeleteMedicalServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String adminUsername = request.getSession().getAttribute("adminUsername").toString();
			
				String staffcode = request.getParameter("StaffNo");
				String upddate = request.getParameter("Date");
				try{
					ConsMedicalDao updMedicalDao = new ConsMedicalDaoImpl();
					int num = updMedicalDao.updateConsMedical(staffcode, upddate);
					log.info("用户"+adminUsername+"在DeleteMedicalServlet中进行了删除操作");
					if(num > 0){
						out.print(Constant.Message.CODE_SUCESS);
					}else {
						out.print(Constant.Message.CODE_FAILD);
					}
				}catch(Exception e){
					log.info("用户 "+adminUsername+"在DeleteMedicalServlet中进行了删除操作，失败！"+e);
				}finally{
					out.flush();
					out.close();
				}
	}
}
