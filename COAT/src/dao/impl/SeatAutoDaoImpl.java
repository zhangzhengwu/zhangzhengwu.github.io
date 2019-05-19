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

import dao.SeatAutoDao;
import dao.common.BaseDao;
import entity.C_Seatassignment;
import entity.ConsList;
import entity.Excel;
import entity.SeatAutochangeApply;
import entity.SeatAutochangeListfromvsm;
import entity.SeatChangeOperation;
import entity.SeatChangeSuccessmsg;
import entity.SeatList;
import util.Constant;
import util.DBManager;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.ExcelTools;
import util.Pager;
import util.SendMail;
import util.Util;

public class SeatAutoDaoImpl extends BaseDao implements SeatAutoDao {

	Logger logger = Logger.getLogger(SeatAutoDaoImpl.class);
	
	   public String timeTaskSendChangeSeatSuccessListToAdmin(){
			String result = "";
			try{
				Util.printLogger(logger,"开始执行指定任务-发送不同楼层换位成功的邮件");    
				List<SeatChangeSuccessmsg> list=getChangeSeatSuccessList();
				if(!Util.objIsNULL(list)&&list.size()>0){
					Util.printLogger(logger,"指定任务-->发送不同楼层换位成功的邮件成功!");
					result = "success";
				}else{
					Util.printLogger(logger,"指定任务-->发送不同楼层换位成功的邮件失败，原因：获取远程获取信息时出错!");
					throw new Exception("发送不同楼层换位成功的邮件为空！");
				} 
				
			}catch(Exception e){
				result=e.getMessage();
				Util.printLogger(logger,"指定任务-->发送不同楼层换位成功的邮件失败："+e.getMessage());
			}
			return result;	
	   }
	
	
		public List<SeatChangeSuccessmsg> getChangeSeatSuccessList() {
			Excel excel=new Excel();
			List<SeatChangeSuccessmsg> list=null;
			Connection connection = null;
			try{
				connection =DBManager.getCon();
				StringBuffer sql= new StringBuffer(" select staffcode,staffname,seatnobefore,locationbefore,floorbefore, ");
				sql.append(" seatnoafter,locationafter,floorafter,exchangetime from seat_change_successmsg where `status` =1 ");
				sql.append(" and (SUBSTR(exchangetime,1,4)+0) = year(CURRENT_DATE)");
				sql.append(" and (SUBSTR(exchangetime,6,2)+0) = month(CURRENT_DATE) ");
				sql.append(" and (SUBSTR(exchangetime,9,2)+0) = day(CURRENT_DATE) ;");
				
				PreparedStatement ps=connection.prepareStatement(sql.toString());
				ResultSet rs=ps.executeQuery();
				list=new ArrayList<SeatChangeSuccessmsg>();
				while(rs.next()){
					SeatChangeSuccessmsg slist=new SeatChangeSuccessmsg(rs.getString("staffcode"),
																		rs.getString("staffname"),
																		rs.getString("seatnobefore"),
																		rs.getString("locationbefore"),
																		rs.getString("floorbefore"),
																		rs.getString("seatnoafter"),
																		rs.getString("locationafter"),
																		rs.getString("floorafter"),
																		rs.getString("exchangetime"));
					list.add(slist);
				}
				
				//当list没有数据时，不发送邮件
				if(list.size() <= 0){
					return null;
				}
				

		        //发送邮件
			    String content="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please be informed that the attached consultant(s) changed the location. Thanks.<br/>";
				content+="<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				
			    //把数据交给Excel
			    excel.setExcelContentList(list);	
			    //设置Excel列头
			    excel.setColumns(new String[]{"Staff Code","Staff Name","Seat No Before","Location Before","Floor Before","Seat No After","Location After","Floor After","Exchange Time"});
			    //属性字段名称
			    excel.setHeaderNames(new String[]{"staffcode","staffname","seatnobefore","locationbefore","floorbefore","seatnoafter","locationafter","floorafter","exchangetime"});
			   //sheet名称
			    excel.setSheetname("SeatChangeOnDifferentFloor");
			    //文件名称
				excel.setFilename("SeatChangeOnDifferentFloor"+System.currentTimeMillis()+".xls");
				//表单生成
				excel.setFilepath(Util.getProValue("file.handle.temp.downpath"));
				ExcelTools.createExcelToPath(excel);
				
				
				String result = SendMail.send("Location Changed-"+DateUtils.Ordercode(),"admsystem@convoy.com.hk",null,null,Util.getProValue("public.system.downlink")+"/upload/temp/"+excel.getFilename(), content, "COAT", null, null);
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
				logger.error("Find List<SeatChangeSuccessmsg>(SeatAutoDaoImpl) Error"+e.toString());
				e.printStackTrace();
			}finally{
				DBManager.closeCon(connection);
			}
			return list;
		}
	
	
	
	
	
	
	/**
	 * 定时从vsmart中获取PA信息
	 * @return
	 */
	public String timeTaskSeatChangeListForPA(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-同步转正的人员信息");    
			List<SeatAutochangeListfromvsm> list=getAllPAList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				saveList(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->同步转正的人员信息成功!");
			}else{
				Util.printLogger(logger,"指定任务-->同步转正的人员信息失败，原因：获取远程获取信息时出错!");
				throw new Exception("获取同步转正的人员信息为空！");
			} 
		
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->同步转正的人员信息失败："+e.getMessage());

		}finally{
			try{
				addChangeApply();
			}catch (Exception e) {
				result=e.getMessage(); 
			}
		}
		
		return result;
		
	}
	/**
	 * 定时从vsmart中获取转正顾问信息
	 * @return
	 */
	public String timeTaskSeatChangeListForProba(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-同步转正的人员信息");    
			List<SeatAutochangeListfromvsm> list=getAllList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				saveList(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->同步转正的人员信息成功!");
			}else{
				Util.printLogger(logger,"指定任务-->同步转正的人员信息失败，原因：获取远程获取信息时出错!");
				throw new Exception("获取同步转正的人员信息为空！");
			} 
			
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->同步转正的人员信息失败："+e.getMessage());
			
		}finally{
			try{
				addChangeApply();
			}catch (Exception e) {
				result=e.getMessage(); 
			}
		}
		
		return result;
		
	}
	
	
	/**
	 * 定时从vsmart中获取晋升至AD顾问信息
	 * @return
	 */
	public String timeTaskSeatChangeListForPromo(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-同步晋升至AD的人员信息");    
			List<SeatAutochangeListfromvsm> list=getAllList1();
			if(!Util.objIsNULL(list)&&list.size()>0){
				saveList(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->同步晋升至AD的人员信息成功!");
			}else{
				Util.printLogger(logger,"指定任务-->同步晋升至AD的人员信息失败，原因：获取远程获取信息时出错!");
				throw new Exception("获取同步晋升至AD的人员信息为空！");
			} 			
			
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->同步晋升至AD的人员信息失败："+e.getMessage());
		}finally{
			try{
			   addChangeApply();
			}catch (Exception e) {
				result=e.getMessage(); 
			}
		}
		
		return result;
		
	}
	
	
	/**
	 * 定时查询7天后leader未审批的换位申请并自动同意
	 * @return
	 */
	public String timeTaskSeatAutoChangeForLeader(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-查询7天后leader未审批的换位申请");    
			List<SeatAutochangeApply> list=getLateSubmitList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				updateLateSubmitList(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->查询7天后leader未审批的换位申请成功!");
			}else{
				throw new Exception("获取7天后leader未审批的换位申请信息为空！");
			} 
			
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->查询7天后leader未审批的换位申请失败："+e.getMessage());
			
		}
		return result;
	}
	
	/**
	 * 定时查询7天后HKADM未审批的换位申请并自动同意
	 * @return
	 */
	public String timeTaskSeatAutoChangeForHKADM(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-查询7天后HKADM未审批的换位申请");    
			List<SeatAutochangeApply> list=getLateConfirmList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				updateLateConfirmList(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->查询7天后HKADM未审批的换位申请成功!");
			}else{
				throw new Exception("获取7天后HKADM未审批的换位申请信息为空！");
			} 
			
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->查询7天后HKADM未审批的换位申请失败："+e.getMessage());
			
		}
		return result;
	}

	
	
	/**
	 * 远程获取转正的人员信息
	 * @return
	 * @throws 
	 */
	public List<SeatAutochangeListfromvsm> getAllList() throws Exception{
		Connection con = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		ResultSet rs = null;
		ResultSet rss = null;
		List<SeatAutochangeListfromvsm> list=null;
		try{
			con=DBManager_sqlservler.getCon();
			conn = DBManager.getCon();
			String sql1="select DISTINCT staffcode from seat_autochange_listfromvsm where changetype = 0 and sfyx = 'Y'";
			pss = conn.prepareStatement(sql1);
			rss = pss.executeQuery();
			String a="";
			while(rss.next()){
				if(Util.objIsNULL(a)){
					a+="'"+rss.getString("staffcode")+"'";
				}else{
					a+=","+"'"+rss.getString("staffcode")+"'";
				}
			}
			
			  sql="select DISTINCT ConsultantId,case when (Alias is null or alias = '') then (GivenName+' '+SurName) else (Alias+' '+SurName) end as staffname,RecruiterId,RecruiterSurname,RecruiterGivenName from vSZO_SOS_ConsultantInfo where (Reason='Completion of Probation' or Reason='Completed Stage 2 Probation') and TerritoryEndDate is null and SchemeCode = 'HK' ";
			  if(!Util.objIsNULL(a)){
				  sql+="and  ConsultantId  not in ("+a+")";
			  }
			  ps=con.prepareStatement(sql);
			  rs=ps.executeQuery();
			  list=new ArrayList<SeatAutochangeListfromvsm>();
				while(rs.next()){
					list.add(new SeatAutochangeListfromvsm(
							rs.getString("ConsultantId"),
							rs.getString("staffname"),
							rs.getString("RecruiterId"),
							rs.getString("RecruiterSurname")+rs.getString("RecruiterGivenName"),
							0,//changetype 
							0,//changeflag
							"",
							"Y",
							""
							));
				}
				rs.close();
				rss.close();
		}catch(Exception e){
			logger.error("同步转正的人员信息时，网络连接异常");
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		    DBManager_sqlservler.closeCon(con);
		}
		return list;
	}
	/**
	 * 远程获取转正的人员信息
	 * @return
	 * @throws 
	 */
	public List<SeatAutochangeListfromvsm> getAllPAList() throws Exception{
		Connection con = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		ResultSet rs = null;
		ResultSet rss = null;
		List<SeatAutochangeListfromvsm> list=null;
		try{
			con=DBManager_sqlservler.getCon();
			conn = DBManager.getCon();
			String sql1="select DISTINCT staffcode from seat_autochange_listfromvsm where changetype = 2 and sfyx = 'Y'";
			pss = conn.prepareStatement(sql1);
			rss = pss.executeQuery();
			String a="";
			while(rss.next()){
				if(Util.objIsNULL(a)){
					a+="'"+rss.getString("staffcode")+"'";
				}else{
					a+=","+"'"+rss.getString("staffcode")+"'";
				}
			}
			
			sql="select distinct ConsultantId,case when (Alias is null or alias = '') then (GivenName+' '+SurName) else (Alias+' '+SurName) end as staffname,RecruiterId,RecruiterSurname,RecruiterGivenName from vSZO_SOS_ConsultantInfo where (GradeId = 'PA' or InternalPositionId = 'PA' or ExternalPositionId = 'PA') and RecruiterId in (select RecruiterId from vSZO_SOS_ConsultantInfo where (GradeId = 'PA' or InternalPositionId = 'PA' or ExternalPositionId = 'PA') and RecruiterId is not null group by RecruiterId having count(1)=1) and TerritoryEndDate is null and SchemeCode = 'HK' ";
			if(!Util.objIsNULL(a)){
				sql+="and  ConsultantId  not in ("+a+")";
			}
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<SeatAutochangeListfromvsm>();
			while(rs.next()){
				list.add(new SeatAutochangeListfromvsm(
						rs.getString("ConsultantId"),
						rs.getString("staffname"),
						rs.getString("RecruiterId"),
						rs.getString("RecruiterSurname")+rs.getString("RecruiterGivenName"),
						2,//changetype 
						0,//changeflag
						"",
						"Y",
						""
						));
			}
			rs.close();
			rss.close();
		}catch(Exception e){
			logger.error("同步转正的人员信息时，网络连接异常");
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
			DBManager_sqlservler.closeCon(con);
		}
		return list;
	}
	/**
	 * 远程获取晋升至AD的人员信息
	 * @return
	 * @throws 
	 */
	public List<SeatAutochangeListfromvsm> getAllList1() throws Exception{
		Connection con = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		ResultSet rs = null;
		ResultSet rss = null;
		List<SeatAutochangeListfromvsm> list=null;
		try{
			con=DBManager_sqlservler.getCon();
			conn = DBManager.getCon();
			String sql1="select DISTINCT staffcode from seat_autochange_listfromvsm where changetype = 1 and sfyx = 'Y'";
			pss = conn.prepareStatement(sql1);
			rss = pss.executeQuery();
			String a="";
			while(rss.next()){
				if(Util.objIsNULL(a)){
					a+="'"+rss.getString("staffcode")+"'";
				}else{
					a+=","+"'"+rss.getString("staffcode")+"'";
				}
			}
			
			sql="select DISTINCT ConsultantId,case when (Alias is null or alias = '') then (GivenName+' '+SurName) else (Alias+' '+SurName) end as staffname,RecruiterId,RecruiterSurname,RecruiterGivenName from vSZO_SOS_ConsultantInfo where Reason='Promotion' and (GradeId='AD' or GradeId='VP') and TerritoryEndDate is null and SchemeCode = 'HK' ";
			if(!Util.objIsNULL(a)){
				sql+="and  ConsultantId  not in ("+a+")";
			}
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<SeatAutochangeListfromvsm>();
			while(rs.next()){
				list.add(new SeatAutochangeListfromvsm(
						rs.getString("ConsultantId"),
						rs.getString("staffname"),
						rs.getString("RecruiterId"),
						rs.getString("RecruiterSurname")+rs.getString("RecruiterGivenName"),
						1,
						0,
						"",
						"Y",
						""
						));
			}
			rs.close();
			rss.close();
		}catch(Exception e){
			logger.error("同步晋升至AD的人员信息时，网络连接异常");
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
			DBManager_sqlservler.closeCon(con);
		}
		return list;
	}
	/**
	 * 获取Leader 7天未审批的换位申请列表
	 * @return
	 * @throws 
	 */
	public List<SeatAutochangeApply> getLateSubmitList() throws Exception{
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<SeatAutochangeApply> list=null;
		try{
			con=DBManager.getCon();
			sql="select * from seat_autochange_apply  where `status`='Submitted' and sfyx='Y' and datediff(now(), updatedate) > 7";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<SeatAutochangeApply>();
			while(rs.next()){
				list.add(new SeatAutochangeApply(rs.getString("refno"),
						                         rs.getString("staffcode"),
						                         rs.getString("staffname"),
						                         rs.getString("seatnobefore"),
						                         rs.getString("seatno"),
						                         rs.getString("leadercode"),
						                         rs.getString("leadername"),
						                         rs.getString("createdate"),
						                         rs.getString("updatedate"),
						                         rs.getInt("flag"),
						                         rs.getString("status"),
						                         rs.getString("sfyx"),
						                         rs.getString("remarkA"),
						                         rs.getString("remarkB"),
						                         rs.getString("remarkC")));
			}
			rs.close();
		}catch(Exception e){
			logger.error("获取Leader 7天未审批的换位申请信息时，网络连接异常");
			throw new Exception(e);
		}finally{
			con.close();
		}
		return list;
	}
	/**
	 * 获取HKADM 7天未审批的换位申请列表
	 * @return
	 * @throws 
	 */
	public List<SeatAutochangeApply> getLateConfirmList() throws Exception{
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<SeatAutochangeApply> list=null;
		try{
			con=DBManager.getCon();
			sql="select * from seat_autochange_apply  where `status`='Confirmed' and sfyx='Y' and datediff(now(), updatedate) > 7";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<SeatAutochangeApply>();
			while(rs.next()){
				list.add(new SeatAutochangeApply(rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("seatnobefore"),
						rs.getString("seatno"),
						rs.getString("leadercode"),
						rs.getString("leadername"),
						rs.getString("createdate"),
						rs.getString("updatedate"),
						rs.getInt("flag"),
						rs.getString("status"),
						rs.getString("sfyx"),
						rs.getString("remarkA"),
						rs.getString("remarkB"),
						rs.getString("remarkC")));
			}
			rs.close();
		}catch(Exception e){
			logger.error("获取HKADM 7天未审批的换位申请列表信息时，网络连接异常");
			throw new Exception(e);
		}finally{
			con.close();
		}
		return list;
	}

	/**
	 * 获取需要发起换位申请的人员信息
	 * @return
	 * @throws 
	 */
	public List<SeatAutochangeListfromvsm> getPromotionList() {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		List<SeatAutochangeListfromvsm> list=null;
		try{
			con=DBManager.getCon();
			sql="select * from seat_autochange_listfromvsm where changeflag=0 and sfyx='Y'";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<SeatAutochangeListfromvsm>();
			while(rs.next()){
				list.add(new SeatAutochangeListfromvsm(
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("leadercode"),
						rs.getString("leadername"),
						rs.getInt("changetype"),
						rs.getInt("changeflag"),
						"",//创建时间
						"Y",
						""
						));
			}
			rs.close();
		}catch(Exception e){
			logger.error("同步晋升至AD的人员信息时，网络连接异常");
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<SeatList> getSeatList(String letter) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		List<SeatList> list=null;
		try{
			con=DBManager.getCon();
			sql="select seatno from seat_list where `status` = 'Y' and seatno like '"+letter+"%'";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<SeatList>();
			while(rs.next()){
				list.add(new SeatList(rs.getString("seatno")));
			}
			rs.close();
		}catch(Exception e){
			logger.error("获取AD座位异常");
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 保存新增的转正/晋升至AD/PA的人员信息
	 * @param list   List<SeatAutochangeListfromvsm>
	 */
	public void saveList(List<SeatAutochangeListfromvsm> list){
		Connection con = null;
		try{
			
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			StringBuffer sql=new StringBuffer("insert into seat_autochange_listfromvsm(staffcode,staffname,leadercode,leadername,changetype,changeflag,createdate,sfyx,remark) values(?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps=con.prepareStatement(sql.toString());
			
			for(int i=0;i<list.size();i++){
				SeatAutochangeListfromvsm seatautolist=list.get(i);
				ps.setString(1, seatautolist.getStaffcode());
				ps.setString(2, seatautolist.getStaffname());
				ps.setString(3, seatautolist.getLeadercode());
				ps.setString(4, seatautolist.getLeadername());
				ps.setInt(5, seatautolist.getChangetype());
				ps.setInt(6, seatautolist.getChangeflag());
				ps.setString(7, DateUtils.getNowDateTime());
				ps.setString(8, seatautolist.getSfyx());
				ps.setString(9, seatautolist.getRemark());
				ps.addBatch();
			}
			if(!sql.toString().equals("")){
				ps.executeBatch();
			}
			
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
				logger.error("同步staff信息时 数据异常进行数据回滚");
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.info("同步staff信息时数据回滚异常   "+e);
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
	 * 更新申请状态并发送邮件提醒
	 * @param list
	 */
	public void updateLateSubmitList(List<SeatAutochangeApply> list){
		Connection con = null;
		PreparedStatement ps = null;
		try{
			
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			
			for(int i=0;i<list.size();i++){
				SeatAutochangeApply seatautolist=list.get(i);
				
				//更新申请
				StringBuffer sql1=new StringBuffer("update seat_autochange_apply set `status`='Confirmed',updatedate=?,remarkB='審批超時，系統自動提交'  where sfyx = 'Y' and refno = ?;");
				ps=con.prepareStatement(sql1.toString());
				ps.setString(1, DateUtils.getNowDateTime());
				ps.setString(2, seatautolist.getRefno());
				int flag1 = ps.executeUpdate();
		        if(flag1<0){
				 throw new RuntimeException("SYSTEM自动更新顾问Confirmed状态失败.");
			    }	
				//保存操作记录
			      SeatChangeOperation operation = new SeatChangeOperation();
				  operation.setRefno(seatautolist.getRefno());
				  operation.setOperationstatus("Confirmed");
				  operation.setOperationname("SYSTEM");
			      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			      operation.setOperationdate(sdf.format(new Date()));
			      String sql2="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			      ps= con.prepareStatement(sql2);
	              ps.setString(1, operation.getRefno());
			      ps.setString(2, operation.getOperationstatus());
			      ps.setString(3, operation.getOperationname());
			      ps.setString(4, operation.getOperationdate());
			      int flag2 = ps.executeUpdate();
			      if(flag2<1){
					 throw new RuntimeException("保存SYSTEM自动更新顾问Confirmed状态 operation失败.");
				  }
				
/*			      
					String[] str=new String[2];
				    str[0]=seatautolist.getLeadercode();
				    str[1]=seatautolist.getStaffcode();
				    //获取邮箱
				    String  [] email=getEmailByCode(str);
					String to = email[0];
					String cc = email[1]+"adminfo@convoy.com.hk";
					
			        //发送邮件
			      	String url = "";
			      	if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
			      		url = Util.getProValue("public.system.uatlink");
			      	}else{
			      		url = Util.getProValue("public.system.link");
			      	}
				    String content="Dear "+seatautolist.getLeadername()+",<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;由於您超過7天未審批流程,系統已自動同意您下屬的換位請求<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;并將該申請轉至下一個流程審核人審核，您可以通過點擊以下鏈接查看：<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>"+url+"</a><br/>";
					content+="<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;關於電話設置，如果您有任何查詢請聯繫3667ADM.<br/>";
					content+="<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				    
				    String result=SendMail.send("COAT – Seat Auto Change Request",to,cc,null,null,content,null,null,null);
				    JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}*/
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
	 * 更新申请状态并发送邮件提醒
	 * @param list
	 */
	public void updateLateConfirmList(List<SeatAutochangeApply> list){
		Connection con = null;
		PreparedStatement ps = null;
		try{
			
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			
			for(int i=0;i<list.size();i++){
				SeatAutochangeApply seatautolist=list.get(i);
				
				//更新申请
				StringBuffer sql1=new StringBuffer("update seat_autochange_apply set `status`='Completed',updatedate=?,remarkC='審批超時，系統自動提交'  where sfyx = 'Y' and refno = ?;");
				ps=con.prepareStatement(sql1.toString());
				ps.setString(1, DateUtils.getNowDateTime());
				ps.setString(2, seatautolist.getRefno());
				int flag1 = ps.executeUpdate();
				if(flag1<0){
					throw new RuntimeException("SYSTEM自动更新顾问Completed状态失败.");
				}	
				//保存操作记录
				SeatChangeOperation operation = new SeatChangeOperation();
				operation.setRefno(seatautolist.getRefno());
				operation.setOperationstatus("Completed");
				operation.setOperationname("SYSTEM");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				operation.setOperationdate(sdf.format(new Date()));
				String sql2="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
				ps= con.prepareStatement(sql2);
				ps.setString(1, operation.getRefno());
				ps.setString(2, operation.getOperationstatus());
				ps.setString(3, operation.getOperationname());
				ps.setString(4, operation.getOperationdate());
				int flag2 = ps.executeUpdate();
				if(flag2<1){
					throw new RuntimeException("保存SYSTEM自动更新顾问Completed状态 operation失败.");
				}
								
				//seat_autochange_listfromvsm 表changeflag 从1-->2; 
				String sql3="update seat_autochange_listfromvsm set changeflag=2 where staffcode=? and changetype=? and sfyx='Y'";
				ps=con.prepareStatement(sql3);
				ps.setString(1, seatautolist.getStaffcode());
				ps.setInt(2, seatautolist.getFlag());
				int flag3 = ps.executeUpdate();
				if(flag3<0){
					throw new RuntimeException("更新seat_autochange_listfromvsm表changeflag 从1-->2失败.");
				}
				
				//seat_autochange_seatstatus 表 seatno状态 从1--> 0;
				String sql4="update seat_autochange_seatstatus set `status` =0 where refno=?";
				ps=con.prepareStatement(sql4);
				ps.setString(1, seatautolist.getRefno());
				int flag4 = ps.executeUpdate();
				if(flag4<0){
					throw new RuntimeException("更新seat_autochange_seatstatus表seatno状态 从1-->0失败.");
				}
				
				SeatList seatList = getSeatMsgBySeatNo(seatautolist.getSeatno());
				SeatList seatlistbefore = getSeatMsgBySeatNo(seatautolist.getSeatnobefore());
				
				/*换位前顾问已有座位*/
				if(!Util.objIsNULL(seatautolist.getSeatnobefore())){
					if((!"15F".equals(seatList.getFloor()))&&seatList.getFloor().equals(seatlistbefore.getFloor())){
						
						String sql5 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag5 = super.update2(sql5, "",
								"",
								"",
								seatList.getPigenBoxno(),
								seatautolist.getSeatnobefore());
						if(flag5 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql6 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag6 = super.saveEntity(sql6,seatautolist.getSeatnobefore(),"SYSTEM",sdft.format(new Date()),"系统换位");
						if(flag6 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}

						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatautolist.getStaffcode(),
								seatautolist.getStaffname(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
										seatautolist.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql8 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag8 = super.saveEntity(sql8,seatautolist.getSeatno(),"SYSTEM",sdft1.format(new Date()),"系统换位");
						if(flag8 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						
					}else{
						
						String sql5 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag5 = super.update2(sql5, "",
								"",
								"",
								seatList.getPigenBoxno(),
								seatautolist.getSeatnobefore());
						if(flag5 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql6 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag6 = super.saveEntity(sql6,seatautolist.getSeatnobefore(),"SYSTEM",sdft.format(new Date()),"系统换位");
						if(flag6 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatautolist.getStaffcode(),
								seatautolist.getStaffname(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
								seatList.getPigenBoxno(),
								seatautolist.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql8 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag8 = super.saveEntity(sql8,seatautolist.getSeatno(),"SYSTEM",sdft1.format(new Date()),"系统换位");
						if(flag8 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
						
					}						
					
				}
				
				if(Util.objIsNULL(seatautolist.getSeatnobefore())){
					
					if(!"15F".equals(seatList.getFloor())){
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatautolist.getStaffcode(),
								seatautolist.getStaffname(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
										Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
												seatautolist.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql8 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag8 = super.saveEntity(sql8,seatautolist.getSeatno(),"SYSTEM",sdft.format(new Date()),"系统换位");
						if(flag8 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
					}else{
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatautolist.getStaffcode(),
								seatautolist.getStaffname(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
										seatList.getPigenBoxno(),
										seatautolist.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql8 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag8 = super.saveEntity(sql8,seatautolist.getSeatno(),"SYSTEM",sdft.format(new Date()),"系统换位");
						if(flag8 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
					}		
				}
				
				//订单号生成
				String newrefno=findref(i);
				synchronized (this) {
					if(Util.objIsNULL(newrefno))
						throw new Exception("流水号产生异常");
				}
				
				C_Seatassignment cs= new C_Seatassignment(newrefno,seatautolist.getStaffcode(),seatautolist.getStaffname(),seatList.getLocation(),seatList.getExtensionno(),seatList.getFloor(),
						seatautolist.getSeatno(),seatList.getLockerno(),seatList.getDeskDrawerno(),seatList.getPigenBoxno(),"系统自动换位："+seatautolist.getRefno(),"SYSTEM",
							DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y);				
				String	sql12="insert c_seatassignment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
				ps=con.prepareStatement(sql12);
				ps.setString(1, cs.getRefno());
				ps.setString(2, cs.getStaffcode() ); 
				ps.setString(3, cs.getStaffname()); 
				ps.setString(4, cs.getLocation());
				ps.setString(5, cs.getExtensionno());
				ps.setString(6, cs.getFloor());
				ps.setString(7, cs.getSeatno());
				ps.setString(8, cs.getLockerno());
				ps.setString(9, cs.getDeskDrawerno());
				ps.setString(10, cs.getPigenBoxno());
				ps.setString(11, cs.getRemark());
				ps.setString(12, cs.getCreator());
				ps.setString(13, cs.getCreatDate());
				ps.setString(14, cs.getStatus());
				ps.setString(15, cs.getSfyx());
				logger.info(cs.getRefno()+"在C_Seatassignment save成功！");
				int flag12 = ps.executeUpdate();
				if(flag12<1){
					throw new RuntimeException("保存C_Seatassignment操作失败.");
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
	 * 增加换位申请
	 * @param 
	 */
	public void addChangeApply(){
		Connection con = null;
		List<SeatAutochangeListfromvsm> list = null;
		try{
			
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			list = getPromotionList();
			if(Util.objIsNULL(list)||list.size()<=0){
				//throw new RuntimeException("获取换位列表异常");
				return ;
			}
				
				//将新增的转正/PA或者晋升为AD的顾问信息加入换位申请记录表中
				SeatAutochangeApply sac = new SeatAutochangeApply();
				
				StringBuffer sql2=new StringBuffer("insert into seat_autochange_apply(refno,staffcode,staffname,seatnobefore,seatno,leadercode,leadername,createdate,updatedate,flag,status,sfyx,remarkA,remarkB,remarkC) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				PreparedStatement ps2=con.prepareStatement(sql2.toString());
				
				
				String num="";
				String refno="";
				synchronized (this) {
					//生产流水号
					num = getNo();
					if(Util.objIsNULL(num)){
						throw new Exception("流水号产生异常");
					}
				}
				
				
				for(int i=0;i<list.size();i++){
					SeatAutochangeListfromvsm saclist=list.get(i);
					
					String sum = "";
					if((Integer.parseInt(num)+i+1)<=9){
						sum="000"+(Integer.parseInt(num)+i+1);
					}else if((Integer.parseInt(num)+i+1)<=99){
						sum="00"+((Integer.parseInt(num)+i+1));
					}else if((Integer.parseInt(num)+i+1)<=999){
						sum="0"+((Integer.parseInt(num)+i+1));
					}else{
						sum=""+((Integer.parseInt(num)+i+1));
					}
					refno=Constant.SEATAUTOCHANGE+DateUtils.Ordercode()+sum;
					
					sac.setRefno(refno);
					
					sac.setStaffcode(saclist.getStaffcode());
					//是否需要判断该staffcode是否已有未结束的主动换位申请 TODO
					
					sac.setFlag(saclist.getChangetype());
					sac.setLeadercode(saclist.getLeadercode());
					
					//获取顾问换座之前的座位号
					String seatnobefore = getSeatnoByStaffcode(saclist.getStaffcode());
					sac.setSeatnobefore(seatnobefore);
					
					//获取座位号
					String seatno = "";
					if(!Util.objIsNULL(saclist.getChangetype())&&(saclist.getChangetype()==0||saclist.getChangetype()==2)){
						//转正/PA获取座位方式
						seatno = getSeatnoByLeaderSeatno(saclist.getLeadercode());
						
						//如果领导附件没有空位，那么选择一个首字母和领导相同的空位给该顾问
						if(Util.objIsNULL(seatno)){
							seatno = getSeatnoByLeaderSeatnoLetter(saclist.getLeadercode());
						}	
						
						//如果领导附件没有空位，那么随机选择领导本层的一个空位给该顾问
						if(Util.objIsNULL(seatno)){
							seatno = getSeatnoByLeaderFloor(saclist.getLeadercode());
						}	
					}else{
						//晋升至AD获取座位方式
						seatno = getNearADSeatnoByLeaderSeatno(saclist.getLeadercode());
						if(Util.objIsNULL(seatno)){
							seatno = getADSeatnoByLeaderSeatno(saclist.getLeadercode());
						}
					}
					
					
					if(Util.objIsNULL(seatno)){											
						String sql5="update seat_autochange_listfromvsm set remark=?  where sfyx='Y' and  changeflag=0 and staffcode=? and changetype=? and leadercode=? ";
						PreparedStatement ps5=con.prepareStatement(sql5);
						ps5.setString(1, "領導附近暫無空位");
						ps5.setString(2, sac.getStaffcode());
						ps5.setInt(3, sac.getFlag());
						ps5.setString(4, sac.getLeadercode());
						int flag5 = ps5.executeUpdate();
					    if(flag5<0){
							 throw new RuntimeException("修改seat_autochange_listfromvsm 标记出现异常");
						}
						continue;
					}else{
						int nums = saveSeatNo(sac.getRefno(),seatno,1);
						if(nums<1){
							throw new RuntimeException("保存座位状态记录异常");
						}
					}
					
					
					sac.setSeatno(seatno);
					sac.setStaffname(saclist.getStaffname());
					sac.setLeadername(saclist.getLeadername());
					sac.setCreatedate(DateUtils.getNowDateTime());
					sac.setUpdatedate(DateUtils.getNowDateTime());
					sac.setStatus("Submitted");
					sac.setSfyx("Y");
					sac.setRemarkA("系統自動產生");
					sac.setRemarkB("");
					sac.setRemarkC("");
					
					ps2.setString(1, sac.getRefno());
					ps2.setString(2, sac.getStaffcode());
					ps2.setString(3, sac.getStaffname());
					ps2.setString(4, sac.getSeatnobefore());
					ps2.setString(5, sac.getSeatno());
					ps2.setString(6, sac.getLeadercode());
					ps2.setString(7, sac.getLeadername());
					ps2.setString(8, sac.getCreatedate());
					ps2.setString(9, sac.getUpdatedate());
					ps2.setInt(10, sac.getFlag());
					ps2.setString(11, sac.getStatus());
					ps2.setString(12, sac.getSfyx());
					ps2.setString(13, sac.getRemarkA());
					ps2.setString(14, sac.getRemarkB());
					ps2.setString(15, sac.getRemarkC());
					ps2.addBatch();
					
					
					
					
					//将已提交换位申请的顾问的状态修改为1，代表之后的定时任务将不再对其作用.
					
					String sql4="update seat_autochange_listfromvsm set changeflag=?,remark=?  where sfyx='Y' and  changeflag=0 and staffcode=? and changetype=? and leadercode=? ";
					
					PreparedStatement ps4=con.prepareStatement(sql4);
					ps4.setInt(1, 1);
					ps4.setString(2, "");
					ps4.setString(3, sac.getStaffcode());
					ps4.setInt(4, sac.getFlag());
					ps4.setString(5, sac.getLeadercode());
					int flag1 = ps4.executeUpdate();
				    if(flag1<0){
						 throw new RuntimeException("修改seat_autochange_listfromvsm 标记出现异常");
					}else{
						//给Leader邮箱发送下属换位邮件提醒
						String[] str=new String[2];
					    str[0]=saclist.getLeadercode();
					    str[1]=saclist.getStaffcode();
					    //获取leader邮箱
					    String  [] email=getEmailByCode(str);
						String to = email[0];
						String cc = email[1];
						
					      //发送邮件通知
				      	String url = "";
				      	if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				      		url = Util.getProValue("public.system.uatlink");
				      	}else{
				      		url = Util.getProValue("public.system.link");
				      	}
				      	
				      	if(saclist.getChangetype()==0||saclist.getChangetype()==2){
						    String content="Dear "+saclist.getLeadername()+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat assignment for your teammate "+saclist.getStaffname()+" is pending your confirmation.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> . (Step: Login in the COAT System, Choose COAT-SeatAssignment --> Recruiter's Corner) <br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;If we do not receive a response from you within 7 days, we will assume you agree the assignment and we will proceed with the seat setting accordingly. <br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						    
						    String result=SendMail.send("COAT – Seat Auto Change Request",to,null,null,null,content,null,null,null);
						    JSONObject json=new JSONObject(result);
							if(json.get("state")=="error"){
								throw new RuntimeException((String)json.get("msg"));
							}
				      	}else if(saclist.getChangetype()==1){
						    String content="Dear "+saclist.getStaffname()+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat assignment is pending your confirmation.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> . (Step: Login in the COAT System, Choose COAT-SeatAssignment --> Recruiter's Corner) <br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;If we do not receive a response from you within 7 days, we will assume you agree the assignment and we will proceed with the seat setting accordingly. <br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						    
						    String result=SendMail.send("COAT – Seat Auto Change Request",cc,null,null,null,content,null,null,null);
						    JSONObject json=new JSONObject(result);
							if(json.get("state")=="error"){
								throw new RuntimeException((String)json.get("msg"));
							}
				      	}else{
				      		throw new RuntimeException("不符合发送邮件的类型");
				      	}
						
					}
					
				}
				if(!sql2.toString().equals("")){
					ps2.executeBatch();
				}	
			
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
				logger.error("系统自动生成换位信息时 数据异常进行数据回滚");
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.info("系统自动生成换位信息时数据回滚异常   "+e);
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
	 * 将已安排的座位号存于附表，用于避免重复选择 
	 * @param seatassignment
	 * @return
	 */
	public int saveSeatNo(String refno,String seatno,int amount) {
		PreparedStatement pr=null;
		Connection con = null;
		int num = 0;
		String	sql="insert into seat_autochange_seatstatus (refno,seatno,status)values(?,?,?)";
		logger.info("保存seat_autochange_seatstatus:"+sql);
		try {
			con=DBManager.getCon();
			pr=con.prepareStatement(sql);
			pr.setString(1, refno);
			pr.setString(2, seatno); 
			pr.setInt(3, amount); 
			logger.info("保存seat_autochange_seatstatus:成功！");
			num = pr.executeUpdate();
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存seat_autochange_seatstatus异常："+e);
			return 0;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}
	
	
	public List<String> getSeatnoListByLeaderSeatnoLetter(String leadercode) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		//取出座位号的首字母
		String headLetter = leaderSeatno.substring(0,1);
		
		seatList = findSeatListByLetter(leaderLocation,leaderFloor,headLetter);
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList;
		}
	}
	
	public List<String> getSeatnoListByLeaderSeatno(String leadercode,int flag) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		//领导同一层合法座位
		if(!Util.objIsNULL(flag)){
			seatList = ChooseSeatListToRegular(leaderLocation,leaderFloor,flag);
		}
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList;
		}
	}
	
	
	
	/**
	 * 通过Leader的座位号查询和领导座位首字母相同的座位号并选择一个空座位
	 * @param leadercode
	 * @return
	 */
	public String getSeatnoByLeaderSeatnoLetter(String leadercode) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		//取出座位号的首字母
		String headLetter = leaderSeatno.substring(0,1);
		
		seatList = findSeatListByLetter(leaderLocation,leaderFloor,headLetter);
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList.get(0);
		}
	}
	/**
	 * 通过Leader的座位号查询同一层楼并选择一个空座位
	 * @param leadercode
	 * @return
	 */
	public String getSeatnoByLeaderFloor(String leadercode) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		seatList = findSeatList(leaderLocation,leaderFloor);
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList.get(0);
		}
	}
	/**
	 * 通过Leader的座位号查询附件空座位号且在同一层楼并选择一个空座位
	 * @param leadercode
	 * @return
	 */
	public String getSeatnoByLeaderSeatno(String leadercode) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		
		//取出座位号的首字母
		String headLetter = leaderSeatno.substring(0,1);
		String otherNum = leaderSeatno.substring(1);
		int num = -1;
		//获取除首字母之外的座位编号
		if(Util.objIsNULL(otherNum)){
			return null;
		}else{
			num = Integer.parseInt(otherNum);
		}
		
		if(num<=10){
			for (int i = 1; i < 10; i++) {
				
				String seatnum = headLetter+"00"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			for (int i = 10; i < num+10; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			
		}else if(num>10 && num<=20){
			for (int i = num-10; i < 10; i++) {
				String seatnum = headLetter+"00"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			for (int i = 10; i < num+10; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}else if(num>20 && num<90){
			for (int i = num-10; i <= num+10; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}else if(num>=90 && num<110){
			for (int i = num-10; i <100; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			for (int i = 100; i<num+10; i++) {
				String seatnum = headLetter+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}else{
			for (int i = num-10; i<num+10; i++) {
				String seatnum = headLetter+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList.get(0);
		}
	}
	/**
	 * 通过Leader的座位号查询AD附件空座位号且在同一层楼并选择一个空座位
	 * @param leadercode
	 * @return
	 */
	public String getNearADSeatnoByLeaderSeatno(String leadercode) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		
		//取出座位号的首字母
		String headLetter = leaderSeatno.substring(0,1);
		String otherNum = leaderSeatno.substring(1);
		int num = -1;
		//获取除首字母之外的座位编号
		if(Util.objIsNULL(otherNum)){
			return null;
		}else{
			num = Integer.parseInt(otherNum);
		}
		
		if(num<=10){
			for (int i = 1; i < 10; i++) {
				String seatnum = headLetter+"00"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			for (int i = 10; i < num+10; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			
		}else if(num>10 && num<=20){
			for (int i = num-10; i < 10; i++) {
				String seatnum = headLetter+"00"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			for (int i = 10; i < num+10; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}else if(num>20 && num<90){
			for (int i = num-10; i <= num+10; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}else if(num>=90 && num<110){
			for (int i = num-10; i <100; i++) {
				String seatnum = headLetter+"0"+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
			for (int i = 100; i<num+10; i++) {
				String seatnum = headLetter+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}else{
			for (int i = num-10; i<num+10; i++) {
				String seatnum = headLetter+i;
				if(seatIsHidden(seatnum)&&seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)&&(!seatSameFloor(seatnum,leaderLocation,leaderFloor))){
					seatList.add(seatnum);
				}
			}
		}
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList.get(0);
		}
	}
	/**
	 * 通过Leader的座位号查询附件空座位号且在同一层楼并选择一个空座位
	 * @param leadercode
	 * @return
	 */
	public String getADSeatnoByLeaderSeatno(String leadercode) {
		
		List<String> seatList = new ArrayList<String>();
		
		String leaderSeatno = getSeatnoByStaffcode(leadercode);
		String leaderLocation = getLocationByStaffcode(leadercode);
		String leaderFloor = getFloorByStaffcode(leadercode);
		if(Util.objIsNULL(leaderSeatno)){
			return null;
		}
		
		//取出座位号的首字母
		String headLetter = leaderSeatno.substring(0,1);
		
		List<SeatList> list = getSeatList(headLetter);
		if(Util.objIsNULL(list)||list.size()<=0){
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			SeatList  seatADList = list.get(i);
			String seatno = seatADList.getSeatno();
			if(seatIsHidden(seatno)&&seatIsEmpty(seatno)&&seatLegitimate(seatno)&&seatIsADSeat(seatno)&&(!seatSameFloor(seatno,leaderLocation,leaderFloor))){
				seatList.add(seatno);
			}
		}
		if(Util.objIsNULL(seatList)||seatList.size()<=0){
			return null;
		}else{
			return seatList.get(0);
		}
	}


	/**
	 * 查询座位号是否存在
	 * @param Seatno
	 * @return
	 */
	public boolean seatIsExist(String seatno) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = false;
		try {
			sql = "select seatno from seat_list where seatno=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取seatno出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return flag;
	}
	/**
	 * 根据座位号查询座位是否为空
	 * @param Seatno
	 * @return
	 */
	public boolean seatIsEmpty(String seatno) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select staffcode from seat_list where seatno=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(!Util.objIsNULL(rs.getString("staffcode"))){
					flag = false;
				}
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
	/**
	 * 根据座位号查询座位是否是隐形座位
	 * @param Seatno
	 * @return
	 */
	public boolean seatIsHidden(String seatno) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select ifhidden from seat_list where seatno=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				if("Y".equals(rs.getString("ifhidden"))){
					flag = false;
				}
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
	/**
	 * 根据座位号查询座位是否为AD或者DD座位
	 * @param Seatno
	 * @return
	 */
	public boolean seatIsDDSeat(String seatno) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select remark from seat_list where seatno=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(("DD".equals(rs.getString("remark")))||("AD".equals(rs.getString("remark")))){
					flag = false;
				}
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
	/**
	 * 根据座位号查询座位是否为AD座位
	 * @param Seatno
	 * @return
	 */
	public boolean seatIsADSeat(String seatno) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = false;
		try {
			sql = "select remark from seat_list where seatno=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				if("AD".equals(rs.getString("remark"))){
					flag = true;
				}
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
	
	/**
	 * 根据座位号查询座位是否已被占用
	 * @param Seatno
	 * @return
	 */
	public boolean seatLegitimate(String seatno) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select * from seat_autochange_seatstatus where seatno=? and status='1'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
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
	/**
	 * 判断座位是否与Leader座位在同一楼层
	 * @param Seatno
	 * @return
	 */
	public boolean seatSameFloor(String seatno,String location,String floor) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select * from seat_list where seatno=? and location=? and floor=?  and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			ps.setString(2, location);
			ps.setString(3, floor);
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

	
	public List<String> findSeatList(String location,String floor) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		List<String> list = new ArrayList<String>();
		try {
			sql = "select seatno from seat_list where `status` = 'Y' and remark = '' and ifhidden='N'  and staffcode='' and location=? and floor=? and seatno not in (select seatno from seat_autochange_seatstatus where STATUS = '1' )";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, location);
			ps.setString(2, floor);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("seatno"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取空座位号列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}
	public List<String> findSeatListByLetter(String location,String floor,String headLetter) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		List<String> list = new ArrayList<String>();
		try {
			sql = "select seatno from seat_list where `status` = 'Y' and remark = '' and staffcode='' and ifhidden='N' and location=? and floor=? and seatno like '"+headLetter+"%' and seatno not in (select seatno from seat_autochange_seatstatus where STATUS = '1' ) ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, location);
			ps.setString(2, floor);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("seatno"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取空座位号列表出现异常：" + e);
			
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}
	public List<String> ChooseSeatListToRegular (String location,String floor,int flag) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		List<String> list = new ArrayList<String>();
		try {
			if((!Util.objIsNULL(flag)) && (flag==0 ||flag==2)){
				sql = "select seatno from seat_list where `status` = 'Y' and remark != 'AD' and remark != 'DD'  and ifhidden='N' and staffcode='' and location=? and floor=? and seatno not in (select seatno from seat_autochange_seatstatus where STATUS = '1' ) ";
			}else if((!Util.objIsNULL(flag)) && flag==1){
				sql = "select seatno from seat_list where `status` = 'Y' and remark = 'AD' and ifhidden='N' and staffcode='' and location=? and floor=? and seatno not in (select seatno from seat_autochange_seatstatus where STATUS = '1' ) ";
			}else{
				throw new Exception("非转正也非晋升AD请求，不合法！");
			}
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, location);
			ps.setString(2, floor);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("seatno"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取空座位号列表出现异常：" + e);
			
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}
	
	
	
	
	
	
	/**
	 * 通过staffcode查询座位号
	 * @param leadercode
	 * @return
	 */
	public  String getSeatnoByStaffcode(String staffcode) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String seatno = null;
		try {
			sql = "select seatno from seat_list where staffcode=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatno = rs.getString("seatno");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取Leader Seatno出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return seatno;
	}
	/**
	 * 通过staffcode查询Location
	 * @param leadercode
	 * @return
	 */
	public  String getLocationByStaffcode(String staffcode) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String location = null;
		try {
			sql = "select location from seat_list where staffcode=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				location = rs.getString("location");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取Leader location出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return location;
	}
	/**
	 * 通过staffcode查询floor
	 * @param leadercode
	 * @return
	 */
	public  String getFloorByStaffcode(String staffcode) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String floor = null;
		try {
			sql = "select floor from seat_list where staffcode=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				floor = rs.getString("floor");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取Leader floor出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return floor;
	}
	
	public String getNo() {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String num=null;
		try{
			con=DBManager.getCon();
			sql="select count(*) from seat_autochange_apply";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getString(1);
			}
			rs.close();
		}catch (Exception e) {
			logger.error("get refno error");
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	
	/**
	 * 根据code查询Email 
	 */
	public String[] getEmailByCode(String [] str){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String[] email=new String[2];
		try{
			con=DBManager.getCon();
			if(str.length==0){
				return null;
			}
			else if(str[0].equals(str[1])){
				
				String sql="select * from cons_list where EmployeeId=?";
				ps=con.prepareStatement(sql);
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
					ps=con.prepareStatement(sql);
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
			DBManager.closeCon(con);
		}
		return null;
	}

	
	
	public Pager findSeatAutoChangeApplyListByStaffcode(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_autochange_apply " +
				" where   date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(createdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and refno like ? " +
				" and (leadercode like ? or staffcode like ?)"; 
		
		String limit="order by createdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
		
		
	}
	public Pager findSeatAutoChangeApplyListAll(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_autochange_apply " +
				" where   date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(createdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and staffcode like ? " +
				" and staffname like ? " +
				" and leadercode like ? " +
				" and leadername like ? " +
				" and seatno like ? " +
				" and status like ? " +
				" and refno like ? "; 
		
		String limit="order by createdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
		
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, SeatList.class);
	}

	public SeatAutochangeApply queryListByRefno(String refno) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String sql = null;
		SeatAutochangeApply seatAutoChangeApply = null;
		try {
			sql = "select * from seat_autochange_apply where refno=? and sfyx='Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, refno);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatAutoChangeApply = new SeatAutochangeApply();
				seatAutoChangeApply.setRefno(rs.getString("refno"));
				seatAutoChangeApply.setStaffcode(rs.getString("staffcode"));
				seatAutoChangeApply.setStaffname(rs.getString("staffname"));
				seatAutoChangeApply.setSeatnobefore(rs.getString("seatnobefore"));
				seatAutoChangeApply.setSeatno(rs.getString("seatno"));
				seatAutoChangeApply.setLeadercode(rs.getString("leadercode"));
				seatAutoChangeApply.setLeadername(rs.getString("leadername"));
				seatAutoChangeApply.setCreatedate(rs.getString("createdate"));
				seatAutoChangeApply.setUpdatedate(rs.getString("updatedate"));
				seatAutoChangeApply.setFlag(rs.getInt("flag"));
				seatAutoChangeApply.setStatus(rs.getString("status"));
				seatAutoChangeApply.setRemarkA(rs.getString("remarkA"));
				seatAutoChangeApply.setRemarkB(rs.getString("remarkB"));
				seatAutoChangeApply.setRemarkC(rs.getString("remarkC"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatAutoChangeApply;
	}
	
	public ConsList queryConsMsg(String staffcode) {
		ConsList consList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String sql = null;
		try {
			sql = "select EmployeeId,EmployeeName from cons_list where EmployeeId like ?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				consList = new ConsList();
				consList.setEmployeeId(rs.getString("EmployeeId"));
				consList.setEmployeeName(rs.getString("EmployeeName"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return consList;
	}
	
	
	public SeatList querySeatNoBefore(String staffcode) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String sql = null;
		SeatList seatList = null;
		try {
			sql = "select staffcode,staffname,seatno from seat_list where staffcode=? and status = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatList = new SeatList();
				seatList.setSeatno(rs.getString("seatno"));
				seatList.setStaffcode(rs.getString("staffcode"));
				seatList.setStaffname(rs.getString("staffname"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatList;
	}
	public SeatList getSeatMsgBySeatNo(String seatno) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		String sql = null;
		SeatList seatList = null;
		try {
			sql = "select location,lockerno,deskDrawerno,pigenBoxno,extensionno,floor,lockerno,deskDrawerno,pigenBoxno,seatno,ifhidden,remark from seat_list where seatno=? and `status`='Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatList = new SeatList();
				seatList.setLocation(rs.getString("location"));
				seatList.setLockerno(rs.getString("lockerno"));
				seatList.setDeskDrawerno(rs.getString("deskDrawerno"));
				seatList.setPigenBoxno(rs.getString("pigenBoxno"));
				seatList.setExtensionno(rs.getString("extensionno"));
				seatList.setFloor(rs.getString("floor"));
				seatList.setLockerno(rs.getString("lockerno"));
				seatList.setDeskDrawerno(rs.getString("deskDrawerno"));
				seatList.setPigenBoxno(rs.getString("pigenBoxno"));
				seatList.setIfhidden(rs.getString("ifhidden"));
				seatList.setRemark(rs.getString("remark"));
				seatList.setSeatno(seatno);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatList;
	}
	
	
	public 	List<String> queryAllLegalSeatNo(int flag,String staffcode){
		
			List<String> seatList = new ArrayList<String>();
			String leaderSeatno = getSeatnoByStaffcode(staffcode);
			if(Util.objIsNULL(leaderSeatno)){
				return null;
			}
			
			//取出座位号的首字母
			String headLetter = leaderSeatno.substring(0,1);
			//获取除首字母之外的座位编号
			Integer num = Integer.parseInt(leaderSeatno.substring(1));
			if(!Util.objIsNULL(flag)&& flag == 0){
				if(num<=10){
					for (int i = 1; i < 10; i++) {
						String seatnum = headLetter+"00"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					for (int i = 10; i < num+10; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					
				}else if(num>10 && num<=20){
					for (int i = num-10; i < 10; i++) {
						String seatnum = headLetter+"00"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					for (int i = 10; i < num+10; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}else if(num>20 && num<90){
					for (int i = num-10; i <= num+10; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}else if(num>=90 && num<110){
					for (int i = num-10; i <100; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					for (int i = 100; i<num+10; i++) {
						String seatnum = headLetter+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}else{
					for (int i = num-10; i<num+10; i++) {
						String seatnum = headLetter+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsDDSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}
				
								
			}else{

				if(num<=10){
					for (int i = 1; i < 10; i++) {
						String seatnum = headLetter+"00"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					for (int i = 10; i < num+10; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					
				}else if(num>10 && num<=20){
					for (int i = num-10; i < 10; i++) {
						String seatnum = headLetter+"00"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					for (int i = 10; i < num+10; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}else if(num>20 && num<90){
					for (int i = num-10; i <= num+10; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}else if(num>=90 && num<110){
					for (int i = num-10; i <100; i++) {
						String seatnum = headLetter+"0"+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
					for (int i = 100; i<num+10; i++) {
						String seatnum = headLetter+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}else{
					for (int i = num-10; i<num+10; i++) {
						String seatnum = headLetter+i;
						if(seatIsExist(seatnum)&&seatIsEmpty(seatnum)&&seatLegitimate(seatnum)&&seatIsADSeat(seatnum)){
							seatList.add(seatnum);
						}
					}
				}				
			}
			return seatList;
	}
	
	
	
	public int LeaderRefusedApply(SeatAutochangeApply seatAutochangeApply,int flag, String name) {
		int num = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql1="update seat_autochange_apply set seatno=? , status=? , updatedate=? , remarkB=?  where  refno=?";
			ps=con.prepareStatement(sql1);
			ps.setString(1, seatAutochangeApply.getSeatno());
			ps.setString(2, "Refused");
			ps.setString(3, DateUtils.getNowDateTime());
			ps.setString(4, seatAutochangeApply.getRemarkB());
			ps.setString(5, seatAutochangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException("更新seat_autochange_apply表refused状态失败.");
			}
			

			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatAutochangeApply.getRefno());
			operation.setOperationstatus(seatAutochangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String sql4="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(sql4);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag4 = ps.executeUpdate();
			if(flag4<1){
				throw new RuntimeException("保存自动换位申请refused operation失败.");
			}
			
			String sql2="update seat_autochange_listfromvsm set changeflag=0 where staffcode=? and changetype=? and sfyx='Y'";
			ps=con.prepareStatement(sql2);
			ps.setString(1, seatAutochangeApply.getStaffcode());
			ps.setInt(2, flag);
			int flag2 = ps.executeUpdate();
			if(flag2<0){
				throw new RuntimeException("更新seat_autochange_listfromvsm表changeflag 从1-->0失败.");
			}
			
			String sql3="update seat_autochange_seatstatus set `status` =0 where refno=?";
			ps=con.prepareStatement(sql3);
			ps.setString(1, seatAutochangeApply.getRefno());
			int flag3 = ps.executeUpdate();
			if(flag3<0){
				throw new RuntimeException("更新seat_autochange_seatstatus表seatno状态 从1-->0失败.");
			}

			con.commit();
			logger.info("refused seat_autochange_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("refused seat_autochange_apply failed"+e.toString());
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);   
		}
		return num;
	}	
	public int updateApply(SeatAutochangeApply seatAutochangeApply,SeatList seatList,SeatList seatlistbefore,String userType,int flag, String name, String staffname, String leadername,String to, String cc,String leaderLocation,String leaderFloor) {
		int num = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = DBManager.getCon();
			con.setAutoCommit(false);
			
		    //判断UAT环境还是生成环境
	      	String url = "";
	      	if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
	      		url = Util.getProValue("public.system.uatlink");
	      	}else{
	      		url = Util.getProValue("public.system.link");
	      	}
	      	String content = "";
	      	//转正和PA
	      	if(!Util.objIsNULL(flag) && (flag==0||flag==2) ){
	      	
	      		//和领导同一楼层
			if(!seatSameFloor(seatAutochangeApply.getSeatno(),leaderLocation,leaderFloor)){
				
				String sql1="update seat_autochange_apply set status=?, updatedate=?, remarkB=?, seatno=?  where  refno=?";
				ps=con.prepareStatement(sql1);
				ps.setString(1, "Completed");
				ps.setString(2, DateUtils.getNowDateTime());
				ps.setString(3, seatAutochangeApply.getRemarkB());
				ps.setString(4, seatAutochangeApply.getSeatno());
				ps.setString(5, seatAutochangeApply.getRefno());
				int flag1 = ps.executeUpdate();
				if(flag1<0){
					throw new RuntimeException("更新seat_autochange_apply表completed状态失败.");
				}
				
				//seat_autochange_listfromvsm 表changeflag 从1-->2; 
				String sql4="update seat_autochange_listfromvsm set changeflag=2 where staffcode=? and changetype=? and sfyx='Y'";
				ps=con.prepareStatement(sql4);
				ps.setString(1, seatAutochangeApply.getStaffcode());
				ps.setInt(2, flag);
				int flag4 = ps.executeUpdate();
				if(flag4<0){
					throw new RuntimeException("更新seat_autochange_listfromvsm表changeflag 从1-->2失败.");
				}
				
				//seat_autochange_seatstatus 表 seatno状态 从1--> 0;
				String sql5="update seat_autochange_seatstatus set `status` =0 where refno=?";
				ps=con.prepareStatement(sql5);
				ps.setString(1, seatAutochangeApply.getRefno());
				int flag5 = ps.executeUpdate();
				if(flag5<0){
					throw new RuntimeException("更新seat_autochange_seatstatus表seatno状态 从1-->0失败.");
				}
				
				//换位前不是空位
				if(!Util.objIsNULL(seatAutochangeApply.getSeatnobefore())){
					//非15F，同一层换
					if((!"15F".equals(seatList.getFloor()))&&seatList.getFloor().equals(seatlistbefore.getFloor())){
						
						String sql6 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql6, "",
								"",
								"",
								seatList.getPigenBoxno(),
								seatAutochangeApply.getSeatnobefore());
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatAutochangeApply.getSeatnobefore(),name,sdf.format(new Date()),"系统换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}

						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
								seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}

						//如果申请人是PA，则邮箱不显示chickenbox no
						if(IsPA(seatAutochangeApply.getStaffcode())){
							//发送邮件提醒
				      		content+="Dear "+staffname+",<br/>";
							content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
				      		+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+""+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
						}else{
							//发送邮件提醒
				      		content+="Dear "+staffname+",<br/>";
							content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
				      		+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+seatlistbefore.getPigenBoxno()+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						}						
						
						

						
						String result=SendMail.send("COAT – Seat Auto Change Request",to,"adminfo@convoy.com.hk",null,null,content,null,"email.ftl",null);
						JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
						
					}else{
						
						
						
						String sql6 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql6, "",
								"",
								"",
								seatlistbefore.getPigenBoxno(),
								seatAutochangeApply.getSeatnobefore());
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatAutochangeApply.getSeatnobefore(),name,sdf.format(new Date()),"系统换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
								seatList.getPigenBoxno(),
								seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
						//需要判断楼层是否不同发送邮件 20181012新增需求 
						if(!(seatList.getFloor().equals(seatlistbefore.getFloor()))){
							String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
							int flag88 = super.saveEntity(sql88, 
									seatAutochangeApply.getStaffcode(),
									seatAutochangeApply.getStaffname(),
									seatlistbefore.getSeatno(),
									seatlistbefore.getLocation(),
									seatlistbefore.getFloor(),
									seatList.getSeatno(),
									seatList.getLocation(),
									seatList.getFloor(),
									DateUtils.getNowDateTime(),
									1,
									"自动换位"
									);
							if(flag88 < 1){
								throw new RuntimeException("保存成功换座记录失败.");
							}					
							
							
							if(!Util.objIsNULL(seatlistbefore.getStaffcode())){
								String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
								int flag99 = super.saveEntity(sql99, 
										seatAutochangeApply.getStaffcode(),
										seatAutochangeApply.getStaffname(),
										seatList.getSeatno(),
										seatList.getLocation(),
										seatList.getFloor(),
										seatlistbefore.getSeatno(),
										seatlistbefore.getLocation(),
										seatlistbefore.getFloor(),
										DateUtils.getNowDateTime(),
										1,
										"自动换位"
										);
								if(flag99 < 1){
									throw new RuntimeException("保存成功换座记录失败.");
								}
							}
						}
						
						//如果申请人是PA，则邮箱不显示chickenbox no
						if(IsPA(seatAutochangeApply.getStaffcode())){
							//发送邮件提醒
				      		content+="Dear "+staffname+",<br/>";
							content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
				      		+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+""+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
						}else{
							//发送邮件提醒
				      		content+="Dear "+staffname+",<br/>";
							content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
				      		+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+seatList.getPigenBoxno()+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
				      		content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						}							

						String result=SendMail.send("COAT – Seat Auto Change Request",to,"adminfo@convoy.com.hk",null,null,content,null,"email.ftl",null);
						JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
						
						
					}						
					
				}
				
				//换位前是空位
				if(Util.objIsNULL(seatAutochangeApply.getSeatnobefore())){
					
					if(!"15F".equals(seatList.getFloor())){
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
										Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
												seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						//如果申请人是PA，则邮箱不显示chickenbox no
						if(IsPA(seatAutochangeApply.getStaffcode())){
							//发送邮件提醒
							content+="Dear "+staffname+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
							content+="<br/>";		      		
							content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
									+"        <table id='xx' cellpadding=0 cellspacing=0 >"
									+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
									+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+""+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
						}else{
							//发送邮件提醒
							content+="Dear "+staffname+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
							content+="<br/>";		      		
							content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
									+"        <table id='xx' cellpadding=0 cellspacing=0 >"
									+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
									+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+seatlistbefore.getPigenBoxno()+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						}						
						
						
						
						
						String result=SendMail.send("COAT – Seat Auto Change Request",to,"adminfo@convoy.com.hk",null,null,content,null,"email.ftl",null);
						JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
						
						
					}else{
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
										seatList.getPigenBoxno(),
										seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
						//如果申请人是PA，则邮箱不显示chickenbox no
						if(IsPA(seatAutochangeApply.getStaffcode())){
							//发送邮件提醒
							content+="Dear "+staffname+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
							content+="<br/>";		      		
							content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
									+"        <table id='xx' cellpadding=0 cellspacing=0 >"
									+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
									+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+""+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
						}else{
							//发送邮件提醒
							content+="Dear "+staffname+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat assignment had confirmed by "+leadername+". ADM will proceed with the seat setting accordingly.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for result. <br/>";
							content+="<br/>";		      		
							content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
									+"        <table id='xx' cellpadding=0 cellspacing=0 >"
									+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
									+"<tr><td>"+seatAutochangeApply.getStaffcode()+"</td><td>"+staffname+"</td><td>"+seatAutochangeApply.getSeatno()+"</td><td>"+seatList.getPigenBoxno()+"</td><td>"+seatlistbefore.getExtensionno()+"</td><td>"+seatList.getFloor()+" "+seatList.getLocation()+"</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						}							
						
						String result=SendMail.send("COAT – Seat Auto Change Request",to,"adminfo@convoy.com.hk",null,null,content,null,"email.ftl",null);
						JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
					}
				}

				String newrefno=findref();
				synchronized (this) {
					if(Util.objIsNULL(newrefno))
						throw new Exception("流水号产生异常");
				}
				
				C_Seatassignment cs= new C_Seatassignment(newrefno,seatAutochangeApply.getStaffcode(),staffname,seatList.getLocation(),seatList.getExtensionno(),seatList.getFloor(),
						seatAutochangeApply.getSeatno(),seatList.getLockerno(),seatList.getDeskDrawerno(),seatList.getPigenBoxno(),"系统自动换位:"+seatAutochangeApply.getRefno(),"SYSTEM",
							DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y);				
				String	sql12="insert c_seatassignment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
				ps=con.prepareStatement(sql12);
				ps.setString(1, cs.getRefno());
				ps.setString(2, cs.getStaffcode() ); 
				ps.setString(3, cs.getStaffname()); 
				ps.setString(4, cs.getLocation());
				ps.setString(5, cs.getExtensionno());
				ps.setString(6, cs.getFloor());
				ps.setString(7, cs.getSeatno());
				ps.setString(8, cs.getLockerno());
				ps.setString(9, cs.getDeskDrawerno());
				ps.setString(10, cs.getPigenBoxno());
				ps.setString(11, cs.getRemark());
				ps.setString(12, cs.getCreator());
				ps.setString(13, cs.getCreatDate());
				ps.setString(14, cs.getStatus());
				ps.setString(15, cs.getSfyx());
				logger.info(cs.getRefno()+"在C_Seatassignment save成功！");
				int flag12 = ps.executeUpdate();
				if(flag12<1){
					throw new RuntimeException("保存C_Seatassignment操作失败.");
				}
			
				//保存流程审批操作记录
				SeatChangeOperation changeoperation = new SeatChangeOperation();
				changeoperation.setRefno(seatAutochangeApply.getRefno());
				changeoperation.setOperationstatus(seatAutochangeApply.getStatus());
				changeoperation.setOperationname(name);
				SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				changeoperation.setOperationdate(sdfs.format(new Date()));
				String sql11="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
				ps= con.prepareStatement(sql11);
				ps.setString(1, changeoperation.getRefno());
				ps.setString(2, changeoperation.getOperationstatus());
				ps.setString(3, changeoperation.getOperationname());
				ps.setString(4, changeoperation.getOperationdate());
				int flag11 = ps.executeUpdate();
				if(flag11<1){
					throw new RuntimeException("保存自动换位申请Completed operation失败.");
				}
				
			}else{
				//和领导不同楼层
				
				String sql1="update seat_autochange_apply set seatno=? , status=? , updatedate=? , remarkB=?  where  refno=?";
				ps=con.prepareStatement(sql1);
				ps.setString(1, seatAutochangeApply.getSeatno());
				ps.setString(2, "Confirmed");
				ps.setString(3, DateUtils.getNowDateTime());
				ps.setString(4, seatAutochangeApply.getRemarkB());
				ps.setString(5, seatAutochangeApply.getRefno());
				int flag1 = ps.executeUpdate();
			    if(flag1<0){
					 throw new RuntimeException("更新seat_autochange_apply表confirm状态失败.");
				}

	      		content+="Dear HKADM,<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat assignment is pending your confirmation.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> . <br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;If we do not receive a response from you within 7 days, we will assume you agree the assignment and we will proceed with the seat setting accordingly. <br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			    
			    String result=SendMail.send("COAT – Seat Auto Change Request",cc,to,null,null,content,null,null,null);
			    JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
			    
				//保存用户操作记录
			    SeatChangeOperation operation = new SeatChangeOperation();
				operation.setRefno(seatAutochangeApply.getRefno());
				operation.setOperationstatus(seatAutochangeApply.getStatus());
				operation.setOperationname(name);
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    operation.setOperationdate(sdf.format(new Date()));
			    String sql4="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			    ps= con.prepareStatement(sql4);
	            ps.setString(1, operation.getRefno());
			    ps.setString(2, operation.getOperationstatus());
			    ps.setString(3, operation.getOperationname());
			    ps.setString(4, operation.getOperationdate());
			    int flag4 = ps.executeUpdate();
			    if(flag4<1){
			    	throw new RuntimeException("保存自动换位申请confirmed operation失败.");
				}
		      
			}
	      	}else{
	      		//晋升至AD

				String sql1="update seat_autochange_apply set status=? , updatedate=? , remarkB=? , seatno=?  where  refno=?";
				ps=con.prepareStatement(sql1);
				ps.setString(1, "Completed");
				ps.setString(2, DateUtils.getNowDateTime());
				ps.setString(3, seatAutochangeApply.getRemarkB());
				ps.setString(4, seatAutochangeApply.getSeatno());
				ps.setString(5, seatAutochangeApply.getRefno());
				int flag1 = ps.executeUpdate();
				if(flag1<0){
					throw new RuntimeException("更新seat_autochange_apply表completed状态失败.");
				}
				
				//seat_autochange_listfromvsm 表changeflag 从1-->2; 
				String sql4="update seat_autochange_listfromvsm set changeflag=2 where staffcode=? and changetype=? and sfyx='Y'";
				ps=con.prepareStatement(sql4);
				ps.setString(1, seatAutochangeApply.getStaffcode());
				ps.setInt(2, flag);
				int flag4 = ps.executeUpdate();
				if(flag4<0){
					throw new RuntimeException("更新seat_autochange_listfromvsm表changeflag 从1-->2失败.");
				}
				
				//seat_autochange_seatstatus 表 seatno状态 从1--> 0;
				String sql5="update seat_autochange_seatstatus set `status` =0 where refno=?";
				ps=con.prepareStatement(sql5);
				ps.setString(1, seatAutochangeApply.getRefno());
				int flag5 = ps.executeUpdate();
				if(flag5<0){
					throw new RuntimeException("更新seat_autochange_seatstatus表seatno状态 从1-->0失败.");
				}
				
				//换位前非空位
				if(!Util.objIsNULL(seatAutochangeApply.getSeatnobefore())){
					//非15F 同一层
					if((!"15F".equals(seatList.getFloor()))&&seatList.getFloor().equals(seatlistbefore.getFloor())){
						
						String sql6 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql6, "",
								"",
								"",
								seatList.getPigenBoxno(),
								seatAutochangeApply.getSeatnobefore());
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatAutochangeApply.getSeatnobefore(),name,sdf.format(new Date()),"系统换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
								seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
					}else{
						
						String sql6 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql6, "",
								"",
								"",
								seatlistbefore.getPigenBoxno(),
								seatAutochangeApply.getSeatnobefore());
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatAutochangeApply.getSeatnobefore(),name,sdf.format(new Date()),"系统换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
								seatList.getPigenBoxno(),
								seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
						
						//需要判断楼层是否不同发送邮件 20181012新增需求 
						if(!(seatList.getFloor().equals(seatlistbefore.getFloor()))){
							String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
							int flag88 = super.saveEntity(sql88, 
									seatAutochangeApply.getStaffcode(),
									seatAutochangeApply.getStaffname(),
									seatlistbefore.getSeatno(),
									seatlistbefore.getLocation(),
									seatlistbefore.getFloor(),
									seatList.getSeatno(),
									seatList.getLocation(),
									seatList.getFloor(),
									DateUtils.getNowDateTime(),
									1,
									"自动换位"
									);
							if(flag88 < 1){
								throw new RuntimeException("保存成功换座记录失败.");
							}					
							
							
							if(!Util.objIsNULL(seatlistbefore.getStaffcode())){
								String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
								int flag99 = super.saveEntity(sql99, 
										seatAutochangeApply.getStaffcode(),
										seatAutochangeApply.getStaffname(),
										seatList.getSeatno(),
										seatList.getLocation(),
										seatList.getFloor(),
										seatlistbefore.getSeatno(),
										seatlistbefore.getLocation(),
										seatlistbefore.getFloor(),
										DateUtils.getNowDateTime(),
										1,
										"自动换位"
										);
								if(flag99 < 1){
									throw new RuntimeException("保存成功换座记录失败.");
								}
							}
						}
						
					}						
					
				}
				
				//换位前是空位
				if(Util.objIsNULL(seatAutochangeApply.getSeatnobefore())){
					
					if(!"15F".equals(seatList.getFloor())){
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
										Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
												seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
					}else{
						
						String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
								staffname,
								Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
										seatList.getPigenBoxno(),
										seatAutochangeApply.getSeatno());
						if(flag7 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
					}					
				}
									
				
				String newrefno=findref();
				synchronized (this) {
					if(Util.objIsNULL(newrefno))
						throw new Exception("流水号产生异常");
				}
				C_Seatassignment cs= new C_Seatassignment(newrefno,seatAutochangeApply.getStaffcode(),staffname,seatList.getLocation(),seatList.getExtensionno(),seatList.getFloor(),
						seatAutochangeApply.getSeatno(),seatList.getLockerno(),seatList.getDeskDrawerno(),seatList.getPigenBoxno(),"系统自动换位："+seatAutochangeApply.getRefno(),"SYSTEM",
							DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y);				
				String	sql12="insert c_seatassignment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
				ps=con.prepareStatement(sql12);
				ps.setString(1, cs.getRefno());
				ps.setString(2, cs.getStaffcode() ); 
				ps.setString(3, cs.getStaffname()); 
				ps.setString(4, cs.getLocation());
				ps.setString(5, cs.getExtensionno());
				ps.setString(6, cs.getFloor());
				ps.setString(7, cs.getSeatno());
				ps.setString(8, cs.getLockerno());
				ps.setString(9, cs.getDeskDrawerno());
				ps.setString(10, cs.getPigenBoxno());
				ps.setString(11, cs.getRemark());
				ps.setString(12, cs.getCreator());
				ps.setString(13, cs.getCreatDate());
				ps.setString(14, cs.getStatus());
				ps.setString(15, cs.getSfyx());
				logger.info(cs.getRefno()+"在C_Seatassignment save成功！");
				int flag12 = ps.executeUpdate();
				if(flag12<1){
					throw new RuntimeException("保存C_Seatassignment操作失败.");
				}
				
				//发送邮件提醒
	/*			content+="Dear "+staffname+",<br/>";//?
				content+="&nbsp;&nbsp;&nbsp;&nbsp;"+name+" Agree your application, please click to view results.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>"+url+"</a><br/>";
				content+="<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
				content+="<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				
				String result=SendMail.send("COAT – Seat Auto Change Request",to,cc,null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}*/

			
				//保存流程审批操作记录
				SeatChangeOperation changeoperation = new SeatChangeOperation();
				changeoperation.setRefno(seatAutochangeApply.getRefno());
				changeoperation.setOperationstatus(seatAutochangeApply.getStatus());
				changeoperation.setOperationname(name);
				SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				changeoperation.setOperationdate(sdfs.format(new Date()));
				String sql11="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
				ps= con.prepareStatement(sql11);
				ps.setString(1, changeoperation.getRefno());
				ps.setString(2, changeoperation.getOperationstatus());
				ps.setString(3, changeoperation.getOperationname());
				ps.setString(4, changeoperation.getOperationdate());
				int flag11 = ps.executeUpdate();
				if(flag11<1){
					throw new RuntimeException("保存自动换位申请Completed operation失败.");
				}
	      	}
	      	
			  con.commit();
			  logger.info("confirm seat_autochange_apply success");
			  num = 1; 
			  
			}catch (Exception e) {
				 num = -1;
				 logger.error("confirm seat_autochange_apply failed"+e.toString());
				 try {
					 con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				 e.printStackTrace();
			}finally{
	             DBManager.closeCon(con);   
			}
		return num;
	}	
	public int updateApplyForADM(SeatAutochangeApply seatAutochangeApply,SeatList seatList,SeatList seatlistbefore,String userType,int flag, String name, String staffname, String leadername,String seatnobefore,String to, String cc) throws SQLException {
		int num = -1;
		try{	
			super.openTransaction();
			String sql1 = "update seat_autochange_apply set status=? , updatedate=? , remarkC=?  where  refno=?";			
			int flag1 = super.update2(sql1, seatAutochangeApply.getStatus(),DateUtils.getNowDateTime(),seatAutochangeApply.getRemarkC(),seatAutochangeApply.getRefno());
			if(flag1<0){
				throw new RuntimeException("更新seat_autochange_apply表completed状态或者VOID状态失败.");
			}			
		
			//判断UAT环境还是生成环境
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			String content = "";
			//leader  Refused
			if(!Util.objIsNULL(seatAutochangeApply.getStatus()) && seatAutochangeApply.getStatus().equals("VOID")){
				//seat_autochange_listfromvsm 表changeflag 从1-->0; 
				String sql2="update seat_autochange_listfromvsm set changeflag=0 where staffcode=? and changetype=? and sfyx='Y'";
				int flag2 = super.update2(sql2, seatAutochangeApply.getStaffcode(),flag);
				if(flag2<0){
					throw new RuntimeException("更新seat_autochange_listfromvsm表changeflag 从1-->0失败.");
				}				
			
				//seat_autochange_seatstatus 表 seatno状态 从1--> 0;
				String sql3="update seat_autochange_seatstatus set `status` =0 where refno=?";
				int flag3 = super.update2(sql3, seatAutochangeApply.getRefno());
				if(flag3<0){
					throw new RuntimeException("更新seat_autochange_seatstatus表seatno状态 从1-->0失败.");
				}				

				
				//发送邮件提醒
				content+="Dear "+leadername+",<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Your application is rejected.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please wait for the next round seat assignment from COAT system.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank You<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Assignment Results:<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
				String result=SendMail.send("COAT – Seat Auto Change Request",to,cc,null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}  
			} else if (!Util.objIsNULL(seatAutochangeApply.getStatus()) && seatAutochangeApply.getStatus().equals("Completed")){
				//seat_autochange_listfromvsm 表changeflag 从1-->2; 
				String sql4="update seat_autochange_listfromvsm set changeflag=2 where staffcode=? and changetype=? and sfyx='Y'";
				int flag4 = super.update2(sql4, seatAutochangeApply.getStaffcode(),flag);
				if(flag4<0){
					throw new RuntimeException("更新seat_autochange_listfromvsm表changeflag 从1-->2失败.");
				}				
				
				//seat_autochange_seatstatus 表 seatno状态 从1--> 0;
				String sql5="update seat_autochange_seatstatus set `status` =0 where refno=?";
				int flag5 = super.update2(sql5, seatAutochangeApply.getRefno());
				if(flag5<0){
					throw new RuntimeException("更新seat_autochange_seatstatus表seatno状态 从1-->0失败.");
				}				

				if(!Util.objIsNULL(seatnobefore)){
					if((!"15F".equals(seatList.getFloor()))&&seatList.getFloor().equals(seatlistbefore.getFloor())){
						
						String sql6 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql6, "",
								"",
								"",
								seatList.getPigenBoxno(),
								seatnobefore);
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatnobefore,name,sdf.format(new Date()),"系统换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
					}else{
						
						String sql6 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql6, "",
								"",
								"",
								seatlistbefore.getPigenBoxno(),
								seatnobefore);
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatnobefore,name,sdf.format(new Date()),"系统换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}
						
						
						//需要判断楼层是否不同发送邮件 20181012新增需求 
						if(!(seatList.getFloor().equals(seatlistbefore.getFloor()))){
							String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
							int flag88 = super.saveEntity(sql88, 
									seatAutochangeApply.getStaffcode(),
									seatAutochangeApply.getStaffname(),
									seatlistbefore.getSeatno(),
									seatlistbefore.getLocation(),
									seatlistbefore.getFloor(),
									seatList.getSeatno(),
									seatList.getLocation(),
									seatList.getFloor(),
									DateUtils.getNowDateTime(),
									1,
									"自动换位"
									);
							if(flag88 < 1){
								throw new RuntimeException("保存成功换座记录失败.");
							}					
							
							
							if(!Util.objIsNULL(seatlistbefore.getStaffcode())){
								String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
								int flag99 = super.saveEntity(sql99, 
										seatAutochangeApply.getStaffcode(),
										seatAutochangeApply.getStaffname(),
										seatList.getSeatno(),
										seatList.getLocation(),
										seatList.getFloor(),
										seatlistbefore.getSeatno(),
										seatlistbefore.getLocation(),
										seatlistbefore.getFloor(),
										DateUtils.getNowDateTime(),
										1,
										"自动换位"
										);
								if(flag99 < 1){
									throw new RuntimeException("保存成功换座记录失败.");
								}
							}
						}
						
					}						
					
				}
				
				if((!"15F".equals(seatList.getFloor()))&&seatList.getFloor().equals(seatlistbefore.getFloor())){
					
					String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
							seatAutochangeApply.getStaffname(),
							Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
							Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getPigenBoxno(),
							seatAutochangeApply.getSeatno());
					if(flag7 < 0){
						throw new RuntimeException("更新座位失败！");
					}
					
					SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
					int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
					if(flag10 < 1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}
					
				}else{

					String sql7 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					int flag7 = super.update2(sql7, seatAutochangeApply.getStaffcode(),
							seatAutochangeApply.getStaffname(),
							Util.objIsNULL(seatlistbefore)?"":seatlistbefore.getExtensionno(),
							seatList.getPigenBoxno(),
							seatAutochangeApply.getSeatno());
					if(flag7 < 0){
						throw new RuntimeException("更新座位失败！");
					}
					
					SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sql10 = "insert into seat_operation (seatno,operationname,operationdate,reason)values(?,?,?,?)";
					int flag10 = super.saveEntity(sql10,seatAutochangeApply.getSeatno(),name,sdft.format(new Date()),"系统换位");
					if(flag10 < 1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}	
					
				}			        
				
				//订单号生成
				String newrefno=findref();
				synchronized (this) {
					if(Util.objIsNULL(newrefno))
						throw new Exception("流水号产生异常");
				}
				
				C_Seatassignment cs= new C_Seatassignment(newrefno,seatAutochangeApply.getStaffcode(),staffname,seatList.getLocation(),seatList.getExtensionno(),seatList.getFloor(),
						seatAutochangeApply.getSeatno(),seatList.getLockerno(),seatList.getDeskDrawerno(),seatList.getPigenBoxno(),"系统自动换位："+seatAutochangeApply.getRefno(),"SYSTEM",
							DateUtils.getNowDateTime(),Constant.C_Submitted,Constant.YXBZ_Y);				
				String	sql12="insert c_seatassignment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
				int flag12 = super.update2(sql12, cs.getRefno(),cs.getStaffcode(),cs.getStaffname(),cs.getLocation(),cs.getExtensionno(),cs.getFloor(),cs.getSeatno(),cs.getLockerno(),cs.getDeskDrawerno(),cs.getPigenBoxno(),cs.getRemark(),cs.getCreator(),cs.getCreatDate(),cs.getStatus(),cs.getSfyx());
				if(flag12<1){
					throw new RuntimeException("保存C_Seatassignment操作失败.");
				}	
				//发送邮件提醒
				content+="Dear "+leadername+",<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Your application is approved.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank You<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Assignment Results:<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	
				
				String result=SendMail.send("COAT – Seat Auto Change Request",to,cc+";adminfo@convoy.com.hk",null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
			} else {
				logger.info("自动换位申请发起时邮件通知结果: 未发送邮件通知");
			}
			
			//保存流程审批操作记录

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql11="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			int flag11 = super.update2(sql11, seatAutochangeApply.getRefno(),seatAutochangeApply.getStatus(),name,sdf.format(new Date()));
			if(flag11<1){
				throw new RuntimeException("保存自动换位申请Completed或者VOID operation失败.");
			}
						
			super.sumbitTransaction();
			logger.info("Completed seat_autochange_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("Completed seat_autochange_apply failed "+e.toString());
			super.rollbackTransaction();
			e.printStackTrace();
		}finally{
			super.closeConnection();  
		}
		return num;
	}	
	
	public String findref(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String num=null;
		try{
			con=DBManager.getCon();
			String sql="select count(*) from c_seatassignment";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getString(1);
				if(rs.getInt(1)<9){
					num="000"+(rs.getInt(1)+1);
				}else if(rs.getInt(1)<99){
					num="00"+(rs.getInt(1)+1);
				}else if(rs.getInt(1)<999){
					num="0"+(rs.getInt(1)+1);
				}else{
					num=""+(rs.getInt(1)+1);
				}
				num="SA"+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}	
	
	public String findref(int i){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String num=null;
		try{
			con=DBManager.getCon();
			String sql="select count(*) from c_seatassignment";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getString(1);
				if(rs.getInt(1)<9){
					num="000"+(rs.getInt(1)+1);
				}else if(rs.getInt(1)<99){
					num="00"+(rs.getInt(1)+1);
				}else if(rs.getInt(1)<999){
					num="0"+(rs.getInt(1)+1);
				}else{
					num=""+(rs.getInt(1)+1);
				}
				num="SA"+DateUtils.Ordercode()+(Integer.parseInt(num)+i);
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}	
	
	/**
	 * 根据 staffcode 判断是否是PA
	 * @param 
	 * @return
	 */
	public boolean IsPA(String staffcode) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = false;
		try {
			sql = "select staffcode from seat_autochange_listfromvsm where changetype = '2' and staffcode = ? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取staffcode是否是PA出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return flag;
	}	

	public SeatList getSeatListBySeatNo(String seatno) throws SQLException {
		SeatList seatList = null;
		try {
			seatList = super.find("select seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno, pigenBoxno,`status`,updater,updateDate,ifhidden,remark,remark1 from seat_list where seatno=? and `status` = 'Y'", seatno);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据座位编号查询顾问座位相关信息失败："+e.toString());
		} finally {
			super.closeConnection();
		}
		return seatList;
	}		
	
}
