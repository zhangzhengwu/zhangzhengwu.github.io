package com.coat.common.dao;

import com.coat.operationrecord.entity.OperationRecord;

/**
 * 保存操作記錄，由於使用BaseDao 操作太過於頻繁會導致鏈接關閉異常故使用單獨的數據庫保存
 * @author kingxu
 *
 */
public interface OperationRecordDao {
	/**
	 * 保存操作記錄
	 * @author kingxu
	 * @date 2016-1-5
	 * @param record
	 * @return
	 * @return int
	 */
	int saveRecord(OperationRecord record);
}
