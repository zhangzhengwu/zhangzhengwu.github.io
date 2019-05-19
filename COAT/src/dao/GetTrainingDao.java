package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import entity.TraiNingBean;

public class GetTrainingDao {

	/**
	 * 第一阶段的全查页面
	 * @return
	 */
	private static final Log LOG = LogFactory.getLog(GetTrainingDao.class);
	@SuppressWarnings("unchecked")
	public static List getTrainingList()
	{
		List ls = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="select * from training limit 100";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				TraiNingBean oneSbean = new TraiNingBean();
				oneSbean.setCode(rs.getString(2));
				oneSbean.setStaffcose(rs.getString(3));
				oneSbean.setAlias(rs.getString(4));
				oneSbean.setName(rs.getString(5));
				oneSbean.setTeam(rs.getString(6));
				oneSbean.setCompletedDate(rs.getString(7));
				oneSbean.setTwoTraining(rs.getString(8));
				ls.add(oneSbean);
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
		String sql ="select * from training";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			LOG.error("查找training集合SQLException:"+e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			LOG.error("查找training集合ClassNotFoundException:"+e);
			e.printStackTrace();
		}finally
		{
			try {
				//关闭连接
				con.close();
			} catch (SQLException e) {
				LOG.error("关闭连接"+e);
				e.printStackTrace();
			}
		}
		return null;
		
	}
	/**
	 * 添加信息
	 * @return
	 */
	public static int addTraining(String id ,String code,String staffcose,String alias,String name,String team,String completedDate,String twoTraining,String day1_AM,String day1_PM,String day2_AM,String day2_PM,String day3_AM,String day3_PM,String day4_AM,String day4_PM,String day5_AM,String day5_PM,String day6_AM,String day6_PM,String day7_AM,String day7_PM,String FollowUp_Training_AM)
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="insert into training(id,code,staff_code,alias,name,team,completedDate,twoTraining,day1_AM,day1_PM,day2_AM,day2_PM,day3_AM,day3_PM,day4_AM,day4_PM,day5_AM,day5_PM,day6_AM,day6_PM,day7_AM,day7_PM,f_Training_AM) " +
				"values("+id+",'"+code+"','"+staffcose+"','"+alias+"','"+name+"','"+team+"','"+completedDate+"','"+twoTraining+"','"+day1_AM+"','"+day1_PM+"','"+day2_AM+"','"+day2_PM+"','"+day3_AM+"','"+day3_PM+"','"+day4_AM+"','"+day4_PM+"','"+day5_AM+"','"+day5_PM+"','"+day6_AM+"','"+day6_PM+"','"+day7_AM+"','"+day7_PM+"','"+FollowUp_Training_AM+"');";
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
