package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import dao.QueryPositionDao;
import dao.impl.QueryPositionDaoImpl;
import entity.Position_list;
/**
 * 查询position
 * @author king.xu
 *
 */
public class SelectPositionServlet extends HttpServlet {
Logger log=Logger.getLogger(SelectPositionServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
	
		QueryPositionDao qdao = new QueryPositionDaoImpl();
		try {
			String position_ename = request.getParameter("EnglishName");
			Position_list pl = new Position_list();
			pl = qdao.queryPosition(position_ename);
			out.print(pl.getPosition_cname());
		} catch (Exception e) {
			log.error("在SelectPositionServlet中对position_list进行查询时出现"+e.toString());
		} finally {
			out.flush();
			out.close();
		}

	}

}
