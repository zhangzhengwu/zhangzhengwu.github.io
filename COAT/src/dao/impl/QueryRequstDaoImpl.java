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

import util.Constant;
import util.DBManager;
import util.Page;
import util.Util;
import dao.QueryRequstDao;
import entity.RequestNewBean;
/**
 * QueryRequstDao 实现类
 * @author King.XU
 *
 */
public class QueryRequstDaoImpl implements  QueryRequstDao{
	Connection connection = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	//
	public List<RequestNewBean> queryRequstList(String ET,String layout_select,String name,String code,String startDate,String endDate,
			String location,String urgentCase,String nocode,String payer,Page page) {
		List<RequestNewBean> list = new ArrayList<RequestNewBean>();
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer("SELECT staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
			"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,ae_Consultant,eliteTeam,location ,upd_date");
			sqlString.append(" FROM request_new where quantity>0 and card_type='N' ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and date_format(UrgentDate,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(UrgentDate,'%Y-%m-%d') <='"+endDate+"' ");
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
			if(!Util.objIsNULL(nocode)){
				if(nocode.equals("Y")){
					sqlString.append(" and length(staff_code) >=10 ");
				}
			}
			sqlString.append(" order by UrgentDate desc ");
			sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info("QUERY NAME CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RequestNewBean rsBean = new RequestNewBean();
				rsBean.setStaff_code(rs.getString(1));
				rsBean.setName(rs.getString(2));
				rsBean.setName_chinese(rs.getString(3));
				rsBean.setTitle_english(rs.getString(4));
				rsBean.setTitle_chinese(rs.getString(5));
				rsBean.setExternal_english(rs.getString(6));
				rsBean.setExternal_chinese(rs.getString(7));
				rsBean.setAcademic_title_e(rs.getString(8));
				rsBean.setAcademic_title_c(rs.getString(9));
				rsBean.setProfess_title_e(rs.getString(10));
				rsBean.setProfess_title_c(rs.getString(11));
				rsBean.setTr_reg_no(rs.getString(12));
				rsBean.setCe_no(rs.getString(13));
				rsBean.setMpf_no(rs.getString(14));
				rsBean.setE_mail(rs.getString(15));
				rsBean.setDirect_line(rs.getString(16));
				rsBean.setFax(rs.getString(17));
				rsBean.setBobile_number(rs.getString(18));
				rsBean.setQuantity(rs.getString(19));
				rsBean.setUrgentDate(rs.getString(20));
				rsBean.setLayout_type(rs.getString(21));
				rsBean.setUrgent(rs.getString(22));
				rsBean.setPayer(rs.getString(23));
				rsBean.setCAM_only(rs.getString(24));
				rsBean.setCFS_only(rs.getString(25));
				rsBean.setCIS_only(rs.getString(26));
				rsBean.setCCL_only(rs.getString(27));
				rsBean.setCFSH_only(rs.getString(28));
				rsBean.setCMS_only(rs.getString(29));
				rsBean.setCFG_only(rs.getString(30));
				rsBean.setBlank_only(rs.getString(31));
				rsBean.setAe_consultant(rs.getString(32));
				rsBean.setEliteTeam(rs.getString(33));
				rsBean.setLocation(rs.getString(34));
				rsBean.setUpd_date(rs.getString(35));
				list.add(rsBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	//
	public ResultSet queryRequstListSet(String ET,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String nocode) {
		ResultSet rs=null;
		try {
			connection = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer("SELECT case location when 'M' THEN '"+Constant.Macau+"' when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'Y' THEN '"+Constant.Convoy+"' END as location, ");
			StringBuffer sqlString = new StringBuffer("SELECT location, company as type,");
			sqlString.append(" layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese, ");
			sqlString.append(" profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,remark1,e_mail,direct_line,fax,bobile_number,quantity,upd_date  ");
			sqlString.append("	 FROM request_new where quantity>0 and card_type='N' ");

			if(!Util.objIsNULL(startDate)){
				sqlString.append("and date_format(UrgentDate,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){

				sqlString.append(" and date_format(UrgentDate,'%Y-%m-%d') <='"+endDate+"' ");
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
				sqlString.append(" and( eliteTeam ='"+ET+"' or  eliteTeam='' or eliteTeam is null )");
			}
			if(!Util.objIsNULL(nocode)){
				if(nocode.equals("Y")){
					sqlString.append(" and length(staff_code) >=10 ");
				}
			}
			logger.info("UPLOAD NAME CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}
		return rs;
	}
	public Result queryRequstForResult(String ET,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String nocode) {
		ResultSet rs=null;
		Result r=null;
		try {
			connection = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer("SELECT case location when 'M' THEN '"+Constant.Macau+"' when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'Y' THEN '"+Constant.Convoy+"' END as location, ");
			StringBuffer sqlString = new StringBuffer("SELECT location, company as type,");
			sqlString.append(" layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese, ");
			sqlString.append(" profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,remark1,e_mail,direct_line,fax,bobile_number,quantity,upd_date  ");
			sqlString.append("	 FROM request_new where quantity>0 and card_type='N' ");

			if(!Util.objIsNULL(startDate)){
				sqlString.append("and date_format(UrgentDate,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){

				sqlString.append(" and date_format(UrgentDate,'%Y-%m-%d') <='"+endDate+"' ");
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
				sqlString.append(" and( eliteTeam ='"+ET+"' or  eliteTeam='' or eliteTeam is null )");
			}
			if(!Util.objIsNULL(nocode)){
				if(nocode.equals("Y")){
					sqlString.append(" and length(staff_code) >=10 ");
				}
			}
			logger.info("UPLOAD NAME CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				//return rs;
				r=ResultSupport.toResult(rs);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return r;
	}
	
	public ResultSet autoReportRequstListSet() {
		ResultSet rs=null;
		try {
			connection = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer(" select  * from (select case location when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'Y' THEN '"+Constant.Convoy+"' END as location, ");
			StringBuffer sqlString = new StringBuffer(" select  * from (select location, company as type,");
			sqlString.append(" case layout_type when 'S' then 'Standard'  when 'P' then 'Premium' end as layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese, ");
			sqlString.append(" profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,upd_date  ");
			sqlString.append(" from request_new where card_type='N' and quantity>0  and staff_code in(select employeeId from cons_list)  order by UrgentDate desc) a group by staff_code ");
			logger.info("Auto Report Request NAME CARD SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}
		return rs;
	}
	
	/**
	 * 2014-5-13 10:27:39 导出Consultant Info 用作Email签名
	 * @return
	 */
	public ResultSet ReportConsInfo(){
		ResultSet rs=null;
		try {
			connection = DBManager.getCon();
		
			
			/*
			 * 2014-5-19 10:53:18 King 注释
			 * String sqlString ="select cl.location,if(ce_no!='',if(aecode!='','CFS+CAM+CIS','CFS+CAM'),if(aecode!='','CFS+CIS','CFS'))as companyName,''as Layout,cl.employeeId as staff_code,concat(cl.EmployeeName,' ',cl.alias)as name,'' as name_chinese,"+ 
			" E_PositionName as title_english,C_PositionName as title_chinese,'' as txternal_english,'' as external_chinese,concat(f.academic_title_e,' ',f.profess_title_e) as profess_title_e,''as profess_title_c,"+
			" TrRegNo as tr_reg_no,Ce_No as ce_no,MPF as mpf_no,cl.email,cl.directline,fax,cl.mobile"+
			" from cons_list cl left join tr on(cl.employeeId=tr.staffno)left join aeconsultant_list al on(cl.employeeId=al.staffcode)"+
			" left join (select staff_code,academic_title_e,profess_title_e ,fax,urgentDate from ("+
			" select staff_code,academic_title_e,profess_title_e ,fax,urgentDate from request_new where card_type='N' and staff_code!='' order by urgentDate desc"+
			" ) a group by staff_code) f on(cl.employeeId=f.staff_code)";*/
			
			
	/*	2015-3-12 16:14:09 king注释
	 * 	String sqlString ="select cl.location,if(ce_no!='',if(aecode!='','CFS+CAM+CIS','CFS+CAM'),if(aecode!='','CFS+CIS','CFS'))as companyName,''as Layout,cl.employeeId as staff_code,concat(cl.EmployeeName,' ',cl.alias)as name,'' as name_chinese,"+ 
			" E_PositionName as title_english,C_PositionName as title_chinese,'' as txternal_english,'' as external_chinese,'' as profess_title_e,''as profess_title_c,"+
			" TrRegNo as tr_reg_no,Ce_No as ce_no,MPF as mpf_no,cl.email,cl.directline,'' as fax,cl.mobile"+
			" from cons_list cl left join tr on(cl.employeeId=tr.staffno)left join aeconsultant_list al on(cl.employeeId=al.staffcode)";
			*/
			/**
			 *2015-3-12 16:14:09 king 修改逻辑(获取用户个人最新信息的同时获取最新名片办理记录（for profess title）)
			 */
			String sqlString="select cl.location,if(ce_no!='',if(aecode!='','CFS+CAM+CIS','CFS+CAM'),if(aecode!='','CFS+CIS','CFS'))as companyName,''as Layout,cl.employeeId as staff_code,concat(cl.EmployeeName,' ',cl.alias)as name,'' as name_chinese,"+
			" E_PositionName as title_english,C_PositionName as title_chinese,'' as txternal_english,'' as external_chinese,profess_title_e as profess_title_e,''as profess_title_c,"+
			 " TrRegNo as tr_reg_no,Ce_No as ce_no,MPF as mpf_no,cl.email,cl.directline,'' as fax,cl.mobile"+
			 " from cons_list cl "+
			 " left join tr on(cl.employeeId=tr.staffno)"+
			" left join aeconsultant_list al on(cl.employeeId=al.staffcode)"+
			" left JOIN (select staff_code,academic_title_e,profess_title_e  from ("+
						" select staff_code,academic_title_e,profess_title_e ,fax,urgentDate from request_new where card_type='N' and staff_code!='' order by urgentDate desc"+
			 " ) a group by staff_code) f on(cl.EmployeeId=f.staff_code)";
			ps = connection.prepareStatement(sqlString,ResultSet.TYPE_FORWARD_ONLY,  
                    ResultSet.CONCUR_READ_ONLY);  
			ps.setFetchSize(Integer.MIN_VALUE);  
			ps.setFetchDirection(ResultSet.FETCH_REVERSE);  
			rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			}
		}catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}
		return rs;
	}
	
	
	/**
	 * 导出顾问最新邮件签名信息
	 * 将ResultSet 转成Result
	 * @author kingxu
	 * @date 2015-7-22
	 * @return
	 * @return Result
	 */
	public Result ReportConsInfos(){
		Result result=null;
		try {
			connection = DBManager.getCon();
			//String sqlString="select * from( select cl.location,if(ce_no!='',if(aecode!='','CFS+CAM+CIS','CFS+CAM'),if(aecode!='','CFS+CIS','CFS'))as companyName,''as Layout,cl.employeeId as staff_code,concat(cl.EmployeeName,' ',cl.alias)as name,'' as name_chinese,"+
			/**String sqlString="select * from( select cl.location,if(ce_no!='',if(aecode!='','CFS+CAM','CFS+CAM'),if(aecode!='','CFS','CFS'))as companyName,''as Layout,cl.employeeId as staff_code,concat(cl.EmployeeName,' ',cl.alias)as name,'' as name_chinese,"+
			" E_PositionName as title_english,C_PositionName as title_chinese,'' as txternal_english,'' as external_chinese,profess_title_e as profess_title_e,''as profess_title_c,"+
			 " TrRegNo as tr_reg_no,Ce_No as ce_no,MPF as mpf_no,cl.email,cl.directline,'' as fax,cl.mobile"+
			 " from cons_list cl "+
			 " left join tr on(cl.employeeId=tr.staffno)"+
			" left join aeconsultant_list al on(cl.employeeId=al.staffcode)"+
			" left JOIN (select staff_code,academic_title_e,profess_title_e  from ("+
						" select staff_code,academic_title_e,profess_title_e ,fax,urgentDate from request_new where card_type='N' and staff_code!='' order by urgentDate desc"+
			 " ) a group by staff_code) f on(cl.EmployeeId=f.staff_code))x";
			**/
			//2016-7-14 12:20:44  优化获取最新顾问名片办理记录
			
			String sqlString="select * from( select cl.location,if(tr.ce_no!='',if(aecode!='','CFS+CAM','CFS+CAM'),if(aecode!='','CFS','CFS'))as companyName,''as Layout,cl.employeeId as staff_code,concat(cl.EmployeeName,' ',cl.alias)as name,'' as name_chinese,"+
					" E_PositionName as title_english,C_PositionName as title_chinese,'' as txternal_english,'' as external_chinese,profess_title_e as profess_title_e,''as profess_title_c,"+
					" TrRegNo as tr_reg_no,tr.Ce_No as ce_no,MPF as mpf_no,cl.email,cl.directline,'' as fax,cl.mobile"+
					" from cons_list cl "+
					" left join tr on(cl.employeeId=tr.staffno)"+
					" left join aeconsultant_list al on(cl.employeeId=al.staffcode)"+
					" left JOIN request_new_last f on(cl.EmployeeId=f.staff_code))x";
			ps = connection.prepareStatement(sqlString,ResultSet.TYPE_FORWARD_ONLY,  
                    ResultSet.CONCUR_READ_ONLY);  
			ps.setFetchSize(Integer.MIN_VALUE);  
			ps.setFetchDirection(ResultSet.FETCH_REVERSE);  
			ResultSet rs = ps.executeQuery();
			result=ResultSupport.toResult(rs);
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return result;
	}
	
	public static void main(String[] args) {
		QueryRequstDaoImpl qrd=new QueryRequstDaoImpl();
		qrd.ReportConsInfo();
	}
	
	
	
	
	public int queryRow(String ET,String layout_select,String name, String code, String startDate,
			String endDate, String location, String urgentCase, String nocode,
			String payer) {
		int num=-1;
		try {
			connection = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer("select count(*) as Num ");
			sqlString.append(" FROM request_new where quantity>0 and card_type='N' ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and date_format(UrgentDate,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(UrgentDate,'%Y-%m-%d') <='"+endDate+"' ");
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
				sqlString.append(" and( eliteTeam ='"+ET+"' or  eliteTeam='' or eliteTeam is null )");
			}
			if(!Util.objIsNULL(nocode)){
				if(nocode.equals("Y")){
					sqlString.append(" and length(staff_code) >=10 ");
				}
			}
			logger.info("QUERY NAME CARD Number SQL:"+sqlString.toString());
			ps = connection.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally
		{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return num;
	}

}
