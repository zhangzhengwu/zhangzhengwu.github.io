package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;

import entity.Missingpayment;
import entity.Missingreport;
import entity.Principal;

/**
 * 
 * @author Wilson
 * 2013年10月9日10:58:12
 */
public interface MissingPaymentDao {
	/**
	 * Wilson 
	 * @param filename
	 * @param os
	 * @param username
	 * @return
	 */
	public int uploadmis(String filename, InputStream os, String username) ;
	/**
	 * 查询
	 * @return
	 */
	public List<Missingpayment> selectMissList(String staffcode, String principal, String startdate,
			String enddate,String clientname, String policyno,String ctype,String lastday, int pageSize, int currentPage) ;
	public List<Missingpayment> selectMissListFcons(String staffcode, String principal, String startdate,
			String enddate,String clientname, String policyno,String ctype,String lastday) ;
	/**
	 * 总行数
	 * @param staffcode
	 * @param principal
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public int selectMissAcount(String staffcode, String principal, String startdate,
			String enddate,String clientname, String policyno,String ctype,String lastday);
	//public ResultSet selectMissForReport(String staffcode, String principal, String startdate,String enddate,String clientname, String policyno,String ctype,String lastday);
	
	public List<String[]> selectMissForReport(String staffcode, String principal, String startdate,String enddate,String clientname, String policyno,String ctype,String lastday);
	/**
	 * 删除
	 * @return
	 */
	public int delMissing(String id, String username) ;
	/**
	 * 保存
	 * @return
	 */
	public int saveMissing(Missingpayment ms, String username) ;
	/**
	 * 保存 saveMissingReport
	 * @return
	 */
	public int saveMissingReport(Missingreport ms) ;
	
	public String searchnum() ;
	
	public List<Principal> queryPrincipal();
	/**
	 * 根据当前时间查询STAFFCODE、ADDDATE
	 * @param adddate  
	 * @return  List<Missingpayment>
	 * @throws Exception 
	 */
	public int  findByTime(String date) throws Exception;
}
