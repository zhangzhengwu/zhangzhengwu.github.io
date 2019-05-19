package dao;

import java.io.InputStream;

public interface EclubDao {
	/**
	 * 上传 保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveClub(String filename, InputStream os,String username);
/**
 * 查询staffcode是否是Club的成员
 * @param staffcode
 * @return
 */
	public int isClub(String staffcode);
	/**
	 * 判断是否C-Club成员
	 * @param staffcode
	 * @return
	 */
	public int sfClub(String staffcode);
}
