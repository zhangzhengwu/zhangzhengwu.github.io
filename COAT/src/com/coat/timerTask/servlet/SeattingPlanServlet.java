package com.coat.timerTask.servlet;

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

import util.Pager;
import util.Util;

import com.coat.pickuprecord.servlet.PickUpRecordServlet;
import com.coat.timerTask.dao.SeattingPlanDao;
import com.coat.timerTask.dao.impl.SeattingPlanDaoImpl;

public class SeattingPlanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(PickUpRecordServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}

	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		PrintWriter out = response.getWriter();
		String result="";
		try{
			user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equalsIgnoreCase("select")){
				result=querySeattindPlan(request, response);
			}else{
				throw new Exception("Unauthorized access!");
			}
		}catch (NullPointerException e) {
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
	
	public String querySeattindPlan(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String result="";
		SeattingPlanDao dao=new SeattingPlanDaoImpl();
		
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String imgFile=request.getParameter("imgFile");
		String dataFile=request.getParameter("dataFile");
		
		try {
			Pager page=Util.pageInfo(request);
			page=dao.querySeattindPlan(null, page, Util.modifyString(imgFile),
					Util.modifyString(dataFile),Util.modifystartdate(startDate),Util.modifyenddate(endDate));
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());//总条数
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
