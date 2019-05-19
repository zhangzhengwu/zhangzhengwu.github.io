package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Util;
import dao.AddStaffRequestDao;
import entity.RequestStaffBean;
import entity.RequestStaffConvoyBean;
/**
 * 保存AddStaffRequestDao实现类
 * @author Wilson.SHEN
 *
 */
public class AddStaffRequestDaoImpl implements AddStaffRequestDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(AddStaffRequestDaoImpl.class);
	/**
	 * saveStaffRequest 保存方法
	 */
	public int  saveStaffRequest(RequestStaffBean rnb) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="insert request_staff(staff_code,"+
				" name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
				"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,"+
				"bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks,location,"+
				"ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,"+
				"CCIA_only,CCFSH_only,CWMC_only,Payer,Company) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("Staff信息保存方法SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, rnb.getStaff_code());
			ps.setString(2, rnb.getName());
			ps.setString(3, rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7,rnb.getExternal_chinese());
			ps.setString(8,rnb.getAcademic_title_e());
			ps.setString(9,rnb.getAcademic_title_c());
			ps.setString(10, rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12,rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14,rnb.getMpf_no());
			ps.setString(15,rnb.getE_mail());
			ps.setString(16,rnb.getDirect_line());
			ps.setString(17,rnb.getFax());
			ps.setString(18,rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20,rnb.getUpd_date());
			ps.setString(21, rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23,rnb.getLayout_type());
			ps.setString(24,rnb.getUrgent());
			ps.setString(25,rnb.getUrgentDate());
			ps.setString(26,rnb.getMarks());
			ps.setString(27, rnb.getLocation());
			ps.setString(28,"N");
			ps.setString(29, rnb.getEliteTeam());
			ps.setString(30,"");
			ps.setString(31,"");
			ps.setString(32,"");
			ps.setString(33,"");
			ps.setString(34,"");
			ps.setString(35,"");
			ps.setString(36,"");
			ps.setString(37,"");
			ps.setString(38,"");
			ps.setString(39,"");
			ps.setString(40,"");
			ps.setString(41,"");
			ps.setString(42,rnb.getCompany());
			logger.info(ps);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("插入request_staff成功！");
				return s;
			}else{
				logger.info("插入request_staff失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Staff信息保存异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	/**
	 * saveStaffRequest 保存方法
	 */
	public int  saveStaffRequest(RequestStaffConvoyBean rnb) {
		int s=-1;
		try{
			
			con=DBManager.getCon();
			sql="insert request_staff(staff_code,"+
				" name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
				"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,"+
				"bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks,location,"+
				"ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,"+
				"CCIA_only,CCFSH_only,CWMC_only,Payer) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("Staff信息保存方法SQL:"+sql+"---  "+Util.reflectTest(rnb));
			ps=con.prepareStatement(sql);
			ps.setString(1, rnb.getStaff_code());
			ps.setString(2, rnb.getName());
			ps.setString(3, rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7,rnb.getExternal_chinese());
			ps.setString(8,rnb.getAcademic_title_e());
			ps.setString(9,rnb.getAcademic_title_c());
			ps.setString(10, rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12,rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14,rnb.getMpf_no());
			ps.setString(15,rnb.getE_mail());
			ps.setString(16,rnb.getDirect_line());
			ps.setString(17,rnb.getFax());
			ps.setString(18,rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20,rnb.getUpd_date());
			ps.setString(21, rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23,rnb.getLayout_type());
			ps.setString(24,rnb.getUrgent());
			ps.setString(25,rnb.getUrgentDate());
			ps.setString(26,rnb.getMarks());
			ps.setString(27, rnb.getLocation());
			ps.setString(28,"N");
			ps.setString(29, rnb.getEliteTeam());
			ps.setString(30,rnb.getCAM_only());
			ps.setString(31,rnb.getCFS_only());
			ps.setString(32,rnb.getCIS_only());
			ps.setString(33,rnb.getCCL_only());
			ps.setString(34,rnb.getCFSH_only());
			ps.setString(35,rnb.getCMS_only());
			ps.setString(36,rnb.getCFG_only());
			ps.setString(37,rnb.getBlank_only());
			ps.setString(38,rnb.getCCIA_only());
			ps.setString(39,rnb.getCCFSH_only());
			ps.setString(40,rnb.getCWMC_only());
			ps.setString(41,rnb.getStaff_code());
			s=ps.executeUpdate();
			if(s>0){
				logger.info("插入request_staff成功！");
				return s;
			}
			else{
				logger.info("插入request_staff失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Staff信息保存异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	/**
	 * 插入历史数据Mater
	 */
	public int saveStaffMasterHistory(RequestStaffBean rnb) {
			int s=-1;
			try{
				con=DBManager.getCon();
				sql="insert staff_master(staffNo,name,c_Name," +
						"e_Title_Department,c_Title_Department,e_ExternalTitle_Department," +
						"c_ExternalTitle_Department,e_EducationTitle," +
						"c_EducationTitle,tr_RegNo," +
						"ce_No,MPF_No,email," +
						"directLine,fax,mobilePhone,num,submit_date) " +
						" values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+
						rnb.getName_chinese()+"','"+rnb.getTitle_english()+"','"+
						rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"+
						rnb.getExternal_chinese()+"','"+rnb.getProfess_title_e()+"','"+
						rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+
						rnb.getCe_no()+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+
						rnb.getDirect_line()+"','"+rnb.getFax()+"','"+rnb.getBobile_number()+"','"+
						rnb.getQuantity()+"','"+rnb.getUpd_date()+"')";
				logger.info("插入STAFF历史数据SQL:"+sql);
				ps=con.prepareStatement(sql);
				s=ps.executeUpdate();
				if(s>0){
					logger.info("插入STAFF历史数据成功！");
					return s;
				}
				else{
					logger.info("插入STAFF历史数据失败！");
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("插入STAFF历史数据异常！"+e);
			}finally{
				DBManager.closeCon(con);
			}
			return s;
	}
	/**
	 *  TODO 保存Convoy request_staff的数据
	 */
	public int saveStaffRequestConvoy(RequestStaffConvoyBean rnb) {
		
		int s=-1;
		try{
			
			con=DBManager.getCon();
			sql="insert request_staff_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
				"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line," +
				"fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks," +
				"location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only," +
				"CFG_only,Blank_only,CCIA_only,CCFSH_only,CWMC_only,Payer,Subject,Remark,remarkcons,shzt) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("Request_Staff_Convoy信息保存方法SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, rnb.getStaff_code());
			ps.setString(2, rnb.getName());
			ps.setString(3, rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7,rnb.getExternal_chinese());
			ps.setString(8,rnb.getAcademic_title_e());
			ps.setString(9,rnb.getAcademic_title_c());
			ps.setString(10, rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12,rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14,rnb.getMpf_no());
			ps.setString(15,rnb.getE_mail());
			ps.setString(16,rnb.getDirect_line());
			ps.setString(17,rnb.getFax());
			ps.setString(18,rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20,rnb.getUpd_date());
			ps.setString(21, rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23,rnb.getLayout_type());
			ps.setString(24,rnb.getUrgent());
			ps.setString(25,rnb.getUrgentDate());
			ps.setString(26,rnb.getMarks());
			ps.setString(27, rnb.getLocation());
			ps.setString(28,"N");
			ps.setString(29, rnb.getEliteTeam());
			ps.setString(30,rnb.getCAM_only());
			ps.setString(31,rnb.getCFS_only());
			ps.setString(32,rnb.getCIS_only());
			ps.setString(33,rnb.getCCL_only());
			ps.setString(34,rnb.getCFSH_only());
			ps.setString(35,rnb.getCMS_only());
			ps.setString(36,rnb.getCFG_only());
			ps.setString(37,rnb.getBlank_only());
			ps.setString(38,rnb.getCCIA_only());
			ps.setString(39,rnb.getCCFSH_only());
			ps.setString(40,rnb.getCWMC_only());
			ps.setString(41,rnb.getPayer());
			ps.setString(42, rnb.getSubject());
			ps.setString(43, rnb.getRemark());
			ps.setString(44, rnb.getRemarkcons());
			ps.setString(45,"S");//待审核
			s=ps.executeUpdate();
			if(s>0){
				logger.info("插入request_staff_convoy成功！");
				return s;
			}
			else{
				logger.info("插入request_staff_convoy失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Request_Staff_Convoy信息保存异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	
	
	
public int saveDeptRequestConvoy(RequestStaffConvoyBean rnb) {
		
		int s=-1;
		try{
			
			con=DBManager.getCon();
			sql="insert request_staff_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese," +
				"academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line," +
				"fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate," +
				"marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only," +
				"CMS_only,CFG_only,Blank_only,CCIA_only,CCFSH_only,CWMC_only,Payer,Subject,Remark,remarkcons,shzt" +
				") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("Request_Staff_Convoy信息保存方法SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, rnb.getStaff_code());
			ps.setString(2, rnb.getName());
			ps.setString(3, rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7,rnb.getExternal_chinese());
			ps.setString(8,rnb.getAcademic_title_e());
			ps.setString(9,rnb.getAcademic_title_c());
			ps.setString(10, rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12,rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14,rnb.getMpf_no());
			ps.setString(15,rnb.getE_mail());
			ps.setString(16,rnb.getDirect_line());
			ps.setString(17,rnb.getFax());
			ps.setString(18,rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20,rnb.getUpd_date());
			ps.setString(21, rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23,rnb.getLayout_type());
			ps.setString(24,rnb.getUrgent());
			ps.setString(25,rnb.getUrgentDate());
			ps.setString(26,rnb.getMarks());
			ps.setString(27, rnb.getLocation());
			ps.setString(28,"N");
			ps.setString(29, rnb.getEliteTeam());
			ps.setString(30,rnb.getCAM_only());
			ps.setString(31,rnb.getCFS_only());
			ps.setString(32,rnb.getCIS_only());
			ps.setString(33,rnb.getCCL_only());
			ps.setString(34,rnb.getCFSH_only());
			ps.setString(35,rnb.getCMS_only());
			ps.setString(36,rnb.getCFG_only());
			ps.setString(37,rnb.getBlank_only());
			ps.setString(38,rnb.getCCIA_only());
			ps.setString(39,rnb.getCCFSH_only());
			ps.setString(40,rnb.getCWMC_only());
			ps.setString(41,rnb.getPayer());
			ps.setString(42, rnb.getSubject());
			ps.setString(43, rnb.getRemark());
			ps.setString(44, rnb.getRemarkcons());
			ps.setString(45,"E");//Dept已审核
			s=ps.executeUpdate();
			if(s>0){
				logger.info("插入request_staff_convoy成功！");
				return s;
			}else{
				logger.info("插入request_staff_convoy失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Request_Staff_Convoy信息保存异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	
	
	/**
	 *  TODO 保存Convoy request_staff的数据
	 */
	public int saveHRRequestConvoy(RequestStaffConvoyBean rnb) {
		
		int s=-1;
		try{
			
			con=DBManager.getCon();
			sql="insert request_staff_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese," +
				"academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line," +
				"fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate," +
				"marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only," +
				"CMS_only,CFG_only,Blank_only,CCIA_only,CCFSH_only,CWMC_only,Payer,Subject,Remark,remarkcons,shzt" +
				") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("Request_Staff_Convoy信息保存方法SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, rnb.getStaff_code());
			ps.setString(2, rnb.getName());
			ps.setString(3, rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7,rnb.getExternal_chinese());
			ps.setString(8,rnb.getAcademic_title_e());
			ps.setString(9,rnb.getAcademic_title_c());
			ps.setString(10, rnb.getProfess_title_e());
			ps.setString(11,rnb.getProfess_title_c());
			ps.setString(12,rnb.getTr_reg_no());
			ps.setString(13,rnb.getCe_no());
			ps.setString(14,rnb.getMpf_no());
			ps.setString(15,rnb.getE_mail());
			ps.setString(16,rnb.getDirect_line());
			ps.setString(17,rnb.getFax());
			ps.setString(18,rnb.getBobile_number());
			ps.setString(19,rnb.getQuantity());
			ps.setString(20,rnb.getUpd_date());
			ps.setString(21, rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23,rnb.getLayout_type());
			ps.setString(24,rnb.getUrgent());
			ps.setString(25,rnb.getUrgentDate());
			ps.setString(26,rnb.getMarks());
			ps.setString(27, rnb.getLocation());
			ps.setString(28,"N");
			ps.setString(29, rnb.getEliteTeam());
			ps.setString(30,rnb.getCAM_only());
			ps.setString(31,rnb.getCFS_only());
			ps.setString(32,rnb.getCIS_only());
			ps.setString(33,rnb.getCCL_only());
			ps.setString(34,rnb.getCFSH_only());
			ps.setString(35,rnb.getCMS_only());
			ps.setString(36,rnb.getCFG_only());
			ps.setString(37,rnb.getBlank_only());
			ps.setString(38,rnb.getCCIA_only());
			ps.setString(39,rnb.getCCFSH_only());
			ps.setString(40,rnb.getCWMC_only());
			ps.setString(41,rnb.getPayer());
			ps.setString(42, rnb.getSubject());
			ps.setString(43, rnb.getRemark());
			ps.setString(44, rnb.getRemarkcons());
			ps.setString(45,"R");//HR已审核
			s=ps.executeUpdate();
			if(s>0){
				logger.info("插入request_staff_convoy成功！");
				return s;
			}else{
				logger.info("插入request_staff_convoy失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Request_Staff_Convoy信息保存异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	/**
	 * Reject Request Staff Convoy
	 */
	public int rejectStaffRequestConvoy(String staffcode, String urgentDate,String adminUsername) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="update request_staff_convoy set shzt='N' where staff_code=? and UrgentDate=?";
			logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 时    ====SQL:"+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, urgentDate);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("用户 =="+adminUsername+"===在     Reject Request STAFF Convoy 成功！");
				return s;
			}
			else{
				logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("用户 =="+adminUsername+"===在    Reject Request Staff Convoy异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
	public int saveStaffConvoyMasterHistory(RequestStaffConvoyBean rnb) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="insert staff_master(staffNo,name,c_Name," +
						"e_Title_Department,c_Title_Department,e_ExternalTitle_Department," +
						"c_ExternalTitle_Department,e_EducationTitle," +
						"c_EducationTitle,tr_RegNo," +
						"ce_No,MPF_No,email," +
						"directLine,fax,mobilePhone,num,submit_date) values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+
					rnb.getName_chinese()+"','"+rnb.getTitle_english()+"','"+
					rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"+
					rnb.getExternal_chinese()+"','"+rnb.getProfess_title_e()+"','"+
					rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+
					rnb.getCe_no()+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+
					rnb.getDirect_line()+"','"+rnb.getFax()+"','"+rnb.getBobile_number()+"','"+
					rnb.getQuantity()+"','"+rnb.getUpd_date()+"')";
			logger.info("插入STAFF历史数据SQL:"+sql);
			ps=con.prepareStatement(sql);
			s=ps.executeUpdate();
			if(s>0){
				logger.info("插入STAFF历史数据成功！");
				return s;
			}
			else{
				logger.info("插入STAFF历史数据失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("插入STAFF历史数据异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return s;
	}
 
}
