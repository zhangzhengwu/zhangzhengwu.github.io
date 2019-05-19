package dao;

import java.io.InputStream;
/**
 * staff list excel表
 * @author Wilson.SHEN
 *
 */
public interface GetStaffListDao {
	/**
	 * 保存 staff list excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveStaffList(String filename, InputStream os,String username);
	/**
	 * 保存 staff list excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveStaffMater(String filename, InputStream os,String username);
}
