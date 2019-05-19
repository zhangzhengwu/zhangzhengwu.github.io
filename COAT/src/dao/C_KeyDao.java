package dao;

import java.io.InputStream;
import java.util.List;
import javax.servlet.jsp.jstl.sql.Result;
import util.Page;
import entity.C_Keys;
import entity.C_KeysHistory;
import entity.C_KeysOperation;
import entity.C_Payment;
/**
 * C_SeatDao接口
 * @author Wilson
 * COAT2014新需求 
 * 2014-5-14 15:19:04
 */
public interface C_KeyDao {
	public int saveKeys(String filename, InputStream os) throws Exception;
	public int completed(String refno, String type,String staffcode,C_Payment payment) ;
	public String complete(C_Keys cKeys);
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
	public List<C_Keys> findKeyList(String startDate,String endDate,String staffcode,String staffname,String refno,String status,Page page);
	/**
	 * 保存Seat Operation
	 * @return
	 */
	public int saveKeysOpreation(C_KeysOperation cKeysOperation);
	 
	/**
	 * 批量保存Seat Record
	 * @return
	 */
	public int saveKeysRecord(C_Keys cKeys);
	/**
	 * 根据Ref.NO操作
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int modify(String refno,String type,String staffcode,String sfyx);
	
	public int detele(String refno,String type,String staffcode,String sfyx);
	
	public int VOID(String refno,String type,String staffcode,String sfyx);
	
	
	public int Ready(String refno,String type,String staffcode,String sfyx,String payment,String locaiton,String to);
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
	public Result downKeysList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status);
	
	public C_Keys findKeyByRef(String refno);
	
	public C_KeysHistory findKeyBycode(String staffcode);
/**
 * 导出Reporting for Key
 * @param startDate
 * @param endDate
 * @return
 */
	 Result downReportingForAccessCard(String startDate,String endDate);
}
