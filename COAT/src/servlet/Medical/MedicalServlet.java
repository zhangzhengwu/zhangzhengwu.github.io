package servlet.Medical;

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

import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.Util;
import dao.MedicalOptOutDao;
import dao.impl.MedicalOptOutDaoImpl;
import entity.Excel;
import entity.MedicalOutPutList;

public class MedicalServlet extends HttpServlet {

	private static final long serialVersionUID = -7804858533667192906L;
	Logger log = Logger.getLogger(MedicalServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request,response);
	}
	String user = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			user=(String) request.getSession().getAttribute("adminUsername");
			if (method.equals("select")) {
				select(request, response);	
			}else if (method.equals("del")){
				result = del(request, response);
			}else if (method.equals("add")){
				result=add(request, response);	
			}else if (method.equals("exportExecl")){
				exportExecl(request, response);	
			}else if (method.equals("legal")){
				legal(request, response);				
			}else {
				throw new Exception("Unauthorized access!");
			}
		} catch (NullPointerException e) {
			result=Util.joinException(e);
			log.error("SystemUserServlet==>" + method + "操作异常：空值==" + e);
		} catch (Exception e) {
			log.error("SystemUserServlet==>" + method + "操作异常：" + e);
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
		response.setContentType("text/html;charset=utf-8");
		
		String startdate = request.getParameter("startDate").trim();
		String enddate = request.getParameter("endDate").trim();
		String staffcode = request.getParameter("staffcode").trim();
		PrintWriter out = response.getWriter();
		MedicalOptOutDao medicalOptOutDao = new MedicalOptOutDaoImpl();
		try {
			Pager page=Util.pageInfo(request);
			page=medicalOptOutDao.findMedicalOptOutList(null, page,
									          Util.objIsNULL(startdate)?"1900-01-01":startdate,
											  Util.objIsNULL(enddate)?"2099-12-31":enddate,
											  Util.objIsNULL(staffcode)?"%%":staffcode);
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

	public String add(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result="";		
		int num = -1;
		MedicalOutPutList medicalOutPutList = null;
		try {
			String staffcode = request.getParameter("staffcode");
			String staffname = request.getParameter("staffname");
			String remark = request.getParameter("remark");
			String now = DateUtils.getNowDateTime();
			String name=(String)request.getSession().getAttribute("adminUsername");
			MedicalOptOutDao medicalOptOutDao = new MedicalOptOutDaoImpl();
			medicalOutPutList = new MedicalOutPutList(staffcode,staffname,now,name,remark,"Y");
			num = medicalOptOutDao.save(medicalOutPutList);
			if(num < 1){
				throw new RuntimeException("保存数据失败");
			}else{
				result = Util.getMsgJosnObject_success();
			}
		
		} catch (Exception e) {
			result = Util.joinException(e);
			log.error("保存失败："+e.toString());
		}
		return result;
	}	
	
	
	
	/**
	 * 删除  将状态修改Y --> N
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		MedicalOptOutDao medicalOptOutDao = new MedicalOptOutDaoImpl();
		int num = -1;
		try {
			String staffcode = request.getParameter("staffcode");
			num = medicalOptOutDao.delete(staffcode);
			if(num < 1){
				throw new RuntimeException("删除失败!");
			}
			result = Util.getMsgJosnSuccessReturn(num);
		}catch (Exception e) {
			result = Util.joinException(e);
		}
		return result;
	}	

	
	public void exportExecl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String startdate = request.getParameter("startDate").trim();
		String enddate = request.getParameter("endDate").trim();
		String staffcode = request.getParameter("staffcode").trim();	
		MedicalOptOutDao medicalOptOutDao = new MedicalOptOutDaoImpl();
		Excel excel=new Excel();
		 try{
			//得到结果
	     List<MedicalOutPutList> list=medicalOptOutDao.exportDate(startdate,enddate,staffcode);	
		    //把数据交给Excel
		    excel.setExcelContentList(list);	
		    //设置Excel列头
		    excel.setColumns(new String[]{"Staff Code","Staff Name","Remark"});
		    //属性字段名称
		    excel.setHeaderNames(new String[]{"staffcode","staffname","remark"});
		   //sheet名称
		    excel.setSheetname("MedicalOutPutList");
		    //文件名称
			excel.setFilename("MedicalOutPutList"+System.currentTimeMillis());
			String filename=ExcelTools.getExcelFileName(excel.getFilename());
			response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
	       //生成EXCEL
			ExcelTools.createExcel(excel,response);
		    }catch (Exception e) {
			    e.printStackTrace();
		    }
		
	}	

	public void legal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode").trim();
		MedicalOptOutDao medicalOptOutDao = new MedicalOptOutDaoImpl();
		int num = -1;
		try {
			num = medicalOptOutDao.ifExist(staffcode);
			out.print(num);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	
}
