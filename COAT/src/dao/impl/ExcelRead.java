/*package dao.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import entity.BvCaseConsultantBean;
import util.DBManager;
import util.Util;
import util.UtilCommon;
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

*//**
 * 导入excel
 * 
 * @author Wilson
 * 
 *//*
public class ExcelRead {
	*//**
	 * 导入excel
	 *//*
	private static final Log LOG = LogFactory.getLog(ExcelRead.class);

	@SuppressWarnings( { "unchecked", "deprecation" })
	public Map<Integer, List<Object>> readExcel(String pathName, int sheetId)
			throws BiffException, IOException {
		// 先删除数据
		DelTabBvCase();
		Workbook book = Workbook.getWorkbook(new File(pathName));
		Map<Integer, List<Object>> map = new LinkedHashMap<Integer, List<Object>>();
		
		for (int k=0; k <=sheetId; k++) {
			Sheet sheet = book.getSheet(k);
			
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			for (int i = 1; i < rows; i++) {
				List<Object> list = new ArrayList<Object>();
				for (int j = 0; j < columns; j++) {
					Cell cell = sheet.getCell(j, i);
					// 存放在list中
					list.add(getExcelDate(cell));

				}
				map.put(i, list);
			}
			getbv_case(map);
		}

		book.close();
		return map;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public  void getbv_case(Map<Integer,List<Object>> map) {
		Connection conn = null;
		PreparedStatement ps = null;
		int num =1;
		try {
			conn = DBManager.getCon();
		
		for (Object object : map.values()) {
			
			List list = new ArrayList(); // (List) object;
			list.add(object);
			//LOG.info("list: " + list.size());
			for (int k = 0; k < list.size(); k++) {
				String[] val = list.get(0).toString().split(",");
				//System.out.println("list: " + list.get(0).toString());

				BvCaseConsultantBean entity = new BvCaseConsultantBean();
				// 判断是否为[空
				if (val[0].trim().equals("[")) {
					break;
				} else {
					entity.setId(num+"");
					// 添加数据
					entity.setBitid(val[0].trim());
					entity.setSubmitdate(UtilCommon.dateToString(new Date(val[1].trim())));
					entity.setBtttype(val[2].trim());
					entity.setConsultant_ID(val[3].trim());
					entity.setBv(changeToDouble(val[4].trim()));
					entity.setCase_No(changeToDouble(val[5].trim()));
					entity.setPolicy_No(val[6].trim());
					entity.setClient_Name(val[7].trim());
					entity.setComment_Date(val[8].trim());
					entity.setTerm(val[9].trim());
					entity.setPremium(val[10].trim());
					entity.setBV_Factor(val[11].trim());
					entity.setDiscount_Factor(val[12].trim());
					entity.setCCY(val[13].trim());
					entity.setConsultant_ID1(val[14].trim());
					entity.setConsultant_ID2(val[15].trim());
					entity.setCBV(val[16].trim());
					entity.setAssumed_BV(val[17].trim());
					entity.setCase_Count(val[18].trim());
					entity.setAssumed_Case_Count(val[19].trim());
					entity.setAddition_BV(val[20].trim());
					//Term,Premium,BV_Factor,Discount_Factor,CCY,Consultant_ID1,Consultant_ID2,cBV,
					//Assumed_BV,Case_Count,Assumed_Case_Count,Addition_BV
					//添加数据add
					if(!Util.objIsNULL(entity.getBitid())){
						addBvCaseBean(entity,ps,conn);
						++num;
					}else {
						System.out.println("数据为空！！");
					}
				}
			}
		}
		} catch (Exception e) { 
			System.out.println("DBManager.getCon();报错！");
			e.printStackTrace();
		}finally {
			DBManager.closeCon(conn);

		}
	}
	public double changeToDouble(String bvString) {
		Double bv = 0.0;
		if (Util.objIsNULL(bvString.trim())) {
			bv = 0.0;
		} else {
			bv = new Double(bvString.trim());
		}
		return bv;
	}

	*//**
	 * 删除bv_case_byconsultant
	 * 
	 * @return
	 *//*
	public int DelTabBvCase() {
		Connection conn = null;
		PreparedStatement ps = null;
		String sqlStr = "delete from bv_case_byconsultant";
		int num = 0;
		try {
			conn = DBManager.getCon();
			ps = conn.prepareStatement(sqlStr);
			num = ps.executeUpdate();
			LOG.info("删除bv_case_byconsultant_NUM:" + num);
		} catch (SQLException e) {
			LOG.error("SQLException_删除bv_case_byconsultant失败:" + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			DBManager.closeCon(conn);

		}
		return num;

	}

	
	 * 添加
	 
	public void addBvCaseBean(BvCaseConsultantBean entity,PreparedStatement ps,Connection conn) {
		String sqlStr = "insert into bv_case_byconsultant (id,bitid,Submit_Date,BIT_Type,Consultant_ID,BV,Case_No,Policy_No,Client_Name,Comment_Date,Term,Premium,BV_Factor,Discount_Factor,CCY,Consultant_ID1,Consultant_ID2,cBV,Assumed_BV,Case_Count,Assumed_Case_Count,Addition_BV) values "
				+ "('"
				+ entity.getId()+ "','"
				+ entity.getBitid().replace("[", "")+ "','"
				+ entity.getSubmitdate()+ "', "+ "'"
				+ entity.getBtttype()+ "','"
				+ entity.getConsultant_ID()+ "',"+ "'"
				+ entity.getBv()+ "','"
				+ entity.getCase_No()+ "',"+ "'"
				+ entity.getPolicy_No()+ "','"
				+ entity.getClient_Name().replace("]", "")+ "','"
				+entity.getComment_Date()+ "','"
				+entity.getTerm()+ "','"
				+entity.getPremium()+ "','"
				+entity.getBV_Factor()+ "','"
				+entity.getDiscount_Factor()+ "','"
				+entity.getCCY()+ "','"
				+entity.getConsultant_ID1()+ "','"
				+entity.getConsultant_ID2()+ "','"
				+entity.getCBV()+ "','"
				+entity.getAssumed_BV()+ "','"
				+entity.getCase_Count()+ "','"
				+entity.getAssumed_Case_Count()+ "','"
				+entity.getAddition_BV()
				+ "')";
		try {
			//System.out.println(sqlStr);
			
			ps = conn.prepareStatement(sqlStr);
			 
			ps.executeUpdate();
		} catch (Exception ex) {
			LOG.error("JDBC INSERT Error : " + ex);
			System.out.println("JDBC INSERT Error : " + ex.toString());
		} 

	}

	public Object getExcelDate(Cell cell) {
		if (cell.getType() == CellType.NUMBER) {
			NumberCell number = (NumberCell) cell;
			return number.getValue();
		} else if (cell.getType() == CellType.LABEL) {
			LabelCell label = (LabelCell) cell;
			return label.getString();
		} else if (cell.getType() == CellType.BOOLEAN) {
			BooleanCell bool = (BooleanCell) cell;
			return bool.getValue();
		} else if (cell.getType() == CellType.DATE) {
			DateCell d = (DateCell) cell;
			return d.getDate();
		} else {
			return cell.getContents();
		}
	}

	public static void main(String[] args) throws BiffException, IOException,
			ParseException, ClassNotFoundException {
		Long befor = System.currentTimeMillis();

		ExcelRead read = new ExcelRead();
		read.readExcel("D:\\cc.xls", 0);
		Long after = System.currentTimeMillis();
		System.out.println("此次操作共耗时：" + (after - befor) + "毫秒");

	}
}
*/