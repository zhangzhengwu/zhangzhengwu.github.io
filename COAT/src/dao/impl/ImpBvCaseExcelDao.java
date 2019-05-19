package dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.DateUtils;
import util.Util;

/**
 * 导入bv-case数据表
 * @author Wilson
 *
 */
public class ImpBvCaseExcelDao {
	
	/**
	 * 导入Excel表格
	 * @param filename
	 * @param os
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static int InputExcel(String filename, InputStream os)
	{
		PreparedStatement ps = null;
		Connection con = null;
		int beginRowIndex =1;//从excel 中开始读取的起始行数
		int totalRows =0;//从excel 表的总行数
		int sheetId = 3; //0，1,2，3页数据
		int num = 0;
	    int count = 0;
		// 先删除数据
		DelTabBvCase();
		
		//读取导入的文件
		//Workbook workbook=null;
		try {
			//workbook = new XSSFWorkbook(new FileInputStream(
			//		new File(filename))); 
			// 根据文件的输入流，创建对Excel 工作薄文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			/**
			 * 循环4页
			 */
	 	for (int k=0; k <=sheetId; k++) {
				HSSFSheet sheet = workbook.getSheetAt(k);
				StringBuilder sb = new StringBuilder("");
				// 得到该excel 表的总行数
				totalRows = sheet.getLastRowNum();
				//得到该excel 表的总行数
				 /**
				 * 每循环一页 创建新的连接
				 */ 
				con = DBManager.getCon();
			    // 分批条数
			    int preCount = 2000;
			    int sa = 1;
			    String staffcode = "";
			    String staffcode2 = "";
			    // 计数器

			    sb.append("insert bv_case_byconsultant values ");
			    String insertSQL ="insert into bv_case_byconsultant values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			  //  System.out.println("startTime==="+DateUtils.getNowDateTime());
				//循环读取excel表格的每行记录，并逐行进行保存
				for (int i = beginRowIndex; i <= totalRows; i++) {
					HSSFRow row = sheet.getRow(i);
					//获取一行每列的数据
					HSSFCell bitidcell = row.getCell((short)0);
					HSSFCell subtimecell = row.getCell((short)1);
					HSSFCell bittypecell = row.getCell((short)2);
					HSSFCell conIdcell = row.getCell((short)3);
					HSSFCell bvcell = row.getCell((short)4);
					HSSFCell caswcell = row.getCell((short)5);
					HSSFCell policycell = row.getCell((short)6);
					HSSFCell Cnamecell = row.getCell((short)7);
					HSSFCell Comment_Datecell = row.getCell((short)8);
					HSSFCell Termcell = row.getCell((short)9);
					HSSFCell Premiumcell = row.getCell((short)10);
					HSSFCell BV_Factorcell = row.getCell((short)11);
					HSSFCell Discount_Factorcell = row.getCell((short)12);
					HSSFCell CCYcell = row.getCell((short)13);
					HSSFCell Consultant_ID1cell = row.getCell((short)14);
					HSSFCell Consultant_ID2cell = row.getCell((short)15);
					HSSFCell cBVcell = row.getCell((short)16);
					HSSFCell Assumed_BVcell = row.getCell((short)17);
					HSSFCell Case_Countcell = row.getCell((short)18);
					HSSFCell Assumed_Case_Countcell = row.getCell((short)19);
					HSSFCell Addition_BVcell = row.getCell((short)20);
					
					//将数据赋给相关的变量
					String bitid =Util.cellToBVCASEString(bitidcell);
					String subtime= Util.cellToBVCASEString(subtimecell);	
					String bittype = Util.cellToBVCASEString(bittypecell);
					String conId = Util.cellToBVCASEString(conIdcell);
					String bv = Util.cellToBVCASEString(bvcell);  
					String caseNo = Util.cellToBVCASEString(caswcell);
					String poliNo = Util.cellToBVCASEString(policycell);
					String Cname = Util.cellToBVCASEString(Cnamecell);
					String Comment_Date = Util.cellToBVCASEString(Comment_Datecell);
					String Term = Util.cellToBVCASEString(Termcell);
					String Premium = Util.cellToBVCASEString(Premiumcell);
					String BV_Factor = Util.cellToBVCASEString(BV_Factorcell);
					String Discount_Factor = Util.cellToBVCASEString(Discount_Factorcell);
					String CCY = Util.cellToBVCASEString(CCYcell);
					String Consultant_ID1 = Util.cellToBVCASEString(Consultant_ID1cell);
					String Consultant_ID2 = Util.cellToBVCASEString(Consultant_ID2cell);
					String cBV = Util.cellToBVCASEString(cBVcell);
					String Assumed_BV = Util.cellToBVCASEString(Assumed_BVcell);
					String Case_Count = Util.cellToBVCASEString(Case_Countcell);
					String Assumed_Case_Count = Util.cellToBVCASEString(Assumed_Case_Countcell);
					String Addition_BV = Util.cellToBVCASEString(Addition_BVcell); 
					String casenum = Util.objIsNULL(caseNo)?"0":caseNo;
					//判断 是否为空
					if(!Util.objIsNULL(bitid)){
						//判断顾问是否在cons list 中
						if(num == 0){
							staffcode2 = conId;
							staffcode = isHave(conId);
						}
						if (!staffcode2.equals(conId)) {
							staffcode = isHave(conId);
						}else {
							 
						}
					 
						if(!Util.objIsNULL(staffcode)){
							if(sa !=1){
								sb.append(",");
							}
							++num;
							count++;
							//在判断CASE的时候，如果F列没有显示空白的，
							if(!bv.equals("0") && casenum.equals("0") || casenum==""){
								String leader = staffcode;//getLEADER(conId);
								 //p列为空时 consno ==O列 case给1 
								if(Util.objIsNULL(Consultant_ID2)){
									if(conId.equals(Consultant_ID1)){
										casenum = "1.0";
									}
								//p列不为空时 看O列和P列是否为cons 或是 leader，case给0.5 
								}else{
									//（只显示了他们中的一个和第三个CODE,那这个CASE只能算半个CASE）
									if (conId.equals(Consultant_ID1) || leader.equals(Consultant_ID2) || conId.equals(Consultant_ID2) || leader.equals(Consultant_ID1)) {
										casenum = "0.5";
										
									//都不等 给0个case
									}else {
										casenum = "0";
									}
								}
							}
						 	ps = con.prepareStatement(insertSQL);
						 	//for (int j = 1; j <= allCount;j++) {
			                //if(i > 1)  sb.append(",");
				               sb.append(" ('"+num+"','"+bitid+"','"+ subtime+"','"+ bittype+"','"+ conId+"','"+ bv+"','"+
									casenum+"','"+poliNo+"','"+Cname+"','"+Comment_Date+"','"+Term+"','"+Premium+"','"+BV_Factor+"','"+
									Discount_Factor+"','"+CCY+"','"+Consultant_ID1+"','"+Consultant_ID2+"','"+cBV+"','"+Assumed_BV+"','"+
									Case_Count+"','"+Assumed_Case_Count+"','"+Addition_BV+"') ");  
				               sa++;
				                //当preCount条时  一次性提交
				                if(i % preCount == 0){
				                    int r = ps.executeUpdate(sb.toString());
									if(r > 0)
									{	
										ps.clearWarnings();
										ps.clearParameters();
										
										sb = new StringBuilder("");
										sb.append("insert into bv_case_byconsultant values("+(99999999+num)+",'','"+DateUtils.getDateToday()+"','','','1','0','','','','','','','','','','','','','','','')");
									//	System.out.println("--"+count);
									}else{
									//	System.out.println("error");
									}
				                }    
				                //i % preCount 剩余的数据  则逐条插入
				                if(i > (totalRows/preCount)*preCount ){
				    				int r = ps.executeUpdate(sb.toString());
									if(r > 0)
									{	
										sb = new StringBuilder("");
										sb.append("insert into bv_case_byconsultant values ("+(10000000+num)+",'','"+DateUtils.getDateToday()+"','','','1','0','','','','','','','','','','','','','','','')");
										//System.out.println("--"+num);
									}else{
										System.out.println("error");
									}
				    			}
				           staffcode2 = conId;
			            }
					}
				}
			} 
			DelTabAddition();
			//System.out.println("-------------------------addition-----------start"+DateUtils.getNowDateTime());
			HSSFSheet sheet = workbook.getSheetAt(4);
			totalRows = sheet.getLastRowNum();
			String sql="";
			con=DBManager.getCon();
			for(int i = beginRowIndex; i <= totalRows; i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell consultantCell=row.getCell(4);
				HSSFCell Year_bvCell=row.getCell(26);
				HSSFCell addition_bvCell=row.getCell(27);
				if(!Util.objIsNULL(consultantCell) ){
					count++;
					 sql ="insert into addition_bv values ('"+Util.cellToBVCASEString(consultantCell)+"','"+Util.cellToBVCASEString(Year_bvCell)+"','"+Util.cellToBVCASEString(addition_bvCell)+"')";
					 ps = con.prepareStatement(sql.toString());
					  ps.executeUpdate();
				}else{
					break ;
				}
			}
		//	System.out.println("-------------------------addition-----------end"+DateUtils.getNowDateTime());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
		return count;
	}
	/**
	 * 查询顾问是否在cons——list中
	 * @return
	 */
	public static String isHave(String cid){
		Connection conn = null;
		PreparedStatement ps = null;
		String sqlStr = "select RecruiterId from cons_list where EmployeeId = '"+cid+"'";
		String a = "";
		try {
			conn = DBManager.getCon();
			ps = conn.prepareStatement(sqlStr);
			
			ResultSet rSet = ps.executeQuery();
			while (rSet.next()) {
				a = rSet.getString(1);
			}
			rSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.closeCon(conn);

		}
		return a;
	}
	/**
	 * 获取顾问 LEADER
	 * @return
	 */
	public static String getLEADER(String cid){
		Connection conn = null;
		PreparedStatement ps = null;
		String sqlStr = "select RecruiterId from cons_list where EmployeeId = '"+cid+"'";
		String a = "";
		try {
			conn = DBManager.getCon();
			ps = conn.prepareStatement(sqlStr);
			
			ResultSet rSet = ps.executeQuery();
			while (rSet.next()) {
				a = rSet.getString(1);
			}
			rSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.closeCon(conn);

		}
		return a;
	}
	/**
	 * 删除bv_case_byconsultant
	 */
	public static int DelTabBvCase() {
		Connection conn = null;
		PreparedStatement ps = null;
		String sqlStr = "delete from bv_case_byconsultant";
		int num = 0;
		try {
			conn = DBManager.getCon();
			ps = conn.prepareStatement(sqlStr);
			num = ps.executeUpdate();
			System.out.println("删除bv_case_byconsultant_NUM:" + num);
		} catch (SQLException e) {
			System.out.println("SQLException_删除bv_case_byconsultant失败:" + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			DBManager.closeCon(conn);

		}
		return num;

	}
	/**
	 * 删除promotion_Additional
	 */
	public static int DelTabAddition() {
		Connection conn = null;
		PreparedStatement ps = null;
		String sqlStr = "delete from addition_bv";
		int num = 0;
		try {
			conn = DBManager.getCon();
			ps = conn.prepareStatement(sqlStr);
			num = ps.executeUpdate();
			System.out.println("删除Addition_bv_NUM:" + num);
		} catch (SQLException e) {
			System.out.println("SQLException_删除Addition_bv失败:" + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			DBManager.closeCon(conn);
			
		}
		return num;
		
	}
 
}
 
