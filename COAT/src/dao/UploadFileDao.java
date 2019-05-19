package dao;

import java.util.List;

public interface UploadFileDao {
/**
 * 保存附件方法
 * @param staffcode
 * @param uploadFileName
 * @param addDate
 * @return
 */
	public int addUploadFile(String staffcode,String uploadFileName,String addDate);
	/**
	 * 查询附件列表
	 * @param staffcode
	 * @return
	 */
	public  List<String> getUploadFile(String staffcode);
}
