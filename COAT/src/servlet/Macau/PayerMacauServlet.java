package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import dao.PayerMacauDao;
import dao.impl.PayerMacauDaoImpl;
import entity.Change_Macau;

public class PayerMacauServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(PayerMacauServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String staffcode = request.getParameter("staffcode");
		List<Change_Macau> list = new ArrayList<Change_Macau>();
		PayerMacauDao qdao = new PayerMacauDaoImpl();
		try{
			list = qdao.queryChargeList(startDate, endDate, staffcode);
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			log.error("在PayerMacauServlet中查询财务时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		} 
	}

}
