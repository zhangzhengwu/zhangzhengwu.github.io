package dao.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.DBManager;
import util.DateUtils;
import util.ExcelTools;
import util.Page;
import util.SendMail;
import util.Util;
import dao.RecruitmentQueryHrDao;
import entity.CRecruitmentAuxiliary;
import entity.C_Payment;
import entity.C_Recruitment_detail;
import entity.C_Recruitment_list;
import entity.C_Recruitment_operation;
import entity.C_Recruitment_order;
import entity.Excel;
import entity.Recruitment_list;

public class RecruitmentQueryHrDaoImpl implements RecruitmentQueryHrDao {

	   Connection connection=null;
	   PreparedStatement ps=null;
	   ResultSet rs=null; 
	   Logger logger = Logger.getLogger(RecruitmentQueryHrDaoImpl.class);
	   
	   //周四将确认要发广告的List发给SSC
	   public String timeTaskSendRecruitmentListToSSC(){
			String result = "";
			 //定时任务只在星期四发起
			if(DateUtils.getWeek(DateUtils.getNowDateTime()).equals("4")){
				try{
					Util.printLogger(logger,"开始执行指定任务-获取确认要发广告的List");    
					List<C_Recruitment_order> list=getConfirmRecruitmentList();
					if(!Util.objIsNULL(list)&&list.size()>0){
						list=null;
						result="success";
						Util.printLogger(logger,"指定任务-->获取确认要发广告的List成功!");
					}else{
						Util.printLogger(logger,"指定任务-->获取确认要发广告的List失败，原因：获取远程获取信息时出错!");
						throw new Exception("获取确认要发广告的List为空！");
					} 
					
				}catch(Exception e){
					result=e.getMessage();
					Util.printLogger(logger,"指定任务-->获取确认要发广告的List失败："+e.getMessage());
				}
				
			}else{
				//不在执行定时任务时间，走空方法，返回success;
				result="success";
			}
			return result;	
	   }
	   
	   
	   //周三下午18：00 将所有未确认的申请状态改为 Cancelled
	   public String timeTaskCancelledRecruitmentList(){
		   String result = "";
		   //定时任务只在星期三发起
		   if(DateUtils.getWeek(DateUtils.getNowDateTime()).equals("3")){
			   try{
				   Util.printLogger(logger,"开始执行指定任务-销毁已失效的广告");    
				   List<C_Recruitment_order> list=queryInvalidList();
				   if(!Util.objIsNULL(list)&&list.size()>0){
					   cancelledInvalidList(list);
					   list=null;
					   result="success";
					   Util.printLogger(logger,"指定任务-->销毁已失效的广告成功!");
				   }else{
					   Util.printLogger(logger,"指定任务-->销毁已失效的广告失败，原因：获取远程获取信息时出错!");
					   throw new Exception("销毁已失效的广告List为空！");
				   } 
				   
			   }catch(Exception e){
				   result=e.getMessage();
				   Util.printLogger(logger,"指定任务-->销毁已失效的广告失败："+e.getMessage());
			   }			   
		   }else{
				//不在执行定时任务时间，走空方法，返回success;
				result="success";
			}
		   return result;	
	   }
	   
       
	   
		/**
		 * 定时获取每种广告类型中最早发起的广告申请列表
		 * @return
		 */
		public String timeTaskGetRecruitmentList(){
			String result = "";
			//定时任务只在星期一发起 (一个周期)
			if(DateUtils.getWeek(DateUtils.getNowDateTime()).equals("1")){

				try{
					Util.printLogger(logger,"开始执行指定任务-获取每种广告类型中最早发起的广告申请列表");    
					List<C_Recruitment_order> list=getAllList();
					if(!Util.objIsNULL(list)&&list.size()>0){
						updateList(list);
						list=null;
						result="success";
						Util.printLogger(logger,"指定任务-->获取每种广告类型中最早发起的广告申请列表成功!");
					}else{
						Util.printLogger(logger,"指定任务-->获取每种广告类型中最早发起的广告申请列表失败，原因：获取远程获取信息时出错!");
						throw new Exception("获取每种广告类型中最早发起的广告申请列表为空！");
					} 
					
				}catch(Exception e){
					result=e.getMessage();
					Util.printLogger(logger,"指定任务-->获取每种广告类型中最早发起的广告申请列表失败："+e.getMessage());
				}
				
			}else{
				//不在执行定时任务时间，走空方法，返回success;
				result="success";
			}
			return result;
			
		}

		/**
		 * 广告结束的最后一个工作日（周四）再跟用户回复邮件
		 * @return
		 */
		public String timeTaskMessageRecruitmentList(){
			String result = "";
			//定时任务只在星期四发起
			if(DateUtils.getWeek(DateUtils.getNowDateTime()).equals("4")){
				
				try{
					Util.printLogger(logger,"开始执行指定任务-获取本周即将发完广告的名单");    
					List<C_Recruitment_order> list=getEndSoonList();
					if(!Util.objIsNULL(list)&&list.size()>0){
						sendEmail(list);
						list=null;
						result="success";
						Util.printLogger(logger,"指定任务-->获取本周即将发完广告的名单成功!");
					}else{
						Util.printLogger(logger,"指定任务-->获取本周即将发完广告的名单列表失败，原因：获取远程获取信息时出错!");
						throw new Exception("获取本周即将发完广告的名单列表为空！");
					} 
					
				}catch(Exception e){
					result=e.getMessage();
					Util.printLogger(logger,"指定任务-->获取本周即将发完广告的名单列表失败："+e.getMessage());
				}
				
			}else{
				//不在执行定时任务时间，走空方法，返回success;
				result="success";
			}
			return result;
			
		}
		
		public List<C_Recruitment_order> getAllList() {
			//还原过期的申请

			List<C_Recruitment_order> list=null;
			try{
				connection =DBManager.getCon();
				String sql= " SELECT  x.* from (select o.refno,o.staffcode,o.staffname,o.usertype,o.position,o.contactperson,o.contactemail,o.chargecode,o.chargename,o.date,o.createdate,o.creater,o.status,o.filterdate,l.mediacode,l.medianame from c_recruitment_order o left join c_recruitment_detail d on o.refno=d.refno left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y' and o.`status`='Submitted' and DATE_FORMAT(NOW(),'%Y-%m-%d')>=DATE_FORMAT(o.filterdate ,'%Y-%m-%d') ORDER BY createdate asc )x  GROUP BY mediacode ";
				ps=connection.prepareStatement(sql.toString());
				rs=ps.executeQuery();
				list=new ArrayList<C_Recruitment_order>();
				while(rs.next()){
					  C_Recruitment_order order=new C_Recruitment_order();
					  order.setRefno(rs.getString("refno"));
					  order.setStaffcode(rs.getString("staffcode"));
					  order.setStaffname(rs.getString("staffname"));
					  order.setUsertype(rs.getString("usertype"));			  
					  order.setPosition(rs.getString("position"));
					  order.setContactperson(rs.getString("contactperson"));
					  order.setContactemail(rs.getString("contactemail"));
					  order.setChargecode(rs.getString("chargecode"));
					  order.setChargename(rs.getString("chargename"));
					  order.setDate(rs.getString("date"));
					  order.setCreatedate(rs.getString("createdate"));
					  order.setCreater(rs.getString("creater"));
					  order.setStatus(rs.getString("status"));
					  //把medianame存入到remark中
					  order.setRemark(rs.getString("medianame"));
					  order.setFilterdate(rs.getString("filterdate"));
					  list.add(order);
				}
				rs.close();
			}catch (Exception e) {
				logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(connection);
			}
			
			return list;
		}
		public List<C_Recruitment_order> getEndSoonList() {
			List<C_Recruitment_order> list=null;
			try{
				connection =DBManager.getCon();
				String sql= "select o.refno,o.staffcode,o.staffname,o.contactperson,o.contactemail,o.chargecode,o.chargename,o.date,d.medianame,l.price from c_recruitment_order o left join c_recruitment_detail d on o.refno = d.refno LEFT JOIN c_recruitment_list l on d.mediacode = l.mediacode where  o.`status`='Scheduled' and o.sfyx = 'Y' and o.date = ? ";
				ps=connection.prepareStatement(sql.toString());
				ps.setString(1, DateUtils.getBeforeWeekFridayToThisWeekThursdayDate(DateUtils.getNowDateTime()));
				rs=ps.executeQuery();
				list=new ArrayList<C_Recruitment_order>();
				while(rs.next()){
					C_Recruitment_order order=new C_Recruitment_order();
					order.setRefno(rs.getString("refno"));
					order.setStaffcode(rs.getString("staffcode"));
					order.setStaffname(rs.getString("staffname"));
					order.setContactperson(rs.getString("contactperson"));
					order.setContactemail(rs.getString("contactemail"));
					order.setChargecode(rs.getString("chargecode"));
					order.setChargename(rs.getString("chargename"));
					order.setDate(rs.getString("date"));
					order.setMedianame(rs.getString("medianame"));
					order.setPrice(rs.getString("price"));
					list.add(order);
				}
				rs.close();
			}catch (Exception e) {
				logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(connection);
			}
			
			return list;
		}

		
		public List<C_Recruitment_order> getConfirmRecruitmentList() {
			Excel excel=new Excel();
			List<C_Recruitment_order> list=null;
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			try{
				connection =DBManager.getCon();
				String sql= "select o.refno,o.staffname,o.staffcode,o.createdate,o.date,o.contactperson,o.contactemail,o.chargecode,o.`status`,d.mediacode,d.medianame,p.paymentAount,p.Handleder,l.mediatype,l.price from c_recruitment_order o LEFT join c_recruitment_detail  d on o.refno=d.refno left join c_payment p on o.refno=p.refno left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y' and o.`status`='Scheduled' and o.date=?";
				ps=connection.prepareStatement(sql.toString());
				ps.setString(1, DateUtils.getNearFridayToNextWeekThursdayDate(DateUtils.getNowDateTime()));
				rs=ps.executeQuery();
				list=new ArrayList<C_Recruitment_order>();
				while(rs.next()){
					C_Recruitment_order r_list=new C_Recruitment_order();
					r_list.setRefno(rs.getString("refno"));
					r_list.setStaffcode(rs.getString("staffcode"));
					r_list.setStaffname(rs.getString("staffname"));
					r_list.setMediatype(rs.getString("mediatype"));
					r_list.setMedianame(rs.getString("d.medianame"));
					r_list.setCreatedate(sf.format(DateUtils.StrToDate(rs.getString("createdate"))));
					r_list.setDate(rs.getString("date"));
					r_list.setContactperson(rs.getString("contactperson"));
					r_list.setContactemail(rs.getString("contactemail"));
					r_list.setHandleder(rs.getString("chargecode"));//默认支付人为chargecode
					r_list.setChargecode(rs.getString("chargecode"));
					r_list.setStatus(rs.getString("status"));
					if(!(r_list.getStatus().equals("Completed"))){
			        	  r_list.setPaymentAount("");
						}else{
							 r_list.setPaymentAount(rs.getDouble("paymentAount")+"");
						}
					r_list.setPrice(rs.getString("price"));
					list.add(r_list);
				}
				
			    //把数据交给Excel
			    excel.setExcelContentList(list);	
			    //设置Excel列头
			    excel.setColumns(new String[]{"ref Number","Request Date","Recruiter StaffCode","Recruiter Name","Media Type","Media Name","Positing Period","Contact Person","Contact Email","Unit Price","ChargeCode","Handle By"});
			    //属性字段名称
			    excel.setHeaderNames(new String[]{"refno","createdate","staffcode","staffname","mediatype","medianame","date","contactperson","contactemail","price","chargecode","Handleder"});
			   //sheet名称
			    excel.setSheetname("Recruitment");
			    //文件名称
				excel.setFilename("Recruitment"+System.currentTimeMillis()+".xls");
				//表单生成
				excel.setFilepath(Util.getProValue("file.handle.temp.downpath"));
				ExcelTools.createExcelToPath(excel);
				
				
				//将生成表单已附件形式发送邮件至SSC
				String result = SendMail.send("COAT – Recruitment Advertisement Placement","adminfo@convoy.com.hk",null,null,Util.getProValue("public.system.downlink")+"/upload/temp/"+excel.getFilename(), "this is the next week recruitment form!", "COAT", null, null);
			    JSONObject json=new JSONObject(result);
			    
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
				rs.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(connection);
			}
			return list;
		}
		
		
		/**
		 * 查询已失效的广告申请
		 * @return
		 */
/*		public List<C_Recruitment_order> getInvalidList() {
			Connection con = null;
			PreparedStatement pps=null;
			ResultSet rst=null; 
			List<C_Recruitment_order> list=null;
			try{
				con =DBManager.getCon();
				String sql= "select * from c_recruitment_order  where `status`='Confirmation Request' and sfyx='Y' and datediff(now(), filterdate) >= 1";
				pps=con.prepareStatement(sql.toString());
				rst=pps.executeQuery();
				list=new ArrayList<C_Recruitment_order>();
				while(rst.next()){
					C_Recruitment_order order=new C_Recruitment_order();
					  order.setRefno(rst.getString("refno"));
					  order.setStaffcode(rst.getString("staffcode"));
					  order.setStaffname(rst.getString("staffname"));
					  order.setUsertype(rst.getString("usertype"));			  
					  order.setPosition(rst.getString("position"));
					  order.setContactperson(rst.getString("contactperson"));
					  order.setContactemail(rst.getString("contactemail"));
					  order.setChargecode(rst.getString("chargecode"));
					  order.setChargename(rst.getString("chargename"));
					  order.setDate(rst.getString("date"));
					  order.setCreatedate(rst.getString("createdate"));
					  order.setCreater(rst.getString("creater"));
					  order.setStatus(rst.getString("status"));
					  order.setFilterdate(rst.getString("filterdate"));
					  list.add(order);
				}
				rst.close();
			}catch (Exception e) {
				logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(con);
			}
			
			return list;
		}*/
		
		/**
		 * 查询已失效的广告申请
		 * @return
		 */
		public List<C_Recruitment_order> queryInvalidList() {
			Connection con = null;
			PreparedStatement pps=null;
			ResultSet rst=null; 
			List<C_Recruitment_order> list=null;
			try{
				con =DBManager.getCon();
				String sql= "select refno,staffcode,staffname,usertype,position,contactperson,contactemail,chargecode,chargename,date,createdate,creater,status,filterdate from c_recruitment_order  where `status`='Confirmation Request' and sfyx='Y'";
				pps=con.prepareStatement(sql.toString());
				rst=pps.executeQuery();
				list=new ArrayList<C_Recruitment_order>();
				while(rst.next()){
					C_Recruitment_order order=new C_Recruitment_order();
					order.setRefno(rst.getString("refno"));
					order.setStaffcode(rst.getString("staffcode"));
					order.setStaffname(rst.getString("staffname"));
					order.setUsertype(rst.getString("usertype"));			  
					order.setPosition(rst.getString("position"));
					order.setContactperson(rst.getString("contactperson"));
					order.setContactemail(rst.getString("contactemail"));
					order.setChargecode(rst.getString("chargecode"));
					order.setChargename(rst.getString("chargename"));
					order.setDate(rst.getString("date"));
					order.setCreatedate(rst.getString("createdate"));
					order.setCreater(rst.getString("creater"));
					order.setStatus(rst.getString("status"));
					order.setFilterdate(rst.getString("filterdate"));
					list.add(order);
				}
				rst.close();
			}catch (Exception e) {
				logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(con);
			}
			
			return list;
		}
		
		/**
		 * 更新申请状态并发送邮件提醒
		 * @param list
		 */
		public void updateList(List<C_Recruitment_order> list){
			Connection con = null;
			PreparedStatement ps = null;
			try{
				
				con=DBManager.getCon();
				con.setAutoCommit(false);//禁止自动提交事务
				
				for(int i=0;i<list.size();i++){
					C_Recruitment_order orderlist=list.get(i);
					
					//生成排期时间 距离当日最近的周五至下一周周四的时间段
					String date = DateUtils.getNearFridayToNextWeekThursdayDate(DateUtils.getNowDateTime());
					//排除已确认有发布的同类型广告
					if(!Legitimate(getMediaCodeByMediaName(orderlist.getRemark()),date)){
						continue;
					}
					//更新c_recruitment_order
					 String sql1="update c_recruitment_order set date=? , status='Confirmation Request' , filterdate=?  where  refno=?";
					 ps=con.prepareStatement(sql1);
					 ps.setString(1, date);
					 ps.setString(2, DateUtils.getNowDateTime());
					 ps.setString(3, orderlist.getRefno());
					 int flag1=ps.executeUpdate();
					 if(flag1<1){
						 throw new RuntimeException("更新c_recruitment_order 状态为Confirmation Request失败");
					 }
					

						//保存系统操作记录
						C_Recruitment_operation operation=new C_Recruitment_operation();
				        operation.setRefno(orderlist.getRefno());
				    	operation.setOperationType("Confirmation Request");
				    	operation.setOperationName("SYSTEM");
				    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	operation.setOperationDate(sdf.format(new Date()));
					    String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values (?,?,?,?)";
					    ps=con.prepareStatement(SQL);
					    ps.setString(1, operation.getRefno());
					    ps.setString(2, operation.getOperationType());
					    ps.setString(3, operation.getOperationName());
					    ps.setString(4, operation.getOperationDate());
					    int flag2=ps.executeUpdate();
					    if(flag2<1){
							 throw new RuntimeException("保存SYSTEM 更新c_recruitment_order 状态为Confirmation Request操作记录失败");
						}
					    
					    //获取当前日的下一个工作日，周六和周日除外
					    String date1 = DateUtils.getNextDate(DateUtils.getNowDateTime());
					    

						String[] str=new String[2];
					    str[0]=orderlist.getStaffcode();
					    str[1]=orderlist.getChargecode();
					    String  [] email=getEmailByCode(str);
					    String to=email[0];
					    String cc=email[1];
					    
				        //发送邮件
				      	String url = "";
				      	if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				      		url = Util.getProValue("public.system.uatlink");
				      	}else{
				      		url = Util.getProValue("public.system.link");
				      	}
					    
					    //发送邮件
					    String content="Dear "+orderlist.getStaffname()+",<br/>";
					    content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Please confirm the recruitment advertisement placement before 10:00 am on  "+date1+"<br/>";
						content+="<br/>Posting Period: "+date+"<br/>";
					    content+="<br/>Media Name: "+orderlist.getRemark()+"<br/>";
					    content+="<br/>Contact Person: "+orderlist.getContactperson()+"<br/>";
					    content+="<br/>Contact Email: <a href='mailto:"+orderlist.getContactemail()+"'>"+orderlist.getContactemail()+"</a><br/>";
						content+="<br/>Please approve the placement on <a href='"+url+"'>"+url+"</a><br/>";
						content+="<br/>by the due date.  Otherwise, the application shall be deemed withdrawn.<br/>";
					    
					    content+="<br/>Regards,<br/>";
					    content+="Administration Department";
					    
					    String result=SendMail.send("COAT – Recruitment Advertisement Placement",to,cc,"adminfo@convoy.com.hk",null,content,null,null,null);
					    JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
				}
				
				con.commit();
			}catch(Exception e){
				e.printStackTrace();
				try {
					con.rollback();
					logger.error("更新申请状态并发送邮件提醒时 数据异常进行数据回滚");
				} catch (SQLException e1) {
					e1.printStackTrace();
					logger.info("更新申请状态并发送邮件提醒时数据回滚异常   "+e);
				}
			}finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
		/**
		 * 更新Scheduled-->Completed
		 * @param list
		 */
		public void updateScheList(List<C_Recruitment_order> list){
			Connection con = null;
			PreparedStatement ps = null;
			try{
				
				con=DBManager.getCon();
				con.setAutoCommit(false);//禁止自动提交事务
				
				for(int i=0;i<list.size();i++){
					C_Recruitment_order schelist=list.get(i);
					
					//更新c_recruitment_order
					 String sql1="update c_recruitment_order set status='Completed'  where  refno=?";
					 ps=con.prepareStatement(sql1);
					 ps.setString(1, schelist.getRefno());
					 int flag1=ps.executeUpdate();
					 if(flag1<1){
						 throw new RuntimeException("更新c_recruitment_order 状态为Completed失败");
					 }
						//保存系统操作记录
						C_Recruitment_operation operation=new C_Recruitment_operation();
				        operation.setRefno(schelist.getRefno());
				    	operation.setOperationType("Completed");
				    	operation.setOperationName("SYSTEM");
				    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	operation.setOperationDate(sdf.format(new Date()));
					    String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values (?,?,?,?)";
					    ps=con.prepareStatement(SQL);
					    ps.setString(1, operation.getRefno());
					    ps.setString(2, operation.getOperationType());
					    ps.setString(3, operation.getOperationName());
					    ps.setString(4, operation.getOperationDate());
					    int flag2=ps.executeUpdate();
					    if(flag2<1){
							 throw new RuntimeException("保存SYSTEM 更新c_recruitment_order 状态为Completed操作记录失败");
						}

				}
				
				con.commit();
			}catch(Exception e){
				e.printStackTrace();
				try {
					con.rollback();
					logger.error("更新申请状态并发送邮件提醒时 数据异常进行数据回滚");
				} catch (SQLException e1) {
					e1.printStackTrace();
					logger.info("更新申请状态并发送邮件提醒时数据回滚异常   "+e);
				}
			}finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
			
			
		}	
		
		/**
		 * 废除已失效的申请
		 * @param list
		 */
		public void cancelledInvalidList(List<C_Recruitment_order> list){
			Connection con = null;
			PreparedStatement ps = null;
			try{
				con=DBManager.getCon();
				con.setAutoCommit(false);//禁止自动提交事务
				
				for(int i=0;i<list.size();i++){
					C_Recruitment_order orderlist=list.get(i);
					
					//更新c_recruitment_order
					String sql1="update c_recruitment_order set status=? where  refno=?";
					ps=con.prepareStatement(sql1);
					ps.setString(1, "Cancelled");
					ps.setString(2, orderlist.getRefno());
					int flag1=ps.executeUpdate();
					if(flag1<1){
						throw new RuntimeException("更新c_recruitment_order 状态为Cancelled失败");
					}
					
					//保存系统操作记录
					C_Recruitment_operation operation=new C_Recruitment_operation();
					operation.setRefno(orderlist.getRefno());
					operation.setOperationType("Cancelled");
					operation.setOperationName("SYSTEM");
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					operation.setOperationDate(sdf.format(new Date()));
					String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values (?,?,?,?)";
					ps=con.prepareStatement(SQL);
					ps.setString(1, operation.getRefno());
					ps.setString(2, operation.getOperationType());
					ps.setString(3, operation.getOperationName());
					ps.setString(4, operation.getOperationDate());
					int flag2=ps.executeUpdate();
					if(flag2<1){
						throw new RuntimeException("保存SYSTEM 更新c_recruitment_order 状态为Cancelled操作记录失败");
					}
					
					String[] str=new String[2];
					str[0]=orderlist.getStaffcode();
					str[1]=orderlist.getChargecode();
					String  [] email=getEmailByCode(str);
					String to=email[0];
					String cc=email[1]+";adminfo@convoy.com.hk";
					
					
					//发送邮件
					String content="Dear "+orderlist.getStaffname()+",<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;由於您未及時對系統安排的廣告時間進行確認，你的廣告已經失效，<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;請重新發起廣告申請！<br/>";
					
					content+="Regards,<br/>";
					content+="Administration Department<br/>";
					
					String result=SendMail.send("COAT – Recruitment Advertisement Placement",to,cc,null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}
				}
				
				con.commit();
			}catch(Exception e){
				e.printStackTrace();
				try {
					con.rollback();
					logger.error("更新申请状态为Cancelled并发送邮件提醒时 数据异常进行数据回滚");
				} catch (SQLException e1) {
					e1.printStackTrace();
					logger.info("更新申请状态为Cancelled并发送邮件提醒时数据回滚异常   "+e);
				}
			}finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
			
			
		}
		/**
		 * 更新已失效的申请
		 * @param list
		 */
/*		public void updateInvalidList(){
			
			List<C_Recruitment_order> list = getInvalidList();
			if(Util.objIsNULL(list) || list.size()<=0){
				return;
			}
			
			Connection con = null;
			PreparedStatement ps = null;
			try{
				con=DBManager.getCon();
				con.setAutoCommit(false);//禁止自动提交事务
				
				for(int i=0;i<list.size();i++){
					C_Recruitment_order orderlist=list.get(i);
					
					//更新c_recruitment_order
					 String sql1="update c_recruitment_order set date=? , status='Submitted' , filterdate=?  where  refno=?";
					 ps=con.prepareStatement(sql1);
					 ps.setString(1, "");
					 ps.setString(2, DateUtils.getNearFridayDate(DateUtils.getNowDateTime()));//最近的周五
					 ps.setString(3, orderlist.getRefno());
					 int flag1=ps.executeUpdate();
					 if(flag1<1){
						 throw new RuntimeException("更新c_recruitment_order 状态为Submitted失败");
					 }
	
						//保存系统操作记录
						C_Recruitment_operation operation=new C_Recruitment_operation();
				        operation.setRefno(orderlist.getRefno());
				    	operation.setOperationType("Submitted");
				    	operation.setOperationName("SYSTEM");
				    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	operation.setOperationDate(sdf.format(new Date()));
					    String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values (?,?,?,?)";
					    ps=con.prepareStatement(SQL);
					    ps.setString(1, operation.getRefno());
					    ps.setString(2, operation.getOperationType());
					    ps.setString(3, operation.getOperationName());
					    ps.setString(4, operation.getOperationDate());
					    int flag2=ps.executeUpdate();
					    if(flag2<1){
							 throw new RuntimeException("保存SYSTEM 更新c_recruitment_order 状态为Submitted操作记录失败");
						}
					    
						String[] str=new String[2];
					    str[0]=orderlist.getStaffcode();
					    str[1]=orderlist.getChargecode();
					    String  [] email=getEmailByCode(str);
					    String to=email[0];
					    String cc=email[1]+";adminfo@convoy.com.hk";
					    
					    
					    //发送邮件
					    String content="Dear "+orderlist.getStaffname()+",<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;由於您未及時登錄系統確認廣告排期時間，系統為您安排的廣告排期時間已經失效，<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;請重新發起廣告申請！<br/>";
						
						
					    content+="Regards,<br/>";
					    content+="Administration Department<br/>";
					    
					    String result=SendMail.send("COAT – Recruitment Advertisement Placement",to,cc,null,null,content,null,null,null);
					    JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
				}
				
				con.commit();
			}catch(Exception e){
				e.printStackTrace();
				try {
					con.rollback();
					logger.error("更新申请状态并发送邮件提醒时 数据异常进行数据回滚");
				} catch (SQLException e1) {
					e1.printStackTrace();
					logger.info("更新申请状态并发送邮件提醒时数据回滚异常   "+e);
				}
			}finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
			
			
		}*/
		
		
		public void sendEmail(List<C_Recruitment_order> list){
			Connection con = null;
			PreparedStatement ps = null;
			try{
				
				con=DBManager.getCon();
				con.setAutoCommit(false);//禁止自动提交事务
				for(int i=0;i<list.size();i++){
					C_Recruitment_order complist=list.get(i);
					
					//更新c_recruitment_order
					 String sql1="update c_recruitment_order set status='Completed'  where  refno=?";
					 ps=con.prepareStatement(sql1);
					 ps.setString(1, complist.getRefno());
					 int flag1=ps.executeUpdate();
					 if(flag1<1){
						 throw new RuntimeException("更新c_recruitment_order 状态为Completed失败");
					 }
						//保存系统操作记录
						C_Recruitment_operation operation=new C_Recruitment_operation();
				        operation.setRefno(complist.getRefno());
				    	operation.setOperationType("Completed");
				    	operation.setOperationName("SYSTEM");
				    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	operation.setOperationDate(sdf.format(new Date()));
					    String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values (?,?,?,?)";
					    ps=con.prepareStatement(SQL);
					    ps.setString(1, operation.getRefno());
					    ps.setString(2, operation.getOperationType());
					    ps.setString(3, operation.getOperationName());
					    ps.setString(4, operation.getOperationDate());
					    int flag2=ps.executeUpdate();
					    if(flag2<1){
							 throw new RuntimeException("保存SYSTEM 更新c_recruitment_order 状态为Completed操作记录失败");
						}
				
					String[] str=new String[2];
					str[0]=complist.getChargecode();
					String  [] email=getEmailByCode(str);
					String to=email[0];
					
					//发送邮件
					String content="Dear "+complist.getChargename()+",<br/>";
					content+="<br/>";					
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please be informed that "+complist.getPrice()+" will be deducted from ( "+complist.getChargename()+":"+complist.getChargecode()+" ) Commission for the following recruitment advertising placement;<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Posting Period: "+complist.getDate()+"<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Media Name: "+complist.getMedianame()+"<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Contact Person: "+complist.getContactperson()+"<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Contact Email: "+complist.getContactemail()+"<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667. Thank You.<br/>";					
					content+="<br/>";					
					content+="Best Regards,<br/>";
					content+="Administration Department<br/>";
					
					String result=SendMail.send("COAT – Recruitment Advertisement Placement",to,null,null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}
				}
				con.commit();
			}catch(Exception e){
				e.printStackTrace();
				try {
					con.rollback();
					logger.error("更新申请状态并发送邮件提醒时 数据异常进行数据回滚");
				} catch (SQLException e1) {
					e1.printStackTrace();
					logger.info("更新申请状态并发送邮件提醒时数据回滚异常   "+e);
				}
			}finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		}
		
		
		public int del(String name, String refno){
				int num = -1;
				try{
					connection = DBManager.getCon();
					connection.setAutoCommit(false);
					String sql="update c_recruitment_order set sfyx='N', status='Deleted'  where sfyx='Y' and status ='Submitted' and refno=? ";
					ps=connection.prepareStatement(sql);
					ps.setString(1, refno);
				    int flag1 = ps.executeUpdate();
			        if(flag1<0){
					  throw new RuntimeException();
				    }
			        
					String sql2="insert into c_recruitment_operation (refno,operationType,operationName,operationDate) values (?,?,?,?)";
				      ps= connection.prepareStatement(sql2);
		              ps.setString(1, refno);
				      ps.setString(2, "Deleted");
				      ps.setString(3, name);
				      ps.setString(4, DateUtils.getNowDateTime());
				      int flag2 = ps.executeUpdate();
				      if(flag2<1){
						 throw new RuntimeException();
					  }	
				      connection.commit();
					logger.info("delete seat_change_apply success");
					num = 1;
				}catch(Exception e){
					num = -1;
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					logger.error("Delete seat_change_apply信息保存异常！"+e.getMessage());
				}finally{
		            DBManager.closeCon(connection); 
				}
				return num;

		}
		
		public List<C_Recruitment_list> queryMediaName(){
			List<C_Recruitment_list> list = new ArrayList<C_Recruitment_list>();
			try {
				connection = DBManager.getCon();
				String sqlString ="SELECT distinct medianame FROM c_recruitment_list WHERE sfyx='Y' ";
				ps = connection.prepareStatement(sqlString);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					C_Recruitment_list crl = new C_Recruitment_list();
					crl.setMedianame(rs.getString("medianame"));
					list.add(crl);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("c_recruitment_list异常！"+e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("c_recruitment_list异常！"+e);
			}finally{
				DBManager.closeCon(connection);
			}
			return list;
			
		}		
		
		
		
		
	   /**
		 * 根据条件查询订单
		 * @param  date、Staff Code、Staff  Name、refno、Status、Order Type、
		 * @return List<C_Recruitment_order>
		 */
	public List<C_Recruitment_order> find(String date1, String date2,C_Recruitment_order order ,Page page,String medianame) {
		
		  List<C_Recruitment_order> listOrders=new ArrayList<C_Recruitment_order>();
		try{
			connection =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select o.refno,o.staffcode,o.staffname,o.usertype,o.position,o.contactperson,o.contactemail,o.chargecode,o.chargename,o.date,o.createdate,o.creater,o.status,l.medianame from c_recruitment_order o left join c_recruitment_detail d on o.refno=d.refno left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y'");
			if(!(Util.objIsNULL(date1))){
			    sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')>=DATE_FORMAT('"+date1+"','%Y-%m-%d')");
				     }
			 if(!(Util.objIsNULL(date2))){
			  sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')<=DATE_FORMAT('"+date2+"','%Y-%m-%d')");
			  }
          if(!(Util.objIsNULL(order.getStaffcode()))){
        	  sql.append("and o.staffcode like '%"+order.getStaffcode()+"%'");
          }
          if(!(Util.objIsNULL(order.getRefno()))){
        	  sql.append("and o.refno like '%"+order.getRefno()+"%'");
          }
		  if(!(Util.objIsNULL(order.getStaffname()))){
				sql.append("and o.staffname like '%"+order.getStaffname()+"%'");
			}
		  if(!(Util.objIsNULL(order.getStatus()))){
			  sql.append("and o.status='"+order.getStatus()+"'");
		  }
		  if(!(Util.objIsNULL(medianame))){
			  sql.append("and l.medianame like'%"+medianame+"%'");
		  }
		  sql.append("  order by o.createdate desc LIMIT ?,?");
		  ps=connection.prepareStatement(sql.toString());
		  ps.setInt(1,(page.getCurPage()-1)*page.getPageSize());
		  ps.setInt(2, page.getPageSize());
		  rs=ps.executeQuery();
		  while(rs.next()){
			  C_Recruitment_order order2=new C_Recruitment_order();
			  order2.setRefno(rs.getString("refno"));
			  order2.setStaffcode(rs.getString("staffcode"));
			  order2.setStaffname(rs.getString("staffname"));
			  order2.setUsertype(rs.getString("usertype"));			  
			  order2.setPosition(rs.getString("position"));
			  order2.setContactperson(rs.getString("contactperson"));
			  order2.setContactemail(rs.getString("contactemail"));
			  order2.setChargecode(rs.getString("chargecode"));
			  order2.setChargename(rs.getString("chargename"));
			  order2.setDate(rs.getString("date"));
			  order2.setCreatedate(rs.getString("createdate"));
			  order2.setCreater(rs.getString("creater"));
			  order2.setStatus(rs.getString("status"));
			  //把产品名称存入到remark中
			  order2.setRemark(rs.getString("medianame"));
			  listOrders.add(order2);
		  }
		  rs.close();
		}catch (Exception e) {
			logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
			e.printStackTrace();
		}finally{
			  
			DBManager.closeCon(connection);
		}
		
		return listOrders;
	}
	/**
	 * 根据条件查询订单
	 * @param  date、Staff Code、Staff  Name、refno、Status、Order Type、
	 * @return List<C_Recruitment_order>
	 */
	public List<C_Recruitment_order> findRecruitmentList(String date1, String date2,Page page,String medianame) {
		
		List<C_Recruitment_order> listOrders=new ArrayList<C_Recruitment_order>();
		try{
			connection =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select o.staffcode,o.staffname,l.medianame,o.status,o.createdate from c_recruitment_order o left join c_recruitment_detail d on o.refno=d.refno left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y' and ( o.`status` = 'Submitted' or o.`status` = 'Confirmation Request' )");
			if(!(Util.objIsNULL(date1))){
				sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')>=DATE_FORMAT('"+date1+"','%Y-%m-%d')");
			}
			if(!(Util.objIsNULL(date2))){
				sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')<=DATE_FORMAT('"+date2+"','%Y-%m-%d')");
			}
			if(!(Util.objIsNULL(medianame))){
				sql.append("and l.medianame like'%"+medianame+"%'");
			}
			sql.append("  order by o.createdate desc LIMIT ?,?");
			ps=connection.prepareStatement(sql.toString());
			ps.setInt(1,(page.getCurPage()-1)*page.getPageSize());
			ps.setInt(2, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				C_Recruitment_order order2=new C_Recruitment_order();
				order2.setStaffcode(rs.getString("staffcode"));
				order2.setStaffname(rs.getString("staffname"));
				order2.setMedianame(rs.getString("medianame"));			  
				order2.setStatus(rs.getString("status"));			  
				order2.setCreatedate(rs.getString("createdate"));
				listOrders.add(order2);
			}
			rs.close();
		}catch (Exception e) {
			logger.error("Find List<C_Recruitment_order>(RecruitmentQueryHrDaoImpl) Error"+e.toString());
			e.printStackTrace();
		}finally{
			
			DBManager.closeCon(connection);
		}
		
		return listOrders;
	}
	/**
	 * 查询订单总记录
	 * @return int
	 */
	public int selecrRow(String date1, String date2,C_Recruitment_order order,String medianame ){
		int num=-1;
		try{
			connection =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select count(*)  from c_recruitment_order o left join c_recruitment_detail d on o.refno=d.refno left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y'");
			if(!(Util.objIsNULL(date1))){
			         sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')>=DATE_FORMAT('"+date1+"','%Y-%m-%d')");
				     }
			 if(!(Util.objIsNULL(date2))){
			  sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')<=DATE_FORMAT('"+date2+"','%Y-%m-%d')");
			  }
			  if(!(Util.objIsNULL(order.getStaffcode()))){
				  sql.append("and o.staffcode like '%"+order.getStaffcode()+"%'");
			  }
			  if(!(Util.objIsNULL(order.getRefno()))){
				  sql.append("and o.refno like '%"+order.getRefno()+"%'");
			  }
			  if(!(Util.objIsNULL(order.getStaffname()))){
					sql.append("and o.staffname like '%"+order.getStaffname()+"%'");
				}
			  if(!(Util.objIsNULL(order.getStatus()))){
				  sql.append("and o.status='"+order.getStatus()+"'");
			  }
			  if(!(Util.objIsNULL(medianame))){
				  sql.append("and l.medianame like '%"+medianame+"%'");
			  }
		  ps=connection.prepareStatement(sql.toString());
		  rs=ps.executeQuery();
		 if(rs.next()){
			num=rs.getInt(1);
		 }
		 rs.close();
		}catch (Exception e) {
			 logger.error("Find RowCount failed (RecruitmentQueryHrDaoImpl)"+e.toString());
			 e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * 查询订单总记录
	 * @return int
	 */
	public int selectRow(String date1, String date2,String medianame ){
		int num=-1;
		try{
			connection =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select count(*) from c_recruitment_order o left join c_recruitment_detail d on o.refno=d.refno left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y' and ( o.`status` = 'Submitted' or o.`status` = 'Scheduled' )");
			if(!(Util.objIsNULL(date1))){
				sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')>=DATE_FORMAT('"+date1+"','%Y-%m-%d')");
			}
			if(!(Util.objIsNULL(date2))){
				sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')<=DATE_FORMAT('"+date2+"','%Y-%m-%d')");
			}
			if(!(Util.objIsNULL(medianame))){
				sql.append("and l.medianame like '%"+medianame+"%'");
			}
			ps=connection.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			rs.close();
		}catch (Exception e) {
			logger.error("Find RowCount failed (RecruitmentQueryHrDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * 根据订单编号查询订单
	 * @param refno
	 * @return C_Recruitment_order
	 */
	public C_Recruitment_order findOrderByNo(String refno){
		C_Recruitment_order order=new C_Recruitment_order();
		try{
			connection=DBManager.getCon();
			String sql="select *  from c_recruitment_order  where refno=?";
			ps=connection.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				order.setRefno(rs.getString("refno"));
				order.setStaffcode(rs.getString("staffcode"));
				order.setStaffname(rs.getString("staffname"));
				order.setPosition(rs.getString("position"));
				order.setStatus(rs.getString("status"));
				order.setDate(rs.getString("date"));
				order.setCreater(rs.getString("creater"));
				order.setContactperson(rs.getString("contactperson"));
				order.setContactemail(rs.getString("contactemail"));
				order.setChargecode(rs.getString("chargecode"));
				order.setChargename(rs.getString("chargename"));
			}
			return order;
		}catch (Exception e) {
			logger.error("find C_Recruitment_order error(RecruitmentQueryHrDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return null;
	}
	
	/**
	 * 根据单号查询订单详情
	 * @param  refno
	 * @return C_Recruitment_detail
	 */
	public C_Recruitment_detail findDetial(String refno){
		C_Recruitment_detail detail=new C_Recruitment_detail();
		try{
			connection=DBManager.getCon();
			String sql="select * from c_recruitment_detail where refno=?";
			ps=connection.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				detail.setRefno(rs.getString("refno"));
				detail.setMediacode(rs.getString("mediacode"));
				detail.setMedianame(rs.getString("medianame"));
				detail.setPrice(rs.getDouble("price"));
			}
			 return	detail;
		}catch (Exception e) {
			logger.error("find C_Recruitment_detail error(RecruitmentQueryHrDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
	      return null;
	}
	
	/**
	 * 根据refno查询付费信息
	 * @param refno
	 * @return C_Payment
	 */
	public C_Payment findCons_listByCode(String refno){
		
		C_Payment cl=new C_Payment();
		try{
		   connection=DBManager.getCon();
		   String sql="select * from c_payment where refno=?";
		   ps=connection.prepareStatement(sql);
		   ps.setString(1, refno);	
		   rs=ps.executeQuery();
		   if(rs.next()){
			   cl.setPaymentMethod(rs.getString("paymentMethod"));
			   cl.setPaymentDate(rs.getString("paymentDate"));
			   cl.setPaymentAount(rs.getDouble("paymentAount"));
			   cl.setHandleder(rs.getString("Handleder"));
			   cl.setSaleno(rs.getString("saleno"));
		   }
		   rs.close();
		}catch (Exception e) {
			logger.error(" find C_Payment(RecruitmentQueryHrDaoImpl) error"+e.toString());
	        e.printStackTrace();
		}
		finally{
			DBManager.closeCon(connection);
		}
		   return cl;
	}
	 /**
	  * 保存刊登日期
	  * @param date
	  * return int 
	  */
	 public int upOrderDate(String date,String refno,String name,String to,String cc,String staffname,String date1,String date2,
			   String Person,String emailss,String mediaName){
		 int num=-1;
		 try{
			 C_Recruitment_operation operation=new C_Recruitment_operation();//用户 操作记录表
			 connection=DBManager.getCon();
			 connection.setAutoCommit(false);
			 String sql="update c_recruitment_order set date=? , status='Confirmation Request'  where  refno=?";
			 ps=connection.prepareStatement(sql);
			 ps.setString(1, date);
			 ps.setString(2, refno);
			 num=ps.executeUpdate();
			 if(num<1){
				 throw new RuntimeException();
			 }
			 //保存用户操作记录
	        operation.setRefno(refno);
	    	operation.setOperationType("Confirmation Request");
	    	operation.setOperationName(name);
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	operation.setOperationDate(sdf.format(new Date()));
		    	
		    String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values (?,?,?,?)";
		    ps=connection.prepareStatement(SQL);
		    ps.setString(1, operation.getRefno());
		    ps.setString(2, operation.getOperationType());
		    ps.setString(3, operation.getOperationName());
		    ps.setString(4, operation.getOperationDate());
		    num=ps.executeUpdate();
		    if(num<1){
				 throw new RuntimeException();
			}
		    
		    String content="Dear "+staffname+",<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please confirm the recruitment advertisement placement by sending email to <a href='mailto:SZOAdm@convoy.com.hk'>SZOAdm@convoy.com.hk</a>  on or before 10:00 noon "+date1+"<br/>";
			content+="<br/>Posting Period: "+date2+"<br/>";
		    content+="<br/>Media Name: "+mediaName+"<br/>";
		    content+="<br/>Contact Person: "+Person+"<br/>";
		    content+="<br/>Contact Email: <a href='mailto:"+emailss+"'>"+emailss+"</a><br/>";
		    
		    
		    content+="Best Regards,<br/>";
		    content+="Administration Department<br/>";
		    
		    String result=SendMail.send("COAT – Recruitment Advertisement Placement",to,cc,null,null,content,null,null,null);
		    JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}
		    connection.commit();
			logger.info("update c_recruitment_order date , status='Confirmation Request' success");
			num=1;
		   }catch (Exception e) {
			   e.printStackTrace();
			   try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 logger.error("Update order Date error(RecruitmentQueryHrDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		 return num;
	 }
	 
	 
	 /**
	  * 更新订单状态
	  * @param status
	  * @return int 
	  */
	public int upOrderStatus(String status,String refno,String name,C_Recruitment_order order,C_Recruitment_detail detail){
		 int num=-1;
		 C_Recruitment_operation operation=new C_Recruitment_operation();//用户 操作记录表
		 try{
			 connection=DBManager.getCon();
			 connection.setAutoCommit(false);
		       if(!Util.objIsNULL(status) && status.equals("Scheduled")){
				 String sql="update c_recruitment_order set status=? where  refno=?";
				 ps=connection.prepareStatement(sql);
				 ps.setString(1, status);
				 ps.setString(2, refno);
				 int updatenum = ps.executeUpdate();		    	   
				 if(updatenum<0){
					throw new RuntimeException("更新c_recruitment_order记录异常");
				 }	   
		    	   //在c_recruitment_auxiliary 确认排期表中插入记录并发送确认邮件通知
		    	   CRecruitmentAuxiliary cra = new CRecruitmentAuxiliary();
					cra.setRefno(refno);
					cra.setMediacode(detail.getMediacode());
					cra.setStatus(status);
					cra.setCreatedate(DateUtils.getNowDateTime());
					cra.setSchedate(order.getDate());
					String sql3="insert into c_recruitment_auxiliary (refno,mediacode,status,createdate,schedate)values(?,?,?,?,?)";
					PreparedStatement ps3= connection.prepareStatement(sql3);
					ps3.setString(1, cra.getRefno());
					ps3.setString(2, cra.getMediacode());
					ps3.setString(3, cra.getStatus());
					ps3.setString(4, cra.getCreatedate());
					ps3.setString(5, cra.getSchedate());
					int flag = ps3.executeUpdate();
					if(flag<1){
						throw new RuntimeException("保存c_recruitment_auxiliary记录异常");
					}	
					
					//在 c_payment表中插入记录
					C_Payment cpayment = new C_Payment();
					cpayment.setRefno(refno);
					cpayment.setStaffname(order.getStaffname());
					cpayment.setType("Advertisement");
					cpayment.setSaleno(order.getChargecode());
					cpayment.setPaymentMethod("Cash");
					cpayment.setPaymentAount(detail.getPrice());
					cpayment.setPaymentDate(DateUtils.getNowDateTime());
					cpayment.setHandleder(order.getChargecode());
					cpayment.setCreator(name);
					cpayment.setCreateDate(DateUtils.getNowDateTime());
					cpayment.setSfyx("Y");
					//select refno,staffname,type,saleno,paymentAount,paymentDate,Handleder,creator,createDate,sfyx  from c_payment;
					String sql4="insert into c_payment (refno,staffname,type,saleno,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx)values(?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement ps4= connection.prepareStatement(sql4);
					ps4.setString(1, cpayment.getRefno());
					ps4.setString(2, cpayment.getStaffname());
					ps4.setString(3, cpayment.getType());
					ps4.setString(4, cpayment.getSaleno());
					ps4.setString(5, cpayment.getPaymentMethod());
					ps4.setDouble(6, cpayment.getPaymentAount());
					ps4.setString(7, cpayment.getPaymentDate());
					ps4.setString(8, cpayment.getHandleder());
					ps4.setString(9, cpayment.getCreator());
					ps4.setString(10, cpayment.getCreateDate());
					ps4.setString(11, cpayment.getSfyx());
					
					int flag2 = ps4.executeUpdate();
					if(flag2<1){
						throw new RuntimeException("保存c_payment记录异常");
					}	
					
			    	   //将c_recruitment_order 表中的 filterdate时间更新为点击同意申请这个时间的下一周的下一周的周四  
					   //TODO 忘了为什么要这么改的逻辑_orlando 但好像也没有用到
			/*			String sql5="update c_recruitment_order set filterdate=?  where  refno=? ";
						PreparedStatement ps5=connection.prepareStatement(sql5);
						ps5.setString(1, DateUtils.getNextNextWeekThursdayDate(DateUtils.getNowDateTime()));
						ps5.setString(2, refno);
						int flag2 = ps5.executeUpdate();
					    if(flag2<0){
							 throw new RuntimeException("修改c_recruitment_order filterdate出现异常");
						}*/
					
					 //保存用户操作记录
					 operation.setRefno(refno);
					 operation.setOperationType(status);
					 operation.setOperationName(name);
					 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 operation.setOperationDate(sdf.format(new Date()));
					 String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate)values(?,?,?,?)";
			    	 ps= connection.prepareStatement(SQL);
		             ps.setString(1, operation.getRefno());
			         ps.setString(2, operation.getOperationType());
			         ps.setString(3, operation.getOperationName());
			         ps.setString(4, operation.getOperationDate());
			         num=ps.executeUpdate(); 
			         
					if(num<1){
						throw new RuntimeException("保存c_recruitment_operation记录异常");
					}	
					
		       }else if(!Util.objIsNULL(status) && status.equals("Cancelled")){
		    	   //Refused Scheduled
		    	   
					 String sql="update c_recruitment_order set status=? where  refno=?";
					 ps=connection.prepareStatement(sql);
					 ps.setString(1, status);
					 ps.setString(2, refno);
					 ps.executeUpdate();	
					 int updatenum = ps.executeUpdate();		    	   
					 if(updatenum<0){
						throw new RuntimeException("更新c_recruitment_order记录异常");
					 }
		    	   //将c_recruitment_order 表中的 filterdate时间更新为点击拒绝申请这个时间的下一周周五
/*					String sql4="update c_recruitment_order set filterdate=?  where  refno=? ";
					PreparedStatement ps4=connection.prepareStatement(sql4);
					ps4.setString(1, DateUtils.getNearFridayDate(DateUtils.getNowDateTime()));
					ps4.setString(2, refno);
					int flag1 = ps4.executeUpdate();
				    if(flag1<0){
						 throw new RuntimeException("修改c_recruitment_order filterdate出现异常");
					}*/
		    	   
					 //保存用户操作记录
					 operation.setRefno(refno);
					 operation.setOperationType("Refused Scheduled");
					 operation.setOperationName(name);
					 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 operation.setOperationDate(sdf.format(new Date()));
					 String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate)values(?,?,?,?)";
			    	 ps= connection.prepareStatement(SQL);
		             ps.setString(1, operation.getRefno());
			         ps.setString(2, operation.getOperationType());
			         ps.setString(3, operation.getOperationName());
			         ps.setString(4, operation.getOperationDate());
			         num=ps.executeUpdate();  
		       }else if(!Util.objIsNULL(status) && status.equals("VOID")){    
		    	 //Refused Confirm
		    	   
					 String sql="update c_recruitment_order set status=? where  refno=?";
					 ps=connection.prepareStatement(sql);
					 ps.setString(1, status);
					 ps.setString(2, refno);
					 ps.executeUpdate();	
					 int updatenum = ps.executeUpdate();		    	   
					 if(updatenum<0){
						throw new RuntimeException("更新c_recruitment_order记录异常");
					 }

		    	   
					 //保存用户操作记录
					 operation.setRefno(refno);
					 operation.setOperationType("Refused Confirmed");
					 operation.setOperationName(name);
					 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 operation.setOperationDate(sdf.format(new Date()));
					 String SQL="insert into c_recruitment_operation(refno,operationType,operationName,operationDate)values(?,?,?,?)";
			    	 ps= connection.prepareStatement(SQL);
		             ps.setString(1, operation.getRefno());
			         ps.setString(2, operation.getOperationType());
			         ps.setString(3, operation.getOperationName());
			         ps.setString(4, operation.getOperationDate());
			         num=ps.executeUpdate();  
		       }else{
		    	   logger.info("AD确认广告排期邮件通知结果: 未发送邮件通知");
		       }
		         
			    connection.commit();
			 
			 logger.info("update c_recruitment_order  refno="+refno+" status="+status+"success");
		   }catch (Exception e) {
			 num = 0;
			 logger.error("Update order status  error(RecruitmentQueryHrDaoImpl)"+e.toString());
			 e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		 return num;
	}
	/**
	 * 增加支付信息
	 * @paramC_Payment
	 * @return int
	 */
	public  int addpayment(C_Payment payment,String name){
		int num=-1;
		 C_Recruitment_operation operation=new C_Recruitment_operation();//用户 操作记录表
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			String sql="insert into c_payment (refno,staffname,type,saleno,paymentMethod,paymentAount,paymentDate,Handleder,createDate,sfyx)values(?,?,?,?,?,?,?,?,?,?)";
			ps=connection.prepareStatement(sql);
			ps.setString(1, payment.getRefno());
			ps.setString(2, payment.getStaffname());
			ps.setString(3, payment.getType());
			ps.setString(4, payment.getSaleno());
			ps.setString(5, payment.getPaymentMethod());
			ps.setDouble(6, payment.getPaymentAount());
			ps.setString(7, payment.getPaymentDate());
			ps.setString(8, payment.getHandleder());
			ps.setString(9, payment.getCreateDate());
			ps.setString(10, payment.getSfyx());		
			ps.executeUpdate();
			//更新表单状态
			String SQL="update c_recruitment_order set status='Completed' where  refno=?";	
			ps=connection.prepareStatement(SQL);
			ps.setString(1, payment.getRefno()); 	
		    ps.executeUpdate(); 
		    
		  //保存用户操作记录
	        operation.setRefno(payment.getRefno());
	    	operation.setOperationType("Completed");
	    	operation.setOperationName(name);
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	operation.setOperationDate(sdf.format(new Date()));
	    	String SQL1="insert into c_recruitment_operation(refno,operationType,operationName,operationDate)values(?,?,?,?)";
	    	 ps=connection.prepareStatement(SQL1);
             ps.setString(1, operation.getRefno());
	         ps.setString(2, operation.getOperationType());
	         ps.setString(3, operation.getOperationName());
	         ps.setString(4, operation.getOperationDate());
	         num=ps.executeUpdate();
		    
		    logger.info("add C_Payment record success(RecruitmentQueryHrDaoImpl) and change refno="+payment.getRefno()+"status success");
			connection.commit();	
		}catch (Exception e) {
			logger.error("Add C_Payment record error(RecruitmentQueryHrDaoImpl)"+e.toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * 根据条件导出excel
	 */
	public List<Recruitment_list> exportDate(String date1, String date2,C_Recruitment_order order){
		List<Recruitment_list> list=new ArrayList<Recruitment_list>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try{
			connection =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select o.refno,o.staffname,o.staffcode,o.createdate,o.date,o.contactperson,o.contactemail,o.chargecode,o.status,d.mediacode,d.medianame,p.paymentAount,p.Handleder,l.mediatype from " +
					"c_recruitment_order o  LEFT join c_recruitment_detail  d on o.refno=d.refno  " +
					" left join c_payment p on o.refno=p.refno  " +
					"left join c_recruitment_list l on d.mediacode=l.mediacode where o.sfyx='Y'");
			if(!(Util.objIsNULL(date1))){
			    sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')>=DATE_FORMAT('"+date1+"','%Y-%m-%d')");
				     }
			 if(!(Util.objIsNULL(date2))){
			  sql.append("and DATE_FORMAT(o.createdate,'%Y-%m-%d')<=DATE_FORMAT('"+date2+"','%Y-%m-%d')");
			  }
			  if(!(Util.objIsNULL(order.getStaffcode()))){
				  sql.append("and o.staffcode like '%"+order.getStaffcode()+"%'");
			  }
			  if(!(Util.objIsNULL(order.getRefno()))){
				  sql.append("and o.refno like '%"+order.getRefno()+"%'");
			  }
			  if(!(Util.objIsNULL(order.getStaffname()))){
					sql.append("and staffname like '%"+order.getStaffname()+"%'");
				}
			  if(!(Util.objIsNULL(order.getStatus()))){
				  sql.append("and o.status='"+order.getStatus()+"'");
			  } 
			  sql.append("order by o.createdate asc");
		    ps=connection.prepareStatement(sql.toString());
		    rs=ps.executeQuery();
			while(rs.next()){
				Recruitment_list r_list=new Recruitment_list();
				r_list.setRefno(rs.getString("refno"));
				r_list.setStaffcode(rs.getString("staffcode"));
				r_list.setStaffname(rs.getString("staffname"));
				r_list.setMediatype(rs.getString("mediatype"));
				r_list.setMedianame(rs.getString("d.medianame"));
				r_list.setCreateDate(sf.format(DateUtils.StrToDate(rs.getString("createdate"))));
				r_list.setDate(rs.getString("date"));
				r_list.setContactperson(rs.getString("contactperson"));
				r_list.setContactemail(rs.getString("contactemail"));
				r_list.setHandleber(rs.getString("Handleder"));
				r_list.setChargecode(rs.getString("chargecode"));
				r_list.setStatus(rs.getString("status"));
				if(!(r_list.getStatus().equals("Completed"))){
		        	  r_list.setPrice("");
					}else{
						 r_list.setPrice(rs.getDouble("paymentAount")+"");
					}
				list.add(r_list);
			}
			rs.close();
		}catch (Exception e) {
			logger.error("export recruitment_list date error (RecruitmentQueryHrDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
		
	}
	
	
	/**
	 * 根据code查询Email 
	 */
	public String[] getEmailByCode(String [] str){
		String[] email=new String[2];
		try{
			connection=DBManager.getCon();
			if(str.length==0){
				return null;
			}
			else if(str[0].equals(str[1])){
				
				String sql="select * from cons_list where EmployeeId=?";
				ps=connection.prepareStatement(sql);
				ps.setString(1,str[0]);
				rs=ps.executeQuery();
				if(rs.next()){
					email[0]=rs.getString("Email");
				}
				email[1]=email[0];
				return email;
			}
			else{
				for(int i=0;i<str.length;i++){
					String sql="select * from cons_list where EmployeeId=?";
					ps=connection.prepareStatement(sql);
					ps.setString(1,str[i]);
					rs=ps.executeQuery();
					if(rs.next()){
						email[i]=rs.getString("Email");
					}
				}
			}
			return email;
		}catch (Exception e) {
			logger.error("get Email Error"+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return null;
	}
	
	
	
	/**
	 * 查询是否已经存在相同类型的广告在同一时间段排期
	 * @param 
	 * @return
	 */
	public boolean Legitimate(String mediacode,String date) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select * from c_recruitment_auxiliary where mediacode=? and schedate=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, mediacode);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while (rs.next()) {
				flag = false;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取Staffcode出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return flag;
	}
	

	public  String getMediaCodeByMediaName(String medianame) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String mediacode = null;
		try {
			sql = "select mediacode from c_recruitment_list where medianame=? and sfyx = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, medianame);
			rs = ps.executeQuery();
			while (rs.next()) {
				mediacode = rs.getString("mediacode");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通过medianame获取c_recruitment_list mediacode出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return mediacode;
	}
	
}
