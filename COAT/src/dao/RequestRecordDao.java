package dao;

import entity.RequestNewBean;

/**
 * 当次卡片记录
 * @author kingxu
 *
 */
public interface RequestRecordDao {
	/**
	 * 
	 * @param code
	 * @param name
	 * @param quantity
	 * @param upd_name
	 * @param Layout_Type
	 * @return
	 */
	public int saveRequestRecord(String UrgentDate,String code,String name,String quantity,String upd_name,String Layout_Type,String Urgent);
	
	public int updateRequestRecord(RequestNewBean rnb,String Payer, String rePayer);
	
	/**
	 * 验证Consultant是否办理过Elite
	 * @param staffcode
	 * @return
	 */
	public boolean vailElite_request_new(String staffcode);
	/**
	 * 验证Consultant是否办理过Elite 并排除本条记录
	 * @param staffcode
	 * @param urgentDate
	 * @return
	 */
	public boolean vailElite_request_new(String staffcode,String urgentDate);
	
	
	/**
	 * 验证staff是否办理过Elite
	 * @param staffcode
	 * @return
	 */
	public boolean vailElite_request_staff(String staffcode);
	
	public boolean vailElite_request_macau(String staffcode);
	 
}
