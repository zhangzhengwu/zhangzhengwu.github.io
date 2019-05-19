package com.coat.chickenbox.servlet;

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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.coat.chickenbox.dao.ChickenBoxDao;
import com.coat.chickenbox.dao.impl.ChickenBoxDaoImpl;
import com.coat.pickuprecord.dao.PickUpRecordDao;
import com.coat.pickuprecord.dao.impl.PickUpRecordDaoImpl;

import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Pager;
import util.Util;


public class ChickenBoxServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ChickenBoxServlet.class);
	
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
			user=(String) request.getSession().getAttribute("adminUsername");
			if(method.equalsIgnoreCase("select")){
				result=queryChicken_BoxList(request, response);
			}else if(method.equalsIgnoreCase("down")){
				downChickenBox(request,response);
			}else if(method.equalsIgnoreCase("saveQueryRecord")){
				saveQueryRecord(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}
		}catch (NullPointerException e) {
			log.error("Chicken Box==>"+method+"操作异常：空值=="+e);
			result=Util.joinException(e);
		}catch (Exception e) {
			log.error("Chicken Box==>"+method+"操作异常："+e);
			result=Util.joinException(e);
		} finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	
	}

	String queryChicken_BoxList(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html;charset=utf-8");
		String staffcode = request.getParameter("staffcode");
		String fullname =request.getParameter("fullname");
		String othersname=request.getParameter("othersname");
		String d_Recruite=request.getParameter("d_Recruite");
		
		ChickenBoxDao dao=new ChickenBoxDaoImpl();
		String result="";
		try{
			Pager page=Util.pageInfo(request);
			page=dao.queryChicken_BoxList(null, page, Util.modifyString(staffcode),
					Util.modifyString(fullname),Util.modifyString(othersname),Util.modifyString(d_Recruite));
			List<Object> list=new ArrayList<Object>();
			
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			list.add(4,page.getPagesize());
			JSONArray jsons=JSONArray.fromObject(list);
			result=jsons.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return result;
	}
	
	public void downChickenBox(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String staffcode = request.getParameter("staffcode");
		String fullname =request.getParameter("fullname");
		String othersname=request.getParameter("othersname");
		String d_Recruite=request.getParameter("d_Recruite");
		
		ChickenBoxDao dao=new ChickenBoxDaoImpl();
		try{
			List<Map<String,Object>> list=dao.downChickenBox(Util.modifyString(staffcode),
					Util.modifyString(fullname),Util.modifyString(othersname),Util.modifyString(d_Recruite));
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0,"Code");
			map.put(1,"Other Name");
			map.put(2,"Full Name1");
			map.put(3,"Full Name2");
			map.put(4,"Location");
			map.put(5,"Box No.");
			map.put(6,"Extension");
			map.put(7,"Recruiter");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					Map<String,Object> rows = list.get(i);
			        p.put("var1",rows.get("staffcode")+"");
			        p.put("var2",rows.get("othername")+"");
			        p.put("var3",rows.get("fullname1")+"");
			        p.put("var4",rows.get("fullname2")+"");
			        p.put("var5",rows.get("location")+"");
			        p.put("var6",rows.get("boxNo")+"");
			        p.put("var7",rows.get("extensionno")+"");
			        p.put("var8",rows.get("RecruiterId")+""); 
					data.add(p);
				}
			}
			model.put("varList", data);
			String filename ="Chicken Box_"+DateUtils.Ordercode()+".xls";
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,filename,"Chicken Box");
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Pick Up-Export Exception :"+e.getMessage());
		}finally{
			dao=null;
		}
	}
	
	public void saveQueryRecord(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String staffcode = request.getParameter("staffcode");
		String fullname =request.getParameter("fullname");
		String othersname=request.getParameter("othersname");
		String d_Recruite=request.getParameter("d_Recruite");
		
		String dataRole= request.getParameter("dataRole");
		ChickenBoxDao dao=new ChickenBoxDaoImpl();
		try{
			int a=dao.saveQueryRecord(dataRole,staffcode,fullname,othersname,d_Recruite,user);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Pick Up-Save Exception :"+e.getMessage());
		}finally{
			dao=null;
		}
	}
	
}
