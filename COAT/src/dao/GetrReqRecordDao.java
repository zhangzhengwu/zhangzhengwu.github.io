package dao;

import java.io.InputStream;
/**
 * GetrReqRecord接口
 * @author wilson
 *
 */
public interface GetrReqRecordDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveReqRecord(String filename, InputStream os);//上传文件接口
	
}
