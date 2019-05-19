package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Pager;
import entity.ConsList;
import entity.SeatChangeApply;
import entity.SeatList;

public interface SeatDao {

	/**
	 * 分页查询用户座位列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
	Pager findSeatList(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * 分页查询用户换位操作记录信息
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
	Pager findOperationList(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * 分页查询座位操作记录信息
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
	Pager findSeatMenuOperationList(String[] fields, Pager page, Object... objects) throws Exception;
	
	/**
	 * 根据传入对象对数据库进行单一插入操作
	 * @param seatlist
	 * @return 插入的条数
	 * @throws SQLException 
	 */
	int saveSeatNo(SeatList seatlist,String name,String to,String cc) throws SQLException;
	
	/**
	 * 根据传入对象对数据库进行单一更新操作
	 * @param seatlist
	 * @return 插入的条数
	 * @throws SQLException 
	 */
	int updateSeatNo(SeatList seatlist,String name,String to,String cc,SeatList seatoldlist) throws SQLException;
	
	/**
	 * 分页查询用户座位交换申请历史列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager findSeatChangeApplyList(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * 分页查询用户座位交换申请历史列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager findCleanOperationList(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * 分页查询用户座位交换申请历史列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager findSeatChangeApplyListByStaffcode(String[] fields, Pager page, Object... objects) throws Exception;
	/**
	 * Leader分页查询用户座位交换申请历史列表
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager findSeatChangeApplyListByLeadercode(String[] fields, Pager page, Object... objects) throws Exception;


	/**
	 * 通过用户的staffcode 查询用户座位相关信息
	 * @param staffcodeA
	 * @return
	 */
	SeatList querystaffcodeA(String staffcodeA);


	/**
	 * 通过用户输入的seatno 查询该座位用户相关信息
	 * @param seatnoB
	 * @return
	 */
	SeatList querySeatnoB(String seatnoB);
	
	/**
	 * 判断是否是特殊座位
	 * @param seatno
	 * @return
	 */
//	int getSpecialSeat(String seatno);
	
	/**
	 * 删除座位
	 * @param seatno
	 * @return
	 * @throws SQLException 
	 */
	int deleteSeatNo(String seatno,String name) throws SQLException;
	/**
	 * 座位号是否已存在
	 * @param seatno
	 * @return 座位号个数： 0 不存在，1 存在
	 * @throws SQLException 
	 */
	int existSeatNo(String seatno) throws SQLException;
	/**
	 * 顾问编号是否已存在
	 * @param seatno
	 * @return 顾问编号个数： 0 不存在，1 存在
	 * @throws SQLException 
	 */
	int existStaffCode(String staffcode) throws SQLException;
	
	
	/**
	 * 更加staffcode查询顾问的座位相关信息
	 * @param staffcode
	 * @return
	 * @throws SQLException
	 */
	SeatList getSeatList(String staffcode) throws SQLException;
	/**
	 * 更加seatno查询顾问的座位相关信息
	 * @param seatno
	 * @return
	 * @throws SQLException
	 */
	SeatList getSeatListBySeatNo(String seatno) throws SQLException;
	

	/**
	 * ADM 在增加座位时即给顾问换位
	 * @param seatoldlist
	 * @param location
	 * @param floor
	 * @param seatno
	 * @param lockerno
	 * @param deskDrawerno
	 * @param pigenBoxno
	 * @param name
	 * @param seatType
	 * @param remark
	 * @return 记录数 
	 * @throws SQLException 
	 */
	int change15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException;
	/**
	 * 更新15楼座位信息
	 * @param seatoldlist
	 * @param location
	 * @param floor
	 * @param seatno
	 * @param lockerno
	 * @param deskDrawerno
	 * @param pigenBoxno
	 * @param name
	 * @param seatType
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	int update15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException;
	/**
	 * 更新15楼座位信息
	 * @param seatoldlist
	 * @param location
	 * @param floor
	 * @param seatno
	 * @param lockerno
	 * @param deskDrawerno
	 * @param pigenBoxno
	 * @param name
	 * @param seatType
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	int update15FloorSeats(SeatList seatoldlist,String staffcodetemp,String staffnametemp,String extensionnotemp,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc,String to1,String cc1) throws SQLException;
	/**
	 * 更新非15楼座位信息
	 * @param seatoldlist
	 * @param location
	 * @param floor
	 * @param seatno
	 * @param lockerno
	 * @param deskDrawerno
	 * @param pigenBoxno
	 * @param name
	 * @param seatType
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	int updateNot15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException;
	/**
	 * 更新非15楼座位信息
	 * @param seatoldlist
	 * @param location
	 * @param floor
	 * @param seatno
	 * @param lockerno
	 * @param deskDrawerno
	 * @param pigenBoxno
	 * @param name
	 * @param seatType
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	int updateNot15FloorSeats(SeatList seatoldlist,String staffcodetemp,String staffnametemp,String extensionnotemp,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc,String to1,String cc1) throws SQLException;

	/**
	 * ADM 在增加座位时即给顾问换位
	 * @param seatoldlist
	 * @param location
	 * @param floor
	 * @param seatno
	 * @param lockerno
	 * @param deskDrawerno
	 * @param pigenBoxno
	 * @param name
	 * @param seatType
	 * @param remark
	 * @return 记录数 
	 * @throws SQLException 
	 */
	int changeNot15FloorSeat(SeatList seatoldlist,String location,String floor,String seatno,String lockerno,String deskDrawerno,String pigenBoxno,String name,String ifhidden,String seatType,String remark,String to,String cc) throws SQLException;
	
	/**
	 * 通过用户输入的staffcode 查询该用户座位当前座位相关信息
	 * @param staffcodeB
	 * @return
	 */
	SeatList queryStaffcodeB(String staffcodeB);
	


	/**
	 * 生成流水号
	 * @return refno
	 */
	String getNo();


	/**
	 * 换位申请数据插入数据库
	 * @param seatChangeApply
	 * @return
	 */
	int saveApply(SeatChangeApply seatChangeApply,String name,String staffname,String leadername,String to,String cc);
	
	/**
	 * 申请人删除错误的申请
	 * @param name
	 * @param refno
	 * @return
	 */
	int del(String name,String refno);
	/**
	 * 清空座位
	 * @param name
	 * @param refno
	 * @return
	 * @throws SQLException 
	 */
	int cleanSeatMenu(SeatList seatList,String reason,String name) throws SQLException;
	
	/**
	 * 辨别被换者是否已有未结束换位申请
	 * @param seatnoA
	 * @param seatnoB
	 * @return
	 */
	int ifExist(String seatnoA,String seatnoB);
	/**
	 * 辨别换位者是否已有未结束换位申请
	 * @param staffcodeA
	 * @param staffcodeB
	 * @return
	 */
	int ifMeExist(String staffcodeA);
	/**
	 * 辨别换位者是否处于隐形座位
	 * @param seatnoA
	 * @return
	 */
	int isHidden(String seatnoA);


	/**
	 * 通过流水号查询详细信息
	 * @param refno
	 * @return
	 */
	SeatChangeApply queryListByRefno(String refno);

	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 */
	int updateApplyB(SeatChangeApply seatChangeApply,String name,String staffnameA,String staffnameB,String to,String cc);
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 * @throws SQLException 
	 */
	int requestLeaderConfirm(SeatChangeApply seatChangeApply,String name,SeatList seatListA,SeatList seatListB,String leadernamea,String leadernameb,String to,String cc,String to1) throws SQLException;
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 * @throws SQLException 
	 */
	int responseLeaderConfirm(SeatChangeApply seatChangeApply,String name,SeatList seatListA,SeatList seatListB,String leadernameb,String to,String cc) throws SQLException;
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 */
	int responseConfirm(SeatChangeApply seatChangeApply,String name,String staffnameA,String leadername,String to,String cc);

	public ConsList queryLeaderMsg(String staffcode);
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 */
	int refuseApply(SeatChangeApply seatChangeApply,String name,String staffnameA,String to);
	int refuseApplyByResponse(SeatChangeApply seatChangeApply,String name,String staffnameA,String staffnameB,String to);
	int refuseApplyByReqestLeader(SeatChangeApply seatChangeApply,String name,String staffnameA,String leadernamea,String to,String cc);
	int refuseApplyByReponseLeader(SeatChangeApply seatChangeApply,String name,String staffnameA,String leadernameb,String to,String cc);
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 * @throws SQLException 
	 */
	int updateApplyC(SeatChangeApply seatChangeApply,String name,String to, String cc, SeatList seatListA, SeatList seatListB) throws SQLException;
	/**
	 * 换位申请数据变更
	 * @param seatChangeApply
	 * @return
	 * @throws SQLException 
	 */
	int updateApplyStatus(SeatChangeApply seatChangeApply) throws SQLException;

	/**
	 * 根据code查询Email
	 * @param str
	 * @return
	 */
	public String[] getEmailByCode(String [] str);
	public String[] getEmailsByCode(String [] str);
	
	/**
	 * 获取座位总数
	 * @return
	 */
	int getTotalSeatNumMessage();
	/**
	 * 获取普通座位总数
	 * @return
	 */
	int getTotalPTSeatNumMessage();
	void SynSeatPlan();
	String batchClean();
	/**
	 * 获取已使用的普通座位总数
	 * @return
	 */
	int getTotalUsePTSeatNumMessage();
	/**
	 * 获取已使用的AD座位总数
	 * @return
	 */
	int getTotalUseADSeatNumMessage();
	
	/**
	 * 根据条件导出excel
	 */
	public List<SeatList> exportDate(SeatList seatList);
	/**
	 * 根据楼层编号获取座位列表
	 * @author kingxu
	 * @date 2017-9-30
	 * @param floor
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> findSeatListByFloor(String floor) throws SQLException;
	

	/**
	 * 批量上传SeatList
	 * @param list
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String uploadSeatList(List<List<Object>> list,String user) throws Exception;	
	
	
	/**
	 * 获取所有的PA staffcode
	 * @return
	 * @throws SQLException 
	 */
	public List<String> getPAList() throws SQLException;
}
