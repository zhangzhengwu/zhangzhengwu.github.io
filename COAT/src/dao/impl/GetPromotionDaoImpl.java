package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.StringHelper;
import util.Util;
import dao.GetPromotionDao;

/**
 * Promotion
 * 
 * @author Wilson
 * 
 */
public class GetPromotionDaoImpl implements GetPromotionDao {

	Logger logger = Logger.getLogger(GetrMedicalDaoImpl.class);

	/**
	 * 保存Promotion表
	 */
	public int savePromotion(String filename, InputStream os) {
		PreparedStatement ps = null;
		String sql = "";
		Connection con = null;
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("promotion_c_report");
		List<String> list=new ArrayList<String>();
		/** 删除promotion_c_report表 * */
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据* */
				HSSFCell staffcodecell = row.getCell(3);
				HSSFCell DD_VPcell = row.getCell(4);
				HSSFCell joinTitlecell = row.getCell(5);
				HSSFCell titlecell = row.getCell(6);
				HSSFCell trainneecell = row.getCell(7);
				HSSFCell staffNamecell = row.getCell(8);
				HSSFCell groupJoincell = row.getCell(9);
				HSSFCell resignDatecell = row.getCell(10);
				HSSFCell trAppDatecell = row.getCell(11);
				HSSFCell tr_Issue_Datecell = row.getCell(12);
				HSSFCell lrAppDatecell = row.getCell(13);
				HSSFCell lrIssueDatecell = row.getCell(14);
				HSSFCell LR_Normal_Licensecell = row.getCell(15);
				HSSFCell ITCompletedcell = row.getCell(16);
				HSSFCell ITPhaseTwocell = row.getCell(17);

				HSSFCell half_conscell = row.getCell(18);
				HSSFCell conscell = row.getCell(19);
				HSSFCell S_cons1cell = row.getCell(20);
				HSSFCell S_cons2cell = row.getCell(21);
				HSSFCell Principal_conscell = row.getCell(22);
				HSSFCell wealth_Managercell = row.getCell(23);
				HSSFCell S_Wealth_Manager1cell = row.getCell(24);
				HSSFCell S_Wealth_Manager2cell = row.getCell(25);
				HSSFCell P_Wealth_Managercell = row.getCell(26);
				HSSFCell Associate_AADcell = row.getCell(27);
				HSSFCell Ass_Director_ADcell = row.getCell(28);
				
				/**
				 * 2015-3-9 17:39:20  King 新增
				 */
				HSSFCell VPCell=row.getCell(32);
				HSSFCell SVPCell=row.getCell(33);
				/**
				 * end
				 */

				/** 给数据库里面的字段赋值* */
				String staffcode = Util.cellToString(staffcodecell);
				String DD_VP = Util.cellToString(DD_VPcell);
				String joinTitle = Util.cellToString(joinTitlecell);
				String title = Util.cellToString(titlecell);
				String trainnee = Util.cellToString(trainneecell);
				String staffName = Util.cellToString(staffNamecell);
				String groupJoin = Util.cellToString(groupJoincell);
				String resignDate = Util.cellToString(resignDatecell);
				String trAppDate = Util.cellToString(trAppDatecell);
				String tr_Issue_Date = Util.cellToString(tr_Issue_Datecell);
				String lrAppDate = Util.cellToString(lrAppDatecell);
				String lrIssueDate = Util.cellToString(lrIssueDatecell);
				String LR_Normal_License = Util
						.cellToString(LR_Normal_Licensecell);
				String ITCompleted = Util.cellToString(ITCompletedcell);
				String ITPhaseTwo = Util.cellToString(ITPhaseTwocell);

				String half_cons = Util.cellToString(half_conscell);
				String cons = Util.cellToString(conscell);
				String S_cons1 = Util.cellToString(S_cons1cell);
				String S_cons2 = Util.cellToString(S_cons2cell);
				String Principal_cons = Util.cellToString(Principal_conscell);
				String wealth_Manager = Util.cellToString(wealth_Managercell);
				String S_Wealth_Manager1 = Util
						.cellToString(S_Wealth_Manager1cell);
				String S_Wealth_Manager2 = Util
						.cellToString(S_Wealth_Manager2cell);
				String P_Wealth_Manager = Util
						.cellToString(P_Wealth_Managercell);
				String Associate_AAD = Util.cellToString(Associate_AADcell);
				String Ass_Director_AD = Util.cellToString(Ass_Director_ADcell);
				/**
				 * 2015-3-9 17:39:20  King 新增
				 */
				String VP=Util.cellToString(VPCell);
				String SVP=Util.cellToString(SVPCell);
				/**
				 * end
				 */
				if (!Util.objIsNULL(staffcode)&&!list.contains(staffcode)) {
					list.add(staffcode);
					sql = "insert promotion_c_report values('" + staffcode
							+ "','" + DD_VP + "','" + joinTitle + "','" + title
							+ "','" + trainnee + "','" + staffName + "','"
							+ groupJoin + "','" + resignDate + "','"
							+ trAppDate + "','" + tr_Issue_Date + "','"
							+ lrAppDate + "','" + lrIssueDate + "','"
							+ LR_Normal_License + "','" + ITCompleted + "','"
							+ ITPhaseTwo + "','" + StringHelper.replaceStr(half_cons) + "','" + StringHelper.replaceStr(cons)
							+ "','" + StringHelper.replaceStr(S_cons1) + "','" + StringHelper.replaceStr(S_cons2) + "','"
							+ StringHelper.replaceStr(Principal_cons) + "','" + StringHelper.replaceStr(wealth_Manager) + "','"
							+ StringHelper.replaceStr(S_Wealth_Manager1) + "','" + StringHelper.replaceStr(S_Wealth_Manager2)
							+ "','" + StringHelper.replaceStr(P_Wealth_Manager) + "','" + StringHelper.replaceStr(Associate_AAD)
							+ "','" + StringHelper.replaceStr(Ass_Director_AD) + "','"+VP+"','"+SVP+"')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入promotion_c_report成功！");
						num++;
					} else {
						logger.info("插入promotion_c_report失敗");
					}
				} else {
					logger.info("插入promotion_c_report code is null！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入promotion_c_report异常！" + e);
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public static void main(String[] args) {
		System.out.println("start");
		//savePromotion();
		System.out.println("end");
	}
	
	/**
 	 * yyyy/MM/dd”转换为格式“yyyy-MM-dd ” 
 	 * @param viewtime
 	 * @return
 	 */
 	public static String strUsToCh2(String viewtime) { 
 		if(Util.objIsNULL(viewtime)){
			return"";
		}else if("n/a".equalsIgnoreCase(viewtime)){
			return "n/a";
		}else if("null".equalsIgnoreCase(viewtime)){
			return "";
		}
 		Date time = new Date(); 

    	//Z 对于格式化来说，使用 RFC 822 4-digit 时区格式 ,Locale.US表示使用了美国时间 
    	SimpleDateFormat sdf = 
    		new SimpleDateFormat("yyyy/MM/dd", Locale.US); 

 	   try {
			time = sdf.parse(viewtime);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 

			viewtime = sdf2.format(time); 
			
 	   } catch (ParseException e) {
			e.printStackTrace();
		}
 	   return viewtime;
 	}
 	
 	/**
 	 * dd/MM/yyyy”转换为格式“yyyy-MM-dd ” 
 	 * @param viewtime
 	 * @return
 	 */
 	public static String strUsToCh3(String viewtime) {
 		if(Util.objIsNULL(viewtime)){
			return"";
		}else if("n/a".equalsIgnoreCase(viewtime)){
			return "n/a";
		}else if("null".equalsIgnoreCase(viewtime)){
			return "";
		}
 		Date time = new Date(); 
    	//Z 对于格式化来说，使用 RFC 822 4-digit 时区格式 ,Locale.US表示使用了美国时间 
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US); 
 	   try {
			time = sdf.parse(viewtime);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 
			viewtime = sdf2.format(time); 
 	   } catch (ParseException e) {
			e.printStackTrace();
		}
 	   return viewtime;
 	}
	/**
	 * 格式化日期为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String strToYMD(String date){
		try {
			if(Util.objIsNULL(date)){
				return"";
			}else if("n/a".equalsIgnoreCase(date)){
				return "n/a";
			}else if("null".equalsIgnoreCase(date)){
				return "";
			}
			Date time = new Date(); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			time = sdf.parse(date);
			date=sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public  void savePromotionCheckingReport() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con2=null;
		PreparedStatement  pst=null;
		try{
			
			StringBuffer sql=new StringBuffer("SELECT  [staff_code] ");
			  sql.append(",[dd_vp]");
		      sql.append(",[join_title]");
		      sql.append(",[title]");
		      sql.append(",[IsTrainee]");
		      sql.append(",[staff_name]");
		      sql.append(",a.[groupdatejoin]");
		      sql.append(",b.ResignEffectiveDate [resign_date]");
		      sql.append(",'' [tr_app_date]");
		      sql.append(",'' [tr_issue_date]");
		      sql.append(",'' [lr_app_date]");
		      sql.append(",'' [lr_issue_date]");
		      sql.append(",'' [lr_normal_license]");
			  sql.append(",'' [ITCompleted]");
			  sql.append(",'' [ITPhaseTwo]");
			  sql.append(",b.HalfConsultant half_cons");
		      sql.append(",[consultant]");
		      sql.append(",[s_consultant_1]");
		      sql.append(",[s_consultant_2]");
		      sql.append(",[Principal_consultant]");
		      sql.append(",[wealth_manager]");
		      sql.append(",[s_wealth_manager]");
		      sql.append(",[s_wealth_manager_2]");
		      sql.append(",[c_wealth_manager]");
		      sql.append(",[aad]");
		      sql.append(",[ad]");
		      sql.append(",[vp]");
		      sql.append(",[svp]");
		      sql.append("FROM [SZO_SYSTEM].[dbo].[vSZO_HRPStaffRecord] a left join  [vSZOADM2] b on a.staff_code=b.EmployeeID ");
			con=DBManager_sqlservler.getCon();
			ps=con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			con2=DBManager.getCon();
			con2.setAutoCommit(false);
			pst=con2.prepareStatement("delete from promotion_c_report;");
			pst.execute();
			pst=con2.prepareStatement("insert into promotion_c_report values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			while(rs.next()){
				pst.setString(1,rs.getString("staff_code"));
				pst.setString(2,rs.getString("dd_vp"));
				pst.setString(3,rs.getString("join_title"));
				pst.setString(4,rs.getString("title"));
				pst.setString(5,rs.getString("IsTrainee"));
		      	pst.setString(6,rs.getString("staff_name"));
		      	pst.setString(7,strToYMD(rs.getString("groupdatejoin")));
		      	pst.setString(8,(rs.getString("resign_date")+"").replace(" 00:00:00.0", ""));
		      	pst.setString(9,rs.getString("tr_app_date"));
		      	pst.setString(10,rs.getString("tr_issue_date"));
		      	pst.setString(11,rs.getString("lr_app_date"));
		      	pst.setString(12,rs.getString("lr_issue_date"));
		      	pst.setString(13,rs.getString("lr_normal_license"));
		      	pst.setString(14,rs.getString("ITCompleted"));
		      	pst.setString(15,rs.getString("ITPhaseTwo"));
		      	pst.setString(16,strToYMD(rs.getString("half_cons")));
		      	pst.setString(17,strToYMD(rs.getString("consultant")));
		      	pst.setString(18,strToYMD(rs.getString("s_consultant_1")));
		      	pst.setString(19,strToYMD(rs.getString("s_consultant_2")));
		      	pst.setString(20,strToYMD(rs.getString("Principal_consultant")));
		      	pst.setString(21,strToYMD(rs.getString("wealth_manager")));
		      	pst.setString(22,strToYMD(rs.getString("s_wealth_manager")));
		      	pst.setString(23,strToYMD(rs.getString("s_wealth_manager_2")));
		      	pst.setString(24,strToYMD(rs.getString("c_wealth_manager")));
		      	pst.setString(25,strToYMD(rs.getString("aad")));
		      	pst.setString(26,strToYMD(rs.getString("ad")));
		      	pst.setString(27,strToYMD(rs.getString("vp")));
		      	pst.setString(28,strToYMD(rs.getString("svp")));
		      	pst.addBatch();
		      	
				/*System.out.println(rs.getString("staff_code"));
				System.out.println(rs.getString("dd_vp"));
				System.out.println(rs.getString("join_title"));
				System.out.println(rs.getString("title"));
				System.out.println(rs.getString("IsTrainee"));
		      	System.out.println(rs.getString("staff_name"));
		      	System.out.println(strToYMD(rs.getString("groupdatejoin")));
		      	System.out.println((rs.getString("resign_date")+"").replace("00:00:00.0", ""));
		      	System.out.println(rs.getString("tr_app_date"));
		      	System.out.println(rs.getString("tr_issue_date"));
		      	System.out.println(rs.getString("lr_app_date"));
		      	System.out.println(rs.getString("lr_issue_date"));
		      	System.out.println(rs.getString("lr_normal_license"));
		      	System.out.println(rs.getString("ITCompleted"));
		      	System.out.println(rs.getString("ITPhaseTwo"));
		      	System.out.println(strToYMD(rs.getString("half_cons")));
		      	System.out.println(strToYMD(rs.getString("consultant")));
		      	System.out.println(strToYMD(rs.getString("s_consultant_1")));
		      	System.out.println(strToYMD(rs.getString("s_consultant_2")));
		      	System.out.println(strToYMD(rs.getString("Principal_consultant")));
		      	System.out.println(strToYMD(rs.getString("wealth_manager")));
		      	System.out.println(strToYMD(rs.getString("s_wealth_manager")));
		      	System.out.println(strToYMD(rs.getString("s_wealth_manager_2")));
		      	System.out.println(strToYMD(rs.getString("c_wealth_manager")));
		      	System.out.println(strToYMD(rs.getString("aad")));
		      	System.out.println(strToYMD(rs.getString("ad")));
		      	System.out.println(strToYMD(rs.getString("vp")));
		      	System.out.println(strToYMD(rs.getString("svp")));
		      	
		      	System.out.println("==========================");*/
			}
			rs.close();
			pst.executeBatch();
			StringBuffer updatesql=new StringBuffer("update promotion_c_report set ");
			updatesql.append("resignDate=if(resignDate='null','',replace(resignDate,'00:00:00.000','')),");
			updatesql.append("half_cons=if(half_cons='null','',replace(half_cons,'00:00:00.000','')),");
			updatesql.append("groupjoin=REPLACE(groupjoin,'00:00:00.000',''),");
			updatesql.append("cons=if(cons='1990-01-01 00:00:00.000' or cons ='null','',replace(cons,'00:00:00.000','')),");
			updatesql.append("s_cons1=if(s_cons1='1990-01-01 00:00:00.000' or s_cons1 ='null','',replace(s_cons1,'00:00:00.000','')),");
			updatesql.append("s_cons2=if(s_cons2='1990-01-01 00:00:00.000' or s_cons2 ='null','',replace(s_cons2,'00:00:00.000','')),");
			updatesql.append("Principal_cons=if(Principal_cons='1990-01-01 00:00:00.000' or Principal_cons ='null','',replace(Principal_cons,'00:00:00.000','')),");
			updatesql.append("Wealth_Manager=if(Wealth_Manager='1990-01-01 00:00:00.000' or Wealth_Manager ='null','',replace(Wealth_Manager,'00:00:00.000','')),");
			updatesql.append("S_Wealth_Manager1=if(S_Wealth_Manager1='1990-01-01 00:00:00.000' or S_Wealth_Manager1 ='null','',replace(S_Wealth_Manager1,'00:00:00.000','')),");
			updatesql.append("S_Wealth_Manager2=if(S_Wealth_Manager2='1990-01-01 00:00:00.000' or S_Wealth_Manager2 ='null','',replace(S_Wealth_Manager2,'00:00:00.000','')),");
			updatesql.append("P_Wealth_Manager=if(P_Wealth_Manager='1990-01-01 00:00:00.000' or P_Wealth_Manager ='null','',replace(P_Wealth_Manager,'00:00:00.000','')),");
			updatesql.append("Associate_AAD=if(Associate_AAD='1990-01-01 00:00:00.000' or Associate_AAD ='null','',replace(Associate_AAD,'00:00:00.000','')),");
			updatesql.append("Ass_Director_AD=if(Ass_Director_AD='1990-01-01 00:00:00.000' or Ass_Director_AD ='null','',replace(Ass_Director_AD,'00:00:00.000','')),");
			updatesql.append("vp=if(vp='1990-01-01 00:00:00.000' or vp ='null','',replace(vp,'00:00:00.000','')),");
			updatesql.append("svp=if(svp='1990-01-01 00:00:00.000' or svp ='null','',replace(svp,'00:00:00.000',''))");
			pst=con2.prepareStatement(updatesql.toString());
			pst.execute();
			con2.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con2.rollback();
			} catch (SQLException e1) {
				System.out.println("回滚失败"+e1.toString());
				e1.printStackTrace();
			}
		}finally{
			try{
				rs.close();
				con.close();
				con2.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * 2018-01-25 Orlando新增定时任务，用于新方式获取报销限制条件数据
	 */
	public  void saveNewPromotionCheckingReport() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con2=null;
		PreparedStatement  pst=null;
		try{
			
			StringBuffer sql=new StringBuffer("SELECT distinct [ConsultantId] ");
			sql.append(",(b.[Alias]+' '+b.[EmployeeName]) as staff_name ");
			sql.append(",a.[GradeId] ");
			sql.append(",[InternalPositionId] ");
			sql.append(",[ExternalPositionId] ");
			sql.append(",[Reason] ");
			sql.append(",b.ResignEffectiveDate [resign_date] ");
			sql.append(",b.HalfConsultant half_cons ");
			sql.append("FROM [SZO_SYSTEM].[dbo].[vSZO_SOS_ConsultantInfo] a left join [vSZOADM2] b on a.ConsultantId=b.EmployeeID where EmployeeCompanyCode = 'CFS' ");
			con=DBManager_sqlservler.getCon();
			ps=con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			con2=DBManager.getCon();
			con2.setAutoCommit(false);
			pst=con2.prepareStatement("delete from promotion_c_data;");
			pst.execute();
			pst=con2.prepareStatement("insert into promotion_c_data values(?,?,?,?,?,?,?,?)");
			while(rs.next()){
				pst.setString(1,rs.getString("ConsultantId"));
				pst.setString(2,rs.getString("staff_name"));
				pst.setString(3,rs.getString("GradeId"));
				pst.setString(4,rs.getString("InternalPositionId"));
				pst.setString(5,rs.getString("ExternalPositionId"));
				pst.setString(6,rs.getString("Reason"));
				pst.setString(7,strToYMD(rs.getString("resign_date")));
				pst.setString(8,rs.getString("half_cons"));
				pst.addBatch();
			}
			rs.close();
			pst.executeBatch();
			StringBuffer updatesql=new StringBuffer("update promotion_c_data set ");
			updatesql.append("resignDate=if(resignDate='null','',replace(resignDate,'00:00:00.000','')),");
			updatesql.append("halfCons=if(halfCons='null','',replace(halfCons,'00:00:00.0',''))");
			pst=con2.prepareStatement(updatesql.toString());
			pst.execute();
			con2.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con2.rollback();
			} catch (SQLException e1) {
				System.out.println("回滚失败"+e1.toString());
				e1.printStackTrace();
			}
		}finally{
			try{
				rs.close();
				con.close();
				con2.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
