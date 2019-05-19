package cn.admin.dao.impl.system;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import cn.admin.dao.system.SystemMsgDao;
import cn.admin.entity.system.SystemMsg;

public class SystemMsgDaoImpl implements SystemMsgDao{
	 Connection con = null;
	 PreparedStatement ps = null;
	 Logger log = Logger.getLogger(SystemMsgDaoImpl.class);
	 ResultSet rs = null;

	/**
	 * 添加一个系统的消息记录
	 * 
	 * @author DavidLuo
	 * @param SystemMsg
	 *            msg
	 * @return 1 success. else failed!
	 */
	public int AddItem(SystemMsg msg) {
		String sql = "insert systemmsg (msgContent,msgEffectStartDate,msgEffectEndDate,msgCtime,isShow,msgType,msgEffectStartTime,msgEffectEndTime,msgEffectList) VALUES (?,?,?,NOW(),?,?,?,?,?);";
		int resultRow = 0;
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, msg.getMsgContent());
			Date startDate = Date.valueOf(msg.getMsgEffectStartDate());
			ps.setDate(2, startDate);
			Date endDate = Date.valueOf(msg.getMsgEffectEndDate());
			ps.setDate(3, endDate);
			ps.setInt(4, msg.getIsShow());
			ps.setInt(5, msg.getMsgType());
			Time startTime = Time.valueOf(msg.getMsgEffectStartTime());
			ps.setTime(6, startTime);
			Time endTime = Time.valueOf(msg.getMsgEffectEndTime());
			ps.setTime(7, endTime);
			ps.setString(8, msg.getMsgEffectList());
			resultRow = ps.executeUpdate();
		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);

		}
		return resultRow;

	}

	public String GetContentById(int id) {
		String Sql = "select msgContent from systemmsg where msgid = ?";
		String content = "";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(Sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				content = rs.getString("msgContent");
			}
		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);

		}
		return content;
	}

	/**
	 * 
	 * 查询所有的消息的总数
	 * 
	 * @author DavidLuo
	 * @return List<SystemMsg>
	 */
	public int GetAllCount() {
		int Count = 0;
		List<SystemMsg> rsList = new ArrayList<SystemMsg>();
		String Sql = "select count(*)as totalNum from systemmsg";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(Sql);
			rs = ps.executeQuery();
			rs.next();
			Count = rs.getInt("totalNum");

		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);

		}
		return Count;

	}

	/**
	 * 
	 * 查询所有的系统消息
	 * 
	 * @author DavidLuo
	 * @return List<SystemMsg>
	 */
	public List<SystemMsg> GetAll(int p, int size) {
		List<SystemMsg> rsList = new ArrayList<SystemMsg>();
		String Sql = "select * from systemmsg order by msgCtime asc limit "
				+ (p - 1) * size + "," + size + "";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(Sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				SystemMsg t = new SystemMsg();
				t.setIsShow(Integer.parseInt(rs.getString("isShow")));
				t.setMsgId(Integer.parseInt(rs.getString("msgid")));
				t.setMsgType(Integer.parseInt(rs.getString("msgType")));
				t.setMsgContent(rs.getString("msgContent"));
				t.setMsgCtime(rs.getString("msgCtime"));
				t.setMsgEffectStartDate(rs.getString("msgEffectStartDate"));
				t.setMsgEffectEndDate(rs.getString("msgEffectEndDate"));
				t.setMsgEffectStartTime(rs.getString("msgEffectStartTime"));
				t.setMsgEffectEndTime(rs.getString("msgEffectEndTime"));
				t.setMsgEffectList(rs.getString("msgEffectList"));
				t.setMsgContent(rs.getString("msgContent"));
				rsList.add(t);
			}
		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);

		}
		return rsList;

	}

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
	public int Update(int id, int msgType, int isShow, Time stime, Time etime,
			Date sdate, Date edate, String showWho, String content) {
		int resultRow = 0;
		String Sql = "update systemmsg set msgtype= ? ,isShow= ? ,msgEffectStartTime= ? ,msgEffectEndTime=?,msgEffectStartDate=?,msgEffectEndDate=?,msgEffectList=?,msgContent=? where msgid=?";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(Sql);
			ps.setInt(1, msgType);
			ps.setInt(2, isShow);
			ps.setTime(3, stime);
			ps.setTime(4, etime);
			ps.setDate(5, sdate);
			ps.setDate(6, edate);
			ps.setString(7, showWho);
			ps.setString(8, content);
			ps.setInt(9, id);
			resultRow = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return resultRow;
	}

	/**
	 * 删除指定ID的数据
	 * 
	 * @param idList
	 * @return
	 */
	public int DelItems(String[] idList) {
		String sql = "delete from systemmsg where msgid in (";
		int i = 0;
		int resultRow = 0;
		while (i < idList.length) {
			sql += "?";
			if (idList.length != i + 1) {
				sql += ",";
			}
			;
			i++;
		}
		sql += ");";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			i = 0;
			while (i < idList.length) {
				ps.setInt(i + 1, Integer.parseInt(idList[i]));
				i++;
			}
			resultRow = ps.executeUpdate();
		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);

		}

		return resultRow;
	}

	/**
	 * 获取登录人可以查看的数据列表
	 * 
	 * @param loginName
	 * @param rootNum
	 * @param showCount
	 * @return
	 */
	public List<SystemMsg> getCureentMsg(String loginName, String rootNum,
			int showCount) {
		String strSql = "";
		List<SystemMsg> msgList = new ArrayList<SystemMsg>();
		try {
			if (showCount != -1) {
				strSql = " limit 0," + showCount + "";
			}
			String Sql = "SELECT * from systemmsg WHERE"
					+ "  msgEffectStartDate<=DATE_FORMAT(NOW(),'%Y-%m-%d') and msgEffectEndDate>=DATE_FORMAT(NOW(),'%Y-%m-%d') and msgEffectStartTime <=DATE_FORMAT(NOW(),'%H:%i:%s')and msgEffectEndTime >=DATE_FORMAT(NOW(),'%H:%i:%s')"
					+ " and (msgType = 0 or (msgType =1 and FIND_IN_SET(?,msgEffectList)!=0) or (msgType =2 and FIND_IN_SET(?,msgEffectList))) and isShow=1 "
					+ strSql + ";";
			con = DBManager.getCon();
			ps = con.prepareStatement(Sql);
			ps.setString(1, rootNum);
			ps.setString(2, loginName);
			rs = ps.executeQuery();
			while (rs.next()) {
				SystemMsg t = new SystemMsg();
				t.setIsShow(Integer.parseInt(rs.getString("isShow")));
				t.setMsgId(Integer.parseInt(rs.getString("msgid")));
				t.setMsgType(Integer.parseInt(rs.getString("msgType")));
				t.setMsgContent(rs.getString("msgContent"));
				t.setMsgCtime(rs.getString("msgCtime"));
				t.setMsgEffectStartDate(rs.getString("msgEffectStartDate"));
				t.setMsgEffectEndDate(rs.getString("msgEffectEndDate"));
				t.setMsgEffectStartTime(rs.getString("msgEffectStartTime"));
				t.setMsgEffectEndTime(rs.getString("msgEffectEndTime"));
				// t.setMsgEffectList(rs.getString("msgEffectList"));
				t.setMsgContent(rs.getString("msgContent"));
				msgList.add(t);
			}
		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return msgList;
	}

	public int getCureentMsgCount(String loginName, String rootNum) {
		int msgCount = 0;
		try {

			String Sql = "SELECT count(msgid) as total from systemmsg WHERE"
					+ "  msgEffectStartDate<=DATE_FORMAT(NOW(),'%Y-%m-%d') and msgEffectEndDate>=DATE_FORMAT(NOW(),'%Y-%m-%d') and msgEffectStartTime <=DATE_FORMAT(NOW(),'%H:%i:%s')and msgEffectEndTime >=DATE_FORMAT(NOW(),'%H:%i:%s')"
					+ " and (msgType = 0 or (msgType =1 and FIND_IN_SET(?,msgEffectList)!=0) or (msgType =2 and FIND_IN_SET(?,msgEffectList))) and isShow=1;";
			con = DBManager.getCon();
			ps = con.prepareStatement(Sql);
			ps.setString(1, rootNum);
			ps.setString(2, loginName);
			rs = ps.executeQuery();
			rs.next();
			msgCount = Integer.parseInt(rs.getString("total"));
		} catch (Exception e) {
			// flag = false;
			e.printStackTrace();
			log.error("添加记录时候出现 ：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return msgCount;
	}
}
