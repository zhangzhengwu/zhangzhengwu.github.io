package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import dao.MotifyDao;
import entity.QueryAdditional;
import entity.RequestNewBean;
/**
 * MotifyDao 实现类
 * @author King.XU
 * 
 */
public class MotifyDaoImpl implements MotifyDao {
	Connection con = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(MotifyDaoImpl.class);
	/**
	 * 查询Additional
	 */
	public QueryAdditional getAdditional(String StaffNo, String remark) {
		QueryAdditional qa = new QueryAdditional();
		String sql = "";
		try {
			con = DBManager.getCon();
			sql = "select * from nq_additional where initials='" + StaffNo
					+ "' and remarks='" + remark + "'";
			logger.info("查询Additional SQL:"+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				qa.setStaffNo(rs.getString(1));
				qa.setName(rs.getString(2));
				qa.setNum(rs.getString(3));
				qa.setAdditional(rs.getString(3));
				qa.setRemark(rs.getString(4));
				qa.setAdd_name(rs.getString(5));
				qa.setAdd_date(rs.getString(6));
				qa.setUpd_date(rs.getString(7));
				qa.setUpd_name(rs.getString(8));
				qa.setSfyx(rs.getString(9));
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询Additional异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return qa;
	}
	
	/**
	 * saveNewRequest 保存
	 * RequestNewBean 对象
	 */
	public int  saveNewRequest(RequestNewBean rnb,String reStaffNo) {
		int s=-1;
		String sql="";
		try{
			
			con=DBManager.getCon();
			sql="insert request_new(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,Payer) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			logger.info("saveNewRequest 保存 SQL:"+sql);
			ps=con.prepareStatement(sql);
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
	 * 修改 Additional
	 */
	public int updateAdditional(String StaffNo, String Additional,
			String Remarks, String sfyx, String re) {
		String sql = "";
		int r = -1;
		try {
			con = DBManager.getCon();
			sql = "update nq_additional set additional='" + Additional
					+ "',remarks='" + Remarks + "',sfyx='" + sfyx
					+ "',upd_user='admin',upd_date=now() where initials='"
					+ StaffNo + "' and remarks='" + re + "'";
			logger.info("修改 Additional SQL"+sql);
			ps = con.prepareStatement(sql);
			r = ps.executeUpdate();
			if (r > 0) {
				logger.info("修改 Additional成功！");
			} else {
				logger.info("修改 Additional失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改 Additional异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;

	}
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateAdditionals(RequestNewBean rnb) {
		String sql="";
		int r=-1;
		try{
			con=DBManager.getCon();
			/*sql="update request_new set  name='"+rnb.getName()+"' , name_chinese='"+rnb.getName_chinese()+"', title_english='"+rnb.getTitle_english()+"', title_chinese='"+rnb.getTitle_chinese()+"', external_english='"+rnb.getExternal_english()+"', external_chinese='"+rnb.getExternal_chinese()+"', academic_title_e='"+rnb.getAcademic_title_e()+"', academic_title_c='"+rnb.getAcademic_title_c()+"', profess_title_e='"+rnb.getProfess_title_e()+"', profess_title_c='"+rnb.getProfess_title_c()+"', tr_reg_no='"+rnb.getTr_reg_no()+"', ce_no='"+rnb.getCe_no()+"', mpf_no='"+rnb.getMpf_no()+"', e_mail='"+rnb.getE_mail()+"', direct_line='"+rnb.getDirect_line()+"', fax='"+rnb.getFax()+"',  bobile_number='"+rnb.getBobile_number()+"', upd_date='"+rnb.getUpd_date()+"', upd_name='"+rnb.getUpd_name()+"' ,location='"+rnb.getLocation()+"',CAM_only='"+rnb.getCAM_only()+"',CFS_only='"+rnb.getCFS_only()+"',CIS_only='"+rnb.getCIS_only()+"',CCL_only='"+rnb.getCCL_only()+"',CFSH_only='"+rnb.getCFSH_only()+"',CMS_only='"+rnb.getCMS_only()+"',CFG_only='"+rnb.getCFG_only()+"',Blank_only='"+rnb.getBlank_only()+"' where UrgentDate='"+rnb.getUrgentDate()+"' and Staff_code='"+rnb.getStaff_code()+"'";*/
			
				sql="update request_new set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			
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
	/***
	 * 更新requestNew 并更新Quantity 管理员权限
	 * @param rnb
	 * @return
	 */
	public int updateNumber(RequestNewBean rnb,String reStaffNo) {
		String sql="";
		int r=-1;
		try{
			con=DBManager.getCon();
			if(rnb.getStaff_code().equals(reStaffNo)){//是否修改了code
				sql="update request_new set card_type='U'  where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			}else{
				 sql="update request_new set  card_type='"+rnb.getStaff_code()+"'  where Staff_code='"+reStaffNo+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			
			/*sql="update request_new set Payer='"+rnb.getPayer()+"',Staff_code='"+rnb.getStaff_code()+"',urgent='"+rnb.getUrgent()+"',layout_type='"+rnb.getLayout_type()+"',name='"+rnb.getName()+"',name_chinese='"+rnb.getName_chinese()+"',title_english='"+rnb.getTitle_english()+"',title_chinese='"+rnb.getTitle_chinese()+"',external_english='"+rnb.getExternal_english()+"',external_chinese='"+rnb.getExternal_chinese()+"',academic_title_e='"+rnb.getAcademic_title_e()+"',academic_title_c='"+rnb.getAcademic_title_c()+"',profess_title_e='"+rnb.getProfess_title_e()+"',profess_title_c='"+rnb.getProfess_title_c()+"',tr_reg_no='"+rnb.getTr_reg_no()+"',ce_no='"+rnb.getCe_no()+"',mpf_no='"+rnb.getMpf_no()+"',e_mail='"+rnb.getE_mail()+"',direct_line='"+rnb.getDirect_line()+"',fax='"+rnb.getFax()+"',bobile_number='"+rnb.getBobile_number()+"',upd_date='"+rnb.getUpd_date()+"',upd_name='"+rnb.getUpd_name()+"',quantity='"+rnb.getQuantity()+"',location='"+rnb.getLocation()+"',CAM_only='"+rnb.getCAM_only()+"',CFS_only='"+rnb.getCFS_only()+"',CIS_only='"+rnb.getCIS_only()+"',CCL_only='"+rnb.getCCL_only()+"',CFSH_only='"+rnb.getCFSH_only()+"',CMS_only='"+rnb.getCMS_only()+"',CFG_only='"+rnb.getCFG_only()+"',Blank_only='"+rnb.getBlank_only()+"' where UrgentDate='"+rnb.getUrgentDate()+"' and Staff_code='"+reStaffNo+"'";*/
			}
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
 * 过滤重复数据
 */
	public int selectRepeat(String staffcode, String remark) {
		 String sql="";
		 int num=-1;
		 try{
			 con=DBManager.getCon();
			 sql="select count(*) as num from nq_additional where initials=? and remarks=? ";
					ps=con.prepareStatement(sql);
					ps.setString(1, staffcode);
					ps.setString(2, remark);
					ResultSet rs=ps.executeQuery();
					if(rs.next()){
					num=rs.getInt(1);
					}
					rs.close();
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.error("判断数据是否重复时出现异常："+e);
		 }finally{
			 DBManager.closeCon(con);
		 }
		 
		return num;
	}
}
