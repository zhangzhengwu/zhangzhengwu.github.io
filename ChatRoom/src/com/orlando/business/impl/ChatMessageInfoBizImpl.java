package com.orlando.business.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orlando.business.ChatMessageInfoBiz;
import com.orlando.entity.ChatMessageInfo;
import com.orlando.entity.ChatMessageInfoForName;
import com.orlando.mysql.superdao.SuperDao;
import com.orlando.mysql.superdao.impl.SuperDaoImpl;
import com.orlando.mysql.superdao.util.DbUtil;

public class ChatMessageInfoBizImpl implements ChatMessageInfoBiz {

	private SuperDao<ChatMessageInfo> userDao = new SuperDaoImpl<>(ChatMessageInfo.class);
	
	@Override
	public List<ChatMessageInfoForName> showChatMessage(int uid){
		DbUtil db = new DbUtil();
		String sql = null;
		ResultSet rs = null;
		try{
			sql = "select (select u_name from tb_user_info where u_id = c_send_id) as sendname,c_send_content,c_send_time,(select u_name from tb_user_info where u_id = c_receiver_id) as receivername from tb_chat_message where c_receiver_id=? or c_send_id=? or c_receiver_id is null ORDER BY c_send_time;";
			rs = db.doQuery(sql, uid, uid);
			List<ChatMessageInfoForName> list = new ArrayList<>();
			ChatMessageInfoForName info = null;
			rs.beforeFirst();
			while(rs.next()){
				info = new ChatMessageInfoForName();
				info.setSendName(rs.getString("sendname"));
				info.setSendContent(rs.getString("c_send_content"));
				info.setSendTime(new Date(rs.getDate("c_send_time").getTime()));
				info.setReceiverName(rs.getString("receivername"));
				list.add(info);
			}
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void closeConnection() {
		userDao.closeConnection();
	}


	@Override
	public boolean addChatMessageInfo(String sendcontent, int uid, int receiverid) {
		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.setSendContent(sendcontent);
		chatMessageInfo.setSendTime(new Date(System.currentTimeMillis()));
		chatMessageInfo.setSendId(uid);
		chatMessageInfo.setReceiverId(receiverid);
		return userDao.add(chatMessageInfo);
	}
	
	@Override
	public boolean addChatMessageInfo(String sendcontent, int uid) {
		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.setSendContent(sendcontent);
		chatMessageInfo.setSendTime(new Date(System.currentTimeMillis()));
		chatMessageInfo.setSendId(uid);
		return userDao.add(chatMessageInfo);
	}


	@Override
	public List<ChatMessageInfoForName> showAllChatMessage() {
		DbUtil db = new DbUtil();
		String sql = null;
		ResultSet rs = null;
		try{
			sql = "select (select u_name from tb_user_info where u_id = c_send_id) as sendname,c_send_content,c_send_time,(select u_name from tb_user_info where u_id = c_receiver_id) as receivername from tb_chat_message ORDER BY c_send_time;";
			rs = db.doQuery(sql);
			List<ChatMessageInfoForName> list = new ArrayList<>();
			ChatMessageInfoForName info = null;
			rs.beforeFirst();
			while(rs.next()){
				info = new ChatMessageInfoForName();
				info.setSendName(rs.getString("sendname"));
				info.setSendContent(rs.getString("c_send_content"));
				info.setSendTime((new java.util.Date(rs.getDate("c_send_time").getTime())));
				info.setReceiverName(rs.getString("receivername"));
				list.add(info);
			}
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
