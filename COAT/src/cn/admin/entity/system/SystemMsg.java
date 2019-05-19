package cn.admin.entity.system;


public class SystemMsg {
	private int msgId;// 自增ID
	private String msgContent;// 消息内容
	private String msgEffectStartDate;// 消息影响的开始日期
	private String msgEffectEndDate;// 消息影响的结束日期
	private String msgCtime;// 创建时间
	private int isShow;// 是否显示这个记录,默认是0不显示，1是显示。
	private int msgType;
	private String msgEffectStartTime; // 消息影响的开始时间
	private String msgEffectEndTime;// 消息影响的结束时间
	private String msgEffectList;// 接收消息的集合。

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgEffectStartDate() {
		return msgEffectStartDate;
	}

	public void setMsgEffectStartDate(String msgEffectStartDate) {
		this.msgEffectStartDate = msgEffectStartDate;
	}

	public String getMsgEffectEndDate() {
		return msgEffectEndDate;
	}

	public void setMsgEffectEndDate(String msgEffectEndDate) {
		this.msgEffectEndDate = msgEffectEndDate;
	}

	public String getMsgCtime() {
		return msgCtime;
	}

	public void setMsgCtime(String msgCtime) {
		this.msgCtime = msgCtime;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgEffectStartTime() {
		return msgEffectStartTime;
	}

	public void setMsgEffectStartTime(String msgEffectStartTime) {
		this.msgEffectStartTime = msgEffectStartTime;
	}

	public String getMsgEffectEndTime() {
		return msgEffectEndTime;
	}

	public void setMsgEffectEndTime(String msgEffectEndTime) {
		this.msgEffectEndTime = msgEffectEndTime;
	}

	public String getMsgEffectList() {
		return msgEffectList;
	}

	public void setMsgEffectList(String msgEffectList) {
		this.msgEffectList = msgEffectList;
	}

	public SystemMsg() {

	}

	public SystemMsg(String msgcontent, String msgeffectStartTime,
			String msgeffectEndTime, int isshow, int msgType,
			String msgEffectStartTime, String msgEffectEndTime,
			String msgEffectList) {
		this.isShow = isshow;
		this.msgContent = msgcontent;
		this.msgEffectEndDate = msgeffectEndTime;
		this.msgEffectStartDate = msgeffectStartTime;
		this.msgType = msgType;
		this.msgEffectEndTime = msgEffectEndTime;
		this.msgEffectEndTime = msgEffectStartTime;
		this.msgEffectEndTime = msgEffectList;
	}

	public SystemMsg(int id, String msgcontent, String msgeffectStartTime,
			String msgeffectEndTime, String ctime, int isshow, int msgType,
			String msgEffectStartTime, String msgEffectEndTime,
			String msgEffectList) {
		this.msgId = id;
		this.isShow = isshow;
		this.msgContent = msgcontent;
		this.msgCtime = ctime;
		this.msgEffectEndDate = msgeffectEndTime;
		this.msgEffectStartDate = msgeffectStartTime;
		this.msgType = msgType;
		this.msgEffectEndTime = msgEffectEndTime;
		this.msgEffectEndTime = msgEffectStartTime;
		this.msgEffectEndTime = msgEffectList;
	}

}
