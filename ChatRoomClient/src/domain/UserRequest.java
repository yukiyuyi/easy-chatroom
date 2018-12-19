package domain;

import java.io.Serializable;
//继承了Request和Serializable
//Request：请求类
//Serializable：序列化
public class UserRequest implements Serializable,Request{
	private static final long serialVersionUID = 1L;
	private User user;  //User类，包括账号密码
	private RequestTypeEnum requestType;  //请求类型，这里包括注册与登陆
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
