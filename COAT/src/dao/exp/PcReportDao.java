package dao.exp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.PCReportBean;
import util.DBManager;

/**
 * PcReportDao
 * @author wilson
 *
 */
public class PcReportDao {
	/**
	 * 读取数据
	 * @param  
	 * @return
	 */
	static Connection con = null;
	static PreparedStatement ps = null;
	
	public static List<PCReportBean> getPCReportList()
	{
		List<PCReportBean> ls = new ArrayList<PCReportBean>();
		String sql ="select * from pc_report limit 100";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				PCReportBean pcBean = new PCReportBean();
				 
				pcBean.setId(rs.getString(1));
				pcBean.setId(rs.getString(2)); 
				pcBean.setDirect_Team(rs.getString(3)); 
				pcBean.setDirect_Leader(rs.getString(4));
				pcBean.setIntroducer(rs.getString(5)); 
				pcBean.setStaff_Code(rs.getString(6)); 
				pcBean.setDD_VP(rs.getString(7)); 
				pcBean.setJoin_Title(rs.getString(8)); 
				pcBean.setTitle(rs.getString(9)); 
				pcBean.setTrainee(rs.getString(10));
				pcBean.setStaff_Name(rs.getString(11)); 
				/*pcBean.setDate_of_Group_Join(rs.getString(12)); 
				pcBean.setResign_Date(rs.getString(13)); 
				pcBean.setTR_App_Date(rs.getString(14)); 
				pcBean.setTR_Issue_Date(rs.getString(15)); 
				pcBean.setLR_App_Date(rs.getString(16)); pcBean.setLR_Issue_Date(rs.getString(17)); 
				pcBean.setLR_Normal_License(rs.getString(18)); pcBean.setIT_Completed_Date(rs.getString(19)); 
				pcBean.setIT_Phase_Two(rs.getString(20)); pcBean.setHalf_Consultant(rs.getString(21)); 
				pcBean.setConsultant(rs.getString(22)); pcBean.setS_Consultant_1(rs.getString(23)); 
				pcBean.setS_Consultant_2(rs.getString(24)); pcBean.setPrincipal_Consultant(rs.getString(25)); 
				pcBean.setWealth_Manager(rs.getString(26)); pcBean.setS_Wealth_Manager(rs.getString(27)); 
				pcBean.setS_Wealth_Manager_2(rs.getString(28)); pcBean.setP_Wealth_Manager(rs.getString(29)); 
				pcBean.setAssociate(rs.getString(30)); pcBean.setAss_Director(rs.getString(31)); 
				pcBean.setDeputy_Director(rs.getString(32)); pcBean.setRegional_Director(rs.getString(33)); 
				pcBean.setAsst_Vice_President(rs.getString(34)); pcBean.setVice_President(rs.getString(35)); 
				pcBean.setSr_Vice_President(rs.getString(36)); pcBean.setAdjustment(rs.getString(37)); 
				pcBean.setBV2003_(rs.getString(38)); pcBean.setBV2004_(rs.getString(39)); 
				pcBean.setBV2005_(rs.getString(40)); pcBean.setBV2006_(rs.getString(41)); 
				pcBean.setBV2007_(rs.getString(42)); pcBean.setAddition_BV(rs.getString(43)); 
				pcBean.setBV_Enough_To_CO1A(rs.getString(44)); pcBean.setBV_Enough_To_CO2(rs.getString(45));*/
				
				ls.add(pcBean);
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
	 * 删除pc_report
	 * @return
	 */
	public int DelTabTraining() {
		String sqlStr = "delete from pc_report";
		int num = 0;
		try {
			con = (Connection) DBManager.getCon();
			ps =  con.prepareStatement(sqlStr);
			num = ps.executeUpdate();
			//System.out.println("----------num---------"+num);
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
		String sql ="select * from pc_report";
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
	public static int addTRregisList(String Id, String Direct_Team, String Direct_Leader, String Introducer, 
			String Staff_Code, String DD_VP, String Join_Title, String Title, String Trainee, String Staff_Name, 
			String Date_of_Group_Join, String Resign_Date, String TR_App_Date, String TR_Issue_Date, String LR_App_Date, 
			String LR_Issue_Date, String LR_Normal_License, String IT_Completed_Date, String IT_Phase_Two, String Half_Consultant,
			String Consultant, String S_Consultant_1, String S_Consultant_2, String Principal_Consultant, String Wealth_Manager, 
			String S_Wealth_Manager, String S_Wealth_Manager_2, String P_Wealth_Manager, String Associate, String Ass_Director, 
			String Deputy_Director, String Regional_Director, String Asst_Vice_President, String Vice_President, String Sr_Vice_President, 
			String Adjustment, String BV2003_, String BV2004_, String BV2005_, String BV2006_, String BV2007_, String Addition_BV, 
			String BV_Enough_To_CO1A, String BV_Enough_To_CO2)
	{
		 
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="insert into pc_report(Id,Direct_Team,Direct_Leader,Introducer,Staff_Code,DD_VP,Join_Title,Title,Trainee,Staff_Name,Date_of_Group_Join," +
				"Resign_Date,TR_App_Date,TR_Issue_Date,LR_App_Date,LR_Issue_Date,LR_Normal_License,IT_Completed_Date,IT_Phase_Two,Half_Consultant,Consultant," +
				"S_Consultant_1,S_Consultant_2,Principal_Consultant,Wealth_Manager,S_Wealth_Manager,S_Wealth_Manager_2,P_Wealth_Manager,Associate,Ass_Director," +
				"Deputy_Director,Regional_Director,Asst_Vice_President,Vice_President,Sr_Vice_President,Adjustment,BV2003_,BV2004_,BV2005_,BV2006_,BV2007_,Addition_BV," +
				"BV_Enough_To_CO1A,BV_Enough_To_CO2)" +
				"values ('"+Id+"','"+Direct_Team+"','"+Direct_Leader+"','"+Introducer+"','"+Staff_Code+"','"+DD_VP+"','"+Join_Title+"','"+Title+"','"+Trainee+"','"+Staff_Name+"'," +
						"'"+Date_of_Group_Join+"','"+Resign_Date+"','"+TR_App_Date+"','"+TR_Issue_Date+"','"+LR_App_Date+"','"+LR_Issue_Date+"','"+LR_Normal_License+"','"+IT_Completed_Date+"'," +
								"'"+IT_Phase_Two+"','"+Half_Consultant+"','"+Consultant+"','"+S_Consultant_1+"','"+S_Consultant_2+"','"+Principal_Consultant+"','"+Wealth_Manager+"','"+S_Wealth_Manager+"'," +
										"'"+S_Wealth_Manager_2+"','"+P_Wealth_Manager+"','"+Associate+"','"+Ass_Director+"','"+Deputy_Director+"','"+Regional_Director+"','"+Asst_Vice_President+"','"+Vice_President+"'," +
												"'"+Sr_Vice_President+"','"+Adjustment+"','"+BV2003_+"','"+BV2004_+"','"+BV2005_+"','"+BV2006_+"','"+BV2007_+"','"+Addition_BV+"','"+BV_Enough_To_CO1A+"','"+BV_Enough_To_CO2+"')";
 
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
