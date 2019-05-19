package dao;

import java.util.List;

import entity.Professional_title_Staff;
/**
 *  professional_title list staff excel表
 * @author kingxu
 *
 */
public interface QueryStaffProfessionalDao {
	/**
	 * 查询 professional_title list staff excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public List<Professional_title_Staff> queryProfessional();
	
	
	public Professional_title_Staff queryStaffProfessional(String professional_title);
}
