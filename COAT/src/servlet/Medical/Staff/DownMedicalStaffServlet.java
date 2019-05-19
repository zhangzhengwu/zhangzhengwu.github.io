package servlet.Medical.Staff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.List;

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
import util.FileUtil;
import util.Util;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.StaffMedicalDao;
import dao.exp.ExpMedicalForStaff;
import dao.impl.StaffMedicalDaoImpl;

@SuppressWarnings("deprecation")
public class DownMedicalStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(DownMedicalStaffServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		 try{
				String staffcode=request.getParameter("staffcode");
				String return_oraginal=request.getParameter("oraginal");
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				StaffMedicalDao sd=new StaffMedicalDaoImpl();
				 ExpMedicalForStaff expcard = new ExpMedicalForStaff();
			//	List<Medical_record_staff> list=new ArrayList<Medical_record_staff>();
				String downFile=Constant.medical_Staff+DateUtils.getDateToday();
				//String downFile="D://To STAFF//"+DateUtils.getDateToday();
				if(FileUtil.directoryExists(downFile)){
					 FileUtil.deleteAll(downFile);
					// new File(downFile).mkdirs();
				}else{
				 new File(downFile).mkdirs();
				}
				 List<String> listString=sd.selectStaffcode(staffcode, return_oraginal, startDate, endDate);
				 LOG.info("Medical Staff personal 文件导出开始...");
					for(int j=0;j<listString.size();j++){	 
				 //String fname =Constant.MEDICALCONSULTANT_NAME;//Excel文件名
				 OutputStream os = new FileOutputStream(downFile+"//"+listString.get(j)+".xls");//取出输出流
				 HSSFWorkbook wb = new HSSFWorkbook();	
				// response.reset();//清空输出流
						//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
						//response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
						//定义输出类型
						//ResultSet res =qcDao.upLoadForConsList(name, staffcode, startDate, endDate);
						ResultSet res=sd.selectResultSet(staffcode, return_oraginal, startDate, endDate);
					if(!Util.objIsNULL(res)){
						HSSFSheet sheet = wb.createSheet("new sheet");
						wb.setSheetName(0, "Medical Claims");
						HSSFRow row=sheet.createRow(0);
						sheet.createFreezePane(0, 1);
						sheet.addMergedRegion(new Region(0,(short)0,0,(short)15));
						expcard.cteateTitleCenterCell(wb,row,(short)0,"CONVOY FINANCIAL SERVICES LIMITED");
						/*for(int h=1;h<15;h++){
							expcard.cteateTitleCenterCell(wb,row,(short)h,"");
						}*/
						expcard.cteateTitleCenterCell(wb,row,(short)15,"");
						row=sheet.createRow(1);
						sheet.createFreezePane(0, 2);
						
						sheet.addMergedRegion(new Region(1,(short)0,1,(short)15));
					
						expcard.cteateTitleCenterThinCell(wb,row,(short)0,"Medical Claims Report");
						for(int h=1;h<=15;h++){
							expcard.cteateTitleCenterThinCell(wb,row,(short)h,"");
						}
						row = sheet.createRow((short)2);
						sheet.createFreezePane(0, 3);
						row.setHeight((short) (20*32));
					 
						expcard.cteatePersonCell(wb,row,(short)0,"Staff Code");
						expcard.cteatePersonCell(wb,row,(short)1,"Name");
						expcard.cteatePersonCell(wb,row,(short)2,"Company");
						expcard.cteatePersonCell(wb,row,(short)3,"Department");
						expcard.cteatePersonCell(wb,row,(short)4,"Grade");
						expcard.cteatePersonCell(wb,row,(short)5,"Plan");
						expcard.cteatePersonCell(wb,row,(short)6,"Type");
						expcard.cteatePersonCell(wb,row,(short)7,"Consultation Date");
						expcard.cteatePersonCell(wb,row,(short)8,"Consulting Fee (HKD)");
						expcard.cteatePersonCell(wb,row,(short)9,"Amount Entitled");
						expcard.cteatePersonCell(wb,row,(short)10,"No. of Terms");
						expcard.cteatePersonCell(wb,row,(short)11,"Month Entitled");
						expcard.cteatePersonCell(wb,row,(short)12,"Normal");
						expcard.cteatePersonCell(wb,row,(short)13,"Special");
						expcard.cteatePersonCell(wb,row,(short)14,"Dental");
						expcard.cteatePersonCell(wb,row,(short)15,"Dental - Regular");
					/*	expcard.cteateTWOTitleCenterCell(wb,row,(short)16,"Return Oraginal");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)17,"Status");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)18,"Reason");*/
					
						expcard.createFixationSheet(res, os,wb,sheet,listString.get(j));
						res.close();
					}
		 }
				
					 out.print("导出成功!地址为:"+downFile.replaceAll("//", "/"));
					 LOG.info("导出Medical Staff personal成功  ! 地址为  ："+downFile.replaceAll("//","/"));
		 }catch(FileNotFoundException f){
				out.print("error:另一个程序正在使用此文件，进程无法访问。");
				LOG.error("导出Medical Staff  personal时 出现    ：  另一个应用程序正在使用此文件，进程无法访问   ");
		}catch(Exception e){
			 LOG.error("导出Medical Staff personal 时出现 ："+e.toString());
			 e.printStackTrace();
		 }finally{
				out.flush();
				out.close();
		 }
	
	}

}
