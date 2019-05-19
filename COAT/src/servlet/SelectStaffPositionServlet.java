package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import dao.QueryStaffPositionDao;
import dao.impl.QueryStaffPositionDaoImpl;
import entity.Position_Staff_list;
public class SelectStaffPositionServlet extends HttpServlet {
			Logger log=Logger.getLogger(SelectStaffPositionServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String position_ename = request.getParameter("EnglishName");
			QueryStaffPositionDao qdao = new QueryStaffPositionDaoImpl();
		try {
			Position_Staff_list pl = new Position_Staff_list();
			pl = qdao.queryStaffPositionbyName(position_ename);
			out.print(pl.getPosition_cname());
		} catch (Exception e) {
			log.error("在SelectStaffPositionServlet中对position_list_staff进行查询时出现"+e.toString());
		} finally {
			out.flush();
			out.close();
		}

	}

}
