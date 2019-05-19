package dao;

import java.io.InputStream;

public interface EConsultantDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveConsultant(String filename, InputStream os,String username);//上传文件接口
	
	
	/**
	 * 计算MAP
	 * @param filename
	 * @param os
	 * @param username
	 * @return
	 */
	public int cacular(String filename,InputStream os,String username);

	/**
	 * 计算顾问个人打卡数据汇总
	 * @author kingxu
	 * @date 2015-10-15
	 * @param filename
	 * @param os
	 * @param username
	 * @return
	 * @return int
	 */
	public int cacularConsultantAttendance(String filename,String username);
}
