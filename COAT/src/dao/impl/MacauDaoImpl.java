package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.MacauDao;
import entity.Consultant_Master;
import entity.NewMacau;

public class MacauDaoImpl implements MacauDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(MacauDaoImpl.class);
	
	
	/**
	 * // TODO 判断staff是否是DD
	 */
	public boolean findDDorTreeHead(String staffcode) {
		boolean isExit=false;
		try{
			con=DBManager.getCon();
			sql="select employeeId from cons_macau where employeeId=? and(employeeId=DDTreeHead or grade='DD' or position='DD')";
		ps=con.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			isExit=true;
		}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			isExit=false;
		}finally{
			DBManager.closeCon(con);
		}
		return isExit;
	}
	
	/**
	 * saveNewRequest 保存
	 * RequestNewBean 对象
	 */
	public int  saveNewMacau(NewMacau rnb,String reStaffNo) {
		int s=-1;
		String sql="";
		try{
			con=DBManager.getCon();
			sql="insert request_Macau values('"+reStaffNo+"','"+rnb.getName()+"','"+rnb.getName_chinese()
				+"','"+rnb.getTitle_english()+"','"+rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"
				+rnb.getExternal_chinese()+"','"+rnb.getAcademic_title_e()+"','"+rnb.getAcademic_title_c()+"','"
				+rnb.getProfess_title_e()+"','"+rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+rnb.getCe_no()
				+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+rnb.getDirect_line()+"','"+rnb.getFax()+"','"
				+rnb.getBobile_number()+"','"+rnb.getQuantity()+"','"+rnb.getUpd_date()+"','"+rnb.getUpd_name()+"','"
				+rnb.getCard_type()+"','"+rnb.getLayout_type()+"','"+rnb.getUrgent()+"','"+rnb.getUrgentDate()+"','"+rnb.getMarks()+"','"+rnb.getLocation()+"','"+rnb.getAe_consultant()+"','"
				+rnb.getCAM_only()+"','"+rnb.getCFS_only()+"','"+rnb.getCIS_only()+"','"+rnb.getCCL_only()+"','"
				+rnb.getCFSH_only()+"','"+rnb.getCMS_only()+"','"+rnb.getCFG_only()+"','"+rnb.getBlank_only()+"','"+rnb.getCIB_only()+"','"+rnb.getPayer()+"')";
			logger.info("saveNewMacau 保存 SQL:"+sql);
			ps=con.prepareStatement(sql);
			s=ps.executeUpdate();
			if(s>0){
				logger.info(Constant.Message.MESSAGE_SUCCESS);
				return s;
			}
			else{
				logger.info(Constant.Message.MESSAGE_FAILD);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("AddRequestDaoImpl 保存异常："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	
public List<NewMacau> queryRequstList(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,Page page/*,String CAM_only, String CFS_only, String CIS_only,String  CCL_only, String CFSH_only, String CMS_only,String CFG_only, String Blank_only*/) {
		
		List<NewMacau> list = new ArrayList<NewMacau>();
		try {
			con= DBManager.getCon();
			StringBuffer sqlString = new StringBuffer("SELECT staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
					"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,ae_Consultant,eliteTeam,location ");
				sqlString.append("FROM request_macau where quantity>0 and card_type='N' ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and date_format(upd_date,'%Y-%m-%d') >=date_format('"+startDate+"','%Y-%m-%d') ");
				
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(upd_date,'%Y-%m-%d') <= date_format('"+endDate+"','%Y-%m-%d') ");
				
			}
			if(!Util.objIsNULL(code)){
				sqlString.append(" and staff_code like '%"+code+"%' ");
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
			if(!Util.objIsNULL(ET_select)){
				sqlString.append(" and eliteTeam='"+ET_select+"' ");;
			}
			sqlString.append(" order by UrgentDate desc ");
			 sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info("QUERY NAME CARD SQL:"+sqlString.toString());
			ps = con.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				NewMacau rsBean = new NewMacau();
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
				rsBean.setCIB_only(rs.getString("CIB_only"));
				rsBean.setAe_consultant(rs.getString(33));
				rsBean.setEliteTeam(rs.getString(34));
				rsBean.setLocation(rs.getString(35));
				list.add(rsBean);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY Macau NameCard ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY Macau NameCard ERROR!"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
public ResultSet queryRequst(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase) {
	ResultSet rs=null;
	try {
		con = DBManager.getCon();
		StringBuffer sqlString = new StringBuffer("SELECT location, ");
			sqlString.append(" concat(if(CFS_only='Y','"+Constant.CFS+"',''), if(CAM_only='Y','+"+Constant.CAM+"',''), ");
			sqlString.append(" if(CIS_only='Y','"+Constant.CIS+"',''),if(CCL_only='Y','"+Constant.CCL+"',''),if(CFSH_only='Y','"+Constant.CFSH+"',''),");
			sqlString.append(" if(CMS_only='Y','"+Constant.CMS+"',''),if(CFG_only='Y','"+Constant.CFG+"',''),if(Blank_only='Y','"+Constant.Blank+"',''),if(CIB_only='Y','"+Constant.Macau+"','')) as type,");
			sqlString.append(" layout_type,eliteTeam,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
			sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity  ");
			sqlString.append("	 FROM request_Macau where quantity>0 and card_type='N'  ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and date_format(upd_date,'%Y-%m-%d')>='"+startDate+"' ");
			}
		if(!Util.objIsNULL(endDate)){
			sqlString.append(" and date_format(upd_date,'%Y-%m-%d')<='"+endDate+"' ");
		}
		if(!Util.objIsNULL(code)){
			sqlString.append(" and staff_code like '%"+code+"%' ");
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
		if(!Util.objIsNULL(ET_select)&& ET_select.equals("Y")){
			sqlString.append(" and eliteTeam='"+ET_select+"' ");;
		}
		logger.info("UPLOAD NAME CARD SQL:"+sqlString.toString());
		ps = con.prepareStatement(sqlString.toString());
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

public List<String[]> queryRequst2(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase) {
	ResultSet rs=null;
	List<String[]> list=new ArrayList<String[]>();
	try {
		con = DBManager.getCon();
		StringBuffer sqlString = new StringBuffer("SELECT location, ");
			sqlString.append(" concat(if(CFS_only='Y','"+Constant.CFS+"',''), if(CAM_only='Y','+"+Constant.CAM+"',''), ");
			sqlString.append(" if(CIS_only='Y','"+Constant.CIS+"',''),if(CCL_only='Y','"+Constant.CCL+"',''),if(CFSH_only='Y','"+Constant.CFSH+"',''),");
			sqlString.append(" if(CMS_only='Y','"+Constant.CMS+"',''),if(CFG_only='Y','"+Constant.CFG+"',''),if(Blank_only='Y','"+Constant.Blank+"',''),if(CIB_only='Y','"+Constant.Macau+"','')) as type,");
			sqlString.append(" layout_type,eliteTeam,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
			sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity  ");
			sqlString.append("	 FROM request_Macau where quantity>0 and card_type='N'  ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and date_format(upd_date,'%Y-%m-%d')>='"+startDate+"' ");
			}
		if(!Util.objIsNULL(endDate)){
			sqlString.append(" and date_format(upd_date,'%Y-%m-%d')<='"+endDate+"' ");
		}
		if(!Util.objIsNULL(code)){
			sqlString.append(" and staff_code like '%"+code+"%' ");
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
		if(!Util.objIsNULL(ET_select)&& ET_select.equals("Y")){
			sqlString.append(" and eliteTeam='"+ET_select+"' ");;
		}
		logger.info("UPLOAD NAME CARD SQL:"+sqlString.toString());
		ps = con.prepareStatement(sqlString.toString());
		rs = ps.executeQuery();
		if(rs!=null){
			while(rs.next()){
				list.add(new String[]{
					rs.getString("location"),	
					rs.getString("type"),	
					rs.getString("layout_type"),	
					rs.getString("eliteTeam"),	
					rs.getString("staff_code"),	
					rs.getString("name"),	
					rs.getString("name_chinese"),	
					rs.getString("title_english"),	
					rs.getString("title_chinese"),	
					rs.getString("external_english"),	
					rs.getString("external_chinese"),	
					rs.getString("academic_title_e"),	
					rs.getString("academic_title_c"),
					rs.getString("profess_title_e"),
					rs.getString("profess_title_c"),
					rs.getString("tr_reg_no"),
					rs.getString("ce_no"),
					rs.getString("mpf_no"),
					rs.getString("e_mail"),
					rs.getString("direct_line"),
					rs.getString("fax"),
					rs.getString("bobile_number"),
					rs.getString("quantity")
				});
			}
		}
	}catch (SQLException e) {
		e.printStackTrace();
		logger.error("QUERY NAME CARD ERROR!"+e);
	}catch (ClassNotFoundException e) {
		e.printStackTrace();
		logger.error("QUERY NAME CARD ERROR!"+e);
	}finally{
		DBManager.closeCon(con);
	}
	return list;
}

public static void main(String[] args) {
	StringBuffer sqlString = new StringBuffer("SELECT location, ");
	sqlString.append(" concat(if(CFS_only='Y','"+Constant.CFS+"',''), if(CAM_only='Y','+"+Constant.CAM+"',''), ");
	sqlString.append(" if(CIS_only='Y','"+Constant.CIS+"',''),if(CCL_only='Y','"+Constant.CCL+"',''),if(CFSH_only='Y','"+Constant.CFSH+"',''),");
	sqlString.append(" if(CMS_only='Y','"+Constant.CMS+"',''),if(CFG_only='Y','"+Constant.CFG+"',''),if(Blank_only='Y','"+Constant.Blank+"',''),if(CIB_only='Y','"+Constant.Macau+"','')) as type,");
	sqlString.append(" layout_type,eliteTeam,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
	sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity  ");
	sqlString.append("	 FROM request_Macau where quantity>0 and card_type='N'  ");
	System.out.println(sqlString.toString());
}


	/***
	 * 插入request_Macau
	 */
	public int  saveNewRequest(NewMacau rnb) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="insert request_macau values(?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?)";
			logger.info("saveNewMacau 保存 SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, rnb.getStaff_code());
			ps.setString(2,rnb.getName());
			ps.setString(3, rnb.getName_chinese());
			ps.setString(4, rnb.getTitle_english());
			ps.setString(5, rnb.getTitle_chinese());
			ps.setString(6, rnb.getExternal_english());
			ps.setString(7, rnb.getExternal_chinese());
			ps.setString(8, rnb.getAcademic_title_e());
			ps.setString(9, rnb.getAcademic_title_c());
			ps.setString(10,rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12, rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14, rnb.getMpf_no());
			ps.setString(15, rnb.getE_mail());
			ps.setString(16, rnb.getDirect_line());
			ps.setString(17, rnb.getFax());
			ps.setString(18, rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20, rnb.getUpd_date());
			ps.setString(21,rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23, rnb.getLayout_type());
			ps.setString(24, rnb.getUrgent());
			ps.setString(25, rnb.getUrgentDate());
			ps.setString(26,rnb.getMarks());
			ps.setString(27,rnb.getLocation());
			ps.setString(28, rnb.getAe_consultant());
			ps.setString(29,rnb.getEliteTeam());
			ps.setString(30, rnb.getCAM_only());
			ps.setString(31, rnb.getCFS_only());
			ps.setString(32, rnb.getCIS_only());
			ps.setString(33, rnb.getCCL_only());
			ps.setString(34, rnb.getCFSH_only());
			ps.setString(35, rnb.getCMS_only());
			ps.setString(36,rnb.getCFG_only());
			ps.setString(37, rnb.getBlank_only());
			ps.setString(38,rnb.getCIB_only());
			ps.setString(39,rnb.getPayer());
			s=ps.executeUpdate();
			if(s>0){
				logger.info(Constant.Message.MESSAGE_SUCCESS);
				return s;
			}
			else{
				logger.info(Constant.Message.MESSAGE_FAILD);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("AddRequestDaoImpl 保存异常："+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	
	/**
	 * 插入历史数据Mater
	 */
	public int saveMasterHistory(NewMacau rnb) {
			int s=-1;
			try{
				con=DBManager.getCon();
				sql="insert cn_master values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+
						rnb.getName_chinese()+"','"+rnb.getTitle_english()+"','"+
						rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"+
						rnb.getExternal_chinese()+"','"+rnb.getProfess_title_e()+"','"+
						rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+
						rnb.getCe_no()+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+
						rnb.getDirect_line()+"','"+rnb.getFax()+"','"+rnb.getBobile_number()+"','"+
						rnb.getQuantity()+"','"+rnb.getUrgentDate()+"')";
				logger.info("插入历史数据SQL："+sql);
				ps=con.prepareStatement(sql);
				s=ps.executeUpdate();
				if(s>0){
					logger.info(Constant.Message.MESSAGE_SUCCESS);
					return s;
				}
				else{
					logger.info(Constant.Message.MESSAGE_FAILD);
				}
			}catch(Exception e){
				logger.error("插入历史数据异常："+e);
				e.printStackTrace();
			}finally{
				//关闭连接
				DBManager.closeCon(con);
			}
			return s;
	}
	
	/**
	 * 根据staffcode查询conslist
	 */
	public  List<Consultant_Master> getConsList(String StaffNo) {
		List<Consultant_Master> cm=new ArrayList<Consultant_Master>();
		 Consultant_Master cs=new Consultant_Master();
		try {
			con=DBManager.getCon();
			sql= "select a.EmployeeId as StaffNo,if(Alias ='',EmployeeName,CONCAT(a.EmployeeName, CONCAT(' ',a.Alias))) as englishname,a.C_Name,E_PositionName,C_PositionName,"+
			" E_ExternalTitle_Department,C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,"+
			" b.TrRegNo,b.Ce_No,b.MPF,a.Email,a.DirectLine,c.fax as fax,c.Mobilephone,c.Num as num,CASE WHEN DATEDIFF(NOW(),CASE when c.submit_date ='' or submit_date is null then '1911-01-01' else c.submit_date end) > 7 THEN 'N' ELSE 'Y' END AS sfts"+
			" from cons_macau a left join  tr b  on a.EmployeeId =b.StaffNo  left join "+
			" (select * from cn_master  where staffNo = '"+StaffNo+"'  order by submit_date desc limit 0,1) c on a.EmployeeId = c.staffno"+
			" where a.EmployeeId = '"+StaffNo+"' limit 0,1";
			logger.info("查询所有数据SQL:"+sql);
			ps=con.prepareStatement(sql);
			ResultSet rs=null;
			rs=	ps.executeQuery();
			if(rs.next()){
				cs.setStaffNo(rs.getString(1));
				cs.setName(rs.getString(2));
				cs.setC_Name(rs.getString(3));
				cs.setE_DepartmentTitle(rs.getString(4));
				cs.setC_DepartmentTitle(rs.getString(5));
				cs.setE_ExternalTitle(rs.getString(6));
				cs.setC_ExternalTitle(rs.getString(7));
				cs.setE_EducationTitle(rs.getString(8));
				cs.setC_EducationTitle(rs.getString(9));
				cs.setTR_RegNo(rs.getString(10));
				cs.setCENo(rs.getString(11));
				cs.setMPFNo(rs.getString(12));
				cs.setEmail(rs.getString(13));
				cs.setDirectLine(rs.getString(14));
				cs.setFax(rs.getString(15));
				cs.setMobilePhone(rs.getString(16));
				cs.setNum(rs.getString(17));
				cs.setSfcf(rs.getString(18));
				cm.add(cs);
			}
			else{
				cm=null;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询所有数据异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询所有数据异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return cm;
	}
 
/**
 * 更新RequestNew，但不更新Quantity 普通权限
 */
public int updateAdditionals(NewMacau rnb) {
	String sql="";
	int r=-1;
	try{
		con=DBManager.getCon();
		/*sql="update request_new set  name='"+rnb.getName()+"' , name_chinese='"+rnb.getName_chinese()+"', title_english='"+rnb.getTitle_english()+"', title_chinese='"+rnb.getTitle_chinese()+"', external_english='"+rnb.getExternal_english()+"', external_chinese='"+rnb.getExternal_chinese()+"', academic_title_e='"+rnb.getAcademic_title_e()+"', academic_title_c='"+rnb.getAcademic_title_c()+"', profess_title_e='"+rnb.getProfess_title_e()+"', profess_title_c='"+rnb.getProfess_title_c()+"', tr_reg_no='"+rnb.getTr_reg_no()+"', ce_no='"+rnb.getCe_no()+"', mpf_no='"+rnb.getMpf_no()+"', e_mail='"+rnb.getE_mail()+"', direct_line='"+rnb.getDirect_line()+"', fax='"+rnb.getFax()+"',  bobile_number='"+rnb.getBobile_number()+"', upd_date='"+rnb.getUpd_date()+"', upd_name='"+rnb.getUpd_name()+"' ,location='"+rnb.getLocation()+"',CAM_only='"+rnb.getCAM_only()+"',CFS_only='"+rnb.getCFS_only()+"',CIS_only='"+rnb.getCIS_only()+"',CCL_only='"+rnb.getCCL_only()+"',CFSH_only='"+rnb.getCFSH_only()+"',CMS_only='"+rnb.getCMS_only()+"',CFG_only='"+rnb.getCFG_only()+"',Blank_only='"+rnb.getBlank_only()+"' where UrgentDate='"+rnb.getUrgentDate()+"' and Staff_code='"+rnb.getStaff_code()+"'";*/
		
			sql="update request_Macau set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
		
		logger.info(rnb.getUpd_name()+"：修改 request_Macau SQL:"+sql);
		ps=con.prepareStatement(sql);
		r=ps.executeUpdate();
		
	}catch(Exception e){
		e.printStackTrace();
		logger.error(rnb.getUpd_name()+"：修改request_Macau异常！"+e);
	}
	finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return r;
}
/***
 * 更新requestNew 并更新Quantity 管理员权限
 * @param rnb
 * @return
 */
public int updateNumber(NewMacau rnb,String reStaffNo) {
	String sql="";
	int r=-1;
	try{
		con=DBManager.getCon();
		if(rnb.getStaff_code().equals(reStaffNo)){
			sql="update request_Macau set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
		}else{
			sql="update request_Macau set card_type='"+rnb.getStaff_code()+"' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+reStaffNo+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
		}
		/*sql="update request_new set Payer='"+rnb.getPayer()+"',Staff_code='"+rnb.getStaff_code()+"',urgent='"+rnb.getUrgent()+"',layout_type='"+rnb.getLayout_type()+"',name='"+rnb.getName()+"',name_chinese='"+rnb.getName_chinese()+"',title_english='"+rnb.getTitle_english()+"',title_chinese='"+rnb.getTitle_chinese()+"',external_english='"+rnb.getExternal_english()+"',external_chinese='"+rnb.getExternal_chinese()+"',academic_title_e='"+rnb.getAcademic_title_e()+"',academic_title_c='"+rnb.getAcademic_title_c()+"',profess_title_e='"+rnb.getProfess_title_e()+"',profess_title_c='"+rnb.getProfess_title_c()+"',tr_reg_no='"+rnb.getTr_reg_no()+"',ce_no='"+rnb.getCe_no()+"',mpf_no='"+rnb.getMpf_no()+"',e_mail='"+rnb.getE_mail()+"',direct_line='"+rnb.getDirect_line()+"',fax='"+rnb.getFax()+"',bobile_number='"+rnb.getBobile_number()+"',upd_date='"+rnb.getUpd_date()+"',upd_name='"+rnb.getUpd_name()+"',quantity='"+rnb.getQuantity()+"',location='"+rnb.getLocation()+"',CAM_only='"+rnb.getCAM_only()+"',CFS_only='"+rnb.getCFS_only()+"',CIS_only='"+rnb.getCIS_only()+"',CCL_only='"+rnb.getCCL_only()+"',CFSH_only='"+rnb.getCFSH_only()+"',CMS_only='"+rnb.getCMS_only()+"',CFG_only='"+rnb.getCFG_only()+"',Blank_only='"+rnb.getBlank_only()+"' where UrgentDate='"+rnb.getUrgentDate()+"' and Staff_code='"+reStaffNo+"'";*/

		logger.info(rnb.getUpd_name()+"：修改 request_new SQL:"+sql);
		ps=con.prepareStatement(sql);
		r=ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
		logger.error(rnb.getUpd_name()+"：修改request_new异常！"+e);
	}
	finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return r;
}
/**
 * saveNewMacau 保存
 * NewMacau 对象
 */
public int  saveMacauNew(NewMacau rnb,String reStaffNo) {
	int s=-1;
	String sql="";
	try{
		con=DBManager.getCon();
	/**	sql="insert request_Macau values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()
			+"','"+rnb.getTitle_english()+"','"+rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"
			+rnb.getExternal_chinese()+"','"+rnb.getAcademic_title_e()+"','"+rnb.getAcademic_title_c()+"','"
			+rnb.getProfess_title_e()+"','"+rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+rnb.getCe_no()
			+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+rnb.getDirect_line()+"','"+rnb.getFax()+"','"
			+rnb.getBobile_number()+"','"+rnb.getQuantity()+"','"+rnb.getUpd_date()+"','"+rnb.getUpd_name()+"','"
			+rnb.getCard_type()+"','"+rnb.getLayout_type()+"','"+rnb.getUrgent()+"','"+rnb.getUrgentDate()+"','"+rnb.getMarks()+"','"+rnb.getLocation()+"','"+rnb.getAe_consultant()+"','"
			+rnb.getCAM_only()+"','"+rnb.getCFS_only()+"','"+rnb.getCIS_only()+"','"+rnb.getCCL_only()+"','"
			+rnb.getCFSH_only()+"','"+rnb.getCMS_only()+"','"+rnb.getCFG_only()+"','"+rnb.getBlank_only()+"','"+rnb.getCIB_only()+"','"+rnb.getPayer()+"')";
		logger.info("saveNewRequest 保存 SQL:"+sql);
		ps=con.prepareStatement(sql);
		**/
		sql="insert request_macau values(?,?,?,?,?,?,?,?,?,?," +
		"?,?,?,?,?,?,?,?,?," +
		"?,?,?,?,?,?,?,?,?,?," +
		"?,?,?,?,?,?,?,?,?,?)";
logger.info("saveNewMacau 保存 SQL:"+sql);
ps=con.prepareStatement(sql);
ps.setString(1, rnb.getStaff_code());
ps.setString(2,rnb.getName());
ps.setString(3, rnb.getName_chinese());
ps.setString(4, rnb.getTitle_english());
ps.setString(5, rnb.getTitle_chinese());
ps.setString(6, rnb.getExternal_english());
ps.setString(7, rnb.getExternal_chinese());
ps.setString(8, rnb.getAcademic_title_e());
ps.setString(9, rnb.getAcademic_title_c());
ps.setString(10,rnb.getProfess_title_e());
ps.setString(11,rnb.getProfess_title_c());
ps.setString(12, rnb.getTr_reg_no());
ps.setString(13,rnb.getCe_no());
ps.setString(14, rnb.getMpf_no());
ps.setString(15, rnb.getE_mail());
ps.setString(16, rnb.getDirect_line());
ps.setString(17, rnb.getFax());
ps.setString(18, rnb.getBobile_number());
ps.setString(19,rnb.getQuantity());
ps.setString(20, rnb.getUpd_date());
ps.setString(21,rnb.getUpd_name());
ps.setString(22, rnb.getCard_type());
ps.setString(23, rnb.getLayout_type());
ps.setString(24, rnb.getUrgent());
ps.setString(25, rnb.getUrgentDate());
ps.setString(26,rnb.getMarks());
ps.setString(27,rnb.getLocation());
ps.setString(28, rnb.getAe_consultant());
ps.setString(29,rnb.getEliteTeam());
ps.setString(30, rnb.getCAM_only());
ps.setString(31, rnb.getCFS_only());
ps.setString(32, rnb.getCIS_only());
ps.setString(33, rnb.getCCL_only());
ps.setString(34, rnb.getCFSH_only());
ps.setString(35, rnb.getCMS_only());
ps.setString(36,rnb.getCFG_only());
ps.setString(37, rnb.getBlank_only());
ps.setString(38,rnb.getCIB_only());
ps.setString(39,rnb.getPayer());
s=ps.executeUpdate();
		
		
		if(s>0){
			logger.info(Constant.Message.MESSAGE_SUCCESS);
			return s;
		}
		else{
			logger.info(Constant.Message.MESSAGE_FAILD);
		}
	}catch(Exception e){
		e.printStackTrace();
		logger.error("AddRequestDaoImpl 保存异常："+e);
	}finally{
		DBManager.closeCon(con);
	}
	return s;
}
/**
 * updateRequestRecord
 */
public int updateRequestRecord(NewMacau rnb,String Payer, String rePayer) {
	int r=-1;
	try{
		con=DBManager.getCon();
		sql="update req_record set code='"+Payer+"', quantity='"+rnb.getQuantity()+"' ,upd_name='"+rnb.getUpd_name()+"', Layout_Type='"+rnb.getLayout_type()+"',Urgent='"+rnb.getUrgent()+"' where code='"+rePayer+"' and request_date='"+rnb.getUrgentDate()+"'";
		logger.info("保存RequestRecord信息："+sql);
		ps=con.prepareStatement(sql);
		r=ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
		logger.error("修改RequestRecord信息异常！"+e);
	}finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return r;
}
/**
 * 更新历史表并更新numbers 管理员权限
 * @param rnb
 * @return
 */
public int updateMasterNumber(NewMacau rnb, String reStaffNo ) {
	int r=-1;
	try{
		con=DBManager.getCon();
		sql="update cn_master set staffNo='"+rnb.getStaff_code()+"', Name='"+
		rnb.getName()+"' ,  C_Name='"+
		rnb.getName_chinese()+"' , E_Title_Department='"+
		rnb.getTitle_english()+"' , C_Title_Department='"+
		rnb.getTitle_chinese()+"' , E_ExternalTitle_Department='"+
		rnb.getExternal_english()+"' , C_ExternalTitle_Department='"+
		rnb.getExternal_chinese()+"' , E_EducationTitle='"+
		rnb.getProfess_title_e()+"' , C_EducationTitle='"+
		rnb.getProfess_title_c()+"' , TR_RegNo='"+
		rnb.getTr_reg_no()+"' , CE_No='"+
		rnb.getCe_no()+"' , MPF_No='"+
		rnb.getMpf_no()+"' , Email='"+
		rnb.getE_mail()+"' , DirectLine='"+
		rnb.getDirect_line()+"' , Fax='"+
		rnb.getFax()+"' , MobilePhone='"+
		rnb.getBobile_number()+"' , num='"+
		rnb.getQuantity()+"' where submit_date='"+rnb.getUrgentDate()+"' and staffNo='"+reStaffNo+"'";
		ps=con.prepareStatement(sql);
		r=	ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		DBManager.closeCon(con);
	}
	return r;
}
/**
 * 刪除財務表數據
 */
public int deleteChargeRecord(String name,String StaffNo, String AddDate) {
	int d=-1;
	String sql="";
	try{
		sql="update  change_Macau set up_date='"+DateUtils.getNowDateTime()+"' , up_name='"+name+"', sfyx='N' where StaffCode='"+StaffNo+"' and AddDate='"+AddDate+"'";
		con=DBManager.getCon();
		
		logger.info("刪除財務表數據MacauDaoImpl SQL:"+sql.toString());
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

 public int saveCIB(String filename, InputStream os,String username) {
	int num=0;
	int beginRowIndex =4;// 开始读取数据的行数
	int totalRows = 0;// 总行数
	Util.deltableforPA("cons_Macau");/*           等待填写                      */
	//Util.nq_addition();
	try {
		con = DBManager.getCon();
		HSSFWorkbook workbook = new HSSFWorkbook(os);
		HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
		totalRows = sheet.getLastRowNum();// 获取总行数
		for (int i = beginRowIndex; i <= totalRows; i++) {
			HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
							/**获取Excel里面的指定单元格数据**/
			HSSFCell EmployeeIdcell = row.getCell(0);
			HSSFCell EmployeeNamecell=row.getCell(1);
		
			HSSFCell C_Namecell=row.getCell(2);
			HSSFCell Aliascell=row.getCell(3);
			HSSFCell joinDatecell=row.getCell(4);
			HSSFCell DirectLinecell=row.getCell(5);
			HSSFCell Emailcell=row.getCell(6);
		
			HSSFCell ExternalPositioncell=row.getCell(7);
			HSSFCell Gradecell=row.getCell(8);
			HSSFCell HKIDcell=row.getCell(9);
			HSSFCell GroupDateJoincell=row.getCell(10);
			HSSFCell Locationcell=row.getCell(11);
			
			HSSFCell Mobilecell=row.getCell(13);
			HSSFCell Positioncell=row.getCell(14);
			HSSFCell C_PositionNamecell=row.getCell(15);
			HSSFCell E_PositionNamecell=row.getCell(16);
			HSSFCell RecruiterIdcell=row.getCell(17);
			HSSFCell RecruiterNamecell=row.getCell(18);
			HSSFCell TelephoneNocell=row.getCell(20);

			/**给数据库里面的字段赋值**/
			String EmployeeId =Util.cellToString(EmployeeIdcell);
			String Alias=Util.cellToString(Aliascell);
			String C_Name=Util.cellToString(C_Namecell);
			String joinDate=Util.cellToString(joinDatecell);
			String DirectLine=Util.cellToString(DirectLinecell);
			String Email=Util.cellToString(Emailcell);
			String EmployeeName=Util.cellToString(EmployeeNamecell);
			String ExternalPosition=Util.cellToString(ExternalPositioncell);
			String Grade=Util.cellToString(Gradecell);
			String HKID=Util.cellToString(HKIDcell);
			String GroupDateJoin=Util.cellToString(GroupDateJoincell);
			String Location=Util.cellToString(Locationcell);
			String Mobile=Util.cellToString(Mobilecell);
			String Position=Util.cellToString(Positioncell);
			String C_PositionName=Util.cellToString(C_PositionNamecell);
			String E_PositionName=Util.cellToString(E_PositionNamecell);
			String RecruiterId=Util.cellToString(RecruiterIdcell);
			String RecruiterName=Util.cellToString(RecruiterNamecell);
			String TelephoneNo=Util.cellToString(TelephoneNocell);
			
			if(!Util.objIsNULL(EmployeeId) && !Util.objIsNULL(EmployeeName)){
				sql = "insert cons_Macau values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1,EmployeeId);
				ps.setString(2,Alias);
				ps.setString(3,C_Name);
				ps.setString(4,joinDate);
				ps.setString(5,DirectLine);
				ps.setString(6,Email);
				ps.setString(7,EmployeeName);
				ps.setString(8,ExternalPosition);
				ps.setString(9,Grade);
				ps.setString(10,HKID);
				ps.setString(11,GroupDateJoin);
				ps.setString(12,Location);
				ps.setString(13,Mobile);
				ps.setString(14,Position);
				ps.setString(15,C_PositionName);
				ps.setString(16,E_PositionName);
				ps.setString(17,RecruiterId);
				ps.setString(18,RecruiterName);
				ps.setString(19,"");//上传文件中未提供TelephoneNo  2013年5月10日10:09:26
				int rsNum = ps.executeUpdate();
				if (rsNum > 0) {
					logger.info("插入consultant——Macau成功！");
					num++;
				} else {
					logger.info("插入consultant——Macau失敗");
				}
/*				if(Grade.equals(Constant.PA_TYPE)){
					
				 	try{
				 		sql="insert nq_additional values('"+EmployeeId+"','"+EmployeeName+"','-300','PA','"+username+"','"+DateUtils.getDateToday()+"','',null,'Y')";
						ps=con.prepareStatement(sql);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					ps.executeUpdate(); 
				}	
				if(Grade.equals(Constant.SA_TYPE)){
					 
					 try{
						sql="insert nq_additional values('"+EmployeeId+"','"+EmployeeName+"','-300','SA','"+username+"','"+DateUtils.getDateToday()+"','',null,'Y')";
						ps=con.prepareStatement(sql);
					}catch(Exception e){
						e.printStackTrace();
					}
					ps.executeUpdate(); 
				}*/
			}
	
			
		}
	} catch (Exception e) {
		return num;
	//	e.printStackTrace();
	} finally {
		//关闭连接
		DBManager.closeCon(con);
	}
	return num;
}

public int getRows(String ET_select,String layout_select,String name, String code, String startDate, String endDate,
		String location, String urgentCase) {
	int num=-1;
 
	try {
		con= DBManager.getCon();
		StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM request_macau where quantity>0 and card_type='N' ");
		if(!Util.objIsNULL(startDate)){
			sqlString.append(" and date_format(upd_date,'%Y-%m-%d') >='"+startDate+"' ");
			
		}
		if(!Util.objIsNULL(endDate)){
			sqlString.append(" and date_format(upd_date,'%Y-%m-%d') <='"+endDate+"' ");
			
		}
		if(!Util.objIsNULL(code)){
			sqlString.append(" and staff_code like '%"+code+"%' ");
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
		if(!Util.objIsNULL(ET_select)&& ET_select.equals("Y")){
			sqlString.append(" and eliteTeam='"+ET_select+"' ");;
		}
		sqlString.append(" order by UrgentDate desc ");
		logger.info("QUERY NAME CARD SQL:"+sqlString.toString());
		ps = con.prepareStatement(sqlString.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			num=Integer.parseInt(rs.getString("Num"));
		}
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
		num=0;
	}finally
	{
		//关闭连接
		DBManager.closeCon(con);
	}
	return num;
}
public String queryConsNum(String code) {
	String a = "";
	String b = "";
	String c = "";
	String d = "";
	try {
		con = DBManager.getCon();
		sql ="SELECT EmployeeId,eqnum,additional,Tzero((eqnum+additional)-(used-selfpay)) as remaining,used,selfpay from  (" 
			+" SELECT  EmployeeId,ReplPA_SA_NUM(EmployeeId) as eqnum,Tzero(Replnum(additional)) AS additional, Tzero(b.quantity) as used,Tzero(c.selfpay) as selfpay"
			+"  FROM (select * from cons_macau  where employeeId='"+code+"') t "
			+"		left join nq_additional d on t.EmployeeId = d.initials  and sfyx='Y' AND   year(ADD_DATE) = year(NOW()) "
			+"	    left join (SELECT code,name,FORMAT(sum(quantity),0) as quantity  FROM req_record WHERE year(request_date) = year(NOW()) group by code) b "
			+"	    	on t.EmployeeId = b.code "
			+"      left join (select sum(Number) as selfpay,StaffCode from change_record WHERE year(adddate) = year(NOW()) and sfyx='Y' group by StaffCode ) c "
			+"            on t.EmployeeId = c.StaffCode"
			+" )AS marquee where EmployeeId ='"+code+"'  limit 0,1";
		logger.info("获取msgSQL："+sql);
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			a = rs.getString(1);
			b = rs.getString(2);
			c = rs.getString(3);
			d = rs.getString(4);
		}rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
		logger.error("获取cons_list英文名称异常！"+e);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		logger.error("获取cons_list英文名称异常！"+e);
	} finally {
		//关闭连接
		DBManager.closeCon(con);
	}
	//return a+"您本年度的印名片数额为："+b+" ；额外补助数额为："+c+" ；已经使用数额为："+d +" 。 ";
	return a+",The quota on this year："+b+" ; Extra quota is："+c+" ; Remaining amount is: "+d +" . ";
}


}
