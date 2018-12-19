package domain;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fromUsername; //消息发送方
	private String toUsername;   //消息接收方
	private String message;      //消息内容
 	private String sendTime;     //发送时间
	private RequestTypeEnum code;//消息类型
	
	private String chatroomId;
	public RequestTypeEnum getCode() {
		return code;
	}
	public void setCode(RequestTypeEnum code) {
		this.code = code;
	}
	public ChatMessage() {
		
	}
	public ChatMessage(String chatroomId, String fromUsername, String toUsername,
			String message, String sendTime,RequestTypeEnum code) {
		super();
		this.chatroomId = chatroomId;
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
		this.message = message;
		this.sendTime = sendTime;
		this.code=code;
	}
	public String getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
