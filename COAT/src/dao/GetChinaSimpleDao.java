package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;

import entity.ChinaConslist;
import entity.ChinaPolicy;
/**
 * 上传 saveSimpl_LIST
 * @author Wilson
 *
 */
public interface GetChinaSimpleDao {
	/**
	 * 上传 保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveSimple(String filename, InputStream os);
	
	//分组查询
	public List<ChinaPolicy> getPolicyGroup();
	//查询
	public ChinaConslist getStaffList(String staffname,String salesOffice,String other);
	/**
	 * 查询首年
	 * @param staffname
	 * @param salesOffice
	 * @param other
	 * @return
	 */
	public ResultSet getFristComi(String staffname,String salesOffice,String other);
	/**
	 * 查询续
	 * @param staffname
	 * @param salesOffice
	 * @param other
	 * @return
	 */
	public ResultSet getTwoComi(String staffname,String salesOffice,String other);
	/**
	 * 查询续年
	 * @param staffname
	 * @param salesOffice
	 * @param other
	 * @return
	 */
	public ResultSet getFristRate(String staffname,String salesOffice,String other);
	/**
	 * 查询团险
	 * @param staffname
	 * @param salesOffice
	 * @param other
	 * @return
	 */
	public ResultSet getPolicyTeam(String staffname,String salesOffice,String other);
	/**
	 * 查询第一辅导体系
	 * @param staffname
	 * @param salesOffice
	 * @param other
	 * @return
	 */
	public ResultSet getPolicyCoach(String staffname,String salesOffice,String other);
	/**
	 * 查询第二辅导体系
	 * @param staffname
	 * @param salesOffice
	 * @param other
	 * @return
	 */
	public ResultSet getPolicyCoachTwo(String staffname,String salesOffice,String other);
	
}
