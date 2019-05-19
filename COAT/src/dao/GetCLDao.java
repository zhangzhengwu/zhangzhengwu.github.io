package dao;

import java.io.InputStream;
import java.util.List;

import entity.Consultant_List;
import entity.Staff_listBean;
/**
 * consultant list excel表
 * @author kingxu
 *
 */
public interface GetCLDao {
	/**
	 * 保存 consultant list excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveCL(String filename, InputStream os,String username);
/**
 * 远程获取consultant
 * @return
 */
	public List<Consultant_List> getAllConsList() throws NullPointerException;
	/**
	 * 插入数据
	 * @param cls
	 */
	public void saveConsultant(List<Consultant_List> cls)  throws Exception;


	/**
	 * 保存Staff
	 * @param list   List<Staff_listBean>
	 */
	public void saveStaff(List<Staff_listBean> list);
	
	/**
	 * 远程获取Staff信息
	 * @return
	 * @throws NullPointerException
	 * @throws Exception 
	 */
	public List<Staff_listBean> getAllStaff()throws Exception;
	
}
