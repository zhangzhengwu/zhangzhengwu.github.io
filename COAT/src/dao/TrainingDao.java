package dao;

import java.util.List;

public interface TrainingDao {
	/**
	 * 上传Training List
	 * @param list 上传数据源
	 * @param username 上传者
	 * @return
	 */
 int saveTraining(List<List<Object>> list,String userName);
	
}
