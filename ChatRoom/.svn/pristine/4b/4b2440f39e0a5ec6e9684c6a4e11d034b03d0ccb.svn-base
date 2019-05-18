package com.orlando.entity;

import java.io.Serializable;
import java.util.Date;

 /** 
 * @ClassName: ChatUserInfo 
 * @Description: 聊天室成员信息实体类
 * @author: 章征武【orlando】
 * @date: 2018年9月13日 上午10:13:46 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
public class ChatUserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userName;
	private String userPwd;
	private String userNick;
	private String userImg;
	private String userEmail;
	private String userPhone;
	private String userCardId;
	private Date userRegisterTime;
	private Boolean userState;
	private String userRemark;
	public Date getUserRegisterTime() {
		return userRegisterTime;
	}
	public void setUserRegisterTime(Date userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserCardId() {
		return userCardId;
	}
	public void setUserCardId(String userCardId) {
		this.userCardId = userCardId;
	}
	
	public Boolean getUserState() {
		return userState;
	}
	public void setUserState(Boolean userState) {
		this.userState = userState;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatUserInfo other = (ChatUserInfo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ChatUserInfo [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", userNick="
				+ userNick + ", userImg=" + userImg + ", userEmail=" + userEmail + ", userPhone=" + userPhone
				+ ", userCardId=" + userCardId + ", userRegisterTime=" + userRegisterTime + ", userState=" + userState
				+ ", userRemark=" + userRemark + "]";
	}
	

}
