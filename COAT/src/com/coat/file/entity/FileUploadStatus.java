package com.coat.file.entity;

public class FileUploadStatus {

	
	
	private String byteProcessed;//已传输大小
	private String filesize;//文件大小
	private String percent;//完成百分比
	private String uploadRate;//上传速度
	private String usetime;//已耗时
	private String totaltime;//预计总耗时
	private String status;//上传状态
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 文件上传状态监测
	 * @param byteProcessed 已传输大小
	 * @param filesize 文件总大小
	 * @param percent 传输百分比
	 * @param uploadRate 传输速率
	 * @param usetime 已耗时
	 * @param totaltime 预计耗时
	 * @param status 状态
	 */
	
	public FileUploadStatus(String byteProcessed, String filesize,
			String percent, String uploadRate, String usetime,
			String totaltime, String status) {
		super();
		this.byteProcessed = byteProcessed;
		this.filesize = filesize;
		this.percent = percent;
		this.uploadRate = uploadRate;
		this.usetime = usetime;
		this.totaltime = totaltime;
		this.status = status;
	}
	
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getByteProcessed() {
		return byteProcessed;
	}
	public void setByteProcessed(String byteProcessed) {
		this.byteProcessed = byteProcessed;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}
	public String getTotaltime() {
		return totaltime;
	}
	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUploadRate() {
		return uploadRate;
	}
	public void setUploadRate(String uploadRate) {
		this.uploadRate = uploadRate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
