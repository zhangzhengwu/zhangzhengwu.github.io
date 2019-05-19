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
import dao.QueryMedicalDao;
import entity.Medical;
/**
 * Medical查询与导出
 * @author Wilson
 *
 */
public class QueryMedicalDaoImpl implements QueryMedicalDao {
	Connection con = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(QueryChargeDaoImpl.class);
	/**
	 *  Medical getRow
	 */
	public int getRows(String name, String code,
			String startDate, String endDate) {
	int num=-1;
		StringBuffer sql= new StringBuffer("SELECT count(*) FROM medical_claim_record WHERE sfyx ='Y' ");
		if (!Util.objIsNULL(code) ) {
			sql.append(" and staffcode like '%"+code+"%' ");
		}
		if (!Util.objIsNULL(name)) {
			sql.append(" AND name like '%"+name+"%'");
		}
		if(!Util.objIsNULL(startDate) ){
			sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
		}
		if(!Util.objIsNULL(endDate)){
			sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
		} 
		sql.append(" order by upd_date desc ");
		try {
			con = DBManager.getCon();
			logger.info("查询Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 *  Medical查询
	 */
	public List<Medical> queryRequstList(String name, String code,
			String startDate, String endDate,Page page) {
		List<Medical> list = new ArrayList<Medical>();
		StringBuffer sql= new StringBuffer("SELECT * FROM medical_claim_record WHERE sfyx ='Y' ");
		if (!Util.objIsNULL(code) ) {
			sql.append(" and staffcode like '%"+code+"%' ");
		}
		if (!Util.objIsNULL(name)) {
			sql.append(" AND name like '%"+name+"%'");
		}
		if(!Util.objIsNULL(startDate) ){
			sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
		}
		if(!Util.objIsNULL(endDate)){
			sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
		} 
		sql.append(" order by upd_date desc ");
		 sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+" ");
		try {
			con = DBManager.getCon();
			logger.info("查询Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Medical mbean = new Medical();
				mbean.setStaffcode(rs.getString("staffcode"));
				mbean.setName(rs.getString("name"));
				mbean.setAD_type(rs.getString("AD_type"));
				mbean.setSP_type(rs.getString("SP_type"));
				mbean.setMedical_date(rs.getString("medical_date"));
				mbean.setMedical_Fee(rs.getString("medical_Fee"));
				mbean.setEntitled_Fee(rs.getString("entitled_Fee"));
				mbean.setTerms_year(rs.getString("Terms_year"));
				mbean.setMedical_month(rs.getString("medical_month"));
				mbean.setMedical_Normal(rs.getString("medical_Normal"));
				mbean.setMedical_Special(rs.getString("medical_Special"));
				mbean.setStaff_CodeDate(rs.getString("staff_CodeDate"));
				mbean.setSameDaye(rs.getString("SameDay"));
				mbean.setHalf_Consultant(rs.getString("Half_Consultant"));
				mbean.setUpd_Name(rs.getString("upd_Name"));
				mbean.setUpd_Date(rs.getString("upd_Date"));
				if(DateUtils.getYear()==Integer.parseInt(rs.getString("upd_Date").substring(0,4)) && DateUtils.getMedicalMonth()==Integer.parseInt(rs.getString("medical_month"))){
					mbean.setSfdj("Y");//当前为可编辑状态
				}else{
					mbean.setSfdj("N");
				}
				mbean.setSfyx(rs.getString("sfyx"));
				list.add(mbean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * Medical查询与导出
	 */
	/*public ResultSet queryRequstListSet(String name, String code,String startDate, String endDate) {
		ResultSet rs=null;
		try{
			StringBuffer sql= new StringBuffer("SELECT staffcode,name,AD_type,medical_month,sum(entitled_Fee) FROM medical_claim_record where sfyx ='Y'  ");
			if(!Util.objIsNULL(startDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
			if( !Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append("  group by staffcode   order by upd_date asc ");
				con = DBManager.getCon();
				logger.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
				ps = con.prepareStatement(sql.toString());
				rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			//DBManager.closeCon(con);
		}
		return rs;
		 
	}*/
	
	public List<Medical> queryRequstListSet(String name, String code,String startDate, String endDate) {
		ResultSet rs=null;
		List<Medical> list=new ArrayList<Medical>();
		try{
			StringBuffer sql= new StringBuffer("SELECT staffcode,name,AD_type,medical_month,sum(entitled_Fee) as xxx FROM medical_claim_record where sfyx ='Y'  ");
			if(!Util.objIsNULL(startDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
			if( !Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append("  group by staffcode   order by upd_date asc ");
			con = DBManager.getCon();
			logger.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			if(rs!=null){
				while(rs.next()){
					list.add(new Medical(
							rs.getString("staffcode"),
							rs.getString("name"),
							rs.getString("AD_type"),
							rs.getString("xxx"),
							rs.getString("medical_month")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
		 
	}
	
	/**
	 * Medical查询与导出
	 */
	public ResultSet upLoadForConsList(String name, String code,String startDate, String endDate) {
		ResultSet rs=null;
		try{
			StringBuffer sql= new StringBuffer("SELECT staffcode,name,AD_type,SP_type,date_format(medical_date,'%d-%b-%y') as medical_date,medical_Fee,entitled_Fee,Terms_year,medical_month,medical_Normal,medical_Special FROM medical_claim_record where  sfyx ='Y' ");
			if(!Util.objIsNULL(startDate) ){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
				if( !Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append(" order by upd_date asc ");
			
			con = DBManager.getCon();
			logger.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			//DBManager.closeCon(con);
		}
		return rs;
		
	}
	
	
	public List<Medical> upLoadForConsList2(String name, String code,String startDate, String endDate) {
		ResultSet rs=null;
		List<Medical>list=new ArrayList<Medical>();
		try{
			StringBuffer sql= new StringBuffer("SELECT staffcode,name,AD_type,SP_type,date_format(medical_date,'%d-%b-%y') as medical_date,medical_Fee,entitled_Fee,Terms_year,medical_month,medical_Normal,medical_Special FROM medical_claim_record where  sfyx ='Y' ");
			if(!Util.objIsNULL(startDate) ){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
				if( !Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append(" order by upd_date asc ");
			
			con = DBManager.getCon();
			logger.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					Medical mbean = new Medical();
					mbean.setStaffcode(rs.getString("staffcode"));
					mbean.setName(rs.getString("name"));
					mbean.setAD_type(rs.getString("AD_type"));
					mbean.setSP_type(rs.getString("SP_type"));
					mbean.setMedical_date(rs.getString("medical_date"));
					mbean.setMedical_Fee(rs.getString("medical_Fee"));
					mbean.setEntitled_Fee(rs.getString("entitled_Fee"));
					mbean.setTerms_year(rs.getString("Terms_year"));
					mbean.setMedical_month(rs.getString("medical_month"));
					mbean.setMedical_Normal(rs.getString("medical_Normal"));
					mbean.setMedical_Special(rs.getString("medical_Special"));
					list.add(mbean);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
		
	}
	
	public List<String> selectConsList(String name,String code,String startDate,String endDate){
		ResultSet rs=null;
		List<String> list =new ArrayList<String>();
		try{
			StringBuffer sql= new StringBuffer("SELECT staffcode FROM medical_claim_record WHERE  sfyx ='Y' ");
			if(!Util.objIsNULL(startDate)){
				sql.append("and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
				if(!Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append("group by staffcode");
			con = DBManager.getCon();
			logger.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
			list.add(rs.getString("staffcode"));	
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("导出Medical信息表异常！"+e);
		}finally{
			 // 关闭连接
			 DBManager.closeCon(con);
		}
		return list;
		
	}
}