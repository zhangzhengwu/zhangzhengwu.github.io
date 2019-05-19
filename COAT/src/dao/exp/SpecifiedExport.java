package dao.exp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DateUtils;
import util.FileUtil;
import util.Util;
import dao.QueryRequstDao;
import dao.impl.QueryRequstDaoImpl;

public class SpecifiedExport {

	Logger logger = Logger.getLogger(SpecifiedExport.class);
	
	public SpecifiedExport() {
	}

	/**
	 * 定时导出NameCard 报表给HK 邮件服务器使用--23点
	 */
	public String timeTaskSpecifiledExport(){
		String result="";
		try{
			Util.printLogger(logger, "开始执行指定任务-导出NameCardExcel文件");
			specifiledExport();
			result="success";
			Util.printLogger(logger, "指定任务-->导出Namecard文件成功!");
		}catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger, "指定任务-->定时导出Namecard出现-->"+e.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("static-access")
	public void specifiledExport() throws Exception{
		String fname="NameCardInfo_Report";
		QueryRequstDao rd=new QueryRequstDaoImpl();
		try{
			String downFile=Util.getProValue("SpecifiedTask.namecard.excel.path")+DateUtils.getDateToday();
			if(FileUtil.directoryExists(downFile)){
				FileUtil.deleteAll(downFile);
			}else{
				new File(downFile).mkdirs();
			}
			OutputStream os = new FileOutputStream(downFile+"\\"+fname+".xls");
			HSSFWorkbook wb = new HSSFWorkbook();
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			//response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
			ExpRequestnew expcard = new ExpRequestnew();
			//ResultSet res=null; 
			//res =rd.ReportConsInfo();// 2014-5-13 10:30:09 King 修改
			Result res=rd.ReportConsInfos();//2015-7-22 11:27:23 king 修改
			if(res!=null){
				HSSFSheet sheet = wb.createSheet();
				wb.setSheetName(0, "Query Request");
				//wb.setSheetName(1, "Query Requests");
				HSSFRow row=sheet.createRow(0);
				sheet.createFreezePane(0, 0);
				expcard.cteateTitleCell(wb,row,(short)0,"Query Request");
				expcard.cteateTitleCell(wb,row,(short)3,"Report Date:");
				expcard.cteateTitleCell(wb,row,(short)4,DateUtils.getNowDate());
				row=sheet.createRow(2);
				sheet.createFreezePane(0, 2);
				expcard.cteateTitleCell(wb,row,(short)0,"Year"+DateUtils.getYear());
				row = sheet.createRow((short)4);
				sheet.createFreezePane(0, 5);
				/****************************设置Excel的列头*************************************************/
				expcard.cteateTitleCell(wb,row,(short)0,"Location");
				expcard.cteateTitleCell(wb,row,(short)1,"Company Name");
				expcard.cteateTitleCell(wb, row, (short)2,"Layout_type");
				//expcard.cteateTitleCell(wb, row, (short)3,"Elite Team");
				expcard.cteateTitleCell(wb,row,(short)3,"Staff_Code");
				expcard.cteateTitleCell(wb,row,(short)4,"Name");
				expcard.cteateTitleCell(wb,row,(short)5,"Name_in_Chinese");
				expcard.cteateTitleCell(wb,row,(short)6,"Title with Department in English");
				expcard.cteateTitleCell(wb,row,(short)7,"Title with Department in Chinese");
				expcard.cteateTitleCell(wb,row,(short)8,"External Title with Department in English");
				expcard.cteateTitleCell(wb,row,(short)9,"External Title with Department in Chinese");
				/*				expcard.cteateTitleCell(wb,row,(short)11,"English Academic Title");
				expcard.cteateTitleCell(wb,row,(short)12,"Chinese Academic Title");*/
				expcard.cteateTitleCell(wb,row,(short)10,"English Academic & Professional Title");
				expcard.cteateTitleCell(wb,row,(short)11,"Chinese Academic & Professional Title");
				expcard.cteateTitleCell(wb,row,(short)12,"T.R. Reg. No.");
				expcard.cteateTitleCell(wb,row,(short)13,"CE No.");
				expcard.cteateTitleCell(wb,row,(short)14,"MPF No.");
				expcard.cteateTitleCell(wb,row,(short)15,"E-mail");
				expcard.cteateTitleCell(wb,row,(short)16,"Direct Line");
				expcard.cteateTitleCell(wb,row,(short)17,"Fax");
				expcard.cteateTitleCell(wb,row,(short)18,"Mobile Phone Number");
				expcard.cteateTitleCell(wb,row,(short)19,"Submit Date");
				/**  sheet 2**/
				//expcard.createAutoFixationSheet(res, os,wb,sheet);
				expcard.createLastPressionalTitleSheet(res, os,wb,sheet);
				//expcard.createFixationSheet(res, os, wb, sheet);
				os.flush();
				os.close();
				res=null;
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			
		}
	}
}
