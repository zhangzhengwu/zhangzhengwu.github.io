package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.SendMail;
import util.Util;
import dao.common.BaseDao;
import entity.SeatLeave;

public class SeatCleanDaoImpl extends BaseDao {

	Logger logger = Logger.getLogger(SeatCleanDaoImpl.class);
	
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, SeatLeave.class);
	}
	
	
	/**
	 * 定时每天下午6点，发送离职人员座位号清空邮件至指定人员
	 * @return
	 */
	public String timeTaskEmailForQuitPeopleBySeatClean(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-发送因离职清空座位号的顾问信息邮件");    
			List<SeatLeave> list=getIntradayLeavePeopleList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				sendEmail(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->发送因离职清空座位号的顾问信息邮件成功!");
			}else{
				throw new Exception("发送因离职清空座位号的顾问信息邮件信息为空！");
			} 
			
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->发送因离职清空座位号的顾问信息邮件失败："+e.getMessage());
			
		}
		return result;
	}	
	
	public List<SeatLeave> getIntradayLeavePeopleList() throws Exception{
		String sql = null;
		List<SeatLeave> listMsg=null;
		try{
			super.getConnction();
			sql="select seatno,staffcode,staffname,extensionno,floor,location,pigenboxno from seat_leave where year(createdate)=year(now()) and month(createdate)=month(now()) and day(createdate)=day(now())";
			listMsg= super.list(sql,null);
		}catch(Exception e){
			logger.error("获取当天因离职清空座位号的顾问信息时，网络连接异常");
			throw new Exception(e);
		}finally{
			super.closeConnection();
		}
		return listMsg;
	}
	
	public String sendEmail(List<SeatLeave> list) throws Exception{
		String result = "";
		String to = Util.getProValue("public.email.notice.addressby.leavepeople.to");
		String cc = Util.getProValue("public.email.notice.addressby.leavepeople.cc");
		String content = "";
			
		//发送邮件提醒
  		content+="Dear All,<br/>";
		content+="<br/>";
  		content+="&nbsp;&nbsp;&nbsp;&nbsp;Per the captioned, below resigned colleague’s seat info. for action. Thanks.<br/>";
  		content+="<br/>";		      		
  		content+="<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
		+"        <table id='xx' cellpadding=0 cellspacing=0 >"
		+"<tr id='first' style='background-color:#9d9dff;color:white;'><td >Staff Code</td><td >Name</td><td >Location</td><td>Seat No.</td><td>Ext.#</td><td>Chicken Box</td></tr>";
  		for(int i=0;i<list.size();i++){
			content+="<tr><td>"+list.get(i).getStaffcode()+"</td><td style='text-transform:capitalize;' >"+list.get(i).getStaffname()+"</td><td>"+list.get(i).getFloor()+" "+list.get(i).getLocation()+"</td><td>"+list.get(i).getSeatno()+"</td><td>"+list.get(i).getExtensionno()+"</td><td>"+list.get(i).getPigenboxno()+"</td></tr>";      		
  		}
		content+="</table><br/><br/>";	      		
  		content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";     
  		content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
		
		result=SendMail.send("Please vacant seat, block line & remove Chicken box of the resigned Consultant. Thanks.",to,cc,null,null,content,null,"email.ftl",null);
		JSONObject json=new JSONObject(result);
		if(json.get("state")=="error"){
			throw new RuntimeException((String)json.get("msg"));
		}
		return result;
	}	
	
	

}
