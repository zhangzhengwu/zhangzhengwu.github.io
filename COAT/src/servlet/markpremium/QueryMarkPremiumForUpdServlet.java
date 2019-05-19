package servlet.markpremium;

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

import dao.C_GetMarkPremiumDao;
import dao.C_GetStationeryDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.C_GetStationeryDaoImpl;
import entity.C_Payment;
import entity.C_marRecord;

/**
 * 查询MarkPremium 明细
 * @author Wilson
 *
 */
public class QueryMarkPremiumForUpdServlet extends HttpServlet {
	Logger log = Logger.getLogger(QueryMarkPremiumForUpdServlet.class);
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
			C_GetMarkPremiumDao  getStationeryDao = new C_GetMarkPremiumDaoImpl();
			
			List<C_marRecord> list=new ArrayList<C_marRecord>();
			list=getStationeryDao.getRecordForUpd(ordercode);
			
			List<C_Payment> listPayment=new ArrayList<C_Payment>();
			listPayment=getStationeryDao.getRecordPayment(ordercode,"MarketingPremium");
			
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
