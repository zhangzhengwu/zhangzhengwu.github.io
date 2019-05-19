package dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.DBManager;
import util.Util;
import dao.SeatForDDTHDao;
import dao.common.BaseDao;
import entity.SeatFloorplantemp;

public class SeatForDDTHDaoImpl extends BaseDao implements SeatForDDTHDao {

	Logger logger = Logger.getLogger(SeatForDDTHDaoImpl.class);
	
	
	/**
	 * 
	 * @return
	 */
	public String timeTaskSeatForDDTH(){
		String result = "";
		List<SeatFloorplantemp> list = null;
		try{
			Util.printLogger(logger,"");    
			list=getSeatFloorplanList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				saveList(list);
				list=null;
				result="success";
				Util.printLogger(logger,"");
			}else{
				Util.printLogger(logger,"");
				throw new Exception("");
			} 
			//执行插入数据操作
			readExecl5F();
			readExecl7F();
			readExecl15F();
			readExecl40F();
			readExecl17F();
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"");
		}
		return result;
		
	}

	public List<SeatFloorplantemp> getSeatFloorplanList() throws Exception{
		Connection con=null;
		PreparedStatement ps=null;
		List<SeatFloorplantemp> list = new ArrayList<SeatFloorplantemp>();
		try {
			con= DBManager.getCon();
			
/*			File file = new File("D://floorplan");
			String floorplan[] = file.list();
			if(Util.objIsNULL(floorplan)){
				logger.error("文件夹内没有文件！");
				throw new Exception("文件夹内没有文件！");
			}
			StringBuffer stringBuffer=new StringBuffer("select * from (");
			String floorstr = "";
			for (int i = 0; i < floorplan.length; i++){
				floorstr = floorplan[i].substring(floorplan[i].indexOf("-")+1,floorplan[i].indexOf(".")).trim();//获取楼层
				if(i>0){
					stringBuffer.append(" union ");
				}
				stringBuffer.append("select (@rowNum"+i+":=@rowNum"+i+"+1)as teamcode ,a.* from (SELECT a.floor,b.DDTreeHead,c.joinDate FROM `seat_list` a left join cons_list b on a.staffcode=b.EmployeeId left join cons_list c on b.DDTreeHead=c.EmployeeId group by b.DDTreeHead,a.floor HAVING b.DDTreeHead is not null order by a.floor desc,c.joinDate) a,(Select (@rowNum"+i+" :=0) ) b  where floor='"+floorstr+"'");
			}
			stringBuffer.append(") d left JOIN (select (@rowNumx:=@rowNumx+1)as teamcode,a.* from seat_colorview a,(Select (@rowNumx :=0)) b) e on d.teamcode = e.teamcode");
*/			
			StringBuffer stringBuffer=new StringBuffer("select * from (select (@rowNum:=@rowNum+1)as teamcode ,a.* from (SELECT a.floor,b.DDTreeHead,c.joinDate FROM `seat_list` a left join cons_list b on a.staffcode=b.EmployeeId left join cons_list c on b.DDTreeHead=c.EmployeeId group by b.DDTreeHead,a.floor HAVING b.DDTreeHead is not null order by a.floor desc,c.joinDate) a,(Select (@rowNum :=0)) b  where floor='5F' UNION select (@rowNum1:=@rowNum1+1)as teamcode ,a.* from (SELECT a.floor,b.DDTreeHead,c.joinDate FROM `seat_list` a left join cons_list b on a.staffcode=b.EmployeeId left join cons_list c on b.DDTreeHead=c.EmployeeId group by b.DDTreeHead,a.floor HAVING b.DDTreeHead is not null order by a.floor desc,c.joinDate) a,(Select (@rowNum1 :=0)) b  where floor='7F' UNION select (@rowNum2:=@rowNum2+1)as teamcode ,a.* from (SELECT a.floor,b.DDTreeHead,c.joinDate FROM `seat_list` a left join cons_list b on a.staffcode=b.EmployeeId left join cons_list c on b.DDTreeHead=c.EmployeeId group by b.DDTreeHead,a.floor HAVING b.DDTreeHead is not null order by a.floor desc,c.joinDate) a,(Select (@rowNum2 :=0)) b  where floor='15F' UNION select (@rowNum3:=@rowNum3+1)as teamcode ,a.* from (SELECT a.floor,b.DDTreeHead,c.joinDate FROM `seat_list` a left join cons_list b on a.staffcode=b.EmployeeId left join cons_list c on b.DDTreeHead=c.EmployeeId group by b.DDTreeHead,a.floor HAVING b.DDTreeHead is not null order by a.floor desc,c.joinDate) a,(Select (@rowNum3 :=0)) b  where floor='40F' UNION select (@rowNum4:=@rowNum4+1)as teamcode ,a.* from (SELECT a.floor,b.DDTreeHead,c.joinDate FROM `seat_list` a left join cons_list b on a.staffcode=b.EmployeeId left join cons_list c on b.DDTreeHead=c.EmployeeId group by b.DDTreeHead,a.floor HAVING b.DDTreeHead is not null order by a.floor desc,c.joinDate) a,(Select (@rowNum4 :=0)) b  where floor='17F') d left JOIN (select (@rowNumx:=@rowNumx+1)as teamcode,a.* from seat_colorview a,(Select (@rowNumx :=0)) b) e on d.teamcode = e.teamcode");
			logger.info(" 查询SeatFloorplantemp SQL:"+stringBuffer);
			System.out.println("stringBuffer:"+stringBuffer);
			
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SeatFloorplantemp sfpt = new SeatFloorplantemp();
				sfpt.setTeamcode(rs.getInt(1));
				sfpt.setFloor(rs.getString(2));
				sfpt.setDdtreeHead(rs.getString(3));
				sfpt.setJoinDate(rs.getString(4));
				sfpt.setHssfcolor(rs.getString(7));				
				sfpt.setHexcolor(rs.getInt(8));
				sfpt.setRbgColor(rs.getString(9));
				list.add(sfpt);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询SeatFloorplantemp异常:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("查询SeatFloorplantemp异常:"+e);
		}finally
		{
			DBManager.closeCon(con);
			super.closeConnection();
		}
		return list;
	}
	public void saveList(List<SeatFloorplantemp> list){
		Connection con = null;
		PreparedStatement ps= null;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			String sql="delete from seat_floorplantemp";
			ps = con.prepareStatement(sql); 
			ps.executeUpdate();
			
			if(!Util.objIsNULL(list)){
				String sql2=new String("insert into seat_floorplantemp(teamcode,floor,DDTreeHead,joinDate,HSSFColor,HEXColor,rbgColor) values(?,?,?,?,?,?,?)");
				ps=con.prepareStatement(sql2.toString());
				for(int i=0;i<list.size();i++){
					SeatFloorplantemp sfpt=list.get(i);
					ps.setInt(1, sfpt.getTeamcode());
					ps.setString(2, sfpt.getFloor());
					ps.setString(3, sfpt.getDdtreeHead());
					ps.setString(4, sfpt.getJoinDate());
					ps.setString(5, sfpt.getHssfcolor());
					ps.setInt(6, sfpt.getHexcolor());
					ps.setString(7, sfpt.getRbgColor());
					ps.executeUpdate();
				}
			}	
			list = null;	
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
				logger.error("同步SeatFloorplantemp信息时 数据异常进行数据回滚");
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.info("同步SeatFloorplantemp信息时数据回滚异常   "+e);
			}
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void readExeclXF(){
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null; 
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		try {
			
			InputStream is = new FileInputStream("E://floorplan//floorplan_macro_v1_3 - 5F.xlsm");
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Configure");//获取Configure页
			totalRows = sheet.getLastRowNum();// 获取总行数
			int startnum=0;
			for (int i = beginRowIndex; i <= totalRows; i++) {
				XSSFRow  row = sheet.getRow(i);// 读取Excel里面的i行
				if(!Util.objIsNULL(row)){
					XSSFCell firstdata = row.getCell(0);//从每一行循环第一列的数据
					if(!Util.objIsNULL(firstdata)&&"DDTH".equalsIgnoreCase(firstdata.toString())){
						startnum=i+1;
					}else{
						if(startnum!=0 && i>=startnum){
							sheet.removeRow(sheet.getRow(i));
						}
					}
				}
			}
			
			//写入DDTH
			//for
			con=DBManager.getCon();
			String sql= "select DDTreeHead,teamcode,HEXColor from seat_floorplantemp where floor=?";
			ps=con.prepareStatement(sql.toString());	
			ps.setString(1, "5F");	
			rs=ps.executeQuery();	
			
			while(rs.next()){	
				XSSFRow  row = sheet.createRow(startnum);
				row.createCell(0).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(1).setCellValue(rs.getInt("teamcode"));
				CellStyle style = workbook.createCellStyle();    
				style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				style.setFillForegroundColor((short)rs.getShort("HEXColor"));
				row.createCell(2).setCellStyle(style);
				startnum++;
			}
			
			String[] sheetname = new String[workbook.getNumberOfSheets()];
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
				sheet=workbook.getSheetAt(i);
				sheetname[i] = sheet.getSheetName(); 
			}
			int index = -1;
			for (int i = 0; i < sheetname.length; i++) {
				if("5F_SeatList".equals(sheetname[i].trim())){
					index = i;
				}
			}
			if(index == -1){
				throw new RuntimeException("指定页签“5F_SeatList”不存在");
			}
			workbook.removeSheetAt(index);
			XSSFSheet seatFlostSheet=workbook.createSheet("5F_SeatList");
			XSSFRow  row = seatFlostSheet.createRow(0);
			row.createCell(0).setCellValue("Seat No.");
			row.createCell(1).setCellValue("Staff Code");
			row.createCell(2).setCellValue("Name");
			row.createCell(3).setCellValue("DDTH");
			row.createCell(4).setCellValue("TeamCode");
			row.createCell(5).setCellValue("Code&Name");
			//写入SeatList
			String sql2 = "select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode from seat_list a left join cons_list b on a.staffcode=b.EmployeeId left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor ='5F'";
			ps=con.prepareStatement(sql2.toString());
			rs=ps.executeQuery();
			int temp = 1;
			while(rs.next()){	
				row = seatFlostSheet.createRow(temp);
				row.createCell(0).setCellValue(rs.getString("seatno"));
				row.createCell(1).setCellValue(rs.getString("EmployeeId"));
				row.createCell(2).setCellValue(rs.getString("EmployeeName"));
				row.createCell(3).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(4).setCellValue(rs.getInt("teamcode"));
				row.createCell(5).setCellValue(rs.getString("EmployeeId")+"-"+rs.getString("EmployeeName"));
				temp++;
			}
			
//			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 5F.xlsm"); 
			FileOutputStream stream=new FileOutputStream("E://floor//floorplan_macro_v1_3 - 5F.xlsm"); 
			workbook.write(stream);
			stream.flush();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void readExecl5F(){
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null; 
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		try {
			 
			   InputStream is = new FileInputStream("E://floorplan//floorplan_macro_v1_3 - 5F.xlsm");
			   XSSFWorkbook workbook = new XSSFWorkbook(is);
			   XSSFSheet sheet = workbook.getSheet("Configure");//获取Configure页
			   totalRows = sheet.getLastRowNum();// 获取总行数
			   int startnum=0;
				for (int i = beginRowIndex; i <= totalRows; i++) {
					XSSFRow  row = sheet.getRow(i);// 读取Excel里面的i行
					if(!Util.objIsNULL(row)){
						XSSFCell firstdata = row.getCell(0);//从每一行循环第一列的数据
						if(!Util.objIsNULL(firstdata)&&"DDTH".equalsIgnoreCase(firstdata.toString())){
							startnum=i+1;
						}else{
							if(startnum!=0 && i>=startnum){
								sheet.removeRow(sheet.getRow(i));
							}
						}
					}
				}
				
				//写入DDTH
				//for
			con=DBManager.getCon();
				String sql= "select DDTreeHead,teamcode,HEXColor from seat_floorplantemp where floor=?";
				ps=con.prepareStatement(sql.toString());	
				ps.setString(1, "5F");	
				rs=ps.executeQuery();	

				while(rs.next()){	
					XSSFRow  row = sheet.createRow(startnum);
					row.createCell(0).setCellValue(rs.getString("DDTreeHead"));
					row.createCell(1).setCellValue(rs.getInt("teamcode"));
					CellStyle style = workbook.createCellStyle();    
					style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
					style.setFillForegroundColor((short)rs.getShort("HEXColor"));
					row.createCell(2).setCellStyle(style);
					startnum++;
				}
				
				String[] sheetname = new String[workbook.getNumberOfSheets()];
				 for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
		             sheet=workbook.getSheetAt(i);
		             sheetname[i] = sheet.getSheetName(); 
				 }
				 int index = -1;
				for (int i = 0; i < sheetname.length; i++) {
					if("5F_SeatList".equals(sheetname[i].trim())){
						index = i;
					}
				}
				if(index == -1){
					throw new RuntimeException("指定页签“5F_SeatList”不存在");
				}
					workbook.removeSheetAt(index);
					XSSFSheet seatFlostSheet=workbook.createSheet("5F_SeatList");
					XSSFRow  row = seatFlostSheet.createRow(0);
					row.createCell(0).setCellValue("Seat No.");
					row.createCell(1).setCellValue("Staff Code");
					row.createCell(2).setCellValue("Name");
					row.createCell(3).setCellValue("DDTH");
					row.createCell(4).setCellValue("TeamCode");
					row.createCell(5).setCellValue("Code&Name");
					//写入SeatList
					String sql2 = "select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode from seat_list a left join cons_list b on a.staffcode=b.EmployeeId left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor ='5F' order by a.seatno";
					ps=con.prepareStatement(sql2.toString());
					rs=ps.executeQuery();
					int temp = 1;
					while(rs.next()){	
						row = seatFlostSheet.createRow(temp);
						row.createCell(0).setCellValue(rs.getString("seatno"));
						row.createCell(1).setCellValue(rs.getString("EmployeeId"));
						row.createCell(2).setCellValue(rs.getString("EmployeeName"));
						row.createCell(3).setCellValue(rs.getString("DDTreeHead"));
						row.createCell(4).setCellValue(rs.getInt("teamcode"));
						row.createCell(5).setCellValue(rs.getString("EmployeeId")+"-"+rs.getString("EmployeeName"));
						temp++;
					}
					
//			    	FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 5F.xlsm"); 
//			    	FileOutputStream stream=new FileOutputStream("E://floor//floorplan_macro_v1_3 - 5F.xlsm"); 
			    	FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 5F.xlsm"); 
			    	workbook.write(stream);
					stream.flush();
					stream.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	public void readExecl17F(){
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null; 
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		try {
			
			InputStream is = new FileInputStream("E://floorplan//floorplan_macro_v1_3 CP3 - 17F.xlsm");
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Configure");//获取Configure页
			totalRows = sheet.getLastRowNum();// 获取总行数
			int startnum=0;
			for (int i = beginRowIndex; i <= totalRows; i++) {
				XSSFRow  row = sheet.getRow(i);// 读取Excel里面的i行
				if(!Util.objIsNULL(row)){
					XSSFCell firstdata = row.getCell(0);//从每一行循环第一列的数据
					if(!Util.objIsNULL(firstdata)&&"DDTH".equalsIgnoreCase(firstdata.toString())){
						startnum=i+1;
					}else{
						if(startnum!=0 && i>=startnum){
							sheet.removeRow(sheet.getRow(i));
						}
					}
				}
			}
			
			//写入DDTH
			//for
			con=DBManager.getCon();
			String sql= "select DDTreeHead,teamcode,HEXColor from seat_floorplantemp where floor=?";
			ps=con.prepareStatement(sql.toString());	
			ps.setString(1, "17F");	
			rs=ps.executeQuery();	
			
			while(rs.next()){	
				XSSFRow  row = sheet.createRow(startnum);
				row.createCell(0).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(1).setCellValue(rs.getInt("teamcode"));
				CellStyle style = workbook.createCellStyle();    
				style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				style.setFillForegroundColor((short)rs.getShort("HEXColor"));
				row.createCell(2).setCellStyle(style);
				startnum++;
			}
			
			String[] sheetname = new String[workbook.getNumberOfSheets()];
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
				sheet=workbook.getSheetAt(i);
				sheetname[i] = sheet.getSheetName(); 
			}
			int index = -1;
			for (int i = 0; i < sheetname.length; i++) {
				if("17F_SeatList".equals(sheetname[i].trim())){
					index = i;
				}
			}
			if(index == -1){
				throw new RuntimeException("指定页签“17F_SeatList”不存在");
			}
			workbook.removeSheetAt(index);
			XSSFSheet seatFlostSheet=workbook.createSheet("17F_SeatList");
			XSSFRow  row = seatFlostSheet.createRow(0);
			row.createCell(0).setCellValue("Seat No.");
			row.createCell(1).setCellValue("Staff Code");
			row.createCell(2).setCellValue("Name");
			row.createCell(3).setCellValue("DDTH");
			row.createCell(4).setCellValue("TeamCode");
			row.createCell(5).setCellValue("Code&Name");
			//写入SeatList
			String sql2 = "select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode from seat_list a left join cons_list b on a.staffcode=b.EmployeeId left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor ='17F' order by a.seatno";
			ps=con.prepareStatement(sql2.toString());
			rs=ps.executeQuery();
			int temp = 1;
			while(rs.next()){	
				row = seatFlostSheet.createRow(temp);
				row.createCell(0).setCellValue(rs.getString("seatno"));
				row.createCell(1).setCellValue(rs.getString("EmployeeId"));
				row.createCell(2).setCellValue(rs.getString("EmployeeName"));
				row.createCell(3).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(4).setCellValue(rs.getInt("teamcode"));
				row.createCell(5).setCellValue(rs.getString("EmployeeId")+"-"+rs.getString("EmployeeName"));
				temp++;
			}
			
//			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 CP3 - 17F.xlsm"); 
//			FileOutputStream stream=new FileOutputStream("E://floor//floorplan_macro_v1_3 CP3 - 17F.xlsm"); 
			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 CP3 - 17F.xlsm"); 
			workbook.write(stream);
			stream.flush();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void readExecl7F(){
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null; 
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		try {
			
			InputStream is = new FileInputStream("E://floorplan//floorplan_macro_v1_3 - 7F.xlsm");
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Configure");//获取Configure页
			totalRows = sheet.getLastRowNum();// 获取总行数
			int startnum=0;
			for (int i = beginRowIndex; i <= totalRows; i++) {
				XSSFRow  row = sheet.getRow(i);// 读取Excel里面的i行
				if(!Util.objIsNULL(row)){
					XSSFCell firstdata = row.getCell(0);//从每一行循环第一列的数据
					if(!Util.objIsNULL(firstdata)&&"DDTH".equalsIgnoreCase(firstdata.toString())){
						startnum=i+1;
					}else{
						if(startnum!=0 && i>=startnum){
							sheet.removeRow(sheet.getRow(i));
						}
					}
				}
			}
			
			//写入DDTH
			//for
			con=DBManager.getCon();
			String sql= "select DDTreeHead,teamcode,HEXColor from seat_floorplantemp where floor=?";
			ps=con.prepareStatement(sql.toString());	
			ps.setString(1, "7F");	
			rs=ps.executeQuery();	
			
			while(rs.next()){	
				XSSFRow  row = sheet.createRow(startnum);
				row.createCell(0).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(1).setCellValue(rs.getInt("teamcode"));
				CellStyle style = workbook.createCellStyle();    
				style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				style.setFillForegroundColor((short)rs.getShort("HEXColor"));
				row.createCell(2).setCellStyle(style);
				startnum++;
			}
			
			String[] sheetname = new String[workbook.getNumberOfSheets()];
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
				sheet=workbook.getSheetAt(i);
				sheetname[i] = sheet.getSheetName(); 
			}
			int index = -1;
			for (int i = 0; i < sheetname.length; i++) {
				if("7F_SeatList".equals(sheetname[i].trim())){
					index = i;
				}
			}
			if(index == -1){
				throw new RuntimeException("指定页签“7F_SeatList”不存在");
			}
			workbook.removeSheetAt(index);
			XSSFSheet seatFlostSheet=workbook.createSheet("7F_SeatList");
			XSSFRow  row = seatFlostSheet.createRow(0);
			row.createCell(0).setCellValue("Seat No.");
			row.createCell(1).setCellValue("Staff Code");
			row.createCell(2).setCellValue("Name");
			row.createCell(3).setCellValue("DDTH");
			row.createCell(4).setCellValue("TeamCode");
			row.createCell(5).setCellValue("Code&Name");
			//写入SeatList
			String sql2 = "select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode from seat_list a left join cons_list b on a.staffcode=b.EmployeeId left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor ='7F' order by a.seatno";
			ps=con.prepareStatement(sql2.toString());
			rs=ps.executeQuery();
			int temp = 1;
			while(rs.next()){	
				row = seatFlostSheet.createRow(temp);
				row.createCell(0).setCellValue(rs.getString("seatno"));
				row.createCell(1).setCellValue(rs.getString("EmployeeId"));
				row.createCell(2).setCellValue(rs.getString("EmployeeName"));
				row.createCell(3).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(4).setCellValue(rs.getInt("teamcode"));
				row.createCell(5).setCellValue(rs.getString("EmployeeId")+"-"+rs.getString("EmployeeName"));
				temp++;
			}
			
//			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 7F.xlsm"); 
//			FileOutputStream stream=new FileOutputStream("E://floor//floorplan_macro_v1_3 - 7F.xlsm"); 
			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 7F.xlsm"); 
			workbook.write(stream);
			stream.flush();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void readExecl15F(){
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null; 
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		try {
			
			InputStream is = new FileInputStream("E://floorplan//floorplan_macro_v1_3 - 15F.xlsm");
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Configure");//获取Configure页
			totalRows = sheet.getLastRowNum();// 获取总行数
			int startnum=0;
			for (int i = beginRowIndex; i <= totalRows; i++) {
				XSSFRow  row = sheet.getRow(i);// 读取Excel里面的i行
				if(!Util.objIsNULL(row)){
					XSSFCell firstdata = row.getCell(0);//从每一行循环第一列的数据
					if(!Util.objIsNULL(firstdata)&&"DDTH".equalsIgnoreCase(firstdata.toString())){
						startnum=i+1;
					}else{
						if(startnum!=0 && i>=startnum){
							sheet.removeRow(sheet.getRow(i));
						}
					}
				}
			}
			
			//写入DDTH
			//for
			con=DBManager.getCon();
			String sql= "select DDTreeHead,teamcode,HEXColor from seat_floorplantemp where floor=?";
			ps=con.prepareStatement(sql.toString());	
			ps.setString(1, "15F");	
			rs=ps.executeQuery();	
			
			while(rs.next()){	
				XSSFRow  row = sheet.createRow(startnum);
				row.createCell(0).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(1).setCellValue(rs.getInt("teamcode"));
				CellStyle style = workbook.createCellStyle();    
				style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				style.setFillForegroundColor((short)rs.getShort("HEXColor"));
				row.createCell(2).setCellStyle(style);
				startnum++;
			}
			
			String[] sheetname = new String[workbook.getNumberOfSheets()];
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
				sheet=workbook.getSheetAt(i);
				sheetname[i] = sheet.getSheetName(); 
			}
			int index = -1;
			for (int i = 0; i < sheetname.length; i++) {
				if("15F_SeatList".equals(sheetname[i].trim())){
					index = i;
				}
			}
			if(index == -1){
				throw new RuntimeException("指定页签“15F_SeatList”不存在");
			}
			workbook.removeSheetAt(index);
			XSSFSheet seatFlostSheet=workbook.createSheet("15F_SeatList");
			XSSFRow  row = seatFlostSheet.createRow(0);
			row.createCell(0).setCellValue("Seat No.");
			row.createCell(1).setCellValue("Staff Code");
			row.createCell(2).setCellValue("Name");
			row.createCell(3).setCellValue("DDTH");
			row.createCell(4).setCellValue("TeamCode");
			row.createCell(5).setCellValue("Code&Name");
			//写入SeatList
			String sql2 = "select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode from seat_list a left join cons_list b on a.staffcode=b.EmployeeId left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor ='15F' order by a.seatno";
			ps=con.prepareStatement(sql2.toString());
			rs=ps.executeQuery();
			int temp = 1;
			while(rs.next()){	
				row = seatFlostSheet.createRow(temp);
				row.createCell(0).setCellValue(rs.getString("seatno"));
				row.createCell(1).setCellValue(rs.getString("EmployeeId"));
				row.createCell(2).setCellValue(rs.getString("EmployeeName"));
				row.createCell(3).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(4).setCellValue(rs.getInt("teamcode"));
				row.createCell(5).setCellValue(rs.getString("EmployeeId")+"-"+rs.getString("EmployeeName"));
				temp++;
			}
			
//			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 15F.xlsm"); 
//			FileOutputStream stream=new FileOutputStream("E://floor//floorplan_macro_v1_3 - 15F.xlsm"); 
			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 15F.xlsm"); 
			workbook.write(stream);
			stream.flush();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void readExecl40F(){
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null; 
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		try {
			
			InputStream is = new FileInputStream("E://floorplan//floorplan_macro_v1_3 - 40F.xlsm");
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Configure");//获取Configure页
			totalRows = sheet.getLastRowNum();// 获取总行数
			int startnum=0;
			for (int i = beginRowIndex; i <= totalRows; i++) {
				XSSFRow  row = sheet.getRow(i);// 读取Excel里面的i行
				if(!Util.objIsNULL(row)){
					XSSFCell firstdata = row.getCell(0);//从每一行循环第一列的数据
					if(!Util.objIsNULL(firstdata)&&"DDTH".equalsIgnoreCase(firstdata.toString())){
						startnum=i+1;
					}else{
						if(startnum!=0 && i>=startnum){
							sheet.removeRow(sheet.getRow(i));
						}
					}
				}
			}
			
			//写入DDTH
			//for
			con=DBManager.getCon();
			String sql= "select DDTreeHead,teamcode,HEXColor from seat_floorplantemp where floor=?";
			ps=con.prepareStatement(sql.toString());	
			ps.setString(1, "40F");	
			rs=ps.executeQuery();	
			
			while(rs.next()){	
				XSSFRow  row = sheet.createRow(startnum);
				row.createCell(0).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(1).setCellValue(rs.getInt("teamcode"));
				CellStyle style = workbook.createCellStyle();    
				style.setFillPattern(CellStyle.SOLID_FOREGROUND); 
				style.setFillForegroundColor((short)rs.getShort("HEXColor"));
				row.createCell(2).setCellStyle(style);
				startnum++;
			}
			
			String[] sheetname = new String[workbook.getNumberOfSheets()];
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
				sheet=workbook.getSheetAt(i);
				sheetname[i] = sheet.getSheetName(); 
			}
			int index = -1;
			for (int i = 0; i < sheetname.length; i++) {
				if("40F_SeatList".equals(sheetname[i].trim())){
					index = i;
				}
			}
			if(index == -1){
				throw new RuntimeException("指定页签“40F_SeatList”不存在");
			}
			workbook.removeSheetAt(index);
			XSSFSheet seatFlostSheet=workbook.createSheet("40F_SeatList");
			XSSFRow  row = seatFlostSheet.createRow(0);
			row.createCell(0).setCellValue("Seat No.");
			row.createCell(1).setCellValue("Staff Code");
			row.createCell(2).setCellValue("Name");
			row.createCell(3).setCellValue("DDTH");
			row.createCell(4).setCellValue("TeamCode");
			row.createCell(5).setCellValue("Code&Name");
			//写入SeatList
			String sql2 = "select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode from seat_list a left join cons_list b on a.staffcode=b.EmployeeId left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor ='40F' order by a.seatno";
			ps=con.prepareStatement(sql2.toString());
			rs=ps.executeQuery();
			int temp = 1;
			while(rs.next()){	
				row = seatFlostSheet.createRow(temp);
				row.createCell(0).setCellValue(rs.getString("seatno"));
				row.createCell(1).setCellValue(rs.getString("EmployeeId"));
				row.createCell(2).setCellValue(rs.getString("EmployeeName"));
				row.createCell(3).setCellValue(rs.getString("DDTreeHead"));
				row.createCell(4).setCellValue(rs.getInt("teamcode"));
				row.createCell(5).setCellValue(rs.getString("EmployeeId")+"-"+rs.getString("EmployeeName"));
				temp++;
			}
			
//			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 40F.xlsm"); 
//			FileOutputStream stream=new FileOutputStream("E://floor//floorplan_macro_v1_3 - 40F.xlsm"); 
			FileOutputStream stream=new FileOutputStream("////BOCNAS11//Dept//SZO//ADM//Megaport Update//floorplan_macro_v1_3 - 40F.xlsm"); 
			workbook.write(stream);
			stream.flush();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, SeatFloorplantemp.class);
	}	
	
	public static void main(String[] args) {
/*		System.out.println(HSSFColor.SEA_GREEN.index);
		System.out.println(HSSFColor.BLUE_GREY.index);
		System.out.println(HSSFColor.ORANGE.index);
		System.out.println(HSSFColor.LIGHT_ORANGE.index);
		System.out.println(HSSFColor.GOLD.index);
		System.out.println(HSSFColor.LIME.index);
		System.out.println(HSSFColor.AQUA.index);
		System.out.println(HSSFColor.LIGHT_BLUE.index);
		System.out.println(HSSFColor.TAN.index);
		System.out.println(HSSFColor.LAVENDER.index);
		System.out.println(HSSFColor.ROSE.index);
		System.out.println(HSSFColor.PALE_BLUE.index);
		System.out.println(HSSFColor.LIGHT_YELLOW.index);
		System.out.println(HSSFColor.LIGHT_GREEN.index);
		System.out.println(HSSFColor.LIGHT_TURQUOISE.index);
		System.out.println(HSSFColor.SKY_BLUE.index);
		System.out.println(HSSFColor.TEAL.index);
		System.out.println(HSSFColor.TURQUOISE.index);
		System.out.println(HSSFColor.YELLOW.index);
		System.out.println(HSSFColor.PINK.index);
		System.out.println(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		System.out.println(HSSFColor.ROYAL_BLUE.index);
		System.out.println(HSSFColor.CORAL.index);
		System.out.println(HSSFColor.LIGHT_TURQUOISE.index);
		System.out.println(HSSFColor.LEMON_CHIFFON.index);
		System.out.println(HSSFColor.PLUM.index);
		System.out.println(HSSFColor.CORNFLOWER_BLUE.index);
		System.out.println(HSSFColor.TURQUOISE.index);
		System.out.println(HSSFColor.PINK.index);
		System.out.println(HSSFColor.YELLOW.index);
		System.out.println(HSSFColor.BRIGHT_GREEN.index);*/
		

		File file = new File("D://floorplan");
		String floorplan[] = file.list();
		for (int i = 0; i < floorplan.length; i++) {
			System.out.println(floorplan[i]);
			System.out.println(floorplan[i].substring(floorplan[i].indexOf("-")+1));
			System.out.println(floorplan[i].substring(floorplan[i].indexOf("-")+1,floorplan[i].indexOf(".")).trim());
			
			
			
		}
	}
}
