package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.C_GetStationeryDao;
import dao.impl.C_GetStationeryDaoImpl;
/**
 * 修改或删除信息数据
 * @author Wilson
 *
 */
public class MotifyStationeryOrderServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(MotifyStationeryOrderServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String ordercode = request.getParameter("ordercode");
		response.setContentType("text/html;charset=utf-8");
		int num = 0;
		C_GetStationeryDao nqdao = new C_GetStationeryDaoImpl();
		try{
			String user=(String) request.getSession().getAttribute("adminUsername");
			//log.info("用户"+request.getSession().getAttribute("convoy_username").toString()+"在MotifyStationeryOrderServlet对Order进行了删除操作！");
			num = nqdao.delStationeryOrder(ordercode,user);
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
