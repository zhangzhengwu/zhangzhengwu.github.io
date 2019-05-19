package dao;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DateUtils;

public interface B_commissionDao {

	/**
	 * 上传发拥明细表
	 */
public int saveCommission(String filename, InputStream os,String username);

/**
 * 计算
 */
public String cacular(OutputStream os,HSSFWorkbook wb,HSSFSheet sheet);
	
}
