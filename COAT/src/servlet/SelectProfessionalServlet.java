package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.QueryProfessionalDao;
import dao.impl.QueryProfessionalDaoImpl;
import entity.Professional_title;
/**
 * 查询Professional
 * @author king.xu
 *
 */
public class SelectProfessionalServlet extends HttpServlet {
				Logger log=Logger.getLogger(SelectProfessionalServlet.class);
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
				QueryProfessionalDao pd = new QueryProfessionalDaoImpl();
		try {
				Professional_title pt = new Professional_title();
				pt = pd.queryProfessional(professional_title);
				out.print(pt.getProf_title_cname());
		} catch (Exception e) {
				log.error("在SelectProfessionalServlet中对professional_title进行查询时出现"+e.toString());
		} finally {
			out.flush();
			out.close();
		}

	}

}
