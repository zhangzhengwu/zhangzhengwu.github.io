package dao;

import java.io.InputStream;

public interface EpartConsultantDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveConsultant(String filename, InputStream os,String username);//上传文件接口
}
