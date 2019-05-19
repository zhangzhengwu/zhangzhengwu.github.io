package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.DelorUpdNqDao;
import dao.impl.DelorUpdNqDaoImpl;
/**
 * 修改或删除信息数据
 * @author kingxu
 *
 */
public class DelorUpdServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DelorUpdServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		log.info("用户"+request.getSession().getAttribute("adminUsername").toString()+"在DelorUpdServlet对nq_additional进行了删除操作！");
		String consid = request.getParameter("consid");
		response.setContentType("text/html;charset=utf-8");
		int num = 0;
		DelorUpdNqDao nqdao = new DelorUpdNqDaoImpl();
		try{
			num = nqdao.delNqAddBean(consid);
			if(num < 0){
				log.info("删除失败！");
			}
		}catch (Exception e) {
			log.info("DelorUpdServlet中删除操作出现异常"+e.toString());
		} 
	}



}
