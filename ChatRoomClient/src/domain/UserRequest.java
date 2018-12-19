package domain;

import java.io.Serializable;
//�̳���Request��Serializable
//Request��������
//Serializable�����л�
public class UserRequest implements Serializable,Request{
	private static final long serialVersionUID = 1L;
	private User user;  //User�࣬�����˺�����
	private RequestTypeEnum requestType;  //�������ͣ��������ע�����½
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
