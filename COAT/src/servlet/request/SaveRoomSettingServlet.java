package servlet.request;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import dao.C_RoomSettingDao;
import dao.impl.C_RoomSettingDaoImpl;
import entity.C_Roomsetting;

public class SaveRoomSettingServlet extends HttpServlet {

	/**
	 * @author skyjiang
	 * Request for Room Setting 模块
	 * This method is called when a form has its tag value method equals to get.
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SaveRoomSettingServlet.class);
	synchronized public  void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String creator = (String)session.getAttribute("adminUsername");
			String eventName = request.getParameter("eventName");
			String eventDate = request.getParameter("eventDate");
			String startTime = request.getParameter("start_date");
			String endTime = request.getParameter("end_date");
			String convoy = request.getParameter("convoy");
			String cp3 = request.getParameter("cp3");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String userType=request.getParameter("userType");
			
			String remarks = request.getParameter("remarks");
			C_RoomSettingDao dao = new C_RoomSettingDaoImpl();
			C_Roomsetting room = new C_Roomsetting();
			
			String refno = dao.findref();
			room.setRefno(refno);
			room.setStaffcode(staffcode);
			room.setStaffname(staffname);
			room.setUserType(userType);
			room.setEventname(eventName);
			room.setEventDate(eventDate);
			room.setStartTime(startTime);
			room.setEndTime(endTime);
			room.setConvoy(convoy);
			room.setCp3(cp3);
			room.setRemark(remarks);
			room.setCreator(creator);
			room.setStatus("Submitted");
			room.setSfyx("Y");
			//新增前判断eventName是否已存在,不存在才新增
			/*
			 * 2014-6-16 10:25:06  King 注释
			 * String numStr = "";    
			
			int nu = dao.getRoomSettingByName(eventName);
			if(nu == 1){
				numStr = "已存在";
				out.print(numStr);
			}else{
				//修改后的方式的方式
				int result1 = dao.saveRoomSetting(room);
				
				if(result1 >0 ){
					out.print("refno-"+refno);
				}
			}*/
			
			//修改后的方式的方式
			int result1 = dao.saveRoomSetting(room);
			
			if(result1 >0 ){
				out.print("refno-"+refno);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
			log.error("RoomSetting==>Save操作异常："+e);
		}finally{
			out.flush();
			out.close();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
