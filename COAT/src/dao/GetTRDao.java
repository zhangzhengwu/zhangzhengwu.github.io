package dao;

import java.io.InputStream;
/**
 * 上传 TR_LIST
 * @author kingxu
 *
 */
public interface GetTRDao {
	/**
	 * 上传 保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveTR(String filename, InputStream os);
}
