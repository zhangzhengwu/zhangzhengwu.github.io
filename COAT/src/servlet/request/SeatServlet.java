package servlet.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;
import dao.SeatDao;
import dao.impl.SeatDaoImpl;
import entity.ConsList;
import entity.Excel;
import entity.SeatChangeApply;
import entity.SeatList;

public class SeatServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3201790821757182949L;
	Logger log = Logger.getLogger(SeatServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request,response);
	}
	String user = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			user=(String) request.getSession().getAttribute("adminUsername");
			if (method.equals("select")) {
				select(request, response);
			}else if (method.equals("selectChangeApply")){
				selectChangeApply(request, response);
			}else if (method.equals("selectCleanOperation")){
				selectCleanOperation(request, response);
			}else if (method.equals("selectChangeApply_general")){
				selectChangeApply_general(request, response);
			}else if (method.equals("selectChangeApply_leader")){
				selectChangeApply_leader(request, response);
//			}else if (method.equals("loadAddPage")){
//				loadAddPage(request, response);
			}else if (method.equals("querySeatB")){
				result = querySeatB(request, response);
			}else if (method.equals("getTotalSeatNum")){
				getTotalSeatNum(request, response);
			}else if (method.equals("getTotalPTSeatNum")){
				getTotalPTSeatNum(request, response);
			}else if (method.equals("getTotalUsePTSeatNum")){
				getTotalUsePTSeatNum(request, response);
			}else if (method.equals("getTotalUseADSeatNum")){
				getTotalUseADSeatNum(request, response);
			}else if (method.equals("queryStaffcodeA")){
				result = queryStaffcodeA(request, response);
			}else if (method.equals("queryStaffcodeB")){
				result = queryStaffcodeB(request, response);
			}else if (method.equals("SeatDetail")){
				SeatDetail(request, response);
			}else if (method.equals("selectSeatMenuOperation")){
				selectSeatMenuOperation(request, response);
			}else if (method.equals("saveApply")){
				result=saveApply(request, response);
			}else if (method.equals("add")){
				result=addSeatNo(request, response);
			}else if (method.equals("updateSeatNo")){
				result=updateSeatNo(request, response);
			}else if (method.equals("updateSeatNos")){
				result=updateSeatNos(request, response);
			}else if (method.equals("selectSeatNo")){
				result=selectSeatNo(request, response);
			}else if (method.equals("getSeatListInfor")){
				result=getSeatListInforByStaffCode(request, response);
			}else if (method.equals("getSeatListInforBySeatNo")){
				getSeatListInforBySeatNo(request, response);
			}else if (method.equals("updateSeatListBySeatNo")){
				updateSeatListBySeatNo(request, response);
			}else if (method.equals("detail")){
				detail(request, response);
			}else if (method.equals("detail2")){
				detail2(request, response);
			}else if (method.equals("cancel")){
				cancel(request, response);
			}else if (method.equals("cancel1")){//
				cancel1(request, response);
			}else if (method.equals("cancel2")){//
				cancel2(request, response);
			}else if (method.equals("synSeatPlan")){
				synSeatPlan(request, response);
			}else if (method.equals("batchCleanSeat")){
				result = batchCleanSeat(request, response);
			}else if (method.equals("requestLeaderConfirm")){
				requestLeaderConfirm(request, response);
			}else if (method.equals("responseLeaderConfirm")){
				responseLeaderConfirm(request, response);
			}else if (method.equals("responseConfirm")){
				responseConfirm(request, response);
			}else if (method.equals("complete")){
				complete(request, response);
			}else if (method.equals("del")){
				del(request, response);
			}else if (method.equals("delSeatNo")){
				result = delSeatNo(request, response);
			}else if (method.equals("refuse")){
				refuse(request, response);
			}else if (method.equals("refuseByResponse")){
				refuseByResponse(request, response);
			}else if (method.equals("refuseByRequestLeader")){
				refuseByRequestLeader(request, response);
			}else if (method.equals("refuseByResponseLeader")){
				refuseByResponseLeader(request, response);
			}else if (method.equals("ifLegit")){
				ifLegit(request, response);
			}else if (method.equals("ifMeLegit")){
				ifMeLegit(request, response);
			}else if (method.equals("isHidden")){
				isHidden(request, response);
			}else if (method.equals("exportExecl")){
				exportExecl(request, response);
			}else if (method.equals("cleanseatMessage")){
				result = cleanseatMessage(request, response);
			}else if (method.equals("selectChangeApplyOperation")){
				selectChangeApplyOperation(request, response);
			}else if (method.equals("findSeatListByFloor")){
				result=findSeatListByFloor(request, response);
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
	
	public String findSeatListByFloor(HttpServletRequest request,HttpServletResponse response)throws IOException {
		String result="";
		try{
			String floor=request.getParameter("floor");
			SeatDao seatdao = new SeatDaoImpl();
			List<Map<String,Object>> list=seatdao.findSeatListByFloor(floor);
			if(!Util.objIsNULL(list)&&list.size()>0){
				result=Util.getMsgJosnSuccessReturn(list);
			}else{
				result=Util.getMsgJosnObject_error();
			}
		}catch (Exception e) {
			result=Util.joinException(e);
		}
		return result;
	}
	
	/**
	 * 提交换位申请并发送邮件提醒功能
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String saveApply(HttpServletRequest request,HttpServletResponse response) throws IOException {

		String result="";
		String seatnoA = request.getParameter("seatnoA");		
		String seatnoB = request.getParameter("seatnoB").trim();		
		String seatnoC = request.getParameter("seatnoC");		
		String floora = request.getParameter("floora");		
		String floorb = request.getParameter("floorb");		
		String seatnoD = request.getParameter("seatnoD");		
		String staffcodeA = request.getParameter("staffcodeA");		
		String staffcodeB = request.getParameter("staffcodeB").trim();	
		String staffcodeC = request.getParameter("staffcodeC");		
		String staffcodeD = request.getParameter("staffcodeD");		
		String pigeonboxA = request.getParameter("pigeonboxA");		
		String pigeonboxB = request.getParameter("pigeonboxB");		
		String pigeonboxC = request.getParameter("pigeonboxC");		
		String pigeonboxD = request.getParameter("pigeonboxD");		
/*		int checkFlag = Integer.parseInt(request.getParameter("checkFlag"));*/
		String extensionA = request.getParameter("extensionA");		
		String extensionB = request.getParameter("extensionB");		
		String extensionC = request.getParameter("extensionC");		
		String extensionD = request.getParameter("extensionD");		
/*		int extensionFlag = Integer.parseInt(request.getParameter("extensionFlag"));*/
		String remarkall = request.getParameter("remarkall");
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		String name=(String)request.getSession().getAttribute("adminUsername");
		String staffname = request.getParameter("staffname");	
		int num = -1;
		try {
	    	  String refno="";
	    	  synchronized (this) {
	    		  	//生产流水号
	    		  	refno =seatdao.getNo();
					if(Util.objIsNULL(refno))
						throw new Exception("流水号产生异常");
					else
						seatChangeApply.setRefno(refno);
			  }	
	    	  seatChangeApply.setStaffcodea(staffcodeA);
	    	  seatChangeApply.setStaffcodeb(staffcodeB);
	    	  seatChangeApply.setStaffcodec(staffcodeC);
	    	  seatChangeApply.setStaffcoded(staffcodeD);
	    	  seatChangeApply.setSeatnoa(seatnoA);
	    	  seatChangeApply.setSeatnob(seatnoB);
	    	  seatChangeApply.setSeatnoc(seatnoC);
	    	  seatChangeApply.setSeatnod(seatnoD);
	    	  seatChangeApply.setPigeonboxa(pigeonboxA);
	    	  seatChangeApply.setPigeonboxb(pigeonboxB);
	    	  seatChangeApply.setPigeonboxc(pigeonboxC);
	    	  seatChangeApply.setPigeonboxd(pigeonboxD);
	    	  seatChangeApply.setRemarkall(remarkall);
	    	  seatChangeApply.setRemark1(floora);
	    	  seatChangeApply.setRemark2(floorb);
	    	/*  seatChangeApply.setCheckflag(checkFlag);*/
	    	  seatChangeApply.setCreatedate(DateUtils.getNowDateTime());
	    	  seatChangeApply.setSfyx("Y");
	    	  seatChangeApply.setExtensiona(extensionA);
	    	  seatChangeApply.setExtensionb(extensionB);
	    	  seatChangeApply.setExtensionc(extensionC);
	    	  seatChangeApply.setExtensiond(extensionD);
/*	    	  seatChangeApply.setExtensionflag(extensionFlag);*/
	    	  
	    	  if(staffcodeB == null || staffcodeB == ""){
	    		  seatChangeApply.setStatus("ResponseConfirm");
	    	  }else{
	    		  seatChangeApply.setStatus("Submitted"); 
	    	  }
	    	  
			  //通过staffcode查询发起人领导的Msg;
			  ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
			  if(Util.objIsNULL(leaderAMsg)){
				  throw new Exception("找不到申请人的上级信息，申请失败");
			  }
			  String leadercode = leaderAMsg.getRecruiterId();
			  String leadername = leaderAMsg.getRecruiterName();
	    	  
	    	  //获取被换位者邮箱信息
				String[] str=new String[2];
			    str[0]=request.getParameter("staffcodeB").trim();
			    str[1]=leadercode;
			    String  [] email=seatdao.getEmailByCode(str);
			    String to=email[0];
			    String cc=email[1];
	    	    num = seatdao.saveApply(seatChangeApply,name,staffname,leadername,to,cc);
			    result=Util.returnValue(num);
			    //result=Util.getMsgJosnSuccessReturn(seatChangeApply);
			 
		} catch (Exception e) {
			result=Util.joinException(e);
			log.error("Save Seat Change Apply Exception :" + e.getMessage());
		}
		return result;
	}
	/**
	 * 新增座位功能
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String addSeatNo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";		
		int num = -1;
		try {
			
			String seatno = request.getParameter("seatno");
			String staffcode = request.getParameter("staffcode");
			String seatType = request.getParameter("seatType");
			String staffname = request.getParameter("staffname");
			String location = request.getParameter("location");
			String pigenBoxno = request.getParameter("pigenBoxno");
			String ifhidden = request.getParameter("ifhidden");
			String floor = request.getParameter("floor");
			String extensionno = request.getParameter("extensionno");
			String deskDrawerno = request.getParameter("deskDrawerno");
			String lockerno = request.getParameter("lockerno");
			String remark = request.getParameter("remark");
			String name=(String)request.getSession().getAttribute("adminUsername");
			SeatDao seatDao = new SeatDaoImpl();
			SeatList seatlist = null;
			SeatList seatoldlist = null;
			String to = null;
			String cc = null;
			if(Util.objIsNULL(staffcode)){
				/*仅仅只是增加一个新的座位*/
				seatlist = new SeatList(staffcode,staffname,location,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,"Y",name,DateUtils.getNowDateTime(),ifhidden,seatType,remark);
				num = seatDao.saveSeatNo(seatlist,name,to,cc);
				if(num < 1){
					throw new RuntimeException("保存数据至座位表失败");
				}else{
					result = Util.getMsgJosnObject_success();
				}
			}else{
				/*增加座位的同时进行了座位交换*/
				/*查询当前staffcode是否已存在于座位表，即当前顾问是否已有座位安排*/
				int sum = seatDao.existStaffCode(staffcode);
				if(sum != 0){
					/*当前staffcode已有座位安排，增加即意味着换位操作*/
					/*查询该staffcode已有座位的相关信息*/
					seatDao = new SeatDaoImpl();
					seatoldlist = seatDao.getSeatList(staffcode);
					
					  ConsList leaderAMsg = seatDao.queryLeaderMsg(staffcode);
					  if(Util.objIsNULL(leaderAMsg)){
						  throw new Exception("找不到申请人的上级信息，申请失败");
					  }					  
					  String leadercode = leaderAMsg.getRecruiterId();
			    	  //获取被换位者邮箱信息
						String[] str=new String[2];
						str[0]=staffcode;
					    str[1]=leadercode;
					    String  [] email=seatDao.getEmailByCode(str);
					    to = email[0];
					    cc = email[1];					
					
					/*根据楼层不同判断chicken box no 是否需要更改*/
					/*如果换位前座位和新增空位都是15F的  chicken box no 跟座位号绑定*/
					if("15F".equals(seatoldlist.getFloor())&&floor.equals(seatoldlist.getFloor())){
						seatDao = new SeatDaoImpl();
						int changenum = seatDao.change15FloorSeat(seatoldlist,location,floor,seatno,lockerno,deskDrawerno,pigenBoxno,name,ifhidden,seatType,remark,to,cc);
						if(changenum < 1){
							throw new RuntimeException("新增座位号并同时给顾问换位时失败");
						}else{
							result = Util.getMsgJosnObject_success(); 
						}
					}else{
						seatDao = new SeatDaoImpl();
						int changenum = seatDao.changeNot15FloorSeat(seatoldlist,location,floor,seatno,lockerno,deskDrawerno,pigenBoxno,name,ifhidden,seatType,remark,to,cc);
						if(changenum < 1){
							throw new RuntimeException("新增座位号并同时给顾问换位时失败");
						}else{
							result = Util.getMsgJosnObject_success(); 
						}
						
					}
				}else{
					/*当前staffcode暂时没有座位安排，直接添加至新增座位号*/
					  seatDao = new SeatDaoImpl();					
					  ConsList leaderAMsg = seatDao.queryLeaderMsg(staffcode);
					  if(Util.objIsNULL(leaderAMsg)){
						  throw new Exception("找不到申请人的上级信息，申请失败");
					  }					  
					  String leadercode = leaderAMsg.getRecruiterId();
			    	  //获取被换位者邮箱信息
						String[] str=new String[2];
						str[0]=staffcode;
					    str[1]=leadercode;
					    String  [] email=seatDao.getEmailByCode(str);
					    to = email[0];
					    cc = email[1];
					seatlist = new SeatList(staffcode,staffname,location,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,"Y",name,DateUtils.getNowDateTime(),ifhidden,seatType,remark);
					num = seatDao.saveSeatNo(seatlist,name,to,cc);
					if(num < 1){
						throw new RuntimeException("保存数据至座位表失败");
					}else{
						result = Util.getMsgJosnObject_success();
					}
				}
			}
			
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("保存座位失败："+e.toString());
		}
		return result;
	}
	/**
	 * 修改座位功能
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String updateSeatNo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";		
		int num = -1;
		try {
			
			String seatno = request.getParameter("seatno");
			String staffcode = request.getParameter("staffcode");
			String seatType = request.getParameter("seatType");
			String staffname = request.getParameter("staffname");
			String location = request.getParameter("location");
			String pigenBoxno = request.getParameter("pigenBoxno");
			String ifhidden = request.getParameter("ifhidden");
			String floor = request.getParameter("floor");
			String extensionno = request.getParameter("extensionno");
			String deskDrawerno = request.getParameter("deskDrawerno");
			String lockerno = request.getParameter("lockerno");
			String remark = request.getParameter("remark");
			String name=(String)request.getSession().getAttribute("adminUsername");
			SeatDao seatDao = new SeatDaoImpl();
			SeatList seatlist = null;
			SeatList seatoldlist = null;
			String to = null;
			String cc = null;
			if(Util.objIsNULL(staffcode)){
				/*仅仅只是修改一个空座位的基本信息*/
				seatlist = new SeatList(staffcode,staffname,location,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,"Y",name,DateUtils.getNowDateTime(),ifhidden,seatType,remark);
				num = seatDao.updateSeatNo(seatlist,name,to,cc,seatoldlist);
				if(num < 0){
					throw new RuntimeException("修改座位表失败");
				}else{
					result = Util.getMsgJosnObject_success();
				}
			}else{
				/*修改座位的同时进行了座位交换*/
				/*查询当前staffcode是否已存在于座位表，即当前顾问是否已有座位安排*/
				int sum = seatDao.existStaffCode(staffcode);
				if(sum != 0){
					/*当前staffcode已有座位安排，增加即意味着换位操作*/
					/*查询该staffcode已有座位的相关信息*/
					seatDao = new SeatDaoImpl();
					seatoldlist = seatDao.getSeatList(staffcode);
					
					  ConsList leaderAMsg = seatDao.queryLeaderMsg(staffcode);
					  if(Util.objIsNULL(leaderAMsg)){
						  throw new Exception("找不到申请人的上级信息，申请失败");
					  }					  
					  String leadercode = leaderAMsg.getRecruiterId();
			    	  //获取被换位者邮箱信息
						String[] str=new String[2];
						str[0]=staffcode;
					    str[1]=leadercode;
					    String  [] email=seatDao.getEmailByCode(str);
					    to = email[0];
					    cc = email[1];	
					
					/*当前staffcode的座位刚好就是当前修改座位，意味着修改有顾问的座位号的基本信息*/
					if(seatno.equals(seatoldlist.getSeatno())){
						seatDao = new SeatDaoImpl();
						seatlist = new SeatList(staffcode,staffname,location,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,"Y",name,DateUtils.getNowDateTime(),ifhidden,seatType,remark);
						num = seatDao.updateSeatNo(seatlist,name,to,cc,seatoldlist);
						if(num < 1){
							throw new RuntimeException("更新座位表失败");
						}else{
							result = Util.getMsgJosnObject_success();
						}
						
					}else{
						/*根据楼层不同判断chicken box no 是否需要更改*/
						/*如果换位前座位和交换的空位都是15F的  chicken box no 跟座位号绑定*/
						if(!("15F".equals(floor))&&floor.equals(seatoldlist.getFloor())){
							seatDao = new SeatDaoImpl();
							int changenum = seatDao.update15FloorSeat(seatoldlist,location,floor,seatno,lockerno,deskDrawerno,pigenBoxno,name,ifhidden,seatType,remark,to,cc);
							if(changenum < 1){
								throw new RuntimeException("修改座位号并同时给顾问换位时失败");
							}else{
								result = Util.getMsgJosnObject_success(); 
							}
						}else{
							seatDao = new SeatDaoImpl();
							int changenum = seatDao.updateNot15FloorSeat(seatoldlist,location,floor,seatno,lockerno,deskDrawerno,pigenBoxno,name,ifhidden,seatType,remark,to,cc);
							if(changenum < 1){
								throw new RuntimeException("修改座位号并同时给顾问换位时失败");
							}else{
								result = Util.getMsgJosnObject_success(); 
							}
							
						}
					}	
				}else{
					/*当前staffcode暂时没有座位安排，直接添加至新增座位号*/
					seatDao = new SeatDaoImpl();
					
					  ConsList leaderAMsg = seatDao.queryLeaderMsg(staffcode);
					  if(Util.objIsNULL(leaderAMsg)){
						  throw new Exception("找不到申请人的上级信息，申请失败");
					  }					  
					  String leadercode = leaderAMsg.getRecruiterId();
			    	  //获取被换位者邮箱信息
						String[] str=new String[2];
						str[0]=staffcode;
					    str[1]=leadercode;
					    String  [] email=seatDao.getEmailByCode(str);
					    to = email[0];
					    cc = email[1];	
					
					seatlist = new SeatList(staffcode,staffname,location,extensionno,floor,seatno,lockerno,deskDrawerno,pigenBoxno,"Y",name,DateUtils.getNowDateTime(),ifhidden,seatType,remark);
					num = seatDao.updateSeatNo(seatlist,name,to,cc,seatoldlist);
					if(num < 1){
						throw new RuntimeException("更新座位表失败");
					}else{
						result = Util.getMsgJosnObject_success();
					}
				}
			}
			
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("保存座位失败："+e.toString());
		}
		return result;
	}
	/**
	 * 修改座位功能
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String updateSeatNos(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";		
		try {
			
			String seatno = request.getParameter("seatno");
			String staffcode = request.getParameter("staffcode");
			String staffcodetemp = request.getParameter("staffcodetemp");
			String seatType = request.getParameter("seatType");
			String staffnametemp = request.getParameter("staffnametemp");
			String location = request.getParameter("location");
			String pigenBoxno = request.getParameter("pigenBoxno");
			String ifhidden = request.getParameter("ifhidden");
			String floor = request.getParameter("floor");
			String extensionnotemp = request.getParameter("extensionnotemp");
			String deskDrawerno = request.getParameter("deskDrawerno");
			String lockerno = request.getParameter("lockerno");
			String remark = request.getParameter("remark");
			String name=(String)request.getSession().getAttribute("adminUsername");
			SeatDao seatDao = new SeatDaoImpl();
			String to = null;
			String to1 = null;
			String cc = null;
			String cc1 = null;
				/*查询该staffcode已有座位的相关信息*/
				seatDao = new SeatDaoImpl();
				SeatList seatoldlist = seatDao.getSeatList(staffcode);
				  ConsList leaderAMsg = seatDao.queryLeaderMsg(staffcode);
				  if(Util.objIsNULL(leaderAMsg)){
					  throw new Exception("找不到申请人的上级信息，申请失败");
				  }	
				  String leadercode = leaderAMsg.getRecruiterId();
				  
				  ConsList leaderBMsg = seatDao.queryLeaderMsg(staffcodetemp);
				  if(Util.objIsNULL(leaderBMsg)){
					  throw new Exception("找不到被换人的上级信息，申请失败");
				  }					  
				  String leadercodeb = leaderBMsg.getRecruiterId(); 
		    	  //获取被换位者邮箱信息
					String[] str=new String[2];
					str[0]=staffcode;
				    str[1]=leadercode;
				    String  [] email=seatDao.getEmailByCode(str);
				    to = email[0];
				    cc = email[1];					
				    
				    String[] str1=new String[2];
				    str1[0]=staffcodetemp;
				    str1[1]=leadercodeb;
				    String  [] email1=seatDao.getEmailByCode(str1);
				    to1 = email1[0];					
				    cc1 = email1[1];	
				/*根据楼层不同判断chicken box no 是否需要更改*/
				/*如果换位前座位和交换的空位都是15F的  chicken box no 跟座位号绑定*/
				if(!("15F".equals(floor))&&floor.equals(seatoldlist.getFloor())){
					seatDao = new SeatDaoImpl();
					int changenum = seatDao.update15FloorSeats(seatoldlist,staffcodetemp,staffnametemp,extensionnotemp,location,floor,seatno,lockerno,deskDrawerno,pigenBoxno,name,ifhidden,seatType,remark,to,cc,to1,cc1);
					if(changenum < 1){
						throw new RuntimeException("修改座位号并同时给顾问换位时失败");
					}else{
						result = Util.getMsgJosnObject_success(); 
					}
				}else{
					seatDao = new SeatDaoImpl();
					int changenum = seatDao.updateNot15FloorSeats(seatoldlist,staffcodetemp,staffnametemp,extensionnotemp,location,floor,seatno,lockerno,deskDrawerno,pigenBoxno,name,ifhidden,seatType,remark,to,cc,to1,cc1);
					if(changenum < 1){
						throw new RuntimeException("修改座位号并同时给顾问换位时失败");
					}else{
						result = Util.getMsgJosnObject_success(); 
					}
					
				}

			
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("保存座位失败："+e.toString());
		}
		return result;
	}
	/**
	 * 根据座位号查询座位表中是否已存在该座位。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String selectSeatNo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";
		try {
			String seatno = request.getParameter("seatno");
			SeatDao seatDao = new SeatDaoImpl();
			int num =seatDao.existSeatNo(seatno);
			if(num != 0){
				result=Util.getMsgJosnErrorReturn("The seat number is exists！");
			}else{
				result=Util.getMsgJosnObject_success();	
			}
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("查询座位表中座位号是否已存在失败"+e.toString());
		}	
		return result;
	}

	/**
	 * 根据页面传入参数获取座位相关信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getSeatListInforByStaffCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = "";
		SeatDao seatDao = null;
		try {
			String staffcode = request.getParameter("staffcode");
			seatDao = new SeatDaoImpl();
			SeatList seatlist = seatDao.getSeatList(staffcode);
			seatDao = new SeatDaoImpl();
			int num = seatDao.existStaffCode(staffcode);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("seatlist", seatlist);
			map.put("isexist", num);
			result = Util.getMsgJosnSuccessReturn(map);
		} catch (Exception e) {
			result = Util.joinException(e);
		}
		return result;
	}
	/**
	 * 根据页面传入参数获取座位相关信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void getSeatListInforBySeatNo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SeatDao seatDao = null;
		try {
			String seatno = request.getParameter("seatno");
			seatDao = new SeatDaoImpl();
			SeatList seatlist = seatDao.getSeatListBySeatNo(seatno);
			request.setAttribute("seatlist", seatlist);
			request.getRequestDispatcher("seat/seatmenu_detail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除座位  将状态修改Y --> N
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String delSeatNo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		String name=(String)request.getSession().getAttribute("adminUsername");
		SeatDao seatdao = null;
		int num = -1;
		try {
			//获取seatno
			seatdao = new SeatDaoImpl();
			String seatno = request.getParameter("seatno");
			num = seatdao.deleteSeatNo(seatno,name);
			if(num < 1){
				throw new RuntimeException("删除座位号失败!");
			}
			result = Util.getMsgJosnSuccessReturn(num);
		}catch (Exception e) {
			result = Util.joinException(e);
		}
		return result;
	}

	public String cleanseatMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		int num = -1;
		SeatDao seatdao = null;
		SeatDao seatDao = new SeatDaoImpl();
		try {
		String name=(String)request.getSession().getAttribute("adminUsername");
		String seatno=request.getParameter("seatno");		
		//根据座位号查询座位信息并保存
		SeatList seatList = seatDao.getSeatListBySeatNo(seatno);
		String reason=request.getParameter("reason");
		seatdao = new SeatDaoImpl();
		num = seatdao.cleanSeatMenu(seatList,reason,name);
		if(num < 1){
			throw new RuntimeException("清空座位号失败!");
		}
		result = Util.getMsgJosnSuccessReturn(num);
		}catch (Exception e) {
			result = Util.joinException(e);
		}
		return result;
	}	
	
	/**
	 * 根据页面传入参数获取座位相关信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void updateSeatListBySeatNo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SeatDao seatDao = null;
		try {
			String seatno = request.getParameter("seatno");
			seatDao = new SeatDaoImpl();
			SeatList seatlist = seatDao.getSeatListBySeatNo(seatno);
			request.setAttribute("seatlist", seatlist);
			request.getRequestDispatcher("seat/seatmenu_update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode").trim();
		String staffname = request.getParameter("staffname").trim();
		String location = request.getParameter("location");
		String floor = request.getParameter("floor");
		String seatno = request.getParameter("seatno");
		String ifhidden = request.getParameter("ifhidden");
		String ifAD = request.getParameter("ifAD");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findSeatList(null, page,
											  Util.objIsNULL(staffcode)?"%%":staffcode,
											  Util.objIsNULL(staffname)?"%%":staffname,
											  Util.objIsNULL(location)?"%%":location,
											  Util.objIsNULL(floor)?"%%":floor,
											  Util.objIsNULL(ifhidden)?"%%":ifhidden,
											  Util.objIsNULL(ifAD)?"%%":ifAD,
											  Util.objIsNULL(seatno)?"%%":seatno);
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
	
	
	
	public void getTotalSeatNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		int totalseatnum = -1 ;
		try {
			totalseatnum=seatdao.getTotalSeatNumMessage();
			out.print(totalseatnum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	public void getTotalPTSeatNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		int totalptseatnum = -1 ;
		try {
			totalptseatnum=seatdao.getTotalPTSeatNumMessage();
			out.print(totalptseatnum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	public void synSeatPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		String result="";
		try {
			seatdao.SynSeatPlan();
			result=Util.getMsgJosnObject_success();
		} catch (Exception e) {
			result=Util.joinException(e);
			e.printStackTrace();
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		
	}
	public String batchCleanSeat(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		String result="";
		try {
			result=seatdao.batchClean();
		} catch (Exception e) {
			result=Util.joinException(e);
			e.printStackTrace();
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		return result;
	}
	public void getTotalUsePTSeatNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		int totaluseptseatnum = -1 ;
		try {
			totaluseptseatnum=seatdao.getTotalUsePTSeatNumMessage();
			out.print(totaluseptseatnum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	public void getTotalUseADSeatNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		int totaluseadseatnum = -1 ;
		try {
			totaluseadseatnum=seatdao.getTotalUseADSeatNumMessage();
			out.print(totaluseadseatnum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		
	}
	
	
	public void selectChangeApplyOperation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String operationname = request.getParameter("operationname");
		String status = request.getParameter("status");
		String refno = request.getParameter("refno");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findOperationList(null, page,Util.objIsNULL(startdate)?"1900-01-01":startdate,
					Util.objIsNULL(enddate)?"2099-12-31":enddate,
							Util.objIsNULL(operationname)?"%%":operationname,
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
	public void exportExecl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		SeatList seatList = new SeatList();
		seatList.setStaffcode(request.getParameter("staffcode"));
		seatList.setStaffname(request.getParameter("staffname"));
		seatList.setLocation(request.getParameter("address"));
		seatList.setFloor(request.getParameter("floor"));
		seatList.setIfhidden(request.getParameter("ifhidden"));
		seatList.setRemark(request.getParameter("ifAD"));
		seatList.setSeatno(request.getParameter("seatno"));
		
		SeatDao seatdao = new SeatDaoImpl();
		Excel excel=new Excel();
		 try{
			//得到结果
	     List<SeatList> list=seatdao.exportDate(seatList);	
		    //把数据交给Excel
		    excel.setExcelContentList(list);	
		    //设置Excel列头
		    excel.setColumns(new String[]{"Seat No","Staff Code","Staff Name","Location","Extension No","Floor","Locker No","Desk Drawer No","Pigen Box No","If Hidden Seat","AD or DD"});
		    //属性字段名称
		    excel.setHeaderNames(new String[]{"seatno","staffcode","staffname","location","extensionno","floor","lockerno","deskDrawerno","pigenBoxno","ifhidden","remark"});
		   //sheet名称
		    excel.setSheetname("SeatList");
		    //文件名称
			excel.setFilename("SeatList"+System.currentTimeMillis());
			String filename=ExcelTools.getExcelFileName(excel.getFilename());
			response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
	       //生成EXCEL
			ExcelTools.createExcel(excel,response);
		    }catch (Exception e) {
			    e.printStackTrace();
		    }
		
	}
	public void selectSeatMenuOperation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String seatno = request.getParameter("seatno");
		String operationname = request.getParameter("operationname");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findSeatMenuOperationList(null, page,Util.objIsNULL(startdate)?"1900-01-01":startdate,
					Util.objIsNULL(enddate)?"2099-12-31":enddate,
							Util.objIsNULL(seatno)?"%%":seatno,
									Util.objIsNULL(operationname)?"%%":operationname);
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
	

/*	public void loadAddPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcodeA = request.getParameter("staffcodeA").trim();
		SeatDao seatdao = new SeatDaoImpl();
		try{
			SeatList userAList = seatdao.querystaffcodeA(staffcodeA);
			request.setAttribute("userAList", userAList);
			request.getRequestDispatcher("seat/SeatChangeApply_add.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	
		}
		
	}*/
	
	public void responseConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
			PrintWriter out = response.getWriter();
			String name=(String)request.getSession().getAttribute("adminUsername");
			//获取refno
			String refno=request.getParameter("refno").trim();
			//获取日期
			String remarkall = request.getParameter("remarkall");
			String staffcodeA = request.getParameter("staffcodeA");
			SeatDao seatdao = new SeatDaoImpl();
			SeatChangeApply seatChangeApply = new SeatChangeApply();
			int num = -1;
			try {
				seatChangeApply.setRefno(refno);
				seatChangeApply.setRemarkall(remarkall);
				seatChangeApply.setStatus("ResponseConfirm");
				
				  //通过staffcode查询发起人的名字，用于邮件提醒输出
				  SeatList seatListA = seatdao.queryStaffcodeB(staffcodeA);
				  String staffnameA = seatListA.getStaffname();
				  
				  //通过staffcode查询发起人领导的Msg;
				  ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
				  if(Util.objIsNULL(leaderAMsg)){
					  throw new Exception("找不到申请人的上级信息，申请失败");
				  }				  
				  String leadercode = leaderAMsg.getRecruiterId();
				  String leadername = leaderAMsg.getRecruiterName();
				  
		    	  //获取被换位者邮箱信息
					String[] str=new String[2];
				    str[0]=staffcodeA;
				    str[1]=leadercode;
				    String  [] email=seatdao.getEmailByCode(str);
				    String to=email[1];
				    String cc=email[0];
				    
		    	  num = seatdao.responseConfirm(seatChangeApply,name,staffnameA,leadername,to,cc);
		    	  out.print(num);
			    
			}catch (Exception e) {
				e.printStackTrace();
				log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
			}finally{
				out.flush();
				out.close();
			}
	}
	
	public void requestLeaderConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String remarkall = request.getParameter("remarkall");
		String staffcodeA = request.getParameter("staffcodeA");
		String staffcodeB = request.getParameter("staffcodeB");
		
		String seatnoC = request.getParameter("seatnoC");
		String seatnoD = request.getParameter("seatnoD");
		String staffcodeC = request.getParameter("staffcodeC");
		String staffcodeD = request.getParameter("staffcodeD");
/*		String extensionC = request.getParameter("extensionC");
		String extensionD = request.getParameter("extensionD");
		String pigeonboxC = request.getParameter("pigeonboxC");
		String pigeonboxD = request.getParameter("pigeonboxD");*/
		
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setSeatnoc(seatnoC);
			seatChangeApply.setSeatnod(seatnoD);
			seatChangeApply.setStaffcodea(staffcodeA);
			seatChangeApply.setStaffcodec(staffcodeC);
			seatChangeApply.setStaffcoded(staffcodeD);
		/*	seatChangeApply.setExtensionc(extensionC);
			seatChangeApply.setExtensiond(extensionD);
			seatChangeApply.setPigeonboxc(pigeonboxC);
			seatChangeApply.setPigeonboxd(pigeonboxD);*/
			
			  ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
			  if(Util.objIsNULL(leaderAMsg)){
				  throw new Exception("找不到申请人的上级信息，申请失败");
			  }			  
			  String leadercodea = leaderAMsg.getRecruiterId();
			  String leadernamea = leaderAMsg.getRecruiterName();
			  
            String leadercodeb = "";
            String leadernameb = "";
			  
			if(Util.objIsNULL(staffcodeB)){
				//换的是空位，接下来由hkadm处理
				seatChangeApply.setStatus("Confirmed");
			}else{
			  ConsList leaderBMsg = seatdao.queryLeaderMsg(staffcodeB);
			  if(Util.objIsNULL(leaderBMsg)){
				  throw new Exception("找不到被换人的上级信息，申请失败");
			  }				  
			  leadercodeb = leaderBMsg.getRecruiterId();
			  leadernameb = leaderBMsg.getRecruiterName();	
			  
				if(leadercodea.equals(leadercodeb)){
					//上级领导是同一个
					seatChangeApply.setStatus("Completed");
				}else{
					//上级领导不是同一个
					seatChangeApply.setStatus("RequestLeaderConfirm");
				}
			}
			//通过staffcode查询发起人的座位信息
			SeatList seatListA = seatdao.getSeatList(staffcodeA);
//			String staffnameA = seatListA.getStaffname();
			//通过staffcode查询被换人的座位信息
			seatdao = new SeatDaoImpl();
			SeatList seatListB = seatdao.getSeatList(staffcodeB);
//			String staffnameB = seatListB.getStaffname();
			//获取被换位者邮箱信息
			String[] str=new String[3];
			str[0]=staffcodeA;
			str[1]=leadercodeb;
			str[2]=staffcodeB;
			String  [] email=seatdao.getEmailsByCode(str);
			String to=email[0];//申请人邮箱
			String cc=email[1];//被换位领导的邮箱
			String to1 = email[2];//被换者邮箱
			num = seatdao.requestLeaderConfirm(seatChangeApply,name,seatListA,seatListB,leadernamea,leadernameb,to,cc,to1);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	public void responseLeaderConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String remarkall = request.getParameter("remarkall");
		String staffcodeA = request.getParameter("staffcodeA");
		String staffcodeB = request.getParameter("staffcodeB");
		String seatnoC = request.getParameter("seatnoC");
		String seatnoD = request.getParameter("seatnoD");
		String staffcodeC = request.getParameter("staffcodeC");
		String staffcodeD = request.getParameter("staffcodeD");
		/*String extensionC = request.getParameter("extensionC");
		String extensionD = request.getParameter("extensionD");		
		String pigeonboxC = request.getParameter("pigeonboxC");
		String pigeonboxD = request.getParameter("pigeonboxD");*/
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setStatus("Completed");
			seatChangeApply.setSeatnoc(seatnoC);
			seatChangeApply.setSeatnod(seatnoD);
			seatChangeApply.setStaffcodea(staffcodeA);
			seatChangeApply.setStaffcodec(staffcodeC);
			seatChangeApply.setStaffcoded(staffcodeD);
		/*	seatChangeApply.setExtensionc(extensionC);
			seatChangeApply.setExtensiond(extensionD);
			seatChangeApply.setPigeonboxc(pigeonboxC);
			seatChangeApply.setPigeonboxd(pigeonboxD);*/
			
			//通过staffcode查询发起人的名字，用于邮件提醒输出
			SeatList seatListA = seatdao.getSeatList(staffcodeA);
			/*String staffnameA = seatListA.getStaffname();*/
			seatdao = new SeatDaoImpl();
			SeatList seatListB = seatdao.getSeatList(staffcodeB);
			/*String staffnameB = seatListB.getStaffname();*/
			
			//获取申请人上级的工号，用于发送邮件
			ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
			  if(Util.objIsNULL(leaderAMsg)){
				  throw new Exception("找不到申请人的上级信息，申请失败");
			  }			
			String leadercodea = leaderAMsg.getRecruiterId();
			//获取被换人上级的名字，用于发送邮件
		    ConsList leaderBMsg = seatdao.queryLeaderMsg(staffcodeB);
			  if(Util.objIsNULL(leaderBMsg)){
				  throw new Exception("找不到被换人的上级信息，申请失败");
			  }		    
		    String leadernameb = leaderBMsg.getRecruiterName();
			
			//获取被换位者邮箱信息
			String[] str=new String[3];
			str[0]=staffcodeA;
			str[1]=staffcodeB;
			str[2]=leadercodea;
			String  [] email=seatdao.getEmailsByCode(str);
			String to=email[0];
			String cc=email[1]+email[2];
			num = seatdao.responseLeaderConfirm(seatChangeApply,name,seatListA,seatListB,leadernameb,to,cc);
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
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String remarkall = request.getParameter("remarkall");
		String staffcodeA = request.getParameter("staffcodeA");
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setStatus("Refused");
			
			//通过staffcode查询发起人的名字，用于邮件提醒输出
			SeatList seatListA = seatdao.queryStaffcodeB(staffcodeA);
			String staffnameA = seatListA.getStaffname();
			//通过staffcode查询审核人的名字，用于邮件提醒输出
			//获取被换位者邮箱信息
			String[] str=new String[2];
			str[0]=request.getParameter("staffcodeA").trim();
			String  [] email=seatdao.getEmailByCode(str);
			String to=email[0];
			
			num = seatdao.refuseApply(seatChangeApply,name,staffnameA,to);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void refuseByResponseLeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String remarkall = request.getParameter("remarkall");
		String staffcodeA = request.getParameter("staffcodeA");
		String staffcodeB = request.getParameter("staffcodeB");
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setStatus("Refused");
			
			//通过staffcode查询发起人的名字，用于邮件提醒输出
			SeatList seatListA = seatdao.queryStaffcodeB(staffcodeA);
			String staffnameA = seatListA.getStaffname();
			//通过staffcode查询审核人的名字，用于邮件提醒输出
			ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
			String leadercodea = leaderAMsg.getEmployeeId();
			ConsList leaderBMsg = seatdao.queryLeaderMsg(staffcodeB);
			String leadernameb = leaderBMsg.getEmployeeName();
			
			
			//获取被换位者邮箱信息
			String[] str=new String[3];
			str[0]=request.getParameter("staffcodeA").trim();
			str[1]=request.getParameter("staffcodeB").trim();
			str[2]=leadercodea;
			String  [] email=seatdao.getEmailsByCode(str);
			String to=email[0];
			String cc=email[1]+email[2];
			
			num = seatdao.refuseApplyByReponseLeader(seatChangeApply,name,staffnameA,leadernameb,to,cc);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void refuseByRequestLeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String remarkall = request.getParameter("remarkall");
		String staffcodeA = request.getParameter("staffcodeA");
		String staffcodeB = request.getParameter("staffcodeB");
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setStatus("Refused");
			
			//通过staffcode查询发起人的名字，用于邮件提醒输出
			SeatList seatListA = seatdao.queryStaffcodeB(staffcodeA);
			String staffnameA = seatListA.getStaffname();

			ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
			String leadernamea = leaderAMsg.getEmployeeName();			
			
			//通过staffcode查询审核人的名字，用于邮件提醒输出
			//获取被换位者邮箱信息
			String[] str=new String[2];
			str[0]=request.getParameter("staffcodeA").trim();
			if(Util.objIsNULL(staffcodeB)){
				str[1]=null;
			}else{
				str[1]=staffcodeB;
			}
			String  [] email=seatdao.getEmailByCode(str);
			String to=email[0];
			String cc=email[1];
			
			num = seatdao.refuseApplyByReqestLeader(seatChangeApply,name,staffnameA,leadernamea,to,cc);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void refuseByResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String remarkall = request.getParameter("remarkall");
		String staffcodeA = request.getParameter("staffcodeA");
		String staffcodeB = request.getParameter("staffcodeB");
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setStatus("Refused");
			
			//通过staffcode查询发起人的名字，用于邮件提醒输出
			SeatList seatListA = seatdao.queryStaffcodeB(staffcodeA);
			String staffnameA = seatListA.getStaffname();
			SeatList seatListB = seatdao.queryStaffcodeB(staffcodeB);
			String staffnameB = seatListB.getStaffname();
			//通过staffcode查询审核人的名字，用于邮件提醒输出
			//获取被换位者邮箱信息
			String[] str=new String[2];
			str[0]=request.getParameter("staffcodeA").trim();
			String  [] email=seatdao.getEmailByCode(str);
			String to=email[0];
			
			num = seatdao.refuseApplyByResponse(seatChangeApply,name,staffnameA,staffnameB,to);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		SeatDao seatdao = new SeatDaoImpl();
		int num = -1;
		try {
			num = seatdao.del(name,refno);
			out.print(num);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}

	public void ifLegit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String seatnoA=request.getParameter("seatnoA").trim();
		String seatnoB=request.getParameter("seatnoB").trim();
		SeatDao seatdao = new SeatDaoImpl();
		int num = -1;
		try {
			num = seatdao.ifExist(seatnoA,seatnoB);
			out.print(num);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void ifMeLegit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String staffcodeA=request.getParameter("staffcodeA").trim();
		SeatDao seatdao = new SeatDaoImpl();
		int num = -1;
		try {
			num = seatdao.ifMeExist(staffcodeA);
			out.print(num);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	public void isHidden(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String seatnoA=request.getParameter("seatnoA").trim();
		SeatDao seatdao = new SeatDaoImpl();
		int num = -1;
		try {
			num = seatdao.isHidden(seatnoA);
			out.print(num);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	public void complete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String name=(String)request.getSession().getAttribute("adminUsername");
		//获取refno
		String refno=request.getParameter("refno").trim();
		//获取日期
		String status = request.getParameter("status");
		String seatnoC = request.getParameter("seatnoC");
		String seatnoD = request.getParameter("seatnoD");
		String staffcodeA = request.getParameter("staffcodeA");
		/*String staffcodeB = request.getParameter("staffcodeB");*/
		String staffcodeC = request.getParameter("staffcodeC");
		String staffcodeD = request.getParameter("staffcodeD");
	/*	String extensionC = request.getParameter("extensionC");
		String extensionD = request.getParameter("extensionD");		
		String pigeonboxC = request.getParameter("pigeonboxC");
		String pigeonboxD = request.getParameter("pigeonboxD");*/
		String remarkall = request.getParameter("remarkall");
		SeatDao seatdao = new SeatDaoImpl();
		SeatChangeApply seatChangeApply = new SeatChangeApply();
		int num = -1;
		try {
			seatChangeApply.setRefno(refno);
			seatChangeApply.setRemarkall(remarkall);
			seatChangeApply.setStatus(status);
			seatChangeApply.setSeatnoc(seatnoC);
			seatChangeApply.setSeatnod(seatnoD);
			seatChangeApply.setStaffcodea(staffcodeA);
			seatChangeApply.setStaffcodec(staffcodeC);
			seatChangeApply.setStaffcoded(staffcodeD);
/*			seatChangeApply.setExtensionc(extensionC);
			seatChangeApply.setExtensiond(extensionD);
			seatChangeApply.setPigeonboxc(pigeonboxC);
			seatChangeApply.setPigeonboxd(pigeonboxD);*/
			
			  //通过staffcode查询发起人的名字，用于邮件提醒输出
			  SeatList seatListA = seatdao.getSeatListBySeatNo(seatnoC);
			/*  String staffnameA = seatListA.getStaffname();*/
			  //通过staffcode查询审核人的名字，用于邮件提醒输出
			  seatdao = new SeatDaoImpl();
			  SeatList seatListB = seatdao.getSeatListBySeatNo(seatnoD);
			/*  String staffnameB = seatListB.getStaffname();*/
			  
			  ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodeA);
			  if(Util.objIsNULL(leaderAMsg)){
				  throw new Exception("找不到申请人的上级信息，申请失败");
			  }				  
			  String leadercodea = leaderAMsg.getRecruiterId();
			  
	    	  //获取被换位者邮箱信息
				String[] str=new String[2];
			    str[0]=request.getParameter("staffcodeA").trim();
			    str[1]=leadercodea;
			    String  [] email=seatdao.getEmailByCode(str);
			    String to=email[0];
				String cc=email[1];

			num = seatdao.updateApplyC(seatChangeApply,name,to,cc,seatListA,seatListB);
			out.print(num);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
	public void detail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String refno = request.getParameter("refno").trim();
		SeatDao seatdao = new SeatDaoImpl();
		try{
			SeatChangeApply seatChangeApply = seatdao.queryListByRefno(refno);
			request.setAttribute("seatChangeApply", seatChangeApply);
			request.getRequestDispatcher("seat/SeatChangeApply_detail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	public void detail2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String refno = request.getParameter("refno").trim();
		SeatDao seatdao = new SeatDaoImpl();
		try{
			SeatChangeApply seatChangeApply = seatdao.queryListByRefno(refno);
			
			  String staffcodea = seatChangeApply.getStaffcodea();
			  String staffcodeb = seatChangeApply.getStaffcodeb();
			  
			  ConsList leaderAMsg = seatdao.queryLeaderMsg(staffcodea);
			  
			  String leadercodea = "";
			  if(!Util.objIsNULL(leaderAMsg)){
			  leadercodea = leaderAMsg.getRecruiterId();
			  }
			  String leadercodeb = "";
			  if(!Util.objIsNULL(staffcodeb)){  
			  ConsList leaderBMsg = seatdao.queryLeaderMsg(staffcodeb);
			  leadercodeb = leaderBMsg.getRecruiterId();
			  }
			  request.setAttribute("leadercodea", leadercodea);
			  request.setAttribute("leadercodeb", leadercodeb);
			  request.setAttribute("seatChangeApply", seatChangeApply);
			  request.setAttribute("type", request.getParameter("type").trim());
			  request.getRequestDispatcher("seat/SeatChangeApply_detail2.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	public void cancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			request.getRequestDispatcher("seat/SeatChangeApply.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	public void cancel1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			request.getRequestDispatcher("seat/SeatChangeApply_query.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	public void cancel2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			request.getRequestDispatcher("seat/SeatChangeApply_leader.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	public String querySeatB(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result ="";
		String seatnoB = request.getParameter("seatnoB").trim();
		SeatDao seatdao = null;
		try{	
			seatdao = new SeatDaoImpl();
			SeatList userBList = seatdao.querySeatnoB(seatnoB);
			seatdao = new SeatDaoImpl();
			//查询所有的PA staffcode
			List<String> PAList = seatdao.getPAList();			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userBList", userBList);
			map.put("PAList", PAList);
			result = Util.getMsgJosnSuccessReturn(map);			
		} catch (Exception e) {
			result=Util.joinException(e);
		}
		return result;
		
	}
	public String queryStaffcodeB(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		String staffcodeB = request.getParameter("staffcodeB").trim();
		SeatDao seatdao = null;
		try{
			seatdao = new SeatDaoImpl();
			
			SeatList userBList = seatdao.queryStaffcodeB(staffcodeB);
			seatdao = new SeatDaoImpl();
			//查询所有的PA staffcode
			List<String> PAList = seatdao.getPAList();			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userBList", userBList);
			map.put("PAList", PAList);
			result = Util.getMsgJosnSuccessReturn(map);
		
		} catch (Exception e) {
			result = Util.joinException(e);
		}
		return result;
	}
	
	public String queryStaffcodeA(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		String staffcodeA = request.getParameter("staffcodeA").trim();
		SeatDao seatdao = null;
		try{
			seatdao = new SeatDaoImpl();
			SeatList userBList = seatdao.queryStaffcodeB(staffcodeA);
/*			JSONObject json = JSONObject.fromObject(userBList);
			out.print(json);*/
			seatdao = new SeatDaoImpl();
			//查询所有的PA staffcode
			List<String> PAList = seatdao.getPAList();			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userBList", userBList);
			map.put("PAList", PAList);
			result = Util.getMsgJosnSuccessReturn(map);
		} catch (Exception e) {
			result = Util.joinException(e);
		} 
		return result;
	}
	
	
	public void selectChangeApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String refno = request.getParameter("refno");
		String status = request.getParameter("status");
		String staffcodea = request.getParameter("staffcodea");
		String seatnoa = request.getParameter("seatnoa");
		String staffcodeb = request.getParameter("staffcodeb");
		String seatnob = request.getParameter("seatnob");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findSeatChangeApplyList(null, page,Util.objIsNULL(startdate)?"1900-01-01":startdate,
											  Util.objIsNULL(enddate)?"2099-12-31":enddate,
											  Util.objIsNULL(refno)?"%%":refno,
											  Util.objIsNULL(staffcodea)?"%%":staffcodea,
											  Util.objIsNULL(seatnoa)?"%%":seatnoa,
											  Util.objIsNULL(staffcodeb)?"%%":staffcodeb,
											  Util.objIsNULL(seatnob)?"%%":seatnob,
											  Util.objIsNULL(status)?"%%":status);
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
	public void selectCleanOperation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String staffcode = request.getParameter("staffcode");
		String seatno = request.getParameter("seatno");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findCleanOperationList(null, page,Util.objIsNULL(startdate)?"1900-01-01":startdate,
					Util.objIsNULL(enddate)?"2099-12-31":enddate,
							Util.objIsNULL(staffcode)?"%%":staffcode,
							Util.objIsNULL(seatno)?"%%":seatno);
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
	public void selectChangeApply_general(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String refno = request.getParameter("refno");
		String name=(String)request.getSession().getAttribute("adminUsername");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findSeatChangeApplyListByStaffcode(null,page,
					Util.objIsNULL(startdate)?"1900-01-01":startdate,
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
	public void selectChangeApply_leader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String refno = request.getParameter("refno");
		String name=(String)request.getSession().getAttribute("adminUsername");
		PrintWriter out = response.getWriter();
		SeatDao seatdao = new SeatDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=seatdao.findSeatChangeApplyListByLeadercode(null,page,
					        Util.objIsNULL(startdate)?"1900-01-01":startdate,
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
	
	
	public void SeatDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String seatno = request.getParameter("seatno");
			request.setAttribute("seatno", seatno);
			request.getRequestDispatcher("seat/SeatMenu_clean.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
