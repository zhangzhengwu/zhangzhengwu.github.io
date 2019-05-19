package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Util;

import dao.GetrMedicalDao;
/**
 * medical_claim_record
 * @author Wilson
 *
 */
public class GetrMedicalDaoImpl implements GetrMedicalDao {

		Logger logger = Logger.getLogger(GetrMedicalDaoImpl.class);
		/**
		 * 保存medical_claim_record表
		 */
		public int saveMedical(String filename, InputStream os) {
			PreparedStatement ps = null;
			String sql = "";
			Connection con = null;
			int num=0;
			int beginRowIndex = 4;// 开始读取数据的行数
			int totalRows = 0;// 总行数
			Util.deltables("medical_claim_record");		 /**删除medical_claim_record表 **/
			try {
				con = DBManager.getCon();
				POIFSFileSystem poiSystem=  new POIFSFileSystem(os);
				HSSFWorkbook workbook = new HSSFWorkbook(poiSystem); 
				HSSFSheet sheet = workbook.getSheet(Constant.MEDICAL_SHEET);//读取Sheet name
				totalRows = sheet.getLastRowNum();// 获取总行数
				for (int i = beginRowIndex; i <= totalRows; i++) {
					HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				 
					/**获取Excel里面的指定单元格数据**/
					HSSFCell staffcodecell = row.getCell(2);
					HSSFCell namecell = row.getCell(3);
					HSSFCell AD_typecell=row.getCell(4);
					HSSFCell SP_typecell=row.getCell(5);
					HSSFCell medical_datecell=row.getCell(6);
					HSSFCell medical_Feecell=row.getCell(7);
					HSSFCell entitled_Feecell=row.getCell(8);
					HSSFCell Terms_yearcell=row.getCell(9);
					HSSFCell medical_monthcell=row.getCell(10);
					HSSFCell medical_Normalcell=row.getCell(11);
					HSSFCell medical_Specialcell=row.getCell(12);
					HSSFCell staff_CodeDatecell=row.getCell(13);
					HSSFCell SameDayecell=row.getCell(14);
					HSSFCell Half_Consultantcell=row.getCell(15);
					/**给数据库里面的字段赋值**/
					String staffcode = Util.cellToString(staffcodecell);
					String name =Util.cellToString(namecell);
					String AD_type =Util.cellToString(AD_typecell);
					String SP_type =Util.cellToString(SP_typecell);
					String medical_date  =Util.cellToString(medical_datecell);
					String medical_Fee  =Util.cellToString(medical_Feecell);
					String entitled_Fee  =Util.cellToString(entitled_Feecell);
					String Terms_year  =Util.cellToString(Terms_yearcell);
					String medical_month  =Util.cellToString(medical_monthcell);
					String medical_Normal  =Util.cellToString(medical_Normalcell);
					String medical_Special  =Util.cellToString(medical_Specialcell);
					String staff_CodeDate  =Util.cellToString(staff_CodeDatecell);
					String SameDaye  =Util.cellToString(SameDayecell);
					String Half_Consultant =Util.cellToString(Half_Consultantcell);
					String upd_date = Util.objIsNULL(medical_date)?DateUtils.getNowDateTime():medical_date;
					if(!Util.objIsNULL(staffcode)){
						sql = "insert medical_claim_record values('"+staffcode+"','"+name +"','"+AD_type +"','"+SP_type +"','"+medical_date+"','"+medical_Fee+"','"+entitled_Fee+"',"+Integer.parseInt(Terms_year)+",'"+medical_month+"','"+medical_Normal+"','"+medical_Special+"','"+staff_CodeDate+"','"+SameDaye+"','"+Half_Consultant+"','admin','"+upd_date+"','Y')";
						logger.info("插入medical_claim_record SQL！"+sql);
						ps = con.prepareStatement(sql);
						int rsNum = ps.executeUpdate();
						if (rsNum > 0) {
							logger.info("插入medical_claim_record成功！");
							num++;
						} else {
							logger.info("插入medical_claim_record失敗");
						}
					}else {
						logger.info("插入medical_claim_record code is null！");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("插入medical_claim_record异常！"+e);
			} finally {
				//关闭连接
				DBManager.closeCon(con);
			}
			return num;
		}


}
