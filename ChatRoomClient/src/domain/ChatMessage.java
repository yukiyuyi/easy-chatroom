package domain;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String chatroomId;
	private String fromUsername;
	private String toUsername;
	private String message;
	private String sendTime;
	private RequestTypeEnum code;
	
	public ChatMessage() {
		
	}
	public ChatMessage(String chatroomId, String fromUsername, String toUsername, String message, String sendTime,RequestTypeEnum code) {
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
	public RequestTypeEnum getCode() {
		return code;
	}
	public void setCode(RequestTypeEnum code) {
		this.code = code;
	}
}
