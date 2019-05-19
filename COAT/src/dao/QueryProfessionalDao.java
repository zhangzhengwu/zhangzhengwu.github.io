package dao;

import java.util.List;

import entity.Professional_title;
/**
 *  professional_title list excel表
 * @author kingxu
 *
 */
public interface QueryProfessionalDao {
	/**
	 * 查询 professional_title list excel表
	 * @param filename
	 * @param os
	 * @return
	 */
	public List<Professional_title> queryProfessional();
	
	
	public Professional_title queryProfessional(String professional_title);
}
