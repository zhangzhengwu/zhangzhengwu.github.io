package com.coat.notice.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Page;
import util.Pager;

import cn.admin.entity.system.SGroup;

import com.coat.notice.entity.Notice;
import com.coat.notice.entity.NoticeType;

import entity.S_user;

/**
 * 
 * @author orlandozhang
 *
 */
public interface NoticeDao {

	/**
	 * 根据当前用户查询系统消息
	 * @param username
	 * @return
	 */
		public List<Notice> querybyUser(String username);
		
		/**
		 * 查询用户
		 * @return
		 */
		List<S_user> selectUser();
		/**
		 * 根据和用户名查询用户信息
		 * @param user
		 * @return
		 */
		List<S_user> qeuryByUser(String user);
		
		
		List<SGroup> selectGroup();
		
		
		
		/**
		 * 根据id查询一条消息的详细
		 * @param id
		 * @return
		 */
		public Notice queryByNoticeId(int id);
		
		/**
		 * 根据当前消息类型查询系统消息
		 * 带分页
		 * @param type
		 * @return
		 */
		public List<Notice> queryByNoticeType(String username,String noticetype, String subject, String company, String date1, String date2,Page page);
		
		
		public List<Notice> queryByNoticeType_Own(String username,String noticetype, String subject, String company, String date1, String date2,Page page) throws SQLException;
		
		/**
		 * 查询导出
		 * @return
		 */
		public List<Map<String,Object>> queryByNoticeType(String username,String noticetype, String subject, String company, String date1, String date2);
		
		public List<Map<String,Object>> queryByNoticeType_own(String username,String noticetype, String subject, String company, String date1, String date2);
		
		/**
		 *  获取总行数
		 * @param date1
		 * @param date2
		 * @return
		 */
		public int getRows(String username,String type,String subject,String company,String date1,String date2);
		
		public int getRows_Own(String username,String type,String subject,String company,String date1,String date2);
		
		/**
		 * 保存消息删除标记
		 * @param noticeid
		 * @param username
		 * @return
		 */
		public int saveNoticeDelete(String noticeid, String username);
		
		/**
		 * 保存消息已读标记
		 * @param noticeid
		 * @param username
		 * @return
		 */
		public int saveReadList(String noticeid, String username);
		
		/**
		 * 查询消息是否已读
		 * @param noticeid
		 * @param username
		 * @return
		 */
		public int queryIsRead(String noticeid, String username);
		
		public int queryNewNotice(String username)throws SQLException;
		
		/**
		 * 查询消息类型列表
		 * @return
		 */
		public List<NoticeType> queryTypeList();
		
		/**
		 * 保存发送消息
		 * @param type
		 * @param subject
		 * @param content
		 * @param attr
		 * @param recipient
		 * @param roles
		 * @param company
		 * @param startdate
		 * @param enddate
		 * @param creater
		 * @param createdate
		 * @param status
		 * @return
		 */
		public int saveNotice(String type, String subject, String content, String attr, String recipient, String roles, String company, String startdate, String enddate, String creator, String createdate, String status);
		
		
		Pager queryPickUpList_Own1(String adminUsername,String[] fields, Pager page,String noticetype,String date1,
				String date2,String subject,String company,String read) throws SQLException;
		
		Pager queryByNoticeType1(String adminUsername,String[] fields, Pager page,String noticetype,String date1,String date2,
				String subject,String company,String read) throws SQLException;
		
		List<Map<String,Object>> findNotice_Type(String adminUsername) throws SQLException;

		
}
