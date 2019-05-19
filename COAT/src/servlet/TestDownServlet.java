package servlet;

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

import dao.QueryMedicalDao;
import dao.exp.ExpMedicalForCons;
import dao.impl.QueryMedicalDaoImpl;

import util.Constant;
import util.DateUtils;
import util.FileUtil;

@SuppressWarnings("deprecation")
public class TestDownServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(TestDownServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	
	
	@SuppressWarnings( "static-access" )
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		 String startDate = request.getParameter("startDate");
		 String endDate = request.getParameter("endDate");
		 String staffcode = request.getParameter("code");
		 String name = request.getParameter("name");
		 ExpMedicalForCons expcard = new ExpMedicalForCons();
		 QueryMedicalDao qcDao = new QueryMedicalDaoImpl();
		try{
		 
			String downFile=Constant.MedicalConsultant+DateUtils.getDateToday();
			if(FileUtil.directoryExists(downFile)){
				 FileUtil.deleteAll(downFile);
				// new File(downFile).mkdirs();
			}else{
			 new File(downFile).mkdirs();
			}
			List<String> li=qcDao.selectConsList(name, staffcode, startDate, endDate);
			log.info("对Medical Consultant进行的导出   导出路径为       ：=="+downFile);
			for(int j=0;j<li.size();j++){
			
			OutputStream os = new FileOutputStream(downFile+"//"+li.get(j)+".xls");
			//定义输出类型
			ResultSet res =qcDao.upLoadForConsList(name, staffcode, startDate, endDate);
		        // 声明一个工作薄
		        HSSFWorkbook wb = new HSSFWorkbook();
		    	if(res!=null){
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
					expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"SF");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"Date");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"HKD");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"Amount");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"Terms");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"Month");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"Mormal");
					expcard.cteateTWOTitleCenterCell(wb,row,(short)10,"Special");
					
					expcard.createFixationSheet(res, os,wb,sheet,li.get(j));
					res.close();
		    	}
			}
			 out.print("导出成功!地址为:"+downFile.replaceAll("//", "/"));
			/*  服务端复制
			 *  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); 
			 StringSelection texts=new StringSelection(downFile.replaceAll("//", "/"));
			 clipboard.setContents(texts, null);*/
		}catch(FileNotFoundException f){
			log.error("对 Medical Consultant进行导出时出现：===Error:另一个了应用程序正在使用此文件，进程无法访问!");
			out.print("error:另一个程序正在使用此文件，进程无法访问。");
		}catch(Exception e){
			e.printStackTrace();
			log.error("对Medical Consultant 进行导出时出现："+e.toString());
			out.print("error:"+e.toString());
		}
	
	}

}
