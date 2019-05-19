package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.GetStaffListDao;

/**
 * GetStaffListDaoImpl 实现类
 * @author King.XU
 *
 */
public class GetStaffListDaoImpl implements GetStaffListDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetStaffListDaoImpl.class);
	
	public int saveStaffList(String filename, InputStream os,String username) {
		int num=0;
		int beginRowIndex =7;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("staff_list"); 
		//System.out.println("======================del=====================");
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell EmployeeIdcell = row.getCell(0);
				HSSFCell companycell=row.getCell(1);
				HSSFCell deptidcell=row.getCell(2);
				HSSFCell EmployeeNamecell=row.getCell(3);
				HSSFCell Aliascell=row.getCell(4);
				HSSFCell gradecell=row.getCell(5);
				HSSFCell etypecell=row.getCell(6);
				HSSFCell Emailcell=row.getCell(7);
				HSSFCell patientcell=row.getCell(8);
				HSSFCell pplancell=row.getCell(9);
				HSSFCell dentalcell=row.getCell(10);
				HSSFCell dplancell=row.getCell(11);
				HSSFCell remarkscell=row.getCell(12);
				HSSFCell enrollmentDatecell=row.getCell(13);
				HSSFCell terminationDatecell=row.getCell(14);
			 
			//	System.out.println("=======================go=======================");
				/**给数据库里面的字段赋值**/
				String staffcode =Util.cellToString(EmployeeIdcell);
				String company=Util.cellToString(companycell);
				String deptid=Util.cellToString(deptidcell);
				String staffname=Util.cellToString(EmployeeNamecell);// 
				String Alias=Util.cellToString(Aliascell);
				String grade=Util.cellToString(gradecell);
				String etype=Util.cellToString(etypecell);
				String email=Util.cellToString(Emailcell);
				String patient=Util.cellToString(patientcell);
				String pplan=Util.cellToString(pplancell);
				String dental=Util.cellToString(dentalcell);
				String dplan=Util.cellToString(dplancell);
				String remarks=Util.cellToString(remarkscell);
				String enrollmentDate=Util.cellToString(enrollmentDatecell);
				String terminationDate=Util.cellToString(terminationDatecell);
				if(staffcode.length()<9 && staffcode.length() > 1){
					sql = "insert staff_list (staffcode,company,deptid,staffname,englishname,grade,Etype,email,patient,pplan,dental,dplan,remarks,enrollmentDate,terminationDate) values('"
						+staffcode+"','"+company+"','"+deptid
						+"','"+staffname+"','"+Alias+"','"+grade+"','"+etype+"','"+email+"','"+patient
						+"','"+pplan+"','"+dental+"','"+dplan+"','"+remarks+"','"+enrollmentDate+"','"+terminationDate+"')";
					
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入staff_list成功！");
						num++;
					} else {
						logger.info("插入staff_list失敗");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}

	public int saveStaffMater(String filename, InputStream os, String username) {

		int num=0;
		int beginRowIndex =1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("medical_claim_staff"); 
		//System.out.println("======================del=====================");
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				 
				/**获取Excel里面的指定单元格数据**/
				HSSFCell EmployeeIdcell = row.getCell(0);
				HSSFCell staffnamecell=row.getCell(1);
				HSSFCell companycell=row.getCell(2);
				HSSFCell deptidcell=row.getCell(3);
				HSSFCell gradecell=row.getCell(4);
				HSSFCell plancell=row.getCell(5);
			 
				
				HSSFCell packagecell=row.getCell(6);
				HSSFCell maxmountcell=row.getCell(7);
				HSSFCell typecell=row.getCell(8);
				HSSFCell datecell=row.getCell(9);
				HSSFCell feecell=row.getCell(10);
				HSSFCell amountcell=row.getCell(11);
				HSSFCell termcell=row.getCell(12);
				HSSFCell normalcell=row.getCell(13);
				HSSFCell specialcell=row.getCell(14);
				HSSFCell dentalcell=row.getCell(15);
				HSSFCell regularcell=row.getCell(16);
				HSSFCell monthcell=row.getCell(17);
				
			 
				/**给数据库里面的字段赋值**/
				String staffcode =Util.cellToString(EmployeeIdcell);
				String staffname=Util.cellToString(staffnamecell);// 
				String company=Util.cellToString(companycell);
				String deptid=Util.cellToString(deptidcell);
				String grade=Util.cellToString(gradecell);
				String plan=Util.cellToString(plancell);
				String Package=Util.cellToString(packagecell);
				String maxmount =Util.cellToString(maxmountcell);
				String type=Util.cellToString(typecell);
				String date =Util.cellToString(datecell);
				String fee =Util.cellToString(feecell);
				String amount =Util.cellToString(amountcell);
				String term=Util.cellToString(termcell);
				String Normal=Util.cellToString(normalcell);
				String Special=Util.cellToString(specialcell);
				String dental=Util.cellToString(dentalcell);
				String regular=Util.cellToString(regularcell);
				String month=Util.cellToString(monthcell);
				
				if(staffcode.length()<9 && staffcode.length() > 1){
					sql = "insert medical_claim_staff (staffcode,name,company,dept,grade,plan,package,email,maxamount,type,term,medical_Normal,medical_Special,medical_Regular,medical_Dental," +
					"medical_date,medical_fee,medical_month,amount,return_oraginal,SameDay,upd_Name,upd_Date,sfyx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					ps = con.prepareStatement(sql);
					ps.setString(1,staffcode );
					ps.setString(2,staffname );
					ps.setString(3,company );
					ps.setString(4,deptid);
					ps.setString(5,grade );
					ps.setString(6,plan );
					ps.setString(7,Package );
					ps.setString(8,"");
					ps.setString(9,maxmount );
					ps.setString(10,type );
					ps.setString(11,term );
					ps.setString(12,Normal );
					ps.setString(13,Special );
					ps.setString(14,regular );
					ps.setString(15,dental );
					ps.setString(16,date );
					ps.setString(17,fee );
					ps.setString(18,month );
					ps.setString(19,amount );
					ps.setString(20,"");
					ps.setString(21,"N" );
					ps.setString(22,username );
					ps.setString(23, DateUtils.getNowDateTime());
					ps.setString(24, "Y");
				//	System.out.println(staffcode);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入staff_list成功！");
						num++;
					} else {
						logger.info("插入staff_list失敗");
					}
				}
			}
		}catch(NullPointerException e){
			//读取了没有数据的表格
		}catch (Exception e) {
			e.printStackTrace();
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	
	}

}
