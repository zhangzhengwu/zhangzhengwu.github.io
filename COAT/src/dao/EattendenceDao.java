package dao;

import java.io.InputStream;
/**
 * EattendenceDao接口
 * @author Wilson
 *
 */
public interface EattendenceDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveAttendence(String filename, InputStream os);//上传文件接口
	/**
	 * 查询staffcode在指定时间里面是否有打卡记录
	 * @param staffcode
	 * @param date
	 * @return
	 */
	public String queryEntryTime(String staffcode,String date);
	
}
