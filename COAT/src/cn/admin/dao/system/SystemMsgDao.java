package cn.admin.dao.system;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import org.apache.log4j.Logger;

import cn.admin.entity.system.SystemMsg;


import java.util.List;
import java.util.ArrayList;

/*
 * 系统消息数据库交互
 */
public interface SystemMsgDao {

	/**
	 * 添加一个系统的消息记录
	 * 
	 * @author DavidLuo
	 * @param SystemMsg
	 *            msg
	 * @return 1 success. else failed!
	 */
	 int AddItem(SystemMsg msg);
	

	String GetContentById(int id);
	/**
	 * 
	 * 查询所有的消息的总数
	 * 
	 * @author DavidLuo
	 * @return List<SystemMsg>
	 */
	int GetAllCount();

	/**
	 * 
	 * 查询所有的系统消息
	 * 
	 * @author DavidLuo
	 * @return List<SystemMsg>
	 */
	List<SystemMsg> GetAll(int p, int size) ;

	/**
	 * 更新数据。
	 * 
	 * @param id
	 * @param msgType
	 * @param isShow
	 * @param stime
	 * @param etime
	 * @param sdate
	 * @param edate
	 * @param showWho
	 * @param content
	 * @return
	 */
	int Update(int id, int msgType, int isShow, Time stime, Time etime,
			Date sdate, Date edate, String showWho, String content) ;

	/**
	 * 删除指定ID的数据
	 * 
	 * @param idList
	 * @return
	 */
	int DelItems(String[] idList) ;

	/**
	 * 获取登录人可以查看的数据列表
	 * 
	 * @param loginName
	 * @param rootNum
	 * @param showCount
	 * @return
	 */
	List<SystemMsg> getCureentMsg(String loginName, String rootNum,
			int showCount);

	int getCureentMsgCount(String loginName, String rootNum) ;
}
