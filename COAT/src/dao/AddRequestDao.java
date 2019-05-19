package dao;

import entity.RequestNewBean;
/**
 * 保存RequestDao
 * @author kingxu
 *
 */
public interface AddRequestDao {
	/**
	 * 保存Request
	 * @param rnb
	 * @return
	 */
	public int saveNewRequest(RequestNewBean rnb);
	/**
	 * 保存saveMasterHistory
	 * @param rnb
	 * @return
	 */
	public int saveMasterHistory(RequestNewBean rnb);
	
	
	/**
	 * 是否是DD、DDTree
	 * @param staffcode
	 * @return
	 */
	public boolean findDDorTreeHead(String staffcode);
}
