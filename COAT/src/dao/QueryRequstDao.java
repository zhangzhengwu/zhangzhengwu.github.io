package dao;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

import util.Page;
import entity.RequestNewBean;
/**
 * 查询RequstNew列表 DAO类
 * @author Wilson
 *
 */
public interface QueryRequstDao {
	/**
	 *  根据条件查询结果
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public List<RequestNewBean> queryRequstList(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,String nocode,String payer,Page page);
	/**
	 *  根据条件查询结果
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public int queryRow(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,String nocode,String payer);
/**
 * 导出RequestNew列表DAO类
 * @param startDate
 * @param endDate
 * @param location
 * @param urgentCase
 * @return
 */
	public ResultSet queryRequstListSet(String ET_select,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String nocode);
/**
 * 导出RequestNew列表
 * @author kingxu
 * @date 2016-4-20
 * @param ET
 * @param layout_select
 * @param name
 * @param code
 * @param startDate
 * @param endDate
 * @param location
 * @param urgentCase
 * @param payer
 * @param nocode
 * @return
 * @return Result
 */
	public Result queryRequstForResult(String ET,String layout_select,String name,String code,String startDate,String endDate,String location,String urgentCase,String payer,String nocode);
/**
 * 自动导出Request_new 
 * @return
 */
	public ResultSet autoReportRequstListSet() ;

	/**
	 * 自动导出Consultant Info 信息用作Email签名
	 * @return
	 */
	public ResultSet ReportConsInfo();
	
	/**
	 * 导出顾问最新邮件签名信息
	 * 将ResultSet 转成Result
	 * @author kingxu
	 * @date 2015-7-22
	 * @return
	 * @return Result
	 */
	public Result ReportConsInfos();

}
