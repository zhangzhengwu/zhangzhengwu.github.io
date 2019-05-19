package servlet.Medical.Staff;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.StaffMedicalDao;
import dao.impl.StaffMedicalDaoImpl;

public class DeleteMedicalStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DeleteMedicalStaffServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String upd_date=request.getParameter("upd_date");
		String type=request.getParameter("type");
		String medical_Dental=request.getParameter("medical_Dental");
		String term=request.getParameter("term");
		
		StaffMedicalDao sd=new StaffMedicalDaoImpl();
		try{
		int num=-1;
		String username=request.getSession().getAttribute("adminUsername").toString();
		num=sd.deleteMedicalStaff(staffcode, upd_date,username);
		if(num>-1){//删除成功，更新数据
			int nums=-1;
			nums=sd.updateDelte(staffcode, type, term, medical_Dental,username);
			if(nums>-1){
				out.print("操作成功!");
			}else{
				out.print("更新数据失败!");
			}
		}else{
			out.print("删除数据失败!");
		}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("删除数据时出现 ："+e.toString());
		}finally{
		out.flush();
		out.close();
		}
	}

}
