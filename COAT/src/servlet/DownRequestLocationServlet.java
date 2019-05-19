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
import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DateHelper;
import util.DateUtils;
import util.FileUtil;
import dao.LocationDao;
import dao.QueryRequstDao;
import dao.exp.ExpRequestnew;
import dao.impl.LocationDaoImpl;
import dao.impl.QueryRequstDaoImpl;
/**
 * 
 * @author king.xu
 *
 */
public class DownRequestLocationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger log=Logger.getLogger(DownRequestLocationServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		log.info(request.getSession().getAttribute("adminUsername").toString()+"对Roymark_Consultant进行导出操作");
		StringBuffer fname=new StringBuffer("NC_");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String nocode  = request.getParameter("nocode");
		String ET_select  = request.getParameter("ET");
		String layout_select  = request.getParameter("layout_select");

		ExpRequestnew expcard = new ExpRequestnew();
		QueryRequstDao rd=new QueryRequstDaoImpl();
		fname.append(DateHelper.getMonthday());

		try {	String downFile="";
				if(ET_select.equals("Y"))
					downFile=Constant.EliteTeamReport+DateUtils.getDateToday();	
				else
					downFile=Constant.LocationReport+DateUtils.getDateToday();
			if(FileUtil.directoryExists(downFile)){
				FileUtil.deleteAll(downFile);
				// new File(downFile).mkdirs();
			}else{
				new File(downFile).mkdirs();
			}
			LocationDao ld=new LocationDaoImpl();
			
			ResultSet res=null;
			if(!ET_select.equals("Y")){
			List<String> list =ld.queryLocationName();
			//res =rd.queryRequstListSet(ET_select,layout_select,name,code,startDate, endDate, location, urgentCase,payer,nocode/*CAM_only, CFS_only, CIS_only, CCL_only, CFSH_only, CMS_only, CFG_only, Blank_only*/);
			Result r=rd.queryRequstForResult(ET_select, layout_select, name, code, startDate, endDate, location, urgentCase, payer, nocode);
				for(int i=0;i<list.size();i++){
					//fname.append();
					//定义输出类型
					if(r!=null){
							OutputStream os = new FileOutputStream(downFile+"//"+fname+("_"+list.get(i)+"(Consultant)")+".xls");
							response.reset();//清空输出流
							HSSFWorkbook wb = new HSSFWorkbook();
							//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
							response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
						
							HSSFSheet sheet = wb.createSheet();
							wb.setSheetName(0, "Query Request");
							//wb.setSheetName(1, "Query Requests");
							HSSFRow row=sheet.createRow(0);
							sheet.createFreezePane(0, 0);
							expcard.cteateTitleCell(wb,row,(short)0,"Query Request");
							expcard.cteateTitleCell(wb,row,(short)3,"UpDate:");
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
							expcard.cteateTitleCell(wb,row,(short)15,"CIB Reg. No.");
							expcard.cteateTitleCell(wb,row,(short)16,"E-mail");
							expcard.cteateTitleCell(wb,row,(short)17,"Direct Line");
							expcard.cteateTitleCell(wb,row,(short)18,"Fax");
							expcard.cteateTitleCell(wb,row,(short)19,"Mobile Phone Number");
							expcard.cteateTitleCell(wb,row,(short)20,"Quantity");
							expcard.cteateTitleCell(wb,row,(short)21,"Submit Date");
		
							/**  sheet 2**/
							//expcard.createFixationLocationSheet(res, os,wb,sheet,list.get(i));
							expcard.createFixationLocationForResult(r, os,wb,sheet,list.get(i));
							//expcard.createFixationSheet(res, os, wb, sheet);
						}
	
				}
			}else{
				//fname.append();
				OutputStream os = new FileOutputStream(downFile+"//"+fname+"_Elite Team.xls");
				response.reset();//清空输出流
				HSSFWorkbook wb = new HSSFWorkbook();
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
				//定义输出类型
				res =rd.queryRequstListSet(ET_select,layout_select,name,code,startDate, endDate, location, urgentCase,payer,nocode/*CAM_only, CFS_only, CIS_only, CCL_only, CFSH_only, CMS_only, CFG_only, Blank_only*/);
					if(res!=null){
						HSSFSheet sheet = wb.createSheet();
						wb.setSheetName(0, "Query Request");
						//wb.setSheetName(1, "Query Requests");
						HSSFRow row=sheet.createRow(0);
						sheet.createFreezePane(0, 0);
						expcard.cteateTitleCell(wb,row,(short)0,"Query Request");
						expcard.cteateTitleCell(wb,row,(short)3,"UpDate:");
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
						expcard.cteateTitleCell(wb,row,(short)15,"KHCIB No.");
						expcard.cteateTitleCell(wb,row,(short)16,"E-mail");
						expcard.cteateTitleCell(wb,row,(short)17,"Direct Line");
						expcard.cteateTitleCell(wb,row,(short)18,"Fax");
						expcard.cteateTitleCell(wb,row,(short)19,"Mobile Phone Number");
						expcard.cteateTitleCell(wb,row,(short)20,"Quantity");
						expcard.cteateTitleCell(wb,row,(short)21,"Submit Date");
	
						/**  sheet 2**/
						//expcard.createFixationLocationSheet(res, os,wb,sheet);
						expcard.createFixationSheet(res, os, wb, sheet);
						res.close();
				}
			}
			out.print("导出成功!地址为:"+downFile.replaceAll("//", "/"));
		/*	 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); 
			 StringSelection texts=new StringSelection(downFile.replaceAll("//", "/"));
			 clipboard.setContents(texts, null);*/
		}catch(FileNotFoundException f){
			out.print("error:另一个程序正在使用此文件，进程无法访问。");
			log.error("error:另一个程序正在使用此文件，进程无法访问。");
		} catch (Exception e) {
			log.error("导出方法的servlet"+e);
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
}
