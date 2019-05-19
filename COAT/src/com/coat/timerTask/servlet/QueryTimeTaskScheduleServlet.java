package com.coat.timerTask.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Page;
import util.Util;

import com.coat.timerTask.dao.TimeTaskDao;
import com.coat.timerTask.dao.impl.TimeTaskDaoImpl;
import com.coat.timerTask.entity.STaskLog;
import com.coat.timerTask.entity.STaskSchedule;

public class QueryTimeTaskScheduleServlet extends HttpServlet {


	private static final long serialVersionUID = -2795187360666827633L;
	Logger log=Logger.getLogger(QueryTimeTaskScheduleServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String method = request.getParameter("method");
		String result="";
		try{
			if(method.equalsIgnoreCase("select")){
			    result=select(request,response);
			}else if(method.equalsIgnoreCase("addTimeTask")){
				result=addTimeTask(request,response);
			}else if(method.equalsIgnoreCase("modifyTimeTask")){
				result=modifyTimeTask(request,response);
			}else if(method.equalsIgnoreCase("selectHisRecord")){
				result=selectHisRecord(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
		}
		catch (NullPointerException e) {
			log.error("Seatting Plan==>"+method+"操作异常：空值=="+e);
			result=Util.joinException(e);
		}catch (Exception e) {
			log.error("Seatting Plan==>"+method+"操作异常："+e);
			result=Util.joinException(e);
		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
		
	}

	private String addTimeTask(HttpServletRequest request,HttpServletResponse response) {
		 String result="";
		 try {
			 TimeTaskDao timeTaskDao = new TimeTaskDaoImpl();
			 STaskSchedule sts = new STaskSchedule();
			 String taskName = request.getParameter("taskName");
			 int ts = timeTaskDao.getSameTaskName(taskName);
			 if(ts<0){
				 sts.setTaskName(taskName);
			 }else{
				 return result=Util.getMsgJosnObject("error","定时任务名称重复！");
			 }
			 sts.setExecuteTime(request.getParameter("executeTime"));
			 sts.setExecuteScript(request.getParameter("executeScript"));
			 sts.setExplain(request.getParameter("explain"));
			 sts.setRemark(request.getParameter("remark"));
			 sts.setCreator(request.getSession().getAttribute("adminUsername").toString());//系统登陆人
			 sts.setCreateDate(DateUtils.getNowDateTime());
			 int num=timeTaskDao.save(sts);
			 result=Util.getMsgJonfornum(num);
		} catch (Exception e) {
			result=Util.joinException(e);
		} finally {
		}
		return result;
	}
	private String modifyTimeTask(HttpServletRequest request,HttpServletResponse response) {
		String result="";
		try {
			STaskSchedule sts = new STaskSchedule();
			sts.setId(Integer.parseInt(request.getParameter("id")));
			sts.setTaskName(request.getParameter("taskName"));
			sts.setExecuteTime(request.getParameter("executeTime"));
			sts.setExecuteScript(request.getParameter("executeScript"));
			sts.setExplain(request.getParameter("explain"));
			sts.setStatus(Integer.parseInt(request.getParameter("status")));
			sts.setRemark(request.getParameter("remark"));
			TimeTaskDao timeTaskDao = new TimeTaskDaoImpl();
			int num=timeTaskDao.update(sts);
			result=Util.getMsgJonfornum(num);
		} catch (Exception e) {
			result=Util.joinException(e);
		} finally {
		}
		return result;
	}

	private String select(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Page page = new Page();
		String taskName = request.getParameter("taskName");
		String status = request.getParameter("status");
		String executeTime = request.getParameter("executeTime");
		List<STaskSchedule> scheduleList = new ArrayList<STaskSchedule>();
		TimeTaskDao timeTaskDao = new TimeTaskDaoImpl();
		String result="";
		try {
			page.setAllRows(timeTaskDao.getRows(taskName,status,executeTime));//设置总行数
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pagenow")));//获取页面当前页
			scheduleList = timeTaskDao.queryTimeTaskSchedule(taskName, status, executeTime, page);
			List<Object> list=new ArrayList<Object>();
			list.add(0,scheduleList);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
		//	JSONArray json = JSONArray.fromObject(list);
			result=Util.getMsgJosnSuccessReturn(list);
		//	out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("在QueryTimeTaskScheduleServlet中对s_task_schedule进行查询时出现"+ e.getMessage());
		} finally {
		}
		
		return result;
	}
	private String selectHisRecord(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Page page = new Page();
		String taskName = request.getParameter("taskName");
		List<STaskLog> sTaskLogList = new ArrayList<STaskLog>();
		TimeTaskDao timeTaskDao = new TimeTaskDaoImpl();
		String result="";
		try {
			page.setAllRows(timeTaskDao.getRows(taskName));//设置总行数
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pagenow")));//获取页面当前页
			sTaskLogList = timeTaskDao.queryTimeTaskLog(taskName, page);
			List<Object> list=new ArrayList<Object>();
			list.add(0,sTaskLogList);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			//	JSONArray json = JSONArray.fromObject(list);
			result=Util.getMsgJosnSuccessReturn(list);
			//	out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("在QueryTimeTaskScheduleServlet中对s_task_log进行查询时出现"+ e.getMessage());
		} finally {
		}
		
		return result;
	}
	
	
	
}
