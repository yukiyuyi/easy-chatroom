package domain;

import java.io.Serializable;

public class MessageRequest implements Request,Serializable{
	private static final long serialVersionUID = 1L;
	private RequestTypeEnum requestType;
	private ChatMessage message;

	public MessageRequest(ChatMessage message,RequestTypeEnum requestType) {
		super();
		this.message = message;
		this.requestType = requestType;
	}
	public MessageRequest() {
		
	}

	public ChatMessage getMessage() {
		return message;
	}

	public void setMessage(ChatMessage message) {
		this.message = message;
	}

	@Override
	public RequestTypeEnum getRequestType() {
		return requestType;
	}

	@Override
	public void setRequestType(RequestTypeEnum requestType) {
		this.requestType = requestType;		
	}

}
