package dao;
 
import java.util.List;

import util.Page;
import entity.QueryAdditional;
/**
 * 
 * @author kingxu
 *
 */
public interface AdditionalDao {
	/**
	 * 用于查询界面
	 * @return
	 */
	public List<QueryAdditional> getQueryAdditional();
	/**
	 * 
	 * @param StaffNo
	 * @param additional
	 * @param Valid
	 * @return
	 */
	public List<QueryAdditional> getQueryAdditional(String StaffNo,String Valid,String start_date,String end_date,Page page);
	/**
	 * 获取总行数
	 * @param StaffNo
	 * @param additional
	 * @param Valid
	 * @return
	 */
	public int  getRows(String StaffNo,String Valid,String start_date,String end_date);
	/**
	 * 保存
	 * @param qa
	 */
	public void add(QueryAdditional qa);
}
