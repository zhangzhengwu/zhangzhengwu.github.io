package servlet.MissPayment;

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

import util.DateUtils;

import dao.MissingPaymentDao;
import dao.impl.MissingPaymentDaoImpl;
import entity.Principal;
/**
 * STAFF 查询
 * @author kingxu
 *
 */
public class SelectMissPrincipalServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SelectMissPrincipalServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		List<Principal> list = new ArrayList<Principal>();
		MissingPaymentDao qdao = new MissingPaymentDaoImpl();
		try{
			list = qdao.queryPrincipal();
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(DateUtils.getNowDateTime()+ "获取  Principal时出现："+e);
		} finally{
			out.flush();
			out.close();
		} 
	}



}
