package util;

public class Page {

    private int allRows; // 一共有多少行记录

    private int curPage = 1; // 当前页面

    private int pageSize = 15; // 一页显示多少行

    private int allPages; // 一共有多少页

    
    
    
    
    
    
    
    
    
    
    
    
    public int getAllRows(){
       return allRows;

    }

    public void setAllRows(int allRows) {
       this.allRows = allRows;

    }

    public int getCurPage() {
	
    return curPage;

    }

    public void setCurPage(int curPage){
    	if(curPage>getAllPages()){
        	curPage= getAllPages();
        	}
        	else{
        		this.curPage= Util.objIsNULL(curPage)?1:curPage;
        	}
    		this.curPage = curPage;
    		

    }


    public int getAllPages(){
    	if(allRows%pageSize==0 && allRows>0){
    	    allPages=allRows/pageSize;
    	}
		else{
			 allPages= (allRows/pageSize)+1;
    	    		}
    	return allPages;
    }

    public void setAllPages(int allPages){
			   this.allPages = allPages;

    }

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

}

