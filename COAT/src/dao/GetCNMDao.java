package dao;

import java.io.InputStream;
import java.util.List;

import entity.Consultant_Master;
import entity.RequestNewBean;
/**
 * 历史表接口
 * @author kingxu
 *
 */
public interface GetCNMDao {
	/**
	 * 上传数据-保存
	 * @param filename
	 * @param os
	 * @return
	 */
	public int saveCNM(String filename, InputStream os);//上传文件接口
	/**
	 * 获取所有数据
	 * @param StaffNo
	 * @return
	 */
	public List<Consultant_Master> getConsList(String StaffNo);
	/**
	 * 获取历史数据
	 * @param StaffNo
	 * @return
	 */
	public List<Consultant_Master> getCMaster(String StaffNo);
	/**
	 * 
	 * @param StaffNo
	 * @return
	 */
	public List<Consultant_Master> getConsTrList(String StaffNo);
	/**
	 * 更新历史表但不更新numbers 普通权限
	 * @param rnb
	 * @return
	 */
	public int updateCNM(RequestNewBean rnb);
	
	/**
	 * 更新历史表并更新numbers 管理员权限
	 * @param rnb
	 * @return
	 */
	public int updateNumber(RequestNewBean rnb,String reStaffNo);
}
