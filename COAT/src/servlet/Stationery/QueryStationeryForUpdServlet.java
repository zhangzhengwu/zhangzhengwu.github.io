package servlet.Stationery;

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

import dao.C_GetStationeryDao;
import dao.impl.C_GetStationeryDaoImpl;
import entity.C_Payment;
import entity.C_stationeryRecord;

/**
 * 查询MarkPremium 明细
 * @author Wilson
 *
 */
public class QueryStationeryForUpdServlet extends HttpServlet {
	Logger log = Logger.getLogger(QueryStationeryForUpdServlet.class);
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
			PrintWriter out = response.getWriter();
			String ordercode = request.getParameter("ordercode");
			String type = request.getParameter("type");

			C_GetStationeryDao  getStationeryDao = new C_GetStationeryDaoImpl();
		 
			List<C_stationeryRecord> list=new ArrayList<C_stationeryRecord>();
			list=getStationeryDao.getRecordForUpd(ordercode);
			List<C_Payment> listPayment=new ArrayList<C_Payment>();
			//System.out.println(ordercode+"----"+type);
			listPayment=getStationeryDao.getRecordPayment(ordercode, type);
			
			
			//System.out.println(listPayment.size());
			List list1=new ArrayList();
			list1.add(0,list); 
			list1.add(1,listPayment); 
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			out.flush();
			out.close();
		 
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("查询 C_marRecord 时出现 ："+e);
		}

	}

}
