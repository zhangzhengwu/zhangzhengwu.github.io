package dao;

import java.io.InputStream;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;
import entity.C_Seatassignment;
import entity.C_SeatassignmentOperation;
/**
 * C_SeatDao接口
 * @author Wilson
 * COAT2014新需求 
 * 2014-5-14 15:19:04
 */
public interface C_SeatDao {
	public int complete(C_Seatassignment cSeatassignment);
	
	public String saveSeat(String filename, InputStream os,String path);
	/**
	 * 删除Seat申请
	 * @param refno
	 * @param staffcode
	 * @return
	 */
	int delSeat(String refno,String staffcode);
	/*public String del(HttpServletRequest request);
	public String search(HttpServletRequest request);*/
	/**
	  * 根据查询AdminService总条数
	  * @param startDate
	  * @param endDate
	  * @param staffcode
	  * @param staffname
	  * @param refno
	  * @param status
	  * @return
	  */
	public int getRow(String startDate,String endDate,String staffcode,String staffname,String refno,String status);
		
	/**
	 * 根据条件分页查询AdminService
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @return
	 */
	public List<C_Seatassignment> findSeatServiceList(String startDate,String endDate,String staffcode,String staffname,String refno,String status,Page page);
	/**
	 * 保存Seat Operation
	 * @return
	 */
	public int saveSeatOpreation(C_SeatassignmentOperation cSeatOpreation);
	 
	/**
	 * 批量保存Seat Record
	 * @return
	 */
	public int saveSeatRecord(C_Seatassignment cSeatassignment);
	/**
	 * 根据Ref.NO操作
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int Ready(String refno,String type,String staffcode,String to,String location);
	
	public int Completed(String refno,String type,String staffcode,String to,String location);
	/**
	 * 根据条件导出
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @return
	 */
	public Result  downSeatServiceList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status);
	
	public C_Seatassignment findSearByRef(String refno);
	
	
	/**
	 * 保存Seat 
	 * @author kingxu
	 * @date 2015-8-14
	 * @param seatassignment
	 * @return
	 * @return String
	 */
	public String saveSeat(C_Seatassignment seatassignment);

	
}
