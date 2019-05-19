package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.SendMail;
import util.Util;
import dao.QueryStaffRequstDao;
import entity.RequestStaffBean;
import entity.RequestStaffConvoyBean;
/**
 * QueryStaffRequstDao 实现类
 * @author WILSON
 * 
 */
public class QueryStaffRequstDaoImpl implements  QueryStaffRequstDao{
	Connection connection = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	//
	public List<RequestStaffBean> queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page) {
		
		List<RequestStaffBean> list = new ArrayList<RequestStaffBean>();
		try {
			connection = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer("SELECT staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
				//	"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate ");
			StringBuffer sqlString = new StringBuffer("SELECT * FROM request_staff where card_type='N'  ");
		/*	if(!Util.objIsNULL(startDate)&&!Util.objIsNULL(endDate)){
				sqlString.append("  and  upd_date >='"+startDate+"' ");
				sqlString.append(" and upd_date <='"+endDate+"' ");
			}*/
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d')");
			 
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(code)){
				sqlString.append(" and staff_code like '%"+code+"%' ");
			}
			if(!Util.objIsNULL(payer)){
				sqlString.append(" and payer like '%"+payer+"%' ");
			}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			sqlString.append(" order by UrgentDate desc ");
			sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info("QUERY STAFF CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RequestStaffBean rsBean = new RequestStaffBean();
				rsBean.setStaff_code(rs.getString("staff_code"));
				rsBean.setName(rs.getString("name"));
				rsBean.setName_chinese(rs.getString("name_chinese"));
				rsBean.setTitle_english(rs.getString("title_english"));
				rsBean.setTitle_chinese(rs.getString("title_chinese"));
				rsBean.setExternal_english(rs.getString("external_english"));
				rsBean.setExternal_chinese(rs.getString("external_chinese"));
				rsBean.setAcademic_title_e(rs.getString("academic_title_e"));
				rsBean.setAcademic_title_c(rs.getString("academic_title_c"));
				rsBean.setProfess_title_e(rs.getString("profess_title_e"));
				rsBean.setProfess_title_c(rs.getString("profess_title_c"));
				rsBean.setTr_reg_no(rs.getString("tr_reg_no"));
				rsBean.setCe_no(rs.getString("ce_no"));
				rsBean.setMpf_no(rs.getString("mpf_no"));
				rsBean.setE_mail(rs.getString("e_mail"));
				rsBean.setDirect_line(rs.getString("direct_line"));
				rsBean.setFax(rs.getString("fax"));
				rsBean.setBobile_number(rs.getString("bobile_number"));
				rsBean.setQuantity(rs.getString("quantity"));
				rsBean.setUrgentDate(rs.getString("upd_date"));
				rsBean.setUpd_name(rs.getString("upd_name"));
				rsBean.setCard_type(rs.getString("card_type"));
				rsBean.setLayout_type(rs.getString("layout_type"));
				rsBean.setUrgent(rs.getString("urgent"));
				rsBean.setUrgentDate(rs.getString("UrgentDate"));
				rsBean.setUpd_date(rs.getString("upd_date"));
				rsBean.setMarks(rs.getString("marks"));
				rsBean.setLocation(rs.getString("location"));
				rsBean.setEliteTeam(rs.getString("eliteTeam"));
				rsBean.setCAM_only(rs.getString("CAM_only"));
				rsBean.setCFS_only(rs.getString("CFS_only"));
				rsBean.setCIS_only(rs.getString("CIS_only"));
				rsBean.setCCL_only(rs.getString("CCL_only"));
				rsBean.setCFSH_only(rs.getString("CFSH_only"));
				rsBean.setCMS_only(rs.getString("CMS_only"));
				rsBean.setCFG_only(rs.getString("CFG_only"));
				rsBean.setBlank_only(rs.getString("Blank_only"));
				rsBean.setCCFSH_only(rs.getString("CCFSH_only"));
				rsBean.setCWMC_only(rs.getString("CWMC_only"));
				rsBean.setPayer(rs.getString("Payer"));
				rsBean.setCompany(rs.getString("Company"));
				list.add(rsBean);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	//
	public List<RequestStaffConvoyBean> queryRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page) {
		
		List<RequestStaffConvoyBean> list = new ArrayList<RequestStaffConvoyBean>();
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer("SELECT * FROM request_staff_convoy where card_type='N'  ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d')");
				
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d')");
			}
		//	if(!Util.objIsNULL(code)){
			//	sqlString.append(" and staff_code like '%"+code+"%' ");
		//	}
			if(!Util.objIsNULL(payer)){
				sqlString.append(" and payer like '%"+payer+"%' ");
			}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			sqlString.append(" order by UrgentDate desc ");
			sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info("QUERY STAFF CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RequestStaffConvoyBean rsBean = new RequestStaffConvoyBean();
				rsBean.setStaff_code(rs.getString("staff_code"));
				rsBean.setName(rs.getString("name"));
				rsBean.setName_chinese(rs.getString("name_chinese"));
				rsBean.setTitle_english(rs.getString("title_english"));
				rsBean.setTitle_chinese(rs.getString("title_chinese"));
				rsBean.setExternal_english(rs.getString("external_english"));
				rsBean.setExternal_chinese(rs.getString("external_chinese"));
				rsBean.setAcademic_title_e(rs.getString("academic_title_e"));
				rsBean.setAcademic_title_c(rs.getString("academic_title_c"));
				rsBean.setProfess_title_e(rs.getString("profess_title_e"));
				rsBean.setProfess_title_c(rs.getString("profess_title_c"));
				rsBean.setTr_reg_no(rs.getString("tr_reg_no"));
				rsBean.setCe_no(rs.getString("ce_no"));
				rsBean.setMpf_no(rs.getString("mpf_no"));
				rsBean.setE_mail(rs.getString("e_mail"));
				rsBean.setDirect_line(rs.getString("direct_line"));
				rsBean.setFax(rs.getString("fax"));
				rsBean.setBobile_number(rs.getString("bobile_number"));
				rsBean.setQuantity(rs.getString("quantity"));
				rsBean.setUrgentDate(rs.getString("upd_date"));
				rsBean.setUpd_name(rs.getString("upd_name"));
				rsBean.setCard_type(rs.getString("card_type"));
				rsBean.setLayout_type(rs.getString("layout_type"));
				rsBean.setUrgent(rs.getString("urgent"));
				rsBean.setUrgentDate(rs.getString("UrgentDate"));
				rsBean.setUpd_date(rs.getString("upd_date"));
				rsBean.setMarks(rs.getString("marks"));
				rsBean.setLocation(rs.getString("location"));
				rsBean.setEliteTeam(rs.getString("eliteTeam"));
				rsBean.setCAM_only(rs.getString("CAM_only"));
				rsBean.setCFS_only(rs.getString("CFS_only"));
				rsBean.setCIS_only(rs.getString("CIS_only"));
				rsBean.setCCL_only(rs.getString("CCL_only"));
				rsBean.setCFSH_only(rs.getString("CFSH_only"));
				rsBean.setCMS_only(rs.getString("CMS_only"));
				rsBean.setCFG_only(rs.getString("CFG_only"));
				rsBean.setBlank_only(rs.getString("Blank_only"));
				rsBean.setCWMC_only(rs.getString("CWMC_only"));
				rsBean.setPayer(rs.getString("Payer"));
				rsBean.setSubject(rs.getString("subject"));
				rsBean.setRemark(rs.getString("remark"));
				rsBean.setRemarkcons(rs.getString("remarkcons"));
				rsBean.setShzt(rs.getString("shzt"));
				list.add(rsBean);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
public List<RequestStaffConvoyBean> queryHRRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page) {
		
		List<RequestStaffConvoyBean> list = new ArrayList<RequestStaffConvoyBean>();
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer("SELECT * FROM request_staff_convoy where card_type='N'  ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(code)){
				sqlString.append(" and staff_code like '%"+code+"%' ");
			}
			//if(!Util.objIsNULL(payer)){
			//	sqlString.append(" and payer like '%"+payer+"%' ");
			//}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			sqlString.append(" order by UrgentDate desc ");
			sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info("QUERY STAFF CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RequestStaffConvoyBean rsBean = new RequestStaffConvoyBean();
				rsBean.setStaff_code(rs.getString("staff_code"));
				rsBean.setName(rs.getString("name"));
				rsBean.setName_chinese(rs.getString("name_chinese"));
				rsBean.setTitle_english(rs.getString("title_english"));
				rsBean.setTitle_chinese(rs.getString("title_chinese"));
				rsBean.setExternal_english(rs.getString("external_english"));
				rsBean.setExternal_chinese(rs.getString("external_chinese"));
				rsBean.setAcademic_title_e(rs.getString("academic_title_e"));
				rsBean.setAcademic_title_c(rs.getString("academic_title_c"));
				rsBean.setProfess_title_e(rs.getString("profess_title_e"));
				rsBean.setProfess_title_c(rs.getString("profess_title_c"));
				rsBean.setTr_reg_no(rs.getString("tr_reg_no"));
				rsBean.setCe_no(rs.getString("ce_no"));
				rsBean.setMpf_no(rs.getString("mpf_no"));
				rsBean.setE_mail(rs.getString("e_mail"));
				rsBean.setDirect_line(rs.getString("direct_line"));
				rsBean.setFax(rs.getString("fax"));
				rsBean.setBobile_number(rs.getString("bobile_number"));
				rsBean.setQuantity(rs.getString("quantity"));
				rsBean.setUrgentDate(rs.getString("upd_date"));
				rsBean.setUpd_name(rs.getString("upd_name"));
				rsBean.setCard_type(rs.getString("card_type"));
				rsBean.setLayout_type(rs.getString("layout_type"));
				rsBean.setUrgent(rs.getString("urgent"));
				rsBean.setUrgentDate(rs.getString("UrgentDate"));
				rsBean.setUpd_date(rs.getString("upd_date"));
				rsBean.setMarks(rs.getString("marks"));
				rsBean.setLocation(rs.getString("location"));
				rsBean.setEliteTeam(rs.getString("eliteTeam"));
				rsBean.setCAM_only(rs.getString("CAM_only"));
				rsBean.setCFS_only(rs.getString("CFS_only"));
				rsBean.setCIS_only(rs.getString("CIS_only"));
				rsBean.setCCL_only(rs.getString("CCL_only"));
				rsBean.setCFSH_only(rs.getString("CFSH_only"));
				rsBean.setCMS_only(rs.getString("CMS_only"));
				rsBean.setCFG_only(rs.getString("CFG_only"));
				rsBean.setBlank_only(rs.getString("Blank_only"));
				rsBean.setCWMC_only(rs.getString("CWMC_only"));
				rsBean.setPayer(rs.getString("Payer"));
				rsBean.setSubject(rs.getString("subject"));
				rsBean.setRemark(rs.getString("remark"));
				rsBean.setRemarkcons(rs.getString("remarkcons"));
				rsBean.setShzt(rs.getString("shzt"));
				list.add(rsBean);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	
public List<RequestStaffConvoyBean> queryDeptRequstConvoyList(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer,Page page) {
	
	List<RequestStaffConvoyBean> list = new ArrayList<RequestStaffConvoyBean>();
	try {
		connection = DBManager.getCon();
		StringBuffer sqlString = new StringBuffer("SELECT * FROM request_staff_convoy where card_type='N'  ");
		if(!Util.objIsNULL(startDate)){
			sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d')");
		}
		if(!Util.objIsNULL(endDate)){
			sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d')");
		}
		
			sqlString.append("and payer in(select staffcode from staff_list where (TerminationDate is null or TerminationDate='')"
					+"and deptid in(select dpt from department where sfyx='Y' and (depart_head='"+payer+"' or depart_head_bak='"+payer+"')))");
			
			
		if(!Util.objIsNULL(code)){
			sqlString.append(" and payer like '%"+code+"%' ");
		}
	//	if(!Util.objIsNULL(payer)){
		//	sqlString.append(" and payer like '%"+payer+"%' ");
		//}
		if(!Util.objIsNULL(name)){
			sqlString.append(" and name like '%"+name+"%' ");
		}
		if(!Util.objIsNULL(location)){
			sqlString.append(" and location='"+location+"' ");
		}
		if(!Util.objIsNULL(urgentCase)){
			sqlString.append(" and urgent ='"+urgentCase+"' ");
		}
		if(!Util.objIsNULL(layout_select)){
			sqlString.append(" and layout_type='"+layout_select+"' ");;
		}
		if(ET.equals("Y") && !Util.objIsNULL(ET)){
			sqlString.append(" and eliteTeam ='"+ET+"' ");
		}
		if(ET.equals("N") && !Util.objIsNULL(ET)){
			sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
		}
		sqlString.append(" order by UrgentDate desc ");
		sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
		logger.info("QUERY STAFF CARD SQL:"+sqlString.toString());
		ps = connection.prepareStatement(sqlString.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			RequestStaffConvoyBean rsBean = new RequestStaffConvoyBean();
			rsBean.setStaff_code(rs.getString("staff_code"));
			rsBean.setName(rs.getString("name"));
			rsBean.setName_chinese(rs.getString("name_chinese"));
			rsBean.setTitle_english(rs.getString("title_english"));
			rsBean.setTitle_chinese(rs.getString("title_chinese"));
			rsBean.setExternal_english(rs.getString("external_english"));
			rsBean.setExternal_chinese(rs.getString("external_chinese"));
			rsBean.setAcademic_title_e(rs.getString("academic_title_e"));
			rsBean.setAcademic_title_c(rs.getString("academic_title_c"));
			rsBean.setProfess_title_e(rs.getString("profess_title_e"));
			rsBean.setProfess_title_c(rs.getString("profess_title_c"));
			rsBean.setTr_reg_no(rs.getString("tr_reg_no"));
			rsBean.setCe_no(rs.getString("ce_no"));
			rsBean.setMpf_no(rs.getString("mpf_no"));
			rsBean.setE_mail(rs.getString("e_mail"));
			rsBean.setDirect_line(rs.getString("direct_line"));
			rsBean.setFax(rs.getString("fax"));
			rsBean.setBobile_number(rs.getString("bobile_number"));
			rsBean.setQuantity(rs.getString("quantity"));
			rsBean.setUrgentDate(rs.getString("upd_date"));
			rsBean.setUpd_name(rs.getString("upd_name"));
			rsBean.setCard_type(rs.getString("card_type"));
			rsBean.setLayout_type(rs.getString("layout_type"));
			rsBean.setUrgent(rs.getString("urgent"));
			rsBean.setUrgentDate(rs.getString("UrgentDate"));
			rsBean.setUpd_date(rs.getString("upd_date"));
			rsBean.setMarks(rs.getString("marks"));
			rsBean.setLocation(rs.getString("location"));
			rsBean.setEliteTeam(rs.getString("eliteTeam"));
			rsBean.setCAM_only(rs.getString("CAM_only"));
			rsBean.setCFS_only(rs.getString("CFS_only"));
			rsBean.setCIS_only(rs.getString("CIS_only"));
			rsBean.setCCL_only(rs.getString("CCL_only"));
			rsBean.setCFSH_only(rs.getString("CFSH_only"));
			rsBean.setCMS_only(rs.getString("CMS_only"));
			rsBean.setCFG_only(rs.getString("CFG_only"));
			rsBean.setBlank_only(rs.getString("Blank_only"));
			rsBean.setCWMC_only(rs.getString("CWMC_only"));
			rsBean.setPayer(rs.getString("Payer"));
			rsBean.setSubject(rs.getString("subject"));
			rsBean.setRemark(rs.getString("remark"));
			rsBean.setRemarkcons(rs.getString("remarkcons"));
			rsBean.setShzt(rs.getString("shzt"));
			list.add(rsBean);
		}rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
		logger.info("QUERY STAFF CARD ERROR:"+e);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		logger.info("QUERY STAFF CARD ERROR:"+e);
	}finally{
		DBManager.closeCon(connection);
	}
	return list;
}


	public int getRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer) {
			int num=-1;
			try {
				connection = DBManager.getCon();
				StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM request_staff where card_type='N' ");
				/*	if(!Util.objIsNULL(startDate)&&!Util.objIsNULL(endDate)){
						sqlString.append("  and  upd_date >='"+startDate+"' ");
						sqlString.append(" and upd_date <='"+endDate+"' ");
					}*/
					if(!Util.objIsNULL(startDate)){
						sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >= date_format('"+startDate+"','%Y-%m-%d') ");
					 
					}
					if(!Util.objIsNULL(endDate)){
						sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <= date_format('"+endDate+"','%Y-%m-%d') ");
					}
				if(!Util.objIsNULL(code)){
					sqlString.append(" and staff_code like '%"+code+"%' ");
				}
				if(!Util.objIsNULL(payer)){
					sqlString.append(" and payer like '%"+payer+"%' ");
				}
				if(!Util.objIsNULL(name)){
					sqlString.append(" and name like '%"+name+"%' ");
				}
				if(!Util.objIsNULL(location)){
					sqlString.append(" and location='"+location+"' ");
				}
				if(!Util.objIsNULL(urgentCase)){
					sqlString.append(" and urgent ='"+urgentCase+"' ");
				}
				if(!Util.objIsNULL(layout_select)){
					sqlString.append(" and layout_type='"+layout_select+"' ");;
				}
				if(ET.equals("Y") && !Util.objIsNULL(ET)){
					sqlString.append(" and eliteTeam ='"+ET+"' ");
				}
				if(ET.equals("N") && !Util.objIsNULL(ET)){
					sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
				}
				logger.info("QUERY STAFF CARD SQL:"+sqlString.toString());
				ps = connection.prepareStatement(sqlString.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					num=Integer.parseInt(rs.getString("Num"));
				}rs.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("查询Staff Name Card时出现 ："+e);
				num=-2;
			}finally
			{
				//关闭连接
				DBManager.closeCon(connection);
			}
		return num;
	}
	public int getConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer) {
		int num=-1;
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM request_staff_convoy where card_type='N' ");
			/*	if(!Util.objIsNULL(startDate)&&!Util.objIsNULL(endDate)){
						sqlString.append("  and  upd_date >='"+startDate+"' ");
						sqlString.append(" and upd_date <='"+endDate+"' ");
					}*/
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d') ");
				
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d') ");
			}
			//if(!Util.objIsNULL(code)){
			//	sqlString.append(" and staff_code like '%"+code+"%' ");
			//}
			if(!Util.objIsNULL(payer)){
				sqlString.append(" and payer like '%"+payer+"%' ");
			}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			logger.info("QUERY STAFF CONVOY CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Staff Name Card Convoy时出现 ："+e);
			num=-2;
		}finally
		{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return num;
	}
	public int getDeptConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer) {
		int num=-1;
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM request_staff_convoy where card_type='N' ");
			/*	if(!Util.objIsNULL(startDate)&&!Util.objIsNULL(endDate)){
						sqlString.append("  and  upd_date >='"+startDate+"' ");
						sqlString.append(" and upd_date <='"+endDate+"' ");
					}*/
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d') ");
				
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d') ");
			}
			//if(!Util.objIsNULL(code)){
		//		sqlString.append(" and staff_code like '%"+code+"%' ");
		//	}
			sqlString.append("and payer in(select staffcode from staff_list where (TerminationDate is null or TerminationDate='')"
					+"and deptid in(select dpt from department where sfyx='Y' and (depart_head='"+payer+"' or depart_head_bak='"+payer+"')))");
			if(!Util.objIsNULL(code)){
				sqlString.append(" and payer like '%"+code+"%' ");
			}
		//	if(!Util.objIsNULL(payer)){
			//	sqlString.append(" and payer like '%"+payer+"%' ");
			//}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			logger.info("QUERY STAFF CONVOY CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Staff Name Card Convoy时出现 ："+e);
			num=-2;
		}finally
		{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return num;
	}
	
	
	public int getHRConvoyRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String ET,String layout_select,String payer) {
		int num=-1;
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM request_staff_convoy where card_type='N' ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and  date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(code)){
				sqlString.append(" and staff_code like '%"+code+"%' ");
			}
			//if(!Util.objIsNULL(payer)){
			//	sqlString.append(" and payer like '%"+payer+"%' ");
			//}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			logger.info("QUERY STAFF CONVOY CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Staff Name Card Convoy时出现 ："+e);
			num=-2;
		}finally
		{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return num;
	}
	
	

	/**
	 * queryRequstListSet
	 */
	public ResultSet queryRequstListSet(String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String ET,String layout_select) {
		ResultSet rs=null;
		try {
			connection = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer("SELECT case location when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'Y' THEN '"+Constant.Convoy+"' ELSE 'Other' END as location, ");
			StringBuffer sqlString = new StringBuffer("SELECT location, ");
				//sqlString.append(" concat(if(CFS_only='Y','"+Constant.CFS+"',''), if(CAM_only='Y','+"+Constant.CAM+"',''), ");
				//sqlString.append(" if(CIS_only='Y','+"+Constant.CIS+"',''),if(CCL_only='Y','+"+Constant.CCL+"',''),if(CFSH_only='Y','+"+Constant.CFSH+"',''),");
				//sqlString.append(" if(CMS_only='Y','+"+Constant.CMS+"',''),if(CFG_only='Y','+"+Constant.CFG+"',''),if(Blank_only='Y','+"+Constant.Blank+"',''),if(CWMC_only='Y','+"+Constant.CWMC+"','')) as type,");
			sqlString.append(" company as type,");
			
				sqlString.append(" layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
				sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity  ");
				sqlString.append(" FROM request_staff where card_type='N' ");
				if(!Util.objIsNULL(startDate)){
					sqlString.append(" and date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d') ");
				}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(code)){//2014-9-1 16:08:00 King 取消注释...
				sqlString.append(" and staff_code like '%"+code+"%' ");
			}
			if(!Util.objIsNULL(payer)){
				sqlString.append(" and payer like '%"+payer+"%' ");
			}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			sqlString.append(" order by UrgentDate desc ");
		//	System.out.println(sqlString.toString());
			logger.info("UPLOAD STAFF CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			rs = ps.executeQuery();
		if(rs!=null){
			return rs;	
		}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		}
		return rs;
	}
	/**
	 * 
	 * @author kingxu
	 * @date 2016-4-20
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @param payer
	 * @param ET
	 * @param layout_select
	 * @return
	 * @return ResultSet
	 */
	public Result queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String ET,String layout_select) {
		ResultSet rs=null;
		Result r=null;
		try {
			connection = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer("SELECT case location when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'Y' THEN '"+Constant.Convoy+"' ELSE 'Other' END as location, ");
			StringBuffer sqlString = new StringBuffer("SELECT location, ");
				//sqlString.append(" concat(if(CFS_only='Y','"+Constant.CFS+"',''), if(CAM_only='Y','+"+Constant.CAM+"',''), ");
				//sqlString.append(" if(CIS_only='Y','+"+Constant.CIS+"',''),if(CCL_only='Y','+"+Constant.CCL+"',''),if(CFSH_only='Y','+"+Constant.CFSH+"',''),");
				//sqlString.append(" if(CMS_only='Y','+"+Constant.CMS+"',''),if(CFG_only='Y','+"+Constant.CFG+"',''),if(Blank_only='Y','+"+Constant.Blank+"',''),if(CWMC_only='Y','+"+Constant.CWMC+"','')) as type,");
			sqlString.append(" company as type,");
			
				sqlString.append(" layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
				sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity  ");
				sqlString.append(" FROM request_staff where card_type='N' ");
				if(!Util.objIsNULL(startDate)){
					sqlString.append(" and date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d') ");
				}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(upd_date,'%Y-%m-%d') <=date_format('"+endDate+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(code)){//2014-9-1 16:08:00 King 取消注释...
				sqlString.append(" and staff_code like '%"+code+"%' ");
			}
			if(!Util.objIsNULL(payer)){
				sqlString.append(" and payer like '%"+payer+"%' ");
			}
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent ='"+urgentCase+"' ");
			}
			if(!Util.objIsNULL(layout_select)){
				sqlString.append(" and layout_type='"+layout_select+"' ");;
			}
			if(ET.equals("Y") && !Util.objIsNULL(ET)){
				sqlString.append(" and eliteTeam ='"+ET+"' ");
			}
			if(ET.equals("N") && !Util.objIsNULL(ET)){
				sqlString.append(" and( eliteTeam ='"+ET+"' or eliteTeam='' or eliteTeam is null )");
			}
			sqlString.append(" order by UrgentDate desc ");
		//	System.out.println(sqlString.toString());
			logger.info("UPLOAD STAFF CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			rs = ps.executeQuery();
		if(rs!=null){
			//return rs;	
			r=ResultSupport.toResult(rs);
			rs.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("QUERY STAFF CARD ERROR:"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return r;
	}
 
	public int updateRequestStaff(String staffcode,String urgentDate,String username) {
		 int num=-1;
		 try{
			 connection = DBManager.getCon();
			 String sql="update request_staff set card_type='U',upd_date=? where staff_code=? and UrgentDate=? ";
			logger.info(username+"---UPDATE STAFF CARD SQL:"+sql.toString());
			 ps=connection.prepareStatement(sql);
			 ps.setString(1, DateUtils.getNowDateTime());
			 ps.setString(2, staffcode);
			 ps.setString(3, urgentDate);
			 num=ps.executeUpdate();
		 }catch(Exception e){
				logger.info(username+"--UPDATE STAFF CARD ERROR:"+e);
			 num=-2;
		 }finally{
			//关闭连接
				DBManager.closeCon(connection);
		 }
		return num;
	}
	public int approveRequestStaffConvoy(String staffcode, String urgentDate,
			String username) {
		int s=-1;
		try{
			connection=DBManager.getCon();
			String sql="update request_staff_convoy set shzt='Y' where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF 成功！");
			}
			else{
				logger.info("Approve Request STAFF 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Approve Request STAFF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return s;
	}
	public int approveRequestDepartConvoy(String staffcode, String urgentDate,
			String username) {
		int s=-1;
		try{
			connection=DBManager.getCon();
			String sql="update request_staff_convoy set shzt='E' where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF 成功！");
			}
			else{
				logger.info("Approve Request STAFF 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Approve Request STAFF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return s;
	}
	public int approveRequestDepartConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons) {
		int s=-1;
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			String sql="update request_staff_convoy set shzt='E',remarkcons=? where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, remarkcons);
			ps.setString(2, staffcode);
			ps.setString(3, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF 成功！");
				
				
				
			}
			else{
				logger.info("Approve Request STAFF 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Approve Request STAFF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return s;
	}
 
	
	
	
	
	public String approveRequestDepartConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons,String englishName) {
		int s=-1;
		String result="";
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			String sql="update request_staff_convoy set shzt='E',remarkcons=? where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, remarkcons);
			ps.setString(2, staffcode);
			ps.setString(3, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF --------------邮件通知HR");
				String body="Dear User,<br/><br/>"
						+"    The name card request initiated by "+englishName+"  "+staffcode+" is in HRD approval status."
						+"<br/><br/>"
						+"Please visit and approve at:<br/>"
						+"<a href='http://www.econvoy.com/group/convoy/coat-test?handle=hrapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
						+"Thank You.";
				result=SendMail.send("Staff name card request approval.", SendMail.getProValue("public.staffnamecard.hrd.email"), body, "COAT", "email.ftl", "");
				JSONObject json=new JSONObject(result);
				//System.out.println(json.get("state"));
				if("success".equalsIgnoreCase(json.get("state")+"")){
					logger.info("Approve Request STAFF --------------邮件通知SZADM");
					body="Dear SZOADM,<br/><br/>" +
							"  Department head 于"+DateUtils.getDateToday()+"审核通过了 "+englishName+"  "+staffcode+" 于 "+urgentDate+"提交的name card 申请。";
					result="success";
					/*result=SendMail.send("Department Head Has been Approval", SendMail.getProValue("public.staffnamecard.szoadm.eamil"), body, "COAT", "email.ftl", "");
					json=new JSONObject(result);
					if(!"success".equalsIgnoreCase(json.get("state")+"")){
						throw new Exception("SZOADM SendMail Error!");
					}*/
				}else{//HR邮件发送失败
					throw new Exception("HR SendMail Error!");
				}
				
			}else{
				logger.info("Approve Request STAFF 失败！");
				result=Util.getMsgJosnObject("error", "");
			}
			connection.commit();
			logger.info("Approve Request STAFF 成功！");
		}catch(Exception e){
			//e.printStackTrace();
			result=Util.jointException(e);
			logger.error("Approve Request STAFF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return result;
	}
	
	
	public int approveRequestDepartConvoy(String staffcode, String urgentDate,
			String username,String remark) {
		int s=-1;
		try{
			connection=DBManager.getCon();
			String sql="update request_staff_convoy set shzt='E' where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF 成功！");
			}
			else{
				logger.info("Approve Request STAFF 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Approve Request STAF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return s;
	}
	
	public int approveRequestHRConvoy(String staffcode, String urgentDate,
			String username) {
		int s=-1;
		try{
			connection=DBManager.getCon();
			String sql="update request_staff_convoy set shzt='R' where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF 成功！");
			}
			else{
				logger.info("Approve Request STAFF 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Approve Request STAF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return s;
	}
	
	public int approveRequestHRConvoy(String staffcode, String urgentDate,
			String username,String remarkcons) {
		int s=-1;
		try{
			connection=DBManager.getCon();
			String sql="update request_staff_convoy set shzt='R',remarkcons=? where staff_code=? and UrgentDate=?";
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			ps=connection.prepareStatement(sql);
			ps.setString(1, remarkcons);
			ps.setString(2, staffcode);
			ps.setString(3, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("Approve Request STAFF 成功！");
			}
			else{
				logger.info("Approve Request STAFF 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Approve Request STAF数据异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return s;
	}
	
	public String findEmailByStaff(String StaffCode, String userType) {
		String result="";
		try{
			
			String sql=" select email from(select  staffcode,email from staff_list union"+
				" select EmployeeId as 'staffcode',email from cons_list )a"+
				" where a.staffcode='"+StaffCode+"'";
			/*if(userType.equals("Staff"))
				sql="select email from staff_list where staffcode='"+StaffCode+"'";
			else
				sql="select email from cons_list where EmployeeId='"+StaffCode+"'";*/
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			sql="";
			if(rs.next()){
				result=rs.getString("email");
			}else{
				result="";
			}
			rs.close();
			logger.info("获取==>"+StaffCode+"==>Email==>"+userType+"==>"+sql);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取==>"+StaffCode+"==>Email==>"+userType+"==>Exception==>"+e.getMessage());
		}finally{
			DBManager.closeCon(connection);
		}
		return result;
	}
	
	
	
	public String findRecruiterEmailByStaff(String StaffCode) {
		String result="";
		try{
			
			String sql="select Email from cons_list where EmployeeId="+
					   "(select RecruiterId from cons_list where EmployeeId='"+StaffCode+"')";
	 
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			sql="";
			if(rs.next()){
				result=rs.getString("email");
			}else{
				result="";
			}
			rs.close();
			logger.info("获取==>"+StaffCode+"==>Recruiter Email==>"+sql);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取==>"+StaffCode+"==>Recruiter Email==>Exception==>"+e.getMessage());
		}finally{
			DBManager.closeCon(connection);
		}
		return result;
	}

	public String findDeptByStaff(String StaffCode) {
		String result="";
		try{
			
			String sql="select email from staff_list where TerminationDate is null and staffcode="+
						"(select depart_head from department where sfyx='Y' and dpt=(select deptid from staff_list where TerminationDate is null and staffcode='"+StaffCode+"' limit 0,1) limit 0,1)";
	 
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString("email");
			}else{
				result="";
			}
			rs.close();
			logger.info("获取==>"+StaffCode+"==>Dept Head Email==>"+sql);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取==>"+StaffCode+"==>Dept Head==>Exception==>"+e.getMessage());
		}finally{
			DBManager.closeCon(connection);
		}
		return result;
	}
	
	public String [] findSupervisorEmail(String StaffCode) {
		String [] result=new String[2];
		try{
			String sql="select supervisor_Email,supervisor2_Email from request_staff_convoy_supervisor_view where sub_Code='"+StaffCode+"' ";
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				result[0]=rs.getString("supervisor_Email");
				result[1]=rs.getString("supervisor2_Email");
			}else{
				result=null;
			}
			rs.close();
			logger.info("获取==>"+StaffCode+"==>Supervisor_Email==>"+sql);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取==>"+StaffCode+"==>Supervisor_Email==>Exception==>"+e.getMessage());
		}finally{
			DBManager.closeCon(connection);
		}
		return result;
	}
	
	
	

}
