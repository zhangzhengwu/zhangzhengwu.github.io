package dao;

import java.io.InputStream;
/**
 * EmapRecordDao接口
 * @author Wilson
 *
 */
public interface EmapRecordDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveEmap(String filename, InputStream os);//上传文件接口
	/**
	 * 查询staffcode在指定日期是否有会议
	 * @param staffcode
	 * @param date
	 * @return
	 */
	
	public int isMap(String staffcode,String date);
	
}
