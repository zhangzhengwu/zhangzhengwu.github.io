package com.orlando.business;

import java.util.List;

import com.orlando.entity.ChatMessageInfoForName;

 /** 
 * @ClassName: ChatMessageInfoBiz 
 * @Description: ChatMessageInfoBiz 业务接口
 * @author: 章征武【orlando】
 * @date: 2018年10月8日 下午3:27:48 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
public interface ChatMessageInfoBiz extends BaseBiz {
	
	/**
	 * @Title: showChatMessage
	 * @Description: 展示对话列表
	 * @param @param uid
	 * @param @return    参数
	 * @return List<ChatMessageInfoForName>    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月9日 上午9:59:58  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public List<ChatMessageInfoForName> showChatMessage(int uid);
	
	/**
	 * @Title: showAllChatMessage
	 * @Description: 显示所有聊天记录
	 * @param @return    参数
	 * @return List<ChatMessageInfoForName>    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月11日 下午4:13:15  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public List<ChatMessageInfoForName> showAllChatMessage();
	/**
	 * @Title: addChatMessageInfo
	 * @Description: 增加对话记录(给某一用户)
	 * @param @param sendcontent
	 * @param @param uid
	 * @param @param receiverid
	 * @param @return    参数
	 * @return boolean    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月9日 上午10:06:21  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public boolean addChatMessageInfo(String sendcontent, int uid, int receiverid);
	
	
	/**
	 * @Title: addChatMessageInfo
	 * @Description: 增加对话记录(给所有用户)
	 * @param @param sendcontent
	 * @param @param uid
	 * @param @return    参数
	 * @return boolean    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月9日 上午10:18:26  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public boolean addChatMessageInfo(String sendcontent, int uid);
	
}
