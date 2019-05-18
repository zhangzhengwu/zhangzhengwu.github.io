package com.orlando.entity;

import java.util.Date;

public class ChatMessageInfoForName {
	private String sendName; //发送信息人
	private String sendContent; //发送信息
	private Date sendTime; //发送信息时间
	private String receiverName; //接收信息人
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
}
