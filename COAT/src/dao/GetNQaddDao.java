package dao;

import java.io.InputStream;
/**
 * 上传
 * @author kingxu
 *
 */
public interface GetNQaddDao {
	/**
	 * 上传
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveNQadd(String filename, InputStream os,String add_name);
}
