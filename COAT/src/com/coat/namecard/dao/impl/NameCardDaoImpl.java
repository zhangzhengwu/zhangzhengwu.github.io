package com.coat.namecard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.Pager;
import util.Util;

import com.coat.namecard.dao.NameCardDao;
import com.coat.namecard.entity.Consultant_Master;
import com.coat.namecard.entity.NameCardConvoy;
import com.coat.namecard.entity.NameCardPayer;
import com.coat.namecard.entity.RequestNewBean;

import dao.common.BaseDao;
import entity.LicensePlate;
import entity.RequestStaffBean;

public class NameCardDaoImpl extends BaseDao implements NameCardDao {
	Logger logger=Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, NameCardConvoy.class);
	}
	
	
	/**
	 * 查询名片申请记录
	 * @author kingxu
	 * @date 2015-9-28
	 * @param fields
	 * @param page
	 * @param ET
	 * @param nocode
	 * @param objects
	 * @return
	 * @throws Exception
	 * @return Pager
	 */
	public Pager findPager(String[] fields, Pager page,String ET, String nocode,Object... objects) throws Exception{
		String sql=" FROM request_new " +
				" where quantity>0 and  card_type='N' " +
				" and  date_format(UrgentDate,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(UrgentDate,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and staff_code like ? " +
				" and payer like ?" +
				" and name like ?" +
				" and location like ?" +
				" and urgent like ?" +
				" and layout_type like ? ";
		if(!Util.objIsNULL(ET)){
			if(ET.equalsIgnoreCase("Y")){
				sql+=" and eliteTeam ='Y' ";
			}else{
				sql+=" and eliteTeam !='Y'";
			}
		}
		if(!Util.objIsNULL(nocode)){
			if(nocode.equals("Y")){
				sql+=" and length(staff_code) >=10 ";
			}
		}
		String limit="order by UrgentDate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	public Consultant_Master findbystaffcode(String staffcode) throws Exception{
		Consultant_Master cn_master=new Consultant_Master();
		try{
			String sql= "select * from (select a.EmployeeId as StaffNo,if(Alias ='',EmployeeName,CONCAT(a.EmployeeName, CONCAT(' ',a.Alias))) as englishname,a.C_Name,a.E_PositionName,'',"+
					" E_ExternalTitle_Department,C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,"+
					" b.TrRegNo,b.Ce_No,b.MPF,a.Email,a.DirectLine,c.fax as fax,c.Mobilephone,c.Num as num ,E_Title_Department,C_Title_Department,a.Mobile, CASE WHEN DATEDIFF(NOW(),CASE when c.submit_date ='' or submit_date is null then '1911-01-01' else c.submit_date end) > 7 THEN 'N' ELSE 'Y' END AS sfts "+
					" ,a.Grade,a.RecruiterId from cons_list a left join  tr b  on a.EmployeeId =b.StaffNo  left join "+
					" (select * from cn_master  where staffNo = ? and Num>0 order by submit_date desc limit 0,1) c on a.EmployeeId = c.staffno"+
					" where a.EmployeeId = ? limit 0,1)x";
			Map<String,Object> map=findMap(sql, staffcode,staffcode);
			sql="SELECT position_ename FROM position_list WHERE SFYX='Y'";
			List<Object[]> list=findDate(sql);
			if(map.size()>0){
				cn_master.setStaffNo(map.get("StaffNo")+"");
				cn_master.setEnglishName(map.get("englishname")+"");
				cn_master.setName(map.get("C_Name")+"");
				cn_master.setLastPosition_E(map.get("E_Title_Department")+"");
				cn_master.setLastPosition_C(map.get("C_Title_Department")+"");
				cn_master.setE_DepartmentTitle(map.get("E_PositionName")+"");
				cn_master.setC_DepartmentTitle("");
				cn_master.setE_ExternalTitle(map.get("E_ExternalTitle_Department")+"");
				cn_master.setC_ExternalTitle(map.get("E_ExternalTitle_Department")+"");
				cn_master.setE_EducationTitle(map.get("E_EducationTitle")+"");
				cn_master.setC_EducationTitle(map.get("C_EducationTitle")+"");
				cn_master.setTR_RegNo(map.get("TrRegNo")+"");
				cn_master.setCENo(map.get("Ce_No")+"");
				cn_master.setMPFNo(map.get("MPF")+"");
				cn_master.setEmail(map.get("Email")+"");
				cn_master.setDirectLine(map.get("DirectLine")+"");
				cn_master.setFax(map.get("fax")+"");
				cn_master.setMobilePhone(Util.objIsNULL(map.get("Mobilephone")+"")?(map.get("Mobile")+""):(map.get("Mobilephone")+""));
				cn_master.setNum(map.get("num")+"");
				String positionName="";
				String tempPosition=map.get("E_PositionName")+"";
				 if (!Util.objIsNULL(tempPosition)) {
						for (int i = 0; i < list.size(); i++) {
							String liststr = (String) list.get(i)[0];
							if (tempPosition.length() >= liststr.length()) {
								if(tempPosition.substring(0, liststr.length()).equals(liststr)){
									positionName = liststr;
									 break; //有一个匹配 则跳出循环
								}
							}
						}
					}
				
				cn_master.setE_DepartmentTitle(positionName);
				cn_master.setC_DepartmentTitle("");
				cn_master.setSfcf(map.get("sfts")+"");
				cn_master.setGrade(map.get("Grade")+"");
				cn_master.setRecruiterId(map.get("RecruiterId")+"");
			}
		}finally{
			super.closeConnection();
		}
		return cn_master;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 保存顾问提交的名片申请
	 * @author kingxu
	 * @date 2015-9-15
	 * @param rnb
	 * @return
	 * @return String
	 * @throws SQLException 
	 */
	public String  saveNewRequest(NameCardConvoy rnb,String username) throws SQLException {
		String result="";
		int s=-1;
		try{
			openTransaction();
			String sql="insert request_new_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,CIB_only,Payer,shzt,add_date,add_name,upd_date,upd_name,remark,remarkcons,company,remark1) values('"+rnb.getStaff_code()
					+"','"+rnb.getName()
					+"','"+rnb.getName_chinese()
					+"','"+rnb.getTitle_english()
					+"','"+rnb.getTitle_chinese()
					+"','"+(Util.objIsNULL(rnb.getExternal_english())?"":rnb.getExternal_english())
					+"','"+(Util.objIsNULL(rnb.getExternal_chinese())?"":rnb.getExternal_chinese())
					+"','"+(Util.objIsNULL(rnb.getAcademic_title_e())?"":rnb.getAcademic_title_e())
					+"','"+(Util.objIsNULL(rnb.getAcademic_title_c())?"":rnb.getAcademic_title_c())
					+"','"+(Util.objIsNULL(rnb.getProfess_title_e())?"":rnb.getProfess_title_e())
					+"','"+(Util.objIsNULL(rnb.getProfess_title_c())?"":rnb.getProfess_title_c())
					+"','"+rnb.getTr_reg_no()
					+"','"+rnb.getCe_no()
					+"','"+rnb.getMpf_no()
					+"','"+rnb.getE_mail()
					+"','"+rnb.getDirect_line()
					+"','"+rnb.getFax()
					+"','"+rnb.getBobile_number()
					+"','"+rnb.getQuantity()
					+"','"+rnb.getCard_type()
					+"','"+rnb.getLayout_type()
					+"','"+rnb.getUrgent()
					+"','"+rnb.getUrgentDate()
					+"','"+rnb.getMarks()
					+"','"+rnb.getLocation()
					+"','"+rnb.getAe_consultant()
					+"','"+rnb.getEliteTeam()
					+"','"+rnb.getCAM_only()
					+"','"+rnb.getCFS_only()
					+"','"+rnb.getCIS_only()
					+"','"+rnb.getCCL_only()
					+"','"+rnb.getCFSH_only()
					+"','"+rnb.getCMS_only()
					+"','"+rnb.getCFG_only()
					+"','"+rnb.getBlank_only()
					+"','"+rnb.getCIB_only()
					+"','"+rnb.getPayer()
					+"','"+rnb.getShzt()
					+"','"+rnb.getAdd_date()
					+"','"+username
					+"','"+DateUtils.getNowDateTime()
					+"','"+rnb.getUpd_name()
					+"','"+rnb.getRemark()
					+"','"+rnb.getRemarkcons()
					+"','"+rnb.getCompany()
					+"','"+rnb.getRemark1()+"')";//HKCIB
			logger.info(username+"---saveNewRequest convoy 保存 SQL:"+sql);
			s=saveEntity(sql);
			String refno=s+"";
			sql="insert into request_new_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+s+"','"+rnb.getUrgentDate()+"','Submit request','"+username+"','"+DateUtils.getNowDateTime()+"');";
			s=update(sql, null);
			if(s<1){
					logger.info(username+"保存NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
					throw new RuntimeException("保存NameCardRequest[request_new_convoy_operation]失败");
			}
			/***********************************************保存提交状态到request_new_convoy_detial START***********************************************************/
			sql="insert into request_new_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark) " +
			"values('"+username+"','"+refno+"','"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()+"','Submitted','"+username+"','"+DateUtils.getNowDateTime()+"','');";
			s=update(sql, null);
			if(s<1){
				logger.info(username+"保存NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("保存NameCardRequest[request_new_convoy_detial]失败");
			}
			/***********************************************保存提交状态到request_new_convoy_detial END***********************************************************/
			result=Util.returnValue(s);
			if(s>0){
				logger.info(username+"-- ---"+Constant.Message.MESSAGE_SUCCESS);
			}else{
				logger.info(username+"---"+Constant.Message.MESSAGE_FAILD);
			}
			sumbitTransaction();
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error(username+"---request_new_convoy "+Constant.Message.MESSAGE_FAILD+e);
		}finally{
			closeConnection();
		}
		return result;
	}
	
	
	
	
	/**
	 * SZOADM保存NameCard申请
	 * @author kingxu
	 * @date 2015-9-24
	 * @param rnb
	 * @param username
	 * @param used
	 * @param sum
	 * @return
	 * @throws SQLException
	 * @return String
	 */
	public String saveNameCardRequest(RequestNewBean rnb, String username,double used,double sum) throws SQLException {
		String result="";
		try{
			openTransaction();//开启事务
			String sql="insert request_new" +
					"(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c," +
					"profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,upd_date,upd_name," +
					"card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only," +
					"CMS_only,CFG_only,Blank_only,Payer,addname,adddate,company,remark1) " +
					"values('"+rnb.getStaff_code()
							+"','"+rnb.getName()
							+"','"+rnb.getName_chinese()
							+"','"+rnb.getTitle_english()
							+"','"+rnb.getTitle_chinese()
							+"','"+rnb.getExternal_english()
							+"','"+ rnb.getExternal_chinese()
							+"','"+ rnb.getAcademic_title_e()
							+"','"+ rnb.getAcademic_title_c()
							+"','"+ rnb.getProfess_title_e()
							+"','"+ rnb.getProfess_title_c()
							+"','"+ rnb.getTr_reg_no()
							+"','"+ rnb.getCe_no()
							+"','"+ rnb.getMpf_no()
							+"','"+ rnb.getE_mail()
							+"','"+ rnb.getDirect_line()
							+"','"+ rnb.getFax()
							+"','"+ rnb.getBobile_number()
							+"','"+ rnb.getQuantity()
							+"','"+ rnb.getUpd_date()
							+"','"+ rnb.getUpd_name()
							+"','"+ rnb.getCard_type()
							+"','"+ rnb.getLayout_type()
							+"','"+ rnb.getUrgent()
							+"','"+ rnb.getUrgentDate()
							+"','"+ rnb.getMarks()
							+"','"+ rnb.getLocation()
							+"','"+ rnb.getAe_consultant()
							+"','"+ rnb.getEliteTeam()
							+"','"+ rnb.getCAM_only()
							+"','"+ rnb.getCFS_only()
							+"','"+ rnb.getCIS_only()
							+"','"+ rnb.getCCL_only()
							+"','"+ rnb.getCFSH_only()
							+"','"+ rnb.getCMS_only()
							+"','"+ rnb.getCFG_only()
							+"','"+ rnb.getBlank_only()
							+"','"+ rnb.getPayer()
							+"','"+ rnb.getAddname()
							+"','"+ rnb.getAdddate()
							+"','"+ rnb.getCompany()
							+"','"+ rnb.getRemark1()//HKCIB
							+"')";
			logger.info(username+"保存NameCardrequest  SQL:"+sql);
			 int s=saveEntity(sql);
			 if(s<1){
				 logger.info(username+"保存NameCardRequest"+Constant.Message.MESSAGE_FAILD);
				 throw new RuntimeException("保存NameCardRequest[request_new]失败");
			 }
				sql="insert into request_new_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+s+"_pro','"+rnb.getUrgentDate()+"','Approval_SZOADM','"+username+"','"+DateUtils.getNowDateTime()+"');";
				s=update(sql, null);
				if(s<1){
						logger.info(username+"保存NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
						throw new RuntimeException("保存NameCardRequest[request_new_convoy_operation]失败");
				}
			if(s>0){
				logger.info(username+"保存NameCardrequest"+Constant.Message.MESSAGE_SUCCESS);
				sql="insert cn_master(staffNo,Name,C_Name,E_Title_Department,C_Title_Department,E_ExternalTitle_Department,C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,TR_RegNo,CE_No,MPF_No,Email,DirectLine,Fax,MobilePhone,Num,HKCIB_NO,submit_date)" +
						" values('"+ rnb.getStaff_code()
							+"','"+ rnb.getName()
							+"','"+rnb.getName_chinese()
							+"','"+ rnb.getTitle_english()
							+"','"+ rnb.getTitle_chinese()
							+"','"+ rnb.getExternal_english()
							+"','"+ rnb.getExternal_chinese()
							+"','"+ rnb.getProfess_title_e()
							+"','"+ rnb.getProfess_title_c()
							+"','"+ rnb.getTr_reg_no()
							+"','"+ rnb.getCe_no()
							+"','"+ rnb.getMpf_no()
							+"','"+ rnb.getE_mail()
							+"','"+ rnb.getDirect_line()
							+"','"+ rnb.getFax()
							+"','"+ rnb.getBobile_number()
							+"','"+ rnb.getQuantity()
							+"','"+ rnb.getRemark1()
							+"','"+ rnb.getUrgentDate()+"');";
				s=update(sql,null);
				if(s>0){
				
					if(rnb.getStaff_code().length()<10){
						sql = "insert req_record(request_date,code,name,departmen,quantity,upd_name,Layout_Type,Urgent)" +
								" values('"+rnb.getUrgentDate()
								+"','" + rnb.getPayer() 
								+ "','" + rnb.getName()
								+"','CD','"+ rnb.getQuantity()
								+"','" + username
								+"','"+ rnb.getLayout_type() 
								+"','"+rnb.getUrgent()+"')";
						s=update(sql, null);
					}
					if(s>0){
						
						NameCardPayer cr=null;
						/**************************需要自己支付***************************************/
						if(!rnb.getStaff_code().equalsIgnoreCase(rnb.getPayer()) || rnb.getUrgent().toUpperCase().equals("Y")){//当申请人与支付人不相等时需要额外支付
							double price=0;
							if(rnb.getUrgent().toUpperCase().equals("Y")){//加快
								if(rnb.getLayout_type().toUpperCase().trim().equals("P") && !rnb.getEliteTeam().toUpperCase().equals("Y")){//名片为自定义类型
									price=Constant.Premium_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
									price=Constant.Standard_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("P") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
									price=Constant.Premium_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && !rnb.getEliteTeam().toUpperCase().equals("Y")){
									price=Constant.Standard_Urgent*Integer.parseInt(rnb.getQuantity());//普通加快的价格
								}
							}else{//为他人支付
								price=Constant.Standard*Integer.parseInt(rnb.getQuantity());//普通的价格
							}
							cr=new NameCardPayer();
							cr.setStaffCode(rnb.getStaff_code());
							cr.setName(rnb.getName());
							cr.setNumber(rnb.getQuantity());
							cr.setAmount(price+"");
							cr.setPayer(rnb.getPayer());
							cr.setRemarks(rnb.getMarks());
							cr.setAddDate(rnb.getUrgentDate());
							cr.setUp_date(DateUtils.getNowDateTime());
							cr.setUp_name(username);
						}else if(rnb.getEliteTeam().equalsIgnoreCase("Y")){//选择Elite Team  （100张免费限额）
							sql="select staff_code from request_new " +
									"where card_type='N' and quantity>0 and staff_code='"+rnb.getStaff_code()+"' " +
									"and eliteTeam='Y' and urgent='N' " +
									"and date_format(UrgentDate,'%Y')=date_format(now(),'%Y')"; 
							Map<String,Object> map=findMap(sql);
							if(!Util.objIsNULL(map)){
								cr=new NameCardPayer();
								cr.setStaffCode(rnb.getStaff_code());
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
									cr.setAmount((Constant.Premium_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
								}else{
									cr.setAmount((Constant.Standard_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
								}
								cr.setPayer(rnb.getPayer());
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(username);
							
							}else{
								if(Integer.parseInt(rnb.getQuantity())>Constant.Elite_Team){//超过免费限额数量，进入财务
									cr=new NameCardPayer();
									cr.setStaffCode(rnb.getStaff_code());
									cr.setName(rnb.getName());
									cr.setNumber((Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team)+"");
									if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
										cr.setAmount((Constant.Premium_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
									}else{
										cr.setAmount((Constant.Standard_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
									}
									cr.setPayer(rnb.getPayer());
									cr.setRemarks(rnb.getMarks());
									cr.setAddDate(rnb.getUrgentDate());
									cr.setUp_date(DateUtils.getNowDateTime());
									cr.setUp_name(username);
									
								}else{
									//不做处理
								}
							}
							
						}else{//普通申请，判断是否超过免费办理限额
							double price=Constant.Standard;
							if(Integer.parseInt(rnb.getQuantity())+used>sum){//当次印刷名片数量与已印名片数量大于名片限额
								if(used>=sum){//已印名片数量大于名片限额
									price=price*Double.parseDouble(rnb.getQuantity());
								}else{
									rnb.setQuantity((Integer.parseInt(rnb.getQuantity())+used-sum)+"");
									price=price*Double.parseDouble(rnb.getQuantity());
								}
								if(rnb.getMarks().trim().equals("")){
									rnb.setMarks("Standard Layout");
								}
								cr=new NameCardPayer();
								cr.setStaffCode(rnb.getStaff_code());
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								cr.setAmount(price+"");
								cr.setPayer(rnb.getPayer());
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(username);
							}
						}
						if(!Util.objIsNULL(cr)){
							String sql2= "insert change_record(StaffCode,Name,Number,Amount,Payer,Remarks,Infomed_to_FAD,charged_Month,AddDate,up_date,up_name,sfyx)" +
									" values('"+cr.getStaffCode()
									+"','"+cr.getName()
									+"','"+cr.getNumber()
									+"','"+cr.getAmount()
									+"','"+cr.getPayer()
									+"','"+cr.getRemarks()
									+"','','','"+cr.getAddDate()
									+"','"+cr.getUp_date()
									+"','"+cr.getUp_name()+"','Y')";
							logger.info("保存财务信息表SQL- staffcode:"+cr.getStaffCode()+"----"+sql2);
							s=update(sql2, null);
							if(s>0){
								result=Util.getMsgJosnObject_success();
							}else{
								logger.info("保存NameCard财务信息表信息失败");
								throw new RuntimeException("保存NameCardRequest[charge_record]失败");
							}
						}else{
							result=Util.getMsgJosnObject_success();
						}
						
					}else{
						logger.info(username+"保存NameCard办理记录"+Constant.Message.MESSAGE_FAILD);
						throw new RuntimeException("保存NameCardRequest[req_record]失败");
					}
				}else{
					logger.info(username+"保存NameCard历史记录"+Constant.Message.MESSAGE_FAILD);
					throw new RuntimeException("保存NameCardRequest[cn_master]失败");
				}
			}else{
				logger.info(username+"保存NameCardrequest"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("保存NameCardRequest[request_new]失败");
			}
			sumbitTransaction();//提交事务
		}catch(Exception e){
			rollbackTransaction();//回滚数据
			logger.error("保存NameCardRequest 异常："+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			closeConnection();
		}
		return result;
	}
	public String approveNameCardRequest(RequestNewBean rnb, String username,double used,double sum) throws SQLException {
		String result="";
		try{
			openTransaction();//开启事务
			//String sql="update  request_new_convoy set shzt='Y' , upd_name='"+username+"', upd_date='"+DateUtils.getNowDateTime()+"', remark='"+rnb.getRemark()+"' where staff_code ='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			/**
			 * 2016-02-15 15:24:35 king 修改，避免remark中包含单引号
			 */
			String sql="update  request_new_convoy set shzt='Y' , upd_name=?, upd_date=?, remark=? where staff_code =? and UrgentDate=?";
			logger.info(username+"审核NameCardrequest  SQL:"+sql);
			int s=update2(sql, username,DateUtils.getNowDateTime(),rnb.getRemark(),rnb.getStaff_code(),rnb.getUrgentDate());
			if(s<1){
				logger.info(username+"审核NameCardRequest"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("审核NameCardRequest[request_new_convoy]失败");
			}
			sql="insert request_new" +
					"(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c," +
					"profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,upd_date,upd_name," +
					"card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only," +
					"CMS_only,CFG_only,Blank_only,Payer,addname,adddate,company,remark1,remark) " +
					"values('"+rnb.getStaff_code()
					+"','"+rnb.getName()
					+"','"+rnb.getName_chinese()
					+"','"+rnb.getTitle_english()
					+"','"+rnb.getTitle_chinese()
					+"','"+rnb.getExternal_english()
					+"','"+ rnb.getExternal_chinese()
					+"','"+ rnb.getAcademic_title_e()
					+"','"+ rnb.getAcademic_title_c()
					+"','"+ rnb.getProfess_title_e()
					+"','"+ rnb.getProfess_title_c()
					+"','"+ rnb.getTr_reg_no()
					+"','"+ rnb.getCe_no()
					+"','"+ rnb.getMpf_no()
					+"','"+ rnb.getE_mail()
					+"','"+ rnb.getDirect_line()
					+"','"+ rnb.getFax()
					+"','"+ rnb.getBobile_number()
					+"','"+ rnb.getQuantity()
					+"','"+ rnb.getUpd_date()
					+"','"+ rnb.getUpd_name()
					+"','"+ rnb.getCard_type()
					+"','"+ rnb.getLayout_type()
					+"','"+ rnb.getUrgent()
					+"','"+ rnb.getUrgentDate()
					+"','"+ rnb.getMarks()
					+"','"+ rnb.getLocation()
					+"','"+ rnb.getAe_consultant()
					+"','"+ rnb.getEliteTeam()
					+"','"+ rnb.getCAM_only()
					+"','"+ rnb.getCFS_only()
					+"','"+ rnb.getCIS_only()
					+"','"+ rnb.getCCL_only()
					+"','"+ rnb.getCFSH_only()
					+"','"+ rnb.getCMS_only()
					+"','"+ rnb.getCFG_only()
					+"','"+ rnb.getBlank_only()
					+"','"+ rnb.getPayer()
					+"','"+ rnb.getAddname()
					+"','"+ rnb.getAdddate()
					+"','"+ rnb.getCompany()
					+"','"+ rnb.getRemark1()//HKCIB
					+"',?)";
			logger.info(username+"审核NameCardrequest  SQL:"+sql);
			s=saveEntity(sql,rnb.getRemark());
			String refno=s+"";
			if(s<1){
				logger.info(username+"审核NameCardRequest"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("审核NameCardRequest[request_new]失败");
			}
			sql="insert into request_new_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) " +
					"values(?,?,'Approval_SZOADM',?,?);";
			s=update2(sql, s+"_pro",rnb.getUrgentDate(),username,DateUtils.getNowDateTime());
			if(s<1){
				logger.info(username+"审核NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("审核NameCardRequest[request_new_convoy_operation]失败");
			}
			
			/**************************************************保存订单状态 Start*************************************************************************/
			sql="insert into request_new_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark) " +
					"values('"+username+"','"+refno+"','"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()+"','Approval_SZOADM','"+username+"','"+DateUtils.getNowDateTime()+"','');";
			s=update(sql, null);
			if(s<1){
				logger.info(username+"审核NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("审核NameCardRequest[request_new_convoy_detial]失败");
			}
			/**************************************************保存订单状态  End***********************************************************************/
			if(s>0){
				logger.info(username+"审核NameCardrequest"+Constant.Message.MESSAGE_SUCCESS);
				sql="insert cn_master(staffNo,Name,C_Name,E_Title_Department,C_Title_Department,E_ExternalTitle_Department,C_ExternalTitle_Department,E_EducationTitle,C_EducationTitle,TR_RegNo,CE_No,MPF_No,HKCIB_NO,Email,DirectLine,Fax,MobilePhone,Num,submit_date)" +
						" values('"+ rnb.getStaff_code()
						+"','"+ rnb.getName()
						+"','"+rnb.getName_chinese()
						+"','"+ rnb.getTitle_english()
						+"','"+ rnb.getTitle_chinese()
						+"','"+ rnb.getExternal_english()
						+"','"+ rnb.getExternal_chinese()
						+"','"+ rnb.getProfess_title_e()
						+"','"+ rnb.getProfess_title_c()
						+"','"+ rnb.getTr_reg_no()
						+"','"+ rnb.getCe_no()
						+"','"+ rnb.getMpf_no()
						+"','"+ rnb.getRemark1()//HKCIB
						+"','"+ rnb.getE_mail()
						+"','"+ rnb.getDirect_line()
						+"','"+ rnb.getFax()
						+"','"+ rnb.getBobile_number()
						+"','"+ rnb.getQuantity()
						+"','"+ rnb.getUrgentDate()+"');";
				s=update(sql,null);
				if(s>0){
					
					if(rnb.getStaff_code().length()<10){
						sql = "insert req_record(request_date,code,name,departmen,quantity,upd_name,Layout_Type,Urgent)" +
								" values('"+rnb.getUrgentDate()
								+"','" + rnb.getPayer() 
								+ "','" + rnb.getName()
								+"','CD','"+ rnb.getQuantity()
								+"','" + username
								+"','"+ rnb.getLayout_type() 
								+"','"+rnb.getUrgent()+"')";
						s=update(sql, null);
					}
					if(s>0){
						
						NameCardPayer cr=null;
						/**************************需要自己支付***************************************/
						if(!rnb.getStaff_code().equalsIgnoreCase(rnb.getPayer()) || rnb.getUrgent().toUpperCase().equals("Y")){//当申请人与支付人不相等时需要额外支付
							double price=0;
							if(rnb.getUrgent().toUpperCase().equals("Y")){//加快
								if(rnb.getLayout_type().toUpperCase().trim().equals("P") && !rnb.getEliteTeam().toUpperCase().equals("Y")){//名片为自定义类型
									price=Constant.Premium_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
									price=Constant.Standard_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("P") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
									price=Constant.Premium_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && !rnb.getEliteTeam().toUpperCase().equals("Y")){
									price=Constant.Standard_Urgent*Integer.parseInt(rnb.getQuantity());//普通加快的价格
								}
							}else{//为他人支付
								price=Constant.Standard*Integer.parseInt(rnb.getQuantity());//普通的价格
							}
							cr=new NameCardPayer();
							cr.setStaffCode(rnb.getStaff_code());
							cr.setName(rnb.getName());
							cr.setNumber(rnb.getQuantity());
							cr.setAmount(price+"");
							cr.setPayer(rnb.getPayer());
							cr.setRemarks(rnb.getMarks());
							cr.setAddDate(rnb.getUrgentDate());
							cr.setUp_date(DateUtils.getNowDateTime());
							cr.setUp_name(username);
						}else if(rnb.getEliteTeam().equalsIgnoreCase("Y")){//选择Elite Team  （100张免费限额）
							sql="select staff_code from request_new " +
									"where card_type='N' and quantity>0 and staff_code='"+rnb.getStaff_code()+"' " +
									"and eliteTeam='Y' and urgent='N' " +
									"and date_format(UrgentDate,'%Y')=date_format(now(),'%Y')"; 
							Map<String,Object> map=findMap(sql);
							if(!Util.objIsNULL(map)){
								cr=new NameCardPayer();
								cr.setStaffCode(rnb.getStaff_code());
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
									cr.setAmount((Constant.Premium_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
								}else{
									cr.setAmount((Constant.Standard_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
								}
								cr.setPayer(rnb.getPayer());
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(username);
								
							}else{
								if(Integer.parseInt(rnb.getQuantity())>Constant.Elite_Team){//超过免费限额数量，进入财务
									cr=new NameCardPayer();
									cr.setStaffCode(rnb.getStaff_code());
									cr.setName(rnb.getName());
									cr.setNumber((Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team)+"");
									if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
										cr.setAmount((Constant.Premium_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
									}else{
										cr.setAmount((Constant.Standard_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
									}
									cr.setPayer(rnb.getPayer());
									cr.setRemarks(rnb.getMarks());
									cr.setAddDate(rnb.getUrgentDate());
									cr.setUp_date(DateUtils.getNowDateTime());
									cr.setUp_name(username);
									
								}else{
									//不做处理
								}
							}
							
						}else{//普通申请，判断是否超过免费办理限额
							double price=Constant.Standard;
							if(Integer.parseInt(rnb.getQuantity())+used>sum){//当次印刷名片数量与已印名片数量大于名片限额
								if(used>=sum){//已印名片数量大于名片限额
									price=price*Double.parseDouble(rnb.getQuantity());
								}else{
									rnb.setQuantity((Integer.parseInt(rnb.getQuantity())+used-sum)+"");
									price=price*Double.parseDouble(rnb.getQuantity());
								}
								if(rnb.getMarks().trim().equals("")){
									rnb.setMarks("Standard Layout");
								}
								cr=new NameCardPayer();
								cr.setStaffCode(rnb.getStaff_code());
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								cr.setAmount(price+"");
								cr.setPayer(rnb.getPayer());
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(username);
							}
						}
						if(!Util.objIsNULL(cr)){
							String sql2= "insert change_record(StaffCode,Name,Number,Amount,Payer,Remarks,Infomed_to_FAD,charged_Month,AddDate,up_date,up_name,sfyx)" +
									" values('"+cr.getStaffCode()
									+"','"+cr.getName()
									+"','"+cr.getNumber()
									+"','"+cr.getAmount()
									+"','"+cr.getPayer()
									+"','"+cr.getRemarks()
									+"','','','"+cr.getAddDate()
									+"','"+cr.getUp_date()
									+"','"+cr.getUp_name()+"','Y')";
							logger.info("保存财务信息表SQL- staffcode:"+cr.getStaffCode()+"----"+sql2);
							s=update(sql2, null);
							if(s>0){
								result=Util.getMsgJosnObject_success();
							}else{
								logger.info("审核NameCard财务信息表信息失败");
								throw new RuntimeException("保存NameCardRequest[charge_record]失败");
							}
						}else{
							result=Util.getMsgJosnObject_success();
						}
						
					}else{
						logger.info(username+"审核NameCard办理记录"+Constant.Message.MESSAGE_FAILD);
						throw new RuntimeException("审核NameCardRequest[req_record]失败");
					}
				}else{
					logger.info(username+"审核NameCard历史记录"+Constant.Message.MESSAGE_FAILD);
					throw new RuntimeException("审核NameCardRequest[cn_master]失败");
				}
			}else{
				logger.info(username+"审核NameCardrequest"+Constant.Message.MESSAGE_FAILD);
				throw new RuntimeException("审核NameCardRequest[request_new]失败");
			}
			sumbitTransaction();//提交事务
		}catch(Exception e){
			e.printStackTrace();
			rollbackTransaction();//回滚数据
			logger.error("审核NameCardRequest 异常："+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			closeConnection();
		}
		return result;
	}
	
	
	 
	
	
/**
 * 修改名片办理记录
 * @author kingxu
 * @date 2015-9-29
 * @param rnb
 * @param reStaffNo
 * @param rePayer
 * @param reUrgent
 * @param used
 * @param sum
 * @return
 * @throws SQLException
 * @return String
 */
	public String updateNameCard(RequestNewBean rnb,String reStaffNo,String rePayer,String reUrgent,double used,double sum) throws SQLException {
		String result="";
		String sql="";
		int s=-1;
		try{
			openTransaction();//开启事务
				sql="update request_new set card_type='U'  where UrgentDate='"+rnb.getUrgentDate()+"'";
				logger.info(rnb.getUpd_name()+"：修改 request_new SQL:"+sql);
				s=update(sql, null);
				if(s>0){
					sql="insert request_new" +
							"(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,academic_title_c," +
							"profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,upd_date,upd_name," +
							"card_type,layout_type,urgent,UrgentDate,marks,location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only," +
							"CMS_only,CFG_only,Blank_only,Payer,addname,adddate,company,remark1) " +
							"values('"+rnb.getStaff_code()
									+"','"+rnb.getName()
									+"','"+rnb.getName_chinese()
									+"','"+rnb.getTitle_english()
									+"','"+rnb.getTitle_chinese()
									+"','"+rnb.getExternal_english()
									+"','"+ rnb.getExternal_chinese()
									+"','"+ rnb.getAcademic_title_e()
									+"','"+ rnb.getAcademic_title_c()
									+"','"+ rnb.getProfess_title_e()
									+"','"+ rnb.getProfess_title_c()
									+"','"+ rnb.getTr_reg_no()
									+"','"+ rnb.getCe_no()
									+"','"+ rnb.getMpf_no()
									+"','"+ rnb.getE_mail()
									+"','"+ rnb.getDirect_line()
									+"','"+ rnb.getFax()
									+"','"+ rnb.getBobile_number()
									+"','"+ rnb.getQuantity()
									+"','"+ rnb.getUpd_date()
									+"','"+ rnb.getUpd_name()
									+"','"+ rnb.getCard_type()
									+"','"+ rnb.getLayout_type()
									+"','"+ rnb.getUrgent()
									+"','"+ rnb.getUrgentDate()
									+"','"+ rnb.getMarks()
									+"','"+ rnb.getLocation()
									+"','"+ rnb.getAe_consultant()
									+"','"+ rnb.getEliteTeam()
									+"','"+ rnb.getCAM_only()
									+"','"+ rnb.getCFS_only()
									+"','"+ rnb.getCIS_only()
									+"','"+ rnb.getCCL_only()
									+"','"+ rnb.getCFSH_only()
									+"','"+ rnb.getCMS_only()
									+"','"+ rnb.getCFG_only()
									+"','"+ rnb.getBlank_only()
									+"','"+ rnb.getPayer()
									+"','"+ rnb.getAddname()
									+"','"+ rnb.getAdddate()
									+"','"+ rnb.getCompany()
									+"','"+ rnb.getRemark1()
									+"')";
					logger.info(rnb.getUpd_name()+"保存NameCardrequest  SQL:"+sql);
					s=saveEntity(sql);
					 if(s<1){
						 logger.info(rnb.getUpd_name()+"保存NameCardRequest"+Constant.Message.MESSAGE_FAILD);
						 throw new RuntimeException("保存NameCardRequest[request_new]失败");
					 }
						sql="insert into request_new_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) " +
							"values('"+s+"_pro','"+rnb.getUrgentDate()+"','Motify_SZOADM','"+rnb.getUpd_name()+"','"+DateUtils.getNowDateTime()+"');";
						logger.info(rnb.getUpd_name()+"保存NameCard操作记录  SQL:"+sql);
						s=update(sql, null);
						if(s<1){
								logger.info(rnb.getUpd_name()+"保存NameCard操作记录"+Constant.Message.MESSAGE_FAILD);
								throw new RuntimeException("保存NameCardRequest[request_new_convoy_operation]失败");
						}
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
								rnb.getCe_no()+"' , HKCIB_NO='"+
								rnb.getRemark1()+"' , MPF_No='"+
								rnb.getMpf_no()+"' , Email='"+
								rnb.getE_mail()+"' , DirectLine='"+
								rnb.getDirect_line()+"' , Fax='"+
								rnb.getFax()+"' , MobilePhone='"+
								rnb.getBobile_number()+"' , num='"+
								rnb.getQuantity()+"' where submit_date='"+rnb.getUrgentDate()+"' and staffNo='"+reStaffNo+"'";
						logger.info(rnb.getUpd_name()+"修改NameCard历史记录  SQL:"+sql);
						s=update(sql, null);
						if(s<1){
								logger.info(rnb.getUpd_name()+"修改NameCard历史记录"+Constant.Message.MESSAGE_FAILD);
								throw new RuntimeException("修改NameCard历史记录[cn_master]失败");
						}
						if(reStaffNo.length()<10){
							sql="update req_record set code='"+rnb.getPayer()+"', quantity='"+rnb.getQuantity()+"' ,upd_name='"+rnb.getUpd_name()+"', Layout_Type='"+rnb.getLayout_type()+"',Urgent='"+rnb.getUrgent()+"' where code='"+rePayer+"' and request_date='"+rnb.getUrgentDate()+"'";
							logger.info("修改NameCard办理记录："+sql);
							s=update(sql,null);
						}else{//针对临时code
							sql = "insert req_record(request_date,code,name,departmen,quantity,upd_name,Layout_Type,Urgent,)" +
									" values('"+rnb.getUrgentDate()+"','" + rnb.getStaff_code() + "','" + rnb.getName()
									+ "','CD','" + rnb.getQuantity() + "','" + rnb.getUpd_name() + "','"
									+ rnb.getLayout_type() + "','"+rnb.getUrgent()+"')";
							logger.info("保存NameCard办理记录："+sql);
						}
						if(s<1){
							logger.info(rnb.getUpd_name()+"保存/修改NameCard办理记录"+Constant.Message.MESSAGE_FAILD);
							throw new RuntimeException("保存/修改NameCard办理记录[req_record]失败");
						}
						sql="update  change_record set up_date='"+DateUtils.getNowDateTime()+"' , up_name='"+rnb.getUpd_name()+"', sfyx='N' where StaffCode='"+reStaffNo+"' and AddDate='"+rnb.getUrgentDate()+"'";
						logger.info("删除NameCard付费记录:"+sql);
						s=update(sql, null);
						//s可以为0,因为不一定每一条申请都有付费记录
						if(Integer.parseInt(rnb.getQuantity())==0){
							result=Util.getMsgJosnObject_success();
							logger.info("修改NameCard成功!");
							sumbitTransaction();
							return result;
						}
						/*if(reUrgent.equalsIgnoreCase("Y")&&rnb.getUrgent().equalsIgnoreCase("N")){
							result=Util.getMsgJosnObject_success();
							logger.info("修改NameCard成功!");
							sumbitTransaction();
							return result;
						}*/
					
						NameCardPayer cr=null;
						/**************************需要自己支付***************************************/
						if(!rnb.getStaff_code().equalsIgnoreCase(rnb.getPayer()) || rnb.getUrgent().toUpperCase().equals("Y")){//当申请人与支付人不相等时需要额外支付
							double price=0;
							if(rnb.getUrgent().toUpperCase().equals("Y")){//加快
								if(rnb.getLayout_type().toUpperCase().trim().equals("P") && !rnb.getEliteTeam().toUpperCase().equals("Y")){//名片为自定义类型
									price=Constant.Premium_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
									price=Constant.Standard_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("P") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
									price=Constant.Premium_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
								}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && !rnb.getEliteTeam().toUpperCase().equals("Y")){
									price=Constant.Standard_Urgent*Integer.parseInt(rnb.getQuantity());//普通加快的价格
								}
							}else{//为他人支付
								price=Constant.Standard*Integer.parseInt(rnb.getQuantity());//普通的价格
							}
							cr=new NameCardPayer();
							cr.setStaffCode(rnb.getStaff_code());
							cr.setName(rnb.getName());
							cr.setNumber(rnb.getQuantity());
							cr.setAmount(price+"");
							cr.setPayer(rnb.getPayer());
							cr.setRemarks(rnb.getMarks());
							cr.setAddDate(rnb.getUrgentDate());
							cr.setUp_date(DateUtils.getNowDateTime());
							cr.setUp_name(rnb.getUpd_name());
						}else if(rnb.getEliteTeam().equalsIgnoreCase("Y")){//选择Elite Team  （100张免费限额）
							sql="select staff_code from request_new " +
									"where card_type='N' and quantity>0 and staff_code='"+rnb.getStaff_code()+"' " +
									"and eliteTeam='Y' and urgent='N' " +
									"and date_format(UrgentDate,'%Y')=date_format(now(),'%Y')"; 
							Map<String,Object> map=findMap(sql);//判断是否办理过eliteteam
							if(!Util.objIsNULL(map)){
								cr=new NameCardPayer();
								cr.setStaffCode(rnb.getStaff_code());
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
									cr.setAmount((Constant.Premium_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
								}else{
									cr.setAmount((Constant.Standard_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
								}
								cr.setPayer(rnb.getPayer());
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(rnb.getUpd_name());
							
							}else{
								if(Integer.parseInt(rnb.getQuantity())>Constant.Elite_Team){//超过免费限额数量，进入财务
									cr=new NameCardPayer();
									cr.setStaffCode(rnb.getStaff_code());
									cr.setName(rnb.getName());
									cr.setNumber((Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team)+"");
									if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
										cr.setAmount((Constant.Premium_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
									}else{
										cr.setAmount((Constant.Standard_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
									}
									cr.setPayer(rnb.getPayer());
									cr.setRemarks(rnb.getMarks());
									cr.setAddDate(rnb.getUrgentDate());
									cr.setUp_date(DateUtils.getNowDateTime());
									cr.setUp_name(rnb.getUpd_name());
									
								}else{
									//不做处理
								}
							}
							
						}else{//普通申请，判断是否超过免费办理限额
							double price=Constant.Standard;
							if(Integer.parseInt(rnb.getQuantity())+used>sum){//当次印刷名片数量与已印名片数量大于名片限额
								if(used>=sum){//已印名片数量大于名片限额
									price=price*Double.parseDouble(rnb.getQuantity());
								}else{
									rnb.setQuantity((Integer.parseInt(rnb.getQuantity())+used-sum)+"");
									price=price*Double.parseDouble(rnb.getQuantity());
								}
								if(rnb.getMarks().trim().equals("")){
									rnb.setMarks("Standard Layout");
								}
								cr=new NameCardPayer();
								cr.setStaffCode(rnb.getStaff_code());
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								cr.setAmount(price+"");
								cr.setPayer(rnb.getPayer());
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(rnb.getUpd_name());
							}
						}
						if(!Util.objIsNULL(cr)){
							String sql2= "insert change_record(StaffCode,Name,Number,Amount,Payer,Remarks,Infomed_to_FAD,charged_Month,AddDate,up_date,up_name,sfyx)" +
									" values('"+cr.getStaffCode()
									+"','"+cr.getName()
									+"','"+cr.getNumber()
									+"','"+cr.getAmount()
									+"','"+cr.getPayer()
									+"','"+cr.getRemarks()
									+"','','','"+cr.getAddDate()
									+"','"+cr.getUp_date()
									+"','"+cr.getUp_name()+"','Y')";
							logger.info("保存财务信息表SQL- staffcode:"+cr.getStaffCode()+"----"+sql2);
							s=update(sql2, null);
							if(s>0){
								result=Util.getMsgJosnObject_success();
							}else{
								logger.info("保存NameCard财务信息表信息失败");
								throw new RuntimeException("保存NameCardRequest[charge_record]失败");
							}
						}else{
							result=Util.getMsgJosnObject_success();
						}
						
				}else{
					logger.info(rnb.getUpd_name()+"修改NameCard(backup)"+Constant.Message.MESSAGE_FAILD);
					throw new RuntimeException("修改NameCardRequest[request_new(backup)]失败");
				}
			sumbitTransaction();
			logger.info(rnb.getUpd_name()+"：修改 request_new SQL:"+sql);
		}catch(Exception e){
			rollbackTransaction();
			result=Util.joinException(e);
			logger.error(rnb.getUpd_name()+"：修改request_new异常！"+e);
		}
		finally{
			//关闭连接
			super.closeConnection();
		}
		return result;
	}
	
	
	public String getEnglishNameByCode(String staffcode) throws SQLException{
		String EnglishName="";
		try{
			String sql = "select EmployeeName from cons_list where EmployeeId='"
					+ staffcode + "' limit 0,1";
			List<Object[]> name=findDate(sql);
			if(name.size()>0){
				EnglishName=name.get(0)[0]+"";
			}
		}catch (Exception e) {
			throw new RuntimeException("获取Staff EnglishName 出现异常"+e.getMessage());
		}finally{
			closeConnection();
		}
		return EnglishName;
	}

	public LicensePlate getHKCIB(String staffcode) throws SQLException{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String sql = null;
		LicensePlate licenseList = null;
		
		try {
			con = DBManager_sqlservler.getCon();
			sql = "select ConsultantID,max (case when LicenseCategory='PIBA' then LicenseNo else '' end) as PIBA,max (case when LicenseCategory='HKCIB' then LicenseNo else '' end) as HKCIB,max (case when LicenseCategory='SFC' then LicenseNo else '' end) as SFC,max (case when LicenseCategory='MPF' then LicenseNo else '' end) as MPF from [SZO_system].[dbo].[vSZO_SOS_ConsultantLicense] where consultantID = ? group by ConsultantID;";
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				licenseList = new LicensePlate();
				licenseList.setPIBA(rs.getString("PIBA"));
				licenseList.setHKCIB(rs.getString("HKCIB"));
				licenseList.setSFC(rs.getString("SFC"));
				licenseList.setMPF(rs.getString("MPF"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager_sqlservler.closeCon(con);
		}
		return licenseList;
	}
	public String getLocation(String staffcode) throws SQLException{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String sql = null;
		String Location = null;
		
		try {
			con = DBManager_sqlservler.getCon();
			sql = "select top 1 Location as location FROM [SZO_system].[dbo].[vSZOADM] where EmployeeID = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				Location = rs.getString("location");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager_sqlservler.closeCon(con);
		}
		return Location;
	}

	public List<RequestStaffBean> getOldRecord(String staffcode) throws SQLException {
		List<RequestStaffBean> list=new ArrayList<RequestStaffBean>();
		try{
			String sql="select * from request_staff where staff_code='"+staffcode+"'" +
					" and card_type='N' and date_format(UrgentDate,'%Y')=date_format(now(),'%Y') " +
					" order by UrgentDate desc ";
			List<Map<String, Object>> listMap=findListMap(sql,new Object[]{});
			if(listMap.size()>0){
				list.add(new RequestStaffBean(
						Util.objIsNULL(listMap.get(0).get("staff_code"))?"":listMap.get(0).get("staff_code").toString(),
						Util.objIsNULL(listMap.get(0).get("name"))?"":listMap.get(0).get("name").toString(),
						Util.objIsNULL(listMap.get(0).get("name_chinese"))?"":listMap.get(0).get("name_chinese").toString(),
						Util.objIsNULL(listMap.get(0).get("title_english"))?"":listMap.get(0).get("title_english").toString(),
						Util.objIsNULL(listMap.get(0).get("title_chinese"))?"":listMap.get(0).get("title_chinese").toString(),
						Util.objIsNULL(listMap.get(0).get("external_english"))?"":listMap.get(0).get("external_english").toString(),
						Util.objIsNULL(listMap.get(0).get("external_chinese"))?"":listMap.get(0).get("external_chinese").toString(),
						Util.objIsNULL(listMap.get(0).get("academic_title_e"))?"":listMap.get(0).get("academic_title_e").toString(),
						Util.objIsNULL(listMap.get(0).get("academic_title_c"))?"":listMap.get(0).get("academic_title_c").toString(),
						Util.objIsNULL(listMap.get(0).get("profess_title_e"))?"":listMap.get(0).get("profess_title_e").toString(),
						Util.objIsNULL(listMap.get(0).get("profess_title_c"))?"":listMap.get(0).get("profess_title_c").toString(),
						Util.objIsNULL(listMap.get(0).get("tr_reg_no"))?"":listMap.get(0).get("tr_reg_no").toString(),
						Util.objIsNULL(listMap.get(0).get("ce_no"))?"":listMap.get(0).get("ce_no").toString(),
						Util.objIsNULL(listMap.get(0).get("mpf_no"))?"":listMap.get(0).get("mpf_no").toString(),
						Util.objIsNULL(listMap.get(0).get("e_mail"))?"":listMap.get(0).get("e_mail").toString(),
						Util.objIsNULL(listMap.get(0).get("direct_line"))?"":listMap.get(0).get("direct_line").toString(),
						Util.objIsNULL(listMap.get(0).get("fax"))?"":listMap.get(0).get("fax").toString(),
						Util.objIsNULL(listMap.get(0).get("bobile_number"))?"":listMap.get(0).get("bobile_number").toString(),
						Util.objIsNULL(listMap.get(0).get("quantity"))?"":listMap.get(0).get("quantity").toString(),
						Util.objIsNULL(listMap.get(0).get("upd_date"))?"":listMap.get(0).get("upd_date").toString(),
						Util.objIsNULL(listMap.get(0).get("upd_name"))?"":listMap.get(0).get("upd_name").toString(),
						Util.objIsNULL(listMap.get(0).get("card_type"))?"":listMap.get(0).get("card_type").toString(),
						Util.objIsNULL(listMap.get(0).get("layout_type"))?"":listMap.get(0).get("layout_type").toString(),
						Util.objIsNULL(listMap.get(0).get("location"))?"":listMap.get(0).get("location").toString(),
						Util.objIsNULL(listMap.get(0).get("ae_consultant"))?"":listMap.get(0).get("ae_consultant").toString(),
						Util.objIsNULL(listMap.get(0).get("eliteTeam"))?"":listMap.get(0).get("eliteTeam").toString(),
						Util.objIsNULL(listMap.get(0).get("CAM_only"))?"":listMap.get(0).get("CAM_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CFS_only"))?"":listMap.get(0).get("CFS_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CIS_only"))?"":listMap.get(0).get("CIS_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CCL_only"))?"":listMap.get(0).get("CCL_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CFSH_only"))?"":listMap.get(0).get("CFSH_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CMS_only"))?"":listMap.get(0).get("CMS_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CFG_only"))?"":listMap.get(0).get("CFG_only").toString(),
						Util.objIsNULL(listMap.get(0).get("Blank_only"))?"":listMap.get(0).get("Blank_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CCIA_only"))?"":listMap.get(0).get("CCIA_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CCFSH_only"))?"":listMap.get(0).get("CCFSH_only").toString(),
						Util.objIsNULL(listMap.get(0).get("CWMC_only"))?"":listMap.get(0).get("CWMC_only").toString(),
						Util.objIsNULL(listMap.get(0).get("marks"))?"":listMap.get(0).get("marks").toString(),
						Util.objIsNULL(listMap.get(0).get("urgent"))?"":listMap.get(0).get("urgent").toString(),
						Util.objIsNULL(listMap.get(0).get("payer"))?"":listMap.get(0).get("payer").toString(),
						Util.objIsNULL(listMap.get(0).get("UrgentDate"))?"":listMap.get(0).get("UrgentDate").toString()	
				));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//关闭连接
			super.closeConnection();
		}
		return list;
	}

	

}
