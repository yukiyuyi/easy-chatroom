package domain;

import java.io.Serializable;

public class UserRequest implements Serializable,Request{
	private static final long serialVersionUID = 1L;
	private User user;
	private RequestTypeEnum requestType;
	public UserRequest() {
	}
	
	public UserRequest(User user, RequestTypeEnum requestType) {
		this.user = user;
		this.requestType = requestType;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RequestTypeEnum getRequestType() {
		return requestType;
	}
	public void setRequestType(RequestTypeEnum requestType) {
		this.requestType = requestType;
	}
}
