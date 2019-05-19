package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.QueryChargeDao;
import entity.Change_Record;

/**
 * 查询财务信息表
 * @author King.XU
 *
 */
public class QueryChargeDaoImpl implements QueryChargeDao {
	Connection con = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(QueryChargeDaoImpl.class);
	/**
	 * 獲取总行数
	 */
	public int getRows(String startDate, String endDate, String staffcode) {
		 int num=-1;
		try{
			con=DBManager.getCon();
			StringBuffer sql= new StringBuffer("select count(*) as Num from change_record  WHERE sfyx='Y' ");
			if(!Util.objIsNULL(startDate) ){
				sql.append(" and DATE_FORMAT(up_date,'%Y-%m-%d') >= '"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(up_date,'%Y-%m-%d') <= '"+endDate+"' ");
			}
			if (!Util.objIsNULL(staffcode)) {
				sql.append(" and StaffCode like '%"+staffcode+"%' ");
			}
			logger.info("查询财务信息表总行数 QueryChargeDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询财务信息表总行数   QueryChargeDaoImpl 时出现 ："+e.toString());
			num=0;
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * queryChargeList
	 */
	public List<Change_Record> queryChargeList(String startDate,
			String endDate, String staffcode,Page page) {
		List<Change_Record> list = new ArrayList<Change_Record>();
		StringBuffer sql= new StringBuffer("select StaffCode,Name,Number,Amount,Payer,Remarks,Infomed_to_FAD,charged_Month,AddDate from change_record  WHERE sfyx='Y' ");
		if(!Util.objIsNULL(startDate) ){
			sql.append(" and DATE_FORMAT(up_date,'%Y-%m-%d') >= '"+startDate+"' ");
		}
		if(!Util.objIsNULL(endDate)){
			sql.append(" and DATE_FORMAT(up_date,'%Y-%m-%d') <= '"+endDate+"' ");
		}
		if (!Util.objIsNULL(staffcode)) {
			sql.append(" and StaffCode like '%"+staffcode+"%'");
		}
		sql.append(" order by AddDate asc ");
		 sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
		try {
			con = DBManager.getCon();
			logger.info("查询财务信息表 QueryChargeDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Change_Record cqBean = new Change_Record();
				cqBean.setStaffCode(rs.getString(1));
				cqBean.setName(rs.getString(2));
				cqBean.setNumber(rs.getString(3));
				cqBean.setAmount(rs.getString(4));
				cqBean.setPayer(rs.getString(5));
				cqBean.setRemarks(rs.getString(6));
				cqBean.setInfomed_to_FAD(rs.getString(7));
				cqBean.setCharged_Month(rs.getString(8));
				cqBean.setAddDate(rs.getString(9));
				list.add(cqBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询财务信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * 导出财务信息表
	 */
	public List<Change_Record> selectCharge(String startDate, String endDate,String staffcode) {
		ResultSet rs=null;
		List<Change_Record> list=new ArrayList<Change_Record>();
		try{
			StringBuffer sql= new StringBuffer("select AddDate,StaffCode,Name,Number,Amount,Payer,Remarks,infomed_to_FAD,charged_Month,up_name,up_date,sfyx from change_record  WHERE sfyx='Y' ");
			if(!Util.objIsNULL(startDate) ){
				sql.append(" and DATE_FORMAT(AddDate,'%Y-%m-%d') >= '"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(AddDate,'%Y-%m-%d') <= '"+endDate+"' ");
			}
			if (!Util.objIsNULL(staffcode)) {
				sql.append(" and StaffCode like '%"+staffcode+"%'");
			}
			sql.append(" order by AddDate asc ");
			con = DBManager.getCon();
			logger.info("导出财务信息表 QueryChargeDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					list.add(new Change_Record(
						rs.getString("staffCode"),
						rs.getString("name"),
						rs.getString("number"),
						rs.getString("amount"),
						rs.getString("payer"),
						rs.getString("Remarks"),
						rs.getString("infomed_to_FAD"),
						rs.getString("charged_Month"),
						rs.getString("addDate"),
						rs.getString("up_name"),
						rs.getString("up_date"),
						rs.getString("sfyx")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出财务信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出财务信息表异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}
	 
	/**
	 * 刪除財務表數據
	 */
	public int deleteChargeRecord(String name,String StaffNo, String AddDate) {
		int d=-1;
		String sql="";
		try{
			sql="update  change_record set up_date='"+DateUtils.getNowDateTime()+"' , up_name='"+name+"', sfyx='N' where StaffCode='"+StaffNo+"' and AddDate='"+AddDate+"'";
			con=DBManager.getCon();
			
			logger.info("刪除財務表數據 QueryChargeDaoImpl SQL:"+sql.toString());
			ps=con.prepareStatement(sql);
			d=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("刪除財務表數據异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return d;
	}




	 
}
