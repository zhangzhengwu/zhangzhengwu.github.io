package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import util.Util;
import dao.C_GetStationeryDao;
import dao.impl.C_GetStationeryDaoImpl;
import entity.C_stationeryOrder;

/**
 * 查询Trainee List
 * @author Wilson
 *
 */
public class QueryStationeryOrderServlet extends HttpServlet {
	Logger log = Logger.getLogger(QueryStationeryOrderServlet.class);
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
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String ordercode = request.getParameter("ordercode");
			String clientcode = request.getParameter("clientcode");

			C_GetStationeryDao  getStationeryDao = new C_GetStationeryDaoImpl();
			/******************************************分页代码***********************************************/
			String pageIndex = request.getParameter("curretPage");
			int currentPage = 1;//当前页
			int pageSize = 15;//页面大小
			int total=0;//得到总记录数
			int totalPage=0;//总页数
			if(Util.objIsNULL(pageIndex)){//页面当前页为空时
				pageIndex="1";//强制赋值第一页
			}
			currentPage=Integer.parseInt(pageIndex);//设置当前页
			
			total=getStationeryDao.getOrdercount(clientcode, ordercode,startDate, endDate);//得到总页数
			 
			totalPage=total/pageSize;//总页数=总记录数/页面大小
			if(total%pageSize>0){//如果总记录数%页面大小==0时
				totalPage+=1;
			}
			if(currentPage>=totalPage){
				currentPage=totalPage;
			}if(currentPage<=1){
				currentPage=1;
			}

			/************************************************************************************************/
			List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
			list=getStationeryDao.queryOrderList( clientcode, ordercode,startDate, endDate,pageSize,currentPage);
			
			List list1=new ArrayList();
			list1.add(0,list);
			list1.add(1,currentPage);
			list1.add(2,totalPage);
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			out.flush();
			out.close();
		 
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("查询 Tranee 时出现 ："+e);
		}

	}

}
