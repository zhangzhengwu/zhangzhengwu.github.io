package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import util.Util;
import entity.BvCaseConsultantBean;


/**
 * 
 * @author wilson
 *
 */
public class BvCaseDao {
	/**
	 * 读取数据
	 * @param  
	 * @return
	 */
	public static List<BvCaseConsultantBean> getAllBvCaseList()
	{
		List<BvCaseConsultantBean> ls = new ArrayList<BvCaseConsultantBean>();
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="select * from bv_case_byconsultant limit 100";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				BvCaseConsultantBean bvCase = new BvCaseConsultantBean();
				//bvCase.setId(rs.getString(0));
				bvCase.setBitid(rs.getString(1));
				bvCase.setSubmitdate(rs.getString(2));
				bvCase.setBtttype(rs.getString(3));
				bvCase.setConsultant_ID(rs.getString(4));
				bvCase.setBv(rs.getDouble(5));
				bvCase.setCase_No(rs.getDouble(6));
				bvCase.setPolicy_No(rs.getString(7));
				bvCase.setClient_Name(rs.getString(8));
				
				ls.add(bvCase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ls;
	}
	/**
	 * 查找集合
	 * @return
	 */
	public static ResultSet select()
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="select * from bv_case_byconsultant limit 100";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	/**
	 * 添加信息
	 * @param  
	 * @return
	 */
	public static int addNewBvCase(int id,String bitid,String submitdate,String btttype
			,String consultant_ID,String bv,String Case_No,String Policy_No,String Client_Name,
			String Comment_Date,String Term,String Premium,String BV_Factor,String Discount_Factor,
			String CCY,String Consultant_ID1,String Consultant_ID2,String 
			cBV,String Assumed_BV,String Case_Count,String Assumed_Case_Count,String Addition_BV)
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="insert into bv_case_byconsultant values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, bitid);
			ps.setString(3, submitdate);
			ps.setString(4, btttype);
			ps.setString(5, consultant_ID);
			ps.setString(6, bv);
			ps.setString(7, Util.objIsNULL(Case_No)?"0":Case_No);
			ps.setString(8, Policy_No);
			ps.setString(9, Client_Name);
			ps.setString(10, Comment_Date);
			ps.setString(11, Term);
			ps.setString(12, Premium);
			ps.setString(13, BV_Factor);
			ps.setString(14, Discount_Factor);
			ps.setString(15, CCY);
			ps.setString(16, Consultant_ID1);
			ps.setString(17, Consultant_ID2);
			ps.setString(18,cBV);
			ps.setString(19,Assumed_BV);
			ps.setString(20,Case_Count);
			ps.setString(21,Assumed_Case_Count);
			ps.setString(22,Addition_BV);
			
			int r = ps.executeUpdate();
			return r;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}
