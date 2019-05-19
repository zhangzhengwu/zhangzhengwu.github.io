package com.coat.timerTask.dao;

import util.Pager;

public interface SeattingPlanDao {

	/**
	 * 查询状态信息
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager querySeattindPlan(String[] fields, Pager page, Object... objects) throws Exception;
}
