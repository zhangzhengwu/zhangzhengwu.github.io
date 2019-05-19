package dao;

import java.io.InputStream;
/**
 * GetrMedicalDao接口
 * @author Wilson
 *
 */
public interface GetrMedicalDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveMedical(String filename, InputStream os);//上传文件接口
	
}
