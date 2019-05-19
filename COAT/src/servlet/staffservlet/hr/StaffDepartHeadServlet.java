package servlet.staffservlet.hr;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DateUtils;
import util.Page;
import util.Util;
import dao.DepartMentDao;
import dao.exp.ExpStaffPosition;
import dao.impl.DepartMentDaoImpl;
import entity.Department;

public class StaffDepartHeadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(StaffDepartHeadServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
	
		try{
			 user=request.getSession().getAttribute("adminUsername").toString();
			if(method.equals("select"))
				select(request, response);
			else if(method.equals("modify"))
				modify(request, response);
			else if(method.equals("del"))
				del(request, response);
			else if(method.equals("save"))
				save(request, response);
			else if(method.equals("down"))
				down(request, response);
			else if(method.equals("vail"))
				vail(request, response);
		}catch (NullPointerException e) {
			response.getWriter().print("null");
			log.error("Staff Department操作异常：空值=="+e);
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Staff Department操作异常："+e);
			response.getWriter().print("error");
		} finally{
			method=null; 
		} 
	}

	public void vail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		try{
			String dpt=request.getParameter("dpt");
			
		}catch (Exception e) {
			log.error("Staff DepartMent 验证是否为Depart Head"+e.toString());
			throw e;
		}finally{
			out.flush();
			out.close();
		}
		
		
	}
	
	
	
	
	
	
	public void select(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String dpt=request.getParameter("dpt");
		String sfyx=request.getParameter("sfyx");
		 Page page=new Page();
		List<Department> list=new ArrayList<Department>();
		DepartMentDao dmd=new DepartMentDaoImpl();
		try{
			page.setAllRows(dmd.getRow(startDate, endDate, dpt, sfyx));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			list=dmd.find(startDate, endDate, dpt, sfyx, page);
			List<Object> list1=new ArrayList<Object>();
			list1.add(0,list);
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			jsons=null;
			list1=null;
		}catch (Exception e) {
			log.error("Staff DepartMent 分页查询异常"+e.toString());
			throw e;
		}finally{
			page=null;
			list=null;
			dmd=null; 
			out.flush();
			out.close();
		}
		
		
	}
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		DepartMentDao dmd=new DepartMentDaoImpl();
		try{
			String dpt=request.getParameter("dpt");
			if(dmd.findByDpt(dpt)){//已经存在Position
				out.print("The position has been in existence, cannot save!");
			}else{
				String department=request.getParameter("department");
				String depart_head=request.getParameter("depart_head");
				String depart_head2=request.getParameter("depart_head2");
				Department dm=new Department();
				dm.setDpt(dpt);
				dm.setDepartment(department);
				dm.setDepart_head(depart_head);
				dm.setDepart_head_bak(depart_head2);
				dm.setAdd_name(user);
				dm.setAdd_date(DateUtils.getNowDateTime());
				dm.setSfyx("Y");
				if(dmd.saveDepart(dm)>0){//保存成功
					out.print("Save Success!");
				}else{
					out.print("Save Error!");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			 log.error("保存position_list_staff 异常   ==="+e); 
		}finally{
			out.flush();
			out.close();
		}
		
	}
	public void modify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String dptId=request.getParameter("dptId");
		DepartMentDao dmd=new DepartMentDaoImpl();
		try{	
			if(!Util.objIsNULL(dptId)){
				String dpt=request.getParameter("dpt");
				String department=request.getParameter("department");
				String depart_head=request.getParameter("depart_head");
				String depart_head2=request.getParameter("depart_head2");
				String addDate=request.getParameter("modify_add_date");
				String sfyx=request.getParameter("sfyx");
				Department dm=dmd.findById(Integer.parseInt(dptId));
				 if(Util.objIsNULL(dm)){
					 out.print("Data does not exist!");
				 }else{
					 if(dm.getAdd_date().trim().equals(addDate.trim())){
						 dm.setDpt(dpt);
						 dm.setDepartment(department);
						 dm.setDepart_head(depart_head);
						 dm.setDepart_head_bak(depart_head2);
						 dm.setUpd_name(user);
						 dm.setUpd_date(DateUtils.getNowDateTime());
						 dm.setSfyx(sfyx);
						int num= dmd.modifyDepart(dm);
						if(num>0)
							out.print("Success!");
						else
							out.print("Error!");
					 }else{
						 out.print("Data is illegal tampering detection system, refused to operate!");
						 log.error("网页数据被篡改!");
					 }
				 }
			}else{
				out.print("Data does not exist!");
			}
		}catch (Exception e) {
			log.error("Staff DepartMent 修改异常"+e.toString());
			throw e;
		}finally{
			out.flush();
			out.close();
		}
	}
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String dptId=request.getParameter("dptId");
		DepartMentDao dmd=new DepartMentDaoImpl();
		try{	
			if(!Util.objIsNULL(dptId)){
				
				String addDate=request.getParameter("addDate");
				Department dpts=dmd.findById(Integer.parseInt(dptId));
				 if(Util.objIsNULL(dpts)){
					 out.print("Data does not exist!");
				 }else{
					 if(dpts.getAdd_date().trim().equals(addDate.trim())){
						 dpts.setUpd_name(user);
						 dpts.setUpd_date(DateUtils.getNowDateTime());
						 dpts.setSfyx("N");
						int num= dmd.modifyDepart(dpts);
						if(num>0)
							out.print("Success!");
						else
							out.print("Error!");
					 }else{
						 out.print("Data is illegal tampering detection system, refused to operate!");
						 log.error("网页数据被篡改!"); 
					 }
				 }
			}else{
				 out.print("Data does not exist!");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			out.flush();
			out.close();
		}
		
	}
	@SuppressWarnings("static-access")
	public void down(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("--对Staff Department进行导出操作!");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String dpt=request.getParameter("dpt");
		String sfyx=request.getParameter("sfyx");
		String fname ="StaffDepartment";//Excel文件名
		try{
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream  os = response.getOutputStream();//取出输出流
		response.reset();//清空输出流
		//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
		ExpStaffPosition expcard=new ExpStaffPosition();
		DepartMentDao dmd=new DepartMentDaoImpl();
		ResultSet rs=dmd.downDepartMent(startDate, endDate, dpt, sfyx);
		
		HSSFSheet sheet = wb.createSheet("new sheet");
		wb.setSheetName(0, "StaffDepartment");
		HSSFRow row=sheet.createRow(0);
		sheet.createFreezePane(0, 1);
		expcard.cteateTWOTitleCell(wb,row,(short)0,"Num");
		expcard.cteateTWOTitleCell(wb,row,(short)1,"Department Abbreviation");
		expcard.cteateTWOTitleCell(wb,row,(short)2,"Department");
		expcard.cteateTWOTitleCell(wb,row,(short)3,"Department Head");
		expcard.cteateTWOTitleCell(wb,row,(short)4,"Department Head2");
		expcard.cteateTWOTitleCell(wb,row,(short)5,"Sumbit Name");
		expcard.cteateTWOTitleCell(wb,row,(short)6,"Sumbit Date");
		expcard.cteateTWOTitleCell(wb,row,(short)7,"Modify Name");
		expcard.cteateTWOTitleCell(wb,row,(short)8,"Modify Date");
		expcard.cteateTWOTitleCell(wb,row,(short)9,"Effective?");
		expcard.createFixationSheet(rs, os,wb,sheet);
		rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Staff DepartMent 导出异常=="+e);
		}
	}
}
