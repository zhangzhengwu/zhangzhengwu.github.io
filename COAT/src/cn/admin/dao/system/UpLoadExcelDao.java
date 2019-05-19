package cn.admin.dao.system;

import java.util.List;

public interface UpLoadExcelDao {

	/**
	 * 保存Excel表格
	 * @param list
	 * @return
	 */
	public int saveExcel(List<List<Object>> list);
}
