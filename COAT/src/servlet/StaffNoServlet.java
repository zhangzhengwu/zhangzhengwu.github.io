package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Util;

import net.sf.json.JSONArray;

import dao.GetCNMDao;
import dao.impl.GetCNMDaoImpl;
import entity.Consultant_Master;

/**
 * 保存员工印卡信息
 * @author king.xu
 *
 */
public class StaffNoServlet extends HttpServlet {
	Logger log=Logger.getLogger(StaffNoServlet.class);
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
		String eip=request.getParameter("eip");
		try{
			List<Consultant_Master> cMaster = null;
			GetCNMDao getCNMDao = new GetCNMDaoImpl();
			cMaster = getCNMDao.getConsList(StaffNo);
			JSONArray jsons = JSONArray.fromObject(cMaster);
			out.print(jsons.toString());
			
			/*if (cMaster != null) {
				JSONArray jsons = JSONArray.fromObject(cMaster);
				out.print(jsons.toString());
			} else {
				cMaster = getCNMDao.getConsTrList(StaffNo);
				JSONArray jsons = JSONArray.fromObject(cMaster);
				out.print(jsons.toString());
			}*/
		}catch(Exception e){
			e.printStackTrace();
			log.error((Util.objIsNULL(eip)?"":"用户  "+StaffNo+"====")+"Consultant带出信息查询时出现异常！"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}

}
