package servlet.Medical;

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

import util.Util;

import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;
import entity.Medical;

public class QueryByStaffNoServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Logger log=Logger.getLogger(QueryByStaffNoServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String StaffNo=request.getParameter("StaffNo");//获取Medical添加页面的staffcode
		String MedicalDate=request.getParameter("MedicalDate");//获取Medical添加页面的MedicalDate
		List<Medical> list = new ArrayList<Medical>();
		MedicalDao md=new MedicalDaoImpl();
		try{
			if(Util.objIsNULL(MedicalDate)){//MedicalDate为空
				list=md.findByStaffCode_New(StaffNo);//根据staffcode判断该Consultant是否存在
			}else{//MedicalDate不为空
				 list = md.queryByStaffNo_New(StaffNo,MedicalDate);//根据staffcode,MedicalDate判断该Consultant是否存在
			}
			 JSONArray jsons=JSONArray.fromObject(list);//把list转换成json数据类型的list
			 out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("在QueryByStaffNoServlet中根据StaffNo查询Medical时出现:"+e.toString());
		} finally{
			out.flush();
			out.flush();
		}
		
	}

}
