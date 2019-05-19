package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.QueryStaffProfessionalDao;
import dao.impl.QueryStaffProfessionalDaoImpl;
import entity.Professional_title_Staff;

public class SelectStaffProfessionalServlet extends HttpServlet {
			Logger log=Logger.getLogger(SelectStaffProfessionalServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String professional_title = request
					.getParameter("EnglishProfessionalTitle");
			QueryStaffProfessionalDao pd = new QueryStaffProfessionalDaoImpl();
		try {
			Professional_title_Staff pt = new Professional_title_Staff();
			pt = pd.queryStaffProfessional(professional_title);
			out.print(pt.getProf_title_cname());
		} catch (Exception e) {
			log.error("在SeleectStaffProfessionalServlet中对professional_title_staff进行查询时出现"+e.toString());
		} finally {
			out.flush();
			out.close();
		}
	}
}