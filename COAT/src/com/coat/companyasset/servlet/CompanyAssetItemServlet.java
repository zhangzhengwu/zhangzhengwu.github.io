package com.coat.companyasset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.coat.companyasset.dao.CompanyAssetItemDao;
import com.coat.companyasset.dao.impl.CompanyAssetItemDaoImpl;
import com.coat.companyasset.entity.C_CompanyAsset_Item;

import net.sf.json.JSONArray;
import util.DateUtils;
import util.Pager;
import util.Util;

public class CompanyAssetItemServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(CompanyAssetItemServlet.class);
	String user = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String result="";
		try {
			user=(String) request.getSession().getAttribute("adminUsername");
			if (method.equals("select")) {
				select(request, response);
			}else if(method.equals("selectItemNo")){
				result = selectItemNo(request,response);
			}else if (method.equals("add")){
				result = add(request, response);	
			}else if (method.equals("openUpdateCompanyAsset")){
				openUpdateCompanyAsset(request, response);	
			}else if (method.equals("updateCompanyAsset")){
				result = updateCompanyAsset(request, response);	
			}else if (method.equals("delete")){
				result = delete(request, response);	
			}else{
				throw new Exception("Unauthorized access!");
			}	
			
		} catch (NullPointerException e) {
			result=Util.joinException(e);
			log.error("CompanyAssetItemServlet==>" + method + "操作异常：空值==" + e);
		} catch (Exception e) {
			log.error("CompanyAssetItemServlet==>" + method + "操作异常：" + e);
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
	
	public void select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String itemcode = request.getParameter("itemcode").trim();
		String itemname = request.getParameter("itemname").trim();
		String sfyx = request.getParameter("sfyx");
		PrintWriter out = response.getWriter();
		CompanyAssetItemDao companyAssetItemDao = new CompanyAssetItemDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=companyAssetItemDao.findPager(null, page,
											  Util.objIsNULL(itemcode)?"%%":itemcode,
											  Util.objIsNULL(itemname)?"%%":itemname,
											  Util.objIsNULL(sfyx)?"%%":sfyx);
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
	
	public String selectItemNo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";
		CompanyAssetItemDao companyAssetItemDao = new CompanyAssetItemDaoImpl();
		try {
			String itemcode = request.getParameter("itemcode");
			int num =companyAssetItemDao.existItemCode(itemcode);
			if(num > 0){
				result=Util.getMsgJosnErrorReturn("The itemcode is exists！");
			}else{
				result=Util.getMsgJosnObject_success();	
			}
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("查询CompanyAssetList中itemcode是否已存在 失败"+e.toString());
		}	
		return result;
	}
	
	public String add(HttpServletRequest request,HttpServletResponse response)  {
		String result="";		
		int num = -1;
		String name=(String)request.getSession().getAttribute("adminUsername");
		CompanyAssetItemDao companyAssetItemDao = new CompanyAssetItemDaoImpl();
		try {
			String itemcode = request.getParameter("itemcode");
			String itemname = request.getParameter("itemname");
			String itemnum = Util.objIsNULL(request.getParameter("itemnum"))? 0+"" : request.getParameter("itemnum");
			C_CompanyAsset_Item companyAssetItem = new C_CompanyAsset_Item(
					itemcode,
					itemname,
					Integer.parseInt(itemnum),
					Integer.parseInt(itemnum),
					name,
					DateUtils.getNowDateTime(),
					"Y"
					);
			num = companyAssetItemDao.save(companyAssetItem);
			if(num < 1){
				throw new RuntimeException("保存数据至CompanyAssetItem失败");
			}else{
				result = Util.getMsgJosnObject_success();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void openUpdateCompanyAsset(HttpServletRequest request,HttpServletResponse response) throws IOException{
		CompanyAssetItemDao companyAssetItemDao = new CompanyAssetItemDaoImpl();
		try {
			String itemId = request.getParameter("itemId");
			C_CompanyAsset_Item companyAssetItem = companyAssetItemDao.findById(Integer.parseInt(itemId));
			request.setAttribute("companyAssetItem", companyAssetItem);
			request.getRequestDispatcher("page/companyasset/companyasset_update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String updateCompanyAsset(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";		
		int num = -1;
		CompanyAssetItemDao companyAssetItemDao = new CompanyAssetItemDaoImpl();
		try {
			String itemId = request.getParameter("itemId");
			String itemname = request.getParameter("itemname");
			String itemnum = request.getParameter("itemnum");
			String remainnum = request.getParameter("remainnum");
			num = companyAssetItemDao.update(Integer.parseInt(itemId),itemname,Integer.parseInt(itemnum),Integer.parseInt(remainnum));
			if(num < 0){
				throw new RuntimeException("update companyasset failed...");
			}else{
				result = Util.getMsgJosnObject_success();
			}
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("update companyasset failed："+e.toString());
		}
		return result;
	}	
	
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		int num = -1;
		CompanyAssetItemDao companyAssetItemDao = new CompanyAssetItemDaoImpl();
		try {
			String itemId = request.getParameter("itemId");
			num = companyAssetItemDao.delete(Integer.parseInt(itemId));
			if(num < 1){
				throw new RuntimeException("delete company asset failed...");
			}
			result = Util.getMsgJosnSuccessReturn(num);
		}catch (Exception e) {
			result = Util.joinException(e);
		}
		return result;
	}
	
	
}
