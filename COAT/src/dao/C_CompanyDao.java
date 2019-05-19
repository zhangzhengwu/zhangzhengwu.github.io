package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Constant;
import util.DBManager;
import util.Page;
import entity.C_Companyasset;
import entity.C_CompanyassetDetail;
import entity.C_CompanyassetItem;
import entity.C_CompanyassetOperation;
import entity.C_EPayment_List;
import entity.C_marProduct;
/**
 * C_CompanyDao接口
 * @author Wilson
 * COAT2014新需求 
 * 2014-5-28
 */
public interface C_CompanyDao {
	public String findref();
	public String complete(C_Companyasset companyasset);
	/**
	 * 根据Ref.NO 查询CompanyAsset Detail
	 * @param refno
	 * @return
	 */
	List<C_CompanyassetDetail> findCompanydetailBycode(String refno);
	public String completedetail(C_CompanyassetDetail companyassetDetail,String itemcode);
	
	/**
	 * 判断c_companyasset_item中的itemcode是否存在
	 * @param itemcode
	 * @return
	 */
	public int findAllByCode(String itemcode);
	
	/**
	 * 批量保存列表
	 * @param list
	 * @return
	 */
	public int saveCompanyAssetItem(List<List<Object>> list);
	
	/**
	 * 获取c_companyasset_item总行数
	 * @param Itemcode
	 * @param Itemname
	 * @param startDate
	 * @param endDate
	 * @param sfyx
	 * @return
	 */
	public int getAllRow(String Itemcode,String Itemname,String startDate,String endDate,String sfyx);
	
	/**
	 * 根据条件分页查询c_companyasset_item
	 * @param Itemcode
	 * @param Itemname
	 * @param startDate
	 * @param endDate
	 * @param sfyx
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<C_CompanyassetItem> queryProduct(String Itemcode,String Itemname,String startDate,String endDate,String sfyx,int currentPage,int pageSize);
	
	/**
	  * 根据查询C_Companyasset总条数
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
	 * 根据查询C_Companyasset总条数
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param collectionDate
	 * @param returnDate
	 * @return
	 */
	public int getRow(String startDate,String endDate,String staffcode,String staffname,String refno,String status,String collectionDate,String returnDate);
	
	
	/**
	 * 根据条件分页查询C_Companyasset
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @return
	 */
	public List<C_Companyasset> findCompanyList(String startDate,String endDate,String staffcode,String staffname,String refno,String status,Page page);
	
	/**
	 * 根据条件分页查询C_Companyasset
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param collectionDate
	 * @param returnDate
	 * @param status
	 * @param page
	 * @return
	 */
	public List<C_Companyasset> findCompanyList(String startDate,String endDate,String staffcode,String staffname,String refno,String collectionDate,String returnDate,String status,Page page);
	
	
	
	/**
	 * 保存Seat Operation
	 * @return
	 */
	public int saveCompanyOpreation(C_CompanyassetOperation companyassetOperation);
	 
	/**
	 * 批量保存Company Record
	 * @return
	 */
	public int saveCompanyRecord(C_Companyasset Company);
	/**
	 * 根据Ref.NO操作
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int Ready(String refno,String type,String staffcode,String to);

	public int completed(String refno,String type,String staffcode,String to);
	
	public int deleted(String refno,String type,String staffcode);
	
	public int returned(String refno,String type,String staffcode);
	/**
	 * 根据条件导出C_Companyasset
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param page
	 * @return
	 */
	public List<C_Companyasset> downCompanyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status);
	
	/**
	 * 根据条件导出C_Companyasset
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param collectioDate
	 * @param returnDate
	 * @return
	 */
	public List<C_Companyasset> downCompanyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status,String collectioDate,String returnDate);
	
	
	
	
	
	
	public C_Companyasset findCompanyByRef(String refno);
	
	public  List<C_CompanyassetItem> findCompanyBycode();
	public  List<C_CompanyassetItem> findCompanyBycode(String sdate);
	
	/**
	 * 导出reporting 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result downReportingForCompanyAsset(String startDate, String endDate);
	
	/**
	 * 根据条件导出Reporting
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param collectionDate
	 * @param returnDate
	 * @return
	 */
	public Result downReportingForCompanyAsset(String startDate, String endDate,String staffcode,String staffname, String refno,String status,String collectionDate,String returnDate);
	
 
	/**
	 * 获取库存
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result findStore(String startDate, String endDate);
	
	 
	
	/**
	 * 获取产品使用量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Result chargeStore(String startDate, String endDate);
	/**
	 * 根据条件获取产品使用量
	 * @param startDate
	 * @param endDate
	 * @param staffcode
	 * @param staffname
	 * @param refno
	 * @param status
	 * @param collectionDate
	 * @param returnDate
	 * @return
	 */
	public Result chargeStore(String startDate, String endDate,String staffcode,String staffname, String refno,String status,String collectionDate,String returnDate);
	
	
}
