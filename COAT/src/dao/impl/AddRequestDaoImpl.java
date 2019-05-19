package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import dao.AddRequestDao;
import entity.RequestNewBean;
/**
 * 保存AddRequestDaoImpl
 * @author King.XU
 *
 */
public class AddRequestDaoImpl implements AddRequestDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(AddRequestDaoImpl.class);
	/**
	 * saveNewRequest 保存
	 * RequestNewBean 对象
	 */
	public int  saveNewRequest(RequestNewBean rnb) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="insert request_new(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,Payer,remark1) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("saveNewRequest 保存 SQL:"+sql);
			ps=con.prepareStatement(sql);
			//ps=con.prepareStatement(sql);
			ps.setString(1,rnb.getStaff_code());
			ps.setString(2,rnb.getName());
			ps.setString(3,rnb.getName_chinese());
			ps.setString(4,rnb.getTitle_english());
			ps.setString(5,rnb.getTitle_chinese());
			ps.setString(6,rnb.getExternal_english());
			ps.setString(7, rnb.getExternal_chinese());
			ps.setString(8, rnb.getAcademic_title_e());
			ps.setString(9, rnb.getAcademic_title_c());
			ps.setString(10, rnb.getProfess_title_e());
			ps.setString(11, rnb.getProfess_title_c());
			ps.setString(12, rnb.getTr_reg_no());
			ps.setString(13, rnb.getCe_no());
			ps.setString(14, rnb.getMpf_no());
			ps.setString(15, rnb.getE_mail());
			ps.setString(16, rnb.getDirect_line());
			ps.setString(17, rnb.getFax());
			ps.setString(18, rnb.getBobile_number());
			ps.setString(19, rnb.getQuantity());
			ps.setString(20, rnb.getUpd_date());
			ps.setString(21, rnb.getUpd_name());
			ps.setString(22, rnb.getCard_type());
			ps.setString(23, rnb.getLayout_type());
			ps.setString(24, rnb.getUrgent());
			ps.setString(25, rnb.getUrgentDate());
			ps.setString(26, rnb.getMarks());
			ps.setString(27, rnb.getLocation());
			ps.setString(28, rnb.getAe_consultant());
			ps.setString(29, rnb.getEliteTeam());
			ps.setString(30, rnb.getCAM_only());
			ps.setString(31, rnb.getCFS_only());
			ps.setString(32, rnb.getCIS_only());
			ps.setString(33, rnb.getCCL_only());
			ps.setString(34, rnb.getCFSH_only());
			ps.setString(35, rnb.getCMS_only());
			ps.setString(36, rnb.getCFG_only());
			ps.setString(37, rnb.getBlank_only());
			ps.setString(38, rnb.getPayer());
			ps.setString(39, rnb.getRemark1());
			s=ps.executeUpdate();
			if(s>0){
				logger.info(Constant.Message.MESSAGE_SUCCESS);
				return s;
			}
			else{
				s=-1;
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
	public int saveMasterHistory(RequestNewBean rnb) {
			int s=-1;
			try{
				con=DBManager.getCon();
				sql="insert cn_master values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				logger.info("插入历史数据SQL："+sql);
				ps=con.prepareStatement(sql);
				ps.setString(1, rnb.getStaff_code());
				ps.setString(2, rnb.getName());
				ps.setString(3,rnb.getName_chinese());
				ps.setString(4, rnb.getTitle_english());
				ps.setString(5, rnb.getTitle_chinese());
				ps.setString(6, rnb.getExternal_english());
				ps.setString(7, rnb.getExternal_chinese());
				ps.setString(8, rnb.getProfess_title_e());
				ps.setString(9, rnb.getProfess_title_c());
				ps.setString(10, rnb.getTr_reg_no());
				ps.setString(11, rnb.getCe_no());
				ps.setString(12, rnb.getMpf_no());
				ps.setString(13, rnb.getE_mail());
				ps.setString(14, rnb.getDirect_line());
				ps.setString(15, rnb.getFax());
				ps.setString(16, rnb.getBobile_number());
				ps.setString(17, rnb.getQuantity());
				ps.setString(18, rnb.getUrgentDate());
				ps.setString(19, rnb.getRemark1());//HKCIB
				s=ps.executeUpdate();
				if(s>0){
					logger.info(Constant.Message.MESSAGE_SUCCESS);
					return s;
				}
				else{
					s=-1;
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
	 * // TODO 判断staff是否是DD
	 */
	public boolean findDDorTreeHead(String staffcode) {
		boolean isExit=false;
		try{
			con=DBManager.getCon();
			sql="select employeeId from cons_list where employeeId=? and(employeeId=DDTreeHead or grade='DD' or position='DD')";
		logger.info("验证staff是否是DD or DD Tree Head   sql:=="+sql);
			ps=con.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			isExit=true;
		}
		ps.close();
		rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在AddRequestDaoImpl.findDDorTreeHead 中出现异常："+e.toString());
			isExit=false;
		}finally{
			DBManager.closeCon(con);
		}
		return isExit;
	}

}
