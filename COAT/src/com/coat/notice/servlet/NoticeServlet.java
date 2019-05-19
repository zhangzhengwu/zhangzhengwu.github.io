package com.coat.notice.servlet;

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

import org.apache.log4j.Logger;

import util.DateUtils;
import util.ExcelTools;
import util.Page;
import util.Pager;
import util.Util;

import cn.admin.entity.system.SGroup;

import com.coat.notice.dao.NoticeDao;
import com.coat.notice.dao.impl.NoticeDaoImpl;
import com.coat.notice.entity.Notice;
import com.coat.notice.entity.NoticeType;

import entity.Excel;
import entity.S_user;

/**
 * 
 * @author orlandozhang
 *
 */
public class NoticeServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(NoticeServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			if(method.equalsIgnoreCase("select")){
				select(request,response);
			}
			if(method.equalsIgnoreCase("selectSingle")){
				selectSingle(request,response);
			}
			if(method.equalsIgnoreCase("selectSingle_Own")){
				selectSingle_Own(request,response);
			}
			if(method.equalsIgnoreCase("selectListByType")){
				selectListByType(request,response);
			}
			if(method.equalsIgnoreCase("queryByNoticeType_Own")){
				queryByNoticeType_Own(request,response);
			}
			if(method.equalsIgnoreCase("saveNoticeDelete")){
				saveNoticeDelete(request,response);
			}
			if(method.equalsIgnoreCase("queryTypeList")){
				queryTypeList(request,response);
			}
			if(method.equalsIgnoreCase("addNotice")){
				addNotice(request,response);
			}
			if(method.equalsIgnoreCase("export")){
				export(request,response);
			}
			if(method.equalsIgnoreCase("export_own")){
				export_own(request,response);
			}
			if(method.equalsIgnoreCase("selectUser")){
				selectUser(request,response);
			}
			if(method.equalsIgnoreCase("selectGroup")){
				selectGroup(request,response);
			}
			if(method.equalsIgnoreCase("autoCompletedUser")){
				result=autoCompletedUser(request,response);
			}
			if(method.equalsIgnoreCase("autocomplete")){
				result=autocomplete(request,response);
			}
			if(method.equalsIgnoreCase("findNotice_Type")){
				findNotice_Type(request,response);
			}
			if(method.equalsIgnoreCase("new_notice")){
				new_notice(request,response);//查询最新一条未读消息
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("AccessCard==>"+method+"操作异常：空值=="+e);
			result=Util.getMsgJosn("exception", "Submit data anomalies, please refresh retry!");
		} catch (Exception e) {
			log.error("AccessCard==>"+method+"操作异常："+e);
			result=Util.joinException(e);
		} finally {
			if(!Util.objIsNULL(result)){
				response.getWriter().print(result);
				response.getWriter().flush();
				response.getWriter().close();
			}
		}
	}

	private void findNotice_Type(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String adminUsername="";
		NoticeDao noticeDao=new NoticeDaoImpl();
		try {
		   adminUsername=(String) request.getSession().getAttribute("adminUsername");
		   List<Map<String,Object>> list=noticeDao.findNotice_Type(adminUsername);
		   List<Notice> n=new ArrayList<Notice>();
		   for (int i = 0; i < list.size(); i++) {
			   n.add(new Notice(
					   Integer.parseInt(list.get(i).get("id")+""),
					   list.get(i).get("subject")+"",
					   list.get(i).get("company")+"",
					   list.get(i).get("createdate")+"",
					   list.get(i).get("creator")+"",
					   list.get(i).get("type")+""
			   ));
		   }
		   JSONArray jsons=JSONArray.fromObject(n);
		   out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
	}
	
	
	
	/**
	 * 根据输入的用户名自动补全
	 * @author kingxu
	 * @date 2016-1-27
	 * @param request
	 * @param response
	 * @return void
	 */
	private String autoCompletedUser(HttpServletRequest request, HttpServletResponse response) {
		String username=request.getParameter("user");
		String result="";
		try{
			
		}catch (Exception e) {
			
		}
		
		return result;
	}
	
    private void export(HttpServletRequest request, HttpServletResponse response) {
	  	//拿到查询条件
		String subject=request.getParameter("subject").trim();
	    String company=request.getParameter("company").trim();
	    String type=request.getParameter("type").trim();
	    String date1=request.getParameter("date1").trim();
	    String date2=request.getParameter("date2").trim();
    	NoticeDao noticeDao=new NoticeDaoImpl();
	    Excel excel=new Excel();
	    String adminUsername = "";
	    try{
	    	
		adminUsername=(String) request.getSession().getAttribute("adminUsername");	
	    //查询数据
		List<Map<String,Object>> list = noticeDao.queryByNoticeType(adminUsername, type, subject, company, date1, date2);
		//把数据交给Excel
	    excel.setExcelContentList(list);	
	    //设置Excel列头
	    excel.setColumns(new String[]{"序号","类型","主题","发件人","公司","时间"});
	    //属性字段名称
	    excel.setHeaderNames(new String[]{"id","type","subject","creator","company","startdate"});
	   //sheet名称
	    excel.setSheetname("Notice");
	    //文件名称
		excel.setFilename("Notice"+System.currentTimeMillis());
		String filename=ExcelTools.getExcelFileName(excel.getFilename());
		response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
       //生成EXCEL
		ExcelTools.createExcel2(excel,response);
	    //out.print("导出成功！");
	    }catch (Exception e) {
	    	log.error("Notice数据导出失败"+e.toString());
		    e.printStackTrace();
	    }
	}

    private void export_own(HttpServletRequest request, HttpServletResponse response) {
	  	//拿到查询条件
		String subject=request.getParameter("subject").trim();
	    String company=request.getParameter("company").trim();
	    String type=request.getParameter("type").trim();
	    String date1=request.getParameter("date1").trim();
	    String date2=request.getParameter("date2").trim();
    	NoticeDao noticeDao=new NoticeDaoImpl();
	    Excel excel=new Excel();
	    String adminUsername = "";
	    try{
	    	
		adminUsername=(String) request.getSession().getAttribute("adminUsername");	
	    //查询数据
		List<Map<String,Object>> list = noticeDao.queryByNoticeType_own(adminUsername, type, subject, company, date1, date2);
		//把数据交给Excel
	    excel.setExcelContentList(list);	
	    //设置Excel列头
	    excel.setColumns(new String[]{"序号","类型","主题","发件人","公司","时间"});
	    //属性字段名称
	    excel.setHeaderNames(new String[]{"id","type","subject","creator","company","startdate"});
	   //sheet名称
	    excel.setSheetname("Notice");
	    //文件名称
		excel.setFilename("Notice"+System.currentTimeMillis());
		String filename=ExcelTools.getExcelFileName(excel.getFilename());
		response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
       //生成EXCEL
		ExcelTools.createExcel2(excel,response);
	    //out.print("导出成功！");
	    }catch (Exception e) {
	    	log.error("Notice数据导出失败"+e.toString());
		    e.printStackTrace();
	    }
	}

    
	private void addNotice(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
    	String recipient = request.getParameter("recipient");
    	String type = request.getParameter("type");
    	String content = request.getParameter("content");
    	String attr = request.getParameter("attr");
    	String subject = request.getParameter("subject");
    	String company = request.getParameter("company");
    	String roles = request.getParameter("roles");
    	String startdate = request.getParameter("startdate");
    	String enddate = request.getParameter("enddate");
	    	if(Util.objIsNULL(enddate)){
	    		 enddate = DateUtils.addOneMonth(startdate);
	    	}
    	String createdate = DateUtils.getNowDateTime();
    	String status = "Y";
    	NoticeDao noticeDao=new NoticeDaoImpl();
    	String creator="";
    	try {
    		creator=(String) request.getSession().getAttribute("adminUsername");
   		     int num = noticeDao.saveNotice(type, subject, content, attr, recipient, roles, company, startdate, enddate, creator, createdate, status);
	   		 if(num>0){
				 log.info("发送消息save success!");
	  			 JSONArray jsons=JSONArray.fromObject(num);
	  			 out.print(jsons.toString());
			 }else{
				 log.info("发送消息save error!");
			 }
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally{
			out.flush();
		}
    	
	}

	private void queryTypeList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
    	NoticeDao noticeDao=new NoticeDaoImpl();
    	List<NoticeType> typeList = new ArrayList<NoticeType>();
		try {
			typeList = noticeDao.queryTypeList();
		    JSONArray jsons=JSONArray.fromObject(typeList);
		    out.print(jsons.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			out.flush();
		}
	}

	private void saveNoticeDelete(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
    	String noticeId = request.getParameter("noticeId");
    	NoticeDao noticeDao=new NoticeDaoImpl();
    	String adminUsername="";
    	try {
    		 adminUsername=(String) request.getSession().getAttribute("adminUsername");
    		 int num = noticeDao.saveNoticeDelete(noticeId,adminUsername);
    		 if(num>0){
    			 log.info("信息已读标签save success!");
	  			 JSONArray jsons=JSONArray.fromObject(num);
	  			 out.print(jsons.toString());
    		 }else{
    			 log.info("信息已读标签save error!");
    		 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
		}
    	
	}

	private void selectListByType(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String subject = request.getParameter("subject");
		String company = request.getParameter("company");
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String noticetype = request.getParameter("noticetype");
		String read=request.getParameter("read");
		
		String adminUsername="";
		NoticeDao noticeDao=new NoticeDaoImpl();
		try {
		   adminUsername=(String) request.getSession().getAttribute("adminUsername");
		   Pager page=Util.pageInfo(request);
		   page=noticeDao.queryByNoticeType1(adminUsername,null, page,  noticetype, date1, date2, subject, company,read);
		   List<Object> list = new ArrayList<Object>();
		   list.add(0,page.getList());//数据
		   list.add(1,page.getTotalpage());//总页数
		   list.add(2,page.getPagenow());//当前页
		   list.add(3,page.getTotal());//总行数
		   list.add(3,page.getPagesize());
		   JSONArray jsons=JSONArray.fromObject(list);
		   out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
	}
	
	private void queryByNoticeType_Own(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String subject = request.getParameter("subject");
		String company = request.getParameter("company");
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String noticetype = request.getParameter("noticetype");
		String read=request.getParameter("read");
		String adminUsername="";
		NoticeDao noticeDao=new NoticeDaoImpl();
		try {
			   adminUsername=(String) request.getSession().getAttribute("adminUsername");
			   Pager page=Util.pageInfo(request);
			   page=noticeDao.queryPickUpList_Own1(adminUsername,null, page,  noticetype, date1, date2, subject, company,read);
			   List<Object> list = new ArrayList<Object>();
			   list.add(0,page.getList());//数据
			   list.add(1,page.getTotalpage());//总页数
			   list.add(2,page.getPagenow());//当前页
			   list.add(3,page.getTotal());//总行数
			   list.add(3,page.getPagesize());
			   JSONArray jsons=JSONArray.fromObject(list);
			   out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
	}

	private void selectSingle(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		String noticeId = request.getParameter("noticeId");
		Notice nt = new Notice();
		String adminUsername="";
		try {
			   adminUsername=(String) request.getSession().getAttribute("adminUsername");
				     // 查询消息是否已读
			   		 int flg = noticeDao.queryIsRead(noticeId,adminUsername);
			   		 if(flg!=1){
				         /*// 保存已读标记 noticeid,username
				   		 int num = noticeDao.saveReadList(noticeId,adminUsername);
			    		 if(num>0){
			    			 log.info("信息已读标签save success!");
			    		 }else{
			    			 log.info("信息已读标签save error!");
			    		 }*/
			   		 }
			   nt = noticeDao.queryByNoticeId(Integer.parseInt(noticeId));
			   JSONArray jsons=JSONArray.fromObject(nt);
			   out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
		
	}

	private void selectSingle_Own(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		String noticeId = request.getParameter("noticeId");
		Notice nt = new Notice();
		String adminUsername="";
		try {
			   adminUsername=(String) request.getSession().getAttribute("adminUsername");
				     // 查询消息是否已读
			   		 int flg = noticeDao.queryIsRead(noticeId,adminUsername);
			   		 if(flg!=1){
				         // 保存已读标记 noticeid,username
				   		 int num = noticeDao.saveReadList(noticeId,adminUsername);
			    		 if(num>0){
			    			 log.info("信息已读标签save success!");
			    		 }else{
			    			 log.info("信息已读标签save error!");
			    		 }
			   		 }
			   nt = noticeDao.queryByNoticeId(Integer.parseInt(noticeId));
			   JSONArray jsons=JSONArray.fromObject(nt);
			   out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
		
	}
	
	void select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		String adminUsername="";
		try{
			adminUsername=(String) request.getSession().getAttribute("adminUsername");
			List<Notice> list=noticeDao.querybyUser(adminUsername);
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
    }
	
	void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		
		//String s=request.getParameter("inputText");
		try{
			List<S_user> list=noticeDao.selectUser();
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
    }
	
	void selectGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		try{
			List<SGroup> list=noticeDao.selectGroup();
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
    }
	
	String autocomplete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		String result="";
		try{
			String activityNo=request.getParameter("activityNo");
			List<S_user> list=noticeDao.qeuryByUser(activityNo);
			//System.out.println("总共条数-->"+list.size());
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
		return result;
    }
	
	private void new_notice(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		NoticeDao noticeDao=new NoticeDaoImpl();
		String adminUsername="";
		try {
			   adminUsername=(String) request.getSession().getAttribute("adminUsername");
			   //查询最新一条未读消息
		   	   int id = noticeDao.queryNewNotice(adminUsername);
		   	   String result=Util.getMsgJosnObject("success", id+"");
			   //JSONArray jsons=JSONArray.fromObject(id);
		   	   out.print(result);
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			out.flush();
		}
		
	}
    
}
