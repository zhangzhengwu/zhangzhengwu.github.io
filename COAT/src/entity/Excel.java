package entity;

import java.util.List;

public class Excel {

	private String title; // 文件内容标题
	
	private String filepath;//文件生成路径
	
	private  String filename;//文件名称
	
	private String[] columns;//存放EXCEL 列明的名称
	
	private List<?> excelContentList;//内容列表
    
    private String sheetname;//sheet名称
    
    private String foldername;
    //表头名称
    private String[] headerNames ;
    
	// 无参构造函数
	 public Excel(){
		 
	 }
	//有参构造函数
	 public Excel(String title,String filepath,String filename,String[] columns,String sheetname){
		 this.title=title;
		 this.filepath=filepath;
		 this.filename=filename;
		 this.columns=columns;
	 }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String getSheetname() {
		return sheetname;
	}
	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}
	public List<?> getExcelContentList() {
		return excelContentList;
	}
	public void setExcelContentList(List<?> excelContentList) {
		this.excelContentList = excelContentList;
	}
	public String[] getHeaderNames() {
		return headerNames;
	}
	public void setHeaderNames(String[] headerNames) {
		this.headerNames = headerNames;
	}
	public String getFoldername() {
		return foldername;
	}
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}
	 
	
	
	
}
