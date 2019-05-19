package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;
import entity.Department;

public interface DepartMentDao {

	/**
	 * 分页查询Depart
	 * @param startDate
	 * @param endDate
	 * @param dpt
	 * @param sfyx
	 * @param page
	 * @return List<Department>
	 */
	public List<Department> find(String startDate,String endDate,String dpt,String sfyx,Page page);
	/**
	 * 查询分页总条数
	 * @param startDate
	 * @param endDate
	 * @param dpt
	 * @param sfyx
	 * @return int
	 */
	public int getRow(String startDate,String endDate,String dpt,String sfyx);
	/**
	 * 导出Department
	 * @param startDate
	 * @param endDate
	 * @param dpt
	 * @param sfyx
	 * @return
	 */
	public ResultSet downDepartMent(String startDate, String endDate, String dpt,String sfyx);
	/**
	 * 验证DepartMent是否存在
	 * @param dptId
	 * @return
	 */
	public Department findById(int dptId);
	/**
	 * 验证DepartMent是否 存在
	 * @param dpts
	 * @return
	 */
	public boolean findByDpt(String dpts);
	/**
	 * 验证staff是否为DepartHead
	 * @param staffcode
	 * @return
	 */
	public boolean vailHead(String staffcode);
	
	/**
	 * 保存DepartMent
	 * @param dpt
	 * @return int
	 */
	public int saveDepart(Department dpt);
	/**
	 * 修改DepartMent
	 * @param dpt
	 * @return int
	 */ 
	public int modifyDepart(Department dpt);
	
	
	/**
	 * 验证是否为上级
	 * @author kingxu
	 * @date 2016-2-25
	 * @param staffcode
	 * @return
	 * @return boolean
	 */
	public boolean vailSupervisor(String staffcode);

	
}
