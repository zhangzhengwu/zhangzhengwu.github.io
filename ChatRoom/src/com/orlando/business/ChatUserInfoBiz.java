package com.orlando.business;

import java.util.List;

import com.orlando.entity.ChatUserInfo;

 /** 
 * @ClassName: ChatUserInfoBiz 
 * @Description: ChatUserInfo 表对应的业务接口
 * @author: 章征武【orlando】
 * @date: 2018年10月8日 上午11:32:37 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
public interface ChatUserInfoBiz extends BaseBiz {


	
	/**
	 * @Title: findListByLoginMsg
	 * @Description: 登录验证
	 * @param @param password
	 * @param @param loginmsg
	 * @param @return    参数
	 * @return List<ChatUserInfo>    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 上午11:32:42  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public List<ChatUserInfo> findListByLoginMsg(String password,String loginmsg);
	
	
	
	/**
	 * @Title: updateChatUserInfoObj
	 * @Description: 更新 ChatUserInfo 
	 * @param @param chatUserInfo
	 * @param @return    参数
	 * @return boolean    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 上午11:36:20  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public boolean updateChatUserInfoObj(ChatUserInfo chatUserInfo);
	
	
	/**
	 * @Title: findUserIsLegal
	 * @Description: 判断登录名是否存在
	 * @param @param loginmsg
	 * @param @return    参数
	 * @return boolean    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 上午11:54:25  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public boolean findUserIsLegal(String loginmsg);
	
	/**
	 * @Title: findByUsername
	 * @Description: 通过username查询 ChatUserInfo 的 List
	 * @param @param username
	 * @param @return    参数
	 * @return List<ChatUserInfo>    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 下午2:41:14  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public List<ChatUserInfo> findByUsername(String username);
	
	
	/**
	 * @Title: findByUsernameEmailPhone
	 * @Description: 通过 username email phone 查询 ChatUserInfo 的 List
	 * @param @param username
	 * @param @param email
	 * @param @param phone
	 * @param @return    参数
	 * @return List<ChatUserInfo>    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 下午2:49:25  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public List<ChatUserInfo> findByUsernameEmailPhone(String username,String email,String phone);
	
	
	
	
	/**
	 * @Title: addChatUserInfoObj
	 * @Description: 新增 ChatUserInfo 
	 * @param @param chatUserInfo
	 * @param @return    参数
	 * @return boolean    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 下午2:51:18  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public boolean addChatUserInfoObj(ChatUserInfo chatUserInfo);
	
	
	
	/**
	 * @Title: getChatUserIdByUserName
	 * @Description: 根据用户名获取用户id
	 * @param @param username
	 * @param @return    参数
	 * @return int    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月9日 上午9:46:48  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public int getChatUserIdByUserName(String username);
	
	
	
	
}
