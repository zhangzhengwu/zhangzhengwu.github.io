package servlet.Medical;

import java.io.IOException;
import java.io.OutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import util.Constant;
import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.Tools;
import util.Util;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.QueryMedicalDao;
import dao.exp.ExpMedicalClaim;
import dao.impl.QueryMedicalDaoImpl;
import entity.Medical;

/**
 * 导出Medical信息数据
 * @author Wilson
 *
 */
@SuppressWarnings("deprecation")
public class DownMedicalServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DownMedicalServlet.class);
	/**
	 * 导出方法的servlet
	 */
	/*@SuppressWarnings({ "static-access" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 LOG.info(request.getSession().getAttribute("adminUsername").toString()+"对Medical进行了导出操作！");
		 response.setContentType("text/html;charset=utf-8");
		 
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("code");
		 String name = request.getParameter("name");
		 HSSFWorkbook wb = new HSSFWorkbook();
		 ExpMedicalClaim expcard = new ExpMedicalClaim();
		 QueryMedicalDao qcDao = new QueryMedicalDaoImpl();
			OutputStream os = null; 
		 try {
			 	String fname =Constant.MEDICALFILE_NAME;//Excel文件名
			  os = response.getOutputStream();//取出输出流
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
				//定义输出类型
				ResultSet res =qcDao.queryRequstListSet(name, staffcode, startDate, endDate);
			if(res!=null){
				HSSFSheet sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "Namelist");
				HSSFRow row=sheet.createRow(0);
				sheet.createFreezePane(0, 0);
				expcard.cteateTitleCell(wb,row,(short)0,"CONVOY FINANCIAL SERVICES LIMITED");
				expcard.cteateTWOTitleCell(wb,row,(short)5,"Month Code");
				expcard.cteateTWOTitleCell(wb,row,(short)7,"Year Code");
				row=sheet.createRow(1);
				sheet.createFreezePane(0, 1);
				expcard.cteateTWOTitleCell(wb,row,(short)0,"Medical Claims Records For "+DateUtils.zhEYearMonth(endDate));
				sheet.addMergedRegion(new Region(1,(short)5,1,(short)6));
				sheet.addMergedRegion(new Region(1,(short)7,1,(short)8));
				expcard.cteateTWOTitleCell(wb,row,(short)5,endDate.substring(endDate.indexOf("-")+1, endDate.lastIndexOf("-")));
				expcard.cteateTWOTitleCell(wb,row,(short)7,""+DateUtils.getYear());
				
				row = sheet.createRow((short)4);
				sheet.createFreezePane(0, 5);
				//sheet.createFreezePane(0, 1);
				expcard.cteateTWOTitleCell(wb,row,(short)0,"Staff Code");
				expcard.cteateTWOTitleCell(wb,row,(short)1,"Name");
				expcard.cteateTWOTitleCell(wb,row,(short)2,"Code");
				expcard.cteateTWOTitleCell(wb,row,(short)3,"Month");
				expcard.cteateTWOTitleCell(wb,row,(short)4,"AutoPay");
				expcard.createFixationSheet(res, os,wb,sheet);
				res.close();
			}else{
				LOG.info("没有数据");
			}
		}catch (Exception e) {
			LOG.error("导出方法的servlet"+e);
			e.printStackTrace();
		}finally{
			os.flush();
			os.flush();
		}
	}*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 LOG.info(request.getSession().getAttribute("adminUsername").toString()+"对Medical进行了导出操作！");
		 response.setContentType("text/html;charset=utf-8");
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("code");
		 String name = request.getParameter("name");
		 QueryMedicalDao qcDao = new QueryMedicalDaoImpl();
		 try {
			List<Medical> list=qcDao.queryRequstListSet(name, staffcode, startDate, endDate);
	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0, "CONVOY FINANCIAL SERVICES LIMITED");
			map.put(5,"Month Code");
			map.put(7,"Year Code");
			title.add(map);
			map=new HashMap<Integer, Object>();
			map.put(0,"Medical Claims Records For "+DateUtils.zhEYearMonth(endDate));
			map.put(5, endDate.substring(endDate.indexOf("-")+1, endDate.lastIndexOf("-")));
			map.put(7,""+DateUtils.getYear());
			title.add(map);
			map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			map.put(0,"Staff Code");
			map.put(1,"Name");
			map.put(2,"Code");
			map.put(3,"Month");
			map.put(4,"AutoPay");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			double num = 0;
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					p.put("var1", list.get(i).getStaffcode()); 
					p.put("var2", list.get(i).getName()); 
					p.put("var3", list.get(i).getAD_type()); 
					p.put("var4", list.get(i).getMedical_month()); 
					p.put("var5", list.get(i).getEntitled_Fee()); 
					num+=Double.parseDouble(list.get(i).getEntitled_Fee());
					data.add(p);
				}
				for (int j = 0; j < 4; j++) {
					PageData p=new PageData();
					p.put("var1", ""); 
					p.put("var2", ""); 
					p.put("var3", ""); 
					p.put("var4", ""); 
					p.put("var5", ""); 
					data.add(p);
				}
				PageData p=new PageData();
				p.put("var1", ""); 
				p.put("var2", ""); 
				p.put("var3", ""); 
				p.put("var4", "Consultant:"); 
				p.put("var5", num+""); 
				data.add(p);
			}
			model.put("varList", data);
			
			
		 /*   map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			title.add(map);
			map=new HashMap<Integer, Object>();
			map.put(3, "Consultant:");
			map.put(4, num);
			title.add(map);*/
			
			
			String filename =Constant.MEDICALFILE_NAME;//Excel文件名
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,filename,"Namelist");
	
		}catch (Exception e) {
			LOG.error("导出方法的servlet"+e);
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}
}