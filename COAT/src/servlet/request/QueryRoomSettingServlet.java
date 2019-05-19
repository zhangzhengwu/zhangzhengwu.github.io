package servlet.request;

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
import dao.C_RoomSettingDao;
import dao.impl.C_RoomSettingDaoImpl;
import entity.C_Payment;
import entity.C_Roomsetting;
import entity.C_RoomsettingOperation;
import entity.C_StationeryOperation;

/**
 * 查询Trainee List
 * @author Wilson
 *
 */
@SuppressWarnings("all")
public class QueryRoomSettingServlet extends HttpServlet {
	Logger log = Logger.getLogger(QueryRoomSettingServlet.class);
	private static final long serialVersionUID = 1L;
	String user=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("query");
		String del = request.getParameter("del");
		String detail = request.getParameter("detail");
		String changeStatus = request.getParameter("changeStatus");
		user=(String) request.getSession().getAttribute("adminUsername");
		if("query".equals(query)){
			query(request,response);
		}else if("del".equals(del)){
			del(request,response);
		}else if("detail".equals(detail)){
			detail(request,response);
		}else if("changeStatus".equals(changeStatus)){
			changeStatus(request,response);
		}else if("changeStationeryStatus".equals(changeStatus)){
			changeStationeryStatus(request,response);
		}
	}

	 void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		try {
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String eventDate = request.getParameter("eventDate");
			String refno=request.getParameter("refno");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String userType=request.getParameter("userType");
			String status=request.getParameter("status");
			C_RoomSettingDao roomDao = new C_RoomSettingDaoImpl();
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
			total=roomDao.getRoomcount(eventDate,userType,staffcode,staffname,refno,startDate, endDate,status);//得到总页数
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
			List<C_Roomsetting> list=new ArrayList<C_Roomsetting>();
			list=roomDao.queryRoomList( eventDate,userType,staffcode,staffname,refno,startDate, endDate,status,pageSize,currentPage);
			
			List list1=new ArrayList();
			list1.add(0,list);
			list1.add(1,currentPage);
			list1.add(2,totalPage);
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		 
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("RoomSetting==>select操作出现异常"+e);
		}finally{
			out.flush();
			out.close();
		}
	}
	 void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			int num = 0;
			String refno=request.getParameter("refno");
			C_RoomSettingDao roomDao = new C_RoomSettingDaoImpl();
			num = roomDao.delRoomSetting(refno);
			if(num > 0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("RoomSetting==>select操作出现异常"+e);
			out.print("Exception :"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
		
	}
	 void detail(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String refno = request.getParameter("refno");
		PrintWriter out = response.getWriter();
		List<C_Roomsetting> list = new ArrayList<C_Roomsetting>();
		C_RoomSettingDao roomDao = new C_RoomSettingDaoImpl();
		try{
			list = roomDao.getRoomSetting(refno);
			List list1=new ArrayList();
			list1.add(0,list);
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		}catch (Exception e) {
			log.error("在QueryRoomSettingServlet中对roomsetting进行查询时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		}
	
	}
	 void changeStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String status = request.getParameter("status");
			try{
				int num = 0;
				String refno = request.getParameter("refno");
				C_RoomSettingDao roomDao = new C_RoomSettingDaoImpl();
				num = roomDao.changeStatus(refno,status);
				if(num > 0){
					out.print("success");
				}else{
					out.print("error");
				}
				C_RoomsettingOperation oper = new C_RoomsettingOperation();
				oper.setOperationName(user);
				oper.setRefno(refno);
				oper.setOperationType(status);
				roomDao.saveRoomSettingOperation(oper);
			}catch(Exception e){
				e.printStackTrace();
				log.error("RoomSetting==>"+status+"操作出现异常"+e);
				out.print("Exception :"+e.toString());
			}finally{
				out.flush();
				out.close();
			}
	}
	 void changeStationeryStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String status = request.getParameter("status");
		try{
			int num = 0;
			String ordercode = request.getParameter("ordercode");
			String staffOrCons = request.getParameter("staffOrCons");
			C_RoomSettingDao roomDao = new C_RoomSettingDaoImpl();
			if("Completed".equals(status) && !"Staff".equals(staffOrCons)){
				C_Payment payment = new C_Payment();
				String menthod = request.getParameter("menthod");
				String payDate = request.getParameter("payDate");
				String handled = request.getParameter("handled");
				String paymentAmount = request.getParameter("paymentAmount");
				String type = request.getParameter("type");
	
				payment.setRefno(ordercode);
				payment.setType(type);
				payment.setPaymentMethod(menthod);
				payment.setPaymentAount(Double.valueOf(paymentAmount));
				payment.setPaymentDate(payDate);
				payment.setHandleder(handled);
				payment.setCreator(user);
				payment.setSfyx("Y");
				roomDao.savePayment(payment);
				
			}
			
			C_StationeryOperation oper = new C_StationeryOperation();
			oper.setOperationName(user);
			oper.setOrdercode(ordercode);
			oper.setOperationType(status);
			roomDao.saveStationeryOperation(oper);
			
			num = roomDao.changeStationeryStatus(ordercode,status);
			if(num > 0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch(Exception e){
			log.error("RoomSetting==>"+status+"操作出现异常"+e);
			out.print("Exception :"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}
}
