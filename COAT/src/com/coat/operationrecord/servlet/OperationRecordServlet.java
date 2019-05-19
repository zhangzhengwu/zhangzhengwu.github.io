package com.coat.operationrecord.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.Pager;
import util.Util;

import com.coat.loginrecord.dao.LoginRecordDao;
import com.coat.loginrecord.dao.impl.LoginRecordDaoImpl;
import com.coat.operationrecord.dao.OperationRecordDao;
import com.coat.operationrecord.dao.impl.OperationRecordDaoImpl;

/**
 * 用户访问地址记录  servlet
 * 2015-09-07
 * @author orlandozhang
 *
 */
public class OperationRecordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(OperationRecordServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			if(method.equalsIgnoreCase("select")){
				select(request,response);
			}else if("usage".equalsIgnoreCase(method)){
				result=usage(request,response);
			}else if("queryLogReport_Year".equalsIgnoreCase(method)){
				result=queryLogReport_Year(request,response);
			}else if("queryLogReport_Month".equalsIgnoreCase(method)){
				result=queryLogReport_Month(request,response);
			}else if("queryLogReport_Day".equalsIgnoreCase(method)){
				result=queryLogReport_Day(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
			
		} catch (NullPointerException e) {
			log.error("AccessCard==>"+method+"操作异常：空值=="+e);
			result=Util.getMsgJosnObject("exception", "Submit data anomalies, please refresh retry!");
		} catch (Exception e) {
			log.error("AccessCard==>"+method+"操作异常："+e);
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
	
    void select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate  = request.getParameter("endDate");
		String username  = request.getParameter("username");
		String modular  = request.getParameter("modular");
		String operation  = request.getParameter("operation");
		String status  = request.getParameter("status");
		PrintWriter out = response.getWriter();
		OperationRecordDao operationRecordDao=new OperationRecordDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=operationRecordDao.findPager(null, page,Util.objIsNULL(startDate)?"1900-01-01":startDate,Util.objIsNULL(endDate)?"2099-12-31":endDate,Util.objIsNULL(username)?"%%":username,Util.objIsNULL(modular)?"%%":modular,Util.objIsNULL(operation)?"%%":operation,Util.objIsNULL(status)?"%%":status);
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		}
    }
    
    String usage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String result="";
		try{
			String username=""+(request.getSession().getAttribute("adminUsername"));
			OperationRecordDao operationRecordDao=new OperationRecordDaoImpl();
			List<Object[]> list=operationRecordDao.findUsageByusername30day(username);
			for(int i=0;i<list.size();i++){
				Object[] object=list.get(i);
				if((""+object[0]).indexOf("Emap/")>-1){
					object[0]=(object[0]+"").substring((object[0]+"").indexOf("Emap/")+5);
				}else if((""+object[0]).indexOf("Emap//")>-1){
					object[0]=(object[0]+"").substring((object[0]+"").indexOf("Emap//")+6);
				}else if((""+object[0]).indexOf("Admin/")>-1){
					object[0]=(object[0]+"").substring((object[0]+"").indexOf("Admin/")+6);
				}else if((""+object[0]).indexOf("Admin//")>-1){
					object[0]=(object[0]+"").substring((object[0]+"").indexOf("Admin//")+7);
				}
			}
			result=JSONArray.fromObject(list).toString();
			result=Util.getMsgJosnObject("success", result);
		}catch (Exception e) {
			throw e;
		} 
		return result;
    }
    
    
    String queryLogReport_Year(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result="";
    	String start_Y=request.getParameter("start_Y");
    	String end_Y=request.getParameter("end_Y");
    	try{
    		OperationRecordDao operationRecordDao=new OperationRecordDaoImpl();
    		Double[] num=operationRecordDao.queryLogReport_Year(start_Y,end_Y);
    		result=JSONArray.fromObject(num).toString();
    		result=Util.getMsgJosnObject("success",result);
    	}catch (Exception e) {
    		throw e;
    	}
    	return result; 
    }
    
    
    String queryLogReport_Month(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result="";
    	String start_M=request.getParameter("start_M");
    	String end_M=request.getParameter("end_M");
    	try{
    		//String a[]=isYM.split("-");
    		OperationRecordDao operationRecordDao=new OperationRecordDaoImpl();
    		Double[] num=operationRecordDao.queryLogReport_Month(start_M,end_M);
    		result=JSONArray.fromObject(num).toString();
    		result=Util.getMsgJosnObject("success",result);
    	}catch (Exception e) {
    		throw e;
    	}
    	return result; 
    }
    
    String queryLogReport_Day(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result="";
    	String startDate=request.getParameter("startDate");
    	String endDate=request.getParameter("endDate");
    	try{
    		//String a[]=isYMD.split("-");
    		OperationRecordDao operationRecordDao=new OperationRecordDaoImpl();
    		List<Map<String,Object>> ListObj=operationRecordDao.queryLogReport_Day(startDate,endDate);
			JSONObject json=new JSONObject();
			json.put("state", "success");
			json.put("msg", ListObj);
			result=json.toString();
    	}catch (Exception e) {
    		throw e;
    	}
    	return result; 
    }
    
    
    
    
    
}
