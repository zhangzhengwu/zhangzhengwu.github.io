package servlet.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import util.DateUtils;

import dao.C_RoomSettingDao;
import dao.impl.C_RoomSettingDaoImpl;
import entity.C_Roomsetting;

/**
 * 查询RoomSetting List
 * @author Sky
 *
 */
@SuppressWarnings({"all"})
public class ReportRoomSettingServlet extends HttpServlet {
	Logger log = Logger.getLogger(ReportRoomSettingServlet.class);
	private static final long serialVersionUID = 1L;
	C_RoomSettingDao  roomDao = new C_RoomSettingDaoImpl();
	DecimalFormat dFormat = new DecimalFormat("##.##");
	String user=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String start_s = request.getParameter("start_s");
		String start_e = request.getParameter("start_e");
		String eventDate = request.getParameter("eventDate");
		String refno=request.getParameter("refno");
		String staffcode=request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		String userType=request.getParameter("userType");
		String status=request.getParameter("status");
		user=(String) request.getSession().getAttribute("adminUsername");
		
		List<C_Roomsetting> clist = new C_RoomSettingDaoImpl().queryRoomList(eventDate,userType,staffcode,staffname,refno,status, start_s, start_e);
		//System.out.println(clist.size());
	    String filePath = "RoomSetting.xls";
	    createExcel(filePath, clist);
	    downloadFile(filePath, response);
	    log.info(user+"==>RoomSetting==>Export=="+DateUtils.getNowDateTime());
	}

	public void createExcel(String filePath, List<C_Roomsetting> allList)
			throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
	    OutputStream os = new FileOutputStream(new File(filePath));
	    HSSFSheet sheet = wb.createSheet("C_Roomsetting信息");

	    HSSFCellStyle style = wb.createCellStyle();
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    HSSFFont font = wb.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("宋体");
	    style.setFont(font);

	    HSSFCellStyle style1 = wb.createCellStyle();
	    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style1.setFont(font);
	    HSSFRow row = null;
	    HSSFCell cell = null;
	    sheet.addMergedRegion(new Region(0, (short) (0), 0, (short) (14)));
	    row = sheet.createRow(0);
	    row.setHeightInPoints((float) 18);
	    cell = row.createCell((short)0);
	    cell.setCellValue("C_Roomsetting报表");
	    cell.setCellStyle(style1);
	    row = sheet.createRow(1);
	    row.setHeightInPoints((float) 18);
	    sheet.setColumnWidth((short)0, (short)5000);
	    sheet.setColumnWidth((short)1, (short)4000);
	    sheet.setColumnWidth((short)2, (short)4000);
	    sheet.setColumnWidth((short)3, (short)4000);
	    sheet.setColumnWidth((short)4, (short)6000);
	    sheet.setColumnWidth((short)5, (short)4000);
	    sheet.setColumnWidth((short)6, (short)5000);
	    sheet.setColumnWidth((short)7, (short)5000);
	    sheet.setColumnWidth((short)8, (short)5000);
	    sheet.setColumnWidth((short)9, (short)5000);
	    sheet.setColumnWidth((short)10, (short)5000);
	    sheet.setColumnWidth((short)11, (short)3000);
	    sheet.setColumnWidth((short)12, (short)5000);
	    sheet.setColumnWidth((short)13, (short)3000);
	    sheet.setColumnWidth((short)14, (short)3000);
	    cell = row.createCell((short)0);
	    cell.setCellValue("流水号");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)1);
	    cell.setCellValue("员工编号");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)2);
	    cell.setCellValue("员工姓名");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)3);
	    cell.setCellValue("用户类型");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)4);
	    cell.setCellValue("活动名称");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)5);
	    cell.setCellValue("活动日期");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)6);
	    cell.setCellValue("活动开始时间");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)7);
	    cell.setCellValue("活动结束时间");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)8);
	    cell.setCellValue("convoy楼层选择方式");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)9);
	    cell.setCellValue("CP3楼层选择方式");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)10);
	    cell.setCellValue("备注");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)11);
	    cell.setCellValue("创建人");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)12);
	    cell.setCellValue("创建时间");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)13);
	    cell.setCellValue("状态");
	    cell.setCellStyle(style1);
	    cell = row.createCell((short)14);
	    cell.setCellValue("是否有效");
	    cell.setCellStyle(style1);

	    sheet.createFreezePane(0, 2);

	    write(sheet, row, allList);

	    wb.write(os);
	    os.close();
	}
	public void write(HSSFSheet sheet, HSSFRow row, List<C_Roomsetting> list)
	  {
	    HSSFCell cell = null;
	    C_Roomsetting room = null;
	    //HSSFCellStyle style = setStyle();
	    for (int i = 0; i < list.size(); ++i) {
	      row = sheet.createRow(i+2);
	      room = (C_Roomsetting)list.get(i);
	      cell = row.createCell((short)0);
	      cell.setCellValue(room.getRefno());
	      cell = row.createCell((short)1);
	      cell.setCellValue(room.getStaffcode());
	      cell = row.createCell((short)2);
	      cell.setCellValue(room.getStaffname());
	      cell = row.createCell((short)3);
	      cell.setCellValue(room.getUserType());
	      cell = row.createCell((short)4);
	      cell.setCellValue(room.getEventname());
	      cell = row.createCell((short)5);
	      cell.setCellValue(room.getEventDate());
	      cell = row.createCell((short)6);
	      cell.setCellValue(room.getStartTime());
	      cell = row.createCell((short)7);
	      cell.setCellValue(room.getEndTime());
	      cell = row.createCell((short)8);
	      cell.setCellValue(room.getConvoy().replaceAll("#",""));
	      cell = row.createCell((short)9);
	      cell.setCellValue(room.getCp3().replaceAll("#",""));
	      cell = row.createCell((short)10);
	      cell.setCellValue(room.getRemark());
	      cell = row.createCell((short)11);
	      cell.setCellValue(room.getCreator());
	      cell = row.createCell((short)(short)12);
	      cell.setCellValue(room.getCreateDate());
	      cell = row.createCell((short)13);
	      cell.setCellValue(room.getStatus());
	      cell = row.createCell((short)14);
	      cell.setCellValue(room.getSfyx());
	    }
	  }
	  /*public HSSFCellStyle setStyle()
	  {
	    HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFCellStyle style1 = wb.createCellStyle();
	    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    HSSFFont font = wb.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("宋体");
	    style1.setFont(font);
	    return style1;
	  }*/
	  public  void downloadFile(String filePath, HttpServletResponse response)
	  {
		 /* response.setContentType("text/html;charset=utf-8");
			HSSFWorkbook wb = createExcel(filePath, clist);
			System.out.println("开始输出要下载的文件...");

			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ "AtiveConsultantList.xls" + "\"");

			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();

			System.out.println("文件下载完毕.");*/
	    String fileName = "";
	    InputStream fs = null;
	    if ((filePath.startsWith("http://")) || (filePath.startsWith("ftp://"))) {
	      try {
	        fileName = new String(filePath.substring(
	          filePath.lastIndexOf("/") + 1, filePath.length()).getBytes
	          ("utf-8"), "ISO8859_1");
	      } catch (Exception localException1) {
	      }
	      try {
	        URL url = new URL(filePath);
	        URLConnection urlConn = url.openConnection();
	        fs = urlConn.getInputStream();
	      } catch (MalformedURLException e) {
	        e.printStackTrace();
	        return;
	      } catch (IOException e) {
	        e.printStackTrace();
	        return;
	      }
	    }
	    try {
	      fileName = new String(filePath.substring(filePath.lastIndexOf
	        ("\\") + 1, filePath.length()));
	    } catch (Exception e) {
	    }
	    try {
	      fs = new FileInputStream(new File(filePath));
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	      return;
	    }

	    response.setContentType("APPLICATION/OCTET-STREAM");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + 
	      fileName + "\"");
	    try
	    {
	      File ft = new File(filePath);
	      byte[] bb = new byte[(int)ft.length()];
	      fs.read(bb);
	      fs.close();
	      OutputStream out = response.getOutputStream();
	      out.write(bb);
	      out.close();
	      System.out.println("文件下载完毕.");
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println("下载文件失败!");
	     log.error("RoomSetting==>Export  Exception:"+e);
	    }
	  }
}
