package servlet.Medical.Staff;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import util.Util;

import dao.StaffMedicalDao;
import dao.exp.ExpMedicalForStaff;
import dao.impl.StaffMedicalDaoImpl;

@SuppressWarnings("deprecation")
public class DownAllMedicalStaffServlet extends HttpServlet {
	Logger log=Logger.getLogger(DownAllMedicalStaffServlet.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("static-access" )
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String staffcode=request.getParameter("staffcode");
		String return_oraginal=request.getParameter("oraginal");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String fname="Medical Claim";
		try{
		
			log.info("导出Medical Staff 全部     开始.....");
			StaffMedicalDao sd=new StaffMedicalDaoImpl();
			 HSSFWorkbook wb = new HSSFWorkbook();	
			 ExpMedicalForStaff expcard = new ExpMedicalForStaff();
				OutputStream os = response.getOutputStream();//取出输出流
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
				//定义输出类型
				ResultSet res=sd.selectAllResultSet(staffcode, return_oraginal, startDate, endDate);
				ResultSet res2=sd.selectMedicalRjectResultSet(staffcode, return_oraginal, startDate, endDate);
				if(!Util.objIsNULL(res)){
					HSSFSheet sheet = wb.createSheet("new sheet");
					wb.setSheetName(0, "Medical Claims");
					HSSFRow row=sheet.createRow(0);
					sheet.createFreezePane(0, 1);
					sheet.addMergedRegion(new Region(0,(short)0,0,(short)18));
					expcard.cteateTitleCenterCell(wb,row,(short)0,"CONVOY FINANCIAL SERVICES LIMITED");
					/*for(int h=1;h<15;h++){
						expcard.cteateTitleCenterCell(wb,row,(short)h,"");
					}*/
					expcard.cteateTitleCenterCell(wb,row,(short)18,"");
					row=sheet.createRow(1);
					sheet.createFreezePane(0, 2);
					sheet.addMergedRegion(new Region(1,(short)0,1,(short)18));
					expcard.cteateTitleCenterThinCell(wb,row,(short)0,"Medical Claims Report");
					for(int h=1;h<19;h++){
						expcard.cteateTitleCenterThinCell(wb,row,(short)h,"");
					}
					row = sheet.createRow((short)2);
					sheet.createFreezePane(0, 3);
					row.setHeight((short) (20*32));
				 
					expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"Staff Code");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"Name");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"Company");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"Department");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"Grade");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"Plan");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"Type");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"Consultation Date");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"Consulting Fee (HKD)");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"Amount Entitled");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)10,"No. of Terms");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)11,"Month Entitled");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)12,"Normal");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)13,"Special");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)14,"Dental");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)15,"Dental - Regular");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)16,"Return Oraginal");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)17,"Status");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)18,"Reason");
				
					//expcard.createFixationSheet(res, os,wb,sheet);
					expcard.createFixationMedicalSheet(res,res2, os,wb,sheet);
					res.close();
				}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("导出Medical  Staff  全部  时出现 ："+e.toString());
		}finally{
		 
		}
	}

}
