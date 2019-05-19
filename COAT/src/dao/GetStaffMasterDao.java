package dao;

import java.io.InputStream;
import java.util.List;

import entity.StaffMasterBean;
/**
 * 上传StaffMaster数据
 * @author kingxu
 *
 */
public interface GetStaffMasterDao {
	/**
	 * 上传数据-保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveStaffMaster(String filename, InputStream os);//上传文件接口
	/**
	 * 获取历史数据
	 * @param StaffNo
	 * @return
	 */
	public List<StaffMasterBean> getStaffaster(String StaffNo);
 
}
