package com.orlando.entity;

import java.util.ArrayList;
import java.util.List;

import com.orlando.business.ChatMessageInfoBiz;
import com.orlando.business.impl.ChatMessageInfoBizImpl;

public class Page {
	private Integer rownum = 3;  //每页显示记录条数
	private Integer pagenum;  //当前第几页
	private ChatMessageInfoBiz chatMessage = new ChatMessageInfoBizImpl();
	List<ChatMessageInfoForName> list; //用于存储一次性查询出来的所有记录
	public Page(){
		list = chatMessage.showAllChatMessage();  //初始化时查询出所有要显示的聊天信息
	}
	public Page(Integer rownum){
		this();
		this.rownum = rownum;
	}
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public Integer getPagenum() {
		return pagenum;
	}
	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}
	
	/**
	 * @Title: showMessageList
	 * @Description: 指定某页需要显示的记录
	 * @param @param pagenum
	 * @param @return    参数
	 * @return List<ChatMessageInfoForName>    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月11日 下午4:39:20  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public List<ChatMessageInfoForName> showMessageList(Integer pagenum){
		// 假定每页显示3条，
		// 第一页显示的就是 list 中的 0 1 2     
		// 第二页显示的就是 list 中的 3 4 5     
		// 第三页显示的就是 list 中的 6 7 8    
		// 第一个记录为 (pagenum-1)*rownum, 最后一个记录其实是下一页第一个记录的前一个记录，即 pagenum*rownum -1 
		int startnum = (pagenum-1)* this.rownum;  
		int endnum = pagenum*this.rownum - 1;
		if(endnum >= (this.list.size()-1)){
			endnum = (this.list.size()-1);
		}
		List<ChatMessageInfoForName> pageList = new ArrayList<>();
		for (int i = startnum; i <= endnum; i++) {
			pageList.add(this.list.get(i));
		}
		return pageList;
	}
	
	/**
	 * @Title: lastPageNum
	 * @Description: 获取末页
	 * @param @return    参数
	 * @return int    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月11日 下午5:29:55  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public int getLastPageNum(){
		int lastPageNum = -1;
		if(this.list.size()%this.rownum == 0){
			lastPageNum = this.list.size() / this.rownum;
		}else{
			lastPageNum = (this.list.size() / this.rownum) + 1;
		}
		return lastPageNum;
	}
}
