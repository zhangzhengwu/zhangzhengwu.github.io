package com.orlando.business.impl;

import java.util.List;

import com.orlando.business.ChatUserInfoBiz;
import com.orlando.entity.ChatUserInfo;
import com.orlando.mysql.superdao.SuperDao;
import com.orlando.mysql.superdao.impl.SuperDaoImpl;

public class ChatUserInfoBizImpl implements ChatUserInfoBiz{

	private SuperDao<ChatUserInfo> userDao = new SuperDaoImpl<>(ChatUserInfo.class);

	@Override
	public List<ChatUserInfo> findListByLoginMsg(String password,String loginmsg){
		String sql = "select * from tb_user_info where  u_pwd = ? and ( u_name=? or u_phone=? or u_email=? );";
		return userDao.findBySQL(sql, password,loginmsg,loginmsg,loginmsg);
	}
	
	@Override
	public boolean updateChatUserInfoObj(ChatUserInfo chatUserInfo){
		return userDao.merge(chatUserInfo);
	}
	
	
	@Override
	public boolean findUserIsLegal(String loginmsg){
		String sql = "select * from tb_user_info where u_name=? or u_phone=? or u_email=?;";
		List<ChatUserInfo> list = userDao.findBySQL(sql,loginmsg,loginmsg,loginmsg);
		if(list.size()>0){
			return true;
		}
		return false;
	}


	@Override
	public List<ChatUserInfo> findByUsername(String username){
		String sql = "select * from tb_user_info where u_name=?;";
		return userDao.findBySQL(sql, username);
		
	}
	
	@Override
	public List<ChatUserInfo> findByUsernameEmailPhone(String username,String email,String phone){
		String sql = "select * from tb_user_info where u_name=? or u_email=? or u_phone=?; ";
		return userDao.findBySQL(sql, username,email,phone);
	}

	@Override
	public boolean addChatUserInfoObj(ChatUserInfo chatUserInfo) {
		return userDao.add(chatUserInfo);
	}

	@Override
	public void closeConnection() {
		userDao.closeConnection();
	}
	
	@Override
	public int getChatUserIdByUserName(String username){
		String sql = "select u_id from tb_user_info where u_name=?";
		List<ChatUserInfo> list = userDao.findBySQL(sql, username);
		return list.get(0).getUserId().intValue();
	}
	
	



	
	

}
