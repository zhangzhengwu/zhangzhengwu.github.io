package dao;

import java.sql.ResultSet;
import java.util.List;

import entity.Econsreport;

public interface EpartConsultantReportDao {
	/**
	 * 根据条件获得相应的consultantReport数据
	 * @param staffcode
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<Econsreport> querybystaff(String staffcode,String startDate,String endDate,int pageSize,int currentPage);
	/**
	 * 根据条件获得条数
	 * @param staffcode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getCount(String staffcode,String startDate,String endDate);
	/**
	 * 根据条件删除相应的consultantreport
	 * @param staffcode
	 * @param mapdate
	 * @return
	 */
	public int deleteConsultantReport(String staffcode,String mapdate);
	/**
	 * 导出Excel文件
	 * @param staffcode
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public ResultSet queryrsbystaff(String staffcode, String startDate,String endDate, int pageSize, int currentPage);
	//public List<String[]> queryrsbystaff(String staffcode, String startDate,String endDate, int pageSize, int currentPage);

}
