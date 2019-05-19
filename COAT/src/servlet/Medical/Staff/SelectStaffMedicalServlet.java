package servlet.Medical.Staff;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import util.Util;
import dao.StaffMedicalDao;
import dao.impl.StaffMedicalDaoImpl;

public class SelectStaffMedicalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SelectStaffMedicalServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String type=request.getParameter("type");
		String MedicalDate=request.getParameter("MedicalDate");
		StaffMedicalDao sd=new StaffMedicalDaoImpl();
		//Staff_listBean sb=new Staff_listBean();
try{
	if(Util.objIsNULL(type)&&Util.objIsNULL(MedicalDate)&&!Util.objIsNULL(staffcode)){//如果type为空证明只查询staff基本信息
		JSONArray jsons=JSONArray.fromObject(sd.selectstaff(staffcode));
		out.print(jsons.toString());
		
	}else if(!Util.objIsNULL(type)&&Util.objIsNULL(MedicalDate)&&Util.objIsNULL(staffcode)){//只查询type列表
		JSONArray jsons=JSONArray.fromObject(sd.selectstaffType(type));
		out.print(jsons.toString());
	}else if(Util.objIsNULL(type)&&!Util.objIsNULL(MedicalDate)&& !Util.objIsNULL(staffcode)){//查询历史记录
		JSONArray jsons=JSONArray.fromObject(sd.getTypeNumber(staffcode, MedicalDate));
		out.print(jsons.toString());
	}
	
}catch(Exception e){
	e.printStackTrace();
	log.error("根据staffcode MedicalDate所对应的 MedicalDate的年份的限额信息时出现："+e.toString());
}finally{
		out.flush();
		out.close();
}
	}

}
