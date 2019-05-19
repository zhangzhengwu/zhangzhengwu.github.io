package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import dao.GetStaffMasterDao;
import dao.impl.GetStaffMasterDaoImpl;
import entity.StaffMasterBean;

/**
 * STAFF印卡信息
 * @author WILSON
 *
 */
public class StaffEditServlet extends HttpServlet {
			Logger log=Logger.getLogger(StaffEditServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String StaffNo = request.getParameter("StaffNo");
			try{
			List<StaffMasterBean> cMaster = null;
			GetStaffMasterDao getStaffDao = new GetStaffMasterDaoImpl();
			cMaster = getStaffDao.getStaffaster(StaffNo);
			JSONArray jsons = JSONArray.fromObject(cMaster);
			out.print(jsons.toString());
			}catch(Exception e){
				log.error("在StaffEditServlet中对staff_master进行查询时出现"+e.toString());
			}finally{
			out.flush();
			out.close();
			}
	 
	}

}
