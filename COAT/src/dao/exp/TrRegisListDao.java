package dao.exp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.TRRegisListBean;
import util.DBManager;

/**
 * TrRegisListDao
 * @author wilson
 *
 */
public class TrRegisListDao {
	/**
	 * 读取数据
	 * @param  
	 * @return
	 */
	static Connection con = null;
	static PreparedStatement ps = null;
	
	public static List<TRRegisListBean> getTRRegisList()
	{
		List<TRRegisListBean> ls = new ArrayList<TRRegisListBean>();
		String sql ="select * from tr_r_list limit 100";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				TRRegisListBean trBean = new TRRegisListBean();
				trBean.setId(rs.getString(1));
				trBean.setTr_id(rs.getString(2));
				trBean.setStaffno(rs.getString(3));
				trBean.setStaff(rs.getString(4));
				trBean.setStaffname(rs.getString(5));
				trBean.setAlias(rs.getString(6));
				trBean.setEnglishname(rs.getString(7));
				trBean.setHkcode(rs.getString(8));
				trBean.setEmployment_date(rs.getString(9));
				trBean.setTr_app_date(rs.getString(10)); 
				trBean.setTr_issue_date(rs.getString(11)); 
				trBean.setGi_licence(rs.getString(12)); 
				trBean.setTr_reg_no(rs.getString(13)); 
				trBean.setReg_line(rs.getString(14)); 
				trBean.setPp_exam_date(rs.getString(15)); 
				trBean.setGi_exam_date(rs.getString(16)); 
				trBean.setLt_exam_date(rs.getString(17)); 
				trBean.setIl_exam_date(rs.getString(18)); 
				trBean.setLr_app_date(rs.getString(19)); 
				trBean.setType1(rs.getString(20)); 
				trBean.setType2(rs.getString(21)); 
				trBean.setType4(rs.getString(22)); 
				trBean.setType9(rs.getString(23)); 
				trBean.setSfc_reg_no(rs.getString(24)); 
				trBean.setType1_pc(rs.getString(25)); 
				trBean.setType1_express(rs.getString(26)); 
				trBean.setType1_normal(rs.getString(27));
				trBean.setLr_lormal_license(rs.getString(28));
				trBean.setLr_issue_date(rs.getString(29));
				
				ls.add(trBean);
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
	 * 删除tr_r_list
	 * @return
	 */
	public int DelTabTraining() {
		String sqlStr = "delete from tr_r_list";
		int num = 0;
		try {
			con = (Connection) DBManager.getCon();
			ps =  con.prepareStatement(sqlStr);
			num = ps.executeUpdate();
			System.out.println("----------num---------"+num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return num;
		
		
	}
	/**
	 * 查找集合
	 * @return
	 */
	public static ResultSet select()
	{
		String sql ="select * from tr_r_list";
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
	public static int addTRregisList(String Id, String tr_id, String staff_No, String staff, 
			String china_name, String alias, String name_hkid, String hkid_no, 
			String date_employment, String license_Date, String date_first_Grant, 
			String gi_licence, String tr_reg_no, String reg_line, String pp_exam_date, 
			String gi_exam_date, String lt_exam_date, String il_exam_date, String date_apply, 
			String type1, String type2, String type4, String type9, String sfc_reg_no, String type_cond,
			String type_express, String type_normal, String lrnormal, String lrissue)
	{
		 
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="insert into tr_r_list(Id,tr_id,staffno,staff,staffname,alias,englishname,hkcode," +
				"employment_date,tr_app_date,tr_issue_date,gi_licence,tr_reg_no,reg_line,pp_exam_date," +
				"gi_exam_date,lt_exam_date,il_exam_date,lr_app_date,type1,type2,type4,type9,sfc_reg_no," +
				"type1_pc,type1_express,type1_normal,lr_lormal_license,lr_issue_date) values('"+
				Id+"','"+tr_id+"','"+staff_No+"','"+staff+"','"+china_name+"','"+alias+"','"+
				name_hkid+"','"+hkid_no+"','"+date_employment+"','"+license_Date+"','"+date_first_Grant+"','"+
				gi_licence+"','"+tr_reg_no+"','"+reg_line+"','"+pp_exam_date+"','"+gi_exam_date+"','"+
				lt_exam_date+"','"+il_exam_date+"','"+date_apply+"','"+type1+"','"+type2+"','"+type4+"','"+
				type9+"','"+sfc_reg_no+"','"+type_cond+"','"+type_express+"','"+type_normal+"','"+lrnormal+"','"+lrissue+"');";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
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
