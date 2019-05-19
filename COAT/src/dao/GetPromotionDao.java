package dao;

import java.io.InputStream;
/**
 * GetPromotionDao接口
 * @author Wilson
 *
 */
public interface GetPromotionDao {
	/**
	 * 导入
	 * @param filename
	 * @param os
	 * @return
	 */
	public int savePromotion(String filename, InputStream os);//上传文件接口
	/**
	 * 从vsmart获取promotion_Checking_report数据转存至COAT
	 * @author kingxu
	 * @date 2017-6-16
	 * @return void
	 */
	void savePromotionCheckingReport();
	
}
