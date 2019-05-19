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
import util.DateUtils;
import util.Util;
import dao.GetCNMDao;
import dao.QueryPositionDao;
import entity.Consultant_Master;
import entity.RequestNewBean;
/**
 * GetCNMDao 实现类
 * @author King.XU
 *
 */
public class GetCNMDaoImpl implements GetCNMDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetCNMDaoImpl.class);
	/**
	 * 导入数据
	 */
	public int saveCNM(String filename, InputStream os) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("cn_master");							/**           等待填写                      **/
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
				sql = "insert cn_master values('"+StaffNo +"','"+Name +"','"+C_Name +"','"+E_Title_Department +"','"+C_Title_Department +"','"
				+E_ExternalTitle_Department +"','"+C_ExternalTitle_Department +"','"+E_EducationTitle +"','"
				+C_EducationTitle +"','"+TR_RegNo +"','"+CE_No +"','"+MPF_No +"','"+Email +"','"+DirectLine +"','"+Fax +"','"+MobilePhone +"','"+Num+"','"+DateUtils.getNowDateTime()+"')";
				ps = con.prepareStatement(sql);
				int rsNum = ps.executeUpdate();
				if (rsNum > 0) {
					logger.info("插入cn_master成功！");
					num++;
				} else {
					logger.info("插入cn_master失敗");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入cn_master表异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 查询所有数据
	 */
	public List<Consultant_Master> getConsList(String StaffNo) {
		List<Consultant_Master> list=new ArrayList<Consultant_Master>();
		Consultant_Master cs=new Consultant_Master();
		try {
			con=DBManager.getCon();
			sql= "select a.EmployeeId as StaffNo,if(Alias ='',EmployeeName,CONCAT(a.EmployeeName, CONCAT(' ',a.Alias))) as englishname,a.C_Name,a.E_PositionName,'',"+
			" E_ExternalTitle_Department,C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,"+
			" b.TrRegNo,b.Ce_No,b.MPF,a.Email,a.DirectLine,c.fax as fax,c.Mobilephone,c.Num as num ,E_Title_Department,C_Title_Department,a.Mobile, CASE WHEN DATEDIFF(NOW(),CASE when c.submit_date ='' or submit_date is null then '1911-01-01' else c.submit_date end) > 7 THEN 'N' ELSE 'Y' END AS sfts "+
			" ,a.Grade,a.RecruiterId from cons_list a left join  tr b  on a.EmployeeId =b.StaffNo  left join "+
			" (select * from cn_master  where staffNo = ? and Num>0 order by submit_date desc limit 0,1) c on a.EmployeeId = c.staffno"+
			" where a.EmployeeId = ? limit 0,1";
			logger.info("查询所有数据SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, StaffNo);
			ps.setString(2, StaffNo);
			ResultSet rs=null;
			rs=	ps.executeQuery();
			QueryPositionDao qdao = new QueryPositionDaoImpl();
		 
				List<String> lists = qdao.queryPositionEName();
			if(rs.next()){
				cs.setStaffNo(rs.getString(1));
				cs.setName(rs.getString(2));
				cs.setC_Name(rs.getString(3));
				/***
				cs.setE_DepartmentTitle(rs.getString(4));
				cs.setC_DepartmentTitle(Util.objIsNULL(rs.getString(5))?rs.getString(4):rs.getString(5));
				 **/
				cs.setLastPosition_E(rs.getString(4));
				cs.setLastPosition_C(rs.getString(5));

				cs.setE_ExternalTitle(rs.getString(6));
				cs.setC_ExternalTitle(rs.getString(7));
				cs.setE_EducationTitle(rs.getString(8));
				cs.setC_EducationTitle(rs.getString(9));
				cs.setTR_RegNo(rs.getString(10));
				cs.setCENo(rs.getString(11));
				cs.setMPFNo(rs.getString(12));
				cs.setEmail(rs.getString(13));
				cs.setDirectLine(rs.getString(14));
				cs.setFax(rs.getString(15));
				cs.setMobilePhone(Util.objIsNULL(rs.getString(16))?rs.getString(20):rs.getString(16));
				cs.setNum(rs.getString(17));
				/**
				cs.setLastPosition_E(rs.getString(18));
				cs.setLastPosition_C(rs.getString(19));
				 **/
				/*cs.setE_DepartmentTitle(Util.objIsNULL(rs.getString(18))?rs.getString(4):rs.getString(18));
				cs.setC_DepartmentTitle(Util.objIsNULL(rs.getString(19))?rs.getString(5):rs.getString(19));
				*/
				
				
		
				String positionName="";
				 if (!Util.objIsNULL(rs.getString(4))) {
						for (int i = 0; i < lists.size(); i++) {
							String liststr = (String) lists.get(i);
							if (rs.getString(4).length() >= liststr.length()) {
								if(rs.getString(4).substring(0, liststr.length()).equals(liststr)){
									positionName = liststr;
									 break; //有一个匹配 则跳出循环
								}
							}
						}
					}
				
				cs.setE_DepartmentTitle(positionName);
				cs.setC_DepartmentTitle(rs.getString(5));
				cs.setSfcf(rs.getString(21));
				cs.setGrade(rs.getString(22));
				cs.setRecruiterId(rs.getString(23));
				list.add(cs);
				return list;
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询所有数据异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询所有数据异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 * 查询历史数据
	 */
	public List<Consultant_Master> getCMaster(String StaffNo) {
		List<Consultant_Master> list=new ArrayList<Consultant_Master>();
		Consultant_Master cs=new Consultant_Master();
		try {
			con=DBManager.getCon();
			sql= "select staffNo,Name as englishname,C_Name,E_Title_Department,C_Title_Department,E_ExternalTitle_Department,"+
			"C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,TR_RegNo,CE_No,MPF_No,Email,"+
			"DirectLine,Fax,MobilePhone,Num,submit_date "+
			"from cn_master  where staffNo = '"+StaffNo+"'  order by submit_date desc limit 0,1";
			logger.info("查询历史数据SQL:"+sql);
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=	ps.executeQuery();
			if(rs.next()){
				cs.setStaffNo(rs.getString(1));
				cs.setName(rs.getString(2));
				cs.setC_Name(rs.getString(3));
				cs.setE_DepartmentTitle(rs.getString(4));
				cs.setC_DepartmentTitle(rs.getString(5));
				cs.setE_ExternalTitle(rs.getString(6));
				cs.setC_ExternalTitle(rs.getString(7));
				cs.setE_EducationTitle(rs.getString(8));
				cs.setC_EducationTitle(rs.getString(9));
				cs.setTR_RegNo(rs.getString(10));
				cs.setCENo(rs.getString(11));
				cs.setMPFNo(rs.getString(12));
				cs.setEmail(rs.getString(13));
				cs.setDirectLine(rs.getString(14));
				cs.setFax(rs.getString(15));
				cs.setMobilePhone(rs.getString(16));
				cs.setNum(rs.getString(17));
				list.add(cs);
				return list;
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询历史数据异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询历史数据异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;

	}
	/**
	 * 查询原始数据
	 */
	public List<Consultant_Master> getConsTrList(String StaffNo) {
		List<Consultant_Master> list=new ArrayList<Consultant_Master>();
		Consultant_Master cs=new Consultant_Master();
		try {
			con=DBManager.getCon();

			sql= "select a.EmployeeId as StaffNo,a.EmployeeName,a.C_Name, E_PositionName, C_PositionName, '' as E_ExternalTitle ,'' as C_ExternalTitle ,"+
			" '' as E_EducationTitle,'' as C_EducationTitle,b.TrRegNo,b.Ce_No,b.MPF,a.Email,a.DirectLine,'' as fax,a.Mobile,'' as num "+
			" from cons_list a left join  tr b  on a.EmployeeId =b.StaffNo where a.EmployeeId = '"+StaffNo+"' limit 0,1";
			logger.info("查询原始数据cons_list_SQL:"+sql);
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=	ps.executeQuery();
			if(rs.next()){
				cs.setStaffNo(rs.getString(1));
				cs.setName(rs.getString(2));
				cs.setC_Name(rs.getString(3));
				cs.setE_DepartmentTitle(rs.getString(4));
				cs.setC_DepartmentTitle(rs.getString(5));
				cs.setE_ExternalTitle(rs.getString(6));
				cs.setC_ExternalTitle(rs.getString(7));
				cs.setE_EducationTitle(rs.getString(8));
				cs.setC_EducationTitle(rs.getString(9));
				cs.setTR_RegNo(rs.getString(10));
				cs.setCENo(rs.getString(11));
				cs.setMPFNo(rs.getString(12));
				cs.setEmail(rs.getString(13));
				cs.setDirectLine(rs.getString(14));
				cs.setFax(rs.getString(15));
				cs.setMobilePhone(rs.getString(16));
				cs.setNum(rs.getString(17));
				list.add(cs);
				return list;
			}
			else{
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询原始数据cons_list异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询原始数据cons_list异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;

	}
	/**
	 * 更新历史表，不更新num 普通权限
	 */
	public int updateCNM(RequestNewBean rnb) {
		int r=-1;
		try{
			con=DBManager.getCon();
			sql="update cn_master set Name='"+
			rnb.getName()+"' ,  C_Name='"+
			rnb.getName_chinese()+"' , E_Title_Department='"+
			rnb.getTitle_english()+"' , C_Title_Department='"+
			rnb.getTitle_chinese()+"' , E_ExternalTitle_Department='"+
			rnb.getExternal_english()+"' , C_ExternalTitle_Department='"+
			rnb.getExternal_chinese()+"' , E_EducationTitle='"+
			rnb.getProfess_title_e()+"' , C_EducationTitle='"+
			rnb.getProfess_title_c()+"' , TR_RegNo='"+
			rnb.getTr_reg_no()+"' , CE_No='"+
			rnb.getCe_no()+"' , MPF_No='"+
			rnb.getMpf_no()+"' , Email='"+
			rnb.getE_mail()+"' , DirectLine='"+
			rnb.getDirect_line()+"' , Fax='"+
			rnb.getFax()+"' , MobilePhone='"+
			rnb.getBobile_number()+"' where submit_date='"+rnb.getUrgentDate()+"' and staffNo='"+rnb.getStaff_code()+"'";

			logger.info("cn_master 更新 SQL:"+sql);
			ps=con.prepareStatement(sql);
			r=	ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("");
		}finally{
			DBManager.closeCon(con);
		}
		return r;
	}
	/**
	 * 更新历史表并更新numbers 管理员权限
	 * @param rnb
	 * @return
	 */
	public int updateNumber(RequestNewBean rnb, String reStaffNo ) {
		int r=-1;
		try{
			con=DBManager.getCon();
			sql="update cn_master set staffNo='"+rnb.getStaff_code()+"', Name='"+
			rnb.getName()+"' ,  C_Name='"+
			rnb.getName_chinese()+"' , E_Title_Department='"+
			rnb.getTitle_english()+"' , C_Title_Department='"+
			rnb.getTitle_chinese()+"' , E_ExternalTitle_Department='"+
			rnb.getExternal_english()+"' , C_ExternalTitle_Department='"+
			rnb.getExternal_chinese()+"' , E_EducationTitle='"+
			rnb.getProfess_title_e()+"' , C_EducationTitle='"+
			rnb.getProfess_title_c()+"' , TR_RegNo='"+
			rnb.getTr_reg_no()+"' , CE_No='"+
			rnb.getCe_no()+"' , MPF_No='"+
			rnb.getMpf_no()+"' , Email='"+
			rnb.getE_mail()+"' , DirectLine='"+
			rnb.getDirect_line()+"' , Fax='"+
			rnb.getFax()+"' , MobilePhone='"+
			rnb.getBobile_number()+"' , num='"+
			rnb.getQuantity()+"' where submit_date='"+rnb.getUrgentDate()+"' and staffNo='"+reStaffNo+"'";
			logger.info("修改Cn_master 表的信息   sql:==="+sql);
			ps=con.prepareStatement(sql);
			r=	ps.executeUpdate();
		}catch(Exception e){
			
			e.printStackTrace();
			logger.error("修改CN_Master 表的信息时出现异常："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return r;
	}

}
