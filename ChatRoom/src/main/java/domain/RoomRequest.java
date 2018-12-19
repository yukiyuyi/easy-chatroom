package domain;

import java.io.Serializable;

public class RoomRequest implements Serializable,Request{
	private static final long serialVersionUID = 1L;
	
	private ChatRoom chatRoom;
	
	private RequestTypeEnum requestType;
	
	public RoomRequest() {
	}
	
	public RoomRequest(ChatRoom chatRoom, RequestTypeEnum requestType) {
		this.chatRoom = chatRoom;
		this.requestType = requestType;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}
	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
	public RequestTypeEnum getRequestType() {
		return requestType;
	}
	public void setRequestType(RequestTypeEnum requestType) {
		this.requestType = requestType;
	}
}
