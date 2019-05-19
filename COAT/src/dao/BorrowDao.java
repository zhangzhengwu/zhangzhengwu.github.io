package dao;

import java.io.InputStream;

public interface BorrowDao {
	/**
	 * 上传 保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveBorrow(String filename, InputStream os,String username);

}
