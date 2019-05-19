package dao;

import java.util.List;

import entity.Promotion;

public interface PromotionDao {

/**
 *获取Promotion列表
 * @return
 */
	public List<Promotion> getPromotionList();
	
	/**
	 * 为pc_report_temp表中添加数据
	 * @return
	 */
	public int addPromotion();
	
	public List<Promotion> findByTitle(String position,String staffcode,String staffName);
	
}
