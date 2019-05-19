package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.ConsconvoyNamecardDao;
import entity.Consultant_Master;
import entity.NameCardConvoy;
/**
 * 挂在convoy系统下 顾问名片办理
 * @author Wilson
 *
 */
public class ConsconvoyNamecardDaoImpl implements ConsconvoyNamecardDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(ConsconvoyNamecardDaoImpl.class);
	
	/**
	 * saveNewRequest 保存
	 * RequestNewBean 对象
	 */
	public int  saveNewCardConvoy(NameCardConvoy rnb,String reStaffNo) {
		int s=-1;
		String sql="";
		try{
			con=DBManager.getCon();
			sql="insert request_new_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,Payer,shzt,add_date,add_name,upd_date,upd_name,remark,remarkcons) values('"+reStaffNo+"','"+rnb.getName()+"','"+rnb.getName_chinese()
				+"','"+rnb.getTitle_english()+"','"+rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"
				+rnb.getExternal_chinese()+"','"+rnb.getAcademic_title_e()+"','"+rnb.getAcademic_title_c()+"','"
				+rnb.getProfess_title_e()+"','"+rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+rnb.getCe_no()
				+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+rnb.getDirect_line()+"','"+rnb.getFax()+"','"
				+rnb.getBobile_number()+"','"+rnb.getQuantity()+"','" 
				+rnb.getCard_type()+"','"+rnb.getLayout_type()+"','"+rnb.getUrgent()+"','"+rnb.getUrgentDate()+"','"+rnb.getMarks()+"','"+rnb.getLocation()+"','"+rnb.getAe_consultant()+"','"
				+rnb.getEliteTeam()+"','"+rnb.getCAM_only()+"','"+rnb.getCFS_only()+"','"+rnb.getCIS_only()+"','"+rnb.getCCL_only()+"','"
				+rnb.getCFSH_only()+"','"+rnb.getCMS_only()+"','"+rnb.getCFG_only()+"','"+rnb.getBlank_only()+"','"+
				rnb.getCIB_only()+"','"+rnb.getPayer()+"','"+rnb.getShzt()+"','"+rnb.getAdd_date()+"','"+rnb.getAdd_name()+"','"
				+rnb.getUpd_date()+"','"+rnb.getUpd_name()+"','"+rnb.getRemark()+"')";
			logger.info("convoy new Name Card 保存 SQL:"+sql);
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
			logger.error("Add convoy new Name Card ："+Constant.Message.MESSAGE_FAILD+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	/***
	 * convoy new Name Card
	 */
	public int  saveNewRequest(NameCardConvoy rnb) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="insert request_new_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,Payer,shzt,add_date,add_name,upd_date,upd_name,remark,remarkcons) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
					",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			logger.info("saveNewRequest convoy 保存 SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1,rnb.getStaff_code());
			ps.setString(2,rnb.getName());
			ps.setString(3,rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7,rnb.getExternal_chinese());
			ps.setString(8,rnb.getAcademic_title_e());
			ps.setString(9,rnb.getAcademic_title_c());
			ps.setString(10,rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12,rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14,rnb.getMpf_no());
			ps.setString(15,rnb.getE_mail());
			ps.setString(16,rnb.getDirect_line());
			ps.setString(17,rnb.getFax());
			ps.setString(18,rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20,rnb.getCard_type());
			ps.setString(21,rnb.getLayout_type());
			ps.setString(22,rnb.getUrgent());
			ps.setString(23,rnb.getUrgentDate());
			ps.setString(24,rnb.getMarks());
			ps.setString(25,rnb.getLocation());
			ps.setString(26,rnb.getAe_consultant());
			ps.setString(27,rnb.getEliteTeam());
			ps.setString(28,rnb.getCAM_only());
			ps.setString(29,rnb.getCFS_only());
			ps.setString(30,rnb.getCIS_only());
			ps.setString(31,rnb.getCCL_only());
			ps.setString(32,rnb.getCFSH_only());
			ps.setString(33,rnb.getCMS_only());
			ps.setString(34,rnb.getCFG_only());
			ps.setString(35,rnb.getBlank_only());
			ps.setString(36,rnb.getCIB_only());
			ps.setString(37,rnb.getPayer());
			ps.setString(38,rnb.getShzt());
			ps.setString(39,rnb.getAdd_date());
			ps.setString(40,rnb.getAdd_name());
			ps.setString(41,rnb.getUpd_date());
			ps.setString(42,rnb.getUpd_name());
			ps.setString(43,rnb.getRemark());
			ps.setString(44,rnb.getRemarkcons());
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
			logger.error("request_new_convoy "+Constant.Message.MESSAGE_FAILD+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	/**
	 * saveNewNameCard 保存
	 */
	public int  saveNameCardConvoy(NameCardConvoy rnb,String reStaffNo) {
		int s=-1;
		String sql="";
		try{
			con=DBManager.getCon();
			sql="insert request_new_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,Payer,shzt,add_date,add_name,upd_date,upd_name,remark,remarkcons) values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()
				+"','"+rnb.getTitle_english()+"','"+rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"
				+rnb.getExternal_chinese()+"','"+rnb.getAcademic_title_e()+"','"+rnb.getAcademic_title_c()+"','"
				+rnb.getProfess_title_e()+"','"+rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+rnb.getCe_no()
				+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+rnb.getDirect_line()+"','"+rnb.getFax()+"','"
				+rnb.getBobile_number()+"','"+rnb.getQuantity()+"','"
				+rnb.getCard_type()+"','"+rnb.getLayout_type()+"','"+rnb.getUrgent()+"','"+rnb.getUrgentDate()+"','"+
				rnb.getMarks()+"','"+rnb.getLocation()+"','"+rnb.getAe_consultant()+"','"+rnb.getEliteTeam()+"','"
				+rnb.getCAM_only()+"','"+rnb.getCFS_only()+"','"+rnb.getCIS_only()+"','"+rnb.getCCL_only()+"','"
				+rnb.getCFSH_only()+"','"+rnb.getCMS_only()+"','"+rnb.getCFG_only()+"','"+rnb.getBlank_only()+"','"+
				rnb.getCIB_only()+"','"+rnb.getPayer()+"','"+rnb.getShzt()+"','"+rnb.getAdd_date()+"','"+rnb.getAdd_name()+"','"
				+rnb.getUpd_date()+"','"+rnb.getUpd_name()+"','"+rnb.getRemark()+"')";
			logger.info("request_new_convoy 保存 SQL:"+sql);
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
			logger.error("request_new_convoy "+Constant.Message.MESSAGE_FAILD+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	public List<NameCardConvoy> queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase) {
		
		List<NameCardConvoy> list = new ArrayList<NameCardConvoy>();
		try {
			con= DBManager.getCon();
			StringBuffer sqlString = new StringBuffer("SELECT staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
					"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer," +
					"CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,ae_Consultant,location ");
				sqlString.append("FROM request_new_convoy where quantity>0 and card_type='N' and  ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" date_format(upd_date,'%Y-%m-%d') >='"+startDate+"' ");
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
			sqlString.append(" order by UrgentDate desc ");
			logger.info("QUERY NAME CARD SQL:"+sqlString.toString());
			ps = con.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				NameCardConvoy rsBean = new NameCardConvoy();
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
				rsBean.setLocation(rs.getString(34));
				list.add(rsBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("QUERY Macau NameCard ERROR!"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("QUERY Macau NameCard ERROR!"+e);
		}
		return list;
	}
	
public ResultSet queryRequst(String name,String code,String startDate,String endDate,String location,String urgentCase) {
	ResultSet rs=null;
	try {
		con = DBManager.getCon();
		StringBuffer sqlString = new StringBuffer("SELECT case location when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'M' THEN '"+Constant.Macau+"' END as location, ");
			sqlString.append(" concat(if(CFS_only='Y','"+Constant.CFS+"',''), if(CAM_only='Y','+"+Constant.CAM+"',''), ");
			sqlString.append(" if(CIS_only='Y','"+Constant.CIS+"',''),if(CCL_only='Y','"+Constant.CCL+"',''),if(CFSH_only='Y','"+Constant.CFSH+"',''),");
			sqlString.append(" if(CMS_only='Y','"+Constant.CMS+"',''),if(CFG_only='Y','"+Constant.CFG+"',''),if(Blank_only='Y','"+Constant.Blank+"',''),if(CIB_only='Y','"+Constant.Macau+"','')) as type,");
			sqlString.append(" layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
			sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity  ");
			sqlString.append("	 FROM request_new_convoy where quantity>0 and card_type='N' and  ");
		if(!Util.objIsNULL(startDate)){
			sqlString.append(" date_format(upd_date,'%Y-%m-%d')>='"+startDate+"' ");
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
	
	/**
	 * 插入历史数据Mater
	 */
	public int saveMasterHistory(NameCardConvoy rnb) {
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
	 * 根据staff code查询cons list
	 */
	public  List<Consultant_Master> getConsList(String StaffNo) {
		List<Consultant_Master> cm=new ArrayList<Consultant_Master>();
		 Consultant_Master cs=new Consultant_Master();
		try {
			con=DBManager.getCon();
			sql= "select a.EmployeeId as StaffNo,c.Name as englishname,a.C_Name,E_PositionName,C_PositionName,"+
			" E_ExternalTitle_Department,C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,"+
			" b.TrRegNo,b.Ce_No,b.MPF,a.Email,a.DirectLine,c.fax as fax,a.Mobile,c.Num as num "+
			" from cons_list a left join  tr b  on a.EmployeeId =b.StaffNo  left join "+
			" (select * from cn_master  where staffNo = '"+StaffNo+"' and Year(submit_date)=Year(now()) order by submit_date desc limit 0,1) c on a.EmployeeId = c.staffno"+
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
				cm.add(cs);
				
			}else{
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
public int updateAdditionals(NameCardConvoy rnb) {
	String sql="";
	int r=-1;
	try{
		con=DBManager.getCon();
		
			sql="update request_new_convoy set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
		
		logger.info(rnb.getUpd_name()+"：修改 request_new_convoy SQL:"+sql);
		ps=con.prepareStatement(sql);
		r=ps.executeUpdate();
		
	}catch(Exception e){
		e.printStackTrace();
		logger.error(rnb.getUpd_name()+"：修改request_new_convoy异常！"+e);
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
public int updateNumber(NameCardConvoy rnb,String reStaffNo) {
	String sql="";
	int r=-1;
	try{
		con=DBManager.getCon();
		if(rnb.getStaff_code().equals(reStaffNo)){
			sql="update request_new_convoy set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
		}else{
			sql="update request_new_convoy set card_type='"+rnb.getStaff_code()+"' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+reStaffNo+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
		}

		logger.info(rnb.getUpd_name()+"：修改 request_new_convoy SQL:"+sql);
		ps=con.prepareStatement(sql);
		r=ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
		logger.error(rnb.getUpd_name()+"：修改request_new_convoy异常！"+e);
	}
	finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return r;
}
 
/**
 * updateRequestRecord
 */
public int updateRequestRecord(NameCardConvoy rnb,String Payer, String rePayer) {
	int r=-1;
	try{
		con=DBManager.getCon();
		sql="update request_new_convoy set code='"+Payer+"', quantity='"+rnb.getQuantity()+"' ,upd_name='"+rnb.getUpd_name()+"', Layout_Type='"+rnb.getLayout_type()+"',Urgent='"+rnb.getUrgent()+"' where code='"+rePayer+"' and request_date='"+rnb.getUrgentDate()+"'";
		logger.info("保存request_new_convoy信息："+sql);
		ps=con.prepareStatement(sql);
		r=ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
		logger.error("修改request_new_convoy信息异常！"+e);
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
public int updateMasterNumber(NameCardConvoy rnb, String reStaffNo ) {
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
		sql="update  change_record set up_date='"+DateUtils.getNowDateTime()+"' , up_name='"+name+"', sfyx='N' where StaffCode='"+StaffNo+"' and AddDate='"+AddDate+"'";
		con=DBManager.getCon();
		
		logger.info("Name card convoy 刪除財務表數據 SQL:"+sql.toString());
		ps=con.prepareStatement(sql);
		d=ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
		logger.error("Name card convoy 刪除財務表數據异常！"+e);
	}finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return d;
}
/**
 *  修改
 */
 
public int updateConsShzt(String updatename ,String urgentdate, String staffcode, String shzt, String remark) {
	int d=-1;
	String sql="";
	String refno="";
	String code="";
	String nameE="";
	String nameC="";
	try{
		ResultSet rs=null;	
		con=DBManager.getCon();
		con.setAutoCommit(false);
		//审核状态置为Y
		//sql="update  request_new_convoy set shzt='"+shzt+"' , upd_name='"+updatename+"', upd_date='"+DateUtils.getNowDateTime()+"', remark='"+remark+"' where staff_code='"+staffcode+"' and UrgentDate='"+urgentdate+"'";
		sql="update  request_new_convoy set shzt=? , upd_name=?, upd_date=?, remark=? where staff_code=? and UrgentDate=?";
		logger.info("Name card convoy 修改审核状态 SQL:"+sql.toString());
		ps=con.prepareStatement(sql);
		ps.setString(1, shzt);
		ps.setString(2, updatename);
		ps.setString(3, DateUtils.getNowDateTime());
		ps.setString(4, remark);
		ps.setString(5, staffcode);
		ps.setString(6, urgentdate);
		d=ps.executeUpdate();
		if(d<0){
			throw new RuntimeException("修改审核状态失败!");
		}
	/****************************************修改(Reject)审核保存到request_new_convoy_detial*************************************************************/
		sql="select refno,staff_code,name,name_chinese from request_new_convoy where staff_code='"+staffcode+"' and UrgentDate='"+urgentdate+"' ";
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next()){
			refno=rs.getString("refno");
			code=rs.getString("staff_code");
			nameE=rs.getString("name");
			nameC=rs.getString("name_chinese");
		}
		//System.out.println(refno+"-----"+code+"-->"+nameE+"--"+nameC);
		sql="insert into request_new_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark) " +
		//"values('"+updatename+"','"+refno+"','"+code+"','"+nameE+"','"+nameC+"','Reject','"+updatename+"','"+DateUtils.getNowDateTime()+"','');";
		"values(?,?,?,?,?,?,?,?,'');";
		ps=con.prepareStatement(sql);
		ps.setString(1, updatename);
		ps.setString(2, refno);
		ps.setString(3, code);
		ps.setString(4, nameE);
		ps.setString(5, nameC);
		ps.setString(6, "Reject");
		ps.setString(7, updatename);
		ps.setString(8, DateUtils.getNowDateTime());
		d=ps.executeUpdate();
		if(d<0){
			logger.info(updatename+"修改审核NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
			throw new RuntimeException("修改审核NameCardRequest[request_new_convoy_detial]失败");
		}
		con.commit();
	/********************************************************************************************************************************************/
	}catch(Exception e){
		e.printStackTrace();
		logger.error("Name card convoy 修改状态异常："+e);
	}finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return d;
}
public String queryConsNum(String code,boolean DD) {
	String a = "";
	String b = "";
	String c = "";
	String d = "";
	//String confi="";
	//if(!DD){
	//	confi=" and Layout_Type='S' ";
	//}
	try {
		con = DBManager.getCon();
		sql ="SELECT EmployeeId,eqnum,additional,Tzero((eqnum+additional)-(used-selfpay)) as remaining,Tzero(used-selfpay)as used,selfpay from  (" 
			+" SELECT  EmployeeId,ReplPA_SA_NUM(EmployeeId) as eqnum,Tzero(Replnum(additionals)) AS additional, Tzero(b.quantity) as used,Tzero(c.selfpay) as selfpay"
			+"  FROM (select * from cons_list  where employeeId='"+code+"') t "
			+"		left join  (select *,sum(additional) as additionals from nq_additional where additional >0  AND   year(ADD_DATE) = year(NOW()) group by initials) d on t.EmployeeId = d.initials  and sfyx='Y' "
			+"	    left join (SELECT code,name,cast(sum(quantity)as decimal) as quantity  FROM req_record WHERE year(request_date) = year(NOW())  group by code) b "
			+"	    	on t.EmployeeId = b.code "
			+"      left join (select sum(Number) as selfpay,StaffCode from change_record WHERE year(adddate) = year(NOW()) and sfyx='Y' group by StaffCode ) c "
			+"            on t.EmployeeId = c.StaffCode"
			+" )AS marquee where EmployeeId ='"+code+"'  limit 0,1";
		logger.info("获取ConsMarquee Message SQL："+sql);
		//System.out.println(sql);
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			a = rs.getString(1);
			b = rs.getString(2);
			c = rs.getString(3);
			d = rs.getString(4);
		}
		rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
		logger.error("获取ConsMarquee Message 英文名称异常！"+e);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		logger.error("获取ConsMarquee Message英文名称异常！"+e);
	} finally {
		//关闭连接
		DBManager.closeCon(con);
	}
	//return a+"您本年度的印名片数额为："+b+" ；额外补助数额为："+c+" ；已经使用数额为："+d +" 。 ";
	return a+",The quota on this year："+b+" ; Extra quota is："+c+" ; Remaining amount is: "+d +" . ";
}
public int delNameCard(String table, String staffcode, String urgentDate) {
	int num=-1;
	try{
		
		con=DBManager.getCon();
		sql="update "+table+" set card_type='D' where staff_code=? and UrgentDate=?";
		ps=con.prepareStatement(sql);
		ps.setString(1, staffcode);
		ps.setString(2, urgentDate);
		num =ps.executeUpdate();
		logger.info("删除 "+table+" 成功!"+staffcode+"--UrgetnDate=="+urgentDate);
	}catch(Exception e){
		e.printStackTrace();
		logger.error("删除 "+table+" 异常!"+e);
		num=-2;
	}finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	return num;
}
 
}
