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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import util.Constant;
import util.DateUtils;
import util.FileUtil;
import util.Util;
import dao.StaffMedicalDao;
import dao.exp.ExpMedicalForStaff;
import dao.impl.StaffMedicalDaoImpl;

@SuppressWarnings({ "unused", "deprecation" })
public class DownForFADMedicalStaffServlet extends HttpServlet {
	Logger log=Logger.getLogger(DownForFADMedicalStaffServlet.class);
	private static final long serialVersionUID = 1L;
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
			 log.info("导出Medical for FAD  开始...");
				String staffcode=request.getParameter("staffcode");
				String return_oraginal=request.getParameter("oraginal");
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				StaffMedicalDao sd=new StaffMedicalDaoImpl();
				 ExpMedicalForStaff expcard = new ExpMedicalForStaff();
			//	List<Medical_record_staff> list=new ArrayList<Medical_record_staff>();
				String downFile=Constant.medical_HRD+DateUtils.getDateToday();
				//String downFile="D://For HRD//"+DateUtils.getDateToday();
				if(FileUtil.directoryExists(downFile)){
					 FileUtil.deleteAll(downFile);
					// new File(downFile).mkdirs();
				}else{
				 new File(downFile).mkdirs();
				}
				 List<String> listString=sd.selectCompany(staffcode, return_oraginal, startDate, endDate);
					for(int j=0;j<listString.size();j++){	 
				// String fname =Constant.MEDICALCONSULTANT_NAME;//Excel文件名
				 OutputStream os = new FileOutputStream(downFile+"//"+listString.get(j)+".xls");//取出输出流
				 HSSFWorkbook wb = new HSSFWorkbook();	
						ResultSet res=sd.selectResultSetForFAD(staffcode, return_oraginal, startDate, endDate);
					if(!Util.objIsNULL(res)){
						HSSFSheet sheet = wb.createSheet("new sheet");
						wb.setSheetName(0, "Medical Claims");
						HSSFRow row=sheet.createRow(0);
						//sheet.createFreezePane(0, 3);//下劃綫
					 row.setHeight((short) (20*15));
						expcard.cteateBlueCell(wb,row,(short)0,"Company");
						expcard.cteateBlueCell(wb,row,(short)1,"Staff Code");
						expcard.cteateBlueCell(wb,row,(short)2,"Name");
						expcard.cteateBlueCell(wb,row,(short)3,"Amount Entitled");
						expcard.createFixCompanySheet(res, os,wb,sheet,listString.get(j));
						res.close();
					}
		 }
					 out.print("导出成功!地址为:"+downFile.replaceAll("//", "/"));
					 log.info("导出成功 ,地址为："+downFile.replaceAll("//","/"));
		 }catch(FileNotFoundException f){
			 log.error("导出Medical Staff  For FAD 时出现   ：  另外一个应用程序正在使用此文件，进程无法访问.");
				out.print("error:另一个程序正在使用此文件，进程无法访问。");
		}catch(Exception e){
			 log.error("导出Medical  Staff For  FAD 时出现  ："+e.toString());
			 e.printStackTrace();
		 }finally{
				out.flush();
				out.close();
		 }
	}
}
