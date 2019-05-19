package dao;

import java.sql.SQLException;
import java.util.List;

import util.Pager;
import entity.ConsList;
import entity.SeatAutochangeApply;
import entity.SeatList;


public interface SeatAutoDao {

	/**
	 * 更加seatno查询顾问的座位相关信息
	 * @param seatno
	 * @return
	 * @throws SQLException
	 */
	SeatList getSeatListBySeatNo(String seatno) throws SQLException;
	
	/**
	 * 分页查询系统自动座位交换申请历史列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager findSeatAutoChangeApplyListByStaffcode(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * 分页查询系统自动座位交换申请历史列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager findSeatAutoChangeApplyListAll(String[] fields, Pager page, Object... objects) throws Exception;
	
	
	/**
	 * 通过流水号查询详细信息
	 * @param refno
	 * @return
	 */
	SeatAutochangeApply queryListByRefno(String refno);
	
	/**
	 * 通过用户输入的staffcode 查询该用户座位当前座位相关信息
	 * @param staffcode
	 * @return
	 */
	SeatList querySeatNoBefore(String staffcode);
	/**
	 * 通过seatno 查询该用户座位当前座位相关信息
	 * @param staffcode
	 * @return
	 */
	SeatList getSeatMsgBySeatNo(String seatno);
	/**
	 * 通过staffcode 查询顾问相关信息
	 * 
	 * @param staffcode
	 * @return
	 */
	ConsList queryConsMsg(String staffcode);
	
	/**
	 * 通过用户输入的staffcode 查询所有的合法的空座位编号
	 * @param staffcode
	 * @return
	 */
	List<String> queryAllLegalSeatNo(int flag,String staffcode);
	
	
	/**
	 * 根据code查询Email
	 * @param str
	 * @return
	 */
	public String[] getEmailByCode(String [] str);
	
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 */
	int updateApply(SeatAutochangeApply seatAutochangeApply,SeatList seatList,SeatList seatlistbefore,String userType,int flag,String name,String staffname,String leadername,String to,String cc,String leaderLocation,String leaderFloor);
	
	/**
	 * 领导拒绝该换位申请
	 * @param seatChangeApply
	 * @return
	 */
	int LeaderRefusedApply(SeatAutochangeApply seatAutochangeApply,int flag,String name);
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 * @throws SQLException 
	 */
	int updateApplyForADM(SeatAutochangeApply seatAutochangeApply,SeatList seatList,SeatList seatlistbefore,String userType,int flag,String name,String staffname,String leadername,String seatnobefore,String to,String cc) throws SQLException;

	public  String getLocationByStaffcode(String staffcode);
	public  String getFloorByStaffcode(String staffcode);

	/**
	 * 通过Leader的座位号查询和领导座位首字母相同的座位号
	 * @param leadercode
	 * @return
	 */
	public List<String> getSeatnoListByLeaderSeatnoLetter(String leadercode);
	/**
	 * 通过Leader的座位号查询同一层楼所有符合要求的座位号
	 * flag: 0 转正  1 晋升至AD
	 * @param leadercode
	 * @return
	 */
	public List<String> getSeatnoListByLeaderSeatno(String leadercode,int flag);
}
