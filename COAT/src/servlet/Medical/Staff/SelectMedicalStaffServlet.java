package servlet.Medical.Staff;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Page;

import net.sf.json.JSONArray;
import dao.StaffMedicalDao;
import dao.impl.StaffMedicalDaoImpl;
import entity.Medical_record_staff;

public class SelectMedicalStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SelectMedicalStaffServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String return_oraginal=request.getParameter("oraginal");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		Page page=new Page();
		StaffMedicalDao sd=new StaffMedicalDaoImpl();
		
		//List<Medical_record_staff> mrslist=new ArrayList<Medical_record_staff>();

		try{
			page.setAllRows(sd.getRows(staffcode, return_oraginal, startDate, endDate));
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			List<Medical_record_staff> mrs=new ArrayList<Medical_record_staff>();
			mrs=sd.selectList(staffcode, return_oraginal, startDate, endDate,page);
			List list=new ArrayList();
			list.add(0,mrs);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
		JSONArray jsons = JSONArray.fromObject(list);
		out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询Medical Staff 的最新历史办理记录时出现："+e.toString());
		}finally{
		out.flush();
		out.close();
		}
	}

}
