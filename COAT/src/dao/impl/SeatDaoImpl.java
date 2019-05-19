package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import dao.SeatDao;
import dao.common.BaseDao;
import entity.ConsList;
import entity.SeatChangeApply;
import entity.SeatChangeOperation;
import entity.SeatList;
import util.Constant;
import util.DBManager;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.Pager;
import util.SendMail;
import util.Util;

public class SeatDaoImpl extends BaseDao implements SeatDao {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(SeatDaoImpl.class);
	
	public Pager findSeatList(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_list " +
				" where `status` = 'Y' and staffcode like ? " +
				" and staffname like ? " +
				" and location like ?" +
				" and floor like ?" +
				" and ifhidden like ?" +
				" and remark like ?" +
				" and seatno like ? ";

		String limit="order by seatno desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	public Pager findOperationList(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_change_operation " +
				" where  date_format(operationdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(operationdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and operationname like ? " +
				" and operationstatus like ? " +
				" and refno like ?";
		
		String limit="order by operationdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	public Pager findSeatMenuOperationList(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_operation " +
				" where  date_format(operationdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(operationdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and seatno like ? " +
				" and operationname like ? ";
		
		String limit="order by operationdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, SeatList.class);
	}

	
	public Pager findSeatChangeApplyList(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_change_apply " +
				" where sfyx = 'Y' " +
				" and  date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(createdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and refno like ? " +
				" and staffcodea like ? " +
				" and seatnoa like ?" +
				" and staffcodeb like ?" +
				" and seatnob like ? " +
				" and status like ? ";

		String limit="order by createdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	
	
	}
	
	public Pager findCleanOperationList(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_operation " +
				" where  date_format(operationdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(operationdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and staffcode like ? and seatno like ? ";
		
		String limit="order by operationdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
		
		
	}
	
	public int saveSeatNo(SeatList seatlist,String name,String to, String cc) throws SQLException{
		
		int num = -1;
		String sql1 = "";
		String sql2 = "";
			try {
				super.openTransaction();
				sql1 = "insert into seat_list(seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenBoxno,`status`,updater,updateDate,ifhidden,remark,remark1) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				num = super.saveEntity(sql1,seatlist.getSeatno(),
										   seatlist.getStaffcode(),
										   seatlist.getStaffname(),
										   seatlist.getExtensionno(),
										   seatlist.getLocation(),
										   seatlist.getFloor(),
										   seatlist.getDeskDrawerno(),
										   seatlist.getLockerno(),
										   seatlist.getPigenBoxno(),
										   seatlist.getStatus(),
										   seatlist.getUpdater(),
										   seatlist.getUpdateDate(),
										   seatlist.getIfhidden(),
										   seatlist.getRemark(),
										   seatlist.getRemark1());
				if(num < 1){
					throw new RuntimeException("新增座位号失败！");
				}
				sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				num = super.saveEntity(sql2,seatlist.getSeatno(),seatlist.getStaffcode(),seatlist.getStaffname(),seatlist.getExtensionno(),seatlist.getFloor(),seatlist.getLocation(),seatlist.getPigenBoxno(),seatlist.getDeskDrawerno(),seatlist.getLockerno(),seatlist.getIfhidden(),seatlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM新增座位信息");
				if(num < 1){
					throw new RuntimeException("新增座位号操作记录失败！");			
				}
				
			      String url = "";
			      if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
			    	  url = Util.getProValue("public.system.uatlink");
			      }else{
			    	  url = Util.getProValue("public.system.link");
			      }				

				//如果staffcode不为空，则发送通知邮件给该staffcode
				if(!Util.objIsNULL(seatlist.getStaffcode())){
					String content = "";				
					content+="Dear "+seatlist.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
		      		+"<tr><td>"+seatlist.getStaffcode()+"</td><td>"+seatlist.getStaffname()+"</td><td>"+seatlist.getSeatno()+"</td><td>"+seatlist.getPigenBoxno()+"</td><td>"+seatlist.getExtensionno()+"</td><td>"+seatlist.getFloor()+" "+seatlist.getLocation()+"</td><td>New</td></tr>"	      		
		      		+"<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>Previous</td></tr></table><br/><br/>";	      		
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					
					String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}					
				}
				
				super.sumbitTransaction();
			} catch (Exception e) {
				num = -1;
				super.rollbackTransaction();
			} finally{
				super.closeConnection();
			}
		
		
		return num;
	}
	public int updateSeatNo(SeatList seatlist,String name,String to,String cc,SeatList seatoldlist) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		try {
			super.openTransaction();
			sql1 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,location = ?,floor = ?,deskDrawerno = ?,lockerno = ?,pigenBoxno = ?,updater = ?,updateDate = ?,ifhidden = ?,remark = ?,remark1 = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql1, seatlist.getStaffcode(),
									 seatlist.getStaffname(),
									 seatlist.getExtensionno(),
									 seatlist.getLocation(),
									 seatlist.getFloor(),
									 seatlist.getDeskDrawerno(),
									 seatlist.getLockerno(),
									 seatlist.getPigenBoxno(),
									 seatlist.getUpdater(),
									 seatlist.getUpdateDate(),
									 seatlist.getIfhidden(),
									 seatlist.getRemark(),
									 seatlist.getRemark1(),
									 seatlist.getSeatno());
			if(num < 0){
				throw new RuntimeException("更新座位信息失败！");
			}
			sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql2,seatlist.getSeatno(),seatlist.getStaffcode(),seatlist.getStaffname(),seatlist.getExtensionno(),seatlist.getFloor(),seatlist.getLocation(),seatlist.getPigenBoxno(),seatlist.getDeskDrawerno(),seatlist.getLockerno(),seatlist.getIfhidden(),seatlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM修改座位信息");
			if(num < 1){
				throw new RuntimeException("新增座位号操作记录失败！");			
			}
			
		      String url = "";
		      if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
		    	  url = Util.getProValue("public.system.uatlink");
		      }else{
		    	  url = Util.getProValue("public.system.link");
		      }				
		      String content = "";	
			//如果staffcode不为空，则发送通知邮件给该staffcode
			if(!Util.objIsNULL(seatlist.getStaffcode())){
				
			      //修改的staffcode原来已有座位
			    if(Util.objIsNULL(seatoldlist)){
					content+="Dear "+seatlist.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
		      		+"<tr><td>"+seatlist.getStaffcode()+"</td><td>"+seatlist.getStaffname()+"</td><td>"+seatlist.getSeatno()+"</td><td>"+seatlist.getPigenBoxno()+"</td><td>"+seatlist.getExtensionno()+"</td><td>"+seatlist.getFloor()+" "+seatlist.getLocation()+"</td><td>New</td></tr>"	      		
		      		+"<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>Previous</td></tr></table><br/><br/>";	      		
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					
					String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}	
				
			    }else if(!(seatoldlist.getSeatno().equals(seatlist.getSeatno()))){
			    	
					content+="Dear "+seatlist.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
		      		+"<tr><td>"+seatlist.getStaffcode()+"</td><td>"+seatlist.getStaffname()+"</td><td>"+seatlist.getSeatno()+"</td><td>"+seatlist.getPigenBoxno()+"</td><td>"+seatlist.getExtensionno()+"</td><td>"+seatlist.getFloor()+" "+seatlist.getLocation()+"</td><td>New</td></tr>"	      		
		      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					
					String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}	
			    	
			    }else{
			    	
			    	
			    }
				
			}			
			
			super.sumbitTransaction();	
		} catch (Exception e) {
			num = -1;
			super.rollbackTransaction();
		} finally{
			super.closeConnection();
		}
		return num;
	}
	public Pager findSeatChangeApplyListByStaffcode(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_change_apply " +
				" where  sfyx = 'Y' " +
				" and  date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(createdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and refno like ? " +
				" and( staffcodea like ? or staffcodeb like ?)"; 
		
		String limit="order by createdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
		
		
	}
	public Pager findSeatChangeApplyListByLeadercode(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM seat_change_apply " +
				" where  sfyx = 'Y' " +
				" and  date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(createdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and refno like ? " +
				" and (staffcodea in (select EmployeeId from cons_list where RecruiterId like ? ) OR staffcodeb in (select EmployeeId from cons_list where RecruiterId like ? ))"; 
		
		String limit="order by createdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
		
		
	}

	public SeatList querystaffcodeA(String staffcodeA) {
		SeatList seatList = null;
		try {
			sql = "select staffcode,seatno,pigenBoxno from seat_list where staffcode=? and `status` = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcodeA);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatList = new SeatList();
				seatList.setStaffcode(rs.getString("staffcode"));
				seatList.setSeatno(rs.getString("seatno"));
				seatList.setPigenBoxno(rs.getString("pigenBoxno"));
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

	public SeatList querySeatnoB(String seatnoB) {
		SeatList seatList = null;
		try {
			sql = "select staffcode,staffname,seatno,extensionno,pigenBoxno,floor from seat_list where seatno=? and `status` = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatnoB);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatList =new SeatList();
				seatList.setStaffcode(rs.getString("staffcode"));
				seatList.setStaffname(rs.getString("staffname"));
				seatList.setSeatno(rs.getString("seatno"));
				seatList.setExtensionno(rs.getString("extensionno"));
				seatList.setPigenBoxno(rs.getString("pigenBoxno"));
				seatList.setFloor(rs.getString("floor"));
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
	
/*	public int getSpecialSeat(String seatno){
		int TotalNum = 0;
		try {
			sql = "select count(*) as totalnum from seat_speciallist where `status` = 'Y' and seatno=?  ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, seatno);
			rs = ps.executeQuery();
			while (rs.next()) {
				TotalNum = rs.getInt("totalnum");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return TotalNum;
	}*/
	public int getTotalSeatNumMessage(){
		int seatTotalNum = 0;
		try {
			sql = "select count(*) as totalseatnum from seat_list where `status` = 'Y' and remark != 'DD' ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatTotalNum = rs.getInt("totalseatnum");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatTotalNum;
	}
	public int getTotalPTSeatNumMessage(){
		int seatptTotalNum = 0;
		try {
			sql = "select count(*) as totalptseatnum from seat_list WHERE remark != 'AD' and remark != 'DD' and  `status` = 'Y' ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatptTotalNum = rs.getInt("totalptseatnum");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatptTotalNum;
	}
	
	public void SynSeatPlan(){
		try {
			SeatForDDTHDaoImpl s = new SeatForDDTHDaoImpl();
			s.timeTaskSeatForDDTH();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String batchClean(){
		String result = "";
		try {
			result = timeTaskBatchClean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getTotalUsePTSeatNumMessage(){
		int seatUseptTotalNum = 0;
		try {
			sql = "select count(*) as totaluseptseatnum from seat_list WHERE `status` = 'Y' and remark != 'DD' and staffcode != '' ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatUseptTotalNum = rs.getInt("totaluseptseatnum");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatUseptTotalNum;
	}
	public int getTotalUseADSeatNumMessage(){
		int seatUseadTotalNum = 0;
		try {
			sql = "select count(*) as totaluseadseatnum from seat_list WHERE `status` = 'Y' and remark = 'AD' and staffcode != '' ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatUseadTotalNum = rs.getInt("totaluseadseatnum");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatUseadTotalNum;
	}
	
	
	public ConsList queryLeaderMsg(String staffcode) {
		ConsList consList = null;
		try {
			sql = "select RecruiterId,RecruiterName from cons_list where EmployeeId like ?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				consList = new ConsList();
				consList.setRecruiterId(rs.getString("RecruiterId"));
				consList.setRecruiterName(rs.getString("RecruiterName"));
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


	public SeatList queryStaffcodeB(String staffcodeB) {
		SeatList seatList = null;
		try {
			sql = "select staffcode,staffname,seatno,extensionno,pigenBoxno,floor from seat_list where staffcode=? and `status` = 'Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcodeB);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatList = new SeatList();
				seatList.setStaffcode(rs.getString("staffcode"));
				seatList.setStaffname(rs.getString("staffname"));
				seatList.setSeatno(rs.getString("seatno"));
				seatList.setExtensionno(rs.getString("extensionno"));
				seatList.setPigenBoxno(rs.getString("pigenBoxno"));
				seatList.setFloor(rs.getString("floor"));
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

	public String getNo() {
		String num=null;
		try{
			con=DBManager.getCon();
			sql="select count(*) from seat_change_apply";
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
				num=Constant.SEATCHANGE+DateUtils.Ordercode()+num;
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

	public int del(String name,String refno){
		int num = -1;
		try{
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set sfyx='N', status='Deleted'  where sfyx='Y' and refno=? ";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
		    int flag1 = ps.executeUpdate();
	        if(flag1<0){
			  throw new RuntimeException();
		    }
	        
			String sql2="insert into seat_change_operation (refno,operationstatus,operationname,operationdate) values (?,?,?,?)";
		      ps= con.prepareStatement(sql2);
              ps.setString(1, refno);
		      ps.setString(2, "Deleted");
		      ps.setString(3, name);
		      ps.setString(4, DateUtils.getNowDateTime());
		      int flag2 = ps.executeUpdate();
		      if(flag2<1){
				 throw new RuntimeException();
			  }	
			con.commit();
			logger.info("delete seat_change_apply success");
			num = 1;
		}catch(Exception e){
			num = -1;
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("Delete seat_change_apply信息保存异常！"+e.getMessage());
		}finally{
            DBManager.closeCon(con); 
		}
		return num;
	}
	public int deleteSeatNo(String seatno,String name) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		SeatList seatlist = null;
		try{
			super.openTransaction();
			sql3="select seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno, pigenBoxno,`status`,updater,updateDate,ifhidden,remark,remark1 from seat_list where seatno=? and `status` = 'Y'";
			seatlist = super.find(sql3, seatno);
			
			sql1 = "update seat_list set `status` = 'N' where seatno = ? and `status` = 'Y'  ";
			int flag1 =  super.update2(sql1, seatno);
			if(flag1<0){
				throw new RuntimeException("删除座位号失败 !");
			}
			
			/*增加座位表操作记录*/
			sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql2,seatno,
					seatlist.getStaffcode(),seatlist.getStaffname(),seatlist.getExtensionno(),seatlist.getFloor(),seatlist.getLocation(),seatlist.getPigenBoxno(),seatlist.getDeskDrawerno(),seatlist.getLockerno(),seatlist.getIfhidden(),seatlist.getRemark(),name,DateUtils.getNowDateTime(),"删除座位号");
			if(num < 1){
				throw new RuntimeException("删除座位号操作记录失败！");
			}	
			super.sumbitTransaction();
			logger.info("delete seat_list success");
		}catch(Exception e){
			num = -1;
			super.rollbackTransaction();
			
		}finally{
			super.closeConnection(); 
		}
		return num;
	}
	
	public int cleanSeatMenu(SeatList seatList,String reason,String name) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		try{
			super.openTransaction();
			sql1="update seat_list set staffcode=?, staffname=?, extensionno=? where seatno=? and `status` = 'Y' ";
			num = super.update2(sql1, "","","",seatList.getSeatno());
			if(num<0){
				throw new RuntimeException("清空座位表失败!");
			}
			
			String reasonMsg = "";
			if("leave".equals(reason)){
				reasonMsg = "离职";
				//离职人员信息存入数据表
				sql3="insert into seat_leave (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,creater,createdate)values(?,?,?,?,?,?,?,?,?)";	
				num = super.saveEntity(sql3, seatList.getSeatno(),seatList.getStaffcode(),seatList.getStaffname(),seatList.getExtensionno(),seatList.getFloor(),seatList.getLocation(),seatList.getPigenBoxno(),name,DateUtils.getNowDateTime());
				if(num<1){
					throw new RuntimeException("保存离职人员记录信息失败.");
				}			
			}else if("change".equals(reason)){
				reasonMsg = "座位调换";
			}else if("relocate".equals(reason)){
				reasonMsg = "搬迁";
			}else if("standup".equals(reason)){
				reasonMsg = "stand up";
			}else{
				reasonMsg = "其他";
			}
			
			//保存座位表操作记录
//			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); sd.format(new Date())
			sql2="insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql2, seatList.getSeatno(),seatList.getStaffcode(),seatList.getStaffname(),seatList.getExtensionno(),seatList.getFloor(),seatList.getLocation(),seatList.getPigenBoxno(),seatList.getDeskDrawerno(),seatList.getLockerno(),seatList.getIfhidden(),seatList.getRemark(),name,DateUtils.getNowDateTime(),reasonMsg);
			if(num<1){
				throw new RuntimeException("保存座位表操作记录失败.");
			}
			super.sumbitTransaction();
			logger.info("clean seat_list success");
		}catch(Exception e){
			num = -1;
			super.rollbackTransaction();
		}finally{
			super.closeConnection(); 
		}
		return num;
	}
	public int ifExist(String seatnoA,String seatnoB){
		int num = 1;
		try{
			con = DBManager.getCon();
			String sql="select count(*) as totalnum from seat_change_apply where sfyx='Y' and `status`!='Completed' and `status`!='Refused' and `status` != 'Deleted' and `status`!='VOID' and (seatnoa = ? or seatnoa = ? or seatnob = ? or seatnob = ?) " ;
			ps=con.prepareStatement(sql);
			ps.setString(1, seatnoA);
			ps.setString(2, seatnoB);
			ps.setString(3, seatnoA);
			ps.setString(4, seatnoB);
			rs = ps.executeQuery();
			while (rs.next()){
				num = rs.getInt("totalnum");
			}
			rs.close();
			logger.info("select seat_change_apply success");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Delete seat_change_apply信息保存异常！"+e.getMessage());
		}finally{
			DBManager.closeCon(con); 
		}
		return num;
	}
	public int ifMeExist(String staffcodeA){
		int num = 1;
		try{
			con = DBManager.getCon();
			String sql="select count(*) as totalnum from seat_change_apply where sfyx='Y' and `status`!='Completed' and `status` !='Deleted' and `status`!='Refused' and `status`!='VOID' and (staffcodea = ? or staffcodeb = ?)" ;
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcodeA);
			ps.setString(2, staffcodeA);
			rs = ps.executeQuery();
			while (rs.next()){
				num = rs.getInt("totalnum");
			}
			rs.close();
			logger.info("select seat_change_apply success");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Delete seat_change_apply信息保存异常！"+e.getMessage());
		}finally{
			DBManager.closeCon(con); 
		}
		return num;
	}
	public int isHidden(String seatnoA){
		int num = 1;
		try{
			con = DBManager.getCon();
			String sql="select count(*) as totalnum from seat_list where ifhidden = 'Y' and `status` = 'Y' and seatno = ? " ;
			ps=con.prepareStatement(sql);
			ps.setString(1, seatnoA);
			rs = ps.executeQuery();
			while (rs.next()){
				num = rs.getInt("totalnum");
			}
			rs.close();
			logger.info("select seat_list success");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("select seat_list信息保存异常！"+e.getMessage());
		}finally{
			DBManager.closeCon(con); 
		}
		return num;
	}
	
	public int saveApply(SeatChangeApply seatChangeApply,String name,String staffname,String leadername,String to,String cc) {
		int num = -1;
		
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="insert into seat_change_apply (refno,staffcodea,seatnoa,staffcodeb,seatnob,seatnoc,staffcodec,seatnod,staffcoded,createdate,status,remark,remarkall,remark3,remark1,remark2,sfyx,extensiona,extensionb,extensionc,extensiond,pigeonboxa,pigeonboxb,pigeonboxc,pigeonboxd) " +
					"  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			  ps=con.prepareStatement(sql);
			  ps.setString(1, seatChangeApply.getRefno());
			  ps.setString(2, seatChangeApply.getStaffcodea());
			  ps.setString(3, seatChangeApply.getSeatnoa());
			  ps.setString(4, seatChangeApply.getStaffcodeb());
			  ps.setString(5, seatChangeApply.getSeatnob());
			  ps.setString(6, seatChangeApply.getSeatnoc());
			  ps.setString(7, seatChangeApply.getStaffcodec());
			  ps.setString(8, seatChangeApply.getSeatnod());
			  ps.setString(9, seatChangeApply.getStaffcoded());
			  ps.setString(10, seatChangeApply.getCreatedate());
			  ps.setString(11, seatChangeApply.getStatus());
			  ps.setString(12, "");//remark
			  ps.setString(13, seatChangeApply.getRemarkall());//remarkall
			  ps.setString(14, "");//remark3
			  ps.setString(15, seatChangeApply.getRemark1());//floora
			  ps.setString(16, seatChangeApply.getRemark2());//floorb
			  ps.setString(17, seatChangeApply.getSfyx());
			  ps.setString(18, seatChangeApply.getExtensiona());
			  ps.setString(19, seatChangeApply.getExtensionb());
			  ps.setString(20, seatChangeApply.getExtensionc());
			  ps.setString(21, seatChangeApply.getExtensiond());
			  ps.setString(22, seatChangeApply.getPigeonboxa());
			  ps.setString(23, seatChangeApply.getPigeonboxb());
			  ps.setString(24, seatChangeApply.getPigeonboxc());
			  ps.setString(25, seatChangeApply.getPigeonboxd());
			  int flag1 = ps.executeUpdate();
		      if(flag1<0){
				 throw new RuntimeException();
			  }
			  //保存用户操作记录
			  SeatChangeOperation operation = new SeatChangeOperation();
			  operation.setRefno(seatChangeApply.getRefno());
			  operation.setOperationstatus(seatChangeApply.getStatus());
			  operation.setOperationname(name);
		      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		      operation.setOperationdate(sdf.format(new Date()));
		      String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate) values(?,?,?,?)";
		      ps= con.prepareStatement(SQL);
              ps.setString(1, operation.getRefno());
		      ps.setString(2, operation.getOperationstatus());
		      ps.setString(3, operation.getOperationname());
		      ps.setString(4, operation.getOperationdate());
		      int flag2 = ps.executeUpdate();
		      if(flag2<1){
				 throw new RuntimeException();
			  }	
		      
		      String url = "";
		      if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
		    	  url = Util.getProValue("public.system.uatlink");
		      }else{
		    	  url = Util.getProValue("public.system.link");
		      }
		      
		      if(!Util.objIsNULL(seatChangeApply.getStaffcodeb())){
		      //发送邮件通知
			    String content="Dear "+staffname+",<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat swap request for your teammate "+name+" is pending your confirmation.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> . (Step: Login in the COAT System, Choose COAT-Seat Exchange --> SeatChangeApply_Query)<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.  <br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			    
			    String result=SendMail.send("COAT – Seat Change Request",to,null,null,null,content,null,null,null);
			    JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
		      }else{
		    	  
				    String content="Dear "+leadername+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat swap request for your teammate "+name+" is pending your confirmation.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> . (Step: Login in the COAT System, Choose COAT-Seat Exchange --> SeatChangeApply_Leader)<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you. <br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				    
				    String result=SendMail.send("COAT – Seat Change Request",cc,null,null,null,content,null,null,null);
				    JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}  
		    	  
		    	  
		      }
			  con.commit();
			  logger.info("Create seat_change_apply success");
			  num = 1;
			  
			}catch (Exception e) {
				 num = -1;
				 logger.error("Create seat_change_apply failed "+e.toString());
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

	public SeatChangeApply queryListByRefno(String refno) {
		SeatChangeApply seatChangeApply = null;
		try {
			sql = "select * from seat_change_apply where refno=? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, refno);
			rs = ps.executeQuery();
			while (rs.next()) {
				seatChangeApply = new SeatChangeApply();
				seatChangeApply.setRefno(rs.getString("refno"));
				seatChangeApply.setStaffcodea(rs.getString("staffcodea"));
				seatChangeApply.setSeatnoa(rs.getString("seatnoa"));
				seatChangeApply.setPigeonboxa(rs.getString("pigeonboxa"));
				seatChangeApply.setStaffcodeb(rs.getString("staffcodeb"));
				seatChangeApply.setSeatnob(rs.getString("seatnob"));
				seatChangeApply.setPigeonboxb(rs.getString("pigeonboxb"));
				seatChangeApply.setSeatnoc(rs.getString("seatnoc"));
				seatChangeApply.setStaffcodec(rs.getString("staffcodec"));
				seatChangeApply.setPigeonboxc(rs.getString("pigeonboxc"));
				seatChangeApply.setSeatnod(rs.getString("seatnod"));
				seatChangeApply.setStaffcoded(rs.getString("staffcoded"));
				seatChangeApply.setPigeonboxd(rs.getString("pigeonboxd"));
				/*seatChangeApply.setCheckflag(rs.getInt("checkflag"));*/
				seatChangeApply.setCreatedate(rs.getString("createdate"));
				seatChangeApply.setStatus(rs.getString("status"));
				seatChangeApply.setExtensiona(rs.getString("extensiona"));
				seatChangeApply.setExtensionb(rs.getString("extensionb"));
				seatChangeApply.setExtensionc(rs.getString("extensionc"));
				seatChangeApply.setExtensiond(rs.getString("extensiond"));
				/*seatChangeApply.setExtensionflag(rs.getInt("extensionflag"));*/
				seatChangeApply.setRemark(rs.getString("remark"));
				String remarks = rs.getString("remarkall").replace("~.~", "\\r\\n");
				seatChangeApply.setRemarkall(remarks);
				seatChangeApply.setRemark1(rs.getString("remark1"));
				seatChangeApply.setRemark2(rs.getString("remark2"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据记录出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return seatChangeApply;
	}

	public int updateApplyB(SeatChangeApply seatChangeApply, String name, String staffnameA, String staffnameB,String to, String cc) {
		int num = -1;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
		    if(flag1<0){
				 throw new RuntimeException();
			}
			  //保存用户操作记录
			  SeatChangeOperation operation = new SeatChangeOperation();
			  operation.setRefno(seatChangeApply.getRefno());
			  operation.setOperationstatus(seatChangeApply.getStatus());
			  operation.setOperationname(name);
		      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		      operation.setOperationdate(sdf.format(new Date()));
		      String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
		      ps= con.prepareStatement(SQL);
              ps.setString(1, operation.getRefno());
		      ps.setString(2, operation.getOperationstatus());
		      ps.setString(3, operation.getOperationname());
		      ps.setString(4, operation.getOperationdate());
		      int flag2 = ps.executeUpdate();
		      if(flag2<1){
				 throw new RuntimeException();
			  }
		     //发送邮件提醒
	      	String url = "";
	      	if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
	      		url = Util.getProValue("public.system.uatlink");
	      	}else{
	      		url = Util.getProValue("public.system.link");
	      	}
	      	
	      	String content = "";
	      	if(!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("Refused")){
			    content+="Dear "+staffnameA+",<br/>";
				content+="<br/>";			    
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is rejected by "+staffnameB+". <br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
	      		String result=SendMail.send("COAT – Seat Change Request",to,null,null,null,content,null,null,null);
			    JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}      		
	      	} else if (!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("Confirmed")){
	      		content+="Dear "+staffnameA+",<br/>";
				content+="<br/>";
	      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by "+staffnameB+".<br/>";
	      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
	      		content+="<br/>";
	      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667. <br/>";
	      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you.<br/>";
	      		content+="<br/>";
	      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
	      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
	      		
	      		String result=SendMail.send("COAT – Seat Change Request",to,cc,null,null,content,null,null,null);
			    JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
	      	} else {
	      		logger.info("换位申请发起时邮件通知结果: 未发送邮件通知");
	      	}
			  con.commit();
			  logger.info("confirm seat_change_apply success");
			  num = 1; 
			  
			}catch (Exception e) {
				 num = -1;
				 logger.error("confirm seat_change_apply failed"+e.toString());
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
	
	public int requestLeaderConfirm(SeatChangeApply seatChangeApply, String name, SeatList seatListA,SeatList seatListB, String leadernamea,String leadernameb,String to, String cc,String to1) throws SQLException {
		int num = -1;
		try{	
/*			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException();
			}
			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatChangeApply.getRefno());
			operation.setOperationstatus(seatChangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(SQL);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag2 = ps.executeUpdate();
			if(flag2<1){
				throw new RuntimeException();
			}*/
			
			super.openConnection();
			super.openTransaction();
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			int flag1 = update2(sql,seatChangeApply.getRemarkall(),seatChangeApply.getStatus(),seatChangeApply.getRefno());
			if(flag1<0){
				throw new RuntimeException("更细座位交换表数据失败");
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			int flag2 = super.saveEntity(SQL,seatChangeApply.getRefno(),seatChangeApply.getStatus(),name,sdf.format(new Date()));
			if(flag2 < 1){
				throw new RuntimeException("保存座位座位交换表操作记录失败.");
			}
			
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
			String content = "";
			if(!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("RequestLeaderConfirm")){
				content+="Dear "+leadernameb+",<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat exchange request between your teammate "+seatListB.getStaffname()+" and "+seatListA.getStaffname()+" is pending for your confirmation. <br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a>  (Step: Login in the COAT System, Choose COAT-Seat Exchange-->SeatExchangeApply_Leader).<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;If we do not receive a response from you within 7days, we will assume you disagree and the <br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;request shall be deemed withdrawn.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667. <br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
				String result=SendMail.send("COAT – Seat Change Request",cc,to+";"+to1,null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}      		
			}else if (!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("Confirmed")){
				content+="Dear Administration Department,<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;A seat change request from   "+seatListA.getStaffname()+"  is pending your approval.  <br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for approval.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;COAT System<br/>";
				
				String result=SendMail.send("COAT – Seat Change Request",Util.getProValue("public.email.seatchange.notice.address"),null,null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}  
				
			}else if (!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("Completed")){
				//非15楼并且同一层
				if((!"15F".equals(seatListA.getFloor()))&&seatListA.getFloor().equals(seatListB.getFloor())){
					
					String sql3 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					int flag3 = super.update2(sql3, seatChangeApply.getStaffcodec(),
							seatListB.getStaffname(),
							seatListB.getExtensionno(),
							seatListB.getPigenBoxno(),
							seatChangeApply.getSeatnoc());
					if(flag3 < 0){
						throw new RuntimeException("更新座位失败！");
					}
					
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sql9 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					int flag9 = super.saveEntity(sql9,seatChangeApply.getSeatnoc(),seatListA.getStaffcode(),seatListA.getStaffname(),seatListA.getExtensionno(),seatListA.getFloor(),seatListA.getLocation(),seatListA.getPigenBoxno(),seatListA.getDeskDrawerno(),seatListA.getLockerno(),seatListA.getIfhidden(),seatListA.getRemark(),name,sd.format(new Date()),"主动换位");
					if(flag9 < 1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}

					String sql4 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					int flag4 = super.update2(sql4, seatChangeApply.getStaffcoded(),
							seatListA.getStaffname(),
							seatListA.getExtensionno(),
							seatListA.getPigenBoxno(),
							seatChangeApply.getSeatnod());
					if(flag4 < 0){
						throw new RuntimeException("更新座位失败！");
					}
					
					SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sql10 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					int flag10 = super.saveEntity(sql10,seatChangeApply.getSeatnod(),seatListB.getStaffcode(),seatListB.getStaffname(),seatListB.getExtensionno(),seatListB.getFloor(),seatListB.getLocation(),seatListB.getPigenBoxno(),seatListB.getDeskDrawerno(),seatListB.getLockerno(),seatListB.getIfhidden(),seatListB.getRemark(),name,sdft.format(new Date()),"主动换位");
					if(flag10 < 1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}
					//如果申请人是PA，则邮箱不显示chickenbox no
					if(IsPA(seatChangeApply.getStaffcodea())){
						content+="Dear "+seatListA.getStaffname()+",<br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by "+leadernamea+" .<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
			      		content+="<br/>";		      		
			      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
						+"        <table id='xx' cellpadding=0 cellspacing=0 >"
						+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
			      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+""+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
			      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+""+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	      		
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						
					}else{
						content+="Dear "+seatListA.getStaffname()+",<br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by "+leadernamea+" .<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
			      		content+="<br/>";		      		
			      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
						+"        <table id='xx' cellpadding=0 cellspacing=0 >"
						+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
			      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+seatListA.getPigenBoxno()+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
			      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+seatListB.getPigenBoxno()+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	      		
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					}
					
					String result=SendMail.send("COAT – Seat Change Request",to,"adminfo@convoy.com.hk",null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}
					
				}else{
					
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String sql3 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					int flag3 = super.update2(sql3, seatChangeApply.getStaffcodec(),
							seatListB.getStaffname(),
							seatListB.getExtensionno(),
							seatListA.getPigenBoxno(),
							seatChangeApply.getSeatnoc());
					if(flag3 < 0){
						throw new RuntimeException("更新座位失败！");
					}
					
					
					String sql9 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					int flag9 = super.saveEntity(sql9,seatChangeApply.getSeatnoc(),seatListA.getStaffcode(),seatListA.getStaffname(),seatListA.getExtensionno(),seatListA.getFloor(),seatListA.getLocation(),seatListA.getPigenBoxno(),seatListA.getDeskDrawerno(),seatListA.getLockerno(),seatListA.getIfhidden(),seatListA.getRemark(),name,sd.format(new Date()),"主动换位");
					if(flag9 < 1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}

					String sql4 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					int flag4 = super.update2(sql4, seatChangeApply.getStaffcoded(),
							seatListA.getStaffname(),
							seatListA.getExtensionno(),
							seatListB.getPigenBoxno(),
							seatChangeApply.getSeatnod());
					if(flag4 < 0){
						throw new RuntimeException("更新座位失败！");
					}
					
					SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sql10 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					int flag10 = super.saveEntity(sql10,seatChangeApply.getSeatnod(),seatListB.getStaffcode(),seatListB.getStaffname(),seatListB.getExtensionno(),seatListB.getFloor(),seatListB.getLocation(),seatListB.getPigenBoxno(),seatListB.getDeskDrawerno(),seatListB.getLockerno(),seatListB.getIfhidden(),seatListB.getRemark(),name,sdft.format(new Date()),"主动换位");
					if(flag10 < 1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}	
					
					// 需要判断楼层是否不同发送邮件 20181012新增需求
					if(!(seatListA.getFloor().equals(seatListB.getFloor()))){
						String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
						int flag88 = super.saveEntity(sql88, 
								seatListA.getStaffcode(),
								seatListA.getStaffname(),
								seatListA.getSeatno(),
								seatListA.getLocation(),
								seatListA.getFloor(),
								seatListB.getSeatno(),
								seatListB.getLocation(),
								seatListB.getFloor(),
								sd.format(new Date()),
								1,
								"主动换位"
								);
						if(flag88 < 1){
							throw new RuntimeException("保存成功换座记录失败.");
						}					
						if(!Util.objIsNULL(seatListB.getStaffcode())){
							String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
							int flag99 = super.saveEntity(sql99, 
									seatListB.getStaffcode(),
									seatListB.getStaffname(),
									seatListB.getSeatno(),
									seatListB.getLocation(),
									seatListB.getFloor(),
									seatListA.getSeatno(),
									seatListA.getLocation(),
									seatListA.getFloor(),
									sd.format(new Date()),
									1,
									"主动换位"
									);
							if(flag99 < 1){
								throw new RuntimeException("保存成功换座记录失败.");
							}						
						}
					}
					
					//如果申请人是PA，则邮箱不显示chickenbox no
					if(IsPA(seatChangeApply.getStaffcodea())){
						content+="Dear "+seatListA.getStaffname()+",<br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by "+leadernamea+" .<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
			      		content+="<br/>";		      		
			      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
						+"        <table id='xx' cellpadding=0 cellspacing=0 >"
						+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
			      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+""+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
			      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+""+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	      		
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						
					}else{
						content+="Dear "+seatListA.getStaffname()+",<br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by "+leadernamea+" .<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
			      		content+="<br/>";		      		
			      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
						+"        <table id='xx' cellpadding=0 cellspacing=0 >"
						+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
			      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+seatListB.getPigenBoxno()+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
			      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+seatListA.getPigenBoxno()+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	      		
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					}
					
					String result=SendMail.send("COAT – Seat Change Request",to,"adminfo@convoy.com.hk",null,null,content,null,null,null);
					JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}
					
					
					
					
					
					
				}
				
			} else {
				logger.info("换位申请发起时邮件通知结果: 未发送邮件通知");
			}
			super.sumbitTransaction();
			logger.info("requestLeaderConfirm seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("requestLeaderConfirm seat_change_apply failed"+e.toString());
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			super.closeConnection();  
		}
		return num;
	}
	
	public int responseConfirm(SeatChangeApply seatChangeApply, String name, String staffnameA, String leadername,String to,String cc) {
		int num = -1;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException();
			}
			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatChangeApply.getRefno());
			operation.setOperationstatus(seatChangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(SQL);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag2 = ps.executeUpdate();
			if(flag2<1){
				throw new RuntimeException();
			}
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
				String content = "";
				content+="Dear "+leadername+",<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;The seat exchange request from your team  "+staffnameA+" is pending for your confirmation.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> (Step: Login in the COAT System, Choose COAT-Seat Exchange --> SeatExchangeApply_Leader)..Thanks<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				
				String result=SendMail.send("COAT – Seat Change Request",to,cc,null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
			
			con.commit();
			logger.info("responseConfirm seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("responseConfirm seat_change_apply failed"+e.toString());
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
	
	public int responseLeaderConfirm(SeatChangeApply seatChangeApply, String name, SeatList seatListA, SeatList seatListB, String leadernameb,String to,String cc) throws SQLException {
		int num = -1;
		try{	
	
			super.openConnection();
			super.openTransaction();
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			int flag1 = update2(sql,seatChangeApply.getRemarkall(),seatChangeApply.getStatus(),seatChangeApply.getRefno());
			if(flag1<0){
				throw new RuntimeException("更细座位交换表数据失败");
			}	
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			int flag2 = super.saveEntity(SQL,seatChangeApply.getRefno(),seatChangeApply.getStatus(),name,sdf.format(new Date()));
			if(flag2 < 1){
				throw new RuntimeException("保存座位座位交换表操作记录失败.");
			}			
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
			String content = "";


			//非15F且同一层换位
			if((!"15F".equals(seatListA.getFloor()))&&seatListA.getFloor().equals(seatListB.getFloor())){
				
				String sql3 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
				int flag3 = super.update2(sql3, seatChangeApply.getStaffcodec(),
						seatListB.getStaffname(),
						seatListB.getExtensionno(),
						seatListB.getPigenBoxno(),
						seatChangeApply.getSeatnoc());
				if(flag3 < 0){
					throw new RuntimeException("更新座位失败！");
				}
				
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sql9 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				int flag9 = super.saveEntity(sql9,seatChangeApply.getSeatnoc(),seatListA.getStaffcode(),seatListA.getStaffname(),seatListA.getExtensionno(),seatListA.getFloor(),seatListA.getLocation(),seatListA.getPigenBoxno(),seatListA.getDeskDrawerno(),seatListA.getLockerno(),seatListA.getIfhidden(),seatListA.getRemark(),name,sd.format(new Date()),"主动换位");
				if(flag9 < 1){
					throw new RuntimeException("保存座位表操作记录失败.");
				}

				String sql4 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
				int flag4 = super.update2(sql4, seatChangeApply.getStaffcoded(),
						seatListA.getStaffname(),
						seatListA.getExtensionno(),
						seatListA.getPigenBoxno(),
						seatChangeApply.getSeatnod());
				if(flag4 < 0){
					throw new RuntimeException("更新座位失败！");
				}
				
				SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sql10 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				int flag10 = super.saveEntity(sql10,seatChangeApply.getSeatnod(),seatListB.getStaffcode(),seatListB.getStaffname(),seatListB.getExtensionno(),seatListB.getFloor(),seatListB.getLocation(),seatListB.getPigenBoxno(),seatListB.getDeskDrawerno(),seatListB.getLockerno(),seatListB.getIfhidden(),seatListB.getRemark(),name,sdft.format(new Date()),"主动换位");
				if(flag10 < 1){
					throw new RuntimeException("保存座位表操作记录失败.");
				}

				//如果申请人是PA，则邮箱不显示chickenbox no
				if(IsPA(seatChangeApply.getStaffcodea())){
					content+="Dear "+seatListA.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by"+leadernameb+".<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
		      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+""+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
		      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+""+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					
				}else{
					content+="Dear "+seatListA.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by"+leadernameb+".<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
		      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+seatListA.getPigenBoxno()+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
		      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+seatListB.getPigenBoxno()+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				}				
				

				
				String result=SendMail.send("COAT – Seat Change Request",to,cc+";adminfo@convoy.com.hk",null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
				
			}else{
				
				
				
				String sql3 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
				int flag3 = super.update2(sql3, seatChangeApply.getStaffcodec(),
						seatListB.getStaffname(),
						seatListB.getExtensionno(),
						seatListA.getPigenBoxno(),
						seatChangeApply.getSeatnoc());
				if(flag3 < 0){
					throw new RuntimeException("更新座位失败！");
				}
				
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sql9 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				int flag9 = super.saveEntity(sql9,seatChangeApply.getSeatnoc(),seatListA.getStaffcode(),seatListA.getStaffname(),seatListA.getExtensionno(),seatListA.getFloor(),seatListA.getLocation(),seatListA.getPigenBoxno(),seatListA.getDeskDrawerno(),seatListA.getLockerno(),seatListA.getIfhidden(),seatListA.getRemark(),name,sd.format(new Date()),"主动换位");
				if(flag9 < 1){
					throw new RuntimeException("保存座位表操作记录失败.");
				}

				String sql4 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
				int flag4 = super.update2(sql4, seatChangeApply.getStaffcoded(),
						seatListA.getStaffname(),
						seatListA.getExtensionno(),
						seatListB.getPigenBoxno(),
						seatChangeApply.getSeatnod());
				if(flag4 < 0){
					throw new RuntimeException("更新座位失败！");
				}
				
				SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sql10 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				int flag10 = super.saveEntity(sql10,seatChangeApply.getSeatnod(),seatListB.getStaffcode(),seatListB.getStaffname(),seatListB.getExtensionno(),seatListB.getFloor(),seatListB.getLocation(),seatListB.getPigenBoxno(),seatListB.getDeskDrawerno(),seatListB.getLockerno(),seatListB.getIfhidden(),seatListB.getRemark(),name,sdft.format(new Date()),"主动换位");
				if(flag10 < 1){
					throw new RuntimeException("保存座位表操作记录失败.");
				}

				// 需要判断楼层是否不同发送邮件 20181012新增需求
				if(!(seatListA.getFloor().equals(seatListB.getFloor()))){
					String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
					int flag88 = super.saveEntity(sql88, 
							seatListA.getStaffcode(),
							seatListA.getStaffname(),
							seatListA.getSeatno(),
							seatListA.getLocation(),
							seatListA.getFloor(),
							seatListB.getSeatno(),
							seatListB.getLocation(),
							seatListB.getFloor(),
							sd.format(new Date()),
							1,
							"主动换位"
							);
					if(flag88 < 1){
						throw new RuntimeException("保存成功换座记录失败.");
					}					
					if(!Util.objIsNULL(seatListB.getStaffcode())){
						String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
						int flag99 = super.saveEntity(sql99, 
								seatListB.getStaffcode(),
								seatListB.getStaffname(),
								seatListB.getSeatno(),
								seatListB.getLocation(),
								seatListB.getFloor(),
								seatListA.getSeatno(),
								seatListA.getLocation(),
								seatListA.getFloor(),
								sd.format(new Date()),
								1,
								"主动换位"
								);
						if(flag99 < 1){
							throw new RuntimeException("保存成功换座记录失败.");
						}					
					}
				}
				
				//如果申请人是PA，则邮箱不显示chickenbox no
				if(IsPA(seatChangeApply.getStaffcodea())){
					content+="Dear "+seatListA.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by"+leadernameb+".<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
		      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+""+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
		      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+""+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
					
				}else{
					content+="Dear "+seatListA.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is accepted by"+leadernameb+".<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
		      		content+="<br/>";		      		
		      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
		      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+seatListB.getPigenBoxno()+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
		      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+seatListA.getPigenBoxno()+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you. <br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
				}		
				
				String result=SendMail.send("COAT – Seat Change Request",to,cc+";adminfo@convoy.com.hk",null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}
				
			}

						
			super.sumbitTransaction();
			logger.info("responseConfirm seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("responseConfirm seat_change_apply failed"+e.toString());
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			super.closeConnection();  
		}
		return num;
	}
	
	public int refuseApply(SeatChangeApply seatChangeApply, String name, String staffnameA, String to) {
		int num = -1;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException();
			}
			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatChangeApply.getRefno());
			operation.setOperationstatus(seatChangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(SQL);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag2 = ps.executeUpdate();
			if(flag2<1){
				throw new RuntimeException();
			}
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
			String content = "";
			
				content+="Dear "+staffnameA+",<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is rejected.<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
				content+="<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
				String result=SendMail.send("COAT – Seat Change Request",to,null,null,null,content,null,null,null);
				JSONObject json=new JSONObject(result);
				if(json.get("state")=="error"){
					throw new RuntimeException((String)json.get("msg"));
				}  
				
			con.commit();
			logger.info("refuse seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("refuse seat_change_apply failed"+e.toString());
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
	public int refuseApplyByReponseLeader(SeatChangeApply seatChangeApply, String name, String staffnameA,String leadernameb, String to,String cc) {
		int num = -1;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException();
			}
			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatChangeApply.getRefno());
			operation.setOperationstatus(seatChangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(SQL);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag2 = ps.executeUpdate();
			if(flag2<1){
				throw new RuntimeException();
			}
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
			String content = "";
			
			content+="Dear "+staffnameA+",<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat exchange request is rejected by "+leadernameb+".<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>"; 
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank you.<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
			String result=SendMail.send("COAT – Seat Change Request",to,cc,null,null,content,null,null,null);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}  
			
			con.commit();
			logger.info("refuse seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("refuse seat_change_apply failed"+e.toString());
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
	public int refuseApplyByReqestLeader(SeatChangeApply seatChangeApply, String name, String staffnameA, String leadernamea,String to,String cc) {
		int num = -1;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException();
			}
			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatChangeApply.getRefno());
			operation.setOperationstatus(seatChangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(SQL);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag2 = ps.executeUpdate();
			if(flag2<1){
				throw new RuntimeException();
			}
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
			String content = "";
			
			content+="Dear "+staffnameA+",<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat change request is rejected by "+leadernamea+".<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
			String result=SendMail.send("COAT – Seat Change Request",to,cc,null,null,content,null,null,null);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}  
			
			con.commit();
			logger.info("refuse seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("refuse seat_change_apply failed"+e.toString());
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
	public int refuseApplyByResponse(SeatChangeApply seatChangeApply, String name, String staffnameA,String staffnameB, String to) {
		int num = -1;
		try{	
			con = DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getRemarkall());
			ps.setString(2, seatChangeApply.getStatus());
			ps.setString(3, seatChangeApply.getRefno());
			int flag1 = ps.executeUpdate();
			if(flag1<0){
				throw new RuntimeException();
			}
			//保存用户操作记录
			SeatChangeOperation operation = new SeatChangeOperation();
			operation.setRefno(seatChangeApply.getRefno());
			operation.setOperationstatus(seatChangeApply.getStatus());
			operation.setOperationname(name);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operation.setOperationdate(sdf.format(new Date()));
			String SQL="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			ps= con.prepareStatement(SQL);
			ps.setString(1, operation.getRefno());
			ps.setString(2, operation.getOperationstatus());
			ps.setString(3, operation.getOperationname());
			ps.setString(4, operation.getOperationdate());
			int flag2 = ps.executeUpdate();
			if(flag2<1){
				throw new RuntimeException();
			}
			//发送邮件提醒
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}
			
			String content = "";
			
			content+="Dear "+staffnameA+",<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat change request is rejected by "+staffnameB+".<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
			String result=SendMail.send("COAT – Seat Change Request",to,null,null,null,content,null,null,null);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}  
			
			con.commit();
			logger.info("refuse seat_change_apply success");
			num = 1; 
			
		}catch (Exception e) {
			num = -1;
			logger.error("refuse seat_change_apply failed"+e.toString());
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
	
	
	public int updateApplyC(SeatChangeApply seatChangeApply,String name,String to, String cc, SeatList seatListA, SeatList seatListB) throws SQLException {
		int num = -1;
		try{	
			
			    super.openConnection();
			    super.openTransaction();
			    String sql1="update seat_change_apply set remarkall=? , status=?  where  refno=?";
			    int flag1 = update2(sql1,seatChangeApply.getRemarkall(),seatChangeApply.getStatus(),seatChangeApply.getRefno());
			    if(flag1<0){
					 throw new RuntimeException("更新换位失败！");
				}
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    String sql2="insert into seat_change_operation (refno,operationstatus,operationname,operationdate)values(?,?,?,?)";
			    int flag2 = super.saveEntity(sql2,seatChangeApply.getRefno(),seatChangeApply.getStatus(),name,sdf.format(new Date()));
				if(flag2 < 1){
					throw new RuntimeException("保存换位操作记录失败.");
				}			    
			
		      //邮件提醒
		      	String url = "";
		      	if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
		      		url = Util.getProValue("public.system.uatlink");
		      	}else{
		      		url = Util.getProValue("public.system.link");
		      	}
		      	
		      	String content = "";
		      	if(!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("VOID")){
				    content+="Dear "+seatListA.getStaffname()+",<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Your seat change request is rejected by "+name+".<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Please visit <a href='"+url+"'>[COAT]</a> for details.<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.  Thank you.<br/>";
					content+="<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
					content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";	      		
		      		String result=SendMail.send("COAT – Seat Change Request",to,cc,null,null,content,null,null,null);
				    JSONObject json=new JSONObject(result);
					if(json.get("state")=="error"){
						throw new RuntimeException((String)json.get("msg"));
					}      		
		      	} else if (!Util.objIsNULL(seatChangeApply.getStatus()) && seatChangeApply.getStatus().equals("Completed")){
					//非15F 同一层换位
					if((!"15F".equals(seatListA.getFloor()))&&seatListA.getFloor().equals(seatListB.getFloor())){
						
						String sql3 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql3, seatChangeApply.getStaffcodec(),
								seatListB.getStaffname(),
								seatListB.getExtensionno(),
								seatListB.getPigenBoxno(),
								seatChangeApply.getSeatnoc());
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatChangeApply.getSeatnoc(),seatListA.getStaffcode(),seatListA.getStaffname(),seatListA.getExtensionno(),seatListA.getFloor(),seatListA.getLocation(),seatListA.getPigenBoxno(),seatListA.getDeskDrawerno(),seatListA.getLockerno(),seatListA.getIfhidden(),seatListA.getRemark(),name,sd.format(new Date()),"主动换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}

						String sql4 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag4 = super.update2(sql4, seatChangeApply.getStaffcoded(),
								seatListA.getStaffname(),
								seatListA.getExtensionno(),
								seatListA.getPigenBoxno(),
								seatChangeApply.getSeatnod());
						if(flag4 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatChangeApply.getSeatnod(),seatListB.getStaffcode(),seatListB.getStaffname(),seatListB.getExtensionno(),seatListB.getFloor(),seatListB.getLocation(),seatListB.getPigenBoxno(),seatListB.getDeskDrawerno(),seatListB.getLockerno(),seatListB.getIfhidden(),seatListB.getRemark(),name,sdft.format(new Date()),"主动换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}

						//如果申请人是PA，则邮箱不显示chickenbox no
						if(IsPA(seatChangeApply.getStaffcodea())){
				      		content+="Dear "+seatListA.getStaffname()+",<br/>";
							content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your application is approved. <br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Change Results:<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a> <br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
				      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+""+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
				      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+""+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
						}else{
				      		content+="Dear "+seatListA.getStaffname()+",<br/>";
							content+="<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your application is approved. <br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Change Results:<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a> <br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
				      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+seatListA.getPigenBoxno()+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
				      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+seatListB.getPigenBoxno()+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
				      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
						}								
			      		
			      		String result=SendMail.send("COAT – Seat Change Request",to,cc+";adminfo@convoy.com.hk",null,null,content,null,null,null);
					    JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
						
					}else{
						
						String sql3 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag3 = super.update2(sql3, seatChangeApply.getStaffcodec(),
								seatListB.getStaffname(),
								seatListB.getExtensionno(),
								seatListA.getPigenBoxno(),
								seatChangeApply.getSeatnoc());
						if(flag3 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql9 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						int flag9 = super.saveEntity(sql9,seatChangeApply.getSeatnoc(),seatListA.getStaffcode(),seatListA.getStaffname(),seatListA.getExtensionno(),seatListA.getFloor(),seatListA.getLocation(),seatListA.getPigenBoxno(),seatListA.getDeskDrawerno(),seatListA.getLockerno(),seatListA.getIfhidden(),seatListA.getRemark(),name,sd.format(new Date()),"主动换位");
						if(flag9 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}

						String sql4 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
						int flag4 = super.update2(sql4, seatChangeApply.getStaffcoded(),
								seatListA.getStaffname(),
								seatListA.getExtensionno(),
								seatListB.getPigenBoxno(),
								seatChangeApply.getSeatnod());
						if(flag4 < 0){
							throw new RuntimeException("更新座位失败！");
						}
						
						SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sql10 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						int flag10 = super.saveEntity(sql10,seatChangeApply.getSeatnod(),seatListB.getStaffcode(),seatListB.getStaffname(),seatListB.getExtensionno(),seatListB.getFloor(),seatListB.getLocation(),seatListB.getPigenBoxno(),seatListB.getDeskDrawerno(),seatListB.getLockerno(),seatListB.getIfhidden(),seatListB.getRemark(),name,sdft.format(new Date()),"主动换位");
						if(flag10 < 1){
							throw new RuntimeException("保存座位表操作记录失败.");
						}	
						
						//需要判断楼层是否不同发送邮件 20181012新增需求 
						if(!(seatListA.getFloor().equals(seatListB.getFloor()))){
							String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
							int flag88 = super.saveEntity(sql88, 
									seatListA.getStaffcode(),
									seatListA.getStaffname(),
									seatListA.getSeatno(),
									seatListA.getLocation(),
									seatListA.getFloor(),
									seatListB.getSeatno(),
									seatListB.getLocation(),
									seatListB.getFloor(),
									sd.format(new Date()),
									1,
									"主动换位"
									);
							if(flag88 < 1){
								throw new RuntimeException("保存成功换座记录失败.");
							}					
							
							
							if(!Util.objIsNULL(seatListB.getStaffcode())){
								String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
								int flag99 = super.saveEntity(sql99, 
										seatListB.getStaffcode(),
										seatListB.getStaffname(),
										seatListB.getSeatno(),
										seatListB.getLocation(),
										seatListB.getFloor(),
										seatListA.getSeatno(),
										seatListA.getLocation(),
										seatListA.getFloor(),
										sd.format(new Date()),
										1,
										"主动换位"
										);
								if(flag99 < 1){
									throw new RuntimeException("保存成功换座记录失败.");
								}
							}
						}
						
			      		content+="Dear "+seatListA.getStaffname()+",<br/>";
						content+="<br/>";
			      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Your application is approved. <br/>";
			      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
						content+="<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Change Results:<br/>";
						content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a> <br/>";
			      		content+="<br/>";		      		
			      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
						+"        <table id='xx' cellpadding=0 cellspacing=0 >"
						+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
			      		+"<tr><td>"+seatListA.getStaffcode()+"</td><td>"+seatListA.getStaffname()+"</td><td>"+seatListB.getSeatno()+"</td><td>"+seatListB.getPigenBoxno()+"</td><td>"+seatListA.getExtensionno()+"</td><td>"+seatListB.getFloor()+" "+seatListB.getLocation()+"</td></tr>"	      		
			      		+"<tr><td>"+seatListB.getStaffcode()+"</td><td>"+seatListB.getStaffname()+"</td><td>"+seatListA.getSeatno()+"</td><td>"+seatListA.getPigenBoxno()+"</td><td>"+seatListB.getExtensionno()+"</td><td>"+seatListA.getFloor()+" "+seatListA.getLocation()+"</td></tr></table><br/><br/>";	
			      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			      		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			      		
			      		String result=SendMail.send("COAT – Seat Change Request",to,cc+";adminfo@convoy.com.hk",null,null,content,null,null,null);
					    JSONObject json=new JSONObject(result);
						if(json.get("state")=="error"){
							throw new RuntimeException((String)json.get("msg"));
						}
						
					}					
					
		      	} else {
		      		logger.info("换位申请发起时邮件通知结果: 未发送邮件通知");
		      	}		      
		      
			super.sumbitTransaction();
			logger.info("complete seat_change_apply success");
			num = 1;
		}catch (Exception e) {
			num = -1;
			logger.error("complete seat_change_apply failed"+e.toString());
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			super.closeConnection();  
		}
		return num;
	}
	
	public int existSeatNo(String seatno) throws SQLException{
		int num = -1;
		try {
			num=super.findCount("select count(1) from seat_list where seatno=? and `status` = 'Y'", seatno);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询座位号是否存在失败："+e.toString());
		}finally{
			super.closeConnection();
		}
		return num;
	}
	public int existStaffCode(String staffcode) throws SQLException{
		int num = -1;
		try {
			num=super.findCount("select count(1) from seat_list where staffcode=? and `status` = 'Y'", staffcode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询顾问是否已有座位安排失败："+e.toString());
		}finally{
			super.closeConnection();
		}
		return num;
	}

	public int change15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		try {
			super.openTransaction();
			sql1 = "insert into seat_list(seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenBoxno,`status`,updater,updateDate,ifhidden,remark,remark1) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql1,seatno,
					seatoldlist.getStaffcode(),
					seatoldlist.getStaffname(),
					seatoldlist.getExtensionno(),
					location,
					floor,
					deskDrawerno,
					lockerno,
					pigenBoxno,
				    "Y",
				    name,
				    DateUtils.getNowDateTime(),
				    ifhidden,
				    seatType,
				    remark);
			if(num < 1){
				throw new RuntimeException("新增座位失败！");
			}else{
				/*增加座位表操作记录*/
				sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				num = super.saveEntity(sql2,seatno,seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),floor,location,pigenBoxno,deskDrawerno,lockerno,ifhidden,seatType,name,DateUtils.getNowDateTime(),"ADM新增座位号");
				if(num < 1){
					throw new RuntimeException("新增座位号操作记录失败！");
				}else{
					/*将该staffcode原先座位信息清空*/
					sql3 ="update seat_list set staffcode = ?,staffname = ?,extensionno = ? where seatno = ? and `status` = 'Y' ";
					num = super.update2(sql3, "","","",seatoldlist.getSeatno());
					if(num < 0){
						throw new RuntimeException("清空旧座位数据失败！");
					}else{
						/*增加座位表操作记录*/
						sql4 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						num = super.saveEntity(sql4,seatoldlist.getSeatno(),seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),seatoldlist.getFloor(),seatoldlist.getLocation(),seatoldlist.getPigenBoxno(),seatoldlist.getDeskDrawerno(),seatoldlist.getLockerno(),seatoldlist.getIfhidden(),seatoldlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM新增座位号时清空旧座位号");
						if(num < 1){
							throw new RuntimeException("新增清空座位号操作记录失败！");
						}
						
						
					      String url = "";
					      if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
					    	  url = Util.getProValue("public.system.uatlink");
					      }else{
					    	  url = Util.getProValue("public.system.link");
					      }				

							String content = "";				
							content+="Dear "+seatoldlist.getStaffname()+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
				      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatno+"</td><td>"+pigenBoxno+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+floor+" "+location+"</td><td>New</td></tr>"	      		
				      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
							String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
							JSONObject json=new JSONObject(result);
							if(json.get("state")=="error"){
								throw new RuntimeException((String)json.get("msg"));
							}					
						
					}
				}
			}
			super.sumbitTransaction();
		} catch (Exception e) {
			num = -1;
			super.rollbackTransaction();
		} finally {
			super.closeConnection();
		}
		return num;
	}
	public int update15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		try {
			super.openTransaction();
			sql1 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,location = ?,floor = ?,deskDrawerno = ?,lockerno = ?,pigenBoxno = ?,ifhidden = ?,remark = ?,remark1 = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql1, seatoldlist.getStaffcode(),
					seatoldlist.getStaffname(),
					seatoldlist.getExtensionno(),
					location,
					floor,
					deskDrawerno,
					lockerno,
					seatoldlist.getPigenBoxno(),
					ifhidden,
					seatType,
					remark,
					seatno);
			if(num < 0){
				throw new RuntimeException("更新座位失败！");
			}else{
				/*增加座位表操作记录*/
				sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				num = super.saveEntity(sql2,seatno,seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),floor,location,seatoldlist.getPigenBoxno(),deskDrawerno,lockerno,ifhidden,seatType,name,DateUtils.getNowDateTime(),"ADM修改座位信息");
				if(num < 1){
					throw new RuntimeException("新增座位号操作记录失败！");
				}else{
					/*将该staffcode原先座位信息清空*/
					sql3 ="update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ?  where seatno = ? and `status` = 'Y' ";
					num = super.update2(sql3, "","","",pigenBoxno,seatoldlist.getSeatno());
					if(num < 0){
						throw new RuntimeException("更新旧座位数据失败！");
					}else{
						/*增加座位表操作记录*/
						sql4 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						num = super.saveEntity(sql4,seatoldlist.getSeatno(),seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),seatoldlist.getFloor(),seatoldlist.getLocation(),seatoldlist.getPigenBoxno(),seatoldlist.getDeskDrawerno(),seatoldlist.getLockerno(),seatoldlist.getIfhidden(),seatoldlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM修改座位号时更新旧座位号");
						if(num < 1){
							throw new RuntimeException("新增旧座位号操作记录失败！");
						}
						
					      String url = "";
					      if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
					    	  url = Util.getProValue("public.system.uatlink");
					      }else{
					    	  url = Util.getProValue("public.system.link");
					      }				

							String content = "";				
							content+="Dear "+seatoldlist.getStaffname()+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
				      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatno+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+floor+" "+location+"</td><td>New</td></tr>"	      		
				      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
							String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
							JSONObject json=new JSONObject(result);
							if(json.get("state")=="error"){
								throw new RuntimeException((String)json.get("msg"));
							}							
						
					}
				}
			}
			super.sumbitTransaction();
		} catch (Exception e) {
			num = -1;
			super.rollbackTransaction();
		} finally {
			super.closeConnection();
		}
		return num;
	}
	public int update15FloorSeats(SeatList seatoldlist,String staffcodetemp,String staffnametemp,String extensionnotemp,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc,String to1,String cc1) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		try {
			super.openTransaction();
			sql1 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,location = ?,floor = ?,deskDrawerno = ?,lockerno = ?,pigenBoxno = ?,ifhidden = ?,remark = ?,remark1 = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql1, seatoldlist.getStaffcode(),
					seatoldlist.getStaffname(),
					seatoldlist.getExtensionno(),
					location,
					floor,
					deskDrawerno,
					lockerno,
					seatoldlist.getPigenBoxno(),
					ifhidden,
					seatType,
					remark,
					seatno);
			if(num < 0){
				throw new RuntimeException("更新座位失败！");
			}else{
				/*增加座位表操作记录*/
				sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				num = super.saveEntity(sql2,seatno,seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),floor,location,seatoldlist.getPigenBoxno(),deskDrawerno,lockerno,ifhidden,seatType,name,DateUtils.getNowDateTime(),"ADM修改座位信息");
				if(num < 1){
					throw new RuntimeException("新增座位号操作记录失败！");
				}else{
					/*将该staffcode原先座位信息清空*/
					sql3 ="update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
					num = super.update2(sql3, staffcodetemp,staffnametemp,extensionnotemp,pigenBoxno,seatoldlist.getSeatno());
					if(num < 0){
						throw new RuntimeException("更新旧座位数据失败！");
					}else{
						/*增加座位表操作记录*/
						sql4 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						num = super.saveEntity(sql4,seatoldlist.getSeatno(),seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),seatoldlist.getFloor(),seatoldlist.getLocation(),seatoldlist.getPigenBoxno(),seatoldlist.getDeskDrawerno(),seatoldlist.getLockerno(),seatoldlist.getIfhidden(),seatoldlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM修改座位号时更新旧座位号");
						if(num < 1){
							throw new RuntimeException("新增旧座位号操作记录失败！");
						}
						
					      String url = "";
					      if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
					    	  url = Util.getProValue("public.system.uatlink");
					      }else{
					    	  url = Util.getProValue("public.system.link");
					      }				

							String content = "";				
							content+="Dear "+seatoldlist.getStaffname()+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
				      		content+="<br/>";		      		
				      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
				      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatno+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+floor+" "+location+"</td><td>New</td></tr>"	      		
				      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
							String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
							JSONObject json=new JSONObject(result);
							if(json.get("state")=="error"){
								throw new RuntimeException((String)json.get("msg"));
							}	
							
							
							
							
							String content1 = "";				
							content1+="Dear "+staffnametemp+",<br/>";
							content1+="<br/>";
							content1+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
							content1+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content1+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
							content1+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
				      		content1+="<br/>";		      		
				      		content1+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
							+"        <table id='xx' cellpadding=0 cellspacing=0 >"
							+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
				      		+"<tr><td>"+staffcodetemp+"</td><td>"+staffnametemp+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+pigenBoxno+"</td><td>"+extensionnotemp+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>New</td></tr>"	      		
				      		+"<tr><td>"+staffcodetemp+"</td><td>"+staffnametemp+"</td><td>"+seatno+"</td><td>"+pigenBoxno+"</td><td>"+extensionnotemp+"</td><td>"+floor+" "+location+"</td><td>Previous</td></tr></table><br/><br/>";	      		
							content1+="<br/>";
							content1+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content1+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							
							String result1=SendMail.send("COAT – Seat Change Request",to1,cc1+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content1,null,null,null);
							JSONObject json1=new JSONObject(result1);
							if(json1.get("state")=="error"){
								throw new RuntimeException((String)json1.get("msg"));
							}
							
							
							
							
							
							
							
					}
				}
			}
			super.sumbitTransaction();
		} catch (Exception e) {
			num = -1;
			super.rollbackTransaction();
		} finally {
			super.closeConnection();
		}
		return num;
	}
	public int updateNot15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		try {
			super.openTransaction();
			sql1 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,location = ?,floor = ?,deskDrawerno = ?,lockerno = ?,pigenBoxno = ?,ifhidden = ?,remark = ?,remark1 = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql1, seatoldlist.getStaffcode(),
					seatoldlist.getStaffname(),
					seatoldlist.getExtensionno(),
					location,
					floor,
					deskDrawerno,
					lockerno,
					pigenBoxno,
					ifhidden,
					seatType,
					remark,
					seatno);
			if(num < 0){
				throw new RuntimeException("更新座位失败！");
			}

			/*增加座位表操作记录*/
			sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql2,seatno,seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),floor,location,pigenBoxno,deskDrawerno,lockerno,ifhidden,seatType,name,DateUtils.getNowDateTime(),"ADM修改座位信息");
			if(num < 1){
				throw new RuntimeException("新增座位号操作记录失败！");
			}

			/*将该staffcode原先座位信息清空*/
			sql3 ="update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql3, "","","",seatoldlist.getPigenBoxno(),seatoldlist.getSeatno());
			if(num < 0){
				throw new RuntimeException("更新旧座位数据失败！");
			}

			/*增加座位表操作记录*/
			sql4 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql4,seatoldlist.getSeatno(),seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),seatoldlist.getFloor(),seatoldlist.getLocation(),seatoldlist.getPigenBoxno(),seatoldlist.getDeskDrawerno(),seatoldlist.getLockerno(),seatoldlist.getIfhidden(),seatoldlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM修改座位号时更新旧座位号");
			if(num < 1){
				throw new RuntimeException("新增旧座位号操作记录失败！");
			}
			
			// 需要判断楼层是否不同发送邮件 20181012新增需求
			if(!(seatoldlist.getFloor().equals(floor))){
				String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
				int flag88 = super.saveEntity(sql88, 
						seatoldlist.getStaffcode(),
						seatoldlist.getStaffname(),
						seatoldlist.getSeatno(),
						seatoldlist.getLocation(),
						seatoldlist.getFloor(),
						seatno,
						location,
						floor,
						DateUtils.getNowDateTime(),
						1,
						"admin update seatno"
						);
				if(flag88 < 1){
					throw new RuntimeException("保存成功换座记录失败.");
				}					
			}
			
			
	        String url = "";
	        if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
	    	    url = Util.getProValue("public.system.uatlink");
	        }else{
	    	    url = Util.getProValue("public.system.link");
	        }				

			String content = "";				
			content+="Dear "+seatoldlist.getStaffname()+",<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
      		content+="<br/>";		      		
      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
			+"        <table id='xx' cellpadding=0 cellspacing=0 >"
			+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatno+"</td><td>"+pigenBoxno+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+floor+" "+location+"</td><td>New</td></tr>"	      		
      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			
			String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}												
		
			super.sumbitTransaction();
		} catch (Exception e) {
			num = -1;
			super.rollbackTransaction();
		} finally {
			super.closeConnection();
		}
		return num;
	}
	public int updateNot15FloorSeats(SeatList seatoldlist,String staffcodetemp,String staffnametemp,String extensionnotemp,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc,String to1,String cc1) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		try {
			super.openTransaction();
			sql1 = "update seat_list set staffcode = ?,staffname = ?,extensionno = ?,location = ?,floor = ?,deskDrawerno = ?,lockerno = ?,pigenBoxno = ?,ifhidden = ?,remark = ?,remark1 = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql1, seatoldlist.getStaffcode(),
					seatoldlist.getStaffname(),
					seatoldlist.getExtensionno(),
					location,
					floor,
					deskDrawerno,
					lockerno,
					pigenBoxno,
					ifhidden,
					seatType,
					remark,
					seatno);
			if(num < 0){
				throw new RuntimeException("更新座位失败！");
			}

			/*增加座位表操作记录*/
			sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql2,seatno,seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),floor,location,pigenBoxno,deskDrawerno,lockerno,ifhidden,seatType,name,DateUtils.getNowDateTime(),"ADM修改座位信息");
			if(num < 1){
				throw new RuntimeException("新增座位号操作记录失败！");
			}

			/*将该staffcode原先座位信息替换*/
			sql3 ="update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql3, staffcodetemp,staffnametemp,extensionnotemp,seatoldlist.getPigenBoxno(),seatoldlist.getSeatno());
			if(num < 0){
				throw new RuntimeException("更新旧座位数据失败！");
			}

			/*增加座位表操作记录*/
			sql4 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql4,seatoldlist.getSeatno(),seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),seatoldlist.getFloor(),seatoldlist.getLocation(),seatoldlist.getPigenBoxno(),seatoldlist.getDeskDrawerno(),seatoldlist.getLockerno(),seatoldlist.getIfhidden(),seatoldlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM修改座位号时更新旧座位号");
			if(num < 1){
				throw new RuntimeException("新增旧座位号操作记录失败！");
			}
			
			
			// 需要判断楼层是否不同发送邮件 20181012新增需求
			if(!(seatoldlist.getFloor().equals(floor))){
				String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
				int flag88 = super.saveEntity(sql88, 
						seatoldlist.getStaffcode(),
						seatoldlist.getStaffname(),
						seatoldlist.getSeatno(),
						seatoldlist.getLocation(),
						seatoldlist.getFloor(),
						seatno,
						location,
						floor,
						DateUtils.getNowDateTime(),
						1,
						"admin update seatnos"
						);
				if(flag88 < 1){
					throw new RuntimeException("保存成功换座记录失败.");
				}	
				
				if(!Util.objIsNULL(staffcodetemp)){
					String sql99 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
					int flag99 = super.saveEntity(sql99, 
							staffcodetemp,
							staffnametemp,
							seatno,
							location,
							floor,
							seatoldlist.getSeatno(),
							seatoldlist.getLocation(),
							seatoldlist.getFloor(),
							DateUtils.getNowDateTime(),
							1,
							"admin update seatnos"
							);
					if(flag99 < 1){
						throw new RuntimeException("保存成功换座记录失败.");
					}					
				}
			}
			
			
			
			
			
			
	        String url = "";
	        if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
	    	    url = Util.getProValue("public.system.uatlink");
	        }else{
	    	    url = Util.getProValue("public.system.link");
	        }				

			String content = "";				
			content+="Dear "+seatoldlist.getStaffname()+",<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
      		content+="<br/>";		      		
      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
			+"        <table id='xx' cellpadding=0 cellspacing=0 >"
			+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatno+"</td><td>"+pigenBoxno+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+floor+" "+location+"</td><td>New</td></tr>"	      		
      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			
			String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}	
			
			String content1 = "";				
			content1+="Dear "+staffnametemp+",<br/>";
			content1+="<br/>";
			content1+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
			content1+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
			content1+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
			content1+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
      		content1+="<br/>";		      		
      		content1+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
			+"        <table id='xx' cellpadding=0 cellspacing=0 >"
			+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
      		+"<tr><td>"+staffcodetemp+"</td><td>"+staffnametemp+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+extensionnotemp+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>New</td></tr>"	      		
      		+"<tr><td>"+staffcodetemp+"</td><td>"+staffnametemp+"</td><td>"+seatno+"</td><td>"+pigenBoxno+"</td><td>"+extensionnotemp+"</td><td>"+floor+" "+location+"</td><td>Previous</td></tr></table><br/><br/>";	      		
			content1+="<br/>";
			content1+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content1+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			
			String result1=SendMail.send("COAT – Seat Change Request",to1,cc1+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content1,null,null,null);
			JSONObject json1=new JSONObject(result1);
			if(json1.get("state")=="error"){
				throw new RuntimeException((String)json1.get("msg"));
			}
		
			super.sumbitTransaction();
			
		} catch (Exception e) {
			num = -1;
			super.rollbackTransaction();
		} finally {
			super.closeConnection();
		}
		return num;
	}
	
	public int changeNot15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException{
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		try {
			super.openTransaction();
			sql1 = "insert into seat_list(seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenBoxno,`status`,updater,updateDate,ifhidden,remark,remark1) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql1,seatno,
					seatoldlist.getStaffcode(),
					seatoldlist.getStaffname(),
					seatoldlist.getExtensionno(),
					location,
					floor,
					deskDrawerno,
					lockerno,
					seatoldlist.getPigenBoxno(),
					"Y",
					name,
					DateUtils.getNowDateTime(),
					ifhidden,
					seatType,
					remark);
			if(num < 1){
				throw new RuntimeException("新增座位失败！");
			}
			

			/*增加座位表操作记录*/
			num = -1;
			sql2 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql2,seatno,seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),floor,location,seatoldlist.getPigenBoxno(),deskDrawerno,lockerno,ifhidden,seatType,name,DateUtils.getNowDateTime(),"ADM新增座位号");
			if(num < 1){
				throw new RuntimeException("新增座位号操作记录失败！");
			}

			
			num = -1;
			/*将该staffcode原先座位信息清空*/
			sql3 ="update seat_list set staffcode = ?,staffname = ?,extensionno = ?,pigenBoxno = ? where seatno = ? and `status` = 'Y' ";
			num = super.update2(sql3, "","","",pigenBoxno,seatoldlist.getSeatno());
			
			if(num < 0){
				throw new RuntimeException("清空旧座位数据失败！");
			}
			

			num = -1;
			/*增加座位表操作记录*/
			sql4 = "insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			num = super.saveEntity(sql4,seatoldlist.getSeatno(),seatoldlist.getStaffcode(),seatoldlist.getStaffname(),seatoldlist.getExtensionno(),seatoldlist.getFloor(),seatoldlist.getLocation(),seatoldlist.getPigenBoxno(),seatoldlist.getDeskDrawerno(),seatoldlist.getLockerno(),seatoldlist.getIfhidden(),seatoldlist.getRemark(),name,DateUtils.getNowDateTime(),"ADM新增座位号时清空旧座位号");
			if(num < 1){
				throw new RuntimeException("新增清空座位号操作记录失败！");
			}
			
			
			
			// 需要判断楼层是否不同发送邮件 20181012新增需求
			if(!(seatoldlist.getFloor().equals(floor))){
				String sql88 = "insert into seat_change_successmsg (staffcode,staffname,seatnobefore,locationbefore,floorbefore,seatnoafter,locationafter,floorafter,exchangetime,status,remark) values (?,?,?,?,?,?,?,?,?,?,?);";
				int flag88 = super.saveEntity(sql88, 
						seatoldlist.getStaffcode(),
						seatoldlist.getStaffname(),
						seatoldlist.getSeatno(),
						seatoldlist.getLocation(),
						seatoldlist.getFloor(),
						seatno,
						location,
						floor,
						DateUtils.getNowDateTime(),
						1,
						"admin add"
						);
				if(flag88 < 1){
					throw new RuntimeException("保存成功换座记录失败.");
				}					
			}
			
			String url = "";
			if(!Util.objIsNULL(Util.getProValue("public.system.uat"))&&Util.getProValue("public.system.uat").equals("true")){
				url = Util.getProValue("public.system.uatlink");
			}else{
				url = Util.getProValue("public.system.link");
			}				

			String content = "";				
			content+="Dear "+seatoldlist.getStaffname()+",<br/>";
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Your request is approved.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Seat Results:<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+url+"'>[COAT]</a><br/>";
      		content+="<br/>";		      		
      		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
			+"        <table id='xx' cellpadding=0 cellspacing=0 >"
			+"<tr id='first' style='background-color:black;color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td><td>Type</td></tr>"
      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatno+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+floor+" "+location+"</td><td>New</td></tr>"	      		
      		+"<tr><td>"+seatoldlist.getStaffcode()+"</td><td>"+seatoldlist.getStaffname()+"</td><td>"+seatoldlist.getSeatno()+"</td><td>"+seatoldlist.getPigenBoxno()+"</td><td>"+seatoldlist.getExtensionno()+"</td><td>"+seatoldlist.getFloor()+" "+seatoldlist.getLocation()+"</td><td>Previous</td></tr></table><br/><br/>";	      		
			content+="<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			
			String result=SendMail.send("COAT – Seat Change Request",to,cc+";"+Util.getProValue("public.email.seatchange.byadm.notice.address"),null,null,content,null,null,null);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}							
			
			super.sumbitTransaction();
		} catch (Exception e) {
			super.rollbackTransaction();
			
		} finally {
			super.closeConnection();
		}
		return num;
	}
	
	
	public SeatList getSeatList(String staffcode) throws SQLException {
		SeatList seatList = null;
		try {
			seatList = super.find("select seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno, pigenBoxno,ifhidden,remark,remark1 from seat_list where staffcode=? and `status` = 'Y'", staffcode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据顾问编号查询顾问座位相关信息失败："+e.toString());
		} finally {
			super.closeConnection();
		}
		return seatList;
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
		
	public int updateApplyStatus(SeatChangeApply seatChangeApply) throws SQLException {
		int num = -1;
		try{	
			con = super.getConnction();
		/*	String sql="update seat_change_apply set status=?  where  refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, seatChangeApply.getStatus());
			ps.setString(2, seatChangeApply.getRefno());
			num = ps.executeUpdate();*/
			num=super.updates("update seat_change_apply set status=?  where  refno=?",
					seatChangeApply.getStatus(),
					seatChangeApply.getRefno());
			if(num < 0){
				throw new RuntimeException("更新换位申请失敗");
			}
			logger.info("update seat_change_apply success");
		}catch (Exception e) {
			logger.error("update seat_change_apply failed"+e.toString());
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return num;
	}

	/**
	 * 根据条件导出excel
	 */
	public List<SeatList> exportDate(SeatList seatList){
		List<SeatList> list=new ArrayList<SeatList>();
		try{
			con =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenBoxno,ifhidden,remark from seat_list where `status`='Y'");
			  if(!(Util.objIsNULL(seatList.getStaffcode()))){
				  sql.append("and staffcode like '%"+seatList.getStaffcode()+"%'");
			  }
			  if(!(Util.objIsNULL(seatList.getStaffname()))){
					sql.append("and staffname like '%"+seatList.getStaffname()+"%'");
				}
			  if(!(Util.objIsNULL(seatList.getSeatno()))){
				  sql.append("and seatno like '%"+seatList.getSeatno()+"%'");
			  }
			  if(!(Util.objIsNULL(seatList.getLocation()))){
				  sql.append("and location like '%"+seatList.getLocation()+"%'");
			  }
			  if(!(Util.objIsNULL(seatList.getFloor()))){
				  sql.append("and floor like '%"+seatList.getFloor()+"%'");
			  }
			  if(!(Util.objIsNULL(seatList.getIfhidden()))){
				  sql.append("and ifhidden like '%"+seatList.getIfhidden()+"%'");
			  }
			  if(!(Util.objIsNULL(seatList.getRemark()))){
				  sql.append("and remark like '%"+seatList.getRemark()+"%'");
			  }
			  sql.append("order by seatno asc");
		    ps=con.prepareStatement(sql.toString());
		    rs=ps.executeQuery();
			while(rs.next()){
				SeatList seatListBox=new SeatList();
				seatListBox.setSeatno(rs.getString("seatno"));
				seatListBox.setStaffcode(rs.getString("staffcode"));
				seatListBox.setStaffname(rs.getString("staffname"));
				seatListBox.setExtensionno(rs.getString("extensionno"));
				seatListBox.setLocation(rs.getString("location"));
				seatListBox.setFloor(rs.getString("floor"));
				seatListBox.setDeskDrawerno(rs.getString("deskDrawerno"));
				seatListBox.setLockerno(rs.getString("lockerno"));
				seatListBox.setPigenBoxno(rs.getString("pigenBoxno"));
				seatListBox.setIfhidden("Y".equals(rs.getString("ifhidden"))?"YES":"NO");
				seatListBox.setRemark(rs.getString("remark"));
				list.add(seatListBox);
			}
			rs.close();
		}catch (Exception e) {
			logger.error("export SeatList date error (SeatDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return list;
		
	}
	
	
	/**
	 * 根据code查询Email 
	 */
	public String[] getEmailByCode(String [] str){
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
	/**
	 * 根据code查询Email 
	 */
	public String[] getEmailsByCode(String [] str){
		String[] email=new String[3];
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
	public List<Map<String, Object>> findSeatListByFloor(String floor) throws SQLException {
		List<Map<String,Object>> list=null;
		try{
			StringBuffer sql = new StringBuffer("select x.* from (select a.seatno,b.EmployeeId,b.EmployeeName,b.DDTreeHead,c.teamcode,a.ifhidden from seat_list a ");
			sql.append(" left join cons_list b on a.staffcode=b.EmployeeId");
			sql.append(" left join seat_floorplantemp c on b.DDTreeHead = c.DDTreeHead and a.floor = c.floor where a.`status`='Y' and a.floor =?  order by seatno,teamcode)x,(select @rowno:=1) t ");
			list=super.findListMap(sql.toString(),floor);
		}catch (Exception e) {
			
		}finally{
			super.closeConnection();
		}
		return list;
	}

	
	public String uploadSeatList(List<List<Object>> list,String user) throws Exception{
		
		int num = 0;
		int num1 = 0;
		int num2 = -1;
		int line=0;
		boolean s = false;
		String result="";
		try{
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			openTransaction();
			String sql = "";
			String sql2 = "";
			String sql3 = "";
		for(int i=1;i<list.size();i++){
			List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(0)+"")){
				line=i;
				
				sql3 = "select count(1) from seat_list where seatno = ? and `status` = 'Y'";
				num2 = super.findCount(sql3, (list2.get(0)+""));
				if(num2 == 0){
					s = true;
				}else{
					s = false;
				}
	
				String temp = "";
				if("YES".equals(list2.get(9))){
					temp = "Y";
				}else{
					temp = "N";					
				}
				
				if(s){
					sql="insert into seat_list (seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenBoxno,`status`,updater,updateDate,ifhidden,remark,remark1)" +
							"values('"+list2.get(0)+"','"+list2.get(1)+"','"+list2.get(2)+"','"+list2.get(4)+"','"+list2.get(3)+"','"+list2.get(5)+"','"+list2.get(7)+"'," +
									" '"+list2.get(6)+"','"+list2.get(8)+"','Y','"+user+"','"+DateUtils.getNowDateTime()+"','"+temp+"','"+list2.get(10)+"','')";
					int keyword=saveEntity(sql);
					if(keyword < 0){
						throw new RuntimeException("保存数据失败!");
					}
					/**--------------------------------保存  操作表--------------------------------------------*/
					sql2="insert into seat_operation (seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenboxno,ifhidden,ifad,operationname,operationdate,reason)values('"+list2.get(0)+"','"+list2.get(1)+"','"+list2.get(2)+"','"+list2.get(4)+"','"+list2.get(3)+"','"+list2.get(5)+"','"+list2.get(7)+"','"+list2.get(6)+"','"+list2.get(8)+"','"+temp+"','"+list2.get(10)+"','"+user+"','"+DateUtils.getNowDateTime()+"','座位表上传时新增座位号')";
					num += update(sql2,null);
					/**--------------------------------保存  操作表--------------------------------------------*/
				}else{
					sql="update seat_list set staffcode = ?,staffname = ?,extensionno = ?,location = ?,floor = ?,deskDrawerno = ?,lockerno = ?,pigenBoxno = ?,updater = ?,updateDate = ?,ifhidden = ?,remark = ?,remark1 = ? where seatno = ? and `status` = 'Y' ";
					
					int keyword=update2(sql,list2.get(1),list2.get(2),list2.get(4),list2.get(3),list2.get(5),list2.get(7),list2.get(6),list2.get(8),user,DateUtils.getNowDateTime(),temp,list2.get(10),"",list2.get(0));
					if(keyword < 0){
						throw new RuntimeException("更新数据失败!");
					}
					/**--------------------------------保存  操作表--------------------------------------------*/
					sql2="insert into seat_operation (seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno,pigenboxno,ifhidden,ifad,operationname,operationdate,reason)values('"+list2.get(0)+"','"+list2.get(1)+"','"+list2.get(2)+"','"+list2.get(4)+"','"+list2.get(3)+"','"+list2.get(5)+"','"+list2.get(7)+"','"+list2.get(6)+"','"+list2.get(8)+"','"+temp+"','"+list2.get(10)+"','"+user+"','"+DateUtils.getNowDateTime()+"','座位表上传时修改座位号')";
					num1 += update(sql2,null);
					/**--------------------------------保存  操作表--------------------------------------------*/
					
				}
			}	
			
		}
			sumbitTransaction();//提交事物
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("addnum", num);//新增条数
			map.put("updatenum", num1);//修改条数
			map.put("totalnum", list.size()-1);//总数
			result=Util.getMsgJosnSuccessReturn(map);
		}catch (Exception e) {
			result=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				super.rollbackTransaction();//回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
				super.closeConnection();
		}
		return result;
		
	}	
	
	
	public List<String> getPAList() throws SQLException{
		List<String> list = null;
		try {
			super.openConnection();
			sql="select staffcode from seat_autochange_listfromvsm where changetype = '2' and sfyx = 'Y'";
			list = super.findStringDate(sql);
		} catch (Exception e) {
			logger.error("select staffcode from seat_autochange_listfromvsm failed"+e.toString());
			throw new SQLException(e);
		} finally {
			super.closeConnection();
		}
		return list;
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

	
	/**
	 * 定时从vsmart中获取座位表中已离职的顾问人员信息
	 * @return
	 */
	public String timeTaskBatchClean(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-定时从vsmart中获取座位表中已离职的顾问人员信息");    
			List<String> list=getAllList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				result=cleanSeatMsg(list);
				Util.printLogger(logger,"指定任务-->定时从vsmart中获取座位表中已离职的顾问人员信息成功!");
			}else{
				Util.printLogger(logger,"指定任务-->从vsmart中获取座位表中已离职的顾问人员信息为空！");
				result = Util.getMsgJosnSuccessReturn("从vsmart中获取座位表中已离职的顾问人员信息为空！");
			} 
		}catch(Exception e){
			result=Util.joinException(e);
			Util.printLogger(logger,"指定任务-->定时从vsmart中获取座位表中已离职的顾问人员信息失败："+e.getMessage());
			
		}
		return result;
	}
	
	
	/**
	 * 远程获取座位表中已离职的人员编号
	 * @return
	 * @throws 
	 */
	public List<String> getAllList() throws Exception{
		Connection con = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		ResultSet rs = null;
		ResultSet rss = null;
		List<String> list=null;
		try{
			con=DBManager_sqlservler.getCon();
			conn = DBManager.getCon();
			String sql1="select DISTINCT staffcode from seat_list where `status` = 'Y' and staffcode != '' ";
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
			
			sql="SELECT distinct [ConsultantId] FROM [SZO_SYSTEM].[dbo].[vSZO_SOS_ConsultantInfo] where (Reason = 'TERMINATION' or Reason = 'RESIGNATION') ";
			if(!Util.objIsNULL(a)){
				sql+="and  ConsultantId in ("+a+")";
			}
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<String>();
			while(rs.next()){
				list.add(rs.getString("ConsultantId"));
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
	 * 
	 */
	public String cleanSeatMsg(List<String> list) throws SQLException{
		String result = "";
		int num = -1;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		SeatList seatList = null;
//		List<SeatList> tempList = null;
		try{
			super.openTransaction();
//			tempList = new ArrayList<SeatList>();
			for(int i=0;i<list.size();i++){
				String staffcode = list.get(i);
				seatList = super.find("select seatno,staffcode,staffname,extensionno,location,floor,deskDrawerno,lockerno, pigenBoxno,ifhidden,remark,remark1 from seat_list where staffcode=? and `status` = 'Y'", staffcode);
				if(!Util.objIsNULL(seatList)){
					sql1="update seat_list set staffcode=?, staffname=?, extensionno=? where seatno=? and `status` = 'Y' ";
					num = super.update2(sql1, "","","",seatList.getSeatno());
					if(num<0){
						throw new RuntimeException("清空座位表失败!");
					}
					//离职人员信息存入数据表
					sql3="insert into seat_leave (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,creater,createdate)values(?,?,?,?,?,?,?,?,?)";	
					num = super.saveEntity(sql3, seatList.getSeatno(),seatList.getStaffcode(),seatList.getStaffname(),seatList.getExtensionno(),seatList.getFloor(),seatList.getLocation(),seatList.getPigenBoxno(),"SYSTEM",DateUtils.getNowDateTime());
					if(num<1){
						throw new RuntimeException("保存离职人员记录信息失败.");
					}			
					//保存座位表操作记录
					sql2="insert into seat_operation (seatno,staffcode,staffname,extensionno,floor,location,pigenboxno,deskDrawerno,lockerno,ifhidden,ifad,operationname,operationdate,reason)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					num = super.saveEntity(sql2, seatList.getSeatno(),seatList.getStaffcode(),seatList.getStaffname(),seatList.getExtensionno(),seatList.getFloor(),seatList.getLocation(),seatList.getPigenBoxno(),seatList.getDeskDrawerno(),seatList.getLockerno(),seatList.getIfhidden(),seatList.getRemark(),"SYSTEM",DateUtils.getNowDateTime(),"已离职");
					if(num<1){
						throw new RuntimeException("保存座位表操作记录失败.");
					}
//					tempList.add(seatList);
				}	
			}	

/*			String to = Util.getProValue("public.email.notice.addressby.leavepeople.to");
			String cc = Util.getProValue("public.email.notice.addressby.leavepeople.cc");
			String content = "";
				
			//发送邮件提醒
	  		content+="Dear All,<br/>";
			content+="<br/>";
	  		content+="&nbsp;&nbsp;&nbsp;&nbsp;Please vacant seat,block line & remove Chicken box of the resigned Consultant,thanks.<br/>";
	  		content+="<br/>";		      		
	  		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
			+"        <table id='xx' cellpadding=0 cellspacing=0 >"
			+"<tr id='first' style='background-color:#9d9dff;color:white;'><td >Staff Code</td><td >Name</td><td >Location</td><td>Seat No.</td><td>Ext.#</td><td>Chicken Box</td></tr>";
	  		for(int i=0;i<tempList.size();i++){
				content+="<tr><td>"+tempList.get(i).getStaffcode()+"</td><td style='text-transform:capitalize;' >"+tempList.get(i).getStaffname()+"</td><td>"+tempList.get(i).getFloor()+" "+tempList.get(i).getLocation()+"</td><td>"+tempList.get(i).getSeatno()+"</td><td>"+tempList.get(i).getExtensionno()+"</td><td>"+tempList.get(i).getPigenBoxno()+"</td></tr>";      		
	  		}
			content+="</table><br/><br/>";	      		
	  		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
	  		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
			
			String mailResult=SendMail.send("COAT – Seat Change Request",to,cc,null,null,content,null,"email.ftl",null);
			JSONObject json=new JSONObject(mailResult);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}*/
			
			super.sumbitTransaction();
			result = Util.getMsgJosnObject_success();
			logger.info("clean seat_list success");
		}catch(Exception e){
			result = Util.joinException(e);
			super.rollbackTransaction();
		}finally{
			super.closeConnection(); 
		}
		return result;
	}
	
	
}
