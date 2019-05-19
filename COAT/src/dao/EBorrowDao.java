package dao;

import java.io.InputStream;

public interface EBorrowDao {
	/**
	 * 上传 保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveBorrow(String filename, InputStream os,String username);
	/**
	 * 查询staffcode在指定时间内是否有借卡记录
	 * @param staffcode
	 * @param date
	 * @return
	 */
	public int isBorrow(String staffcode,String date);

}
