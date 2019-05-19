package dao;

import entity.RequestStaffBean;
import entity.RequestStaffConvoyBean;
/**
 * 保存 StaffRequest 
 * @author kingxu
 *
 */
public interface AddStaffRequestDao {
	/**
	 * 保存StaffRequest
	 * @param rnb
	 * @return
	 */
	public int saveStaffRequest(RequestStaffBean staffBean);
	
	/**
	 * saveStaffRequest 保存方法
	 */
	public int  saveStaffRequest(RequestStaffConvoyBean rnb);
 
	
	
	
	/**
	 * 保存saveMasterHistory
	 * @param rnb
	 * @return
	 */
	public int saveStaffMasterHistory(RequestStaffBean staffBean);
	
	/**
	 * 保存saveMasterHistory
	 * @param rnb
	 * @return
	 */
	public int saveStaffConvoyMasterHistory(RequestStaffConvoyBean staffBean);
	
	
	
	
	/**
	 * 保存StaffRequest
	 * @param rnb
	 * @return
	 */
	public int saveStaffRequestConvoy(RequestStaffConvoyBean staffBean);
	
 
	/**
	 * Approve StaffRequestConvoy
	 * @param staffcode
	 * @param urgentDate
	 * @return
	 */
	public int rejectStaffRequestConvoy(String staffcode,String urgentDate,String adminUsername);
	/**
	 * HR 保存StaffRequest
	 * @param staffBean
	 * @return
	 */
	public int saveHRRequestConvoy(RequestStaffConvoyBean staffBean);
	
	/**
	 * Dept 保存Staff Request
	 * @param rnb
	 * @return
	 */
	public int saveDeptRequestConvoy(RequestStaffConvoyBean rnb);
	
}
