package com.coat.namecard.servlet;

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

import com.coat.namecard.dao.NameCardConvoyDao;
import com.coat.namecard.dao.impl.NameCardConvoyDaoImpl;
import com.coat.namecard.entity.NameCardConvoyDetial;

import util.Pager;
import util.Util;

public class NameCardConvoyReaderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(NameCardConvoyReaderServlet.class);
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
			if(method.equalsIgnoreCase("selectnamecardbyconsultant")){
				result=selectnamecardbyconsultant(request, response);
			}else if(method.equalsIgnoreCase("selectnamecardbyapprove")){
				result=selectnamecardbyapprove(request, response);
			}else if(method.equalsIgnoreCase("selectState")){
				result=selectState(request, response);
			}else if(method.equalsIgnoreCase("detail")){
				result=NameCardDetail(request, response);
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
	 * 查询订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	String selectState(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode");
		String staffrefno = request.getParameter("staffrefno");
		//StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		NameCardConvoyDao namecardDao=new NameCardConvoyDaoImpl();
		String result="";
		try{
			Pager page=Util.pageInfo(request);
			page=namecardDao.queryStateDetail(null, page, Util.objIsNULL(staffrefno)?"%%":"%"+staffrefno+"%",
					Util.objIsNULL(staffcode)?"%%":"%"+staffcode+"%");
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
	
	/**
	 * SZO Approve 页面查询
	 * @author kingxu
	 * @date 2015-10-13
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String selectnamecardbyapprove(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//页面传值数据
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String location  = request.getParameter("location");
				String urgentCase  = request.getParameter("urgentCase");
				String code  = request.getParameter("code");
				String shzt  = request.getParameter("shzt");
				String name=request.getParameter("name");
				String result="";
				try{
						Pager page=Util.pageInfo(request);
						NameCardConvoyDao namecardDao=new NameCardConvoyDaoImpl();
						page=namecardDao.findPager(new String[]{"c.*"}, page, Util.modifyString(code),Util.modifystartdate(startDate),Util.modifyenddate(endDate),Util.modifyString(location),Util.modifyString(urgentCase),Util.modifyString(shzt),Util.modifyString(name));
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
	
	/**
	 * 顾问页面查询方法
	 * @author kingxu
	 * @date 2015-10-10
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @return String
	 */
	String selectnamecardbyconsultant(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//页面传值数据
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String location  = request.getParameter("location");
				String urgentCase  = request.getParameter("urgentCase");
				String code  = user;
				String shzt  = request.getParameter("shzt");
				String result="";
				try{
						Pager page=Util.pageInfo(request);
						NameCardConvoyDao namecardDao=new NameCardConvoyDaoImpl();
						page=namecardDao.findPager(new String[]{"c.*"}, page, code,Util.modifystartdate(startDate),Util.modifyenddate(endDate),Util.modifyString(location),Util.modifyString(urgentCase),Util.modifyString(shzt),"%%");
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
	
	String NameCardDetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result="";
		NameCardConvoyDao namecardDao=new NameCardConvoyDaoImpl();
		List<NameCardConvoyDetial> list=new ArrayList<NameCardConvoyDetial>();
		try{
			String refno=request.getParameter("staffrefno");
			list=namecardDao.findNameCardDetail(refno);
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
			jsons=null;
		}catch(Exception e){
			//e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			namecardDao=null;
			list=null;
		}
		return result;
	}

}
