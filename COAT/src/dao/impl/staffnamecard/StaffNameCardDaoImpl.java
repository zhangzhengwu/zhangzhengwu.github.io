package dao.impl.staffnamecard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DBManager_oracle;
import util.DateUtils;
import util.Page;
import util.Pager;
import util.SendMail;
import util.Util;
import dao.QueryStaffRequstDao;
import dao.common.BaseDao;
import dao.impl.QueryStaffRequstDaoImpl;
import dao.staffnamecard.StaffNameCardDao;
import entity.RequestStaffBean;
import entity.RequestStaffCompanyView;
import entity.RequestStaffConvoyApprovePrompt;
import entity.RequestStaffConvoyBean;
import entity.RequestStaffConvoyDetial;

public class StaffNameCardDaoImpl extends BaseDao implements StaffNameCardDao {
Logger logger=Logger.getLogger(this.getClass());
Connection conn=null;
PreparedStatement ps=null;
ResultSet rs=null;	
	
	public StaffNameCardDaoImpl() {
	}

	public Pager findPager(String[] fields, Pager page,String ET, Object... objects) throws Exception{
		String sql=" FROM request_staff_convoy " +
				" where card_type='N' " +
				" and  date_format(upd_date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(upd_date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and staff_code like ? " +
				" and payer like ?" +
				" and name like ?" +
				" and location like ?" +
				" and urgent like ?" +
				" and layout_type like ? " +
				" and shzt like ? ";
		if(!Util.objIsNULL(ET)){
			if(ET.equalsIgnoreCase("Y")){
				sql+=" and eliteTeam ='Y' ";
			}else{
				sql+=" and eliteTeam !='Y'";
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
	
	public Pager queryStateDetail(String[] fields, Pager page,Object... objects) throws Exception{
		String sql=" FROM request_staff_convoy_detial  where  staffrefno like ? and staffcode like ? ";
		String limit="order by createdate asc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	public Pager findHRApprovalPager(String[] fields, Pager page,String ET, Object... objects) throws Exception{
		String sql=" FROM request_staff_convoy " +
				" where card_type='N' " +
				" and  date_format(upd_date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(upd_date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and staff_code like ? " +
				" and payer like ?" +
				" and name like ?" +
				" and location like ?" +
				" and urgent like ?" +
				" and layout_type like ? " +
				" and shzt like ? ";
		if(!Util.objIsNULL(ET)){
			if(ET.equalsIgnoreCase("Y")){
				sql+=" and eliteTeam ='Y' ";
			}else{
				sql+=" and eliteTeam !='Y'";
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
	  
	
	public Pager findDeptApprovalPager(String[] fields, Pager page,String ET, Object... objects) throws Exception{
		
		String sql=" FROM request_staff_convoy " +
				" where card_type='N' " +
				" and  date_format(upd_date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(upd_date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				//" and staff_code in(select staffcode from staff_list where (TerminationDate is null or TerminationDate='')"+
				//" and deptid in(select dpt from department where sfyx='Y' and (depart_head=? or depart_head_bak=?)))" +
				" and staff_code in(SELECT sub_code FROM request_staff_convoy_supervisor_view where supervisor_ID=? or supercisor2_ID=?)"+
				" and payer like ?" +
				" and name like ?" +
				" and location like ?" +
				" and urgent like ?" +
				" and layout_type like ? "+
				" and shzt like ? ";
		if(!Util.objIsNULL(ET)){
			if(ET.equalsIgnoreCase("Y")){
				sql+=" and eliteTeam ='Y' ";
			}else{
				sql+=" and eliteTeam !='Y'";
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
	
	public String SZOADMApproval(RequestStaffConvoyBean rnb,String username,String refno) throws SQLException{
		int s=-1;
		String result="";
		try{
			openTransaction();
			String sql="insert request_staff(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
					"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line," +
					"fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks," +
					"location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only," +
					"CFG_only,Blank_only,CCIA_only,CCFSH_only,CWMC_only,Payer,Company)" +
					"values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()+"','"+rnb.getTitle_english()+"'," +
							"'"+rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"+rnb.getExternal_chinese()+"','"+rnb.getAcademic_title_e()+"'," +
							"'"+rnb.getAcademic_title_c()+"','"+rnb.getProfess_title_e()+"','"+rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"'," +
							"'"+rnb.getCe_no()+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+rnb.getDirect_line()+"'," +
							"'"+rnb.getFax()+"','"+rnb.getBobile_number()+"','"+rnb.getQuantity()+"','"+rnb.getUpd_date()+"'," +
							"'"+rnb.getUpd_name()+"','"+rnb.getCard_type()+"','"+rnb.getLayout_type()+"','"+rnb.getUrgent()+"'," +
							"'"+rnb.getUrgentDate()+"','"+rnb.getMarks()+"','"+ rnb.getLocation()+"','"+rnb.getCard_type()+"'," +
							"'"+rnb.getEliteTeam()+"','"+rnb.getCAM_only()+"','"+rnb.getCFS_only()+"','"+rnb.getCIS_only()+"'," +
							"'"+rnb.getCCL_only()+"','"+rnb.getCFSH_only()+"','"+rnb.getCMS_only()+"','"+rnb.getCFG_only()+"'," +
							"'"+rnb.getBlank_only()+"','"+rnb.getCCIA_only()+"','"+rnb.getCCFSH_only()+"','"+rnb.getCWMC_only()+"'," +
							"'"+rnb.getPayer()+"','"+rnb.getCompany()+"');";
			String sql2="update request_staff_convoy set shzt='Y',remarkcons='"+rnb.getRemarkcons()+"' where refno='"+refno+"' and (shzt='R' or shzt='E'); ";
			String sql3="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+rnb.getUrgentDate()+"','Approval_SZOADM','"+username+"','"+DateUtils.getNowDateTime()+"');";
			String sql4="insert staff_master(staffNo,name,c_Name,e_Title_Department,c_Title_Department,e_ExternalTitle_Department,c_ExternalTitle_Department,e_EducationTitle,c_EducationTitle,tr_RegNo,ce_No,MPF_No,email,directLine,fax,mobilePhone,num,submit_date) values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+
					rnb.getName_chinese()+"','"+rnb.getTitle_english()+"','"+
					rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"+
					rnb.getExternal_chinese()+"','"+rnb.getProfess_title_e()+"','"+
					rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"','"+
					rnb.getCe_no()+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+
					rnb.getDirect_line()+"','"+rnb.getFax()+"','"+rnb.getBobile_number()+"','"+
					rnb.getQuantity()+"','"+rnb.getUpd_date()+"')";
			
			String sql5="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+username+"'," +
			"'"+refno+"','"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()+"','Y','"+username+"','"+DateUtils.getNowDateTime()+"','')";
			
			s=batchExcute(new String[]{sql,sql2,sql3,sql4,sql5});
			logger.info("SZOADM Approve Request  Convoy 时    ====SQL:"+sql+"\r\n"+sql2+"\r\n"+sql3);
			if(s>4){
				String body="Dear "+rnb.getName()+",<br/>&nbsp;&nbsp;Your name card request has been processed and the name card will ready after 7 working days";
				if(Util.objIsNULL(rnb.getE_mail())){
					throw new RuntimeException("The applicant email address is empty");
				}else{
					result=SendMail.send("Your name card request submitted at "+rnb.getUrgentDate()+" has been proccessed by ADM.",rnb.getE_mail() , body, "email.ftl", "");
					JSONObject json=new JSONObject(result);
					if(!"success".equalsIgnoreCase(json.get("state")+"")){
						throw new RuntimeException("SZOADM"+json.getString("msg"));
					}
				}
			}else{
				throw new RuntimeException("SZOADM Approval Error");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("插入Request_Staff Name Card_Convoy信息保存异常！"+e.getMessage());
		}finally{
			super.closeConnection();
		}
		return result;
		
	}
	
	
/**
 * 根据不同权限保存名片办理记录
 */
public String saveStaffRequestConvoy(RequestStaffConvoyBean rnb,String roleType,String adminUsername) throws SQLException {
		String result="";
		int s=-1;
		try{
			//conn=DBManager.getCon();
			openTransaction();
			String sql="insert request_staff_convoy(staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e," +
					"academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line," +
					"fax,bobile_number,quantity,upd_date,upd_name,card_type,layout_type,urgent,UrgentDate,marks," +
					"location,ae_consultant,eliteTeam,CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only," +
					"CFG_only,Blank_only,CCIA_only,CCFSH_only,CWMC_only,Payer,Subject,Remark,remarkcons,shzt,Company)" +
					"values('"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()+"','"+rnb.getTitle_english()+"'," +
							"'"+rnb.getTitle_chinese()+"','"+rnb.getExternal_english()+"','"+rnb.getExternal_chinese()+"','"+rnb.getAcademic_title_e()+"'," +
							"'"+rnb.getAcademic_title_c()+"','"+rnb.getProfess_title_e()+"','"+rnb.getProfess_title_c()+"','"+rnb.getTr_reg_no()+"'," +
							"'"+rnb.getCe_no()+"','"+rnb.getMpf_no()+"','"+rnb.getE_mail()+"','"+rnb.getDirect_line()+"'," +
							"'"+rnb.getFax()+"','"+rnb.getBobile_number()+"','"+rnb.getQuantity()+"','"+rnb.getUpd_date()+"'," +
							"'"+rnb.getUpd_name()+"','"+rnb.getCard_type()+"','"+rnb.getLayout_type()+"','"+rnb.getUrgent()+"'," +
							"'"+rnb.getUrgentDate()+"','"+rnb.getMarks()+"','"+ rnb.getLocation()+"','"+rnb.getCard_type()+"'," +
							"'"+rnb.getEliteTeam()+"','"+rnb.getCAM_only()+"','"+rnb.getCFS_only()+"','"+rnb.getCIS_only()+"'," +
							"'"+rnb.getCCL_only()+"','"+rnb.getCFSH_only()+"','"+rnb.getCMS_only()+"','"+rnb.getCFG_only()+"'," +
							"'"+rnb.getBlank_only()+"','"+rnb.getCCIA_only()+"','"+rnb.getCCFSH_only()+"','"+rnb.getCWMC_only()+"'," +
							"'"+rnb.getPayer()+"','"+rnb.getSubject()+"','"+rnb.getRemark()+"','"+rnb.getRemarkcons()+"'," +
							"'"+rnb.getShzt()+"','"+rnb.getCompany()+"')";
			
			int refno=saveEntity(sql);
			if(refno<0){
				throw new RuntimeException("保存失败!");
			}
			String sql2="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+adminUsername+"'," +
				"'"+refno+"','"+rnb.getStaff_code()+"','"+rnb.getName()+"','"+rnb.getName_chinese()+"','"+rnb.getShzt()+"','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','')";
			s=batchExcute(new String[]{sql2});
			
			logger.info("Request_Staff Name Card_Convoy信息保存方法SQL:"+sql);
			//s=update(sql, new Object[]{});
			if(s>0){
				String body="";
				String [] email=new String [2];
				String subject="Staff name card request approval.";
				String state="";
				
				//if(Constant.RoleType_staff.equalsIgnoreCase(roleType)||Constant.RoleType_hr.equalsIgnoreCase(roleType)){//staff提交申请
				if(!Constant.RoleType_depthead.equals(roleType)){//只要roleType不为空，任何申请都认定为由staff提交的
					state="S";
					body="Dear Supervisor/Department Head,<br/><br/>"
							+"&nbsp;&nbsp;The name card request initiated by "+rnb.getName()+"  "+rnb.getStaff_code()+"  is in Supervisor/Department Head approval status."
							+"<br/><br/>"
							+"Please visit and approve at:<br/>";
							if(Util.getProValue("public.system.uat")=="true"){
								body+="<a href='http://www.econvoy.com/group/convoy/coat-test?handle=deptapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
										+"Thank You.";//通知dept head 审核
							}else{
								body+="<a href='http://www.econvoy.com/group/convoy/office-admin?handle=deptapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
										+"Thank You.";//通知dept head 审核	
							}
							
					subject="COAT: "+rnb.getName()+" - Name Card Request";
					QueryStaffRequstDao qsrDao=new QueryStaffRequstDaoImpl();
					//email=qsrDao.findDeptByStaff(rnb.getStaff_code());//获取部门主管邮箱
					email=qsrDao.findSupervisorEmail(rnb.getStaff_code());//获取staff上级邮箱
					if(Util.objIsNULL(email)){
						throw new RuntimeException("Get department supervisor email address exception");
					}
				}else if(Constant.RoleType_depthead.equalsIgnoreCase(roleType)){//dept head 提交申请
					state="E";
					subject="Staff name card request approval.";
					//通知SZADM同事处理
					/*body="Dear SZOADM,<br/><br/>" +
							"&nbsp;&nbsp; Management("+rnb.getName()+"  "+rnb.getStaff_code()+") 于"+DateUtils.getDateToday()+"提交了name card 申请。";*/
					
					/*email[0]=Util.getProValue("public.staffnamecard.szoadm.eamil");
					email[1]=Util.getProValue("public.staffnamecard.szoadm.eamil");*/
					//SZOADM 不接收通知邮件
					body = "";
					result=Util.getMsgJosnObject_success();
				}
				else{//未找到允许的角色类型，抛出权限异常
					throw new RuntimeException("Unauthorized request");
				}
				if(!Util.objIsNULL(body)){//判断通知邮件是否为空
					//--<<staff\dept
					/***********************************************************************************************************/
					String sql3="insert into request_staff_convoy_approve_prompt(refno,emailBody,emailTitle,emailAddress,sendDate,state,remark)values(" 
						+" '"+refno+"',?,?,?,'"+DateUtils.getNowDateTime()+"','"+state+"','')";
					int f=update(sql3,new Object[]{body,subject,email[0]});
					if(f<0){
						throw new RuntimeException("Save the card status failed!"); 
					}
					logger.info("request_staff_convoy_approve_prompt信息保存方法SQL:"+sql3);
					/***********************************************************************************************************/
					result=SendMail.send(subject, email[0], body,  "email.ftl", "");
					//result="{\"msg\": \"success\" , \"state\": \"success\" }";
					JSONObject json=new JSONObject(result);
					if(!"success".equalsIgnoreCase(json.get("state")+"")){
						throw new RuntimeException(json.getString("msg"));
					}
					
				}
				/*else{
					throw new RuntimeException("Necessary parameter missing");
				}*/
				
				logger.info("插入Request_Staff Name Card_Convoy成功！");
			}else{
				result=Util.getMsgJosnObject_error();
				logger.info("插入Request_Staff Name Card_Convoy失败！");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			e.printStackTrace();
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("插入Request_Staff Name Card_Convoy信息保存异常！"+e);
		}finally{
			super.closeConnection();
		}
		return result;
	}
	
	
	
	
	/**
	 * dept approval 
	 * @author kingxu
	 * @date 2015-8-10
	 * @param staffcode
	 * @param urgentDate
	 * @param username
	 * @param remarkcons
	 * @param englishName
	 * @return
	 * @return String
	 * @throws SQLException 
	 * 2015-11-11 09:24:20 去除HR审核流程，Dept无需发送通知和HR
	 * 
	 */
	public String approveRequestDepartConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons,String englishName,String refno,String chineseNames) throws SQLException {
		int s=-1;
		String result="";
		try{
			openTransaction();//开启手动控制事物
			String sql="update request_staff_convoy set shzt='E',remarkcons='"+remarkcons+"' where shzt='S' and refno="+refno+"";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Approval_Dept','"+username+"','"+DateUtils.getNowDateTime()+"')";
			//-->
			String sql3="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+username+"'," +
				"'"+refno+"','"+staffcode+"','"+englishName+"','"+chineseNames+"','E','"+username+"','"+DateUtils.getNowDateTime()+"','')";
			
			
			s=batchExcute(new String[]{sql,sql2,sql3});
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			if(s>2){
		/*		logger.info("Approve Request STAFF --------------邮件通知HR");
				String body="Dear User,<br/><br/>"
						+"    The name card request initiated by "+englishName+"  "+staffcode+" is in HRD approval status."
						+"<br/><br/>"
						+"Please visit and approve at:<br/>"
						+"<a href='http://www.econvoy.com/group/convoy/coat-test?handle=hrapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
						+"Thank You.";*/
				
				//--<<
				/*******************************************************************************************************************/
				String sql4="insert into request_staff_convoy_approve_prompt(refno,emailBody,emailTitle,emailAddress,sendDate,state,remark)values(" 
					+" '"+refno+"',?,?,?,'"+DateUtils.getNowDateTime()+"','E','')";
					logger.info("request_staff_convoy_approve_prompt信息保存方法SQL:"+sql4);
				/*******************************************************************************************************************/
				logger.info("Approve Request STAFF --------------邮件通知SZADM");
				String	body="Dear SZOADM,<br/><br/>" +
						"  Supervisor/Department Head 于"+DateUtils.getDateToday()+"审核通过了 "+englishName+"  "+staffcode+" 于 "+urgentDate+"提交的name card 申请。";
				
				int b=update(sql4,new Object[]{body,"Department Head Has been Approval",SendMail.getProValue("public.staffnamecard.szoadm.eamil")});
				if(b<0){
					throw new RuntimeException("Update the card status failed!"); 
				}
				logger.info("request_staff_convoy_approve_prompt信息保存方法SQL:"+sql4);
				
				result = "success";
				/*result=SendMail.send("Department Head Has been Approval", SendMail.getProValue("public.staffnamecard.szoadm.eamil"), body, "COAT", "email.ftl", "");
				JSONObject json=new JSONObject(result);
				if(!"success".equalsIgnoreCase(json.get("state")+"")){//SZOADM邮件发送失败
					throw new Exception(json.getString("msg"));
				}*/
			
			}else{
				logger.info("Approve Request STAFF 失败！");
				result=Util.getMsgJosnObject("error", "");
			}
			sumbitTransaction();//提交事物
			logger.info("Approve Request STAFF 成功！");
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("Approve Request STAFF数据异常！"+e);
		}finally{
			super.closeConnection();
		}
		return result;
	}
	
	public String approveRequestHRConvoy_remark(String staffcode, String urgentDate,
			String username,String remarkcons,String englishName,String refno,String chineseName) throws SQLException {
		int s=-1;
		String result="";
		try{
			openTransaction();//开启手动控制事物
			String sql="update request_staff_convoy set shzt='R',remarkcons='"+remarkcons+"' where shzt='E' and staff_code='"+staffcode+"' and UrgentDate='"+urgentDate+"'";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Approval_HR','"+username+"','"+DateUtils.getNowDateTime()+"')";
			//-->
			String sql3="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+username+"'," +
			"'"+refno+"','"+staffcode+"','"+englishName+"','"+chineseName+"','R','"+username+"','"+DateUtils.getNowDateTime()+"','')";
			
			s=batchExcute(new String[]{sql,sql2,sql3});
			logger.info("Approve Request STAFF Convoy 时    ====SQL:"+sql);
			//s=super.update(sql, new String[]{remarkcons,staffcode,urgentDate});
			if(s>2){
				logger.info("HR Approve Request STAFF --------------邮件通知SZADM");
				String body="Dear SZOADM,<br/><br/>" +
						"  HRD 于"+DateUtils.getDateToday()+"审核通过了 "+englishName+"  "+staffcode+" 于 "+urgentDate+"提交的name card 申请。";
				result="success";
				/*result=SendMail.send("HRD Has been Approval", SendMail.getProValue("public.staffnamecard.szoadm.eamil"), body, "COAT", "email.ftl", "");
				JSONObject json=new JSONObject(result);
				if(!"success".equalsIgnoreCase(json.get("state")+"")){
					throw new Exception(json.getString("msg"));
				}*/
				//--<<通知SZADM
				/*******************************************************************************************************************/
					//String sql4="update request_staff_convoy_approve_prompt set state='R',emailBody=?,emailTitle='HRD Has been Approval', " +
							//"emailAddress=?,sendDate='"+DateUtils.getDateToday()+"' where state='E' and refno="+refno+"";
					String sql4="insert into request_staff_convoy_approve_prompt(refno,emailBody,emailTitle,emailAddress,sendDate,state,remark)values(" 
						+" '"+refno+"',?,'HRD Has been Approval',?,'"+DateUtils.getNowDateTime()+"','R','')";
					int b=update(sql4,new Object[]{body,SendMail.getProValue("public.staffnamecard.szoadm.eamil")});
					if(b<0){
						throw new RuntimeException("Update the card status failed!"); 
					}
					logger.info("request_staff_convoy_approve_prompt信息保存方法SQL:"+sql4);
				/*******************************************************************************************************************/
			}else{
				logger.info("HR Approve Request STAFF 失败！");
				result=Util.getMsgJosnObject("error", "");
			}
			sumbitTransaction();//提交事物
			logger.info("HR Approve Request STAFF 成功！");
		}catch(Exception e){
			//e.printStackTrace();
			super.rollbackTransaction();
			result=Util.jointException(e);
			logger.error("HR Approve Request STAFF数据异常！"+e);
		}finally{
			super.closeConnection();
		}
		return result;
	}
	
	
	
	/**
	 * SZADM Reject econvoy Staff 提交的申请
	 * @param staffcode
	 * @param urgentDate
	 * @param adminUsername
	 * @return
	 * @throws SQLException 
	 */
	public int rejectStaffRequestConvoy(String staffcode, String urgentDate,String adminUsername,String refno,String remark,String nameE,String nameC) throws SQLException {
		int num=-1;
		try{
			String sql="update request_staff_convoy set shzt='N',remark=remarkcons,remarkcons='"+remark+"' where staff_code='"+staffcode+"' and UrgentDate='"+urgentDate+"'  and shzt!='N' and shzt!='Y'";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Reject_SZADM','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
			String sql3="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+adminUsername+"'," +
					"'"+refno+"','"+staffcode+"','"+nameE+"','"+nameC+"','N','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','')";
			
			openTransaction();//开启手动控制事物
			num=batchExcute(new String[]{sql,sql2,sql3});
			logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 时    ====SQL:\r\n"+sql+"\r\n"+sql2+"\r\n"+sql3);
			if(num>2){
				logger.info("用户 =="+adminUsername+"===在     Reject Request STAFF Convoy 成功！");
			}else{
				//logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
				throw new RuntimeException("Reject Staff Name Card Error");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			super.rollbackTransaction();//回滚事务
			logger.error("用户 =="+adminUsername+"===在    Reject Request Staff Convoy异常!"+e);
			logger.error("用户 =="+adminUsername+"===在    Reject Request Staff Convoy异常--->数据回滚!");
			num=-1;
		}finally{
			super.closeConnection();
		}
		return num;
	}
	
	public int delstaffnamecard(String refno,String adminUsername,String urgentDate) throws SQLException {
		int num=-1;
		try{
			
			String sql="update request_staff_convoy set card_type='D', shzt='D' where refno="+refno+"  and  shzt='S' ";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Delete_Staff','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
			
			openTransaction();//开启手动控制事物
			num=batchExcute(new String[]{sql,sql2});
			logger.info("用户 =="+adminUsername+"===在   Delete Request STAFF Convoy 时    ====SQL:\r\n"+sql+"\r\n"+sql2);
			if(num>1){
				logger.info("用户 =="+adminUsername+"===在     Delete Request STAFF Convoy 成功！");
			}else{
				//logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
				throw new RuntimeException("");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			super.rollbackTransaction();//回滚事务
			logger.error("用户 =="+adminUsername+"===在    Delete Request Staff Convoy异常!"+e);
			logger.error("用户 =="+adminUsername+"===在    Delete Request Staff Convoy异常--->数据回滚!");
			num=-1;
		}finally{
			super.closeConnection();
		}
		return num;
	}
	public int deldeptnamecard(String refno,String adminUsername,String urgentDate) throws SQLException {
		int num=-1;
		try{
			
			String sql="update request_staff_convoy set  card_type='D', shzt='D' where refno="+refno+"  and  shzt='E' ";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Delete_Dept','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
			openTransaction();//开启手动控制事物
			num=batchExcute(new String[]{sql,sql2});
			logger.info("用户 =="+adminUsername+"===在   Delete Dept Request  Convoy 时    ====SQL:\r\n"+sql+"\r\n"+sql2);
			if(num>1){
				logger.info("用户 =="+adminUsername+"===在     Delete Dept Request  Convoy 成功！");
			}else{
				//logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
				throw new RuntimeException("");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			super.rollbackTransaction();//回滚事务
			logger.error("用户 =="+adminUsername+"===在    Delete Dept Request  Convoy异常!"+e);
			logger.error("用户 =="+adminUsername+"===在    Delete Dept Request  Convoy异常--->数据回滚!");
			num=-1;
		}finally{
			super.closeConnection();
		}
		return num;
	}
	public int delhrnamecard(String refno,String adminUsername,String urgentDate) throws SQLException {
		int num=-1;
		try{
			
			String sql="update request_staff_convoy set card_type='D', shzt='D' where refno="+refno+"  and  shzt='R' ";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Delete_HR','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
			
			openTransaction();//开启手动控制事物
			num=batchExcute(new String[]{sql,sql2});
			logger.info("用户 =="+adminUsername+"===在   Delete HR Request  Convoy 时    ====SQL:\r\n"+sql+"\r\n"+sql2);
			if(num>1){
				logger.info("用户 =="+adminUsername+"===在     Delete HR Request  Convoy 成功！");
			}else{
				//logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
				throw new RuntimeException("");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			super.rollbackTransaction();//回滚事务
			logger.error("用户 =="+adminUsername+"===在    Delete HR Request  Convoy异常!"+e);
			logger.error("用户 =="+adminUsername+"===在    Delete HR Request  Convoy异常--->数据回滚!");
			num=-1;
		}finally{
			super.closeConnection();
		}
		return num;
	}
	
	
	/**
	 * Dept Reject econvoy Staff 提交的申请
	 * @param staffcode
	 * @param urgentDate
	 * @param adminUsername
	 * @param refno
	 * @return
	 * @throws SQLException
	 */
	public int rejectStaffRequestConvoyDept(String staffcode, String urgentDate,String adminUsername,String refno,String remark,String nameE,String nameC) throws SQLException {
		int num=-1;
		try{
			String sql="update request_staff_convoy set shzt='N',remark=remarkcons,remarkcons='"+remark+"' where staff_code='"+staffcode+"' and UrgentDate='"+urgentDate+"' and shzt='S' and shzt!='N'";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Reject_Dept','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
			String sql3="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+adminUsername+"'," +
			"'"+refno+"','"+staffcode+"','"+nameE+"','"+nameC+"','N','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','')";
			
			openTransaction();//开启手动控制事物
			num=batchExcute(new String[]{sql,sql2,sql3});
			logger.info("用户 =="+adminUsername+"===在 Dept Reject Request STAFF Convoy 时    ====SQL:\r\n"+sql+"\r\n"+sql2);
			if(num>2){
				logger.info("用户 =="+adminUsername+"===在 Dept  Reject Request STAFF Convoy 成功！");
			}else{
				//logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
				throw new RuntimeException("");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			super.rollbackTransaction();//回滚事务
			logger.error("用户 =="+adminUsername+"===在 Dept   Reject Request Staff Convoy异常!"+e);
			logger.error("用户 =="+adminUsername+"===在 Dept   Reject Request Staff Convoy异常--->数据回滚!");
			num=-1;
		}finally{
			super.closeConnection();
		}
		return num;
	}
	/**
	 * HR Reject econvoy Staff 提交的申请
	 * @param staffcode
	 * @param urgentDate
	 * @param adminUsername
	 * @param refno
	 * @return
	 * @throws SQLException
	 */
	public int rejectStaffRequestConvoyHR(String staffcode, String urgentDate,String adminUsername,String refno,String remark,String nameE,String nameC) throws SQLException {
		int num=-1;
		try{
			String sql="update request_staff_convoy set shzt='N',remark=remarkcons,remarkcons='"+remark+"' where staff_code='"+staffcode+"' and UrgentDate='"+urgentDate+"' and shzt='E'  and shzt!='N'";
			String sql2="insert into request_staff_convoy_operation(refno,urgentDate,operationType,operationName,operationDate) values('"+refno+"','"+urgentDate+"','Reject_HR','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
			String sql3="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)values('"+adminUsername+"'," +
				"'"+refno+"','"+staffcode+"','"+nameE+"','"+nameC+"','N','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','')";
			openTransaction();//开启手动控制事物
			num=batchExcute(new String[]{sql,sql2,sql3});
			logger.info("用户 =="+adminUsername+"===在  HR  Reject Request STAFF Convoy 时    ====SQL:\r\n"+sql+"\r\n"+sql2);
			if(num>2){
				logger.info("用户 =="+adminUsername+"===在  HR   Reject Request STAFF Convoy 成功！");
			}else{
				//logger.info("用户 =="+adminUsername+"===在    Reject Request STAFF Convoy 失败！");
				throw new RuntimeException("");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			super.rollbackTransaction();//回滚事务
			logger.error("用户 =="+adminUsername+"===在  HR  Reject Request Staff Convoy异常!"+e);
			logger.error("用户 =="+adminUsername+"===在  HR  Reject Request Staff Convoy异常--->数据回滚!");
			num=-1;
		}finally{
			super.closeConnection();
		}
		return num;
	}
	
	public List<Map<String,Object>> queryStaffRequestConvoy(String staffNo) throws SQLException{
		
	    List<Map<String, Object>> list = null;
	    Connection con=null;
		try {
			con=DBManager_oracle.getCon();
			list = DBManager_oracle.rsMapper(DBManager_oracle.executeQuery("select * from V_CUST_VSMART_BPPS where employee_status='Active' and EMPLOYEE_ID = '"+staffNo+"' "));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager_oracle.closeCon(con);
			closeConnection();
		}
	
	    return list;
	}
	
	public static void main(String[] args) throws Exception {
		Connection con= DBManager_oracle.getCon();
		List<Map<String, Object>> list = DBManager_oracle.rsMapper(DBManager_oracle.executeQuery("select * from V_CUST_VSMART_BPPS where EMPLOYEE_ID = 'KL0176' "));
		System.out.println(list);
	}
	
	
	@Deprecated
	public String queryStaffRequestConvoy1(String staffNo) throws SQLException{
	    List<Map<String, Object>> list = null;
	    Connection con=null;
	    String str="";
		try {
			con=DBManager_oracle.getCon();
			list = DBManager_oracle.rsMapper(DBManager_oracle.executeQuery("select department_code from V_CUST_VSMART_BPPS where employee_status='Active' and EMPLOYEE_ID = '"+staffNo+"' "));
			if(!Util.objIsNULL(list)&list.size()>0){
				str=list.get(0).get("DEPARTMENT_CODE")+"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager_oracle.closeCon(con);
			//closeConnection();
		}
	
	    return str;
	 }
	
	public String queryStaffRequestGrade(String staffNo) throws SQLException{
		List<Map<String, Object>> list = null;
		Connection con=null;
		String str="";
		try {
			con=DBManager_oracle.getCon();
			list = DBManager_oracle.rsMapper(DBManager_oracle.executeQuery("select GRADE from V_CUST_VSMART_BPPS where employee_status='Active' and EMPLOYEE_ID = '"+staffNo+"' "));
			if(!Util.objIsNULL(list)&list.size()>0){
				str=list.get(0).get("GRADE")+"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager_oracle.closeCon(con);
			//closeConnection();
		}
		
		return str;
	}
	
	
/*	public  String  getPositionCode(String positioncode) throws SQLException{
		String enPosition = null;
		try {
			//conn = DBManager.getCon();
			//String sql="select positionEn from positionlist_staff where positioncode = '"+positioncode+"'";
			Map<String,Object> map=findMap("select positionEn from positionlist_staff where status='Y' and positioncode=? limit 0,1", new Object[]{positioncode});
			//ps=conn.prepareStatement(sql);
			//rs = ps.executeQuery();
			//while(rs.next()){
			//	enPosition = rs.getString(1);

			//}
			if(map.size()>0){
				enPosition=map.get("positionEn")+"";
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			super.closeConnection();
		}
		 return enPosition;
	}*/
	
	public  String  getLocation(String staffcode) throws SQLException{
		String location = null;
		try {
			Map<String,Object> map=findMap("select Location from staff_list where staffcode=? limit 0,1", new Object[]{staffcode});
			//ps=conn.prepareStatement(sql);
			//rs = ps.executeQuery();
			//while(rs.next()){
			//	enPosition = rs.getString(1);

			//}
			if(map.size()>0){
				location=map.get("Location")+"";
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			super.closeConnection();
		}
		 return location;
	}
	
/*	public  String  getDepartmentCode(String departmentcode) throws SQLException{
		String enDepartment = null;
		try {
			//conn = DBManager.getCon();
			//String sql="select positionEn from positionlist_staff where positioncode = '"+positioncode+"'";
			Map<String,Object> map=findMap("select departcode from departmentlist_staff where status='Y' and departcode=? limit 0,1", new Object[]{departmentcode});
			//ps=conn.prepareStatement(sql);
			//rs = ps.executeQuery();
			//while(rs.next()){
			//	enPosition = rs.getString(1);
			
			//}
			if(map.size()>0){
				enDepartment=map.get("departmentcode")+"";
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			super.closeConnection();
		}
		return enDepartment;
	}*/
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		 return (T) zhuanhuan(rs, RequestStaffConvoyBean.class);
	}

	public int getConvoyRows(String name, String code, String startDate,
			String endDate, String location, String urgentCase, String ET,
			String layoutSelect, String payer, String isverify) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<RequestStaffConvoyBean> queryRequstConvoyList(String name,
			String code, String startDate, String endDate, String location,
			String urgentCase, String ET, String layoutSelect, String payer,
			Page page, String isverify) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RequestStaffConvoyDetial> findNameCardDetail(String staffrefno)throws SQLException {
		List<RequestStaffConvoyDetial> detial=new ArrayList<RequestStaffConvoyDetial>();
		List<Map<String,Object>> map=new ArrayList<Map<String,Object>>();
		try{
			String sql="select *from request_staff_convoy_detial a LEFT JOIN request_staff_convoy_state b " +
					" on a.state=b.state where a.staffrefno=? ORDER BY b.sequence";
			map=findListMap(sql,new Object[]{staffrefno});
			
			for (int i = 0; i <map.size(); i++) {
				detial.add(new RequestStaffConvoyDetial(
						(String)map.get(i).get("username"),
						(Integer)map.get(i).get("staffrefno"),
						(String)map.get(i).get("staffcode"),
						(String)map.get(i).get("staffnameE"),
						(String)map.get(i).get("staffnameC"),
						(String)map.get(i).get("state"),
						(String)map.get(i).get("creator"),
						(String)map.get(i).get("createdate"),
						(String)map.get(i).get("remark")));
			}
			/*ps=conn.prepareStatement(sql);
			ps.setString(1, staffrefno);
			rs=ps.executeQuery();
			while(rs.next()){
				detial.add(new RequestStaffConvoyDetial(
						rs.getString("username"),
						rs.getInt("staffrefno"),
						rs.getString("staffcode"),
						rs.getString("staffnameE"),
						rs.getString("staffnameC"),
						rs.getString("state"),
						rs.getString("creator"),
						rs.getString("createdate"),
						rs.getString("remark")));
			}
			rs.close();*/
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(conn);
			super.closeConnection();
			super.closeConnection();
		}
		return detial;
	}

	public String uploadNameCardDetial(List<List<Object>> list,String user)throws Exception {
		int num=0;
		int num2=0;
		int num3=0;
		int line=0;
		boolean s=true;
		String xx="";
		String staffCode="";
		String staffNameE="";
		String staffNameC="";
		String addState="";
		List<String> state=new ArrayList<String>();
		try{
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			String sql="";
			openTransaction();
		for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(0)+"")){
				line=i;
				//-->状态为Delivery、Receive才允许上传
				if(list2.get(1).toString().equalsIgnoreCase("Delivery")||list2.get(1).toString().equalsIgnoreCase("Receive")){
					if(list2.get(1).toString().equalsIgnoreCase("Delivery")){
						addState="D";
					}
					if(list2.get(1).toString().equalsIgnoreCase("Receive")){
						addState="G";
					}
					state=findAllByCode(list2.get(0).toString());//-->判断已存在的code中，状态是否相同,相同的不保存
					for (int j = 0; j < state.size(); j++) {
						if(state.get(j).toString().equalsIgnoreCase(addState)){
							s=false;
						}else{
							s=true;
						}
					}
					if(s){
						String sql3="select staff_code,name,name_chinese from request_staff_convoy where refno='"+list2.get(0)+"' ";
						Map<String,Object> map=findMap(sql3);
						if(map.size()>0){
							staffCode=(String)map.get("staff_code");
							staffNameE=(String)map.get("name");
							staffNameC=(String)map.get("name_chinese");
						}
						sql="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)" +
								"values('"+user+"','"+list2.get(0)+"','"+staffCode+"','"+staffNameE+"','"+staffNameC+"','"+addState+"','"+user+"','"+list2.get(2)+"','')";
						num+=update2(sql);
						if(num<(i+0)){
							throw new RuntimeException();
						}
					}else{
						num3+=1;
					}
					
				}else{
					num2+=1;
				}
				
			}	
		}
			sumbitTransaction();
			xx=Util.getMsgJosnObject("success", "成功条数:"+num+"条,其中有"+num3+"条已存在,"+num2+"条状态格式不正确未上传");
		}catch (Exception e) {
			xx=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			super.closeConnection();
		}
		return xx;
	}
	
	//判断itemcode是否存在
	public List<String> findAllByCode(String itemcode) {
		List<String> state=new ArrayList<String>();
		Connection con=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select state from request_staff_convoy_detial where staffrefno ='"+itemcode+"'");
			logger.info("Query request_staff_convoy_detial SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				state.add(rs.getString(1));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("c_companyasset_item 中根据条件查询数据个数时出现异常："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return state;
	}
/*****************************************approve的定时提示邮件 START******************************************************/
	
	/**
	 * 定时扫描staffnamecar发出approve通知邮件后，七日后未操作者再次提醒-1点
	 * @return
	 */
	public String timeTaskApprovePrompt(){
		String result="";
		int num=-1;
		Util.printLogger(logger,"开始执行指定任务01点-->扫描StaffNameCard-->approve状态 并处理状态");    
		try{	
			num=approvePrompt();
			if(num>=0){
				result="success";
				Util.printLogger(logger,"执行指定任务01点-->扫描扫描StaffNameCard-->approve状态 并处理状态成功");    
			}else{
				throw new Exception();
			}
		}catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger,"执行指定任务01点-->扫描扫描StaffNameCard-->approve状态 并处理状态 失败"+e.getMessage());
		}
		return result;
	}
	
	
	
	public int approvePrompt() throws Exception{
		int a=0;
		String body="";
		String subject="";
		String []email=new String [2];
		QueryStaffRequstDao qsrDao=new QueryStaffRequstDaoImpl();
		List<RequestStaffConvoyApprovePrompt> approve=new ArrayList<RequestStaffConvoyApprovePrompt>();
		try{
			approve=findEmailRecord();
			if(approve.size()>0){
				for (int i = 0; i < approve.size(); i++) {
					
					email=qsrDao.findSupervisorEmail(approve.get(i).getStaff_code());//获取staff上级邮箱
					
					if(!Util.objIsNULL(approve.get(i).getSendDate())){
						//-->根据不同状态(state)发送邮件
						//SendMail.send(email[1], approve.get(i).getEmailAddress(),approve.get(i).getEmailBody(), "COAT", "email.ftl", "");
						if(approve.get(i).getShzt().equalsIgnoreCase("S")){
							SendMail.send("Staff name card request approval.",email[1],email[0],"","",approve.get(i).getEmailBody(),"COAT","email.ftl","");
						}else{
							//edie 2018-05-08 提出ADM不收该邮件
							//SendMail.send("Supervisor/Department Head Has been Approval.",SendMail.getProValue("public.staffnamecard.szoadm.eamil"),approve.get(i).getEmailBody(),"COAT","email.ftl","");
						}
					}else{
						//-->根据不同状态(shzt)发送邮件
						if(approve.get(i).getShzt().equalsIgnoreCase("S")){
							body="Dear Supervisor/Department Head,<br/><br/>"
								+"&nbsp;&nbsp;The name card request initiated by "+approve.get(i).getName()+"  "+approve.get(i).getStaff_code()+"  is in Supervisor/Department Head approval status."
								+"<br/><br/>"
								+"Please visit and approve at:<br/>";
								if(Util.getProValue("public.system.uat")=="true"){
									body+="<a href='http://www.econvoy.com/group/convoy/coat-test?handle=deptapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
											+"Thank You.";//通知dept head 审核
								}else{
									body+="<a href='http://www.econvoy.com/group/convoy/office-admin?handle=deptapproval'>http://www.econvoy.com/group/convoy/office-admin</a><br/>"
											+"Thank You.";//通知dept head 审核	
								}
								
							subject="COAT: "+approve.get(i).getName()+" - Name Card Request";
							SendMail.send("Staff name card request approval.",email[1],email[0],"","",approve.get(i).getEmailBody(),"COAT","email.ftl","");
							/***********************************保存邮件记录表 start**********************************************/
							String sql="insert into request_staff_convoy_approve_prompt(refno,emailBody,emailTitle,emailAddress,sendDate,state,remark)values(" 
								+" '"+approve.get(i).getRefno()+"',?,?,?,'"+DateUtils.getNowDateTime()+"','S','')";
							int f=update(sql,new Object[]{body,subject,email});
							if(f<0){
								throw new RuntimeException("Save the card status failed!"); 
							}
							logger.info("request_staff_convoy_approve_prompt信息保存方法SQL:"+sql);
								
							/***********************************保存邮件记录表 end**********************************************/
						}else if(approve.get(i).getShzt().equalsIgnoreCase("E")){
							//--->这里取消HR审核步骤，邮件直接由Department发送给szoadm
							body="Dear SZOADM,<br/><br/>" +
							"  HRD 于"+DateUtils.getDateToday()+"审核通过了 "+approve.get(i).getName()+"  "+approve.get(i).getStaff_code()+" 于 "+approve.get(i).getUrgentDate()+"提交的name card 申请。";
							//edie 2018-05-08 提出ADM不收该邮件
							//SendMail.send("Supervisor/Department Head Has been Approval", SendMail.getProValue("public.staffnamecard.szoadm.eamil"), body, "COAT", "email.ftl", "");
						
							/***********************************保存邮件记录表 start**********************************************/
							String sql="insert into request_staff_convoy_approve_prompt(refno,emailBody,emailTitle,emailAddress,sendDate,state,remark)values(" 
								+" '"+approve.get(i).getRefno()+"',?,'HRD Has been Approval',?,'"+DateUtils.getNowDateTime()+"','E','')";
							int b=update(sql,new Object[]{body,SendMail.getProValue("public.staffnamecard.szoadm.eamil")});
							if(b<0){
								throw new RuntimeException("Update the card status failed!"); 
							}
							logger.info("request_staff_convoy_approve_prompt信息保存方法SQL:"+sql);
							/***********************************保存邮件记录表 end**********************************************/
						}
					}
					a++;
				}
				approve=null;
			}
			
		}catch (Exception e) {
			a=-1;
			throw new Exception(e);
		}finally{
			try {
				super.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	
	}

	/**
	 * -->查询邮件发送日期比较当天日期大于或者等于7天的数据
	 * */
	public List<RequestStaffConvoyApprovePrompt> findEmailRecord(){
		List<RequestStaffConvoyApprovePrompt> detial=new ArrayList<RequestStaffConvoyApprovePrompt>();
		String sysTime=Util.getProValue("public.system.onlineTime");//系统的上线日期
		//String sysTime="2016-02-25";
		try{
			conn=DBManager.getCon();
			String sql="select *from (select a.refno,a.UrgentDate,a.shzt,a.staff_code,a.name,b.state,b.sendDate,b.emailBody,b.emailTitle,b.emailAddress,if(sendDate is null,a.UrgentDate,b.sendDate)as maildate from request_staff_convoy a" +
					" LEFT OUTER JOIN request_staff_convoy_approve_prompt b on (a.refno=b.refno and a.shzt=b.state)" +
					//" where (a.shzt='S' or a.shzt='E' or a.shzt='R') ORDER BY b.sendDate,a.UrgentDate )x where (to_days(now()) - to_days(maildate))>=7 " +
					" where (a.shzt='S' or a.shzt='E') ORDER BY b.sendDate,a.UrgentDate )x" +
					" where (to_days(now()) - to_days(maildate))>='"+Util.getProValue("SpecifiedTask.namecard.staff.sendDate.hour")+"' " +
					" and DATE_FORMAT(maildate,'%Y-%m-%d')>=DATE_FORMAT('"+sysTime+"','%Y-%m-%d') ";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				detial.add(new RequestStaffConvoyApprovePrompt(
						rs.getString("refno"),
						rs.getString("emailBody"),
						rs.getString("emailTitle"),
						rs.getString("emailAddress"),
						rs.getString("sendDate"),
						rs.getString("state"),
						"",
						rs.getString("UrgentDate"),
						rs.getString("shzt"),
						rs.getString("staff_code"),
						rs.getString("name"),
						rs.getString("maildate")
				));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return detial;
	
	}
/*****************************************approve的定时提示邮件 END******************************************************/	
	
	public int addState(String refno, String state, String approveDate,String user)throws SQLException {
		String staffCode="";
		String staffNameE="";
		String staffNameC="";
		List<String> list=new ArrayList<String>();
		int num=-1;
		boolean s=false;
		try {
			openTransaction();
			list=findAllByCode(refno);
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).toString().equalsIgnoreCase(state)){
					s=false;
				}else{
					s=true;
				}
			}
			if(s){
				String sql="select staff_code,name,name_chinese from request_staff_convoy where refno='"+refno+"' ";
				Map<String ,Object>map=findMap(sql);
				if(map.size()>0){
					staffCode=(String)map.get("staff_code");
					staffNameE=(String)map.get("name");
					staffNameC=(String)map.get("name_chinese");
				}
				sql="insert into request_staff_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)" +
						"values('"+user+"','"+refno+"','"+staffCode+"','"+staffNameE+"','"+staffNameC+"','"+state+"','"+user+"','"+approveDate+"','')";
				num=update2(sql);
				if(num<1){
					throw new RuntimeException("The new state failure!");
				}
			}else{
				num=-2;
			}
			sumbitTransaction();
		}catch (Exception e) {
			num=-1;
			e.printStackTrace();
			try {
				super.rollbackTransaction();//异常回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			super.closeConnection();
		}
		return num;
	}
	
	
	public List<RequestStaffBean> getOldRecord(String staffcode) throws SQLException {
		List<RequestStaffBean> list=new ArrayList<RequestStaffBean>();
		try{
			String sql="select * from request_staff where staff_code='"+staffcode+"'" +
					//" and date_format(UrgentDate,'%Y')=date_format(now(),'%Y') " +
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

	public List<RequestStaffCompanyView> getCompany(){
		List<RequestStaffCompanyView> list=new ArrayList<RequestStaffCompanyView>();
		try{
			String sql="select * from request_staff_convoy_company_view ";
			List<Map<String, Object>> listMap=findListMap(sql,new Object[]{});
			if(listMap.size()>0){
				for (int i = 0; i < listMap.size(); i++) {
					list.add(new RequestStaffCompanyView(
							Util.objIsNULL(listMap.get(i).get("company_ID"))?"":listMap.get(i).get("company_ID").toString(),	
									Util.objIsNULL(listMap.get(i).get("company_Type"))?"":listMap.get(i).get("company_Type").toString(),	
											Util.objIsNULL(listMap.get(i).get("englishName"))?"":listMap.get(i).get("englishName").toString(),	
													Util.objIsNULL(listMap.get(i).get("chineseName"))?"":listMap.get(i).get("chineseName").toString(),	
											Util.objIsNULL(listMap.get(i).get("reamrk"))?"":listMap.get(i).get("reamrk").toString()	
					));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				super.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public String getCompany_EnglishName(String type){
		String result="";
		try{
			openConnection();
			String sql="select englishName from request_staff_convoy_company_view where company_Type=? ";
			List<Map<String, Object>> listMap=findListMap(sql,new Object[]{type});
			if(listMap.size()>0){
				result=listMap.get(0).get("englishName")+"";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				super.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 定时远程数据库中获取<顾问上级信息>，并且保存到本地数据库
	 * @return
	 */
	public String timeTaskGetSupervisor(){
		String result="";
		int num=-1;
		Util.printLogger(logger,"开始执行指定任务06点-staffNameCard 获取顾问上级信息");    
		try{	
			num=getSupervisor();
			if(num>=0){
				result="success";
				Util.printLogger(logger,"开始执行指定任务06点-staffNameCard 获取顾问上级信息-->成功");    
			}else{
				Util.printLogger(logger,"开始执行指定任务06点-staffNameCard 获取顾问上级信息-->失败"); 
				throw new Exception();
			}
		}catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger,"执行指定任务06点-staffNameCard 获取顾问上级信息 失败"+e.getMessage());
		}
		
		return result;
		
	}
	
	
	/**
	 * 远程数据库中获取顾问上级信息，并且保存到本地数据库
	 * @throws Exception 
	 */
	public int getSupervisor() throws Exception {
		System.out.println("Start============>获取顾问上级信息");
		int num=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		try{
			openTransaction();
			//先把表格数据清空
			String sql2="delete from request_staff_convoy_supervisor_view";
			num=update2(sql2);
			if(num<0){
				throw new Exception("清空request_staff_convoy_supervisor_view表失败!");
			}
			
			conn=DBManager_oracle.getCon("CONVOYUAT");
			String sql ="select * from CONVOYPROD.V_CUST_NAME_CARD  ";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				String sql3="insert into request_staff_convoy_supervisor_view (sub_Code,supervisor_ID,supervisor_Email,supercisor2_ID," +
				"supervisor2_Email,remark)values(?,?,?,?,?,?)";
				num=update2(sql3,new Object[]{
					Util.objIsNULL(rs.getString("Employee_id"))?"":rs.getString("Employee_id").toString(),	
					Util.objIsNULL(rs.getString("SUPERVISOR_ID"))?"":rs.getString("SUPERVISOR_ID").toString(),	
					Util.objIsNULL(rs.getString("SUERVISOR_EMAIL"))?"":rs.getString("SUERVISOR_EMAIL").toString(),	
					Util.objIsNULL(rs.getString("SUPERVISOR2_ID"))?"":rs.getString("SUPERVISOR2_ID").toString(),	
					Util.objIsNULL(rs.getString("SUPERVISOR2_EMAIL"))?"":rs.getString("SUPERVISOR2_EMAIL").toString(),	
					""	
				});
				if(num<0){
					throw new Exception("插入request_staff_convoy_supervisor_view表失败!");
				}
			}
			sumbitTransaction();
			conn.close();
			closeConnection();
		}catch(Exception e){
			try {
				rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			System.out.println("End============>获取顾问上级信息");
		}
		return num;
	}

	/**
	 * 定时远程数据库中获取<公司信息>，并且保存到本地数据库
	 * @return
	 */
	public String timeTaskGetCompany(){
		String result="";
		int num=-1;
		Util.printLogger(logger,"开始执行指定任务06点-staffNameCard 获取公司信息");    
		try{	
			num=getForm_Company();
			if(num>=0){
				result="success";
				Util.printLogger(logger,"开始执行指定任务06点-staffNameCard 获取公司信息-->成功");    
			}else{
				Util.printLogger(logger,"开始执行指定任务06点-staffNameCard 获取公司信息-->失败");    
				throw new Exception();
			}
		}catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger,"执行指定任务06点-staffNameCard 获取公司信息 失败"+e.getMessage());
		}
		return result;
		
	}
	
	/**
	 * 远程数据库中获取顾问上级信息，并且保存到本地数据库
	 * @throws Exception 
	 */
	public int getForm_Company() throws Exception {
		System.out.println("Start============>获取公司类型");
		int num=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		try{
			openTransaction();
			//先把表格数据清空
			String sql2="delete from request_staff_convoy_company_view";
			num=update2(sql2);
			if(num<0){
				throw new Exception("清空request_staff_convoy_company_view表失败!");
			}
			String sql ="select * from company_view ";
			conn=DBManager_oracle.getCon("1");
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				String sql3="insert into request_staff_convoy_company_view (company_ID,company_Type,englishName,chineseName,remark)values(?,?,?,?,?)";
				num=update2(sql3,new Object[]{"",rs.getString("CompanyCode"),rs.getString("englishName"),rs.getString("chineseName"),""});
				if(num<0){
					throw new Exception("插入request_staff_convoy_company_view表失败!");
				}
			}
			
			sumbitTransaction();
			conn.close();
			super.closeConnection();
		}catch(Exception e){
			try {
				rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new Exception(e);
		}finally{
			System.out.println("End============>获取公司类型");
		}
		return num;
	}

	public String findCompany(String staffNO) {
	    List<Map<String, Object>> list = null;
	    Connection con=null;
	    String str="";
		try {
			System.out.println("----------"+staffNO);
			
			con=DBManager_oracle.getCon();
			list = DBManager_oracle.rsMapper(DBManager_oracle.executeQuery("select COMPANY_CODE from V_CUST_VSMART_BPPS where employee_status='Active' and EMPLOYEE_ID = '"+staffNO+"' "));
			System.out.println("--->"+list.size());
			if(!Util.objIsNULL(list)&list.size()>0){
				str=list.get(0).get("COMPANY_CODE")+"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager_oracle.closeCon(con);
			//closeConnection();
		}
	
	    return str;
	 }
}
