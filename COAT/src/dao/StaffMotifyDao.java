package dao;

/**
 * 
 * @author Wilson
 *
 */
public interface StaffMotifyDao {
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateStaffRequest(String staffcode,String urgentdate,String username);
 
}
