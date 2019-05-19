package servlet.EmapServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import util.Util;
import dao.EpartConsultantReportDao;
import dao.impl.EpartConsultantReportDaoImpl;
import entity.Econsreport;

public class ConspartReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ConspartReportServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String startDate=request.getParameter("start_Date");
			String endDate=request.getParameter("end_Date");
			String staffcode=request.getParameter("staffcode");
			EpartConsultantReportDao ec=new EpartConsultantReportDaoImpl();
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
			total=ec.getCount(staffcode, startDate, endDate);//得到总页数
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
			List<Econsreport> list=new ArrayList<Econsreport>();
			list=ec.querybystaff(staffcode, startDate, endDate,pageSize,currentPage);
			List list1=new ArrayList();
			list1.add(0,list);
			list1.add(1,currentPage);
			list1.add(2,totalPage);
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询 ConsPartReport 时出现 ："+e);
		}

	}

}
