package com.coat.namecard.servlet;

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

import com.coat.namecard.dao.NameCardDao;
import com.coat.namecard.dao.NameCardPayerDao;
import com.coat.namecard.dao.impl.NameCardDaoImpl;
import com.coat.namecard.dao.impl.NameCardPayerDaoImpl;
import com.coat.namecard.entity.Consultant_Master;


import util.Pager;
import util.Util;

public class NameCardReaderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(NameCardReaderServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		PrintWriter out = response.getWriter();
		String result="";
		try{
			user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equalsIgnoreCase("selectnamecard")){
				result=selectnamecard(request, response);
			}else if(method.equalsIgnoreCase("findbystaffcode")){
				result=findbystaffcode(request, response);
			}else if(method.equalsIgnoreCase("finduserUsage")){
				result=finduserUsage(request, response);
			}else{
				throw new Exception("Unauthorized access!");
			}
			
		}catch (NullPointerException e) {
			log.error("Name Card==>"+method+"操作异常：空值=="+e);
			result=Util.joinException(e);
		}catch (Exception e) {
			log.error("Name Card==>"+method+"操作异常："+e);
			result=Util.joinException(e);

		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	}
	
	
	
	
	/**
	 * 查询顾问名片办理用量
	 * @author kingxu
	 * @date 2015-9-28
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String finduserUsage(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";
		try{
			 String staffcode=request.getParameter("code");
			NameCardPayerDao payer=new NameCardPayerDaoImpl();
			Map<String,Object> map=payer.nameCardMarquee(staffcode);//获取名片用量
			double basenum=Double.parseDouble((Util.objIsNULL(map.get("eqnum"))?0:map.get("eqnum"))+"");
			double addnum=Double.parseDouble( (Util.objIsNULL(map.get("addnum"))?0:map.get("addnum"))+"");
			double used=Double.parseDouble((Util.objIsNULL(map.get("used"))?0:map.get("used"))+"");
			double selfpay=Double.parseDouble((Util.objIsNULL(map.get("selfpay"))?0:map.get("selfpay"))+"");
			result=map.get("employeeId")+",The quota on this year:"+basenum+" ; Extra quota is:"+addnum+" ; remaining amount is <font style='color:red;'>"+(basenum+addnum-(used-selfpay))+"</font>";
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	
	/**
	 * 查询NameCard记录
	 * @author kingxu
	 * @date 2015-9-28
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String selectnamecard(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String result="";
		try{
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String location  = request.getParameter("location");
			String urgentCase  = request.getParameter("urgentCase");
			String nocode  = request.getParameter("nocode");
			String name  = request.getParameter("name");
			String code  = request.getParameter("code");
			String payer  = request.getParameter("payer");
			String ET_select  = request.getParameter("ET");
			String layout_select  = request.getParameter("layout_select");
			Pager page=Util.pageInfo(request);
			NameCardDao namecardDao=new NameCardDaoImpl();
			page=namecardDao.findPager(null, page, ET_select,nocode, Util.objIsNULL(startDate)?"1900-01-01":startDate,Util.objIsNULL(endDate)?"2099-12-31":endDate,Util.objIsNULL(code)?"%%":code,Util.objIsNULL(payer)?"%%":payer,Util.objIsNULL(name)?"%%":name,Util.objIsNULL(location)?"%%":location,Util.objIsNULL(urgentCase)?"%%":urgentCase,Util.objIsNULL(layout_select)?"%%":layout_select);
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	String findbystaffcode(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String StaffNo = request.getParameter("StaffNo");
		String eip=request.getParameter("eip");
		String result="";
		try{
			Consultant_Master cMaster = null;
			NameCardDao namecardDao=new NameCardDaoImpl();
			cMaster=namecardDao.findbystaffcode(StaffNo);
			JSONObject json=JSONObject.fromObject(cMaster);
			result=json.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
