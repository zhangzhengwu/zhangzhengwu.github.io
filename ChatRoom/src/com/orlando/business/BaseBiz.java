package com.orlando.business;

 /** 
 * @ClassName: BaseBiz 
 * @Description: 业务接口的父接口
 * @author: 章征武【orlando】
 * @date: 2018年10月8日 下午5:58:25 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
public interface BaseBiz {
	
	/**
	 * @Title: closeConnection
	 * @Description: 关闭数据库
	 * @param     参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 下午5:58:46  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public abstract void closeConnection();
}
