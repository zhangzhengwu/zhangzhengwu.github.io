package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import util.ObjectExcelView;
import util.Page;
import util.PageData;
import util.Tools;
import util.Util;
import dao.ConsListDao;
import dao.exp.ExpCardQuota;
import dao.impl.ConsListDaoImpl;
import entity.ConsList;

public class ConsListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ConsListServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		//PrintWriter out = response.getWriter();
		
		String method=request.getParameter("method");
		
		try{		
			if(method.equals("query"))
				query(request, response);
			else if(method.equals("down"))
				down(request, response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("ConsList==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ConsList==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 	
	
	}

	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void query(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		PrintWriter out = response.getWriter();
		Page page=new Page();
		String employeeId = request.getParameter("employeeId");
		String c_Name=request.getParameter("c_Name");
		String HKID=request.getParameter("HKID");
		String recruiterId = request.getParameter("recruiterId");
		
		List<ConsList> conslist = new ArrayList<ConsList>();
		ConsListDao ad = new ConsListDaoImpl();
		try {
			page.setAllRows(ad.getRows(employeeId, c_Name, HKID, recruiterId));
			page.setPageSize(20);													//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));		//获取页面当前页码   
			conslist = ad.queryReqeustList(employeeId, c_Name, HKID, recruiterId, page);
			
			List list=new ArrayList();
			list.add(0,conslist);			//数据
			list.add(1,page.getAllPages());	//总页数
			list.add(2,page.getCurPage());	//当前页
			list.add(3,page.getAllRows());	//总行数	
			JSONArray jsons = JSONArray.fromObject(list);
			out.print(jsons.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.flush();
			out.close();
		}	
	}
	 
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * 注意：：OutputStream和Writer在一个response中不能同时获得。
	 */
	/*@SuppressWarnings("static-access")
	void down(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		System.out.println("======>>DownServlet======");
		//log.info(request.getSession().getAttribute("adminUsername").toString()+"对Cons_List Query 进行了导出操作！");
	
		HSSFWorkbook wb = new HSSFWorkbook();
	 	ExpCardQuota expcard = new ExpCardQuota();
		ConsListDao qcDao = new ConsListDaoImpl();
	try{
		String employeeId =request.getParameter("employeeId");
		String c_Name=(String)request.getParameter("c_Name");
		String HKID=request.getParameter("HKID");
		String recruiterId=request.getParameter("recruiterId");
		
		*//**
		 * Get方式提交修改乱码的形式
		 *//*
		c_Name = new String(c_Name.getBytes("ISO8859-1"),"utf-8");
		
		*//**
		 * Post方式提交修改乱码的形式
		 *//*
		//request.setCharacterEncoding("utf-8");
		
		String fname ="Cons_List Query";				//Excel文件名
		OutputStream os = response.getOutputStream();	//取出输出流
		response.reset();	//清空输出流		
		response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");	//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		ResultSet res =qcDao.downConsList(employeeId,c_Name,HKID,recruiterId);				
		
		if(res!=null){
			HSSFSheet sheet = wb.createSheet("new sheet");
			wb.setSheetName(0, "ConsList  Query");
			HSSFRow row=sheet.createRow(1);
			sheet.createFreezePane(0, 1);
			expcard.cteateTitleCell(wb,row,(short)0,"ConList Query");
			expcard.cteateTitleCell(wb,row,(short)7,"Download Time:");
			expcard.cteateTitleCell(wb,row,(short)8,DateUtils.getNowDate());
			row=sheet.createRow(2);
			sheet.createFreezePane(0, 2);
			expcard.cteateTitleCell(wb,row,(short)0,"Year"+DateUtils.getYear());
			row = sheet.createRow(4);
			sheet.createFreezePane(0, 5);
			expcard.cteateTitleCell(wb,row,(short)0,"EmployeeId");
			expcard.cteateTitleCell(wb,row,(short)1,"Alias");
			expcard.cteateTitleCell(wb,row,(short)2,"C_Name");
			expcard.cteateTitleCell(wb,row,(short)3,"HKID");
			expcard.cteateTitleCell(wb,row,(short)4,"RecruiterId");			
			expcard.cteateTitleCell(wb,row,(short)5,"joinDate");
			expcard.cteateTitleCell(wb,row,(short)6,"DirectLine");
			expcard.cteateTitleCell(wb,row,(short)7,"Email");
			expcard.cteateTitleCell(wb,row,(short)8,"EmployeeName");
			expcard.cteateTitleCell(wb,row,(short)9,"ExternalPosition");			
			expcard.cteateTitleCell(wb,row,(short)10,"GroupDateJoin");
			expcard.cteateTitleCell(wb,row,(short)11,"Location");
			expcard.cteateTitleCell(wb,row,(short)12,"C_PositionName");
			expcard.cteateTitleCell(wb,row,(short)13,"E_PositionName");		
			expcard.cteateTitleCell(wb,row,(short)14,"RecruiterName");
			expcard.cteateTitleCell(wb,row,(short)15,"TelephoneNo");
			expcard.cteateTitleCell(wb,row,(short)16,"ADTreeHead");
			expcard.cteateTitleCell(wb,row,(short)17,"DDTreeHead");
			expcard.createFixationSheet(res, os,wb,sheet);
			res.close();
		}
	} catch (Exception e) {
		log.error("导出方法的servlet"+e);
		e.printStackTrace();
	}
	}*/
	
	void down(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String employeeId =request.getParameter("employeeId");
		String c_Name=(String)request.getParameter("c_Name");
		String HKID=request.getParameter("HKID");
		String recruiterId=request.getParameter("recruiterId");
	try{
		/**
		 * Get方式提交修改乱码的形式
		 */
		c_Name = new String(c_Name.getBytes("ISO8859-1"),"utf-8");
		
		/**
		 * Post方式提交修改乱码的形式
		 */
		//request.setCharacterEncoding("utf-8");
		ConsListDao qcDao = new ConsListDaoImpl();
		List<ConsList> list=qcDao.downConsList(employeeId,c_Name,HKID,recruiterId);
    	Map<String, Object> model=new HashMap<String, Object>();
		List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
		Map<Integer,Object> map=new HashMap<Integer, Object>();
		title.add(map);
		map=new HashMap<Integer, Object>();
		map.put(0, "ConList Query");
		map.put(7, "Download Time:");
		map.put(8, DateUtils.getNowDate());
		title.add(map);
		map=new HashMap<Integer, Object>();
		map.put(0, "Year"+DateUtils.getYear());
		title.add(map);
		map=new HashMap<Integer, Object>();
		title.add(map);
		map=new HashMap<Integer, Object>();
		map.put(0,"EmployeeId");
		map.put(1,"Alias");
		map.put(2,"C_Name");
		map.put(3,"HKID");
		map.put(4,"RecruiterId");			
		map.put(5,"joinDate");
		map.put(6,"DirectLine");
		map.put(7,"Email");
		map.put(8,"EmployeeName");
		map.put(9,"ExternalPosition");			
		map.put(10,"GroupDateJoin");
		map.put(11,"Location");
		map.put(12,"C_PositionName");
		map.put(13,"E_PositionName");		
		map.put(14,"RecruiterName");
		map.put(15,"TelephoneNo");
		map.put(16,"ADTreeHead");
		map.put(17,"DDTreeHead");
		title.add(map);
		model.put("titles", title);
		List<PageData> data=new ArrayList<PageData>();
		if(!Util.objIsNULL(list)){
			for (int i = 0; i < list.size(); i++) {
				PageData p=new PageData();
				p.put("var1", list.get(i).getEmployeeId()); 
				p.put("var2", list.get(i).getAlias()); 
				p.put("var3", list.get(i).getC_Name()); 
				p.put("var4", list.get(i).getHKID()); 
				p.put("var5", list.get(i).getRecruiterId()); 
				p.put("var6", list.get(i).getJoinDate()); 
				p.put("var7", list.get(i).getDirectLine()); 
				p.put("var8", list.get(i).getEmail()); 
				p.put("var9", list.get(i).getEmployeeName()); 
				p.put("var10",list.get(i).getExternalPosition()); 
				p.put("var11", list.get(i).getGroupDateJoin()); 
				p.put("var12", list.get(i).getLocation()); 
				p.put("var13", list.get(i).getC_PositionName()); 
				p.put("var14", list.get(i).getE_PositionName()); 
				p.put("var15", list.get(i).getRecruiterName()); 
				p.put("var16", list.get(i).getTelephoneNo()); 
				p.put("var17", list.get(i).getADTreeHead()); 
				p.put("var18", list.get(i).getDDTreeHead()); 
				data.add(p);
			}
		}
		model.put("varList", data);
		Date date=new Date();
		String filename ="Cons_List Query"+Tools.date2Str(date, "yyyyMMddHHmmss")+".xls";
		response.reset();//清空输出流
		//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
		ObjectExcelView view=new ObjectExcelView();
		view.addStaticAttribute("xxx", "gsdg");
	    HSSFWorkbook wb= new HSSFWorkbook();
		view.buildExcelDocument2(model, wb,response,filename,"Cons_List Query");
		
	} catch (Exception e) {
		log.error("导出方法的servlet"+e);
		e.printStackTrace();
	}
	}
	
	
}
