package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;
import entity.Department;
import entity.Professional_title_Staff;
/**
 *  Department excel表
 * @author skyjiang
 *
 */
public interface QueryDepartmentDao {
	/**
	 * 查询 Department excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public List<Department> queryDepartment();
	/**
	 * 判断是否已经存在该Department_ename
	 * @param DepartmentEname
	 * @return
	 */
	public boolean findDepartmentName(String department);
	/**
	 *  判断是否已经存在该Department_ename
	 * @param department
	 * @return
	 */
	public boolean findDepartmentEName(String department);
	
	public Department queryDepartmentbyName(String Department_ename);
	/**
	 * 根据Department_ename 查询需要修改的Department 
	 * @param department
	 * @return
	 */
	public Department queryDepartmentId(String deptId);
	
	
	
	public Professional_title_Staff queryDepartmentsId(String deptId); 
	
	/**
	 * 保存Department
	 * @param ps
	 * @return
	 */
	public int saveDepartment(Department pss);
	/**
	 * 保存
	 * @param pft
	 * @return
	 */
	public int saveDept(Professional_title_Staff pft);
	
	
	/**
	 * 修改Department信息
	 * @param pss
	 * @return
	 */
	public int ModifyDepartment(Department pss);
	
	/**
	 * 修改Department 信息
	 * @param pss
	 * @return
	 */
	public int ModifyDepartment(Professional_title_Staff pss); 
	
	
	
	public Department queryDepartment(String Department_ename);
	/**
	 * 根据条件查询条数
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param sfyx
	 * @return
	 */
	public int getRow(String startDate,String endDate,String department,String sfyx);
	/**
	 * 分页查询Department
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param sfyx
	 * @param page
	 * @return
	 */
	public List<Department>findDepartment(String startDate,String endDate,String department,String sfyx,Page page);
	/**
	 * 导出StaffDepartment
	 * @param startDate
	 * @param endDate
	 * @param department
	 * @param sfyx
	 * @return
	 */
	public ResultSet DownDepartment(String startDate,String endDate,String department,String sfyx);
	
	
	
	
	/**
	 * 查询分页总条数
	 * @param startDate
	 * @param endDate
	 * @param departmemntName
	 * @param sfyx
	 * @return
	 */
	public int getRows(String startDate, String endDate, String departmemntName,
			String sfyx);
	
	/**
	 * 分页查询Department
	 * @param startDate
	 * @param endDate
	 * @param positionEname
	 * @param sfyx
	 * @param page
	 * @return
	 */
	public List<Professional_title_Staff> findPosition(String startDate,
			String endDate, String positionEname, String sfyx, Page page);
	
	
	
}
