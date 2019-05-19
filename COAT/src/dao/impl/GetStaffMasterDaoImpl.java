package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.Util;
import dao.GetStaffMasterDao;
import entity.StaffMasterBean;
/**
 * 上传StaffMaster数据
 * @author King.XU
 *
 */
public class GetStaffMasterDaoImpl implements GetStaffMasterDao {
	/**
	 * 上传数据-保存
	 * @param filename
	 * @param os
	 * @return
	 */
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetStaffMasterDaoImpl.class);
	public int saveStaffMaster(String filename, InputStream os) {
			int num=0;
			int beginRowIndex = 1;// 开始读取数据的行数
			int totalRows = 0;// 总行数
			Util.deltables("staff_master");	/**先清表，后插入**/
			try {
				con = DBManager.getCon();
				HSSFWorkbook workbook = new HSSFWorkbook(os);
				HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
				totalRows = sheet.getLastRowNum();// 获取总行数
				for (int i = beginRowIndex; i <= totalRows; i++) {
					HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
					/**获取Excel里面的指定单元格数据**/
					HSSFCell StaffNocell = row.getCell(0);
					HSSFCell Namecell=row.getCell(1);
					HSSFCell C_Namecell=row.getCell(2);
					HSSFCell E_Title_Departmentcell=row.getCell(3);
					HSSFCell C_Title_Departmentcell=row.getCell(4);
					HSSFCell E_ExternalTitle_Departmentcell=row.getCell(5);
					HSSFCell C_ExternalTitle_Departmentcell=row.getCell(6);
					HSSFCell E_EducationTitlecell=row.getCell(7);
					HSSFCell C_EducationTitlecell=row.getCell(8);
					HSSFCell TR_RegNocell=row.getCell(9);
					HSSFCell CE_Nocell=row.getCell(10);
					HSSFCell MPF_Nocell=row.getCell(11);
					HSSFCell Emailcell=row.getCell(12);
					HSSFCell DirectLinecell=row.getCell(13);
					HSSFCell Faxcell=row.getCell(14);
					HSSFCell MobilePhonecell=row.getCell(15);
					HSSFCell Numcell=row.getCell(16);

					/**给数据库里面的字段赋值**/
					String StaffNo = Util.cellToString(StaffNocell);
					String Name=Util.cellToString(Namecell);
					String C_Name=Util.cellToString(C_Namecell);
					String E_Title_Department=Util.cellToString(E_Title_Departmentcell);
					String C_Title_Department=Util.cellToString(C_Title_Departmentcell);
					String E_ExternalTitle_Department=Util.cellToString(E_ExternalTitle_Departmentcell);
					String C_ExternalTitle_Department=Util.cellToString(C_ExternalTitle_Departmentcell);
					String E_EducationTitle=Util.cellToString(E_EducationTitlecell);
					String C_EducationTitle=Util.cellToString(C_EducationTitlecell);
					String TR_RegNo=Util.cellToString(TR_RegNocell);
					String CE_No=Util.cellToString(CE_Nocell);
					String MPF_No=Util.cellToString(MPF_Nocell);
					String Email=Util.cellToString(Emailcell);
					String DirectLine=Util.cellToString(DirectLinecell);
					String Fax=Util.cellToString(Faxcell);
					String MobilePhone=Util.cellToString(MobilePhonecell);
					String Num=Util.cellToString(Numcell);
					
					sql = "insert staff_master values('"+StaffNo +"','"+Name +"','"+C_Name +"','"+E_Title_Department +"','"+C_Title_Department +"','"
					+E_ExternalTitle_Department +"','"+C_ExternalTitle_Department +"','"+E_EducationTitle +"','"
					+C_EducationTitle +"','"+TR_RegNo +"','"+CE_No +"','"+MPF_No +"','"+Email +"','"+DirectLine +"','"+Fax +"','"+MobilePhone +"','"+Num+"',"+"NOW() "+")";
					
					logger.info("插入staff_master_SQL:"+sql);
					
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入staff_master成功！");
						num++;
					} else {
						logger.info("插入staff_master失敗");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("插入staff_master异常！"+e);
			} finally {
				//关闭连接
				DBManager.closeCon(con);
			}
			return num;
		}

	/**
	 * 查询 StaffMaster
	 */
	public List<StaffMasterBean> getStaffaster(String StaffNo) {

		List<StaffMasterBean> list=new ArrayList<StaffMasterBean>();
		StaffMasterBean cs=new StaffMasterBean();
		try {
			con=DBManager.getCon();
			 
			sql= "select l.staffcode,l.staffname as englishname,C_Name,E_Title_Department,C_Title_Department,E_ExternalTitle_Department,"+
				"C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,TR_RegNo,CE_No,MPF_No,l.Email,"+
				"DirectLine,Fax,MobilePhone,Num,submit_date "+ 
				"from staff_list as l left join staff_master as s on(s.staffNo=l.staffcode)  where l.staffcode = ?  order by submit_date desc limit 0,1";
			logger.info("查询 StaffMaster SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, StaffNo);
			ResultSet rs=null;
			rs=	ps.executeQuery();
		
			if(rs.next()){
				cs.setStaffNo(rs.getString(1));
				cs.setName(rs.getString(2));
				cs.setC_Name(rs.getString(3));
				cs.setE_Title_Department(rs.getString(4));
				cs.setC_Title_Department(rs.getString(5));
				cs.setE_ExternalTitle_Department(rs.getString(6));
				cs.setC_ExternalTitle_Department(rs.getString(7));
				cs.setE_EducationTitle(rs.getString(8));
				cs.setC_EducationTitle(rs.getString(9));
				cs.setTr_RegNo(rs.getString(10));
				cs.setCe_No(rs.getString(11));
				cs.setMPF_No(rs.getString(12));
				cs.setEmail(rs.getString(13));
				cs.setDirectLine(rs.getString(14));
				cs.setFax(rs.getString(15));
				cs.setMobilePhone(rs.getString(16));
				cs.setNum(rs.getString(17));
				
				list.add(cs);
			}
			else{
				list=null;
			}
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 StaffMaster异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 StaffMaster异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 * 查询 StaffMaster
	 */
	public List<StaffMasterBean> getStaffasterandList(String StaffNo) {
		
		List<StaffMasterBean> list=new ArrayList<StaffMasterBean>();
		StaffMasterBean cs=new StaffMasterBean();
		try {
			con=DBManager.getCon();
			
			sql= "select staffNo,Name as englishname,C_Name,E_Title_Department,C_Title_Department,E_ExternalTitle_Department,"+
			"C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,TR_RegNo,CE_No,MPF_No,Email,"+
			"DirectLine,Fax,MobilePhone,Num,submit_date "+
			"from staff_master  where staffNo = '"+StaffNo+"'  order by submit_date desc limit 0,1";
			logger.info("查询 StaffMaster SQL:"+sql);
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=	ps.executeQuery();
			
			if(rs.next()){
				cs.setStaffNo(rs.getString(1));
				cs.setName(rs.getString(2));
				cs.setC_Name(rs.getString(3));
				cs.setE_Title_Department(rs.getString(4));
				cs.setC_Title_Department(rs.getString(5));
				cs.setE_ExternalTitle_Department(rs.getString(6));
				cs.setC_ExternalTitle_Department(rs.getString(7));
				cs.setE_EducationTitle(rs.getString(8));
				cs.setC_EducationTitle(rs.getString(9));
				cs.setTr_RegNo(rs.getString(10));
				cs.setCe_No(rs.getString(11));
				cs.setMPF_No(rs.getString(12));
				cs.setEmail(rs.getString(13));
				cs.setDirectLine(rs.getString(14));
				cs.setFax(rs.getString(15));
				cs.setMobilePhone(rs.getString(16));
				cs.setNum(rs.getString(17));
				
				list.add(cs);
			}
			else{
				list=null;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 StaffMaster异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 StaffMaster异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}
 
}

