package dao;

import java.util.List;

import util.Page;

import entity.Staff_listBean;

public interface Staff_listDao {

	/**
	 * 条件查询（全查）
	 */
	 public List<Staff_listBean>  findAll(String staffcode,String companyName,String deptid,String staffname,Page page);
	 
	 /**
	  * 记录总页数
	  */
	 public int selectRow(String staffcode,String companyName,String deptid,String staffname);
	 
	 /**
	  * Excel导出（查询数据）
	  */
	 public List<Staff_listBean> exportDate(String staffcode,String companyName,String deptid,String staffname);
}
