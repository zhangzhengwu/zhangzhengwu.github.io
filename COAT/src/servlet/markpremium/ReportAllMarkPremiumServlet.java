package servlet.markpremium;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;
import util.Util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import dao.C_GetMarkPremiumDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import entity.C_marOrder;
import entity.C_marRecord;

/**
 * 查询Trainee List
 * @author Wilson
 *
 */
public class ReportAllMarkPremiumServlet extends HttpServlet {
	Logger log = Logger.getLogger(ReportAllMarkPremiumServlet.class);
	private static final long serialVersionUID = 1L;
	C_GetMarkPremiumDao  getMarkPremiumDao = new C_GetMarkPremiumDaoImpl();
	DecimalFormat dFormat = new DecimalFormat("##.##");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fname="Marketing Premium Form";
		OutputStream os = response.getOutputStream();//取出输出流
		response.reset();//清空输出流
		//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
		response.setHeader("Content-disposition", "attachment; filename="+ fname + ".pdf");
		
		ByteOutputStream byteos =new ByteOutputStream();
		Document document = new Document();// 建立一个Document对象
		try {
			response.setContentType("text/html;charset=utf-8");
			 
			ServletContext application=this.getServletContext();
			String absPath=new java.io.File( application.getRealPath(request.getRequestURI())).getParent();
			String urlString = absPath.substring(0, absPath.lastIndexOf("\\"));
			
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String staffcode = request.getParameter("staffcode");
			String ordercode = request.getParameter("ordercode");
			//System.out.println(startDate+"========"+endDate+"========"+staffcode+"========"+ordercode+"========");
			PdfWriter pdfWriter = PdfWriter.getInstance(document, byteos);
			/**************************************************************/
			List<C_marOrder> list=new ArrayList<C_marOrder>();
			list=getMarkPremiumDao.queryOrderList(staffcode, ordercode, startDate, endDate, 1000000, 1);
			
			for (int i = 0; i < list.size(); i++) {
				document.newPage();
				C_marOrder order = list.get(i);		
					
				document = reprtPDF(document,order.getClientcode(),order.getOrdercode(), startDate, endDate,urlString);
			}
			document.close();   
			byteos.writeTo(os);   
		
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("导出 Mark Premium Form 时出现 ："+e);
		} catch (DocumentException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			os.flush(); 
			os.close();
		}

	}
	public Document reprtPDF(Document document,String staffcode,String ordercode,  String startDate, String endDate,String URL){
			String ordcodeStr = ordercode;
			document.setPageSize(PageSize.A4);// 设置页面大小
			document.open();
			String location="";
			String title="";
			try {
				/**
				 * 获取数据
				 */
	 			List<C_marRecord> recordlist = getMarkPremiumDao.reportOrderRecord(staffcode, ordcodeStr, startDate, endDate);
				C_marOrder orderList = getMarkPremiumDao.getOrder(ordcodeStr);
				if (Util.objIsNULL(orderList)) {
					return null;
				}
				if (orderList.getCollectionlocation().trim().equals("Y")){
					location="@CONVOY";
				}else if(orderList.getCollectionlocation().trim().equals("C")){
					location="CP3";
				}else {
					location="CWC";
				}
				
				/*String urlString = "";*/
				if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					//urlString = Constant.MarkReport_URL_CONS;
					title ="Marketing Premium Order Form (Consultant)";
				}else {
					//urlString = Constant.MarkReport_URL_STAFF;
					title ="Marketing Premium Order Form (Staff)";
				}

				/*File f = new File(urlString);
				if(!f.exists()){
					f.mkdirs();
				}*/
				//PdfWriter.getInstance(document, new FileOutputStream(urlString+ staffcode+"_"+ordcodeStr+".pdf"));// 建立一个PdfWriter对象
				  // 建立一个PdfWriter对象
				
				BaseFont bfChinese = BaseFont.createFont("STSong-Light",
						"UniGB-UCS2-H", false);// 设置中文字体 BaseFont.NOT_EMBEDDED
				Font headFont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
				float[] widths = { 100f, 100f, 100f, 100f, 100f, 100f, 100f, 100f  };// 设置表格的列宽
				
				PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
					table.setSpacingBefore(130f);// 设置表格上面空白宽度
					table.setTotalWidth(535);// 设置表格的宽度
					table.setLockedWidth(true);// 设置表格的宽度固定
					// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
				Image img = Image.getInstance(URL+"\\images\\report.png");
				
				PdfPCell cell = new PdfPCell(img,true);// 建立一个单元格
					cell.setBorder(0);//设置单元格无边框
					cell.setFixedHeight(40);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);// 增加单元格
					
				 //空行
					cell = new PdfPCell( new Paragraph("  ", headFont));
					cell.setBorder(0);//设置单元格无边框
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);// 增加单元格
					
					
				//设置头部部分
					cell = new PdfPCell( new Paragraph("Title : ", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(1);// 设置合并单元格的列数
					table.addCell(cell);
					
					cell = new PdfPCell( new Paragraph(title, headFont));
					cell.setBorder(0);
					cell.setColspan(3);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph("Order Reference : ", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(orderList.getOrdercode(), headFont));
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
					//第二行 头部部分 
					cell = new PdfPCell( new Paragraph("Order by :", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(1);// 设置合并单元格的列数
					table.addCell(cell);
					
					cell = new PdfPCell( new Paragraph(orderList.getClientcode()+"("+orderList.getClientname()+")", headFont));
					cell.setBorder(0);
					cell.setColspan(3);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph("Order Date:", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(orderList.getOrderdate().substring(0,10), headFont));
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
				
				//换两行
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
				
				//订单表格 列头
					cell = new PdfPCell(new Paragraph("Code", headFont)); 
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("Description", headFont)); 
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("Chinese Product Name", headFont)); 
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("Unit Price(HK$)", headFont)); 
					cell.setColspan(1);
					table.addCell(cell);
					//cell = new PdfPCell(new Paragraph("C-Club Price", headFont));
					//cell.setColspan(1);
					//table.addCell(cell);
					cell = new PdfPCell(new Paragraph("Quantity", headFont));
					cell.setColspan(1);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("Amt(HK$)", headFont)); 
					cell.setColspan(1);
					table.addCell(cell);
				//动态加载数据
					for (int i = 0; i < recordlist.size(); i++) {
						C_marRecord vo = (C_marRecord)recordlist.get(i);
						cell = new PdfPCell(new Paragraph(vo.getProcode(),headFont));
						cell.setColspan(1);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(vo.getProname(),headFont));
						cell.setColspan(2);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("",headFont));
						cell.setColspan(2);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(vo.getPrice()+"",headFont));
						cell.setColspan(1);
						table.addCell(cell);
						
						cell = new PdfPCell(new Paragraph(dFormat.format(vo.getQuantity())+"",headFont));
						cell.setColspan(1);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(vo.getPriceall()+"",headFont));
						cell.setColspan(1);
						table.addCell(cell);
					}
					
						cell = new PdfPCell( new Paragraph(" ", headFont));
						cell.setColspan(6);// 设置合并单元格的列数
						table.addCell(cell);
						cell = new PdfPCell( new Paragraph("Total:", headFont));
						cell.setColspan(1);// 设置合并单元格的列数
						table.addCell(cell);
						cell = new PdfPCell( new Paragraph(orderList.getPriceall()+"", headFont));
						cell.setColspan(1);// 设置合并单元格的列数
						table.addCell(cell);
					
					
				//换两行
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph("—————————————————————————————————————————————————————", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
				
				//设置尾部部分
				//只有顾问有C-CLUB
				if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("C-Club Member", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+orderList.getC_clubmember(), headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
					
					
				cell = new PdfPCell( new Paragraph("Collection Location", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+location, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				if (!orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
					cell = new PdfPCell( new Paragraph("Department", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":"+orderList.getDepartment(), headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
				}
					cell = new PdfPCell( new Paragraph("Payment Method", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":", headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
					
					if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
						cell = new PdfPCell( new Paragraph("C-club", headFont));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
						cell.setBorder(0);
						cell.setColspan(2);// 设置合并单元格的列数
						table.addCell(cell);
						String clubnum = Util.objIsNULL(orderList.getC_club())?"________________":orderList.getC_club();
						cell = new PdfPCell( new Paragraph(":$"+clubnum, headFont));
						cell.setBorder(0);
						cell.setColspan(6);// 设置合并单元格的列数
						table.addCell(cell);
					}
					cell = new PdfPCell( new Paragraph("Cash", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					String cashnum = Util.objIsNULL(orderList.getCash())?"________________":orderList.getCash();
					cell = new PdfPCell( new Paragraph(":$"+cashnum, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
					
					cell = new PdfPCell( new Paragraph("Cheque", headFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
					cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					String Chequenum = Util.objIsNULL(orderList.getCheque())?"________________":orderList.getCheque();
					String banker = Util.objIsNULL(orderList.getBanker())?"________________":orderList.getBanker();
					String Chequeno = Util.objIsNULL(orderList.getChequeno())?"________________":orderList.getChequeno();
					cell = new PdfPCell( new Paragraph(":$"+Chequenum+"     Banker:"+banker+"     Cheque No.:"+Chequeno, headFont));
					cell.setBorder(0);
					cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
						
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
					
					cell = new PdfPCell( new Paragraph("Received by ", headFont));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
						cell.setBorder(0);
						cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(":_________________", headFont));
						cell.setBorder(0);
						cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
					cell = new PdfPCell( new Paragraph(" ", headFont));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居right显示
						cell.setBorder(0);
					cell.setColspan(2);// 设置合并单元格的列数
					table.addCell(cell);
						cell = new PdfPCell( new Paragraph(orderList.getClientcode()+"("+orderList.getClientname()+")", headFont));
						cell.setBorder(0);
						cell.setColspan(6);// 设置合并单元格的列数
					table.addCell(cell);
		
					
					cell = new PdfPCell( new Paragraph(" ", headFont));
					cell.setBorder(0);
					cell.setColspan(8);// 设置合并单元格的列数
					table.addCell(cell);
					
					//订单表格 列头
					Font headFont2 = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小
					headFont2.setColor(BaseColor.WHITE);
					cell = new PdfPCell(new Paragraph("For ADM Use", headFont2)); 
					cell.setBackgroundColor(BaseColor.BLACK);
					
					cell.setColspan(8);
					table.addCell(cell);
					
					if (orderList.getOrdertype().trim().equals(Constant.ORDER_TYPE_Cons)) {
						cell = new PdfPCell(new Paragraph("Amount Received by ", headFont)); 
						cell.setColspan(2);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("", headFont)); 
						cell.setColspan(6);
						table.addCell(cell);
					}
					cell = new PdfPCell(new Paragraph("Handled by ", headFont)); 
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("", headFont)); 
					cell.setColspan(6);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Date", headFont)); 
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph("", headFont)); 
					cell.setColspan(6);
					table.addCell(cell);
					
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
				document.add(table);
			} catch (Exception e) {
				e.printStackTrace();
				//logger.error("导出PDF——c_mar_Order时出现 ："+DateUtils.getNowDateTime()+e.toString());
			}
			 
			return document;

		}
}
