package dao;

import java.io.InputStream;

public interface EleaveConsDao {
	/**
	 * 上传 保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveLeave(String filename, InputStream os,String username);
	
	/**
	 * 查询staffcode在指定日期是否请假
	 * @param staffcode
	 * @param date
	 * @return
	 */
	public String isLeave(String staffcode,String date);

}
