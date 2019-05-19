package util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Pager {

	private int pagenow=1;//当前页码
	private int pagesize=15;//每页多少行
	private int totalpage;//总页数
	private int total;//总记录数
	private List list;//当页数据
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Pager(int pagenow, int pagesize) {
		this.pagenow = pagenow;
		this.pagesize = pagesize;
	}





	/**
	 * 带数据分页
	 * @param pagenow
	 * @param pagesize
	 * @param totalpage
	 * @param total
	 * @param list
	 */
	public Pager(int pagenow, int pagesize, int totalpage, int total, List list) {
		super();
		this.pagenow = pagenow;
		this.pagesize = pagesize;
		this.totalpage = totalpage;
		this.total = total;
		this.list = list;
	}
	
	
	
	
	
	/**
	 * 不带数据分页
	 * @param pagenow
	 * @param pagesize
	 * @param totalpage
	 * @param total
	 */
	public Pager(int pagenow, int pagesize, int totalpage, int total) {
		super();
		this.pagenow = pagenow;
		this.pagesize = pagesize;
		this.totalpage = totalpage;
		this.total = total;
	}
	
	
	
	
	
	public Pager(HttpServletRequest request){
		String pagenow=request.getParameter("pagenow");
		String pagesize=request.getParameter("pagesize");
		this.pagenow=Util.objIsNULL(pagenow)?1:Integer.parseInt(pagenow);
		this.pagesize=Util.objIsNULL(pagesize)?15:Integer.parseInt(pagesize);
	}
	
	
	
	public Pager() {
		super();
	}
	public int getPagenow() {
		return pagenow;
	}
	public void setPagenow(int pagenow) {
		   if (pagenow < 1) {
			   pagenow = 1;
	        } else{
        		pagenow= Util.objIsNULL(pagenow)?1:pagenow;
        	}
		
		this.pagenow = pagenow;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		
		this.totalpage = totalpage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		if(total%pagesize==0 && total>0){
    	    this.totalpage=total/pagesize;
    	}
		else{
			 this.totalpage= (total/pagesize)+1;
    	}
		if(this.pagenow>this.totalpage){
			pagenow=totalpage;
		}
		this.total = total;
	}
	
	
	
	public void setList(List list) {
		this.list = list;
	}
	public List getList() {
		return list;
	}
	
	
	
	
}
