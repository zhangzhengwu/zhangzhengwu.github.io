package com.coat.attendance.servlet;
import java.io.File;
import java.io.FileNotFoundException;
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

import com.coat.attendance.dao.AttendanceDao;
import com.coat.attendance.dao.impl.AttendanceDaoImpl;

import util.ReadExcel;
import util.Util;

public class AttendanceWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AttendanceWriteServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<span style='position:fixed;top:50%;left:40%;font-size:24px;'>You don't have the permission!</span>");
	}
	String user=null;
	List<Map<String,String>> resultList=null;//文件结果列表
	boolean completed=false;//判断是否已经上传完毕
	

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
			}else if(("getuploadStatus").equalsIgnoreCase(method)){//上传attendance基础数据
				result=getuploadStatus(request,response);
			}else if(("uploadbaseData").equalsIgnoreCase(method)){//上传attendance基础数据
				result=uploadbaseData(request,response);
			}else if(("cacularAttendance").equalsIgnoreCase(method)){//上传attendance基础数据
				result=cacularAttendance(request,response);
			}else if(("uploadAttendance").equalsIgnoreCase(method)){//上传attendance基础数据
				result=uploadAttendance(request,response);
			}else{
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
	
	public static void main(String[] args) {
		try{
			ReadExcel.readExcel(new File("ssdfg.xls"), 0, 0, 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 上传计算Attendance所需基础数据
	 * @author kingxu
	 * @date 2015-10-15
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	synchronized String uploadbaseData(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String result="";
		try{
			resultList=new ArrayList<Map<String,String>>();
			String basePath=Util.getProValue("public.attendance.file.basepath");//上传文件基本盘符路径
			String resign=request.getParameter("resign");//离职列表
			String borrow=request.getParameter("borrow");//借卡数据
			String cclub=request.getParameter("cclub");//cclub成员
			String exceptionDate=request.getParameter("exceptionDate");//假期表
			String leaveRecord=request.getParameter("leaveRecord");
			String emap=request.getParameter("emap");
			String trainingList=request.getParameter("trainingList");//培训列表
			Map<String,String> map=new HashMap<String, String>();
			AttendanceDao attendance=new AttendanceDaoImpl();
			int num=0;
			//--------------------------------------resign(离职表)-------------------------------------------------------
			try{
				num=attendance.saveResign(ReadExcel.readExcel(new File(basePath+resign), 0, 5, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("resign---"+num);
				map.put("resign", num+"");
				 resultList.add(map);
				 request.getSession().setAttribute("attendanceStatus", resultList);
			}
			//--------------------------------------borrow(借卡记录表)-------------------------------------------------------
			try{
				attendance=new AttendanceDaoImpl(); 
				num=attendance.saveBorrow(ReadExcel.readExcel(new File(basePath+borrow), 0, 3, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("borrow---"+num);
				map.put("borrow", num+"");
				 resultList.add(map);
				 request.getSession().setAttribute("attendanceStatus", resultList);
			}
			//--------------------------------------cclub(cclub 成员表)-------------------------------------------------------
			try{
				attendance=new AttendanceDaoImpl(); 
				num=attendance.savecclub(ReadExcel.readExcel(new File(basePath+cclub), 0, 1, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("cclub---"+num);
				map.put("cclub", num+"");
				 resultList.add(map);
				 request.getSession().setAttribute("attendanceStatus", resultList);
			}
			//--------------------------------------exceptionDate(假期表)-------------------------------------------------------
			try{
				attendance=new AttendanceDaoImpl(); 
				num=attendance.saveEexceptionDate(ReadExcel.readExcel(new File(basePath+exceptionDate), 0, 1, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("exceptionDate---"+num);
				map.put("exceptionDate", num+"");
				 resultList.add(map);
				 request.getSession().setAttribute("attendanceStatus", resultList);
			}
			//--------------------------------------leaveRecord(请假记录表)-------------------------------------------------------
			try{
				attendance=new AttendanceDaoImpl(); 
				num=attendance.saveleaveRecord(ReadExcel.readExcel(new File(basePath+leaveRecord), 0, 3, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("leaveRecord---"+num);
				map.put("leaveRecord", num+"");
				 resultList.add(map);
				 request.getSession().setAttribute("attendanceStatus", resultList);
			}
			//--------------------------------------emap(Emap Meeting 表)-------------------------------------------------------
			try{
				attendance=new AttendanceDaoImpl(); 
				num=attendance.saveEmap(ReadExcel.readExcel(new File(basePath+emap), 0, 2, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("emap---"+num);
				map.put("emap", num+"");
				 resultList.add(map);
				 request.getSession().setAttribute("attendanceStatus", resultList);
			}
			//--------------------------------------trainingList(培训表)-------------------------------------------------------
			try{
				attendance=new AttendanceDaoImpl(); 
				num=attendance.saveTrainingList(ReadExcel.readExcel(new File(basePath+trainingList), 0, 2, 1), user);
			}catch(FileNotFoundException e){
				num=-1;
			}catch(Exception e){
				num=-2;
			}finally{
				System.out.println("trainingList---"+num);
				map.put("trainingList", num+"");
				resultList.add(map);
				request.getSession().setAttribute("attendanceStatus", resultList);
			}
			Thread.sleep(1000);//等待获取状态方法完成
		
			result=Util.getMsgJosnObject_success();
		
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			resultList=null;
			request.getSession().setAttribute("attendanceStatus", resultList);
		}
		return result; 
		
	}
	
	/**
	 * 上传Attendance表
	 * @author kingxu
	 * @date 2015-10-16
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String uploadAttendance(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String result="";
		try{
			String attendanceList=request.getParameter("attendance");
			AttendanceDao attendance=new AttendanceDaoImpl(); 
			result=attendance.uploadAttendance(ReadExcel.readExcel(new File(attendanceList), 0,15, 7), user);
		}catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	
	
	
	
	
	/**
	 * 计算Consultant 整月打卡数据
	 * @author kingxu
	 * @date 2015-10-16
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	synchronized String cacularAttendance(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String result="";
		try{
			String basePath=Util.getProValue("public.attendance.file.basepath");//上传文件基本盘符路径
			AttendanceDao attendance=new AttendanceDaoImpl();
			String conslist=request.getParameter("conslist");
			result=attendance.cacularAttendance(basePath+conslist, user);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 获取上传计算Attendance所需基础数据进度
	 * @author kingxu
	 * @date 2015-10-15
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String getuploadStatus(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String result="";
		response.addHeader("Expires", "0");
		response.addHeader("Cache-Control","no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.addHeader("Pragma", "no-cache");
		if(!Util.objIsNULL(request.getSession().getAttribute("attendanceStatus"))){
			result=JSONArray.fromObject(request.getSession().getAttribute("attendanceStatus")).toString();
		}
		return result;
	}
	
	
	
}
