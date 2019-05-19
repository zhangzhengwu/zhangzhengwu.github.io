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
import dao.QueryDepartmentDao;
import dao.exp.ExpStaffPosition;
import dao.impl.QueryDepartmentDaoImpl;
import entity.Department;
import entity.Professional_title_Staff;

public class DepartmentServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(DepartmentServlet.class);
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
		}catch (NullPointerException e) {
			response.getWriter().print("null");
			log.error("Position操作异常：空值=="+e);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Position操作异常："+e);
			response.getWriter().print("error");
		} finally{
			method=null; 
		} 
	}
	
	
	public void down(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("--对professional_title_staff进行导出操作!");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String department=request.getParameter("department");
		String sfyx=request.getParameter("sfyx");
		String fname ="professional_title_staff";//Excel文件名
		
		try{
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream  os = response.getOutputStream();//取出输出流
		response.reset();//清空输出流
		//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
		ExpStaffPosition expcard=new ExpStaffPosition();
		QueryDepartmentDao qdao = new QueryDepartmentDaoImpl();
		ResultSet rs=qdao.DownDepartment(startDate, endDate, department, sfyx);
		
		HSSFSheet sheet = wb.createSheet("new sheet");
		wb.setSheetName(0, "professional_title_staff");
		HSSFRow row=sheet.createRow(0);
		sheet.createFreezePane(0, 1);
		//sheet.createFreezePane(0, 1);
		expcard.cteateTWOTitleCell(wb,row,(short)0,"Num");
		expcard.cteateTWOTitleCell(wb,row,(short)1,"Department(Eng)");
		expcard.cteateTWOTitleCell(wb,row,(short)2,"Department(Chi)");
		expcard.cteateTWOTitleCell(wb,row,(short)3,"Sumbit Date");
		expcard.cteateTWOTitleCell(wb,row,(short)4,"Sumbit Name");
		expcard.cteateTWOTitleCell(wb,row,(short)5,"Modify Name");
		expcard.cteateTWOTitleCell(wb,row,(short)6,"Modify Date");
		expcard.cteateTWOTitleCell(wb,row,(short)7,"Status");
		expcard.createFixationSheet(rs, os,wb,sheet);
		rs.close();
		
		}catch (Exception e) {
			// TODO: handle exception
			log.error("StaffPostion 导出异常=="+e);
		}
	}
	
	
	public void modify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String deptId=request.getParameter("deptId");
		QueryDepartmentDao qdao = new QueryDepartmentDaoImpl();
		try{	
		
			if(!Util.objIsNULL(deptId)){
				String dpt=request.getParameter("dpt");
				String department=request.getParameter("department");
				String sfyx=request.getParameter("sfyx");
				String addDate=request.getParameter("modify_add_date");
				Professional_title_Staff ps=qdao.queryDepartmentsId(deptId);
				 if(Util.objIsNULL(ps)){
					 out.print("Data does not exist!");
				 }else{
					 if(ps.getAdd_date().trim().equals(addDate.trim())){
						ps.setProf_title_ename(dpt);
						ps.setProf_title_cname(department);
						 ps.setUpd_name(user);
						 ps.setUpd_date(DateUtils.getNowDateTime());
						 ps.setSfyx(sfyx);
						int num= qdao.ModifyDepartment(ps);
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
			log.error("StaffPosition 修改异常"+e.toString());
			throw e;
		}finally{
			out.flush();
			out.close();
		}
	}
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String deptId=request.getParameter("deptId");
		QueryDepartmentDao qdao = new QueryDepartmentDaoImpl();
		try{	
		
			if(!Util.objIsNULL(deptId)){
				String department=request.getParameter("department");
				Professional_title_Staff ps=qdao.queryDepartmentsId(deptId);
				 if(Util.objIsNULL(ps)){
					 out.print("Data does not exist!");
				 }else{
					 if(ps.getProf_title_ename().trim().equals(department.trim())){
						 ps.setUpd_name(user);
						 ps.setUpd_date(DateUtils.getNowDateTime());
						 ps.setSfyx("N");
						int num= qdao.ModifyDepartment(ps);
						if(num>0)
							out.print("Success!");
						else
							out.print("Error!");
					 }else{
						 out.print("Data is illegal tampering detection system, refused to operate!");
						 log.error("网页数据被篡改!"); 
					 }
				 }
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
	
	
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		QueryDepartmentDao qdao = new QueryDepartmentDaoImpl();
		try{
			String dpt=request.getParameter("dpt");
			String department=request.getParameter("department");
			
			if(qdao.findDepartmentEName(department)){//已经存在Position
				out.print("The department has been in existence, cannot save!");
			}else{
				
				Professional_title_Staff dt=new Professional_title_Staff(dpt,department,DateUtils.getNowDateTime(),user,"","","Y");
			
				if(qdao.saveDept(dt)>0){//保存成功
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
	
	
	
	
	/**
	 * 查询Department资料
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void select(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String department=request.getParameter("department");
		String sfyx=request.getParameter("sfyx");
		List<Professional_title_Staff> list = new ArrayList<Professional_title_Staff>();
		QueryDepartmentDao qdao = new QueryDepartmentDaoImpl();
		
		 Page page=new Page();
		try{
		
				page.setAllRows(qdao.getRows(startDate, endDate, department, sfyx));
				page.setPageSize(15);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
				list=qdao.findPosition(startDate, endDate, department, sfyx, page);
				List list1=new ArrayList();
				list1.add(0,list);
				list1.add(1,page.getAllPages());//总页数
				list1.add(2,page.getCurPage());//当前页
				list1.add(3,page.getAllRows());//总行数
				JSONArray jsons=JSONArray.fromObject(list1);
				out.print(jsons.toString());
				jsons=null;
				list1=null;
		}catch (Exception e) {
			log.error("StaffDepartment 分页查询异常"+e.toString());
			throw e;
		}finally{
			page=null;
			list=null;
			qdao=null;
			out.flush();
			out.close();
		}
		
	}
 
	
	
	
	
	
}
