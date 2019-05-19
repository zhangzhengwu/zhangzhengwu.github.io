package com.coat.notice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Pager;
import util.Util;

import cn.admin.entity.system.SGroup;

import com.coat.notice.dao.NoticeDao;
import com.coat.notice.entity.Notice;
import com.coat.notice.entity.NoticeType;

import dao.common.BaseDao;
import entity.S_user;

/**
 * 
 * @author orlandozhang
 *
 */
public class NoticeDaoImpl extends BaseDao implements NoticeDao{
	
	PreparedStatement ps = null;
	Connection con = null;
	Logger logger = Logger.getLogger(NoticeDaoImpl.class);
	
	public List<Notice> querybyUser(String username) {
		List<Notice>  list = new ArrayList<Notice>();
		try {
			StringBuffer sql = new StringBuffer("select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate, " + 
					" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," + 
					" case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' where rl.username is null and n.status = 'Y' and nd.isdelete is null  and " + 
					" (n.type = 'All' OR (n.type = 'Group' and (n.recipient like '"+username+"%' or n.recipient like '%"+username+"%' or " + 
					" n.recipient like '%"+username+"')) OR (n.type = 'Personal' and (n.recipient like '"+username+"%' or " + 
					" n.recipient like '%"+username+"%' or n.recipient like '%"+username+"')))");

/*			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}*/		
			sql.append("  ORDER BY n.createdate desc ");
			logger.info("查询  sql:===="+sql.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				Notice nt = new Notice(
						rs.getInt("id"),
						rs.getString("type"),
						rs.getString("subject"),
						rs.getString("content"),
						rs.getString("attr"),
						rs.getString("recipient"),
						rs.getString("roles"),
						rs.getString("company"),
						rs.getString("startdate"),
						rs.getString("enddate"),
						rs.getString("creator"),
						rs.getString("createdate"),
						rs.getString("status"),
						rs.getString("type1"),
						rs.getString("noticeid"),
						rs.getString("username"),
						rs.getString("ifread")
						);
				list.add(nt);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public Notice queryByNoticeId(int id){
		Notice noticeObj = new Notice();
		try {
			con = DBManager.getCon();
			String sqlString ="select * from notice where status ='Y' and id ='"+id+"'";
			logger.info("根据id查询 SQL:"+sqlString);
			ps = con.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				noticeObj.setId(rs.getInt("id"));
				noticeObj.setType(rs.getString("type"));
				noticeObj.setSubject(rs.getString("subject"));
				noticeObj.setContent(rs.getString("content"));
				noticeObj.setAttr(rs.getString("attr"));
				noticeObj.setRecipient(rs.getString("recipient"));
				noticeObj.setRoles(rs.getString("roles"));
				noticeObj.setCompany(rs.getString("company"));
				noticeObj.setStartdate(rs.getString("startdate"));
				noticeObj.setEnddate(rs.getString("enddate"));
				noticeObj.setCreator(rs.getString("creator"));
				noticeObj.setCreatedate(rs.getString("createdate"));
				noticeObj.setStatus(rs.getString("status"));
				
				String groupName=findGroupName(rs.getString("roles"));
				noticeObj.setRemark4(groupName);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("根据名称查询notice异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("根据名称查询notice异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return noticeObj;
	}
	
	public String findGroupName(String id) throws SQLException{
		String result="";
		try {
			String ids[]=id.split(",");
			for (int i = 0; i < ids.length; i++) {
				String sql="select groupname from s_group where groupid=?  ";
				Map<String,Object> map=findMap(sql,ids[i]);
				if(!Util.objIsNULL(map)&&map.size()>0){
					if(result==""){
						result+=map.get("groupname");
					}else{
						result+=","+map.get("groupname");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return result;
	}
	
	
	public List<Notice> queryByNoticeType(String username,String noticetype, String subject, String company, String date1, String date2, Page page){
		List<Notice>  list = new ArrayList<Notice>();
		try {
			StringBuffer sql = new StringBuffer("select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate, " + 
					" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," + 
					" case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+noticetype+"%' and n.status = 'Y' ");
			/*if(!"All".equals(noticetype)){
				sql.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}*/
			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}		
			if(!Util.objIsNULL(subject)){
				sql.append(" and n.subject like '%"+subject+"%' ");
			}			
			if(!Util.objIsNULL(company)){
				sql.append(" and n.company like '%"+company+"%' ");
			}	
			
			sql.append("  ORDER BY n.createdate desc ");
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			//System.out.println("sql-->"+sql.toString());
			logger.info("查询  sql:===="+sql.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				Notice nt = new Notice(
						rs.getInt("id"),
						rs.getString("type"),
						rs.getString("subject"),
						rs.getString("content"),
						rs.getString("attr"),
						rs.getString("recipient"),
						rs.getString("roles"),
						rs.getString("company"),
						rs.getString("startdate"),
						rs.getString("enddate"),
						rs.getString("creator"),
						rs.getString("createdate"),
						rs.getString("status"),
						rs.getString("ifread")
						);
				list.add(nt);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return list;	
	}
	
	
	
	public List<Notice> queryByNoticeType_Own(String username,String noticetype, String subject, String company, String date1, String date2, Page page) throws SQLException{
		List<Notice>  list = new ArrayList<Notice>();
		try {
			StringBuffer sql = new StringBuffer("select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate, " + 
					" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," + 
					" case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+noticetype+"%' and n.status = 'Y' ");
			if(!"All".equals(noticetype)){
				sql.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}
			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}		
			if(!Util.objIsNULL(subject)){
				sql.append(" and n.subject like '%"+subject+"%' ");
			}			
			if(!Util.objIsNULL(company)){
				sql.append(" and n.company like '%"+company+"%' ");
			}	
			sql.append("  ORDER BY n.createdate desc ");
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			//System.out.println("sql-->"+sql.toString());
			logger.info("查询  sql:===="+sql.toString());
		 
			List<Map<String, Object>> map=  super.listMap(sql.toString());
			//super.findPager(fields, sql, limit, page, objects)
			
			for(int i=0;i<map.size();i++){
				Map<String,Object> m=map.get(i);
				Notice nt = new Notice(
						Integer.parseInt(m.get("id")+""),
						m.get("type")+"",
						m.get("subject")+"",
						m.get("content")+"",
						m.get("attr")+"",
						m.get("recipient")+"",
						m.get("roles")+"",
						m.get("company")+"",
						m.get("startdate")+"",
						m.get("enddate")+"",
						m.get("creator")+"",
						m.get("createdate")+"",
						m.get("status")+"",
						m.get("ifread")+""
						);
				list.add(nt);
			}
		/*	ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				Notice nt = new Notice(
						rs.getInt("id"),
						rs.getString("type"),
						rs.getString("subject"),
						rs.getString("content"),
						rs.getString("attr"),
						rs.getString("recipient"),
						rs.getString("roles"),
						rs.getString("company"),
						rs.getString("startdate"),
						rs.getString("enddate"),
						rs.getString("creator"),
						rs.getString("createdate"),
						rs.getString("status"),
						rs.getString("ifread")
						);
				list.add(nt);
			}
			rs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			super.closeConnection();
		}
		return list;	
	}

	public Pager queryPickUpList_Own1(String username,String[] fields, Pager page,String noticetype,String date1,String date2,
			String subject,String company,String read) throws SQLException{
		Pager pager=null;
		try {
			StringBuffer sql = new StringBuffer(" from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+noticetype+"%' and n.status = 'Y' ");
			if(!"All".equals(noticetype)){
				sql.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}
			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}		
			if(!Util.objIsNULL(subject)){
				sql.append(" and n.subject like '%"+subject+"%' ");
			}			
			if(!Util.objIsNULL(company)){
				sql.append(" and n.company like '%"+company+"%' ");
			}	
			if(!Util.objIsNULL(read)){
				if(read.equalsIgnoreCase("Y")){
					sql.append("and  rl.username is not null  ");
				}else{
					sql.append("and  rl.username is null  ");
				}
			}
			logger.info("查询  sql:===="+sql.toString());
			String limit="  ORDER BY n.createdate desc limit ?,? ";
			String[] fileds=("n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate,"+ 
			 "n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username,"+
			 " rl.username as ifread ").split(",");
			pager=super.findPager(fileds, sql.toString(), limit, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			super.closeConnection();
		}
		return pager;	
	}
	
	public Pager queryByNoticeType1(String username,String[] fields, Pager page,String noticetype,String date1,String date2,String subject,String company,String read) throws SQLException{
		Pager pager=null;
		try {
			StringBuffer sql = new StringBuffer(" from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"'" +
					" LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"'" +
					" where nd.isdelete is null and n.type like '%"+noticetype+"%' and n.status = 'Y' ");
			/*if(!"All".equals(noticetype)){
				sql.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}*/
			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}		
			if(!Util.objIsNULL(subject)){
				sql.append(" and n.subject like '%"+subject+"%' ");
			}			
			if(!Util.objIsNULL(company)){
				sql.append(" and n.company like '%"+company+"%' ");
			}
			if(!Util.objIsNULL(read)){
				if(read.equalsIgnoreCase("Y")){
					sql.append("and  rl.username is not null  ");
				}else{
					sql.append("and  rl.username is null  ");
				}
			}
			logger.info("查询  sql:===="+sql.toString());
			String limit="  ORDER BY n.createdate desc limit ?,? ";
			String[] fileds=("n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate,"+ 
			 "n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username,"+
			 " rl.username as ifread ").split(",");
			pager=super.findPager(fileds, sql.toString(), limit, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			super.closeConnection();
		}
		return pager;	
	}
	
	public List<Map<String,Object>> findNotice_Type(String username) throws SQLException{
		List<Map<String,Object>> lists=null;
		try {
			StringBuffer sql = new StringBuffer("select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate, " + 
					" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," + 
					" case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"'" +
					" LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null" +
					" and n.type like '%%' and n.status = 'Y' and rl.username is null ");
			sql.append("  ORDER BY n.createdate desc ");
			lists=listMap(sql+"");
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			super.closeConnection();
		}
		return lists;	
	}
	
	
	public List<Map<String,Object>> queryByNoticeType(String username,String noticetype, String subject, String company, String date1, String date2){
		//List<Notice>  list = new ArrayList<Notice>();
		List<Map<String,Object>> lists=null;
		try {
			StringBuffer sql = new StringBuffer("select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate, " + 
					" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," + 
					" case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+noticetype+"%' and n.status = 'Y' ");
			/*if(!"All".equals(noticetype)){
				sql.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}*/
			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}		
			if(!Util.objIsNULL(subject)){
				sql.append(" and n.subject like '%"+subject+"%' ");
			}			
			if(!Util.objIsNULL(company)){
				sql.append(" and n.company like '%"+company+"%' ");
			}	
			sql.append("  ORDER BY n.createdate desc ");
			
			lists=listMap(sql+"");
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return lists;	
	}
	
	
	public List<Map<String,Object>> queryByNoticeType_own(String username,String noticetype, String subject, String company, String date1, String date2){
		List<Map<String,Object>> lists=null;
		try {
			StringBuffer sql = new StringBuffer("select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate, " + 
					" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," + 
					" case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+noticetype+"%' and n.status = 'Y' ");
			if(!"All".equals(noticetype)){
				sql.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}
			if(!Util.objIsNULL(date1)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') >=date_format('"+date1+"','%Y-%m-%d')");
			}
			if(!Util.objIsNULL(date2)){
				sql.append(" and  date_format(n.createdate,'%Y-%m-%d') <=date_format('"+date2+"','%Y-%m-%d')");
			}		
			if(!Util.objIsNULL(subject)){
				sql.append(" and n.subject like '%"+subject+"%' ");
			}			
			if(!Util.objIsNULL(company)){
				sql.append(" and n.company like '%"+company+"%' ");
			}	
			sql.append("  ORDER BY n.createdate desc ");
			
			lists=listMap(sql+"");
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Notice 时出现 ："+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return lists;	
	}
	
	public int getRows(String username,String type,String subject,String company,String date1,String date2){
		int num=-1;
		try {
			con = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+type+"%' and n.status = 'Y' ");
			/*if(!"All".equals(type)){
				sqlString.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}*/
			if(!Util.objIsNULL(date1)){
				sqlString.append(" and  date_format(n.createdate,'%Y-%m-%d') >= date_format('"+date1+"','%Y-%m-%d') ");
			 
			}
			if(!Util.objIsNULL(date2)){
				sqlString.append(" and  date_format(n.createdate,'%Y-%m-%d') <= date_format('"+date2+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(subject)){
				sqlString.append(" and n.subject like '%"+subject+"%' ");
			}
			if(!Util.objIsNULL(company)){
				sqlString.append(" and n.company like '%"+company+"%' ");
			}

			logger.info("SQL:"+sqlString.toString());
			ps = con.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询时出现 ："+e);
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		
		return num;
	}
	
	public int getRows_Own(String username,String type,String subject,String company,String date1,String date2){
		int num=-1;
		try {
			con = DBManager.getCon();
			StringBuffer sqlString = new StringBuffer(" select count(*) as Num FROM notice n LEFT JOIN noticetype nt " + 
					" on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id and nd.username = '"+username+"' LEFT JOIN readlist rl on rl.noticeid = n.id and rl.username = '"+username+"' where nd.isdelete is null and n.type like '%"+type+"%' and n.status = 'Y' ");
			if(!"All".equals(type)){
				sqlString.append(" and ((n.recipient = '"+username+"' or n.recipient like '"+username+",%' or n.recipient like '%,"+username+",%' or n.recipient like '%,"+username+"') OR (n.roles = '1' or n.roles like '1,%' or n.roles like '%,1,%' or n.roles like '%,1'))");
			}
			if(!Util.objIsNULL(date1)){
				sqlString.append(" and  date_format(n.createdate,'%Y-%m-%d') >= date_format('"+date1+"','%Y-%m-%d') ");
			 
			}
			if(!Util.objIsNULL(date2)){
				sqlString.append(" and  date_format(n.createdate,'%Y-%m-%d') <= date_format('"+date2+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(subject)){
				sqlString.append(" and n.subject like '%"+subject+"%' ");
			}
			if(!Util.objIsNULL(company)){
				sqlString.append(" and n.company like '%"+company+"%' ");
			}

			logger.info("SQL:"+sqlString.toString());
			ps = con.prepareStatement(sqlString.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}
			
			
			
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询时出现 ："+e);
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		
		return num;
	}
	
	public int saveNoticeDelete(String noticeid, String username){
		int num = 0;
		try {
			con = DBManager.getCon();
			String sql = "insert noticedelete (isdelete,username,noticeid) values ('Y','"+username+"','"+noticeid+"')"; 
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			num=1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public int saveReadList(String noticeid, String username){
		int num = 0;
		try {
			con = DBManager.getCon();
			String sql = "insert readlist (username,noticeid) values ('"+username+"','"+noticeid+"')"; 
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			num=1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int queryIsRead(String noticeid, String username){
		int num=-1;
		 try{
			 con = DBManager.getCon();
			 String sql = "select * from readlist where noticeid='"+noticeid+"' and username = '"+username+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					num=1;
				}else{
					num=0;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询readlist表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return num;
	}
	

	public int queryNewNotice(String username) throws SQLException{
		int num=-1;
		 try{
			 String sql= "select n.id,n.type,n.subject,n.content,n.attr,n.recipient,n.roles,n.company,n.startdate," +
			 		" n.enddate,n.creator,n.createdate,n.status,nt.type as type1,rl.noticeid,rl.username," +
			 		"case when rl.username is not null then '已读' when rl.username is null then '未读' end ifread from" +
			 		" notice n LEFT JOIN noticetype nt on n.type = nt.type LEFT JOIN noticedelete nd on nd.noticeid = n.id" +
			 		" and nd.username = ? LEFT JOIN readlist rl on rl.noticeid = n.id" +
			 		" and rl.username = ? where nd.isdelete is null  and n.status = 'Y'" +
			 		" and rl.username is null ORDER BY createdate DESC limit 0,1";
			
			 Map<String,Object> map=findMap(sql,username,username); 
			 if(map.size()>0){
				 num=Integer.parseInt(map.get("id")+"");
			 }
		 }catch(Exception e){
			 logger.error("查询未读消息异常");
		 }finally{
			 super.closeConnection();
		 }
			return num;
	}
	
	
	
	
	public List<NoticeType> queryTypeList(){
		List<NoticeType>  list = new ArrayList<NoticeType>();
		try {
			StringBuffer sql = new StringBuffer("select type from noticetype ");
			con=DBManager.getCon();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				NoticeType nt = new NoticeType(
						rs.getString("type")
						);
				list.add(nt);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 noticetype 时出现 ："+e.toString());
		} finally {
			DBManager.closeCon(con);
		}
		return list;
		
	}
	
	public int saveNotice(String type, String subject, String content, String attr, String recipient, String roles, String company, String startdate, String enddate, String creator, String createdate, String status){
		int num = 0;
		try {
			con = DBManager.getCon();
			String sql = "insert notice (type, subject, content, attr, recipient, roles, company, startdate, enddate, creator, createdate, status) values ('"+type+"','"+subject+"','"+content+"','"+attr+"','"+recipient+"','"+roles+"','"+company+"','"+startdate+"','"+enddate+"','"+creator+"','"+createdate+"','"+status+"')"; 
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			num=1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		
		return (T) super.zhuanhuan(rs, Notice.class);
	}
	
	
	public List<S_user> selectUser(){
		List<S_user> userList=new ArrayList<S_user>();
		try {
			String sql = "select loginname from s_user "; 
			
			List<Map<String,Object>> map=findListMap(sql);
			
			if(!Util.objIsNULL(map)&&map.size()>0){
				for (int i = 0; i < map.size(); i++) {
					userList.add(new S_user(
						Util.objIsNULL(map.get(i).get("loginname"))?"":map.get(i).get("loginname").toString()
					));
				}
			}
			
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据名称查询S_user异常！"+e);
		}finally{
		}
		return userList;
	}

	public List<SGroup> selectGroup() {
		List<SGroup> s_group=new ArrayList<SGroup>();
		try {
			String sql = "select groupid,groupcode,groupname from s_group "; 
			List<Map<String,Object>> map=findListMap(sql);
			if(!Util.objIsNULL(map)&&map.size()>0){
				for (int i = 0; i < map.size(); i++) {
					s_group.add(new SGroup(
						Integer.parseInt(map.get(i).get("groupid")+""),
						Util.objIsNULL(map.get(i).get("groupcode"))?"":map.get(i).get("groupcode").toString(),
						Util.objIsNULL(map.get(i).get("groupname"))?"":map.get(i).get("groupname").toString()
					));
				}
			}
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据名称查询s_group异常！"+e);
		}finally{
		}
		return s_group;
	}
	
	
	
	public List<S_user> qeuryByUser(String user){
		List<S_user> userList=new ArrayList<S_user>();
		try {
			String sql = "select loginname from s_user where loginname like '%"+user+"%' "; 
			List<Map<String,Object>> map=findListMap(sql);
			
			if(!Util.objIsNULL(map)&&map.size()>0){
				for (int i = 0; i < map.size(); i++) {
					userList.add(new S_user(
						Util.objIsNULL(map.get(i).get("loginname"))?"":map.get(i).get("loginname").toString()
					));
				}
			}
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据名称查询S_user异常！"+e);
		}finally{
		}
		return userList;
	}
	
}
