package dao;

import java.io.InputStream;
/**
 * resignCons接口
 * @author Wilson
 *
 */
public interface EresignConsDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveResignCons(String filename, InputStream os);//上传文件接口
	
	/**
	 * 判断Consultant是否离职
	 * @param staffcode
	 * @return
	 */
	public int isResignList(String staffcode);
	
}
