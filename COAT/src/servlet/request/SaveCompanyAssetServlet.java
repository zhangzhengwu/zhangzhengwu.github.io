package servlet.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.Region;

import util.Constant;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.C_CompanyDao;
import dao.EPaymentDao;
import dao.impl.C_CompanyDaoImpl;
import dao.impl.EPaymentDaoImpl;
import entity.C_Companyasset;
import entity.C_CompanyassetDetail;
import entity.C_CompanyassetItem;
import entity.C_EPayment_List;

public class SaveCompanyAssetServlet extends HttpServlet {

	/**
	 * Wilson 2014-5-14 15:02:34
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SaveCompanyAssetServlet.class);
	String user=null;
	Map<String,HSSFCellStyle> styleMap=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mothed = request.getParameter("method");
		if (mothed.equals("down")) {
			doPost(request, response);
		}else {
			PrintWriter out = response.getWriter();
			out.print("You have no legal power!");
			out.flush();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		String mothed = request.getParameter("method");
		try{
		user=(String) request.getSession().getAttribute("adminUsername");
			if (mothed.toLowerCase().trim().equals("add")) {
				add(request,response);
			}else if (mothed.toLowerCase().trim().equals("complete")) {
				complete(request,response);
			}else if(mothed.toLowerCase().trim().equals("ready")){
				Ready(request,response);
			}else if (mothed.toLowerCase().trim().equals("returned")) {
				returned(request,response);
			}else if (mothed.toLowerCase().trim().equals("select")) {
				select(request,response);
			}else if (mothed.toLowerCase().trim().equals("delete")) {
				del(request,response);
			}else if (mothed.toLowerCase().trim().equals("detail")) {
				Detail(request,response);
			}else if (mothed.toLowerCase().trim().equals("down")) {
				down(request,response);
			}else if (mothed.toLowerCase().trim().equals("finditem")) {
				finditem(request,response);
			}else if (mothed.toLowerCase().trim().equals("query")) {
				query(request,response);
			}else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("CompanyAsset==>"+mothed+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("CompanyAsset==>"+mothed+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		}finally{
			mothed=null;
		}
			
		
	}
	
	/**
	 * 根据条件分页查询c_companyasset_item
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void query(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		C_CompanyDao dao=new C_CompanyDaoImpl();
		Page page=new Page();
		List<C_CompanyassetItem> list=new ArrayList<C_CompanyassetItem>();
		List list1=new ArrayList();
		try{
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String Itemcode=request.getParameter("productcode");
			String Itemname=request.getParameter("productname");
			String sfyx=request.getParameter("sfyx");
			
			/******************************************分页代码***********************************************/
			    page.setAllRows(dao.getAllRow(Itemcode,Itemname, startDate, endDate,sfyx));//总的行数
				page.setPageSize(15);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			/************************************************************************************************/
			list=dao.queryProduct(Itemcode,Itemname, startDate, endDate,sfyx,page.getCurPage(),page.getPageSize());
			
			list1.add(0,list);
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总行数
			
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("selects==>Search Exception"+e);
		}finally{
			 dao =null;
			 page=null;
			 list=null;
			 list1=null;
			out.flush();
			out.close();
		}
	}
	
	private void finditem(HttpServletRequest request,HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		String edate=request.getParameter("edate");
		PrintWriter out=null;
		
		try{
			out=response.getWriter();
			JSONArray jsons=JSONArray.fromObject(aDao.findCompanyBycode(edate));
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("CompanyAsset==>findItem Exception "+e);
			out.print("Exception:"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
		
	}

	@SuppressWarnings("deprecation")
	private void down(HttpServletRequest request, HttpServletResponse response) {
		 HSSFWorkbook book = new HSSFWorkbook();	
			C_CompanyDao aDao=new C_CompanyDaoImpl();
			OutputStream os;
			SimpleDateFormat sdfus =null;
			 SimpleDateFormat sdf = null;
			try {
				sdfus = new SimpleDateFormat("dd-MMM-yyyy", Locale.US); 
				sdf = new SimpleDateFormat("MMM", Locale.US);
				HSSFCellStyle cellstyle=null;
				Map<String ,Font> fontMap=new HashMap<String,Font>();
				 styleMap=new HashMap<String,HSSFCellStyle>();
					Font fonts = book.createFont();
					fonts.setFontHeightInPoints((short)10); //字体大小
					fonts.setFontName("Times New Roman");
					fonts.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
					fontMap.put("BOLD", fonts);
					
					
				
						cellstyle= book.createCellStyle();
						cellstyle.setWrapText(true);// 自动换行
						cellstyle.setFont(fontMap.get("BOLD"));
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());//黄色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						cellstyle.setBorderBottom((short)1);
						cellstyle.setBorderTop((short)1);
						cellstyle.setBorderLeft((short)1);
						cellstyle.setBorderRight((short)1);
					styleMap.put("cteateYellowTitleCell", cellstyle);
					
					cellstyle= book.createCellStyle();
					
						cellstyle.setFont(fontMap.get("BOLD"));
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());//黄色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						cellstyle.setWrapText(true);// 自动换行
						cellstyle.setBorderBottom((short)1);
						cellstyle.setBorderTop((short)1);
						cellstyle.setBorderLeft((short)1);
						cellstyle.setBorderRight((short)1);
					styleMap.put("cteateOrangeNumTitleCell", cellstyle);
					
						cellstyle= book.createCellStyle();
						cellstyle.setFont(fontMap.get("BOLD"));
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());//黄色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						
						cellstyle.setWrapText(true);// 自动换行
						cellstyle.setBorderBottom((short)1);
						cellstyle.setBorderTop((short)1);
						cellstyle.setBorderLeft((short)1);
						cellstyle.setBorderRight((short)1);
					styleMap.put("cteateOrangeTitleCell", cellstyle);
						cellstyle= book.createCellStyle();
						cellstyle.setFont(fontMap.get("BOLD"));
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());//黄色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						cellstyle.setWrapText(true);// 自动换行
						cellstyle.setBorderBottom((short)1);
						cellstyle.setBorderTop((short)1);
						cellstyle.setBorderLeft((short)1);
						cellstyle.setBorderRight((short)1);
					styleMap.put("cteateSEA_GREENTitleCell", cellstyle);

						cellstyle= book.createCellStyle();
						cellstyle.setWrapText(true);// 自动换行
						cellstyle.setFont(fontMap.get("BOLD"));
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
					styleMap.put("cteateTHRTitleCell", cellstyle);
						cellstyle= book.createCellStyle();
						cellstyle.setFont(fontMap.get("BOLD"));
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
					styleMap.put("cteateTWOTitleCell", cellstyle);
						cellstyle= book.createCellStyle();
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setBorderBottom((short)1);
						cellstyle.setBorderTop((short)1);
						cellstyle.setBorderLeft((short)1);
						cellstyle.setBorderRight((short)1);
						cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					styleMap.put("cteateReasonCell", cellstyle);
						cellstyle= book.createCellStyle();
						cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setBorderBottom((short)1);
						cellstyle.setBorderTop((short)1);
						cellstyle.setBorderLeft((short)1);
						cellstyle.setBorderRight((short)1);
						cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					styleMap.put("cteateTotalCell", cellstyle);
						cellstyle= book.createCellStyle();
						cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					styleMap.put("cteateNumCell", cellstyle);
						cellstyle= book.createCellStyle();
						cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
						cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
						cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					styleMap.put("cteateTitleCell", cellstyle);
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				os = response.getOutputStream();
				//取出输出流
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename=Request for Company Asset_"+DateUtils.Ordercode()+".xls");
				
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				String staffcode=request.getParameter("staffcode");
				String staffname=request.getParameter("staffname");
				String refno=request.getParameter("refno");
				String status=request.getParameter("status");
				String collectionDate=request.getParameter("collectionDate");
				String returnDate=request.getParameter("returnDate");
				HSSFSheet sheet = book.createSheet("Company Asset Borrow");
				HSSFRow row=sheet.createRow(0);
				//sheet.createFreezePane(0, 1);
				sheet.addMergedRegion(new Region(0,(short)0,0,(short)3));
				cteateTitleCenterCell(book,row,(short)0,"Convoy Financial Services Limited");
				 
				row=sheet.createRow(1);
			
				//sheet.createFreezePane(0, 2);
				sheet.addMergedRegion(new Region(1,(short)0,1,(short)3));
				cteateTWOCell(book,row,(short)0,"Request for Company Asset");
				cteateSEA_GREENTitleCell(book,row,(short)8,"Item Code");
				sheet.addMergedRegion(new Region(1,(short)1,1,(short)3));
				row.setHeight((short)400);
				sheet.setColumnWidth(0,(short)35.7*120);//设置列宽 这里乘以35.7是因为这里设置列的宽度不是以像素进和设置的，乘以35.7后接进15个像素
				sheet.setColumnWidth(1,(short)35.7*120);//
				sheet.setColumnWidth(2,(short)35.7*120);//
				sheet.setColumnWidth(3,(short)35.7*120);//
				sheet.setColumnWidth(4,(short)35.7*120);//
				sheet.setColumnWidth(5,(short)35.7*120);//
				sheet.setColumnWidth(6,(short)35.7*120);//
				sheet.setColumnWidth(7,(short)35.7*120);//
				sheet.setColumnWidth(8,(short)35.7*120);// 
				
				sheet.setColumnWidth(9,(short)35.7*100);//
				sheet.setColumnWidth(10,(short)35.7*100);//
				sheet.setColumnWidth(11,(short)35.7*100);//
				sheet.setColumnWidth(12,(short)35.7*100);//
				sheet.setColumnWidth(13,(short)35.7*100);//
				sheet.setColumnWidth(14,(short)35.7*100);//
				sheet.setColumnWidth(15,(short)35.7*100);//
				sheet.setColumnWidth(16,(short)35.7*100);//
				sheet.setColumnWidth(17,(short)35.7*100);//
				sheet.setColumnWidth(18,(short)35.7*100);//
				sheet.setColumnWidth(19,(short)35.7*100);//
				sheet.setColumnWidth(20,(short)35.7*100);//
				sheet.setColumnWidth(21,(short)35.7*100);//
				sheet.setColumnWidth(22,(short)35.7*100);//
				sheet.setColumnWidth(23,(short)35.7*100);//
				sheet.setColumnWidth(24,(short)35.7*100);//
				sheet.setColumnWidth(25,(short)35.7*100);//
				sheet.setColumnWidth(26,(short)35.7*100);//
				sheet.setColumnWidth(27,(short)35.7*100);//
				sheet.setColumnWidth(28,(short)35.7*100);//
				
				
				
			/*	
				
				
				cteateTWOCell(book, row, (short)8, "Item Code"); 
				cteateTWOCell(book, row, (short)9, "CA001"); 
				cteateTWOCell(book, row, (short)10, "CA002"); 
				cteateTWOCell(book, row, (short)11, "CA003");
				cteateTWOCell(book, row, (short)12, "CA004");
				cteateTWOCell(book, row, (short)13, "CA005");
				cteateTWOCell(book, row, (short)14, "CA006"); 
				cteateTWOCell(book, row, (short)15, "CA007"); 
				cteateTWOCell(book, row, (short)16, "CA008"); 
				cteateTWOCell(book, row, (short)17, "CA009"); 
				cteateTWOCell(book, row, (short)18, "CA010"); 
				cteateTWOCell(book, row, (short)19, "CA011");
				cteateTWOCell(book, row, (short)20, "CA012");
				cteateTWOCell(book, row, (short)21, "CA013");
				cteateTWOCell(book, row, (short)22, "CA014");
				cteateTWOCell(book, row, (short)23, "CA015");
				cteateTWOCell(book, row, (short)24, "CA016");
				cteateTWOCell(book, row, (short)25, "CA017");
				cteateTWOCell(book, row, (short)26, "CA018");
				cteateTWOCell(book, row, (short)27, "CA019");
				cteateTWOCell(book, row, (short)28, "CA020");
								
				
				

				row=sheet.createRow(2);
				
				
				
				
				cteateTWOTitleCenterCell(book, row, (short)0, "Month");
				cteateTWOTitleCenterCell(book, row, (short)1, "Request date");
				cteateTWOTitleCenterCell(book, row, (short)2, "Order Number");
				cteateTWOTitleCenterCell(book, row, (short)3, "Staff Code");
				cteateTWOTitleCenterCell(book, row, (short)4, "Name");
				cteateTWOTitleCenterCell(book, row, (short)5, "Staff / Consultant");
				cteateTWOTitleCenterCell(book, row, (short)6, "Date of collection"); 
				cteateTWOTitleCenterCell(book, row, (short)7, "Date of Return"); 
				cteateTWOTitleCenterCell(book, row, (short)8, "Description"); 
				cteateTWOTitleCenterCell(book, row, (short)9, "Company Logo Banner"); 
				cteateTWOTitleCenterCell(book, row, (short)10, "Company Logo Banner"); 
				cteateTWOTitleCenterCell(book, row, (short)11, "Company Logo Banner");	
				cteateTWOTitleCenterCell(book, row, (short)12, "Company Logo Banner");
				cteateTWOTitleCenterCell(book, row, (short)13, "Company Logo Banner");
				cteateTWOTitleCenterCell(book, row, (short)14, "Company Logo Banner"); 
				cteateTWOTitleCenterCell(book, row, (short)15, "Company Logo Banner"); 
				cteateTWOTitleCenterCell(book, row, (short)16, "Company Logo Banner"); 
				cteateTWOTitleCenterCell(book, row, (short)17, "易拉架"); 
				cteateTWOTitleCenterCell(book, row, (short)18, "Info Board"); 
				cteateTWOTitleCenterCell(book, row, (short)19, "Info Board");
				cteateTWOTitleCenterCell(book, row, (short)20, "Info Board");
				cteateTWOTitleCenterCell(book, row, (short)21, "Info Board");
				cteateTWOTitleCenterCell(book, row, (short)22, "Sales Booth");
				cteateTWOTitleCenterCell(book, row, (short)23, "Backdrop");
				cteateTWOTitleCenterCell(book, row, (short)24, "Writing Clip Board");
				cteateTWOTitleCenterCell(book, row, (short)25, "方向指示牌");
				cteateTWOTitleCenterCell(book, row, (short)26, "指示牌");
				cteateTWOTitleCenterCell(book, row, (short)27, "分隔帶座");
				cteateTWOTitleCenterCell(book, row, (short)28, "現金流 game");
				
				
				
				
				
				
				
				List<C_Companyasset> list= aDao.downCompanyList(startDate, endDate, staffcode, staffname, refno, status,collectionDate,returnDate);
				for (int i = 1; i <= list.size(); i++) {
					C_Companyasset cons = list.get(i-1);
					int num = i+2;
					row = sheet.createRow(num);
					row.createCell(0).setCellValue(DateUtils.getMonth(cons.getCreateDate()));
					row.createCell(1).setCellValue(DateUtils.strChToUsDMY(cons.getCreateDate().substring(0, 10)));
					row.createCell(2).setCellValue(cons.getRefno());
					row.createCell(3).setCellValue(cons.getStaffcode());
					row.createCell(4).setCellValue(cons.getStaffname());
					row.createCell(5).setCellValue(cons.getUserType());
					row.createCell(6).setCellValue(DateUtils.strChToUsDMY(cons.getCollectionDate()));
					row.createCell(7).setCellValue(DateUtils.strChToUsDMY(cons.getReturnDate()));
					
					List<C_CompanyassetDetail> listdetail= aDao.findCompanydetailBycode(cons.getRefno());
					for (int j = 1; j <= listdetail.size(); j++) {
						C_CompanyassetDetail detail = listdetail.get(j-1);
						int codenum = Integer.valueOf(detail.getItemcode().substring(detail.getItemcode().length()-2, detail.getItemcode().length()));
						row.createCell(8+codenum).setCellValue(detail.getNum());
					}
					
				}
				*/
				
				C_CompanyDao cd=new C_CompanyDaoImpl();
				Map<String,Map<String,String>> zhu=null;
				Map<String,String> zi=null;
				 Result rss=null;
				 Result store=null;
				 Result stores=null;
				
					
					 rss=cd.downReportingForCompanyAsset(startDate, endDate, staffcode, staffname, refno, status,collectionDate,returnDate);
					 store=cd.findStore(startDate, endDate);
					 stores=cd.chargeStore(startDate,endDate);//获取库存使用量
					 zhu= zhuanhuan_ref(stores);//库存使用量Map的形式
					 zi=new HashMap<String,String>();
				
				HSSFRow row_2=sheet.createRow(2);
			
				cteateTWOTitleCenterCell(book, row_2, (short)0, "Month");
				cteateTWOTitleCenterCell(book, row_2, (short)1, "Request date");
				cteateTWOTitleCenterCell(book, row_2, (short)2, "Order Number");
				cteateTWOTitleCenterCell(book, row_2, (short)3, "Staff Code");
				cteateTWOTitleCenterCell(book, row_2, (short)4, "Name");
				cteateTWOTitleCenterCell(book, row_2, (short)5, "Staff / Consultant");
				cteateTWOTitleCenterCell(book, row_2, (short)6, "Date of collection"); 
				cteateTWOTitleCenterCell(book, row_2, (short)7, "Date of Return"); 
				cteateYellowTitleCell(book,row_2,(short)8,"Description");
						if(store!=null && store.getRowCount()!=0){  
					        for(int i=0;i<store.getRowCount();i++){  
					        	Map<String,Object> rows = store.getRows()[i];
					            cteateSEA_GREENTitleCell(book,row,(short)(9+i),rows.get("procode").toString());
					            cteateYellowTitleCell(book,row_2,(short)(9+i),rows.get("englishname").toString());
					            sheet.setColumnWidth((short)(9+i), 100*80); 
					        }
					        
						}
					
						 sheet.setColumnWidth((short)0, 100*20); 
						 sheet.setColumnWidth((short)1, 100*40); 
						 sheet.setColumnWidth((short)2, 100*42); 
						 sheet.setColumnWidth((short)3, 100*40); 
						 sheet.setColumnWidth((short)4, 100*80); 
						 sheet.setColumnWidth((short)5, 100*50); 
						 sheet.setColumnWidth((short)6, 100*50); 
						 sheet.setColumnWidth((short)7, 100*50); 
						 sheet.setColumnWidth((short)8, 100*50); 
						 sheet.setColumnWidth((short)9, 100*110); 
						 sheet.setColumnWidth((short)10, 100*110); 
						
						
						 //遍历Result
						if(rss!=null && rss.getRowCount()!=0){  
						        for(int i=0;i<rss.getRowCount();i++){  
						            Map rows = rss.getRows()[i];
						        	HSSFRow row6=sheet.createRow(2+i+1);
						        	row6.setHeight((short)300);
							        cteateTitleCell(book,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
							        cteateTitleCell(book,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
							        cteateTitleCell(book,row6,(short)2,rows.get("refno").toString());
							        cteateTitleCell(book,row6,(short)3,rows.get("staffcode").toString());
							        cteateTitleCell(book,row6,(short)4,rows.get("staffname").toString());
							        cteateTitleCell(book,row6,(short)5,rows.get("userType").toString());
							        cteateTitleCell(book,row6,(short)6,rows.get("collectionDate").toString());
							        cteateTitleCell(book,row6,(short)7,rows.get("returnDate").toString());
							        cteateTitleCell(book,row6,(short)8,rows.get("eventName").toString());
							        zi=zhu.get(rows.get("refno").toString());//获取refno下所有产品
							      if(!Util.objIsNULL(zi)){//使用情况不为空
									        Set sets =zi.keySet();
									        int num=1;
									        for(Object key:sets){//遍历使用详细
									        	 for(int h=0;h<store.getRowCount();h++){//遍历所有产品
									        		 Map rowss = store.getRows()[h];
									        		 if(key.equals(rowss.get("procode").toString())){
									        			 
									        			 cteateTotalCell(book,row6,(short)(h+9),"1");
									        		 }else{
									        			 if(num==1){
									        				 cteateReasonCell(book,row6,(short)(h+9),"");
									        			 }
									        		 }
									        	 }
									        	 num++;
									        }
									        
							        }else{
							        	 for(int h=0;h<store.getRowCount();h++){
							        		 cteateReasonCell(book,row6,(short)(h+9),"");
							        	 }
							        }
						        }  
						}
				book.write(os);
				os.flush();
				os.close();
				log.info(user+"==>CompanyAsset==>Export=="+DateUtils.getNowDateTime());
			} catch (IOException e) {
				e.printStackTrace();
				log.error("CompanyAsset==>Export Exception"+e);
			}catch(Exception e){
				e.printStackTrace();
				log.error("CompanyAsset==>Export Exception"+e);
			}
	}
	
	public void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTitleCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateSEA_GREENTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateSEA_GREENTitleCell"));
	}

	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateYellowTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateYellowTitleCell"));
		
	}
	@SuppressWarnings("deprecation")
	public void cteateTotalCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		cell.setCellStyle(styleMap.get("cteateTotalCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateTWOTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTWOTitleCell"));
	}
	@SuppressWarnings("deprecation")
	public void cteateReasonCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateReasonCell"));		
	}
	/**
	 * 将数据库Result转为Map 形式存储 for Company Asset
	 * @param store
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	Map<String,Map<String,String>> zhuanhuan_ref(Result store) throws Exception{
		Map<String,Map<String,String>> map2=new HashMap<String,Map<String,String>>();
		Map<String,String> map3=new HashMap<String,String>();
		String before=null;
		Map<String,Object> rows=null;
		try{
			if(store!=null && store.getRowCount()!=0){ 
		        for(int i=0;i<store.getRowCount();i++){  
		            rows = store.getRows()[i];
		        	//before 不为null,两个不相等，或者为最后一个
		            	if((!Util.objIsNULL(before)&&!before.equals(rows.get("refno").toString()))||i==store.getRowCount()-1){//两个相等
		            		map2.put(before, map3);
		    				
		    				 if(i==store.getRowCount()-1){
		    					 if(!before.equals(rows.get("refno").toString())){
		    						 map3=new HashMap<String, String>();
		    					 }
		            			 before=rows.get("refno").toString();
		 	    				map3.put(rows.get("itemcode").toString(),rows.get("num").toString());
		 	    				map2.put(before, map3);
		 	    				//before=null;
		            		 }
		    				 map3=new HashMap<String, String>();
		    			}
		    				before=rows.get("refno").toString();
		    				map3.put(rows.get("itemcode").toString(),rows.get("num").toString());
		        }
			}else{
				map2=null;
			}
		}catch (Exception e) {
			throw new Exception("转换CompanyAsset 数据时出现异常=="+e.getMessage());
		}finally{
			rows=null;
			map3=null;
			before=null;
		}
		return map2;
		
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void Detail(HttpServletRequest request, HttpServletResponse response){
		
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		List<C_CompanyassetDetail>  ccList= new ArrayList<C_CompanyassetDetail>();
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			ccList=aDao.findCompanydetailBycode(refno);
			List list1=new ArrayList();
			list1.add(0,ccList); 
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("CompanyAsset==>Detail Exception"+e);
			out.print("Exception :"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}
	private void del(HttpServletRequest request, HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type= Constant.C_deleted;
			int num=aDao.deleted(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Success!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("CompanyAsset==>Deleted Exception"+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	private void select(HttpServletRequest request, HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		 Page page=new Page();
		 List<C_Companyasset> list=new ArrayList<C_Companyasset>();
		try{
			out=response.getWriter();
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			String collectionDate=request.getParameter("collectionDate");
			String returnDate=request.getParameter("returnDate");
			page.setAllRows(aDao.getRow(startDate, endDate, staffcode, staffname, refno, status,collectionDate,returnDate));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			list=aDao.findCompanyList(startDate, endDate, staffcode, staffname, refno, collectionDate,returnDate,status, page);
			List list1=new ArrayList();
			list1.add(0,list);//数据存放
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总条数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("CompanyAsset==>Search Exception"+e);
			out.print("Search Exception :"+e.getMessage());
		}finally{
			list=null;
			out.flush();
			out.close();
		}
		
	}

	private void complete(HttpServletRequest request, HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		String result="";
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			String to=request.getParameter("to");
			
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=aDao.completed(refno, type, user,to);
			result=Util.returnValue(num);
			
		}catch (Exception e) {
			result=Util.joinException(e);
			log.error("CompanyAsset==>Completed Exception"+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		 
	}
	private void Ready(HttpServletRequest request, HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		String result="";
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			String to=request.getParameter("to");
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=aDao.Ready(refno, type, user,to);
			result=Util.returnValue(num);
		}catch (Exception e) {
			result=Util.joinException(e);
			log.error("CompanyAsset==>Completed Exception"+e);
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		
	}
	private void returned(HttpServletRequest request, HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			if (Util.objIsNULL(refno) || Util.objIsNULL(type) ) {
				out.print("Error!");
			}
			int num=aDao.returned(refno, type, user);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Success!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("CompanyAsset==>Return Exception"+e);
			out.print("Operation Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		
	}
 
	private void add(HttpServletRequest request, HttpServletResponse response) {
		C_CompanyDao aDao=new C_CompanyDaoImpl();
		PrintWriter out=null;
		try{
			out=response.getWriter();
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String userType =request.getParameter("userType");
			String location=request.getParameter("location");
			String eventName=request.getParameter("eventName");
			String collectionDate=request.getParameter("collectionDate");
			String returnDate=request.getParameter("returnDate");
			String item=request.getParameter("item");
			String refno = aDao.findref();
			String retu="";
			Set<String> errorString=new HashSet<String>();
		 
			//获取产品信息，查询对比是否还有库存
			//List<C_CompanyassetItem>  itemList = aDao.findCompanyBycode();
			 String[] companyitems=item.toString().split("&#&");//组合产品数组
			 /*	
				 * 2014-6-20 17:34:02 King 注释
				 * 
			 for (int i = 0; i < companyitems.length; i++) {
				 String  itemcode =companyitems[i];
				 
				 // 遍历现有数据
			   for(int j=0;j<itemList.size();j++){//遍历订单下面的产品
					   C_CompanyassetItem cp=itemList.get(j);//获取产品
					   if(cp.getItemcode().equals(itemcode) && cp.getRemainnum()==0){// 库存数量=0
							   errorString.add(itemcode);//保存库存不足的数据
					   }
				   }
				 
			}*/
			 if(errorString.size()>0){
				 JSONArray jsons=JSONArray.fromObject(errorString);
				 out.print(jsons.toString()+"已被借出，请重试！");
			 }else	{
			
				aDao.complete(new C_Companyasset(refno,staffcode,staffname,
						userType,location,eventName,collectionDate,returnDate, user,
						DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y));
				 for (int i = 0; i < companyitems.length; i++) {
					 String  itemcode =companyitems[i];
					 //暂定num=1
					 retu = aDao.completedetail(new C_CompanyassetDetail(
						 refno,itemcode,1 ),itemcode) ;
				 }
				out.print(retu);
			 }
		}catch (Exception e) {
			e.printStackTrace();
			log.error("CompanyAsset==>Save Exception"+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateTitleCenterCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)16); //字体大小
		font.setFontName("Times New Roman");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFont(font);
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//黄色
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateTWOCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("Times New Roman");
		cellstyle.setWrapText(true);//自动换行
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateTWOTitleCenterCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("Times New Roman");
		cellstyle.setWrapText(true);//自动换行
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色

		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
}
