package com.orlando.utils;

 /** 
 * @ClassName: Util 
 * @Description: 通用工具类
 * @author: 章征武【orlando】
 * @date: 2018年9月13日 上午10:41:10 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
public class Util {


	/**
	 * @Title: objIsNULL
	 * @Description: OBJ是否为null或者为“ “, 是返回true
	 * @param @param obj
	 * @param @return    参数
	 * @return boolean    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 上午10:40:17  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public static boolean objIsNULL(Object obj) {
		if (obj == null){
			return true;
		}
		if (obj.toString().trim().length() == 0){
			return true;
		}
		if (obj.toString().toLowerCase().equals("null")){
			return true;
		}
		if (obj.toString().toLowerCase().equals(" ")){
			return true;
		}
		if (obj.toString().toLowerCase().equals("")){
			return true;
		}
		return false;
	}
}
