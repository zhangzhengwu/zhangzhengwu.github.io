package servlet.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.SeatAutoDao;
import dao.SeatDao;
import dao.impl.SeatAutoDaoImpl;
import dao.impl.SeatDaoImpl;
import entity.ConsList;
import entity.SeatAutochangeApply;
import entity.SeatList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Pager;
import util.Util;

public class SeatAutoServlet extends HttpServlet {


	private static final long serialVersionUID = -4496871162439469055L;
	Logger log = Logger.getLogger(SeatAutoServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            doPost(request, response);
	}

	String user = null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			user=(String) request.getSession().getAttribute("adminUsername");
			if (method.equals("selectAutoChangeApply")) {
				selectAutoChangeApply(request, response);
			}else if (method.equals("detail")){
				detail(request, response);
			}else if (method.equals("cancel")){
				cancel(request, response);
			}else if (method.equals("cancel1")){
				cancel1(request, response);
			}else if (method.equals("complete")){
				complete(request, response);
			}else if (method.equals("querySeatNoBefore")){
				querySeatNoBefore(request, response);
			}else if (method.equals("queryAllLegalSeatNo")){
				queryAllLegalSeatNo(request, response);
			}else if (method.equals("selectAutoChangeApplyForADM")){
				selectAutoChangeApplyForADM(request, response);
			}else if (method.equals("detailForADM")){
				detailForADM(request, response);
			}else if (method.equals("confirm")){
				confirm(request, response);
			}else if (method.equals("getSeatListInforBySeatNo")){
				getSeatListInforBySeatNo(request, response);
			}else if (method.equals("refuse")){
				refuse(request, response);
			}else {
				throw new Exception("Unauthorized access!");
			}
		} catch (NullPointerException e) {
			result=Util.joinException(e);
			log.error("SystemUserServlet==>" + method + "操作异常：空值==" + e);
		} catch (Exception e) {
			log.error("SystemUserServlet==>" + method + "操作异常：" + e);
			result=Util.joinException(e);
		} finally {
			if(!Util.objIsNULL(result)){
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		}
	
	}
	
	/**
	 * 根据页面传入参数获取座位相关信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void getSeatListInforBySeatNo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		SeatAutoDao seatAutoDao = null;
		try {
			String seatnoafter = request.getParameter("seatnoafter");
			seatAutoDao = new SeatAutoDaoImpl();
			SeatList seatNewListAfter = seatAutoDao.getSeatListBySeatNo(seatnoafter);
			JSONObject json = JSONObject.fromObject(seatNewListAfter);
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 查询系统自动换位
	 * @param request
	 * @param response
	 */
	public void selectAutoChangeApply(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String refno = request.getParameter("refno");
		String name=(String)request.getSession().getAttribute("adminUsername");
		PrintWriter out = response.getWriter();
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatAutoDao.findSeatAutoChangeApplyListByStaffcode(null, page,Util.objIsNULL(startdate)?"1900-01-01":startdate,
					Util.objIsNULL(enddate)?"2099-12-31":enddate,
							Util.objIsNULL(refno)?"%%":refno,name,name);
			List<Object> list = new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
			JSONArray json = JSONArray.fromObject(list);
			out.print(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	/**
	 * ADM查询系统自动换位
	 * @param request
	 * @param response
	 */
	public void selectAutoChangeApplyForADM(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String staffcode = request.getParameter("staffcode");
		String staffname = request.getParameter("staffname");
		String leadercode = request.getParameter("leadercode");
		String leadername = request.getParameter("leadername");
		String seatno = request.getParameter("seatno");
		String status = request.getParameter("status");
		String refno = request.getParameter("refno");
		PrintWriter out = response.getWriter();
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatAutoDao.findSeatAutoChangeApplyListAll(null, page,Util.objIsNULL(startdate)?"1900-01-01":startdate,
					Util.objIsNULL(enddate)?"2099-12-31":enddate,
							Util.objIsNULL(staffcode)?"%%":staffcode,
							Util.objIsNULL(staffname)?"%%":staffname,
							Util.objIsNULL(leadercode)?"%%":leadercode,
							Util.objIsNULL(leadername)?"%%":leadername,
							Util.objIsNULL(seatno)?"%%":seatno,
							Util.objIsNULL(status)?"%%":status,
							Util.objIsNULL(refno)?"%%":refno);
			List<Object> list = new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
			JSONArray json = JSONArray.fromObject(list);
			out.print(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	
	public void detail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String refno = request.getParameter("refno").trim();
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		SeatDao seatDao = new SeatDaoImpl();
		SeatList seatListBefore = null;
		try{
			SeatAutochangeApply seatAutoChangeApply = seatAutoDao.queryListByRefno(refno);
			if(!Util.objIsNULL(seatAutoChangeApply.getSeatnobefore())){
				seatListBefore = seatDao.getSeatListBySeatNo(seatAutoChangeApply.getSeatnobefore());
			}
			seatDao = new SeatDaoImpl();
			SeatList seatListafter = seatDao.getSeatListBySeatNo(seatAutoChangeApply.getSeatno());
			request.setAttribute("seatAutoChangeApply", seatAutoChangeApply);
			request.setAttribute("seatListBefore", seatListBefore);
			request.setAttribute("seatListAfter", seatListafter);
			request.getRequestDispatcher("seat/SeatAutoChangeApply_detail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}	
	public void detailForADM(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String refno = request.getParameter("refno").trim();
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		SeatDao seatDao = new SeatDaoImpl();
		SeatList seatListBefore = null;
		try{
			SeatAutochangeApply seatAutoChangeApply = seatAutoDao.queryListByRefno(refno);
			if(!Util.objIsNULL(seatAutoChangeApply.getSeatnobefore())){
				seatListBefore = seatDao.getSeatListBySeatNo(seatAutoChangeApply.getSeatnobefore());
			}
			seatDao = new SeatDaoImpl();
			SeatList seatListafter = seatDao.getSeatListBySeatNo(seatAutoChangeApply.getSeatno());
			
			request.setAttribute("seatAutoChangeApply", seatAutoChangeApply);
			request.setAttribute("seatListBefore", seatListBefore);
			request.setAttribute("seatListAfter", seatListafter);
			request.getRequestDispatcher("seat/SeatAutoChangeApply_ADMdetail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}	
	
	
	public void cancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			request.getRequestDispatcher("seat/SeatAutoChangeApply_query.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	public void cancel1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			request.getRequestDispatcher("seat/SeatAutoChangeApply_ADMquery.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	
	public void querySeatNoBefore(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode").trim();
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		PrintWriter out = response.getWriter();
		try{
			SeatList userList = seatAutoDao.querySeatNoBefore(staffcode);
			JSONObject json = JSONObject.fromObject(userList);
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}	
	public void queryAllLegalSeatNo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String leadercode = request.getParameter("leadercode").trim();
		int flag = Integer.parseInt(request.getParameter("flag"));
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		PrintWriter out = response.getWriter();
		try{
/*			List<String> seatList = seatAutoDao.queryAllLegalSeatNo(flag,leadercode);
			if((!Util.objIsNULL(flag)) && flag==1 && (Util.objIsNULL(seatList)||seatList.size()<=0) ){
				seatList = seatAutoDao.getSeatnoListByLeaderSeatnoLetter(leadercode);
			}
			*/
			//2017-12-06 edie新逻辑，显示同一层楼所有的合法座位
			List<String> seatList = seatAutoDao.getSeatnoListByLeaderSeatno(leadercode,flag);
			JSONArray json = JSONArray.fromObject(seatList);
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}	
	
	public void confirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		String userType=(String)request.getSession().getAttribute("convoy_userType");
		//获取refno
		String refno=request.getParameter("refno").trim();
		String remarkB = request.getParameter("remarkB");
		int flag = Integer.parseInt(request.getParameter("flag"));
		String seatnobefore = request.getParameter("seatnobefore");
		String seatno = request.getParameter("seatnoafter");
		String staffcode = request.getParameter("staffcode");
		String staffname = request.getParameter("staffname");
		String leadercode = request.getParameter("leadercode");
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		SeatAutochangeApply seatAutoChangeApply = new SeatAutochangeApply();
		
		
		int num = -1;
		try {
			seatAutoChangeApply.setRefno(refno);
			seatAutoChangeApply.setRemarkB(remarkB);
			seatAutoChangeApply.setSeatno(seatno);
			seatAutoChangeApply.setStaffcode(staffcode);
			seatAutoChangeApply.setStaffname(staffname);
			if(!Util.objIsNULL(seatnobefore)){
				seatAutoChangeApply.setSeatnobefore(seatnobefore);
			}
//			seatAutoChangeApply.setLeadercode(leadercode);
			
			  //查询Leader location 和 floor 用来判断选择座位是否在同一层
			  String leaderLocation = seatAutoDao.getLocationByStaffcode(leadercode);
			  String leaderFloor = seatAutoDao.getFloorByStaffcode(leadercode);
			  
			  SeatList seatlist = new SeatList();
			  seatlist = seatAutoDao.getSeatMsgBySeatNo(seatno);
			  SeatList seatlistbefore = new SeatList();
			  if(!Util.objIsNULL(seatnobefore)){
				  seatlistbefore = seatAutoDao.getSeatMsgBySeatNo(seatnobefore);
			  }

			  //通过staffcode查询发起人的名字，用于邮件提醒输出
/*			  ConsList consStaffMsg = seatAutoDao.queryConsMsg(staffcode);
			  String staffName = "";
			  if(!Util.objIsNULL(consStaffMsg)){
				  staffName = consStaffMsg.getEmployeeName();
			  }*/
			  
			  //通过staffcode查询审核人的名字，用于邮件提醒输出
			  ConsList consLeaderMsg = seatAutoDao.queryConsMsg(leadercode);
			  String leaderName = "";
			  if(!Util.objIsNULL(consLeaderMsg)){
				  leaderName = consLeaderMsg.getEmployeeName();
			  }
			  
			  
	    	  //获取被换位者邮箱信息
				String[] str=new String[2];
			    str[0]=request.getParameter("staffcode").trim();
			    String  [] email=seatAutoDao.getEmailByCode(str);
			    String to=email[0];
				String cc="adminfo@convoy.com.hk";
			  
	    	  num = seatAutoDao.updateApply(seatAutoChangeApply,seatlist,seatlistbefore,userType,flag,name,staffname,leaderName,to,cc,leaderLocation,leaderFloor);
	    	  out.print(num);
		    
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void refuse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		String refno=request.getParameter("refno").trim();
		String remarkB = request.getParameter("remarkB");
		int flag = Integer.parseInt(request.getParameter("flag"));
		String seatno = request.getParameter("seatnoafter");
		String staffcode = request.getParameter("staffcode");
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		SeatAutochangeApply seatAutoChangeApply = new SeatAutochangeApply();
		int num = -1;
		try {
			seatAutoChangeApply.setRefno(refno);
			seatAutoChangeApply.setRemarkB(remarkB);
			seatAutoChangeApply.setSeatno(seatno);
			seatAutoChangeApply.setStaffcode(staffcode);
			num = seatAutoDao.LeaderRefusedApply(seatAutoChangeApply,flag,name);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Refused Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	public void complete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		String userType=(String)request.getSession().getAttribute("convoy_userType");
		//获取refno
		String refno=request.getParameter("refno").trim();
		String remarkC = request.getParameter("remarkC");
		int flag = Integer.parseInt(request.getParameter("flag"));
		String staffcode = request.getParameter("staffcode");
		String staffname = request.getParameter("staffname");
		String leadercode = request.getParameter("leadercode");
		String seatnobefore = request.getParameter("seatnobefore");
		String seatnoafter = request.getParameter("seatnoafter");
		String status = request.getParameter("status");
		SeatAutoDao seatAutoDao = new SeatAutoDaoImpl();
		SeatAutochangeApply seatAutoChangeApply = new SeatAutochangeApply();
		int num = -1;
		try {
			seatAutoChangeApply.setRefno(refno);
			seatAutoChangeApply.setRemarkC(remarkC);
			seatAutoChangeApply.setStatus(status);
			seatAutoChangeApply.setStaffcode(staffcode);
			seatAutoChangeApply.setStaffname(staffname);
			seatAutoChangeApply.setSeatno(seatnoafter);
			if(!Util.objIsNULL(seatnobefore)){
				seatAutoChangeApply.setSeatnobefore(seatnobefore);
			}
			
			  SeatList seatlist = new SeatList();
			  seatlist = seatAutoDao.getSeatMsgBySeatNo(seatnoafter);

			  SeatList seatlistbefore = new SeatList();
			  if(!Util.objIsNULL(seatnobefore)){
				  seatlistbefore = seatAutoDao.getSeatMsgBySeatNo(seatnobefore);
			  }
			  //通过staffcode查询发起人的名字，用于邮件提醒输出
/*			  ConsList consStaffMsg = seatAutoDao.queryConsMsg(staffcode);
			  String staffName = "";
			  if(!Util.objIsNULL(consStaffMsg)){
				  staffName = consStaffMsg.getEmployeeName();
			  }*/
			  
			  
			  //通过staffcode查询审核人的名字，用于邮件提醒输出
			  ConsList consLeaderMsg = seatAutoDao.queryConsMsg(leadercode);
			  String leaderName = "";
			  if(!Util.objIsNULL(consLeaderMsg)){
				  leaderName = consLeaderMsg.getEmployeeName();
			  }
			  
	    	  //获取被换位者邮箱信息
				String[] str=new String[2];
			    str[0]=request.getParameter("staffcode").trim();
			    str[1]=request.getParameter("leadercode").trim();
			    String  [] email=seatAutoDao.getEmailByCode(str);
			    String to=email[1];
				String cc=email[0]+";adminfo@convoy.com.hk";
			  
	    	  num = seatAutoDao.updateApplyForADM(seatAutoChangeApply,seatlist,seatlistbefore,userType,flag,name,staffname,leaderName,seatnobefore,to,cc);
	    	  out.print(num);
	    	 
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
	
	
	
	
	
	
	
	

}
