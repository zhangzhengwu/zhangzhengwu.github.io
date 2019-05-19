package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.PayerDao;
import dao.impl.PayerDaoImpl;
/**
 * 删除财务支付信息
 * @author kingxu
 *
 */
public class DeletePayerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Logger log=Logger.getLogger(DeletePayerServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			log.info("用户"+request.getSession().getAttribute("adminUsername").toString()+"在DeletePayerServlet中进行了删除操作");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String StaffCode = request.getParameter("StaffNo");
			String Date = request.getParameter("Date");
			try{
			PayerDao pd = new PayerDaoImpl();
			pd.deletePayer(request.getSession().getAttribute("adminUsername").toString(),StaffCode, Date);
			out.print("删除成功！");
			}catch(Exception e){
				log.info("用户 "+request.getSession().getAttribute("adminUsername").toString()+"在DeletePayerServlet中进行过删除操作，失败！");
			}finally{
			out.flush();
			out.close();
			}
	}

}
