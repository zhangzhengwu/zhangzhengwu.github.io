package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;
import util.Util;
import dao.C_GetStationeryDao;
import dao.impl.C_GetStationeryDaoImpl;
import entity.C_marOrder;
import entity.C_stationeryOrder;

/**
 * 查询Trainee List
 * @author Wilson
 *
 */
public class ReportStationeryServlet extends HttpServlet {
	Logger log = Logger.getLogger(ReportStationeryServlet.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType("text/html;charset=utf-8");
			ServletContext application=this.getServletContext();
			String absPath=new java.io.File( application.getRealPath(request.getRequestURI())).getParent();

			String urlString = absPath.substring(0, absPath.lastIndexOf("\\"));
			
			PrintWriter out = response.getWriter();
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String staffcode = request.getParameter("staffcode");
			String ordercode = request.getParameter("ordercode");

			C_GetStationeryDao  getStationeryDao = new C_GetStationeryDaoImpl();
			 
			/************************************************************************************************/
			List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
			//List<String> list=new ArrayList<String>();
			//list=getStationeryDao.getReportDetail( staffcode,ordercode, startDate, endDate  );
			list=getStationeryDao.queryOrderList(staffcode, ordercode, startDate, endDate, 1000000, 1);
			for (int i = 0; i < list.size(); i++) {
				C_stationeryOrder order = list.get(i);			
				int  num = getStationeryDao.reprtPDF(order.getClientcode(), order.getOrdercode(), startDate, endDate,urlString);
				
			}
			out.print("导出成功!地址为:"+Constant.StationeryReport_URL);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("导出 Mark Premium 时出现 ："+e);
		}

	}

}
