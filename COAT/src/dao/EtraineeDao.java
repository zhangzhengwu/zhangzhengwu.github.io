package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;

import entity.EtraineeList;
import entity.TraineeList;
/**
 * Etrainee接口
 * @author Wilson
 *
 */
public interface EtraineeDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveTrainee(String filename, InputStream os);//上传文件接口
	/**
	 * trainee
	 * @param start_date
	 * @param end_date
	 * @param Staff_Code
	 * @return
	 */
	public List<TraineeList> queryTraineeList(String start_date,String end_date,String Staff_Code);
	/**
	 * 导出页面查询
	 * @param start_date
	 * @param end_date
	 * @param Staff_Code
	 * @return
	 */
	public List<EtraineeList> queryTraineeReport(String start_date,String end_date,String Staff_Code);
	/**
	 * 根据条件获得相应的consultantReport数据
	 * @param staffcode
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public List<EtraineeList> querybystaff(String staffcode,String startDate,String endDate,int pageSize,int currentPage);
	/**
	 * 根据条件获得条数
	 * @param staffcode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getCount(String staffcode,String startDate,String endDate);
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
	//public List<String []> queryrsbystaff(String staffcode, String startDate,String endDate, int pageSize, int currentPage);
}
