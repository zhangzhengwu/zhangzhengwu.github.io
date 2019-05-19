package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.QueryCardConvoyDao;
import dao.common.BaseDao;
import entity.Consultant_Master;
import entity.RequestConvoyBean;
/**
 * 查询request convoy cons 所需的数据
 * @author Wilson
 * 2012年9月27日10:11:38
 */
public class QueryConvoyCardDaoImpl  extends BaseDao  implements QueryCardConvoyDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(QueryConvoyCardDaoImpl.class);
	
	/**queryRequstList
	 * saveNewRequest 保存
	 * RequestNewBean 对象
	 */
	public int  saveNewConvoy(RequestConvoyBean rnb,String reStaffNo) {
		int s=-1;
		String sql="";
		try{
			con=DBManager.getCon();
			sql="insert request_new_convoy values('"+reStaffNo+"','"+rnb.getName()+"','"+rnb.getName_chinese()
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
	
	public int  getRows(String name,String code,String startDate,String endDate,String location,String urgentCase,String shzt) {
		int num=-1;
		try {
			con= DBManager.getCon();
			StringBuffer sqlString=new StringBuffer("  select  count(*) from  (" +
					"SELECT  staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
					" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
					" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_Consultant,eliteTeam,location,shzt,remark,urgentDate as add_date,remarkcons "+
			" FROM request_new_convoy c where quantity>0 and card_type='N'  and shzt!='Y' "+
            " UNION "+
            " select staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
					" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
					" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_Consultant,eliteTeam,location,'Y' as shzt,'',urgentDate as add_date,''  from request_new where quantity>0 and card_type='N'"+
                    " ) c where  payer like '%"+code+"%' ");
			if(!Util.objIsNULL(startDate)){
				sqlString.append(" and date_format(add_date,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(add_date,'%Y-%m-%d') <='"+endDate+"' ");
			}
			 
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent like '%"+urgentCase+"%' ");
			}
			if(!Util.objIsNULL(shzt)){
				sqlString.append(" and shzt ='"+shzt+"' ");
			}
			sqlString.append(" order by UrgentDate desc ");
			logger.info("QUERY NAME CARD SQL:"+sqlString.toString());
			ps = con.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString(1));
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
		return num;
	}
	public List<RequestConvoyBean> queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase,String shzt,Page page) {
		
		List<RequestConvoyBean> list = new ArrayList<RequestConvoyBean>();
		try {
			con= DBManager.getCon();
			/**StringBuffer sqlString = new StringBuffer("SELECT staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
					"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer," +
					"CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,ae_Consultant,eliteTeam,location,shzt,remark,add_date,remarkcons ");
				sqlString.append("FROM request_new_convoy where quantity>0 and card_type='N' ");
				**/
			StringBuffer sqlString=new StringBuffer("  select  c.* from  (SELECT  staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
					" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
					" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_Consultant,eliteTeam,location,shzt,remark,urgentDate as add_date,remarkcons "+
			" FROM request_new_convoy c where quantity>0 and card_type='N'  and shzt!='Y' "+
            " UNION "+
            " select staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
					" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
					" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_Consultant,eliteTeam,location,'Y' as shzt,'',urgentDate as add_date,''  from request_new where quantity>0 and card_type='N'"+
                    " ) c where  payer like '%"+code+"%' ");
				
				
				
				if(!Util.objIsNULL(startDate)){
					sqlString.append(" and date_format(add_date,'%Y-%m-%d') >='"+startDate+"' ");
				}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(add_date,'%Y-%m-%d') <='"+endDate+"' ");
			}
		 
			if(!Util.objIsNULL(name)){
				sqlString.append(" and name like '%"+name+"%' ");
			}
			if(!Util.objIsNULL(location)){
				sqlString.append(" and location='"+location+"' ");
			}
			if(!Util.objIsNULL(urgentCase)){
				sqlString.append(" and urgent like '%"+urgentCase+"%' ");
			}
			if(!Util.objIsNULL(shzt)){
				sqlString.append(" and shzt ='"+shzt+"' ");
			}
			sqlString.append(" order by UrgentDate desc ");
			sqlString.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+" ");
			logger.info("QUERY NAME CARD SQL:"+sqlString.toString());
			ps = con.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RequestConvoyBean rsBean = new RequestConvoyBean();
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
				rsBean.setShzt(rs.getString(36));
				rsBean.setRemark(rs.getString(37));
				rsBean.setAdd_date(rs.getString(38));
				rsBean.setRemarkcons(rs.getString(39));
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
	
	public List<Map<String,Object>> queryRequst(String name,String code,String startDate,String endDate,String location,String urgentCase,String shzt) throws SQLException {
		//ResultSet rs=null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			//con = DBManager.getCon();
			//StringBuffer sqlString = new StringBuffer("SELECT case location when 'O' THEN '"+Constant.OIE+"' when 'C' THEN '"+Constant.CP3+"' when 'W' THEN '"+Constant.CWC+"' when 'M' THEN '"+Constant.Macau+"' when 'Y' then '"+Constant.Convoy+"' END as location, ");
			StringBuffer sqlString = new StringBuffer("SELECT location, ");
				sqlString.append(" company as type,");
				sqlString.append(" layout_type,staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e, ");
				sqlString.append(" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,remark1,e_mail,direct_line,fax,bobile_number,quantity,shzt  ");
				sqlString.append("	 FROM request_new_convoy where quantity>0 and card_type='N' ");
				if(!Util.objIsNULL(startDate)){
					sqlString.append(" and date_format(add_date,'%Y-%m-%d')>='"+startDate+"' ");
				}
			if(!Util.objIsNULL(endDate)){
				sqlString.append(" and date_format(add_date,'%Y-%m-%d')<='"+endDate+"' ");
			}
			if(!Util.objIsNULL(code)){
				sqlString.append(" and payer like '%"+code+"%' ");
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
			if(!Util.objIsNULL(shzt)){
				sqlString.append(" and shzt ='"+shzt+"' ");
			}
			logger.info("UPLOAD NAME CARD SQL:"+sqlString.toString());
			/*ps = con.prepareStatement(sqlString.toString());
			rs = ps.executeQuery();
			if(rs!=null){
				return rs;	
			}*/
			list=findListMap(sqlString.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QUERY NAME CARD ERROR!"+e);
		}finally{
			super.closeConnection();
		}
		return list;
	}
	/***
	 * 插入request_Macau
	 */
	public int  saveNewRequest(RequestConvoyBean rnb) {
		int s=-1;
		try{
			con=DBManager.getCon();
			sql="insert request_new_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,Payer,shzt,add_date,add_name,upd_date,upd_name,remark,remarkcons) values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()
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
	
	/**
	 * 插入历史数据Mater
	 */
	public int saveMasterHistory(RequestConvoyBean rnb) {
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
			}
			else{
				cm= null;
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
	public int updateAdditionals(RequestConvoyBean rnb) {
		String sql="";
		int r=-1;
		try{
			con=DBManager.getCon();
			
				sql="update request_new_convoy set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			
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
	 * 1，新增—— N
	 * 2，随机code 改为新code  ——staff code
	 * 3，没有修改code的情况，置为——U
	 * @param rnb
	 * @return
	 */
	public int updateNumber(RequestConvoyBean rnb,String reStaffNo) {
		String sql="";
		int r=-1;
		try{
			con=DBManager.getCon();
			if(rnb.getStaff_code().equals(reStaffNo)){
				sql="update request_new_convoy set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			}else{
				sql="update request_new_convoy set card_type='"+rnb.getStaff_code()+"' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+reStaffNo+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
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
	 * saveNewMacau 保存
	 * NewMacau 对象
	 */
	public int  saveConvoyNew(RequestConvoyBean rnb,String reStaffNo) {
		int s=-1;
		String sql="";
		try{
			con=DBManager.getCon();
			sql="insert request_Macau values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()
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
	public int updateRequestRecord(RequestConvoyBean rnb,String Payer, String rePayer) {
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
	public int updateMasterNumber(RequestConvoyBean rnb, String reStaffNo ) {
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
			
			logger.info("刪除財務表數據 SQL:"+sql.toString());
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
		return 1;
	}

	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
