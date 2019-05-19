package servlet.Medical;

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
import dao.exp.ExpMedicalForCons;
import dao.impl.QueryMedicalDaoImpl;
import entity.Medical;

/**
 * 导出Medical信息数据给Consultant
 * @author Wilson
 *
 */
@SuppressWarnings("deprecation")
public class DownForConsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DownForConsServlet.class);
	/**
	 * 导出方法的servlet
	 */
	/*@SuppressWarnings("static-access")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// LOG.info(request.getSession().getAttribute("adminUsername").toString()+"对Medical进行了导出操作！");
		 response.setContentType("text/html;charset=utf-8");
		 
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("code");
		 String name = request.getParameter("name");
		 HSSFWorkbook wb = new HSSFWorkbook();
		 ExpMedicalForCons expcard = new ExpMedicalForCons();
		 QueryMedicalDao qcDao = new QueryMedicalDaoImpl();
		 try {
			 LOG.info("对Medical Consultant 导出全部");
				//定义输出类型
				ResultSet res =qcDao.upLoadForConsList(name, staffcode, startDate, endDate);
				res.last();
				if(res.getRow()>0){
			 	String fname =Constant.MEDICALCONSULTANT_NAME;//Excel文件名
				OutputStream os = response.getOutputStream();//取出输出流
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
		
				HSSFSheet sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "Medical Claims");
				HSSFRow row=sheet.createRow(0);
				sheet.createFreezePane(0, 0);
				sheet.addMergedRegion(new Region(0,(short)0,0,(short)8));
				expcard.cteateTitleCenterCell(wb,row,(short)0,"CONVOY FINANCIAL SERVICES LIMITED");
				row=sheet.createRow(1);
				sheet.createFreezePane(0, 1);
				sheet.addMergedRegion(new Region(1,(short)0,1,(short)8));
				expcard.cteateTitleCenterCell(wb,row,(short)0,"Medical Claims Report");
				
				row = sheet.createRow((short)3);
				sheet.createFreezePane(0, 5);
				sheet.addMergedRegion(new Region(3,(short)9,3,(short)10));
				expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"AD?");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"Consultation");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"Consulting Fee");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"Claimed");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"No of");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"Claimed in");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"No. of claimed case");

				row = sheet.createRow((short)4);
				sheet.createFreezePane(0, 5);
				expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"Staff Code");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"Name");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"Code");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"SP");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"Date");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"HKD");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"Amount");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"Terms");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"Month");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"Normal");
				expcard.cteateTWOTitleCenterCell(wb,row,(short)10,"Special");
				
				expcard.createFixationSheet(res, os,wb,sheet);
				res.close();
			}else{
			 
				PrintWriter out = response.getWriter();
			out.print("<script>alert('没有数据!');history.go(-1);</script>");
			//response.sendRedirect("MedicalClaim_Consultant/QueryMedical_Consultant.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("导出全部Medical for Cons方法的servlet"+e);
		}
	}*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 response.setContentType("text/html;charset=utf-8");
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("code");
		 String name = request.getParameter("name");
		 QueryMedicalDao qcDao = new QueryMedicalDaoImpl();
		 try {
			 	String fname =Constant.MEDICALCONSULTANT_NAME;//Excel文件名
			 	List<Medical> list =qcDao.upLoadForConsList2(name, staffcode, startDate, endDate);
		    	Map<String, Object> model=new HashMap<String, Object>();
				List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
				Map<Integer,Object> map=new HashMap<Integer, Object>();
				map.put(3, "CONVOY FINANCIAL");
				map.put(4, "SERVICES LIMITED");
				title.add(map);
				map=new HashMap<Integer, Object>();
				map.put(3, "Medical Claims Report");
				title.add(map);
				
				map=new HashMap<Integer, Object>();
				title.add(map);
				
				map=new HashMap<Integer, Object>();
				map.put(0,"");
				map.put(1,"");
				map.put(2,"AD?");
				map.put(3,"");
				map.put(4,"Consultation");
				map.put(5,"Consulting Fee");
				map.put(6,"Claimed");
				map.put(7,"No of");
				map.put(8,"Claimed in");
				map.put(9,"No. of claimed case");
				title.add(map);
				
				map=new HashMap<Integer, Object>();
				map.put(0,"Staff Code");
				map.put(1,"Name");
				map.put(2,"Code");
				map.put(3,"SP");
				map.put(4,"Date");
				map.put(5,"HKD");
				map.put(6,"Amount");
				map.put(7,"Terms");
				map.put(8,"Month");
				map.put(9,"Normal");
				map.put(10,"Special");
				title.add(map);
				
				model.put("titles", title);
				List<PageData> data=new ArrayList<PageData>();
				if(!Util.objIsNULL(list)){
					for (int i = 0; i < list.size(); i++) {
						PageData p=new PageData();
						p.put("var1", list.get(i).getStaffcode()); 
						p.put("var2", list.get(i).getName()); 
						p.put("var3", list.get(i).getAD_type()); 
						p.put("var4", list.get(i).getSP_type()); 
						p.put("var5", list.get(i).getMedical_date()); 
						p.put("var6", list.get(i).getMedical_Fee()); 
						p.put("var7", list.get(i).getEntitled_Fee()); 
						p.put("var8", list.get(i).getTerms_year()); 
						p.put("var9", list.get(i).getMedical_month()); 
						p.put("var10",list.get(i).getMedical_Normal()); 
						p.put("var11", list.get(i).getMedical_Special()); 
						data.add(p);
					}
					for (int j = 0; j <4; j++) {
						PageData p=new PageData();
						p.put("var1", ""); 
						data.add(p);
					}
					PageData p1=new PageData();
					p1.put("var1", ""); 
					p1.put("var2", ""); 
					p1.put("var3", "Remarks : "); 
					data.add(p1);
					
					PageData p2=new PageData();
					p2.put("var1", ""); 
					p2.put("var2", ""); 
					p2.put("var3", "S - Special Treatment "); 
					data.add(p2);
					
					PageData p3=new PageData();
					p3.put("var1", ""); 
					p3.put("var2", ""); 
					p3.put("var3", "D - AD / Director Grade "); 
					data.add(p3);
					
					PageData p4=new PageData();
					p4.put("var1", ""); 
					p4.put("var2", ""); 
					p4.put("var3", "Max No of Ordinary Treatment is 15"); 
					data.add(p4);
					
					PageData p5=new PageData();
					p5.put("var1", ""); 
					p5.put("var2", ""); 
					p5.put("var3", "Max No of Special Treatment is 10 "); 
					data.add(p5);

					PageData p6=new PageData();
					p6.put("var1", ""); 
					p6.put("var2", ""); 
					p6.put("var3", "Should you have any enquiries, please send email to SZOAdm@convoy.com.hk "); 
					data.add(p6);
				}
				model.put("varList", data);
				Date date=new Date();
				String filename =fname+Tools.date2Str(date, "yyyyMMddHHmmss")+".xls";
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
				ObjectExcelView view=new ObjectExcelView();
				view.addStaticAttribute("xxx", "gsdg");
			    HSSFWorkbook wb= new HSSFWorkbook();
				view.buildExcelDocument2(model, wb,response,filename,"Medical Claims");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("导出全部Medical for Cons方法的servlet"+e);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}
}