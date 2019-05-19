package servlet.markpremium;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.wsdl.Output;

import org.apache.log4j.Logger;

import util.Constant;
import util.Util;
import dao.C_GetMarkPremiumDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import entity.C_marOrder;

/**
 * 查询Trainee List
 * @author Wilson
 *
 */
public class ReportMarkPremiumServlet extends HttpServlet {
	Logger log = Logger.getLogger(ReportMarkPremiumServlet.class);
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
			//String urlString = request.getContextPath();
			ServletContext application=this.getServletContext();
			String absPath=new java.io.File( application.getRealPath(request.getRequestURI())).getParent();

			String urlString = absPath.substring(0, absPath.lastIndexOf("\\"));
			
			PrintWriter out = response.getWriter();
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String staffcode = request.getParameter("staffcode");
			String ordercode = request.getParameter("ordercode");

			C_GetMarkPremiumDao  getStationeryDao = new C_GetMarkPremiumDaoImpl();
			 
			/**************************************************************/
			List<C_marOrder> list=new ArrayList<C_marOrder>();
			list=getStationeryDao.queryOrderList(staffcode, ordercode, startDate, endDate, 1000000, 1);
			
			//List<String> list=new ArrayList<String>();
			//list=getStationeryDao.getReportDetail( staffcode,ordercode, startDate, endDate  );
			 
			for (int i = 0; i < list.size(); i++) {
				C_marOrder order = list.get(i);			
				int  num = getStationeryDao.reprtPDF(order.getClientcode(), order.getOrdercode(), startDate, endDate,urlString);
					 
			}
			out.print("导出成功!地址为:"+Constant.MarkReport_URL);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("导出 Mark Premium 时出现 ："+e);
		}

	}

}
