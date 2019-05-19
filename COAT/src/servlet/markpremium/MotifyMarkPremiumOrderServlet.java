package servlet.markpremium;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.C_GetMarkPremiumDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
/**
 * 修改或删除信息数据
 * @author Wilson
 *
 */
public class MotifyMarkPremiumOrderServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(MotifyMarkPremiumOrderServlet.class);
	synchronized public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	synchronized public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String ordercode = request.getParameter("ordercode");
		int num = 0;
		C_GetMarkPremiumDao nqdao = new C_GetMarkPremiumDaoImpl();
		try{
			log.info("用户"+request.getSession().getAttribute("convoy_username").toString()+"在MotifyStationeryOrderServlet对Order进行了删除操作！");
			num = nqdao.delMarkOrder(ordercode);
			if(num < 0){
				out.print("error");
			}else {
				out.print("success");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.info("DelorUpdServlet中删除操作出现异常"+e.toString());
		} finally{
			out.flush();
			out.close();
		}
	}



}
