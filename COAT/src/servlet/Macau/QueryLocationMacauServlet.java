package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import dao.LocationDao;
import dao.impl.LocationDaoImpl;

public class QueryLocationMacauServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		// String
		// user=request.getSession().getAttribute("adminUsername").toString();
		PrintWriter out = response.getWriter();
		LocationDao locationDao = new LocationDaoImpl();
		
		JSONArray jsons = JSONArray.fromObject(locationDao.queryMacauLocation());
		out.print(jsons.toString());
		out.flush();
		out.close();

	}

}
