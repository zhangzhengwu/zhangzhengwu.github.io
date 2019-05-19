package dao;

import java.io.InputStream;
/**
 * Excetption接口
 * @author Wilson
 *
 */
public interface EExceptionDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveException(String filename, InputStream os);//上传文件接口
	/**
	 * 指定日期是否放假
	 * @param date
	 * @return
	 */
	public int isholiday(String date);
}
