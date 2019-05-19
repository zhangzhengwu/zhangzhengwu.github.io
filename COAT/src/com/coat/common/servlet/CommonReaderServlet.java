package com.coat.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.Util;

import com.coat.attendance.servlet.AttendanceWriteServlet;
import com.coat.common.dao.LocationDao;
import com.coat.common.dao.PositionDao;
import com.coat.common.dao.ProfessionalDao;
import com.coat.common.dao.impl.LocationDaoImpl;
import com.coat.common.dao.impl.PositionDaoImpl;
import com.coat.common.dao.impl.ProfessionalDaoImpl;
import com.coat.namecard.dao.NameCardDao;
import com.coat.namecard.dao.impl.NameCardDaoImpl;

import entity.LicensePlate;
import entity.RequestStaffBean;

public class CommonReaderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AttendanceWriteServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<span style='position:fixed;top:50%;left:40%;font-size:24px;'>You don't have the permission!</span>");
		out.flush();
		out.close();
	}
	String user=null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method")+"";
		PrintWriter out = response.getWriter();
		String result="";
		try{
			user=Util.objIsNULL(request.getSession().getAttribute("adminUsername"))?(request.getSession().getAttribute("convoy_username")+""):(request.getSession().getAttribute("adminUsername"))+"";//request.getSession().getAttribute("convoy_username").toString();
			if(Util.objIsNULL(user)){
				new RuntimeException("Identity information is missing");
			}else if("getlocation".equals(method)){
				result=getlocation(request,response);
			}else if("getmacaulocation".equals(method)){
				result=getmacaulocation(request,response);
			}else if("getposition".equals(method)){
				result=getposition(request,response);
			}else if("getprofessional".equals(method)){
				result=getProfessional(request,response);
			}else if("getlicenseplate".equals(method)){
				getlicenseplate(request,response);
			}else if("getLocation".equals(method)){
				getLocation(request,response);
			}else if("getstaffprofessional".equals(method)){
				result=getstaffProfessional(request,response);
			}else if("visit".equals(method)){
				result=visit(request,response);
			}else if("visit2".equals(method)){
				visit2(request,response);
			}/*else if("getOldRecord".equals(method)){
				result=getOldRecord(request,response);
			}*/else{
				throw new Exception("Unauthorized access!");
			}
		}catch (NullPointerException e) {
			log.error(this.getClass().getName()+"==>"+method+"操作异常：空值=="+e);
			result=Util.joinException(e);
		}catch (Exception e) {
			log.error(this.getClass().getName()+"==>"+method+"操作异常："+e);
			result=Util.joinException(e);
		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	}
	
	String visit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return request.getSession().getAttribute("UseracceTime")+"";
	}
	
	void visit2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("UseracceTime",295);
		
	}
	
	
	public void getlicenseplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode").trim();
		PrintWriter out = response.getWriter();
		NameCardDao namecardDao=new NameCardDaoImpl();
		
		try{
			LicensePlate licensePlate = namecardDao.getHKCIB(staffcode);
			JSONObject json = JSONObject.fromObject(licensePlate);
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	public void getLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode").trim();
		PrintWriter out = response.getWriter();
		NameCardDao namecardDao=new NameCardDaoImpl();
		try{
			String location = namecardDao.getLocation(staffcode);
			out.print(location);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 获取position list
	 * @author kingxu
	 * @date 2015-11-2
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	String getlocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			HttpSession session=request.getSession();
			JSONArray json=null;
			if(Util.objIsNULL(session.getAttribute("locationlist"))){
				LocationDao location=new LocationDaoImpl();
				List<Map<String,Object>> list=location.getlocation();
				 json=JSONArray.fromObject(list);
				 session.setAttribute("locationlist",list);
			}else{
				//Util.printLogger(log, "locationlist--来源于缓存");
				 json=JSONArray.fromObject(session.getAttribute("locationlist"));
			}
			result=json.toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
		
	}
	String getposition(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			HttpSession session=request.getSession();
			JSONArray json=null;
			if(Util.objIsNULL(session.getAttribute("positionlist"))){
				PositionDao position =new PositionDaoImpl();
				List<Map<String,Object>> list=position.getposition();
				json=JSONArray.fromObject(list);
				session.setAttribute("positionlist",list);
			}else{
				//Util.printLogger(log, "getposition--来源于缓存");
				json=JSONArray.fromObject(session.getAttribute("positionlist"));
			}
			
			result=json.toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
		
	}
	/**
	 * 获取澳门location list
	 * @author kingxu
	 * @date 2015-11-2
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String getmacaulocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			HttpSession session=request.getSession();
			JSONArray json=null;
			if(Util.objIsNULL(session.getAttribute("macaulocationlist"))){
				LocationDao location=new LocationDaoImpl();
				List<Map<String,Object>> list=location.getmacaulocation();
				json=JSONArray.fromObject(list);
				session.setAttribute("macaulocationlist",list);
			}else{
				//Util.printLogger(log, "macaulocationlist--来源于缓存");
				json=JSONArray.fromObject(session.getAttribute("macaulocationlist"));
			}
			
			result=json.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
		
	}
	/**
	 * 获取consultant professional list
	 * @author kingxu
	 * @date 2015-11-3
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String getProfessional(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			HttpSession session=request.getSession();
			JSONArray json=null;
			if(Util.objIsNULL(session.getAttribute("professionallist"))){
				ProfessionalDao professional=new ProfessionalDaoImpl();
				List<Map<String,Object>> list=professional.getProfessional();
				json=JSONArray.fromObject(list);
				session.setAttribute("professionallist",list);
			}else{
				//Util.printLogger(log, "professionallist--来源于缓存");
				json=JSONArray.fromObject(session.getAttribute("professionallist"));
			}
			
			result=json.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
		
	}
	String getstaffProfessional(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			HttpSession session=request.getSession();
			JSONArray json=null;
			if(Util.objIsNULL(session.getAttribute("staffprofessionallist"))){
				ProfessionalDao professional=new ProfessionalDaoImpl();
				List<Map<String,Object>> list=professional.getStaffProfessional();
				json=JSONArray.fromObject(list);
				session.setAttribute("staffprofessionallist",list);
			}else{
				//Util.printLogger(log, "staffprofessionallist--来源于缓存");
				json=JSONArray.fromObject(session.getAttribute("staffprofessionallist"));
			}
			
			result=json.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
		
	}
	/**
	 * 查找上一次的申请记录
	 * @return
	 */
	String getOldRecord(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String result="";
		String code=request.getParameter("staffcode");
		List<RequestStaffBean> list=new ArrayList<RequestStaffBean>();
		try{
			NameCardDao namecardDao=new NameCardDaoImpl();
			list=namecardDao.getOldRecord(code);
			JSONArray json=JSONArray.fromObject(list);
			result=json.toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
		}
	}
